package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;

public interface EventManager<E extends Event> {
    void subscribe(@NotNull Subscriber<E> subscriber);

    void unsubscribe(@NotNull Subscriber<E> subscriber);

    void send(@NotNull E event);

    void clear();

    void close();
}
