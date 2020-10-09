package com.wbc.supervisor.shared;

/**
 * Created by JIM on 10/23/2014.
 */
public class StatsConstants {

    public static final int CLASS_RESTORED = 1;
    public static final int CLASS_VERSION = 2;
    public static final int CLASS_REGISTRATION = 3;
    public static final int CLASS_TOP_PARENT = 4;
    public static final int CLASS_CONFIG_CHANGED = 5;
    public static final int CLASS_JOIN = 6;
    public static final int CLASS_MOVE_SUBNET = 7;
    public static final int CLASS_SNMP_FOUND = 9;
    public static final int CLASS_ADMIN_VERIFIED = 13;
    public static final int CLASS_SWITCH_REPORTS_MAC = 17;
    public static final int CLASS_DEVICE_CHANGE_MAC = 19;
    public static final int CLASS_DEVICE_CHANGE_IP = 20;
    public static final int CLASS_DELETE_CHILD = 23;
    public static final int CLASS_DELETE_NODE = 25;
    public static final int CLASS_ADMIN_UNVERIFIED = 26;
    public static final int CLASS_BACKUP_CREATED = 27;
    public static final int CLASS_BACKUP_DELETED = 28;
    public static final int CLASS_ADJ_SCANRANGE = 29;
    public static final int CLASS_ADMIN_MOVE = 30;
    public static final int CLASS_STOPPED = 31;
    public static final int CLASS_DELETE_TOP = 32;
    public static final int CLASS_ADD_CHILD = 33;
    public static final int CLASS_MERGING = 34;
    public static final int CLASS_LINK_CHANGE = 35;
    public static final int CLASS_UNVERIFIED = 36;
    public static final int CLASS_MERGE_INFO = 37;
    public static final int CLASS_PING = 101;
    public static final int CLASS_BW = 102;
    public static final int CLASS_CONNECT = 103;
    public static final int CLASS_MOVE = 104;
    public static final int CLASS_SNMP = 105;
    public static final int CLASS_TRAP = 106;
    public static final int CLASS_NAME_CHANGE = 107;
    public static final int CLASS_LINK_CHANGE_OLD = 108;
    public static final int CLASS_ENETIP_CHANGE = 109;

    public static final int SERIES_INDEX_PT = 0;
    public static final int SERIES_INDEX_PF = 1;
    public static final int SERIES_INDEX_BW = 2;
    public static final int SERIES_INDEX_IP = 3;
    public static final int SERIES_INDEX_MAC = 4;
    public static final int SERIES_INDEX_MOVE= 5;
    public static final int SERIES_INDEX_LINK = 6;
    public static final int SERIES_INDEX_DISC = 7;
    public static final int SERIES_INDEX_ENET = 8;

    public StatsConstants() {
    }

    public int  getClassForIndex( int index ) {
        int type = -1;
        switch (index) {
            case SERIES_INDEX_PT:
                type = CLASS_PING;
                break;
            // case SERIES_INDEX_PF:  There is no event of ping failures
            case SERIES_INDEX_BW:
                type = CLASS_BW;
                break;
            case SERIES_INDEX_IP:
                type = CLASS_DEVICE_CHANGE_IP;
                break;
            case SERIES_INDEX_MAC:
                type = CLASS_DEVICE_CHANGE_MAC;
                break;
            case SERIES_INDEX_MOVE:
                type = CLASS_MOVE;
                break;
            case SERIES_INDEX_LINK:
                type = CLASS_LINK_CHANGE;
                break;
            case SERIES_INDEX_DISC:
                type = CLASS_CONNECT;
                break;
            case SERIES_INDEX_ENET:
                type = CLASS_ENETIP_CHANGE;
                break;
        }
        return type;
    }


