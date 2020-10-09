package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;

public class ProductKey implements IsSerializable
{
	String result;
	String errorText;

	public String getErrorText()
	{
		return errorText;
	}

	public void setErrorText( String errorText )
	{
		this.errorText = errorText;
	}
}
