package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BroadcastEventManager<E extends Event> implements EventManager<E> {
    protected final List<Subscriber<E>> subscribers = new ArrayList<>();

    public BroadcastEventManager() {

    }

    @Override
    public void send(@NotNull E event) {
        for (final Subscriber<E> subscriber : subscribers) {
            subscriber.receive(event);
        }
    }

    @Override
    public void close() {}

    @Override
    public void subscribe(@NotNull Subscriber<E> subscriber) {
        subscribers.add(subscriber);
    }

    @Override
    public void unsubscribe(@NotNull Subscriber<E> subscriber) {
        subscribers.remove(subscriber);
    }
}
