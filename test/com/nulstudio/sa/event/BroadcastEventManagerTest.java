package com.nulstudio.sa.event;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BroadcastEventManagerTest extends EventManagerTest {

    @Override
    @Test
    protected void send() {
        final List<String> inputList = List.of("1", "2", "3"), expected = List.of(
                "[1] 1", "[2] 1", "[3] 1", "[1] 2", "[2] 2", "[3] 2", "[1] 3", "[2] 3", "[3] 3"
        );
        final List<String> result = new ArrayList<>();
        final List<Subscriber<Event>> subscribers = List.of(
                event -> result.add(String.format("[1] %s", event.getContent())),
                event -> result.add(String.format("[2] %s", event.getContent())),
                event -> result.add(String.format("[3] %s", event.getContent()))
        );

        final EventManager<Event> eventManager = getInstance();
        for (final Subscriber<Event> subscriber : subscribers) {
            eventManager.subscribe(subscriber);
        }

        for (final String input : inputList) {
            eventManager.send(new Event(input));
        }

        assertEquals(expected, result);
    }

    @Override
    protected BroadcastEventManager<Event> getInstance() {
        return new BroadcastEventManager<>();
    }
}