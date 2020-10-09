package com.wbc.supervisor.client.dashboardutilities.view.menus;//package com.wbc.supervisor.shared.dashboardutilities.client.view.menus;
//
//import com.wbc.supervisor.client.supervisorService;
//import com.wbc.supervisor.client.supervisorServiceAsync;
//import com.wbc.supervisor.client.dashboardutilities.view.menus.MenuBase;
//import com.wbc.supervisor.shared.dashboardutilities.SwitchErrorData;
//import com.google.gwt.core.client.GWT;
//import com.google.gwt.editor.client.Editor.Path;
//import com.google.gwt.http.client.*;
//import com.google.gwt.user.client.Window;
//import com.google.gwt.user.client.rpc.AsyncCallback;
//import com.google.gwt.user.client.ui.IsWidget;
//import com.google.gwt.user.client.ui.Widget;
//import com.sencha.gxt.core.client.ValueProvider;
//import com.sencha.gxt.data.shared.LabelProvider;
//import com.sencha.gxt.data.shared.ListStore;
//import com.sencha.gxt.data.shared.ModelKeyProvider;
//import com.sencha.gxt.data.shared.PropertyAccess;
//import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
//import com.sencha.gxt.widget.core.client.grid.ColumnModel;
//import com.sencha.gxt.widget.core.client.grid.Grid;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MenuCRCGXTGrid extends MenuBase implements IsWidget {
//
//
//    private static final CRCErrorProperties props = GWT.create(CRCErrorProperties.class);
//    private String url = "http://localhost:8080/crc";
//    private ListStore<SwitchErrorData> store;
//    private String type;
//    private String period;
//    private AsyncCallback asyncCallback;
//
//    public MenuCRCGXTGrid( String id, String s)
//    {
//        init();
//
//    }
//
//    public MenuCRCGXTGrid( String type, String period , AsyncCallback asyncCallback  )
//    {
//        this.type = type ;
//        this.period =  period ;
//        this.asyncCallback = asyncCallback;
//        init();
//    }
//
//    private void init()
//    {
//        getData();
//    }
//
//    @Override
//    public Widget asWidget() {
//
//     // heading.setText("CRC");
//       container.insert( grid(),0 );
//       return super.asWidget();
//
//    }
//    public interface CRCErrorProperties extends PropertyAccess<SwitchErrorData>
//    {
//        @Path( "descid" )
//        ModelKeyProvider<SwitchErrorData> key();
//
//        @Path( "switchIp" )
//        LabelProvider<SwitchErrorData> nameLabel();
//
//        ValueProvider<SwitchErrorData, Integer> descid();
//        ValueProvider<SwitchErrorData, String> ip();
//        ValueProvider<SwitchErrorData, String> port();
//        ValueProvider<SwitchErrorData, String> portDesc();
//        ValueProvider<SwitchErrorData, Long> valuesTotal();
//        ValueProvider<SwitchErrorData, String> timeValue();
//    }
//
//    public Grid<SwitchErrorData> grid()
//    {
//
//        ColumnConfig<SwitchErrorData, Integer> descIdCol = new ColumnConfig<SwitchErrorData, Integer>(props.descid(), 150, "DescId");
//        ColumnConfig<SwitchErrorData, String> ipCol = new ColumnConfig<SwitchErrorData, String>(props.ip(), 150, "IP");
//        ColumnConfig<SwitchErrorData, String> portCol = new ColumnConfig<SwitchErrorData, String>(props.port(), 150, "Port");
//        ColumnConfig<SwitchErrorData, String> descCol = new ColumnConfig<SwitchErrorData, String>(props.portDesc(), 150, "Port Desc");
//        ColumnConfig<SwitchErrorData, Long> valuesTotalCol = new ColumnConfig<SwitchErrorData, Long>(props.valuesTotal(), 150, "Total");
//       // ColumnConfig<SwitchErrorData, String> timeValue = new ColumnConfig<SwitchErrorData, String>("", 150, "Total");
//
//
//        List<ColumnConfig<SwitchErrorData, ?>> columns = new ArrayList<ColumnConfig<SwitchErrorData, ?>>();
//
//        columns.add(descIdCol);
//        columns.add(ipCol);
//        columns.add(portCol);
//        columns.add(descCol);
//        columns.add(valuesTotalCol);
//
//        ColumnModel<SwitchErrorData> cm = new ColumnModel<SwitchErrorData>(columns);
//
//        store = new ListStore<SwitchErrorData>(props.key());
//
//        final Grid<SwitchErrorData> grid = new Grid<SwitchErrorData>(store, cm);
//        grid.setAllowTextSelection(false);
//        grid.getView().setStripeRows(true);
//        grid.getView().setColumnLines(true);
//        grid.setBorders(false);
//        grid.setColumnReordering(true);
//
//        return grid;
//
//    }
//
//    private void updateTable( List<SwitchErrorData> crcData )
//    {
//      store.addAll(crcData );
//    }
//
//    private void getData()
//    {
//
//        try {
//
//            StringBuilder param = new StringBuilder();
//            param.append("?period=").append( period );
//            param.append("&type=").append( type );
//
//            RequestBuilder builder = new RequestBuilder( RequestBuilder.GET, URL.encode( url + "/getSwitchErrors" + param) );
//
//
//            builder.sendRequest( null, new RequestCallback() {
//                public void onResponseReceived( Request request, Response response) {
//                    if (200 == response.getStatusCode() && response.getText().length()>2) {
//                        getCrcData(response.getText());
//                      //TODO: need to find a way to convert json at client side .
//
//                    } else {
//
//                        Window.alert( "No data found :" + response.getStatusCode() +""+ response.getText());
//                    }
//                }
//
//                public void onError(Request request, Throwable exception) {
//                    Window.alert("error" + exception);
//                }
//
//            });
//        } catch (RequestException e) {
//            Window.alert( "no server"+e );
//        }
//
//    }
//
//    private void getCrcData(String json)
//    {
//        DashboardutilitiesServiceAsync rpcService = GWT.create( DashboardutilitiesService.class );
///*
//        rpcService.getCrcData(json, new AsyncCallback<Map<String, SwitchErrorData>>() {
//            @Override
//            public void onFailure(Throwable caught) {
//
//                Window.alert( caught.getMessage() );
//                asyncCallback.onFailure( caught );
//            }
//
//            @Override
//            public void onSuccess(Map<String, SwitchErrorData> map) {
//                asyncCallback.onSuccess(null );
//                List<SwitchErrorData> list = new ArrayList<SwitchErrorData>(  );
//                int count = -1; // TODO remove this when we have a unique Id from server
//                for (String key: map.keySet()) {
//                    count++;
//                    SwitchErrorData switchErrorData = map.get(key);
//                    switchErrorData.setDescid( switchErrorData.getDescid()+count );
//                    switchErrorData.setValuesTotal(getValuesTotal(switchErrorData.getValues()));
//                    list.add( switchErrorData );
//                }
//               updateTable( list );
//
//            }
//
//
//        });
//        */
//    }
//
//    private long getValuesTotal( ArrayList<Integer> values )
//    {
//        long total = 0;
//        for(Integer value : values)
//        {
//            total = total + value;
//        }
//        return total;
//    }
//
//}
