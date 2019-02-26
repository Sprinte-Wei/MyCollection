package mycollection;
/**
 * 依据jdk的一个简单的HashMap实现，实现了简单的功能
 * 位桶数组默认长度为16，达到0.75倍时扩容，长度均为2的整数次幂
 *
 * @author CGWEI
 *
 * @param <K> key
 * @param <V> value
 */
public class MyHashMap<K, V> {
    private class Node<K, V>{
        K key;
        V value;
        int hash;
        Node next;
        Node(int hash, K key, V value){
            this.hash = hash;
            this.key = key;
            this.value = value;
        }
        @Override
        public String toString() {
            StringBuilder b = new StringBuilder(key.toString());
            b.append(':');
            b.append(value.toString());
            return b.toString();
        }
    }
    private Node[] table;
    private int size;
    private static final int DEFAULT_SIZE = 1 << 4;
    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private static int hash(Object key, int length){
        if(key == null){
            throw new RuntimeException("key null");
        }
        return key.hashCode() & (length - 1);
    }
    private void ensureCapacity(){
        int actual = 0;
        for(int i = 0; i < table.length; i++){
            if(table[i] != null){
                actual++;
            }
        }
        int factor = (table.length >> 1) + (table.length >> 2);
        if(actual > factor){
            Node[] newTable = new Node[table.length << 1];
            System.arraycopy(table, 0, newTable, 0, table.length);
            table = newTable;
        }
    }

    public MyHashMap(){
        table = new Node[DEFAULT_SIZE];
    }
    public int size(){
        return size;
    }
    public V get(K key){
        int hash = hash(key, table.length);
        Node<K, V> temp = table[hash];
        while(temp != null){
            if(temp.key.equals(key)){
                return temp.value;
            }
            temp = temp.next;
        }
        return null;
    }
    public void put(K key, V value){
        int hash = hash(key, table.length);
        Node<K, V> newNode = new Node<>(hash, key, value);
        if(table[hash] == null){
            ensureCapacity();
            table[hash] = newNode;
            size++;
        }
        else{
            Node temp = table[hash];
            while(temp.next != null){
                if(temp.key.equals(key)){
                    temp.value = value;
                    break;
                }
                else{
                    temp = temp.next;
                }
            }
            if(!temp.key.equals(key)){
                temp.next = newNode;
                size++;
            }
            else{
                temp.value = value;
            }
        }
    }
    public void remove(K key){
        int hash = hash(key, table.length);
        Node temp = table[hash];
        if(table[hash] == null){
            throw new RuntimeException("No key:"+key);
        }
        if(!table[hash].key.equals(key)){
            while (temp.next != null){
                if(temp.next.key.equals(key)){
                    temp.next = temp.next.next;
                    size--;
                    return;
                }
                temp = temp.next;
            }
        }
        else{
            table[hash] = table[hash].next;
            size--;
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder(5*size);
        for(int i = 0; i < table.length; i++){
            s.append(i + 1);
            s.append(' ');
            s.append('[');
            Node temp = table[i];
            while(temp != null){
                s.append(temp.toString());
                s.append(", ");
                temp = temp.next;
            }
            s.append(']');
            s.append('\n');
        }
        return s.toString();
    }

    public static void main(String[] args) {
        MyHashMap<Integer, String> m = new MyHashMap<>();
//        int count = 26;
//        for(int i = 0; i < count; i++){
//            m.put(i, i+"");
//        }
        m.put(1,"1");
        m.put(17,"17");
        m.put(2,"2");
        m.put(1,"3");
        m.remove(1);
        m.remove(2);
        System.out.println(m.get(17));
        System.out.println(m.size());
        System.out.println(m);
    }
}
