package com.example.android.digitalcoursefile;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdatePassword_UT {
    /*
        (?=.*[a-z])     : at least 1 lowercase alphabetical character
        (?=.*[A-Z])	    : The string must contain at least 1 uppercase alphabetical character
        (?=.*[0-9])     : The string must contain at least 1 numeric character
        (?=.{4,})       : The string must be four characters or longer
    */

    @Test
    public void testIsPasswordValid() {
        String testPassword = "vijaySai13";
        Boolean expected = true;
        PasswordUpdate regObj = new PasswordUpdate();
        boolean output = regObj.checkPassword(testPassword);
        assertEquals(expected, output);
    }

    @Test
    public void PassNoLowerCase() {
        String testPassword = "SAI13";
        Boolean expected = false;
        PasswordUpdate regObj = new PasswordUpdate();
        boolean output = regObj.checkPassword(testPassword);
        assertEquals(expected, output);
    }

    @Test
    public void PassNoUpperCase() {
        String testPassword = "sai13";
        Boolean expected = false;
        PasswordUpdate regObj = new PasswordUpdate();
        boolean output = regObj.checkPassword(testPassword);
        assertEquals(expected, output);
    }

    @Test
    public void PassNoNumeric() {
        String testPassword = "vijaySai";
        Boolean expected = false;
        PasswordUpdate regObj = new PasswordUpdate();
        boolean output = regObj.checkPassword(testPassword);
        assertEquals(expected, output);
    }

    @Test
    public void PassNoMoreLength() {
        String testPassword = "Sai";
        Boolean expected = false;
        PasswordUpdate regObj = new PasswordUpdate();
        boolean output = regObj.checkPassword(testPassword);
        assertEquals(expected, output);
    }

    @Test
    public void bothPasswordSame() {
        String testPassword1 = "VijaySai13";
        String testPassword2 = "VijaySai13";
        Boolean expected = true;
        PasswordUpdate regObj = new PasswordUpdate();
        boolean output = regObj.checkPasswordSame(testPassword1,testPassword2);
        assertEquals(expected, output);
    }

    @Test
    public void bothPasswordNotSame() {
        String testPassword1 = "VijaySai13";
        String testPassword2 = "vijay13";
        Boolean expected = false;
        PasswordUpdate regObj = new PasswordUpdate();
        boolean output = regObj.checkPasswordSame(testPassword1,testPassword2);
        assertEquals(expected, output);
    }
}
