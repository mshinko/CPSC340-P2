import static org.junit.Assert.*;
import org.junit.Test;
public class BigNumArithmeticTest {
    @Test
    public void Add_test()
    {
        BigNumArithmetic b = new BigNumArithmetic();
        assertEquals("3",b.performAddition("2", "1"));
        assertEquals("2500",b.performAddition("1200", "1300"));
    }
    @Test
    public void testAppend() {
        // Create a new LList
        LList list = new LList();

        // Test case 1: Appending to an empty list
        assertTrue(list.isEmpty());
        assertTrue(list.append(5));
        assertFalse(list.isEmpty());
        assertEquals(1, list.length());

        // Test case 2: Appending to a non-empty list
        assertTrue(list.append(10));
        assertEquals(2, list.length());

        // Test case 3: Appending multiple elements
        assertTrue(list.append(15));
        assertTrue(list.append(20));
        assertEquals(4, list.length());
    }
    @Test
    public void Multiply_test()
    {
        BigNumArithmetic b = new BigNumArithmetic();
        assertEquals("12000",b.performMultiplication("20", "600"));
        assertEquals("13104",b.performMultiplication("56", "234"));
        assertEquals("100",b.performMultiplication("10", "10"));
        assertEquals("120000",b.performMultiplication("200", "600"));
        assertEquals("0",b.performMultiplication("200", "0"));
    }
    @Test
    public void Subtraction_test()
    {
        BigNumArithmetic b = new BigNumArithmetic();
        assertEquals("0",b.performSubtraction("600", "600"));
        assertEquals("12",b.performSubtraction("8", "20"));
        assertEquals("580",b.performSubtraction("20", "600"));
    }


    @Test
    public void testStringToLL() {
        BigNumArithmetic converter = new BigNumArithmetic();

        // Test case 1: Normal input
        String number1 = "12345";
        LList expected1 = new LList();
        expected1.append(5);
        expected1.append(4);
        expected1.append(3);
        expected1.append(2);
        expected1.append(1);
        assertEquals(expected1.getValue().toString(), converter.stringToLL(number1).getValue().toString());

        // Test case 2: Input with leading zeros
        String number2 = "000123";
        LList expected2 = new LList();
        expected2.append(3);
        expected2.append(2);
        expected2.append(1);
        assertEquals(expected2.getValue().toString(), converter.stringToLL(number2).getValue().toString());

        // Test case 3: Input with all zeros
        String number3 = "0000";
        LList expected3 = new LList();
        expected3.append(0);
        assertEquals(expected3.getValue().toString(), converter.stringToLL(number3).getValue().toString());

        // Test case 4: Input with a single non-zero digit
        String number4 = "7";
        LList expected4 = new LList();
        expected4.append(7);
        assertEquals(expected4.getValue().toString(), converter.stringToLL(number4).getValue().toString());


    }
    @Test
    public void testLLToString() {
        BigNumArithmetic converter = new BigNumArithmetic();

        // Test case 1: Empty list
        LList list1 = new LList();
        String expected1 = "";
        assertEquals(expected1, converter.LLtoString(list1));

        // Test case 2: List with single element
        LList list2 = new LList();
        list2.append(5);
        String expected2 = "5";
        assertEquals(expected2, converter.LLtoString(list2));

        // Test case 3: List with multiple elements
        LList list3 = new LList();
        list3.append(1);
        list3.append(2);
        list3.append(3);
        String expected3 = "123";
        assertEquals(expected3, converter.LLtoString(list3));

        // Test case 4: List with zero values
        LList list4 = new LList();
        list4.append(0);
        list4.append(0);
        list4.append(0);
        String expected4 = "000";
        assertEquals(expected4, converter.LLtoString(list4));
    }

    @Test
    public void testEmptyInputFile() {
        String[] args = new String[]{""};
        BigNumArithmetic.main(args); // This should not throw any exceptions
    }

}