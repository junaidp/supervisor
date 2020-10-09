package com.wbc.supervisor.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.wbc.supervisor.client.dashboard2.visjsCharts.VisjsData;
import com.wbc.supervisor.shared.dashboard2dto.*;
import com.wbc.supervisor.shared.dto.DataBasedChartSeriesDTO;
import com.wbc.supervisor.shared.dto.DeviceinfoDataDTO;
import com.wbc.supervisor.shared.dto.DeviceinfoNamesDTO;
import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;

import java.util.ArrayList;
import java.util.HashMap;

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

     public static class App {
        private static supervisorServiceAsync ourInstance = GWT.create(supervisorService.class);

        public static synchronized supervisorServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
