package com.wbc.supervisor.client.dashboardutilities.view.login;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ProgressMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHost;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostAction;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.DelayedTask;

public class EditIntraVueHost extends AddUpdateIntravueHost {

    public EditIntraVueHost(AsyncCallback<String> asyncCallback, IntravueHost intravueHost){
        super(asyncCallback);
        setWidget( layout());
        layoutEdit();
        updateFields(intravueHost);
    }

    private void updateFields(IntravueHost intraVueHost) {
        txtFieldEmail.setText( intraVueHost.getHostEmails());
        txtFieldHostIp.setText( intraVueHost.getHostip());
        txtFieldHostName.setText( intraVueHost.getHostname());

        btnAddHost.addSelectHandler(event -> updateHost(intraVueHost) );
    }

    protected void layoutEdit() {
        setHeading(Constants.EDIT_REMOTE_INTRAVUE);
        btnAddHost.setText("Update");
        txtFieldHostIp.setEnabled(false);

    }

    protected void updateHost(IntravueHost intraVueHost)
    {
        intraVueHost.setHostEmails(txtFieldEmail.getText());
        intraVueHost.setHostname(txtFieldHostName.getText());
        saveHost(intraVueHost, IntravueHostAction.UPDATE);


  }

}
