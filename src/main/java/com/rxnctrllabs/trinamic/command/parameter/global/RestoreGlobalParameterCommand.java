package com.rxnctrllabs.trinamic.command.parameter.global;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class RestoreGlobalParameterCommand extends TrinamicCommand {

    public RestoreGlobalParameterCommand(final int type, final int motor) {
        super(TrinamicCommand.RSGP, type, motor, TrinamicCommand.DO_NOT_CARE);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
