package h03;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class that represents string matching BOFA using an intern array of lists of transitions -
 * basically an array containing the states and their possible transitions to other states.
 *
 * @param <T> The type of the function/letters of the used alphabet.
 */
public class PartialMatchLengthUpdateValuesAsAutomaton<T> extends PartialMatchLengthUpdateValues<T> {
    /**
     * The states of the automaton as a list of its transitions.
     */
    private List<Transition<T>>[] theStates;

    /**
     * Constructs a PartialMatchLengthUpdateValuesAsAutomaton object with the given function and search string.
     * This is done by creating the private array of this object by creating the various lists and their possible transitions to other states.
     *
     * @param fct          The function to be used.
     * @param searchString The search string to be used.
     */
    public PartialMatchLengthUpdateValuesAsAutomaton(FunctionToInt<T> fct, T[] searchString) {
        super(fct);

        theStates = (List<Transition<T>>[]) new List[searchString.length + 1];


        //create lookup table
        for (int state = 0; state < searchString.length + 1; state++)   {

            List<Transition<T>> newList = new ArrayList<>();


            //iterates through the different inputs
            for (int input = 0; input < fct.sizeOfAlphabet(); input++) {


                if (state != searchString.length && input == fct.apply(searchString[state]))    {
                    newList.add(new Transition<>(state + 1, Stream.of(searchString[state]).toList()));
                }
                else {

                    T t = null;
                    for (T ts : searchString)   {
                        if (fct.apply(ts) == input) t = ts;
                    }

                    if (t != null)  {
                        T[] currentInput = Arrays.copyOfRange(searchString, 0, state + 1);
                        currentInput[currentInput.length - 1] = t;

                        int k = computePartialMatchLengthUpdateValues(currentInput);

                        if (k != 0) {
                            newList.add(new Transition<>(k, Stream.of(t).toList()));
                        }

                    }

                }

            }



            theStates[state] = newList;
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getPartialMatchLengthUpdate(int state, T letter) {
        int result = 0;

        List<Transition<T>> searchedList = this.theStates[state];
        Iterator<Transition<T>> searchIterator = searchedList.iterator();

        while (searchIterator.hasNext())    {
            Transition<T> transition = searchIterator.next();

            if (transition.LETTERS.contains(letter))    result = transition.J;
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getSearchStringLength() {
        return  this.theStates.length - 1;
    }
}

