package h06;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

public class PublicTests {

    private static final int TABLE_SIZE = 69;

    @Nested
    class Hash2IndexFctTest {

        private final Hash2IndexFct<Integer> fct = getHash2IndexFct();

        @Test
        void testApply() {
            assertEquals(7, fct.apply(1));
            assertEquals(51, fct.apply(-666));
        }
    }

    private Hash2IndexFct<Integer> getHash2IndexFct() {
        return new Hash2IndexFct<>(TABLE_SIZE, 420);
    }

    @Nested
    class LinearProbingTest {

        private final LinearProbing<Integer> probing = new LinearProbing<>(getHash2IndexFct());

        @Test
        void testApply() {
            assertEquals(13, probing.apply(1, 6));
            assertEquals(57, probing.apply(-666, 6));
        }
    }

    @Nested
    class DoubleHashingTest {

        private final DoubleHashing<Integer> doubleHashing = getDoubleHashing();

        @Test
        void testApply() {
            assertEquals(49, doubleHashing.apply(1, 6));
            assertEquals(12, doubleHashing.apply(-666, 6));
        }
    }

    private DoubleHashing<Integer> getDoubleHashing() {
        return new DoubleHashing<>(getHash2IndexFct(), getHash2IndexFct());
    }

    @Nested
    class MyDateTest {

        private MyDate getDate(boolean b) {
            var cal = Calendar.getInstance();
            cal.set(2002, Calendar.MARCH, 11, 3, 4, 5);
            return new MyDate(cal, b);
        }

        @Test
        void testConstructor() {
            var date = getDate(false);

            assertEquals(2002, date.getYear());
            assertEquals(Calendar.MARCH, date.getMonth());
            assertEquals(11, date.getDay());
            assertEquals(3, date.getHour());
            assertEquals(4, date.getMinute());
            assertFalse(date.getBool());
        }

        @Test
        void testHashWithFalse() {
            var date = getDate(false);
            assertEquals(200024328, date.hashCode());
        }

        @Test
        void testHashWithTrue() {
            var date = getDate(true);
            assertEquals(2058635491, date.hashCode());
        }

        @Test
        void testEquals() {
            assertEquals(getDate(true), getDate(true));
            assertEquals(getDate(false), getDate(false));
            assertNotEquals(getDate(true), getDate(false));
        }
    }

    @Nested
    class MyIndexHoppingHashMapTest extends MyMapTest {

        MyIndexHoppingHashMapTest() {
            super(new MyIndexHoppingHashMap<>(4, 2, 0.5, getDoubleHashing()));
        }
    }

    @Nested
    class MyListsHashMapTest extends MyMapTest {

        MyListsHashMapTest() {
            super(new MyListsHashMap<>(getHash2IndexFct()));
        }
    }

    public static abstract class MyMapTest {

        protected final MyMap<Integer, Integer> fib;

        public MyMapTest(MyMap<Integer, Integer> fib) {
            this.fib = fib;
            fib.put(0, 0);
            fib.put(1, 1);
        }

        @Test
        void testContainsKey() {
            assertTrue(fib.containsKey(0));
            assertTrue(fib.containsKey(1));
            assertFalse(fib.containsKey(2));
            assertFalse(fib.containsKey(3));
        }

        @Test
        void testGetValue() {
            assertEquals(0, fib.getValue(0));
            assertEquals(1, fib.getValue(1));
        }

        @Test
        void testPut() {
            assertEquals(0, fib.put(0, 2));
            assertEquals(2, fib.getValue(0));

            assertNull(fib.put(3, 2));
            assertTrue(fib.containsKey(3));
            assertEquals(2, fib.getValue(3));
        }

        @Test
        void testRemove() {
            assertNull(fib.remove(69));
            assertEquals(1, fib.remove(1));
            assertFalse(fib.containsKey(1));
            assertNull(fib.getValue(1));
        }

        @Test
        void testFib() {
            for (int i = 2; i <= 20; i++) {
                var a = fib.getValue(i - 1);
                assertNotNull(a);

                var b = fib.getValue(i - 2);
                assertNotNull(b);

                fib.put(i, a + b);
            }

            int[] expected = {
                0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765
            };

            for (int i = 0; i < expected.length; i++) {
                assertEquals(expected[i], fib.getValue(i));
            }
        }
    }

    @Nested
    class RuntimeTestTest {

        private final MyDate[][] testdata = RuntimeTest.generateTestdata();

        private final TestSet<MyDate> testSet =
            RuntimeTest.createTestSet(1, 1, 1, 1,
                testdata);

        @Test
        void testGenerateTestdata() {
            assertTestData(testdata[0], true);
            assertTestData(testdata[1], false);
        }

        private void assertTestData(MyDate[] testdata, boolean expectedBoolean) {
            assertEquals(1000, testdata.length);
            for (MyDate date : testdata) {
                assertTrue(date.getYear() >= 1970);
                assertTrue(date.getYear() <= 2022);
                assertEquals(expectedBoolean, date.getBool());
            }
        }

        @Test
        void testCreateTestSet() {
            assertTestData(testSet.getTestData(), true);
            assertInstanceOf(MyIndexHoppingHashMap.class, testSet.getHashTable());
        }

        @Test
        void testTest() {
            assertDoesNotThrow(() ->
                RuntimeTest.test(testSet));
        }
    }
}
