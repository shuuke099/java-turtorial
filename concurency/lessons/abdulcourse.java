What Your Code Means

You have two methods:

static void display() {
    int i = 1;

    while (true) {
        System.out.println(i + " Hello");
        i++;
    }
}

and:

public static void main(String[] args) {
    display();

    int i = 1;

    while (true) {
        System.out.println(i + " World");
        i++;
    }
}
// Important Point Because display() has an infinite loop:

while (true)

// the program gets stuck inside display() forever.

So this part in main() is never reached:

int i = 1;

while (true) {
    System.out.println(i + " World");
    i++;
}
Output

The output will be:

1 Hello
2 Hello
3 Hello
4 Hello
5 Hello
...

It will continue forever.

You will not see:

1 World
2 World
3 World

because display() never finishes.

Why?

Method calls run like this:

main starts
 ↓
display() is called
 ↓
display keeps printing Hello forever
 ↓
main cannot continue

So main waits for display() to return, but it never returns.



// ==================This Version Uses a Thread====================

Your new code is different because Test extends Thread.

class Test extends Thread {

    public void run() {
        int i = 1;

        while (true) {
            System.out.println(i + " Hello");
            i++;
        }
    }

    public static void main(String[] args) {
        Test t = new Test();

        t.start();

        int i = 1;

        while (true) {
            System.out.println(i + " World");
            i++;
        }
    }
}
What Happens Step by Step
1. Main thread starts
public static void main(String[] args)

Java always starts with the main thread.

2. Object is created
Test t = new Test();

This creates a thread object, but it does not start the new thread yet.

3. New thread starts
t.start();

This creates a second thread.

That second thread runs:

public void run()

So now you have two threads running at the same time:

Thread	Prints
Main thread	World
Worker thread	Hello
Possible Output

Because both loops are infinite, output may mix like this:

1 Hello
1 World
2 World
2 Hello
3 Hello
3 World
4 World
4 Hello
...

Or like this:

1 World
2 World
1 Hello
2 Hello
3 World
3 Hello
...

The exact order is unknown.

Very Important Rule

Inside each thread, order is preserved.

So the Hello thread will print:

1 Hello
2 Hello
3 Hello
4 Hello

The World thread will print:

1 World
2 World
3 World
4 World

But between the two threads, Java may mix them differently.


class Test implements Runnable {

    public void run() {
        int i = 1;

        while (true) {
            System.out.println(i + " Hello");
            i++;
        }
    }

    public static void main(String[] args) {
        Test m = new Test();

        Thread t = new Thread(m);

        t.start();

        int i = 1;

        while (true) {
            System.out.println(i + " World");
            i++;
        }
    }
}



// These Thread constructors still exist in Java 21.

new Thread()
new Thread(Runnable r)
new Thread(Runnable r, String name)
new Thread(String name)

// But this one is old style and less common:

new Thread(ThreadGroup group, String name)
For the Java 21 exam

You should know the common constructors:

Thread t1 = new Thread();

Thread t2 = new Thread(() -> {
    System.out.println("Hello");
});

Thread t3 = new Thread(() -> {
    System.out.println("Hello");
}, "Worker-1");

Thread t4 = new Thread("MyThread");
But Java 21 prefers factory methods

Modern Java 21 style:

Thread t = Thread.ofPlatform()
        .name("Worker-1")
        .start(() -> System.out.println("Hello"));

Virtual thread:

Thread t = Thread.ofVirtual()
        .name("Virtual-1")
        .start(() -> System.out.println("Hello"));
Exam memory
new Thread(...)        = old platform thread style
Thread.ofPlatform()   = modern platform thread builder
Thread.ofVirtual()    = modern virtual thread builder






//==================Thread Methods====================





This slide is showing some of the most important Thread methods. For Java 21 and the OCP exam, you should know what each one does, when it is used, and which ones are considered old or rarely used.

Thread Instance Methods

