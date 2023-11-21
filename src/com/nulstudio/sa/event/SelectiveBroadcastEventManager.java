package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;

import java.util.*;

public class SelectiveBroadcastEventManager<E extends Event> extends AbstractSelectiveBroadcastEventManager<E> {
    @Override
    public void send(@NotNull E event) {
        final String topic = event.getTopic();
        if (topic != null && subscribers.containsKey(topic)) {
            final List<Subscriber<E>> subscriberList = subscribers.get(topic);
            for (final Subscriber<E> subscriber : subscriberList) {
                subscriber.receive(event);
            }
        }
        for (final Subscriber<E> subscriber : broadcastSubscribers) {
            subscriber.receive(event);
        }
    }
}
