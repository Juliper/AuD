package h04;

import h04.collection.MyCollections;
import h04.function.*;
import org.jetbrains.annotations.Nullable;
import java.lang.management.*;

import java.util.*;

/**
 * A sorting experiment to determine the optimal thresholds for {@link MyCollections#sort(List)}.
 *
 * @author Kim Berninger, Nhan Huynh
 */
public final class SortingExperiment {

    /**
     * Don't let anyone instantiate this class.
     */
    private SortingExperiment() {
    }

    /**
     * Main entry point in executing the sorting experiment.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        LinearRegression linearRegression = new LinearRegression();
        LinearInterpolation linearInterpolation = new LinearInterpolation();


        Integer[][] result = computeOptimalThresholds(1000, 800, 100, 0.5);

        ListToIntFunction<Integer> runs1 = new FunctionOnRatioOfRuns<>(Comparator.naturalOrder() ,linearRegression.fitFunction(result[0]));
        ListToIntFunction<Integer> inv1 = new FunctionOnRatioOfInversions<>(Comparator.naturalOrder(), linearRegression.fitFunction(result[1]));


        ListToIntFunction<Integer> runs2 = new FunctionOnRatioOfRuns<>(Comparator.naturalOrder() ,linearInterpolation.fitFunction(result[0]));
        ListToIntFunction<Integer> inv2 = new FunctionOnRatioOfInversions<>(Comparator.naturalOrder(), linearInterpolation.fitFunction(result[1]));



        for (int i = 0; i < 4 ; i++)    {
            float time = 0;

            for (int j = 0; j < 500; j++)   {

                //random list
                List<Integer> p = new ArrayList<>();
                for (int q = 1; q <= 1000; q++)   p.add(q);


                //swaps s elements
                for (int q = 0; q < 800; q++) {
                    int x = (int) Math.ceil(Math.random() * (p.size() - 1));

                    int y = (int) Math.ceil(Math.random() * (p.size() - 1));
                    while (x == y)  {
                        y = (int) Math.ceil(Math.random() * (p.size() - 1));
                    }


                    Collections.swap(p, x, y);
                }


                if (i == 0) time = time + runs1.apply(p);
                else if(i == 1) time = time + inv1.apply(p);
                else if(i == 2) time = time + runs2.apply(p);
                else time = time + inv2.apply(p);


            }


            time = time / 500;

            if (i == 0) System.out.println("Linear regression ratio of runs: average elapsed time: " + (Math.round(time * 100) / 100) + "ms");
            else if (i == 1)    System.out.println("Linear regression ratio of inversions: average elapsed time: " + (Math.round(time * 100) / 100) + "ms");
            else if (i == 2)    System.out.println("Linear interpolation ratio of runs: average elapsed time: " + (Math.round(time * 100) / 100) + "ms");
            else System.out.println("Linear interpolation ratio of inversions: average elapsed time: " + (Math.round(time * 100) / 100) + "ms");

        }
    }

    /**
     * Computes the most optimal threshold for runs and inversions in consideration to the least CPU time.
     *
     * @param n     the length of the list to be sorted to measure the CPU time of the algorithm
     * @param swaps the maximum number of permutations to be performed in order to generate the random permutations
     * @param bins  the number of bins in which the key figures of runs and inversions are to be grouped respectively
     * @param gamma the minimum proportion of the threshold to be tried for a bin should be tried for a bin to determine a valid
     *              result
     * @return the most optimal threshold for runs and inversions
     */
    public static @Nullable Integer[][] computeOptimalThresholds(int n, int swaps, int bins, double gamma) {
        Integer[][] result = new Integer[2][bins];

        //how many u's (umschaltlängen)
        int o = 0;
        for (int m = 0; Math.ceil((double) n / Math.pow(2, m)) > 1;  m++)   o++;

        boolean[][][] exists = new boolean[2][bins][o];


        //all permutations of u and s
        for (int x = 0; x < 2; x++) {
            Long[][] time = new Long[2][bins];


            for (int m = 0; Math.ceil((double) n / Math.pow(2, m)) > 1;  m++)   {
                int u = (int) Math.ceil((double) n / Math.pow(2, m));


                for (int s = 0; s <= swaps; s++)    {

                    List<Integer> p = createRandomSequence(n, x, s);


                    MyCollections<Integer> collections = new MyCollections<>(
                        new ConstantListToIntFunction<Integer>(u),
                        Comparator.naturalOrder()
                    );


                    //determines runs
                    int runs = 1;
                    Iterator<Integer> iterator1 = p.iterator();
                    Integer last = iterator1.next();
                    while (iterator1.hasNext())    {
                        Integer element = iterator1.next();

                        if (last > element)  runs++;

                        last = element;
                    }
                    //determines inversions
                    int inv = 0;
                    for (int i = 0; i < p.size(); i++)  {

                        for (int j = i + 1; j < p.size(); j++)  {

                            if (p.get(i) < p.get(j))    inv++;

                        }
                    }






                    ThreadMXBean bean = ManagementFactory.getThreadMXBean();
                    if ( ! bean.isCurrentThreadCpuTimeSupported() ) return null;
                    long startTimeCpu = bean.getCurrentThreadCpuTime();

                    collections.sort(p);

                    long cpuTime = bean.getCurrentThreadCpuTime() - startTimeCpu;


                    assert isSorted(p, Comparator.naturalOrder());


                    int indexR = (int) Math.floor( (double) (bins * (runs - 1)) / n);

                    if (time[0][indexR] == null)   {
                        exists[0][indexR][m] = true;

                        time[0][indexR] = cpuTime;

                        result[0][indexR] = u;

                    }else {
                        exists[0][indexR][m] = true;

                        if (time[0][indexR] > cpuTime)  {
                            time[0][indexR] = cpuTime;

                            result[0][indexR] = u;
                        }
                        else if (time[0][indexR] == cpuTime && result[0][indexR] > u)    {
                            time[0][indexR] = cpuTime;

                            result[0][indexR] = u;
                        }


                    }


                    int indexI = (int) Math.abs(Math.floor( (double) (bins * inv) / ( ( (double) (n * (n - 1) ) / 2 ) + 1 ) ));


                    if (time[1][indexI] == null)   {

                        exists[1][indexI][m] = true;

                        time[1][indexI] = cpuTime;

                        result[1][indexI] = u;

                    }else {
                        exists[1][indexI][m] = true;

                        if (time[1][indexI] > cpuTime)  {

                            time[1][indexI] = cpuTime;

                            result[1][indexI] = u;

                        }
                        else if (time[1][indexI] == cpuTime && result[1][indexI] > u)    {
                            time[1][indexI] = cpuTime;

                            result[1][indexI] = u;
                        }
                    }


                }

            }
        }


        //cleanUp
        for (int rORi = 0; rORi < 2; rORi++)    {

            for (int bin = 0; bin < bins; bin++)    {

                int numberOf = 0;
                for (int length = 0; length < o ;length++)  {
                    if (exists[rORi][bin][length]) numberOf++;
                }


                boolean ifNull = numberOf > ( (gamma * Math.ceil(Math.log(n))) / (Math.log(2)));

                if (ifNull) result[rORi][bin] = null;
            }

        }






        return result;
    }

