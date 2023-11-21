package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;

interface EventHandler<E> {
    void handle(@NotNull E event);
}
