import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BigNumArithmeticTest {
    @Test
    public void Add_test()
    {
        BigNumArithmetic b = new BigNumArithmetic();
        assertEquals("3",b.performAddition("2", "1"));
    }


}