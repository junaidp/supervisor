package com.dashboardutilities.server;

import com.dashboardutilities.shared.ErrorInfo;
import com.dashboardutilities.shared.IntravueHostStatus;
import com.dashboardutilities.shared.WbcFileInfo;
import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;


public class ServerUtilitiesTest {

    @Test
    @Ignore
    public void testGetSwitchprobeLocalFiles() {
        Logger logger = Logger.getRootLogger();
        String tester = "jim";
        String folder ="";
        if (tester.equals("jim")) {
            folder = "c:/wbcUtilities/switchprobes";
        } else if (tester.equals("junaid")) {
            folder = "c:/wbcUtilities/switchprobes";
        }
        ArrayList<WbcFileInfo> filelist = ServerUtilities.getSwitchprobeLocalFiles( folder, logger );
        filelist.forEach( file -> {

            int maxNameLen = 60;
            int maxDateLen = 20; //show as 2020-08-05 04:36
            int maxSizeLen = 7;   // 1000999
            StringBuilder sb = new StringBuilder();
            sb.append( file.getName());
            int len = file.getName().length();
            //for (int i=len; i<maxNameLen; i++) sb.append(" ");
            for (int i=len; i<maxNameLen; i++) sb.append("&nbsp");
            sb.append(file.getDateString());
            len = file.getDateString().length();
            //for (int i=len; i<maxDateLen; i++) sb.append(" ");
            for (int i=len; i<maxNameLen; i++) sb.append("&nbsp");
            len = file.getSize().length();
            int numSpaces = maxSizeLen-len;
            for (int i=0; i< numSpaces; i++) {
                //sb.append(" ");
                sb.append("&nbsp");
            }
            sb.append(file.getSize());
            System.out.println( sb.toString());
        });
        System.out.println("Success");

    }

    @Test
    @Ignore
    public  void testwalks() {
        final String folder = "c:/wbcUtilities/switchprobes";
        //final String pwd = System.getProperty("user.dir");
        final String pwd = folder;
        System.out.println("Working Directory = " + pwd);
        Path dir = Paths.get(pwd);
        System.out.println("Files.walk");
        try {
            Files.walk(dir, 1).forEach(path -> doSomething("walk", path));
        } catch (IOException e) {
            logException("walk", e);
        }
        System.out.println("Files.walkFileTree");
        try {
            Files.walkFileTree(dir, Collections.emptySet(), 1, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    doSomething("visitFile", file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    logException("visitFile", exc);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logException("walkFileTree", e);
        }
        System.out.println("Files.list");
        try {
            Files.list(dir).forEach(path -> doSomething("dir", path));
        } catch (IOException e) {
            logException("dir", e);
        }
    }

    private static void logException(String title, IOException e) {
        System.err.println(title + "\terror: " + e);
    }

    private static void doSomething(String title, Path file) {
        System.out.println(title + "\t: " + file);
    }

}