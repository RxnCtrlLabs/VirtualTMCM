package com.rxnctrllabs.virtualtmcm;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.receiving.command.IncomingCommandHandler;

import javax.bluetooth.BluetoothStateException;
import javax.bluetooth.LocalDevice;

public class VirtualMotorApplication {

    private final BluetoothCommunicationService bluetoothCommunicationService;

    private VirtualMotorApplication(final BluetoothCommunicationService bluetoothCommunicationService) {
        this.bluetoothCommunicationService = bluetoothCommunicationService;
    }

    private void launch() {
        this.bluetoothCommunicationService.start();
    }

    public static void main(String[] args) throws BluetoothStateException {
        final LocalDevice localDevice = LocalDevice.getLocalDevice();

        final IncomingCommandHandler receivedCommandHandler = new IncomingCommandHandler();
        final ICommandVisitor commandVisitor = new CommandIdentifier();

        final BluetoothCommunicationService bluetoothCommunicationService = new BluetoothCommunicationService(localDevice, receivedCommandHandler, commandVisitor);

        final VirtualMotorApplication virtualMotorApplication = new VirtualMotorApplication(bluetoothCommunicationService);
        virtualMotorApplication.launch();
    }
}
