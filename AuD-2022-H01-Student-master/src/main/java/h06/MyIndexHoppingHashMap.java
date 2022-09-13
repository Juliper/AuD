package h06;

import org.jetbrains.annotations.Nullable;

public class MyIndexHoppingHashMap<K, V> implements MyMap<K, V> {
    private final double resizeThreshold;
    private final double resizeFactor;
    private K[] theKeys;
    private V[] theValues;
    private boolean[] occupiedSinceLastRehash;
    private int occupiedCount = 0;
    private final BinaryFct2Int<K> hashFunction;

    /**
     * Create a new index hopping hash map.
     *
     * @param initialSize     The initial size of the hashmap.
     * @param resizeFactor    The resize factor which determines the new size after the resize threshold is reached.
     * @param resizeThreshold The threshold after which the hash table is resized.
     * @param hashFunction    The used hash function.
     */
    @SuppressWarnings("unchecked")
    public MyIndexHoppingHashMap(int initialSize, double resizeFactor, double resizeThreshold, BinaryFct2Int<K> hashFunction) {
        this.theKeys = (K[]) new Object[initialSize];
        this.theValues = (V[]) new Object[initialSize];
        this.occupiedSinceLastRehash = new boolean[initialSize];

        this.resizeFactor = resizeFactor;
        this.resizeThreshold = resizeThreshold;
        this.hashFunction = hashFunction;
        this.hashFunction.setTableSize(initialSize);
    }

    @Override
    public boolean containsKey(K key) {


        for(int offset = 0; occupiedSinceLastRehash[hashFunction.apply(key, offset)]; offset++)    {

            if (theKeys[hashFunction.apply(key, offset)].equals(key))    return true;

        }

        return false;
    }

    @Override
    public @Nullable V getValue(K key) {

        for(int offset = 0; occupiedSinceLastRehash[hashFunction.apply(key, offset)]; offset++)    {

            if (theKeys[hashFunction.apply(key, offset)].equals(key))    return theValues[hashFunction.apply(key, offset)];

        }

        return null;
    }

    @Override
    public @Nullable V put(K key, V value) {
        double threshold = occupiedSinceLastRehash.length * resizeThreshold;


        //rehashing
        if (containsKey(key)) {

            if (threshold < occupiedCount)  {

                rehash();

            }

        } else {
            boolean found = false;


            for (int offset = 0; occupiedSinceLastRehash[hashFunction.apply(key, offset)]; offset++) {


                if (theKeys[hashFunction.apply(key, offset)] == null) {

                    if (occupiedSinceLastRehash[hashFunction.apply(key, offset)]) {
                        if (threshold < occupiedCount) rehash();
                    } else if (threshold < occupiedCount + 1) rehash();

                    found = true;
                    break;
                }

            }



            if (!found)  {

                if (threshold < occupiedCount + 1) rehash();

            }

        }






        //insertion

        if (containsKey(key))   {


            for(int offset = 0; occupiedSinceLastRehash[hashFunction.apply(key, offset)]; offset++)    {

                if (theKeys[hashFunction.apply(key, offset)].equals(key))    {

                    V tmp = theValues[hashFunction.apply(key, offset)];

                    theValues[hashFunction.apply(key, offset)] = value;
                    return tmp;

                }

            }

        }else {

            int lastOffset = -1;

            for(int offset = 0; occupiedSinceLastRehash[hashFunction.apply(key, offset)]; offset++)    {

                if (theKeys[hashFunction.apply(key, offset)] == null)    {

                    if (!occupiedSinceLastRehash[hashFunction.apply(key, offset)])   {
                        occupiedSinceLastRehash[hashFunction.apply(key, offset)] = true;
                        occupiedCount++;
                    }


                    theKeys[hashFunction.apply(key, offset)] = key;
                    theValues[hashFunction.apply(key, offset)] = value;

                    return null;
                }

                lastOffset = offset;
            }

            if (lastOffset == -1)   lastOffset = 0;
            else lastOffset++;



            occupiedSinceLastRehash[hashFunction.apply(key, lastOffset)] = true;
            occupiedCount++;
            theKeys[hashFunction.apply(key, lastOffset)] = key;
            theValues[hashFunction.apply(key, lastOffset)] = value;

            return null;


        }


        return null;

    }

    @Override
    public @Nullable V remove(K key) {


        for(int offset = 0; occupiedSinceLastRehash[hashFunction.apply(key, offset)]; offset++)    {

            if (theKeys[hashFunction.apply(key, offset)].equals(key))    {

                V tmp = theValues[hashFunction.apply(key, offset)];

                theValues[hashFunction.apply(key, offset)] = null;
                theKeys[hashFunction.apply(key, offset)] = null;

                return tmp;

            }

        }

        return null;

    }

    /***
     * Creates a new bigger hashtable (current size multiplied by resizeFactor)
     * and inserts all elements of the old hashtable into the new one.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
        int newSize = (int) (occupiedSinceLastRehash.length * resizeFactor);

        K[] theKeys = this.theKeys;
        V[] theValues = this.theValues;

        this.occupiedSinceLastRehash = new boolean[newSize];
        this.theKeys = (K[]) new Object[newSize];
        this.theValues = (V[]) new Object[newSize];
        this.hashFunction.setTableSize(newSize);


        for (int i = 0; i < theKeys.length; i++)   {

            if (theKeys[i] != null) put(theKeys[i], theValues[i]);

        }


    }
}
