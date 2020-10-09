package com.wbc.supervisor.dashboard;

import com.wbc.supervisor.shared.utilities.ThresholdMetrics;

import java.util.ArrayList;

/**
 * Created by JIM on 1/25/2015.
 */
public class ThresholdAnalyzer {

    public static ThresholdMetrics analyzeThresholds ( ArrayList<Number> data ) {
        ThresholdMetrics metrics = new ThresholdMetrics();
        double sum = 0;
        double notZeroSum = 0;
        int    notZeroCount = 0;
        double min = Double.MAX_VALUE;
        double max = 0;
        double minOverZero = Double.MAX_VALUE;
        if (data==null || data.size()==0) return metrics;

        try {
            for (Number aNumber : data) {
                Double aDouble = aNumber.doubleValue();
                sum += aDouble;
                if (aDouble < min) min = aDouble;
                if (aDouble > max) max = aDouble;
                if (aDouble > 0 && aDouble < minOverZero) minOverZero = aDouble;
                if (aDouble > 0 ) {
                    notZeroSum += aDouble;
                    notZeroCount++;
                }

            }
            metrics.setSize(data.size());
            metrics.setsAvg(sum / data.size());
            if (notZeroCount > 0 ) metrics.setNonZeroSavg( notZeroSum /notZeroCount );
            metrics.setMax(max);
            if (min == Double.MAX_VALUE) min = 0;
            metrics.setMin(min);
            if (minOverZero == Double.MAX_VALUE) minOverZero = 0;
            metrics.setMinOverZero(minOverZero);
            metrics.setNumZero( data.size() - notZeroCount );
        } catch ( Exception ex ) {
            System.out.println( "Exception in ThresholdAnalyzer:analyzeThresholds " + ex.getMessage()) ;
        }
        return metrics;
    }
}
