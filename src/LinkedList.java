package massive;

public class LinkedList<T> implements List<T> {
    private static class Node<T> {
        T x;
        Node<T> next;
        Node(T x) { 
        	this.x = x; 
        }
    }
    private Node<T> head; //First node
    private int n;

    //Append to the end of the list
    @Override 
    public boolean add(T element) {
        Node<T> nn = new Node<>(element);
        if (head == null) {
        	head = nn;
        } else {
            Node<T> cur = head;
            while (cur.next != null) {
            	cur = cur.next;
            }
            cur.next = nn;
        }
        n++;
        return true;
    }
    //Insert at the given index
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > n) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
        }

        Node<T> nn = new Node<>(element);

        //Insert at head
        if (index == 0) {
            nn.next = head;
            head = nn;
        } else {
            Node<T> prev = head;
            for (int k = 0; k < index - 1; k++) {
                prev = prev.next;
            }
            nn.next = prev.next;
            prev.next = nn;
        }
        n++;
    }
    
    //Retyrn the element at index
    @Override
    public T get(int index) {
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
        }

        Node<T> cur = head;
        for (int i = 0; i < index; i++) {
            cur = cur.next;
        }
        return cur.x;
    }
    
    @Override
    public T remove(int index) {
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
        }

        T val;
        if (index == 0) {
            val = head.x;
            head = head.next;
        } else {
            Node<T> prev = head;
            for (int i = 0; i < index - 1; i++) {
                prev = prev.next;
            }
            Node<T> victim = prev.next;
            val = victim.x;
            prev.next = victim.next;
        }

        n--;
        return val;
    }

    @Override 
    public int size() { 
    	return n; 
    }
}
