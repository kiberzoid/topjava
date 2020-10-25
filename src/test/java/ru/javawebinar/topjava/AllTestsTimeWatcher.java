package ru.javawebinar.topjava;

import org.junit.rules.ExternalResource;
import org.springframework.util.StopWatch;

public class AllTestsTimeWatcher extends ExternalResource {

    private StopWatch stopWatch;

    public AllTestsTimeWatcher(StopWatch stopWatch) {
        this.stopWatch = stopWatch;
    }

    @Override
    protected void after() {
        System.out.println(stopWatch.prettyPrint());
    }
}
