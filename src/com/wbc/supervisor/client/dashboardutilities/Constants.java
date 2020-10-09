package com.wbc.supervisor.client.dashboardutilities;

import com.wbc.supervisor.client.dashboardutilities.DashboardUtilMessages;
import com.google.gwt.core.client.GWT;

public class Constants {


	private DashboardUtilMessages messages =
			GWT.create(DashboardUtilMessages.class);

	public static final String APP_VERSION =  "1.0.1";
	public static final String APP_VERSION_EXTRA =  "August 20, 2020";  // use this to verify version in about box, without effecting APP_VERSION
    public static final String SPR = "Switch probe Report";
    public static final String CRC = "CRC Report";
    public static final String EVERY_4_HOUR = "Every 4 hours for 72 hours";
    public static final String DAILY_FOR_1_WEEK = "Daily for 1 week";
    public static final String HOURLY8HOURS = "Hourly for 8 hours";
    public static final String HOURLY24HOURS = "Hourly for 24 hours";
    public static final String HOURLY48HOURS = "Hourly for 48 hours";
    public static final String CRC_ID = "crc";
    public static final String IFINERROR_ID = "ifInErr";
    public static final String CREATE_DATABASE = "Create Database keeping Scan Ranges";
	public static final String CONNECTION_REPORT = "Connection History Report";
	public static final String THRESHOLD_REPORT = "Threshold Report";
	public static final String ADD_REMOTE_INTRAVUE = "Add Remote IntraVUE";
	public static final String EDIT_REMOTE_INTRAVUE = "Edit Remote IntraVUE";
	public static final String ADD_USER = "Add User";
	public static final String EDIT_USER = "Edit User";
	public static final String CONNECTIONS = "Connections";
    public static final String IPNOTFOUND = "IpNotFound";

	//Menus
	public static final String SWITCH_PROBE = "Switch probe Report";
    public static final String SWITCH_PROBE_MESAGE = "Generating Switch Probe Report";
    public static final String MENU_CONNECT= "Connect";
	public static final String MENU_ENGLISH= "English";
	public static final String MENU_GERMAN= "German";
    public static final String SWITCH_PROBE_HEADING =  "Switchprobe Report generated from the file";
	public static final String DISCONNCTED_STATISC_REPORT= "1 Week Disconnections Report";
	public static final String MENU_UPLOAD_PK= "Update Authorization File";
	public static final String USERS= "Users";
	public static final String MENU_UPDATE_INTRAVUE_DEVICE_INFO = "Update/Import IntraVUE device info";

	public static final String MENU_MANUAL= "Manual";
	public static final String MENU_ABOUT= "About";
	public static final String MENU_STATUS= "Status";

	//URLS OLD
    public static final String URL_OLD_CREATE_CLEAN_DB =   "cleanDatabase/createCleanDatabase";
    public static final String URL_OLD_SWITCH_PROBE_REPORT =   "switchProbe/getSwitchData";
    public static final String URL_OLD_FILE_UPLOAD =   "fileUpload/upload";
	public static final String URL_OLD_THRESHOLD_ANALYSIS = "threshold/getData";
	public static final String URL_OLD_KPI_MANAGEMENT = "kpi/getData";
	public static final String URL_OLD_KPI_MANAGEMENT_SET = "kpi/setData";

	public static final String URL_OLD_PROD = "http://localhost:8765/dserver/"; //local tomcat
	public static final String URL_OLD_DEV = "http://localhost:8765/";// local development environment where spring/server project runnig
	public static final String URL_OLD_CONNECTION_REPORT = "connectionReport/getData";
	public static final String URL_OLD_DISCONNECTED_STATISTIC_REPORT = "disconnectedStatisticReport/getData";
	public static final String URL_OLD_VALIUDATION_USERANDPORT = "users/validateUserAndProductKey";
	public static final String URL_OLD_GET_PRODUCT_KEY = "users/getProductKey";
	public static final String URL_OLD_ABOUT = "users/getVersion";
	public static final String URL_OLD_THRESHOLD_SAVE_RECOMMENDED = "threshold/saveRecommended";


	//URLS
	public static final String URL_CREATE_CLEAN_DB =   "createCleanDatabase";
	public static final String URL_SWITCH_PROBE_REPORT =   "getSwitchprobeReport";
	public static final String URL_SWITCH_PROBE_REPORT_REMOTE = "getRemoteSwitchprobeReport";
	public static final String URL_FILE_UPLOAD = "uploadSwitchprobe";
	public static final String URL_THRESHOLD_ANALYSIS = "getThresholdStatistics";
	public static final String URL_KPI_MANAGEMENT = "getKpiEvents";
	public static final String URL_KPI_MANAGEMENT_SET = "setKpiEvents";
//http://admin:intravue@192.168.178.29:8765/wbcserver
	// public static final String URL_PROD = "http://127.0.0.1:8765/wbcserver/"; //local tomcat
	// public static final String URL_DEV = "http://127.0.0.1:8765/wbcserver/";// local development environment where spring/server project runnig
	//public static final String URL_WBC_SERVER_LOCAL = Globals.HOST_IP_ADDRESS +":" + Globals.WBC_PORT + "/wbcserver/";// local development environment where spring/server project runnig
	public static final String URL_WBC_SERVER_LOCAL =  "/ivDashboard/";

