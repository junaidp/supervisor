package com.wbc.supervisor.client.dashboardutilities.utils;

import com.wbc.supervisor.shared.dashboardutilities.ConnectionInfoData;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHost;
import com.wbc.supervisor.shared.dashboardutilities.UtilGrid;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.SymbolDTO;
import com.wbc.supervisor.shared.dashboardutilities.threshold.StatData;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.Window;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.widget.core.client.box.MessageBox;

public class CustomCell
{

	public static ButtonCell<SymbolDTO> getButtonCellSymbol()
	{
		ButtonCell<SymbolDTO> symbolCell = new ButtonCell<SymbolDTO>()
		{
			@Override
			public void render(Context context, SymbolDTO v, SafeHtmlBuilder sb)
			{
				if(v!= null && v.getIp()!= null)
				{
					if(v.getDescId()!=0)
						sb.appendHtmlConstant( "<span style='color:blue; cursor: pointer;'>" + v.getIp() + "</span>" );
					else
						sb.appendHtmlConstant( "<span style=''>" + v.getIp() + "</span>" );

				}
				else
				{
					sb.appendHtmlConstant("<span> &nbsp; </span>");
				}
			}

			@Override
			public void onBrowserEvent(Context context, Element parent, SymbolDTO v, NativeEvent event,
					ValueUpdater<SymbolDTO> valueUpdater)
			{
				super.onBrowserEvent( context, parent, v, event, valueUpdater );
				String eventType = event.getType();
				if(v!= null && v.getIp()!= null && v.getDescId()!=0)
				{
					if ("click".equals( eventType ))
					{
						MessageBox box = new MessageBox("Not implemented yet", "Getting further infor for IP:"+ v.getIp());
						box.show();
					}
				}

			}
		};
			return symbolCell;
	}

	public static Cell<ConnectionInfoData> getButtonCellIP()
	{
		ButtonCell<ConnectionInfoData> symbolCell = new ButtonCell<ConnectionInfoData>()
		{
			@Override
			public void render(Context context, ConnectionInfoData ip, SafeHtmlBuilder sb)
			{
				if(ip!= null )
				{
					if(ip.getDescid()!=0)
							sb.appendHtmlConstant( "<span style='background-color:white; color:blue; cursor: pointer;  text-decoration: underline;'>" + ip.getIpaddress() + "</span>" );
					else
						sb.appendHtmlConstant( "<span style='background-color:white;'>" + ip.getIpaddress() + "</span>" );

				}
				else
				{
					sb.appendHtmlConstant("<span> &nbsp; </span>");
				}
			}

			@Override
			public void onBrowserEvent(Context context, Element parent, ConnectionInfoData ip, NativeEvent event,
									   ValueUpdater<ConnectionInfoData> valueUpdater)
			{
				super.onBrowserEvent( context, parent, ip, event, valueUpdater );

				String eventType = event.getType();
				if(ip!= null && ip.getDescid() != 0)
				{
					if ("click".equals( eventType ))
					{
						DashboardUtils.logInfo("Opening Ip :"+ ip.getIpaddress() +" with DescId: "+ ip.getDescid());
						DashboardUtils.onIpSelection( ip.getIpaddress(),  ip.getDescid());
					}
				}

			}
		};


		return symbolCell;
	}

	public static Cell<String> getDisableRemoveHostButtonCellIP() {
		ButtonCell<String> symbolCell = new ButtonCell<String>() {
			@Override
			public void render(Context context, String ip, SafeHtmlBuilder sb) {
					if (ip.equals("127.0.0.1")) {
						sb.appendHtmlConstant("<span style='background-color:white;'> ");
					}
					else{
						super.render(context, "Remove Host", sb);
					}


				}
			@Override
			public void onBrowserEvent(Context context, Element parent, String ip, NativeEvent event,
									   ValueUpdater<String> valueUpdater)
			{

				//super.onBrowserEvent( context, parent, ip, event, valueUpdater );

				String eventType = event.getType();

					if ("click".equals( eventType ))
					{

						GWT.log("Removing Host :"+ ip);
						//DashboardUtils.onIpSelection( ipAddress,  Integer.parseInt(descId.trim()));
					}
				}


		};

		return symbolCell;
	}



