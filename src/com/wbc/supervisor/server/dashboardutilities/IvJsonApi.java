package com.wbc.supervisor.server.dashboardutilities;


import com.google.gson.Gson;

import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostStatus;
import jsonapi.network.JsonNetworkConfiguration;
import jsonapi.network.JsonNetworkInfo;
import jsonapi.tree.JsonDevicesTree;
import jsonapi.tree.JsonSvcStatus;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

public class IvJsonApi {

    // these are all fixed, tree must be 1, added for clarity
    static final String layout = "1";
    static final String loff = "40";
    static final String lminr = "500";
    static final String lmaxr = "6000";
    static final String lmind = "200";

    public IvJsonApi() {
    }

    public static ErrorInfo getIntravueStatusInfo(String hostip, IntravueHostStatus hostStatus, Logger logger ) {
        ErrorInfo errorInfo = new ErrorInfo();
        try {
            String tree = "1";
            String url = getDeviceTreeUrl(hostip, tree );
            errorInfo = getJsonFromIntravue(url, logger);
            logger.info("Size of DeviceTree = " + errorInfo.getErrorText().length());
            Gson gson = new Gson();
            JsonDevicesTree jsonDevicesTree = gson.fromJson(errorInfo.getErrorText(), JsonDevicesTree.class);

            StringBuilder sb = new StringBuilder();
            ArrayList<String> ivStatus = jsonDevicesTree.getIvStatus();
            if (ivStatus.size()==0) {
                hostStatus.setIvHostStatus("GOOD");
            } else {
                for (String status : ivStatus) {
                    sb.append(status.toString()).append("  ");
                }
                hostStatus.setIvHostStatus( sb.toString());
            }
            hostStatus.setIvHostStatusColor("black");
            hostStatus.setStoppedMessageColor("black");
            if ( ivStatus.contains("DATA_NOT_CURRENT")) {
                int minutes = jsonDevicesTree.getAge();
                String timeText;
                if (minutes < 120) {
                    timeText = "" + minutes + " minutes";
                } else if (minutes < 48*60) {
                    int hours = minutes / 60;
                    timeText = "" + hours + " hours";
                } else {
                    int days = minutes / 1440;
                    timeText = "" + days + " days";
                }
                hostStatus.setStoppedMessage("The IntraVUE scanner is stopped.  It has been stopped for " + timeText + " in the current database.");
                hostStatus.setStoppedMessageColor("red");
                hostStatus.setIvHostStatusColor("red");
            }
            else if ( ivStatus.contains("SOFTWARE_REGISTRATION_NOT_VALID")) {
                hostStatus.setIvHostStatusColor("orange");
            }
            JsonSvcStatus svcStatus = jsonDevicesTree.getSvcStatus();
            String ivVersion = svcStatus.getVersion();
            if ( ivVersion != null && !ivVersion.isEmpty()) {
                hostStatus.setIvVersion( ivVersion);
            } else {
                hostStatus.setIvVersion( "Before 3.2.0" );
            }

            // ----- get Network Info
            url = "http://" + hostip + ":8765/iv2/api/configuration/networks";
            errorInfo = getJsonFromIntravue(url, logger);
            gson = new Gson();
            JsonNetworkConfiguration networkConfiguration = gson.fromJson(errorInfo.getErrorText(), JsonNetworkConfiguration.class);

            JsonNetworkInfo.JsonSystemInfo systemInfo = networkConfiguration.getSystemInfo();
            Map hostips = systemInfo.getHostIp();
            Map hostmacs = systemInfo.getHostNetMask();
            hostStatus.setIpNic1((String) hostips.get("NIC 1"));
            hostStatus.setMacNic1((String) hostmacs.get("NIC 1"));
            String nic2 = (String) hostips.get("NIC 2");
            sb.append("          ");
            if (nic2 != null) {
                hostStatus.setIpNic2((String) hostips.get("NIC 2"));
                hostStatus.setMacNic2((String) hostmacs.get("NIC 2"));
            } else {
                hostStatus.setIpNic2("not active");
                hostStatus.setMacNic2("");
            }
            sb = new StringBuilder();
            // -------------------- scan ranges
            JsonNetworkInfo jsonNetworkinfo   = networkConfiguration.getNetworkInfo();
            ArrayList<JsonNetworkInfo.JsonNetworks> jsonNetworks = jsonNetworkinfo.getNetworks();
            jsonNetworks.forEach(network -> {
                IntravueHostStatus.IvNetworkInfo ivNetworkInfo = new IntravueHostStatus.IvNetworkInfo();
                String agent = network.getIvagent();
                if ( !agent.isEmpty()) {
                    ivNetworkInfo.setHasAgent(true);
                    ivNetworkInfo.setAgentIp(agent);
                    ivNetworkInfo.setAgentNetgroup(network.getNetgroup() );
                } else {
                    ivNetworkInfo.setHasAgent(false);
                }
                ivNetworkInfo.setNetworkName(network.getName());
                ivNetworkInfo.setTopIP(network.getTop());

                ArrayList<IntravueHostStatus.IvScanRange> scanRanges = ivNetworkInfo.getScanRanges();
                ArrayList<JsonNetworkInfo.JsonRange> ranges = network.getRange();
                ranges.forEach( range -> {
                    IntravueHostStatus.IvScanRange scanRange = new IntravueHostStatus.IvScanRange();
                    scanRange.setFromIP(range.getFrom());
                    scanRange.setToIP(range.getTo());
                    ivNetworkInfo.getScanRanges().add(scanRange);
                });
                hostStatus.getNetworkInfo().add( ivNetworkInfo);
            });
            //-------------------- found IPs
            hostStatus.setFoundIps(Integer.parseInt(jsonNetworkinfo.getFoundIps()));
            // -------------------- scanner speed
            if ( "1".equals(systemInfo.getWindows()) ) {
                hostStatus.setHostDescription("IntraVUE hosted on Windows");
            } else {
                hostStatus.setHostDescription("IntraVUE hosted on IntraVUE Edge/Appliance");
            }
            Map props = systemInfo.getProperties();
            String speed = (String)props.get("scannerSpeedIndex");
            if (speed != null) {
                if (speed.equals("0")) {
                    hostStatus.setScannerSpeed("SLOW");
                } else if (speed.equals("1")) {
                    hostStatus.setScannerSpeed("MEDIUM");
                } else if (speed.equals("2")) {
                    hostStatus.setScannerSpeed("FAST");
                } else if (speed.equals(3)) {
                    hostStatus.setScannerSpeed("ULTRA");
                } else {
                    hostStatus.setScannerSpeed("Non-standard " + speed);
                }
            } else {
                hostStatus.setScannerSpeed("Unknown");
            }
            hostStatus.setDateTime(new Date());
        } catch ( Exception ex) {
            errorInfo.makeError();
            errorInfo.setErrorText( ex.getMessage());
        }
        return errorInfo;   // never changed, no try/catches
    }




