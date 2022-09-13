package h01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleListOfListsProcessorsTest {

    @Test
    public void testAddition() {
        assertEquals(2, 1 + 1);
    }

    private static ListItem<ListItem<Double>> createListOfLists() {
        ListItem<ListItem<Double>> result = new ListItem<>();
        ListItem<ListItem<Double>> head = result;
        ListItem<Double> tail = null;



        //Leere Hauptlisten erstellen
        for (int i = 0; i < 99; i++) {

            head.next = new ListItem<>();
            head = head.next;

            if (i != 0) {
                for (int k = 0; i < 100; k++) {

                }
                head.key = new ListItem<>();
            }

        }

        //50 Einzellisten unter limit(mit einer Einzelliste null, und nur einem Element)
        for (ListItem<ListItem<Double>> j = result; j != null; j = j.next) {
            if (j == result) {
                continue;
            }

            if (j == result.next) {
                j.key = new ListItem<>();
                j.key.key = 1.0;
            }

            for (int i = 0; i < 48; i++) {

            }

        }


        return result;
    }
}
