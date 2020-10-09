package com.wbc.supervisor.client.dashboardutilities.utils;

import java.util.Comparator;

public class IpComparator implements Comparator
{
	@Override
	public int compare(Object adr1, Object adr2) {
		try
		{
			if(adr1 == null || adr1.toString().isEmpty()) return -1;
			if(adr2 == null || adr2.toString().isEmpty()) return 1;
			String ad2 = adr2.toString();
			String[] ba1 = adr1.toString().split( "\\." );
			String[] ba2 = ad2.split( "\\." );

			for ( int i = 0; i < ba1.length; i++ )
			{
				int b1 = Integer.parseInt( ba1[ i ] );
				int b2 = Integer.parseInt( ba2[ i ] );

				if (b1 == b2)
					continue;
				if (b1 < b2)
					return -1;
				else
					return 1;
			}
			return 0;
		}
		catch ( Exception ex )
		{
			return 0;
		}

	}


}
