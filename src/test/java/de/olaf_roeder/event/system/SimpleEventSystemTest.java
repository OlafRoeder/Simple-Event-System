package de.olaf_roeder.event.system;

import org.awaitility.Awaitility;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.RepeatedTest;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

class SimpleEventSystemTest {

    @RepeatedTest(10)
    void addEventRunnable() {

        /* Given */
        SimpleEventSystem sut = new SimpleEventSystem();
        AtomicBoolean success = new AtomicBoolean(false);
        sut.addEventRunnable(Object.class, () -> success.set(true));

        /* When */
        sut.publishEvent(new Object());

        /* Then */
        Awaitility.await().untilTrue(success);
    }

    @RepeatedTest(10)
    void addEventConsumer() {

        /* Given */
        SimpleEventSystem sut = new SimpleEventSystem();
        AtomicReference<String> success = new AtomicReference<>();
        sut.addEventConsumer(String.class, success::set);

        /* When */
        sut.publishEvent("test");

        /* Then */
        Awaitility.await().untilAtomic(success, Matchers.hasToString("test"));
    }

    @RepeatedTest(10)
    void addEventFunction() {

        /* Given */
        SimpleEventSystem sut = new SimpleEventSystem();
        AtomicReference<String> success = new AtomicReference<>();
        sut.addEventFunction(String.class, string -> {
            success.set(string);
            return success.get();
        });

        /* When */
        sut.publishEvent("test");

        /* Then */
        Awaitility.await().untilAtomic(success, Matchers.hasToString("test"));
    }

    @RepeatedTest(10)
    void addEventSupplier() {

        /* Given */
        SimpleEventSystem sut = new SimpleEventSystem();
        AtomicReference<String> success = new AtomicReference<>();
        sut.addEventSupplier(String.class, () -> {
            success.set("test");
            return success.get();
        });

        /* When */
        sut.publishEvent("test");

        /* Then */
        Awaitility.await().untilAtomic(success, Matchers.hasToString("test"));
    }
}