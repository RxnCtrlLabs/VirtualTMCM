package com.rxnctrllabs.trinamic.command.calculation;

import com.rxnctrllabs.trinamic.command.ICommandVisitor;
import com.rxnctrllabs.trinamic.command.TrinamicCommand;

import java.util.HashMap;
import java.util.Map;

public class CalculationCommand extends TrinamicCommand {

    public enum Type {
        ADD(0), SUB(1), MUL(2), DIV(3), MOD(4), AND(5), OR(6), XOR(7), NOT(8), LOAD(9);

        public final int value;

        Type(final int value) {
            this.value = value;
        }
    }

    private static final Map<String, Type> typeNameMap = new HashMap<>();
    private static final Map<Integer, Type> typeNumberMap = new HashMap<>();

    static {
        typeNameMap.put("ADD", Type.ADD);
        typeNameMap.put("SUB", Type.SUB);
        typeNameMap.put("MUL", Type.MUL);
        typeNameMap.put("DIV", Type.DIV);
        typeNameMap.put("MOD", Type.MOD);
        typeNameMap.put("AND", Type.AND);
        typeNameMap.put("OR", Type.OR);
        typeNameMap.put("XOR", Type.XOR);
        typeNameMap.put("NOT", Type.NOT);
        typeNameMap.put("LOAD", Type.LOAD);

        typeNumberMap.put(0, Type.ADD);
        typeNumberMap.put(1, Type.SUB);
        typeNumberMap.put(2, Type.MUL);
        typeNumberMap.put(3, Type.DIV);
        typeNumberMap.put(4, Type.MOD);
        typeNumberMap.put(5, Type.AND);
        typeNumberMap.put(6, Type.OR);
        typeNumberMap.put(7, Type.XOR);
        typeNumberMap.put(8, Type.NOT);
        typeNumberMap.put(9, Type.LOAD);
    }

    public static Type get(String option) {
        return typeNameMap.get(option.toUpperCase());
    }

    public static Type get(final int typeNumber) {
        return typeNumberMap.get(typeNumber);
    }

    public CalculationCommand(final Type type, final long value) {
        super(TrinamicCommand.CALC, type.value, TrinamicCommand.DO_NOT_CARE, value);
    }

    @Override
    public void accept(final ICommandVisitor visitor) {
        visitor.visit(this);
    }
}
