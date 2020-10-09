package com.wbc.supervisor.dashboard;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;

/**
 * Created by JIM on 2/6/2015.
 */
public class HoursToGraph {
    int iHoursToGraph;
    long longEndTime;
    long firstTime;
    private int numTimePeriods = 24;

    public HoursToGraph(int iHoursToGraph, int numValuesPerDevice, long endTime ) {
        this.iHoursToGraph = iHoursToGraph;
        this.numTimePeriods = numValuesPerDevice;
        this.longEndTime = endTime;
        getFirstTime();
    }

    public long getFirstTime() {
        firstTime = 0;
        int millisPerPeriod = (int)(60000 * 60 * (double)((double)iHoursToGraph / (double)numTimePeriods ));
        DateTime lastFloor = new DateTime(((longEndTime / millisPerPeriod) * millisPerPeriod) + millisPerPeriod );
        if( iHoursToGraph == 48 && ( lastFloor.getHourOfDay()%2)!=0) {
            // this is an odd hour, we must subtract an hour so all huors are even
            lastFloor = lastFloor.minusHours(1);
        }
        DateTime firstFloor = lastFloor.minusMillis(numTimePeriods * millisPerPeriod);
        firstTime = firstFloor.getMillis();
        /*
        if ( iHoursToGraph == 8) {
            int millisPerPeriod = 60000 * 20;
            DateTime lastFloor = new DateTime((longEndTime / millisPerPeriod) * millisPerPeriod);
            DateTime firstFloor = lastFloor.minusMinutes(numTimePeriods * 20);
            firstTime = firstFloor.getMillis();

        } else if ( iHoursToGraph == 24) {
            int millisPerPeriod = 60000 * 60;
            DateTime lastFloor = new DateTime((longEndTime / millisPerPeriod) * millisPerPeriod);
            DateTime firstFloor = lastFloor.minusHours(numTimePeriods);
            firstTime = firstFloor.getMillis();
        } else if ( iHoursToGraph == 48) {
            int millisPerPeriod = 2 * 60000 * 60;
            DateTime lastFloor = new DateTime((longEndTime / millisPerPeriod) * millisPerPeriod);
            DateTime firstFloor = lastFloor.minusHours(numTimePeriods);
            firstTime = firstFloor.getMillis();
        }
        */
        return firstTime;
    }

