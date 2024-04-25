package Assignment3B;

import java.util.Collections;
import java.util.*;

public class PranjalHashSet<E> implements Collection<E> {
  // Define the default hash table size. Must be a power of 2
  private final static int DEFAULT_INITIAL_CAPACITY = 4;
  
  // Define the maximum hash table size. 1 << 30 is same as 2^30
  private final static int MAXIMUM_CAPACITY = 1 << 30; 
  
  // Current hash table capacity. Capacity is a power of 2
  private int capacity;
  
  // Define default load factor
  private final static float DEFAULT_MAX_LOAD_FACTOR = 0.75f; 

  // Specify a load factor threshold used in the hash table
  private float loadFactorThreshold; 
  
  // The number of elements in the set
  private int size = 0; 
  
  // Hash table is an array with each cell that is a linked list
  private LinkedList<E>[] table;

  /** Construct a set with the default capacity and load factor */
  public PranjalHashSet() {  
    this(DEFAULT_INITIAL_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);    
  }
  
  /** Construct a set with the specified initial capacity and 
   * default load factor */
  public PranjalHashSet(int initialCapacity) { 
    this(initialCapacity, DEFAULT_MAX_LOAD_FACTOR);    
  }
  
  /** Construct a set with the specified initial capacity 
   * and load factor */
  public PranjalHashSet(int initialCapacity, float loadFactorThreshold) { 
    if (initialCapacity > MAXIMUM_CAPACITY)
      this.capacity = MAXIMUM_CAPACITY;
    else
      this.capacity = trimToPowerOf2(initialCapacity);
    
    this.loadFactorThreshold = loadFactorThreshold;    
    table = new LinkedList[capacity];
  }
  
  @Override /** Remove all elements from this set */ 
  public void clear() {
    size = 0;
    removeElements();
  }

  @Override /** Return true if the element is in the set */
  public boolean contains(Object e) {
    int bucketIndex = hash(e.hashCode());
    if (table[bucketIndex] != null) {
      LinkedList<E> bucket = table[bucketIndex]; 
      return bucket.contains(e);
    }
    
    return false;
  }
  
  @Override /** Add an element to the set */
  public boolean add(E e) {
    if (contains(e)) // Duplicate element not stored
      return false;
    
    if (size + 1 > capacity * loadFactorThreshold) {
      if (capacity == MAXIMUM_CAPACITY)
        throw new RuntimeException("Exceeding maximum capacity");
    
      rehash();
    }
    
    int bucketIndex = hash(e.hashCode());
    
    // Create a linked list for the bucket if it is not created
    if (table[bucketIndex] == null) {
      table[bucketIndex] = new LinkedList<E>();
    }

    // Add e to hashTable[index]
    table[bucketIndex].add(e);

    size++; // Increase size
    
    return true;
  }

  @Override /** Remove the element from the set */
  public boolean remove(Object e) {
    if (!contains(e))
      return false;
    
    int bucketIndex = hash(e.hashCode());
    
    // Create a linked list for the bucket if it is not created
    if (table[bucketIndex] != null) {
      LinkedList<E> bucket = table[bucketIndex]; 
      bucket.remove(e);
    }

    size--; // Decrease size
    
    return true;
  }

  @Override /** Return true if the set contains no elements */
  public boolean isEmpty() {
    return size == 0;
  }

  @Override /** Return the number of elements in the set */
  public int size() {
    return size;
  }

  @Override /** Return an iterator for the elements in this set */
  public java.util.Iterator<E> iterator() {
    return new PranjalHashSetIterator(this);
  }
  
  /** Inner class for iterator */
  private class PranjalHashSetIterator implements java.util.Iterator<E> {
    // Store the elements in a list
    private java.util.ArrayList<E> list;
    private int current = 0; // Point to the current element in list
    private PranjalHashSet<E> set;
    
    /** Create a list from the set */
    public PranjalHashSetIterator(PranjalHashSet<E> set) {
      this.set = set;
      list = setToList();
    }

    @Override /** Next element for traversing? */
    public boolean hasNext() {
      return current < list.size();
    }

    @Override /** Get current element and move cursor to the next */
    public E next() {
      return list.get(current++);
    }

    @Override /** Remove the element returned by the last next() */
    public void remove() {
      // Left as an exercise
      // You need to remove the element from the set
      // You also need to remove it from the list
    }
  }  
 
  
  /** Hash function */
  private int hash(int hashCode) {
    return hashCode & (capacity - 1);
  }

  /** Return a power of 2 for initialCapacity */
  private int trimToPowerOf2(int initialCapacity) {
    int capacity = 1;
    while (capacity < initialCapacity) {
      capacity <<= 1;
    }
    
    return capacity;
  }
  
  /** Remove all e from each bucket */
  private void removeElements() {
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null) {
        table[i].clear();
      }
    }
  }
  
  /** Rehash the set */
  private void rehash() {
    java.util.ArrayList<E> list = setToList(); // Copy to a list
    capacity <<= 1; // Double capacity      
    table = new LinkedList[capacity]; // Create a new hash table
    size = 0; // Reset size 
    
    for (E element: list) {
      add(element); // Add from the old table to the new table
    }
  }

  /** Copy elements in the hash set to an array list */
  private java.util.ArrayList<E> setToList() {
    java.util.ArrayList<E> list = new java.util.ArrayList<>();
    
    for (int i = 0; i < capacity; i++) {
      if (table[i] != null) {
        for (E e: table[i]) {
          list.add(e);
        }
      }
    }  
    
    return list;
  }

  @Override
  public String toString() {
    java.util.ArrayList<E> list = setToList();
    StringBuilder builder = new StringBuilder("[");
    
    // Add the elements except the last one to the string builder
    for (int i = 0; i < list.size() - 1; i++) {
      builder.append(list.get(i) + ", ");
    }
    
    // Add the last element in the list to the string builder
    if (list.size() == 0)
      builder.append("]");
    else
      builder.append(list.get(list.size() - 1) + "]");
    
    return builder.toString();
  }

  @Override
  public boolean addAll(Collection<? extends E> c) {
      boolean modified = false;
      for (E e : c) {
          if (add(e)) {
              modified = true;
          }
      }
      return modified;
  }


  @Override
  public boolean containsAll(Collection<?> c) {
      for (Object e : c) {
          if (!contains(e)) {
              return false;
          }
      }
      return true;
  }


  @Override
  public boolean removeAll(Collection<?> c) {
      boolean modified = false;
      for (Object e : c) {
          if (remove(e)) {
              modified = true;
          }
      }
      return modified;
  }


  @Override
  public boolean retainAll(Collection<?> c) {
      boolean modified = false;
      Iterator<E> it = iterator();
      while (it.hasNext()) {
          if (!c.contains(it.next())) {
              it.remove();
              modified = true;
          }
      }
      return modified;
  }


  @Override
  public Object[] toArray() {
      ArrayList<E> list = setToList();
      return list.toArray();
  }


  @Override
  public <T> T[] toArray(T[] a) {
      ArrayList<E> list = setToList();
      if (a.length < size) {
          // If array is too small, allocate the new one with the same runtime type as the specified array
          return (T[]) java.lang.reflect.Array.newInstance(a.getClass().getComponentType(), size);
      }
      System.arraycopy(list.toArray(), 0, a, 0, size);
      if (a.length > size) {
          a[size] = null; // Null-terminate the array, if there's extra space
      }
      return a;
  }

}
