package de.olaf_roeder.event.system;

import de.olaf_roeder.concurrency.EventExecutorService;

import java.util.function.Consumer;

public class SimpleEventSystem {

    private final EventRepository eventRepository;
    private final EventPublisher eventPublisher;

    public SimpleEventSystem() {
        this(new EventRepository(), new EventExecutorService());
    }

    SimpleEventSystem(EventRepository eventRepository, EventExecutorService eventExecutorService) {
        this(eventRepository, new EventPublisher(eventRepository, eventExecutorService));
    }

    SimpleEventSystem(EventRepository eventRepository, EventPublisher eventPublisher) {
        this.eventRepository = eventRepository;
        this.eventPublisher = eventPublisher;
    }

    public final <T> void addEventRunnable(Class<T> eventClass, Runnable runnable) {
        eventRepository.addEventRunnable(eventClass, runnable);
    }

    public final <T> void addEventConsumer(Class<T> eventClass, Consumer<T> eventConsumer) {
        eventRepository.addEventConsumer(eventClass, eventConsumer);
    }

    public final <T> void publishEvent(T event) {
        eventPublisher.publish(event);
    }
}