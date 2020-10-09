package com.wbc.supervisor.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.wbc.supervisor.client.dashboard2.visjsCharts.VisjsData;
import com.wbc.supervisor.shared.dashboard2dto.*;
import com.wbc.supervisor.shared.dto.DataBasedChartSeriesDTO;
import com.wbc.supervisor.shared.dto.DeviceinfoDataDTO;
import com.wbc.supervisor.shared.dto.DeviceinfoNamesDTO;
import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;

import java.util.ArrayList;
import java.util.HashMap;

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

}
