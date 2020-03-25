package com.example.android.digitalcoursefile;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UpdateEmail_UT {
    @Test
    public void testIsEmailValid() {
        String testEmail = "vijay@gmail.com";
        Boolean expected = true;
        EmailUpdate regObj = new EmailUpdate();
        //Assert.assertThat(String.format("Email Validity Test failed for %s ", testEmail));
        boolean output = regObj.checkEmailForValidity(testEmail);
        assertEquals(expected, output);
    }

    @Test
    public void testIsEmailNotValid() {
        String testEmail = "vijay.com";
        Boolean expected = false;
        EmailUpdate regObj = new EmailUpdate();
        //Assert.assertThat(String.format("Email Validity Test failed for %s ", testEmail));
        boolean output = regObj.checkEmailForValidity(testEmail);
        assertEquals(expected, output);
    }
}
