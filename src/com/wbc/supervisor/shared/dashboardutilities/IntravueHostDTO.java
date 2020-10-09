package com.wbc.supervisor.shared.dashboardutilities;



import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;
import java.util.List;

public class IntravueHostDTO implements IsSerializable {
    private List<IntravueHost> hostlist;
    private ErrorInfo errorInfo;

    public IntravueHostDTO(){
        hostlist = new ArrayList<>();
        errorInfo = new ErrorInfo();
    }

    public List<IntravueHost> getHostlist() {
        return hostlist;
    }

    public void setHostlist(List<IntravueHost> hostlist) {
        this.hostlist = hostlist;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }
}
