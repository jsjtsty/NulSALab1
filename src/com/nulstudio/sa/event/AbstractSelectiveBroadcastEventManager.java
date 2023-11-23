package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;

import java.util.*;

abstract class AbstractSelectiveBroadcastEventManager<E extends Event> implements SelectiveEventManager<E> {

    protected final Map<String, List<Subscriber<E>>> subscribers = new HashMap<>();

    protected final List<Subscriber<E>> broadcastSubscribers = new ArrayList<>();

    @Override
    public void subscribe(@NotNull Subscriber<E> subscriber) {
        broadcastSubscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(@NotNull Subscriber<E> subscriber) {
        final List<String> emptyList = new ArrayList<>();
        for (final Map.Entry<String, List<Subscriber<E>>> entry : subscribers.entrySet()) {
            final String topic = entry.getKey();
            final List<Subscriber<E>> subscriberList = entry.getValue();
            subscriberList.remove(subscriber);
            if (subscriberList.isEmpty()) {
                emptyList.add(topic);
            }
        }
        for (final String topic : emptyList) {
            subscribers.remove(topic);
        }
        broadcastSubscribers.remove(subscriber);
    }

    @Override
    public void clear() {
        subscribers.clear();
        broadcastSubscribers.clear();
    }

    @Override
    public void subscribe(@NotNull String topic, @NotNull Subscriber<E> subscriber) {
        final List<Subscriber<E>> subscriberList;
        if (subscribers.containsKey(topic)) {
            subscriberList = subscribers.get(topic);
        } else {
            subscriberList = new ArrayList<>();
            subscribers.put(topic, subscriberList);
        }

        subscriberList.add(subscriber);
    }

    @Override
    public void close() {}
}
