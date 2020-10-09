package com.wbc.supervisor.client.dashboardutilities.view.widgets;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;

public class WarningMessageBox extends MessageBox
{

	public WarningMessageBox(String title, String message)
	{
		super( SafeHtmlUtils.fromTrustedString(title), SafeHtmlUtils.fromTrustedString(message));

		setIcon(ICONS.warning());
		setPredefinedButtons(PredefinedButton.OK);
		show();
	}

	public WarningMessageBox(String title, String message, DialogHideHandler dialogHideHandler)
	{
		this(title, message);

		if(dialogHideHandler != null)
			addDialogHideHandler(dialogHideHandler);
	}

}
