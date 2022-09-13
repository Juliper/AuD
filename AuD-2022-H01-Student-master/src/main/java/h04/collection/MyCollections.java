package h04.collection;
import h04.function.DoubleToIntFunction;
import h04.function.FunctionOnRatioOfRuns;
import h04.function.ListToIntFunction;

import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.util.*;


/**
 * A collection that allows to order (sort) the unordered sequence. The sorting algorithm is based on merge-sort, switching to
 * selection-sort when the sequence is small to increase performance.
 *
 * @param <T> the type of the elements in the list that can be sorted
 * @author Nhan Huynh
 */
public class MyCollections<T> {

    /**
     * Determines the toggle length when the sorting algorithm should be toggled (usage of another sorting algorithm).
     */
    private final ListToIntFunction<T> function;

    /**
     * The comparator used to compare the elements of the list.
     */
    private final Comparator<? super T> cmp;

    /**
     * Constructs and initializes a {@code MyCollections}.
     *
     * @param function the function determining the toggle length
     * @param cmp      the comparator used to compare the elements of the list
     */
    public MyCollections(ListToIntFunction<T> function, Comparator<? super T> cmp) {
        this.function = function;
        this.cmp = cmp;
    }

    /**
     * Sorts the list in place.
     *
     * @param list the list to sort
     */
    public void sort(List<T> list) {
        //convert list to ListItem
        ListItem<T> head = listToListItem(list);

        //sort
        ListItem<T> result = adaptiveMergeSortInPlace(head, function.apply(list));

        //convert listItem to list
        listItemToList(result, list);
    }

    /**
     * Transfers all elements from a list to a list item sequence.
     *
     * @param list the list to transfer from
     * @return the list item sequence containing the element of the list
     */
    private ListItem<T> listToListItem(List<T> list) {
        //WORKS
        ListItem<T> head = null;
        ListItem<T> tail = null;


        //list copy
        for (T t : list)    {
            if (head == null)   {
                head = new ListItem<>();
                tail = head;

                head.key = t;
            }else {
                tail.next = new ListItem<>();
                tail.next.key = t;
                tail = tail.next;
            }
        }



        return head;
    }

    /**
     * Transfers all elements from a ListItem sequence to a list.
     *
     * @param head the list item sequence
     * @param list the list to transfer to
     */
    private void listItemToList(ListItem<T> head, List<T> list) {
        //WORKS
        ListItem<T> pointer = head;
        int i = 0;

        while (pointer != null) {
            list.set(i++, pointer.key);
            pointer = pointer.next;
        }
    }

    /**
     * Sorts the list in place using the merge sort algorithm. If the (sub-)sequence is smaller than the specified threshold, the
     * selection sort algorithm  (in place) is used.
     *
     * @param head      the list to sort
     * @param threshold the threshold determining the toggle length
     * @return the sorted list
     */
    private ListItem<T> adaptiveMergeSortInPlace(ListItem<T> head, int threshold) {
        //Exception guard
        if (head == null) return null;

        if (head.next == null)  return head;



        //determines length and tests order
        boolean sorted = true;
        int length = 0;
        ListItem<T> p = head;

        while (p != null)   {
            length++;
            if (p.next != null) {
                if (cmp.compare(p.key, p.next.key) > 0) {
                    sorted = false;
                }
            }

            p = p.next;
        }


        //if sorted return list
        if (sorted) return head;


        //if threshold is met but sequence is not sorted
        if (length <= threshold)   return selectionSortInPlace(head);




        //if not sorted and threshold is not met
        ListItem<T> left = head;    //saves first Item
        ListItem<T> right = split(left, (length % 2 == 0) ? length/2 : (length + 1)/2); //split given list



        return merge(   //sort both fragments
            adaptiveMergeSortInPlace(left, threshold),
            adaptiveMergeSortInPlace(right, threshold)
        );

    }

    /**
     * Splits the list into two subsequences.
     *
     * <p>The decomposition of the list into two subsequences is related to the searched optimal size and the number of
     * elements of runs, which is close to the optimal size.
     *
     * @param head        the list to split
     * @param optimalSize the optimal size after the split
     * @return the second part of the list
     */
    private ListItem<T> split(ListItem<T> head, int optimalSize) {
        ListItem<T> possibleCut = null;
        int indexCut = 0;



        int i = 0;
        ListItem<T> p = head;
        while (p.next != null)  {

            if (cmp.compare(p.key , p.next.key) > 0)   {

                if (possibleCut == null)    {
                    possibleCut = p;
                    indexCut = i;
                }
                else {

                    if (Math.abs( (indexCut + 1) - optimalSize) > Math.abs( (i + 1) - optimalSize)) {
                        possibleCut = p;
                        indexCut = i;
                    }

                }


                if (indexCut + 1 == optimalSize)    break;
            }




            i++;
            p = p.next;
        }



        ListItem<T> result = possibleCut.next;
        possibleCut.next = null;

        return result;
    }

    /**
     * Merges the two given sub-sequences into one sorted sequence.
     *
     * @param left  the left sub-sequence
     * @param right the right sub-sequence
     * @return the merged sorted sequence
     */
    private ListItem<T> merge(ListItem<T> left, ListItem<T> right) {
        ListItem<T> result = null;
        ListItem<T> tail = null;


        while (left != null && right != null)    {

            if (cmp.compare(left.key, right.key) > 0)   {
                if (result == null) {
                    result = right;

                    tail = result;
                    right = right.next;

                    result.next = null;

                }else {
                    tail.next = right;

                    right = right.next;
                    tail = tail.next;
                }
            }
            else{
                if (result == null) {
                    result = left;

                    tail = result;
                    left = left.next;

                    result.next = null;
                }else {
                    tail.next = left;

                    left = left.next;
                    tail = tail.next;
                }

            }

            tail.next = null;
        }


        //add rest
        while (left != null)    {
            tail.next = left;

            left = left.next;
            tail = tail.next;
        }
        while (right != null)   {
            tail.next = right;

            right = right.next;
            tail = tail.next;
        }


        return result;
    }

