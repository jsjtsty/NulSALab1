package com.nulstudio.sa.test;

import com.nulstudio.sa.event.Event;
import com.nulstudio.sa.event.EventManager;
import com.nulstudio.sa.event.Subscriber;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleEventManagerThroughTester extends AbstractEventManagerThroughTester {

    protected interface EventNotification {
        void notify(int id);
    }

    protected record EventManagerTestSubscriber(int id, @NotNull EventNotification eventNotification)
            implements Subscriber<Event> {
        @Override
        public void receive(@NotNull Event event) {
            eventNotification.notify(id);
        }
    }

    @NotNull
    protected final EventManager<Event> eventManager;

    public SimpleEventManagerThroughTester(@NotNull EventManager<Event> eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public long test(long requestCount, int subscriberCount) {
        final AtomicBoolean end = new AtomicBoolean(false);
        final EventNotification eventNotification = id -> end.set(true);
        final List<Subscriber<Event>> subscribers = new ArrayList<>(subscriberCount);
        for (int subscriberId = 0; subscriberId < subscriberCount; ++subscriberId) {
            subscribers.add(new EventManagerTestSubscriber(subscriberId, eventNotification));
        }

        eventManager.clear();
        subscribers.forEach(eventManager::subscribe);

        final long startTime = System.currentTimeMillis();
        for (long eventId = 0; eventId < requestCount - 1; ++eventId) {
            eventManager.send(new Event(DEFAULT_CONTENT));
        }
        if (requestCount > 0) {
            eventManager.send(new Event(END_CONTENT));
        }

        long endTime;
        do {
            endTime = System.currentTimeMillis();
        } while (!end.get());

        eventManager.close();

        return requestCount * 1000L / (endTime - startTime);
    }
}
