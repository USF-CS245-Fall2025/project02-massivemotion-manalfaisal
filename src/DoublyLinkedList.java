package massive;

public class DoublyLinkedList<T> implements List<T> {
    private static class Node<T> {
        T x;
        Node<T> next, prev;
        Node(T x) { 
        	this.x = x; 
        }
    }
    private Node<T> head, tail;
    private int n;
   
    //Append to end
    @Override 
    public boolean add(T element) {
        Node<T> nn = new Node<>(element);
        if (tail == null) {
            head = tail = nn;
        } else {
            tail.next = nn;
            nn.prev = tail;
            tail = nn;
        }
        n++;
        return true;
    }
    
   //Insert at index, shifting later nodes to the right
    public void add(int index, T element) {
    	if(index < 0 || index > n) {
    		throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
    	}
    	if(index == n) {
    		add(element);
    		return;
    	}
    	
    	Node<T> cur;
    	if(index < n / 2) {
    		cur = head;
    		for(int i = 0; i < index; i--) {
    			cur = cur.next;
    		}
    	} else {
    		cur = tail;
    		for(int i = n - 1; i > index; i--) {
    			cur = cur.prev;
    		}
    	}
    	
    	//Insert new node BEFORE cur
        Node<T> nn = new Node<>(element);
        nn.next = cur;
        nn.prev = cur.prev;
        if (cur.prev != null) {
        	cur.prev.next = nn;
        } else {
        	head = nn;
        }
        cur.prev = nn;

        n++;
    }
    
    //Get element at index
    @Override 
    public T get(int index) {
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
        }
        if (index < n / 2) {
            Node<T> cur = head;
            for (int i = 0; i < index; i++) {
            	cur = cur.next;
            }
            return cur.x;
        } else {
            Node<T> cur = tail;
            for (int i = n - 1; i > index; i--) {
            	cur = cur.prev;
            }
            return cur.x;
        }
    }
    //Remove and return element at index
    @Override
    public T remove(int index) {
        if (index < 0 || index >= n) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
        }

        //Find node at 'index'
        Node<T> cur;
        if (index < n / 2) {
            cur = head;
            for (int k = 0; k < index; k++) cur = cur.next;
        } else {
            cur = tail;
            for (int k = n - 1; k > index; k--) cur = cur.prev;
        }

        //Unlink
        if (cur.prev != null) {
        	cur.prev.next = cur.next;
        } else {
        	head = cur.next;
        }
        if (cur.next != null) {
        	cur.next.prev = cur.prev; 
        } else {
        	tail = cur.prev;
        }

        n--;
        return cur.x;
    }


    @Override 
    public int size() { 
    	return n; 
    }
}
