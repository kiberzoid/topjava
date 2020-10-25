package ru.javawebinar.topjava;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.springframework.util.StopWatch;

public class EachTestTimeWatcher extends TestWatcher {
    private StopWatch stopWatch;

    private long start;

    public EachTestTimeWatcher(StopWatch stopWatch) {
        this.stopWatch = stopWatch;
    }


    @Override
    protected void starting(Description description) {
        start = System.nanoTime();
        stopWatch.start(description.getMethodName());
    }

    @Override
    protected void finished(Description description) {
        System.out.printf("%s - %d nanoseconds", description.getMethodName(), System.nanoTime() - start);
        stopWatch.stop();
    }
}