    /**
     * Sorts the list in place using the selection sort algorithm.
     *
     * @param head the list to sort
     * @return the sorted list
     */
    private ListItem<T> selectionSortInPlace(ListItem<T> head) {
        if (head == null || head.next == null)  return head;

        ListItem<T> tailResult = null;
        ListItem<T> result = null;



        while (head != null)   {
            ListItem<T> lastMinimumBefore = null;
            ListItem<T> p = head;

            while (p.next != null)   {

                if (lastMinimumBefore == null)  {

                    if (cmp.compare(head.key, p.next.key) > 0) lastMinimumBefore = p;

                }else   {

                    if (cmp.compare(lastMinimumBefore.next.key, p.next.key) > 0) lastMinimumBefore = p;

                }

                p = p.next;
            }



            if (result == null) {

                if (lastMinimumBefore == null)  {
                    result = head;
                    tailResult = result;

                    head = head.next;
                    result.next = null;
                }else   {
                    ListItem<T> tmp = lastMinimumBefore.next;
                    lastMinimumBefore.next = lastMinimumBefore.next.next;
                    tmp.next = null;

                    result = tmp;
                    tailResult = result;
                }

            }else   {

                if (lastMinimumBefore == null)  {
                    tailResult.next = head;

                    head = head.next;
                    tailResult.next.next = null;
                    tailResult = tailResult.next;
                }else   {
                    ListItem<T> tmp = lastMinimumBefore.next;
                    lastMinimumBefore.next = lastMinimumBefore.next.next;
                    tmp.next = null;

                    tailResult.next = tmp;
                    tailResult = tailResult.next;
                }


            }



        }

        return result;
    }

    /**
     * Swaps two elements in the list.
     *
     * @param head the list to swap
     * @param i    the index of the first element
     * @param j    the index of the second element
     * @return the list with the swapped elements
     */
    private ListItem<T> swap(ListItem<T> head, int i, int j) {
        if (head == null)   return head;
        if (i == j) return head;


        //length
        ListItem<T> p = head;
        int length = 0;
        while (p != null)    {
            length++;
            p = p.next;
        }
        if (i > length - 1 || j > length - 1 || j < 0 || i < 0) return null;



        ListItem<T> beforeI = null;
        ListItem<T> beforeJ = null;

        for (int ii = 0; ii < i; ii++)  beforeI = (beforeI == null) ? head : beforeI.next;
        for (int jj = 0; jj < j; jj++)  beforeJ = (beforeJ == null) ? head : beforeJ.next;


        if (i + 1 == j)   {

            if (beforeI == null)    {
                ListItem<T> tmp = head.next;
                head.next = head.next.next;
                tmp.next = head;
                head = tmp;

            }else {
                ListItem<T> tmpI = beforeI.next;
                beforeI.next = beforeI.next.next;
                tmpI.next = null;

                tmpI.next = beforeI.next.next;
                beforeI.next.next = tmpI;
            }
        }
        else if (j + 1 == i)    {

            if (beforeJ == null)    {
                ListItem<T> tmp = head.next;
                head.next = head.next.next;
                tmp.next = head;
                head = tmp;
            }else {
                ListItem<T> tmpI = beforeJ.next;
                beforeJ.next = beforeJ.next.next;
                tmpI.next = null;

                tmpI.next = beforeJ.next.next;
                beforeJ.next.next = tmpI;
            }
        }
        else {
            if (beforeI == null)    {
                ListItem<T> tmp = beforeJ.next;

                beforeJ.next = beforeJ.next.next;


                tmp.next = head;

                tmp.next = tmp.next.next;

                head.next = beforeJ.next;
                beforeJ.next = head;


                head = tmp;
            }
            else if (beforeJ == null)   {
                ListItem<T> tmp = beforeI.next;

                beforeI.next = beforeI.next.next;


                tmp.next = head;

                tmp.next = tmp.next.next;

                head.next = beforeI.next;
                beforeI.next = head;


                head = tmp;

            }
            else {
                ListItem<T> tmp = beforeI.next;
                beforeI.next = beforeI.next.next;

                tmp.next = beforeJ.next.next;

                beforeJ.next.next = tmp;

                tmp = beforeJ.next;
                beforeJ.next = beforeJ.next.next;

                tmp.next = beforeI.next;
                beforeI.next = tmp;
            }
        }


        return head;
    }

    /**
     * Returns the index of the last maximum element in the list.
     *
     * @param head the list to search
     * @param low  the lower bound of the sublist (inclusive)
     * @param high the upper bound of the sublist (inclusive)
     * @return the index of the last maximum element in the list
     */
    private int getMaximumIndex(ListItem<T> head, int low, int high) {
        if (head == null) return -1;

        //length
        ListItem<T> p = head;
        int length = 0;
        while (p != null)    {
            length++;
            p = p.next;
        }
        if (low > length - 1 || high > length - 1 || low < 0 || high < 0) return -1;



        int currentInt = low + 1;
        for (int i = 0; i < low; i++)   head = head.next;

        ListItem<T> maximum = head;
        int maximumInt = 0;


        head = head.next;

        while (head != null && currentInt <= high)    {
            int i = cmp.compare(maximum.key, head.key);

            if (i < 0)  {
                maximumInt = currentInt;
                maximum = head;
            }

            head = head.next;
            currentInt++;
        }



        return maximumInt;
    }

}

