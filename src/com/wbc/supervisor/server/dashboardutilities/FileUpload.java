package com.wbc.supervisor.server.dashboardutilities;


import com.google.gson.Gson;
import com.wbc.supervisor.server.supervisorServiceImpl;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

import static com.wbc.supervisor.shared.dashboardutilities.Globals.WEBAPP_NAME;


public class FileUpload extends HttpServlet{


    public void doPost(HttpServletRequest request, HttpServletResponse response)  {
        Logger logger = supervisorServiceImpl.getLogger();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        //TODO have this servlet respond with ErrorInfo to be decoded by caller
        String message = "success";
        PrintWriter writer = null;

        try{
            writer = response.getWriter();
            String baseDir = System.getProperty("catalina.home");
            String dbpksFilename = String.format("%s/webapps/" + WEBAPP_NAME + "/WEB-INF/static/dbpks.txt", baseDir);
            String testFilename = String.format("%s/webapps/" + WEBAPP_NAME + "/WEB-INF/static/testpks.txt", baseDir);

            ErrorInfo errorInfo = new ErrorInfo();
            boolean specialDebug = true;
            List<FileItem> formItems = upload.parseRequest(request);
            File testFile = null;
            if (formItems != null && formItems.size() > 0) {
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                         testFile = new File(testFilename);
                        long testSize = testFile.length();
                        if (specialDebug) logger.debug("Test File Size " + testSize );
                         item.write(testFile);
                        if(AuthenticationHandler.isHashcodeValid(testFile)) {
                            File storeFile = new File(dbpksFilename);
                            logger.info("FileUpload:doPost upload  file ["+ storeFile.getName() + "] is authorized, now send to  " + dbpksFilename );
                            long dbpksize = storeFile.length();
                            if (specialDebug) logger.debug("File Size " + dbpksize );
                            if (dbpksize != testSize) {
                                message =  String.format("The new file size %s does not agree with the test size %s", dbpksize, testSize );
                            }
                            if (dbpksize == 0 ) {
                                message =  String.format("The new dbpks file is ZERO size" );
                            }
                            if ( !message.equals("success")) logger.debug(message);
                            item.write(storeFile);
                            errorInfo.setErrorText("Success");
                            errorInfo.setResult("File Uploaded");
                        }
                        else
                        {
                            errorInfo.setErrorText("Error");
                            errorInfo.setResult("File's Hash is not valid, upload cancelled.");

                        }
                    }
                }
            }
            if(testFile!=null)testFile.delete();
            Gson gson = new Gson();
            writer.append(gson.toJson(errorInfo));

        } catch (Exception ex) {
            request.setAttribute("message","There was an error: " + ex.getMessage());
            writer.append("There was an error: " + ex.getMessage());
        }

    }

}
