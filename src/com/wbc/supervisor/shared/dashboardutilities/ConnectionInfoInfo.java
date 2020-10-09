package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.HashMap;

public class ConnectionInfoInfo extends UtilGrid implements IsSerializable
{
    private ErrorInfo errorInfo;
    private HashMap<String, ConnectionInfoData> connTable;

    public ConnectionInfoInfo() {
        connTable = new HashMap<String, ConnectionInfoData>();
        errorInfo = new ErrorInfo();
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public HashMap<String, ConnectionInfoData> getConnTable() {
        return connTable;
    }

    public void setConnTable(HashMap<String, ConnectionInfoData> connTable) {
        this.connTable = connTable;
    }
}
