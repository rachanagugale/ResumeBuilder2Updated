package sample;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class TestPercentGenerator
{
    @Test
    void testPercent()
    {
        Person person = mock(Person.class);
        assertEquals(560/650, person.calculatePercent(560, 650));
    }
}