package com.nulstudio.sa;

import com.nulstudio.sa.event.Event;
import com.nulstudio.sa.event.SelectiveEventManager;
import org.jetbrains.annotations.NotNull;

public class SelectiveEventManagerThroughTester extends AbstractEventManagerThroughTester {

    @NotNull
    protected final SelectiveEventManager<Event> eventManager;

    public SelectiveEventManagerThroughTester(@NotNull SelectiveEventManager<Event> eventManager) {
        this.eventManager = eventManager;
    }

    @Override
    public long test(long requestCount, int subscriberCount) {
        return 0;
    }
}
