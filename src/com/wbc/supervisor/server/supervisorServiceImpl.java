package com.wbc.supervisor.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.dashboard2.graphics.chart.WbcSeriesInfo;
import com.wbc.supervisor.client.dashboard2.visjsCharts.VisjsConnection;
import com.wbc.supervisor.client.dashboard2.visjsCharts.VisjsData;
import com.wbc.supervisor.client.dashboard2.visjsCharts.VisjsNode;

import com.wbc.supervisor.dashboard.*;
import com.wbc.supervisor.server.dashboardutilities.*;
import com.wbc.supervisor.shared.*;
import com.wbc.supervisor.shared.dto.*;
import com.wbc.supervisor.database.generated.tables.records.PropertiesRecord;
import com.wbc.supervisor.database.jooq.JooqUtil;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

// WBCUTIL
import com.wbc.supervisor.client.dashboardutilities.view.login.UserAction;
import com.wbc.supervisor.shared.dashboardutilities.UserEntity;
import com.wbc.supervisor.shared.dashboardutilities.Globals;
import com.wbc.supervisor.shared.dashboardutilities.*;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.*;
import com.wbc.supervisor.shared.dashboardutilities.threshold.ThresholdPresentationData;
import com.wbc.supervisor.shared.dashboardutilities.threshold.ThresholdPresentationInfo;
import com.wbc.supervisor.shared.dashboardutilities.threshold.ThresholdSetData;
import com.wbc.supervisor.shared.dashboardutilities.threshold.ThresholdType;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.*;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import static com.wbc.supervisor.client.dashboardutilities.Constants.APP_VERSION;
import static com.wbc.supervisor.client.dashboardutilities.Constants.APP_VERSION_EXTRA;

public class supervisorServiceImpl extends RemoteServiceServlet implements supervisorService {

    // Implementation of sample interface method
    Logger logger ;   // = Logger.getLogger("com.wbc.Dashboard2.server.dashboard2SericeImpl.class");
    public static DSLContext dslDashboard ;
    public static Connection dsbConn;
    long timezoneOffsetMillis = 0;

    // data maintained by the dashboard Server
    HashMap<Integer, ArrayList<DeviceTopologyInfo>> allNetworksTopoList;
    HashMap<Integer, DeviceInfoDTO> deviceidInfoMap;
    boolean logDsbsvrRequests = true;
    String appPath="";
    public long lastIntravueSampleLong = 0;  // this is set by DashboardThread
    private DashboardThread dashboardThread = null;
    private final boolean logRpcCalls = true;

    private String version = "0.8.267.150529";
    Properties intravueProperties = new Properties();


    private static String sWbcAppDir;
    public static String webinfPath;
    public static Properties svrProps = new Properties();
    private static SimpleDateFormat sdf;
    private boolean debugImplCalls = true;
    private static final Logger loggerUtil = LogManager.getLogger( supervisorServiceImpl.class);


    public supervisorServiceImpl() {
        System.out.println("ctor start");
        // setup logging using log4j in the server
        // setupLog4j();
        appPath = System.getProperty("user.dir");
        //
        logger = Logger.getRootLogger();
        String sLogDir = getMakeLogDir( System.getProperty("catalina.base"), "dashboard2");
        String targetLog= sLogDir + "/logs/dashboard2.log";
        RollingFileAppender apndr = null;
        try {
            apndr = new RollingFileAppender(new PatternLayout("%d %-5p [%c{1}] %m%n"),targetLog,true);
            apndr.setMaxFileSize("5MB");
            apndr.setMaxBackupIndex(5);
            apndr.rollOver();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.addAppender(apndr);
        // setup Jooqds
        StringBuilder errors = new StringBuilder();
        dsbConn = JooqUtil.getConnection("netvue", "netvue", "dashboard2", "127.0.0.1", errors);
        if ( dsbConn == null) {
            //TODO need a way to tell user there was a problem
            logger.fatal("Failed to get dashboard2 connection:, EXITING " + errors.toString());
        }
        dslDashboard = DSL.using(dsbConn, SQLDialect.MYSQL);
        timezoneOffsetMillis = DashboardUtil.getTimezoneOffsetFromDb(dslDashboard, logger);
        logger.info("--------------------------------- dashboard2ServiceImpl " + version +  "  -------------------------------------------------");
        allNetworksTopoList = new HashMap<Integer, ArrayList<DeviceTopologyInfo>>();
        deviceidInfoMap = new HashMap<Integer, DeviceInfoDTO>();
        System.out.println("appPath is " + appPath + ", base is " + supervisor.getAppBase() );
        logger.info("appPath is " + appPath);
        //    Timestamp lastTs = DashboardUtil.getLastThresholdsTimestamp( dslDashboard, logger );
        //   lastIntravueSampleLong = lastTs.getTime();
        // get the intravue properties
        DashboardUtil.getIntravueProperties( dslDashboard, intravueProperties );
        System.out.println("ctor end " + lastIntravueSampleLong );
        logger.info("ctor end " + lastIntravueSampleLong );
    }

    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }


