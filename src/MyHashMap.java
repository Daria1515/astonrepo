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
    private Node<K, V>[] table;
    private int size;

    public MyHashMap() {
        table = (Node<K, V>[]) new Node[DEFAULT_CAPACITY];
    }

    private int index(K key) {
        return (key == null) ? 0 : (key.hashCode() & (table.length - 1));
    }

    public void put(K key, V value) {
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
                    // удаляем первый узел в списке
                    table[index] = current.next;
                } else {
                    // удаляем не первый узел
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
