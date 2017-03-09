package com.rxnctrllabs.trinamic.command.motion;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class RotateLeftCommand extends TrinamicCommand {

    public RotateLeftCommand(final int motor, final long value) {
        super(TrinamicCommand.ROL, TrinamicCommand.DO_NOT_CARE, motor, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
