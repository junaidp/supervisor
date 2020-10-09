package com.wbc.supervisor.server.dashboardutilities;

import com.wbc.supervisor.server.supervisorServiceImpl;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHost;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostDTO;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IvHostHelper {

    private static final String SEP_CHAR = ";";
    private static final int IP_IDX = 0;
    private static final int NAME_IDX = 1;
    private static final int EMAILS_IDX = 2;
    private static final int PK_IDX = 3;
    private static final int EXPIRES_IDX = 4;
    private static final int SERVERVERSION_IDX = 5;
    private static final int KEYCODE_IDX = 6;

    private static boolean debug = false;

    public IvHostHelper() {
    }

    // NOTE: NO HOST FILES IS CREATED BY DEFAULT
    public ErrorInfo createHostsData(Logger logger) {
        if(debug) logger.info("IvHostHelper:createHostData called.");
        ErrorInfo errorInfo = new ErrorInfo();
        IntravueHost host = new IntravueHost();
        host.setHostEmails("");
        host.setHostip("127.0.0.1");
        host.setHostname("Localhost");
        List<IntravueHost> hosts = new ArrayList<>();
        hosts.add(host);
        errorInfo = writeHosts(hosts, logger);
        if(debug) logger.info("IvHostHelper:createHostData result " + errorInfo.toString());
        return errorInfo;
    }

    public static File getHostFile() {
        String appDir = supervisorServiceImpl.getAppDir();
        //String hostname =  "/Users/junaidp/IdeaProjects/wbcUtilities/data/hostdata.csv";
        String hostfile = appDir + "/data/hostdata.csv";
        return new File( hostfile);
    }

    public static String getAuthorizedPkFilename() {
        String dbPksFilename =  supervisorServiceImpl.webinfPath + "/static/dbpks.txt";
        return dbPksFilename;
    }

    public IntravueHostDTO getIntravueHosts(Logger logger) {
        if(debug) logger.info("IvHostHelper:getIntravueHosts called.");
        IntravueHostDTO dto = new IntravueHostDTO();
        File hostFile = getHostFile();
        if ( !hostFile.exists()) {
            logger.info("getIntravueHosts: Host file does not exist, creating " + hostFile.getAbsolutePath());
            try {
                hostFile.createNewFile();
                if ( !hostFile.canRead()) hostFile.setReadable( true, false );
                if ( !hostFile.canWrite()) hostFile.setWritable( true, false );
                if ( !hostFile.canRead()) {
                    dto.getErrorInfo().makeError();
                    dto.getErrorInfo().setErrorText("The hostfile can't be read after setting permissions");
                    logger.error(  dto.getErrorInfo().getErrorText());
                } else if ( !hostFile.canWrite()) {
                    dto.getErrorInfo().makeError();
                    dto.getErrorInfo().setErrorText("The hostfile can't be written after setting permissions");
                    logger.error(  dto.getErrorInfo().getErrorText());
                } else {
                    if (hostFile.getAbsolutePath().startsWith("/")) {
                        // NOTE: Test for canRead and canWrite are passing but a access denied exception is still being thrown
                        Path path = Paths.get(hostFile.getAbsolutePath());
                        if (!Files.exists(path)) Files.createFile(path);
                        Set<PosixFilePermission> perms = Files.readAttributes(path, PosixFileAttributes.class).permissions();
                        logger.info(String.format("Permissions before: %s%n", PosixFilePermissions.toString(perms)));

                        perms.add(PosixFilePermission.OWNER_WRITE);
                        perms.add(PosixFilePermission.OWNER_READ);
                        perms.add(PosixFilePermission.OWNER_EXECUTE);
                        perms.add(PosixFilePermission.GROUP_WRITE);
                        perms.add(PosixFilePermission.GROUP_READ);
                        perms.add(PosixFilePermission.GROUP_EXECUTE);
                        perms.add(PosixFilePermission.OTHERS_WRITE);
                        perms.add(PosixFilePermission.OTHERS_READ);
                        perms.add(PosixFilePermission.OTHERS_EXECUTE);
                        Files.setPosixFilePermissions(path, perms);
                        logger.info(String.format("Permissions after:  %s%n", PosixFilePermissions.toString(perms)));

                        dto.getErrorInfo().makeWarning();
                        dto.getErrorInfo().setErrorText("The host file did not exist, created a new host file.  Add a new host to connect.");
                        logger.error(dto.getErrorInfo().getErrorText());
                        logger.info("SANITY 4");
                    }
                }
            } catch (IOException e) {
                dto.getErrorInfo().makeError();
                dto.getErrorInfo().setErrorText("getIntravueHosts IOException creating new host file " + e.getMessage() );
                logger.error(  dto.getErrorInfo().getErrorText());
            } catch (IllegalArgumentException e) {
                dto.getErrorInfo().makeError();
                dto.getErrorInfo().setErrorText("getIntravueHosts IllegalArgumentException creating new host file " + e.getMessage() );
                logger.error(  dto.getErrorInfo().getErrorText());
                // more debug, exception message is null
                logger.debug(supervisorServiceImpl.getExceptionStackLines("getIntravueHosts Exception follows", e, 20 ));
            } catch (ClassCastException e) {
                dto.getErrorInfo().makeError();
                dto.getErrorInfo().setErrorText("getIntravueHosts ClassCastException creating new host file " + e.getMessage() );
                logger.error(  dto.getErrorInfo().getErrorText());
                // more debug, exception message is null
                logger.debug(supervisorServiceImpl.getExceptionStackLines("getIntravueHosts Exception follows", e, 20 ));
            } catch (Exception e) {
                dto.getErrorInfo().makeError();
                dto.getErrorInfo().setErrorText("getIntravueHosts Exception creating new host file " + e.getMessage() );
                logger.error(  dto.getErrorInfo().getErrorText());
                // more debug, exception message is null
                logger.debug(supervisorServiceImpl.getExceptionStackLines("getIntravueHosts Exception follows", e, 20 ));
            }
        }
        if (dto.getErrorInfo().isOK()) {
            dto = readHosts(logger);
        }
        // logger.debug("DELETE THIS.  test only. result of getIntravueHosts passed back to client > " + dto.getErrorInfo().toString() );
        return dto;
    }

    public ErrorInfo saveIntravueHost( IntravueHost newHost, Logger logger ) {
        if(debug) logger.info("IvHostHelper:saveIntravueHost called for " + newHost.toString());
        ErrorInfo errorInfo = new ErrorInfo();
        // read all tho host
        // read all hosts
        IntravueHostDTO dto = getIntravueHosts(logger);
        List<IntravueHost> oldlist = dto.getHostlist();
        if (dto.getErrorInfo().isOK() || dto.getErrorInfo().getResult().equals("warning")) {
            if (dto.getErrorInfo().getResult().equals("warning")) {
                dto.getErrorInfo().setResult("ok");
            }
            // iterate  hosts looking for this host
            boolean found = false;
            for( IntravueHost host : oldlist) {
                if ( host.getHostip().equals(newHost.getHostip())) {
                    errorInfo.makeError();
                    errorInfo.setErrorText("A host already exists for a host with IP " + newHost.getHostip() );
                    found = true;
                    break;
                }
                // new 8/4/2020 - Do not allow two names to be the same
                if ( host.getHostname().equals(newHost.getHostname())) {
                    errorInfo.makeError();
                    errorInfo.setErrorText("A host already exists with the same name as " + newHost.getHostname() );
                    found = true;
                    break;
                }

            }
            if (!found) {
                // write hosts back
                oldlist.add(newHost);
                dto.setErrorInfo( writeHosts(oldlist, logger));
            }
        } else {
            logger.error("no host file found");
        }
        return errorInfo;
    }


    public ErrorInfo removeIntravueHost( IntravueHost hostToRemove, Logger logger ) {
        if(debug) logger.info("IvHostHelper:removeIntravueHost called.");
        ErrorInfo errorInfo = new ErrorInfo();
        // read all hosts
        IntravueHostDTO dto = getIntravueHosts(logger);
        if (dto.getErrorInfo().isOK()) {
            // iterate  hosts looking for this host
            List<IntravueHost> oldlist = dto.getHostlist();
            List<IntravueHost> newList = new ArrayList<>();
            boolean found = false;
            for( IntravueHost host : oldlist) {
                if ( !host.getHostip().equals(hostToRemove.getHostip())) {
                    newList.add(host);
                } else {
                    found = true;
                }
            }
            // write hosts back
            errorInfo = writeHosts( newList, logger );
            if (!found) {
                errorInfo.setResult("warning");
                errorInfo.setErrorText("A host entry was not found for a host with ip = " + hostToRemove.getHostip() );
            }
        }
        return errorInfo;
    }

    public ErrorInfo updateIntravueHost( IntravueHost hostToChange, Logger logger ) {
        if(debug) logger.info("IvHostHelper:updateIntravueHost called.");
        ErrorInfo errorInfo = new ErrorInfo();
        // read all hosts
        IntravueHostDTO dto = getIntravueHosts(logger);
        if (dto.getErrorInfo().isOK()) {
            // iterate  hosts looking for this host
            List<IntravueHost> oldlist = dto.getHostlist();
            List<IntravueHost> newList = new ArrayList<>();
            for( IntravueHost host : oldlist) {
                if ( !host.getHostip().equals(hostToChange.getHostip())) {
                    newList.add(host);
                } else {
                    newList.add(hostToChange);
                }
            }
            // write hosts back
            errorInfo = writeHosts( newList, logger );
        }
        return errorInfo;
    }


    private ErrorInfo writeHosts( List<IntravueHost> list, Logger logger ) {
        ErrorInfo errorInfo = new ErrorInfo();
        File hostFile = getHostFile();
        if(debug) logger.info("IvHostHelper:writeHosts called, file = " + hostFile.getAbsolutePath());
        FileWriter fileWriter = null;
        try {
            if ( ! hostFile.exists()) {
                // the file does not exist create it
                logger.info("writeHosts: Host file does not exist, creating " + hostFile.getAbsolutePath());
                hostFile.createNewFile();
            }
            fileWriter = new FileWriter(hostFile);
            /*
                            private static final int IP_IDX = 0;
                            private static final int NAME_IDX = 1;
                            private static final int EMAILS_IDX = 2;
                            private static final int PK_IDX = 3;
                            private static final int EXPIRES_IDX = 4;
                            private static final int SERVERVERSION_IDX = 5;
             */
            // no headers
            for ( IntravueHost host : list ) {
                fileWriter.append(host.getHostip());
                fileWriter.append(SEP_CHAR);
                fileWriter.append(host.getHostname());
                fileWriter.append(SEP_CHAR);
                fileWriter.append(host.getHostEmails());
                fileWriter.append(SEP_CHAR);
                fileWriter.append(host.getPk());
                fileWriter.append(SEP_CHAR);
                fileWriter.append(host.getExpireDate());
                fileWriter.append(SEP_CHAR);
                fileWriter.append(host.getWbcserverVersion());
                fileWriter.append(SEP_CHAR);
                fileWriter.append(host.getKeycode());
                fileWriter.append('\n');
                if (debug) logger.info("IvHostHelper:writeHosts: wrote " + host.toString());
            }
        } catch (Exception e) {
            errorInfo.makeError();
            errorInfo.setErrorText("createHostsData Exception: " + e.getMessage());
            logger.error( errorInfo.getErrorText());
        } finally {
            try {
                if (fileWriter != null) {
                    fileWriter.flush();
                    fileWriter.close();
                }
            } catch (IOException e) {
                errorInfo.makeError();
                errorInfo.setErrorText("createHostsData flushing/closiing IOException: " + e.getMessage());
                logger.error( errorInfo.getErrorText());
            }
        }
        return errorInfo;
    }

    private IntravueHostDTO readHosts(Logger logger) {
        if(debug) logger.info("IvHostHelper:readHosts called.");
        IntravueHostDTO dto = new IntravueHostDTO();
        List<IntravueHost> list = dto.getHostlist();
        BufferedReader fileReader = null;
        File hostFile = getHostFile();
        if ( ! hostFile.exists()) {
            dto.getErrorInfo().setResult("warning");
            dto.getErrorInfo().setErrorText("A host file (" + hostFile.getAbsolutePath() + ") does not exist yet.  Please add a host");
            logger.error( dto.getErrorInfo().getErrorText());
            return dto;
        }
        if ( !hostFile.canRead()) {
            dto.getErrorInfo().makeError();
            dto.getErrorInfo().setErrorText("The hostfile does not have read permissions");
            logger.error(dto.getErrorInfo().getErrorText());
        } else if ( !hostFile.canWrite()) {
            dto.getErrorInfo().makeError();
            dto.getErrorInfo().setErrorText("The hostfile does not have write permissions to update hosts");
            logger.error(  dto.getErrorInfo().getErrorText());
        }
        if ( dto.getErrorInfo().isError()) {
            return dto;
        }
        // logger.debug("TEST: hostfile has read and write permissions");
        try {
            String line = "";
            fileReader = new BufferedReader(new FileReader(hostFile));
            /*
                            private static final int IP_IDX = 0;
                            private static final int NAME_IDX = 1;
                            private static final int EMAILS_IDX = 2;
                            private static final int PK_IDX = 3;
                            private static final int EXPIRES_IDX = 4;
                            private static final int SERVERVERSION_IDX = 5;
                            private static final int KEYCODE_IDX = 6;
             */

            while ((line = fileReader.readLine()) != null) {
                if (line.startsWith("#")) continue;  // ignore any line with a #
                String[] tokens = line.split(SEP_CHAR);
                IntravueHost ivhost = new IntravueHost();
                if (tokens.length > IP_IDX) {
                    ivhost.setHostip(tokens[IP_IDX]);
                }
                if (tokens.length > NAME_IDX) {
                    ivhost.setHostname(tokens[NAME_IDX]);
                }
                if (tokens.length >EMAILS_IDX ) {
                    ivhost.setHostEmails(tokens[EMAILS_IDX]);
                }
                if (tokens.length > PK_IDX) {
                    ivhost.setPk(tokens[PK_IDX]);
                }
                if (tokens.length > EXPIRES_IDX) {
                    ivhost.setExpireDate(tokens[EXPIRES_IDX]);
                }
                if (tokens.length>SERVERVERSION_IDX) {
                    ivhost.setWbcserverVersion(tokens[SERVERVERSION_IDX]);
                }
                if (tokens.length>KEYCODE_IDX) {
                    ivhost.setKeycode(tokens[KEYCODE_IDX]);
                }
                list.add( ivhost);
                if (debug) logger.debug("found host "+ ivhost.toString());
            }
        } catch (Exception e) {
            dto.getErrorInfo().makeError();
            dto.getErrorInfo().setErrorText("getIntravueHosts Reading hosts Exception: " + e.getMessage() );
            logger.error( dto.getErrorInfo().getErrorText());
        } finally {
            try {
                if (fileReader != null) fileReader.close();
            } catch (IOException e) {
                dto.getErrorInfo().makeError();
                dto.getErrorInfo().setErrorText("getIntravueHosts Closing fileReader Exception: " + e.getMessage());
                logger.error( dto.getErrorInfo().getErrorText());
            }
        }
        if (dto.getHostlist().isEmpty()) {
            logger.info("getIntravueHosts no hosts in hostfile");
        }
        return dto;
    }

    /*
        {"keyCode":"2C7DBBF5","scvVerifies":true,"obsoletedPK":false,"usingSiteLicense":false,"verifies":0,"scv":"2006192654","regnCode":"FFFFFFFF3604A84D4CC0A5DC215E57D0","productKey":"3ALU4XX03EDA000S541698990"}
     */
    public static ErrorInfo getProductKeyAndKeycode(String ip,Logger logger) {
        boolean debugGetPK = false;
        ErrorInfo responseInfo = new ErrorInfo();
        if (debugGetPK) logger.info("test getProductKey ip="+ip);
        try {
            //Globals not accessible here this way
            String target = "http://" + ip + ":8765/iv2/license";
            URL url = new URL( target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/text");
            if (debugGetPK) logger.info("test getProductKey before conn");
            if (conn.getResponseCode() != 200) {
                responseInfo.setResult("error");
                responseInfo.setErrorText("Failed to get PK: HTTP error code : " + conn.getResponseCode());
                logger.error( responseInfo.getErrorText());
                return responseInfo;
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            if (debugGetPK) logger.info("test getProductKey reading stream");
            String output;
            String pk = "";
            String keycode="";
            boolean keycodeFound = false;
            while ((output = br.readLine()) != null) {
                if (debugGetPK) logger.info("Readling line | " + output);
                if (output.startsWith("{\"keyCode")) {
                    int index = output.indexOf(",", 12);
                    if (index < 17) {
                        // no keycode is this short, error
                        responseInfo.makeError();
                        responseInfo.setErrorText("keycode marker found in host, but no keycode reported");
                        logger.info( responseInfo.getErrorText());
                        break;
                    }
                    keycode = output.substring(12, index-1);
                    keycodeFound = true;
                    // ----------- handle PK on this line
                    index = output.indexOf("productKey") + 13;
                    if (index + 25 >= output.length()) {
                        pk="missing";
                    } else {
                        pk = output.substring(index, index + 25);
                    }
                    break;
                }
            }
            br.close();
            if (debugGetPK) logger.info("test getProductKey finished stream");
            if ( keycodeFound ) {
                responseInfo.setErrorText(pk+","+keycode);
                if (debugGetPK) logger.info("test getProductKey pkFound pk "+ pk + " keycode " + keycode);
            } else {
                if ( responseInfo.isOK()) {
                    responseInfo.makeError();
                    responseInfo.setErrorText("Keycode not found in request to host.");
                }
                logger.error( responseInfo.getErrorText());
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("getProductKey MalformedURLException %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        } catch (IOException e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("getProductKey IOException %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        } catch (Exception e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("getProductKey Exception %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        }
        if (debugGetPK) logger.info("test getProductKey end " + responseInfo.toString());
        return responseInfo;
    }

    public static ErrorInfo validateKeycode(String userEmail, String keycodeToCheck, String ipIvHost,Logger logger ) {
        ErrorInfo responseInfo = new ErrorInfo();
        boolean debugKc = false;
        try {
            // get the PK from the IV host
            String target = "http://" + ipIvHost + ":8765/iv2/license";
            URL url = new URL( target);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/text");

            if (conn.getResponseCode() != 200) {
                responseInfo.setResult("error");
                responseInfo.setErrorText("Failed to get PK: HTTP error code : " + conn.getResponseCode());
                logger.error( responseInfo.getErrorText());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            String keycode = "";
            String pkValidOnHostCode= "";
            boolean keycodeFound = false;
            boolean vFound = false;
            while ((output = br.readLine()) != null) {
                if (output.startsWith("{\"keyCode")) {
                    int index = output.indexOf(",", 12);
                    if (index < 12) {
                        // keycode is missing
                        logger.info("keycode marker found but was empty");
                        responseInfo.makeError();
                        responseInfo.setErrorText("Keycode data missing or not set in Intravue or IntraVUE is not registered");
                        break;
                    } else {
                        keycodeFound = true;
                        keycode = output.substring(12, index-1);
                        index = output.indexOf("verifies") + 10;
                        int index2 = output.indexOf(",", index);
                        pkValidOnHostCode = output.substring(index, index2);
                    }
                }
            }
            if ( keycodeFound ) {
                if (debugKc) logger.debug(("validateKeycode: keycode found"));
                if ( !keycode.equals(keycodeToCheck)) {
                    responseInfo.setResult("error");
                    responseInfo.setErrorText(String.format("Keycode found, %s, does not match Keycode requested %s.", keycode, keycodeToCheck));
                    logger.error( responseInfo.getErrorText());
                } else {
                    // check the PK in the authorized list
                    // String baseDir = System.getProperty("catalina.home");
                    String authenticationFilename = getAuthorizedPkFilename();
                    AuthenticationHandler handler = new AuthenticationHandler( authenticationFilename, keycode );
                    int result = handler.getAuthorization( pkValidOnHostCode, logger );
                    if (result != 102) {
                        responseInfo.setResult("error");
                        responseInfo.setErrorText(String.format( "Keycode authentication error for Keycode %s, %s. Please contract support", keycode, AuthenticationHandler.getMessageForCode(null, result) ));
                        logger.error( responseInfo.getErrorText());
                    } else {
                        // ok
                        if (debugKc) logger.debug("validateKeycode: authorization OK");
                    }
                }
            } else {
                responseInfo.setResult("error");
                responseInfo.setErrorText("Keycode not found.");
                logger.error( responseInfo.getErrorText());
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("validateKeycode MalformedURLException %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        } catch (IOException e) {
            responseInfo.setResult("error");
            responseInfo.setErrorText( String.format("validateKeycode IOException %s", e.getMessage()));
            logger.error( responseInfo.getErrorText());
        }
        return responseInfo;
    }

    public static ErrorInfo getExpirationDate( String keycode, Logger logger ) {
        ErrorInfo responseInfo = new ErrorInfo();
        String authenticationFilename = "";
        AuthenticationHandler handler;

        if (supervisorServiceImpl.webinfPath == null) {
            // This is a test
            logger.error("getExpirationDate: This message should ONLY be seen during an offline test.");
            authenticationFilename = "C:\\wbcUtilities\\tomcat8\\webapps\\wbcutil\\WEB-INF\\static\\dbpks.txt";
            handler = new AuthenticationHandler(authenticationFilename, keycode);
            responseInfo = handler.getExpiration();
        } else {
            authenticationFilename = getAuthorizedPkFilename();
            handler = new AuthenticationHandler(authenticationFilename, keycode);
            int result = handler.getAuthorization(keycode, logger);
            if (result != 102) {
                responseInfo.setResult("error");
                responseInfo.setErrorText(String.format("Keycode authentication error for Keycode %s, %s. Please contract support", keycode, AuthenticationHandler.getMessageForCode(null, result)));
                logger.error(responseInfo.getErrorText());
            } else {
                responseInfo = handler.getExpiration();
            }
        }
        return responseInfo;
    }

}
