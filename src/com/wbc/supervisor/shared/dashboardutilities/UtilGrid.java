package com.wbc.supervisor.shared.dashboardutilities;

import com.wbc.supervisor.shared.dashboardutilities.switchprobe.*;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;

//Base class for Grids
public class UtilGrid implements IsSerializable {

    private ArrayList<ArpDataExtended> listArp;
    private ArrayList<InterfaceData> listInterfaceData;
    private ArrayList<PortDataExtended> listPortData;
    private ArrayList<CiscoVlanData> listCiscoVlanData;
    private ArrayList<MacInfo> listMacInfo;
    private ArrayList<IpData> listIpInformation;


    public UtilGrid()
    {

    }

    public ArrayList<IpData> getListIpInformation() {
        return listIpInformation;
    }

    public void setListIpInformation(ArrayList<IpData> listIpInformation) {
        this.listIpInformation = listIpInformation;
    }


    public ArrayList<PortDataExtended> getListPortData() {
        return listPortData;
    }

    public void setListPortData(ArrayList<PortDataExtended> listPortData) {
        this.listPortData = listPortData;
    }

    public ArrayList<CiscoVlanData> getListCiscoVlanData() {
        return listCiscoVlanData;
    }

    public void setListCiscoVlanData(ArrayList<CiscoVlanData> listCiscoVlanData) {
        this.listCiscoVlanData = listCiscoVlanData;
    }

    public ArrayList<MacInfo> getListMacInfo() {
        return listMacInfo;
    }

    public void setListMacInfo(ArrayList<MacInfo> listMacInfo) {
        this.listMacInfo = listMacInfo;
    }

     public ArrayList<InterfaceData> getListInterfaceData() {
        return listInterfaceData;
    }

    public void setListInterfaceData(ArrayList<InterfaceData> listInterfaceData) {
        this.listInterfaceData = listInterfaceData;
    }

    public ArrayList<ArpDataExtended> getListArp() {
        return listArp;
    }

    public void setListArp(ArrayList<ArpDataExtended> listArp) {
        this.listArp = listArp;
    }





}
