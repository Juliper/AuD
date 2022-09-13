package h06;

public class Hash2IndexFct<T> implements Fct2Int<T> {
	/**
	 * Table size used in calculation.
	 */
	private int tableSize;

	/**
	 * Offset used in calculation.
	 */
	private final int offset;

    /**
     * Creates a new hash function h(x) = (x + offset) mod tableSize.
     *
     * @param initTableSize Initial table size used in calculation.
     * @param offset        Offset used in calculation.
     */
    public Hash2IndexFct(int initTableSize, int offset) {
        this.tableSize = initTableSize;
        this.offset = offset;
    }

    /**
     * Calculates the hash value of parameter "key".
     *
     * @param key The key from which to calculate the hash value.
     * @return key.hashCode() modulo tableSize
     */
    @Override
    public int apply(T key) {
        long a = (long) Math.abs(key.hashCode()) + this.offset;


        return Math.floorMod(a, this.tableSize);
    }

    /**
     * Returns the current table size.
     *
     * @return Current table size.
     */
    @Override
    public int getTableSize() {
		return tableSize;
    }

    /**
     * Sets the current table size.
     *
     * @param tableSize New table size.
     */
    @Override
    public void setTableSize(int tableSize) {
		this.tableSize = tableSize;
    }
}
