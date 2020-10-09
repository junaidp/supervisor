package com.wbc.supervisor.client.dashboard2.visjsCharts;

import java.io.Serializable;

/**
 * Created by JIM on 12/6/2014.
 */
public class VisjsConnection implements Serializable, VisjsConnectionInterface {
    int from;  // 0
    int to;  // 1
    String color="";  // 2
    int width=1;      // 3
    String style="";  // 4
    int length=0;     // 5


    public VisjsConnection(int from, int to) {
        this.from = from;
        this.to = to;
    }

    public VisjsConnection() {
        this.from = 0;
        this.to = 0;
    }

    @Override
    public int getTo() {
        return to;
    }

    @Override
    public void setTo(int to) {
        this.to = to;
    }

    @Override
    public int getFrom() {
        return from;
    }

    @Override
    public void setFrom(int from) {
        this.from = from;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }


    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "VisjsConnection{" +
                "from=" + from +
                ", to=" + to +
                ", color='" + color + '\'' +
                ", width=" + width +
                ", style='" + style + '\'' +
                ", length=" + length +
                '}';
    }

    public String toCsv() {
        return "" + from + "," + to ;
    }

    public String toCsvFull() {
        StringBuilder sb = new StringBuilder();
        sb.append(from);
        sb.append(",");
        sb.append(to);
        sb.append(",");
        sb.append(color);
        sb.append(",");
        sb.append(width);
        sb.append(",");
        sb.append(style);
        sb.append(",");
        sb.append(length);
        return sb.toString();
    }

}