    //TODO Junaid: make this an rpc mathod by adding the service,etc// Done
    public ArrayList<String> getIntravueProperties() {
        DashboardUtil.getIntravueProperties( dslDashboard, intravueProperties );
        //TODO only get this when it changes
        ArrayList<String> udnames = new ArrayList<String>();

        udnames.add( intravueProperties.getProperty("userDefined1", "User Defined 1"));
        udnames.add( intravueProperties.getProperty("userDefined2", "User Defined 2"));
        udnames.add( intravueProperties.getProperty("userDefined3", "User Defined 3"));
        udnames.add( intravueProperties.getProperty("userDefined4", "Version"));
        udnames.add( intravueProperties.getProperty("userDefined5", "Vendor"));
        udnames.add( intravueProperties.getProperty("userDefined6", "Model"));
        return udnames;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
        logger.warn("DO NOT HANDLE doGet's !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPut(req, resp);
        logger.warn("DO NOT HANDLE doPut's !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }


    public static String getMakeLogDir( String base, String appname)  {
        // get current drive
        String home = "";
        if (base==null || base.isEmpty()) {
            // this is running in debugger or something but NOT apache
            home = "c:/dashboard2/logs/devmode";
        } else {
            int index = base.indexOf( "intravue");
            home = base.substring(0, index + 8)  + "/" + appname ;
        }
        File test = new File( home );
        if ( !test.exists()) {
            test.mkdirs();
        }
        if ( !new File( home ).exists() ) {
            new File( home ).mkdir();
        }
        return home;
    }

    //TODO - check to see if a method in this class is allowed to be synchronized ?
    /*
    This method gets the topodate from dsbsvr
    It is stored in a hashmap of DeviceTopologyInfo for each network of this intravue.  Could be 1 or 50
     */
    public void getNetworkDeviceTreeMapFromDatabase(){
        boolean debug = false;
        long t1 = System.currentTimeMillis();
        ArrayList<DeviceTopologyInfo> deviceTopoList =  DashboardUtil.getTopoList( dslDashboard, logger );
        if (deviceidInfoMap.size() == 0) {
            getDeviceInfoFromDatabase();
        }
        allNetworksTopoList.clear();
        for (DeviceTopologyInfo deviceTopologyInfo : deviceTopoList) {
            Integer parent = deviceTopologyInfo.getParent();
            Integer networkId = deviceTopologyInfo.getNetworkid();
            if ( parent == 1) {
                // put this in the network of the child
                Integer child = deviceTopologyInfo.getChild();
                // now get the record of the child
                if (deviceidInfoMap.containsKey(child)) {
                    int childNetworkId = deviceidInfoMap.get(child).getNetworkid();
                    if (allNetworksTopoList.containsKey(childNetworkId)) {
                        ArrayList<DeviceTopologyInfo> list = allNetworksTopoList.get(childNetworkId);
                        list.add(deviceTopologyInfo);
                    } else {
                        ArrayList<DeviceTopologyInfo> list = new ArrayList<DeviceTopologyInfo>();
                        list.add(deviceTopologyInfo);
                        allNetworksTopoList.put(childNetworkId, list);
                    }
                }
                logger.warn("Device in topo , not in deviceinfo parent is ONE " + parent + "  " + deviceTopologyInfo.toString() );
            } else {
                if (networkId != 0) {
                    if (allNetworksTopoList.containsKey(networkId)) {
                        ArrayList<DeviceTopologyInfo> list = allNetworksTopoList.get(networkId);
                        list.add(deviceTopologyInfo);
                    } else {
                        ArrayList<DeviceTopologyInfo> list = new ArrayList<DeviceTopologyInfo>();
                        // we need to add "1" to every network
                        list.add(deviceTopologyInfo);
                        allNetworksTopoList.put(networkId, list);
                    }
                }
            /*
            if (deviceidInfoMap.containsKey(parent)) {
                int networkId = deviceidInfoMap.get(parent).getNetworkid();
                if (networkId != 0) {
                    if (allNetworksTopoList.containsKey(networkId)) {
                        ArrayList<DeviceTopologyInfo> list = allNetworksTopoList.get(networkId);
                        list.add(deviceTopologyInfo);
                    } else {
                        ArrayList<DeviceTopologyInfo> list = new ArrayList<DeviceTopologyInfo>();
                        // we need to add "1" to every network
                        list.add(deviceTopologyInfo);
                        allNetworksTopoList.put(networkId, list);
                    }
                }
            } else {
                if (parent == 1) {
                    // put this in the network of the child
                    Integer child = deviceTopologyInfo.getChild();
                    // now get the record of the child
                    if (deviceidInfoMap.containsKey(child)) {
                        int childNetworkId = deviceidInfoMap.get(child).getNetworkid();
                        if (allNetworksTopoList.containsKey(childNetworkId)) {
                            ArrayList<DeviceTopologyInfo> list = allNetworksTopoList.get(childNetworkId);
                            list.add(deviceTopologyInfo);
                        } else {
                            ArrayList<DeviceTopologyInfo> list = new ArrayList<DeviceTopologyInfo>();
                            list.add(deviceTopologyInfo);
                            allNetworksTopoList.put(childNetworkId, list);
                        }
                    }
                    logger.warn("Device in topo , not in deviceinfo parent is ONE " + parent + "  " + deviceTopologyInfo.toString() );
                } else {
                    logger.warn("Device in topo , not in deviceinfo " + parent + "  " + deviceTopologyInfo.toString() );
                }
            */
            }
        }
        if (debug ) {
            logger.debug("getNetworkDeviceTreeMapFromDatabase complete " + ( System.currentTimeMillis() - t1));
        }
        if (logDsbsvrRequests) logger.debug("getNetworkDeviceTreeMapFromDatabase:  complete ");
    }

    public void getDeviceInfoFromDatabase(){
        boolean debug = false;
        long t1 = System.currentTimeMillis();
        deviceidInfoMap.clear();
         deviceidInfoMap = DashboardUtil.getDeviceInfo( dslDashboard , logger );

        if (debug ) {
            logger.debug("getDeviceInfoFromDatabase complete " + ( System.currentTimeMillis() - t1));
        }
        if (logDsbsvrRequests) logger.debug("getDeviceInfoFromDatabase:  complete ");
    }

    /*
    private void setupLog4j() {
        // get current drive
        String base = System.getProperty("catalina.base");
        int index = base.indexOf( "intravue");
        String homedir = base.substring(0, index + 8)  + "/" + "dashboard2" ;
        File test = new File( homedir );
        if ( !test.exists()) {
            test.mkdirs();
        }
        System.out.println("homedir is " + homedir )        ;
        System.setProperty( "dashboard2.server.logfile.name", homedir + "/server.log");
        // make sure file exists or create it
        File logfile = new File( System.getProperty("dashboard2.server.logfile.name"));
        if ( !logfile.exists()) {
            try {
                logfile.createNewFile();
                System.out.println("Logfile did not exist, created " + System.getProperty("dashboard2.server.logfile.name"));
            } catch (IOException e) {
                System.out.println("Logfile did not exist, exception creating file " + System.getProperty("dashboard2.server.logfile.name") + "  " + e.getMessage() );
            }
        }
        logger = Logger.getRootLogger();
        logger.setLevel(org.apache.log4j.Level.DEBUG);
    }
    */


    @Override
    public ArrayList<WbcNamesDTO> getNetworkInfo() {
        if (logRpcCalls) logger.debug("SvcImpl rpc for getNetworkInfo() ");
        ArrayList<WbcNamesDTO> wbcNames = new ArrayList<WbcNamesDTO>();
        String jsonResponse = HttpHelper.sendGet( "http://127.0.0.1:8765/dsbsvr/DashboardServer?type=getNetworks", null, logger , false );
        Gson gson = new Gson();
        boolean useGson = false;
        if (useGson) {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonResponse).getAsJsonArray();
            String message = gson.fromJson(array.get(0), String.class);
        } else {
            try {
                JSONObject inObject = new JSONObject(jsonResponse);
                // now get data out of json into objects
                String outResult =  inObject.get("result").toString();
                String outData = inObject.get("data").toString();
                if (outResult.toLowerCase().equals("ok")) {
                    TypeToken<List<WbcNamesDTO>> token = new TypeToken<List<WbcNamesDTO>>(){};
                    gson.fromJson(outData, token.getType());
                    wbcNames = gson.fromJson(outData, token.getType());
                } else {
                    logger.debug("getNetworkInfo: error");
                    String message = "getNetworkInfo: failed, failure response not complete, error = " + outData ;
                    logger.error( message );
                    Window.alert( message );
                }
            } catch (Exception e) {
                logger.fatal("getNetworkInfo: json exception > " + e.getMessage() + " json string that was processed > " + jsonResponse );
                WbcNamesDTO network = new WbcNamesDTO("Network 1 XXX", "10.1.1.211", 1, 0);
                wbcNames.add(network);
                network = new WbcNamesDTO("Network Mirrored XXX", "10.1.1.211", 2, 0);
                wbcNames.add(network);
            }
        }
        return wbcNames;
    }


    @Override
    public MultiSeriesTimebasedChartDTO getPingSummaryData(int nwid,  String maxAverage, long oldestSample, long newestSample, int samplesPerPoint  ) {
       if (logRpcCalls) logger.debug("SvcImpl rpc for getPingSummaryData() ");
        MultiSeriesTimebasedChartDTO dto=null;
        try {
            long t1 = System.currentTimeMillis();
            dto = PingThresholds.getMaxNetworkPingResponses(dslDashboard,
                    DashboardUtil.getCurrentDmpfileId(dslDashboard, logger),
                    nwid,
                    oldestSample,
                    newestSample,
                    samplesPerPoint,
                    timezoneOffsetMillis,
                    logger
            );
            long t3 = System.currentTimeMillis();
            if (true) System.out.println( "getPingSummaryData: time to get data " + (t3-t1) );
        } catch (Exception e) {
            logger.error("getPingSummaryData: Exception " + e.getMessage(), e );
        }
        return dto;
    }

    @Override
    public MultiSeriesTimebasedChartDTO getBandwidthSummaryData(int nwid, String type, String maxAverage, long oldestSample, long newestSample, int samplesPerPoint ) {
        if (logRpcCalls) logger.debug("SvcImpl rpc for getBandwidthSummaryData() ");
        int dbid = DashboardUtil.getCurrentDmpfileId( dslDashboard, logger );
        long t1 = System.currentTimeMillis();
        MultiSeriesTimebasedChartDTO dto = BandwidthThresholds.getMaxNetworkBandwidthResponses(dslDashboard, dbid, nwid, oldestSample, newestSample, samplesPerPoint, timezoneOffsetMillis, logger);
        long t2 = System.currentTimeMillis();
        if (true) System.out.println( "getBandwidthSummaryData:  time to get data " + (t2-t1) );
        return dto;
    }


    @Override
    public MultiSeriesTimebasedChartDTO[] getStatsBarGraphData(int nwid, boolean criticalOnly,  long oldestSample, long newestSample, int samplesPerPoint  ) {
        boolean debugStatGraph = true;
        if (logRpcCalls) logger.debug("SvcImpl rpc for getStatsBarGraphData() ");
        MultiSeriesTimebasedChartDTO[] dto=null;
        try {
            long t1 = System.currentTimeMillis();
            dto = StatsUtil.getNetworkStats(dslDashboard,
                    DashboardUtil.getCurrentDmpfileId(dslDashboard, logger),
                    nwid,
                    oldestSample,
                    newestSample,
                    samplesPerPoint,
                    timezoneOffsetMillis,
                    criticalOnly,
                    deviceidInfoMap,
                    logger


            );
            long t3 = System.currentTimeMillis();
            if (debugStatGraph) System.out.println( "getStatsBarGraphData: time to get data " + (t3-t1) );
            if (debugStatGraph) System.out.println( "getStatsBarGraphData: period of data " + oldestSample + "  " + new DateTime(oldestSample).toString() + " newestSample "  + new DateTime(newestSample).toString());
            if (debugStatGraph && criticalOnly) {
                for (MultiSeriesTimebasedChartDTO multiSeriesTimebasedChartDTO : dto) {
                    System.out.println("XXXXX");
                    LinkedHashMap<String,ArrayList<Number>> lhm = multiSeriesTimebasedChartDTO.getData();
                    for (String s : lhm.keySet()) {
                        ArrayList<Number> alist = lhm.get(s);
                        for (Number number : alist) {
                            System.out.println( s + "  " + number );
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("getStatsBarGraphData: Exception " + e.getMessage(), e );
        }
        return dto;
    }

    @Override
    public ArrayList<DataBasedChartSeriesDTO> getDetailedPingData( int networkId, String method, int maxDevices, long oldestSample, long newestSample, int samplesPerPoint ) {
        if (logRpcCalls) logger.debug("SvcImpl rpc for getDetailedPingData() ");
        ArrayList<Integer> descidsInNetwork = new ArrayList<Integer>();
        boolean debugTimestamps = false;
        HashMap<Integer, DeviceInfoDTO> parentMap = new HashMap<Integer, DeviceInfoDTO>();
        for (Integer deviceid : deviceidInfoMap.keySet()) {
            DeviceInfoDTO di = deviceidInfoMap.get(deviceid);
            if ( di.getNetworkid() == networkId) {
                if ( di.getIpAddress().startsWith("U")  || di.getIpAddress().startsWith("n")) continue;
                descidsInNetwork.add(di.getParentid());   // the parentid is what is in the database
                // System.out.println( di.toInfoString());
                parentMap.put( di.getParentid(), di );
            }
        }

        HashMap<Integer, ArrayList<Number>> pingResults = new HashMap<Integer, ArrayList<Number>>();
        pingResults = PingThresholds.getNetworkPingResponses(dslDashboard,
                descidsInNetwork,
                oldestSample,
                newestSample,
                samplesPerPoint,
                maxDevices,
                0L,  // timezone offset, n/a for this operation
                logger
        );

        ArrayList<DataBasedChartSeriesDTO> seriesList = new ArrayList<DataBasedChartSeriesDTO>();
        int wbcSeriesInfoIndex = 0;
        for (Integer deviceid : pingResults.keySet()) {
            if (deviceid == 0) {
                // deviceid 0 in seriesList will contain the time values for graphing
                DataBasedChartSeriesDTO chartDto = new DataBasedChartSeriesDTO();
                ArrayList<Number> values = pingResults.get(deviceid);
                chartDto.setDataList(values);
                WbcSeriesInfo wbcSeriesInfo = new WbcSeriesInfo();
                wbcSeriesInfo.setName("timeData");
                chartDto.setWbcSeriesInfo( wbcSeriesInfo);
                seriesList.add(chartDto);
                continue;  // this is the time data
            }
            ArrayList<Number> values = pingResults.get(deviceid);
            if ( parentMap.containsKey(deviceid)) {
                DeviceInfoDTO dto = parentMap.get(deviceid);
                // System.out.print(deviceid + "  " + dto.ipAddress + "  " + dto.getName() + "   ");
                DataBasedChartSeriesDTO chartDto = new DataBasedChartSeriesDTO();
                chartDto.setDataList(values);
                WbcSeriesInfo wbcSeriesInfo = new WbcSeriesInfo();
                wbcSeriesInfo.setName( dto.getIpAddress() + " " + dto.getName() );
                wbcSeriesInfo.setArrayIndex( wbcSeriesInfoIndex++);
                //TODO - figure out how to have a list/method that creates better colors
//                wbcSeriesInfo.setR(getRandomInt255());
//                wbcSeriesInfo.setG(getRandomInt255());
//                wbcSeriesInfo.setB(getRandomInt255());
                wbcSeriesInfo.setR(getColor(wbcSeriesInfoIndex,0));
                wbcSeriesInfo.setG(getColor(wbcSeriesInfoIndex,1));
                wbcSeriesInfo.setB(getColor(wbcSeriesInfoIndex,2));
                wbcSeriesInfo.setType("line");
                chartDto.setWbcSeriesInfo( wbcSeriesInfo);
                seriesList.add(chartDto);
            } else {
                logger.debug("getDetailedPingData: deviceid " + deviceid + "  Nothing in map " );
            }
        }
        logger.debug("getDetailedPingData: size of seriesList " + seriesList.size() );
        return seriesList;
    }

    private int getColor(int wbcSeriesInfoIndex, int index) {

        int[] color = ColorInfo.getColor(wbcSeriesInfoIndex);
        return  color[index];

    }

    private int getRandomInt255() {
        Random colorInt = new Random();
        return colorInt.nextInt(256);  // gets 0 to 255
    }


    @Override
    // this is being called every 15 seconds by MainPanel
    public Long  getLastSampleno() {
        if (logRpcCalls) logger.info("SvcImpl rpc for getLastSampleno() =" + lastIntravueSampleLong);
        if (true) System.out.println("SvcImpl rpc for getLastSampleno() = " + lastIntravueSampleLong );
        return lastIntravueSampleLong; // this is set by DashboardThread
    }



    public ArrayList<DeviceTopologyInfo> getNetworkDeviceTreeMap(int networkId){
        if (logRpcCalls) logger.debug("SvcImpl rpc for getNetworkDeviceTreeMap() ");
        boolean debug = false;
        ArrayList<DeviceTopologyInfo> topoList = new ArrayList<DeviceTopologyInfo>();
        if ( allNetworksTopoList.isEmpty()) {
            getNetworkDeviceTreeMapFromDatabase();
        }
        if ( allNetworksTopoList.containsKey(networkId)) {
            topoList = allNetworksTopoList.get(networkId);
        } else {
            logger.warn("getNetworkDeviceTreeMap: nothing for network " + networkId);
        }
        if (debug ) logger.debug("getNetworkDeviceTreeMap called, size of topolist " + topoList.size() );
        return topoList;
    }

    public ArrayList<DeviceinfoNamesDTO> getNetworkDeviceNamesMap(int networkId){
        if (logRpcCalls) logger.debug("SvcImpl rpc for getNetworkDeviceNamesMap() ");
        boolean debug = false;
        if (debug ) logger.debug("getNetworkDeviceNamesMap called ");
        ArrayList<DeviceinfoNamesDTO> namesList = new ArrayList<DeviceinfoNamesDTO>();
        if ( deviceidInfoMap.isEmpty() ) {
            if (logDsbsvrRequests) logger.debug("getNetworkDeviceNamesMap called  and no deviceInfoMap for network " + networkId );
            getDeviceInfoFromDatabase();
        }
        for (Integer deviceid : deviceidInfoMap.keySet()) {
            DeviceInfoDTO di = deviceidInfoMap.get(deviceid);
            if ( di.getNetworkid() == networkId) {
                DeviceinfoNamesDTO names = new DeviceinfoNamesDTO();
                names.setName(di.getName());
                names.setLocation( di.getLocation());
                names.setDeviceid(di.getParentid());
                // later names.setIpAddress( new IpAddress(di.getIpAddress()) );
                names.setIpAddress( di.getIpAddress() );
                names.setUd1name(di.getUd1name());
                names.setUd2name(di.getUd2name());
                names.setUd3name(di.getUd3name());
                names.setUd4name(di.getUd4name());
                names.setUd5name(di.getUd5name());
                names.setUd6name(di.getUd6name());
                // notes is stored in the properties table
                String deviceNotes = DashboardUtil.getDeviceNote(dslDashboard, deviceid);
                names.setNotes( deviceNotes );
                di.setNotes(deviceNotes);  // update the memory table too
                namesList.add(names);
            }
        }
        if (debug) logger.debug("getNetworkDeviceNamesMap: deviceInfoMap size " + deviceidInfoMap.size() + ",  namesList size " + namesList.size() );
        return namesList;
    }

    public ConnectionDataDTO getConnectionDataForNetwork(int networkId ) {
        if (logRpcCalls) logger.debug("SvcImpl rpc for getConnectionDataForNetwork() ");
        ConnectionDataDTO dto = new ConnectionDataDTO();
        String jsonResponse = HttpHelper.sendGet( "http://127.0.0.1:8765/dsbsvr/DashboardServer?type=getNetworkConnectionData&nwid="+networkId, null, null , false );
        if (false) {
            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonResponse).getAsJsonArray();
            Gson gson = new Gson();
            String message = gson.fromJson(array.get(0), String.class);
            if (message.toLowerCase().equals("ok")) {
                String data = gson.fromJson(array.get(1), String.class);
                TypeToken<ConnectionDataDTO> token = new TypeToken<ConnectionDataDTO>() {
                };
                dto = gson.fromJson(data, token.getType());
            } else {
                message = "getConnectionDataForNetwork: failed, failure response not complete, error = " + gson.fromJson(array.get(1), String.class);
                logger.error(message);
                Window.alert(message);
            }
        } else {
            ArrayList<ConnectionDataDTO> dtolist = new ArrayList<ConnectionDataDTO>();
            Gson gson = new Gson();
            // System.out.println( jsonResponse );
            try {
                JSONObject inObject = new JSONObject(jsonResponse);
                // now get data out of json into objects
                String outResult =  inObject.get("result").toString();
                String outData = inObject.get("data").toString();
                if (outResult.toLowerCase().equals("ok")) {
                    TypeToken<List<ConnectionDataDTO>> token = new TypeToken<List<ConnectionDataDTO>>(){};
                    dtolist = gson.fromJson(outData, token.getType());
                    dto = dtolist.get(0);
                } else {
                    System.out.println("getConnectionDataForNetwork: error");
                    String message = "getConnectionDataForNetwork: failed, failure response not complete, error = " + outData ;
                    System.out.println(message);
                    // Window.alert(message);
                }
            } catch (JSONException e) {
                logger.fatal("getConnectionDataForNetwork: json exception > " + e.getMessage() + " json string that was processed > " + jsonResponse );            }
        }
        return dto;
    }

    @Override
    public VisjsData getVisjsData( int networkId  ) {
        if (logRpcCalls) logger.debug("SvcImpl rpc for getVisjsData() ");
        ArrayList<DeviceTopologyInfo> topoList = new ArrayList<DeviceTopologyInfo>();
        if ( allNetworksTopoList.isEmpty()) {
            getNetworkDeviceTreeMapFromDatabase();
        }
        if ( allNetworksTopoList.containsKey(networkId)) {
            topoList = allNetworksTopoList.get(networkId);
        }

        VisjsData visData = new VisjsData();
        boolean disableVisjs = false;
        if (disableVisjs) {
            ArrayList<VisjsNode> visjsNodes = new ArrayList<VisjsNode>();
            ArrayList<VisjsConnection> visjsConnections = new ArrayList<VisjsConnection>();
            visData.setVisjsNodes( visjsNodes);
            visData.setVisjsConnections(visjsConnections);
        } else {
            visData = TopologyHelper.getTopologyAsVisjsData(topoList, deviceidInfoMap);
        }
        return visData;
    }

    @Override
    public String updateNetworkDeviceNamesMap(ArrayList<DeviceinfoNamesDTO> updatedList) {
        if (logRpcCalls) logger.debug("SvcImpl rpc for updateNetworkDeviceNamesMap() ");
        boolean debug = false;
        ArrayList<NamesDTO> allChanges = new ArrayList<NamesDTO>();
        String response="";
        for (DeviceinfoNamesDTO deviceinfoNamesDTO : updatedList) {
            int descid = deviceinfoNamesDTO.getDeviceid();
            ArrayList<NamesDTO> changes = DashboardUtil.getNameChanges(deviceidInfoMap, deviceinfoNamesDTO, logger);
            // update the note in the properties file

            DashboardUtil.updateDeviceNote( dslDashboard, deviceidInfoMap, deviceinfoNamesDTO, logger );
            allChanges.addAll( changes );
        }
        // put on wire
        org.json.JSONObject object = new org.json.JSONObject();
        String jsonResponse ="";
        try {
            object.put("type", "changeNames");
            object.put("data", allChanges);
            if (debug) {
                String jsondata = object.toString();
                logger.debug("updateNetworkDeviceNamesMap: json data > " + jsondata);
            }
            jsonResponse = ServerUtils.postJsonToServer("http://127.0.0.1:8765/dsbsvr/DashboardServer?type=updateNames", object);
            if (debug) logger.info( "updateNetworkDeviceNamesMap: response from server " + jsonResponse );
            JSONObject inObject = new JSONObject(jsonResponse);
            String outResult =  inObject.get("result").toString();
            String outData = inObject.get("data").toString();
            if ( !outResult.toLowerCase().equals("ok")) {
                logger.debug("updateNetworkDeviceNamesMap: error " + outData );
                response = outData;
            }
        } catch (JSONException e) {
            logger.fatal("getNetworkTopologyInfo: json exception > " + e.getMessage() + " json string that was processed > " + jsonResponse );
        } catch (Exception e) {
            logger.error("getNetworkTopologyInfo:  exception > " + e.getMessage());
        }
        return response;
    }

    public ArrayList<DeviceInfoDTO> getNetworkDeviceInfo(int networkId){
        boolean debug = false;
        if (debug || logRpcCalls ) logger.debug("SvcImpl getNetworkDeviceInfo called ");
        ArrayList<DeviceInfoDTO> namesList = new ArrayList<DeviceInfoDTO>();
        if ( deviceidInfoMap.isEmpty() ) {
            if (logDsbsvrRequests) logger.debug("getNetworkDeviceInfo called and no deviceInfoMap for network " + networkId );
            getDeviceInfoFromDatabase();
        }
        for (Integer deviceid : deviceidInfoMap.keySet()) {
            DeviceInfoDTO di = deviceidInfoMap.get(deviceid);
            if (di.networkid == networkId) {
                namesList.add(di);
            }
        }
        if (debug) logger.debug("getNetworkDeviceInfo: deviceInfoMap size " + deviceidInfoMap.size() + ",  namesList size " + namesList.size() );
        return namesList;
    }

    public ArrayList<DeviceinfoDataDTO> getNetworkDeviceData(int networkId){
        boolean debug = false;
        if (debug || logRpcCalls ) logger.debug("SvcImpl getNetworkDeviceData called ");
        ArrayList<DeviceinfoDataDTO> dataList  = new ArrayList<DeviceinfoDataDTO>();
        if ( deviceidInfoMap.isEmpty() ) {
            if (logDsbsvrRequests) logger.debug("getNetworkDeviceData called and no deviceInfoMap for network " + networkId );
            getDeviceInfoFromDatabase();
        }
        for (Integer deviceid : deviceidInfoMap.keySet()) {
            DeviceInfoDTO di = deviceidInfoMap.get(deviceid);
            if (di.networkid == networkId) {
                DeviceinfoDataDTO dto = extractDataFromInfo(di);
                dataList.add(dto);
            }
        }
        if (debug) logger.debug("getNetworkDeviceData: deviceInfoMap size " + deviceidInfoMap.size() + ",  dataList size " + dataList.size() );
        return dataList;
    }

    private DeviceinfoDataDTO extractDataFromInfo(DeviceInfoDTO di) {
        DeviceinfoDataDTO dto = new DeviceinfoDataDTO();
        //TODO add category to DTO from server
        // dto.setCategory( di.getCategory() );
        // dto.setDescription(di.getDescription());
//        dto.setDeviceid(di.ged);  TODO: Need to set descid..
        dto.setName(di.getName());
        dto.setIpAddress(di.getIpAddress());
        dto.setMacAddress(di.getMacAddress());
        dto.setLocation(di.getLocation());
        dto.setUd1Name(di.getUd1name());
        dto.setVendor(di.getVendorName());
        dto.setVerified(di.isVerified());
        dto.setWireless(di.isWirelessDevice);
        dto.setSwitch(di.isSwitch);
        dto.setHasRedbox(di.hasRedBox);
        dto.setCriticalType(di.getCriticalType());
        dto.setDescid(di.getParentid());// Junaid Added this line as descId was 0 always and because of that jsp page wasnt opening
        return dto;
    }


    @Override
    public String callBrowseIntravueNetwork( int networkid ) {
        if (logRpcCalls) logger.debug("SvcImpl rpc for callBrowseIntravueNetwork() ");
        String showNumber = "0";
        com.wbc.supervisor.database.generated.tables.Properties propTable = com.wbc.supervisor.database.generated.tables.Properties.PROPERTIES;
        PropertiesRecord pRecord = dslDashboard.selectFrom(propTable)
                .where(propTable.TYPE.equal("shownum")).and(propTable.NAME.equal("network")).and(propTable.ID.eq("" + networkid))
                .fetchOne();
        if (pRecord != null) {
            showNumber = pRecord.getValue();
        }


        // HttpSession session ;
        // session=getThreadLocalRequest().getSession(true);


        return showNumber ;
    }

    @Override
    public String callDeviceStatsDetailsJsp(int currentNetworkId, int statIndexNumber, long hoursToGraph) {
        if (logRpcCalls) logger.debug("SvcImpl rpc for callDeviceStatsDetailsJsp() ");
        boolean debug = false;
        int dbid = DashboardUtil.getCurrentDmpfileId( dslDashboard, logger );
        int numValuesPerDevice = 24;
        long t1 = System.currentTimeMillis();
        StatDetailsByDeviceDTO dto = StatsUtil.getNetworkStatsForType( dslDashboard, dsbConn,  dbid, currentNetworkId, statIndexNumber, (int)hoursToGraph, numValuesPerDevice, lastIntravueSampleLong, logger );
        long t2 = System.currentTimeMillis();
        // add a totals columns
        ArrayList<String> dates = dto.getDates();
        dto.setChartTitle( StatsConstants.getStatNameFromIndex(statIndexNumber) + " Incidents Details By Device for " + hoursToGraph + " hours");
        dates.add("Total");
        // add a totals row
        HashMap<Integer,DeviceStatDetails> deviceDataMap = dto.getDeviceDataMap();
        DeviceStatDetails totalsLine = new DeviceStatDetails(numValuesPerDevice);
        totalsLine.setDivisor(1);
        totalsLine.setIpaddress("totals");
        totalsLine.setName("totals");
        // calculate the totals per line and in total
        for (Integer deviceid : deviceDataMap.keySet()) {
            int total = 0;
            DeviceStatDetails details = deviceDataMap.get(deviceid);
            if ( deviceidInfoMap.containsKey(deviceid)) {
                if (debug) System.out.println("callDeviceStatsDetailsJsp  deviceid " + deviceid + " ip " + deviceidInfoMap.get(deviceid).getIpAddress());
                details.setIpaddress(deviceidInfoMap.get(deviceid).getIpAddress());
                details.setName(deviceidInfoMap.get(deviceid).getName());
            } else {
                System.out.println(" deviceid " + deviceid + " NOT found in deviceidInfoMap");
                details.setIpaddress("unknown " + deviceid);
                details.setName("unknown deviceid " + deviceid);
            }
            ArrayList<Integer> values = details.getValues();
            int index = 0;
            for (Integer value : values) {
                if ( details.getDivisor() == 1) {
                    total += value;
                    totalsLine.setValue( index, totalsLine.getValue(index) + value );
                    index++;
                } else {
                    // NOTE
                    // to display a decimal number the values would have to change from int to string
                    // deal with this in the jsp, divide by divisor there and  put in string column/row
                }
            }
        }
        // now deal with the totalsLine line
        ArrayList<Integer> values8 = totalsLine.getValues();
        int total8 = 0;
        for (Integer value : values8) {
            if ( totalsLine.getDivisor() == 1) {
                total8 += value;
                if (debug) System.out.print( value + "(" + total8 + ")" + "\t");
            } else {
                // SAME NOTE AS ABOVE
            }
        }
        if (debug) System.out.println("");  // do a CR/LF
        values8.add(total8);
        // add totals line at end so totals are themselves not totaled.
        deviceDataMap.put(-1, totalsLine);
        if (debug) {
            System.out.println("callDeviceStatsDetailsJsp: DEBUG dto start, SIZE " + dto.getDeviceDataMap().size()  + " ----------");
            System.out.println(dto.toString());
            System.out.println("callDeviceStatsDetailsJsp: DEBUG dto end   ----------");
        }
        // ready to launch web page
        HttpSession session ;
        session=getThreadLocalRequest().getSession(true);
        session.setAttribute("statDetailByDevice", dto);
        return "";
    }

    public String getServerAddress(){
        String serverAddress = "";
        try {
            serverAddress = Inet4Address.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return serverAddress;
    }

    @Override
    public HashMap getLocalDetails() {
        InetAddress IP = null;
        HashMap hm =null;
        try {


            Locale currentLocale = getThreadLocalRequest().getLocale();
            SimpleDateFormat df = (SimpleDateFormat) SimpleDateFormat.getDateTimeInstance(DateFormat.SHORT,DateFormat.LONG, currentLocale);
//            SimpleDateFormat df1= (SimpleDateFormat) SimpleDateFormat.getTimeInstance(DateFormat.LONG, currentLocale);

            String dateFormat = df.toPattern();
            /*
            getLocalAddr
            Returns the preferred Locale that the client will accept content in, based on the Accept-Language header.
            If the client request doesn't provide an Accept-Language header, this method returns the default locale for the server.
             */
            String locl= getThreadLocalRequest().getLocalAddr();
            String romote =  getThreadLocalRequest().getRemoteAddr();


            // 127.0.0.1 will ALWAYS be the address between the dashboard2 and the dsbsvr, but many times, not as the address used by the client.
            // IP = InetAddress.getLocalHost();

            IP = Inet4Address.getByName( locl );
            logger.debug("getLocalDetails: " + IP.getHostAddress());

            // Put elements to the map
            hm = new HashMap();
            hm.put("ipAddress", IP.getHostAddress());
            hm.put("localDf", dateFormat);

        } catch (UnknownHostException e) {
            logger.error("getLocalDetails: Exception: " + e.getMessage(), e);
        }
        return hm;
    }



    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null) {
            result += line;
        }
        inputStream.close();
        return result;
    }

   /* @Override
    public void init(ServletConfig config) throws ServletException {
        System.out.println("init start");
        super.init(config);
        Properties svrProps = new Properties();
        dashboardThread = new DashboardThread( this, svrProps, logger );
        new Thread( dashboardThread ).start();
        System.out.println("dashboard2ServiceImpl init complete");
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            dsbConn.close();
        } catch (SQLException e) {
            logger.warn("Failed to close dsbConn at destroy.");
        }
        logger.debug("dashboard2ServiceImpl destroy called.");
        if ( dashboardThread != null) {
            dashboardThread.kill();
            logger.debug("dashboard2ServiceImpl kill called ");
            dashboardThread = null;
        }
        logger.debug("dashboard2ServiceImpl destroy end");
    }*/

    @Override
    public String callDeviceInfoAndStatDetailsPage( int deviceid ) {
        HttpSession session ;
        StringBuilder sb = new StringBuilder();
        session=getThreadLocalRequest().getSession(true);
        boolean showErrorData = false;
        DeviceInfoDTO di = null;
        if ( deviceidInfoMap.containsKey( deviceid)) {
            di = deviceidInfoMap.get(deviceid);
            session.setAttribute("deviceinfo", di );
            // update notes
            // NOTE: this will be stored in a new table which has not been implemented
            di.setNotes( "User editable device notes has not been fully implemented.");
            ///////Getting userdefined names//
            session.setAttribute("userdefinednames", getIntravueProperties() );
            /////End getting user defined names///
            //
            String jsonResponse = HttpHelper.sendGet( "http://127.0.0.1:8765/dsbsvr/DashboardServer?type=getLastEvents&deviceid=" + deviceid, null, logger , false );
            try {
                JSONObject inObject = new JSONObject(jsonResponse);
                // now get data out of json into objects
                String outResult =  inObject.get("result").toString();
                String outData = inObject.get("data").toString();
                Gson gson = new Gson();
                if (outResult.toLowerCase().equals("ok")) {
                    TypeToken<List<IntravueEventDTO>> token = new TypeToken<List<IntravueEventDTO>>(){};
                    ArrayList<IntravueEventDTO> events = gson.fromJson(outData, token.getType());
                    session.setAttribute("events", events);


                    // now fake some info
                    StringBuilder info = new StringBuilder();
                    info.append("FAKE Device Status");
                    info.append("<br>");
                    info.append("Ping: min - 1.2, max- 5.6, avg - 2.1, stddev - .5 ");
                    info.append("<br>");
                    info.append("Recv Bytes/Minute min - 840, max - 14200 - avg 2300 - stddev 1000");
                    session.setAttribute("stats", info.toString() );
                } else {
                    String message = "callDeviceInfoAndStatDetailsPage: failed, failure response not complete, error = " + outData ;
                    logger.error( message );
                    sb.append( message );
                    showErrorData = true;
                }
            } catch (JSONException e) {
                logger.fatal("callDeviceInfoAndStatDetailsPage: json exception > " + e.getMessage() + " json string that was processed > " + jsonResponse );
                showErrorData = true;
                sb.append("Error decoding json data");
            }
            int minutesPerPoint = 1;
            MultiSeriesTimebasedChartDTO msDto = BandwidthThresholds.getBandwidthForDevice(dslDashboard, deviceid, lastIntravueSampleLong - ( 8 * 60000 ), lastIntravueSampleLong, minutesPerPoint, 0, logger );
            session.setAttribute("bwdto", msDto );
            msDto = PingThresholds.getPingsForDevice(dslDashboard, deviceid, lastIntravueSampleLong - ( 8 * 60000 ), lastIntravueSampleLong, minutesPerPoint, 0, logger );
            session.setAttribute("pingdto", msDto );
        } else {
            sb.append("Could not find data in DeviceInfo for deviceid " + deviceid);
            showErrorData = true;
        }
        if ( showErrorData) {
            if ( di == null) {
                di = new DeviceInfoDTO();
            }
            di.setNotes( sb.toString());
            session.setAttribute("deviceinfo", di);
            session.setAttribute("userdefinednames", getIntravueProperties() );
            ArrayList<IntravueEventDTO> events = new ArrayList<IntravueEventDTO>();
            session.setAttribute("events", events );
            MultiSeriesTimebasedChartDTO msDto = new MultiSeriesTimebasedChartDTO();
            session.setAttribute("bwdto", msDto );
            session.setAttribute("pingdto", msDto );
        }
        return "ok";
    }

    @Override
    public Integer getScannerState() {
        logger.info("In getScannerState " + 0);
        return  DashboardUtil.getScannerState(dslDashboard, logger);
    }

    public ArrayList<KpiStatsRow> getKpiStatsForPeriod(int networkId, long starttime, long endtime){
        if (logRpcCalls) logger.debug("SvcImpl rpc for getKpiStatsForPeriod() ");

        boolean debug = true;
        boolean working = true;
        boolean criticalOnly = true;
        ArrayList<KpiStatsRow> kpiList = new ArrayList<KpiStatsRow>();
        if ( working ) {
            int dbid = DashboardUtil.getCurrentDmpfileId( dslDashboard, logger );
            if (debug ) logger.debug("getKpiStatsForPeriod  , deviceinfomap size " + deviceidInfoMap.size() + " nwid " + networkId + "   " + new Timestamp(starttime).toString() + "  " + new Timestamp(endtime).toString() );
            HashMap<Integer, KpiStatsRow> kpiStatsMap = StatsUtil.getKpiStatsByDeviceForNetwork(dslDashboard, deviceidInfoMap, dbid, networkId, criticalOnly, starttime, endtime, logger);
            kpiList = new ArrayList<KpiStatsRow>(kpiStatsMap.values() );
            if (true) {
                logger.debug("getKpiStatsForPeriod TURN OFF FOR PRODUCTION ABC");
                for (KpiStatsRow kpiStatsRow : kpiList) {
                    logger.debug( "statsRow " + kpiStatsRow.toString() );
                }
            }
        } else {
            // still in debug mode, no real data
            for (int i = 0; i < 5; i++) {
                KpiStatsRow row = new KpiStatsRow("dummy " + i, "10.1.1." + i);
                row.setClevel(3);
                row.setDisc( i );
                row.setMove(i);
                row.setPt( 2 * i );
                row.setTotal( i + i + (2*i) );
                row.setUpTime( "99.3");
                kpiList.add(row);
            }
        }
        return kpiList;
    }

/////////////////////////////// WBCUTIL  ////////////////////////////////////////////////

    @Override
    public SwitchErrorInfo getCrcData( String json ) throws IOException
    {

        Gson gson = new Gson();
        SwitchErrorInfo switchErrorInfo = gson.fromJson( json, SwitchErrorInfo.class );

        return  switchErrorInfo;
    }

    @Override
    public SwitchProbeReportInfoImpl getSwitchReport(String json, String fileName, String uploadedFile) throws IOException
    {
        Gson gson = new Gson();
        SwitchProbeReportInfoImpl switchProbeReportInfoImpl = gson.fromJson( json, SwitchProbeReportInfoImpl.class);
        SwitchProbeReportInfoImpl switchs = new SwitchProbeReportInfoImpl();
        switchs.setGeneralData( switchProbeReportInfoImpl.getGeneralData() );
        switchs.setMacList( switchProbeReportInfoImpl.getMacList() );
        switchs.setDuplicateMacList(switchProbeReportInfoImpl.getDuplicateMacList());
        switchs.setArpList( switchProbeReportInfoImpl.getArpList() );
        switchs.setVlanList( switchProbeReportInfoImpl.getVlanList() );
        switchs.setIfList( switchProbeReportInfoImpl.getIfList() );
        switchs.setIpList( switchProbeReportInfoImpl.getIpList() );
        switchs.setPortList( switchProbeReportInfoImpl.getPortList() );
        switchs.setErrorMessages( switchProbeReportInfoImpl.getErrorMessages() );
        switchs.setWarningMessages( switchProbeReportInfoImpl.getWarningMessages() );
        switchs.setErrorInfo(switchProbeReportInfoImpl.getErrorInfo());

        if(!fileName.equalsIgnoreCase("AlreadyUploaded"))
            saveSwitchProbeJson(json, fileName, uploadedFile);

        return switchs;
    }

    private void saveSwitchProbeJson(String json, String fileName, String uploadedFile) throws IOException {

        String switchProbePath  = supervisorServiceImpl.getAppDir() + "/switchprobes/"+fileName;
        FileUtils.writeStringToFile(new File(switchProbePath), json, StandardCharsets.UTF_8);
    }

    @Override
    public String getAppDirPath(){
        return supervisorServiceImpl.getAppDir() ;

    }

    private String getFormattedDate(Date date)
    {
        return new SimpleDateFormat("yyyyMMdd.HHMM").format(date);
    }

    private void setSymbolDTO( ArrayList<MacInfo> macList )
    {
        for(MacInfo macInfo : macList)
        {
            macInfo.setSymbolDTO(new SymbolDTO(macInfo.getDescid(), macInfo.getIp()));
        }
    }

    @Override
    public KpiClassInfo getKpiData( String json ) throws Exception
    {
        Gson gson = new Gson();
        KpiClassInfo kpiClassInfo = gson.fromJson( json, KpiClassInfo.class);
        return kpiClassInfo;
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    @Override
    public ConnectionInfoInfo getConnectionData( String url ) throws Exception
    {
        String json = "";

        try {
            Authenticator.setDefault(new IntravueAuthenticator());
            InputStream is = new URL(url).openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            json = readAll(rd);
            Gson gson = new Gson();
            ConnectionInfoInfo connectionInfo = gson.fromJson( json, ConnectionInfoInfo.class);

            return connectionInfo;
        } catch (Exception ex)
        {
            throw ex;
        }

    }

    @Override
    public String getJson( ArrayList<Integer> list ) throws Exception
    {
        Gson gson = new Gson();
        String result = gson.toJson( list );
        return result;
    }

    @Override
    public DisconnectionsByDayInfo getDisconnectionData( String json ) throws Exception
    {
        Gson gson = new Gson();
        DisconnectionsByDayInfo disconnectionsByDayInfo = gson.fromJson( json, DisconnectionsByDayInfo.class);
        return disconnectionsByDayInfo;
    }

    @Override
    public ThresholdPresentationInfo getThresholdData( String json, ThresholdType thresholdType ) throws Exception
    {
        Gson gson = new Gson();
        ThresholdPresentationInfo thresholdPresentationInfo = gson.fromJson( json, ThresholdPresentationInfo.class);
        ThresholdPresentationInfo thresholdPresentationInfoFiltered = new ThresholdPresentationInfo();

        HashMap<String, ThresholdPresentationData> presentationInfoMap = new HashMap<String, ThresholdPresentationData>(  );
        //	int count =0;
        for ( Map.Entry<String, ThresholdPresentationData> entry : thresholdPresentationInfo.getPresentationInfoMap().entrySet()) {
            entry.getValue().getStatData().setMin(round(entry.getValue().getStatData().getMin()));
            entry.getValue().getStatData().setMax(round(entry.getValue().getStatData().getMax()));
            entry.getValue().getStatData().setAvg(round(entry.getValue().getStatData().getAvg()));
            entry.getValue().getStatData().setStddev(round(entry.getValue().getStatData().getStddev()));
            entry.getValue().getStatData().setStddev(round(entry.getValue().getStatData().getStddev()));
            entry.getValue().setRecommend_threshold(entry.getValue().getStatData().getRecommended());

            switch ( thresholdType )
            {
                case THRESHOLD_TYPE_PING:
                case THRESHOLD_TYPE_RECV:
                case THRESHOLD_TYPE_XMIT:
                    if (entry.getValue().getThreshType() == ( thresholdType.getId() ))
                    {
                        presentationInfoMap.put( entry.getKey(), entry.getValue() );
                    }
                    break;

                case THRESHOLD_TYPE_BOTH_BANDWIDTH:
                    if (entry.getValue().getThreshType() == 2 || entry.getValue().getThreshType() == 3)
                    {
                        presentationInfoMap.put( entry.getKey(), entry.getValue() );
                    }
                    break;
                case THRESHOLD_TYPE_ALL:
                    presentationInfoMap.put( entry.getKey(), entry.getValue() );
            }

        }
        thresholdPresentationInfoFiltered.setErrorInfo( thresholdPresentationInfo.getErrorInfo() );
        thresholdPresentationInfoFiltered.setPresentationInfoMap( presentationInfoMap );

        return thresholdPresentationInfoFiltered;
    }

    @Override
    public String getRecommendedThresholdsJson( List<ThresholdSetData> list ) throws Exception
    {
        String thresholdString =  ServerUtilities.convertThresholdSetDataToString((ArrayList<ThresholdSetData>) list, null);
        try {
            return ServerUtilities.appendMd5ToThresholdSet(thresholdString);
        }catch(Exception ex)
        {
            throw ex;
        }
    }

    @Override
    public ErrorInfo getErrorText(String json ) throws Exception
    {
        Gson gson = new Gson();
        ErrorInfo result = gson.fromJson( json, ErrorInfo.class );
        return result;
    }

    @Override
    public String getIntraVueHostJson(IntravueHost intravueHost) {
        Gson gson = new Gson();
        String result = gson.toJson( intravueHost );
        return result;
    }

    @Override
    public IntravueHostDTO getIntravueHostDTO(String json) {
        if (debugImplCalls) sysOut("Impl: calling getIntravueHostDTO");
        Gson gson = new Gson();
        IntravueHostDTO intravueHostDTO = gson.fromJson(json, IntravueHostDTO.class);
        if (debugImplCalls) {
            ErrorInfo ei = intravueHostDTO.getErrorInfo();
            sysOut("Impl: return from getIntravueHostDTO " + ei.toString() );
        }
        return intravueHostDTO;
    }

    @Override
    // 7/20/20 Panduit change, this returns BOTH pk,keycode as comma separated string in ErrorInfo text
    public ErrorInfo getProductKeyAndKeycode(String ip) throws Exception {
        sysOut("wbcutil version " + APP_VERSION );
        if (debugImplCalls) sysOut("Impl: calling getProductKey for host "+ ip);
        ErrorInfo errorInfo = IvHostHelper.getProductKeyAndKeycode(ip, logger);
        if (!errorInfo.isOK()) {
            sysOut("Impl:getProductKey returns an error: " + errorInfo.toString(), "error" );
        }
        if (debugImplCalls) sysOut("Impl: return from getProductKeyAndKeycode " + errorInfo.toString() );
        return errorInfo;
    }

    @Override
    public ErrorInfo validateKeycode(IntravueHost intravueHost , String getVersionUrl) {

        String ivHostIp = intravueHost.getHostip();
        String userEmail = intravueHost.getHostEmails();
        String keycode = intravueHost.getKeycode();

        if (debugImplCalls) sysOut("Impl: calling validateKeycode for host" + ivHostIp + ", keycode " + keycode);
        ErrorInfo errorInfo = IvHostHelper.validateKeycode(userEmail, keycode, ivHostIp, logger);
        if (debugImplCalls) sysOut("Impl: return from validateKeycode for host" + ivHostIp + ", keycode " + keycode);
        // NOTE - at startup on after a new deployment, the host file may not exist but cookies do
        if (errorInfo.isWarning()) {
            sysOut("Impl:validatePK returns a warning: " + errorInfo.toString(), "warn" );
        } else if ( errorInfo.isError()) {
            sysOut("Impl:validatePK returns an error: " + errorInfo.toString(), "error" );
        }else{
            //ok
            errorInfo = updateIntravueHost(intravueHost, getVersionUrl);
        }
        if (debugImplCalls) sysOut("Impl: return from validateKeycode " + errorInfo.toString() );
        return errorInfo;
    }

    @Override
    // 7/20/20 - this function parameter changed from pk to keycode
    public ErrorInfo getExpirationDate(String keycode ) {
        if (debugImplCalls) sysOut("Impl: calling getExpirationDate for keycode " + keycode);
        ErrorInfo errorInfo = IvHostHelper.getExpirationDate( keycode, logger);
        if (!errorInfo.isOK()) {
            sysOut("Impl:getExpirationDate returns an error: " + errorInfo.toString(), "error" );
        }
        if (debugImplCalls) sysOut("Impl: return from getExpirationDate");
        return errorInfo;
    }


    @Override
    public IntravueHostDTO getIntravueHosts() {
        if (debugImplCalls) sysOut("Impl: calling getIntravueHosts");
        IvHostHelper ivHostHelper = new IvHostHelper ();
        IntravueHostDTO dto = ivHostHelper.getIntravueHosts(logger);
        if (debugImplCalls) sysOut("Impl: return from getIntravueHosts " + dto.getErrorInfo().toString());
        return dto;
    }

    @Override
    public ErrorInfo saveUpdateDeleteIntravueHost(IntravueHost intravueHost, IntravueHostAction intravueHostAction) {
        IvHostHelper ivHostHelper = new IvHostHelper();
        if (debugImplCalls) sysOut("Impl: calling saveUpdateDeleteIntravueHost with action " + intravueHostAction );
        switch (intravueHostAction) {
            case SAVE:
                return ivHostHelper.saveIntravueHost(intravueHost, logger);
            case UPDATE:
                return ivHostHelper.updateIntravueHost(intravueHost, logger);
            case DELETE:
                return ivHostHelper.removeIntravueHost(intravueHost, logger);
        }
        if (debugImplCalls) sysOut("Impl: ERROR calling saveUpdateDeleteIntravueHost with unhandled action " + intravueHostAction );
        return null;
    }

    private ErrorInfo updateIntravueHost(IntravueHost intravueHost, String getVersionUrl){
        ErrorInfo errInfo = new ErrorInfo();
        ErrorInfo expirationDateInfo = IvHostHelper.getExpirationDate(intravueHost.getKeycode(), logger);
        intravueHost.setExpireDate(expirationDateInfo.getErrorText());

        try {
            Authenticator.setDefault(new IntravueAuthenticator());
            InputStream is = new URL(getVersionUrl).openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String json = readAll(rd);
            Gson gson = new Gson();
            ErrorInfo errorInfo = gson.fromJson( json, ErrorInfo.class);
            intravueHost.setWbcserverVersion(errorInfo.getErrorText());
            errInfo = saveUpdateDeleteIntravueHost(intravueHost, IntravueHostAction.UPDATE);
        }
        catch (Exception ex)
        {
            errInfo.makeError();
            if ( ex.getLocalizedMessage().contains("/getVersion")) {
                errInfo.setErrorText("Could not get ivDashboard version from host.  Check to be sure ivDashboard is installed in host.  See the Installation section of Help");
            } else {
                errInfo.setErrorText("Error in updateIntravueHost:"+ ex.getLocalizedMessage());
            }
        }
        logger.info("TEST updateIntravueHost: result > " + errInfo.toString());
        return errInfo;
    }

    @Override
    public ErrorInfo saveCSVtoFile(UtilGrid utilGrid, String fileName) {

        ErrorInfo returnInfo = new ErrorInfo();
        logger.info("");
        if (utilGrid == null ) {
            returnInfo.makeError();
            returnInfo.setErrorText("Error: data parameter missing or empty");

        } else if (fileName == null || fileName.isEmpty()) {
            returnInfo.makeError();
            returnInfo.setErrorText("Error: Missing filename to export to");

        }
        else {

            if (utilGrid instanceof ConnectionInfoInfo) {
                return GridExports.saveCSVtoFileConnection((ConnectionInfoInfo) utilGrid, fileName);
            } else if (utilGrid instanceof ThresholdPresentationInfo) {
                return GridExports.saveCSVtoFileThreshold((ThresholdPresentationInfo) utilGrid, fileName);
            }else if (utilGrid instanceof DisconnectionsByDayInfo) {
                return GridExports.saveDisconnectedReportCSVtoFile((DisconnectionsByDayInfo) utilGrid, fileName);
            }else if (utilGrid instanceof SwitchErrorInfo) {
                return GridExports.saveIfInErrortoCSVFile((SwitchErrorInfo) utilGrid, fileName);
            }
            else if(utilGrid.getListArp() != null){
                return GridExports.saveArpToCsvFileArp(utilGrid.getListArp(), fileName);
            }
            else if(utilGrid.getListCiscoVlanData() != null){
                return GridExports.saveVlanToCsvFile(utilGrid.getListCiscoVlanData(), fileName);
            }
            else if(utilGrid.getListInterfaceData() != null){
                return GridExports.saveIfDataToCsvFile(utilGrid.getListInterfaceData(), fileName);
            }
            else if(utilGrid.getListMacInfo() != null){
                return GridExports.saveMacToCsvFile(utilGrid.getListMacInfo(), fileName);
            }
            else if(utilGrid.getListPortData() != null){
                return GridExports.savePortDataToCsvFile(utilGrid.getListPortData(), fileName);
            }
            else if(utilGrid.getListIpInformation() != null){
                return GridExports.saveIpToCsvFile(utilGrid.getListIpInformation(), fileName);
            }

            returnInfo.makeError();
            returnInfo.setErrorText("Error: Data is Unknown");
        }
        return returnInfo;
    }

    @Override
    public IntravueSwitchInfoInfo getSwitchesData(String json) {
        Gson gson = new Gson();
        IntravueSwitchInfoInfo intravueSwitchInfoInfo = gson.fromJson(json, IntravueSwitchInfoInfo.class);
        return intravueSwitchInfoInfo;
    }

    @Override
    public String readFile(String forFile, String fileName) throws FileNotFoundException {
        String path = "";
        // so that This method can be use for any other module for fileReading other than switchprobe.
        if(forFile.equalsIgnoreCase("switchprobe"))
            path  = supervisorServiceImpl.getAppDir() + "/switchprobes/"+fileName;
        try {
            String content = new String(Files.readAllBytes(Paths.get(path)));
            return content;
        }catch (Exception ex){
            logger.error("Error in reading file"+ ex);
        }
        return "";
    }

    @Override
    public ErrorInfo saveUpdateDeleteUsers(UserEntity userEntity, UserAction userAction) {
        //TODO: implement the save/update/Delete User
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            Connection con= DriverManager.getConnection(
                    "jdbc:mariadb://127.0.0.1:3306/wbcutil","netvue","netvue");
            Statement stmt=con.createStatement();
            ResultSet rs=stmt.executeQuery("select * from user ");
            while(rs.next())
                System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
            con.close();
        }catch(Exception e){ System.out.println(e);}

        IvHostHelper ivHostHelper = new IvHostHelper();
        if (debugImplCalls) sysOut("Impl: calling saveUpdateDeleteUser with action " + userAction );
        switch (userAction) {
            case SAVE:
                //	return ivHostHelper.saveUser(userEntity, logger);
            case UPDATE:

            case DELETE:

        }
        if (debugImplCalls) sysOut("Impl: ERROR calling saveUpdateDeleteUser with unhandled action " + userAction );
        return null;
    }

    @Override
    public ArrayList<WbcFileInfo> getSwitchprobeLocalFiles()
    {
        ArrayList<WbcFileInfo> filelist = ServerUtilities.getSwitchprobeLocalFiles( supervisorServiceImpl.getAppDir() + "/switchprobes/", logger );
        return filelist;
    }

    @Override
    public ArrayList<String> getSwitchprobeLocalFilesAsString()
    {
        ArrayList<String> filelist = ServerUtilities.getSwitchprobeLocalFilesAsString( supervisorServiceImpl.getAppDir() + "/switchprobes/", logger );
        return filelist;
    }

    @Override
    public IntravueHostStatus getIntravueStatusInfo(String ip, IntravueHostStatus ivHostStatus) {
        ErrorInfo errorInfo = IvJsonApi.getIntravueStatusInfo(ip, ivHostStatus, logger);
        if(!errorInfo.getResult().equalsIgnoreCase("ok"))
            ivHostStatus.setHostDescription("Error:" + errorInfo.getErrorText() );
        return ivHostStatus;

    }

    private Double round(Double input)
    {
        BigDecimal bd = new BigDecimal(input).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		/*
		String catalinaBase = System.getProperty("catalina.base");

		System.out.println("DEBUG catalina base is " + catalinaBase );
		if (catalinaBase == null || catalinaBase.isEmpty()) {
			System.out.println("FATAL no catalina Base" );
			// running in debug or intellij run mode, force folder
			sysOut("init throwing UnavailableExcetion !!");
			throw new
					UnavailableException("FATAL: could not locate install folder. ");
		}
		 */
        getMakeAppDir(config);
        initServer();
        ServerTest.runStartupTests( sWbcAppDir, logger );
    }

    /*
    In DEV mode under windows intellij
    Using CATALINA_BASE:   "C:\Users\Jim\.IntelliJIdea2019.3\system\tomcat\Unnamed_wbcutil_2"
    Using CATALINA_HOME:   "D:\DEV2020\2020-5\wbcUtilities\tomcat8"

    When deployed to Panduit intravue's tomcat


    When deployed to RPi
    Webapps folder is /home/pi/wbcutil
    webinf /home/pi/wbcutil/tomcat8/webapps/wbcutil/WEB-INF


     */
    public void getMakeAppDir(ServletConfig config)  {
        // get current drive
        webinfPath = config.getServletContext().getRealPath("/WEB-INF");
        int offset = 8;
        String testbase = System.getProperty("catalina.base");
        sysOut("BASE = " + testbase);
        String testhome = System.getProperty("catalina.home");
        sysOut( "HOME = " + testhome);
        int index = webinfPath.indexOf( "intravue");
        if (index < 1) {
            // installed in private tomcat
            index = webinfPath.indexOf( Globals.INSTALL_FOLDERNAME);
            if (index < 1) {
                // we are on WBC linux or testing
                index = webinfPath.indexOf("tomcat8");
                if ( index > 5) {
                    //	 THIS IS THE NORMAL CASE FOR PRODUCTION, WHEN NOT TESTING
                    sWbcAppDir = webinfPath.substring(0, index-1);
                } else {
                    sysOut( "USERNAME = " + System.getProperty("USERNAME"));
                    sysOut("could not find expected start app location from " + webinfPath + ", setting MAC debug mode");
                    webinfPath = "/Users/junaidp/IdeaProjects/wbcutil/war/WEB-INF";//TEST
                    sWbcAppDir = "/Users/junaidp/IdeaProjects/wbcutil";
                }
            } else {
                // we are in intellij debugger ??
                offset = Globals.INSTALL_FOLDERNAME.length();
                sWbcAppDir = webinfPath.substring(0, index + offset) ;
            }
        } else {
            // we are installed on Panduit Intravue
            sWbcAppDir = webinfPath.substring(0, index + offset) + "/" + Globals.INSTALL_FOLDERNAME ;
        }
        sysOut( "App Dir " + sWbcAppDir);
        String dirName = sWbcAppDir + "/logs";
        File dirFile = new File(dirName);
        if ( !dirFile.exists() ) dirFile.mkdirs();
        dirName = sWbcAppDir + "/data";
        dirFile = new File(dirName);
        if ( !dirFile.exists() ) dirFile.mkdirs();
        dirName = sWbcAppDir + "/dist";
        dirFile = new File(dirName);
        if ( !dirFile.exists() ) dirFile.mkdirs();

		/*File test = new File( sWbcAppDir );
		if ( !test.exists()) {
			test.mkdirs();
		}
		if ( !new File( sWbcAppDir ).exists() ) {
			new File( sWbcAppDir ).mkdir();
		}*/
    }


    public static void initServer(){
        boolean debugInit = true;
        if (debugInit) sysOut( Globals.WEBAPP_NAME + " init started");
        // ServletContext sc = getServletContext();
        try {
            File test = new File(sWbcAppDir + "/logs" );
            if (!test.exists()) {
                test.mkdirs();
            }
            initAppProperties();
            //  ------  create logging
            String targetLog = test.getAbsolutePath() + "/" + Globals.WEBAPP_NAME + ".log";
            RollingFileAppender apndr = null;
            try {
                apndr = new RollingFileAppender(new PatternLayout("%d %-5p [%c{1}] %m%n"), targetLog, true);
                apndr.setMaxFileSize("5MB");
                apndr.setMaxBackupIndex(10);
                apndr.rollOver();
            } catch (IOException e) {
                loggerUtil.error("initServer IOException - " + e.getMessage());
                //e.printStackTrace();
            }
            loggerUtil.addAppender(apndr);
            loggerUtil.setLevel( Level.toLevel( svrProps.getProperty("log.level"))) ;
            //
            loggerUtil.warn("----------------------  START (init called) ----------- Version " + APP_VERSION + " - " + APP_VERSION_EXTRA +  " ---------- 0");
            loggerUtil.info("Webapps folder is " + sWbcAppDir );
            loggerUtil.info("webinfPath folder is " + webinfPath );

            // sysOut("ServerInfo >> " + config.getServletContext().getServerInfo() );
            //-----------------------------------
            for (Object o : svrProps.keySet()) {
                String key = (String) o;
                String value = svrProps.getProperty(key);
                loggerUtil.info("initServer Property " + key + " = " + value);
            }
            //-----------------------------------
        } catch ( Exception ex ) {
            loggerUtil.fatal("initServer Exception " + ex.getMessage());
        }
        //-----------------------------------
        sysOut("initServer init complete");
    }

	/*
	This method is no longer used, dbpks.txt stays in WEB-INF/static

	private static void initAuthorizedPkFile() {
		// does it exist ?
		File test = new File( sWbcAppDir + "/data" );
		if ( !test.exists()) {
			test.mkdirs();
		}
		// if not copy it from web-inf
		File srcFile = new File (System.getProperty("catalina.base") + "/webapps/wbcutil/WEB-INF/data/dbpks.txt");
		File destFile = new File(sWbcAppDir + "/data/dbpks.txt");
		if ( ! srcFile.exists()) {
			sysOut("dbpks does not exist in src to copy from " + srcFile.getAbsolutePath() );
		} else {
			if (!destFile.exists()) {
				sysOut("dbpks does not exist, copying it from " + srcFile.getAbsolutePath() + " to " + destFile.getAbsolutePath());
				copyFileUsingStream(srcFile, destFile);
			}
		}
		if (!destFile.exists()) {
			logger.fatal("PK Authorization file does not exist, no PKs can be authorized");
		}
	}
	 */

    private static void copyFileUsingStream(File source, File dest) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);
            os = new FileOutputStream(dest);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            if (is != null) is.close();
            if (os != null) os.close();
        } catch ( IOException ex) {
            sysOut("IOException in copyFileUsingStream: " + ex.getMessage());
        }
    }

    private static void initAppProperties()  {
        svrProps = new Properties();
        try {
            String propfilename =  sWbcAppDir + "/dashboard.properties" ;
            File fProps = new File( propfilename );
            if ( !fProps.exists()) {
                loggerUtil.warn( "prop file does not exist, creating new ");
                OutputStream os = new FileOutputStream( propfilename );
                svrProps.setProperty("server.debug", "false");
                svrProps.setProperty("log.level", "" + Level.DEBUG );
                svrProps.setProperty("server.database.port", "3310" );
                svrProps.setProperty("switchprobe.debug", "false" );
                svrProps.setProperty("authentication.debug", "false" );
                svrProps.store(os, "");
                os.close();
            }
            else
            {
                InputStream is = new FileInputStream( fProps);
                svrProps.load(is);
                boolean foundAdditions = false;

                if (svrProps.getProperty("switchprobe") == null) {
                    svrProps.setProperty("switchprobe.debug", "false" );
                    foundAdditions = true;
                }
                if (svrProps.getProperty("authentication") == null) {
                    svrProps.setProperty("authentication.debug", "false" );
                    foundAdditions = true;
                }
                is.close();
                if (foundAdditions) {
                    OutputStream os = new FileOutputStream(propfilename);
                    svrProps.store(os, "");
                    os.close();
                }

            }
        } catch ( Exception ex ) {
            loggerUtil.fatal( "initAppProperties Exception " + ex.toString() ) ;
        }
    }

    public Properties getSvrProperties() {
        return svrProps;
    }


    public static int getServerDatabasePort() {
        return Integer.parseInt( svrProps.getProperty("server.database.port", "3310"));
    }
    public static String getAppDir(){
        return sWbcAppDir;
    }

    private static void sysOut(String s) {
        if (s==null) {
            s = "null message";
        }
        loggerUtil.info( String.format("%s", s ));
    }

    private static void sysOut(String str, String level) {
        if (str==null) {
            str = "null message";
        }
        String msg = String.format("%s",  str );
        if (level.equalsIgnoreCase("error")) {
            loggerUtil.error(str);
        } else if (level.equalsIgnoreCase("debug")) {
            loggerUtil.debug(str);
        } else if (level.equalsIgnoreCase("info")) {
            loggerUtil.info(str);
        } else if (level.equalsIgnoreCase("warn")) {
            loggerUtil.warn(str);
        } else {
            loggerUtil.warn( str + "  (note) incorrect level passed to sysOut=" + level );
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        sysOut("Impl destroy called.", "info" );
    }

    public static class IntravueAuthenticator extends Authenticator {

        // Called when password authorization is needed
        protected PasswordAuthentication getPasswordAuthentication() {
            CookieHandler.setDefault(new CookieManager(null, CookiePolicy.ACCEPT_ALL));
            boolean debug = true;
            if (debug) loggerUtil.debug("IntravueAuthenticator called.");

            String username = "admin";
            String password = "intravue";

            return new PasswordAuthentication(username, password.toCharArray());

        }
    }

    public static Logger getLogger() {
        return loggerUtil;
    }


    public static String getExceptionStackLines( String message,Exception exception, int numLines ) {
        return message + System.lineSeparator() + getExceptionStackLines(exception,numLines);
    }

    public static String getExceptionStackLines( Exception exception, int numLines ) {
        StringBuilder sb = new StringBuilder();
        final StackTraceElement[] stackTrace = exception.getStackTrace();
        int index = 0;
        for (StackTraceElement element : stackTrace)
        {
            final String exceptionMsg =
                    "Exception thrown from " + element.getMethodName()
                            + " in class " + element.getClassName() + " [on line number "
                            + element.getLineNumber() + " of file " + element.getFileName() + "]";
            try
            {
                if (index==0) {
                    sb.append(exceptionMsg);
                    sb.append(System.lineSeparator());
                }
                sb.append(element.toString());
                sb.append( System.lineSeparator());
                index++;
                if (index >= numLines) break;
                /*
                out.write((headerLine + newLine).getBytes());
                out.write((headerTitlePortion + index++ + newLine).getBytes() );
                out.write((headerLine + newLine).getBytes());
                out.write((exceptionMsg + newLine + newLine).getBytes());
                out.write(
                        ("Exception.toString: " + element.toString() + newLine).getBytes());
                 */
            }
            catch (Exception ioEx)
            {
                System.err.println(
                        "IOException encountered while trying to write "
                                + "StackTraceElement data \n"
                                + ioEx.getMessage() );
            }
        }
        return sb.toString();
    }


}