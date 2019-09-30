package sample;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ExceptionTest
{
    @Test
    void testExpectedException()
    {
        Assertions.assertThrows(NumberFormatException.class, () -> Float.parseFloat("abc"));
    }

}