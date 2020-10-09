package com.wbc.supervisor.client.dashboardutilities.view.menus.about;

import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.menus.MenuBase;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.DialogDashboard;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.google.gwt.dom.client.Style;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

public class MenuAbout extends Dialog
{

	public MenuAbout(AsyncCallback<String> asyncCallback)
	{

		displayVersion(asyncCallback);

	}

	private void displayVersion(AsyncCallback<String> asyncCallback)
	{
		VerticalLayoutContainer container = new VerticalLayoutContainer();

		Image logo = new Image( "images/WBC-INS-Logo.png" );
		HTML htmlVersion = new HTML("Version: "+ Constants.APP_VERSION + "  " + Constants.APP_VERSION_EXTRA );//version + extra
		htmlVersion.getElement().getStyle().setPaddingBottom(20, Style.Unit.PX);
		VerticalPanel v = new VerticalPanel();
		v.setWidth("100%");
		v.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
		v.add(logo);
		v.add(htmlVersion);
		container.add(v);

		setSize( "600px", "500px" );
		setWidget( container );
		setHeading( "About" );
		container.add( new CollapsibleAbout(this));
		setModal(true);
		show();
		setResizable( true );
		getButton( PredefinedButton.OK).addSelectHandler( new SelectEvent.SelectHandler()
		{
			@Override
			public void onSelect( SelectEvent event )
			{
				hide();
			}
		} );

		addDialogHideHandler(new DialogHideEvent.DialogHideHandler() {
			@Override
			public void onDialogHide(DialogHideEvent event) {
				hide();
				//asyncCallback.onSuccess("closed");
			}
		});

	}

}
