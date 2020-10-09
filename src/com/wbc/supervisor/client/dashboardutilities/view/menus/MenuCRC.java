package com.wbc.supervisor.client.dashboardutilities.view.menus;

import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.SortMapByValue;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.SwitchErrorData;
import com.wbc.supervisor.shared.dashboardutilities.SwitchErrorInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.info.Info;

import java.util.Date;
import java.util.HashMap;

public class MenuCRC extends MenuBase implements IsWidget {


    private String type;
    private String period;
    private AsyncCallback asyncCallback;
    private FlexTable table = new FlexTable();
    private SwitchErrorInfo switchErrorData = null;
    private ExportWidget exportWidget = new ExportWidget();
    private String order = "ASC";


    public MenuCRC( String id, String s)
    {
        init();

    }

    public MenuCRC( String type, String period , AsyncCallback asyncCallback  )
    {
        this.type = type ;
        this.period =  period ;
        this.asyncCallback = asyncCallback;
        init();
    }

    private void init()
    {
        getData();
    }

    @Override
    public Widget asWidget() {

        container.add( flexOptions());
        container.add(exportWidget);
        container.add( table);
        return super.asWidget();

    }

   private FlexTable flexOptions()
    {

        FlexTable flexOptions = new FlexTable();
        StringComboBox sortBy = new StringComboBox();
        sortBy.add("Switch Ip");
        sortBy.add("Port Description");
        sortBy.add("Port");
        sortBy.add("Total");

        StringComboBox sortOrder = new StringComboBox();
        sortOrder.add("ASC");
        sortOrder.add("DESC");

        flexOptions.setWidget( 0,0, sortBy);
        flexOptions.setWidget( 0,1, sortOrder);

        sortOrder.setEmptyText( "ASC" );
        sortOrder.setAllowTextSelection( true );
        sortBy.setEmptyText( "Switch Ip" );
        sortOrder.selectAll();
        sortOrder.select( 0 );

        sortOrder.addSelectionHandler( Event -> sort(sortBy.getText(), sortOrder.getText()));
        sortBy.addSelectionHandler( Event -> sort(sortBy.getValue(), sortOrder.getText()));

        sortOrder.setTriggerAction( ComboBoxCell.TriggerAction.ALL );
        sortBy.setTriggerAction( ComboBoxCell.TriggerAction.ALL );

        flexOptions.setVisible(false);
		 return flexOptions;

    }

