package com.michaelszymczak.jcstressbootstrap.sample;

import org.openjdk.jcstress.annotations.Actor;
import org.openjdk.jcstress.annotations.Arbiter;
import org.openjdk.jcstress.annotations.Description;
import org.openjdk.jcstress.annotations.Expect;
import org.openjdk.jcstress.annotations.JCStressTest;
import org.openjdk.jcstress.annotations.Outcome;
import org.openjdk.jcstress.annotations.State;
import org.openjdk.jcstress.infra.results.BooleanResult2;

import java.util.BitSet;

@JCStressTest
@Description("Tests if racy update to BitSet experiences word tearing.")
@Outcome(id = "[true, false]", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Thread 1 has intervened, word tearing occurred.")
@Outcome(id = "[false, true]", expect = Expect.ACCEPTABLE_INTERESTING, desc = "Thread 2 has intervened, word tearing occurred.")
@Outcome(id = "[true, true]",  expect = Expect.ACCEPTABLE_INTERESTING, desc = "Observed both thread writes.")
@State
public class BitSetTest {

    final BitSet o = new BitSet();

    @Actor
    public void thread1() {
        o.set(0);
    }

    @Actor
    public void thread2() {
        o.set(1);
    }

    @Arbiter
    public void observe(BooleanResult2 res) {
        res.r1 = o.get(0);
        res.r2 = o.get(1);
    }

}
