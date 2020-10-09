package com.wbc.supervisor.client.dashboardutilities.utils;

import com.wbc.supervisor.shared.dashboardutilities.DisconnectionByDayData;
import com.wbc.supervisor.shared.dashboardutilities.SwitchErrorData;
import com.google.gwt.core.client.GWT;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class SortMapByValue

{
	private static boolean ASC = true;
	private static boolean DESC = false;
	public static HashMap<String, SwitchErrorData> sort( HashMap<String, SwitchErrorData> unsortMap, String sortBy, String sortOrder )
	{

		// Creating dummy unsorted map


		System.out.println("Before sorting......");
		printMap(unsortMap);

		System.out.println("After sorting ascending order......");
		HashMap<String, SwitchErrorData> sortedMapAsc = sortByValue(unsortMap, sortOrder.equals( "ASC" )? ASC : DESC, sortBy);
		printMap(sortedMapAsc);


		return sortedMapAsc;
	}

	private static HashMap<String, SwitchErrorData> sortByValue(HashMap<String, SwitchErrorData> unsortMap, final boolean order, String field)
	{
		List<Entry<String, SwitchErrorData>> list = new LinkedList<>(unsortMap.entrySet());

		switch(field)
		{
		case "Port":
			// Sorting the list based on values
			list.sort( ( o1, o2 ) -> order ? Integer.parseInt(o1.getValue().getPort()) - Integer.parseInt(o2.getValue().getPort()):
					Integer.parseInt(o2.getValue().getPort()) - Integer.parseInt(o1.getValue().getPort()));

			break;
		case "Port Description":
			list.sort( ( o1, o2 ) -> order ? o1.getValue().getPortDesc().compareTo( o2.getValue().getPortDesc() ) == 0
					? o1.getKey().compareTo( o2.getKey() )
					: o1.getValue().getPortDesc().compareTo( o2.getValue().getPortDesc() ) : o2.getValue().getPortDesc().compareTo( o1.getValue().getPortDesc() ) == 0
					? o2.getKey().compareTo( o1.getKey() )
					: o2.getValue().getPortDesc().compareTo( o1.getValue().getPortDesc() ) );
			break;
		case "Switch Ip":
			list.sort( ( o1, o2 ) -> order ? DashboardUtils.toNumeric(o1.getValue().getIp()).compareTo( DashboardUtils.toNumeric(o2.getValue().getIp()) ) == 0
					? o1.getKey().compareTo( o2.getKey() )
					: DashboardUtils.toNumeric(o1.getValue().getIp()).compareTo( DashboardUtils.toNumeric(o2.getValue().getIp()) ) : DashboardUtils.toNumeric(o2.getValue().getIp()).compareTo( DashboardUtils.toNumeric(o1.getValue().getIp()) ) == 0
					? o2.getKey().compareTo( o1.getKey() )
					: DashboardUtils.toNumeric(o2.getValue().getIp()).compareTo( DashboardUtils.toNumeric(o1.getValue().getIp()) ) );
			break;
		case "Total":
			list.sort( ( o1, o2 ) -> order ? Integer.parseInt(o1.getValue().getValuesTotal()+"") - Integer.parseInt(o2.getValue().getValuesTotal()+""):
					Integer.parseInt(o2.getValue().getValuesTotal()+"") - Integer.parseInt(o1.getValue().getValuesTotal()+""));

			break;

		default:

		}
		return list.stream().collect( Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

	}


	public static HashMap<Integer, DisconnectionByDayData> sortDisconnection( HashMap<Integer, DisconnectionByDayData> unsortMap, String sortBy, String sortOrder )
	{
		GWT.log( "After sorting" );
		HashMap<Integer, DisconnectionByDayData> sortedMapAsc = sortByValueDisconnection(unsortMap, sortOrder.equals( "ASC" )? ASC : DESC, sortBy);
		return sortedMapAsc;
	}

	private static HashMap<Integer, DisconnectionByDayData> sortByValueDisconnection(HashMap<Integer, DisconnectionByDayData> unsortMap, final boolean order, String field)
	{
		List<Entry<Integer, DisconnectionByDayData>> list = new LinkedList<>(unsortMap.entrySet());

		switch(field)
		{
		case "Network":
			list.sort( ( o1, o2 ) -> order ? o1.getValue().getNetwork().compareTo( o2.getValue().getNetwork() ) == 0
					? o1.getKey().compareTo( o2.getKey() )
					: o1.getValue().getNetwork().compareTo( o2.getValue().getNetwork() ) : o2.getValue().getNetwork().compareTo( o1.getValue().getNetwork() ) == 0
					? o2.getKey().compareTo( o1.getKey() )
					: o2.getValue().getNetwork().compareTo( o1.getValue().getNetwork() ) );

			break;
		case "Name":
			list.sort( ( o1, o2 ) -> order ? o1.getValue().getName().compareTo( o2.getValue().getName() ) == 0
					? o1.getKey().compareTo( o2.getKey() )
					: o1.getValue().getName().compareTo( o2.getValue().getName() ) : o2.getValue().getName().compareTo( o1.getValue().getName() ) == 0
					? o2.getKey().compareTo( o1.getKey() )
					: o2.getValue().getName().compareTo( o1.getValue().getName() ) );
			break;
		case "Ip Address":
			list.sort( ( o1, o2 ) -> order ? DashboardUtils.toNumeric(o1.getValue().getIp()).compareTo( DashboardUtils.toNumeric(o2.getValue().getIp()) ) == 0
					? o1.getKey().compareTo( o2.getKey() )
					: DashboardUtils.toNumeric(o1.getValue().getIp()).compareTo( DashboardUtils.toNumeric(o2.getValue().getIp()) ) : DashboardUtils.toNumeric(o2.getValue().getIp()).compareTo( DashboardUtils.toNumeric(o1.getValue().getIp()) ) == 0
					? o2.getKey().compareTo( o1.getKey() )
					: DashboardUtils.toNumeric(o2.getValue().getIp()).compareTo( DashboardUtils.toNumeric(o1.getValue().getIp()) ) );
			break;
		case "Total":
			list.sort( ( o1, o2 ) -> order ? Integer.parseInt(o1.getValue().getDayTotals().get(o1.getValue().getDayTotals().size()-1)+"") - Integer.parseInt(o2.getValue().getDayTotals().get(o1.getValue().getDayTotals().size()-1)+""):
					Integer.parseInt(o2.getValue().getDayTotals().get(o1.getValue().getDayTotals().size()-1)+"") - Integer.parseInt(o1.getValue().getDayTotals().get(o1.getValue().getDayTotals().size()-1)+""));

			break;

		default:

		}
		return list.stream().collect( Collectors.toMap(Entry::getKey, Entry::getValue, (a, b) -> b, LinkedHashMap::new));

	}


	private static void printMap(Map<String, SwitchErrorData>  map)
	{
		map.forEach((key, value) -> System.out.println("Key : " + key + " Value : " + value));
	}
}