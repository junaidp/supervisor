package com.wbc.supervisor.shared.dashboardutilities.threshold;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.UtilGrid;

import java.util.HashMap;

public class ThresholdPresentationInfo extends UtilGrid implements IsSerializable
{

    private HashMap<String, ThresholdPresentationData> presentationInfoMap;
    private ErrorInfo errorInfo;

    public ThresholdPresentationInfo() {
        presentationInfoMap = new HashMap<>();
        errorInfo = new ErrorInfo();
    }

    public HashMap<String, ThresholdPresentationData> getPresentationInfoMap() {
        return presentationInfoMap;
    }

    public void setPresentationInfoMap(HashMap<String, ThresholdPresentationData> presentationInfoMap) {
        this.presentationInfoMap = presentationInfoMap;
    }

    public ErrorInfo getErrorInfo() {
        return errorInfo;
    }

    public void setErrorInfo(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }
}
