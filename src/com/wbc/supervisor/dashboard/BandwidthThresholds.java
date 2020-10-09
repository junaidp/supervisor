package com.wbc.supervisor.dashboard;

import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;
import com.wbc.supervisor.database.generated.tables.Thresholds;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by JIM on 10/6/2014.
 */
public class BandwidthThresholds {

    public static MultiSeriesTimebasedChartDTO getMaxNetworkBandwidthResponses( DSLContext dsl,
                                                                                int dbid,
                                                                                int nwid,
                                                                                long firstSampleTime,
                                                                                long lastSampleTime,
                                                                                int minutesPerPoint ,
                                                                                long tzOffsetMillis,
                                                                                Logger logger ) {
        /*
        ArrayList<String> series = new ArrayList<String>();
        series.add("Receive");
        series.add("Transmit");
        MultiSeriesTimebasedChartDTO dto = new MultiSeriesTimebasedChartDTO( series ) ;
        ArrayList<ArrayList<Integer>> colors = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> color = new ArrayList<Integer>();
        color.add(255);
        color.add(0);
        color.add(0);
        colors.add( color );
        color.set(0,0);
        color.set(2,255);
        colors.add(color);
        dto.setSeriesColors(colors);
        */
        //
        boolean debug = false;
        StringBuilder sb = new StringBuilder();
        Timestamp firstTs = new Timestamp(firstSampleTime);
        Timestamp lastTs = new Timestamp(lastSampleTime);
        sb.append("getMaxNetworkBandwidthResponses: dbid ");
        sb.append(dbid);
        sb.append(" nwid ");
        sb.append(nwid);
        sb.append(" from ");
        sb.append(firstTs);
        sb.append(" to ");
        sb.append(lastTs);
        sb.append(" SPP ");
        sb.append(minutesPerPoint);
        if (debug) {
            logger.debug(sb.toString());
            logger.info("getMaxNetworkBandwidthResponses: " + sb.toString());
        }
        Thresholds thresholds = new Thresholds();
        //TODO check a global setting that tells me if there is more than one dmpfile active in thresholds
        // if there is not, then remove the criteria that slows down mysql
        Result<Record3<Timestamp, Double, Double>> results = dsl.select(thresholds.OCCURRED, thresholds.RECV, thresholds.XMIT)
                .from(thresholds)
                .where(thresholds.DBID.equal(Integer.valueOf(dbid)))
                .and(thresholds.NWID.equal(Integer.valueOf(nwid)))
                .and(thresholds.OCCURRED.between(firstTs, lastTs))
                .orderBy( thresholds.OCCURRED)
                .fetch()
                ;
        if (debug) logger.debug("getMaxNetworkBandwidthResponses: thresholds found  " + results.size() );
        if (debug)  logger.debug("getMaxNetworkBandwidthResponses: thresholds found  " + results.size());
        // HashMap<String,ArrayList<Number>> bwData = new HashMap<String, ArrayList<Number>>();
        /*
        // compute the values of the times to be used
        System.out.println("DEBUG BandwidthThresholds calling computeDatesForSamples   " + System.currentTimeMillis() );
        ArrayList<String> dates = DashboardUtil.computeDatesForSamples( firstSampleTime,lastSampleTime, minutesPerPoint );
        System.out.println("DEBUG BandwidthThresholds done computeDatesForSamples   " + System.currentTimeMillis() );
        // set the bwData map so it contains a key for every date, and a correct size list of all nulls
        int numSeries = 2;
        ArrayList<Number> nullList = new ArrayList<Number>( numSeries );
        for ( int i=0; i<numSeries; i++) {
            nullList.add( null );
        }
        for ( String datetime : dates ) {
            bwData.put( datetime, nullList );
        }
        // end of setting up bwData
        */
        // There is now a list for all points in the graph initialized to null numbers for each series point
        LinkedHashMap<String,ArrayList<Number>> dtoData = new LinkedHashMap<String, ArrayList<Number>>();
        MultiSeriesTimebasedChartDTO bwMaxDto = new MultiSeriesTimebasedChartDTO();
        long timePeriodFactor = minutesPerPoint * 60000L;
        try {
            for( Record3 record : results ) {
                long pointLong = record.getValue(thresholds.OCCURRED).getTime() + tzOffsetMillis ;
                ArrayList<Number> bwValues = null;
                String sTime = Long.toString(  (pointLong/timePeriodFactor) * timePeriodFactor );
                if ( dtoData.containsKey( sTime)) {
                    bwValues = dtoData.get(sTime);
                } else {
                    bwValues = new ArrayList<Number>();
                    dtoData.put(Long.toString(pointLong), bwValues);
                }
                Number recv = (Number) record.getValue(1);
                if ( bwValues.size() < 1) {
                    bwValues.add( 0, recv );
                } else if ( bwValues.get(0).doubleValue() < (Double)recv) {
                    bwValues.set(0, recv) ;
                }
                Number xmit = (Number) record.getValue(2);
                if ( bwValues.size() < 2) {
                    bwValues.add( 1, xmit );
                } else if ( bwValues.get(1).doubleValue() < (Double)xmit) {
                    bwValues.set(1, xmit) ;
                }
                // dtoData.put( Long.toString(pointLong), bwValues );
            }
            if (debug) logger.debug("getMaxNetworkBandwidhtResponses: thresholds entries in dto  " + dtoData.size() );
            bwMaxDto.setData( dtoData );
        } catch ( Exception e) {
            logger.error("PingThresholds: exception " + e.getMessage(), e);
        }
        return bwMaxDto;
    }

