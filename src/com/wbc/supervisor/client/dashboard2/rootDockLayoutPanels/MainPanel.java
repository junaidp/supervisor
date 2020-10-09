package com.wbc.supervisor.client.dashboard2.rootDockLayoutPanels;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HorizontalSplitPanel;
import com.wbc.supervisor.client.dashboard2.events.TimerEvent;
import com.wbc.supervisor.client.dashboard2.mainPanelPanels.EastPanel;
import com.wbc.supervisor.client.dashboard2.mainPanelPanels.WestPanel;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Junaid on 9/28/14.
 */
public class MainPanel extends Composite {

    private Timer minuteTimer;
    long  lastSampleno=0;
    long  lastFiredSampleno=0;
    long  timezoneOffsetMillis=0;
    private static Logger logger = Logger.getLogger("MainPanel.class");
    private boolean debugStartup = false;

    public MainPanel(){

        if (debugStartup) logger.log(Level.ALL, "DebugStartup: MainPanel ctor start");
        HorizontalSplitPanel splitPanel = new HorizontalSplitPanel();
        initWidget(splitPanel);
        splitPanel.addStyleName("intravueLight");
        if (debugStartup) logger.log(Level.ALL, "DebugStartup: MainPanel , new WestPanel ");
        splitPanel.add(new WestPanel());
        if (debugStartup) logger.log(Level.ALL, "DebugStartup: MainPanel , new EastPanel ");
        splitPanel.add(new EastPanel());
        logger.info("MainPanel: after adding panels");
        splitPanel.setSplitPosition("150px");
        splitPanel.setWidth("100%");
        lastSampleno = 0;
        // Create a new timer
        minuteTimer = new Timer () {
            public void run() {
                getLastSampleno();
                if (debugStartup) logger.info("MainPanel Timer got getLastSample " + lastSampleno + " last fired " + lastFiredSampleno );
                if (lastFiredSampleno < lastSampleno) {
                    if (debugStartup) logger.info("MainPanel: minute timer found new sample  " + lastSampleno);
                    supervisor.eventBus.fireEvent(new TimerEvent( lastSampleno, "new_sample" ));
                    if ( lastFiredSampleno == 0 ) {
                        logger.info("MainPanel reschedule timer to 15 seconds ");
                        scheduleRepeating(15000);  // the normal 15 second cycle
                    }
                    lastFiredSampleno = lastSampleno ;
                }
            }
        };

        minuteTimer.scheduleRepeating(2000);  // check for new data every 2 seconds until data found
        if (debugStartup) logger.log(Level.ALL, "DebugStartup: MainPanel ctor end");
    }


    private void getLastSampleno() {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        dashboard2Service.getLastSampleno(new AsyncCallback<Long>() {

            @Override
            public void onFailure(Throwable caught) {
                logger.info("MainPanel fail: getLastSampleNo!  message " + caught.getMessage()   );
                // this makes debugging hard....   Window.alert("fail: MainPanel  getLastSampleNo" + caught.getMessage()  + " cause " + caught.getCause());
            }

            @Override
            public void onSuccess( Long last ) {
                if (debugStartup) logger.info("MainPanel success: getLastSampleNo!  sample = " + last   );
                lastSampleno = last;
            }
        });
    }


}
