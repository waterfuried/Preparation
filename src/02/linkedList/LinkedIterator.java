package linkedList;

import exceptions.*;

public class LinkedIterator<T> implements MyIterable<T> {
    private GenericLink<T> current, head, tail;
    private int size;

    public LinkedIterator() {
        head = null;
        tail = null;
        current = null;
        size = 0;
    }

    public GenericLink<T> getHead() { return head; }
    public GenericLink<T> getTail() { return tail; }

    @Override public int getSize() { return size; }

    @Override public void reset() { current = head; }

    @Override public void moveToEnd() { current = tail; }

    // TODO(?): после сброса позиции итератора в начальное положение
    //  что должно возвращаться этим методом - следующее значение или текущее?
    //  если следующее, как получить первый элемент? вводить отдельный метод?
    @Override public T next() {
        current = current.next;
        return current.data;
    }

    @Override public boolean hasNext() {
        return current.next != null;
    }

    @Override public boolean contains(T t) throws EmptyListException {
        return isPresent(t) != null;
    }

    @Override public GenericLink<T> getFirstAppearance(T t) {
        current = head;
        while (current != null)
            if (current.data.equals(t))
                return current;
            else
                current = current.next;
        return null;
    }

    @Override public GenericLink<T> getLastAppearance(T t) {
        current = tail;
        while (current != null)
            if (current.data.equals(t))
                return current;
            else
                current = current.prev;
        return null;
    }

    @Override public boolean atBegin() { return current.prev == null; }

    @Override public boolean atEnd() { return current.next == null; }

    @Override public void add(T t) {
        GenericLink<T> l = new GenericLink<>(t);
        if (head == null)
            addFirst(l);
        else {
            l.prev = tail;
            tail.next = l;
            tail = l;
        }
        size++;
    }

    @Override public boolean insertAfter(T t) {
        GenericLink<T> l = new GenericLink<>(t);
        if (head == null)
            addFirst(l);
        else {
            if (current == null) return false;
            updateLinksAfter(current, l);
        }
        size++;
        return true;
    }

    @Override public boolean insertBefore(T t) {
        GenericLink<T> l = new GenericLink<>(t);
        if (head == null)
            addFirst(l);
        else {
            if (current == null) return false;
            updateLinksBefore(current, l);
        }
        size++;
        return true;
    }

    public void addAfter(int index, T t) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        updateLinksAfter(getElementAt(index), new GenericLink<>(t));
        size++;
    }

    public void addBefore(int index, T t) throws IndexOutOfBoundsException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        updateLinksBefore(getElementAt(index), new GenericLink<>(t));
        size++;
    }

    @Override public void remove(T t) throws EmptyListException, NoSuchValueException {
        GenericLink<T> l = isPresent(t);
        if (l == null) throw new NoSuchValueException(t);
        deleteElement(l);
    }

    @Override public void removeAll(T t) throws EmptyListException, NoSuchValueException {
        GenericLink<T> l = isPresent(t);
        if (l == null) throw new NoSuchValueException(t);
        do {
            deleteElement(l);
            l = isPresent(t);
        } while (l != null);
    }

    @Override
    public void clear() {
        GenericLink<T> l = head;
        while (l != null) {
            GenericLink<T> next = l.next;
            l.prev = null;
            l.next = null;
            l.data = null;
            l = next;
        }
        head = null;
        tail = null;
        current = null;
    }

    @Override public void removeAt(int index) throws EmptyListException {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();
        deleteElement(getElementAt(index));
    }

    private void addFirst(GenericLink<T> l) {
        head = l;
        tail = l;
        current = l;
    }

    private void updateLinksAfter(GenericLink<T> cur, GenericLink<T> l) {
        if (cur.equals(tail)) tail = l;
        l.prev = cur;
        l.next = cur.next;
        cur.next.prev = l;
        cur.next = l;
    }

    private void updateLinksBefore(GenericLink<T> cur, GenericLink<T> l) {
        if (cur.equals(head)) head = l;
        l.next = cur;
        l.prev = cur.prev;
        cur.prev.next = l;
        cur.prev = l;
    }

    private GenericLink<T> getElementAt(int index) {
        GenericLink<T> l = head;
        int i = 0;
        while (i++ < index) l = l.next;
        return l;
    }

    private GenericLink<T> isPresent(T t) throws EmptyListException {
        if (head == null) throw new EmptyListException();
        GenericLink<T> l = head;
        while (l != null && !l.data.equals(t)) l = l.next;
        return l;
    }

    private void deleteElement(GenericLink<T> l) {
        boolean isCurrent = l.equals(current);
        if (l.prev == null) { // head
            head = l.next;
            if (l.next != null) head.next = l.next.next;
        } else
        if (l.next == null) { //tail
            tail = l.prev;
            tail.prev = l.prev.prev;
        } else {
            l.prev.next = l.next;
            l.next.prev = l.prev;
        }
        // если удален элемент, являвшийся текущим указателем итератора,
        // переместить указатель либо на следующий, либо на предыдущий
        if (isCurrent)
            if (l.next == null) current = l.prev; else current = l.next;
        size--;
    }

    private T getCurrent() { return current == null ? null : current.data; }

    // метод remove в Iterator описан как удаление последнего возвращенного итератором элемента
    private T deleteCurrent() {
        T t = current.data;
        if (current.next != null) current.next.prev = null;
        current = current.next;
        head = current;
        return t;
    }
}