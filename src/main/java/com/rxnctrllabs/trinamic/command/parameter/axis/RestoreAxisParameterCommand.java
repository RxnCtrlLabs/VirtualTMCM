package com.rxnctrllabs.trinamic.command.parameter.axis;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class RestoreAxisParameterCommand extends TrinamicCommand {

    public RestoreAxisParameterCommand(final int type, final int motor) {
        super(TrinamicCommand.RSAP, type, motor, TrinamicCommand.DO_NOT_CARE);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
