package com.wbc.supervisor.client.dashboardutilities.event;

import com.google.gwt.event.shared.GwtEvent;

public class MainEvent extends GwtEvent<MainEventHandler> {

  public MainEvent() {

    }

public static Type<MainEventHandler> TYPE = new Type<MainEventHandler>();

@Override
public Type<MainEventHandler> getAssociatedType() {
    return TYPE;
}

@Override
protected void dispatch(MainEventHandler handler) {
    handler.onMain(this);

}

}


