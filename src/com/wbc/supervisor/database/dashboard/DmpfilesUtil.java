package com.wbc.supervisor.database.dashboard;

import com.wbc.supervisor.database.generated.tables.Dmpfiles;
import com.wbc.supervisor.database.generated.tables.Properties;
import com.wbc.supervisor.database.generated.tables.records.DmpfilesRecord;
import org.jooq.DSLContext;
import org.jooq.Record1;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by JIM on 9/23/2014.
 */
public class DmpfilesUtil {


    public static DmpfilesRecord getDmpfileInfo(DSLContext dslDashboard, int dbid ) {
        Dmpfiles dmpfiles = Dmpfiles.DMPFILES;
        DmpfilesRecord r = dslDashboard.selectFrom(dmpfiles)
                .where(dmpfiles.DBID.equal(dbid))
                .fetchAny();  // there should be / can be only one
        if ( r == null) {
            r = new DmpfilesRecord();
            r.setEventid(0);
            r.setLastadj(new Timestamp(0L));
            r.setOccurred(new Timestamp(0L));
            r.setVersion("not found");
            r.setSig("not found");
        }
        return r;
    }

    public static int getCurrentDatabaseId(DSLContext dslDashboard) {
        int id = 0;
        Properties propTable = Properties.PROPERTIES;
        Result<Record1<String>> results = dslDashboard.select(propTable.VALUE)
                .from(propTable)
                .where( propTable.TYPE.equal("db")).and( propTable.NAME.equal("current.db"))
                .fetch();
        if ( results.size() > 0) {
            Record1 record = results.get(0);
            if ( record != null) {
                id = Integer.parseInt((String)record.getValue(0));
            }
        }
        return id;
    }

    public static int findDmpfileDbid(DSLContext dslDashboard, DmpfilesRecord dmpToFind  ) {
        int dbid = 0;
        Dmpfiles dmpfiles = Dmpfiles.DMPFILES;
        DmpfilesRecord r = dslDashboard.selectFrom(dmpfiles)
                .where(dmpfiles.EVENTID.equal( dmpToFind.getEventid()))
                .and(dmpfiles.OCCURRED.equal( dmpToFind.getOccurred()))
                .and(dmpfiles.SIG.equal( dmpToFind.getSig()))
                .fetchAny();  // there should be / can be only one
        if ( r != null) {
            dbid = r.getDbid();
        }
        return dbid;
    }

    public static int addNewDmpfile(DSLContext dslDashboard, DmpfilesRecord recordToAdd  ) {
        int dbid = 0;
        if ( recordToAdd.getStringid() == null) {
            recordToAdd.setStringid(0);
        }
        // test to be sure it is NOT already in the database !!!
        dbid = findDmpfileDbid( dslDashboard, recordToAdd);
        if ( dbid != 0) return dbid;
        dslDashboard.executeInsert(recordToAdd);
        dbid = findDmpfileDbid( dslDashboard, recordToAdd);
        updateDbidInProperties( dslDashboard, dbid );
        return dbid;
    }

    public static void updateDbidInProperties (DSLContext dsl, int dbid ) {
        Properties propTable = Properties.PROPERTIES;
        String sval = "" + dbid;
        dsl.update(propTable)
                .set(propTable.VALUE, sval)
                .where( propTable.TYPE.equal("db")).and(propTable.NAME.equal("current.db"))
                .execute();
    }

    public static boolean isDatabaseSignatureChanged( DmpfilesRecord storedDatabase, DmpfilesRecord remoteDatabase, StringBuilder sb ) {
        boolean changed = false;
        if ( !storedDatabase.getEventid().equals( remoteDatabase.getEventid())) {
            changed = true;
            sb.append( String.format("Eventid changed from %d to %d", storedDatabase.getEventid(),  remoteDatabase.getEventid() ));
        }
        if ( !storedDatabase.getSig().equals( remoteDatabase.getSig())) {
            changed = true;
            sb.append( String.format("Signature changed from %s to %s", storedDatabase.getSig(),  remoteDatabase.getSig() ));
        }
        if ( storedDatabase.getOccurred().getTime() != remoteDatabase.getOccurred().getTime()) {
            changed = true;
            sb.append( String.format("occurred changed from %s to %s", storedDatabase.getOccurred().toString(),  remoteDatabase.getOccurred().toString() ));
        }
        return changed;
    }

}
