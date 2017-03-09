package com.rxnctrllabs.trinamic.command.control;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class JumpAlwaysCommand extends TrinamicCommand {

    public JumpAlwaysCommand(final long value) {
        super(TrinamicCommand.JA, TrinamicCommand.DO_NOT_CARE, TrinamicCommand.DO_NOT_CARE, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
