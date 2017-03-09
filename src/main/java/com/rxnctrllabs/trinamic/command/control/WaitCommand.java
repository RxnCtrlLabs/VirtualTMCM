package com.rxnctrllabs.trinamic.command.control;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

import java.util.HashMap;
import java.util.Map;

public class WaitCommand extends TrinamicCommand {

    public enum Type {
        TICKS(0), POS(1), REFSW(2), LIMSW(3), RFS(4);

        public final int value;

        Type(final int value) {
            this.value = value;
        }
    }

    private static final Map<String, Type> typeNameMap = new HashMap<>();
    private static final Map<Integer, Type> typeNumberMap = new HashMap<>();

    static {
        typeNameMap.put("TICKS", Type.TICKS);
        typeNameMap.put("POS", Type.POS);
        typeNameMap.put("REFSW", Type.REFSW);
        typeNameMap.put("LIMSW", Type.LIMSW);
        typeNameMap.put("RFS", Type.RFS);

        typeNumberMap.put(0, Type.TICKS);
        typeNumberMap.put(1, Type.POS);
        typeNumberMap.put(2, Type.REFSW);
        typeNumberMap.put(3, Type.LIMSW);
        typeNumberMap.put(4, Type.RFS);
    }

    public static Type get(String option) {
        return typeNameMap.get(option.toUpperCase());
    }

    public static Type get(final int typeNumber) {
        return typeNumberMap.get(typeNumber);
    }

    public WaitCommand(final Type type, final int motor, final long value) {
        super(TrinamicCommand.WAIT, type.value, motor, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
