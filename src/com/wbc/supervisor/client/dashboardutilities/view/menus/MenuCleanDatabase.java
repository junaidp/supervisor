package com.wbc.supervisor.client.dashboardutilities.view.menus;

import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WizardFieldLabel;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import java.util.Date;

public class MenuCleanDatabase extends MenuBase implements IsWidget
{

	private TextField textField = null;
	private TextButton createDb = null;
	private CheckBox overWrite;

	@Override
	public Widget asWidget() {

		container.clear();
		container.add( layout());
		return super.asWidget();

	}

	public MenuCleanDatabase()
	{

	}

	private Widget layout()
	{
		VerticalLayoutContainer cleanDbContainer = new VerticalLayoutContainer();
		textField = new TextField(  );
		textField.setWidth( 300 );
		textField.setEmptyText( "Enter Database Name" );
		createDb = new TextButton( "Create Database" );
		overWrite = new CheckBox(  );
		createDb.setSize( "120px", "30px" );

		textField.setText( "CleanDatabaseWithScanRanges_"+ DashboardUtils.getFormattedDate(new Date()));
		cleanDbContainer.add( new WizardFieldLabel( textField, "Database Name" , 300));
		cleanDbContainer.add( new WizardFieldLabel( overWrite, "Overwrite new backup database file if it exists" , 300) );
		cleanDbContainer.add( new WizardFieldLabel( createDb, "" ,300,"") );

		createDb.addSelectHandler( Event -> createCleanDatabase() );
		return  cleanDbContainer;
	}

	private void createCleanDatabase()
	{
		StringBuilder param = new StringBuilder();
		param.append("?dbName=").append( textField.getText() );
		param.append("&overWrite=").append( overWrite.getValue() );

		DashboardUtils.callServer(Constants.CLEAN_DATABASE, Constants.CLEANING_DATABASE_MESAGE, param, RequestBuilder.POST, Constants.URL_CREATE_CLEAN_DB, null,  new AsyncCallback<Response>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox("Database Creation", "error" + caught);

			}

			@Override
			public void onSuccess( Response response )
			{
				if (200 == response.getStatusCode() && response.getText().length()>2) {

					DashboardUtils.convertErrorTextJson(response.getText(), new AsyncCallback<ErrorInfo>() {
						@Override
						public void onFailure(Throwable throwable) {
							new WarningMessageBox( "Database Creation", "Error opening errorInfo");

						}

						@Override
						public void onSuccess(ErrorInfo errorInfo) {
							if(!errorInfo.getErrorText().isEmpty())
							{
								new WarningMessageBox( "Database Creation", errorInfo.getErrorText() );

							}
							else {
								MessageBox msgBox = new MessageBox( "Database Creation", errorInfo.getResult() );
								msgBox.show();
							}
						}
					});

				} else {

					new WarningMessageBox("Database Creation", "No success :" + response.getStatusCode() +""+ response.getText());
				}
			}
		} );
	}

}
