package h03;

import java.util.Arrays;

/**
 * An abstract class that contains a function (FunctionToInt) and can compute the length of partial string matches of a given string of type T.
 *
 * @param <T> The type of objects to be searched through by an object of this class.
 */
public abstract class PartialMatchLengthUpdateValues<T> {
    /**
     * The function to be used in this object.
     */
    protected FunctionToInt<T> fct;

    /**
     * Constructs a PartialMatchLengthUpdateValues object with the given function.
     *
     * @param fct The function to be used by this object.
     */
    public PartialMatchLengthUpdateValues(FunctionToInt<T> fct) {
        this.fct = fct;
    }

    /**
     * Returns the next state that will be entered when using the given letter from the given state.
     *
     * @param state  The current state.
     * @param letter The letter to be added.
     * @return The next state.
     */
    public abstract int getPartialMatchLengthUpdate(int state, T letter);

    /**
     * Returns the length of the search string used in this object.
     *
     * @return The length of the search string.
     */
    public abstract int getSearchStringLength();

    /**
     * Returns the amount of elements k in searchString so that the first k elements of searchString match the last k elements of searchString.
     *
     * @param searchString The searchString to search through.
     * @return The amount k.
     */
    protected int computePartialMatchLengthUpdateValues(T[] searchString) {
        //return value
        int result = 0;


        boolean lever = true;

        for (int i = 1; i < searchString.length; i++)   {

            //tests possiblest start
            if (fct.apply(searchString[i])  == fct.apply(searchString[0])) {


                int current = i;
                int j = 0;

                //compares string at index 0 with string at possible start
                for (int searchStingIndex = 0; searchStingIndex < searchString.length && current < searchString.length; searchStingIndex++)   {

                    if (fct.apply(searchString[searchStingIndex]) != fct.apply(searchString[current++])) {
                        lever = false;
                        break;
                    }
                    j++;
                }

                if (lever)  {
                    result = j;
                    break;
                }else lever = true;

            }
        }


        return result;
    }
}

