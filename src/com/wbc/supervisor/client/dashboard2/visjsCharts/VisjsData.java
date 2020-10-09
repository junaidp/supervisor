package com.wbc.supervisor.client.dashboard2.visjsCharts;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

/**
 * Created by JIM on 1/9/2015.
 */
public class VisjsData implements IsSerializable {
    ArrayList<VisjsNode> visjsNodes = new ArrayList<VisjsNode>();
    ArrayList<VisjsConnection> visjsConnections = new ArrayList<VisjsConnection>();

    public VisjsData(ArrayList<VisjsConnection> visjsConnections, ArrayList<VisjsNode> visjsNodes) {
        this.visjsConnections = visjsConnections;
        this.visjsNodes = visjsNodes;
    }

    public VisjsData() {
    }

    public ArrayList<VisjsNode> getVisjsNodes() {
        return visjsNodes;
    }

    public void setVisjsNodes(ArrayList<VisjsNode> visjsNodes) {
        this.visjsNodes = visjsNodes;
    }

    public ArrayList<VisjsConnection> getVisjsConnections() {
        return visjsConnections;
    }

    public void setVisjsConnections(ArrayList<VisjsConnection> visjsConnections) {
        this.visjsConnections = visjsConnections;
    }
}
