package ThreadsafeCounter;

public class SharedResources {
    // верхняя граница диапазона случайных чисел
    public static final int RANDOM_RANGE = 100;
    // число создаваемых потоков
    public static final int MAX_THREAD_COUNT = 10;


    // разделяемый ресурс - сам счетчик, согласованно изменяемый несколькими потоками
    public static int counter = 0;
}