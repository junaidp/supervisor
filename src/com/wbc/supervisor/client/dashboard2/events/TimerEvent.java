package com.wbc.supervisor.client.dashboard2.events;

import com.google.gwt.event.shared.GwtEvent;

public class TimerEvent extends GwtEvent<TimerEventHandler> {
		  public static Type<TimerEventHandler> TYPE = new Type<TimerEventHandler>();

    private long millis;
    private String purpose;

    public TimerEvent() {
        millis = System.currentTimeMillis();
        purpose = "";
    }
    public TimerEvent( String purpose ) {
        millis = System.currentTimeMillis();
        this.purpose = purpose;
    }

    public TimerEvent( long millisToSet, String purpose ) {
        millis = millisToSet;
        this.purpose = purpose;
    }

    public long getMillis() { return millis; }
    public String getPurpose(){ return purpose; }

    @Override
		public Type<TimerEventHandler> getAssociatedType() {
		    return TYPE;
		}

		@Override
		protected void dispatch(TimerEventHandler handler) {
		    handler.onTimer(this);
			
		}



	}


