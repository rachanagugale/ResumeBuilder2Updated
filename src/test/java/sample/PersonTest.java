package sample;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest
{

    @org.junit.jupiter.api.Test
    void validatePhone()
    {
        Validation validate = new Validation();
        assertFalse(validate.validatePhone("abcdefghijk"), "Phone number cannot be letters");
        assertTrue(validate.validatePhone("9876543210"), "Phone number cannot be letters");

    }

    @org.junit.jupiter.api.Test
    void validateEmail()
    {
        Validation validate = new Validation();
        assertTrue(validate.validateEmail("abc@gmail.com"), "Invalid email");
        assertFalse(validate.validateEmail("pqrgmail.com"), "Invalid email");
    }

    @org.junit.jupiter.api.Test
    void validateCGPA()
    {
        Validation validate = new Validation();
        assertTrue(validate.validateCGPA("8.8"), "CGPA should be between 0 and 10");
        assertFalse(validate.validateCGPA("95"), "CGPA should be between 0 and 10");
    }

    @org.junit.jupiter.api.Test
    void validateSSCPercent()
    {
        Validation validate = new Validation();
        assertTrue(validate.validateSSCPercent("94.3"), "SSC Percent should be between 0 and 100");
        assertFalse(validate.validateSSCPercent("277"), "SSC Percent should be between 0 and 100");
    }

    @org.junit.jupiter.api.Test
    void validateCoverLetter()
    {
        Validation validate = new Validation();
        assertTrue(validate.validateCoverLetter("abcdefghijklmnopqrstuvwxyz"), "Cover Letter should contain less than 200 characters");
        assertFalse(validate.validateCoverLetter("Goal oriented, determined and focused with a keen eye for details. Inquisitive, with a keen desire to learn new things and a knack for solving problems. Good with Java, C++ and other languages. Ability to work in a team to make the best of any situation. Looking to start my career as a fresh software engineer with a reputed firm driven by technology and utilize my knowledge for the growth of the firm."), "Cover Letter should contain less than 200 characters");
    }



}