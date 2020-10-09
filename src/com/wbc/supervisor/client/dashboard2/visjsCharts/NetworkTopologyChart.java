package com.wbc.supervisor.client.dashboard2.visjsCharts;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.client.dashboard2.events.MainPanelLoadedEvent;
import com.wbc.supervisor.client.dashboard2.events.MainPanelLoadedEventHandler;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEventHandler;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.shared.RpcAnalysisInfo;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junaid on 11/2/2014.
 */
public class NetworkTopologyChart extends VerticalPanel{

    int newNetworkId=0;
    private WbcNameWidget currentNetwork=null;
    final HTMLPanel topoContainer = new HTMLPanel("");
    private static Logger logger = Logger.getLogger("NetworkTopologyChart.class");
    boolean debug = false;
    private boolean maintabLoaded = false;
    boolean logStartup = true;
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo( "NetworkTopologyChart", "Network Change" );


    public NetworkTopologyChart() {
        HorizontalPanel hpnlMain = new HorizontalPanel();
        if (!networkChangeRpcInfo.isReady())logger.info( "At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes() );
        if (debug || logStartup) logger.log(Level.INFO, "NetworkTopologyChart ctor");
//        initWidget(topoContainer);
        supervisor.eventBus.addHandler(MainPanelLoadedEvent.TYPE, new MainPanelLoadedEventHandler() {
            @Override
            public void onMainPanelLoaded(MainPanelLoadedEvent event) {
                maintabLoaded = true;
                if (debug || logStartup) logger.log(Level.INFO, "NetworkTopologyChart maintab loaded event handled");
            }
        });
        add(hpnlMain);
        NetworkVueWestPanel networkVueWestPanel = new NetworkVueWestPanel();
        hpnlMain.add(networkVueWestPanel);
        hpnlMain.add(topoContainer);
        setStyleName("intravueLight");

    }

    public void createNetworkTopologyChart(int currentNetworkId){
        if (debug || logStartup) logger.log(Level.INFO, "NetworkTopologyChart createNetworkTopologyChart, adds networkChange handler");


        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if (newNetwork != currentNetwork && maintabLoaded) {
                    if (debug || logStartup) logger.log(Level.INFO, "NetworkTopologyChart: onNetworkChange: NEW network " + newNetwork.getName1() );
                    newNetworkId = newNetwork.getId();
                    currentNetwork = newNetwork;
                    if (!networkChangeRpcInfo.isReady()) {
                        logger.info( networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed." );
                        // test, show the times
                        logger.info( "TEST " + networkChangeRpcInfo.showLongTimes() );
                    }
                    networkChangeRpcInfo.setEventReceivedTime( System.currentTimeMillis());
                    getNetworkTreeMap( newNetworkId );
                } else if ( maintabLoaded == false ) {
                    if (debug || logStartup) logger.log(Level.INFO, "NetworkTopologyChart: onNetworkChange: NEW network  BUT MAINTTAB NOT LOADED " + newNetwork.getName1() );
                } else {
                    if (debug) logger.log(Level.INFO, "NetworkTopologyChart got network change event but network not changed? " + newNetwork.getName1() );
                }
            }
        });
        getNetworkTreeMap( currentNetworkId);

    }

    private void getNetworkTreeMap( int nwid ) {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);
        topoContainer.clear();

        if (debug || logStartup) logger.log(Level.INFO, "NetworkTopologyChart: getNetworkTreeMap:  before rpc getVisjsData");
        networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());
        dashboard2Service.getVisjsData(nwid, new AsyncCallback<VisjsData>() {
            @Override
            public void onFailure(Throwable caught) {
                logger.log(Level.SEVERE, "NetworkTopologyChart rpc onFailure for getVisjsData");
                Window.alert("fail getVisjsData" + caught.getMessage());
            }

            @Override
            public void onSuccess(VisjsData visData) {
                networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());
                networkChangeRpcInfo.setActionStartTime(System.currentTimeMillis());
                if (debug || logStartup) logger.log(Level.INFO, "NetworkTopologyChart first line in OnSuccess.");
                topoContainer.setSize(Window.getClientWidth() - 160 + "px", Window.getClientHeight() - 140 + "px");
                Window.addResizeHandler(new ResizeHandler() {
                    @Override
                    public void onResize(ResizeEvent event) {
                        topoContainer.setSize(Window.getClientWidth() - 160 + "px", Window.getClientHeight() - 140 + "px");

                    }
                });
                ArrayList<VisjsNode> visjsNodes = visData.getVisjsNodes();
                StringBuilder sbNodes = new StringBuilder();
                boolean showData = false;
                if (showData) logger.log(Level.INFO, "------------------------------------------------" );
                StringBuilder sb = new StringBuilder();
                for (VisjsNode visjsNode : visjsNodes) {
                    sbNodes.append(visjsNode.toCsvFull());
                    sbNodes.append(";");
                    if (showData) sb.append( visjsNode.toString() + "\r\n");
                }
                if (showData) logger.log(Level.INFO, sb.toString() );
                sb = new StringBuilder();
                if (showData) logger.log(Level.INFO, "------------------------------------------------" );
                if (sbNodes.length() > 0) sbNodes.deleteCharAt(sbNodes.length() - 1);
                ArrayList<VisjsConnection> visjsConnections = visData.getVisjsConnections();
                StringBuilder sbConns = new StringBuilder();
                for (VisjsConnection visjsConnection : visjsConnections) {
                    // if (visjsConnection.getFrom() == 1 ) continue; // new data does not have the top parent, 1
                    sbConns.append(visjsConnection.toCsvFull());
                    sbConns.append(";");
                    if (showData) sb.append( visjsConnection.toString() + "\r\n");
                }
                if (showData) logger.log(Level.INFO, sb.toString() );
                if (showData) logger.log(Level.INFO, "------------------------------------------------" );
                if (sbConns.length() > 0) sbConns.deleteCharAt(sbConns.length() - 1);
                // topoContainer.add(new Label("sss"));
                showChart(topoContainer.getElement(), sbNodes.toString(), sbConns.toString());
                if (debug) logger.log(Level.INFO, "NetworkTopologyChart: AFTER call to testChart");
                networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
                logger.log(Level.INFO, networkChangeRpcInfo.getData());
                networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY
            }
        });
    }


    //    public native void testChart(Element vp, JsArray<VisjsNodeJso> nodes ) /*-{
