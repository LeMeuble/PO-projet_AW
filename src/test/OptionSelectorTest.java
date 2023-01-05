package test;

import main.util.OptionSelector;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OptionSelectorTest {

    @Test
    public void addOptionSingleTrue() {

        OptionSelector<Integer> test = new OptionSelector<>();
        test.addOption(1);

        assertEquals( 1, test.getOptionsCount());
        assertEquals(1, test.getAvailableOptionsCount());

        assertTrue(test.getOptions().contains(1));

        assertFalse(test.getOptions().contains(2));

    }

    @Test
    public void addOptionSingleFalse() {

        OptionSelector<Integer> test = new OptionSelector<>();
        test.addOption(1, false);

        assertEquals(1, test.getOptionsCount());
        assertEquals(0, test.getAvailableOptionsCount());

        assertTrue(test.getOptions().contains(1));

        assertFalse(test.getOptions().contains(2));

    }

    @Test
    public void addOptionSingleDuplicate() {

        OptionSelector<Integer> test = new OptionSelector<>();
        test.addOption(1);
        test.addOption(1, false);

        assertEquals(1, test.getOptionsCount());
        assertEquals(1, test.getAvailableOptionsCount());

        assertTrue(test.getOptions().contains(1));

        assertFalse(test.getOptions().contains(2));
    }

    @Test
    public void addOptionMultiple() {

        OptionSelector<Integer> test = new OptionSelector<>();
        test.addOption(1);
        test.addOption(2, false);

        assertEquals(2, test.getOptionsCount());
        assertEquals(1, test.getAvailableOptionsCount());

        assertTrue(test.getOptions().contains(1));
        assertTrue(test.getOptions().contains(2));
        assertTrue(test.getAvailableOptions().contains(1));

        assertFalse(test.getAvailableOptions().contains(2));

    }

    @Test
    public void fromListAllAvailable() {


        List<Integer> liste = Arrays.asList(1, 2, 3, 4, 5);

        OptionSelector<Integer> test = new OptionSelector<>(liste);

        assertEquals(5, test.getOptionsCount());
        assertEquals(5, test.getAvailableOptionsCount());

        assertTrue(test.getOptions().contains(1));
        assertTrue(test.getOptions().contains(2));
        assertTrue(test.getOptions().contains(3));
        assertTrue(test.getOptions().contains(4));
        assertTrue(test.getOptions().contains(5));

        assertTrue(test.getAvailableOptions().contains(1));

        assertFalse(test.getAvailableOptions().contains(7));

    }

    @Test
    public void contains() {

        OptionSelector<Integer> test = new OptionSelector<>();
        test.addOption(1);

        assertTrue(test.contains(1));

        assertFalse(test.contains(2));

    }

    @Test
    public void nextAllTrue() {

        OptionSelector<Integer> test = new OptionSelector<>();
        test.addOption(0);
        test.addOption(1);
        test.addOption(2);

        assertEquals(0, test.getSelectedOption());
        test.next();
        assertEquals(1, test.getSelectedOption());
        test.next();
        assertEquals(2, test.getSelectedOption());
        test.next();
        assertEquals(0, test.getSelectedOption());

    }

    @Test
    public void nextNotAllTrue() {

        OptionSelector<Integer> testFirstFalse = new OptionSelector<>();
        testFirstFalse.addOption(0, false);
        testFirstFalse.addOption(1);
        testFirstFalse.addOption(2);

        assertEquals(1, testFirstFalse.getSelectedOption());
        testFirstFalse.next();
        assertEquals(2, testFirstFalse.getSelectedOption());
        testFirstFalse.next();
        assertEquals(1, testFirstFalse.getSelectedOption());


        OptionSelector<Integer> testSecondFalse = new OptionSelector<>();
        testSecondFalse.addOption(0);
        testSecondFalse.addOption(1, false);
        testSecondFalse.addOption(2);

        assertEquals(0, testSecondFalse.getSelectedOption());
        testSecondFalse.next();
        assertEquals(2, testSecondFalse.getSelectedOption());
        testSecondFalse.next();
        assertEquals(0, testSecondFalse.getSelectedOption());


        OptionSelector<Integer> testLastFalse = new OptionSelector<>();
        testLastFalse.addOption(0);
        testLastFalse.addOption(1);
        testLastFalse.addOption(2, false);

        assertEquals(0, testLastFalse.getSelectedOption());
        testLastFalse.next();
        assertEquals(1, testLastFalse.getSelectedOption());
        testLastFalse.next();
        assertEquals(0, testLastFalse.getSelectedOption());

    }

}