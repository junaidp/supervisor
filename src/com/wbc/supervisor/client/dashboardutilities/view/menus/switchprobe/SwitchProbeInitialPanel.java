package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.CustomComboBoxWidget;
import com.wbc.supervisor.shared.dashboardutilities.Globals;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.UploadFile;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.IntravueSwitchInfoData;
import com.wbc.supervisor.shared.dashboardutilities.WbcFileInfo;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.SwitchProbeReportInfoImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.core.client.util.ToggleGroup;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.*;

import java.util.ArrayList;
import java.util.Date;


public class SwitchProbeInitialPanel extends VerticalLayoutContainer
{

	private final String ipAddress = "IP Address";
	private final String folder = "Folder for Report";
	private SwitchesPanel switchesPanel = null;

	private AsyncCallback<SwitchProbeReportInfoImpl> asyncCallback;
	private Radio radioExistingFile = new Radio(  );
	private Radio radioFromSwitch = new Radio(  );
	private Radio radioFromPreviouslySavedFile= new Radio(  );
	private String uploadedFile = "";
	private VerticalLayoutContainer uploadContainer = new VerticalLayoutContainer();
	private String selectedSwitchIP = "";
	private String hostName = "";
	private String community = "";
	private ToggleGroup group = null;
	private String switchProbePath = "";
	supervisorServiceAsync rpcService = null;
	private TextField path = null;
	private ComboBox<WbcFileInfo> listPreviouslyOpenedFiles = null;
	private UploadFile uploadWidget = null;
	HorizontalLayoutContainer hSavedFile = new HorizontalLayoutContainer();


	public SwitchProbeInitialPanel( AsyncCallback<SwitchProbeReportInfoImpl> asyncCallback )
	{
		//setVisible(false);
		this.asyncCallback = asyncCallback;
		rpcService = GWT.create( supervisorService.class );
		getAppDirPath();
		getSwitchprobeLocalFiles();

	}

