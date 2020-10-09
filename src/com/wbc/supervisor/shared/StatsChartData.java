package com.wbc.supervisor.shared;
/*
public class StatsChartData implements WbcChartData
{

    public static final java.awt.Color pingOverColor     = java.awt.Color.BLACK ;
    public static final java.awt.Color pingFailColor     = java.awt.Color.MAGENTA;
    public static final java.awt.Color bwOverColor       = java.awt.Color.PINK;
    public static final java.awt.Color ipChangeColor     = java.awt.Color.BLUE;
    public static final java.awt.Color macChangeColor    = java.awt.Color.CYAN;
    public static final java.awt.Color moveColor         = java.awt.Color.ORANGE;
    public static final java.awt.Color linkSpeedColor    = java.awt.Color.YELLOW;
    public static final java.awt.Color disconnectColor   = java.awt.Color.RED;
    public static final java.awt.Color enetipColor       = java.awt.Color.GREEN;

	ArrayList<Number> statValues;
	ArrayList<String> sampleValues;
	@Override
	public void setXDataPoints(ArrayList<Number> xPoint) {
//		statValues.addAll( xPoint );
		statValues = xPoint ;
	}

	@Override
	public void setYDataPoints(ArrayList<String> ylist) {
//		sampleValues.addAll( yPoint );
		sampleValues = ylist ;
	}

	@Override
	public ArrayList<Number> getXDataPoints() {
		return statValues;
	}

	@Override
	public ArrayList<String> getYDataPoints() {
		return sampleValues;
	}

    public ArrayList<Number> getYDataMillis() {
		// For highchart AXIS.TYPE.DATE_TIME is milliseconds, like java's
		ArrayList<Number> millis = new ArrayList<Number>( sampleValues.size());
		for ( Number x : sampleValues ) {
			millis.add( x.longValue() * 60000L );
		}
		return millis;
	}

    public long getMinXvalue() {
        long min = Long.MAX_VALUE ;
        for (String sampleValue : sampleValues) {
            long sample = Long.parseLong( sampleValue );
            if ( sample < min) min = sample;
        }
        return min;
    }
    //TODO Add clear() method
    //TODO add update() method
}

*/