package com.wbc.supervisor.shared.dashboardutilities.switchprobe;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GeneralInfoImpl implements IsSerializable
{

    private long filesize;
    private boolean isComplete;
    private boolean hasInterfaces;
    private boolean hasMacTable;
    private boolean isRouter;
    private String probeVersion;
    private String switchIp;
    private String communityUsed;
    private String systemName;
    private String location;
    private String description;
    private String defaultGateway;
    private int numIPs;
    private int numIfs;
    private int numPorts;
    private int numVLANs;
    private int numArps;
    private int numPortForMac;
    private int numDuplicateMacs;

    public int getNumDuplicateMacs() {
        return numDuplicateMacs;
    }

    public GeneralInfoImpl() {
    }

    public boolean isRouter() {
        return isRouter;
    }

    public void setRouter(boolean router) {
        isRouter = router;
    }

    public long getFilesize() {
        return filesize;
    }

    public void setFilesize(long filesize) {
        this.filesize = filesize;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public boolean isHasInterfaces() {
        return hasInterfaces;
    }

    public void setHasInterfaces(boolean hasInterfaces) {
        this.hasInterfaces = hasInterfaces;
    }

    public boolean isHasMacTable() {
        return hasMacTable;
    }

    public void setHasMacTable(boolean hasMacTable) {
        this.hasMacTable = hasMacTable;
    }

    public String getProbeVersion() {
        return probeVersion;
    }

    public void setProbeVersion(String probeVersion) {
        this.probeVersion = probeVersion;
    }

    public String getSwitchIp() {
        return switchIp;
    }

    public void setSwitchIp(String switchIp) {
        this.switchIp = switchIp;
    }

    public String getCommunityUsed() {
        return communityUsed;
    }

    public void setCommunityUsed(String communityUsed) {
        this.communityUsed = communityUsed;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultGateway() {
        return defaultGateway;
    }

    public void setDefaultGateway(String defaultGateway) {
        this.defaultGateway = defaultGateway;
    }

    public int getNumIPs() {
        return numIPs;
    }

    public void setNumIPs(int numIPs) {
        this.numIPs = numIPs;
    }

    public int getNumIfs() {
        return numIfs;
    }

    public void setNumIfs(int numIfs) {
        this.numIfs = numIfs;
    }

    public int getNumPorts() {
        return numPorts;
    }

    public void setNumPorts(int numPorts) {
        this.numPorts = numPorts;
    }

    public int getNumVLANs() {
        return numVLANs;
    }

    public void setNumVLANs(int numVLANs) {
        this.numVLANs = numVLANs;
    }

    public int getNumArps() {
        return numArps;
    }

    public void setNumArps(int numArps) {
        this.numArps = numArps;
    }

    public int getNumPortForMac() {
        return numPortForMac;
    }

    public void setNumPortForMac(int numPortForMac) {
        this.numPortForMac = numPortForMac;
    }
}
