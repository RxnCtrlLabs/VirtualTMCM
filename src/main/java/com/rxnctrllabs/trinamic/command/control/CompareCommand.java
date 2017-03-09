package com.rxnctrllabs.trinamic.command.control;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class CompareCommand extends TrinamicCommand {

    public CompareCommand(final long value) {
        super(TrinamicCommand.COMP, TrinamicCommand.DO_NOT_CARE, TrinamicCommand.DO_NOT_CARE, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
