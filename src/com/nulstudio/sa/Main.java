package com.nulstudio.sa;

import com.nulstudio.sa.event.*;

public class Main {

    public static void main(String[] args) {
        final EventManager<Event> eventManager = new UnicastBroadcastEventManager<>();
        final EventManagerThroughTester eventManagerThroughTester = new SimpleEventManagerThroughTester(eventManager);
        final long rps = eventManagerThroughTester.test();
        System.out.println(rps);
    }
}