	private void layout( )
	{

		group = new ToggleGroup();
		path = new TextField();

		radioExistingFile.setBoxLabel("Create from existing file on browser's host");
		radioFromSwitch.setBoxLabel("Generate File from switch and then report");
		radioFromPreviouslySavedFile.setBoxLabel("Load from previously saved files on server");

		add(previouslySavedFileLayout());

		//add(new WizardFieldLabel(listPreviouslyOpenedFiles, "Load from previously saved files"));
		hSavedFile.add(radioFromPreviouslySavedFile, new HorizontalLayoutContainer.HorizontalLayoutData(-1,-1, new Margins(0,0,0,0)));
		add(hSavedFile, new VerticalLayoutData(-1,-1, new Margins(0,0,20,0)));
		add(radioExistingFile);
		add(radioFromSwitch );
		add(uploadContainer);

		radioFromPreviouslySavedFile.setValue( true );
		group.add(radioExistingFile);
		group.add(radioFromSwitch);
		group.add(radioFromPreviouslySavedFile);

		group.addValueChangeHandler( Event -> showPanel(radioExistingFile.getValue(), uploadContainer, switchesPanel ));

		 uploadWidget = new UploadFile();

		uploadContainer.add( uploadWidget.fileUploadPanel( DashboardUtils.getFullUrl(Constants.URL_FILE_UPLOAD), "", null, new AsyncCallback<String>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				DashboardUtils.logError("Error in uploadFile :" + caught.getMessage());
			}

			@Override
			public void onSuccess( String result )
			{
				if(result.contains("File Selected")){
					uploadedFile = result.substring(result.indexOf(":")+2);
					//hEditFiledAndLabel.setVisible(true);
					String fileName = getFileName("");
					path.setText(fileName.substring(0, fileName.lastIndexOf(".")));

				}
				else {
					uploadedFile = result;
					if (result.endsWith("json"))
						getSwitchProbeSavedReport();
					else
						getSwitchData(Constants.URL_SWITCH_PROBE_REPORT);
				}

			}
		} ));


		uploadWidget.getSubmitButton().setEnabled(true);
		uploadWidget.getSubmitButton().addSelectHandler(Event -> onFileSelection(Event));
		uploadWidget.setLoadingPrevioiuslySavedFile(true);
		//add(new WizardFieldLabel( buttonStart, "" , "") );

	}

	private FlexTable previouslySavedFileLayout() {
		FlexTable hEditFiledAndLabel = new FlexTable();

		FieldLabel text = new FieldLabel();
		FieldLabel dotJson = new FieldLabel();
		FieldLabel probePath = new FieldLabel();
		dotJson.setContent(".json");
		text.setContent("Result saving for future use on server at");
		probePath.setContent(switchProbePath);
		probePath.setLabelWordWrap(false);
		text.setLabelWordWrap(false);
		probePath.setToolTip(probePath.getContent());


		hEditFiledAndLabel.setWidget(0,0,text);
		hEditFiledAndLabel.setWidget(0,1,probePath);
		hEditFiledAndLabel.setWidget(0,2,path);
		hEditFiledAndLabel.setWidget(0,3,dotJson);

		probePath.setLabelSeparator("");
		dotJson.setLabelSeparator("");
		probePath.setWidth(300);
		text.setWidth(262);
		path.setWidth(420);
		return hEditFiledAndLabel;
	}

	private void onFileSelection(SelectEvent event) {
		if(radioFromPreviouslySavedFile.getValue() && !listPreviouslyOpenedFiles.getText().isEmpty() && !listPreviouslyOpenedFiles.getText().equalsIgnoreCase("Select File")) {
			//uploadedFile = listPreviouslyOpenedFiles.getText().substring(0, listPreviouslyOpenedFiles.getText().indexOf("-"));
			uploadedFile= 	listPreviouslyOpenedFiles.getValue().getName();
				//uploadedFile = listPreviouslyOpenedFiles.getText().substring(0, listPreviouslyOpenedFiles.getText().indexOf(" "));
			DashboardUtils.logInfo("Loading switchprobe file from previously saved file:" + uploadedFile );
			getSwitchProbeSavedReport();
			}


	}

	private void getSwitchProbeSavedReport() {
		AutoProgressMessageBox msg = new AutoProgressMessageBox("SwitchProbe Saved Report", "Generating SwitchProbe Report");
		msg.show();

		rpcService.readFile("switchprobe",uploadedFile, new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				new WarningMessageBox("Error reading saved SwitchProbe file", caught.getLocalizedMessage());
				msg.hide();
			}

			@Override
			public void onSuccess(String result) {
				msg.hide();
				getSwitchProbeReportInfo(result, "AlreadyUploaded");
			}
		});
	}

	private void addSwitchesPanel() {
		switchesPanel = new SwitchesPanel();
		//hEditFiledAndLabel.setVisible(true);
		add(switchesPanel);

		switchesPanel.getIpField().addKeyUpHandler(Event -> setFileName(switchesPanel.getIpField().getText()));


		switchesPanel.getSwitches(radioFromSwitch, group, new AsyncCallback<IntravueSwitchInfoData>() {
			@Override
			public void onFailure(Throwable caught) {
				DashboardUtils.logWarning("In Failure from switchesPanel.getSwitches " + caught.getMessage());
			}

			@Override
			public void onSuccess(IntravueSwitchInfoData result) {
				hostName = result.getName();
				if(result.getCommunity().equalsIgnoreCase("ONSELECTION")){
					setFileName(result.getIp());
					//path.setText(fileName.substring(0, fileName.lastIndexOf(".")));
					return;
				}
				selectedSwitchIP = result.getIp();
				community = result.getCommunity();

				DashboardUtils.logInfo("Going to get selected Switch Data , IP:" + result.getIp() +", Community:"+ result.getCommunity());
				getSwitchData(Constants.URL_SWITCH_PROBE_REPORT_REMOTE);
			}
		});
	}

	private void setFileName(String ip) {
		DelayedTask delay = new DelayedTask() {
			@Override
			public void onExecute() {
				if(ip.isEmpty()){
					path.clear();
					return;
				}
				String tempHostname = Globals.HOST_NAME;
				tempHostname.replace(" ", "_");
				String fileName = tempHostname +"_"+ip+"_"+ DashboardUtils.getFormattedDateWithTimeWithUnderscore(new Date()) +"_switchprobe";
				path.setText(fileName);

			}
		};
		delay.delay(200);
	}

	private void showPanel(Boolean radioExistingFileSelected, Widget uploadContainer, SwitchesPanel switchesPanel) {
		boolean previousOrExistingSelected = radioExistingFileSelected || radioFromPreviouslySavedFile.getValue();
		uploadContainer.setVisible(previousOrExistingSelected);
		if(!previousOrExistingSelected){
			if(switchesPanel == null)
			addSwitchesPanel();
		}
		if(switchesPanel != null)
		switchesPanel.setVisible(!previousOrExistingSelected);

		path.clear();
		uploadWidget.getFile().clear();
		listPreviouslyOpenedFiles.setEnabled(radioFromPreviouslySavedFile.getValue());


		uploadWidget.setLoadingPrevioiuslySavedFile(false);
		if(radioFromPreviouslySavedFile.getValue()) uploadWidget.setLoadingPrevioiuslySavedFile(true);
	}

	private void getSwitchData(String url)
	{

		StringBuilder param = new StringBuilder();
		if(radioFromSwitch.getValue() || (switchesPanel !=null && switchesPanel.radioNotFromList.getValue())) {
			String ipAddress = selectedSwitchIP ; //+ "_" + DashboardUtils.getFormattedDate(new Date()) + "_probe.txt";
			param.append("?ip=").append(ipAddress);
			param.append("&comm=").append(community);
		}
		else {
			param.append("?reportName=").append(Globals.HOST_IP_ADDRESS);
			param.append("&overWrite=").append("true");
			param.append("&bGetFromSwitch=").append(radioFromSwitch.getValue());
		}

		DashboardUtils.callServer( Constants.SWITCH_PROBE, Constants.SWITCH_PROBE_MESAGE, param, RequestBuilder.GET, url, null, new AsyncCallback<Response>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox( Constants.SWITCH_PROBE, "Error in Generating Switch Probe Report"+ caught.getMessage() );
			}

			@Override
			public void onSuccess( Response response )
			{
				//ipAddress will only have value if report is generating from switch
				String ip = "";
				if(radioFromSwitch.getValue())
					ip = selectedSwitchIP;

				getSwitchProbeReportInfo(response.getText(), path.getText()+".json");

			}
		});

	}

	private String getFileName(String ip) {
		String fileName = "";
		if(ip.isEmpty()) {
			String uploadedFileWithoutExtension = uploadedFile.substring(0, uploadedFile.lastIndexOf("."));
			fileName = uploadedFileWithoutExtension + ".json";
		}
		else
			fileName = hostName+"_"+ip+"_"+ DashboardUtils.getFormattedDate(new Date())+"switchprobe.json";

		fileName.replaceAll(" ", "_");

		return fileName;
	}

	private void getSwitchProbeReportInfo(String text, String fileName)
	{
		AutoProgressMessageBox msg = new AutoProgressMessageBox("SwitchProbe Saved Report", "Generating SwitchProbe Report");
		msg.show();
		fileName = fileName.replaceAll(" ", "_");
		DashboardUtils.logInfo("Calling getSwitchReport with fileName:" + fileName);
		rpcService.getSwitchReport( text, fileName, uploadedFile,  new AsyncCallback<SwitchProbeReportInfoImpl>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox(Constants.SWITCH_PROBE, caught.getMessage());
				msg.hide();
			}

			@Override
			public void onSuccess( SwitchProbeReportInfoImpl switchProbeReportInfo )
			{
				DashboardUtils.handleError(switchProbeReportInfo.getErrorInfo(), Constants.SWITCH_PROBE, new AsyncCallback<Void>()
				{
					@Override
					public void onFailure( Throwable caught )
					{
						msg.hide();
						//Message already shown in handleError
					}

					@Override
					public void onSuccess( Void result )

					{
						switchProbeReportInfo.setUploadedFile( uploadedFile );
						asyncCallback.onSuccess( switchProbeReportInfo );

						int delay = delayLoading(switchProbeReportInfo);

							DelayedTask d = new DelayedTask() {
								@Override
								public void onExecute() {
									msg.hide();
								}
							};
							d.delay(delay);

					}
				} );

			}
		} );
	}

	private int delayLoading(SwitchProbeReportInfoImpl switchProbeReportInfo) {
		int delayInMs = 0; // Delay Loading for Milliseconds
		if(switchProbeReportInfo.getMacList().size() > 3000 || switchProbeReportInfo.getArpList().size() > 3000) {
			DashboardUtils.logInfo("Big SwitchProbeFile , Still loading..");
			delayInMs = 1000;
		}
		// Here we can add more scenarios if required and set the delay accordingly
			return delayInMs;
		}

	private void getSwitchprobeLocalFiles() {
		AutoProgressMessageBox msg = new AutoProgressMessageBox("SwitchProbe Saved Local Files", "Getting Previously Saved Files..");

		msg.show();
		rpcService.getSwitchprobeLocalFiles(new AsyncCallback<ArrayList<WbcFileInfo>>() {
			@Override
			public void onFailure(Throwable caught) {
				msg.hide();
				new WarningMessageBox("SwitchProbe Saved Local Files", "Error in getSwitchprobeLocalFiles "+ caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<WbcFileInfo> result) {
				msg.hide();
				CustomComboBoxWidget combo = new CustomComboBoxWidget();
				listPreviouslyOpenedFiles = combo.createComboBox(result);


				listPreviouslyOpenedFiles.setTriggerAction(ComboBoxCell.TriggerAction.ALL);
				listPreviouslyOpenedFiles.setEmptyText("Select File");
				listPreviouslyOpenedFiles.setAllowTextSelection(true);
				listPreviouslyOpenedFiles.setWidth(420);
				hSavedFile.add(listPreviouslyOpenedFiles, new HorizontalLayoutContainer.HorizontalLayoutData(-1,-1, new Margins(0,0,10,306)));


//					listPreviouslyOpenedFiles.add(wbcFileInfo.getName() +"-"+ wbcFileInfo.getDateString()+"("+wbcFileInfo.getSize()+")");
			}
		});
	}

	private void getSwitchprobeLocalFilesString() {
		AutoProgressMessageBox msg = new AutoProgressMessageBox("SwitchProbe Saved Local Files", "Getting Previously Saved Files..");
		msg.show();
		rpcService.getSwitchprobeLocalFilesAsString(new AsyncCallback<ArrayList<String>>() {
			@Override
			public void onFailure(Throwable caught) {
				msg.hide();
				new WarningMessageBox("SwitchProbe Saved Local Files", "Error in getSwitchprobeLocalFiles "+ caught.getMessage());
			}

			@Override
			public void onSuccess(ArrayList<String> result) {
				msg.hide();
				for(String fileString: result) {
					DashboardUtils.logInfo("getSwitchprobeLocalFiles: String from server:" + fileString );
					//listPreviouslyOpenedFiles.add(fileString);
				}
			}
		});
	}





	public SwitchProbeInitialPanel()
	{

	}

	private void getAppDirPath() {
		rpcService.getAppDirPath(new AsyncCallback<String>() {
			@Override
			public void onFailure(Throwable caught) {
				DashboardUtils.logError("Error in getAppDirPath" + caught.getMessage());
			}

			@Override
			public void onSuccess(String result) {
				switchProbePath =  result + "/switchprobes/";
				layout();

			}
		});

	}

}
