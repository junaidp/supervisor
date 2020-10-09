package com.wbc.supervisor.server;

import com.wbc.supervisor.client.dashboard2.visjsCharts.VisjsConnection;
import com.wbc.supervisor.client.dashboard2.visjsCharts.VisjsData;
import com.wbc.supervisor.client.dashboard2.visjsCharts.VisjsNode;
import com.wbc.supervisor.shared.DeviceInfoDTO;
import com.wbc.supervisor.shared.DeviceTopologyInfo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by JIM on 12/31/2014.
 */
public class TopologyHelper {


    public static VisjsData getTopologyAsVisjsData( ArrayList<DeviceTopologyInfo> topoList, HashMap<Integer, DeviceInfoDTO> deviceidInfoMap ) {
        VisjsData visData = new VisjsData();
        boolean debug = false;
        ArrayList<VisjsNode> visjsNodes = new ArrayList<VisjsNode>();
        ArrayList<VisjsConnection> visjsConnections = new ArrayList<VisjsConnection>();

        // the following is EXACTLY the same as in DeviceList
        HashMap<Integer, ArrayList<Integer>> treeMap = new HashMap<Integer, ArrayList<Integer>>();
        HashMap<Integer,DeviceTopologyInfo> deviceInfoByChildMap = new HashMap<Integer, DeviceTopologyInfo>(); // place to hold device info
        int lowestParent = Integer.MAX_VALUE;
        for (DeviceTopologyInfo topoInfo : topoList) {
            int parent = topoInfo.getParent();
            if (parent < lowestParent) lowestParent = parent;
            int child = topoInfo.getChild();
            if (treeMap.containsKey( parent)) {
                deviceInfoByChildMap.put(child, topoInfo);
                ArrayList<Integer> children = treeMap.get(parent);
                children.add(child);
                if (debug) System.out.println("DEBUG TEST: parent " + parent + " ADD child " + child + " children size " + children.size());
            } else {
                deviceInfoByChildMap.put(child,topoInfo);
                ArrayList<Integer> children = new ArrayList<Integer>();
                children.add(child);
                treeMap.put(parent,children);
                if (debug) System.out.println("DEBUG TEST: NEW parent " + parent + " child " + child);
            }
        }
        // END of DeviceList copy
        // This is SIMILAR to DeviceList, but putting into different structure
        try {
            ArrayList<Integer> children2 = treeMap.get(lowestParent);
            for (Integer child : children2) {
                if (debug) System.out.println("          has child " + child);
                createVisjsData(lowestParent, child, children2, treeMap, deviceInfoByChildMap, visjsNodes, visjsConnections, deviceidInfoMap );
            }
            // TEST, add node 1
            VisjsNode node = new VisjsNode();
            node.setId(1);
            node.setLabel("SCANNER");
            node.setColor("black");
            node.setGroup("scanner");
            node.setTitle("This node represents the IntraVUE scanner - all UPLINK PORTS lead to or face here");
            visjsNodes.add(node);
        }
        catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
        }

