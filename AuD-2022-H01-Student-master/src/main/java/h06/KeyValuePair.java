package h06;

public class KeyValuePair<K, V> {
    /**
     * The key associated with "value".
     */
    private final K key;

    /**
     * The value associated with "key".
     */
    private V value;

    /**
     * Constructs a key value pair out of parameter "key" and "value".
     *
     * @param key   The key of the new key value pair.
     * @param value The value of the new key value pair.
     */
    public KeyValuePair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key of the key value pair.
     *
     * @return The key.
     */
    public K getKey() {
        return this.key;
    }

    /**
     * Returns the value of the key value pair.
     *
     * @return The value.
     */
    public V getValue() {
        return this.value;
    }

    /**
     * Sets the value of the key value pair.
     *
     * @param newValue The new value.
     */
    public void setValue(V newValue) {
        this.value = newValue;
    }
}
