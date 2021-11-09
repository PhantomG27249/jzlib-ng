package io.github.duplexsystem.jzlibng;
import org.threadly.concurrent.UnfairExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;



public class FastParallelGZIPEnviroment {

    private static class ThreadFactoryHolder {

        private static final ThreadFactory THREAD_FACTORY = new ThreadFactory() {
            private final ThreadFactory defaultThreadFactory = Executors.defaultThreadFactory();
            private final AtomicLong counter = new AtomicLong(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = defaultThreadFactory.newThread(r);
                thread.setName("parallelgzip-" + counter.getAndIncrement());
                thread.setDaemon(true);
                return thread;
            }
        };
    }




    public UnfairExecutor UnfairExecutor (int threadCount){
        UnfairExecutor executor = new UnfairExecutor(threadCount);
        return executor;

    }

    public static UnfairExecutor newThreadPoolUnfairExecutor(int threadCount) {
        UnfairExecutor executor = new UnfairExecutor(threadCount, ThreadFactoryHolder.THREAD_FACTORY);
        return executor;
    }

    private static class ThreadPoolHolder {
        private static final UnfairExecutor EXECUTOR = newThreadPoolUnfairExecutor(Runtime.getRuntime().availableProcessors());
    }

    public static UnfairExecutor getSharedThreadPool() {
        return ThreadPoolHolder.EXECUTOR;
    }
}
/**
 * Since pigz has effectively the same work group size use the threadly Unfair Executor
 * Takes in a threadCount and Thread Factor to create an unfairExecutor
 * Threadly unfairExecutor is around 2-15x the performance of a regular executor
 * **/