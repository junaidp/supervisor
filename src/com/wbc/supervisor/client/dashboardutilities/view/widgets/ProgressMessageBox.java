package com.wbc.supervisor.client.dashboardutilities.view.widgets;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;

public class ProgressMessageBox extends AutoProgressMessageBox
{
	private int timeout = 20;
	private DelayedTask timeoutTimer = null;

	public ProgressMessageBox(SafeHtml headingHtml, SafeHtml messageHtml)
	{
		super(headingHtml, messageHtml);
		init();
	}

	public ProgressMessageBox(SafeHtml headingHtml)
	{
		super(headingHtml);
		init();
	}

	public ProgressMessageBox(String headingHtml, String messageHtml, int timeout)
	{
		super(headingHtml, messageHtml);
		this.timeout = timeout;
		init();
	}

	public ProgressMessageBox(String headingHtml, String messageHtml)
	{
		super(headingHtml, messageHtml);
		init();
	}

	public ProgressMessageBox(String headingHtml)
	{
		super(headingHtml);
		init();
	}

	protected void init()
	{
		setHeaderVisible(false);
		setOnEsc(false);
		auto();
		getProgressBar().setDuration(timeout * 1000);
		setProgressText("Please Wait...");

		startTimer();
	}

	public void startTimer()
	{
		if (timeout > 0)
		{
			timeoutTimer = new DelayedTask()
			{
				@Override
				public void onExecute()
				{
					if (timeoutTimer != null)
						askTimeout();
				}

			};

			timeoutTimer.delay(timeout * 1000);
		}
	}

	private void askTimeout()
	{
		Prompt.get().confirm("Loading", "Taking time, you like to continue?", () -> startTimer(), () -> hide());
	}

	public int getTimeout()
	{
		return timeout;
	}

	public void setTimeout(int timeout)
	{
		this.timeout = timeout;
	}

	@Override
	public void hide()
	{
		cancelTimer();
		super.hide();

	}

	@Override
	protected void onHide()
	{
		cancelTimer();
		super.onHide();

	}

	private void cancelTimer()
	{
		if (timeoutTimer != null)
		{
			timeoutTimer.cancel();
			timeoutTimer = null;
			timeout = 0;
		}

	}

}

