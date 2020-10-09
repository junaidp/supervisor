package com.wbc.supervisor.client.dashboard2.events;

import com.google.gwt.event.shared.GwtEvent;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameWidget;

public class SelectedNetworkChangeEvent extends GwtEvent<SelectedNetworkChangeEventHandler> {
		  public static Type<SelectedNetworkChangeEventHandler> TYPE = new Type<SelectedNetworkChangeEventHandler>();

    public WbcNameWidget getWbcNameWidget() {

        return wbcNameWidget;
    }

    private WbcNameWidget wbcNameWidget;



    public SelectedNetworkChangeEvent(WbcNameWidget wbcNameWidget) {
        this.wbcNameWidget = wbcNameWidget;
    }


    @Override
		public Type<SelectedNetworkChangeEventHandler> getAssociatedType() {
		    return TYPE;
		}

		@Override
		protected void dispatch(SelectedNetworkChangeEventHandler handler) {
		    handler.onNetworkChange(this);
			
		}




	}


