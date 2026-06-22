Hospital Scenario

Imagine a hospital.

Without concurrency:

One nurse must:

Check Patient A
Then Patient B
Then Patient C

Everyone waits.

With concurrency:

Three nurses:

Nurse 1 → Patient A
Nurse 2 → Patient B
Nurse 3 → Patient C

Work happens simultaneously.

2. What Is a Process?

A process is:

A running program.

Examples:

Chrome
Spotify
IntelliJ
Java Application

Each running application is a process.

Process Example
HospitalSystem.exe

When started:

Process Created

Inside that process may be multiple threads.

3. What Is a Thread?

A thread is:

The smallest unit of execution.

A thread performs instructions.

Think:

Process = Hospital

Thread = Nurse

Hospital contains many nurses.

Process contains many threads.

Process vs Thread
Process
 ├── Thread 1
 ├── Thread 2
 ├── Thread 3

Example:

Google Chrome Process
 ├── Render Thread
 ├── Audio Thread
 ├── Network Thread
Single-Threaded Process

Contains one thread.

Process
 └── Thread 1

Everything happens sequentially.

Example:

readFile();
downloadData();
saveData();

One task after another.

Multi-Threaded Process

Contains multiple threads.

Process
 ├── Thread 1
 ├── Thread 2
 └── Thread 3

Tasks overlap.

4. Shared Memory

Threads inside the same process share memory.

Example:

class Counter {
    static int count = 0;
}

Thread A:

count++;

Thread B:

System.out.println(count);

Both use the same variable.




8. Runnable

Runnable represents a task that returns nothing.

Runnable task = () -> {
    System.out.println("Hello");
};

It has one main method:

void run()

Example:

Runnable task = () -> System.out.println("Running task");

Thread t = new Thread(task);
t.start();
9. Callable

Callable represents a task that returns a value.

Callable<Integer> task = () -> {
    return 100;
};

Difference:

Interface	Returns value?	Throws checked exception?
Runnable	No	No
Callable	Yes	Yes

Simple rule:

Runnable = do work
Callable = do work and return result
10. Creating Threads

Modern Java gives multiple ways.

Old way
Thread t = new Thread(() -> {
    System.out.println("Hello");
});
t.start();


Platform thread factory
Thread.ofPlatform().start(() -> {
    System.out.println("Platform thread");
});
Virtual thread factory
Thread.ofVirtual().start(() -> {
    System.out.println("Virtual thread");
});

Exam idea:

Java 21 prefers factory methods because they clearly show the thread type.


















Thread t = new Thread(() -> {
    try {
        Thread.sleep(5000);
    } catch (InterruptedException e) {
        System.out.println("Interrupted");
    }
});

t.start();
t.interrupt();
Step 1: Thread object is created
Thread t = new Thread(() -> { ... });

At this point:

Thread object exists
But it is NOT running yet
State = NEW

Nothing inside the lambda has executed yet.

Step 2: t.start() starts a new thread
t.start();

Now Java creates a separate execution path.

So now you have:

main thread
new thread t

The new thread begins running this code:

try {
    Thread.sleep(5000);
}
Step 3: Thread.sleep(5000) begins

Inside thread t:

Thread.sleep(5000);

This means:

Thread t should pause for 5 seconds

So thread t enters:

TIMED_WAITING state

It is sleeping.

Step 4: Main thread continues immediately

Important: t.start() does not make main wait.

So main immediately moves to:

t.interrupt();
Step 5: What does t.interrupt() do?
t.interrupt();

This means:

Main thread sends an interrupt signal to thread t.

It is like saying:

“Hey thread t, stop waiting/sleeping if you can.”

It does not directly kill the thread.

It does not forcefully stop it.

It sends a signal.

Step 6: Because t is sleeping, interruption wakes it up

Since thread t is inside:

Thread.sleep(5000);

sleep detects the interrupt.

So Java stops the sleep early and throws:

InterruptedException
Step 7: The catch block runs

The exception is caught here:

catch (InterruptedException e) {
    System.out.println("Interrupted");
}

So output is usually:

Interrupted
Step 8: Thread finishes

After printing:

System.out.println("Interrupted");

there is no more code inside the thread.

So thread t ends.

State becomes:

TERMINATED