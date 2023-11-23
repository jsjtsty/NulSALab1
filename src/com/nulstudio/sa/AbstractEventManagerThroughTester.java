package com.nulstudio.sa;

public abstract class AbstractEventManagerThroughTester implements EventManagerThroughTester {

    protected static final long DEFAULT_REQUEST_COUNT = 100000000L;

    protected static final int DEFAULT_SUBSCRIBER_COUNT = 3;

    protected static final String DEFAULT_CONTENT = "";

    protected static final String END_CONTENT = "End";

    protected static boolean checkAck(boolean[] ack, int count) {
        boolean result = true;
        for (int i = 0; i < count; ++i) {
            if (!ack[i]) {
                result = false;
                break;
            }
        }
        return result;
    }

    @Override
    public long test() {
        return test(DEFAULT_REQUEST_COUNT, DEFAULT_SUBSCRIBER_COUNT);
    }
}