These are called on a thread object.

Thread t = new Thread(task);
1. start()
Purpose

Creates a new thread and tells the JVM scheduler to run it.

Thread t = new Thread(() -> {
    System.out.println("Worker");
});

t.start();
What Happens?
Main Thread
    |
    | start()
    |
    +------------> New Thread Created
Exam Trap
t.run();

does NOT create a new thread.

t.start();

creates a new thread.

2. run()

Contains the work performed by the thread.

class Task implements Runnable {

    public void run() {
        System.out.println("Working");
    }
}

Normally the JVM calls it after:

t.start();

You rarely call it yourself.

3. join()
Purpose

Wait for another thread to finish.

Thread worker = new Thread(task);

worker.start();

worker.join();

System.out.println("Done");
Execution
Main
 |
 | worker.start()
 |
 | worker.join()
 |   WAIT
 |
Worker finishes
 |
Main continues

Output:

Worker
Done
4. join(long millis)

Wait only a limited amount of time.

worker.join(3000);

Meaning:

Wait up to 3 seconds

If worker finishes sooner:

Continue immediately

If worker is still running:

Continue anyway
5. interrupt()
Purpose

Send an interruption request.

worker.interrupt();

It does NOT forcibly kill the thread.

Think:

interrupt() = tap on the shoulder

not

interrupt() = kill thread
Static Methods

// Called on the class itself.

Thread.methodName()
1. currentThread()

Returns the thread currently executing.

Thread current = Thread.currentThread();

System.out.println(current.getName());

Output: main

or

Worker-1
Hospital Example
System.out.println(
    Thread.currentThread().getName()
);

Output:

NurseThread

Meaning:

Which worker is currently doing the task?
2. yield()
Purpose

Politely tell scheduler:

"I can pause if another thread wants the CPU."

Thread.yield();
Important

It is only a suggestion.

The scheduler may ignore it.

yield() does NOT guarantee anything
3. activeCount()

Returns an estimate of active threads.

System.out.println(Thread.activeCount());

Example output:

5

Meaning:

Approximately 5 threads running
Exam Note

Keyword:

estimate

not exact count.

4. dumpStack()

Prints stack trace of current thread.

Thread.dumpStack();

Output:

java.lang.Exception: Stack trace
 at Demo.main(Demo.java:15)

Used mostly for debugging.

Full Professional Scenario

Imagine a hospital.

Main Thread

Hospital Director

Worker Thread

Patient Monitor

public class Hospital {

    public static void main(String[] args)
            throws InterruptedException {

        Thread monitor = new Thread(() -> {

            System.out.println(
                    Thread.currentThread().getName()
            );

            for (int i = 1; i <= 5; i++) {

                System.out.println(
                        "Checking patient " + i
                );

                Thread.yield();

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    System.out.println(
                            "Monitor interrupted"
                    );

                    return;
                }
            }
        });

        monitor.setName("PatientMonitor");

        System.out.println(
                "Active Threads: "
                + Thread.activeCount()
        );

        monitor.start();

        monitor.join(3000);

        monitor.interrupt();

        Thread.dumpStack();

        System.out.println("Director finished");
    }
}
Java 21 Exam Table
Method	Type	Important?
start()	Instance	⭐⭐⭐⭐⭐
run()	Instance	⭐⭐⭐⭐⭐
join()	Instance	⭐⭐⭐⭐⭐
join(long)	Instance	⭐⭐⭐⭐
interrupt()	Instance	⭐⭐⭐⭐⭐
currentThread()	Static	⭐⭐⭐⭐⭐
yield()	Static	⭐⭐
activeCount()	Static	⭐⭐
dumpStack()	Static	⭐
Most Important for the Exam

Focus heavily on:

start()
run()
join()
interrupt()
currentThread()

These appear much more often than:

yield()
activeCount()
dumpStack()

and understanding the difference between:

start()

and

run()

is one of the most common thread questions on Java exams.