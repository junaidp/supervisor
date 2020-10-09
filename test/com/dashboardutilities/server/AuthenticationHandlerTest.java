package com.dashboardutilities.server;

import org.junit.Test;
import java.io.File;

import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AuthenticationHandlerTest
{

    @Test
    public void isHashcodeValid() {
        String goodDbPksFilename ="c:/wbcUtilities/tomcat8/webapps/wbcutil/WEB-INF/static/dbPks.txt";
        String badDbPksFilename = "P:/master/other-files/dbpks.txt";
        File testFile = new File( goodDbPksFilename );
        assertTrue( testFile.exists());
        boolean isGood = AuthenticationHandler.isHashcodeValid(testFile);
        assertTrue(isGood);
        testFile = new File( badDbPksFilename );
        assertTrue( testFile.exists());
        isGood = AuthenticationHandler.isHashcodeValid(testFile);
        assertFalse(isGood);
        System.out.println("success");
    }
}