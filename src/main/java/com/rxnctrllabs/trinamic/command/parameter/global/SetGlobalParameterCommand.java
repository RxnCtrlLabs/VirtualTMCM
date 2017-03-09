package com.rxnctrllabs.trinamic.command.parameter.global;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class SetGlobalParameterCommand extends TrinamicCommand {

    public SetGlobalParameterCommand(final int type, final int motor, final long value) {
        super(TrinamicCommand.SGP, type, motor, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
