package h01;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;
import java.io.*;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Main entry point in executing the program.
 */
public class Main {

    /**
     * Main entry point in executing the program.
     *
     * @param args program arguments, currently ignored
     */
    public static void main(String[] args) {

        ListItem<ListItem<Double>> mainList = new ListItem<>();
        ListItem<ListItem<Double>> changedMainList = mainList;



        ListItem<Double> secondaryList1 = new ListItem<>();
        secondaryList1.next = new ListItem<>();
        secondaryList1.next.next = new ListItem<>();

        secondaryList1.key = 1.0;
        secondaryList1.next.key = 3.0;
        secondaryList1.next.next.key = 5.0;



        ListItem<Double> secondaryList2 = new ListItem<>();



        ListItem<Double> secondaryList3 = new ListItem<>();
        secondaryList3.next = new ListItem<>();
        secondaryList3.next.next = new ListItem<>();
        secondaryList3.next.next.next = new ListItem<>();
        secondaryList3.next.next.next.next = new ListItem<>();

        secondaryList3.key = 1.0;
        secondaryList3.next.key = 2.0;
        secondaryList3.next.next.key = 3.0;
        secondaryList3.next.next.next.key = 4.0;
        secondaryList3.next.next.next.next.key = 5.0;




        changedMainList.next = new ListItem<>();
        changedMainList.next.next = new ListItem<>();

        changedMainList.key = secondaryList1;
        changedMainList.next.key = secondaryList2;
        changedMainList.next.next.key = secondaryList3;




        double limitInPlace = 5.0;
        ListItem<ListItem<Double>> resultInPlace = DoubleListOfListsProcessor.partitionListsInPlaceIteratively(mainList, limitInPlace);

        for(ListItem<ListItem<Double>> q = resultInPlace; q != null; q = q.next){
            System.out.print("(");
            for(ListItem<Double> p = q.key; p != null; p = p.next){
                if(p.key == null){
                    System.out.print("");
                }else
                    System.out.print(p.key);
                if(p.next != null){
                    System.out.print(", ");
                }

            }
            System.out.println(")");
        }




        ListItem<Double> test1 = new ListItem<>();
        test1.key = 3.0;
        test1.next = new ListItem<>();
        test1.next.key = 2.0;
        test1.next.next = new ListItem<>();
        test1.next.next.key = 1.0;

        ListItem<Double> test2 = new ListItem<>();
        test2.key = 1.0;
        test2.next = new ListItem<>();
        test2.next.key = 3.0;
        test2.next.next = new ListItem<>();
        test2.next.next.key = 2.0;

        ListItem<Double> test3 = new ListItem<>();
        test3.key = 1.0;
        test3.next = new ListItem<>();
        test3.next.key = 3.0;

        ListItem<ListItem<Double>> test = new ListItem<>();
        test.key = test1;
        test.next = new ListItem<>();
        test.next.key = test2;
        test.next.next = new ListItem<>();
        test.next.next.key = test3;
        test.next.next.next = new ListItem<>();



        ListItem<ListItem<Double>> result = DoubleListOfListsProcessor.partitionListsInPlaceIteratively(test, 3);

        for (ListItem<ListItem<Double>> i = result; i != null; i = i.next) {
            System.out.println("New Sublist");
            if(i.key == null) System.out.println("empty");

            for (ListItem<Double> j = i.key; j != null; j = j.next) {
                System.out.println(j.key);
            }

        }
    }

}
