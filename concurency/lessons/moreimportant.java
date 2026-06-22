This is describing some important concepts about thread execution order in Java.

Example Code
public class JoinExample {

    public static void main(String[] args) throws InterruptedException {

        Thread worker = new Thread(() -> {

            System.out.println("begin");

            for (int i = 1; i <= 5; i++) {
                System.out.println(i);
            }

            System.out.println("end");
        });

        worker.start();

        worker.join();

        System.out.println("Main Finished");
    }
}
1. Execution Order Is Unknown

When multiple threads run, the JVM scheduler decides when each thread gets CPU time.

Example:

Thread t1 = new Thread(() -> System.out.println("Thread 1"));
Thread t2 = new Thread(() -> System.out.println("Thread 2"));

t1.start();
t2.start();

Possible output:

Thread 1
Thread 2

Or:

Thread 2
Thread 1

Or any other scheduling order.

Why?

Because threads execute concurrently and the JVM does not guarantee which thread runs first.

Exam Tip

t1.start();
t2.start();

does NOT mean:

t1 finishes before t2 starts

It only means:

t1 is requested to start first
2. begin Always Prints Before end

Inside a single thread, statements execute sequentially.

Example:

Thread worker = new Thread(() -> {

    System.out.println("begin");

    System.out.println("working");

    System.out.println("end");
});

Output must be:

begin
working
end

Never:

end
begin
working

because a thread executes its own instructions in order.

3. Loop Order Inside a Thread Is Preserved

Example:

Thread worker = new Thread(() -> {

    for (int i = 1; i <= 5; i++) {
        System.out.println(i);
    }
});

Output:

1
2
3
4
5

The loop variable changes in sequence.

You will never see:

1
4
2
5
3

inside the same thread.

4. What Can Change?

When another thread runs simultaneously.

Example:

Thread t1 = new Thread(() -> {

    System.out.println("begin");

    for (int i = 1; i <= 5; i++) {
        System.out.println(i);
    }

    System.out.println("end");
});

Thread t2 = new Thread(() -> {
    System.out.println("Hello");
});

t1.start();
t2.start();

Possible output:

begin
1
2
Hello
3
4
5
end

Or:

Hello
begin
1
2
3
4
5
end

Notice:

Hello can appear almost anywhere.
But inside t1, the order is preserved.

You can never get:

begin
3
1
2
4
5
end

because the loop executes sequentially.

5. join() Prevents Main Thread From Exiting Early

Without join():

Thread worker = new Thread(() -> {
    System.out.println("Worker Running");
});

worker.start();

System.out.println("Main Finished");

Possible output:

Main Finished
Worker Running

or

Worker Running
Main Finished

The main thread does not wait.

6. What Does join() Do?

join() tells the current thread:

"Wait until the target thread finishes."

Example:

worker.start();

worker.join();

System.out.println("Main Finished");

Execution:

main thread
    |
    |---- worker.start()
    |
    |---- waits at join()
    |
worker thread runs
    |
    |---- finishes
    |
main thread resumes
    |
    |---- Main Finished

Output:

Worker Running
Main Finished

Now the order is guaranteed.

Hospital Scenario

Imagine a hospital:

Main Thread

Hospital Director

Worker Thread

Nurse preparing medication

Without join():

Director leaves hospital
Nurse still preparing medicine

With join():

Director waits
Nurse finishes preparation
Director leaves

That is exactly what join() does.

Key Exam Rules






===============FUTURE================ 
invokeAll(Collection<Callable<T>>)
Purpose

Run ALL tasks.

Wait until ALL finish.

Return ALL futures.

Hospital Scenario

The director sends three doctors:

Doctor A → ICU count
Doctor B → ER count
Doctor C → Surgery count

Director wants:

ALL reports

before continuing.

Code
var tasks = List.of(
        (Callable<String>) () -> "ICU Report",
        (Callable<String>) () -> "ER Report",
        (Callable<String>) () -> "Surgery Report"
);

List<Future<String>> results =
        service.invokeAll(tasks);
Timeline
// Doctor A starts
// Doctor B starts
// Doctor C starts

Director waits
// Waiting...
// Waiting...
// Waiting...
All finish
// ICU Report
// ER Report
// Surgery Report

Then invokeAll() returns List<Future<String>>
Getting Results
for(Future<String> f : results)
    System.out.println(f.get());

Output:

ICU Report
ER Report
Surgery Report
Visual
Director
   |
   +--> Doctor A
   |
   +--> Doctor B
   |
   +--> Doctor C

Wait until ALL finish
Exam Rule

invokeAll()

✅ Executes all tasks

✅ Waits for all tasks

✅ Returns list of Futures

5. invokeAny(Collection<Callable<T>>)
Purpose

