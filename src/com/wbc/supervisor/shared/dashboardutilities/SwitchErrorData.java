package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;

public class SwitchErrorData implements IsSerializable
{
	private String ip;
	private int descid;
	private String  port;
	private String portDesc;
	private ArrayList<Integer> values;
	private long valuesTotal;
	private ArrayList<String> times;


	public long getValuesTotal()
	{
		return valuesTotal;
	}

	public void setValuesTotal( long valuesTotal )
	{
		this.valuesTotal = valuesTotal;
	}

	public SwitchErrorData() {
		values = new ArrayList<Integer>();
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPortDesc() {
		return portDesc;
	}

	public void setPortDesc(String portDesc) {
		this.portDesc = portDesc;
	}

	public ArrayList<Integer> getValues() {
		return values;
	}

	public void setValues(ArrayList<Integer> values) {
		this.values = values;
	}

	public int getDescid() {
		return descid;
	}

	public void setDescid(int descid) {
		this.descid = descid;
	}
}

