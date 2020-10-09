package com.wbc.supervisor.client.dashboardutilities.view.menus.pagging;

import com.wbc.supervisor.shared.dashboardutilities.switchprobe.ArpDataExtended;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.loader.*;

public class PagingGridConfirmableLoader extends PagingLoader<PagingLoadConfig, PagingLoadResult<ArpDataExtended>> {
    public PagingGridConfirmableLoader(RpcProxy<FilterPagingLoadConfig, PagingLoadResult<ArpDataExtended>> proxy) {
        super(null);
    }

    @Override
    public void loadData(PagingLoadConfig config) {
        super.loadData(config);
    }
}
