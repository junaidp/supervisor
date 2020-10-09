package com.wbc.supervisor.client.dashboardutilities.view.login;
import com.wbc.supervisor.shared.dashboardutilities.Globals;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.menus.MenuBase;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHost;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostAction;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.Cell.Context;
import com.sencha.gxt.widget.core.client.info.Info;

public class ConnectionDialog extends MenuBase {
    private static final ConnectionDialog.ConnectionProperties props = GWT.create( ConnectionDialog.ConnectionProperties.class);
    private ListStore<IntravueHost> store = null;
    protected TextButton btnAddHost = new TextButton("Add Host");

    public ConnectionDialog(AsyncCallback<String> asyncCallback)
    {
        this.asyncCallback = asyncCallback;
        container.add( layout());
        getRemoteIntravues();
    }

   private Widget layout()
    {
        VerticalLayoutContainer vlc = new   VerticalLayoutContainer();

        vlc.setHeight(300);
        vlc.add(btnAddHost);
        btnAddHost.getElement().getStyle().setPaddingBottom(10, Style.Unit.PX);
        vlc.add( grid());
        btnAddHost.setWidth(100);
        btnAddHost.addSelectHandler(event -> addIntravueHost(null) );
        return vlc;
    }

    public interface ConnectionProperties extends PropertyAccess<IntravueHost>
    {
        ModelKeyProvider<IntravueHost> hostip();
        ValueProvider<IntravueHost, String> hostname();
        ValueProvider<IntravueHost, String> hostEmails();
        ValueProvider<IntravueHost, String> pk();
        ValueProvider<IntravueHost, String> expireDate();
        ValueProvider<IntravueHost, String> wbcserverVersion();
        ValueProvider<IntravueHost, String> keycode();

    }

    public Grid<IntravueHost> grid()
    {

        ColumnConfig<IntravueHost, String> ipAddressCol = new ColumnConfig<IntravueHost, String>(valueProviderIp, 150, "IP");
        ColumnConfig<IntravueHost, String> nameCol = new ColumnConfig<IntravueHost, String>(props.hostname(), 150, "Name");
        ColumnConfig<IntravueHost, String> emailCol = new ColumnConfig<IntravueHost, String>(props.hostEmails(), 150, "Email");
        ColumnConfig<IntravueHost, String> versionCol = new ColumnConfig<IntravueHost, String>(props.wbcserverVersion(), 150, "ivDashboard Version");
        ColumnConfig<IntravueHost, String> productKeyCol = new ColumnConfig<IntravueHost, String>(props.pk(), 150, "Product Key");
        ColumnConfig<IntravueHost, String> expirationDateCol = new ColumnConfig<IntravueHost, String>(props.expireDate(), 150, "Expire Date");
        ColumnConfig<IntravueHost, String> keyCodeCol = new ColumnConfig<IntravueHost, String>(props.keycode(), 150, "Keycode");

        ColumnConfig<IntravueHost, String> btnRemoveConnection = new ColumnConfig<IntravueHost, String>(valueProviderIp, 120, "");
        ColumnConfig<IntravueHost, String> btnEditToHost = new ColumnConfig<IntravueHost, String>( props.hostname(),120, "");
        ColumnConfig<IntravueHost, String> btnConnectToHost = new ColumnConfig<IntravueHost, String>( props.hostname(),120, "");

        TextButtonCell btnRemoveHost = new TextButtonCell();
        TextButtonCell btnConnectHost = new TextButtonCell();
        TextButtonCell btnEdittHost = new TextButtonCell();

        btnConnectHost.setText("Connect Host");
        btnEdittHost.setText("Edit Host");
        btnRemoveHost.setText("Remove Host");

        btnConnectHost.addSelectHandler(event -> connectToHost(event));
        btnEdittHost.addSelectHandler(event -> editHost(event));
        btnRemoveHost.addSelectHandler(event -> removeHost(event));

        btnConnectToHost.setCell(btnConnectHost);
        btnEditToHost.setCell(btnEdittHost);
        btnRemoveConnection.setCell(btnRemoveHost);
      //  btnRemoveConnection.setCell(CustomCell.getDisableRemoveHostButtonCellIP());
        List<ColumnConfig<IntravueHost, ?>> columns = new ArrayList<ColumnConfig<IntravueHost, ?>>();

        columns.add(ipAddressCol);
        columns.add(nameCol);
        columns.add(keyCodeCol);
        columns.add(versionCol);
        columns.add(productKeyCol);
        columns.add(expirationDateCol);
        columns.add(emailCol);

        columns.add(btnConnectToHost);
        columns.add(btnEditToHost);
        columns.add(btnRemoveConnection);

        ColumnModel<IntravueHost> cm = new ColumnModel<IntravueHost>(columns);

        store = new ListStore<IntravueHost>(props.hostip());

        final Grid<IntravueHost> grid = new Grid<IntravueHost>(store, cm);
        grid.setHeight(400);
        grid.setColumnResize(true);
        productKeyCol.setWidth(200);

        DashboardUtils.setDefaultGridStyle( grid );
        return grid;
    }

    ValueProvider<IntravueHost, String> valueProviderIp = new ValueProvider<IntravueHost, String>()
    {
        @Override
        public String getPath()
        {
            return "";
        }

        @Override
        public String getValue(IntravueHost taskDTO)
        {
             return taskDTO.getHostip();
        }

        @Override
        public void setValue(IntravueHost object, String value)
        {
        }
    };

