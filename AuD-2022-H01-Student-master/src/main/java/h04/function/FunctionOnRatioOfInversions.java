package h04.function;

import java.util.Comparator;
import java.util.List;

/**
 * Represents a function that accepts a list-valued argument and produces an int-valued result.
 *
 * <p>The function values are calculated by the ratio of inversions to the maximum number of inversions.
 *
 * @author Nhan Huynh
 */
public class FunctionOnRatioOfInversions<T> extends FunctionOnDegreeOfDisorder<T> {

    /**
     * The function to be applied to the ratio of runs.
     */
    private final DoubleToIntFunction function;

    /**
     * Constructs and initializes a {@code FunctionOnRatioOfInversions}.
     *
     * @param cmp      the comparator used to compare the elements of the list
     * @param function the function to be applied to the ratio of runs
     */
    public FunctionOnRatioOfInversions(Comparator<? super T> cmp, DoubleToIntFunction function) {
        super(cmp);
        this.function = function;
    }

    @Override
    public int apply(List<T> elements) {
        //WORKS

        //exception guard
        if (elements == null) throw new NullPointerException();


        int inversions = 0;
        int possibleInversions = 0;


        for (int i = 0; i < elements.size() - 1; i++)  {

            for (int j = i + 1; j < elements.size(); j++)  {

                if (cmp.compare(elements.get(i), elements.get(j)) > 0)  inversions++;

                possibleInversions++;
            }

        }


        return function.apply( (possibleInversions == 0) ? 0 : ((double) inversions) / ((double) possibleInversions));
    }
}

