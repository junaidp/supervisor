package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.ArrayList;
import java.util.Date;

public class IntravueHostStatus implements IsSerializable {

    // data from device tree
    private String ivHostStatus;
    private String ivHostStatusColor;
    private String stoppedMessage;
    private String stoppedMessageColor;
    private String ivVersion;

    // data from network info
    private String ipNic1;
    private String ipNic2;
    private String macNic1;
    private String macNic2;

    private int foundIps;
    private String hostDescription;
    private String ScannerSpeed;

    private ArrayList<IvNetworkInfo> networkInfo;
    private Date dateTime;

    public IntravueHostStatus() {
        networkInfo = new ArrayList<>();
    }

    public String getIvHostStatusColor() {
        return ivHostStatusColor;
    }

    public void setIvHostStatusColor(String ivHostStatusColor) {
        this.ivHostStatusColor = ivHostStatusColor;
    }

    public String getStoppedMessageColor() {
        return stoppedMessageColor;
    }

    public void setStoppedMessageColor(String stoppedMessageColor) {
        this.stoppedMessageColor = stoppedMessageColor;
    }

    public String getIvHostStatus() {
        return ivHostStatus;
    }

    public void setIvHostStatus(String ivHostStatus) {
        this.ivHostStatus = ivHostStatus;
    }

    public String getStoppedMessage() {
        return stoppedMessage;
    }

    public void setStoppedMessage(String stoppedMessage) {
        this.stoppedMessage = stoppedMessage;
    }

    public String getIvVersion() {
        return ivVersion;
    }

    public void setIvVersion(String ivVersion) {
        this.ivVersion = ivVersion;
    }

    public String getIpNic1() {
        return ipNic1;
    }

    public void setIpNic1(String ipNic1) {
        this.ipNic1 = ipNic1;
    }

    public String getIpNic2() {
        return ipNic2;
    }

    public void setIpNic2(String ipNic2) {
        this.ipNic2 = ipNic2;
    }

    public String getMacNic1() {
        return macNic1;
    }

    public void setMacNic1(String macNic1) {
        this.macNic1 = macNic1;
    }

    public String getMacNic2() {
        return macNic2;
    }

    public void setMacNic2(String macNic2) {
        this.macNic2 = macNic2;
    }

    public ArrayList<IvNetworkInfo> getNetworkInfo() {
        return networkInfo;
    }

    public void setNetworkInfo(ArrayList<IvNetworkInfo> networkInfo) {
        this.networkInfo = networkInfo;
    }

    public int getFoundIps() {
        return foundIps;
    }

    public void setFoundIps(int foundIps) {
        this.foundIps = foundIps;
    }

    public String getHostDescription() {
        return hostDescription;
    }

    public void setHostDescription(String hostDescription) {
        this.hostDescription = hostDescription;
    }

    public String getScannerSpeed() {
        return ScannerSpeed;
    }

    public void setScannerSpeed(String scannerSpeed) {
        ScannerSpeed = scannerSpeed;
    }

    @Override
    public String toString() {
        return "IntravueHostStatus{" +
                "ivHostStatus='" + ivHostStatus + '\'' +
                ", stoppedMessage='" + stoppedMessage + '\'' +
                ", ivVersion='" + ivVersion + '\'' +
                ", ipNic1='" + ipNic1 + '\'' +
                ", ipNic2='" + ipNic2 + '\'' +
                ", macNic1='" + macNic1 + '\'' +
                ", macNic2='" + macNic2 + '\'' +
                ", foundIps=" + foundIps +
                ", hostDescription='" + hostDescription + '\'' +
                ", ScannerSpeed='" + ScannerSpeed + '\'' +
                ", networkInfo=" + networkInfo +
                '}';
    }

    //XXXXXXXXXXXXXXXXXXXX  INNER CLASSES  XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX



    // keeping this VERY simple for now
    static public class IvNetworkInfo implements IsSerializable {
        private boolean hasAgent;
        private String agentIp;
        private String agentNetgroup;
        private String networkName;
        private String topIP;
        private ArrayList<IvScanRange> scanRanges;


        public IvNetworkInfo() {
            scanRanges = new ArrayList<IvScanRange>();
        }

        public ArrayList<IvScanRange> getScanRanges() {
            return scanRanges;
        }

        public void setScanRanges(ArrayList<IvScanRange> scanRanges) {
            this.scanRanges = scanRanges;
        }

        public boolean isHasAgent() {
            return hasAgent;
        }

        public void setHasAgent(boolean hasAgent) {
            this.hasAgent = hasAgent;
        }

        public String getAgentIp() {
            return agentIp;
        }

        public void setAgentIp(String agentIp) {
            this.agentIp = agentIp;
        }

        public String getAgentNetgroup() {
            return agentNetgroup;
        }

        public void setAgentNetgroup(String agentNetgroup) {
            this.agentNetgroup = agentNetgroup;
        }

        public String getNetworkName() {
            return networkName;
        }

        public void setNetworkName(String networkName) {
            this.networkName = networkName;
        }

        public String getTopIP() {
            return topIP;
        }

        public void setTopIP(String topIP) {
            this.topIP = topIP;
        }


    }

    static public class IvScanRange implements IsSerializable {
        private String fromIP;
        private String toIP;

        public String getFromIP() {
            return fromIP;
        }

        public void setFromIP(String fromIP) {
            this.fromIP = fromIP;
        }

        public String getToIP() {
            return toIP;
        }

        public void setToIP(String toIP) {
            this.toIP = toIP;
        }

    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

}
