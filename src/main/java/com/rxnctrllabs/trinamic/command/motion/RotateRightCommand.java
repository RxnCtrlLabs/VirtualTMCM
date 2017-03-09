package com.rxnctrllabs.trinamic.command.motion;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class RotateRightCommand extends TrinamicCommand {

    public RotateRightCommand(final int motor, final long value) {
        super(TrinamicCommand.ROR, TrinamicCommand.DO_NOT_CARE, motor, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
