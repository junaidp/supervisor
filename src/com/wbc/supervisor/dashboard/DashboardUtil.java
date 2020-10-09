package com.wbc.supervisor.dashboard;


import com.wbc.supervisor.shared.dto.DeviceinfoNamesDTO;
import com.wbc.supervisor.shared.DeviceInfoDTO;
import com.wbc.supervisor.shared.DeviceTopologyInfo;
import com.wbc.supervisor.shared.NamesDTO;
import com.wbc.supervisor.database.generated.tables.Deviceinfo;
import com.wbc.supervisor.database.generated.tables.Devicenameinfo;
import com.wbc.supervisor.database.generated.tables.Properties;
import com.wbc.supervisor.database.generated.tables.Topoinfo;
import com.wbc.supervisor.database.generated.tables.records.DeviceinfoRecord;
import com.wbc.supervisor.database.generated.tables.records.DevicenameinfoRecord;
import com.wbc.supervisor.database.generated.tables.records.PropertiesRecord;
import com.wbc.supervisor.database.generated.tables.records.TopoinfoRecord;
import org.apache.log4j.Logger;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JIM on 10/3/2014.
 */
public class DashboardUtil {

    private static java.util.logging.Logger logger = java.util.logging.Logger.getLogger("PingDetailsEastPanel.class");

    public static int getCurrentDmpfileId(DSLContext dslDashboard, Logger logger ) {
        Properties props = Properties.PROPERTIES;
        PropertiesRecord currentDbidProp = dslDashboard.selectFrom(props)
                .where(props.TYPE.equal("db")).and(props.NAME.equal("current.db"))
                .fetchOne();
        return Integer.parseInt(currentDbidProp.getValue());
    }

    public static long getTimezoneOffsetFromDb(DSLContext dslDashboard, Logger logger ) {
        Properties props = Properties.PROPERTIES;
        PropertiesRecord currentDbidProp = dslDashboard.selectFrom(props)
                .where(props.TYPE.equal("system")).and(props.NAME.equal("tzoffset"))
                .fetchOne();
        return Long.parseLong(currentDbidProp.getValue());
    }

    public static Timestamp getLastThresholdsTimestamp(DSLContext dslDashboard, Logger logger  ) {
        Integer dbid = getCurrentDmpfileId(dslDashboard, logger);


        String sql = "select max(s.occurred) from thresholds as s where s.dbid=" + dbid + " limit 1" ;
        Result<Record> result = dslDashboard.fetch(sql);
        long t3 = System.currentTimeMillis();
        //TODO protect against null if database is clean
        if ( result.size() > 0 ) {
            Record1 r1 = (Record1) result.get(0);
            if (r1 == null) {
                logger.error("getLastThresholdsTimestamp: no record in thresholds for dbid " + dbid );
                return new Timestamp(0L);
            } else {
                Timestamp ts = (Timestamp) r1.getValue(0);
                if (ts==null) {
                    logger.error("getLastThresholdsTimestamp:null timestamp in thresholds for dbid " + dbid );
                    return new Timestamp(0L);
                }
                return ts;
            }
        } else {
            logger.error("getLastThresholdsTimestamp: result size is 0 for dbid " + dbid );
            return new Timestamp(0L);
        }
        /*



         Result<Record1<Timestamp>> results = dslDashboard.selectDistinct(thresholds.OCCURRED.max())
                .from(thresholds)
                .where(thresholds.DBID.equal(dbid))
                .fetch();
        if ( results.size() != 0) {
            Record1 record = results.get(0);
            Timestamp ts = record.getValue(thresholds.OCCURRED);
            return ts;
        } else {
            return new Timestamp(0L);
        }
        */
    }


    public static synchronized ArrayList<String> computeDatesForSamples( long firstSampleTime, long lastSampleTime, int minutesPerPoint ) {
        // input is in long millis, minutes per point is 1 minute
        long firstSample = firstSampleTime / 60000L;
        long lastSample = lastSampleTime / 60000l;
        logger.info("DEBUG computeDatesForSamples   " + System.currentTimeMillis() + " firstSample " + firstSample + "  lastSample " + lastSample + "  minutesPerPoint " + minutesPerPoint);
        ArrayList<String> dates = new ArrayList<String>();
        for ( long sample=firstSample; sample<lastSample; sample+=minutesPerPoint ) {
//            dates.add( Long.toString( ((sample * 60000L )/minutesPerPoint) * minutesPerPoint ));
            dates.add( Long.toString( (sample/minutesPerPoint) * minutesPerPoint * 60000L ));
        }
        logger.info("DEBUG computeDatesForSamples   " + System.currentTimeMillis() + " done");
        return dates;
    }

