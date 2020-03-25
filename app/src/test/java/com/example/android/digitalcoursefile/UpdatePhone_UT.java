package com.example.android.digitalcoursefile;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdatePhone_UT {
    @Test
    public void testIsPhoneValid() {
        //Indian mobile Nos. starting from 7, 8, or 9
        String testPhone = "7390265789";
        Boolean expected = true;
        UpdatePhone regObj = new UpdatePhone();
        boolean output = regObj.checkPhone(testPhone);
        assertEquals(expected, output);
    }

    @Test
    public void testIsPhoneNotValid() {
        //Indian mobile Nos. starting from 7, 8, or 9
        String testPhone = "2390265789";
        Boolean expected = false;
        UpdatePhone regObj = new UpdatePhone();
        boolean output = regObj.checkPhone(testPhone);
        assertEquals(expected, output);
    }

    @Test
    public void bothPhoneSame() {
        //Indian mobile Nos. starting from 7, 8, or 9
        String testPhone1 = "7139026579";
        String testPhone2 = "7139026579";
        Boolean expected = true;
        UpdatePhone regObj = new UpdatePhone();
        boolean output = regObj.checkPhoneSame(testPhone1,testPhone2);
        assertEquals(expected, output);
    }

    @Test
    public void bothPhoneNotSame() {
        //Indian mobile Nos. starting from 7, 8, or 9
        String testPhone1 = "7785016579";
        String testPhone2 = "7139026579";
        Boolean expected = false;
        UpdatePhone regObj = new UpdatePhone();
        boolean output = regObj.checkPhoneSame(testPhone1,testPhone2);
        assertEquals(expected, output);
    }

    @Test
    public void bothSameButNotValid() {
        //Indian mobile Nos. starting from 7, 8, or 9
        String testPhone1 = "2139026579";
        String testPhone2 = "2139026579";
        Boolean expected = false;
        UpdatePhone regObj = new UpdatePhone();
        boolean output = regObj.checkPhoneSame(testPhone1,testPhone2);
        assertEquals(expected, output);
    }
}
