package com.wbc.supervisor.server;

import com.wbc.supervisor.dashboard.DashboardUtil;
import com.wbc.supervisor.server.supervisorServiceImpl;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.jooq.DSLContext;

import java.sql.Timestamp;
import java.util.Properties;

/**
 * Created by JIM on 2/21/2015.
 */
public class DashboardThread implements  Runnable
{
    Logger logger;
    supervisorServiceImpl dashboard2Service;
    String threadVersion = "150227";
    private int runFlag = 0;
    long threadStartTime = 0;
    long threadSleepTime = 15 * 60 * 1000;
    boolean killCalled = false;


    public DashboardThread(supervisorServiceImpl dashboard2Service, Properties props, Logger logger) {
        this.dashboard2Service = dashboard2Service;
        this.logger = logger;

    }

    @Override
    public void run() {
        logger.info("DashboardThread run starting. -----------------  version " + threadVersion );
        String message = "";
        StringBuilder errors = new StringBuilder();
        long lastTopoUpdate = 0;
        long lastDeviceinfoChange = 0;
        DSLContext dslDashboard = dashboard2Service.dslDashboard;

        try
        {

            while ( runFlag == 0 )
            {
                logger.debug("DashboardThread: checking for changes");
                Timestamp lastTs = DashboardUtil.getLastThresholdsTimestamp( dslDashboard, logger );
                dashboard2Service.lastIntravueSampleLong = lastTs.getTime();

                //int dbid = DashboardUtil.getCurrentDmpfileId( dslDashboard, logger );
                long tempLast = lastDeviceinfoChange = DashboardUtil.updateNamesOnChange(dslDashboard, lastDeviceinfoChange, logger);
                if ( tempLast > lastDeviceinfoChange) {
                    dashboard2Service.getDeviceInfoFromDatabase();
                    lastDeviceinfoChange= tempLast;
                }
                tempLast = DashboardUtil.updateTopoOnChange( dslDashboard, lastTopoUpdate, logger );
                if ( tempLast > lastTopoUpdate) {
                    dashboard2Service.getNetworkDeviceTreeMapFromDatabase();
                    lastTopoUpdate= tempLast;
                }


                try {
                    // find time now, and sleep until 15 seconds after next minute
                    DateTime now = new DateTime();
                    DateTime nextCheck = now.plusSeconds(15);
                    threadSleepTime =  nextCheck.getMillis() - now.getMillis();
                    boolean debugRun = true;
                    if (debugRun) logger.debug(("sleeping " + threadSleepTime + "  to " + nextCheck.toString() ));
                    Thread.sleep( threadSleepTime );
                } catch (InterruptedException ie){
                    logger.fatal("XXX InterruptedException !!");
                }
            }
        }
        catch (Exception e ){
            logger.fatal("Exception 2: closing ?", e);
        }
        catch (Throwable e) {
            logger.fatal("Throwable : thrrowable 2 Exception, closing ?", e);
        }
        finally
        {
            System.out.println("DashboardThread finally!!");
            System.out.println("DashboardThread run end, stopped.  runflag = " + runFlag + " , message = " + message);
        }
    }

    public void kill() {
        logger.fatal("DashboardThread kill called");
        killCalled = true; // no more logger
        runFlag = 1;  //normal kill;
    }

}
