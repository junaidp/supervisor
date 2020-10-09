package com.wbc.supervisor.client.dashboard2.DeviceAndTopologyInfo;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.wbc.supervisor.client.dashboard2.dashBoardWidgets.TopologyInfoExpandedPanel;
import com.wbc.supervisor.client.dashboard2.events.MainPanelLoadedEvent;
import com.wbc.supervisor.client.dashboard2.events.MainPanelLoadedEventHandler;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEvent;
import com.wbc.supervisor.client.dashboard2.events.SelectedNetworkChangeEventHandler;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.shared.RpcAnalysisInfo;
import com.wbc.supervisor.shared.DeviceTopologyInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Junaid on 11/9/2014.
 */
public class TopologyInfoPanel extends VerticalPanel {

    int newNetworkId = 0;
    private WbcNameWidget currentNetwork = null;
    private Anchor ancCollapseAll = new Anchor("Collapse All");
    private Anchor ancExpandAll = new Anchor("Expand All");
    private Anchor ancExpand2Level = new Anchor("Expand to 2 Levels");
    private Anchor ancExpand3Level = new Anchor("Expand to 3 Levels");
    private static Logger logger = Logger.getLogger("NetworkTopologyChart.class");
    boolean maintabLoaded = false;
    private VerticalPanel treePanel = new VerticalPanel();
    private RpcAnalysisInfo networkChangeRpcInfo = new RpcAnalysisInfo("TopologyInfoPanel", "Network Change");
    FlexTable table = new FlexTable();

    public TopologyInfoPanel() {
        if (!networkChangeRpcInfo.isReady())
            logger.info("At ctor networkChangeRpcInfo IS NOT READY, s/b all 0 " + networkChangeRpcInfo.showLongTimes());
        clear();
        add(displayOptionsLayout());
        add(treePanel);
//        setWidth("700px");
        setStyleName("intravueLight");
    }

    public void displayTopologyInfoPanel() {

        supervisor.eventBus.addHandler(MainPanelLoadedEvent.TYPE, new MainPanelLoadedEventHandler() {
            @Override
            public void onMainPanelLoaded(MainPanelLoadedEvent event) {
                maintabLoaded = true;
            }
        });

        supervisor.eventBus.addHandler(SelectedNetworkChangeEvent.TYPE, new SelectedNetworkChangeEventHandler() {
            public void onNetworkChange(SelectedNetworkChangeEvent event) {
                WbcNameWidget newNetwork = event.getWbcNameWidget();
                if ((newNetwork != currentNetwork) && maintabLoaded) {
                    logger.log(Level.INFO, "DeviceList: onNetworkChange: NEW network " + newNetwork.getName1());
                    newNetworkId = newNetwork.getId();
                    currentNetwork = newNetwork;
                    if (!networkChangeRpcInfo.isReady()) {
                        logger.info(networkChangeRpcInfo.getId() + " onNetworkChangeEvent being handled and previous not completed.");
                        // test, show the times
                        logger.info("TEST " + networkChangeRpcInfo.showLongTimes());
                    }
                    networkChangeRpcInfo.setEventReceivedTime(System.currentTimeMillis());
                    getNetworkTreeMap(newNetworkId);
                } else {
                    logger.log(Level.INFO, "DeviceList got network change event but network not changed? " + newNetwork.getName1());
                }
            }
        });
//        getNetworkTreeMap( currentNetworkId );
    }

    private HorizontalPanel displayOptionsLayout() {

        HorizontalPanel hpnlDisplayOptionsLayout = new HorizontalPanel();
        hpnlDisplayOptionsLayout.add(ancCollapseAll);
        hpnlDisplayOptionsLayout.add(ancExpandAll);
        hpnlDisplayOptionsLayout.add(ancExpand2Level);
        hpnlDisplayOptionsLayout.add(ancExpand3Level);
        hpnlDisplayOptionsLayout.setSpacing(7);

        return hpnlDisplayOptionsLayout;
    }