    private static String getDeviceTreeUrl(String hostip, String tree) {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(hostip);
        sb.append(":8765/iv2/api");
        sb.append("/devices?tree=");
        sb.append(tree);
        sb.append("&layout=");
        sb.append(layout);
        sb.append("&loff");
        sb.append(loff);
        sb.append("&lminr");
        sb.append(lminr);
        sb.append("&lmaxr");
        sb.append(lmaxr);
        sb.append("&lmind");
        sb.append(lmind);
        sb.append("&tsp=");
        sb.append(System.currentTimeMillis());
        return sb.toString();
    }





    private static ErrorInfo getJsonFromIntravue(String target, Logger logger) {
        ErrorInfo responseInfo = new ErrorInfo();
        boolean debug = false;
        try {
            //Globals not accessible here this way
            URL url = new URL( target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/text");
            if (debug) logger.info("test getJsonFromIntravue before conn");
            if (conn.getResponseCode() != 200) {
                responseInfo.setResult("error");
                responseInfo.setErrorText("Failed to get connection: HTTP error code : " + conn.getResponseCode());
                logger.error( responseInfo.getErrorText());
                return responseInfo;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            if (debug) logger.info("test getJsonFromIntravue reading stream");
            String output;
            StringBuilder sb = new StringBuilder();
            while ((output = br.readLine()) != null) {
                sb.append(output);
            }
            br.close();
            responseInfo.setErrorText( sb.toString() );
            if (debug) logger.info("test getJsonFromIntravue finished stream");
            conn.disconnect();
        } catch (MalformedURLException e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("getJsonFromIntravue MalformedURLException %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        } catch (IOException e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("getJsonFromIntravue IOException %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        } catch (Exception e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("getJsonFromIntravue Exception %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        }
        if (debug) logger.info("test getJsonFromIntravue end " + responseInfo.toString());
        return responseInfo;

    }


}
