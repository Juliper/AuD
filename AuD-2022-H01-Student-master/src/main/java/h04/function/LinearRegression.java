package h04.function;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Fits a function to a set of data points.
 *
 * <p>The function has the following form:
 * <pre>{@code
 *    n = number of data points
 *    samples = number of non null data points
 *    x = i / (n - 1)
 *    y = f(x)
 *    x^bar = sum(x_i) / samples
 *    y^bar = sum(y_i) / samples
 *    beta_1 = sum_i^n [(x_i - x hat) * (y_i - y hat)] / sum_i^n [(x_i - x hat)^2]
 *    beta_2 = y^bar - beta_1 * x^bar
 *
 *    Fitter(x, y) = beta_1 * x + beta_2
 * }</pre>
 *
 * @author Nhan Huynh
 */
public class LinearRegression implements DoubleToIntFunctionFitter {

    @Override
    public DoubleToIntFunction fitFunction(@Nullable Integer[] y) {

        double xs = 0;
        double ys = 0;
        int number = 0;


        for (int i = 0; i < y.length; i++)  {


            if (y[i] != null)   {
                double xi = (double) i / (double) (y.length - 1);

                xs = xs + xi;
                ys = ys + y[i];

                number++;
            }

        }


        xs = xs / number;
        ys = ys / number;



        double firstSum = 0;
        double secondSum = 0;

        for (int i = 0; i < y.length; i++)  {


            if (y[i] != null)   {
                double xi = (double) i / (double) (y.length - 1);

                firstSum = firstSum + (xi - xs) * ((double) y[i] - ys);
                secondSum = secondSum + Math.pow(xi - xs, 2);

            }

        }



        double beta1 = firstSum / secondSum;

        double beta2 = ys - beta1 * xs;

        return new LinearDoubleToIntFunction(beta1, beta2);
    }
}

