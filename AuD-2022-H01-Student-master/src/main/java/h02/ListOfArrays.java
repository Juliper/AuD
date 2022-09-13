package h02;

import java.util.Arrays;
import java.util.Iterator;

/**
 * A list of ListOfArraysItem objects.
 * Contains the length of the arrays of this list and a reference to the first and last item (or null as an empty list).
 *
 * @param <T> The generic type of this list
 */
public class ListOfArrays<T> {
    /**
     * The length of the arrays of the items of this list.
     */
    private static final int ARRAY_LENGTH = 256;

    /**
     * The head of this list.
     */
    private ListOfArraysItem<T> head;

    /**
     * The tail of this list.
     */
    private ListOfArraysItem<T> tail;

    /**
     * Constructs a list of ListOfArrayItem objects that represents the elements given in sequence.
     *
     * @param sequence The elements to be added to the list.
     */
    public ListOfArrays(T[] sequence) {
        //when sequence empty
        if (sequence == null || sequence.length == 0)   {
            this.head = null;
            this.tail = null;
        }



        //initialize head and tail
        head = new ListOfArraysItem<>();
        head.array = (T[]) new Object[ARRAY_LENGTH];
        tail = head;


        //fill array and if needed to create new ListItem
        for (T t : sequence) {
            if (t == null) continue;


            if (tail.currentNumber >= ARRAY_LENGTH) {
                tail.next = new ListOfArraysItem<>();
                tail.next.array = (T[]) new Object[ARRAY_LENGTH];
                tail = tail.next;
            }

            tail.array[tail.currentNumber++] = t;
        }
    }

    /**
     * Returns an iterator over this list.
     *
     * @return The iterator of type ListOfArraysIterator.
     */
    public ListOfArraysIterator<T> iterator() {
        return new ListOfArraysIterator<>(this.head);
    }

