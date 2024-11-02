package de.olaf_roeder.eventsystem.engine;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class EventRepository {

    private final Map<Class<?>, List<Consumer<?>>> eventConsumer = new HashMap<>();
    private final Map<Class<?>, List<Runnable>> eventRunnables = new HashMap<>();

    public final <T> void addEventConsumer(Class<T> eventClass, Consumer<T> consumer) {
        eventConsumer.computeIfAbsent(eventClass, list -> new ArrayList<>()).add(consumer);
    }

    public final <T> void addEventRunnable(Class<T> eventClass, Runnable eventRunnable) {
        eventRunnables.computeIfAbsent(eventClass, list -> new ArrayList<>()).add(eventRunnable);
    }

    final <T> Stream<Consumer<?>> getConsumer(Class<T> eventClass) {

        List<Consumer<?>> consumer = eventConsumer.getOrDefault(eventClass, Collections.emptyList());

        return consumer.stream();
    }

    final <T> Stream<Runnable> getRunnables(Class<T> eventClass) {

        List<Runnable> runnables = eventRunnables.getOrDefault(eventClass, Collections.emptyList());

        return runnables.stream();
    }
}