package com.rxnctrllabs.trinamic.command.motion;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class StopRotationCommand extends TrinamicCommand {

    public StopRotationCommand(final int motor) {
        super(TrinamicCommand.MST, TrinamicCommand.DO_NOT_CARE, motor, TrinamicCommand.DO_NOT_CARE);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
