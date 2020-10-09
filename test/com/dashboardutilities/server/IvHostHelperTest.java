package com.dashboardutilities.server;

import com.wbc.supervisor.server.dashboardutilities.IvHostHelper;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.google.gwt.editor.client.Editor;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class IvHostHelperTest {

    @Test
    @Ignore
    public void getExpirationDate() {
        Logger logger = Logger.getRootLogger();
        ErrorInfo errorInfo = new ErrorInfo();
        IvHostHelper helper = new IvHostHelper();
        String keycodeGood = "2C7DBBF5";
        String pkexpired = "";
        errorInfo = helper.getExpirationDate( keycodeGood, logger );
        assertTrue( errorInfo.isOK());

    }
}