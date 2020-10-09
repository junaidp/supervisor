package com.wbc.supervisor.database.dashboard;

import com.wbc.supervisor.database.generated.tables.Properties;
import com.wbc.supervisor.database.generated.tables.Thresholds;
import com.wbc.supervisor.database.generated.tables.records.PropertiesRecord;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Result;

import java.sql.Timestamp;

/**
 * Created by JIM on 10/3/2014.
 */
public class DashboardUtil {

    public static int getCurrentDmpfileId( DSLContext dslDashboard) {
        Properties props = Properties.PROPERTIES;
        PropertiesRecord currentDbidProp = dslDashboard.selectFrom(props)
                .where(props.TYPE.equal("db")).and(props.NAME.equal("current.db"))
                .fetchOne();
        return Integer.parseInt(currentDbidProp.getValue());
    }

    public static void updateTimezoneOffsetInProperties (DSLContext dsl, int offset ) {
        Properties propTable = Properties.PROPERTIES;
        dsl.update(propTable)
                .set(propTable.VALUE, ""+offset )
                .where( propTable.TYPE.equal("system")).and(propTable.NAME.equal("tzoffset"))
                .execute();
    }

    public static Timestamp getLastThresholdsTimestamp(DSLContext dslDashboard, int dbid, StringBuilder errors  ) {
        Thresholds thresholds = new Thresholds();
        String sql = "select max(s.occurred) from thresholds as s where s.dbid=" + dbid + " limit 1";
        Result<Record> result = dslDashboard.fetch(sql);
        //TODO protect against null if database is clean
        if (result.size() > 0) {
            Record1 r1 = (Record1) result.get(0);
            if (r1 == null) {
                errors.append("getLastThresholdsTimestamp: no record in thresholds for dbid " + dbid);
                return new Timestamp(0L);
            } else {
                return (Timestamp) r1.getValue(0);
            }
        } else {
            errors.append("getLastThresholdsTimestamp: result size is 0 for dbid " + dbid);
            return new Timestamp(0L);
        }
    }

}
