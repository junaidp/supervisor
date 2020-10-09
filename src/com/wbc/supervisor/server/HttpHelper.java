package com.wbc.supervisor.server;

import org.apache.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


/**
 * Created by JIM on 9/14/2014.
 */
public class HttpHelper {





    public static String sendGet(String url, String userAgent, Logger logger, boolean debug  ) {
        String result ="";
        HttpURLConnection con = null;
        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            // optional default is GET
            con.setRequestMethod("GET");
            //add request header
            if ( userAgent != null) con.setRequestProperty("User-Agent", userAgent);
            int responseCode = con.getResponseCode();
            if (debug) logger.debug("\nSending 'GET' request to URL : " + url);
            if (debug) logger.debug("Response Code : " + responseCode);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            if (debug) logger.debug(response.toString());
            result = response.toString() ;
        } catch (MalformedURLException e) {
            result = "sendPost MalformedURLException: "+  e.getMessage() ;
            e.printStackTrace();
        } catch (IOException e) {
            result = "sendPost IOException: "+  e.getMessage() ;
            e.printStackTrace();
        }
        return result;
    }


    public static String sendPost( String url, String userAgent, String params, Logger logger ) {
        boolean debug = false;
        String result = "";
        HttpsURLConnection con = null;
        try {
            URL obj = new URL(url);
            con = (HttpsURLConnection) obj.openConnection();
            //add reuqest header
            con.setRequestMethod("POST");
            if (userAgent != null) con.setRequestProperty("User-Agent", userAgent);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";
            urlParameters = params;
            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();
            int responseCode = con.getResponseCode();
            if (debug) logger.debug("\nSending 'POST' request to URL : " + url);
            if (debug) logger.debug("Post parameters : " + urlParameters);
            if (debug) logger.debug("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream())
            );
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            if (debug) logger.debug(response.toString());
            result = response.toString();
        } catch (IOException e) {
            result = "sendPost IOException: "+  e.getMessage() ;
            e.printStackTrace();
        }
        return result;
    }
    // HTTP POST request
    private void sendPost() throws Exception {

        final String USER_AGENT = "Mozilla/5.0";
        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }

    // HTTP GET request
    private void sendGet() throws Exception {
        final String USER_AGENT = "Mozilla/5.0";
        String url = "http://www.google.com/search?q=mkyong";
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);
        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        //print result
        System.out.println(response.toString());
    }
}