Run ALL tasks.

Return the FIRST successful result.

Ignore the rest.

Hospital Emergency Scenario

A critical patient arrives.

Director asks three specialists simultaneously:

Cardiologist
Neurologist
Trauma Surgeon

Question:

"What treatment should we start immediately?"

The director only needs ONE answer.

The first doctor who responds wins.

Code
var tasks = List.of(
        (Callable<String>) () -> {
            Thread.sleep(5000);
            return "Cardiology";
        },

        (Callable<String>) () -> {
            Thread.sleep(1000);
            return "Neurology";
        },

        (Callable<String>) () -> {
            Thread.sleep(3000);
            return "Trauma";
        }
);

// Execute
String result =  service.invokeAny(tasks);
Timeline
// Cardiology -> 5 sec
// Neurology -> 1 sec
// Trauma -> 3 sec
First completed
Neurology
Result
System.out.println(result);
// Output:Neurology

What happens to the others?

The executor attempts to cancel the remaining tasks that havent completed.

Neurology finished ✔

Cardiology cancelled
Trauma cancelled

(assuming they havent already finished)






================FUTURE METHODS================
Future Interface
What is a Future?

A Future<V> represents the result of a task that may finish later.

Think of it as a claim ticket given to you after submitting work.

You don't have the result yet, but you can use the ticket later to:

Check if the work is finished
Get the result
Cancel the work
Professional Hospital Scenario

Imagine a hospital director asks a lab technician:

"Run a blood test and give me the results."

The test takes time.

The technician immediately gives the director a tracking ticket.

Director:
    Run blood test.

Lab:
    Here is your tracking ticket.

Director:
    I'll check later.

That tracking ticket is a:

Future<TestResult>
Example
ExecutorService service =
        Executors.newSingleThreadExecutor();

Future<String> result =
        service.submit(() -> {
            Thread.sleep(5000);
            return "Blood Test Complete";
        });

Immediately after submission:

Task running...
Result not ready yet.

But you have:

Future<String> result

which represents the future result.

1. isDone()
Purpose

Checks whether the task has finished.

Hospital Scenario

Director asks:

"Has the lab finished the blood test yet?"

Code
result.isDone();
Possible Output
Still running
false
Finished
true
Example
System.out.println(result.isDone());

Output:

false

Five seconds later:

true
Timeline
Blood test started
       |
       |
       |
isDone() -> false
       |
       |
Blood test completed
       |
isDone() -> true
2. isCancelled()
Purpose

Checks whether the task was cancelled.

Hospital Scenario

Director says:

"Stop the blood test. We no longer need it."

Later asks:

"Was it cancelled?"

Code
result.isCancelled();
Possible Results
Not cancelled
false
Cancelled
true
Example
result.cancel(true);

System.out.println(result.isCancelled());

Output:

true
3. cancel(boolean mayInterrupt)
Purpose

Attempts to stop a task.

Hospital Scenario

A technician is processing a report.

The director suddenly says:

"Stop immediately."

Code
result.cancel(true);
Parameter Meaning
true
cancel(true)

means:

Interrupt the worker thread if possible.

false
cancel(false)

means:

Do not interrupt a running thread.

Only cancel if it hasn't started yet.

Example
Future<String> result =
        service.submit(() -> {
            Thread.sleep(10000);
            return "Finished";
        });

result.cancel(true);

Possible result:

Task interrupted
Timeline
Task started
      |
      |
cancel(true)
      |
      v
Interrupted
4. get()
Purpose

Wait for completion and return the result.

Hospital Scenario

Director says:

"I need the blood test results now."

If the lab isn't finished:

Director waits...

If finished:

Result returned immediately.
Code
String report = result.get();
Example
Future<String> result =
        service.submit(() -> {
            Thread.sleep(5000);
            return "Blood Test Complete";
        });

System.out.println(result.get());

Output after 5 seconds:

Blood Test Complete
Important Exam Point

get() blocks.

Meaning:

Wait
Wait
Wait
Wait
Result arrives
Continue
Timeline
Task starts
      |
      |
      |
get()
      |
Main thread waits
      |
Task finishes
      |
Result returned
5. get(long timeout, TimeUnit unit)
Purpose

Wait only a limited amount of time.

If the task takes too long:

TimeoutException

is thrown.

Hospital Scenario

Director says:

"Wait 3 minutes for the blood test. If it isn't ready, move on."

Code
result.get(3, TimeUnit.MINUTES);
Example
Future<String> result =
        service.submit(() -> {
            Thread.sleep(10000);
            return "Blood Test Complete";
        });

result.get(2, TimeUnit.SECONDS);

Task needs:

10 seconds

Director waits:

