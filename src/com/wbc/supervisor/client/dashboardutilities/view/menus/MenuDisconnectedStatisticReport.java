package com.wbc.supervisor.client.dashboardutilities.view.menus;

import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.wbc.supervisor.shared.dashboardutilities.Globals;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.SortMapByValue;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.DisconnectionByDayData;
import com.wbc.supervisor.shared.dashboardutilities.DisconnectionsByDayInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import java.util.Date;
import java.util.HashMap;

public class MenuDisconnectedStatisticReport extends MenuBase implements IsWidget
{

	private DisconnectionsByDayInfo disconnectionsByDayInfo;
	private String type;
	private String period;
	private AsyncCallback asyncCallback;
	private FlexTable table = new FlexTable();
	private ExportWidget exportWidget = new ExportWidget();
	private String order = "ASC";


	@Override
	public Widget asWidget() {

		container.clear();
		container.add(exportWidget);
		container.add(flexOptions() );
		container.add( table );
		return super.asWidget();

	}

	public MenuDisconnectedStatisticReport()
	{
		getData();
	}

	private void getData()
	{
		StringBuilder param = new StringBuilder();

		DashboardUtils.callServer( Constants.DISCONNCTED_STATISC_REPORT, Constants.DISCONNECTED_STATISTIC_REPORT_MESSAGE, param, RequestBuilder.GET, Constants.URL_DISCONNECTED_STATISTIC_REPORT, null,  new AsyncCallback<Response>()
		{
			@Override
			public void onFailure( Throwable caught )
			{

				new WarningMessageBox( Constants.DISCONNCTED_STATISC_REPORT, caught.getMessage() );
			}

			@Override
			public void onSuccess( Response result )
			{
				getConnectionData( result.getText() );
			}
		});
	}

