package h06;

public class DoubleHashing<T> implements BinaryFct2Int<T> {
    /**
     * Hash function 1 used in object internal operations. Set in the constructor.
     */
    private final Hash2IndexFct<T> fct1;

    /**
     * Hash function 2 used in object internal operations. Set in the constructor.
     */
    private final Hash2IndexFct<T> fct2;

    /**
     * Creates an object of the DoubleHashingTableIndexFct class.
     * The object uses the hash functions specified in the "internalHashFunction0" and "internalHashFunction1" parameters.
     * Therefore, the hash functions are stored in the object attributes of the same name for further use.
     *
     * @param fct1 The first hash function to be used in upcoming tasks.
     * @param fct2 The second hash function to be used in upcoming tasks.
     */
    public DoubleHashing(Hash2IndexFct<T> fct1, Hash2IndexFct<T> fct2) {
        this.fct1 = fct1;
        this.fct2 = fct2;
    }

    /**
     * Calculates the hash value of parameter "key" using "internalHashFunction0"
     * and "internalHashFunction1" as well as parameter "factor".
     *
     * @param key    The key from which to calculate the hash value.
     * @param factor The factor that is multiplied by the second hash function result.
     * @return ModuloUtil.addModulo(hash0 ( key), offset * hash1(key), max(tableSize, offset * hash1(key)) % tableSize
     */
    @Override
    public int apply(T key, int factor) {
        int a = this.fct1.apply(key);
        long b = this.fct2.apply(key);

        if (Math.floorMod(b, 2) == 0) b++;



        return Math.floorMod((long) a + (factor * b), getTableSize());
    }

    /**
     * Returns the current table size.
     *
     * @return Current table size.
     */
    @Override
    public int getTableSize() {
        return fct1.getTableSize();
    }

    /**
     * Sets the current table size for both internal hash functions.
     *
     * @param tableSize New table size.
     */
    @Override
    public void setTableSize(int tableSize) {
        fct1.setTableSize(tableSize);
        fct2.setTableSize(tableSize);
    }
}
