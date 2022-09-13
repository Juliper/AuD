package h01;

import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.util.Objects;

public class DoubleListOfListsProcessor {

    /**
     * Partitions a copy of {@code listOfLists} so that the sum of each sub-list does not exceed {@code limit}.
     * This implementation must not modify the input list.
     *
     * @param listOfLists a reference to the head of a list with arbitrary size
     * @param limit       the maximum value that may not be exceeded by the sum of any sub-list in the returned list
     * @return a partitioned list of lists
     * @throws RuntimeException if a single value exceeds {@code limit}
     */
    public static @Nullable ListItem<@Nullable ListItem<Double>> partitionListsAsCopyIteratively(
        @Nullable ListItem<@Nullable ListItem<Double>> listOfLists,
        double limit
    ) {
        if (listOfLists == null)    return null;

        double sum = 0;
        int i = 0;
        int j = 0;

        ListItem<ListItem<Double>> result = new ListItem<>();
        ListItem<ListItem<Double>> head = result;
        ListItem<Double> tail = null;


        for (ListItem<ListItem<Double>> p1 = listOfLists; p1 != null; p1 = p1.next) {

            if (i != 0) {
                head.next = new ListItem<>();
                head = head.next;
            }


            for (ListItem<Double> p2 = p1.key; p2 != null; p2 = p2.next) {
                if(p2.key > limit) throw new RuntimeException("element at (" + i + ", " + j + ") exceeds limit\n by " + Math.abs(limit - p2.key));


                if (sum + p2.key > limit) {

                    head.next = new ListItem<>();
                    head = head.next;


                    head.key = new ListItem<>();
                    tail = head.key;
                    tail.key = p2.key;


                    sum = p2.key;
                } else {

                    if (head.key == null) {

                        head.key = new ListItem<>();
                        tail = head.key;

                    } else {

                        tail.next = new ListItem<>();
                        tail = tail.next;

                    }


                    tail.key = p2.key;
                    sum += p2.key;
                }


                j++;
            }

            j = 0;
            i++;
            sum = 0;
        }

        return result;
    }

    /**
     * Partitions a copy of {@code listOfLists} so that the sum of each sub-list does not exceed {@code limit}.
     * This implementation must only use recursion and not modify the input list.
     *
     * @param listOfLists a reference to the head of a list with arbitrary size
     * @param limit       the maximum value that may not be exceeded by the sum of any sub-list in the returned list
     * @return a partitioned list of lists
     * @throws RuntimeException if a single value exceeds {@code limit}
     */
    public static @Nullable ListItem<@Nullable ListItem<Double>> partitionListsAsCopyRecursively(
        @Nullable ListItem<@Nullable ListItem<Double>> listOfLists,
        double limit
    ) {
        if (listOfLists == null)    return null;

        double sum = 0;
        int i = 0;
        int j = 0;

        ListItem<ListItem<Double>> result = new ListItem<>();
        ListItem<ListItem<Double>> head = result;
        ListItem<Double> tail = null;

        partitionListsAsCopyRecursivelyHelper(listOfLists, limit, sum, i, j, head, tail);

        return result;
    }


    private static void partitionListsAsCopyRecursivelyHelper(ListItem<ListItem<Double>> listOfLists, double limit, double sum, int i, int j, ListItem<ListItem<Double>> head, ListItem<Double> tail) {
        if (listOfLists == null) return;


        if (i != 0) {
            head.next = new ListItem<>();
            head = head.next;
        }

        partitionListsAsCopyRecursivelyHelper2(listOfLists, listOfLists.key, limit, 0, i, j, head, tail);

    }


