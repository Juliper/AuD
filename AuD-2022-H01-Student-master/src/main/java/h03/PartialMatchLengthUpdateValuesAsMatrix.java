package h03;


import java.util.Arrays;

/**
 * A class that represents string matching BOFA using an intern matrix.
 *
 * @param <T> The type of the function/letters of the used alphabet.
 */
public class PartialMatchLengthUpdateValuesAsMatrix<T> extends PartialMatchLengthUpdateValues<T> {
    /**
     * The matrix of this object in which the lookup-table is implemented.
     */
    public int[][] matrix;

    /**
     * Constructs a PartialMatchLengthUpdateValuesAsMatrix object with the given function and search string.
     * This is done by creating the private matrix of this object so that it may be used to look up next possible states.
     *
     * @param fct          The function to be used.
     * @param searchString The search string to be used.
     */
    public PartialMatchLengthUpdateValuesAsMatrix(FunctionToInt<T> fct, T[] searchString) {
        super(fct);


        //main array possible inputs, sub array current state
        this.matrix = new int[searchString.length + 1][fct.sizeOfAlphabet()];


        //create lookup table
        for (int state = 0; state <= searchString.length; state++)    {


            //iterates through the different inputs
            for (int input = 0; input < fct.sizeOfAlphabet(); input++) {


                if (state != searchString.length && input == fct.apply(searchString[state]))    this.matrix[state][input] = state + 1;
                else {

                    T t = null;
                    for (T ts : searchString)   {
                        if (fct.apply(ts) == input) t = ts;
                    }

                    if (t == null)  this.matrix[state][input] = 0;
                    else {
                        T[] currentInput = Arrays.copyOfRange(searchString, 0, state + 1);
                        currentInput[state] = t;


                        this.matrix[state][input] = computePartialMatchLengthUpdateValues(currentInput);
                    }

                }


            }




        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPartialMatchLengthUpdate(int state, T letter) {
        return this.matrix[state][this.fct.apply(letter)];
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSearchStringLength() {
        return this.matrix.length - 1;
    }
}

