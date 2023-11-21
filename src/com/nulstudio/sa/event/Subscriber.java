package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;

public interface Subscriber<E extends Event> {
    void receive(@NotNull E event);
}
