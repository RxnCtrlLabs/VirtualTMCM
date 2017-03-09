package com.rxnctrllabs.trinamic.receiving.response;

import java.util.Arrays;

public class TrinamicResponse {

    public static final int RESPONSE_LENGTH = 9;

    public static final int WRONG_CHECKSUM = 1;
    public static final int INVALID_COMMAND = 2;
    public static final int WRONG_TYPE = 3;
    public static final int INVALID_VALUE = 4;
    public static final int EEPROM_LOCKED = 5;
    public static final int COMMAND_NOT_AVAILABLE = 6;
    public static final int SUCCESS_EXECUTING = 100;
    public static final int SUCCESS_STORING = 101;

    private final int replyAddress;
    private final int moduleAddress;
    private final int status;
    private final int commandNumber;
    private long value = 0;
    private final int checksum;
    private final Integer[] datagram;

    public TrinamicResponse(final Integer[] datagram) {
        this.datagram = datagram;

        if (datagram.length != RESPONSE_LENGTH) {
            throw new IllegalArgumentException("Datagram must have 9 bytes");
        }

        this.replyAddress = datagram[0];
        this.moduleAddress = datagram[1];
        int status = datagram[2];
        this.commandNumber = datagram[3];

        this.value += datagram[4] << 24;
        this.value += datagram[5] << 16;
        this.value += datagram[6] << 8;
        this.value += datagram[7];

        int checksum = 0;
        for (int i = 0; i < RESPONSE_LENGTH - 1; i++) {
            checksum += datagram[i];
        }

        this.checksum = checksum & 0xFF;
        this.status = (this.checksum == datagram[8]) ? status : WRONG_CHECKSUM;
    }

    public int getReplyAddress() {
        return this.replyAddress;
    }

    public int getModuleAddress() {
        return this.moduleAddress;
    }

    public int getStatus() {
        return this.status;
    }

    public int getCommandNumber() {
        return this.commandNumber;
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

    public boolean isOkay() {
        return (this.status == 0x64) || (this.status == 0x65);
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        final TrinamicResponse that = (TrinamicResponse) obj;

        return Arrays.equals(this.datagram, that.datagram);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(this.datagram);
    }

    @Override
    public String toString() {
        return "TrinamicResponse{" +
                "replyAddress=" + replyAddress +
                ", moduleAddress=" + moduleAddress +
                ", status=" + status +
                ", commandNumber=" + commandNumber +
                ", value=" + value +
                ", checksum=" + checksum +
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