    public static MultiSeriesTimebasedChartDTO getBandwidthForDevice( DSLContext dsl,
                                                                                int deviceid,
                                                                                long firstSampleTime,
                                                                                long lastSampleTime,
                                                                                int minutesPerPoint ,
                                                                                long tzOffsetMillis,
                                                                                Logger logger ) {
        //
        boolean debug = false;
        StringBuilder sb = new StringBuilder();
        Timestamp firstTs = new Timestamp(firstSampleTime);
        Timestamp lastTs = new Timestamp(lastSampleTime);
        sb.append("getMaxNetworkBandwidthResponses: deviceid ");
        sb.append(deviceid);
        sb.append(" from ");
        sb.append(firstTs);
        sb.append(" to ");
        sb.append(lastTs);
        sb.append(" SPP ");
        sb.append(minutesPerPoint);
        if (debug) {
            logger.debug(sb.toString());
            logger.info("getMaxNetworkBandwidthResponses: " + sb.toString());
        }
        Thresholds thresholds = new Thresholds();
        //TODO check a global setting that tells me if there is more than one dmpfile active in thresholds
        // if there is not, then remove the criteria that slows down mysql
        Result<Record3<Timestamp, Double, Double>> results = dsl.select(thresholds.OCCURRED, thresholds.RECV, thresholds.XMIT)
                .from(thresholds)
                .where(thresholds.DEVICEID.equal(Integer.valueOf(deviceid)))
                .and(thresholds.OCCURRED.between(firstTs, lastTs))
                .orderBy( thresholds.OCCURRED)
                .fetch()
                ;
        if (debug) logger.debug("getMaxNetworkBandwidthResponses: thresholds found  " + results.size() );
        // There is now a list for all points in the graph initialized to null numbers for each series point
        LinkedHashMap<String,ArrayList<Number>> dtoData = new LinkedHashMap<String, ArrayList<Number>>();
        MultiSeriesTimebasedChartDTO bwMaxDto = new MultiSeriesTimebasedChartDTO();
        long timePeriodFactor = minutesPerPoint * 60000L;
        try {
            for( Record3 record : results ) {
                long pointLong = record.getValue(thresholds.OCCURRED).getTime() + tzOffsetMillis ;
                ArrayList<Number> bwValues = null;
                String sTime = Long.toString(  (pointLong/timePeriodFactor) * timePeriodFactor );
                bwValues = new ArrayList<Number>();
                dtoData.put(Long.toString(pointLong), bwValues);
                Number recv = (Number) record.getValue(1);
                bwValues.add( 0, recv );
                Number xmit = (Number) record.getValue(2);
                bwValues.add( 1, xmit );

                /*
                if ( dtoData.containsKey( sTime)) {
                    bwValues = dtoData.get(sTime);
                } else {
                    bwValues = new ArrayList<Number>();
                    dtoData.put(Long.toString(pointLong), bwValues);
                }
                Number recv = (Number) record.getValue(1);
                if ( bwValues.size() < 1) {
                    bwValues.add( 0, recv );
                } else if ( bwValues.get(0).doubleValue() < (Double)recv) {
                    bwValues.set(0, recv) ;
                }
                Number xmit = (Number) record.getValue(2);
                if ( bwValues.size() < 2) {
                    bwValues.add( 1, xmit );
                } else if ( bwValues.get(1).doubleValue() < (Double)xmit) {
                    bwValues.set(1, xmit) ;
                }
                */

                // dtoData.put( Long.toString(pointLong), bwValues );
            }
            if (debug) logger.debug("getMaxNetworkBandwidhtResponses: thresholds entries in dto  " + dtoData.size() );
            bwMaxDto.setData( dtoData );
        } catch ( Exception e) {
            logger.error("PingThresholds: exception " + e.getMessage(), e);
        }
        return bwMaxDto;
    }

}