    /**
     * Inserts a given collection into this list at index i
     * (elements in front of index i will stay and elements at index i and after will be pushed behind the added collection).
     *
     * @param collection The collection to add.
     * @param i          The index at which the collection should be inserted.
     * @throws IndexOutOfBoundsException If the given index is not within the bounds of this list.
     */
    public void insert(T[] collection, int i) throws IndexOutOfBoundsException {
        //when parameter is empty
        if (collection.length == 0) return;
        int maxIndex = 0;

        //checks if head empty
        if (head == null) {

            //when head is empty
            if (i == 0) {
                head = new ListOfArraysItem<>();
                head.array = (T[]) new Object[ARRAY_LENGTH];
            }else throw new IndexOutOfBoundsException();


        } else {

            //exception guard
            if (i < 0) throw new IndexOutOfBoundsException();

            ListOfArraysItem<T> list = head;
            while(list.next != null) {
                maxIndex += list.currentNumber;
                list = list.next;
            }
            maxIndex += list.currentNumber;


            if (maxIndex < i) throw new IndexOutOfBoundsException();
        }






        //determine List and actual array index for the insertion
        ListOfArraysItem<T> list = head;
        int index = i;

        if (maxIndex == i)  {

            list = tail;
            index = tail.currentNumber;

        }else {

            while (index > list.currentNumber) {
                index -= list.currentNumber;
                list = list.next;
            }
            if (index == list.currentNumber)    {
                index -= list.currentNumber;
            }

        }




        //insertion
        //if i == maxIndex
        if (list == tail && index == list.currentNumber) {

            for (T t : collection)  {

                if (list.currentNumber >= ARRAY_LENGTH)  {
                    list.next = new ListOfArraysItem<>();
                    list.next.array = (T[]) new Object[ARRAY_LENGTH];
                    list = list.next;

                    tail = list;
                }

                list.array[list.currentNumber++] = t;
            }

        }
        //if i == 0
        else if(list == head && i == 0) {

            int collectionTail = collection.length;

            for (int p = collectionTail - 1; p > 0 && head.currentNumber != ARRAY_LENGTH; p--)    {
                insertInListArray(collection[p], 0, head);
                collectionTail--;
            }

            //right

            if (collectionTail != 0)    {
                ListOfArraysItem<T> newHead = new ListOfArraysItem<>();
                newHead.array = (T[]) new Object[ARRAY_LENGTH];
                newHead.currentNumber = 0;

                newHead.next = list;
                this.head = newHead;


                ListOfArraysItem<T> currentList = this.head;

                for (int p = 0; p < collectionTail; p++)    {

                    if (currentList.currentNumber == ARRAY_LENGTH)  {
                        ListOfArraysItem<T> newnewList = new ListOfArraysItem<>();
                        newnewList.array = (T[]) new Object[ARRAY_LENGTH];
                        newnewList.currentNumber = 0;

                        newnewList.next = currentList.next;
                        currentList.next = newnewList;
                        currentList = currentList.next;
                    }

                    currentList.array[currentList.currentNumber++] = collection[p];
                }

            }
        }
        //if 0 < i < maxIndex
        else {

            if (index == 0) {
                int collectionTail = collection.length;

                for (int p = collectionTail - 1; p > 0 && list.next.currentNumber != ARRAY_LENGTH; p--)    {
                    insertInListArray(collection[p], 0, list.next);
                    collectionTail--;
                }

                for (int p = 0; p < collectionTail; p++)  {

                    if (list.currentNumber >= ARRAY_LENGTH)  {
                        ListOfArraysItem<T> newList = new ListOfArraysItem<>();
                        newList.array = (T[]) new Object[ARRAY_LENGTH];

                        newList.next = list.next;
                        list.next = newList;
                        list = list.next;
                    }

                    list.array[list.currentNumber++] = collection[p];
                }
            }else {
                int collectionTail = collection.length;

                if (list.next != null)  {

                    for (int p = list.currentNumber - 1; p > 0 && p >= index && list.next.currentNumber != ARRAY_LENGTH; p--)    {
                        insertInListArray(list.array[p], 0, list.next);
                        list.array[p] = null;
                        list.currentNumber--;
                    }

                    for (int p = collectionTail - 1; p > 0 && list.next.currentNumber != ARRAY_LENGTH; p--)    {
                        insertInListArray(collection[p], 0, list.next);
                        collectionTail--;
                    }
                }



                if (list.array[index] == null)  {

                    for (int p = 0; p < collectionTail; p++)  {

                        if (list.currentNumber >= ARRAY_LENGTH)  {
                            ListOfArraysItem<T> newList = new ListOfArraysItem<>();
                            newList.array = (T[]) new Object[ARRAY_LENGTH];

                            newList.next = list.next;
                            list.next = newList;
                            list = list.next;
                        }

                        list.array[list.currentNumber++] = collection[p];
                    }

                }else {
                    int elementsLeft = collectionTail;
                    int crawlspace = ARRAY_LENGTH - list.currentNumber;


                    if (crawlspace >= elementsLeft) {

                        for (int p = collectionTail - 1; p >= 0; p--)  {
                            insertInListArray(collection[p], index, list);
                        }
                    }else {

                        ListOfArraysItem<T> newItem = new ListOfArraysItem<>();
                        newItem.array = (T[]) new Object[ARRAY_LENGTH];
                        newItem.next = list.next;
                        list.next = newItem;


                        for (int p = index; p < ARRAY_LENGTH; p++)  {
                            if (list.array[p] == null) break;
                            list.next.array[list.next.currentNumber++] = list.array[p];
                            list.array[p] = null;
                            list.currentNumber--;
                        }

                        for (int p = collectionTail - 1; p >= 0 && newItem.currentNumber != ARRAY_LENGTH; p--)  {
                            insertInListArray(collection[p], 0, newItem);
                            collectionTail--;
                        }



                        for (int p = 0; p < collectionTail; p++)  {

                            if (list.currentNumber >= ARRAY_LENGTH)  {
                                ListOfArraysItem<T> newList = new ListOfArraysItem<>();
                                newList.array = (T[]) new Object[ARRAY_LENGTH];

                                newList.next = list.next;
                                list.next = newList;
                                list = list.next;
                            }

                            list.array[list.currentNumber++] = collection[p];
                        }
                    }

                }
            }

        }

        //set tail again
        while (this.tail.next != null)   {
            this.tail = this.tail.next;
        }
    }



