package com.rxnctrllabs.trinamic.command.io;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class SetOutputCommand extends TrinamicCommand {

    public SetOutputCommand(final int type, final int motor, final long value) {
        super(TrinamicCommand.SIO, type, motor, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
