package com.wbc.supervisor.client.dashboard2.graphics.chart;

//import org.moxieapps.gwt.highcharts.client.Color;

import java.io.Serializable;

/**
 * Created by JIM on 10/6/2014.
 */
public class WbcSeriesInfo implements Serializable {
    private String name;
    private String type;
    private int r;
    private int g;
    private int b;
    private String seriesId;
    private int arrayIndex;



    public WbcSeriesInfo() {
    }

    public WbcSeriesInfo(String name, String type, int r, int g, int b, int arrayIndex) {
        this.name = name;
        this.type = type;
        this.r = r;
        this.g = g;
        this.b = b;
        this.seriesId="not assigned";
        this.arrayIndex = arrayIndex;
    }

    public WbcSeriesInfo(String name, String type, int[] colorInts, int arrayIndex) {
        this.name = name;
        this.type = type;
        this.r = colorInts[0];
        this.g = colorInts[1];
        this.b = colorInts[2];
        this.seriesId="not assigned";
        this.arrayIndex = arrayIndex;
    }

    public void setColor( int r, int g, int b ) {
        this.r = r;
        this.g = g;
        this.b = b;
    }
    /*
    public Color getColor() {
        Color color = new Color( r, g, b);
        return color;
    };
    */

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(String seriesId) {
        this.seriesId = seriesId;
    }

    public int getArrayIndex() {
        return arrayIndex;
    }

    public void setArrayIndex(int arrayIndex) {
        this.arrayIndex = arrayIndex;
    }

    @Override
    public String toString() {
        return "WbcSeriesInfo{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", r=" + r +
                ", g=" + g +
                ", b=" + b +
                ", seriesId='" + seriesId + '\'' +
                ", arrayIndex=" + arrayIndex +
                '}';
    }
}
