package com.dashboardutilities.server;

import com.dashboardutilities.shared.ErrorInfo;
import com.dashboardutilities.shared.IntravueHostStatus;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class IvJsonApiTest {

    @Test
    @Ignore
    public void testGetStatus() {
        Logger logger = Logger.getRootLogger();
        IntravueHostStatus ivHostStatus = new IntravueHostStatus();
        String ipToTest = "10.1.1.173";
        ErrorInfo errorInfo = IvJsonApi.getIntravueStatusInfo(ipToTest, ivHostStatus, logger);
        assertTrue(errorInfo.isOK());

        System.out.println("Nic1 IP: " + ivHostStatus.getIpNic1());
        System.out.println("host status: " + ivHostStatus.getIvHostStatus());
        System.out.println("Host version: " + ivHostStatus.getIvVersion());

        System.out.println(ivHostStatus.toString());
        System.out.println("Print networks");

        ArrayList<IntravueHostStatus.IvNetworkInfo> networkInfo = ivHostStatus.getNetworkInfo();
        networkInfo.forEach( network -> {
            System.out.println( network.getNetworkName() + " " + network.getTopIP());
            ArrayList<IntravueHostStatus.IvScanRange> scanRanges = network.getScanRanges();
            scanRanges.forEach( range -> {
                System.out.println("from: " + range.getFromIP() + "    to: " + range.getToIP());
            });
        });



        System.out.println("Success");

    }

}