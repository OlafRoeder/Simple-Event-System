package de.olaf_roeder.concurrency;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

/**
 * An {@link ExecutorService} that handles shutdown when JVM stops and logs
 * uncaught Exceptions. <br>
 * API: <br>
 * {@link #submit(Runnable)}<br>
 */
@Slf4j
public class EventExecutorService {

    private static final boolean ASYNC_MODE = true;
    private static final long TIMEOUT = 1000;

    private static final UncaughtExceptionHandler UNCAUGHT_EXCEPTION_HANDLER =
            (t, e) -> log.error("Thread " + t.getName() + " threw exception " + e.getClass(), e);

    /**
     * Same as {@link Executors#newWorkStealingPool()} plus custom
     * {@link UncaughtExceptionHandler}.
     */
    private static final ExecutorService EXECUTOR = new ForkJoinPool(Runtime.getRuntime().availableProcessors(),
            ForkJoinPool.defaultForkJoinWorkerThreadFactory, UNCAUGHT_EXCEPTION_HANDLER, ASYNC_MODE);

    public EventExecutorService() {
        Runtime.getRuntime().addShutdownHook(new Thread("GlobalExecutorService Shutdown Hook") {

            @Override
            public void run() {
                shutdown();
            }
        });
    }

    /**
     * Submits a {@link Runnable} task to the underlying {@link ExecutorService}.
     * The task will be executed asynchronously, so there is no guarantee that
     * subsequent submitted tasks are run in order.
     *
     * @param task A {@link Runnable} object.
     */
    public void submit(@NonNull Runnable task) {

        if (EXECUTOR.isShutdown())
            throw new IllegalStateException(
                    "Executor was shut down. Do not call GlobalExecutorService.submit(Runnable) after GlobalExecutorService.shutdown()!");

        EXECUTOR.submit(task);
    }

    private void shutdown() {

        log.debug("Shutting down " + EXECUTOR);

        EXECUTOR.shutdown();

        try {

            if (!EXECUTOR.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS)) {

                EXECUTOR.shutdownNow().forEach(task -> log.debug("Still running task: " + task.getClass()));

                if (!EXECUTOR.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS))
                    log.error(EXECUTOR + " could not shut down properly.");
            }
        } catch (InterruptedException e) {
            EXECUTOR.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}