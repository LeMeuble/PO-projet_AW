import fr.istic.util.OptionSelector;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OptionSelectorTest {

    @Test
    public void addOptionSingleTrue() {

        OptionSelector<Integer> test = new OptionSelector<>();
        test.addOption(1);

        assertEquals(1, test.getOptionsCount());
        assertEquals(1, test.getAvailableOptionsCount());

        assertTrue(test.getValues().contains(1));

        assertFalse(test.getValues().contains(2));

    }

    @Test
    public void addOptionSingleFalse() {

        OptionSelector<Integer> test = new OptionSelector<>();
        test.addOption(1, false);

        assertEquals(1, test.getOptionsCount());
        assertEquals(0, test.getAvailableOptionsCount());

        assertTrue(test.getValues().contains(1));

        assertFalse(test.getValues().contains(2));

    }

    @Test
    public void addOptionSingleDuplicate() {

        OptionSelector<Integer> test = new OptionSelector<>();
        test.addOption(1);
        test.addOption(1, false);

        assertEquals(2, test.getOptionsCount());
        assertEquals(1, test.getAvailableOptionsCount());

        assertTrue(test.getValues().contains(1));
        assertFalse(test.getValues().contains(2));
    }

    @Test
    public void addOptionMultiple() {

        OptionSelector<Integer> test = new OptionSelector<>();
        test.addOption(1);
        test.addOption(2, false);

        assertEquals(2, test.getOptionsCount());
        assertEquals(1, test.getAvailableOptionsCount());

        assertTrue(test.getValues().contains(1));
        assertTrue(test.getValues().contains(2));
        assertTrue(test.getAvailableValues().contains(1));

        assertFalse(test.getAvailableValues().contains(2));

    }

    @Test
    public void fromListAllAvailable() {


        List<Integer> liste = Arrays.asList(1, 2, 3, 4, 5);

        OptionSelector<Integer> test = new OptionSelector<>(liste);

        assertEquals(5, test.getOptionsCount());
        assertEquals(5, test.getAvailableOptionsCount());

        assertTrue(test.getValues().contains(1));
        assertTrue(test.getValues().contains(2));
        assertTrue(test.getValues().contains(3));
        assertTrue(test.getValues().contains(4));
        assertTrue(test.getValues().contains(5));

        assertTrue(test.getAvailableValues().contains(1));

        assertFalse(test.getAvailableValues().contains(7));

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

        assertEquals(0, test.getSelectedValue());
        test.next();
        assertEquals(1, test.getSelectedValue());
        test.next();
        assertEquals(2, test.getSelectedValue());
        test.next();
        assertEquals(0, test.getSelectedValue());

    }

    @Test
    public void nextNotAllTrue() {

        OptionSelector<Integer> testFirstFalse = new OptionSelector<>();
        testFirstFalse.addOption(0, false);
        testFirstFalse.addOption(1);
        testFirstFalse.addOption(2);

        assertEquals(1, testFirstFalse.getSelectedValue());
        testFirstFalse.next();
        assertEquals(2, testFirstFalse.getSelectedValue());
        testFirstFalse.next();
        assertEquals(1, testFirstFalse.getSelectedValue());


        OptionSelector<Integer> testSecondFalse = new OptionSelector<>();
        testSecondFalse.addOption(0);
        testSecondFalse.addOption(1, false);
        testSecondFalse.addOption(2);

        assertEquals(0, testSecondFalse.getSelectedValue());
        testSecondFalse.next();
        assertEquals(2, testSecondFalse.getSelectedValue());
        testSecondFalse.next();
        assertEquals(0, testSecondFalse.getSelectedValue());


        OptionSelector<Integer> testLastFalse = new OptionSelector<>();
        testLastFalse.addOption(0);
        testLastFalse.addOption(1);
        testLastFalse.addOption(2, false);

        assertEquals(0, testLastFalse.getSelectedValue());
        testLastFalse.next();
        assertEquals(1, testLastFalse.getSelectedValue());
        testLastFalse.next();
        assertEquals(0, testLastFalse.getSelectedValue());

    }


}