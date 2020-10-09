package com.wbc.supervisor.client.dashboardutilities.view.menus;

import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.UploadFile;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.DialogCustom;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ProgressMessageBox;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WizardFieldLabel;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.logging.client.DefaultLevel;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

public class MenuUpdateIntravueDeviceInfo extends MenuBase implements IsWidget
{

	private CheckBox overWrite = null;

	@Override
	public Widget asWidget() {
		container.clear();
		container.setWidth( "100%" );
		container.add( layout() );
		return super.asWidget();

	}

	public MenuUpdateIntravueDeviceInfo()
	{


	}

	private VerticalLayoutContainer layout( )
	{
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		overWrite = new CheckBox();
		overWrite.setBoxLabel("Update names which already have values");
		//container.add(new WizardFieldLabel(new HTML("Update"), "Update names which already have values"), new VerticalLayoutContainer.VerticalLayoutData(500,-1));
		container.add( UploadFile.get().fileUploadPanel(  DashboardUtils.getFullUrl(Constants.URL_UPLOAD_UPDATE_INTRAVUE_DEVICE), "",overWrite, new AsyncCallback<String>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox(Constants.UPDATE_INTRAVUE_DEVICE_INFO, "Error in uploadFile :" + caught.getMessage());
				logWarning("Error in uploading updateIntravueDeviceInfo:"+caught.getMessage() );
			}

			@Override
			public void onSuccess( String result )
			{
				logInfo("UpdateIntravueDeviceInfo :"+ result+" uploaded");
				new MessageBox("File Upload","File uploaded Successfully");
				DashboardUtils.convertErrorTextJson(result, new AsyncCallback<ErrorInfo>() {
					@Override
					public void onFailure(Throwable caught) {
						logWarning("Error in converting Json for fileUpload:" + caught.getMessage());
						updateNames();
					}

					@Override
					public void onSuccess(ErrorInfo result) {
						DashboardUtils.handleError(result, "File upload", new AsyncCallback<Void>() {
							@Override
							public void onFailure(Throwable caught) {

							}

							@Override
							public void onSuccess(Void result) {
								updateNames();
							}
						});
					}
				});
			}


		} ));

		return container;
	}

	private void updateNames() {
		StringBuilder param = new StringBuilder();
		param.append( "?overWrite=" ).append( overWrite.getValue() );
		DashboardUtils.callServer(Constants.UPDATE_INTRAVUE_DEVICE_INFO, Constants.UPDATING_INTRAVUE_DEVICE_INFO, param, RequestBuilder.POST, Constants.URL_UPDATE_INTRAVUE_DEVICE, null, new AsyncCallback<Response>() {
			@Override
			public void onFailure(Throwable caught) {
				new WarningMessageBox(Constants.UPDATE_INTRAVUE_DEVICE_INFO, caught.getLocalizedMessage());
			}

			@Override
			public void onSuccess(Response result) {
					DashboardUtils.convertErrorTextJson(result.getText(), new AsyncCallback<ErrorInfo>() {
						@Override
						public void onFailure(Throwable caught) {

						}

						@Override
						public void onSuccess(ErrorInfo errorInfo) {
							DashboardUtils.handleError(errorInfo, Constants.UPDATE_INTRAVUE_DEVICE_INFO, new AsyncCallback<Void>() {
								@Override
								public void onFailure(Throwable caught) {

								}

								@Override
								public void onSuccess(Void result) {
									HTML msg = new HTML(errorInfo.getErrorText());
									DialogCustom dialog = new DialogCustom(msg, Constants.UPDATE_INTRAVUE_DEVICE_INFO);
								}
							});
						}
					});
			}
		});
	}

}
