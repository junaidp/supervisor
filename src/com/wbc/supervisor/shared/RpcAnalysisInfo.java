package com.wbc.supervisor.shared;

import com.google.gwt.i18n.client.DateTimeFormat;
import java.util.Date;

/**
 * Created by JIM on 1/22/2015.
 */
public class RpcAnalysisInfo {

    String name;  // calling class
    String description; // use

    // The following may be put into an array class
    long eventReceivedTime = 0;
    long rpcCalledTime= 0;
    long rpcReturnedTime= 0;
    long actionStartTime= 0;
    long actionCompleteTime= 0;

    public RpcAnalysisInfo() {
    }

    public RpcAnalysisInfo(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getEventReceivedTime() {
        return eventReceivedTime;
    }

    public void setEventReceivedTime(long receivedTime) {
        if (eventReceivedTime != 0) {
            System.out.println( "RpcAnalysisInfo ERROR: " + getId() + ", eventReceived being set without reset, possible synchronization error " );
        }
        this.eventReceivedTime = receivedTime;
    }

    public long getRpcCalledTime() {
        return rpcCalledTime;
    }

    public void setRpcCalledTime(long rpcCalledTime) {
        this.rpcCalledTime = rpcCalledTime;
    }

    public long getRpcReturnedTime() {
        return rpcReturnedTime;
    }

    public void setRpcReturnedTime(long rpcReturnedTime) {
        this.rpcReturnedTime = rpcReturnedTime;
    }

    public long getActionStartTime() {
        return actionStartTime;
    }

    public void setActionStartTime(long actionStartTime) {
        this.actionStartTime = actionStartTime;
    }

    public long getActionCompleteTime() {
        return actionCompleteTime;
    }

    public void setActionCompleteTime(long actionCompleteTime) {
        this.actionCompleteTime = actionCompleteTime;
    }

    public void reset() {
        eventReceivedTime = 0;
        rpcCalledTime= 0;
        rpcReturnedTime= 0;
        actionStartTime= 0;
        actionCompleteTime= 0;
    }

    public boolean isReady() {
        long total = eventReceivedTime + rpcCalledTime + rpcReturnedTime + actionStartTime + actionCompleteTime ;
        if ( total == 0) return true;
        return false;
    }


    public String getId() {
        StringBuilder sb = new StringBuilder();
        sb.append( name );
        sb.append("  ");
        sb.append( description );
        return sb.toString();
    }


    public String showLongTimes() {
        return "RpcAnalysisInfo{" +
                "eventReceivedTime=" + eventReceivedTime +
                ", rpcCalledTime=" + rpcCalledTime +
                ", rpcReturnedTime=" + rpcReturnedTime +
                ", actionStartTime=" + actionStartTime +
                ", actionCompleteTime=" + actionCompleteTime +
                '}';
    }

    public String getData() {
        StringBuilder sb = new StringBuilder();
        sb.append("Rpc Data -  ");
        sb.append( name );
        sb.append("  ");
        if(description.contains("NamesPanel"))
        {
            System.out.println("HERE");
        }
        sb.append( description );
        sb.append("  ");
        long timeToGetRpc = rpcReturnedTime - rpcCalledTime;
        long timeToProcess = actionCompleteTime - actionStartTime;
        long totalTime = actionCompleteTime - eventReceivedTime;
        sb.append("rpc ");
        sb.append( timeToGetRpc);
        sb.append(" process ");
        sb.append( timeToProcess);
        sb.append(" total ");
        sb.append( totalTime);
        sb.append(" started ");
        sb.append(  getTimeStampMs(eventReceivedTime ));
        sb.append(" completed ");
        sb.append(  getTimeStampMs(actionCompleteTime ));
        return sb.toString();
    }


    public static String getTimeStampMs( long ltime )
    {

        Date date = new Date( ltime );
        String dateString = date.toString();
        try
        {
            DateTimeFormat fmt = DateTimeFormat.getFormat("HH:mm ss.SSS");
            return fmt.format(date);

        }
        catch( Exception e )
        {
            System.out.println("ERROR getTimeStampMs: Cannot parse \"" + dateString + "\"");
            return dateString;
        }
    }


}