    private void setHandlers(final TreeItem topParentItem) {
        ancExpandAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                expandAll(topParentItem);
            }
        });

        ancCollapseAll.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                collapseAll(topParentItem);
            }
        });

        ancExpand2Level.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                expandto2Levels(topParentItem);
            }
        });

        ancExpand3Level.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                expandto3Levels(topParentItem);
            }
        });

    }

    private void getNetworkTreeMap(int nwid) {
        supervisorServiceAsync dashboard2Service = GWT
                .create(com.wbc.supervisor.client.supervisorService.class);

        networkChangeRpcInfo.setRpcCalledTime(System.currentTimeMillis());
        dashboard2Service.getNetworkDeviceTreeMap(nwid, new AsyncCallback<ArrayList<DeviceTopologyInfo>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("fail getting device list" + caught.getMessage());
            }

            @Override
            public void onSuccess(ArrayList<DeviceTopologyInfo> result) {
                networkChangeRpcInfo.setRpcReturnedTime(System.currentTimeMillis());

                displayDeviceListTree(result);
            }
        });
    }

    /*
            treeNodes is a list of DeviceTopologyInfo
            it contains a parent and a child
            the child is the item being added.
            there will be one node with parent = 1, this is the top and should be added to main
     */
    public void displayDeviceListTree(ArrayList<DeviceTopologyInfo> treeNodes) {
        networkChangeRpcInfo.setActionStartTime(System.currentTimeMillis());
        boolean debug = false;
        Tree deviceListTree = new Tree();
        TreeItem topParentItem = new TreeItem();
        if (currentNetwork != null) {
            topParentItem.setText("Network Name: " + currentNetwork.getName1());
        } else {
            topParentItem.setText("Unnamed Network");
        }
        // in next try, make map of DeviceTopologyInfo by parents
        if (debug) logger.log(Level.INFO, "DEBUG TEST: size of treeNodes: " + treeNodes.size());
        HashMap<Integer, ArrayList<Integer>> treeMap = new HashMap<Integer, ArrayList<Integer>>();
        HashMap<Integer, DeviceTopologyInfo> deviceInfoByChildMap = new HashMap<Integer, DeviceTopologyInfo>(); // place to hold device info
        int lowestParent = Integer.MAX_VALUE;
        for (DeviceTopologyInfo topoInfo : treeNodes) {
            int parent = topoInfo.getParent();
            if (parent < lowestParent) lowestParent = parent;
            int child = topoInfo.getChild();
            if (treeMap.containsKey(parent)) {
                deviceInfoByChildMap.put(child, topoInfo);
                ArrayList<Integer> children = treeMap.get(parent);
                children.add(child);
                if (debug)
                    logger.log(Level.INFO, "DEBUG TEST: parent " + parent + " ADD child " + child + " children size " + children.size());
            } else {
                deviceInfoByChildMap.put(child, topoInfo);
                ArrayList<Integer> children = new ArrayList<Integer>();
                children.add(child);
                treeMap.put(parent, children);
                if (debug) logger.log(Level.INFO, "DEBUG TEST: NEW parent " + parent + " child " + child);
            }
        }
        try {
            if (treeMap != null && treeMap.containsKey(lowestParent)) {
                ArrayList<Integer> children2 = treeMap.get(lowestParent);
                if (debug) logger.log(Level.INFO, "lowest parent - " + lowestParent);
                if (children2 != null) {
                    Map<String, TreeItem> nodeMap = new HashMap<String, TreeItem>();
                    for (Integer child : children2) {
                        if (debug) logger.log(Level.INFO, "          has child " + child);
                        addChildrenToNodeMap(lowestParent, child, topParentItem, children2, treeMap, deviceInfoByChildMap, nodeMap);
                    }
                } else {
                    logger.log(Level.WARNING, "children2 is null");
                }
            } else {
                logger.log(Level.WARNING, "treeMap is null, or does not contain lowest ");
            }
        } catch (Exception ex) {
            logger.log(Level.INFO, "Exception " + ex.getMessage(), ex);
        }

        // Start with child of 1
        // iterate through children, adding tree nodes for all its children
        /*
        if (treeMap.containsKey(1)) { // 1 is special case, top of map

            topParentItem.setState(true);
            addChilrenToTree(topParentItem, 1, treeMap, deviceInfoByParentMap);
        }
        */


        //TODO: can not keep added Trees, must clear previous tree // done below
        deviceListTree.clear();
        treePanel.clear();
        deviceListTree.addItem(topParentItem);
        deviceListTree.setWidth("100%");
        treePanel.add(deviceListTree);
        treePanel.setWidth("100%");


        topParentItem.setState(true);
        deviceListTree.addSelectionHandler(new SelectionHandler<TreeItem>() {
            @Override
            public void onSelection(SelectionEvent<TreeItem> event) {
                Window.alert("Selected Value: "
                        + event.getSelectedItem().getText());
            }
        });

        expandAll(topParentItem);
        setHandlers(topParentItem);
        networkChangeRpcInfo.setActionCompleteTime(System.currentTimeMillis());
        logger.log(Level.INFO, networkChangeRpcInfo.getData());
        networkChangeRpcInfo.reset();  // ALWAYS RESET WHEN COMPLETE OR WILL CAUSE ERROR DISPLAY
    }

    public void expandAll(TreeItem treeItem) {
        treeItem.setState(true);
        for (int i = 0; i < treeItem.getChildCount(); i++) {
            treeItem.getChild(i).setState(true);
            expandAll(treeItem.getChild(i));
        }

    }

    public void collapseAll(TreeItem treeItem) {

        for (int i = 0; i < treeItem.getChildCount(); i++) {
            treeItem.getChild(i).setState(false);
            collapseAll(treeItem.getChild(i));
        }
        treeItem.setState(false);

    }

    public void expandto2Levels(TreeItem treeItem) {
        collapseAll(treeItem);
        treeItem.setState(true);
        for (int i = 0; i < treeItem.getChildCount(); i++) {
            treeItem.getChild(i).setState(true);
        }

    }

    public void expandto3Levels(TreeItem treeItem) {
        collapseAll(treeItem);
        treeItem.setState(true);
        for (int i = 0; i < treeItem.getChildCount(); i++) {
            treeItem.getChild(i).setState(true);
            for (int j = 0; j < treeItem.getChild(i).getChildCount(); j++) {
                treeItem.getChild(i).getChild(j).setState(true);

            }

        }
    }


    //TODO: Remove nodeMap as parameter and in main, this is not used for anything anymore
