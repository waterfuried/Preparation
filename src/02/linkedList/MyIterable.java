package linkedList;

import java.util.Iterator;

public interface MyIterable<T> extends Iterator<T> {
    // установить текущую позицию на начало списка
    void reset();

    // установить текущую позицию на конец списка
    void moveToEnd();

    // получить следующий элемент списка
    @Override T next();

    // проверить наличие следующего элемента в списке
    @Override boolean hasNext();

    // проверить наличие определенного элемента в списке
    boolean contains(T t);

    // найти первый подходящий элемент
    GenericLink<T> getFirstAppearance(T t);

    // найти последний подходящий элемент
    GenericLink<T> getLastAppearance(T t);

    // проверить, находится ли текущая позиция в начале списка
    boolean atBegin();

    // проверить, находится ли текущая позиция в конце списка
    boolean atEnd();

    // добавить значение в конец списка
    void add(T t);

    // вставить значение после текущей позиции в списке
    boolean insertAfter(T t);

    // вставить значение перед текущей позицей в списке
    boolean insertBefore(T t);

    // вставить значение после определенной позиции в списке
    void addAfter(int index, T t);

    // вставить значение перед определенной позицией в списке
    void addBefore(int index, T t);

    // удалить из списка первое встретившееся определенное значение
    void remove(T t);

    // удалить из списка все определенные значение
    void removeAll(T t);

    // удалить из списка значение в определенной позиции
    void removeAt(int index);

    // очистить список
    void clear();

    // получить размер списка
    int getSize();

    // получить указатели на начало и конец списка
    GenericLink<T> getHead();
    GenericLink<T> getTail();
}