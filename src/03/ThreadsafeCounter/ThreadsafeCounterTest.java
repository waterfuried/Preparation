package ThreadsafeCounter;

import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;

import static ThreadsafeCounter.SharedResources.*;

/*
  2. Реализовать потокобезопасный счетчик с помощью интерфейса Lock.
*/

public class ThreadsafeCounterTest {
    public static void main(String[] args) {
        ReentrantLock sharedLock = new ReentrantLock();

        // поскольку потоки выполняют схожие операции, их стоит создавать через пул потоков;
        // для сокращения затрат на создание пула
        // установить его размер в половину от предельного числа потоков
        ExecutorService service = Executors.newFixedThreadPool(MAX_THREAD_COUNT/2);

        int i = 0;
        while (i++ < MAX_THREAD_COUNT)
            service.submit(new ThreadsafeCounter(sharedLock, i));
        //без использования пула; надеюсь, что создаваемые так потоки существуют паралелльно
        //new ThreadsafeCounter.ThreadsafeCounter(sharedLock, i).run();

        service.shutdown();
    }
}