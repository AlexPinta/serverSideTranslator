package core;

import batch.ContactItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Service;

@Service
public class StreamTaskExecutorImpl implements StreamTaskExecutor, ApplicationListener<ContextClosedEvent> {
    @Autowired
    ThreadPoolTaskScheduler taskScheduler;
    @Autowired
    ThreadPoolTaskExecutor taskExecutor;
    @Autowired
    private ContactItemReader contactItemReader;

    private boolean streamEnable = false;

    private StreamTaskExecutorImpl() {}

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        taskExecutor.shutdown();
        taskScheduler.shutdown();
    }

    @Override
    @Scheduled(fixedRateString="${task.execution.rate}")
    public void retrieveStreamData() {
        if (!streamEnable) return;
        try {
            contactItemReader.read();
        } catch (Exception e) {
            //TODO logging
            e.printStackTrace();
        }

    }

    @Override
    public void setStreamEnable(boolean streamEnable) {
        this.streamEnable = streamEnable;
    }
}
