package com.michaelszymczak.jcstressbootstrap.sample;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IntResult1;

import java.util.concurrent.atomic.AtomicInteger;

@JCStressTest
@Description("Atomic int")
@Outcome(id = "[101]", expect = Expect.ACCEPTABLE, desc = "")
@State
public class AITest {

    private final Fixture fixture = new Fixture();

    @Actor
    public void thread1() {
        fixture.modify(100);
    }

    @Actor
    public void thread2() {
        fixture.modify(1);
    }

    @Arbiter
    public void observe(IntResult1 intResult1) {
        intResult1.r1 = fixture.finalValue();
    }

    private static class Fixture {
        private final AtomicInteger ai = new AtomicInteger(0);

        public void modify(int value) {
            ai.addAndGet(value);
        }

        public int finalValue() {
            return ai.intValue();
        }
    }

}
