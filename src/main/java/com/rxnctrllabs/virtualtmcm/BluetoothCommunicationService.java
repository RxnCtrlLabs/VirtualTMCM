package com.rxnctrllabs.virtualtmcm;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;
import com.rxnctrllabs.trinamic.receiving.command.IncomingCommandHandler;
import com.rxnctrllabs.trinamic.receiving.command.IncomingCommandHandlerObserver;
import com.rxnctrllabs.trinamic.receiving.response.TrinamicResponse;
import com.rxnctrllabs.virtualtmcm.parameterstore.AxisParameterStore;
import com.rxnctrllabs.virtualtmcm.parameterstore.GlobalParameterStore;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.DiscoveryAgent;
import javax.bluetooth.LocalDevice;
import javax.bluetooth.UUID;
import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;
import javax.microedition.io.StreamConnectionNotifier;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

public class BluetoothCommunicationService implements IncomingCommandHandlerObserver {

    private static final UUID IM_UUID = new UUID("0000110100001000800000805f9b34fb", false);
    private static final String BLUETOOTH_URL = String.format("btspp://localhost:%s;name=RemoteBluetooth", IM_UUID);

    private enum ConnectionState {
        NONE, LISTENING, CONNECTED
    }

    private class AcceptThread extends Thread {

        private final StreamConnectionNotifier connectionNotifier;

        AcceptThread(final LocalDevice localDevice) {
            StreamConnectionNotifier temp = null;

            try {
                localDevice.setDiscoverable(DiscoveryAgent.GIAC);

                temp = (StreamConnectionNotifier) Connector.open(BLUETOOTH_URL);
            } catch (BluetoothStateException e) {
                System.out.println("Bluetooth is not turned on.");
            } catch (IOException ignored) {
            }
            this.connectionNotifier = temp;
        }

        @Override
        public void run() {
            setName("AcceptThread");

            Optional<StreamConnection> connection;
            while (!isConnected()) {
                try {
                    System.out.println("Waiting for connection..");
                    connection = Optional.of(this.connectionNotifier.acceptAndOpen());
                } catch (IOException e) {
                    System.out.println("acceptAndOpen() failed");
                    break;
                }

                connection.ifPresent(this::tryConnection);
            }
        }

        private void tryConnection(final StreamConnection connection) {
            synchronized (BluetoothCommunicationService.this) {
                switch (BluetoothCommunicationService.this.currentState) {
                    case LISTENING:
                        connected(connection);
                        break;
                    case NONE:
                    case CONNECTED:
                        try {
                            connection.close();
                        } catch (IOException ignored) {
                        }
                        break;
                }
            }
        }

        void cancel() {
            try {
                this.connectionNotifier.close();
            } catch (IOException ignored) {
            }
        }
    }

    private class ConnectedThread extends Thread {

        private final StreamConnection connection;
        private InputStream inputStream;
        private OutputStream outputStream;

        ConnectedThread(final StreamConnection connection) {
            this.connection = connection;

            try {
                this.inputStream = connection.openInputStream();
                this.outputStream = connection.openOutputStream();
            } catch (IOException ignored) {
            }
        }

        @Override
        public void run() {
            setName("ConnectedThread");
            System.out.println("Starting ConnectedThread");

            byte[] buffer = new byte[32];
            int numRead;

            while (true) {
                try {
                    numRead = this.inputStream.read(buffer);
                    for (int i = 0; i < numRead; i++) {
                        BluetoothCommunicationService.this.incomingCommandHandler.addByte(buffer[i]);
                    }
                } catch (IOException e) {
                    connectionLost();
                    break;
                }
            }
            System.out.println("Ending ConnectedThread");

            connectionLost();
        }

        void writeResponse(Integer[] datagram) {
            try {
                for (int i : datagram) {
                    this.outputStream.write(i);
                }
            } catch (IOException ignored) {
            }
        }

        void cancel() {
            try {
                this.connection.close();
            } catch (IOException ignored) {
            }
        }
    }

    private ConnectionState currentState;
    private AcceptThread acceptThread;
    private ConnectedThread connectedThread;

    private final LocalDevice localDevice;
    private final IncomingCommandHandler incomingCommandHandler;
    private final ICommandVisitor commandVisitor;

    BluetoothCommunicationService(final LocalDevice localDevice, final IncomingCommandHandler incomingCommandHandler, final ICommandVisitor commandVisitor) {
        this.commandVisitor = commandVisitor;
        this.currentState = ConnectionState.NONE;

        this.localDevice = localDevice;
        this.incomingCommandHandler = incomingCommandHandler;
        this.incomingCommandHandler.addObserver(this);
    }

    @Override
    public void notifyCommandReceived(final TrinamicCommand receivedCommand) {
        receivedCommand.accept(this.commandVisitor);

        int type = receivedCommand.getType();
        int commandNumber = receivedCommand.getCommandNumber();

        Long value = 0L;
        switch (commandNumber) {
            case TrinamicCommand.SAP:
            case TrinamicCommand.STAP:
                AxisParameterStore.setAxisParameter(type, receivedCommand.getValue());
                break;
            case TrinamicCommand.SGP:
            case TrinamicCommand.STGP:
                GlobalParameterStore.setGlobalParameter(type, receivedCommand.getValue());
                break;
            case TrinamicCommand.GAP:
                value = AxisParameterStore.getAxisParameter(type);
                break;
            case TrinamicCommand.GGP:
                value = GlobalParameterStore.getGlobalParameter(type);
                break;
        }

        sendResponse(TrinamicResponseBuilder.from(receivedCommand)
                .withValue(value)
                .build());
    }

    synchronized void start() {
        closeConnectedThread();

        setState(ConnectionState.LISTENING);

        if (this.acceptThread == null) {
            this.acceptThread = new AcceptThread(this.localDevice);
            this.acceptThread.start();
        }
    }

    private void sendResponse(TrinamicResponse response) {
        ConnectedThread temp;

        synchronized (this) {
            if (!this.currentState.equals(ConnectionState.CONNECTED)) {
                return;
            }
            temp = this.connectedThread;
            this.incomingCommandHandler.resetBuffer();
        }

        temp.writeResponse(response.getDatagram());
    }

    private synchronized void connected(StreamConnection connection) {
        closeConnectedThread();
        closeAcceptThread();

        this.connectedThread = new ConnectedThread(connection);
        this.connectedThread.start();

        setState(ConnectionState.CONNECTED);
    }

    private void connectionLost() {
        System.out.println("Connection Lost");
        start();
    }

    private boolean isConnected() {
        return this.currentState.equals(ConnectionState.CONNECTED);
    }

    private synchronized void setState(ConnectionState newState) {
        this.currentState = newState;
    }

    private void closeAcceptThread() {
        if (this.acceptThread != null) {
            this.acceptThread.cancel();
            this.acceptThread = null;
        }
    }

    private void closeConnectedThread() {
        if (this.connectedThread != null) {
            this.connectedThread.cancel();
            this.connectedThread = null;
        }
    }
}