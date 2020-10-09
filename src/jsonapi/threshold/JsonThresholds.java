package jsonapi.threshold;



import jsonapi.tree.JsonLink;

import java.util.ArrayList;

public class JsonThresholds {

    private int isError;
    private int isPeak;
    private int id;
    private int parentid;
    private int maxsampleno;
    private JsonLink link;
    private JsonBandwidthInfo bandwidthInfo;
    private String deviceDetailsURI;
    private JsonMaxbandwidthSample maxbandwidthSample;
    private String parentDetailsURI;
    private JsonPingInfo pingInfo;
    private ArrayList<JsonPingSamples> pingSamples;
    private ArrayList<JsonBandwidthSample> bandwidthSample;

    public JsonThresholds() {
        // bandwidthInfo = new ArrayList<JsonBandwidthInfo>();
        // pingInfo = new ArrayList<JsonPingInfo>();
        pingSamples = new ArrayList<JsonPingSamples>();
        bandwidthSample = new ArrayList<JsonBandwidthSample>();
    }

    public int getIsError() {
        return isError;
    }

    public void setIsError(int isError) {
        this.isError = isError;
    }

    public int getIsPeak() {
        return isPeak;
    }

    public void setIsPeak(int isPeak) {
        this.isPeak = isPeak;
    }

    public int getMaxsampleno() {
        return maxsampleno;
    }

    public void setMaxsampleno(int maxsampleno) {
        this.maxsampleno = maxsampleno;
    }

    public JsonLink getLink() {
        return link;
    }

    public void setLink(JsonLink link) {
        this.link = link;
    }

    public JsonBandwidthInfo getBandwidthInfo() {
        return bandwidthInfo;
    }

    public void setBandwidthInfo(JsonBandwidthInfo bandwidthInfo) {
        this.bandwidthInfo = bandwidthInfo;
    }

    /*
    public ArrayList<JsonBandwidthInfo> getBandwidthInfo() {
        return bandwidthInfo;
    }

    public void setBandwidthInfo(ArrayList<JsonBandwidthInfo> bandwidthInfo) {
        this.bandwidthInfo = bandwidthInfo;
    }

     */

    public String getDeviceDetailsURI() {
        return deviceDetailsURI;
    }

    public void setDeviceDetailsURI(String deviceDetailsURI) {
        this.deviceDetailsURI = deviceDetailsURI;
    }

    public JsonMaxbandwidthSample getMaxbandwidthSample() {
        return maxbandwidthSample;
    }

    public void setMaxbandwidthSample(JsonMaxbandwidthSample maxbandwidthSample) {
        this.maxbandwidthSample = maxbandwidthSample;
    }



    public int getParentid() {
        return parentid;
    }

    public void setParentid(int parentid) {
        this.parentid = parentid;
    }

    public String getParentDetailsURI() {
        return parentDetailsURI;
    }

    public void setParentDetailsURI(String parentDetailsURI) {
        this.parentDetailsURI = parentDetailsURI;
    }

    public JsonPingInfo getPingInfo() {
        return pingInfo;
    }

    public void setPingInfo(JsonPingInfo pingInfo) {
        this.pingInfo = pingInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<JsonPingSamples> getPingSamples() {
        return pingSamples;
    }

    public void setPingSamples(ArrayList<JsonPingSamples> pingSamples) {
        this.pingSamples = pingSamples;
    }

    public ArrayList<JsonBandwidthSample> getBandwidthSample() {
        return bandwidthSample;
    }

    public void setBandwidthSample(ArrayList<JsonBandwidthSample> bandwidthSample) {
        this.bandwidthSample = bandwidthSample;
    }
}
