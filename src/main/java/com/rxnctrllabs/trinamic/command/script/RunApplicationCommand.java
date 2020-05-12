package com.rxnctrllabs.trinamic.command.script;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class RunApplicationCommand extends TrinamicCommand {

    public enum Type {
        CURRENT(0), SPECIFIC(1);

        public final int value;

        Type(final int value) {
            this.value = value;
        }
    }

    public RunApplicationCommand() {
        super(TrinamicCommand.RUN_APP, Type.CURRENT.value, TrinamicCommand.DO_NOT_CARE, TrinamicCommand.DO_NOT_CARE);
    }

    public RunApplicationCommand(final long position) {
        super(TrinamicCommand.RUN_APP, Type.SPECIFIC.value, TrinamicCommand.DO_NOT_CARE, position);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
