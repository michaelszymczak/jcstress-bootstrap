package com.michaelszymczak.jcstressbootstrap.sample;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IntResult1;

import java.util.ArrayList;
import java.util.List;

@JCStressTest
@Description("Tests if racy update to BitSet experiences word tearing.")
@Outcome(id = "[3]", expect = Expect.ACCEPTABLE, desc = "")
@Outcome(id = "[13]", expect = Expect.ACCEPTABLE, desc = "")
@State
public class StreamTest {

    private final Fixture fixture = new Fixture();

    @Actor
    public void thread1() {
        fixture.modify();
    }

    @Actor
    public void thread2(IntResult1 r) {
        r.r1 = fixture.sum();
    }

    private static class Fixture {
        private final List<Integer> list;

        Fixture() {
            list = new ArrayList<>();
            list.add(1);
            list.add(2); // will be always visible, see: http://tech.puredanger.com/2008/11/26/jmm-and-final-field-freeze/
        }

        public synchronized void modify() {
            list.add(10);
        }

        public synchronized int sum() {
            return list.stream().mapToInt(i -> i).sum();
        }
    }

}
