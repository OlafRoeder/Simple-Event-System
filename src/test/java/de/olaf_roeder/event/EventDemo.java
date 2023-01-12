package de.olaf_roeder.event;

import de.olaf_roeder.event.system.SimpleEventSystem;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventDemo {

    public static void main(String[] args) {

        SimpleEventSystem simpleEventSystem = new SimpleEventSystem();

        simpleEventSystem.addEventRunnable(RunnableEvent.class, () -> log.info("runnable event in thread {}", Thread.currentThread().getName()));
        simpleEventSystem.addEventConsumer(ConsumerEvent.class, consumerEvent -> log.info("consumer event with payload {} in thread {}", consumerEvent.payload, Thread.currentThread().getName()));

        for (int i = 0; i < 1_000_000; i++) {
            simpleEventSystem.publishEvent(new RunnableEvent("run me"));
            simpleEventSystem.publishEvent(new ConsumerEvent("consume me"));
        }
    }

    @Value
    private static class RunnableEvent {
        String payload;
    }

    @Value
    private static class ConsumerEvent {
        String payload;
    }
}