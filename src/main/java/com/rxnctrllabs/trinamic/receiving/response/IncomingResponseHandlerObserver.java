package com.rxnctrllabs.trinamic.receiving.response;

public interface IncomingResponseHandlerObserver {

    void notifyResponseReceived(TrinamicResponse receivedResponse);
}
