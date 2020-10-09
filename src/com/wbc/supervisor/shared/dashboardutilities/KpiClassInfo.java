package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

public class KpiClassInfo implements IsSerializable
{

    private ArrayList<Integer> excludedClasses;
    private ErrorInfo errorInfo;

    public KpiClassInfo() {
        errorInfo = new ErrorInfo();
        excludedClasses = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getExcludedClasses() {
        return excludedClasses;
    }

    public void setExcludedClasses(ArrayList<Integer> excludedClasses) {
        this.excludedClasses = excludedClasses;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }
}
