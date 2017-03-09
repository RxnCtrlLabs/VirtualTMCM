package com.rxnctrllabs.trinamic.command.script;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class ResetApplicationCommand extends TrinamicCommand {

    public ResetApplicationCommand() {
        super(TrinamicCommand.RESET_APP, TrinamicCommand.DO_NOT_CARE, TrinamicCommand.DO_NOT_CARE, TrinamicCommand.DO_NOT_CARE);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