	public static ButtonCell<StatData> getButtonCellStatAvg()
	{
		ButtonCell<StatData> symbolCell = new ButtonCell<StatData>()
		{
			@Override
			public void render(Context context, StatData v, SafeHtmlBuilder sb)
			{

				sb.appendHtmlConstant( "<span style=''>" + v.getAvg() + "</span>" );
			}
		};
		return symbolCell;
	}

	public static ButtonCell<StatData> getButtonCellStatMin()
	{
		ButtonCell<StatData> symbolCell = new ButtonCell<StatData>()
		{
			@Override
			public void render(Context context, StatData v, SafeHtmlBuilder sb)
			{

				sb.appendHtmlConstant( "<span style=''>" + v.getMin() + "</span>" );
			}
		};
		return symbolCell;
	}

	public static ButtonCell<StatData> getButtonCellStatMax()
	{
		ButtonCell<StatData> symbolCell = new ButtonCell<StatData>()
		{
			@Override
			public void render(Context context, StatData v, SafeHtmlBuilder sb)
			{

				sb.appendHtmlConstant( "<span style=''>" + v.getMax() + "</span>" );
			}
		};
		return symbolCell;
	}


	public static ButtonCell<StatData> getButtonCellStatStdDev()
	{
		ButtonCell<StatData> symbolCell = new ButtonCell<StatData>()
		{
			@Override
			public void render(Context context, StatData v, SafeHtmlBuilder sb)
			{

				sb.appendHtmlConstant( "<span style=''>" + v.getStddev() + "</span>" );
			}
		};
		return symbolCell;
	}

	public static ButtonCell<StatData> getButtonCellStatStdDevOver()
	{
		ButtonCell<StatData> symbolCell = new ButtonCell<StatData>()
		{
			@Override
			public void render(Context context, StatData v, SafeHtmlBuilder sb)
			{

				sb.appendHtmlConstant( "<span style=''>" + v.getStdOverToString() + "</span>" );
			}
		};
		return symbolCell;
	}

	public static ButtonCell<StatData> getButtonCellStatStdDevCounts()
	{
		ButtonCell<StatData> symbolCell = new ButtonCell<StatData>()
		{
			@Override
			public void render(Context context, StatData v, SafeHtmlBuilder sb)
			{

				sb.appendHtmlConstant( "<span style=''>" + v.getStdOverCounts() + "</span>" );
			}
		};
		return symbolCell;
	}

	public static ButtonCell<StatData> getButtonCellXOver()
	{
		ButtonCell<StatData> symbolCell = new ButtonCell<StatData>()
		{
			@Override
			public void render(Context context, StatData v, SafeHtmlBuilder sb)
			{

				sb.appendHtmlConstant( "<span style=''>" + DashboardUtils.getFormattedRoundNumber( v.getAvg() + (5 * v.getStddev())) + "</span>" );
			}
		};
		return symbolCell;
	}

	public static ButtonCell<Integer> getButtonCellThresholdType()
	{
		ButtonCell<Integer> symbolCell = new ButtonCell<Integer>()
		{
			@Override
			public void render(Context context, Integer v, SafeHtmlBuilder sb)
			{
				String type = "PING";
				if(v == 1)
				{
					type = "PING";
				}
				if(v == 2)
				{
					type = "RECV";
				}
				if(v == 3)
				{
					type = "XMIT";
				}
				sb.appendHtmlConstant( "<span style=''>" + type + "</span>" );
			}
		};
		return symbolCell;
	}

	public static Cell<StatData> getButtonCellSamples()
	{
		ButtonCell<StatData> symbolCell = new ButtonCell<StatData>()
		{
			@Override
			public void render(Context context, StatData v, SafeHtmlBuilder sb)
			{

				sb.appendHtmlConstant( "<span style=''>" + v.getCount() + "</span>" );
			}
		};
		return symbolCell;
	}

	public static Cell<StatData> getButtonCellRecommended()
	{
		ButtonCell<StatData> symbolCell = new ButtonCell<StatData>()
		{
			@Override
			public void render(Context context, StatData v, SafeHtmlBuilder sb)
			{

				sb.appendHtmlConstant( "<span style=''>" + v.getRecommended() + "</span>" );
			}
		};
		return symbolCell;
	}
}
