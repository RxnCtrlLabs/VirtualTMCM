package com.rxnctrllabs.trinamic.command.parameter.axis;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class GetAxisParameterCommand extends TrinamicCommand {

    public GetAxisParameterCommand(final int type, final int motor) {
        super(TrinamicCommand.GAP, type, motor, TrinamicCommand.DO_NOT_CARE);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
