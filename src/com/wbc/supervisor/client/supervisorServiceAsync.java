package com.wbc.supervisor.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wbc.supervisor.client.dashboard2.visjsCharts.VisjsData;
import com.wbc.supervisor.client.dashboardutilities.view.login.UserAction;
import com.wbc.supervisor.shared.*;
import com.wbc.supervisor.shared.dashboardutilities.*;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.SwitchProbeReportInfoImpl;
import com.wbc.supervisor.shared.dashboardutilities.threshold.ThresholdPresentationInfo;
import com.wbc.supervisor.shared.dashboardutilities.threshold.ThresholdSetData;
import com.wbc.supervisor.shared.dashboardutilities.threshold.ThresholdType;
import com.wbc.supervisor.shared.dto.DataBasedChartSeriesDTO;
import com.wbc.supervisor.shared.dto.DeviceinfoDataDTO;
import com.wbc.supervisor.shared.dto.DeviceinfoNamesDTO;
import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface supervisorServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);
    void getNetworkInfo( AsyncCallback<ArrayList<WbcNamesDTO>> callback);
    void getPingSummaryData     (int nwid,                       String maxAverage, long oldestSample, long newestSample,  int numSamplesPerPoint, AsyncCallback<MultiSeriesTimebasedChartDTO> callback);
    void getBandwidthSummaryData(int nwid, String thresholdType, String maxAverage, long oldestSample, long newestSample,  int numSamplesPerPoint, AsyncCallback<MultiSeriesTimebasedChartDTO> callback);
    void getStatsBarGraphData   (int nwid, boolean criticalOnly,                    long oldestSample, long newestSample,  int numSamplesPerPoint, AsyncCallback<MultiSeriesTimebasedChartDTO[]> callback);

    void getDetailedPingData(int nwid, String method, int maxDevices,  long oldestSample, long newestSample,  int numSamplesPerPoint, AsyncCallback<ArrayList<DataBasedChartSeriesDTO>> callback);

    void getLastSampleno( AsyncCallback< Long > callback );
    void getNetworkDeviceTreeMap( int nwid , AsyncCallback< ArrayList<DeviceTopologyInfo> > callback );
    void getNetworkDeviceNamesMap( int nwid , AsyncCallback< ArrayList<DeviceinfoNamesDTO> > callback );
    void getConnectionDataForNetwork( int nwid , AsyncCallback<ConnectionDataDTO> callback );

    void getVisjsData(int nwid, AsyncCallback<VisjsData> callback );
    void getNetworkDeviceInfo(int nwid, AsyncCallback<ArrayList<DeviceInfoDTO>> callback);
    void getNetworkDeviceData(int nwid, AsyncCallback<ArrayList<DeviceinfoDataDTO>> callback);
    void updateNetworkDeviceNamesMap(ArrayList<DeviceinfoNamesDTO> updatedList, AsyncCallback<String> callback);
    void getLocalDetails(AsyncCallback<HashMap> callback);

    void callBrowseIntravueNetwork(int nwid, AsyncCallback<String> async);
    void callDeviceStatsDetailsJsp(int currentNetworkId , int statIndexNumber , long hoursToGraph,AsyncCallback<String> async);
    void getServerAddress(AsyncCallback<String> async);
    void callDeviceInfoAndStatDetailsPage(int deviceid, AsyncCallback<String> async);
    void getScannerState(AsyncCallback<Integer> async);
    void getKpiStatsForPeriod(int networkId, long starttime, long endtime,AsyncCallback<ArrayList<KpiStatsRow>> async);
    void getIntravueProperties(AsyncCallback<ArrayList<String>> async);

    ////////////////////////WBCUTILS //////////////////
    void getCrcData( String json, AsyncCallback<SwitchErrorInfo> async ) ;
    void getSwitchReport(String json, String ip, String uploadedFile, AsyncCallback<SwitchProbeReportInfoImpl> async) ;
    void getKpiData(String json, AsyncCallback<KpiClassInfo> async ) ;
    void getConnectionData (String json, AsyncCallback<ConnectionInfoInfo> async);
    void getJson( ArrayList<Integer> list, AsyncCallback<String> async);
    void getDisconnectionData (String json, AsyncCallback<DisconnectionsByDayInfo> async);
    void getThresholdData (String json, ThresholdType thresholdType, AsyncCallback<ThresholdPresentationInfo> async);
    void getErrorText(String json, AsyncCallback<ErrorInfo> async);
    void getRecommendedThresholdsJson(List<ThresholdSetData> list, AsyncCallback<String> async );
    void getIntraVueHostJson( IntravueHost intravueHost, AsyncCallback<String> async);
    void getIntravueHostDTO( String json, AsyncCallback<IntravueHostDTO> async);
    void getProductKeyAndKeycode(String keycode, AsyncCallback<ErrorInfo> async);
    void getExpirationDate(String keycode, AsyncCallback<ErrorInfo> async);
    void validateKeycode(IntravueHost intravueHost, String getVersionUrl, AsyncCallback<ErrorInfo> async);
    void getIntravueHosts(AsyncCallback<IntravueHostDTO> async);
    void saveUpdateDeleteIntravueHost(IntravueHost intravueHost, IntravueHostAction intravueHostAction, AsyncCallback<ErrorInfo> async);
    void saveCSVtoFile(UtilGrid utilGrid, String fileName,  AsyncCallback<ErrorInfo> async);
    void getSwitchesData(String json, AsyncCallback<IntravueSwitchInfoInfo> async);
    void saveUpdateDeleteUsers(UserEntity userEntity, UserAction userAction, AsyncCallback<ErrorInfo> async);
    void readFile(String forFile, String fileName, AsyncCallback<String> async);
    void getIntravueStatusInfo(String ip, IntravueHostStatus ivHostStatus, AsyncCallback<IntravueHostStatus> async);
    void getAppDirPath(AsyncCallback<String> async);
    void getSwitchprobeLocalFiles(AsyncCallback<ArrayList<WbcFileInfo>> async);
    void getSwitchprobeLocalFilesAsString( AsyncCallback<ArrayList<String>> aync);
}
