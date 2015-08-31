## Stream modification w/ and w/o synchronization

### Problem

One thread A process a stream that originates from a collection, let's say an ArrayList.
What happens when another thread B modifies the collection when the stream is being processed by another thread?
Does the thread B see the changes made by thread A, or maybe ignore them?

### Test

Instead of theorizing, let's create a test suite and see what will happen. I'll use (still experimental) tool named jcstress,
that is used to verify assumptions about code correctness in a multithreaded environment.
 
    git clone https://github.com/michaelszymczak/jcstress-bootstrap.git
    cd jcstress-bootstrap
    make run

Now you can open results/com.michaelszymczak.jcstressbootstrap.sample.StreamTest.html 
in the browser or see the already [prepared version](com.michaelszymczak.jcstressbootstrap.sample.StreamTest_synchronized.html)

Let's remove synchronization from modify or sum methods and see what will happen.

    make run
    
Refresh results/com.michaelszymczak.jcstressbootstrap.sample.StreamTest.html or see prepared versions:
[read not synchronized](com.michaelszymczak.jcstressbootstrap.sample.StreamTest_sum_not_synchronized.html)
[write not synchronized](com.michaelszymczak.jcstressbootstrap.sample.StreamTest_modify_not_synchronized.html)

### Conclusion

Java prevents us from modifying collections currently processed using streams. I think that's good decision - the fail fast approach may
save a lot of time and forces us to think about the problem instead of ignoring it.

    