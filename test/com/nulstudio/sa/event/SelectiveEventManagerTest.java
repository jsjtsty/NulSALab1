package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;

import static org.junit.jupiter.api.Assertions.*;

abstract class SelectiveEventManagerTest extends EventManagerTest {

    protected record TopicSubscriber(@NotNull String topic, @NotNull Subscriber<Event> subscriber) {}

    @Override
    protected abstract SelectiveEventManager<Event> getInstance();
}