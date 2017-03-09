package com.rxnctrllabs.trinamic.receiving.response;

import java.util.ArrayList;
import java.util.List;

public class IncomingResponseHandler {

    private final List<Integer> receivedBytes;
    private final List<IncomingResponseHandlerObserver> observers;

    public IncomingResponseHandler() {
        this.receivedBytes = new ArrayList<>();
        this.observers = new ArrayList<>();
    }

    public void addObserver(final IncomingResponseHandlerObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(final IncomingResponseHandlerObserver observer) {
        this.observers.remove(observer);
    }

    public void addByte(final int incomingByte) {
        this.receivedBytes.add(incomingByte & 0xFF);

        checkCompleteResponse();
    }

    public void resetBuffer() {
        this.receivedBytes.clear();
    }

    private void checkCompleteResponse() {
        if (this.receivedBytes.size() >= TrinamicResponse.RESPONSE_LENGTH) {
            List<Integer> fullResponse = new ArrayList<>(this.receivedBytes.subList(0, TrinamicResponse.RESPONSE_LENGTH));
            this.receivedBytes.removeAll(fullResponse);

            Integer[] integers = fullResponse.toArray(new Integer[fullResponse.size()]);

            TrinamicResponse receivedResponse = new TrinamicResponse(integers);

            Integer checksum = fullResponse.get(8);
            if (receivedResponse.getChecksum() == checksum) {
                notifyResponseReceived(receivedResponse);
            }
        }
    }

    private void notifyResponseReceived(final TrinamicResponse receivedResponse) {
        for (IncomingResponseHandlerObserver observer : this.observers) {
            observer.notifyResponseReceived(receivedResponse);
        }
    }
}
