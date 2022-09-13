package h04.function;

import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

/**
 * Fits a function to a set of data points.
 *
 * <p>The function has the following form:
 * <ol>
 *   <li>Create a double-array and copy all indices values of y to the new array which are not {@code null}.</li>
 *   <li>The indices containing {@code null} values are linearly interpolated using the next left and right known function
 *   values.</li>
 *   <li>Create an int-array and round all values.</li>
 *   <li>Create a {@code ArrayDoubleToIntFunction} containing the rounded values.</li>
 * </ol>
 *
 * @author Nhan Huynh
 */
public class LinearInterpolation implements DoubleToIntFunctionFitter {

    @Override
    public DoubleToIntFunction fitFunction(@Nullable Integer[] y) {



        double[] doubles = new double[y.length];
        int[] result = new int[y.length];

        double x0 = 0;
        double f0 = 0;

        for (int j = 0; j < y.length; j++)  {

            if (y[j] != null)   {
                f0 = y[j];
                x0 = j;


                doubles[j] = y[j];
                result[j] = y[j];
            }
            else {
                double x1 = j;
                while (x1 < y.length && y[(int) x1] == null)   x1++;

                double f1 = 0;

                if (x1 == y.length) {
                    x1 = x0;
                    f1 = f0;
                }else {
                    f1 = y[(int) x1];
                }



                doubles[j] = f0 * ((x1 - j) / (x1 - x0)) + f1 * ((j - x0) / (x1 - x0));
                result[j] = (int) Math.round(doubles[j]);

                x0 = j;
                f0 = result[j];
            }

        }

        return new ArrayDoubleToIntFunction(result);
    }
}

