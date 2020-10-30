package com.itlbv;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class DIYArrayList<T> implements List<T> {

  private static final int DEFAULT_SIZE = 15;

  private Object[] data;
  private int size;

  public DIYArrayList() {
    data = new Object[DEFAULT_SIZE];
    size = 0;
  }

  public DIYArrayList(int initialSize) {
    data = new Object[initialSize];
    size = initialSize;
  }

  @Override
  public boolean add(T t) {
    if (size == data.length) {
      growData();
    }
    data[size] = t;
    ++size;
    return true;
  }

  private void growData() {
    data = Arrays.copyOf(data, data.length + 1);
  }

  @Override
  public int size() {
    return size;
  }

  @Override
  @SuppressWarnings("unchecked")
  public T get(int i) {
    if (i >= data.length) throw new IndexOutOfBoundsException();
    return (T) data[i];
  }

  @Override
  public T set(int i, T t) {
    if (i >= size) throw new IndexOutOfBoundsException();
    T oldValue = (T) data[i];
    data[i] = t;
    return oldValue;
  }

  @Override
  public Object[] toArray() {
    return data.clone();
  }

  @Override
  public ListIterator<T> listIterator() {
    return new DiyIterator();
  }

  @Override
  public Iterator<T> iterator() {
    return listIterator();
  }

  @Override
  public ListIterator<T> listIterator(int i) {
    return new DiyIterator();
  }

  @Override
  public boolean isEmpty() {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean contains(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <T1> T1[] toArray(T1[] t1s) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean remove(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean containsAll(Collection<?> collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(Collection<? extends T> collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean addAll(int i, Collection<? extends T> collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean removeAll(Collection<?> collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public boolean retainAll(Collection<?> collection) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void clear() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void add(int i, T t) {
    throw new UnsupportedOperationException();
  }

  @Override
  public T remove(int i) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int indexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public int lastIndexOf(Object o) {
    throw new UnsupportedOperationException();
  }

  @Override
  public List<T> subList(int i, int i1) {
    throw new UnsupportedOperationException();
  }

  private class DiyIterator implements ListIterator<T> {

    private int cursor;
    private int lastReturned;

    @Override
    @SuppressWarnings("unchecked")
    public T next() {
      int i = cursor;
      if (i >= size) throw new NoSuchElementException(Integer.toString(cursor));
      cursor++;
      lastReturned = i;
      return (T) data[i];
    }

    @Override
    public void set(T e) {
      DIYArrayList.this.set(lastReturned, e);
    }

    @Override
    public boolean hasNext() {
      return cursor < size;
    }

    /*
     ********** Only unsupported operations down from here *********************************
     */

    @Override
    public boolean hasPrevious() {
      throw new UnsupportedOperationException();
    }

    @Override
    public T previous() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int nextIndex() {
      throw new UnsupportedOperationException();
    }

    @Override
    public int previousIndex() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void remove() {
      throw new UnsupportedOperationException();
    }

    @Override
    public void add(T e) {
      throw new UnsupportedOperationException();
    }
  }
}
