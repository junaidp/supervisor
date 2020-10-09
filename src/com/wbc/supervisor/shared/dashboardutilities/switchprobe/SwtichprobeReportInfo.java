package com.wbc.supervisor.shared.dashboardutilities.switchprobe;



import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;

import java.util.ArrayList;

public interface SwtichprobeReportInfo
{

    ErrorInfo getErrorInfo();

    void setErrorInfo( ErrorInfo errorInfo );

    GeneralInfoImpl getGeneralData();

    void setGeneralData( GeneralInfoImpl generalData );

    ArrayList<String> getWarningMessages();

    void setWarningMessages( ArrayList<String> warningMessages );

    ArrayList<String> getErrorMessages();

    void setErrorMessages( ArrayList<String> errorMessages );
}
