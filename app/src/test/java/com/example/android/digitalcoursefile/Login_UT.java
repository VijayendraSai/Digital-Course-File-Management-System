package com.example.android.digitalcoursefile;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class Login_UT {
    /* Password :
        (?=.*[a-z])     : at least 1 lowercase alphabetical character
        (?=.*[A-Z])	    : The string must contain at least 1 uppercase alphabetical character
        (?=.*[A-Z])	    : The string must contain at least 1 uppercase alphabetical character
        (?=.{4,})       : The string must be four characters or longer
    */

    /* Username :
        (?=.{4,10}$)     : 4-10 characters long
        [a-zA-Z]         : alphabetical character
    */

    @Test
    public void testIsUsernameValid() {
        String testPassword = "vijaySai";
        Boolean expected = true;
        MainActivity regObj = new MainActivity();
        boolean output = regObj.checkUsername(testPassword);
        assertEquals(expected, output);
    }

    @Test
    public void testIsPasswordValid() {
        String testPassword = "vijaySai13";
        Boolean expected = true;
        MainActivity regObj = new MainActivity();
        boolean output = regObj.checkPassword(testPassword);
        assertEquals(expected, output);
    }

    @Test
    public void userNoLength() {
        String testPassword = "sai";
        Boolean expected = false;
        MainActivity regObj = new MainActivity();
        boolean output = regObj.checkUsername(testPassword);
        assertEquals(expected, output);
    }

    @Test
    public void userNotAlphaNumeric() {
        String testPassword = "sai1234";
        Boolean expected = false;
        MainActivity regObj = new MainActivity();
        boolean output = regObj.checkUsername(testPassword);
        assertEquals(expected, output);
    }
}
