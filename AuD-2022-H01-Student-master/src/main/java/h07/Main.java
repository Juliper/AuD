package h07;

import java.util.*;
import java.util.function.Function;

/**
 * Main entry point in executing the program.
 */
public class Main {
    protected static final Comparator<Integer> CMP = Integer::compare;
    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(8);
        list.add(4);
        list.add(5);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(4);
        list.add(8);
        list.add(10);

        bubbleSort(list);

        System.out.println(list);
    }


    public static List<Integer> match(final String haystack, final String needle) {
        if(haystack == null || needle == null || haystack.length() < needle.length())	{
            return null;
        }

        List<Integer> startIndices = new ArrayList<>();


        for(int i = 0; i < haystack.length(); i++)	{
            if (i + needle.length() > haystack.length())    break;

            for(int j = 0; j < needle.length(); j++)	{
                if(haystack.charAt(i + j) != needle.charAt(j))	break;
                else {
                    if(j == needle.length() - 1)	startIndices.add(i);
                }

            }

        }

        return startIndices;
    }


    public static List<Integer> bubbleSort(final List<Integer> list) {
        Comparator<Integer> cmp = Comparator.naturalOrder();

        for(int i = 0; i < list.size(); i++)	{

            for(int j = 0; j < (list.size() - 1) - i ; j++)	{

                if(cmp.compare(list.get(j), list.get(j + 1)) > 0)	{
                    Integer tmp = list.get(j);

                    list.set(j, list.get(j + 1));
                    list.set(j + 1, tmp);
                }

            }

        }

        return list;
    }
}
