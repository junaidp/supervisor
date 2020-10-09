package com.wbc.supervisor.client.dashboard2.events;

import com.google.gwt.event.shared.GwtEvent;


public class WbcGeneralEvent extends GwtEvent<WbcGeneralEventHandler> {
		  public static Type<WbcGeneralEventHandler> TYPE = new Type<WbcGeneralEventHandler>();


    private String name;
    private String data;


    public WbcGeneralEvent(String name, String data) {
        this.name = name;
        this.data = data;
    }

    @Override
		public Type<WbcGeneralEventHandler> getAssociatedType() {
		    return TYPE;
		}

		@Override
		protected void dispatch(WbcGeneralEventHandler handler) {
		    handler.onValueChange(this);
			
		}


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }



	}


