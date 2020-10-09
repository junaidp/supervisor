package com.wbc.supervisor.client.dashboard2;

/**
 * Created by Junaid on 9/16/14.
 */
public class DashboardConstants {

    public static final String NETWORK_NAME = "network name";
    public static final String PARENT_IP = "ip of top parent";
    public static final String KPI = "critical order (KPI)";
    public static final String ALARMS_WARNINGS = "alarms and warnings";
    public static final int EAST_PANEL_WIDTH = 280;
    public static final String CLOSE_PREVIEW = "close preview";
    public static final String PREVIEW = "preview";

    public static final int IV_STATE_UNKNOWN = -1;
    public static final int IV_STATE_INVALID = 2;
    public static final int IV_STATE_OFFLINE = 3;
    public static final int IV_STATE_ONLINE = 4;
    public static final int IV_STATE_STOPPED = 5;
    public static final long SESSION_DURATION = 1000 * 60 * 60 * 24 * 14;  //(2 weeks )
    public static final String TIME_OF_LAST_UPDATE = "Time of last update: ";
    public static final int TIMER_FETCHING_DETAIL = 1000*60*2;  //2 min
    public static final int TIMER_FETCH_SCANNERSTATE= 60000 * 5 ; //5 minute
    public static final String SELECT_TYPE = "Select Type: ";
    public static final String DEVICES_TO_GRAPH = "Devices to Graph: ";
    public static final int GRAPHS_HEIGHT = 225;
   //user defined Column index no's,
    public static final int UD1=0;
    public static final int UD2=1;
    public static final int UD3=2;
    public static final int UD4=3;
    public static final int UD5=4;
    public static final int UD6=5;

    public static final int TAB_SIZE = 750;
    // intravue colors
    public static final String HTML_COLOR_INTRAVUE_LIGHT  = "#00CCFF";
    public static final String HTML_COLOR_INTRAVUE_MEDIUM = "#3399CC";
    public static final String HTML_COLOR_INTRAVUE_DARK   = "#006699";



}
