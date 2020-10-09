package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.IpComparator;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WizardFieldLabel;
import com.wbc.supervisor.shared.dashboardutilities.IntravueSwitchInfoData;
import com.wbc.supervisor.shared.dashboardutilities.IntravueSwitchInfoInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.Style;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.state.client.GridFilterStateHandler;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.Radio;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SwitchesPanel extends SwitchProbePanel {


    private static final SwitchesPanel.VProperties props = GWT.create( SwitchesPanel.VProperties.class );
    private ListStore<IntravueSwitchInfoData> store ;
    IdentityValueProvider<IntravueSwitchInfoData> identity = new IdentityValueProvider<IntravueSwitchInfoData>();
    final CheckBoxSelectionModel<IntravueSwitchInfoData> selectionModel = new CheckBoxSelectionModel<IntravueSwitchInfoData>(identity);
    private AsyncCallback<IntravueSwitchInfoData> asyncCallback = null;
    private  TextField ipField = new TextField();
    private  TextField communityField = new TextField();


    protected Radio radioNotFromList = new Radio();

    public SwitchesPanel() {
        ipField.setEnabled(false);
        communityField.setEnabled(false);
        add(grid());
    }

    @Override
    protected ArrayList<String> getParams(BaseGrid baseGrid, String c) {
        return null;
    }

    @Override
    protected void filter() {
        filter(filterWidget.searchField.getText());
    }

    protected void getSwitches(Radio radioFromSwitch, ToggleGroup group, AsyncCallback<IntravueSwitchInfoData> asyncCallback)
    {
        this.asyncCallback = asyncCallback;
        StringBuilder param = new StringBuilder();
        group.add(radioNotFromList);
        selectionModel.addSelectionChangedHandler(new SelectionChangedEvent.SelectionChangedHandler<IntravueSwitchInfoData>() {
            @Override
            public void onSelectionChanged(SelectionChangedEvent<IntravueSwitchInfoData> event) {
                onGridSelection(event, radioFromSwitch);
            }
        });

        final AutoProgressMessageBox messageBoxProgress = DashboardUtils.getProgressMessageBox( "Switches", "Getting Available Switches .." );

        DashboardUtils.callServer( Constants.GET_SWITCHES, Constants.GET_SWITCHES_MESSAGE, param, RequestBuilder.GET, Constants.URL_GET_SWITCHES, null, new AsyncCallback<Response>()
        {
            @Override
            public void onFailure( Throwable caught )
            {
                new WarningMessageBox( Constants.GET_SWITCHES, caught.getMessage() );
                DashboardUtils.logError("Error in getting Switches: " + caught.getMessage());
                messageBoxProgress.hide();
            }

            @Override
            public void onSuccess( Response result )
            {
                DashboardUtils.logInfo("Back from GetSwitchesData:" + result);
                DashboardUtils.logInfo("Converting Switches Json");
                getSwitchesData( result.getText() );
                messageBoxProgress.hide();
            }
        } );
    }

    private void onGridSelection(SelectionChangedEvent<IntravueSwitchInfoData> event, Radio radioFromSwitch) {
        GWT.log(event.getSelection().size()+" is size");
        if(! event.getSelection().isEmpty()) {
            DashboardUtils.logInfo("Unselecting radioFromSwitch");
            ipField.setEnabled(false);
            communityField.setEnabled(false);
            radioFromSwitch.setValue(true, true);
        }
    }

    private void getSwitchesData(String json) {
        supervisorServiceAsync rpcService = GWT.create( supervisorService.class );
        rpcService.getSwitchesData(json, new AsyncCallback<IntravueSwitchInfoInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                DashboardUtils.logError("Error in Converting Json Switches: " + caught.getMessage());
            }

            @Override
            public void onSuccess(IntravueSwitchInfoInfo intravueSwitchInfoInfo) {
                DashboardUtils.logInfo("Back from GetSwitchesData:" + intravueSwitchInfoInfo.getErrorInfo());

                DashboardUtils.handleError(intravueSwitchInfoInfo.getErrorInfo(), Constants.GET_SWITCHES, new AsyncCallback<Void>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        if(caught != null)
                        DashboardUtils.logError("Error in GetSwitchesData: "+  caught.getMessage());

                    }

                    @Override
                    public void onSuccess(Void result) {
                        HashMap<String, IntravueSwitchInfoData> map = intravueSwitchInfoInfo.getSwitchTable();

                        ArrayList<IntravueSwitchInfoData> list = new ArrayList<IntravueSwitchInfoData>();
                        for (Map.Entry<String, IntravueSwitchInfoData> entry : map.entrySet()) {
                            String key = entry.getKey();
                            IntravueSwitchInfoData value = entry.getValue();
                            list.add(value);
                        }

                        populate(list);

                    }
                });

            }
        });

    }

    public interface VProperties extends PropertyAccess<IntravueSwitchInfoData>
    {

        @Editor.Path( "key" )
        ModelKeyProvider<IntravueSwitchInfoData> key();
        ValueProvider<IntravueSwitchInfoData, String> ip();
        ValueProvider<IntravueSwitchInfoData, String> name();
        ValueProvider<IntravueSwitchInfoData, String> community();
        ValueProvider<IntravueSwitchInfoData, String> nwName();

    }

    public VerticalLayoutContainer grid()
    {
        ColumnConfig<IntravueSwitchInfoData, String> ipCol = new ColumnConfig<IntravueSwitchInfoData, String>( props.ip(), 100, "IP Address" );
        ColumnConfig<IntravueSwitchInfoData, String> nameCol = new ColumnConfig<IntravueSwitchInfoData, String>( props.name(), 250, "Name" );
        ColumnConfig<IntravueSwitchInfoData, String> nwNameCol = new ColumnConfig<IntravueSwitchInfoData, String>( props.nwName(), 100, "Network" );
        ColumnConfig<IntravueSwitchInfoData, String> communityCol = new ColumnConfig<IntravueSwitchInfoData, String>( props.community(), 100, "Community" );

        List<ColumnConfig<IntravueSwitchInfoData, ?>> columns = new ArrayList<ColumnConfig<IntravueSwitchInfoData, ?>>();
        columns.add(selectionModel.getColumn());
        columns.add( ipCol );
        columns.add( nameCol );
        columns.add( nwNameCol );
        columns.add( communityCol );

        ColumnModel<IntravueSwitchInfoData> cm = new ColumnModel<IntravueSwitchInfoData>( columns );

        store = new ListStore<IntravueSwitchInfoData>( props.key() );

        final Grid<IntravueSwitchInfoData> grid = new Grid<IntravueSwitchInfoData>( store, cm );
        DashboardUtils.setDefaultGridStyle( grid );

        ipCol.setComparator(new IpComparator() );
        //ipCol.setCell( CustomCell.getButtonCellIP() );

        //FILTERS
        StringFilter<IntravueSwitchInfoData> ipFilter = new StringFilter<IntravueSwitchInfoData>(props.ip());
        StringFilter<IntravueSwitchInfoData> nameFilter = new StringFilter<IntravueSwitchInfoData>(props.name());
        StringFilter<IntravueSwitchInfoData> nwNameFilter = new StringFilter<IntravueSwitchInfoData>(props.nwName());
        StringFilter<IntravueSwitchInfoData> communityFilter = new StringFilter<IntravueSwitchInfoData>(props.community());

        GridFilters<IntravueSwitchInfoData> filters = new GridFilters<IntravueSwitchInfoData>();
        filters.initPlugin( grid );
        filters.setLocal( true );
        filters.addFilter( ipFilter );
        filters.addFilter( nameFilter );
        filters.addFilter( nwNameFilter );
        filters.addFilter( communityFilter );

        // Stage manager, load the previous state
        GridFilterStateHandler<IntravueSwitchInfoData> handler = new GridFilterStateHandler<IntravueSwitchInfoData>( grid, filters );
        handler.loadState();
        grid.setSelectionModel(selectionModel);
        selectionModel.setSelectionMode(Style.SelectionMode.SINGLE);


        selectionModel.addSelectionChangedHandler(Event -> onSelectionChanged(Event));

       TextButton generateReport = new TextButton("Generate Report");
        generateReport.addSelectHandler(new SelectEvent.SelectHandler() {
            @Override
            public void onSelect(SelectEvent event) {
                DashboardUtils.logInfo("Grid Selection is "+ grid.getSelectionModel().getSelectedItem());
                IntravueSwitchInfoData selectedSwitch = null;
                if(grid.getSelectionModel().getSelectedItem() != null) {
                    DashboardUtils.logInfo("Generating switch probe Report for ip:" + grid.getSelectionModel().getSelectedItem().getIp());
                    selectedSwitch = grid.getSelectionModel().getSelectedItem();
                }
                else if(! ipField.getText().isEmpty() && !communityField.getText().isEmpty()){
                    DashboardUtils.logInfo("Generating switch probe Report for ip:" + ipField.getText() +" and Community: " + communityField.getText());
                    selectedSwitch = new IntravueSwitchInfoData();
                    selectedSwitch.setIp(ipField.getText());
                    selectedSwitch.setCommunity(communityField.getText());
                }
                else return;

                asyncCallback.onSuccess(selectedSwitch);
            }
        });


        HorizontalLayoutContainer h = new HorizontalLayoutContainer();
        h.add(radioNotFromList, new HorizontalLayoutContainer.HorizontalLayoutData(-1,-1, new Margins(2,5,0,0)));
        h.add(new WizardFieldLabel( ipField, "Get switchprobe from device not in list"));
        ipField.setEmptyText("Ip Field");
        h.add(new WizardFieldLabel( communityField, "community name or @ number") , new HorizontalLayoutContainer.HorizontalLayoutData(-1,-1, new Margins(0,0,0,10)));
        communityField.setWidth(290);
        add(h);
        add(generateReport, new VerticalLayoutData(-1,-1, new Margins(30,0,0,0)));
        ToolBar toolBar = new ToolBar();
        toolBar.setBorders(false);
        toolBar.add(grid);
        VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
        verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, 25));
        verticalLayoutContainer.add(grid, new VerticalLayoutData(1, 270));
        setWidth(900);

        radioNotFromList.addValueChangeHandler(Event -> onRadioNotFromList(radioNotFromList.getValue()));
        return verticalLayoutContainer;

    }

    private void onSelectionChanged(SelectionChangedEvent<IntravueSwitchInfoData> event) {
        IntravueSwitchInfoData intravueSwitchInfoData =  new IntravueSwitchInfoData();
        radioNotFromList.setValue(Boolean.FALSE, true);
        ipField.clear();
        communityField.clear();
        intravueSwitchInfoData.setIp(event.getSelection().get(0).getIp());
        intravueSwitchInfoData.setName(event.getSelection().get(0).getName());
        intravueSwitchInfoData.setCommunity("ONSELECTION");
        asyncCallback.onSuccess(intravueSwitchInfoData);
    }

    private void onRadioNotFromList(Boolean selected) {
        if(selected) {
            selectionModel.deselectAll();
            ipField.setEnabled(true);
            communityField.setEnabled(true);
        }
    }

    protected void populate( ArrayList<IntravueSwitchInfoData> list )
    {
        try
        {
            //DashboardUtils.logInfo( "POPULATING SwitchesPanel with size: " + list.size()  );
            store.clear();
            store.addAll( list );

        }
        catch ( Exception ex )
        {
            new WarningMessageBox( "SwitchesPanel ", ex.getMessage() );
            DashboardUtils.logError("Error in Populating SwitchesPanel:" + ex);
        }

    }

    public TextField getIpField() {
        return ipField;
    }


}
