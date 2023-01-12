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
        simpleEventSystem.addEventSupplier(SupplierEvent.class, () -> {
            log.info("supplier event in thread {}", Thread.currentThread().getName());
            return null;
        });
        simpleEventSystem.addEventFunction(FunctionEvent.class, functionEvent -> {
            log.info("function event with payload {} in thread {}", functionEvent.payload, Thread.currentThread().getName());
            return null;
        });

        for (int i = 0; i < 1_000_000; i++) {
            simpleEventSystem.publishEvent(new RunnableEvent("run me"));
            simpleEventSystem.publishEvent(new ConsumerEvent("consume me"));
            simpleEventSystem.publishEvent(new SupplierEvent("supply me"));
            simpleEventSystem.publishEvent(new FunctionEvent("function me ..?"));
        }
    }

    @Value
    private static class RunnableEvent {
        String payload;
    }

    @Value
    private static class FunctionEvent {
        String payload;
    }

    @Value
    private static class SupplierEvent {
        String payload;
    }

    @Value
    private static class ConsumerEvent {
        String payload;
    }
}