package h06;

public interface BinaryFct2Int<T> {
    /**
     * Transforms the parameters "x" and "i" to an integer value.
     *
     * @param x The value of type T used in the transformation process.
     * @param i The value of type integer used in the transformation process.
     * @return Integer value which is calculated using parameters "x" and "i".
     */
    int apply(T x, int i);

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
