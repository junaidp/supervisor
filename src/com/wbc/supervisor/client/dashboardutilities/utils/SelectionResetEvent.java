package com.wbc.supervisor.client.dashboardutilities.utils;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * Fires after the reset selection event
 */
public class SelectionResetEvent extends GwtEvent<SelectionResetEvent.SelectionResetHandler> {

  public interface HasSelectionResetHandlers {
    HandlerRegistration addSelectionResetHandler( SelectionResetHandler handler );
  }

  public interface SelectionResetHandler extends EventHandler {

    void onReset( SelectionResetEvent event );
  }

  /**
   * Handler type.
   */
  private static Type<SelectionResetHandler> TYPE;

  /**
   * Gets the type associated with this event.
   * 
   * @return returns the handler type
   */
  public static Type<SelectionResetHandler> getType() {
    if (TYPE == null) {
      TYPE = new Type<SelectionResetHandler>();
    }
    return TYPE;
  }

  public SelectionResetEvent() {
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  @Override
  public Type<SelectionResetHandler> getAssociatedType() {
    return (Type) TYPE;
  }

  @Override
  protected void dispatch(SelectionResetHandler handler) {
    handler.onReset(this);
  }

}
