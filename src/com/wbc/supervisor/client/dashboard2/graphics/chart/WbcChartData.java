package com.wbc.supervisor.client.dashboard2.graphics.chart;

import java.util.ArrayList;

/**
 * Created by Junaid on 8/11/14.
 */
public interface WbcChartData {

    void setXDataPoints(ArrayList<Number> xPoint);
    void setYDataPoints(ArrayList<String> yPoint);
    ArrayList<Number> getXDataPoints();
    ArrayList<String> getYDataPoints();
}
