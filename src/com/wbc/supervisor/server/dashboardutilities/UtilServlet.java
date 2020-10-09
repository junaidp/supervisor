package com.wbc.supervisor.server.dashboardutilities;

import com.google.gson.Gson;
import com.wbc.supervisor.server.supervisorServiceImpl;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import org.apache.log4j.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

public class UtilServlet extends HttpServlet {

    boolean debug = true;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter writer = response.getWriter();
        writer.close();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Logger logger = supervisorServiceImpl.getLogger();
        String path = request.getServletPath();
        logger.debug( String.format("UtilServlet.doGet Request %s ", path ));
        ErrorInfo returnInfo = new ErrorInfo();
        boolean returnErrorInfo = false;
        String jsonData="";

        try {

            if (path.equals("/logread")) {
                String filename = request.getParameter("file");
                if (filename!=null) logger.debug("Logread: filename=" + filename );
                if (filename==null) {
                    String fullFilename = supervisorServiceImpl.getAppDir() + "/logs/wbcutil.log" ;
                    logger.debug("Logread: no filename, using " + fullFilename );
                    Logread.readServerLog( response, fullFilename, logger );
                } else if (filename.equals( "catalina")) {
                    SimpleDateFormat fmt = new SimpleDateFormat( "yyyy-MM-dd" );
                    Date date = Date.from( Instant.now());
                    String formattedDate = fmt.format( date );
                    String fullFilename = supervisorServiceImpl.getAppDir() + "/tomcat8/logs/catalina." + formattedDate + ".log";
                    Logread.readServerLog(response, fullFilename, logger);
                } else if (filename.equals( "catalinaall")) {
                    String fullFilename = supervisorServiceImpl.getAppDir() + "/tomcat8/logs/catalina.out";
                   Logread.readServerLog(response, fullFilename, logger);
                } else if (filename.equals("localhost")) {
                    SimpleDateFormat fmt = new SimpleDateFormat( "yyyy-MM-dd" );
                    Date date = Date.from( Instant.now());
                    String formattedDate = fmt.format( date );
                    String fullFilename = supervisorServiceImpl.getAppDir() + "/tomcat8/logs/localhost." + formattedDate + ".log";
                    Logread.readServerLog(response, fullFilename, logger);
                } else if (filename.equals("dbpks")) {
                    String fullFilename = supervisorServiceImpl.getAppDir() + "/tomcat8/webapps/wbcutil/WEB-INF/static/dbpks.txt";
                    Logread.readServerLog(response, fullFilename, logger);
                } else {
                    logger.error("Logread: unknown filename parameter " + filename );
                }
                return;  // this method handles the return
            } else {
                logger.debug("UtilServlet at Not Found");
                returnInfo.setResult("error");
                returnInfo.setErrorText("unknown request " + path);
            }
            PrintWriter writer = response.getWriter();
            if (returnErrorInfo) {
                writer.print(jsonData);
            } else {
                Gson gson = new Gson();
                String errorData = gson.toJson( returnInfo );
                writer.println(errorData);
            }
            writer.close();
        } catch (Exception ex ) {
            returnInfo.makeError();
            returnInfo.setErrorText("UtilServlet Exception: " + ex.getClass() + "   " + ex.getMessage());
            logger.error( returnInfo.getErrorText() );
            ex.printStackTrace();
        }
        if (debug ) {
            if (returnErrorInfo) {
                logger.debug("UtilServlet doGet end, json data sent  >  " + jsonData);
            } else {
                logger.debug("UtilServlet doGet end " + returnInfo.toString()  );
            }
        }
    }


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

}