    /*
    * NOTE the moxie Color does not 'get' its r,g,b, so below can not be used conveniently
    public static final Color pingOverColor     = new Color( java.awt.Color.BLACK.getRed(),   java.awt.Color.BLACK.getGreen(),  java.awt.Color.BLACK.getBlue() );
    public static final Color pingFailColor     = new Color( java.awt.Color.MAGENTA.getRed(),   java.awt.Color.MAGENTA.getGreen(),  java.awt.Color.MAGENTA.getBlue() );
    public static final Color bwOverColor       = new Color( java.awt.Color.PINK.getRed(),   java.awt.Color.PINK.getGreen(),  java.awt.Color.PINK.getBlue() );
    public static final Color ipChangeColor     = new Color( java.awt.Color.BLUE.getRed(),   java.awt.Color.BLUE.getGreen(),  java.awt.Color.BLUE.getBlue() );
    public static final Color macChangeColor    = new Color( java.awt.Color.CYAN.getRed(),   java.awt.Color.CYAN.getGreen(),  java.awt.Color.CYAN.getBlue() );
    public static final Color moveColor         = new Color( java.awt.Color.ORANGE.getRed(),   java.awt.Color.ORANGE.getGreen(),  java.awt.Color.ORANGE.getBlue() );
    public static final Color linkSpeedColor    = new Color( java.awt.Color.YELLOW.getRed(),   java.awt.Color.YELLOW.getGreen(),  java.awt.Color.YELLOW.getBlue() );
    public static final Color disconnectColor   = new Color( java.awt.Color.RED.getRed(),   java.awt.Color.RED.getGreen(),  java.awt.Color.RED.getBlue() );
    public static final Color enetipColor       = new Color( java.awt.Color.GREEN.getRed(),   java.awt.Color.GREEN.getGreen(),  java.awt.Color.GREEN.getBlue() );
     */

    /* color can not be used on client side
    public static final java.awt.Color pingOverColor     = java.awt.Color.BLACK ;
    public static final java.awt.Color pingFailColor     = java.awt.Color.MAGENTA;
    public static final java.awt.Color bwOverColor       = java.awt.Color.PINK;
    public static final java.awt.Color ipChangeColor     = java.awt.Color.BLUE;
    public static final java.awt.Color macChangeColor    = java.awt.Color.CYAN;
    public static final java.awt.Color moveColor         = java.awt.Color.ORANGE;
    public static final java.awt.Color linkSpeedColor    = java.awt.Color.YELLOW;
    public static final java.awt.Color disconnectColor   = java.awt.Color.RED;
    public static final java.awt.Color enetipColor       = java.awt.Color.GREEN;
    */

    public static final int[] pingOverColor     = ColorInfo.black;
    public static final int[] pingFailColor     = ColorInfo.magenta;
    public static final int[] bwOverColor       = ColorInfo.pink;
    public static final int[] ipChangeColor     = ColorInfo.blue ;
    public static final int[] macChangeColor    = ColorInfo.cyan ;
    public static final int[] moveColor         = ColorInfo.orange;
    public static final int[] linkSpeedColor    = ColorInfo.yellow;
    public static final int[] disconnectColor   = ColorInfo.red   ;
    public static final int[] enetipColor       = ColorInfo.green ;

    public static String getStatNameFromIndex( int iType ) {
        String name = "Unknown";
        if (iType == StatsConstants.SERIES_INDEX_PT) {
            name = "Ping Over Threshold";
        } else if (iType == StatsConstants.SERIES_INDEX_BW) {
            name = "Bandwidth Over Threshold";
        } else if (iType == StatsConstants.SERIES_INDEX_IP) {
            name = "IP Address Change";
        } else if (iType == StatsConstants.SERIES_INDEX_MAC) {
            name = "MAC Address Change";
        } else if (iType == StatsConstants.SERIES_INDEX_MOVE) {
            name = "Moves";
        } else if (iType == StatsConstants.SERIES_INDEX_LINK ) {
            name = "Link Speed Change";
        } else if (iType == StatsConstants.SERIES_INDEX_DISC) {
            name = "Disconnection";
        } else if (iType == StatsConstants.SERIES_INDEX_ENET) {
            name = "Ethernet/IP Change";
        } else {
            name = "Unknown ( " + iType + ")" ;
        }
        return name;
    }


}
