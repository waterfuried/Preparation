import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

/*
  2. Реализовать потокобезопасный счетчик с помощью интерфейса Lock.
*/

// с использованием многопоточной блокировки через классы,
// реализующие интерфейс Lock - ReentrantLock, ReadAndWriteLock, ...
class ThreadsafeCounter implements Runnable {
    // сам счетчик
    private static int counter = 0;

    // число создаваемых потоков
    private static final int MAX_THREAD_COUNT = 10;

    // верхняя граница диапазона случайных чисел
    private static final int RANDOM_RANGE = 100;

    // класс реализует интерфейс Lock, предоставляя возможности раз/блокировки
    private final ReentrantLock lock;

    public ThreadsafeCounter(ReentrantLock lock) { this.lock = lock; }

    // изменение счетчика потоком...
    @Override public void run() {
        lock.lock(); // ...только одним

        try {
            boolean increase = Math.random()*RANDOM_RANGE > (RANDOM_RANGE / 2f);
            if (increase) counter++; else counter--;
            System.out.println(
                    Thread.currentThread().getName()+" "+
                            (increase ? "increased" : "decreased")+
                            ": " + counter);
        }
        finally { lock.unlock(); } // ...в каждый момент времени
    }

    public static void main(String[] args) {
        ReentrantLock sharedLock = new ReentrantLock();

        // поскольку потоки выполняют схожие операции, их стоит создавать через пул потоков
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREAD_COUNT);

        int i = 0;
        while (i++ < MAX_THREAD_COUNT)
            service.submit(new ThreadsafeCounter(sharedLock));
            //без использования пула; надеюсь, что создаваемые так потоки существуют паралелльно
            //new ThreadsafeCounter(sharedLock).run();

        service.shutdown();
    }
}