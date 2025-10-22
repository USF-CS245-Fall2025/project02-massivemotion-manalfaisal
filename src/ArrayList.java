package massive;

/* A simple dynamic array implementation of the custom List<T> interface.
 * Grows automatically as elements are added.
 */
public class ArrayList<T> implements List<T> {
    private Object[] a = new Object[10]; 
    private int n = 0;                    

    @Override
    public boolean add(T element) {
        if (n == a.length) {
        	Object[] b = new Object[a.length * 2];
        	for(int i = 0; i < a.length; i++) {
        		b[i] = a[i];
        	}
        	a = b;
        }
        a[n] = element; 
        n++;
        return true;
    }
    
    //Inserts an element at a given index, shifting later elements right
    @Override 
    public void add(int index, T element) {
    	if(index < 0 || index > n)
    		throw new IndexOutOfBoundsException();
    	
    	//Resize if needed
    	if(n == a.length) {
    		Object[] b = new Object[a.length * 2];
    		for(int i = 0; i < a.length; i++) {
    			b[i] = a[i]; 
    		}
    		a = b;
    	}
    	
    	//Shift elements to the right
    	for(int i = n; i > index; i --) {
    		a[i] = a [i - 1];
    	}
    	
    	a[index] = element;
    	n++;
    }
    @Override
    public T get(int index) {
    	if(index < 0 || index >= n) {
    		throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
    	}
    	return (T) a[index];
    }

    @Override
    public T remove(int index) {
        if(index < 0 || index >= n) {
    		throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + n);
        }
        T val = (T) a[index];
        //Shift elements left to fill the gap
        for (int i = index + 1; i < n; i++) {
            a[i - 1] = a[i];
        }
        n--;
        a[n] = null;
        return val;
    }

    @Override
    public int size() {
        return n;
    }
}
