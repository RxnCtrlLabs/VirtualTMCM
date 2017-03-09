package com.rxnctrllabs.trinamic.command.control;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

import java.util.HashMap;
import java.util.Map;

public class JumpConditionalCommand extends TrinamicCommand {

    public enum Type {
        ZE(0), NZ(1), EQ(2), NE(3), GT(4), GE(5), LT(6), LE(7), ETO(8), EAL(9);

        public final int value;

        Type(final int value) {
            this.value = value;
        }
    }

    private static final Map<String, Type> typeNameMap = new HashMap<>();
    private static final Map<Integer, Type> typeNumberMap = new HashMap<>();

    static {
        typeNameMap.put("ZE", Type.ZE);
        typeNameMap.put("NZ", Type.NZ);
        typeNameMap.put("EQ", Type.EQ);
        typeNameMap.put("NE", Type.NE);
        typeNameMap.put("GT", Type.GT);
        typeNameMap.put("GE", Type.GE);
        typeNameMap.put("LT", Type.LT);
        typeNameMap.put("LE", Type.LE);
        typeNameMap.put("ETO", Type.ETO);
        typeNameMap.put("EAL", Type.EAL);

        typeNumberMap.put(0, Type.ZE);
        typeNumberMap.put(1, Type.NZ);
        typeNumberMap.put(2, Type.EQ);
        typeNumberMap.put(3, Type.NE);
        typeNumberMap.put(4, Type.GT);
        typeNumberMap.put(5, Type.GE);
        typeNumberMap.put(6, Type.LT);
        typeNumberMap.put(7, Type.LE);
        typeNumberMap.put(8, Type.ETO);
        typeNumberMap.put(9, Type.EAL);
    }

    public static Type get(String option) {
        return typeNameMap.get(option.toUpperCase());
    }

    public static Type get(final int typeNumber) {
        return typeNumberMap.get(typeNumber);
    }

    public JumpConditionalCommand(final Type type, final long value) {
        super(TrinamicCommand.JC, type.value, TrinamicCommand.DO_NOT_CARE, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
