package de.olaf_roeder.event.system;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

class EventRepository {

    //TODO haben Function und Supplier in einem EventSystem irgendeine Daseinsberechtigung?

    private final Map<Class<?>, List<Reference<Consumer<?>>>> eventConsumer = new HashMap<>();
    private final Map<Class<?>, List<Reference<Function<?, ?>>>> eventFunctions = new HashMap<>();
    private final Map<Class<?>, List<Reference<Supplier<?>>>> eventSupplier = new HashMap<>();
    private final Map<Class<?>, List<Reference<Runnable>>> eventRunnables = new HashMap<>();

    final <T> void addEventConsumer(Class<T> eventClass, Consumer<T> consumer) {
        eventConsumer.computeIfAbsent(eventClass, list -> new ArrayList<>()).add(new WeakReference<>(consumer));
    }

    final <T, R> void addEventFunction(Class<T> eventClass, Function<T, R> function) {
        eventFunctions.computeIfAbsent(eventClass, list -> new ArrayList<>()).add(new WeakReference<>(function));
    }

    final <T> void addEventSupplier(Class<T> eventClass, Supplier<T> supplier) {
        eventSupplier.computeIfAbsent(eventClass, list -> new ArrayList<>()).add(new WeakReference<>(supplier));
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

    final <T> Stream<Function<?, ?>> getFunctions(Class<T> eventClass) {

        List<Reference<Function<?, ?>>> references = eventFunctions.getOrDefault(eventClass, Collections.emptyList());

        return references.stream()
                .filter(functionReference -> functionReference.get() != null)
                .map(Reference::get);
    }

    final <T> Stream<Supplier<?>> getSuppliers(Class<T> eventClass) {

        List<Reference<Supplier<?>>> references = eventSupplier.getOrDefault(eventClass, Collections.emptyList());

        return references.stream()
                .filter(supplierReference -> supplierReference.get() != null)
                .map(Reference::get);
    }

    final <T> Stream<Runnable> getRunnables(Class<T> eventClass) {

        List<Reference<Runnable>> references = eventRunnables.getOrDefault(eventClass, Collections.emptyList());

        return references.stream()
                .filter(runnableReference -> runnableReference.get() != null)
                .map(Reference::get);
    }
}