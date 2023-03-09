package ThreadsafeCounter;

import java.util.concurrent.locks.ReentrantLock;

import static ThreadsafeCounter.SharedResources.*;

// с использованием многопоточной блокировки через классы,
// реализующие интерфейс Lock - ReentrantLock, ReadAndWriteLock, ...
class ThreadsafeCounter implements Runnable {
    private final int number;

    // класс реализует интерфейс Lock, предоставляя возможности раз/блокировки
    private final ReentrantLock lock;

    public ThreadsafeCounter(ReentrantLock lock, int number) {
        this.lock = lock;
        this.number = number;
    }

    // изменение счетчика потоком...
    @Override public void run() {
        lock.lock(); // ...только одним

        try {
            boolean increase = Math.random()*RANDOM_RANGE > (RANDOM_RANGE / 2f);
            if (increase) SharedResources.counter++; else SharedResources.counter--;
            System.out.println(
                    //Thread.currentThread().getName()+" "+
                    "Thread #"+number+" have "+
                    (increase ? "increased" : "decreased")+" counter at "+
                    new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date())+
                    "> " + SharedResources.counter);
        }
        finally { lock.unlock(); } // ...в любой момент времени
    }
}