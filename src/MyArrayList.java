package mycollection;

import java.util.Arrays;

/**
 * 依据jdk的一个简单的ArrayList
 * 默认长度为10，每次扩容为原来的1.5倍
 *
 * @author CGWEI
 *
 * @param <E>
 */

public class MyArrayList<E> {
    Object[] elements;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    public MyArrayList(){
        elements = new Object[DEFAULT_CAPACITY];
    }
    public MyArrayList(int capacity){
        if(capacity<0){
            throw new RuntimeException("长度设置异常："+capacity);
        }
        else if(capacity == 0){
            elements = new Object[DEFAULT_CAPACITY];
        }
        else {
            elements = new Object[capacity];
        }
    }
    private void checkRange(int index){
        if(index < 0 || index >= size){
            throw new RuntimeException("索引越界："+index);
        }
    }
    private void ensureCapacity(){
        Object[] newArray = new Object[elements.length + (elements.length >> 1)];
        System.arraycopy(elements,0,newArray,0,elements.length);
        elements = newArray;
    }

    public int size(){
        return size;
    }
    public  boolean isEmpty(){
        return size==0;
    }
    public void add(E element){
        if(size+1 > elements.length){
            ensureCapacity();
        }
        elements[size++] = element;
    }
    public E get(int index){
        checkRange(index);
        return (E)elements[index];
    }
    public void set(E element, int index){
        checkRange(index);
        elements[index] = element;
    }
    public void remove(int index){
        checkRange(index);
        int removeCount = elements.length - index - 1;
        System.arraycopy(elements, index+1, elements, index, removeCount);
        elements[--size] = null;
    }
    public void remove(E element){
        for(int i = 0; i < elements.length; i++){
            if(elements[i].equals(element)){
                remove(i);
                break;
            }
        }
    }
    @Override
    public String toString() {
        return Arrays.toString(elements);
    }
}

