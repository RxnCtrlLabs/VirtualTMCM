package com.rxnctrllabs.trinamic.command.parameter.global;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class StoreGlobalParameterCommand extends TrinamicCommand {

    public StoreGlobalParameterCommand(final int type, final int motor) {
        super(TrinamicCommand.STGP, type, motor, TrinamicCommand.DO_NOT_CARE);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
