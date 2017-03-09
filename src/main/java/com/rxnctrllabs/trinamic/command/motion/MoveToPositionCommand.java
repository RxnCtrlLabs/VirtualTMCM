package com.rxnctrllabs.trinamic.command.motion;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

import java.util.HashMap;
import java.util.Map;

public class MoveToPositionCommand extends TrinamicCommand {

    public enum Type {
        ABS(0), REL(1);

        public final int value;

        Type(final int value) {
            this.value = value;
        }
    }

    private static final Map<String, Type> typeNameMap = new HashMap<>();
    private static final Map<Integer, Type> typeNumberMap = new HashMap<>();

    static {
        typeNameMap.put("ABS", Type.ABS);
        typeNameMap.put("REL", Type.REL);

        typeNumberMap.put(0, Type.ABS);
        typeNumberMap.put(1, Type.REL);
    }

    public static Type get(String option) {
        return typeNameMap.get(option.toUpperCase());
    }

    public static Type get(final int typeNumber) {
        return typeNumberMap.get(typeNumber);
    }

    public MoveToPositionCommand(final Type type, final int motor, final long value) {
        super(TrinamicCommand.MVP, type.value, motor, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
