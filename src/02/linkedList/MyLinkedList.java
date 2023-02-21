package linkedList;

import exceptions.*;

// 1. Реализовать основные методы связанного списка.
public class MyLinkedList<T> {
    private final MyIterable<T> iterator;

    public MyLinkedList() { iterator = new LinkedIterator<>(); }

    // проверить список на пустоту
    public boolean isEmpty() { return iterator.getHead() == null; }

    // вернуть первое значение списка
    public T peekHead() throws EmptyListException {
        GenericLink<T> head = iterator.getHead();
        if (head == null) throw new EmptyListException();

        return head.data;
    }
    // синоним операции
    public T peekFirst() throws EmptyListException { return peekHead(); }

    // вернуть последнее значение списка
    public T peekTail() throws EmptyListException {
        GenericLink<T> tail = iterator.getTail();
        if (tail == null) throw new EmptyListException();

        return tail.data;
    }
    // синоним операции
    public T peekLast() throws EmptyListException { return peekTail(); }

    @Override public String toString() throws EmptyListException {
        GenericLink<T> head = iterator.getHead();
        if (head == null) throw new EmptyListException();

        StringBuilder sb = new StringBuilder();
        linkedList.GenericLink<T> l = head;
        while (l != null) {
            sb.append(" ").append(l.data);
            l = l.next;
        }
        return sb.toString();
    }

    public String toStringUpsideDown() throws RuntimeException {
        GenericLink<T> head = iterator.getHead(), tail = iterator.getTail();
        if (head == null) throw new EmptyListException();

        StringBuilder sb = new StringBuilder();
        linkedList.GenericLink<T> l = tail;
        while (l != null) {
            sb.append(" ").append(l.data);
            l = l.prev;
        }
        return sb.toString();
    }

    public MyIterable<T> getIterator() { return iterator; }

    public int size() { return iterator.getSize(); }

    public boolean contains(T t) throws EmptyListException { return iterator.contains(t); }

    public GenericLink<T> getFirstAppearance(T t) { return iterator.getFirstAppearance(t); }
    public GenericLink<T> getLastAppearance(T t) { return iterator.getLastAppearance(t); }

    public void add(T t) { iterator.add(t); }

    public void addBefore(int index, T t) throws IndexOutOfBoundsException {
        iterator.addBefore(index, t);
    }

    public void addAfter(int index, T t) throws IndexOutOfBoundsException {
        iterator.addAfter(index, t);
    }

    public void insertBefore(T t) { iterator.insertBefore(t); }

    public void insertAfter(T t) { iterator.insertAfter(t); }

    public void remove(T t) throws EmptyListException, NoSuchValueException {
        iterator.remove(t);
    }

    public void removeAll(T t) throws EmptyListException, NoSuchValueException {
        iterator.removeAll(t);
    }

    public void removeAt(int index) throws EmptyListException {
        iterator.removeAt(index);
    }

    public void clear() { iterator.clear(); }
}