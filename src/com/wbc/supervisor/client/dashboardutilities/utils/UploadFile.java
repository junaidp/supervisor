package com.wbc.supervisor.client.dashboardutilities.utils;


import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Hidden;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.box.AlertMessageBox;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SubmitCompleteEvent;
import com.sencha.gxt.widget.core.client.event.SubmitEvent;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.FileUploadField;
import com.sencha.gxt.widget.core.client.form.FormPanel;
import com.sencha.gxt.widget.core.client.info.Info;

public class UploadFile
{
	private FormPanel form = null;
	private boolean loadingPrevioiuslySavedFile = false;


	public static UploadFile get()
	{
		return UploadFile.INSTANCE;
	}

	public static final UploadFile INSTANCE = GWT.create(UploadFile.class);
	AutoProgressMessageBox messageBoxProgress = null;
	private AsyncCallback<String> asyncCallback;
	private String selectedFile = "";
	private TextButton submitButton = null;
	private FileUploadField file = null;

	public VerticalLayoutContainer fileUploadPanel(String menuUrl, String fileName, CheckBox overWrite, AsyncCallback<String> asyncCallback){

		file = new FileUploadField();
		this.asyncCallback = asyncCallback;

		file.setName("file");
		file.setAllowBlank(false);
		file.setWidth(300);
		TextButton resetButton = new TextButton("Reset");
		resetButton.addSelectHandler(new SelectEvent.SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				form.reset();
				file.reset();
			}
		});

		form = new FormPanel();
		//form.setWidth( 500 );
		DashboardUtils.logInfo("Uploading file to : "+ menuUrl);
		form.setEncoding(FormPanel.Encoding.MULTIPART);
		form.setMethod( FormPanel.Method.POST);
		form.setAction( menuUrl);
		//form.setAction(GWT.getModuleBaseURL()+"fileupload");
		HorizontalLayoutContainer panel = new HorizontalLayoutContainer();
		form.setWidget(panel);

		file.addValueChangeHandler(new ValueChangeHandler<String>() {
			@Override
			public void onValueChange(ValueChangeEvent<String> event) {
				selectedFile = file.getTextField().getText();
				asyncCallback.onSuccess("File Selected: " + selectedFile);
			}
		});

		panel.add(new FieldLabel(file, "Upload File"), new HorizontalLayoutContainer.HorizontalLayoutData(-1,-1));

		 submitButton = new TextButton("Upload File");
		 submitButton.setEnabled( false );
		 submitButton.addSelectHandler(new SelectEvent.SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {


				if (!form.isValid() || loadingPrevioiuslySavedFile) {
					return;
				}
				DashboardUtils.logInfo("formAction: "+ form.getAction());
				DashboardUtils.logInfo("selected file :"+ file !=null && file.getTextField()!= null ? file.getTextField().getText(): file+"");
				messageBoxProgress = DashboardUtils.getProgressMessageBox("File Upload", "File upload in progress");
				selectedFile = file.getTextField().getText();

				if(overWrite != null) {
					Hidden hiddenOverWrite = new Hidden("overWrite", overWrite.getValue().toString());
					panel.add(hiddenOverWrite);
				}
				Hidden hiddenSourceFile = new Hidden("sourceFilename", selectedFile);
				panel.add(hiddenSourceFile);

				form.submit();
				submitButton.setEnabled(false);
				submitButton.setText("Loading...");

			}
		});

		form.addSubmitHandler(new SubmitEvent.SubmitHandler() {
			@Override
			public void onSubmit(SubmitEvent event) {
				DashboardUtils.logInfo("File Uploading Started");

			}
		});


		form.addSubmitCompleteHandler(new SubmitCompleteEvent.SubmitCompleteHandler() {
				public void onSubmitComplete(SubmitCompleteEvent event) {
				messageBoxProgress.hide();
				DashboardUtils.logInfo("File Uploaded with response: " +event.getResults());
				try {
					String result = event.getResults().substring(event.getResults().indexOf(">")+1, event.getResults().lastIndexOf("<") ) ;

					DashboardUtils.convertErrorTextJson(result, new AsyncCallback<ErrorInfo>() {
						@Override
						public void onFailure(Throwable caught) {
							if(caught != null)
							new WarningMessageBox("Upload File", caught.getMessage());
							return;
						}

						@Override
						public void onSuccess(ErrorInfo result) {
							DashboardUtils.handleError(result, "Upload File",null);
						}
					});
				}catch(Exception ex)
				{
					DashboardUtils.logInfo("Return from upload is no ErrorInfo object");
					//MessageBox box = new MessageBox("Upload File", "File Upload Completed");
					//box.show();
					Info.display("Upload File", "File Upload Completed");
				}

					submitButton.setEnabled(true);
					submitButton.setText("Upload File");
					if(messageBoxProgress!= null) messageBoxProgress.hide();
					asyncCallback.onSuccess(selectedFile);

				}
		});

		file.addChangeHandler(new ChangeHandler() {
			@Override
			public void onChange( ChangeEvent event) {
				DashboardUtils.logInfo("File Changed: ");
				if(! file.getName().isEmpty())
				{
					submitButton.setEnabled( true );
				}
			}
		});


		VerticalLayoutContainer v = new VerticalLayoutContainer();
		if(overWrite != null)
		v.add(overWrite);
		HorizontalLayoutContainer h = new HorizontalLayoutContainer();
		h.add(form, new HorizontalLayoutContainer.HorizontalLayoutData(410,1, new Margins(0,0,0,0)));
		h.add(submitButton, new HorizontalLayoutContainer.HorizontalLayoutData(90,26, new Margins(0,0,0,0)));
		v.add(h, new VerticalLayoutContainer.VerticalLayoutData(100, -1, new Margins(5,5,5,5)));
		v.setHeight(400);
		return v;
	}

	public TextButton getSubmitButton() {
		return submitButton;
	}

	public void setLoadingPrevioiuslySavedFile(boolean loadingPrevioiuslySavedFile) {
		this.loadingPrevioiuslySavedFile = loadingPrevioiuslySavedFile;
	}

	public FileUploadField getFile() {
		return file;
	}


}