    /**
     * Extracts a block of elements of this list. The block is defined by the boundary indices i and j (both included in the block that will be extracted) as a ListOfArrays object.
     * This will delete the extracted elements from the list.
     *
     * @param i The lower boundary index.
     * @param j The higher boundary index.
     * @return The extracted elements as a ListOfArrays object.
     */
    public ListOfArrays<T> extract(int i, int j) {
        //exception guard
        ListOfArraysItem<T> list = head;
        int maxIndex = 0;
        while(list.next != null) {
            maxIndex += list.currentNumber;
            list = list.next;
        }
        maxIndex += list.currentNumber;

        if (i < 0) throw new IndexOutOfBoundsException(i);

        if (j > maxIndex) throw new IndexOutOfBoundsException(j);

        if (i > j) throw new IndexOutOfBoundsException(i + " is greater than " + j);




        //create result array
        T[] sequence = (T[]) new Object[(j - i) + 1];

        ListOfArraysIterator<T> iterator = iterator();
        for (int p = 0; p < i; p++) {
            iterator.next();
        }

        for (int q = 0; q < sequence.length; q++) {
            sequence[q] = iterator.next();
        }


        //delete the items
        ListOfArraysItem<T> actualList = null;
        int index = i;

        if (index > head.currentNumber) {
            actualList = head;
            index -= actualList.currentNumber;


            while (index > actualList.next.currentNumber) {
                actualList = actualList.next;
                index -= actualList.currentNumber;
            }
            if (index == actualList.next.currentNumber)  {
                index -= actualList.next.currentNumber;
                actualList = actualList.next;
            }
        }

        if (actualList != null) {

            for (int p = 0; p <= j - i; p++)    {

                if (actualList.next.array[index] == null)  {

                    if (actualList.next.currentNumber == 0)  {
                        actualList.next = actualList.next.next;
                    }else {
                        actualList = actualList.next;
                    }
                    index = 0;
                }
                deleteItem(index, actualList.next);
            }

        }else {

            ListOfArraysItem<T> pointer = this.head;

            for (int p = 0; p <= j - i; p++)    {

                if (pointer == this.head)    {
                    if (pointer.array[index] == null)  {

                        if (pointer.currentNumber == 0)  {
                            this.head = this.head.next;
                            pointer = this.head;
                        }else {
                            pointer = pointer.next;
                        }
                        index = 0;
                    }
                    deleteItem(index, pointer);
                }else {

                    if (pointer.array[index] == null)  {
                        this.head.next = pointer.next;
                        pointer = pointer.next;
                        index = 0;
                    }
                    deleteItem(index, pointer);
                }

            }

        }





        //cleanup
        if (head != null)   {
            if (actualList == null) {

                if (head.next != null)  {
                    while (this.head.currentNumber != ARRAY_LENGTH && this.head.next.currentNumber != 0) {
                        this.head.array[this.head.currentNumber++] = this.head.next.array[0];
                        this.head.next.array[0] = null;
                        this.head.next.currentNumber--;


                        for (int p = 0; p + 1< ARRAY_LENGTH; p++)   {
                            this.head.next.array[p] = this.head.next.array[p + 1];
                        }

                        this.head.next.array[ARRAY_LENGTH - 1] = null;
                    }

                    if (this.head.next.currentNumber == 0)  {
                        this.head.next = this.head.next.next;
                    }

                }

            }else {

                if (actualList.next != null && actualList.next.next != null)    {
                    while (actualList.next.currentNumber != ARRAY_LENGTH && actualList.next.next.currentNumber != 0) {
                        actualList.next.array[actualList.next.currentNumber++] = actualList.next.next.array[0];
                        actualList.next.next.array[0] = null;
                        actualList.next.next.currentNumber--;

                        for (int p = 0; p + 1< ARRAY_LENGTH; p++)   {
                            actualList.next.next.array[p] = actualList.next.next.array[p + 1];
                        }

                        actualList.next.next.array[ARRAY_LENGTH - 1] = null;
                    }

                    if (actualList.next.next.currentNumber == 0)    {
                        actualList.next.next = actualList.next.next.next;
                    }
                }

            }
        }


        //set tail again
        while (this.tail.next != null)   {
            this.tail = this.tail.next;
        }

        return new ListOfArrays<>(sequence);
    }


    private void deleteItem(int index, ListOfArraysItem<T> list)  {

        list.array[index] = null;

        while (index + 1 < ARRAY_LENGTH)    {
            list.array[index] =  list.array[++index];
        }

        list.array[ARRAY_LENGTH - 1] = null;
        list.currentNumber--;
    }

