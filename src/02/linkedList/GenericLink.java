package linkedList;

class GenericLink<T> {
    T data;
    GenericLink<T> next, prev;

    GenericLink(T data) {
        this.data = data;
        prev = null;
        next = null;
    }

    @Override public String toString() { return data.toString(); }
}