    public static ArrayList<NamesDTO> getNameChanges(HashMap<Integer, DeviceInfoDTO> deviceInfoMap, DeviceinfoNamesDTO deviceinfoNamesDTO, Logger logger) {
        ArrayList<NamesDTO> changedNames = new ArrayList<NamesDTO>();
        int descid = deviceinfoNamesDTO.getDeviceid();
        if (deviceInfoMap.containsKey( descid)) {
            DeviceInfoDTO info = deviceInfoMap.get(descid);
            if ( !info.getName().equals(deviceinfoNamesDTO.getName())) {
                NamesDTO name = new NamesDTO(descid, "name", deviceinfoNamesDTO.getName());
                changedNames.add(name);
            }
            if ( !info.getLocation().equals(deviceinfoNamesDTO.getLocation())) {
                NamesDTO name = new NamesDTO(descid, "loc", deviceinfoNamesDTO.getLocation());
                changedNames.add(name);
            }
            if ( !info.getUd1name().equals(deviceinfoNamesDTO.getUd1name())) {
                NamesDTO name = new NamesDTO(descid, "ud1", deviceinfoNamesDTO.getUd1name());
                changedNames.add(name);
            }
            if ( !info.getUd2name().equals(deviceinfoNamesDTO.getUd2name())) {
                NamesDTO name = new NamesDTO(descid, "ud2", deviceinfoNamesDTO.getUd2name());
                changedNames.add(name);
            }
            if ( !info.getUd3name().equals(deviceinfoNamesDTO.getUd3name())) {
                NamesDTO name = new NamesDTO(descid, "ud3", deviceinfoNamesDTO.getUd3name());
                changedNames.add(name);
            }
            if ( !info.getUd4name().equals(deviceinfoNamesDTO.getUd4name())) {
                NamesDTO name = new NamesDTO(descid, "ud4", deviceinfoNamesDTO.getUd4name());
                changedNames.add(name);
            }
            if ( !info.getUd5name().equals(deviceinfoNamesDTO.getUd5name())) {
                NamesDTO name = new NamesDTO(descid, "ud5", deviceinfoNamesDTO.getUd5name());
                changedNames.add(name);
            }
            if ( !info.getUd6name().equals(deviceinfoNamesDTO.getUd6name())) {
                NamesDTO name = new NamesDTO(descid, "ud6", deviceinfoNamesDTO.getUd6name());
                changedNames.add(name);
            }
        }
        return changedNames;
    }

