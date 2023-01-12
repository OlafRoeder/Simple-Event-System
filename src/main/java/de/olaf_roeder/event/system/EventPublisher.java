package de.olaf_roeder.event.system;

import de.olaf_roeder.concurrency.EventExecutorService;

import java.util.function.Consumer;

class EventPublisher {

    private final EventRepository eventRepository;
    private final EventExecutorService eventExecutorService;

    EventPublisher(EventRepository eventRepository, EventExecutorService eventExecutorService) {
        this.eventRepository = eventRepository;
        this.eventExecutorService = eventExecutorService;
    }

    /*adding and getting consumer/functions to the eventRepository is strictly limited, so casts are correct*/
    @SuppressWarnings("unchecked")
    final <T> void publish(T event) {
        eventRepository.getRunnables(event.getClass()).forEach(eventExecutorService::submit);
        eventRepository.getConsumer(event.getClass()).forEach(consumer -> eventExecutorService.submit(() -> ((Consumer<T>) consumer).accept(event)));
    }
}