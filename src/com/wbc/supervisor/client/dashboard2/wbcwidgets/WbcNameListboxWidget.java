package com.wbc.supervisor.client.dashboard2.wbcwidgets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.shared.IpAddress;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Junaid on 7/25/14.
 */
public class WbcNameListboxWidget extends ScrollPanel {

    private VerticalPanel vpnl = new VerticalPanel();




    private ArrayList<String> name1List = new ArrayList<String>();  // a period separated string containing the name1 string and the network id
    private ArrayList<WbcNameWidget> wbcNameWidgets = new ArrayList<WbcNameWidget>();

    public ArrayList<WbcNameWidget> getWbcNameWidgets() {
        return wbcNameWidgets;
    }

    public void setWbcNameWidgets(ArrayList<WbcNameWidget> wbcNameWidgets) {
        this.wbcNameWidgets = wbcNameWidgets;
    }

    public WbcNameListboxWidget(){

        setSize("100%", "100%"); // move it to class level, So size can be changed from outside the class. Height Need to be fixed,
        //otherwise scroll panel will not be visible, can be changed to Browser Height at run time from the code
        VerticalPanel vpnlMain = new VerticalPanel();
        vpnlMain.setSize("100%", "100%");
        vpnl.setWidth("100%");
        add(vpnlMain);
        vpnlMain.clear();
        vpnlMain.addStyleName("intravueLight");
        vpnlMain.setHeight(Window.getClientHeight()-134+"px");
        vpnlMain.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
        vpnlMain.add(vpnl);

    }

    public void addNameWidget( WbcNameWidget nameWidget) {
        vpnl.add( nameWidget);
        nameWidget.setWidth("100%");
        name1List.add(nameWidget.getName1() +"."+ nameWidget.getId());
        wbcNameWidgets.add(nameWidget);
    }


    //  need method to get currently selected name widget
    // one to get int of widget in list
    // one to get the name widget that is selected


    // sorting
    // need method to sort widgets based on rank of each widget


    // An external method may want to change the 'sort order' of the widgets
    // e.g.
	/*
    public void sortByName1() {
        create a sortable array of strings
        for each widget, get the name1 value and put in array along with its id in the list
        sort the names
        change the value of rank in each name widget
        clear the list in the scroll panel
        put the name widgets back in the list in the right order
    }
	 */
    public void sortByName(){
        Collections.sort(name1List);
        vpnl.clear();
        for(int i=0; i< wbcNameWidgets.size(); i++){
            for(int j=0; j< wbcNameWidgets.size(); j++){
                String id = name1List.get(i).substring(name1List.get(i).indexOf(".")+1, name1List.get(i).length());
                if(Integer.parseInt(id)  == wbcNameWidgets.get(j).getId()){
                    wbcNameWidgets.get(j).setRank(i);
                    vpnl.add(wbcNameWidgets.get(j));
                }
            }
        }
    }

    public void sortByName( boolean useName1 ) {
        HashMap<String, WbcNameWidget> tempMap = new HashMap<String, WbcNameWidget>();
        for( WbcNameWidget name : wbcNameWidgets ) {
            if ( useName1) tempMap.put( name.getName1(), name );
            else tempMap.put( name.getName2(), name );
        }
        List<String> nameList = new ArrayList<String>( tempMap.keySet());
        Collections.sort(nameList);
        vpnl.clear();
        int rank = 1;
        for( String nameKey : nameList ) {
            WbcNameWidget widget = tempMap.get( nameKey);
            widget.setRank( rank++);
            vpnl.add( widget);
        }
    }

    public void sortByIpaddress( boolean useName1 ) {
        HashMap<IpAddress, WbcNameWidget> tempMap = new HashMap<IpAddress, WbcNameWidget>();
        for( WbcNameWidget name : wbcNameWidgets ) {
            if ( useName1) tempMap.put( new IpAddress(name.getName1()), name );
            else tempMap.put( new IpAddress(name.getName2()), name );
        }
        List<IpAddress> ipList = new ArrayList<IpAddress>( tempMap.keySet());
        Collections.sort(ipList);
        vpnl.clear();
        int rank = 1;
        for( IpAddress key : ipList ) {
            WbcNameWidget widget = tempMap.get( key);
            widget.setRank( rank++);
            vpnl.add( widget);
        }
    }

    /**
     *
     * @param orderMap - a HashMap of two Integers, the key is the sort number, the value is the id of the WbcName widget
     */
    public void sortByHashmap( HashMap<Integer, Integer> orderMap ) {
        HashMap<Integer, WbcNameWidget> tempMap = new HashMap<Integer, WbcNameWidget>();
        for( WbcNameWidget name : wbcNameWidgets ) {
            tempMap.put( name.getId(), name );
        }
        vpnl.clear();
        for( Integer id : orderMap.keySet()) {
            WbcNameWidget widget = tempMap.get(id);
            widget.setRank( orderMap.get(id));
            vpnl.add( widget );
        }
    }

    public String toString1() {
        StringBuilder sb = new StringBuilder();
        sb.append( "Size " + wbcNameWidgets.size() );
        for ( WbcNameWidget wbcName : wbcNameWidgets ) {
            sb.append( " ");
            sb.append( wbcName.getName1() );
        }
        return sb.toString();
    }

    public String toString2() {
        StringBuilder sb = new StringBuilder();
        sb.append( "Size " + wbcNameWidgets.size() );
        for ( WbcNameWidget wbcName : wbcNameWidgets ) {
            sb.append( " ");
            sb.append( wbcName.getName2() );
        }
        return sb.toString();
    }


}

