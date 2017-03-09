package com.rxnctrllabs.trinamic.receiving.command;

import com.rxnctrllabs.trinamic.command.TrinamicCommand;

public interface IncomingCommandHandlerObserver {

    void notifyCommandReceived(TrinamicCommand receivedCommand);
}
