package com.wbc.supervisor.client.dashboard2.events;

import com.google.gwt.event.shared.GwtEvent;

public class BrowseEvent extends GwtEvent<BrowseEventHandler> {
  public static Type<BrowseEventHandler> TYPE = new Type<BrowseEventHandler>();


@Override
public Type<BrowseEventHandler> getAssociatedType() {
    return TYPE;
}

@Override
protected void dispatch(BrowseEventHandler handler) {
    handler.onBrowse(this);

}

}


