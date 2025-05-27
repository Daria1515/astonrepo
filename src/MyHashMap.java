public class MyHashMap<K, V> {

    private static class Node<K, V> {
        final K key;
        V value;
        Node<K, V> next;

        Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }

    private static final int DEFAULT_CAPACITY = 16;
    private static final float LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;
    private int size;
    private int threshold;

    public MyHashMap() {
        table = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
        threshold = (int) (DEFAULT_CAPACITY * LOAD_FACTOR);
    }

    private int index(K key, int length) {
        return (key == null) ? 0 : (key.hashCode() & (length - 1));
    }

    private int index(K key) {
        return index(key, table.length);
    }

    public void put(K key, V value) {
        if (size >= threshold) {
            resize();
        }

        int index = index(key);
        Node<K, V> current = table[index];

        if (current == null) {
            table[index] = new Node<>(key, value, null);
            size++;
            return;
        }

        Node<K, V> prev = null;
        while (current != null) {
            if ((key == null && current.key == null) || (key != null && key.equals(current.key))) {
                current.value = value;
                return;
            }
            prev = current;
            current = current.next;
        }

        prev.next = new Node<>(key, value, null);
        size++;
    }

    private void resize() {
        int newCapacity = table.length * 2;
        Node<K, V>[] newTable = (Node<K, V>[]) new Node[newCapacity];

        for (Node<K, V> headNode : table) {
            Node<K, V> current = headNode;
            while (current != null) {
                Node<K, V> next = current.next;

                int newIndex = index(current.key, newCapacity);
                current.next = newTable[newIndex];
                newTable[newIndex] = current;

                current = next;
            }
        }

        table = newTable;
        threshold = (int) (newCapacity * LOAD_FACTOR);
    }

    public V get(K key) {
        int index = index(key);
        Node<K, V> current = table[index];

        while (current != null) {
            if ((key == null && current.key == null) || (key != null && key.equals(current.key))) {
                return current.value;
            }
            current = current.next;
        }

        return null;
    }

    public void remove(K key) {
        int index = index(key);
        Node<K, V> current = table[index];
        Node<K, V> prev = null;

        while (current != null) {
            if ((key == null && current.key == null) || (key != null && key.equals(current.key))) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return;
            }

            prev = current;
            current = current.next;
        }
    }

    public int size() {
        return size;
    }
}
