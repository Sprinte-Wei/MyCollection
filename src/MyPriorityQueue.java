package mycollection;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;


public class MyPriorityQueue<E> {

    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    private int size = 0;

    private final Comparator<E> comparator;

    private Object[] queue;

    public MyPriorityQueue(){
        this(DEFAULT_INITIAL_CAPACITY, null);
    }
    public MyPriorityQueue(int size) {
        this(size, null);
    }
    public MyPriorityQueue(Comparator<E> comparator) {
        this(DEFAULT_INITIAL_CAPACITY, comparator);
    }
    /**
     * 无comparator时，自动按照从小到大排列建立最小堆
     * 无size时，自动按照DEFAULT_INITIAL_CAPACITY申请空间
     * @param size
     * @param comparator
     */
    public MyPriorityQueue(int size, Comparator<E> comparator) {
        if(size < 1){
            throw new RuntimeException("Wrong initial size of priority queue: " + size);
        }
        this.queue = new Object[size];
        this.comparator = comparator;
    }

    public boolean offer(E element) {
        if(element == null){
            throw new RuntimeException();
        }
        ensureCapacity();
        queue[size] = element;
        siftUp(size++, element);
        return true;
    }
    private void ensureCapacity() {
        int oldCapacity = queue.length;
        if(size == oldCapacity){
            int newCapacity = oldCapacity + ((oldCapacity < 64) ?
                    (oldCapacity + 2) :
                    (oldCapacity >> 1));
            queue = Arrays.copyOf(queue, newCapacity);
        }
    }

    public E peek(){
        if(size == 0){
            throw  new NullPointerException();
        }
        return (E)queue[0];
    }

    public E poll(){
        if(size == 0){
            throw  new NullPointerException();
        }
        E result = (E)queue[0];
        if(size != 0){
            siftDown();
        }
        return result;
    }

    /**
     * 添加新元素时的向上移动
     * @param k
     * @param x
     */
    private void siftUp(int k, E x) {
        if(comparator != null){
            siftUpUsingComparator(k, x);
        }
        else{
            siftUpComparable(k, x);
        }
    }
    private void siftUpComparable(int k, E x) {

    }
    private void siftUpUsingComparator(int k, E x) {
        while (k > 0){
            int parent = (k - 1) >> 1;
            Object e = queue[parent];
            if(comparator.compare(x, (E)e) >= 0){
                break;
            }
            queue[k] = e;
            k = parent;
        }
        queue[k] = x;
    }


    private void siftDown() {
        if(comparator != null){
            siftDownUsingComparator();
        }
        else{
            siftDownComparable();
        }
    }
    private void siftDownComparable() {

    }
    private void siftDownUsingComparator() {
        queue[0] = queue[--size];
        queue[size] = null;
        int child = 1;
        while(child < size){
            child = (child + 1 < size) ? (comparator.compare((E)queue[child], (E)queue[child + 1]) >= 0 ? child + 1 : child) : child;
            if(comparator.compare((E)queue[(child-1)/2], (E)queue[child]) <= 0){
                break;
            }
            E temp = (E)queue[child];
            queue[child] = queue[(child-1)/2];
            queue[(child-1)/2] = temp;
            child = (child << 1) + 1;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        for(int i = 0; i < size; i++){
            stringBuilder.append(queue[i].toString());
            stringBuilder.append(", ");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        stringBuilder.setCharAt(stringBuilder.length() - 1, ']');
        return stringBuilder.toString();
    }

    private static class Student{
        int money;
        String name;
        Student(int m, String n){
            money = m;
            name = n;
        }
        @Override
        public String toString() {
            return money + ":" + name;
        }
    }

    //小跟堆的比较器
    private static class MoneyComparator implements Comparator<Student>{
        @Override
        public int compare(Student o1, Student o2) {
            return o1.money - o2.money;
        }
    }

    //大根堆的比较器
    private static class NameComparator implements Comparator<Student>{
        @Override
        public int compare(Student o1, Student o2) {
            return o2.name.compareTo(o1.name);
        }
    }

    public static void main(String[] args) {
        Student[] students = new Student[11];
        MyPriorityQueue<Student> moneyPriorityQueue = new MyPriorityQueue<>(11, new MoneyComparator());
        MyPriorityQueue<Student> namePriorityQueue = new MyPriorityQueue<>(11, new NameComparator());
        for(int i = 0; i < 11; i++){
            students[i] = generateRandomStudent();
            moneyPriorityQueue.offer(students[i]);
            namePriorityQueue.offer(students[i]);
        }
        System.out.println(Arrays.toString(students));
        System.out.println(moneyPriorityQueue);
        System.out.println(namePriorityQueue);
        for(int i = 0; i < 11; i++){
            System.out.println(moneyPriorityQueue.poll() + " " + namePriorityQueue.poll());
        }
    }


    private static Student generateRandomStudent(){
        int money = new Random().nextInt(10);
        StringBuilder name = new StringBuilder(3);
        for(int i = 0; i < 3; i++){
            name.append((char)('a' + new Random().nextInt(26)));
        }
        Student s = new Student(money, name.toString());
        return s;
    }

}
