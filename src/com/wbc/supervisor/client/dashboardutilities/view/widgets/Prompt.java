package com.wbc.supervisor.client.dashboardutilities.view.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent;
import com.sencha.gxt.widget.core.client.event.DialogHideEvent.DialogHideHandler;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;

public class Prompt
{

	public static Prompt get()
	{
		return Prompt.INSTANCE;
	}

	public static final Prompt INSTANCE = GWT.create(Prompt.class);

	public void alert(String title, String text)
	{
		alert(title, text, null);
	}

	public void alert(String title, String text, final Runnable runnable)
	{
		AlertMessageBox alertMessageBox = new AlertMessageBox(SafeHtmlUtils.fromTrustedString(title), SafeHtmlUtils.fromTrustedString(text));
		alertMessageBox.addHideHandler(new HideHandler()
		{
			@Override
			public void onHide(HideEvent event)
			{
				if (runnable != null)
				{
					runnable.run();
				}
			}
		});
		alertMessageBox.setWidth(300);
		alertMessageBox.show();
	}

	public void confirm(String title, String text, final Runnable yesRunnable)
	{
		confirm(title, text, yesRunnable, null);
	}

	public void confirm(String title, String text, final Runnable yesRunnable, final Runnable noRunnable)
	{
		ConfirmMessageBox confirmMessageBox = new ConfirmMessageBox(SafeHtmlUtils.fromTrustedString(title), SafeHtmlUtils.fromTrustedString(text));
		confirmMessageBox.addDialogHideHandler(new DialogHideHandler()
		{
			@Override
			public void onDialogHide(DialogHideEvent event)
			{
				if (event.getHideButton() == PredefinedButton.YES)
				{
					if (yesRunnable != null)
					{
						yesRunnable.run();
					}
				}
				else
				{
					if (noRunnable != null)
					{
						noRunnable.run();
					}
				}
			}
		});
		confirmMessageBox.setWidth(300);
		confirmMessageBox.show();
	}

	public void info(String title, String text)
	{
		InfoMessageBox infoMessageBox = new InfoMessageBox(title, text, null);
		infoMessageBox.show();

	}

	public void info(String title, String text, HideHandler handler)
	{
		InfoMessageBox infoMessageBox = new InfoMessageBox(title, text, handler);
		infoMessageBox.show();

	}

	public void warning(String title, String text, final Runnable yesRunnable, final Runnable noRunnable)
	{
		WarningMessageBox warningMessageBox = new WarningMessageBox(title, text, new DialogHideHandler()
		{
			@Override
			public void onDialogHide(DialogHideEvent event)
			{
				if (event.getHideButton() == PredefinedButton.YES)
				{
					if (yesRunnable != null)
					{
						yesRunnable.run();
					}
				}
				else
				{
					if (noRunnable != null)
					{
						noRunnable.run();
					}
				}
			}
		});
		warningMessageBox.show();
	}

	public void info(String title, String text, Runnable runnable)
	{
		InfoMessageBox infoMessageBox = new InfoMessageBox(title, text, new HideHandler()
		{

			@Override
			public void onHide(HideEvent event)
			{
				runnable.run();
			}
		});
		infoMessageBox.show();

	}
}
