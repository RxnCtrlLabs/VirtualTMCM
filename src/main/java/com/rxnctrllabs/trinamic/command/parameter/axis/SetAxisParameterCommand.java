package com.rxnctrllabs.trinamic.command.parameter.axis;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class SetAxisParameterCommand extends TrinamicCommand {

    public SetAxisParameterCommand(final int type, final int motor, final long value) {
        super(TrinamicCommand.SAP, type, motor, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
