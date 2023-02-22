import linkedList.*;

public class Test {
    public static void main(String[] args) {
        System.out.println("ArrayList demo:");
        MyArrayList list = new MyArrayList(new int[] {1, -1, -2, 5, 3, 4, -3, -5, 0, -7, -4, 8, -3, 9, 0, 2});
        list.append(11);
        System.out.println(list);
        list.deleteAll(-3);
        list.sort();
        System.out.println(list);

        System.out.println("\nLinkedList demo:");
        MyLinkedList<Integer> linked = new MyLinkedList<>();
        linked.add(1);
        linked.add(2);
        linked.add(3);
        linked.add(4);
        int N = 3;
        boolean b = linked.getFirstAppearance(N) != null;
        System.out.println(linked+"\nthe value of "+N+" is "+(b ? "" : "not ")+"in list");
        linked.insertBefore(5);
        linked.addAfter(1, 0);
        linked.addBefore(2, -1);
        linked.insertAfter(3);
        System.out.println(linked);
        linked.remove(0);
        linked.insertAfter(3);
        linked.insertBefore(0);
        linked.insertAfter(0);
        System.out.println(linked);
        linked.removeAll(3);
        linked.getIterator().reset();
        // не стал ломать голову, как после сброса итератора на начало не пропускать первый элемент
        while (linked.getIterator().hasNext())
            System.out.print(" "+linked.getIterator().next());
        linked.clear();
    }
}