import exceptions.*;

// 2. Реализовать основные методы ArrayList.
public class MyArrayList {
    private int[] arr;
    private int capacity;

    public static final int SORT_BUBBLE = 0;
    public static final int SORT_SELECT = 1;
    public static final int SORT_INSERT = 2;

    /** создать новый список указанной размерности
     * @param size размерность списка
     * @throws RuntimeException если размерность списка неположительная
     */
    public MyArrayList(int size) throws RuntimeException {
        // есть некоторая смысловая неувязка: если метод append воспринимать как альтернативу set -
        // задание значений элементов, считая с индекса 0, то можно задавать здесь емкость как 0,
        // но при этом, во избежание путаницы, метод set вообще стоит удалить, поскольку он изменяет
        // элементы массива, никак не меняя значения их действительного количества (capacity);
        // если его (set) оставить и задать с его помощью несколько элементов, то вызов append будет
        // предполагать желание добавить (подчеркиваю) новый элемент, то есть раширить массив -
        // в этом случае запись в append должна происходть в позицию size, а не 0
        if (size <= 0)
            throw new IllegalArgumentException();
        capacity = size;//0
        arr = new int[size];
    }

    /** инициализровать список массивом
     * @param init массив значений
     */
    public MyArrayList(int[] init) {
        capacity = init.length;
        arr = init;
    }

    /** получить представление списка в виде строки
     * @return представление списка в виде строки
     */
    @Override public String toString() {
        if (capacity == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < capacity; i++)
            sb.append(arr[i]).append(capacity-i > 1 ? " " : "");
        return sb.toString();
    }

    /** получить значение по указанному индексу
     * @param idx индекс элемента списка
     * @return значение по указанному индексу
     * @throws IndexOutOfBoundsException если индекс отрицательный или превышает расзмерность списка
     */
    public int get(int idx) throws IndexOutOfBoundsException {
        if (idx < 0 || idx >= capacity) throw new IndexOutOfBoundsException();
        return arr[idx];
    }

    /** установить значение элемента списка по указанному индексу
     * @param idx индекс элемента списка
     * @param value новое значение элемента
     * @throws IndexOutOfBoundsException если индекс отрицательный или превышает расзмерность списка
     */
    public void set(int idx, int value) throws IndexOutOfBoundsException {
        if (idx < 0 || idx >= capacity) throw new IndexOutOfBoundsException();
        arr[idx] = value;
    }

    /** удалить из списка первое вхождение указанного значения
     * @param value удаляемое значение
     * @throws NoSuchValueException если список не содержит указанного значения
     */
    void delete(Integer value) throws NoSuchValueException {
        for (int i = 0; i < capacity; i++)
            if (arr[i] == value) {
                System.arraycopy(arr, i + 1, arr, i, capacity - i - 1);
                --capacity;
                return;
            }
        throw new NoSuchValueException(value);
    }

    /** удалить из списка все вхождения указанного значения
     * @param value удаляемое значение
     * @throws NoSuchValueException если список не содержит указанного значения
     */
    void deleteAll(Integer value) throws NoSuchValueException {
        int i = 0, counter = 0;
        while (i < capacity)
            if (arr[i] == value) {
                // искать последовательность удаляемых значений
                int starter = i;
                counter = 1;
                while (i < capacity - 1 && arr[i+1] == value) {
                    counter++;
                    i++;
                }
                System.arraycopy(arr, i+1, arr, starter, capacity-i-1);
                capacity -= counter;
            } else
                i++;
        if (counter == 0) throw new NoSuchValueException(value);
    }

    /**
     * очистить список
     */
    void clear() { capacity = 0; }

    /** добавить новое значение в конец списка
     * @param value новое значение
     */
    void append(int value) {
        if (capacity == arr.length)
            if (capacity == 0)
                arr = new int[1];
            else {
                int[] old = arr;
                arr = new int[old.length * 2];
                System.arraycopy(old, 0, arr, 0, old.length);
            }
        arr[capacity++] = value;
    }

    /** вставить значение в указанную позицию
     * @param idx позиция для вставки значения
     * @param value вставляемое значение
     * @throws IndexOutOfBoundsException если индекс отрицательный или превышает расзмерность списка
     */
    // вставить число в позицию в массиве, расширив его размерность
    void insert(int idx, int value) throws IndexOutOfBoundsException {
        if (idx < 0 || idx >= capacity) throw new IndexOutOfBoundsException();
        int[] old = arr;
        if (capacity == arr.length) arr = new int[old.length * 2];
        // если вставка делается не в начало массива, скопировать все элементы до позиции вставки
        if (idx > 0) System.arraycopy(old, 0, arr, 0, idx);
        // скопировать все элементы после позиции вставки: по меньшей мере 1 - последний
        System.arraycopy(old, idx, arr, idx + 1, capacity - idx);
        arr[idx] = value;
        capacity++;
    }

    /** проверить наличие значения в списке
     * @param value искомое значение
     * @return true, если значение в списке присутствует,<br>
     * false - в противном случае
     */
    public boolean contains(int value) {
        // линейный поиск имеет порядок выполнения O(n)
        /*for (int i = 0; i < capacity; i++)
            if (arr[i] == value) return true;
        return false;*/
        // при половинном делении - O(log(n))
        int low = 0, high = capacity - 1, mid;
        while (low < high) {
            mid = (low + high) / 2;
            if (value == arr[mid])
                return true;
            else
            if (value < arr[mid])
                high = mid;
            else
                low = mid + 1;
        }
        return false;
    }

    // поменять значения местами
    private void swap(int a, int b) {
        int tmp = arr[a];
        arr[a] = arr[b];
        arr[b] = tmp;
    }

    /** сортировать список
     * @param type тип сортировки:<br>
     *             SORT_BUBBLE=пузырьковая<br>
     *             SORT_INSERT=вставками<br>
     *             SORT_BUBBLE=выбором<br>
     * @return время сортировки, мс
     */
    public long sort(int type) {
        long start = System.nanoTime();
        // switch оставлен в такой форме для совместимости с Java 8
        switch (type) {
            case SORT_SELECT: sortSelect(); break;
            case SORT_INSERT: sortInsert(); break;
            default: sortBubble();
        }
        return System.nanoTime() - start;
    }

    /** сортировать список пузырьковой сортировкой
     * @return время сортировки, мс
     */
    public long sort() {
        long start = System.nanoTime();
        sortBubble();
        return System.nanoTime() - start;
    }

    // пузырьковая сортировка
    private void sortBubble() {
        for (int iter = 0; iter < capacity; iter++) {
            boolean bubble = false;
            for (int idx = 0; idx < capacity-iter-1; idx++)
                if (arr[idx] > arr[idx+1]) {
                    swap(idx, idx+1);
                    bubble = true;
                }
            if (!bubble) break;
        }
    }

    // сортировка выбором
    private void sortSelect() {
        for (int idx = 0; idx < capacity; idx++) {
            int curr = idx;
            for (int srch = idx + 1; srch < capacity; srch++)
                if (arr[srch] < arr[curr]) curr = srch;
            if (curr != idx) swap(idx, curr);
        }
    }

    // сортировка вставками
    private void sortInsert() {
        for (int curr = 1; curr < capacity; curr++) {
            int temp = arr[curr];
            int move = curr;
            while (move > 0 && arr[move-1] >= temp)
                arr[move] = arr[--move];
            arr[move] = temp;
        }
    }
}