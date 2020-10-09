package com.wbc.supervisor.client.dashboardutilities.view.login;

import com.wbc.supervisor.shared.dashboardutilities.IntravueHost;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostAction;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class AddIntraVueHost extends AddUpdateIntravueHost {

    public AddIntraVueHost(AsyncCallback<String> asyncCallback)
    {
        super(asyncCallback);
        setWidget( layout());
        IntravueHost intraVueHost = new IntravueHost();

        btnAddHost.addSelectHandler(event -> saveHost(intraVueHost, IntravueHostAction.SAVE) );


    }

 }



