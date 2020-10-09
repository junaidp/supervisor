package com.wbc.supervisor.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.wbc.supervisor.client.dashboard2.DebugUtils;
import com.wbc.supervisor.client.dashboard2.RootDockLayout;
import com.wbc.supervisor.shared.DashboardLogger;


import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class supervisor implements EntryPoint {

    /**
     * This is the entry point method.
     */
    public static HandlerManager eventBus = new HandlerManager(null);
    public static Logger logger = Logger.getLogger("dashboard2");
    public static String appBase="";
    private boolean bResetHandlers = false;

    public void onModuleLoad() {
        String url = GWT.getModuleBaseURL();
        appBase = GWT.getHostPageBaseURL();
        logger.log(Level.INFO, "base url is " + url + ", base " + appBase);

        setupLogging();
        logger.log(Level.ALL, "This is a test in onModuleLoad");
        RootDockLayout mainPanel = new RootDockLayout();//Moving to onSuccess of getLocalDetailsRpc..so we sure no panel try to access cookies before setting them up
        RootLayoutPanel.get().add(mainPanel);
        //TODO Will be looking into what need to be done for Cookies later.
        //TODO Junaid, please discuss why  we need to do this every 10 seconds//  THIS is not a Repeat schedule, so its not runing after every 10 seconds
        // It runs only Once,  10 secs after application started,
        // seems to be setting 'fixed' data every 10 seconds
        // commenting out
        logger.log(Level.ALL, "JWM commented out call to getLocalDetails in OnModuleLoad, may need to be reversed.  Done to find rpc error or startup.");
        /*
        Timer minuteTimer = new Timer() {
            public void run() {
                getLocalDetails();
            }
        };
        minuteTimer.schedule(10000);
        */

        DebugUtils.initDebugAndErrorHandling();

        logger.log(Level.ALL, "base url is " + url + ", base " + appBase);

        //////////////////////Custom Logger/////////////////////////
        //TODO Junaid, please explain this logger vs the logger above.  Is this used by other classes, not used here ??
        final Logger log = Logger.getLogger(supervisor.class.getName());
        if (bResetHandlers) {
            Handler handlers[] = Logger.getLogger("").getHandlers();
            for (Handler handler : handlers) {
                handler.setFormatter(new DashboardLogger(false));
            }
        }
        log.log(Level.ALL, "this is a test from OnModuleLoad for log vs logger ");
        ///////////////////////////////////////////////////////////
    }

    private void setupLogging() {
        DateTimeFormat fmt = DateTimeFormat.getFormat("HH:mm:ss SSS" );
        if (bResetHandlers) {
            Handler handlers[] = Logger.getLogger("").getHandlers();
            for (Handler handler : handlers) {
                handler.setFormatter(new DashboardLogger(false));
            }
        }
    }

    public static String getAppBase() {
        return appBase;
    }

}