    public String getFirstTimeMysqlSyntax() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm");
        return fmt.print(firstTime);
    }

    public String getEndTimeMysqlSyntax() {
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm");
        return fmt.print(longEndTime);
    }

    /*
            Used to get Strings to use in chart headings
     */
    public ArrayList<String> getArrayOfTimes() {
        ArrayList<String> times = new ArrayList<String>(numTimePeriods);
        DateTimeFormatter fmtHr = DateTimeFormat.forPattern("HH:mm");
        DateTimeFormatter fmtDay = DateTimeFormat.forPattern("MM/dd");
        if ( iHoursToGraph == 8) {
            int millisPerPeriod = 60000 * 20;
            DateTime firstFloor = new DateTime( firstTime );
            for ( int i=0; i<numTimePeriods; i++) {
                times.add( i, fmtDay.print(firstFloor.plusMinutes(i * 20) ) + " " + fmtHr.print( firstFloor.plusMinutes(i * 20)) + " - " + fmtHr.print(firstFloor.plusMinutes((i*20)+20)));
            }
        } else if ( iHoursToGraph == 24) {
            DateTime firstFloor = new DateTime( firstTime );
            for ( int i=0; i<numTimePeriods; i++) {
                times.add( i, fmtDay.print(firstFloor.plusHours(i) ) + " " + fmtHr.print( firstFloor.plusHours(i)) + " - " + fmtHr.print(firstFloor.plusHours(i + 1)));
            }
        } else if ( iHoursToGraph == 48) {
            DateTime firstFloor = new DateTime( firstTime );
            for ( int i=0; i<numTimePeriods; i++) {
                times.add( i, fmtDay.print(firstFloor.plusHours(i*2) ) + " " + fmtHr.print( firstFloor.plusHours(i*2)) + " - " + fmtHr.print(firstFloor.plusHours((i*2)+2)));
            }
        }
        return times;
    }

    /*
                Used to get Strings to use in chart headings
         */
    public ArrayList<String> getArrayOfSqlStartTimes() {
        ArrayList<String> times = new ArrayList<String>(numTimePeriods);
        DateTimeFormatter fmt = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm");
        if ( iHoursToGraph == 8) {
            DateTime firstFloor = new DateTime( firstTime );
            for ( int i=0; i<numTimePeriods; i++) {
                //times.add( i, fmtDay.print(firstFloor.plusMinutes(i * 20) ) + " " + fmtHr.print( firstFloor.plusMinutes(i * 20)) + " - " + fmtHr.print(firstFloor.plusMinutes((i*20)+20)));
                times.add( fmt.print(firstFloor.plusMinutes(i * 20)));
            }
        } else if ( iHoursToGraph == 24) {
            DateTime firstFloor = new DateTime( firstTime );
            for ( int i=0; i<numTimePeriods; i++) {
                times.add( i, fmt.print(firstFloor.plusHours(i) ));
            }
        } else if ( iHoursToGraph == 48) {
            DateTime firstFloor = new DateTime( firstTime );
            for ( int i=0; i<numTimePeriods; i++) {
                times.add( i, fmt.print(firstFloor.plusHours(i*2) ));
            }
        }
        return times;
    }

    public String getSqlCaseCondition() {
        StringBuilder sb = new StringBuilder();
        if ( iHoursToGraph == 8) {
            sb.append("    CONCAT( ");
            sb.append("        DATE(occurred), ' ',");
            sb.append("        HOUR(occurred), ':',");
            sb.append("        CASE ");
            sb.append("            WHEN MINUTE(occurred) BETWEEN  0 AND 19 THEN '00'");
            sb.append("            WHEN MINUTE(occurred) BETWEEN 20 AND 39 THEN '20'");
            sb.append("            WHEN MINUTE(occurred) BETWEEN 40 AND 59 THEN '40'");
            sb.append("        END ");
            sb.append("    ) AS PerPeriod, ");
        } else if ( iHoursToGraph == 24) {
            sb.append("    CONCAT( ");
            sb.append("        DATE(occurred), ' ',");
            sb.append("        CASE ");
            sb.append("            WHEN HOUR(occurred) = 0 THEN '00:00'");
            sb.append("            WHEN HOUR(occurred) = 1 THEN '01:00'");
            sb.append("            WHEN HOUR(occurred) = 2 THEN '02:00'");
            sb.append("            WHEN HOUR(occurred) = 3 THEN '03:00'");
            sb.append("            WHEN HOUR(occurred) = 4 THEN '04:00'");
            sb.append("            WHEN HOUR(occurred) = 5 THEN '05:00'");
            sb.append("            WHEN HOUR(occurred) = 6 THEN '06:00'");
            sb.append("            WHEN HOUR(occurred) = 7 THEN '07:00'");
            sb.append("            WHEN HOUR(occurred) = 8 THEN '08:00'");
            sb.append("            WHEN HOUR(occurred) = 9 THEN '09:00'");
            sb.append("            WHEN HOUR(occurred) = 10 THEN '10:00'");
            sb.append("            WHEN HOUR(occurred) = 11 THEN '11:00'");
            sb.append("            WHEN HOUR(occurred) = 12 THEN '12:00'");
            sb.append("            WHEN HOUR(occurred) = 13 THEN '13:00'");
            sb.append("            WHEN HOUR(occurred) = 14 THEN '14:00'");
            sb.append("            WHEN HOUR(occurred) = 15 THEN '15:00'");
            sb.append("            WHEN HOUR(occurred) = 16 THEN '16:00'");
            sb.append("            WHEN HOUR(occurred) = 17 THEN '17:00'");
            sb.append("            WHEN HOUR(occurred) = 18 THEN '18:00'");
            sb.append("            WHEN HOUR(occurred) = 19 THEN '19:00'");
            sb.append("            WHEN HOUR(occurred) = 20 THEN '20:00'");
            sb.append("            WHEN HOUR(occurred) = 21 THEN '21:00'");
            sb.append("            WHEN HOUR(occurred) = 22 THEN '22:00'");
            sb.append("            WHEN HOUR(occurred) = 23 THEN '23:00'");
            sb.append("        END ");
            sb.append("    ) AS PerPeriod, ");
        } else if ( iHoursToGraph == 48) {
            sb.append("    CONCAT( ");
            sb.append("        DATE(occurred), ' ',");
            sb.append("        CASE ");
            /*                                don't use between for just 2 possibilities
            sb.append("            WHEN HOUR(occurred) BETWEEN  0 AND 1 THEN '00'");
             */
            sb.append("            WHEN HOUR(occurred) = 0 THEN '00:00'");
            sb.append("            WHEN HOUR(occurred) = 1 THEN '00:00'");
            sb.append("            WHEN HOUR(occurred) = 2 THEN '02:00'");
            sb.append("            WHEN HOUR(occurred) = 3 THEN '02:00'");
            sb.append("            WHEN HOUR(occurred) = 4 THEN '04:00'");
            sb.append("            WHEN HOUR(occurred) = 5 THEN '04:00'");
            sb.append("            WHEN HOUR(occurred) = 6 THEN '06:00'");
            sb.append("            WHEN HOUR(occurred) = 7 THEN '06:00'");
            sb.append("            WHEN HOUR(occurred) = 8 THEN '08:00'");
            sb.append("            WHEN HOUR(occurred) = 9 THEN '08:00'");
            sb.append("            WHEN HOUR(occurred) = 10 THEN '10:00'");
            sb.append("            WHEN HOUR(occurred) = 11 THEN '10:00'");
            sb.append("            WHEN HOUR(occurred) = 12 THEN '12:00'");
            sb.append("            WHEN HOUR(occurred) = 13 THEN '12:00'");
            sb.append("            WHEN HOUR(occurred) = 14 THEN '14:00'");
            sb.append("            WHEN HOUR(occurred) = 15 THEN '14:00'");
            sb.append("            WHEN HOUR(occurred) = 16 THEN '16:00'");
            sb.append("            WHEN HOUR(occurred) = 17 THEN '16:00'");
            sb.append("            WHEN HOUR(occurred) = 18 THEN '18:00'");
            sb.append("            WHEN HOUR(occurred) = 19 THEN '18:00'");
            sb.append("            WHEN HOUR(occurred) = 20 THEN '20:00'");
            sb.append("            WHEN HOUR(occurred) = 21 THEN '20:00'");
            sb.append("            WHEN HOUR(occurred) = 22 THEN '22:00'");
            sb.append("            WHEN HOUR(occurred) = 23 THEN '22:00'");
            sb.append("        END ");
            sb.append("    ) AS PerPeriod, ");
        }
        return sb.toString();
    }


}
