package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.HashMap;

public class IntravueSwitchInfoInfo implements IsSerializable {

    private ErrorInfo errorInfo;
    private HashMap<String, IntravueSwitchInfoData> switchTable;

    public HashMap<String, IntravueSwitchInfoData> getSwitchTable() {
        return switchTable;
    }

    public void setSwitchTable(HashMap<String, IntravueSwitchInfoData> switchTable) {
        this.switchTable = switchTable;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public IntravueSwitchInfoInfo() {
        switchTable = new HashMap<String, IntravueSwitchInfoData>();
        errorInfo = new ErrorInfo();
    }

}