    protected void getRemoteIntravues()
    {
        AutoProgressMessageBox messageBoxProgress = DashboardUtils.getProgressMessageBox( "Remote Intravues","Getting Remote Intravues" );

        rpcService.getIntravueHosts(new AsyncCallback<IntravueHostDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                messageBoxProgress.hide();
                logError("Failure in getting IntraVueHost "+ caught.getMessage());
                new WarningMessageBox( Constants.GETTING_REMOTE_INTRAVUE, "Error in getting IntraVueHost "+ caught.getMessage() );

            }

            @Override
            public void onSuccess(IntravueHostDTO intravueHostDTO) {
                messageBoxProgress.hide();
                logInfo("Back from getting Remote Intravues " + intravueHostDTO.getErrorInfo().toString() );
                if ( intravueHostDTO.getErrorInfo().isOK()) {
                    populate(intravueHostDTO);
                } else if ( intravueHostDTO.getErrorInfo().isWarning()) {
                    new WarningMessageBox(Constants.GETTING_REMOTE_INTRAVUE, intravueHostDTO.getErrorInfo().getResult()+":"+intravueHostDTO.getErrorInfo().getErrorText());
                    populate(intravueHostDTO);
                } else {
                    new WarningMessageBox(Constants.GETTING_REMOTE_INTRAVUE, intravueHostDTO.getErrorInfo().getResult()+":"+intravueHostDTO.getErrorInfo().getErrorText());
                }
            }
        });

   }

    private void populate(IntravueHostDTO intravueHostDTO) {
        store.clear();
        store.addAll( intravueHostDTO.getHostlist() );
    }

    protected void addIntravueHost(IntravueHost intravueHost)
    {
        AddIntraVueHost addNwIntraHost = new AddIntraVueHost(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                DashboardUtils.logError("Error in Save Intravue"+ caught.getMessage() );
            }

            @Override
            public void onSuccess(String result) {
                Info.display("Intravue Host", result);
                getRemoteIntravues();
            }
        });
        addNwIntraHost.go();
    }

    protected void connectToHost(SelectEvent event)
    {

        Context c = event.getContext();
        int row = c.getIndex();
        IntravueHost intravueHost = store.get(row);
        checkSelectedHostStatus(intravueHost);

    }

    private void checkSelectedHostStatus(IntravueHost intravueHost) {
         if(intravueHost.getHostip() == Globals.HOST_IP_ADDRESS){
           AlertMessageBox msg =  new AlertMessageBox("Host Status","Host Already Connected");
           msg.show();
        }
        else{
          //  HashMap<String, String> map = new HashMap<String, String>();
          //  map.put("ip", store.get(row).getHostip());
          //  map.put("userEmail", store.get(row).getHostEmails());
           // asyncCallback.onSuccess(map);
            getProductKey(intravueHost, new AsyncCallback<String>() {
                @Override
                public void onFailure(Throwable caught) {

                }

                @Override
                public void onSuccess(String result) {
                    getRemoteIntravues();
                }
            });
        }
    }

    protected void editHost(SelectEvent event)
    {
        Context c = event.getContext();
        int row = c.getIndex();
        IntravueHost intravueHost = store.get(row);
        EditIntraVueHost e = new EditIntraVueHost(new AsyncCallback<String>() {
                    @Override
                    public void onFailure(Throwable caught) {
                        DashboardUtils.logError("Error in Edit Intravue"+ caught.getMessage() );
                    }

                    @Override
                    public void onSuccess(String result) {
                        Info.display("Intravue Host", result);
                        getRemoteIntravues();
                    }
                }, intravueHost);
        e.go();

    }

    protected void removeHost(SelectEvent event)
    {
        Context c = event.getContext();
        int row = c.getIndex();
        IntravueHost intravueHost = store.get(row);
        removeIvHostCall(store.get(row));
        /*
        8/11/20 JWM you can remove localhost
        if(intravueHost.getHostip().equals("127.0.0.1"))
            new WarningMessageBox("Host Deletion", "Host cannot be removed");
            else
            removeIvHostCall(store.get(row));
        */
    }

    private void removeIvHostCall(IntravueHost intravueHost) {
        AutoProgressMessageBox messageBoxProgress = DashboardUtils.getProgressMessageBox( "Remote Intravues","Removing Remote Intravue" );

        rpcService.saveUpdateDeleteIntravueHost(intravueHost, IntravueHostAction.DELETE, new AsyncCallback<ErrorInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                messageBoxProgress.hide();
                logError("Error in Removing IntraVueHost "+ caught.getMessage());
                new WarningMessageBox( Constants.VALIDATION, "Error in Removing IntraVueHost "+ caught.getMessage() );

            }

            @Override
            public void onSuccess(ErrorInfo result) {
                messageBoxProgress.hide();
                logInfo("Success in remove IvHost"+ result.getResult()+"," + result.getErrorText());
                if(!result.getResult().equalsIgnoreCase("error")) {
                    Info.display("Intravue Host", "Removed");
                    getRemoteIntravues();
                }
                else{
                    new WarningMessageBox("Remove Intravue Host", result.getResult()+":"+ result.getErrorText());
                }

            }
        });
     }

}


