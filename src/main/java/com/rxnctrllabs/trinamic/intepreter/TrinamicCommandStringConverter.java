package com.rxnctrllabs.trinamic.intepreter;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class TrinamicCommandStringConverter {

    private interface ICommandBuilder {
        TrinamicCommand build(String[] options, final TrinamicScript script);
    }

    private static final Map<String, ICommandBuilder> commandBuilderMap = new HashMap<>();

    static {
        commandBuilderMap.put("ROR", (options, script) -> {
            int motor = Integer.parseInt(options[1]);
            long value = Long.parseLong(options[2]);
            return new RotateRightCommand(motor, value);
        });

        commandBuilderMap.put("ROL", (options, script) -> {
            int motor = Integer.parseInt(options[1]);
            long value = Long.parseLong(options[2]);
            return new RotateLeftCommand(motor, value);
        });

        commandBuilderMap.put("MST", (options, script) -> {
            int motor = Integer.parseInt(options[1]);
            return new StopRotationCommand(motor);
        });

        commandBuilderMap.put("MVP", (options, script) -> {
            MoveToPositionCommand.Type type = MoveToPositionCommand.get(options[1]);
            int motor = Integer.parseInt(options[2]);
            long value = Long.parseLong(options[3]);
            return new MoveToPositionCommand(type, motor, value);
        });

        commandBuilderMap.put("SAP", (options, script) -> {
            int type = Integer.parseInt(options[1]);
            int motor = Integer.parseInt(options[2]);
            long value = Long.parseLong(options[3]);
            return new SetAxisParameterCommand(type, motor, value);
        });

        commandBuilderMap.put("GAP", (options, script) -> {
            int type = Integer.parseInt(options[1]);
            int motor = Integer.parseInt(options[2]);
            return new GetAxisParameterCommand(type, motor);
        });

        commandBuilderMap.put("STAP", (options, script) -> {
            int type = Integer.parseInt(options[1]);
            int motor = Integer.parseInt(options[2]);
            return new StoreAxisParameterCommand(type, motor);
        });

        commandBuilderMap.put("RSAP", (options, script) -> {
            int type = Integer.parseInt(options[1]);
            int motor = Integer.parseInt(options[2]);
            return new RestoreAxisParameterCommand(type, motor);
        });

        commandBuilderMap.put("SGP", (options, script) -> {
            int type = Integer.parseInt(options[1]);
            int motor = Integer.parseInt(options[2]);
            long value = Long.parseLong(options[3]);
            return new SetGlobalParameterCommand(type, motor, value);
        });

        commandBuilderMap.put("GGP", (options, script) -> {
            int type = Integer.parseInt(options[1]);
            int motor = Integer.parseInt(options[2]);
            return new GetGlobalParameterCommand(type, motor);
        });

        commandBuilderMap.put("STGP", (options, script) -> {
            int type = Integer.parseInt(options[1]);
            int motor = Integer.parseInt(options[2]);
            return new StoreGlobalParameterCommand(type, motor);
        });

        commandBuilderMap.put("RSGP", (options, script) -> {
            int type = Integer.parseInt(options[1]);
            int motor = Integer.parseInt(options[2]);
            return new RestoreGlobalParameterCommand(type, motor);
        });

        commandBuilderMap.put("SIO", (options, script) -> {
            int type = Integer.parseInt(options[1]);
            int motor = Integer.parseInt(options[2]);
            long value = Long.parseLong(options[3]);
            return new SetOutputCommand(type, motor, value);
        });

        commandBuilderMap.put("GIO", (options, script) -> {
            int type = Integer.parseInt(options[1]);
            int motor = Integer.parseInt(options[2]);
            return new GetInputCommand(type, motor);
        });

        commandBuilderMap.put("CALC", (options, script) -> {
            CalculationCommand.Type type = CalculationCommand.get(options[1]);
            long value = Long.parseLong(options[2]);
            return new CalculationCommand(type, value);
        });

        commandBuilderMap.put("COMP", (options, script) -> {
            long value = Long.parseLong(options[1]);
            return new CompareCommand(value);
        });

        commandBuilderMap.put("JC", (options, script) -> {
            Map<String, Integer> commandNumberLabelMap = script.getLabelToCommandNumberMap();

            JumpConditionalCommand.Type type = JumpConditionalCommand.get(options[1]);
            long value = commandNumberLabelMap.get(options[2]);
            return new JumpConditionalCommand(type, value);
        });

        commandBuilderMap.put("JA", (options, script) -> {
            Map<String, Integer> commandNumberLabelMap = script.getLabelToCommandNumberMap();

            long value = commandNumberLabelMap.get(options[1]);
            return new JumpAlwaysCommand(value);
        });

        commandBuilderMap.put("WAIT", (options, script) -> {
            WaitCommand.Type type = WaitCommand.get(options[1]);
            int motor = Integer.parseInt(options[2]);
            long value = Long.parseLong(options[3]);
            return new WaitCommand(type, motor, value);
        });

        commandBuilderMap.put("STOP", (options, script) -> new StopProgramCommand());
    }

    static List<TrinamicCommand> convertTrinamicScript(TrinamicScript script) {
        List<TrinamicCommand> commandList = new ArrayList<>();

        TrinamicCommand newCommand = null;
        for (String commandString : script.getCommandStrings()) {
            String[] options = commandString.split(" |, ");
            String commandName = options[0];

            ICommandBuilder commandBuilder = commandBuilderMap.get(commandName.toUpperCase());
            if (commandBuilder == null) {
                throw new RuntimeException("Unsupported Command " + commandName);
            }

            newCommand = commandBuilder.build(options, script);
            commandList.add(newCommand);
        }

        if (newCommand == null || newCommand.getCommandNumber() != TrinamicCommand.STOP) {
            commandList.add(new StopProgramCommand());
        }

        return commandList;
    }
}
