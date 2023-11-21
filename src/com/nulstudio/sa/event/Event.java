package com.nulstudio.sa.event;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Event {

    @Nullable
    protected final String topic;

    @NotNull
    protected final String content;

    public Event(@NotNull String content) {
        this.topic = null;
        this.content = content;
    }

    public Event(@NotNull String topic, @NotNull String content) {
        this.topic = topic;
        this.content = content;
    }

    @NotNull
    public String getContent() {
        return content;
    }

    @Nullable
    public String getTopic() {
        return topic;
    }

    @Override
    public String toString() {
        return String.format("Event[topic=%s, content=%s]", topic, content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(topic, content);
    }

}
