package h01;

import java.io.BufferedReader;
import java.io.StringReader;
import java.io.StringWriter;

import static h01.ListUtils.assertListsEquals;
import static h01.ListUtils.toList;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PublicTests {

    private final double limit = 6.0;
    private final ListItem<ListItem<Double>> expectedRepartitionedList =
        toList(
            toList(1.0, 3.0),
            toList(5.0),
            toList(1.0, 2.0, 3.0),
            toList(4.0),
            toList(5.0));
    private final ListItem<ListItem<Double>> listForReadWrite = toList(
        toList(1.0, 2.0),
        toList(1.0, 2.0, 3.0));
    private final String contentForReadWrite = "1.0#2.0\n1.0#2.0#3.0";
    private ListItem<ListItem<Double>> listToPartition;

    @BeforeEach
    void setup() {
        listToPartition = toList(toList(1.0, 3.0, 5.0), toList(1.0, 2.0, 3.0, 4.0, 5.0));
    }

    @Test
    void testPartitionListsAsCopyIteratively() {
        var actual = DoubleListOfListsProcessor.partitionListsAsCopyIteratively(listToPartition, limit);
        assertListsEquals(expectedRepartitionedList, actual);
    }

    @Test
    void testPartitionListsInPlaceIteratively() {
        var actual = DoubleListOfListsProcessor.partitionListsInPlaceIteratively(listToPartition, limit);
        assertListsEquals(expectedRepartitionedList, actual);
    }

    @Test
    void testPartitionListsAsCopyRecursively() {
        var actual = DoubleListOfListsProcessor.partitionListsAsCopyRecursively(listToPartition, limit);
        assertListsEquals(expectedRepartitionedList, actual);
    }

    @Test
    void testPartitionListsInPlaceRecursively() {
        var actual = DoubleListOfListsProcessor.partitionListsInPlaceRecursively(listToPartition, limit);
        assertListsEquals(expectedRepartitionedList, actual);
    }

    @Test
    void testWrite() {
        var writer = new StringWriter();
        DoubleListOfListsProcessor.write(writer, listForReadWrite);
        var actual = writer.toString();
        assertEquals(contentForReadWrite.replace("\n", "\\n"), actual.replace("\n", "\\n"));
    }

    @Test
    void testRead() {
        var reader = new BufferedReader(new StringReader(contentForReadWrite));
        var actual = DoubleListOfListsProcessor.read(reader);
        assertListsEquals(listForReadWrite, actual);
    }

}

class ListUtils {

    private ListUtils() {
    }

    public static void assertListsEquals(ListItem<?> expected, ListItem<?> actual) {
        assertEquals(toString(expected), toString(actual));
    }

    @SafeVarargs
    public static <T> ListItem<T> toList(T... elements) {
        ListItem<T> head = null;
        ListItem<T> tail = null;
        for (var e : elements) {
            if (head == null) {
                head = tail = new ListItem<>();
            } else {
                tail = tail.next = new ListItem<>();
            }
            tail.key = e;
        }
        return head;
    }

    public static String toString(ListItem<?> list) {
        var builder = new StringBuilder("(");
        while (list != null) {
            builder.append(list.key instanceof ListItem<?> item ? toString(item) : list.key);
            if (list.next != null) {
                builder.append(" ");
            }
            list = list.next;
        }
        return builder.append(")").toString();
    }
}

