package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnicastBroadcastEventManager<E extends Event> extends AbstractSelectiveBroadcastEventManager<E> {
    protected final EventQueue<E> eventQueue;

    protected class UnicastEventHandler implements EventHandler<E> {
        @Override
        public void handle(@NotNull E event) {
            final String topic = event.getTopic();
            if (topic == null) {
                if (!broadcastSubscribers.isEmpty()) {
                    final Subscriber<E> subscriber = broadcastSubscribers.get((broadcastSubscribers.size() - 1));
                    subscriber.receive(event);
                }
            } else if (subscribers.containsKey(topic) && !subscribers.get(topic).isEmpty()) {
                final List<Subscriber<E>> subscriberList = subscribers.get(topic);
                final Subscriber<E> subscriber = subscriberList.get(subscriberList.size() - 1);
                subscriber.receive(event);
            } else if (!broadcastSubscribers.isEmpty()) {
                final Subscriber<E> subscriber = broadcastSubscribers.get((broadcastSubscribers.size() - 1));
                subscriber.receive(event);
            } else {
                eventQueue.enqueueDeadEvent(event);
            }
        }
    }

    public UnicastBroadcastEventManager() {
        eventQueue = EventQueue.loop(new UnicastEventHandler());
    }

    @Override
    public void subscribe(@NotNull String topic, @NotNull Subscriber<E> subscriber) {
        super.subscribe(topic, subscriber);
        eventQueue.update(event -> subscribers.containsKey(event.getTopic()) || !broadcastSubscribers.isEmpty());
    }

    @Override
    public void subscribe(@NotNull Subscriber<E> subscriber) {
        super.subscribe(subscriber);
        eventQueue.update(event -> subscribers.containsKey(event.getTopic()) || !broadcastSubscribers.isEmpty());
    }

    @Override
    public void send(@NotNull E event) {
        eventQueue.enqueue(event);
    }
}
