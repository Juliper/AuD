package h04.function;

import org.jetbrains.annotations.NotNull;

import java.util.Comparator;
import java.util.List;

/**
 * Represents a function that accepts a list-valued argument and produces an int-valued result.
 *
 * <p>The function values are calculated by the ratio of runs to the number of elements.
 *
 * @author Nhan Huynh
 */
public class FunctionOnRatioOfRuns<T> extends FunctionOnDegreeOfDisorder<T> {

    /**
     * The function to be applied to the ratio of runs.
     */
    private final DoubleToIntFunction function;

    /**
     * Constructs and initializes a {@code FunctionOnRatioOfRuns}.
     *
     * @param cmp      the comparator used to compare the elements of the list
     * @param function the function to be applied to the ratio of runs
     */
    public FunctionOnRatioOfRuns(Comparator<? super T> cmp, DoubleToIntFunction function) {
        super(cmp);
        this.function = function;
    }

    @Override
    public int apply(List<T> elements) {
        //WORKS

        if (elements == null) throw new NullPointerException();
        if (elements.isEmpty()) return function.apply(0);


        int runs = 1;
        T last = null;
        for (T t : elements)    {

            if (last != null)   {
                if (this.cmp.compare(last, t) > 0)  {
                    runs++;
                }
            }

            last = t;
        }

        return function.apply( ((double) runs) / ((double) elements.size()));
    }
}

