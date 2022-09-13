package h06;

public interface Fct2Int<T> {
    /**
     * Transforms the parameter "x" to an integer value.
     *
     * @param x The value of type T used in the transformation process.
     * @return Integer value which is calculated using parameter "x".
     */
    int apply(T x);

    /**
     * Returns the current table size.
     *
     * @return Current table size.
     */
    int getTableSize();

    /**
     * Sets the current table size.
     *
     * @param tableSize New table size.
     */
    void setTableSize(int tableSize);
}
