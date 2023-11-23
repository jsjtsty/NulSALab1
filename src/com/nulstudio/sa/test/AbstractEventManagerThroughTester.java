package com.nulstudio.sa.test;

public abstract class AbstractEventManagerThroughTester implements EventManagerThroughTester {

    protected static final long DEFAULT_REQUEST_COUNT = 100000000L;

    protected static final int DEFAULT_SUBSCRIBER_COUNT = 3;

    protected static final String DEFAULT_CONTENT = "";

    protected static final String END_CONTENT = "End";

    @Override
    public long test() {
        return test(DEFAULT_REQUEST_COUNT, DEFAULT_SUBSCRIBER_COUNT);
    }
}
