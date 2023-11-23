package com.nulstudio.sa;

import com.nulstudio.sa.event.*;
import com.nulstudio.sa.test.EventManagerThroughTester;
import com.nulstudio.sa.test.SimpleEventManagerThroughTester;

public class Main {

    public static void main(String[] args) {
        final EventManagerThroughTester broadcastTester = new SimpleEventManagerThroughTester(new BroadcastEventManager<>()),
                selectiveTester = new SimpleEventManagerThroughTester(new SelectiveBroadcastEventManager<>()),
                unicastTester = new SimpleEventManagerThroughTester(new UnicastBroadcastEventManager<>());
        final long broadcastRps = broadcastTester.test(), selectiveBroadcastRps = selectiveTester.test(),
                unicastRps = unicastTester.test();
        System.out.println("Broadcast Rps: " + broadcastRps);
        System.out.println("Selective Broadcast Rps: " + selectiveBroadcastRps);
        System.out.println("Unicast Rps: " + unicastRps);
    }
}