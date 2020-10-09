package com.wbc.supervisor.shared.dashboardutilities.switchprobe;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SymbolDTO implements IsSerializable
{
	private int descId;
	private String ip;

	public SymbolDTO( int descid, String ip )
	{
		this.descId = descid;
		this.ip = ip;
	}

	public SymbolDTO( )
	{

	}

	public int getDescId()
	{
		return descId;
	}

	public void setDescId( int descId )
	{
		this.descId = descId;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp( String ip )
	{
		this.ip = ip;
	}
}