        if (debug) {
            for (VisjsNode visjsNode : visjsNodes) {
                System.out.println( visjsNode.toString());
            }
            for (VisjsConnection visjsConnection : visjsConnections) {
                System.out.println( visjsConnection.toString());
            }
        }
        visData.setVisjsNodes( visjsNodes);
        visData.setVisjsConnections(visjsConnections);
        return visData;
    }

    private static void createVisjsData(int level,
                                        Integer child,
                                        ArrayList<Integer> children,
                                        HashMap<Integer, ArrayList<Integer>> treeMap,
                                        HashMap<Integer, DeviceTopologyInfo> deviceInfoByChildMap,
                                        ArrayList<VisjsNode> visjsNodes,
                                        ArrayList<VisjsConnection> visjsConnections,
                                        HashMap<Integer,DeviceInfoDTO> deviceidInfoMap
    )
    {
        boolean debug = false;
        boolean debugTitle = false;
        if (debug) System.out.println("level " + level + "                  child " + child + "  " + deviceInfoByChildMap.get(child).getIpaddress() );
        for (Integer childOfChild : children) {
            DeviceTopologyInfo di = deviceInfoByChildMap.get(childOfChild) ;
            if (debug) System.out.println("        child " + child + "  has childOfChild " + childOfChild + "  " + di.getIpaddress());
            String nodeName = di.getIpaddress();
            boolean showNameWithIp = false;
            if (showNameWithIp) {
                if (!di.getName().isEmpty()) {
                    if (!di.getName().startsWith("n/a")) {
                        nodeName = di.getIpaddress() + "\n" + di.getName();
                    }
                }
            }
            VisjsNode node = new VisjsNode( childOfChild, nodeName );

            DeviceInfoDTO deviceInfo = deviceidInfoMap.get( childOfChild );
            String title = createTitle( di, deviceInfo );
            if (debugTitle) System.out.println(di.getIpaddress() + " title > " + title);
            node.setTitle( title );

            boolean useIconImages = false;
            if (useIconImages) {

            } else {
                if (deviceInfo.isSwitch) {
                    if (deviceInfo.isVerified()) {
                        node.setGroup(VisjsNode.VSWITCH);
                    } else {
                        node.setGroup(VisjsNode.SWITCH);
                    }
                } else {
                    if (deviceInfo.isVerified()) {
                        node.setGroup(VisjsNode.VDEVICE);
                    } else {
                        node.setGroup(VisjsNode.DEVICE);
                    }
                }
            }
            //
            visjsNodes.add( node )   ;
            VisjsConnection conn = new VisjsConnection( di.getParent(), di.getChild());
            if ( deviceInfo.isConnected()) {
                conn.setColor( "green");
            } else {
                conn.setColor("red");
            }
            long speed = deviceInfo.getSpeed();
            if ( speed > 10000000 ) {
                conn.setWidth( 256 );
            } else if ( speed > 1000000 ) {
                conn.setWidth( 64 );
            } else if ( speed > 100000 ) {
                conn.setWidth( 16 );
            } else if ( speed > 10000 ) {
                conn.setWidth( 4 );
            } else {
                conn.setWidth( 1 );
            }
            if (deviceInfo.isWirelessDevice) {
                conn.setStyle("dash-line");
            } else {
                conn.setStyle("line");
            }
            visjsConnections.add( conn ) ;
            ArrayList<Integer> childrenOfChildren = treeMap.get(childOfChild);
            if (childrenOfChildren != null) {
                createVisjsData(++level, childOfChild, childrenOfChildren, treeMap, deviceInfoByChildMap, visjsNodes, visjsConnections, deviceidInfoMap );
            }
        }
    }

    private static String createTitle(DeviceTopologyInfo ti, DeviceInfoDTO di) {
        StringBuilder title = new StringBuilder();
        title.append("Name: ");
        title.append( "<b>" );
        title.append( di.getName() );
        title.append( "</b><br>");
        title.append( "Port: " );
        title.append( ti.getPortno() );
        title.append( "<br> ");
        if (ti.getUpPort() > 0) {
            title.append( "Uplink Port: " );
            title.append( ti.getUpPort() );
            title.append( "<br> ");
        }
        title.append( "<br><br>");
        title.append( "  ");
        title.append( "MAC:" );
        title.append( "  ");
        String mac = ti.getMac();
        if (mac==null || mac.isEmpty()) {
            mac = di.getMacAddress();
        }
        title.append( mac );
        title.append( "<br> ");
        title.append( "Location: " );
        title.append( "  ");
        title.append( di.getLocation() );
        title.append( "<br>  ");
        title.append( "UD1: " );
        title.append( "  ");
        title.append( di.getUd1name());
        title.append( "<br>  ");
        title.append( "UD2: " );
        title.append( "  ");
        title.append( di.getUd2name());
        title.append( "<br>  ");
        title.append( "UD3: " );
        title.append( "  ");
        title.append( di.getUd3name());
        title.append( "<br>  ");
        title.append( "Version: " );
        title.append( "  ");
        title.append( di.getUd4name());
        title.append( "<br>  ");
        title.append( "Vendor: " );
        title.append( "  ");
        title.append( di.getUd5name());
        title.append( "<br>  ");
        title.append( "Model: " );
        title.append( "  ");
        title.append( di.getUd6name());
        /*
        StringBuilder escaped = new StringBuilder();
        if (title.toString().contains(",")) {
            escaped.append( StringEscapeUtils.escapeHtml3(title.toString()));
        } else {
            escaped.append(title.toString());
        }
        */

        /*
        return StringEscapeUtils.escapeHtml3(title.toString());
        */
        String titleResult = title.toString();
        if (title.toString().contains(",")) {
            titleResult = title.toString().replace(",", " ");
        }
        return titleResult;

    }


