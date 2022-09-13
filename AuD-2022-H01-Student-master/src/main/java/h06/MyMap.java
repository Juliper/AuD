package h06;

import org.jetbrains.annotations.Nullable;

public interface MyMap<K, V> {
    /**
     * Checks if key is contained in map.
     *
     * @param key The key to search.
     * @return True if the key is found, false otherwise.
     */
    boolean containsKey(K key);

    /**
     * Returns the value for the corresponding key.
     *
     * @param key The key to identify the matching key-value pair.
     * @return The value corresponding to the parameter "key".
     */
    @Nullable V getValue(K key);

    /**
     * Inserts the value for the corresponding key and returns the old value.
     * Creates new key-value pair if key is nonexistent and returns null.
     *
     * @param key   The key used to identify the key-value pair to be edited / inserted.
     * @param value The value with which the existing value is to be overwritten / or
     *              which is to be inserted.
     * @return The old value if the key-value pair is already existing, null otherwise.
     */
    @Nullable V put(K key, V value);

    /**
     * Removes the key-value pair associated with the key.
     * Return the corresponding value.
     *
     * @param key The key used to identify the key-value pair to be removed.
     * @return The value corresponding to the parameter "key".
     */
    @Nullable V remove(K key);
}
