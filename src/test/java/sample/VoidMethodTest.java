package sample;

import com.itextpdf.text.DocumentException;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoidMethodTest
{
    @Test
    void mockMethod() throws IOException, DocumentException
    {
        Person person = mock(Person.class);
        doAnswer(new Answer()
        {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable
            {
                System.out.println("Method executed");
                return null;
            }
        }).when(person).process();
        verify(person).process();
    }
}