package com.nulstudio.sa.event;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractSelectiveBroadcastEventManagerTest extends SelectiveEventManagerTest {
    @Override
    protected abstract AbstractSelectiveBroadcastEventManager<Event> getInstance();
}