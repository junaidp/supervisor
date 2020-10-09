package com.wbc.supervisor.client.dashboard2.graphics.chart;

//TODO remove this as import so this is not dependant on highcharts

//import org.moxieapps.gwt.highcharts.client.DateTimeLabelFormats;

import java.util.ArrayList;

/**
 * Created by Junaid on 8/11/14.
 */
public interface WbcChartInterface {

    String getChartName();
    String getChartTitle();
    String getChartSubtitle();
    String getXAxisTitle();
    String getYAxisTitle();
    String getForeground();
    String getBackground();
//    String getSeriesName();

    void setChartName(String name);
    void setChartTitle(String title);
    void setChartSubtitle( String subtitle );
    void setXAxisTitle( String xtitle);
    void setYAxisTitle( String ytitle );
    void setForeground( String colorString );
    void setBackground( String colorString );
//    void setSeriesName( String seriesname );

    void setWidth( int pixelWidth);
    int getWidth();
    void setHeight( int pixelHeight);
    int getHeight();
    void setWidthAndHeight( int pixelWidth, int pixelHeight );
    void setChartType( String type );  // line, bar, ...

    //TODO JWM - change below method so it is not HighChart specific, see imports
    //void setDateTimeFormatXAxis( DateTimeLabelFormats format );

    // Chart  createChart(WbcChartData wbcChartData);
    // allow users of interface to create their own chart types

    ArrayList<WbcSeriesInfo> getSeriesInfo();
    void setSeriesInfo( ArrayList<WbcSeriesInfo> info );
}

