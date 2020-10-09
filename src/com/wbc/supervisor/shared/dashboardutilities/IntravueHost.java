package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;

public class IntravueHost implements IsSerializable {

    /*
    NOTE initialize with at leastg an space in string so when parsing the host file there are not lines like ",,,,"
     */
    private String hostip="";
    private String hostname="";
    private String hostEmails="not set";
    private String pk="unknown";
    private String keycode="unknown";
    private String expireDate="unknown";
    private String wbcserverVersion = "unknown";

    public String getHostip() {
        return hostip;
    }

    public void setHostip(String hostip) {
        this.hostip = hostip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getHostEmails() {
        return hostEmails;
    }

    public void setHostEmails(String hostEmails) {
        this.hostEmails = hostEmails;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getKeycode() {
        return keycode;
    }

    public void setKeycode(String keycode) {
        this.keycode = keycode;
    }

    public String getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }

    public String getWbcserverVersion() {
        return wbcserverVersion;
    }

    public void setWbcserverVersion(String wbcserverVersion) {
        this.wbcserverVersion = wbcserverVersion;
    }

    public String toString() {
        return "Hostip " + hostip + ", name " + hostname + ", keycode " + keycode + ", pk " + pk + ", expires " + expireDate.toString() + ", server version " + wbcserverVersion +  ", emails " +  hostEmails ;
    }
}
