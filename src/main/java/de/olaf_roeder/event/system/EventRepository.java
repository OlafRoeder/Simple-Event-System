package de.olaf_roeder.event.system;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

class EventRepository {

    private final Map<Class<?>, List<Reference<Consumer<?>>>> eventConsumer = new HashMap<>();
    private final Map<Class<?>, List<Reference<Runnable>>> eventRunnables = new HashMap<>();

    final <T> void addEventConsumer(Class<T> eventClass, Consumer<T> consumer) {
        eventConsumer.computeIfAbsent(eventClass, list -> new ArrayList<>()).add(new WeakReference<>(consumer));
    }

    final <T> void addEventRunnable(Class<T> eventClass, Runnable eventRunnable) {
        eventRunnables.computeIfAbsent(eventClass, list -> new ArrayList<>()).add(new WeakReference<>(eventRunnable));
    }

    final <T> Stream<Consumer<?>> getConsumer(Class<T> eventClass) {

        List<Reference<Consumer<?>>> references = eventConsumer.getOrDefault(eventClass, Collections.emptyList());

        return references.stream()
                .filter(consumerReference -> consumerReference.get() != null)
                .map(Reference::get);
    }

    final <T> Stream<Runnable> getRunnables(Class<T> eventClass) {

        List<Reference<Runnable>> references = eventRunnables.getOrDefault(eventClass, Collections.emptyList());

        return references.stream()
                .filter(runnableReference -> runnableReference.get() != null)
                .map(Reference::get);
    }
}