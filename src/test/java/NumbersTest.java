import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Simple unit test.
 */
public class NumbersTest {
    @Test
    public void sumCorrectForPositiveNumbers(){
        Numbers numbers = new Numbers();
        int a = 10;
        int b = 353;
        int result = numbers.summ(a, b);
        assertEquals(a + b, result);
    }

    @Test
    public void sumCorrectForNegativeNumbers(){
        Numbers numbers = new Numbers();
        int a = -245;
        int b = -4624;
        int result = numbers.summ(a, b);
        assertEquals(a + b, result);
    }

    @Test
    public void intervalSumOk(){
        Numbers numbers = new Numbers();
        int a = -10;
        int b = 253;
        int correctSum = 0;
        for(int i = a; i <= b; i++){
            correctSum += i;
        }
        int functionCountedSum = numbers.intervalSumm(a, b);
        assertEquals(correctSum, functionCountedSum);
    }
}