//    public native void testChart(Element vp, ArrayList<String> nodes ) /*-{
    public native void showChart(Element vp , String nodeString, String connectionString ) /*-{
        var container = vp;
        var n = new $wnd.vis.DataSet();
        var e = new $wnd.vis.DataSet();
        var debug = false;
        if ( debug ) console.log("in showChart");

        var nodes = nodeString.split(";");
        if ( debug ) console.log("size of nodes " + nodes.length + " > " + nodeString);
        var arrayLength = nodes.length;
        for (var i = 0; i < arrayLength; i++) {
            // if (debug) console.log("i " + i + "  " + nodes[i]);
            var nodeData = nodes[i].split(",");
            if ( debug ) console.log("test size of nodeData " + nodeData.length + "  > " + nodes[i] );
            var id = nodeData[0];
            var ip = nodeData[1];
            // if ( debug ) console.log("test 1");
            var title = nodeData[2];  // do not use yet
            if ( debug ) console.log("test 2");
            var group = nodeData[3];
            // if ( debug ) console.log("test 3");
            // var color = nodeData[4];
            if ( debug ) console.log("test 4");
            if (true) {
                if ( debug ) console.log("test 5");
                n.add([
                    {id: id, label: ip, group: group, title: title }
                ]);

            } else {
                if ( debug ) console.log("test 6");
                n.add([
                    {id: id, label: ip, group: group, color: color, title: title }
                ]);

            }
            if ( debug ) console.log("end of node " + i );
        }
        if ( debug ) console.log("hello 2" );

        // create an array with edges
        var conns = connectionString.split(";");
        if ( debug ) console.log("size of conns " + conns.length );
        arrayLength = conns.length;
        for (var i = 0; i < arrayLength; i++) {
            //Do something
            var connData = conns[i].split(",");
            if ( debug ) console.log("test size of connData " + connData.length + "  > " + conns[i] );
            var from = connData[0];
            var to = connData[1];
            // if (debug) console.log("i= " + i +  " from " + from + ", to " + to);
            var color = connData[2];
            var width = connData[3];
            var style = connData[4];
            var length = connData[5];
            //if ( length == 0) {
            if (true) {
                e.add([
                    {from: from, to: to, color: color, width: width, style: style }
                ]);
            } else {
                e.add([
                    {from: from, to: to, color: color, width: width, style: style, length: length }
                ]);
            }
            if ( debug ) console.log("end of conn " + i );
        }
        if (false) {
            if ( debug ) console.log("XXXXXXXXXXXXXXXXXXXXXXXXX 2" );
            console.log("size of n = " + n.size  );
            for (var i=0; i<n.size; i++) {
                var n1 = n.get(i);
                if (debug) console.log("n-i= " + i +  "  ----  " + n1.toString() );
            }
            console.log("size of e = " + e.size  );
            for (var i=0; i<e.size; i++) {
                var e1 = e.get(i);
                if (debug) console.log("e-i= " + i +  "  ----  " + e1.toString() );
            }
            if ( debug ) console.log("ZZZZZZZZZZZZZZZZZZZZ" );
        }
        // create a network
        var data = {
            nodes: n,
            edges: e
        };
        var options = {
            stabilize: false,   // stabilize positions before displaying
            nodes: {
                fontColor: '#000000'
            },
            edges: {
                color: '#d3d3d3'
            },
            groups: {
                uswitch: {
                    shape: 'triangle',
                    radius: 200,
                    color: '#FF9900' // orange
                },
                vswitch: {
                    shape: 'triangle',
                    radius: 200,
                    color: '#2B7CE9' // blue
                },
                udevice: {
                    shape: 'ellipse',
                    color: "#FF9900" // orange
                },
                vdevice: {
                    shape: 'ellipse',
                    color: "#2B7CE9" // blue
                },
                scanner: {
                    shape: 'star',
                    radius: 200,
                    color: "#000000"  // black
                }
            }
        };
        var dummyOptions = {};
        new $wnd.vis.Network(container,  data, options);
        if (debug) console.log("end of DrawChart" );
    }-*/;



}


/*
            for (var i = 0; i < nodes.length; i++) {
                var node = nodes[i];
                if (debug) console.log("a-i= " + i + " id " + node.nodesData[0] + ", label " + node.nodesData[1] );
            }
            for (var i = 0; i < conns.length; i++) {
                var conns = conns[i];
                if (debug) console.log("b-i= " + i + " from " + conns.nodesData[0] + ", to " + conns.nodesData[1] );
            }


 */