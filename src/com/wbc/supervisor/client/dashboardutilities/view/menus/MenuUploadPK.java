package com.wbc.supervisor.client.dashboardutilities.view.menus;

import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.UploadFile;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;

public class MenuUploadPK extends MenuBase implements IsWidget
{

	@Override
	public Widget asWidget() {
		container.clear();
		container.setWidth( "100%" );
		container.add( layout() );
		return super.asWidget();

	}

	public MenuUploadPK()
	{


	}

	private VerticalLayoutContainer layout( )
	{
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		container.add( UploadFile.get().fileUploadPanel( GWT.getModuleBaseURL()+"fileupload", Constants.PK_FILE, null, new AsyncCallback<String>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				DashboardUtils.logError("Error in uploadFile :" + caught.getMessage());
				logWarning("Error in uploading pkFile:"+caught.getMessage() );
			}

			@Override
			public void onSuccess( String result )
			{
				logInfo("PkFile:"+ result+" uploaded");
				new MessageBox("Pk Upload","File uploaded Successfully");
			}
		} ));

		return container;
	}

}
