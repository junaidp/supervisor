package com.wbc.supervisor.client.dashboardutilities.utils;

import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WizardFieldLabel;
import com.wbc.supervisor.shared.dashboardutilities.Globals;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.GeneralInfo;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.SwtichprobeReportInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.http.client.*;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.logging.client.SimpleRemoteLogHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanCodex;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class DashboardUtils
{
	private static SimpleRemoteLogHandler remoteLog = new SimpleRemoteLogHandler();
	public static String getFormattedDateWithTime( Date date )
	{
		DateTimeFormat fmt = DateTimeFormat.getFormat( "MM/dd . H:mm" );
		String formattedDate = fmt.format( date );
		formattedDate = formattedDate.replace( ".", "<br>" );
		return formattedDate;
	}

	public static String getFormattedDateWithTimeWithUnderscore( Date date )
	{
		DateTimeFormat fmt = DateTimeFormat.getFormat( "yyyyMMdd_Hmm" );
		String formattedDate = fmt.format( date );
		formattedDate = formattedDate.replace( ".", "<br>" );
		return formattedDate;
	}

	public static String getFormattedDateWithTimeJanFormat( Date date )
	{
		DateTimeFormat fmt = DateTimeFormat.getFormat( "MMM d,yyyy H:mm" );
		String formattedDate = fmt.format( date );
		return formattedDate;
	}

	public static String getFormattedDate( Date date )
	{
		DateTimeFormat fmt = DateTimeFormat.getFormat( "yyyyMMdd" );
		String formattedDate = fmt.format( date );
		formattedDate = formattedDate.replace( ".", "<br>" );
		return formattedDate;
	}

	public static SwtichprobeReportInfo getAutoBeanSwitchProbeReportInfo( Response response )
	{
		DashboardFactory factory = GWT.create( DashboardFactory.class );
		AutoBean<SwtichprobeReportInfo> autoBean = factory.switchProbeReportInfo();
		autoBean.as();

		AutoBean<SwtichprobeReportInfo> bean = AutoBeanCodex.decode( factory, SwtichprobeReportInfo.class, response.getText() );
		return bean.as();
	}

	public static GeneralInfo getAutoBeanGeneralInfo( Response response )
	{
		DashboardFactory factory = GWT.create( DashboardFactory.class );
		AutoBean<GeneralInfo> autoBean = factory.generalInfo();
		autoBean.as();

		AutoBean<GeneralInfo> bean = AutoBeanCodex.decode( factory, GeneralInfo.class, response.getText() );
		return bean.as();
	}

	public static AutoProgressMessageBox getProgressMessageBox( String heading, String progressMessage )
	{
		AutoProgressMessageBox messageBoxProgress = new AutoProgressMessageBox( heading );
		messageBoxProgress.auto();
		messageBoxProgress.getProgressBar().setDuration( 5000 );
		messageBoxProgress.setProgressText( progressMessage );
		messageBoxProgress.setPredefinedButtons( Dialog.PredefinedButton.CANCEL );
		messageBoxProgress.show();
		return messageBoxProgress;
	}




	public static void callServer(final String progressMessageBoxHeading, String progressMessage, StringBuilder param, RequestBuilder.Method messageType, final String urlcall, String requestData, final AsyncCallback<Response> asyncCallback )
	{
		final AutoProgressMessageBox messageBoxProgress = DashboardUtils.getProgressMessageBox( progressMessageBoxHeading, progressMessage );

		try
		{

			String url = Constants.PRE_URL + Globals.HOST_IP_ADDRESS + ":" + Globals.WBC_PORT + Constants.URL_WBC_SERVER_LOCAL; //HOSTIPADDRESS is witnin this constant
			logInfo("Calling " + url + urlcall  +" with params " +param +" and Request Type: "+ messageType);
			RequestBuilder builder = new RequestBuilder( messageType, URL.encode( url + urlcall + param ) );

			boolean test = true;
			if (test) {
				builder.setHeader("WBC-custom", "Test1");
				builder.setHeader("Access-Control-Allow-Origin", "*");
				// builder.setHeader("Content-Type", "application/x-www-form-urlencoded" );
			}

			boolean showInfo = true;
			if (showInfo) {
				// JWM Test
				boolean usePassword = false;
				if (usePassword) {
					builder.setUser("admin");
					builder.setPassword("wbcins");
				}
				StringBuilder sb = new StringBuilder();
				sb.append("--- callServer headers before ---");
				sb.append("\r\n");

				String name = "sec-fetch-site";
				sb.append(name);
				sb.append(" = ");
				sb.append(builder.getHeader(name));
				sb.append("\r\n");

				name = "sec-fetch-mode";
				sb.append(name);
				sb.append(" = ");
				sb.append(builder.getHeader(name));
				sb.append("\r\n");

				name = "sec-fetch-dest";
				sb.append(name);
				sb.append(" = ");
				sb.append(builder.getHeader(name));
				sb.append("\r\n");

				name = "WBC-custom";
				sb.append(name);
				sb.append(" = ");
				sb.append(builder.getHeader(name));
				sb.append("\r\n");

				name = "Access-Control-Allow-Origin";
				sb.append(name);
				sb.append(" = ");
				sb.append(builder.getHeader(name));
				sb.append("\r\n");

				logInfo(sb.toString());
				// Now we can set something if we want
			}


			builder.sendRequest( requestData, new RequestCallback()
			{
				public void onResponseReceived(Request request, final Response response )
				{
					messageBoxProgress.hide();
					logInfo("Response Received from wbcserver code "  +  response.getStatusCode() + "  " +  response.getStatusText() );

					convertErrorTextJson(response.getText(), new AsyncCallback<ErrorInfo>() {
						@Override
						public void onFailure(Throwable caught) {
							logInfo("Not an Error Object");
							asyncCallback.onSuccess( response );
						}

						@Override
						public void onSuccess(ErrorInfo errorInfo) {
							if(errorInfo != null)
						    logInfo("Error Info: "+ errorInfo.getErrorText()+":"+ errorInfo.getResult());

							if (response.getStatusCode() != 200)
							{
								logWarning("Response Status is "+ response.getStatusCode());
                                if(errorInfo != null)
								new WarningMessageBox( "Server call Failed", errorInfo.getErrorText() );
								return;
							}
							else
							{
								logInfo("Response StatusCode "+ response.getStatusCode());
								logInfo("ErrorInfo:"+ errorInfo.getResult() +", " + errorInfo.getErrorText());
								if (errorInfo != null && errorInfo.getResult() != null && !errorInfo.getResult().equalsIgnoreCase( "ok" ))
								{
									logWarning("ErrorInfo: "+ errorInfo.getErrorText()+ ": "+ errorInfo.getResult());
									new WarningMessageBox( progressMessageBoxHeading , errorInfo.getErrorText()+ ": "+ errorInfo.getResult());
									return;
								}

							}
							logInfo("Sending response to the relevent Menu to further process and populate view " + urlcall);
							asyncCallback.onSuccess( response );
						}
					});
				}

				public void onError( Request request, Throwable exception )
				{
					logError(exception.getStackTrace()+"");
					messageBoxProgress.hide();
					asyncCallback.onFailure( exception );

				}

			} );
		}
		catch ( RequestException e )
		{
			logError(e.getStackTrace()+"");
			messageBoxProgress.hide();
			new WarningMessageBox( "Server call", "no server " + e );

		}
		catch ( Exception e )
		{
			logError(e.getStackTrace()+"");


			messageBoxProgress.hide();
			new WarningMessageBox( "Server call", "no server " + e );

		}
	}

	public static void handleError(ErrorInfo errorInfo, String heading, AsyncCallback<Void> asyncCallback )
	{
		if (errorInfo != null && errorInfo.getResult() != null)
		{
			logInfo("Inside DashboardUtils.handleError , errorInfo.getResult():  "+ errorInfo.getResult());
			if (errorInfo.getErrorText().equalsIgnoreCase( "warning" ))
			{
				logWarning("Inside DashboardUtils.handleError , errorInfo:  "+ errorInfo.getErrorText());
				new WarningMessageBox( heading, errorInfo.getResult() );
				if(asyncCallback != null)asyncCallback.onSuccess( null );
			}
			else if (errorInfo.getErrorText().equalsIgnoreCase( "error" ))
			{
				logError("Inside DashboardUtils.handleError , errorInfo:  "+ errorInfo.getErrorText());
				new WarningMessageBox( heading, errorInfo.getResult() );
				if(asyncCallback != null)asyncCallback.onFailure( null );
			}
			else if (!errorInfo.getResult().equalsIgnoreCase( "ok" ))
			{
				logError("Inside DashboardUtils.handleError , errorInfo:  "+ errorInfo.getErrorText());
				new WarningMessageBox( heading,  errorInfo.getErrorText()+": "+ errorInfo.getResult() );
				if(asyncCallback != null)asyncCallback.onFailure( null );
			}
			else
			{
				if(asyncCallback != null)asyncCallback.onSuccess( null );
			}
		}
		else
		{
			if(asyncCallback != null)asyncCallback.onSuccess( null );
		}
	}

	static Long toNumeric( String ip )
	{
		String[] s = ip.split( "\\." );
		long ipLong = ( Long.parseLong( s[ 0 ] ) << 24 ) + ( Long.parseLong( s[ 1 ] ) << 16 ) + ( Long.parseLong( s[ 2 ] ) << 8 ) + ( Long.parseLong( s[ 3 ] ) );

		return ipLong;
	}

	public static void applyDataRowStyles( FlexTable table )
	{
		HTMLTable.RowFormatter rf = table.getRowFormatter();

		for ( int row = 1; row < table.getRowCount(); ++row )
		{
			if (( row % 2 ) != 0)
			{
				rf.addStyleName( row, "FlexTable-OddRow" );
			}
			else
			{
				rf.addStyleName( row, "FlexTable-EvenRow" );
			}
		}
	}

	public static void setDefaultGridStyle( Grid<?> grid )
	{
		grid.setAllowTextSelection(false);
		grid.getView().setStripeRows(true);
		grid.getView().setColumnLines(true);
		grid.setBorders(false);
		grid.setColumnReordering(true);
		grid.setWidth( "100%" );
		grid.getView().setForceFit( true );
		grid.getView().setAutoFill(true);
	resize(grid);

	}

	public static String getFormattedRoundNumber(Double value)
	{
		return NumberFormat.getFormat(".00").format(value);

	}

	public static void onIpSelection( String ip, int descId )
	{
		Window.open( "http://"+ Globals.HOST_IP_ADDRESS +":8765/iv3/#/network/topology/topology-graph?selected="+descId+"", "" , "");
	}

	public static void resize(final Widget v )
	{
		Window.addResizeHandler( new ResizeHandler()
		{
			@Override
			public void onResize( ResizeEvent event )
			{
				v.setWidth( Window.getClientWidth()-10+"px" );
			}
		} );
	}

	public static void logInfo(String message)
	{
		GWT.log(message);
		remoteLog.publish(new LogRecord( Level.INFO, message));

	}

	public static void logWarning(String message)
	{
		GWT.log(message);
		remoteLog.publish(new LogRecord( Level.WARNING, message));

	}

	public static void logError(String message)
	{
		GWT.log(message);
		remoteLog.publish(new LogRecord( Level.SEVERE, message));

	}

	public static void convertErrorTextJson(String json, final AsyncCallback<ErrorInfo> asyncCallback)
	{
		supervisorServiceAsync rpcService = GWT.create( supervisorService.class );
		rpcService.getErrorText( json, new AsyncCallback<ErrorInfo>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				logError("Error in converting json for Error Txt "+ caught.getLocalizedMessage() );
				asyncCallback.onFailure( caught);
			}

			@Override
			public void onSuccess( ErrorInfo result )
			{
				logInfo( "Success in converting Json for error text" + result);
				asyncCallback.onSuccess( result );
			}
		} );
	}

	public static String getFullUrl(String url)
	{
		return Constants.PRE_URL+ Globals.HOST_IP_ADDRESS +":" + Globals.WBC_PORT + Constants.URL_WBC_SERVER_LOCAL + url;
	}

	public static String getFullUrl(String url, String ip)
	{
		return Constants.PRE_URL+ ip +":" + Globals.WBC_PORT + Constants.URL_WBC_SERVER_LOCAL + url;
	}

	public static void export(AsyncCallback<String> asyncCallback)
	{

		Dialog fileNameSelectionDialog = new Dialog();
		TextField text = new TextField();
		WizardFieldLabel fieldLabel = new WizardFieldLabel(text, "FileName");
		fieldLabel.getElement().getStyle().setPadding(10, Style.Unit.PX);
		fileNameSelectionDialog.add(fieldLabel);
		fileNameSelectionDialog.show();
		TextButton save = new TextButton("Export");
		save.addSelectHandler(new SelectEvent.SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				asyncCallback.onSuccess(text.getText());
				fileNameSelectionDialog.hide();
			}
		});
		fileNameSelectionDialog.setPredefinedButtons();
		fileNameSelectionDialog.setHeading("Export csv from Grid");
		fileNameSelectionDialog.addButton(save);

	}


}

