package com.wbc.supervisor.client.dashboard2.events;

import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.user.client.ui.ListBox;
import com.wbc.supervisor.client.dashboard2.wbcwidgets.WbcNameListboxWidget;

public class WbcNamesControlEvent extends GwtEvent<WbcNamesControlEventHandler> {
		  public static Type<WbcNamesControlEventHandler> TYPE = new Type<WbcNamesControlEventHandler>();
		 private ListBox listSortFields;

    public ListBox getListSortFields() {
        return listSortFields;
    }

    public WbcNameListboxWidget getListWbcNames() {
        return listWbcNames;
    }

    private WbcNameListboxWidget listWbcNames;

    public WbcNamesControlEvent(ListBox listSortFields, WbcNameListboxWidget listWbcNames) {
        //TODO understand how this event is used
        this.listSortFields = listSortFields;
        this.listWbcNames = listWbcNames;
    }

    @Override
		public Type<WbcNamesControlEventHandler> getAssociatedType() {
		    return TYPE;
		}

		@Override
		protected void dispatch(WbcNamesControlEventHandler handler) {
		    handler.onValueChange(this);
			
		}



	}


