package ru.javawebinar.topjava;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import ru.javawebinar.topjava.service.MealServiceTest;

public class EachTestTimeWatcher extends TestWatcher {
    private static final Logger logger
            = LoggerFactory.getLogger(EachTestTimeWatcher.class);

    private StopWatch stopWatch;

    public EachTestTimeWatcher(StopWatch stopWatch) {
        this.stopWatch = stopWatch;
    }


    @Override
    protected void starting(Description description) {
        stopWatch.start(description.getMethodName());
    }

    @Override
    protected void finished(Description description) {
        stopWatch.stop();
        logger.info("Test {} - {} milliseconds", description.getMethodName(), stopWatch.getLastTaskTimeMillis());
    }
}
