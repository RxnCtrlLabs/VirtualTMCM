package com.rxnctrllabs.virtualtmcm;

import com.rxnctrllabs.trinamic.command.TrinamicCommand;
import com.rxnctrllabs.trinamic.receiving.response.TrinamicResponse;

import static com.rxnctrllabs.trinamic.receiving.response.TrinamicResponse.RESPONSE_LENGTH;

class TrinamicResponseBuilder {

    static TrinamicResponseBuilder from(final TrinamicCommand command) {
        return new TrinamicResponseBuilder(command.getModuleAddress(), command.getCommandNumber());
    }

    private final int moduleAddress;
    private final int command;
    private long value = 0L;

    private TrinamicResponseBuilder(final int moduleAddress, final int command) {
        this.moduleAddress = moduleAddress;
        this.command = command;
    }

    TrinamicResponseBuilder withValue(Long value) {
        this.value = value;
        return this;
    }

    TrinamicResponse build() {
        Integer[] responseDatagram = new Integer[]{0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        responseDatagram[0] = 0x02;
        responseDatagram[1] = this.moduleAddress;
        responseDatagram[2] = 0x64;
        responseDatagram[3] = this.command;
        responseDatagram[4] = (int) this.value >> 24 & 0xFF;
        responseDatagram[5] = (int) this.value >> 16 & 0xFF;
        responseDatagram[6] = (int) this.value >> 8 & 0xFF;
        responseDatagram[7] = (int) this.value & 0xFF;

        int checksum = 0;
        for (int i = 0; i < RESPONSE_LENGTH - 1; i++) {
            checksum += responseDatagram[i];
        }

        responseDatagram[8] = checksum & 0xFF;

        return new TrinamicResponse(responseDatagram);
    }
}
