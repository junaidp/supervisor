package com.wbc.supervisor.client.dashboardutilities;

import com.google.gwt.user.client.ui.HasWidgets;

public interface View
{

	void go( final HasWidgets container );

	Presenter getPresenter();

	void setPresenter( Presenter presenter );
}
