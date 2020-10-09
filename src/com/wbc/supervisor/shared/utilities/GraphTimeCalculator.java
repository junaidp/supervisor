package com.wbc.supervisor.shared.utilities;

import java.util.Date;

/**
 * Created by JIM on 4/13/2015.
 */
public class GraphTimeCalculator {

    private long starttime= 1000000;
    private long endtime = starttime;
    private int  hoursToGraph;
    private int maxPointsInGraph;


    public GraphTimeCalculator( int hoursToGraph, int maxPointsInGraph ) {
        this.hoursToGraph = hoursToGraph;
        this.maxPointsInGraph = maxPointsInGraph;
    }

    public long setEndtime( long endtime) {
        starttime = endtime - ( hoursToGraph * 60 * 60 * 1000 );
        return starttime;
    }

    public long setStarttime( long starttime ) {
        endtime = starttime + ( hoursToGraph * 60 * 60 * 1000 );
        return endtime;
    }


    public long setEndtime( long endtime, boolean debug, String text ) {
        starttime = endtime - ( hoursToGraph * 60 * 60 * 1000 );
        return starttime;
    }

    public long setStarttime( long starttime, boolean debug, String text  ) {
        endtime = starttime + ( hoursToGraph * 60 * 60 * 1000 );
        return endtime;
    }


    public long setEndtime( long endtime,  boolean debug, String message, java.util.logging.Logger logger ) {
        long starttime = endtime - ( hoursToGraph * 60 * 60 * 1000 );
        if (debug) logger.info( "GraphTimeCalculator: setEndtime = " + endtime + " makes startTime = " + starttime + " hoursToGraph =" + hoursToGraph  + "  " + message );
        return starttime;
    }

    public long setStarttime( long starttime,  boolean debug, String message, java.util.logging.Logger logger  ) {
        long endtime = starttime + ( hoursToGraph * 60 * 60 * 1000 );
        if (debug) logger.info( "GraphTimeCalculator: setStarttime = " + starttime + " makes endtime = " + endtime + "  " + message );
        return endtime;
    }


    /**
     *
     * @param endtimeToday
     * @return 23:59 of the day before
     *
     * NOTE this uses deprecated Data class
     */
    public long getEndtimeForYesterday( long endtimeToday ) {
        Date date = new Date( endtimeToday );
        long hh = date.getHours();
        long  mm = date.getMinutes();
        long endYesterday = endtimeToday - ( hh * 60 * 60 * 1000L );
        endYesterday = endYesterday  - ( mm * 60 * 1000L);
        endYesterday = endYesterday - ( 60000);  // subtract one more minute for seconds
        return endYesterday ;
    }




    /*
        Goal is to have about 240 points in a graph max
        Goal is to have graph points be 1 minute, 5 min, 10 min, or 30 min values;
     */
    public int getMinuteSamplesPerGraphPoint( ) {
        int totalMinutes = 60 * hoursToGraph;  // 120 for 2, 48 = 2880
        if (totalMinutes / 30 > maxPointsInGraph) {
            if (totalMinutes / 20 < maxPointsInGraph) {
                if (totalMinutes / 10 < maxPointsInGraph) {
                    if (totalMinutes / 5 < maxPointsInGraph) {
                        return 1;
                    } else {
                        return  5;
                    }
                } else {
                    return  10;
                }
            } else {
                return 20;
            }
        } else {
            // more than we want but
            return totalMinutes / 30;
        }
    }

    public long getMillisPerPoint(int minutes) {

        return minutes*60000;
    }
}
