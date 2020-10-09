package com.wbc.supervisor.client.dashboard2.events;

import com.google.gwt.event.shared.GwtEvent;

public class MainPanelLoadedEvent extends GwtEvent<MainPanelLoadedEventHandler> {
  public static Type<MainPanelLoadedEventHandler> TYPE = new Type<MainPanelLoadedEventHandler>();


@Override
public Type<MainPanelLoadedEventHandler> getAssociatedType() {
    return TYPE;
}

@Override
protected void dispatch(MainPanelLoadedEventHandler handler) {
    handler.onMainPanelLoaded(this);

}

}


