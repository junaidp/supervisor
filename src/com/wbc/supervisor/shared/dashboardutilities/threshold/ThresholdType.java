package com.wbc.supervisor.shared.dashboardutilities.threshold;

import java.util.ArrayList;

public enum ThresholdType
{
	THRESHOLD_TYPE_PING(1, "PING", "PING Only"),
	THRESHOLD_TYPE_RECV(2, "RECV", "RECV Bandwidth Only"),
	THRESHOLD_TYPE_XMIT(3, "XMIT", "XMIT Bandwidth Only"),
	THRESHOLD_TYPE_BOTH_BANDWIDTH(4, "Both", "RECV and XMIT Bandwidth"),
	THRESHOLD_TYPE_ALL(4, "All", "All Thresholds");

	int id;
	String type;
	String desc;

	public int getId()
	{
		return id;
	}

	public String getType()
	{
		return type;
	}

	public String getDesc()
	{

		return desc;
	}

	public static ArrayList<String> getAllValues()
	{
		ArrayList<String> values = new ArrayList<String>();
		for(ThresholdType value : ThresholdType.values())
		{
			values.add(value.desc);
		}
		return values;
	}

	public static ThresholdType getSelected(String value)
	{
		switch ( value ){
		case "PING Only":
			return THRESHOLD_TYPE_PING;
		case "RECV Bandwidth Only":
			return THRESHOLD_TYPE_RECV;
		case "XMIT Bandwidth Only":
			return THRESHOLD_TYPE_XMIT;
		case "RECV and XMIT Bandwidth":
			return THRESHOLD_TYPE_BOTH_BANDWIDTH;
		case "All Thresholds":
			return THRESHOLD_TYPE_ALL;

			default:
				return  THRESHOLD_TYPE_ALL;

		}
	}

	ThresholdType( int id, String type, String desc )
	{
		this.id = id;
		this.type = type;
		this.desc = desc;
	}
}