/*
    public static String getTopologyAsJsonString( ArrayList<DeviceTopologyInfo> topoList ) {
        boolean debug = false;
        ArrayList<VisjsNode> visjsNodes = new ArrayList<VisjsNode>();
        ArrayList<VisjsConnection> visjsConnections = new ArrayList<VisjsConnection>();

        // the following is EXACTLY the same as in DeviceList
        HashMap<Integer, ArrayList<Integer>> treeMap = new HashMap<Integer, ArrayList<Integer>>();
        HashMap<Integer,DeviceTopologyInfo> deviceInfoByChildMap = new HashMap<Integer, DeviceTopologyInfo>(); // place to hold device info
        for (DeviceTopologyInfo topoInfo : topoList) {
            int parent = topoInfo.getParent();
            int child = topoInfo.getChild();
            if (treeMap.containsKey( parent)) {
                deviceInfoByChildMap.put(child, topoInfo);
                ArrayList<Integer> children = treeMap.get(parent);
                children.add(child);
                if (debug) System.out.println("DEBUG TEST: parent " + parent + " ADD child " + child + " children size " + children.size());
            } else {
                deviceInfoByChildMap.put(child,topoInfo);
                ArrayList<Integer> children = new ArrayList<Integer>();
                children.add(child);
                treeMap.put(parent,children);
                if (debug) System.out.println("DEBUG TEST: NEW parent " + parent + " child " + child);
            }
        }
        // END of DeviceList copy
        // This is SIMILAR to DeviceList, but putting into different structure
        try {
            ArrayList<Integer> children2 = treeMap.get(1);
            for (Integer child : children2) {
                if (debug) System.out.println("          has child " + child);
                createVisjsData(1, child, children2, treeMap, deviceInfoByChildMap, visjsNodes, visjsConnections );
            }
        }
        catch (Exception ex) {
            System.out.println("Exception " + ex.getMessage());
        }

        if (debug) {
            for (VisjsNode visjsNode : visjsNodes) {
                System.out.println( visjsNode.toString());
            }
            for (VisjsConnection visjsConnection : visjsConnections) {
                System.out.println( visjsConnection.toString());
            }
        }

        JSONObject responseObject = new JSONObject();
        List<JSONObject> jsonVisJsNodesList = new LinkedList<JSONObject>();
        List<JSONObject> jsonVisJsConnectionsList = new LinkedList<JSONObject>();
        try {
            for (VisjsNode visjsNode : visjsNodes) {
                JSONObject jsonVisJs = new JSONObject();
                jsonVisJs.put("id", visjsNode.getId());
                jsonVisJs.put("label", visjsNode.getLabel());
                jsonVisJsNodesList.add(jsonVisJs);
            }

            for (VisjsConnection visjsConnection : visjsConnections) {
                JSONObject jsonVisJsConnection = new JSONObject();
                jsonVisJsConnection.put("from", visjsConnection.getFrom());
                jsonVisJsConnection.put("to", visjsConnection.getTo());
                jsonVisJsConnectionsList.add(jsonVisJsConnection);
            }

            responseObject.put("visjsNodes", jsonVisJsNodesList);
            responseObject.put("visjsConnections", jsonVisJsConnectionsList);
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        return responseObject.toString();

    }
*/



}
