package com.nulstudio.sa.event;

public interface Subscriber<E extends Event> {
    void receive(E event);
}
