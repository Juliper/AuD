package h02;

import java.util.NoSuchElementException;

/**
 * Represents an iterator over a ListOfArrays object.
 *
 * @param <T> The generic type of the list that this iterator iterates on.
 */
public class ListOfArraysIterator<T> {
    private ListOfArraysItem<T> head;
    private int counter;

    /**
     * A constructor to construct a ListOfArraysIterator object.
     *
     * @param head The head of the list to iterate over.
     */
    public ListOfArraysIterator(ListOfArraysItem<T> head) {
        this.head = head;
        this.counter = 0;
    }

    /**
     * Returns whether there is another element to be iterated on.
     *
     * @return True if there is another element.
     */
    public boolean hasNext() {
        //guard if current list is empty
        if (head == null || head.array == null) return false;


        if (counter >= head.currentNumber) {

            return head.next != null && head.next.array != null;

        } else return true;
    }

    /**
     * Returns the next element of this iterator and moves the iterator one element forward.
     *
     * @return The next element.
     * @throws NoSuchElementException If there is no next element.
     */
    public T next() throws NoSuchElementException {

        if (hasNext()) {

            if (counter == head.currentNumber) {
                head = head.next;
                counter = 0;
            }

            return head.array[counter++];
        } else throw new NoSuchElementException();

    }
}

