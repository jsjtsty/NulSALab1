package com.nulstudio.sa.event;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnicastBroadcastEventManagerTest extends AbstractSelectiveBroadcastEventManagerTest {

    @Override
    @Test
    protected void send() {
        final List<String> result = new ArrayList<>();
        final TopicSubscriber subscriber1 = new TopicSubscriber(
                "Topic1",
                event -> result.add(String.format("[1, %s] %s", event.getTopic(), event.getContent()))
        ), subscriber2 = new TopicSubscriber(
                "Topic1",
                event -> result.add(String.format("[2, %s] %s", event.getTopic(), event.getContent()))
        ), subscriber3 = new TopicSubscriber(
                "Topic2",
                event -> result.add(String.format("[3, %s] %s", event.getTopic(), event.getContent()))
        );

        final UnicastBroadcastEventManager<Event> eventManager = getInstance();

        eventManager.subscribe(subscriber1.topic(), subscriber1.subscriber());
        eventManager.send(new Event("Topic1", "1"));

        interrupt();

        eventManager.subscribe(subscriber2.topic(), subscriber2.subscriber());
        eventManager.send(new Event("Topic1", "2"));

        eventManager.send(new Event("Topic2", "3"));

        interrupt();

        assertEquals(List.of("[1, Topic1] 1", "[2, Topic1] 2"), result);

        eventManager.subscribe(subscriber3.topic(), subscriber3.subscriber());

        interrupt();

        assertEquals(List.of("[1, Topic1] 1", "[2, Topic1] 2", "[3, Topic2] 3"), result);

        eventManager.close();
    }

    @Override
    protected UnicastBroadcastEventManager<Event> getInstance() {
        return new UnicastBroadcastEventManager<>();
    }

    protected void interrupt() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}