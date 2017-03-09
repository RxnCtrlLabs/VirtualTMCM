package com.rxnctrllabs.trinamic.command.parameter.axis;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public class StoreAxisParameterCommand extends TrinamicCommand {

    public StoreAxisParameterCommand(final int type, final int motor) {
        super(TrinamicCommand.STAP, type, motor, TrinamicCommand.DO_NOT_CARE);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
