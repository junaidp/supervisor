package com.wbc.supervisor.server.dashboardutilities;

import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.Globals;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import static com.wbc.supervisor.client.dashboardutilities.Constants.*;

/*
    Sample test.properties file

    run.test=true
    test.ip=10.1.1.5
    tests=connectionreport,kpi,disconnected

    Set run.text to true or false
    place this file at root of wbcUtilities

 */
public class ServerTest {

    public static void runStartupTests(String sWbcAppDir, Logger logger ) {
        Properties testProps = new Properties();
        try {
            String propfilename =  sWbcAppDir + "/test.properties" ;
            File fProps = new File( propfilename );
            if ( !fProps.exists()) {
                logger.warn( "test prop file does not exist, not running tests ");
            }
            else
            {
                InputStream is = new FileInputStream( fProps);
                testProps.load(is);
                is.close();
                String doTest = testProps.getProperty("run.test");
                if ( ! doTest.equalsIgnoreCase("true")) {
                    logger.debug("test.properties file set to NOT do tests");
                    return;
                }
                String ip = testProps.getProperty("test.ip");
                if ( ip != null && !ip.isEmpty()) {
                    String tests = testProps.getProperty("tests");
                    String[] tokens = tests.split(",");
                    if (tokens.length > 0) {
                        for (int i=0; i<tokens.length; i++) {
                            String test = tokens[i];
                            ErrorInfo result = new ErrorInfo();
                            if ( test.equals("connectionreport")) {
                                result = getDataFromHost( ip, URL_CONNECTION_REPORT, logger);
                            } else if ( test.equals("kpi")) {
                                result = getDataFromHost( ip, URL_KPI_MANAGEMENT, logger);
                            } else if ( test.equals("disconnected")) {
                                result = getDataFromHost( ip, URL_DISCONNECTED_STATISTIC_REPORT, logger);
                            } else {
                                result.makeError();
                                result.setErrorText("unhandled test found in test.properties = " + test );
                            }
                            logger.info("Result of test " + result.toString());
                        }
                    } else {
                        logger.warn("No tests found in test.properties");
                    }
                } else {
                    logger.info("test.properties exists but no valid test.ip");
                }
            }
        } catch ( Exception ex ) {
            logger.fatal( "runStartupTests Exception " + ex.toString() ) ;
        }
    }


    public static ErrorInfo getDataFromHost(String ip, String paramString, Logger logger) {
        ErrorInfo responseInfo = new ErrorInfo();
        logger.info("getDataFromHost ip="+ip + ", " + paramString);
        try {
            //Globals not accessible here this way
            String target = "http://" + ip + ":8765/" + Globals.WBCSERVER_NAME + "/"  + paramString ;
            URL url = new URL( target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            logger.info("test before conn");
            if (conn.getResponseCode() != 200) {
                responseInfo.setResult("error");
                responseInfo.setErrorText("Failed to get PK: HTTP error code : " + conn.getResponseCode());
                logger.error( responseInfo.getErrorText());
                return responseInfo;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            logger.info("test reading stream");
            String output;
            String pk = "";
            boolean found = false;
            while ((output = br.readLine()) != null) {
                logger.info("Readling line | " + output);
            }
            br.close();
            logger.info("test finished stream");
            conn.disconnect();
        } catch (MalformedURLException e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("getDataFromHost MalformedURLException %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        } catch (IOException e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("getDataFromHost IOException %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        } catch (Exception e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("getDataFromHost Exception %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        }
        logger.info("test getDataFromHost end " + responseInfo.toString());
        return responseInfo;
    }


}
