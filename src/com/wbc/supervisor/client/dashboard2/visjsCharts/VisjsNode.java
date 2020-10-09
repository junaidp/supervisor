package com.wbc.supervisor.client.dashboard2.visjsCharts;

import java.io.Serializable;

/**
 * Created by JIM on 12/6/2014.
 */
public class VisjsNode implements Serializable, VisjsNodeInterface {
    int id;        // 0
    String label;  // 1
    String title="";  // 2 hover text
    String group="";  // 3
    String color="";  // 4

    public static final String SWITCH = "uswitch";
    public static final String VSWITCH = "vswitch";
    public static final String DEVICE = "udevice";
    public static final String VDEVICE = "vdevice";


    public VisjsNode(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public VisjsNode() {
        this.id = 0;
        this.label = "none";
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void setLabel(String label) {
        this.label = label;
    }


    @Override
    public String toString() {
        return "VisjsNode{" +
                "id=" + id +
                ", label='" + label + '\'' +
                ", title='" + title + '\'' +
                ", group='" + group + '\'' +
                ", color='" + color + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toCsv() {
        return "" + id + "," + label ;
    }

    public String toCsvFull() {
        StringBuilder sb = new StringBuilder();
        sb.append(id);
        sb.append(",");
        sb.append(label);
        sb.append(",");
        sb.append(title);
        sb.append(",");
        sb.append(group);
        sb.append(",");
        sb.append(color);
        return sb.toString();
    }
}