//    private void addChildrenToNodeMap(int level,
//                                      Integer child,
//                                      TreeItem parentItem,
//                                      ArrayList<Integer> children,
//                                      HashMap<Integer, ArrayList<Integer>> treeMap,
//                                      HashMap<Integer,DeviceTopologyInfo> topoInfoMap,
//                                      Map<String, TreeItem> nodeMap  )
//    {
//        boolean debug = false;
//        if (debug) logger.log(Level.INFO,"level " + level + "                  child " + child + "  " + topoInfoMap.get(child).getIpaddress());
//        for (Integer childOfChild : children) {
//            if (debug) logger.log(Level.INFO,"        child " + child + "  has childOfChild " + childOfChild + "  " + topoInfoMap.get(childOfChild).getIpaddress());
//            TreeItem item = new TreeItem();
//            if ( nodeMap.containsKey( child )) {
//                logger.log(Level.INFO,"        ERROR, should not be a duplicate" );
//            } else {
//                DeviceTopologyInfo topoInfo = topoInfoMap.get(childOfChild);
//                // item.setHTML(  "" + + childOfChild + "  " + topoInfoMap.get(childOfChild).getIpaddress()   );
//                StringBuilder sb = new StringBuilder();
//                if ( topoInfo.getPortno() > 0 ) {
//                    sb.append( "Port " );
//                    sb.append( topoInfo.getPortno());
//                    sb.append("  ");
//                }
//                if ( topoInfo.getUpPort() > 0) {
//                    sb.append("SWITCH ");
//                }
//                sb.append( topoInfo.getIpaddress());
//                sb.append("  ");
//                sb.append( topoInfo.getName());
//                sb.append("  ");
//                if ( topoInfo.getUpPort() > 0) {
//                    sb.append("uplink port ");
//                    sb.append( topoInfo.getUpPort());
//                }
//                item.setHTML(  sb.toString() );
//                if (debug) logger.log(Level.INFO,"  DATA FOR NODE " + sb.toString() );
//                nodeMap.put( childOfChild.toString(), item );
//                parentItem.addItem(item);
//            }
//            ArrayList<Integer> childrenOfChildren = treeMap.get(childOfChild);
//            if (childrenOfChildren != null) {
//                addChildrenToNodeMap( ++level, childOfChild, item, childrenOfChildren, treeMap, topoInfoMap, nodeMap);
//            }
//        }
//    }

    private void addChildrenToNodeMap(int level,
                                      Integer child,
                                      TreeItem parentItem,
                                      ArrayList<Integer> children,
                                      HashMap<Integer, ArrayList<Integer>> treeMap,
                                      HashMap<Integer, DeviceTopologyInfo> topoInfoMap,
                                      Map<String, TreeItem> nodeMap) {
        boolean debug = false;
        if (debug)
            logger.log(Level.INFO, "level " + level + "                  child " + child + "  " + topoInfoMap.get(child).getIpaddress());
        for (Integer childOfChild : children) {
            if (debug)
                logger.log(Level.INFO, "        child " + child + "  has childOfChild " + childOfChild + "  " + topoInfoMap.get(childOfChild).getIpaddress());
            TreeItem item = new TreeItem();
            if (nodeMap.containsKey(child)) {
                logger.log(Level.INFO, "        ERROR, should not be a duplicate");
            } else {
                leftNode(topoInfoMap, nodeMap, debug, childOfChild, item, level);
                parentItem.addItem(item);
                parentItem.setWidth("100%");

            }
            ArrayList<Integer> childrenOfChildren = treeMap.get(childOfChild);
            if (childrenOfChildren != null) {
                addChildrenToNodeMap(++level, childOfChild, item, childrenOfChildren, treeMap, topoInfoMap, nodeMap);
            }

        }
    }

    private void leftNode(HashMap<Integer, DeviceTopologyInfo> topoInfoMap, Map<String, TreeItem> nodeMap, boolean debug, Integer childOfChild, TreeItem item, int level) {
        DeviceTopologyInfo topoInfo = topoInfoMap.get(childOfChild);
        // item.setHTML(  "" + + childOfChild + "  " + topoInfoMap.get(childOfChild).getIpaddress()   );
        StringBuilder sb = new StringBuilder();
        topoInfo.setLevel( level );
        if (topoInfo.getPortno() > 0) {
            sb.append("Port ");
            sb.append(topoInfo.getPortno());
            sb.append("  ");
        }
        if (topoInfo.getUpPort() > 0) {
            sb.append("SWITCH ");
        }
        sb.append(topoInfo.getIpaddress());
        sb.append("  ");
        sb.append(topoInfo.getName());
        sb.append("  ");
        if (topoInfo.getUpPort() > 0) {
            sb.append("uplink port ");
            sb.append(topoInfo.getUpPort());
        }
        TopologyInfoExpandedPanel expandedPanel = new TopologyInfoExpandedPanel(topoInfo);

        final FlowPanel hp = new FlowPanel();
//        HorizontalPanel right = new HorizontalPanel();
//        Button btn = new Button("ss");
//        final HTML htmlLeft = new HTML(sb.toString());
//        hp.add(htmlLeft);
//        hp.add(right);
//        right.add(expandedPanel);
//        hp.setWidth("100%");
//        right.setWidth(Window.getClientWidth()-100+"px");
//        hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
//        right.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
//        right.setWidth("100%");
//        final ArrayList<HTML> widths = new ArrayList<HTML>();
//        widths.add(htmlLeft);
//        item.setWidth(Window.getClientWidth()-100+"px");
        //////////////////////////////////////////////////////////////////////////////////////




//                System.out.println(htmlLeft.getAbsoluteLeft()+"!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        right.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
//        add(btn);

        item.setHTML(  sb.toString() );
//        item.setWidth("100%");
//        final Label lbl = new Label("ssssssssss");
//        hp.add(lbl);
////        hp.setBorderWidth(2);
//        lbl.setWidth("100%");
////        hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
//        lbl.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
//        hp.setWidth("100%");
//        item.setWidget(hp);
//        item.setWidth("100%");
//        hp.setStyleName("border");
//        item.setStyleName("border",true);
//        System.out.println(".........................." + hp.getOffsetWidth());
//        btn.addClickHandler(new ClickHandler() {
//            @Override
//            public void onClick(ClickEvent event) {
//               Window.alert(".........................." + htmlLeft.getOffsetWidth());
//            }
//        });
//
        if (debug) logger.log(Level.INFO, "  DATA FOR NODE " + sb.toString());
        nodeMap.put(childOfChild.toString(), item);

//        Timer t = new Timer() {   //import (com.google.gwt.user.client.Timer)
//            @Override
//            public void run() {
//                getPosition(lbl.getElement());
//
//            }
//        };
//
//        t.schedule(10000);
    }

//    public native void getPosition(Element vp ) /*-{
//        var element = vp;
//        var xPosition = 0;
//        var yPosition = 0;
//
//        while(element) {
//            xPosition += (element.offsetLeft - element.scrollLeft + element.clientLeft);
//            yPosition += (element.offsetTop - element.scrollTop + element.clientTop);
//            element = element.offsetParent;
//        }
//        var position = {x: xPosition, y: yPosition};
//        //alert("The image is located at: " + position.x + ", " + position.y);
//        //alert(element.valueOf());
//    }-*/;


}