    private void updateTable( SwitchErrorInfo switchErrorInfo )
    {
        HTML switchIp = new HTML("SwitchIP");
        HTML port = new HTML("Port");
        HTML portDescription = new HTML("Port Description");
        HTML total = new HTML("Total");

        table.setWidget( 0,0, switchIp );
        table.setWidget( 0,1, port);
		table.setWidget( 0, 2, portDescription );

        //Sort Handlers on Headers
        switchIp.addClickHandler(Event -> sort("Switch Ip", order));
        port.addClickHandler(Event -> sort("Port", order));
        portDescription.addClickHandler(Event -> sort("Port Description", order));
        total.addClickHandler(Event -> sort("Total", order));

        table.getCellFormatter().addStyleName(
                0, 0,"FlexTable-ColumnLabelCell headerCell leftJustified");

        table.getCellFormatter().addStyleName(
                0, 1,"FlexTable-ColumnLabelCell headerCell leftJustified");
        table.getCellFormatter().addStyleName(
                0, 2,"FlexTable-ColumnLabelCell headerCell leftJustified");

        for(int i = 0 ; i< switchErrorInfo.getHeader().size(); i++)
       {
           int colNo = i+3;
           Date date = switchErrorInfo.getHeader().get( i );

           table.setWidget(0, colNo, new HTML( DashboardUtils.getFormattedDateWithTime(date)));

           table.getCellFormatter().addStyleName(
                   0, colNo,"FlexTable-ColumnLabelCell headerCell");
           table.getCellFormatter().addStyleName(
                   0, colNo+1,"FlexTable-ColumnLabelCell headerCell");

           int cell = table.getCellCount(0);

           int count = 0;

           for (String key: switchErrorInfo.getErrorMap().keySet()) {
               count = count+1;
               int row = count;
               SwitchErrorData switchErrorData = switchErrorInfo.getErrorMap().get(key);
               Anchor ipAddressLink = new Anchor(switchErrorData.getIp());
               table.setWidget( row, 0, ipAddressLink);
               table.setWidget( row, 1, new HTML(switchErrorData.getPort()+"") );
               table.setWidget( row, switchErrorData.getValues().size()+2, new HTML(switchErrorData.getValuesTotal()+"") );
               table.setWidget( row,  colNo, new HTML(switchErrorData.getValues().get( i )+"") );
               HTML htmlDesc = new HTML();
			   htmlDesc.setHTML( switchErrorData.getPortDesc() );
			   htmlDesc.setWordWrap( false );
               table.setWidget( row, 2, htmlDesc );

               ipAddressLink.addClickHandler( Event -> DashboardUtils.onIpSelection(switchErrorData.getIp(), switchErrorData.getDescid()) );

               table.getRowFormatter().addStyleName( row, "FlexTable-OddRow");
               table.getCellFormatter().addStyleName(
                       row, colNo,"FlexTable-ColumnLabelCell");

               table.getCellFormatter().addStyleName(
                       row, 0,"FlexTable-ColumnLabelCell leftJustified");
               table.getCellFormatter().addStyleName(
                       row, 1,"FlexTable-ColumnLabelCell leftJustified");
               table.getCellFormatter().addStyleName(
                       row, 2,"FlexTable-ColumnLabelCell leftJustified");
               table.getCellFormatter().addStyleName(
					   row, switchErrorData.getValues().size()+2,"FlexTable-ColumnLabelCell");

        }
       }

        table.setWidget( 0, switchErrorInfo.getHeader().size()+3, total );
        table.getCellFormatter().addStyleName(
                0, switchErrorInfo.getHeader().size()+2,"FlexTable-ColumnLabelCell headerCell");
        table.getCellFormatter().addStyleName(
                0, switchErrorInfo.getHeader().size()+3,"FlexTable-ColumnLabelCell headerCell");


    }

    private void getData()
    {

     		StringBuilder param = new StringBuilder();
			param.append( "?period=" ).append( period );
			param.append( "&type=" ).append( type );
			DashboardUtils.callServer( Constants.CRC, Constants.CRC_MESSAGE, param, RequestBuilder.GET, Constants.URL_CRC, null, new AsyncCallback<Response>()
			{
				@Override
				public void onFailure( Throwable caught )
				{
					new WarningMessageBox( "Report", caught.getLocalizedMessage() );
				}

				@Override
				public void onSuccess( Response response )
				{
					getCrcData( response.getText() );
				}
			} );

	 }

    private void getCrcData(String json)
    {
        supervisorServiceAsync rpcService = GWT.create( supervisorService.class );

        rpcService.getCrcData(json, new AsyncCallback<SwitchErrorInfo>() {
            @Override
            public void onFailure(Throwable caught) {

                Window.alert( caught.getMessage() );
                asyncCallback.onFailure( caught );
            }

            @Override
            public void onSuccess(SwitchErrorInfo switchErrorInfo) {
                  DashboardUtils.handleError(switchErrorInfo.getErrorInfo(), Constants.CRC, new AsyncCallback<Void>()
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
                          asyncCallback.onSuccess(null );
                          switchErrorData = switchErrorInfo;
                          sort("Switch Ip", "ASC");

                      }
                  } );

            }

        });
    }


    private void sort( String sortBy, String sortOrder ){

       if(sortBy == null || sortBy.isEmpty())
	   {
		     sortBy = "Switch Ip";
	   }
       if(sortOrder == null || sortOrder.isEmpty())
       {
       		sortOrder = "ASC";
       }
		HashMap<String, SwitchErrorData> sorted = SortMapByValue.sort( switchErrorData.getErrorMap(), sortBy, sortOrder );
		switchErrorData.setErrorMap( sorted );

		updateTable( switchErrorData );
        DashboardUtils.applyDataRowStyles(table);
        exportWidget.export(null, switchErrorData);

        if(order.equalsIgnoreCase("ASC"))
            order = "DESC";
        else order = "ASC";
    }


}