    /**
     * Returns {@code true} if the list is sorted, {@code false} otherwise.
     *
     * @param list the list to be checked
     * @param cmp  the comparator used to compare elements
     * @param <T>  the type of the elements
     * @return {@code true} if the list is sorted, {@code false} otherwise
     */
    private static <T> boolean isSorted(List<T> list, Comparator<? super T> cmp) {
        Iterator<T> it = list.iterator();
        // Empty list
        if (!it.hasNext()) {
            return true;
        }

        T previous = it.next();
        while (it.hasNext()) {
            T current = it.next();
            if (cmp.compare(previous, current) > 0) {
                return false;
            }
            previous = current;
        }
        return true;
    }


    /**
     * Creates a random sequence
     * @param length length of the result
     * @param order determines the order at begin
     * @param swaps number of swaps
     * @return unordered list
     */
    private static List<Integer> createRandomSequence(int length, int order, int swaps) {
        List<Integer> result = new ArrayList<>();


        //create sequence
        if (order == 0) {
            for (int i = 1; i <= length; i++)   result  .add(i);
        }else for (int i = length; i > 0; i--)   result.add(i);


        //swaps s elements
        for (int q = 0; q < swaps; q++) {
            int i = (int) Math.ceil(Math.random() * (result.size() - 1));
            int j = i;

            while (i == j)  {
                j = (int) Math.ceil(Math.random() * (result.size() - 1));
            }


            Collections.swap(result, i, j);
        }


        return result;
    }
}

