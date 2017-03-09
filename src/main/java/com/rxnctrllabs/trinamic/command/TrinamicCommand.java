package com.rxnctrllabs.trinamic.command;

import java.util.Arrays;

public abstract class TrinamicCommand {

    public static final int COMMAND_LENGTH = 9;

    public static final int MODULE_ADDRESS = 1;
    public static final int DO_NOT_CARE = 0;

    public static final int ROR = 1;
    public static final int ROL = 2;
    public static final int MST = 3;
    public static final int MVP = 4;

    public static final int SAP = 5;
    public static final int GAP = 6;
    public static final int STAP = 7;
    public static final int RSAP = 8;

    public static final int SGP = 9;
    public static final int GGP = 10;
    public static final int STGP = 11;
    public static final int RSGP = 12;

    public static final int SIO = 14;
    public static final int GIO = 15;

    public static final int CALC = 19;
    public static final int COMP = 20;

    public static final int JC = 21;
    public static final int JA = 22;
    public static final int WAIT = 27;

    public static final int STOP = 28;

    public static final int STOP_APP = 128;
    public static final int RUN_APP = 129;
    public static final int RESET_APP = 131;

    public static final int START_DL_MODE = 132;
    public static final int EXIT_DL_MODE = 133;

    public static final int APP_STATUS = 135;

    private final Integer moduleAddress;
    private final Integer command;
    private final Integer type;
    private final Integer motor;
    private final Long value;
    private final Integer checksum;

    private final Integer[] datagram;

    public TrinamicCommand(final Integer command, final Integer type, final Integer motor, final long value) {
        this.moduleAddress = MODULE_ADDRESS;
        this.command = command;
        this.type = type;
        this.motor = motor;
        this.value = value;

        this.datagram = new Integer[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        this.datagram[0] = MODULE_ADDRESS;
        this.datagram[1] = command;
        this.datagram[2] = type;
        this.datagram[3] = motor;

        this.datagram[4] = (int) value >> 24 & 0xFF;
        this.datagram[5] = (int) value >> 16 & 0xFF;
        this.datagram[6] = (int) value >> 8 & 0xFF;
        this.datagram[7] = (int) value & 0xFF;

        for (int i = 0; i < COMMAND_LENGTH - 1; i++) {
            this.datagram[8] += this.datagram[i];
        }
        this.checksum = this.datagram[8] %= 0x0100;
    }

    public int getModuleAddress() {
        return this.moduleAddress;
    }

    public int getCommandNumber() {
        return this.command;
    }

    public int getType() {
        return this.type;
    }

    public int getMotor() {
        return this.motor;
    }

    public long getValue() {
        return this.value;
    }

    public int getChecksum() {
        return this.checksum;
    }

    public Integer[] getDatagram() {
        return this.datagram;
    }

    public abstract void accept(ICommandVisitor visitor);

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        final TrinamicCommand that = (TrinamicCommand) obj;

        return Arrays.equals(this.datagram, that.datagram);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.datagram);
    }

    @Override
    public String toString() {
        return "TrinamicCommand{" +
                "checksum=" + checksum +
                ", value=" + value +
                ", motor=" + motor +
                ", type=" + type +
                ", command=" + command +
                ", moduleAddress=" + moduleAddress +
                '}';
    }

    public String toDatagramString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i : this.datagram) {
            String formattedByte = String.format("%02X ", i & 0xFF);
            stringBuilder.append(formattedByte);
        }
        return stringBuilder.toString().trim();
    }
}