2 seconds

Result:

TimeoutException
Timeline
Task started
      |
      |
get(2 sec)
      |
      |
2 seconds reached
      |
TimeoutException
Full Example
ExecutorService service =
        Executors.newSingleThreadExecutor();

Future<Integer> future =
        service.submit(() -> {
            Thread.sleep(3000);
            return 100;
        });

System.out.println(future.isDone());

Integer value = future.get();

System.out.println(value);

System.out.println(future.isDone());

service.shutdown();

Possible Output

false
100
true
Future Method Summary
Method	Purpose
isDone()	Has the task finished?
isCancelled()	Was the task cancelled?
cancel(true)	Try to stop the task and interrupt thread
cancel(false)	Cancel only if not started
get()	Wait forever for result
get(timeout, unit)	Wait limited time for result
OCP Exam Memory Trick
Hospital Ticket
Director submits blood test
          |
          v
      Future
          |
          +--> isDone()
          |
          +--> isCancelled()
          |
          +--> cancel()
          |
          +--> get()
          |
          +--> get(timeout)

Think:

Future = Tracking ticket for work that finishes later.





// ================FUTURE SCHEDULING================

19. Scheduling Tasks

Scheduling means:

“Run this task later or again and again.”

Used for:

Run in the future
Run repeatedly
Run after a delay
20. ScheduledExecutorService
ScheduledExecutorService service =
        Executors.newSingleThreadScheduledExecutor();

This creates one worker thread that can schedule tasks.

Think:

Hospital Scheduler
        |
        v
One worker available
21. schedule()
Runs once after a delay
service.schedule(task1, 10, TimeUnit.SECONDS);

Meaning:

Wait 10 seconds
Then run task1 one time

Example:

Runnable task1 = () -> System.out.println("Hello Zoo");

service.schedule(task1, 10, TimeUnit.SECONDS);

Possible output after 10 seconds:

Hello Zoo
22. schedule() with Callable
Callable<String> task2 = () -> "Monkey";

service.schedule(task2, 8, TimeUnit.MINUTES);

Meaning:

Wait 8 minutes
Then run task2
Return "Monkey"

Because it returns a value, you can store it:

ScheduledFuture<String> result =
        service.schedule(task2, 8, TimeUnit.MINUTES);
23. ScheduledFuture

A ScheduledFuture is like a normal Future, but it also knows:

getDelay()

Example:

long delay =
        result.getDelay(TimeUnit.SECONDS);

Meaning:

How many seconds remain before this task runs?
24. scheduleAtFixedRate()
service.scheduleAtFixedRate(task1, 5, 1, TimeUnit.MINUTES);

Meaning:

Wait 5 minutes first
Then run every 1 minute

Timeline:

Start program
|
| wait 5 minutes
v
Run task
|
| 1 minute after scheduled start time
v
Run task again
|
| 1 minute after scheduled start time
v
Run task again
Important idea

scheduleAtFixedRate() focuses on the clock schedule.

It tries to run at fixed times:

5:00
6:00
7:00
8:00

Even if the task takes time.

Hospital scenario

A hospital wants patient room checks every 1 minute:

9:00 check room
9:01 check room
9:02 check room
9:03 check room

The scheduler tries to keep that exact rhythm.

Problem

If each check takes too long, tasks can pile up or run back-to-back.

Example:

Task should run every 1 minute
But each task takes 3 minutes

This can overwhelm the executor.

25. scheduleWithFixedDelay()
service.scheduleWithFixedDelay(task1, 0, 2, TimeUnit.MINUTES);

Meaning:

Start immediately
Run task
After task finishes, wait 2 minutes
Run again

Timeline:

Run task
|
task finishes
|
wait 2 minutes
|
run task again
|
task finishes
|
wait 2 minutes
|
run task again
Hospital scenario

A nurse must clean a room.

Rule:

“After finishing one room cleaning, wait 2 minutes before starting again.”

So it depends on when the previous task finishes.

Fixed Rate vs Fixed Delay
Method	Meaning
schedule()	Run once after delay
scheduleAtFixedRate()	Run repeatedly based on start time schedule
scheduleWithFixedDelay()	Run repeatedly after previous task finishes plus delay
Easy memory trick
schedule()
Run one time later
scheduleAtFixedRate()
Run at fixed clock times
scheduleWithFixedDelay()
Finish first, then wait
Exam example
service.scheduleAtFixedRate(task1, 5, 1, TimeUnit.MINUTES);

Means:

Initial delay: 5 minutes
Period: every 1 minute
service.scheduleWithFixedDelay(task1, 0, 2, TimeUnit.MINUTES);

Means:

Initial delay: 0 minutes
Delay: 2 minutes after previous task finishes