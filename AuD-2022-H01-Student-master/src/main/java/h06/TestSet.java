package h06;

public class TestSet<W> {
    private final MyMap<W, W> hashTable;
    private final W[] testData;

    /**
     * Constructs a test set for given hash table and test data.
     *
     * @param hashTable The hash table to use.
     * @param testData  The test data to use.
     */
    public TestSet(MyMap<W, W> hashTable, W[] testData) {
        this.hashTable = hashTable;
        this.testData = testData;
    }

    /**
     * Returns the hashtable of the test set.
     *
     * @return The hashtable.
     */
    public MyMap<W, W> getHashTable() {
        return hashTable;
    }

    /**
     * Returns the test data set of the test set.
     *
     * @return The test data set.
     */
    public W[] getTestData() {
        return testData;
    }
}
