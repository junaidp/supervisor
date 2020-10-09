package com.wbc.supervisor.dashboard;

import com.wbc.supervisor.shared.StatsConstants;
import com.wbc.supervisor.shared.dto.DeviceStatDetails;
import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;
import com.wbc.supervisor.shared.dto.StatDetailsByDeviceDTO;
import com.wbc.supervisor.shared.dashboard2dto.DeviceInfoDTO;
import com.wbc.supervisor.shared.dashboard2dto.KpiStatsRow;
import com.wbc.supervisor.database.generated.tables.Stats;
import com.wbc.supervisor.database.generated.tables.records.StatsRecord;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record3;
import org.jooq.Result;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by JIM on 10/21/2014.
 */
public class StatsUtil {


    public static MultiSeriesTimebasedChartDTO[] getNetworkStats(DSLContext dsl,
                                                                 int dbid,
                                                                 int nwid,
                                                                 long firstSampleTime,
                                                                 long lastSampleTime,
                                                                 int minutesPerPoint,
                                                                 long tzOffsetMillis,
                                                                 boolean isCritical,
                                                                 HashMap<Integer, DeviceInfoDTO> deviceidInfoMap,
                                                                 Logger logger)
    {
        MultiSeriesTimebasedChartDTO typeByDateDTO =  new MultiSeriesTimebasedChartDTO();
        MultiSeriesTimebasedChartDTO deviceTotalsByTypeDTP =  new MultiSeriesTimebasedChartDTO();

        boolean debug = false;
        boolean verbose = false;
        boolean debugIpDevices = false;
        StringBuilder sb = new StringBuilder();
        Timestamp firstTs = new Timestamp(firstSampleTime);
        Timestamp lastTs = new Timestamp(lastSampleTime);
        sb.append("getNetworkStats: dbid ");
        sb.append(dbid);
        sb.append(" nwid ");
        sb.append(nwid);
        sb.append(" from ");
        sb.append(firstTs);
        sb.append(" to ");
        sb.append(lastTs);
        sb.append(" SPP ");
        sb.append(minutesPerPoint);
        if (debug | debugIpDevices) {
            logger.debug("************************************ this must agree with query for stats bar chart *******************");
            logger.debug( "getNetworkStats > " + sb.toString());
            logger.debug("*******************************************************************************************************");
            System.out.println("getNetworkStats: " + sb.toString());
        }
        Stats stats = Stats.STATS;

        //TODO if nwid is -1, then collect for ALL networks..... summary data
        Result<Record3<Integer, Timestamp, Integer>> results = dsl.select(stats.DEVICEID, stats.OCCURRED, stats.TYPE )
                .from(stats)
                .where(stats.DBID.equal(Integer.valueOf(dbid)))
                .and(stats.NWID.equal(Integer.valueOf(nwid)))
                .and(stats.OCCURRED.between(firstTs, lastTs))
                .orderBy( stats.OCCURRED)
                .fetch()
                ;
        if (debug) logger.debug("getNetworkStats: stats found  " + results.size() );
        if (debug)  System.out.println("getNetworkStats: stats found  " + results.size() );
        LinkedHashMap<String,ArrayList<Number>> dtoIpData = new LinkedHashMap<String, ArrayList<Number>>();
        LinkedHashMap<String,ArrayList<Number>> dtoTimeData = new LinkedHashMap<String, ArrayList<Number>>();
        long timePeriodFactor = minutesPerPoint * 60000L;
        // init the hashmap so there is a value for each point
        long firstPoint = (( firstSampleTime + tzOffsetMillis)/ timePeriodFactor ) * timePeriodFactor   ;
        long finalPoint = (( lastSampleTime  + tzOffsetMillis)/ timePeriodFactor ) * timePeriodFactor   ;
        if (debug) logger.debug("getNetworkStats: Recomputed time,  firstPoint " + new Timestamp(firstPoint).toString() + " , finalPoint " + new Timestamp(finalPoint).toString() );
        ArrayList<Number> initStatValues = new ArrayList<Number>();
        initStatValues.addAll(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));  // initilize all possible series
        dtoTimeData.put(Long.toString(firstPoint), initStatValues);
        long lastPoint = firstPoint;
        // create an empty dataset so that all time stats are initialized to 0 in the array, IPs will be on the fly
        while ( true ) {
            long nextPoint = (((lastPoint + timePeriodFactor)) / timePeriodFactor) * timePeriodFactor   ;
            if ( nextPoint > finalPoint) break;
            // System.out.println( period++ + " nextpoint " + nextPoint );
            initStatValues = new ArrayList<Number>();
            initStatValues.addAll(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));  // initilize all possible series
            dtoTimeData.put(Long.toString(nextPoint), initStatValues);
            lastPoint = nextPoint;
        }
        // collect data
        try {
            for( Record3 record : results ) {
                long pointLong = record.getValue(stats.OCCURRED).getTime() + tzOffsetMillis ;
                String sDeviceid = record.getValue(stats.DEVICEID).toString();
                if (isCritical) {
                    if (deviceidInfoMap.containsKey( Integer.parseInt(sDeviceid))) {
                        DeviceInfoDTO di = deviceidInfoMap.get( Integer.parseInt(sDeviceid) );
                        if (di.getCriticalType() < 2) continue;
                    } else {
                        logger.warn("getNetworkStats: nothing in deviceInfoMap for " + sDeviceid );
                    }
                }
                ArrayList<Number> statValues = null;
                ArrayList<Number> ipValues = null;
                String sTime = Long.toString(  (pointLong/timePeriodFactor) * timePeriodFactor );
                // System.out.println("sTime " + sTime );
                if ( dtoTimeData.containsKey( sTime)) {
                    statValues = dtoTimeData.get(sTime);
                } else {
                    // THIS SHOULD NOT HAPPEN !!!!
                    // WE INITIALIZED WHAT SHOULD BE ALL THE POSSIBLE POINTS
                    // statValues is NULL, do not continue;
                    logger.error("getNetworkStats: missing/bad time period, not in map " + sTime );
                    //System.out.println("getNetworkStats: missing/bad time period, not in map " + sTime );
                    continue;
                    /*
                    statValues = new ArrayList<Number>();
                    statValues.addAll(Arrays.asList(0,0,0,0,0,0,0,0,0) );  // initilize all possible series
                    dtoTimeData.put(Long.toString(pointLong), statValues);
                    */
                }
                if ( dtoIpData.containsKey(sDeviceid) ) {
                    ipValues = dtoIpData.get(sDeviceid);
                } else {
                    ArrayList<Number> initIpValues = new ArrayList<Number>();
                    initIpValues.addAll(Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0));  // initilize all possible series
                    dtoIpData.put(sDeviceid, initIpValues);
                    ipValues = dtoIpData.get(sDeviceid);
                    if (debugIpDevices) logger.debug("getNetworkStats: add new IP = " +  sDeviceid );
                }
                if (verbose && false) {
                    System.out.print( "BEFORE " + statValues + "  " );
                    for (Number number : statValues) {
                        System.out.print( number + "   " );
                    }
                    System.out.println();
                }
                int iType = record.getValue(stats.TYPE);
                // NOTE ping failures are not collected by events,
                if (iType == StatsConstants.CLASS_PING) {
                    statValues.set( StatsConstants.SERIES_INDEX_PT, (Integer)statValues.get(StatsConstants.SERIES_INDEX_PT) + 1 );
                    ipValues.set(   StatsConstants.SERIES_INDEX_PT, (Integer)ipValues.get(StatsConstants.SERIES_INDEX_PT) + 1 );
                } else if (iType == StatsConstants.CLASS_BW) {
                    statValues.set( StatsConstants.SERIES_INDEX_BW, (Integer)statValues.get(StatsConstants.SERIES_INDEX_BW) + 1 );
                    ipValues.set( StatsConstants.SERIES_INDEX_BW, (Integer)ipValues.get(StatsConstants.SERIES_INDEX_BW) + 1 );
                } else if (iType == StatsConstants.CLASS_DEVICE_CHANGE_IP) {
                    statValues.set( StatsConstants.SERIES_INDEX_IP, (Integer)statValues.get(StatsConstants.SERIES_INDEX_IP) + 1 );
                    ipValues.set( StatsConstants.SERIES_INDEX_IP, (Integer)ipValues.get(StatsConstants.SERIES_INDEX_IP) + 1 );
                } else if (iType == StatsConstants.CLASS_DEVICE_CHANGE_MAC) {
                    statValues.set( StatsConstants.SERIES_INDEX_MAC, (Integer)statValues.get(StatsConstants.SERIES_INDEX_MAC) + 1 );
                    ipValues.set( StatsConstants.SERIES_INDEX_MAC, (Integer)ipValues.get(StatsConstants.SERIES_INDEX_MAC) + 1 );
                } else if (iType == StatsConstants.CLASS_MOVE) {
                    statValues.set( StatsConstants.SERIES_INDEX_MOVE, (Integer)statValues.get(StatsConstants.SERIES_INDEX_MOVE) + 1 );
                    ipValues.set( StatsConstants.SERIES_INDEX_MOVE, (Integer)ipValues.get(StatsConstants.SERIES_INDEX_MOVE) + 1 );
                } else if (iType == StatsConstants.CLASS_LINK_CHANGE || iType == StatsConstants.CLASS_LINK_CHANGE_OLD) {
                    statValues.set( StatsConstants.SERIES_INDEX_LINK, (Integer)statValues.get(StatsConstants.SERIES_INDEX_LINK) + 1 );
                    ipValues.set( StatsConstants.SERIES_INDEX_LINK, (Integer)ipValues.get(StatsConstants.SERIES_INDEX_LINK) + 1 );
                } else if (iType == StatsConstants.CLASS_CONNECT) {
                    statValues.set( StatsConstants.SERIES_INDEX_DISC, (Integer)statValues.get(StatsConstants.SERIES_INDEX_DISC) + 1 );
                    ipValues.set( StatsConstants.SERIES_INDEX_DISC, (Integer)ipValues.get(StatsConstants.SERIES_INDEX_DISC) + 1 );
                } else if (iType == StatsConstants.CLASS_ENETIP_CHANGE) {
                    statValues.set( StatsConstants.SERIES_INDEX_ENET, (Integer)statValues.get(StatsConstants.SERIES_INDEX_ENET) + 1 );
                    ipValues.set( StatsConstants.SERIES_INDEX_ENET, (Integer)ipValues.get(StatsConstants.SERIES_INDEX_ENET) + 1 );
                } else {
                    logger.error("UNHANDLED type " + iType);
                }
                if (verbose) {
                    System.out.print( "AFTER sip " + sDeviceid + "  " + sTime + "  " + statValues + "  " );
                    for (Number number : statValues) {
                        System.out.print( number + "   " );
                    }
                    System.out.println();
                }
            }
            if (debug) logger.debug("getNetworkStats:  entries in dto  " + dtoTimeData.size());
            typeByDateDTO.setData(dtoTimeData);
            deviceTotalsByTypeDTP.setData(dtoIpData);
            if (false) {
                for (String dateString : dtoTimeData.keySet()) {
                    sb.delete(0, sb.length());
                    ArrayList<Number> list = dtoTimeData.get(dateString);
                    long tsl =  Long.valueOf(dateString);
                    Timestamp ts =  new Timestamp( tsl );
                    sb.append( dateString );
                    for (Number number : list) {
                        sb.append(  "  " );
                        sb.append( number );
                    }
                    System.out.println(sb.toString());
                    //TODO send output to logger
                }
            }
        } catch ( Exception e) {
            logger.error("getNetworkStats: exception " + e.getMessage(), e);
            System.out.println("getNetworkStats: exception " + e.getMessage());
        }
        MultiSeriesTimebasedChartDTO[] returnData = new MultiSeriesTimebasedChartDTO[2];
        returnData[0] = typeByDateDTO;
        returnData[1] = deviceTotalsByTypeDTP;
        return returnData;
    }


    /**
     *      Gets stats details by device for one type of stats
     *
     * @param dslDashboard
     * @param dsbConn
     * @param dbid
     * @param currentNetworkId
     * @param statIndexNumber
     * @param hoursToGraph
     * @param numValuesPerDevice
     * @param lastSampleTime_from_getLastSampleno
     * @param logger
     * @return
     */
    public static StatDetailsByDeviceDTO getNetworkStatsForType(DSLContext dslDashboard, Connection dsbConn, int dbid, int currentNetworkId, int statIndexNumber, int hoursToGraph, int numValuesPerDevice, long lastSampleTime_from_getLastSampleno, Logger logger ) {
        boolean debug = false;
        boolean debugIpDevices = false;
        HoursToGraph htg = new HoursToGraph( hoursToGraph, numValuesPerDevice, lastSampleTime_from_getLastSampleno);
        ArrayList<String> times = htg.getArrayOfTimes();
        /*
        int i = 1;
        for (String time : times) {
            System.out.println( i++ + "  " +  time );
        }
        */
        StatsConstants sc = new StatsConstants();
        int type = sc.getClassForIndex(statIndexNumber);
        StringBuilder sb = new StringBuilder();

        sb.append("select deviceid, ");

        sb.append( htg.getSqlCaseCondition());

        sb.append("    count(qty) ");
        sb.append("from stats as s ");
        sb.append("where  type=");
        sb.append( type );
        sb.append(" and occurred BETWEEN '");
        sb.append( htg.getFirstTimeMysqlSyntax() );
        sb.append("' and '");
        sb.append( htg.getEndTimeMysqlSyntax() );
        sb.append("' and nwid=");
        sb.append( currentNetworkId );
        sb.append(" and dbid=");
        sb.append( dbid );
        sb.append(" group by deviceid, PerPeriod ");
        //sb.append(" order by deviceid");

        if (debug  || debugIpDevices) {
            logger.debug("************************************ this must agree with query for stats by type *******************");
            logger.debug( "getNetworkStatsForType: query > " + sb.toString());
            logger.debug("******************************************************************************************************");
        }

        ResultSet rs = null;
        StatDetailsByDeviceDTO dto = new StatDetailsByDeviceDTO(numValuesPerDevice);
        dto.setDates( htg.getArrayOfTimes());
        ArrayList<String> sTimes = htg.getArrayOfSqlStartTimes() ;
        HashMap<Integer,DeviceStatDetails> deviceDataMap = dto.getDeviceDataMap();
        try {
            rs = dsbConn.createStatement().executeQuery(sb.toString());
            Result<Record> result = dslDashboard.fetch(rs);
            int count = 1;
            for (Record r : result) {
                logger.debug(count + "  " + r.toString());
                Integer deviceid = (Integer) r.getValue(0);
                DeviceStatDetails details ;
                if ( deviceDataMap.containsKey(deviceid)) {
                    details = deviceDataMap.get(deviceid);
                } else {
                    details = new DeviceStatDetails(numValuesPerDevice);
                    details.setDivisor(1);
                    deviceDataMap.put( deviceid, details );
                    if (debugIpDevices) logger.debug("getNetworkStatsForType: add new deviceid = " +  deviceid );
                }
                byte[] ba = (byte[]) r.getValue(1);
//                int x = ba.length;
                Long qty = (Long) r.getValue(2);
                String ts = new String( ba );
                int index = getIndexFromTimes( sTimes, ts );
                if (index < 0) {
                    logger.error("getNetworkStatsForType: invalid index, ts " + ts  + " sTimes " + sTimes );
                } else {
                    details.setValue(index, qty.intValue());
                }
                ;
                // System.out.println("record: " + count++ + "  " + deviceid + "  " +  ts + "   " + qty );
            }
        } catch (SQLException e) {
            logger.error("StatDetailByDeviceDTO: SQLException " + e.getMessage(), e);
            if (true) {
                System.out.println("getNetworkStatsForType: exception " + e.getMessage());
                e.printStackTrace();
            }
        } catch (Exception e) {
            logger.error("StatDetailByDeviceDTO: Exception " + e.getMessage(), e);
            if (true) {
                System.out.println("getNetworkStatsForType: exception " + e.getMessage());
                e.printStackTrace();
            }
        }
        return dto;
    }

    private static int getIndexFromTimes(ArrayList<String> sTimes, String ts) {
        int index = -1;
        for (String sTime : sTimes) {
            if (sTime.equals(ts)) return sTimes.indexOf(ts);
        }
        return index;
    }

    /**
     *      Gets details for every device having stats for a network
     *
     * @param dslDashboard
     * @param diMap
     * @param dbid
     * @param networkId
     * @param starttime
     * @param endtime
     * @param logger
     * @return
     */
    public static HashMap<Integer, KpiStatsRow> getKpiStatsByDeviceForNetwork(DSLContext dslDashboard, HashMap<Integer, DeviceInfoDTO> diMap, int dbid, int networkId, boolean criticalOnly, long starttime, long endtime, Logger logger) {
        HashMap<Integer, KpiStatsRow> kpiStatsMap = new HashMap<Integer, KpiStatsRow>();
        boolean debug = true;
        Stats stats = Stats.STATS;

        long l2000  = new DateTime( 2000, 1, 1, 0, 0).getMillis();
        if ( starttime < l2000 || endtime < l2000) {
            logger.error( "getKpiStatsByDeviceForNetwork: ERROR, invalid year 0 being used");
            return kpiStatsMap;
        }
        Result<StatsRecord> statsRecords = dslDashboard.selectFrom(stats)
                .where(stats.DBID.equal(dbid))
                .and(stats.NWID.equal(networkId))
                .and(stats.OCCURRED.between( new Timestamp(starttime), new Timestamp(endtime)))
                .fetch();
        if (debug) logger.debug("getKpiStatsByDeviceForNetwork: found records "  + statsRecords.size() ) ;
        for (StatsRecord statsRecord : statsRecords) {
            int deviceid = statsRecord.getDeviceid();
            KpiStatsRow statsRow = new KpiStatsRow();
            if ( kpiStatsMap.containsKey( deviceid)) {
                statsRow = kpiStatsMap.get(deviceid);
            } else {
                // no stats Row for this deviceid yet
                if (diMap.containsKey(deviceid)) {
                    DeviceInfoDTO di = diMap.get(deviceid);
                    if ( criticalOnly && di.getCriticalType() < 2) {
                        continue;
                    }
                    statsRow.setClevel(di.getCriticalType());
                    statsRow.setNameToDisplay(di.getName());
                    statsRow.setIpaddress(di.getIpAddress());
                    statsRow.setSwitch(di.isSwitch());
                    kpiStatsMap.put(deviceid, statsRow);
                    if (debug) logger.debug("getKpiStatsByDeviceForNetwork: adding new device " + deviceid + "  " + di.getIpAddress()) ;
                } else {
                    logger.warn("getKpiStatsByDeviceForNetwork: failed to find device info for deviceid " + deviceid);
                    continue;
                }
            }
            int type = statsRecord.getType();
            if ( type == StatsConstants.CLASS_PING) {
                statsRow.setPt( statsRow.getPt() + 1);
            } else if ( type == StatsConstants.CLASS_BW) {
                statsRow.setPt( statsRow.getBw() + 1);
            } else if ( type == StatsConstants.CLASS_DEVICE_CHANGE_IP) {
                statsRow.setPt( statsRow.getIpc() + 1);
            } else if ( type == StatsConstants.CLASS_DEVICE_CHANGE_MAC) {
                statsRow.setPt( statsRow.getMacc() + 1);
            } else if ( type == StatsConstants.CLASS_MOVE) {
                statsRow.setPt( statsRow.getMove() + 1);
            } else if ( type == StatsConstants.CLASS_LINK_CHANGE) {
                statsRow.setPt( statsRow.getLink() + 1);
            } else if ( type == StatsConstants.CLASS_CONNECT) {
                statsRow.setPt( statsRow.getDisc() + 1);
            } else {
                logger.warn("getKpiStatsByDeviceForNetwork: unknown STAT type found " + type + " record " + statsRecord.toString() );
                continue;
            }
            statsRow.setTotal( statsRow.getTotal() + 1 );
        }
        return kpiStatsMap;
    }

}
