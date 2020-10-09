package com.wbc.supervisor.client.dashboard2.events;

import com.google.gwt.event.shared.GwtEvent;

public class AllMainPanelsLoadedEvent extends GwtEvent<AllMainPanelsLoadedEventHandler> {
  public static Type<AllMainPanelsLoadedEventHandler> TYPE = new Type<AllMainPanelsLoadedEventHandler>();


@Override
public Type<AllMainPanelsLoadedEventHandler> getAssociatedType() {
    return TYPE;
}

@Override
protected void dispatch(AllMainPanelsLoadedEventHandler handler) {
    handler.onMainPanelLoaded(this);

}

}


