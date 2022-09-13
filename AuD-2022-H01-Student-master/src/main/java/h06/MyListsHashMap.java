package h06;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedList;

public class MyListsHashMap<K, V> implements MyMap<K, V> {
    private final LinkedList<KeyValuePair<K, V>>[] table;
    private final Fct2Int<K> hashFunction;

    /**
     * Creates a new list hash map.
     *
     * @param hashFunction The used hash function.
     */
    @SuppressWarnings("unchecked")
    public MyListsHashMap(Fct2Int<K> hashFunction) {
        this.table = new LinkedList[hashFunction.getTableSize()];
        for (int i = 0; i < this.table.length; i++) {
            this.table[i] = new LinkedList<>();
        }
        this.hashFunction = hashFunction;
    }

    @Override
    public boolean containsKey(K key) {
        LinkedList<KeyValuePair<K, V>> list = table[hashFunction.apply(key)];


        for (KeyValuePair<K, V> kvKeyValuePair : list) {
            if (kvKeyValuePair.getKey().equals(key)) return true;
        }


        return false;
    }

    @Override
    public @Nullable V getValue(K key) {
        LinkedList<KeyValuePair<K, V>> list = table[hashFunction.apply(key)];


        for (KeyValuePair<K, V> kvKeyValuePair : list) {
            if (kvKeyValuePair.getKey().equals(key)) return kvKeyValuePair.getValue();
        }


        return null;
    }

    @Override
    public @Nullable V put(K key, V value) {

        LinkedList<KeyValuePair<K, V>> list = table[hashFunction.apply(key)];


        for (KeyValuePair<K, V> kvKeyValuePair : list) {
            if (kvKeyValuePair.getKey().equals(key)) {
                V tmp = kvKeyValuePair.getValue();

                kvKeyValuePair.setValue(value);

                return tmp;
            }
        }



        list.addFirst(new KeyValuePair<>(key, value));


        return null;
    }

    @Override
    public @Nullable V remove(K key) {

        LinkedList<KeyValuePair<K, V>> list = table[hashFunction.apply(key)];


        for (KeyValuePair<K, V> kvKeyValuePair : list) {
            if (kvKeyValuePair.getKey().equals(key)) {
                V tmp = kvKeyValuePair.getValue();

                list.remove(kvKeyValuePair);

                return tmp;
            }
        }


        return null;
    }
}
