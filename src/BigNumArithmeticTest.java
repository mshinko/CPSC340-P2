import static org.junit.Assert.*;
import org.junit.Test;
public class BigNumArithmeticTest {
    @Test
    public void Add_test()
    {
        BigNumArithmetic b = new BigNumArithmetic();
        assertEquals("3",b.performAddition("2", "1"));
    }
    @Test
    public void Multiply_test()
    {
        BigNumArithmetic b = new BigNumArithmetic();
        assertEquals("12000",b.performMultiplication("20", "600"));
        assertEquals("13104",b.performMultiplication("56", "234"));
        assertEquals("100",b.performMultiplication("10", "10"));
        assertEquals("120000",b.performMultiplication("200", "600"));
    }

}