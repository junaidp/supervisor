/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.wbc.supervisor.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

import com.google.gwt.user.client.rpc.IsSerializable;

/**
 *
 * @author intravue
 */
public class DeviceInfoDTO implements IsSerializable{

    public String  ipAddress;
    public String  macAddress;
    public String  netmask;
    public int     networkid;

    public int     childid;                  // descid in ifdesc table
    public int     parentid;                 //  parent record for this childid

    public boolean connected = false;
    public String  name;
    public String  location;
    public String  ud1name;
    public String  ud2name;
    public String  ud3name;
    public String  ud4name;
    public String  ud5name;
    public String  ud6name;
    public String  vendorName = "";
    public boolean verified    = false;
    public boolean isWAP  = false;
    public boolean autoConnect = false;
    public boolean isSwitch    = false;
    public boolean hasRedBox   = false;
    public boolean isWirelessDevice = false;
    public int     criticalType = 0 ;
    public int     speed = 0; // added
    public String notes;


    /*
    public static final int DEVICE_NAME = 0;
    public static final int LOCATION_NAME = 2;
    public static final int UD1_NAME = 3;
    public static final int UD2_NAME = 4;
    public static final int UD3_NAME = 5;
    public static final int UD4_NAME = 6;  // version
    public static final int UD5_NAME = 7;  // vendor
    public static final int UD6_NAME = 8;  // model
    public static final int VENDORMAC_NAME = 101;
    public static final int MAC_ADDRESS = 102;

    public static final int KPI_UNKNOWN = 0;
    public static final int KPI_IGNORE = 1;
    public static final int KPI_CRITICAL_I = 2;  // critical intermittent
    public static final int KPI_CRITICAL_A = 3;  // critical always on
    */

    /* not needed for dto
    public DeviceInfoDTO(String ipaddress, int cid, int pid) {
        this.ipAddress = ipaddress;
        childid = cid;
        parentid = pid;
        name = "";
        macAddress = "undefined";
        networkid=-1;
        netmask="undefined";
        name = "";
        ud1name = "";
        ud2name = "";
        ud3name = "";
        ud4name = "";
        ud5name = "";
        ud6name = "";
        criticalType = 0;
    }
    */

    public DeviceInfoDTO() {
        this.ipAddress = "0.0.0.0";
        childid = -1;
        parentid = -1;
        name = "";
        macAddress = "undefined";
        networkid=-1;
        netmask="undefined";
        location = "";
        ud1name = "";
        ud2name = "";
        ud3name = "";
        ud4name = "";
        ud5name = "";
        ud6name = "";
        criticalType = 0;
        connected = false;
        vendorName = "";
        verified    = false;
        isWAP  = false;
        autoConnect = false;
        isSwitch    = false;
        hasRedBox   = false;
        isWirelessDevice = false;
    }

    /* not needed for dto
    public DeviceInfoDTO(String ipaddress, String macAddress, String netmask, int networkid, int cid, int pid) {
        this.ipAddress = ipaddress;
        this.macAddress = macAddress;
        this.networkid = networkid;
        this.netmask = netmask;
        childid = cid;
        parentid = pid;
        name = "";
        ud1name = "";
        ud2name = "";
        ud3name = "";
        ud4name = "";
        ud5name = "";
        ud6name = "";
        criticalType = 0;
    }
    */

    /*
    public String getNameByType( int type ) {
        String temp = "";
        if ( type==DEVICE_NAME ) {
            temp = name;
        }  else if (type == LOCATION_NAME) {
            temp = location;
        }  else if (type == UD1_NAME) { 
            temp = ud1name;
        }  else if (type == UD2_NAME) { 
            temp = ud2name;
        }  else if (type == UD3_NAME) { 
            temp = ud3name;
        }  else if (type == UD4_NAME) {  // Version
            temp = ud4name;
        }  else if (type == UD5_NAME) {  // Vendor
            temp = ud5name;
            if (temp.isEmpty()) {
                temp = this.vendorName;
            }
        }  else if (type == UD6_NAME) {   // Model
            temp = ud6name;
        }  else if (type == VENDORMAC_NAME) {
            temp = this.vendorName;
        }  else if (type == MAC_ADDRESS) {
            temp = macAddress.replace(" ", ":" );
        }
        return temp;
    }
    */

