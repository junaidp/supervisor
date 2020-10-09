package com.wbc.supervisor.client.dashboard2.mainTabPanelEastPanels.TimeBasedPanels;

import com.google.gwt.user.client.ui.VerticalPanel;
import com.wbc.supervisor.client.dashboard2.dashBoardWidgets.WbcStatsDisplayWidget;
import com.wbc.supervisor.shared.StatsConstants;
import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.logging.Logger;

/**
 * Created by Junaid on 9/16/14.
 */
public class StatsBarChartControlPanel extends VerticalPanel {

    WbcStatsDisplayWidget pingOver ;
    WbcStatsDisplayWidget pingFail ;
    WbcStatsDisplayWidget bwOver ;
    WbcStatsDisplayWidget ipChange ;
    WbcStatsDisplayWidget macChange ;
    WbcStatsDisplayWidget move ;
    WbcStatsDisplayWidget linkSpeed ;
    WbcStatsDisplayWidget disconnect ;
    WbcStatsDisplayWidget enetip ;
    int networkid;
    private Logger logger = Logger.getLogger( "StatsBarchartControlPanel" );

    public StatsBarChartControlPanel(){

        pingOver = new WbcStatsDisplayWidget( "Ping Over", "pingOverBackground",  StatsConstants.SERIES_INDEX_PT);
        pingFail = new WbcStatsDisplayWidget( "Ping Fail", "pingOverBackground",  StatsConstants.SERIES_INDEX_PF);
        bwOver = new WbcStatsDisplayWidget( "Bandwidth", "bwOverBackground",  StatsConstants.SERIES_INDEX_BW);
        ipChange = new WbcStatsDisplayWidget( "IP Change", "ipChangeBackground",  StatsConstants.SERIES_INDEX_IP);
        macChange = new WbcStatsDisplayWidget( "MAC Change", "macChangeBackground",  StatsConstants.SERIES_INDEX_MAC);
        move = new WbcStatsDisplayWidget( "Move", "moveBackground",  StatsConstants.SERIES_INDEX_MOVE);
        linkSpeed = new WbcStatsDisplayWidget( "Link Speed", "linkSpeedBackground",  StatsConstants.SERIES_INDEX_LINK);
        disconnect = new WbcStatsDisplayWidget( "Disconnections", "disconnectedBackground",  StatsConstants.SERIES_INDEX_DISC);
        enetip = new WbcStatsDisplayWidget( "Enet/IP Change", "enetipBackground",  StatsConstants.SERIES_INDEX_ENET);
        //TODO JWM - add stat for connections, not just disconnections


        add(pingOver);
        //note we do not collect ping fail yet
        add(bwOver);
        add(ipChange);
        add(macChange);
        add(move);
        add(linkSpeed);
        add(disconnect);
        add(enetip);
    }


    public void updateCountsForTypes( MultiSeriesTimebasedChartDTO dto ) {
        boolean debug = false;
        LinkedHashMap<String, ArrayList<Number>> typesByIpMap = dto.getData();
        int numTypes = 9;
        int[] totalIpsForType = new int[9];
        for (int i = 0; i < numTypes; i++) {
            totalIpsForType[i] = 0;
        }
        for (String ip : typesByIpMap.keySet()) {
            ArrayList<Number> ipValues = typesByIpMap.get(ip);
            for (int i = 0; i < numTypes; i++) {
                Number xval = ipValues.get(i);
                if ((Integer) xval > 0) {
                    totalIpsForType[i]++;
                    if (debug)  System.out.print("IP " + ip + " i " + i + "  " +   xval + "\r\n");
                }
            }
            if (debug) {
                System.out.print("StatsBarChartControlPanel: IP " + ip );
                for (int i = 0; i < numTypes; i++) {
                    System.out.print(" i= " + totalIpsForType[i] );
                }
                System.out.print("\r\n" );
            }
        }
        if (debug) {
            for (int i = 0; i < numTypes; i++) {
                logger.info("StatsBarChartControlPanel: offset " + i + " = " + totalIpsForType[i]);
            }
        }
        pingOver.setCountText(totalIpsForType[StatsConstants.SERIES_INDEX_PT]);
        pingFail.setCountText(totalIpsForType[StatsConstants.SERIES_INDEX_PF]);  // note not collected by events
        bwOver.setCountText(totalIpsForType[StatsConstants.SERIES_INDEX_BW]);
        ipChange.setCountText(totalIpsForType[StatsConstants.SERIES_INDEX_IP]);
        macChange.setCountText(totalIpsForType[StatsConstants.SERIES_INDEX_MAC]);
        move.setCountText(totalIpsForType[StatsConstants.SERIES_INDEX_MOVE]);
       linkSpeed.setCountText(totalIpsForType[StatsConstants.SERIES_INDEX_LINK]);
        disconnect.setCountText(totalIpsForType[StatsConstants.SERIES_INDEX_DISC]);
        enetip.setCountText(totalIpsForType[StatsConstants.SERIES_INDEX_ENET]);
    }

}
