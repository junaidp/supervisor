package com.wbc.supervisor.client.dashboardutilities.utils;

import java.util.Comparator;

public class DoubleComparator implements Comparator
{
	@Override
	public int compare( Object p1, Object p2 )
	{
		if (Double.parseDouble(p1.toString()) < Double.parseDouble(p2.toString())) return -1;
		if (Double.parseDouble(p1.toString()) > Double.parseDouble(p2.toString())) return 1;
		return 0;
	}


}