	private void getConnectionData( String json )
	{
		supervisorServiceAsync rpcService = GWT.create( supervisorService.class );
		rpcService.getDisconnectionData( json, new AsyncCallback<DisconnectionsByDayInfo>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox( Constants.DISCONNCTED_STATISC_REPORT, caught.getLocalizedMessage() );
			}

			@Override
			public void onSuccess( DisconnectionsByDayInfo disconnectionsByDay )
			{
				DashboardUtils.handleError(disconnectionsByDay.getErrorInfo(), Constants.DISCONNCTED_STATISC_REPORT, new AsyncCallback<Void>()
				{
					@Override
					public void onFailure( Throwable caught )
					{
						if(caught != null)
						logError( caught.getLocalizedMessage() );
					}

					@Override
					public void onSuccess( Void result )
					{
						DashboardUtils.logInfo("Back from getting Data for Disconnected Statistic Report:" + result);
						disconnectionsByDayInfo = disconnectionsByDay;
						sort("Total", "DESC");
						updateTable( disconnectionsByDay );
						exportWidget.export(null, disconnectionsByDay);
					}
				} );
			}
		} );
	}

	private FlexTable flexOptions( )
	{

		FlexTable flexOptions = new FlexTable();
		StringComboBox sortBy = new StringComboBox();
		sortBy.add("Ip Address");
		sortBy.add("Network");
		sortBy.add("Name");
		sortBy.add("Total");

		StringComboBox sortOrder = new StringComboBox();
		sortOrder.add("ASC");
		sortOrder.add("DESC");

		flexOptions.setWidget( 0,0, sortBy);
		flexOptions.setWidget( 0,1, sortOrder);

		sortOrder.setEmptyText( "DESC" );
		sortOrder.setAllowTextSelection( true );
		sortBy.setEmptyText( "Total" );
		sortOrder.selectAll();
		sortOrder.select( 0 );

		sortOrder.addSelectionHandler( Event -> sort(sortBy.getText(), sortOrder.getText()));
		sortBy.addSelectionHandler( Event -> sort(sortBy.getText(), sortOrder.getText()));

		sortOrder.setTriggerAction( ComboBoxCell.TriggerAction.ALL );
		sortBy.setTriggerAction( ComboBoxCell.TriggerAction.ALL );
		flexOptions.setVisible(false);
		return flexOptions;

	}

	private void updateTable( DisconnectionsByDayInfo disconnectionsByDayInfo )
	{
		HTML ipAddress = new HTML( "Ip Address" );
		HTML network = new HTML( "Network" );
		HTML name = new HTML( "Name" );
		HTML total = new HTML( "Total" );

		table.setWidget( 0, 0,  ipAddress);
		table.setWidget( 0, 1, network );
		table.setWidget( 0, 2, name );

		//Sort Handlers on Headers
		ipAddress.addClickHandler(Event -> sort("Ip Address", order));
		network.addClickHandler(Event -> sort("Network", order));
		name.addClickHandler(Event -> sort("Name", order));
		total.addClickHandler(Event -> sort("Total", order));

		table.getCellFormatter().addStyleName(
				0, 0, "FlexTable-ColumnLabelCell headerCell leftJustified" );

		table.getCellFormatter().addStyleName(
				0, 1, "FlexTable-ColumnLabelCell headerCell leftJustified" );
		table.getCellFormatter().addStyleName(
				0, 2, "FlexTable-ColumnLabelCell headerCell leftJustified" );

		for ( int i = 0; i < disconnectionsByDayInfo.getDates().size(); i++ )
		{
			int colNo = i + 3;
			Date date = disconnectionsByDayInfo.getDates().get( i );

			table.setWidget( 0, colNo, new HTML( DashboardUtils.getFormattedDateWithTime( date ) ) );

			table.getCellFormatter().addStyleName(
					0, colNo, "FlexTable-ColumnLabelCell headerCell" );
			table.getCellFormatter().addStyleName(
					0, colNo + 1, "FlexTable-ColumnLabelCell headerCell" );

			int cell = table.getCellCount( 0 );

			int count = 0;

			for ( Integer key : disconnectionsByDayInfo.getDisconnectionByDayDataHashMap().keySet() )
			{
				count = count + 1;
				int row = count;
				DisconnectionByDayData disconnectionByDayData = disconnectionsByDayInfo.getDisconnectionByDayDataHashMap().get( key );
				Anchor ipAddressLink = new Anchor( disconnectionByDayData.getIp() );
				table.setWidget( row, 0, ipAddressLink );
				table.setWidget( row, 1, new HTML( disconnectionByDayData.getNetwork() + "" ) );
				table.setWidget( row, disconnectionsByDayInfo.getDates().size()+3, new HTML(disconnectionByDayData.getDayTotals().get(disconnectionByDayData.getDayTotals().size()-1)+"") );
				table.setWidget( row,  colNo, new HTML(disconnectionByDayData.getDayTotals().get( i )+"") );
				HTML htmlDesc = new HTML();
				htmlDesc.setHTML( disconnectionByDayData.getName() );
				htmlDesc.setWordWrap( false );
				table.setWidget( row, 2, htmlDesc );

				ipAddressLink.addClickHandler( Event -> onIpSelection(disconnectionByDayData.getIp(), disconnectionByDayData.getDescid()) );

				table.getRowFormatter().addStyleName( row, "FlexTable-OddRow" );
				table.getCellFormatter().addStyleName(
						row, colNo, "FlexTable-ColumnLabelCell" );

				table.getCellFormatter().addStyleName(
						row, 0, "FlexTable-ColumnLabelCell leftJustified" );
				table.getCellFormatter().addStyleName(
						row, 1, "FlexTable-ColumnLabelCell leftJustified" );
				table.getCellFormatter().addStyleName(
						row, 2, "FlexTable-ColumnLabelCell leftJustified" );
				table.getCellFormatter().addStyleName(
						row, disconnectionByDayData.getDayTotals().size() + 2, "FlexTable-ColumnLabelCell" );

			}
		}

		table.setWidget( 0, disconnectionsByDayInfo.getDates().size() + 3, total );
		table.getCellFormatter().addStyleName(
				0, disconnectionsByDayInfo.getDates().size() + 2, "FlexTable-ColumnLabelCell headerCell" );
		table.getCellFormatter().addStyleName(
				0, disconnectionsByDayInfo.getDates().size() + 3, "FlexTable-ColumnLabelCell headerCell" );

	}

	private void sort( String sortBy, String sortOrder ){
		if(sortBy == null || sortBy.isEmpty())
		{
			sortBy = "Total";
		}
		if(sortOrder == null || sortOrder.isEmpty())
		{
			sortOrder = "DESC";
		}
		DashboardUtils.logInfo("Sorting By "+ sortBy +", " + sortOrder);

		HashMap<Integer, DisconnectionByDayData> sorted = SortMapByValue.sortDisconnection( disconnectionsByDayInfo.getDisconnectionByDayDataHashMap(), sortBy, sortOrder );
		disconnectionsByDayInfo.setDisconnectionByDayDataHashMap( sorted );

		updateTable( disconnectionsByDayInfo );
		DashboardUtils.applyDataRowStyles(table);

		if(order.equalsIgnoreCase("ASC"))
			order = "DESC";
		else order = "ASC";

	}

	private void onIpSelection( String ip, int descId )
	{
		Window.open( "http://"+ Globals.HOST_IP_ADDRESS +":8765/iv3/#/network/topology/topology-graph?selected="+descId+"", "" , "");
	}

}