    // (0=Device, 1=IP, 2=Location, 3=UD1, 4=UD2, 5=Diagnostic)
    public void setName( String name )  {
        this.name = name ;
    }
    public void setLocation( String name )  {
        this.location = name ;
    }
    public void setVendorName(String name) {
      this.vendorName = name;
    }
    /*
    public void setUdName( int udNo, String name )  {
        if ( udNo == 1) {
            ud1name = name ;
        } else if (udNo == 2) {
            ud2name = name ;
        } else if (udNo == 3) {
            ud3name = name ;
        } else if (udNo == 4) {
            ud4name = name ;
        } else if (udNo == 5) {
            ud5name = name ;
        } else if (udNo == 6) {
            ud6name = name ;
        } else {
            System.out.println("setUdName ERROR, invalid number");
        }
    }
    */

    public int getCriticalType() {
        return criticalType;
    }

    public void setCriticalType(int criticalType) {
        this.criticalType = criticalType;
    }

    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        if ( isSwitch ) 
        {
            sb.append( "SWITCH  " );
        } else {   
            sb.append( "IP  " );
        }
        sb.append( ipAddress );
        sb.append( "  " );
        sb.append( name );
        return sb.toString();
    }



    public String toInfoString() 
    {
        StringBuilder sb = new StringBuilder();
        if ( isSwitch ) 
        {
            sb.append( "SWITCH  " );
        } else {   
            sb.append( "IP  " );
        }
        sb.append( ipAddress );
        sb.append( "  " );
        sb.append( name );
        sb.append( " cid " );
        sb.append( childid );
        sb.append( ", pid " );
        sb.append( parentid );
        sb.append( ", mac " );
        sb.append( macAddress );
        sb.append( ", netmask " );
        sb.append(  netmask);
        sb.append(  ", nw " );
        sb.append(  networkid );
        sb.append(  ", connected ");
        sb.append(  connected);
        sb.append(  ", verified ");
        sb.append(  verified);
        sb.append( ", Critical Level = ");
        sb.append( criticalType );
        sb.append( ", speed = ");
        sb.append( speed );
        return sb.toString() ;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getNetmask() {
        return netmask;
    }

    public void setNetmask(String netmask) {
        this.netmask = netmask;
    }

    public int getNetworkid() {
        return networkid;
    }

    public void setNetworkid(int networkid) {
        this.networkid = networkid;
    }

    public int getChildid() {
        return childid;
    }

    public void setChildid(int childid) {
        this.childid = childid;
    }

    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public String getUd1name() {
        return ud1name;
    }

    public void setUd1name(String ud1name) {
        this.ud1name = ud1name;
    }

    public String getUd2name() {
        return ud2name;
    }

    public void setUd2name(String ud2name) {
        this.ud2name = ud2name;
    }

    public String getUd3name() {
        return ud3name;
    }

    public void setUd3name(String ud3name) {
        this.ud3name = ud3name;
    }

    public String getUd4name() {
        return ud4name;
    }

    public void setUd4name(String ud4name) {
        this.ud4name = ud4name;
    }

    public String getUd5name() {
        return ud5name;
    }

    public void setUd5name(String ud5name) {
        this.ud5name = ud5name;
    }

    public String getUd6name() {
        return ud6name;
    }

    public void setUd6name(String ud6name) {
        this.ud6name = ud6name;
    }

    public String getVendorName() {
        return vendorName;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public boolean isWAP() {
        return isWAP;
    }

    public void setWAP(boolean isWAP) {
        this.isWAP = isWAP;
    }

    public boolean isAutoConnect() {
        return autoConnect;
    }

    public void setAutoConnect(boolean autoConnect) {
        this.autoConnect = autoConnect;
    }

    public boolean isSwitch() {
        return isSwitch;
    }

    public void setSwitch(boolean isSwitch) {
        this.isSwitch = isSwitch;
    }

    public boolean isHasRedBox() {
        return hasRedBox;
    }

    public void setHasRedBox(boolean hasRedBox) {
        this.hasRedBox = hasRedBox;
    }

    public boolean isWirelessDevice() {
        return isWirelessDevice;
    }

    public void setWirelessDevice(boolean isWirelessDevice) {
        this.isWirelessDevice = isWirelessDevice;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
