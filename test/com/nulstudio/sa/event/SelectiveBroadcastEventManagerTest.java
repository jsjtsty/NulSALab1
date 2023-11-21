package com.nulstudio.sa.event;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SelectiveBroadcastEventManagerTest extends SelectiveEventManagerTest {

    @Override
    @Test
    protected void send() {
        final List<Event> inputList = List.of(
                new Event("Topic1", "1"),
                new Event("Topic2", "2")
        );
        final List<String> expected = List.of("[1, Topic1] 1", "[2, Topic2] 2", "[3, Topic2] 2");
        final List<String> result = new ArrayList<>();
        final List<TopicSubscriber> subscribers = List.of(
                new TopicSubscriber(
                        "Topic1",
                        event -> result.add(String.format("[1, %s] %s", event.getTopic(), event.getContent()))
                ), new TopicSubscriber(
                        "Topic2",
                        event -> result.add(String.format("[2, %s] %s", event.getTopic(), event.getContent()))
                ), new TopicSubscriber(
                        "Topic2",
                        event -> result.add(String.format("[3, %s] %s", event.getTopic(), event.getContent()))
                )
        );

        final SelectiveBroadcastEventManager<Event> eventManager = getInstance();
        for (final TopicSubscriber topicSubscriber : subscribers) {
            eventManager.subscribe(topicSubscriber.topic(), topicSubscriber.subscriber());
        }

        for (final Event event : inputList) {
            eventManager.send(event);
        }

        assertEquals(expected, result);
    }

    @Override
    protected SelectiveBroadcastEventManager<Event> getInstance() {
        return new SelectiveBroadcastEventManager<>();
    }
}