package com.wbc.supervisor.client.dashboardutilities.view.menus.kpimanagement;

import com.wbc.supervisor.client.dashboardutilities.DashboardUtilMessages;
import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.menus.MenuBase;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.KpiClassInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuKPIManagement extends MenuBase implements IsWidget
{

	private DashboardUtilMessages messages =
			GWT.create(DashboardUtilMessages.class);

	private HashMap<Integer, KpiEvent> events = null;

	@Override
	public Widget asWidget() {

		container.clear();
		return super.asWidget();

	}

	public MenuKPIManagement()
	{
		events = new HashMap<Integer, KpiEvent>();
		staticEvents( );

		getData();
	}

	private void getData()
	{
		StringBuilder param = new StringBuilder();
		DashboardUtils.callServer( Constants.KPI_MANAGEMENT, Constants.KPI_MANAGMEMENT_MESSAGE, param, RequestBuilder.GET, Constants.URL_KPI_MANAGEMENT, null, new AsyncCallback<Response>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox( Constants.KPI_MANAGEMENT, caught.getMessage() );
			}

			@Override
			public void onSuccess( Response result )
			{
				getKpiData(result.getText());

			}
		});
	}

	private void setData( String list )
	{
		StringBuilder param = new StringBuilder();
		param.append( "?list=").append( list );

		DashboardUtils.callServer( Constants.KPI_MANAGEMENT, Constants.KPI_MANAGMEMENT_SETDATA_MESSAGE, param, RequestBuilder.POST, Constants.URL_KPI_MANAGEMENT_SET, null, new AsyncCallback<Response>()
		{
			@Override
			public void onFailure( Throwable caught )
			{

				new WarningMessageBox( Constants.KPI_MANAGMEMENT_SETDATA_MESSAGE, caught.getMessage() );
			}

			@Override
			public void onSuccess( Response result )
			{
				getKpiData(result.getText());
			}
		});
	}

	private void getKpiData( String text )
	{
		supervisorServiceAsync rpcService = GWT.create( supervisorService.class );

		rpcService.getKpiData( text, new AsyncCallback<KpiClassInfo>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox( Constants.KPI_MANAGEMENT, caught.getLocalizedMessage() );
			}

			@Override
			public void onSuccess( KpiClassInfo result )
			{
				DashboardUtils.handleError( result.getErrorInfo(), Constants.KPI_MANAGEMENT, new AsyncCallback<Void>()
				{
					@Override
					public void onFailure( Throwable caught )
					{

					}

					@Override
					public void onSuccess( Void none )
					{
						if(events == null)
						{
							logError("Events in Kpi is null");
							return;
						}
					        for(Integer excludedClass : result.getExcludedClasses())
							{
								if(events.get(excludedClass) == null )
								{
									logWarning("Event:"+ excludedClass +"is not available");
									continue;
								}

								KpiEvent kpiEvent = events.get(excludedClass);

								if (kpiEvent != null) {
									kpiEvent.check.setValue(false, true);

							}
							}
						container.clear();
						container.add( layout());
					}
				} );
			}
		} );

	}

	private Widget layout()
	{

		VerticalLayoutContainer container = new VerticalLayoutContainer();

		 TextButton btnSend = new TextButton( "Update Settings" );
		 TextButton checkAll = new TextButton( "Select All" );
		 TextButton unCheckAll = new TextButton( "UnSelect All" );
		 TextButton selectRecommended = new TextButton( "Select Recommended" );

		 FlexTable flexTable = new FlexTable();
		 flexTable.setWidget( 0,0, checkAll);
		 flexTable.setWidget( 0,1, unCheckAll );
		 flexTable.setWidget( 0,2, selectRecommended );
		 HorizontalPanel hp = new HorizontalPanel();
		 hp.setWidth("300px");
		 hp.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
		 hp.add(btnSend);
		 flexTable.setWidget( 0,3, hp);
		 btnSend.addSelectHandler( Event -> sendData() );
		 container.add( flexTable );

		 checkAll.addSelectHandler( Event-> checkAll(true) );
		 unCheckAll.addSelectHandler( Event-> checkAll(false) );
		 selectRecommended.addSelectHandler(Event -> selectRecommended());

		HorizontalLayoutContainer othersContainer = new HorizontalLayoutContainer();
		VerticalLayoutContainer otherContainerLeft = new VerticalLayoutContainer();
		VerticalLayoutContainer otherContainerRight = new VerticalLayoutContainer();
		othersContainer.add( otherContainerLeft,  new HorizontalLayoutContainer.HorizontalLayoutData( -1,-1, new Margins( 0,10,0,0 ) ));
		othersContainer.add( otherContainerRight , new HorizontalLayoutContainer.HorizontalLayoutData( -1,-1, new Margins( 0,10,0,400 ) ));

		for ( Map.Entry<Integer, KpiEvent> entry : events.entrySet()) {

			KpiEvent kpiEvent = entry.getValue();
			if(entry.getKey() == 20)
			{
				HTML heading = new HTML("Events related to performance");
				heading.addStyleName( "heading" );
				container.add(heading, new VerticalLayoutContainer.VerticalLayoutData( -1,30,new Margins( 10,0,10,0 ) ) );
			}
			else if(entry.getKey() == 105)
			{
				HTML heading = new HTML("Events that could be monitored");
				heading.addStyleName( "heading" );
				container.add(heading, new VerticalLayoutContainer.VerticalLayoutData( -1,30,new Margins( 10,0,10,0 ) ) );
			}
			else if(entry.getKey() == 2)
			{
				HTML heading = new HTML("Other Events");
				heading.addStyleName( "heading" );
				container.add(heading, new VerticalLayoutContainer.VerticalLayoutData( -1,30,new Margins( 10,0,10,0 ) ) );
			}

			if(kpiEvent.other)
			{
				if(otherContainerLeft.getWidgetCount()<15)
					otherContainerLeft.add( kpiEvent, new VerticalLayoutContainer.VerticalLayoutData( -1,30,new Margins( 0,0,0,0 ) ) );
				else
					otherContainerRight.add( kpiEvent, new VerticalLayoutContainer.VerticalLayoutData( -1,30,new Margins( 0,0,0,0 ) ) );

			}
			else
			container.add( kpiEvent, new VerticalLayoutContainer.VerticalLayoutData( -1,30,new Margins( 0,0,0,0 ) ) );

		}
		container.add( othersContainer , new VerticalLayoutContainer.VerticalLayoutData( -1,30,new Margins( 0,0,0,0 ) ) );

		return  container;
	}

	private void selectRecommended() {
		for ( Map.Entry<Integer, KpiEvent> entry : events.entrySet()) {
			entry.getValue().check.setValue( false, true );
		}
		for ( Map.Entry<Integer, KpiEvent> entry : events.entrySet()) {
			entry.getValue().check.setValue( false, true );
		}
		DashboardUtils.logInfo("Selecting Recommended only: i.e -> 20,19,31,101,102,103,104");
		events.get(20).check.setValue(true, true);
		events.get(19).check.setValue(true, true);
		events.get(31).check.setValue(true, true);
		events.get(101).check.setValue(true, true);
		events.get(102).check.setValue(true, true);
		events.get(103).check.setValue(true, true);
		events.get(104).check.setValue(true, true);
	}

	private void sendData()
	{
		ArrayList<Integer> list = new ArrayList<Integer>( );
		for ( Map.Entry<Integer, KpiEvent> entry : events.entrySet()) {
			if(!entry.getValue().check.getValue())
				list.add( entry.getKey());

		}
		if(!list.isEmpty())
		{
		//	AutoProgressMessageBox messageBoxProgress = DashboardUtils.getProgressMessageBox(Constants.KPI_MANAGEMENT, Constants.KPI_MANAGMEMENT_SETDATA_MESSAGE);
			//setData(list);
			getListJson( list );
		}

	}

	private void getListJson( ArrayList<Integer> list )
	{
		supervisorServiceAsync rpcService = GWT.create( supervisorService.class );
		rpcService.getJson( list, new AsyncCallback<String>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox( "getting list json", caught.getLocalizedMessage() );
			}

			@Override
			public void onSuccess( String result )
			{
				setData(result);
			}
		} );
	}

	private void checkAll( boolean value )
	{
		for ( Map.Entry<Integer, KpiEvent> entry : events.entrySet()) {
			entry.getValue().check.setValue( value, true );
		}
	}

	private void staticEvents( )
	{
		events.put( 20,new KpiEvent(messages.id_device_changed_ip(), true , false));
		events.put( 19,new KpiEvent(messages.id_device_changed_mac(), true, false ));
		events.put( 31,new KpiEvent(messages.id_scanner_stopped(), true , false));
		events.put( 101,new KpiEvent(messages.id_ping_response(), true , false));
		events.put( 102,new KpiEvent(messages.id_bandwidth_threshold(), true , false));
		events.put( 103,new KpiEvent(messages.id_device_connection(), true , false));
		events.put( 104,new KpiEvent(messages.id_device_moved(), true , false));

		events.put( 105,new KpiEvent(messages.id_SNMP_lost_returned(), true , false));
		events.put( 108,new KpiEvent(messages.id_device_changed_speed(), true , false));
		events.put( 109,new KpiEvent(messages.id_device_changed_ENIP(), true , false));

		events.put( 2,new KpiEvent(messages.id_scanner_version(), true, true ));
		events.put( 1,new KpiEvent(messages.id_restored_database(), true , true));
		events.put( 3,new KpiEvent(messages.id_registration(), true, true ));
		events.put( 4,new KpiEvent(messages.id_Joined_as_top_parent(), true , true));
		events.put( 5,new KpiEvent(messages.id_system_configuration_changes_applied(), true, true ));
		events.put( 7,new KpiEvent(messages.id_device_moved_to_subnetparent(), true, true ));
		events.put( 9,new KpiEvent(messages.id_SNMP_supported(), true, true ));
		events.put( 13,new KpiEvent(messages.id_admin_verified(), true, true ));
		events.put( 17,new KpiEvent(messages.id_switch_reports_mac(), true, true ));
		events.put( 25,new KpiEvent(messages.id_deleted_node(), true, true ));
		events.put( 26,new KpiEvent(messages.id_admin_UN_verified(), true, true ));
		events.put( 27,new KpiEvent(messages.id_automatic_backup_file_created(), true, true ));
		events.put( 28,new KpiEvent(messages.id_automatic_backup_file_deleted(), true, true ));
		events.put( 29,new KpiEvent(messages.id_adjusting_scanrange_to_add_top_parent(), true, true ));
		events.put( 30,new KpiEvent(messages.id_admin_moved_child_node(), true, true ));

		events.put( 32,new KpiEvent(messages.id_deleted_top_parent(), true, true ));
		events.put( 33,new KpiEvent(messages.id_added_child_node(), true, true ));
		events.put( 33,new KpiEvent(messages.id_deleted_child_node(), true, true ));
		events.put( 34,new KpiEvent(messages.id_merging_device(), true, true ));
		events.put( 36,new KpiEvent(messages.id_device_unverified(), true, true ));
		events.put( 37,new KpiEvent(messages.id_merging_device_into(), true, true ));

		events.put( 38,new KpiEvent(messages.id_not_under_a_support(), true, true ));
		events.put( 41,new KpiEvent(messages.id_Error_occurred_while_processing_info(), true, true ));
		events.put( 42,new KpiEvent(messages.id_Unsupported_kpi_version(), true, true ));
		events.put( 43,new KpiEvent(messages.id_interim_kpi_version(), true, true ));
		events.put( 44,new KpiEvent(messages.id_error_connecting(), true, true ));
		events.put( 106,new KpiEvent(messages.id_trap(), true, true ));
		events.put( 107,new KpiEvent(messages.id_delete_change_name(), true, true ));
		events.put( 113,new KpiEvent(messages.id_LLDP_Device_moved(), true, true ));
		events.put( 114,new KpiEvent(messages.id_counter_for_IP(), true, true ));
	}

}