    public static HashMap<Integer, DeviceInfoDTO> getDeviceInfo(DSLContext dslDashboard, Logger logger) {
        HashMap<Integer, DeviceInfoDTO> diMap = new HashMap<Integer, DeviceInfoDTO>();
        Deviceinfo deviceinfo = Deviceinfo.DEVICEINFO;

        Result<DeviceinfoRecord> records = dslDashboard.selectFrom(deviceinfo).fetch();
        for (DeviceinfoRecord record : records) {
            DeviceInfoDTO dto = new DeviceInfoDTO();
            dto.setIpAddress(record.getIpaddress());
//System.out.println("getDeviceInfo: get/set macaddress " + record.getIpaddress() + "     " + record.getMacaddress());
            dto.setMacAddress(record.getMacaddress());
            dto.setNetmask(record.getNetmask());
            dto.setChildid(record.getChildid());
            dto.setParentid(record.getParentid());
            if (Byte.valueOf(record.getAutoconnect()) == 0) {
                dto.setAutoConnect(false);
            } else {
                dto.setAutoConnect(true);
            }
            dto.setName(record.getName());

            dto.setVerified(true);
            if (Byte.valueOf(record.getVerified()) == 0) {
                dto.setVerified(false);
            }
            dto.setWAP(false);
            //TODO add isWap to the deviceinfo table
            /*
            if (Byte.valueOf(record.get()) == 0) {
                dto.setVerified(false);
            }
            */
            dto.setSwitch(true);
            if (Byte.valueOf(record.getIsswitch()) == 0) {
                dto.setSwitch(false);
            }
            dto.setHasRedBox(true);
            if (Byte.valueOf(record.getHasredbox()) == 0) {
                dto.setHasRedBox(false);
            }
            dto.setWirelessDevice(true);
            if (Byte.valueOf(record.getIswirelessdevice()) == 0) {
                dto.setWirelessDevice(false);
            }
            dto.setCriticalType(record.getKpitype());
            dto.setSpeed(record.getSpeed());
            dto.setNetworkid(record.getNetworkid());
            if (Byte.valueOf(record.getConnected()) == 0) {
                dto.setConnected(false);
            } else {
                dto.setConnected(true);
            }
            diMap.put( dto.getParentid(), dto );
        }

        Devicenameinfo devicenameinfo = Devicenameinfo.DEVICENAMEINFO;
        Result<DevicenameinfoRecord> records2 = dslDashboard.selectFrom(devicenameinfo).fetch();
        for (DevicenameinfoRecord record : records2) {
            Integer deviceid = record.getDeviceid();
            if ( diMap.containsKey(deviceid)) {
                DeviceInfoDTO dto = diMap.get(deviceid);
                dto.setLocation(record.getLocation());
                dto.setUd1name(record.getUd1name());
                dto.setUd2name(record.getUd2name());
                dto.setUd3name(record.getUd3name());
                dto.setUd4name(record.getUd4name());
                dto.setUd5name(record.getUd5name());
                dto.setUd6name(record.getUd6name());
                dto.setVendorName(record.getUd5name());
                dto.setNotes(record.getNotes());
            } else {
                logger.warn("found DevicenameinfoRecord with no DeviceinfoRecord for deviceid " + deviceid + record.toString()  );
            }
        }
        return diMap;    }

    public static ArrayList<DeviceTopologyInfo> getTopoList(DSLContext dslDashboard, Logger logger) {
        ArrayList<DeviceTopologyInfo> list = new ArrayList<DeviceTopologyInfo>();
        Topoinfo topoinfo = Topoinfo.TOPOINFO;
        Result<TopoinfoRecord> records2 = dslDashboard.selectFrom(topoinfo).fetch();
        for (TopoinfoRecord record : records2) {
            DeviceTopologyInfo dto = new DeviceTopologyInfo();
            dto.setParent( record.getParent() );
            dto.setChild( record.getChild());
            dto.setIpaddress( record.getChildip());
            dto.setPortno( record.getPortno());
            dto.setUpPort( record.getUpportno());
            dto.setName( record.getName());
            //TODO added connected to topoinfo table
            dto.setUpPortName("unknown");
            dto.setPortname("unknown");
            dto.setNetworkid(record.getNetworkid());
            if (false) logger.info("parent "+ record.getParent() + " " + dto.toString() );
            list.add(dto);
        }
        return list;
    }

    public static long updateTopoOnChange(DSLContext dslDashboard, long lastChange, Logger logger) {
        long lastUpdate = 0;
        Properties propTable = Properties.PROPERTIES;
        Result<Record1<String>> results = dslDashboard.select(propTable.VALUE )
                .from(propTable)
                .where(propTable.TYPE.equal("update")).and(propTable.NAME.equal("devices"))
                .fetch();
        if ( results.size() > 0) {
            Record1 record = results.get(0);
            if ( record != null) {
                lastUpdate = Long.parseLong((String)record.getValue(0));
            }
        }
        if (lastUpdate == 0) {
            logger.info("updateTopoOnChange: lastUpdate is 0, do not update. return passed in lastChange " + lastChange );
            lastUpdate = lastChange;
        }
        return lastUpdate;
    }

