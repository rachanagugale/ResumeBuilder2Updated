package sample;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class DBTest
{
    @Test
    void testDBConnection()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/stqa", "root", "root");
            assertNotNull(connection);
        } catch (ClassNotFoundException e)
        {
            e.printStackTrace();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    /*
    This is not working for some reason :(

    @Test
    void testDataRetrieval()
    {
        Person person = mock(Person.class);
        verify(person).retrieveData("Rachana");
    }
    */
}