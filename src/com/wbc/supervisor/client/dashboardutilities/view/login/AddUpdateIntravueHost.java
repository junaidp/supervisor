package com.wbc.supervisor.client.dashboardutilities.view.login;

import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.DialogBase;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WizardFieldLabel;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHost;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostAction;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.TextField;

public class AddUpdateIntravueHost extends DialogBase {

    protected AsyncCallback<String> asyncCallback;
    protected TextField txtFieldHostIp = null;
    protected TextField txtFieldHostName = null;
    protected TextField txtFieldEmail = null;

    protected TextButton btnAddHost = new TextButton("Add");
    private supervisorServiceAsync rpcService = GWT.create( supervisorService.class );



    public AddUpdateIntravueHost(AsyncCallback<String> asyncCallback) {
        super();
        this.asyncCallback = asyncCallback;

    }

    protected void saveHost(IntravueHost intraVueHost, IntravueHostAction intravueHostAction)
    {
        intraVueHost.setHostEmails(txtFieldEmail.getText());
        if(intraVueHost.getHostip() == null || intraVueHost.getHostip().isEmpty()) {
            intraVueHost.setHostip(txtFieldHostIp.getText());
        }
        intraVueHost.setHostname(txtFieldHostName.getText());
        saveIvHostCall(intraVueHost, intravueHostAction);

    }

    private void saveIvHostCall(IntravueHost intraVueHost, IntravueHostAction intravueHostAction) {
        AutoProgressMessageBox messageBoxProgress = DashboardUtils.getProgressMessageBox( "Remote Intravues","Saving Remote Intravue" );

        rpcService.saveUpdateDeleteIntravueHost(intraVueHost, intravueHostAction, new AsyncCallback<ErrorInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                messageBoxProgress.hide();
                DashboardUtils.logError("Error in "+ intravueHostAction + ":" + caught.getLocalizedMessage());
                new WarningMessageBox("Remote Intravues", caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(ErrorInfo result) {
                messageBoxProgress.hide();
                DashboardUtils.logInfo("Success in "+ intravueHostAction+":"+ result.getResult()+"," + result.getErrorText());
                if(!result.getResult().equalsIgnoreCase("error") ) {
                    asyncCallback.onSuccess(intravueHostAction == IntravueHostAction.UPDATE? "updated":"added");
                }
                else{
                    new WarningMessageBox("Save Intravue Host", result.getResult()+":"+ result.getErrorText()) ;
                }
                hide();
            }
        });


    }

    public void go()
    {
        setHeading(Constants.ADD_REMOTE_INTRAVUE);
        setDefault();
        setPredefinedButtons();
        addButton(btnAddHost);
        show();
    }

    protected Widget layout()
    {
        VerticalLayoutContainer addHostContainer = new   VerticalLayoutContainer();
        addHostContainer.addStyleName("margin10");
        txtFieldHostIp = new TextField(  );
        txtFieldHostIp.setWidth( 300 );
        txtFieldHostIp.setEmptyText( "Enter Ip Address" );

        txtFieldHostName = new TextField(  );
        txtFieldHostName.setWidth( 300 );
        txtFieldHostName.setEmptyText( "Enter Host Name" );

        txtFieldEmail = new TextField(  );
        txtFieldEmail.setWidth( 300 );
        txtFieldEmail.setEmptyText( "Enter Email" );

        btnAddHost.setSize( "100px", "30px" );
        addHostContainer.add( new WizardFieldLabel( txtFieldHostIp, "Host IP" ));
        addHostContainer.add( new WizardFieldLabel( txtFieldHostName, "Host Name" ) );
        addHostContainer.add( new WizardFieldLabel( txtFieldEmail, "Email" ) );


        return addHostContainer;

    }

}
