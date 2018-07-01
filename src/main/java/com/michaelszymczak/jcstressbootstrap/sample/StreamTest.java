package com.michaelszymczak.jcstressbootstrap.sample;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.IntResult1;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

@JCStressTest
@Description("Exception when modifying underlying array list")
@Outcome(id = "[3]", expect = Expect.ACCEPTABLE, desc = "")
@Outcome(id = "[8]", expect = Expect.ACCEPTABLE, desc = "")
@Outcome(id = "[-1]", expect = Expect.ACCEPTABLE_INTERESTING, desc = "ConcurrentModificationException")
@Outcome(id = "[-2]", expect = Expect.ACCEPTABLE_INTERESTING, desc = "NullPointerException")
@State
public class StreamTest {

    private final Fixture fixture = new Fixture();

    @Actor
    public void thread1() {
        fixture.modify(5);
    }

    @Actor
    public void thread2(IntResult1 r) {
        try {
            r.r1 = fixture.sum();
        } catch (ConcurrentModificationException e) {
            r.r1 = -1;
        } catch (NullPointerException e) {
            r.r1 = -2;
        }
    }

    private static class Fixture {
        private final List<Integer> list;

        Fixture() {
            list = new ArrayList<>();
            list.add(1);
            list.add(2); // always visible, see: http://tech.puredanger.com/2008/11/26/jmm-and-final-field-freeze/
        }

        public void modify(int i) {
            list.add(i);
        }

        public int sum() {
            return list.stream().mapToInt(i -> i).sum();
        }
    }

}
