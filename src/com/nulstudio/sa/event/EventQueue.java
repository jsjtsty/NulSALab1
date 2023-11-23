package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

class EventQueue<E extends Event> {

    protected final Queue<E> eventQueue = new ConcurrentLinkedQueue<>();

    protected final List<E> deadQueue = new LinkedList<>();

    protected boolean started = true;

    public interface DeadEventInspector<E extends Event> {
        boolean check(@NotNull E event);
    }

    public EventQueue() {

    }

    protected void restoreDeadEvent(DeadEventInspector<E> deadEventInspector) {
        final Iterator<E> iterator = deadQueue.iterator();
        while (iterator.hasNext()) {
            final E event = iterator.next();
            if (deadEventInspector.check(event)) {
                eventQueue.offer(event);
                iterator.remove();
            }
        }
    }

    @Nullable
    public synchronized E dequeue() {
        while (eventQueue.isEmpty()) {
            if (!started) {
                clear();
                return null;
            }

            try {
                wait();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return eventQueue.poll();
    }

    public synchronized void enqueue(@NotNull E event) {
        eventQueue.offer(event);
        notify();
    }

    public synchronized void enqueueDeadEvent(@NotNull E event) {
        deadQueue.add(event);
    }

    public synchronized void clear() {
        eventQueue.clear();
        deadQueue.clear();
    }

    public synchronized void close() {
        started = false;
        notify();
    }

    public synchronized void start() {
        started = true;
        notify();
    }

    public synchronized void update(DeadEventInspector<E> deadEventInspector) {
        restoreDeadEvent(deadEventInspector);
        notify();
    }

    @NotNull
    public static <E extends Event> EventQueue<E> loop(EventHandler<E> eventHandler) {
        final EventQueue<E> eventQueue = new EventQueue<>();
        final Thread thread = new Thread(() -> {
            E event;
            while ((event = eventQueue.dequeue()) != null) {
                eventHandler.handle(event);
            }
        });
        thread.start();
        return eventQueue;
    }
}
