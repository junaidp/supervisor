package com.wbc.supervisor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RemoteServiceRelativePath("supervisorService")
public interface supervisorService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    ///////////////////////DASHBOARD 2 ///////////////////////
    //    ArrayList<ThresholdStat> getThresholdDataForIntravue( int nwid, String thresholdType, String maxAverage, int  oldestSample, int numGraphPoints, int numSamplesPerPoint)throws Exception;
    ArrayList<WbcNamesDTO> getNetworkInfo();
    // obsolete >> ArrayList<ChartDTO> getThresholdDataForIntravue(int nwid, String thresholdType, String maxAverage, int oldestSample, int numGraphPoints,int numSamplesPerPoint);

    MultiSeriesTimebasedChartDTO getPingSummaryData     (int nwid, String maxAverage, long oldestSample, long newestSample, int numSamplesPerPoint);
    MultiSeriesTimebasedChartDTO getBandwidthSummaryData(int nwid, String thresholdType, String maxAverage, long oldestSample, long newestSample,  int numSamplesPerPoint);
    MultiSeriesTimebasedChartDTO[] getStatsBarGraphData   (int nwid, boolean criticalOnly,                  long oldestSample, long newestSample,  int numSamplesPerPoint);

    ArrayList<DataBasedChartSeriesDTO> getDetailedPingData(int nwid, String method, int maxDevices, long oldestSample, long newestSample, int numSamplesPerPoint);
    Long getLastSampleno( );
    //TODO Give getData a meaningful name, gets visjs data, topology
    ArrayList<DeviceTopologyInfo> getNetworkDeviceTreeMap(int nwid);
    ArrayList<DeviceinfoNamesDTO> getNetworkDeviceNamesMap(int nwid);
    ArrayList<DeviceInfoDTO> getNetworkDeviceInfo(int nwid);
    ArrayList<DeviceinfoDataDTO> getNetworkDeviceData(int nwid);
    ConnectionDataDTO getConnectionDataForNetwork(int nwid );
    VisjsData getVisjsData(int nwid );
    String updateNetworkDeviceNamesMap(ArrayList<DeviceinfoNamesDTO> updatedList );
    HashMap getLocalDetails();
    String callBrowseIntravueNetwork(int networkid );
    String callDeviceStatsDetailsJsp(int currentNetworkId , int statIndexNumber , long hoursToGraph);
    String callDeviceInfoAndStatDetailsPage(int deviceid);
    String getServerAddress();
    Integer getScannerState();
    ArrayList<KpiStatsRow> getKpiStatsForPeriod(int networkId, long starttime, long endtime);
    ArrayList<String> getIntravueProperties();



    /////////////////////////WBCUTILS ////////////////////////////////
    SwitchErrorInfo getCrcData(String json) throws IOException;
    SwitchProbeReportInfoImpl getSwitchReport(String json, String generatedFromSwitch, String uploadedFile) throws  IOException;
    KpiClassInfo getKpiData(String json) throws Exception;
    ConnectionInfoInfo getConnectionData(String json) throws Exception;
    String getJson( ArrayList<Integer> list ) throws Exception;
    DisconnectionsByDayInfo getDisconnectionData(String json) throws Exception;
    ThresholdPresentationInfo getThresholdData(String json, ThresholdType thresholdType) throws Exception;
    String getRecommendedThresholdsJson( List<ThresholdSetData> list ) throws Exception;
    ErrorInfo getErrorText(String json) throws Exception;
    String getIntraVueHostJson(IntravueHost intravueHost);
    IntravueHostDTO getIntravueHostDTO(String json);
    ErrorInfo getProductKeyAndKeycode(String keycode) throws Exception;
    ErrorInfo getExpirationDate(String keycode) throws Exception;
    ErrorInfo validateKeycode (IntravueHost intravueHost, String getVersionUrl);
    IntravueHostDTO getIntravueHosts();
    ErrorInfo saveUpdateDeleteIntravueHost(IntravueHost intravueHost, IntravueHostAction intravueHostAction);
    ErrorInfo saveCSVtoFile(UtilGrid utilGrid, String fileName);
    IntravueSwitchInfoInfo getSwitchesData(String json);
    String readFile(String forFile, String fileName) throws Exception;

    ErrorInfo saveUpdateDeleteUsers(UserEntity userEntity, UserAction userAction);

    IntravueHostStatus getIntravueStatusInfo(String ip, IntravueHostStatus ivHostStatus);
    String getAppDirPath();
    ArrayList<WbcFileInfo> getSwitchprobeLocalFiles();
    ArrayList<String> getSwitchprobeLocalFilesAsString();

     public static class App {
        private static supervisorServiceAsync ourInstance = GWT.create(supervisorService.class);

        public static synchronized supervisorServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
