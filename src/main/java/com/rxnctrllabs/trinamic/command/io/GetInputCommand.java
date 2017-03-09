package com.rxnctrllabs.trinamic.command.io;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class GetInputCommand extends TrinamicCommand {

    public GetInputCommand(final int type, final int motor) {
        super(TrinamicCommand.GIO, type, motor, TrinamicCommand.DO_NOT_CARE);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
