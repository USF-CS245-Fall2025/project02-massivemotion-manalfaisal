package massive;

public class DummyHeadLinkedList<T> implements List<T> {
    private static class Node<T> {
        T x;
        Node<T> next;
        Node() {}
        Node(T x) { 
        	this.x = x; 
        }
    }

    private final Node<T> head = new Node<>(); //dummy head
    private int n;

    //Append to the end
    @Override
    public boolean add(T element) {
        Node<T> nn = new Node<>(element);
        Node<T> cur = head;
        while (cur.next != null) {
            cur = cur.next;   
        }
        cur.next = nn;           
        n++;
        return true;
    }

    //Insert at index
    @Override
    public void add(int index, T element) {
        if (index < 0 || index > n) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
        }
        Node<T> prev = head;            
        for (int k = 0; k < index; k++) {
            prev = prev.next;           
        }
        Node<T> nn = new Node<>(element);
        nn.next = prev.next;
        prev.next = nn;
        n++;
    }    
    
    //Get element at index
    @Override
    public T get(int index) {
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
        }
        Node<T> cur = head.next;        
        for (int k = 0; k < index; k++) {
            cur = cur.next;
        }
        return cur.x;
    }

    //Remove and return element at index
    @Override
    public T remove(int index) {
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
        }
        Node<T> prev = head;            
        for (int k = 0; k < index; k++) {
            prev = prev.next;
        }
        Node<T> victim = prev.next;
        prev.next = victim.next;       
        n--;
        return victim.x;
    }

    @Override
    public int size() {
        return n;
    }
}

