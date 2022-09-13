package h06;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Random;
import java.util.stream.LongStream;

public class RuntimeTest {
    private final static int TEST_SET_SIZE = 1_000;

    /**
     * Generates two test data sets with 1,000 dates each.
     * The first test data set is in component 0 of the returned array and is initialized with true.
     * The second test data set is in component 1 of the returned array and is initialized with false.
     * The dates are between 1970 and 2022.
     *
     * @return Two test data sets of 1,000 dates each.
     */
    public static MyDate[][] generateTestdata() {
        MyDate[][] result = new MyDate[2][TEST_SET_SIZE];

        Random random = new Random();
        LongStream longs = random.longs();
        Iterator<Long> iterator = longs.iterator();




        for (int i = 0; i < TEST_SET_SIZE; i++)  {

            long current = Math.floorMod(Math.abs(iterator.next()), (long) 1.6409952E12);

            Calendar calendar = new GregorianCalendar();
            calendar.setTimeInMillis(current);


            result[0][i] = new MyDate(calendar, true);
            result[1][i] = new MyDate(calendar, false);

        }


        return result;
    }

    /**
     * Generates a test set.
     *
     * @param i        See exercise sheet.
     * @param j        See exercise sheet.
     * @param k        See exercise sheet.
     * @param l        See exercise sheet.
     * @param testData The testdata used.
     * @return A test set.
     */
    public static TestSet<MyDate> createTestSet(int i, int j, int k, int l, MyDate[][] testData) {
        int size = (l == 1) ? 64 : 4096;


        return new TestSet<>(


            (j == 1) ? new MyIndexHoppingHashMap<>(size, 2, 0.75,

                (k == 1) ? new LinearProbing<>(new Hash2IndexFct<>(size, 0)) :
                    new DoubleHashing<>(new Hash2IndexFct<>(size, 0), new Hash2IndexFct<>(size, 42))


            ) : new MyListsHashMap<>(new Hash2IndexFct<>(size, 0)),



            (i == 1) ? testData[0] :
                testData[1]


        );

    }

    /**
     * Tests the given test set.
     *
     * @param testSet The test set to test.
     */
    public static void test(TestSet<MyDate> testSet) {
        MyDate[] dates = testSet.getTestData();
        MyMap<MyDate, MyDate> table = testSet.getHashTable();



        for (int i = 0; i < 750; i++)   {

            table.put(dates[i], dates[i]);

        }

        int i = 0;

        for (MyDate date : dates)   {

            if (table.containsKey(date))   i++;

        }
        if (i != 750)   System.out.println("FAIL 1");
        i = 0;



        for (MyDate date : dates)   {

            if (table.getValue(date) != null)   {

                if (!dates[i++].equals(table.getValue(date)))   System.out.println("FAIL");

            }

        }
        if (i != 750)   System.out.println("FAIL 1");
        i = 0;



        for (MyDate date : dates)   {

            MyDate tmp = table.remove(date);



            if (tmp != null)    {
                if (!tmp.equals(dates[i++]))   System.out.println("FAIL");
            }



        }

        if (i != 750)   System.out.println("FAIL 1");

    }
}