    public static long updateNamesOnChange(DSLContext dslDashboard, long lastChange, Logger logger) {
        long lastUpdate = 0;
        Properties propTable = Properties.PROPERTIES;
        Result<Record1<String>> results = dslDashboard.select(propTable.VALUE )
                .from(propTable)
                .where(propTable.TYPE.equal("update")).and(propTable.NAME.equal("names"))
                .fetch();
        if ( results.size() > 0) {
            Record1 record = results.get(0);
            if ( record != null) {
                lastUpdate = Long.parseLong((String)record.getValue(0));
            }
        }
        if (lastUpdate == 0) {
            logger.info("updateNamesOnChange: lastUpdate is 0, do not update. return passed in lastChange " + lastChange );
            lastUpdate = lastChange;
        }
        return lastUpdate;
    }

    /*
    from dsbsvr
        final int IV_STATE_UNKNOWN = -1;
        final int IV_STATE_INVALID = 2;
        final int IV_STATE_OFFLINE = 3;
        final int IV_STATE_ONLINE  = 4;
        final int IV_STATE_STOPPED = 5;

     */

    public static int getScannerState(DSLContext dslDashboard, Logger logger) {
        Properties propTable = Properties.PROPERTIES;
        PropertiesRecord pRecord = dslDashboard.selectFrom(propTable)
                .where(propTable.TYPE.equal("scanner")).and(propTable.NAME.equal("state"))
                .fetchOne();
        if (pRecord != null) {
            return Integer.parseInt(pRecord.getValue());
        } else {
            return -1;
        }
    }

    public static void updateDeviceNote(DSLContext dslDashboard, HashMap<Integer, DeviceInfoDTO> deviceidInfoMap, DeviceinfoNamesDTO changedDeviceinfoNamesDTO, Logger logger) {
        int descid = changedDeviceinfoNamesDTO.getDeviceid();
        if (deviceidInfoMap.containsKey( descid)) {
            DeviceInfoDTO info = deviceidInfoMap.get(descid);
            if ( !info.getNotes().equals(changedDeviceinfoNamesDTO.getNotes())) {
                info.setNotes( changedDeviceinfoNamesDTO.getNotes());
                Properties propTable = Properties.PROPERTIES;
                PropertiesRecord pRecord = dslDashboard.selectFrom(propTable)
                        .where(propTable.TYPE.equal("device")).and(propTable.NAME.equal("notes")).and(propTable.ID.eq("" + descid))
                        .fetchOne();
                if (pRecord != null) {
                    // no previous note, insert it
                    pRecord.setType("device");
                    pRecord.setName("notes");
                    pRecord.setValue( changedDeviceinfoNamesDTO.getNotes());
                    pRecord.insert();
                } else {
                    pRecord.setValue( changedDeviceinfoNamesDTO.getNotes());
                    pRecord.update();
                }
            }
        }
    }

    public static String getDeviceNote(DSLContext dslDashboard, int deviceid ) {
        String note = "";
        Properties propTable = Properties.PROPERTIES;
        PropertiesRecord pRecord = dslDashboard.selectFrom(propTable)
                .where(propTable.TYPE.equal("device")).and(propTable.NAME.equal("notes")).and(propTable.ID.eq("" + deviceid))
                .fetchOne();
        if (pRecord != null) {
            note = pRecord.getValue();
        }
        return note;
    }

    public static void getIntravueProperties(DSLContext dslDashboard, java.util.Properties intravueProperties) {
        Properties propTable = Properties.PROPERTIES;
        Result<PropertiesRecord> results = dslDashboard.selectFrom(propTable)
                .where(propTable.TYPE.equal("system")).and(propTable.NAME.equal("userDefined"))
                .fetch();
        for (PropertiesRecord pRecord : results) {
            int udNumber = Integer.parseInt(pRecord.getId());
            if (udNumber == 1) {
                intravueProperties.setProperty("userDefined1", pRecord.getValue());
            } else if (udNumber == 2) {
                intravueProperties.setProperty("userDefined2", pRecord.getValue());
            } else if (udNumber == 3) {
                intravueProperties.setProperty("userDefined3", pRecord.getValue());
            } else if (udNumber == 4) {
                intravueProperties.setProperty("userDefined4", pRecord.getValue());
            } else if (udNumber == 5) {
                intravueProperties.setProperty("userDefined5", pRecord.getValue());
            } else if (udNumber == 6) {
                intravueProperties.setProperty("userDefined6", pRecord.getValue());
            }
        }
    }
}
