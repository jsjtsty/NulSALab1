package com.nulstudio.sa;

import com.nulstudio.sa.event.BroadcastEventManager;
import com.nulstudio.sa.event.Event;
import com.nulstudio.sa.event.EventManager;
import com.nulstudio.sa.event.Subscriber;

public class Main {
    public static void main(String[] args) {
        final EventManager<Event> eventManager = new BroadcastEventManager<>();
        final Subscriber<Event> subscriber1 = event -> System.out.printf("[1] %s%n", event.getContent());
        final Subscriber<Event> subscriber2 = event -> System.out.printf("[2] %s%n", event.getContent());
        eventManager.subscribe(subscriber1);
        eventManager.subscribe(subscriber2);

        eventManager.send(new Event("Fuck xiaoyu"));
    }
}