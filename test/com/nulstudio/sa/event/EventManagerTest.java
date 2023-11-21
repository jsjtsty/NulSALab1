package com.nulstudio.sa.event;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

abstract class EventManagerTest {
    @Test
    protected abstract void send();

    protected abstract EventManager<Event> getInstance();
}