    /**
     * Inserts the elements of given Iterator each with their given offset from the last inserted element (or the first element) into this list.
     *
     * @param iterator The Iterator over the elements (and their offsets) that should be added.
     * @throws IndexOutOfBoundsException If an offset is negative.
     */
    public void insert(Iterator<ElementWithIndex<T>> iterator) throws IndexOutOfBoundsException {   //EXCEPTIONS
        ElementWithIndex<T> element = iterator.next();

        T[] speicher = (T[]) new Object[ARRAY_LENGTH];
        int counter = 0;



        //determines list and index
        ListOfArraysItem<T> list = head;
        int index = element.getIndex();
        while (index >= list.currentNumber) {
            index -= list.currentNumber;
            list = list.next;
        }


        //first element insert
        speicher[counter] = list.array[index];
        list.array[index++] = element.getElement();


        //offset elements insert
        boolean lever = false;
        while (iterator.hasNext())  {
            element = iterator.next();


            for (int i = element.getIndex(); i > 0; i--)    {

                if (speicher[0] == null)    return;

                if (!lever) {
                    if (index == list.currentNumber)    {
                        if (list.next == null)  {
                            lever = true;
                            list.next = new ListOfArraysItem<>();
                            list.next.array = (T[]) new Object[ARRAY_LENGTH];
                            list.next.currentNumber = 0;

                            this.tail = list.next;
                        }
                        list = list.next;
                        index = 0;
                    }
                }else {
                    if (list.currentNumber == ARRAY_LENGTH) {
                        list.next = new ListOfArraysItem<>();
                        list.next.array = (T[]) new Object[ARRAY_LENGTH];
                        list.next.currentNumber = 0;

                        this.tail = list.next;
                        list = list.next;
                    }
                }


                if (!lever) {
                    if (counter + 1 == ARRAY_LENGTH)    return;

                    speicher[counter + 1] = list.array[index]; //
                    list.array[index++] = speicher[0];


                    //clean speicher
                    for (int q = 0; q < speicher.length - 1; q++) {
                        speicher[q] = speicher[q + 1];
                    }
                    speicher[speicher.length - 1] = null;


                }else {
                    list.array[list.currentNumber++] = speicher[0];

                    //clean speicher
                    for (int q = 0; q < speicher.length - 1; q++) {
                        speicher[q] = speicher[q + 1];
                    }
                    speicher[speicher.length - 1] = null;
                }

            }


            if (!lever) {
                if (index == list.currentNumber)    {
                    if (list.next == null)  {
                        lever = true;
                        list.next = new ListOfArraysItem<>();
                        list.next.array = (T[]) new Object[ARRAY_LENGTH];
                        list.next.currentNumber = 0;

                        this.tail = list.next;
                    }

                    list = list.next;
                    index = 0;
                }
            }else {
                if (list.currentNumber == ARRAY_LENGTH)    {
                    list.next = new ListOfArraysItem<>();
                    list.next.array = (T[]) new Object[ARRAY_LENGTH];
                    list.next.currentNumber = 0;

                    this.tail = list.next;
                    list = list.next;
                }
            }

            if (counter + 1 == ARRAY_LENGTH)    return;

            if (!lever)  {
                speicher[++counter] = list.array[index];
                list.array[index++] = element.getElement();
            }else {
                list.array[list.currentNumber++] = element.getElement();
            }



        }

        //add rest
        if (speicher[0] != null)    {
            for (int i = 0; i < speicher.length && speicher[i] != null; i++)    {

                if (list.currentNumber == ARRAY_LENGTH) {
                    list.next = new ListOfArraysItem<>();
                    list.next.array = (T[]) new Object[ARRAY_LENGTH];
                    list.next.currentNumber = 0;

                    list = list.next;
                }

                list.array[list.currentNumber++] = speicher[i];
            }
        }



        //set tail again
        while (this.tail.next != null)   {
            this.tail = this.tail.next;
        }

    }



    private void insertInListArray(T t, int i, ListOfArraysItem<T> list)  {
        T tmp = list.array[i];
        T tmp2;
        int arrayCounter = i + 1;
        list.array[i] = t;

        while (arrayCounter < ARRAY_LENGTH) {
            tmp2 = list.array[arrayCounter];
            list.array[arrayCounter++] = tmp;
            tmp = tmp2;
        }
        list.currentNumber++;
    }
}

