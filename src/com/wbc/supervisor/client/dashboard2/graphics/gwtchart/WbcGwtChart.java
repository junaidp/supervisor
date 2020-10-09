package com.wbc.supervisor.client.dashboard2.graphics.gwtchart;


import com.wbc.supervisor.client.dashboard2.graphics.chart.WbcChartInterface;
import com.wbc.supervisor.client.dashboard2.graphics.chart.WbcSeriesInfo;

import java.util.ArrayList;

/**
 * Created by Junaid on 2/11/2015.
 */
public class WbcGwtChart implements WbcChartInterface {



    private String chartName = "Intravue";
    private String chartTitle = "Thresh hold data for intravue";
    private String chartSubTitle = "";
    private String chartYAxisTitle = "Y Axis";
    private String chartXAxisTitle = "X Axis";
    private String foregroundColorString = "#000000";
    private String backgroundColorString = "#FFFFFF";

    private int pixelWidth = 150; // 0 means use window size
    private int pixelHeight = 150;
    private int windowOffsetWidth = 500;

    private int minimumYAxisValue = 0;
    private String      chartType;
    // private Series.Type chartType;
    /*private DateTimeLabelFormats dateTimeFormat = new DateTimeLabelFormats()
            .setHour("%H: %M")
            .setMinute("%H: %M");
    */
    private ArrayList<WbcSeriesInfo> seriesInfo = new ArrayList<WbcSeriesInfo>();

    @Override
    public String getChartName() {
        return chartName;
    }

    @Override
    public String getChartTitle() {
        return chartTitle;
    }

    @Override
    public String getChartSubtitle() {
        return chartSubTitle;
    }

    @Override
    public String getXAxisTitle() {
        return chartXAxisTitle;
    }

    @Override
    public String getYAxisTitle() {
        return chartYAxisTitle;
    }

    @Override
    public String getForeground() {
        return foregroundColorString;
    }

    @Override
    public String getBackground() {
        return backgroundColorString;
    }

    @Override
    public void setChartName(String name) {
        this.chartName = name;
    }

    @Override
    public void setChartTitle(String title) {
        this.chartTitle = title;
    }

    @Override
    public void setChartSubtitle(String subtitle) {
        this.chartName = subtitle;
    }

    @Override
    public void setXAxisTitle(String xtitle) {
        this.chartXAxisTitle = xtitle;
    }

    @Override
    public void setYAxisTitle(String ytitle) {
        this.chartYAxisTitle = ytitle;
    }

    @Override
    public void setForeground(String colorString) {
        this.foregroundColorString = colorString;
    }

    @Override
    public void setBackground(String colorString) {
        this.backgroundColorString = colorString;
    }

    @Override
    public void setWidth(int pixelWidth) {
        this.pixelWidth = pixelWidth;
    }

    @Override
    public int getWidth() {
        return pixelWidth;
    }

    public String getWidthString() {
        if (pixelWidth == 0) {
            return "100%";
        } else {
            return "" + pixelWidth + "px";
        }
    }

    @Override
    public void setHeight(int pixelHeight) {
        this.pixelHeight = pixelHeight;
    }

    @Override
    public int getHeight() {
        return pixelHeight;
    }

    public String getHeightString() {
        if (pixelHeight == 0) {
            return "100%";
        } else {
            return "" + pixelHeight + "px";
        }
    }

    @Override
    public void setWidthAndHeight(int pixelWidth, int pixelHeight) {
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
    }

    @Override
    public void setChartType(String type) {
        chartType = type;

    }

    public void setWindowOffsetWidth( int width ) {
        this.windowOffsetWidth = width;
    }

    /*
    public Series.Type getSeriesTypeFromString( String type ){
        Series.Type seriesType = Series.Type.LINE;
        if ( type.toUpperCase().equals("BAR")) {
            seriesType =  Series.Type.BAR;
        }
        return seriesType;
    }

    public Series.Type getChartType() {
        return chartType;
    }
    @Override
    public void setType( String type )  {
        if ( type.toUpperCase().equals("AREA")) {
            // AREA                - Show the series as an area filled in beneath a non-curved line
            chartType = Series.Type.AREA;
        } else if ( type.toUpperCase().equals("AREA_SPLINE")) {
            // AREA_SPLINE         - Show the series as an area filled in beneath a curved line
            chartType = Series.Type.AREA_SPLINE;
        } else if ( type.toUpperCase().equals("BAR")) {
            // BAR                 - Show the series as horizontal bars
            chartType = Series.Type.BAR;
        } else if ( type.toUpperCase().equals("CANDLESTICK")) {
            // CANDLESTICK         - Show the series as a sequence of candlesticks, where each candlestick represents four values.
            chartType = Series.Type.CANDLESTICK;
        } else if ( type.toUpperCase().equals("COLUMN")) {
            // COLUMN              - Show the series as vertical bars
            chartType = Series.Type.COLUMN;
        } else if ( type.toUpperCase().equals("LINE")) {
            // LINE                - Show the series as a sequence of connected straight lines
            chartType = Series.Type.LINE;
        } else if ( type.toUpperCase().equals("OHLC")) {
            // OHLC                - Show the series as a sequence of bars that show the open, high, low, and close values.
            chartType = Series.Type.OHLC;
        } else if ( type.toUpperCase().equals("PIE")) {
            // PIE                 - Show the series as a pie chart
            chartType = Series.Type.PIE;
        } else if ( type.toUpperCase().equals("SCATTER")) {
            // SCATTER             - Show the series as a scatter plot
            chartType = Series.Type.SCATTER;
        } else if ( type.toUpperCase().equals("SPLINE"))  {
            // SPLINE              - Show the series as a sequence of lines that are rendered as a spline to appear as a smooth curve
            chartType = Series.Type.SPLINE;
        } else {
            System.out.println("WbcHighChart: Invalid chart type requested " + type + ", setting line type");
            chartType = Series.Type.LINE;
        }
    }
    public void setDateTimeFormatXAxis( DateTimeLabelFormats format ) {
        //TODO JWM remove DateTimeLabelFormats as input type in interface, too specific to highchart.
        dateTimeFormat = format;
    }
    public DateTimeLabelFormats getDateTimeLabelFormats() { return dateTimeFormat; };
*/

    @Override
    public ArrayList<WbcSeriesInfo> getSeriesInfo() {
        return seriesInfo;
    }

    @Override
    public void setSeriesInfo( ArrayList<WbcSeriesInfo> info) {
        seriesInfo = info;
    }

    public int getMinimumYAxisValue() {
        return minimumYAxisValue;
    }

    public void setMinimumYAxisValue(int minimumYAxisValue) {
        this.minimumYAxisValue = minimumYAxisValue;
    }
}
