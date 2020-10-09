package com.wbc.supervisor.client.dashboard2.DeviceAndTopologyInfo;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Junaid on 2/22/2015.
 */
public class ExpandedTopologyInfoPanel extends VerticalPanel{
    private VerticalPanel treePanel = new VerticalPanel();


    public ExpandedTopologyInfoPanel(){
        controlPanel();
        Tree tree = new Tree();
        TreeItem topParentItem = new TreeItem();
        topParentItem.setText("Top Parent");
        tree.addItem(topParentItem);
        treePanel.add(tree);
        add(tree);
    }

    public void controlPanel(){
        ListBox listControl = new ListBox();
        listControl.addItem("KPI Incident");
        add(listControl);
    }
}
