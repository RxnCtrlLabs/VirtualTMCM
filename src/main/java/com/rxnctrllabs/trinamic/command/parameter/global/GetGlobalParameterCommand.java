package com.rxnctrllabs.trinamic.command.parameter.global;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class GetGlobalParameterCommand extends TrinamicCommand {

    public GetGlobalParameterCommand(final int type, final int motor) {
        super(TrinamicCommand.GGP, type, motor, TrinamicCommand.DO_NOT_CARE);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
