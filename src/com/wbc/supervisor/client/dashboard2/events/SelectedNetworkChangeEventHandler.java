package com.wbc.supervisor.client.dashboard2.events;

import com.google.gwt.event.shared.EventHandler;

public interface SelectedNetworkChangeEventHandler extends EventHandler {
	void onNetworkChange(SelectedNetworkChangeEvent event);
}
