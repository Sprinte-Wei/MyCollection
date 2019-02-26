package mycollection;

/**
 * 依据jdk的一个简单的LinkedList双向链表的实现
 *
 * @author CGWEI
 *
 * @param <E>
 */

public class MyLinkedList<E> {

    private static class Node<E>{
        E element;
        Node previous;
        Node next;
        Node(E element, Node previous, Node next) {
            this.element = element;
            this.previous = previous;
            this.next = next;
        }
    }
    private Node first;
    private Node last;
    private int size;

    public int size(){
        return size;
    }
    public void add(E element){
        Node node = new Node(element, last, null);
        if(first == null){
            first = node;
        }
        else{
            last.next = node;
        }
        last = node;
        size++;
    }
    public E get(int index){
        return (E)getNode(index).element;
    }
    public void set(E element, int index){
        checkRange(index);
        Node temp = getNode(index);
        temp.element = element;
    }
    public void add(E element, int index){
        checkRange(index);
        Node temp = getNode(index);
        Node newNode = new Node(element, temp.previous, temp);
        if(temp == first){
            first = newNode;
        }
        else{
            temp.previous.next = newNode;
        }
        temp.previous = newNode;
        size++;
    }
    public void remove(int index){
        checkRange(index);
        Node temp = getNode(index);
        Node up = temp.previous;
        Node down = temp.next;
        if(up != null){
            up.next = down;
        }
        if(down != null){
            down.previous = up;
        }
        if(index==0){
            first = down;
        }
        if(index == size - 1){
            last = up;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        Node temp = first;
        while(temp != null){
            stringBuilder.append(temp.element);
            stringBuilder.append(',');
            temp = temp.next;
        }
        int l = stringBuilder.length();
        if(l != 1){
            stringBuilder.setCharAt(stringBuilder.length()-1, ']');
        }
        else{
            stringBuilder.append(']');
        }
        return stringBuilder.toString();
    }


    private Node getNode(int index){
        checkRange(index);
        Node node;
        if(index < (size >> 1)){
            node = first;
            for(int i = 0; i < index; i++){
                node = node.next;
            }
        }
        else {
            node = last;
            for(int i = size-index-1; i > 0; i--){
                node = node.previous;
            }
        }
        return node;
    }
    private void checkRange(int index){
        if(index < 0 || index >= size){
            throw new RuntimeException("索引值不合法："+index);
        }
    }
}