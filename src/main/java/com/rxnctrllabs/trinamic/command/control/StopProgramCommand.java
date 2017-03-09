package com.rxnctrllabs.trinamic.command.control;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class StopProgramCommand extends TrinamicCommand {

    public StopProgramCommand() {
        super(TrinamicCommand.STOP, TrinamicCommand.DO_NOT_CARE, TrinamicCommand.DO_NOT_CARE, TrinamicCommand.DO_NOT_CARE);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
