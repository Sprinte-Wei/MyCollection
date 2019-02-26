package mycollection;

import java.util.HashMap;

/**
 * 依据jdk的一个简单的HashSet
 * 底层实现依靠一个HashmMap
 *
 * @author CGWEI
 *
 * @param <K>
 */
public class MyHashSet<K> {
    private HashMap<K, Object> map;
    private static final Object PRESENT = new Object();
    public MyHashSet(){
        map = new HashMap<>();
    }
    public int size(){
        return map.size();
    }
    public void add(K key){
        map.put(key, PRESENT);
    }

}
