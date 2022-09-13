package h03;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * A class that represents a function with a given alphabet.
 */
public class SelectionOfCharsIndex implements FunctionToInt<Character> {
    /**
     * The chars of the objects' alphabet.
     */
    private final char[] theChars;

    /**
     * Constructs a new SelectionOfCharsIndex object with the given alphabet scope.
     * The given alphabet must not be null, has to contain at least one element and each element has to be unique.
     *
     * @param theAlphabet The given alphabet.
     */
    public SelectionOfCharsIndex(List<Character> theAlphabet) {

        //catches empty lists
        if (theAlphabet == null || theAlphabet.isEmpty())  {
            this.theChars = null;
            return;
        }
        else    {

            //searches for duplicate characters in the alphabet
            for (int i = 0; i < theAlphabet.size(); i++) {
                for (int j = i + 1; j < theAlphabet.size(); j++)    {
                    if (theAlphabet.get(i) == theAlphabet.get(j))   {
                        this.theChars = null;
                        return;
                    }
                }
            }


            //if everything is right
            this.theChars = new char[theAlphabet.size()];
        }





        //fills array with theAlphabet
        int index = 0;
        for (Character character : theAlphabet) {
            this.theChars[index++] = character;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int sizeOfAlphabet() {
        return this.theChars.length;
    }

    /**
     * Returns the index at which the given parameter is contained in the alphabet.
     *
     * @param character The given parameter to be searched for.
     * @return The index of the given parameter.
     * @throws IllegalArgumentException Iff the given parameter is not contained in the alphabet.
     */
    @Override
    public int apply(Character character) throws IllegalArgumentException {
        if (character == null) throw new IllegalArgumentException();

        //goes through the alphabet and compares
        int i = 0;
        for (char chars : theChars) {
            if (chars == character) return i;
            i++;
        }

        //when the character is not in the alphabet
        throw new IllegalArgumentException();
    }
}

