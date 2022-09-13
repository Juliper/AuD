package h02;

/**
 * An item of a list analogous to ListItem.
 * Contains an array of the type T.
 * Attribute next references the next item of this list or null.
 * Attribute currentNumber is the amount of elements currently in array.
 *
 * @param <T> The generic type of this list
 */
class ListOfArraysItem<T> {
    /**
     * The current amount of objects in the array of this item.
     */
    public int currentNumber;

    /**
     * The array of this item.
     */
    public T[] array;

    /**
     * The next item of the list - or null.
     */
    public ListOfArraysItem<T> next;
}

