package com.wbc.supervisor.server.dashboardutilities;


import com.wbc.supervisor.shared.dashboardutilities.*;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.*;
import com.wbc.supervisor.shared.dashboardutilities.threshold.StatData;
import com.wbc.supervisor.shared.dashboardutilities.threshold.ThresholdPresentationData;
import com.wbc.supervisor.shared.dashboardutilities.threshold.ThresholdPresentationInfo;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class GridExports {

    private static DecimalFormat df2 = new DecimalFormat("#.##");
    private static DecimalFormat df4 = new DecimalFormat("#.####");
    private static final Logger logger = LogManager.getLogger( GridExports.class);


    public static ErrorInfo saveCSVtoFileThreshold(ThresholdPresentationInfo info, String filename ) {
        ErrorInfo returnInfo = new ErrorInfo();
        String fullFilename = setFilePath(filename);
        File fOut = new File( fullFilename );
        OutputStream os = null;
        logger.debug("create: fOut name = " + fOut.getAbsolutePath() );
        //Incase if directory not exist, create it
        fOut.getParentFile().mkdirs();
        String lf = System.lineSeparator();
        try {
            os = new FileOutputStream(fOut);
            // print header
            StringBuilder header = new StringBuilder();
            header.append("Type");
            header.append(";");
            header.append("IP Address");
            header.append(";");
            header.append("Network");
            header.append(";");
            header.append("Samples");
            header.append(";");
            header.append("Min");
            header.append(";");
            header.append("Max");
            header.append(";");
            header.append("Avg");
            header.append(";");
            header.append("StdDev");
            header.append(";");
            header.append("StdDev Over [0-1,1-2,2-3,3-5,5-10,10+]");
            header.append(";");
            header.append("5x over");
            header.append(";");
            header.append("Current");
            header.append(";");
            header.append("Recommend");
            header.append(";");
            header.append("Ct Over Rec");
            os.write(header.toString().getBytes());
            os.write(lf.getBytes());
            //
            HashMap<String, ThresholdPresentationData> data = info.getPresentationInfoMap();
            for (Map.Entry<String, ThresholdPresentationData> entry : data.entrySet()) {
                String key = entry.getKey();
                ThresholdPresentationData tpInfo = entry.getValue();
                StatData stat = tpInfo.getStatData();
                StringBuilder sb = new StringBuilder();
                if (tpInfo.getThreshType() == Constants.THRESHOLD_TYPE_PING) {
                    sb.append("PING ");
                } else if (tpInfo.getThreshType() == Constants.THRESHOLD_TYPE_RECV) {
                    sb.append("RECV ");
                } else if (tpInfo.getThreshType() == Constants.THRESHOLD_TYPE_XMIT) {
                    sb.append("XMIT ");
                }
                sb.append(";");
                sb.append(tpInfo.getIpaddress());
                sb.append(";");
                sb.append(tpInfo.getNetwork());
                sb.append(";");
                sb.append(stat.getCount());
                sb.append(";");
                sb.append(df2.format(stat.getMin()));
                sb.append(";");
                sb.append(df2.format(stat.getMax()));
                sb.append(";");
                sb.append(df2.format(stat.getAvg()));
                sb.append(";");
                sb.append(df4.format(stat.getStddev()));
                sb.append(";");
                sb.append(stat.getStdOverToString());
                sb.append(";");
                double maxOverAvg = stat.getMax() - stat.getAvg();
               // sb.append(df2.format(maxOverAvg / stat.getStddev()));
                //sb.append(";");
                sb.append(df2.format(stat.getAvg() + (5 * stat.getStddev())));
                sb.append(";");
                sb.append(df2.format(stat.getCur_threshold()));
                sb.append(";");
                sb.append(df2.format(tpInfo.getRecommend_threshold()));
                sb.append(";");
                sb.append(df2.format(tpInfo.getCountOverRec()));
                System.out.println(sb.toString());
                os.write(sb.toString().getBytes());
                os.write(lf.getBytes());
                // System.out.println(sb.toString());
            }
            if(fOut.createNewFile()){
                System.out.println("file created");
            }
            os.close();
        } catch (IOException e) {
            logger.error("Exception in saveCSVtoFile "+ e);
            returnInfo.makeError();
            String message = "IOException: " + e.getMessage();
            returnInfo.setErrorText(message);
        }
        return returnInfo;
    }

    public static ErrorInfo saveCSVtoFileConnection(ConnectionInfoInfo connInfo, String filename) {
        ErrorInfo returnInfo = new ErrorInfo();
        String fullFilename = setFilePath(filename);
        File fOut = new File( fullFilename );
        OutputStream os = null;
        logger.debug("create: fOut name = " + fOut.getAbsolutePath() );
        //Delete/empty folder before adding new files, as this is a temp folder ,finally the file will be uploaded to clients default download folder.
        fOut.getParentFile().delete();
        fOut.getParentFile().mkdirs();
        fOut.deleteOnExit();
        String lf = System.lineSeparator();
        StringBuilder header = new StringBuilder();
        try {
            os = new FileOutputStream(fOut);
            header.append("IP Address");
            header.append(";");
            header.append("Network");
            header.append(";");
            header.append("Name");
            header.append(";");
            header.append("vendor");
            header.append(";");
            header.append("Join Time");
            header.append(";");
            header.append("Join Days");
            header.append(";");
            header.append("Last Time");
            header.append(";");
            header.append("Last Days");
            header.append(";");
            header.append("Connected?");
            header.append(";");
            header.append("Critical State");
            //
            os.write(header.toString().getBytes());
            os.write(lf.getBytes());
            //
            //System.out.println(header.toString());

            for (Map.Entry<String, ConnectionInfoData> entry : connInfo.getConnTable().entrySet()) {
                String key = entry.getKey();
                ConnectionInfoData info = entry.getValue();
                StringBuilder sb = new StringBuilder();
                sb.append(info.getIpaddress());
                sb.append(";");
                sb.append(info.getNetwork());
                sb.append(";");
                sb.append(info.getDeviceName());
                sb.append(";");
                sb.append(info.getVendor());
                sb.append(";");
                sb.append(info.getJoinTime());
                sb.append(";");
                sb.append(info.getJoinDays());
                sb.append(";");
                sb.append(info.getLastTime());
                sb.append(";");
                sb.append(info.getLastDays());
                sb.append(";");
                sb.append(info.isConnected());
                sb.append(";");
                sb.append(info.getCriticalState());
                os.write(sb.toString().getBytes());
                os.write(lf.getBytes());
                // System.out.println(sb.toString());
            }
            if(fOut.createNewFile()){
                System.out.println("file created");
            }
            os.close();
        } catch (IOException e) {
            logger.error( "Error in Saving connectionFile as CSV "+ e );
            returnInfo.makeError();
            String message = "FileUploadException: " + e.getMessage();
            returnInfo.setErrorText(message);
        }
        return returnInfo;
    }

    public static ErrorInfo saveDisconnectedReportCSVtoFile(DisconnectionsByDayInfo presentationInfo, String filename ) {
        ErrorInfo returnInfo = new ErrorInfo();
        String fullFilename = setFilePath(filename);
        File fOut = new File( fullFilename );
        OutputStream os = null;
        logger.debug("create: fOut name = " + fOut.getAbsolutePath() );
        //Incase if directory not exist, create it
        fOut.getParentFile().mkdirs();
        String lf = System.lineSeparator();
        try {
            os = new FileOutputStream(fOut);
            StringBuilder line = new StringBuilder();
            line.append("ip address");
            line.append(";");
            line.append("Network");
            line.append(";");
            line.append("Name");
            line.append(";");
            SimpleDateFormat newFormat = new SimpleDateFormat("MM/dd");
            ArrayList<Date> dates = presentationInfo.getDates();
            for (Date date : dates) {
                line.append(newFormat.format(date));
                line.append(";");
            }
            line.append("Total");
            os.write(line.toString().getBytes());
            os.write(lf.getBytes());

            //=====================================================
            // now data
            for (Map.Entry<Integer, DisconnectionByDayData> entry : presentationInfo.getDisconnectionByDayDataHashMap().entrySet()) {
                DisconnectionByDayData v = entry.getValue();
                line = new StringBuilder();
                line.append(v.getIp());
                line.append(";");
                line.append(v.getNetwork());
                line.append(";");
                line.append(v.getName());
                line.append(";");
                ArrayList<Integer> values = v.getDayTotals();
                for (Integer cell : values) {
                    line.append(cell);
                    line.append(";");
                }
                line.deleteCharAt(line.length() - 1);
                os.write(line.toString().getBytes());
                os.write(lf.getBytes());
            }
            os.close();
        } catch (IOException e) {
            logger.error( "Exception in saveDisconnectedReportCSVtoFile "+ e);
            returnInfo.makeError();
            String message = "IOException: " + e.getMessage();
            returnInfo.setErrorText(message);
        }
        return returnInfo;
    }

    public static ErrorInfo saveArpToCsvFileArp(ArrayList<ArpDataExtended> list, String filename) {
        ErrorInfo errorInfo = new ErrorInfo();
        String fullFilename = setFilePath(filename);
        File fOut = new File( fullFilename );
        OutputStream os = null;
        logger.debug("create: fOut name = " + fOut.getAbsolutePath() );
        //Incase if directory not exist, create it
        fOut.getParentFile().mkdirs();
        String lf = System.lineSeparator();
        try {
            os = new FileOutputStream(fOut);
            // print header
            StringBuilder header = new StringBuilder();
            header.append("Mac Address");
            header.append(";");
            header.append("IP Address");
            header.append(";");
            header.append("Name");
            header.append(";");
            header.append("Interface");
            header.append(";");
            header.append("IF Description");
            header.append(";");
            header.append("Vendor");
            os.write(header.toString().getBytes());
            os.write(lf.getBytes());
            //
            for( int i=0; i<list.size(); i++) {
                StringBuilder sb = new StringBuilder();
                ArpDataExtended data = list.get(i);
                sb.append(data.getMac());
                sb.append(";");
                sb.append(data.getIp());
                sb.append(";");
                sb.append(data.getName());
                sb.append(";");
                sb.append(data.getIfNum());
                sb.append(";");
                sb.append(data.getIfDescription());
                sb.append(";");
                sb.append(data.getVendor());
                os.write(sb.toString().getBytes());
                os.write(lf.getBytes());
            }
            os.close();
        } catch (IOException e) {
            logger.error( "Exception in saveArpToCsvFileArp "+ e);
            errorInfo.makeError();
            String message = "IOException: " + e.getMessage();
            errorInfo.setErrorText(message);
        }
        return errorInfo;
    }

    public static ErrorInfo saveMacToCsvFile(ArrayList<MacInfo> list, String filename) {
        ErrorInfo errorInfo = new ErrorInfo();
        String fullFilename = setFilePath(filename);
        File fOut = new File( fullFilename );
        OutputStream os = null;
        logger.debug("create: fOut name = " + fOut.getAbsolutePath() );
        //Incase if directory not exist, create it
        fOut.getParentFile().mkdirs();
        String lf = System.lineSeparator();
        try {
            os = new FileOutputStream(fOut);
            // print header
            StringBuilder header = new StringBuilder();
            header.append("Mac Address");
            header.append(";");
            header.append("Port");
            header.append(";");
            header.append("Port Description");
            header.append(";");
            header.append("Vlan");
            header.append(";");
            header.append("IP Address");
            header.append(";");
            header.append("Name");
            header.append(";");
            header.append("Vendor");
            os.write(header.toString().getBytes());
            os.write(lf.getBytes());
            //
            for( int i=0; i<list.size(); i++) {
                StringBuilder sb = new StringBuilder();
                MacInfo data = list.get(i);
                sb.append(data.getMac());
                sb.append(";");
                sb.append(data.getPort());
                sb.append(";");
                sb.append(data.getPortDescription());
                sb.append(";");
                sb.append(data.getVlanNum());
                sb.append(";");
                sb.append(data.getIp());
                sb.append(";");
                sb.append(data.getName());
                sb.append(";");
                sb.append(data.getVendor());
                os.write(sb.toString().getBytes());
                os.write(lf.getBytes());
            }
            os.close();
        } catch (IOException e) {
            logger.error( "Exception in saveMacToCsvFile "+ e);
            errorInfo.makeError();
            String message = "IOException: " + e.getMessage();
            errorInfo.setErrorText(message);
        }
        return errorInfo;
    }

    /*
    private String key;
    int mgt;
    int vlan;
    int type;
     */
    public static ErrorInfo saveVlanToCsvFile(ArrayList<CiscoVlanData> list, String filename) {
        ErrorInfo errorInfo = new ErrorInfo();
        String fullFilename = setFilePath(filename);
        File fOut = new File( fullFilename );
        OutputStream os = null;
        logger.debug("create: fOut name = " + fOut.getAbsolutePath() );
        //Incase if directory not exist, create it
        fOut.getParentFile().mkdirs();
        String lf = System.lineSeparator();
        try {
            os = new FileOutputStream(fOut);
            // print header
            StringBuilder header = new StringBuilder();
            header.append("VLAN");
            header.append(";");
            header.append("Type");
            header.append(";");
            header.append("mgt");
            os.write(header.toString().getBytes());
            os.write(lf.getBytes());
            //
            for( int i=0; i<list.size(); i++) {
                StringBuilder sb = new StringBuilder();
                CiscoVlanData data = list.get(i);
                sb.append(data.getVlan());
                sb.append(";");
                sb.append(data.getType());
                sb.append(";");
                sb.append(data.getMgt());
                os.write(sb.toString().getBytes());
                os.write(lf.getBytes());
            }
            os.close();
        } catch (IOException e) {
            logger.error( "Exception in saveVlanToCsvFile "+ e);
            errorInfo.makeError();
            String message = "IOException: " + e.getMessage();
            errorInfo.setErrorText(message);
        }
        return errorInfo;	}

    /*
    private int portNumber;
    private int portIf;
    private String description;
    private  int type;
    private  long speed;
    private String key;
     */
    public static ErrorInfo savePortDataToCsvFile(ArrayList<PortDataExtended> list, String filename) {
        ErrorInfo errorInfo = new ErrorInfo();
        String fullFilename = setFilePath(filename);
        File fOut = new File( fullFilename );
        OutputStream os = null;
        logger.debug("create: fOut name = " + fOut.getAbsolutePath() );
        //Incase if directory not exist, create it
        fOut.getParentFile().mkdirs();
        String lf = System.lineSeparator();
        try {
            os = new FileOutputStream(fOut);
            // print header
            StringBuilder header = new StringBuilder();
            header.append("Port");
            header.append(";");
            header.append("Port Description");
            header.append(";");
            header.append("Interface");
            header.append(";");
            header.append("Speed");
            header.append(";");
            header.append("Type");
            os.write(header.toString().getBytes());
            os.write(lf.getBytes());
            //
            for( int i=0; i<list.size(); i++) {
                StringBuilder sb = new StringBuilder();
                PortDataExtended data = list.get(i);
                sb.append(data.getPortNumber());
                sb.append(";");
                sb.append(data.getDescription());
                sb.append(";");
                sb.append(data.getPortIf());
                sb.append(";");
                sb.append(data.getSpeed());
                sb.append(";");
                sb.append(data.getType());
                os.write(sb.toString().getBytes());
                os.write(lf.getBytes());
            }
            os.close();
        } catch (IOException e) {
            logger.error( "Exception in savePortDataToCsvFile "+ e);
            errorInfo.makeError();
            String message = "IOException: " + e.getMessage();
            errorInfo.setErrorText(message);
        }
        return errorInfo;
    }

    /*
    private String key;
    private int ifID;
    private String description;
    private  int type;
    private  long speedBps;
    private  long speedMps;
    private String mac;
     */
    public static ErrorInfo saveIfDataToCsvFile(ArrayList<InterfaceData> list, String filename) {
        ErrorInfo errorInfo = new ErrorInfo();
        String fullFilename = setFilePath(filename);
        File fOut = new File( fullFilename );
        OutputStream os = null;
        logger.debug("create: fOut name = " + fOut.getAbsolutePath() );
        //Incase if directory not exist, create it
        fOut.getParentFile().mkdirs();
        String lf = System.lineSeparator();
        try {
            os = new FileOutputStream(fOut);
            // print header
            StringBuilder header = new StringBuilder();
            header.append("Interface");
            header.append(";");
            header.append("If Description");
            header.append(";");
            header.append("Speed (bps)");
            header.append(";");
            header.append("Speed (Mps)");
            header.append(";");
            header.append("Type");
            os.write(header.toString().getBytes());
            os.write(lf.getBytes());
            //
            for( int i=0; i<list.size(); i++) {
                StringBuilder sb = new StringBuilder();
                InterfaceData data = list.get(i);
                sb.append(data.getIfID());
                sb.append(";");
                sb.append(data.getDescription());
                sb.append(";");
                sb.append(data.getSpeedBps());
                sb.append(";");
                sb.append(data.getSpeedMps());
                sb.append(";");
                sb.append(data.getType());
                os.write(sb.toString().getBytes());
                os.write(lf.getBytes());
            }
            os.close();
        } catch (IOException e) {
            logger.error( "Exception in saveIfDataToCsvFile "+ e);
            errorInfo.makeError();
            String message = "IOException: " + e.getMessage();
            errorInfo.setErrorText(message);
        }
        return errorInfo;
    }

    public static ErrorInfo saveIfInErrortoCSVFile( SwitchErrorInfo switchErrorInfo, String filename) {
        ErrorInfo returnInfo = new ErrorInfo();
        String fullFilename = setFilePath(filename);
        File fOut = new File( fullFilename );
        OutputStream os = null;
        logger.debug("create: fOut name = " + fOut.getAbsolutePath() );
        //Incase if directory not exist, create it
        fOut.getParentFile().mkdirs();
        String lf = System.lineSeparator();
        StringBuilder line = new StringBuilder();
        try {
            os = new FileOutputStream(fOut);
            line.append("ip address");
            line.append(";");
            line.append("port");
            line.append(";");
            line.append("port description");
            line.append(";");
            SimpleDateFormat newFormat = new SimpleDateFormat("MM-dd HH:00");
            ArrayList<Date> dates = switchErrorInfo.getDates();
            for (Date date : dates) {
                line.append( newFormat.format(date));
                line.append(";");
            }
            line.append("Total");
            //
            os.write(line.toString().getBytes());
            os.write(lf.getBytes());
            //
            SwitchErrorData data;
            for (Map.Entry<String, SwitchErrorData> entry : switchErrorInfo.getErrorMap().entrySet()) {
                SwitchErrorData v = entry.getValue();
                line = new StringBuilder();
                line.append(v.getIp());
                line.append(";");
                line.append(v.getPort());
                line.append(";");
                line.append(v.getPortDesc());
                line.append(";");
                ArrayList<Integer> values = v.getValues();
                for (Integer cell : values) {
                    line.append(cell);
                    line.append(";");
                }
                line.deleteCharAt(line.length() - 1);
                os.write(line.toString().getBytes());
                os.write(lf.getBytes());
                // System.out.println(line.toString());
            }
            os.close();
        } catch (IOException e) {
            logger.error( "Exception in saveIfInErrortoCSVFile "+ e);
            returnInfo.makeError();
            String message = "FileUploadException: " + e.getMessage();
            returnInfo.setErrorText(message);
        }
        return returnInfo;
    }


    public static ErrorInfo saveIpToCsvFile(ArrayList<IpData> list, String filename) {
        ErrorInfo errorInfo = new ErrorInfo();
        String fullFilename = setFilePath(filename);
        File fOut = new File( fullFilename );
        OutputStream os = null;
        logger.debug("create: fOut name = " + fOut.getAbsolutePath() );
        //Incase if directory not exist, create it
        fOut.getParentFile().mkdirs();
        String lf = System.lineSeparator();
        try {
            os = new FileOutputStream(fOut);
            // print header
            StringBuilder header = new StringBuilder();
            header.append("Ip Address ");
            header.append(";");
            header.append("Interface Number");
            header.append(";");
            header.append("Netmask");

            os.write(header.toString().getBytes());
            os.write(lf.getBytes());
            //
            for( int i=0; i<list.size(); i++) {
                StringBuilder sb = new StringBuilder();
                IpData data = list.get(i);
                sb.append(data.getIp());
                sb.append(";");
                sb.append(data.getInterfaceNum());
                sb.append(";");
                sb.append(data.getNetMask());

                os.write(sb.toString().getBytes());
                os.write(lf.getBytes());
            }
            os.close();
        } catch (IOException e) {
            logger.error( "Exception in saveIpToCsvFile "+ e);
            errorInfo.makeError();
            String message = "IOException: " + e.getMessage();
            errorInfo.setErrorText(message);
        }
        return errorInfo;
    }

    private static String setFilePath(String filename) {
        String baseDir = System.getProperty("catalina.home");
        String fullFilename = String.format("%s/webapps/" + Globals.WEBAPP_NAME + "/exports/"+filename+".csv", baseDir);
        logger.info("Export Path is "+ fullFilename);
        try {
            String folder = fullFilename.substring(0, fullFilename.lastIndexOf("/"));
            FileUtils.deleteDirectory(new File(folder));
        }catch (Exception ex)
        {
            logger.warn("Error in deleting Folder contents for export Folder:"+ ex);
        }
        return fullFilename;
    }




}
