package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;

public interface SelectiveEventManager<E extends Event> extends EventManager<E> {
    void subscribe(@NotNull String topic, @NotNull Subscriber<E> subscriber);

}
