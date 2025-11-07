package com.sashaprylutskyy.squidgamems.util;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
public class TimerService {

    private final ThreadPoolTaskScheduler scheduler;
    private final Map<String, ScheduledFuture<?>> tasks = new ConcurrentHashMap<>();

    public TimerService() {
        this.scheduler = new ThreadPoolTaskScheduler();
        this.scheduler.setPoolSize(4);
        this.scheduler.setThreadNamePrefix("timer-");
        this.scheduler.initialize();
    }

    /**
     * Запускає задачу через delayMillis мілісекунд.
     * @return ID задачі для можливого скасування
     */
    public String runAfterDelay(Runnable task, long delayMillis) {
        String taskId = UUID.randomUUID().toString();
        Instant runAt = Instant.now().plusMillis(delayMillis);
        ScheduledFuture<?> future = scheduler.schedule(() -> {
            try {
                task.run();
            } finally {
                tasks.remove(taskId);
            }
        }, runAt);
        tasks.put(taskId, future);
        return taskId;
    }

    public boolean cancel(String taskId) {
        ScheduledFuture<?> future = tasks.remove(taskId);
        if (future != null) {
            return future.cancel(false);
        }
        return false;
    }

    public boolean isActive(String taskId) {
        ScheduledFuture<?> future = tasks.get(taskId);
        return future != null && !future.isDone();
    }
}
