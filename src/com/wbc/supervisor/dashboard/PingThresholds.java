package com.wbc.supervisor.dashboard;

/**
 * Created by JIM on 10/3/2014.
 */


import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;
import com.wbc.supervisor.shared.utilities.ThresholdMetrics;
import com.wbc.supervisor.database.generated.tables.Thresholds;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.*;

/**
 * Created by JIM on 9/29/2014.
 */
public class PingThresholds {

    /**
     *  returns the worst case, max, ping response in any period
     * @param dsl
     * @param dbid
     * @param nwid
     * @param firstSampleTime
     * @param lastSampleTime
     * @param minutesPerPoint
     * @return
     *
     * NOTE  in future we will want to bring back 2 values, this will be in bandwidthe so wait tell then
     *      ping
     *      ping failures, as a bar = number of ping failures in that period
     */

    public static MultiSeriesTimebasedChartDTO getMaxNetworkPingResponses( DSLContext dsl,
                                                                           int dbid,
                                                                           int nwid,
                                                                           long firstSampleTime,
                                                                           long lastSampleTime,
                                                                           int minutesPerPoint ,
                                                                           long tzOffsetMillis,
                                                                           Logger logger ) {
        boolean debug = false;
        StringBuilder sb = new StringBuilder();
        Timestamp firstTs = new Timestamp(firstSampleTime );
        Timestamp lastTs = new Timestamp(lastSampleTime );
        sb.append( "getMaxNetworkPingResponses: dbid " );
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
            System.out.println( "getMaxNetworkPingResponses: " + sb.toString());
        }
        Thresholds thresholds = new Thresholds();
        //TODO check a global setting that tells me if there is more than one dmpfile active in thresholds
        // if there is not, then remove the criteria that slows down mysql
        Result<Record3<Timestamp, Double, Double>> results = dsl.select(thresholds.OCCURRED, thresholds.PING, thresholds.PF)
                .from(thresholds)
                .where(thresholds.DBID.equal(Integer.valueOf(dbid)))
                .and(thresholds.NWID.equal(Integer.valueOf(nwid)))
                .and(thresholds.OCCURRED.between(firstTs, lastTs))
                .orderBy( thresholds.OCCURRED)
                .fetch()
                ;
        if (debug) {
            logger.debug("getMaxNetworkPingResponses: thresholds found  " + results.size() );
            System.out.println("getMaxNetworkPingResponses: thresholds found  " + results.size());
        }
//        HashMap<String,ArrayList<Number>> pingData = new HashMap<String, ArrayList<Number>>();
        /*
        System.out.println("DEBUG PingThresholds calling computeDatesForSamples   " + System.currentTimeMillis() );
        ArrayList<String> dates = DashboardUtil.computeDatesForSamples(firstSampleTime, lastSampleTime, minutesPerPoint);
        System.out.println("DEBUG PingThresholds done computeDatesForSamples   " + System.currentTimeMillis() );
        // set the bwData map so it contains a key for every date, and a correct size list of all nulls
        ArrayList<Number> nullList = new ArrayList<Number>( 1 );
        nullList.add( null );
        for ( String datetime : dates ) {
            pingData.put( datetime, nullList );
        }
        */
        // end of setting up bwData
        ArrayList<Number> nullList = new ArrayList<Number>( 1 );
        LinkedHashMap<String,ArrayList<Number>> dtoData = new LinkedHashMap<String, ArrayList<Number>>();
        MultiSeriesTimebasedChartDTO pingMaxDto = new MultiSeriesTimebasedChartDTO();
        long timePeriodFactor = minutesPerPoint * 60000L;
        try {

            for (Record3 record : results) {
                long pointLong = record.getValue(thresholds.OCCURRED).getTime() + tzOffsetMillis ;
                String sTime = Long.toString((pointLong / timePeriodFactor) * timePeriodFactor);
                ArrayList<Number> values = null;
                if (dtoData.containsKey(sTime)) {
                    values = dtoData.get(sTime);
                } else {
                    values = new ArrayList<Number>();
                    dtoData.put(Long.toString(pointLong), values);
                }
                Number pingTime = (Number) ((Double)record.getValue(1) / 1000 );  // pings are in microseconds;
                if (values.size() < 1) {
                    values.add( 0, pingTime );
                } else if (values.get(0).doubleValue() < (Double) pingTime) {
                    values.set(0, pingTime);
                }
                /* this will be used when / if we get ping failures also
                if ( bwValues.get(1) == null) {
                    bwValues.set(1, (Number)record.getValue(2)) ;
                } else if ( bwValues.get(1).doubleValue() < (Double)record.getValue(2)) {
                    bwValues.set(1, (Number)record.getValue(2)) ;
                }
                */
//                dtoData.put(Long.toString(pointLong), values);
            }
            System.out.println("getMaxNetworkPingResponses: thresholds entries in dto  " + dtoData.size());
            pingMaxDto.setData(dtoData);
        } catch ( Exception e) {
            logger.error( "getMaxNetworkPingResponses: exception " + e.getMessage(), e );
        }
        return pingMaxDto;
    }


    /**
     *
     * @param dsl
     * @param ids
     * @param firstSampleTime
     * @param lastSampleTime
     * @param minutesPerPoint
     * @param tzOffsetMillis
     * @param logger
     * @return
     */
    public static HashMap<Integer, ArrayList<Number>> getNetworkPingResponses( DSLContext dsl,
                                                                                 ArrayList<Integer> ids,
                                                                                 long firstSampleTime,
                                                                                 long lastSampleTime,
                                                                                 int minutesPerPoint ,
                                                                                 int maxDevicesToReturn,
                                                                                 long tzOffsetMillis,
                                                                                 Logger logger ) {

        // does the caller need to specify the max number to return
        // e.g, top 20 worst ping reponses vs all
        boolean debug = false;
        boolean debugTimestamps = false;
        HashMap<Integer, ArrayList<Number>> pingResults = new HashMap<Integer, ArrayList<Number>>();
        StringBuilder sb = new StringBuilder();
        Timestamp firstTs = new Timestamp(firstSampleTime );
        Timestamp lastTs = new Timestamp(lastSampleTime );
        sb.append( "getNetworkPingResponses: size of list " );
        sb.append(ids.size());
        sb.append(" from ");
        sb.append(firstTs);
        sb.append(" to ");
        sb.append(lastTs);
        sb.append(" SPP ");
        sb.append(minutesPerPoint);
        if (debug) {
            logger.debug(sb.toString());
            System.out.println( "getNetworkPingResponses: " + sb.toString());
        }
        Thresholds thresholds = new Thresholds();

        int numExpectedValues = (int)(( lastSampleTime - firstSampleTime) / 60000L );
        long firstSampleno = firstSampleTime / 60000 ;


        Double zero = 0.0;
        long timePeriodFactor = minutesPerPoint * 60000L;
        ArrayList<Number> pingTimestamps = null;
        pingTimestamps = new ArrayList<Number>();
        for ( int i = 0; i <= numExpectedValues; i++ ) {
            pingTimestamps.add( (double)((firstSampleno + i) * 60000L ) );
        }
        HashMap<Integer, ArrayList<Number>> selectedPings = new HashMap<Integer, ArrayList<Number>>();
        try {
            for (Integer deviceid : ids) {
                //TODO Make this a prepared statement and only provide the descid
                Result<Record2<Double, Timestamp>> results = dsl.select(thresholds.PING, thresholds.OCCURRED)
                        .from(thresholds)
                        .where(thresholds.DEVICEID.equal(deviceid))
                        .and(thresholds.OCCURRED.between(firstTs, lastTs))
                        .orderBy(thresholds.OCCURRED)
                        .fetch();
                /*
                        TODO:  It would be good to make this work
                List<Double> list = results.getValues(thresholds.PING);
                //ArrayList<Double> pings = new ArrayList<Double>();
                //pings = results.getValues(thresholds.PING);
                pingResults.put( deviceid, (ArrayList)results );

                */

                /*



                        THERE IS A WEAKNESS, BUG TO  HAPPEN HERE
                        THE FIRST DEVICE IS USED TO CREATE THE TIME VALUE DATA (COLUMN)
                        IF THE DEVICE JOINED AFTER THE START, ALL THE DEVICES WOULD BE MISSING THE EARLIER TIMES

                        FOR useFixedSizeArray TO WORK WE SHOULD PROBABLY FILL ALL THE TIMES BASED ON START/END TIMES
                        THEN FOR EACH NEW DEVICE START WITH A ZERO FILLED DATA ARRAY AND PUT IN THE TIMES THAT ARE NOT ZERO



                 */
                ArrayList<Number> pings = new ArrayList<Number>(Collections.nCopies( pingTimestamps.size(), zero));
                for (Record2<Double, Timestamp> result : results) {
                    // compute index into array
                    // it is based on time
                    long thisSampleno = result.value2().getTime() / 60000L;

                    int index = (int) (thisSampleno - firstSampleno);
                    pings.set(index, result.value1() / 1000);  // pings are in microseconds
                    /*
                    if (collectPingTimes) {
                        //  Original, uses timezone good, but not as index equal as below >>  long pointLong = result.getValue(thresholds.OCCURRED).getTime() + tzOffsetMillis ;
                        pingTimestamps.set(index, (double) result.value2().getTime());
                        if (debugTimestamps) {
                            logger.debug("getNetworkPingResponses: index " + index + " , time in db " + result.value2().toString() + ", sampleno for time " + thisSampleno + " value set " + (thisSampleno * 60000L));
                        }
                    } else  {
                        if ( !pingTimestamps.contains( Double.valueOf(result.value2().getTime()))) {
                            pingTimestamps.set(index, (double) result.value2().getTime());
                            if (debugTimestamps) logger.warn("getNetworkPingResponses adding a time for a ping not in the pingTimestamps double " + (double) result.value2().getTime()    +  "  " + result.value2().toString() + " deviceid " + deviceid  + " index " + index );
                        }
                    }
                    */
                }
                pingResults.put(deviceid, pings);
            }

            if (debug) logger.debug("getNetworkPingResponses: size of pingResults " + pingResults.size());
            // find the metrics for each ping and put the 'selection type' into a map
            HashMap<Double, Integer> mapOfAverages = new HashMap<Double, Integer>();
            for (Integer deviceid : pingResults.keySet()) {
                ArrayList<Number> pings = pingResults.get(deviceid);
                ThresholdMetrics metrics = ThresholdAnalyzer.analyzeThresholds(pings);
                Double avg = metrics.getsAvg();
                while (mapOfAverages.containsKey(avg)) {
                    // we must make the avg unique to be able to get only the highest X number, can't have ties.
                    avg += .00001;
                }
                mapOfAverages.put(avg, deviceid);
                if (debug) logger.debug("getNetworkPingResponses: adding deviceid " + deviceid + " avg " + avg);
            }
            if (debug) logger.debug("getNetworkPingResponses: size of mapOfAverages " + mapOfAverages.size());
            // now sort the avgs
            List<Double> maxList = new ArrayList<Double>(mapOfAverages.keySet());
            Collections.sort(maxList, Collections.reverseOrder());

            int count = 0;
            for (Double key : maxList) {
                Integer deviceid = mapOfAverages.get(key);
                ArrayList<Number> pings = pingResults.get(deviceid);
                if (count < maxDevicesToReturn) {
                    selectedPings.put(deviceid, pings);
                    if (debugTimestamps)
                        logger.debug("getNetworkPingResponses: num values for deviceid " + deviceid + " = " + pings.size());
                    count++;
                } else {
                    break;
                }
            }
            if (debug) logger.debug("getNetworkPingResponses: size of selectedPings " + selectedPings.size() + "  max " + maxDevicesToReturn);

            // now add the timestamps
            selectedPings.put(0, pingTimestamps);
            if (debugTimestamps) logger.debug("getNetworkPingResponses: num pingTimestamps = " + pingTimestamps.size());
        } catch ( Exception ex) {
            logger.fatal("getNetworkPingResponses: Exception " + ex.getMessage(), ex );
        }
        return selectedPings;
    }

    public static MultiSeriesTimebasedChartDTO getPingsForDevice( DSLContext dsl,
                                                                           int deviceid,
                                                                           long firstSampleTime,
                                                                           long lastSampleTime,
                                                                           int minutesPerPoint ,
                                                                           long tzOffsetMillis,
                                                                           Logger logger ) {
        boolean debug = false;
        StringBuilder sb = new StringBuilder();
        Timestamp firstTs = new Timestamp(firstSampleTime );
        Timestamp lastTs = new Timestamp(lastSampleTime );
        sb.append( "getMaxNetworkPingResponses: deviceid " );
        sb.append(deviceid);
        sb.append(" from ");
        sb.append(firstTs);
        sb.append(" to ");
        sb.append(lastTs);
        sb.append(" SPP ");
        sb.append(minutesPerPoint);
        if (debug) {
            logger.debug(sb.toString());
            System.out.println( "getMaxNetworkPingResponses: " + sb.toString());
        }
        Thresholds thresholds = new Thresholds();
        //TODO check a global setting that tells me if there is more than one dmpfile active in thresholds
        // if there is not, then remove the criteria that slows down mysql
        Result<Record3<Timestamp, Double, Double>> results = dsl.select(thresholds.OCCURRED, thresholds.PING, thresholds.PF)
                .from(thresholds)
                .where(thresholds.DEVICEID.equal(Integer.valueOf(deviceid)))
                .and(thresholds.OCCURRED.between(firstTs, lastTs))
                .orderBy( thresholds.OCCURRED)
                .fetch()
                ;
        if (debug) {
            logger.debug("getMaxNetworkPingResponses: thresholds found  " + results.size() );
            System.out.println("getMaxNetworkPingResponses: thresholds found  " + results.size());
        }
        // end of setting up bwData
        ArrayList<Number> nullList = new ArrayList<Number>( 1 );
        LinkedHashMap<String,ArrayList<Number>> dtoData = new LinkedHashMap<String, ArrayList<Number>>();
        MultiSeriesTimebasedChartDTO pingMaxDto = new MultiSeriesTimebasedChartDTO();
        long timePeriodFactor = minutesPerPoint * 60000L;
        try {

            for (Record3 record : results) {
                long pointLong = record.getValue(thresholds.OCCURRED).getTime() + tzOffsetMillis ;
                String sTime = Long.toString((pointLong / timePeriodFactor) * timePeriodFactor);
                ArrayList<Number> values = null;
                values = new ArrayList<Number>();
                dtoData.put(Long.toString(pointLong), values);
                Number pingTime = (Number) ((Double)record.getValue(1) / 1000 );  // pings are in microseconds;
                values.add( 0, pingTime );
                /* this will be used when / if we get ping failures also
                if ( bwValues.get(1) == null) {
                    bwValues.set(1, (Number)record.getValue(2)) ;
                } else if ( bwValues.get(1).doubleValue() < (Double)record.getValue(2)) {
                    bwValues.set(1, (Number)record.getValue(2)) ;
                }
                */
            }
            System.out.println("getMaxNetworkPingResponses: thresholds entries in dto  " + dtoData.size());
            pingMaxDto.setData(dtoData);
        } catch ( Exception e) {
            logger.error( "getMaxNetworkPingResponses: exception " + e.getMessage(), e );
        }
        return pingMaxDto;
    }

}