    private static void partitionListsAsCopyRecursivelyHelper2(ListItem<ListItem<Double>> listOfLists, ListItem<Double> list, double limit, double sum, int i, int j, ListItem<ListItem<Double>> head, ListItem<Double> tail) {
        if (list == null) {
            partitionListsAsCopyRecursivelyHelper(listOfLists.next, limit, 0, ++i, 0, head, tail);
            return;
        }

        if(list.key > limit) throw new RuntimeException("element at (" + i + ", " + j + ") exceeds limit\n by " + Math.abs(limit - list.key));

        if (sum + list.key > limit) {

            head.next = new ListItem<>();
            head.next.key = new ListItem<>();

            head.next.key.key = list.key;

            partitionListsAsCopyRecursivelyHelper2(listOfLists, list.next, limit, list.key, i, ++j, head.next, head.next.key);
        } else {

            if (head.key == null) {

                head.key = new ListItem<>();
                tail = head.key;

            } else {

                tail.next = new ListItem<>();
                tail = tail.next;

            }


            tail.key = list.key;

            sum += list.key;

            partitionListsAsCopyRecursivelyHelper2(listOfLists, list.next, limit, sum, i, ++j, head, tail);
        }
    }

    /**
     * Partitions a copy of {@code listOfLists} so that the sum of each sub-list does not exceed {@code limit}.
     * This implementation must not create new sub-lists.
     *
     * @param listOfLists a reference to the head of a list with arbitrary size
     * @param limit       the maximum value that may not be exceeded by the sum of any sub-list in the returned list
     * @return a partitioned list of lists
     * @throws RuntimeException if a single value exceeds {@code limit}
     */
    public static @Nullable ListItem<@Nullable ListItem<Double>> partitionListsInPlaceIteratively(
        @Nullable ListItem<@Nullable ListItem<Double>> listOfLists,
        double limit
    ) {
        int i = 0;
        int j = 0;
        double sum = 0;



        for (ListItem<ListItem<Double>> pH = listOfLists; pH != null; pH = pH.next) {

            if (pH.key != null) {
                if(pH.key.key > limit)  throw new RuntimeException("element at (" + i + ", " + j + ") exceeds limit\n by " + Math.abs(limit - pH.key.key)); //Math.abs nicht erlaubt
                j++;
                sum += pH.key.key;
            }

            if (pH.key != null) {
                for (ListItem<Double> pE = pH.key; pE.next != null; ) {
                    if(pE.next.key > limit) throw new RuntimeException("element at (" + i + ", " + j + ") exceeds limit\n by " + Math.abs(limit - pE.next.key)); //Math.abs nicht erlaubt

                    if(sum + pE.next.key > limit) {
                        ListItem<ListItem<Double>> newHauptliste = new ListItem<>();
                        newHauptliste.next = pH.next;
                        pH.next = newHauptliste;
                        pH = pH.next;

                        newHauptliste.key = pE.next;
                        pE.next = null;

                        pE = newHauptliste.key;
                        sum = pE.key;

                    } else {
                        sum += pE.next.key;
                        pE = pE.next;
                    }

                    j++;
                }
            }

            i++;
            j = 0;
            sum = 0;
        }

        return listOfLists;
    }

    /**
     * Partitions a copy of {@code listOfLists} so that the sum of each sub-list does not exceed {@code limit}.
     * This implementation must only use recursion and not crete new sub-lists.
     *
     * @param listOfLists a reference to the head of a list with arbitrary size
     * @param limit       the maximum value that may not be exceeded by the sum of any sub-list in the returned list
     * @return a partitioned list of lists
     * @throws RuntimeException if a single value exceeds {@code limit}
     */
    public static @Nullable ListItem<@Nullable ListItem<Double>> partitionListsInPlaceRecursively(
        @Nullable ListItem<@Nullable ListItem<Double>> listOfLists,
        double limit
    ) {
        partitionListsInPlaceRecursivelyHelper(listOfLists, listOfLists.key, listOfLists.key.key, limit, 0, 0);
        return listOfLists;
    }