	public static final String URL_CONNECTION_REPORT = "connectionReport";
	public static final String URL_DISCONNECTED_STATISTIC_REPORT = "disconnectedStatisticReport";
	public static final String URL_VALIUDATION_USERANDPORT = "validateUserAndProductKey";
	public static final String URL_GET_PRODUCT_KEY = "getProductKey";
	public static final String URL_VERSION = "getVersion";
	public static final String URL_THRESHOLD_SAVE_RECOMMENDED = "setThresholdStatistics";
	public static final String URL_CRC = "getCRCdata";
	public static final String URL_GET_SWITCHES = "getSwitches";
	public static final String URL_GET_IV_HOSTS = "getIvHosts";
	public static final String URL_ADD_IV_HOSTS = "updateIvHost";   // host does not exist it is added
	public static final String URL_UPDATE_IV_HOSTS = "updateIvHost";  // if host exists it is changed
	public static final String URL_SAVE_IV_HOSTS = "saveIvHost";
	public static final String URL_REMOVE_IV_HOSTS = "removeIvHost";
	public static final String URL_UPLOAD_PK = "uploadDbpks";
	public static final String URL_UPLOAD_UPDATE_INTRAVUE_DEVICE = "uploadUpdateNamesFile";
	public static final String URL_UPDATE_INTRAVUE_DEVICE = "updateNames";
	public static final String PRE_URL = "http://";

	public static final String URL_EXPORT_CONNECTION = "exportConnections";

	//HEADINGS
	public static final String CLEAN_DATABASE= "Create Clean Database With Ranges";
	public static final String THRESHOLD_ANALYSIS = "Threshold Analysis and Configuration";
	public static final String KPI_MANAGEMENT = "KPI Management";
	public static final String VALIDATION = "User Validation";
	public static final String GET_PRODUCT_KEY = "Product Key";
	public static final String GET_PRODUCTKEY_AND_KEYCODE = "Product Key and Keycode";
	public static final String ABOUT = "About";
	public static final String REMOTE_INTRAVUE = "Remote IntraVUEs";
	public static final String GET_SWITCHES = "SWICTHES";
	public static final String UPDATE_INTRAVUE_DEVICE_INFO = "Update IntraVUE Device Info";

	//ProgressBar Messages
	public static final String CLEANING_DATABASE_MESAGE = "Creating Clean Database";
	public static final String THRESHOLD_ANALYSIS_MESSAGE = "Generating ThresholdAnalysis ";
	public static final String KPI_MANAGMEMENT_MESSAGE = "Getting KPI Data ";
	public static final String CONNECTION_REPORT_MESSAGE = "Getting Connection Data";
	public static final String DISCONNECTED_STATISTIC_REPORT_MESSAGE = "Getting Disconnected Statistic Report Data";
	public static final String CRC_MESSAGE = "Getting Data ..";
	public static final String KPI_MANAGMEMENT_SETDATA_MESSAGE = "Setting Data";
	public static final String VALIDATION_MESSAGE = "Validating User and port";
	public static final String GET_PRODUCT_KEY_MESSAGE = "Getting product key";
	public static final String ABOUT_MESSAGE = "Getting Application version";
	public static final String SAVE_THRESHOLD_RECOMMENDED = "Saving Recommended Thresholds ..";
	public static final String ADDING_REMOTE_INTRAVUE = "Saving Remote IntraVUE";
	public static final String GETTING_REMOTE_INTRAVUE = "Getting Remote IntraVUEs";
	public static final String REMOVING_REMOTE_INTRAVUE = "Removing Remote IntraVUEs";
	public static final String EXPORTING = "Exporting";
	public static final String GRID_EXPORT_PROGRESS_HEADING = "Grid Export";
	public static final String GET_SWITCHES_MESSAGE = "Getting Switches ";
	public static final String UPDATING_INTRAVUE_DEVICE_INFO = "Updating IntraVUE Device Info";

	//WARNINGS
	public static final String GET_PRODUCT_KEY_WARNING = "Problem in getting Product Key";
	public static final String PRODUCT_KEY_NOT_FOUND = "Valid PK is required to make requests";
	public static final String SAVING_INTRAVUE_ERROR = "Error in saving IntraVUEHost";

	//FILE_NAMES
	public static final String PK_FILE = "dbpks.txt";


}
