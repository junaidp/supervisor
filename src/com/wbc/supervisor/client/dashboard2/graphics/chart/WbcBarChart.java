package com.wbc.supervisor.client.dashboard2.graphics.chart;

import java.util.ArrayList;

/**
 * Created by Junaid on 8/11/14.
 */
public class WbcBarChart implements WbcChartData{

    private ArrayList<Number> xPoint;
    private ArrayList<String> yPoint;

    @Override
    public void setXDataPoints(ArrayList<Number> xPoint) {
        this.xPoint = xPoint;

    }

    @Override
    public void setYDataPoints(ArrayList<String> yPoint) {
        this.yPoint = yPoint;
    }

    @Override
    public ArrayList<Number> getXDataPoints() {
        return xPoint;
    }

    @Override
    public ArrayList<String> getYDataPoints() {
        return yPoint;
    }


}

