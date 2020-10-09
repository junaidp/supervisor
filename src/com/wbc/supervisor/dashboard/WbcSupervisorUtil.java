package com.wbc.supervisor.dashboard;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;
import com.wbc.supervisor.shared.dashboard2dto.DeviceInfoDTO;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WbcSupervisorUtil {

    private final static String wbcSupervisorAPIUrl = "http://localhost:8090/api";
    private final static String wbcSupervisor_getDeviceInfo = "getDeviceInfoRecords";
    private final static String wbcSupervisor_getPingSummaryData = "getPingSummaryData";
    private final static String wbcSupervisor_getBandwidthSummaryData = "getBandwidthSummaryData";
    private final static String wbcSupervisor_getStatsBarGraphData = "getStatsBarGraphData";

    public static HashMap<Integer, DeviceInfoDTO> getDeviceInfo(DSLContext dslDashboard, Logger logger) throws IOException {
        HashMap<Integer, DeviceInfoDTO> deviceInfoDTOHashMap = null;
        try {
            InputStream is = new URL(wbcSupervisorAPIUrl + "/" + wbcSupervisor_getDeviceInfo).openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String json = readAll(rd);
            Gson gson = new Gson();

            Type type = new TypeToken<HashMap<Integer, DeviceInfoDTO>>() {
            }.getType();
           deviceInfoDTOHashMap = gson.fromJson(json, type);
        }catch(Exception ex)
        {
            logger.error(ex);
        }
        return deviceInfoDTOHashMap;
    }

    public static MultiSeriesTimebasedChartDTO getPingSummaryData(int nwid, String maxAverage, long oldestSample, long newestSample, int samplesPerPoint  )  {
        MultiSeriesTimebasedChartDTO multiSeriesTimebasedChartDTO = null ;

        StringBuilder param = new StringBuilder();
        param.append( "?nwid=" ).append( nwid );
        param.append( "&maxAverage=" ).append( maxAverage );
        param.append( "&oldestSample=" ).append( oldestSample );
        param.append( "&newestSample=" ).append( newestSample );
        param.append( "&samplesPerPoint=" ).append( samplesPerPoint );


        try {
            InputStream is = new URL(wbcSupervisorAPIUrl + "/" + wbcSupervisor_getPingSummaryData+param.toString()).openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String json = readAll(rd);
            Gson gson = new Gson();
            multiSeriesTimebasedChartDTO = gson.fromJson(json, MultiSeriesTimebasedChartDTO.class);
        }catch(Exception ex){
            System.out.println(ex);
        }

        // This Mehotd called only to remove String which were added from json (Not to be called from all methods)
       convertStringtoNumber(multiSeriesTimebasedChartDTO);

        return multiSeriesTimebasedChartDTO;
    }

    public static MultiSeriesTimebasedChartDTO getBandwidthSummaryData(int dbid, int nwid, long oldestSample, long newestSample, int samplesPerPoint, long timezoneOffsetMillis, Logger logger) {

        MultiSeriesTimebasedChartDTO multiSeriesTimebasedChartDTO = null ;

        StringBuilder param = new StringBuilder();
        param.append( "?dbid=" ).append( dbid );
        param.append( "&nwid=" ).append( nwid );
        param.append( "&oldestSample=" ).append( oldestSample );
        param.append( "&newestSample=" ).append( newestSample );
        param.append( "&samplesPerPoint=" ).append( samplesPerPoint );
        param.append( "&timezoneOffsetMillis=" ).append( timezoneOffsetMillis );

        try {
            InputStream is = new URL(wbcSupervisorAPIUrl + "/" + wbcSupervisor_getBandwidthSummaryData+param.toString()).openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String json = readAll(rd);
            Gson gson = new Gson();

            multiSeriesTimebasedChartDTO = gson.fromJson(json, MultiSeriesTimebasedChartDTO.class);

        }catch(Exception ex){
            System.out.println(ex);
        }
        // This Mehotd called only to remove String which were added from json (Not to be called from all methods)
        convertStringtoNumber(multiSeriesTimebasedChartDTO);

        return multiSeriesTimebasedChartDTO;
    }


    private static void convertStringtoNumber(MultiSeriesTimebasedChartDTO multiSeriesTimebasedChartDTO) {
        Iterator it = multiSeriesTimebasedChartDTO.getData().entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            ArrayList<Number> numNew = new ArrayList<Number>();
            for (Number n : (ArrayList<Number>) pair.getValue()) {
                n = Double.parseDouble(n + "");
                numNew.add(n);
            }

            multiSeriesTimebasedChartDTO.getData().replace((String) pair.getKey(), numNew);
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static MultiSeriesTimebasedChartDTO[] getNetworkStats(int nwid, long oldestSample, long newestSample, int samplesPerPoint, long timezoneOffsetMillis, boolean criticalOnly) {

        MultiSeriesTimebasedChartDTO [] multiSeriesTimebasedChartDTO = null ;

        StringBuilder param = new StringBuilder();
        param.append( "?nwid=" ).append( nwid );
        param.append( "&oldestSample=" ).append( oldestSample );
        param.append( "&newestSample=" ).append( newestSample );
        param.append( "&samplesPerPoint=" ).append( samplesPerPoint );
        param.append( "&timezoneOffsetMillis=" ).append( timezoneOffsetMillis );
        param.append("&criticalOnly=" ).append( criticalOnly );
      //  param.append("&deviceidInfoMap=" ).append( deviceidInfoMap );

        try {
            InputStream is = new URL(wbcSupervisorAPIUrl + "/" + wbcSupervisor_getStatsBarGraphData+param.toString()).openStream();

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String json = readAll(rd);
            Gson gson = new Gson();

            multiSeriesTimebasedChartDTO = gson.fromJson(json, new TypeToken<MultiSeriesTimebasedChartDTO[]>(){}.getType());

        }catch(Exception ex){
            System.out.println(ex);
        }
        // This Mehotd called only to remove String which were added from json (Not to be called from all methods)
        // convertStringtoNumber(multiSeriesTimebasedChartDTO);

        return multiSeriesTimebasedChartDTO;
    }
}

