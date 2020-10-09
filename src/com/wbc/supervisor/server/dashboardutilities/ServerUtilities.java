package com.wbc.supervisor.server.dashboardutilities;

import com.wbc.supervisor.shared.dashboardutilities.WbcFileInfo;
import com.wbc.supervisor.shared.dashboardutilities.threshold.ThresholdSetData;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

public class ServerUtilities {

    /*
    Utility to convert threshold recommended settings class into a string

    Call this with the list of changes.

    The return String will be a comma separated set of ThresholdSetData, separated by colons.

    The call appendMd5ToThresholdSet which will generate a MD5 hash of the data and add a false threshold = -1,-1,HASHHASHHASHHASHHASHHASHHASHHASH
     */
    public static String convertThresholdSetDataToString(ArrayList<ThresholdSetData> setList, Logger logger) {
        StringBuilder sb = new StringBuilder();
        for (ThresholdSetData data : setList ) {
            sb.append( data.getThreshid());
            sb.append(",");
            sb.append( data.getNewSetting());
            sb.append(",");
            sb.append( data.getThreshType());
            sb.append(";");
        }
        // remove trailing ;
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    /*
    Utility to add a fake ThresholdSetData that contains the MD5 of the data set and append it to the data string

    NOTE: Caller must Catch and handle Exceptions
     */
    public static String appendMd5ToThresholdSet( String thresholdString ) throws UnsupportedEncodingException, NoSuchAlgorithmException  {
        String md5 = SHAHelper.SHA1(thresholdString );
        return String.format("%s;-1,-1,%s", thresholdString, md5);
    }

    public static ArrayList<WbcFileInfo> getSwitchprobeLocalFiles(String folder, Logger logger ) {
        ArrayList<WbcFileInfo> files = new ArrayList<>();
        Path path = Paths.get(folder);
        try {
            Files.list(path).forEach(file -> {
                File probeFile = file.toFile();
                if (probeFile.getName().endsWith(".json")) {
                    WbcFileInfo info = new WbcFileInfo();
                    info.setName(probeFile.getName());
                    info.setKey( UUID.randomUUID() + ""); //Just to have some unique number which is required for the GXT grid.

                    info.setSize( String.valueOf( probeFile.length() ));
                    BasicFileAttributes attributes = null;
                    try {
                        attributes = Files.readAttributes(Paths.get(file.toString()), BasicFileAttributes.class);
                    } catch (IOException e) {
                        logger.error("getSwitchprobeLocalFiles IOException1: " + e.getMessage());
                    }
                    FileTime fileTime = attributes.creationTime();
                    Date date = new Date(((FileTime) fileTime).toMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    String formattedDate = sdf.format(date);
                    info.setDateString( formattedDate );

                    files.add(info);
                }
            });
        } catch (IOException e) {
            logger.error("getSwitchprobeLocalFiles IOException2: " + e.getMessage());
        }
        return files;
    }

    public static ArrayList<String> getSwitchprobeLocalFilesAsString( String folder, Logger logger ) {
        ArrayList<String> files = new ArrayList<>();
        Path path = Paths.get(folder);
        try {
            int maxNameLen = 60;
            int maxDateLen = 20; //show as 2020-08-05 04:36
            int maxSizeLen = 7;   // 1000999
            Files.list(path).forEach(file -> {
                File probeFile = file.toFile();
                if (probeFile.getName().endsWith(".json")) {
                    String filename = probeFile.getName();
                    String size = String.valueOf( probeFile.length() );
                    BasicFileAttributes attributes = null;
                    try {
                        attributes = Files.readAttributes(Paths.get(file.toString()), BasicFileAttributes.class);
                    } catch (IOException e) {
                        logger.error("getSwitchprobeLocalFiles IOException1: " + e.getMessage());
                    }
                    FileTime fileTime = attributes.creationTime();
                    Date date = new Date(((FileTime) fileTime).toMillis());
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    String formattedDate = sdf.format(date);
                    StringBuilder sb = new StringBuilder();
                    sb.append( filename);
                    int len = filename.length();
                    for (int i=len; i<maxNameLen; i++) sb.append(" ");
                    sb.append( formattedDate );
                    len = formattedDate.length();
                    for (int i=len; i<maxDateLen; i++) sb.append(" ");
                    len = size.length();
                    int numSpaces = maxSizeLen-len;
                    for (int i=0; i< numSpaces; i++) {
                        sb.append(" ");
                    }
                    sb.append(size);
                    files.add( sb.toString());
                    logger.info("DELETE THIS MESSAGE: getSwitchprobeLocalFilesAsString text added: " + sb.toString());
                }
            });
        } catch (IOException e) {
            logger.error("getSwitchprobeLocalFilesAsString IOException2: " + e.getMessage());
        }
        return files;
    }

}
