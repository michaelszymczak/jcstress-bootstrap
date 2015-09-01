## Stream modification w/ and w/o synchronization

### Problem

One thread A processes a stream that originates from some collection (let's say an ArrayList).
What happens when another thread B modifies the collection when the stream is being processed by the thread A?
Does the thread B see the changes made by thread A, or maybe it ignores them?

### Test

Instead of theorizing, let's create a test suite and see what will happen. I'll use (still experimental) tool named jcstress,
that is used to verify assumptions about code correctness in a multithreaded environment.
 
    git clone https://github.com/michaelszymczak/jcstress-bootstrap.git
    cd jcstress-bootstrap
    make run

Now you can open results/com.michaelszymczak.jcstressbootstrap.sample.StreamTest.html 
in the browser or see the already [prepared version](http://htmlpreview.github.io/?https://raw.githubusercontent.com/michaelszymczak/jcstress-bootstrap/master/docs/StreamModifications/com.michaelszymczak.jcstressbootstrap.sample.StreamTest_synchronized.html)

Let's remove synchronization from modify or sum methods and see what will happen.

    make run
    
Refresh results/com.michaelszymczak.jcstressbootstrap.sample.StreamTest.html or see prepared versions:
[read not synchronized](http://htmlpreview.github.io/?https://raw.githubusercontent.com/michaelszymczak/jcstress-bootstrap/master/docs/StreamModifications/com.michaelszymczak.jcstressbootstrap.sample.StreamTest_sum_not_synchronized.html)
[write not synchronized](http://htmlpreview.github.io/?https://raw.githubusercontent.com/michaelszymczak/jcstress-bootstrap/master/docs/StreamModifications/com.michaelszymczak.jcstressbootstrap.sample.StreamTest_modify_not_synchronized.html)

### Conclusion

Java prevents us from modifying collections currently processed using streams. I think that's good decision - the fail fast approach may
save a lot of time and forces us to think about the problem instead of ignoring it. Using tools such as jcstress may save us a lot of trouble.

### Further reading

The documentation clarifies the behaviour: https://docs.oracle.com/javase/8/docs/api/java/util/ConcurrentModificationException.html

Interesting explanation can be also found here: http://blog.jooq.org/2014/06/13/java-8-friday-10-subtle-mistakes-when-using-the-streams-api/ (8. Modifying the backing collection of a stream)

    
