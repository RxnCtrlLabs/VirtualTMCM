package com.rxnctrllabs.trinamic.receiving.command;

import com.rxnctrllabs.trinamic.command.TrinamicCommand;
import com.rxnctrllabs.trinamic.command.calculation.CalculationCommand;
import com.rxnctrllabs.trinamic.command.control.CompareCommand;
import com.rxnctrllabs.trinamic.command.control.JumpAlwaysCommand;
import com.rxnctrllabs.trinamic.command.control.JumpConditionalCommand;
import com.rxnctrllabs.trinamic.command.control.StopProgramCommand;
import com.rxnctrllabs.trinamic.command.control.WaitCommand;
import com.rxnctrllabs.trinamic.command.io.GetInputCommand;
import com.rxnctrllabs.trinamic.command.io.SetOutputCommand;
import com.rxnctrllabs.trinamic.command.motion.MoveToPositionCommand;
import com.rxnctrllabs.trinamic.command.motion.RotateLeftCommand;
import com.rxnctrllabs.trinamic.command.motion.RotateRightCommand;
import com.rxnctrllabs.trinamic.command.motion.StopRotationCommand;
import com.rxnctrllabs.trinamic.command.parameter.axis.GetAxisParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.axis.RestoreAxisParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.axis.SetAxisParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.axis.StoreAxisParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.global.GetGlobalParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.global.RestoreGlobalParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.global.SetGlobalParameterCommand;
import com.rxnctrllabs.trinamic.command.parameter.global.StoreGlobalParameterCommand;
import com.rxnctrllabs.trinamic.command.script.ExitDownloadModeCommand;
import com.rxnctrllabs.trinamic.command.script.ResetApplicationCommand;
import com.rxnctrllabs.trinamic.command.script.RunApplicationCommand;
import com.rxnctrllabs.trinamic.command.script.StartDownloadModeCommand;
import com.rxnctrllabs.trinamic.command.script.StopApplicationCommand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IncomingCommandHandler {

    private interface ICommandBuilder {
        TrinamicCommand build(Integer typeNumber, Integer motorNumber, Long value);
    }

    private static final Map<Integer, ICommandBuilder> COMMAND_BUILDER_MAP = new HashMap<>();

    static {
        COMMAND_BUILDER_MAP.put(TrinamicCommand.ROR, (typeNumber, motorNumber, value) -> new RotateRightCommand(motorNumber, value));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.ROL, (typeNumber, motorNumber, value) -> new RotateLeftCommand(motorNumber, value));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.MST, (typeNumber, motorNumber, value) -> new StopRotationCommand(motorNumber));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.MVP, (typeNumber, motorNumber, value) -> new MoveToPositionCommand(MoveToPositionCommand.get(typeNumber), motorNumber, value));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.SAP, SetAxisParameterCommand::new);

        COMMAND_BUILDER_MAP.put(TrinamicCommand.GAP, (typeNumber, motorNumber, value) -> new GetAxisParameterCommand(typeNumber, motorNumber));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.STAP, (typeNumber, motorNumber, value) -> new StoreAxisParameterCommand(typeNumber, motorNumber));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.RSAP, (typeNumber, motorNumber, value) -> new RestoreAxisParameterCommand(typeNumber, motorNumber));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.SGP, SetGlobalParameterCommand::new);

        COMMAND_BUILDER_MAP.put(TrinamicCommand.GGP, (typeNumber, motorNumber, value) -> new GetGlobalParameterCommand(typeNumber, motorNumber));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.STGP, (typeNumber, motorNumber, value) -> new StoreGlobalParameterCommand(typeNumber, motorNumber));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.RSGP, (typeNumber, motorNumber, value) -> new RestoreGlobalParameterCommand(typeNumber, motorNumber));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.SIO, SetOutputCommand::new);

        COMMAND_BUILDER_MAP.put(TrinamicCommand.GIO, (typeNumber, motorNumber, value) -> new GetInputCommand(typeNumber, motorNumber));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.CALC, (typeNumber, motorNumber, value) -> new CalculationCommand(CalculationCommand.get(typeNumber), value));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.COMP, (typeNumber, motorNumber, value) -> new CompareCommand(value));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.JC, (typeNumber, motorNumber, value) -> new JumpConditionalCommand(JumpConditionalCommand.get(typeNumber), value));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.JA, (typeNumber, motorNumber, value) -> new JumpAlwaysCommand(value));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.WAIT, (typeNumber, motorNumber, value) -> new WaitCommand(WaitCommand.get(typeNumber), motorNumber, value));

        COMMAND_BUILDER_MAP.put(TrinamicCommand.STOP, (typeNumber, motorNumber, value) -> new StopProgramCommand());

        COMMAND_BUILDER_MAP.put(TrinamicCommand.STOP_APP, (typeNumber, motorNumber, value) -> new StopApplicationCommand());

        COMMAND_BUILDER_MAP.put(TrinamicCommand.RUN_APP, (typeNumber, motorNumber, value) -> new RunApplicationCommand());

        COMMAND_BUILDER_MAP.put(TrinamicCommand.RESET_APP, (typeNumber, motorNumber, value) -> new ResetApplicationCommand());

        COMMAND_BUILDER_MAP.put(TrinamicCommand.START_DL_MODE, (typeNumber, motorNumber, value) -> new StartDownloadModeCommand());

        COMMAND_BUILDER_MAP.put(TrinamicCommand.EXIT_DL_MODE, (typeNumber, motorNumber, value) -> new ExitDownloadModeCommand());
    }

    private final List<Integer> receivedBytes;
    private final List<IncomingCommandHandlerObserver> observers;

    public IncomingCommandHandler() {
        this.receivedBytes = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addObserver(final IncomingCommandHandlerObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(final IncomingCommandHandlerObserver observer) {
        this.observers.remove(observer);
    }

    public void addByte(final int incomingByte) {
        this.receivedBytes.add(incomingByte & 0xFF);

        checkCompleteCommand();
    }

    public void resetBuffer() {
        this.receivedBytes.clear();
    }

    private void checkCompleteCommand() {
        if (this.receivedBytes.size() >= TrinamicCommand.COMMAND_LENGTH) {
            List<Integer> fullCommand = new ArrayList<>(this.receivedBytes.subList(0, TrinamicCommand.COMMAND_LENGTH));
            this.receivedBytes.removeAll(fullCommand);

            TrinamicCommand receivedCommand = parseCommand(fullCommand);

            Integer checksum = fullCommand.get(8);
            if (receivedCommand != null && receivedCommand.getChecksum() == checksum) {
                notifyCommandReceived(receivedCommand);
            }
        }
    }

    private TrinamicCommand parseCommand(List<Integer> fullCommand) {
        Integer commandNumber = fullCommand.get(1);
        Integer typeNumber = fullCommand.get(2);
        Integer motorNumber = fullCommand.get(3);

        Long value = 0L;
        value += fullCommand.get(4) * 0x01_00_00_00;
        value += fullCommand.get(5) * 0x01_00_00;
        value += fullCommand.get(6) * 0x01_00;
        value += fullCommand.get(7);

        ICommandBuilder commandBuilder = COMMAND_BUILDER_MAP.get(commandNumber);
        if (commandBuilder != null) {
            return commandBuilder.build(typeNumber, motorNumber, value);
        }
        return null;
    }

    private void notifyCommandReceived(final TrinamicCommand receivedCommand) {
        for (IncomingCommandHandlerObserver observer : this.observers) {
            observer.notifyCommandReceived(receivedCommand);
        }
    }
}
