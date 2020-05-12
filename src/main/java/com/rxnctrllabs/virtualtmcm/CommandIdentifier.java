package com.rxnctrllabs.virtualtmcm;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.calculation.CalculationCommand;
import com.rxnctrllabs.trinamic.command.control.*;
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
import com.rxnctrllabs.trinamic.command.script.*;
import com.rxnctrllabs.trinamic.receiving.response.TrinamicResponse;
import com.rxnctrllabs.virtualtmcm.store.AxisParameterStore;
import com.rxnctrllabs.virtualtmcm.store.GlobalParameterStore;

import static com.rxnctrllabs.trinamic.command.parameter.axis.AxisParameter.ACTUAL_POSITION;

public class CommandIdentifier implements ICommandVisitor {

    private TrinamicResponse nextResponse;

    @Override
    public void visit(final CalculationCommand command) {
        System.out.println("CalculationCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final CompareCommand command) {
        System.out.println("CompareCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final JumpAlwaysCommand command) {
        System.out.println("JumpAlwaysCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final JumpConditionalCommand command) {
        System.out.println("JumpConditionalCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final StopProgramCommand command) {
        System.out.println("StopProgramCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final WaitCommand command) {
        System.out.println("WaitCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final GetInputCommand command) {
        System.out.println("GetInputCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final SetOutputCommand command) {
        System.out.println("SetOutputCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final MoveToPositionCommand command) {
        System.out.println("MoveToPositionCommand: " + command.toDatagramString());

        long pulseCount = AxisParameterStore.getAxisParameter(ACTUAL_POSITION);
        switch (MoveToPositionCommand.get(command.getType())) {
            case ABS:
                pulseCount = command.getValue();
                break;
            case REL:
                pulseCount += command.getValue();
                break;
        }

        System.out.println("Moving to position: " + pulseCount);
        AxisParameterStore.setAxisParameter(ACTUAL_POSITION, pulseCount);
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final RotateLeftCommand command) {
        System.out.println("RotateLeftCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final RotateRightCommand command) {
        System.out.println("RotateRightCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final StopRotationCommand command) {
        System.out.println("StopRotationCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final GetAxisParameterCommand command) {
        System.out.println("GetAxisParameterCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .withValue(AxisParameterStore.getAxisParameter(command.getType()))
                .build();
    }

    @Override
    public void visit(final RestoreAxisParameterCommand command) {
        System.out.println("RestoreAxisParameterCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final StoreAxisParameterCommand command) {
        System.out.println("StoreAxisParameterCommand: " + command.toDatagramString());

        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final SetAxisParameterCommand command) {
        System.out.println("SetAxisParameterCommand: " + command.toDatagramString());

        AxisParameterStore.setAxisParameter(command.getType(), command.getValue());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final GetGlobalParameterCommand command) {
        System.out.println("GetGlobalParameterCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .withValue(GlobalParameterStore.INSTANCE.getGlobalParameter(command.getType()))
                .build();
    }

    @Override
    public void visit(final RestoreGlobalParameterCommand command) {
        System.out.println("RestoreGlobalParameterCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final StoreGlobalParameterCommand command) {
        System.out.println("StoreGlobalParameterCommand: " + command.toDatagramString());

        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final SetGlobalParameterCommand command) {
        System.out.println("SetGlobalParameterCommand: " + command.toDatagramString());

        GlobalParameterStore.INSTANCE.setGlobalParameter(command.getType(), command.getValue());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final ExitDownloadModeCommand command) {
        System.out.println("ExitDownloadModeCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final ResetApplicationCommand command) {
        System.out.println("ResetApplicationCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final ApplicationStatusCommand command) {
        System.out.println("ApplicationStatusCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final RunApplicationCommand command) {
        System.out.println("RunApplicationCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final StartDownloadModeCommand command) {
        System.out.println("StartDownloadModeCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    @Override
    public void visit(final StopApplicationCommand command) {
        System.out.println("StopApplicationCommand: " + command.toDatagramString());
        this.nextResponse = TrinamicResponseBuilder.from(command)
                .build();
    }

    TrinamicResponse getNextResponse() {
        return nextResponse;
    }
}