    private static void partitionListsInPlaceRecursivelyHelper(ListItem<ListItem<Double>> head, ListItem<Double> tail, double current, double limit, int i, int j) {
        if (head == null)   return;

        if (head.key == null) {

            if (head.next != null) {
                if (head.next.key != null) {
                    partitionListsInPlaceRecursivelyHelper(head.next, head.next.key, head.next.key.key, limit, ++i, 0);
                } else partitionListsInPlaceRecursivelyHelper(head.next, null, 0, limit, ++i, 0);
            }


            return;
        }

        if(tail.key > limit) throw new RuntimeException("element at (" + i + ", " + j + ") exceeds limit\n by " + Math.abs(limit - tail.next.key)); //Math.abs nicht erlaubt
        j++;

         if (tail.next == null && head.next != null) {

             if (head.next.key != null) {
                 partitionListsInPlaceRecursivelyHelper(head.next, head.next.key, head.next.key.key, limit, ++i, 0);
             } else partitionListsInPlaceRecursivelyHelper(head.next, null, 0, limit, ++i, 0);

         }

         if (tail.next == null) return;


        if(tail.next.key > limit) throw new RuntimeException("element at (" + i + ", " + j + ") exceeds limit\n by " + Math.abs(limit - tail.next.key)); //Math.abs nicht erlaubt


        if(tail.next.key + current > limit) {
            ListItem<ListItem<Double>> list = new ListItem<>();
            list.next = head.next;
            head.next = list;

            list.key = tail.next;
            tail.next = null;

            partitionListsInPlaceRecursivelyHelper(list, list.key, list.key.key, limit, i, 0);
        } else {

            partitionListsInPlaceRecursivelyHelper(head, tail.next, current + tail.next.key, limit, i, ++j);

        }
    }

    /**
     * Writes out {@code listOfLists} (well, the sub-lists) to {@code writer}.
     *
     * @param writer      the writer to write to
     * @param listOfLists the list of lists to write out
     */
    public static void write(Writer writer, @Nullable ListItem<@Nullable ListItem<Double>> listOfLists) {
        try {
            for (ListItem<ListItem<Double>> mainList = listOfLists; mainList != null; mainList = mainList.next) {

                if(mainList.key == null) writer.write("#");
                writer.flush();

                for (ListItem<Double> subList = mainList.key; subList != null; subList = subList.next) {
                    writer.write(subList.key.toString());
                    if(subList.next != null) writer.write("#");
                    writer.flush();
                }

                if(mainList.next != null) writer.write("\n");

                writer.flush();
            }

        }catch (IOException ignored) {}
    }

    /**
     * Reads a list of lists from {@code reader} and returns it.
     *
     * @param reader the reader to read from
     * @return a list of lists of double
     */
    public static @Nullable ListItem<@Nullable ListItem<Double>> read(BufferedReader reader) {
        ListItem<ListItem<Double>> result = null;
        ListItem<ListItem<Double>> pointer = null;

        try {

            for(String line = reader.readLine(); line != null; line = reader.readLine()) {
                if(result == null) {
                    result = new ListItem<>();
                    result.key = readStringLine(line);
                    pointer = result;
                } else {
                    pointer.next = new ListItem<>();
                    pointer.next.key = readStringLine(line);
                    pointer = pointer.next;
                }
            }

        } catch (IOException ignored) {}

        return result;
    }

    static public ListItem<Double> readStringLine(String line) {
        if (Objects.equals(line, "#")) {
            return null;
        }

        StringBuilder value = new StringBuilder();
        ListItem<Double> result = null;
        ListItem<Double> pointer = null;


        for (char chars : line.toCharArray()) {

            if (chars == '#') {
                if (result == null) {
                    result = new ListItem<>();
                    pointer = result;

                    result.key = Double.parseDouble(value.toString());
                    value = new StringBuilder();
                    continue;
                } else {
                    pointer.next = new ListItem<>();
                    pointer.next.key = Double.parseDouble(value.toString());
                    pointer = pointer.next;
                    value = new StringBuilder();
                    continue;
                }
            }

            value.append(chars);
        }


        if (result == null) {
            result = new ListItem<>();
            result.key = Double.parseDouble(value.toString());
        } else {
            pointer.next = new ListItem<>();
            pointer.next.key = Double.parseDouble(value.toString());
        }

        return result;
    }
}
