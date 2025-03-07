package comp3350.quizrus.tests.business;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import comp3350.quizrus.business.Random;

public class RandomTest {
    @Test
    public void testRandom() {
        System.out.println("\nStarting RandomTest");
        List<Integer> intList1 = new ArrayList<>();
        // Add it from 1 to 100, so the list will be [1,2,3,...,100]
        for (int i = 0; i < 100; i++) {
            intList1.add(i);
        }
        System.out.println("Testing if it shuffle");
        Random.randomizeListItem(intList1);
        assertFalse(isSorted(intList1));

        System.out.println("Testing if passed in list is null");
        assertFalse(Random.randomizeListItem(null));

        System.out.println("Testing if passed in list is one item");
        List<Integer> intList2 = new ArrayList<>();
        intList2.add(1);
        assertEquals((int) intList2.get(0), 1);

        System.out.println("Testing if passed in empty list");
        List<Integer> intList3 = new ArrayList<>();
        assertFalse(Random.randomizeListItem(intList3));

        System.out.println("Finished RandomUser");
        System.out.println();
    }

    private boolean isSorted(List<Integer> integerList) {
        boolean isSorted = true;
        for (int i = 0; i < integerList.size(); i++) {
            if (integerList.get(i) != i) {
                isSorted = false;
            }
        }
        return isSorted;
    }
}
