package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.wbc.supervisor.client.dashboardutilities.DashboardUtilMessages;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WizardFieldLabel;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.GeneralInfoImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.DisclosurePanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

import java.util.ArrayList;

public class GeneralInformationPanel extends SwitchProbePanel
{
	public GeneralInformationPanel()
	{

	}

	@Override
	protected ArrayList<String> getParams(BaseGrid baseGrid, String c) {
		return null;
	}

	@Override
	protected void filter() {
		filter(filterWidget.searchField.getText());
	}

	private DashboardUtilMessages messages =
			GWT.create(DashboardUtilMessages.class);

	public void populate( GeneralInfoImpl generalData, ArrayList<String> errormessages, ArrayList<String> warningMessages )
	{
		clear();
		VerticalLayoutContainer vLeft = new VerticalLayoutContainer();
		getErrorAndWarnings(errormessages, warningMessages, vLeft);
		vLeft.add(new WizardFieldLabel( new HTML(generalData.getDescription()) , messages.id_general_description() ) );
		vLeft.add(new WizardFieldLabel( new HTML(generalData.getSwitchIp()),messages.id_general_switchIpAddress()));
		vLeft.add(new WizardFieldLabel( new HTML(generalData.getCommunityUsed()) , messages.id_general_idCommunity()) );
		vLeft.add(new WizardFieldLabel( new HTML(generalData.getLocation() ) , messages.id_general_location()) );
		if (generalData.isRouter()) {
			vLeft.add(new WizardFieldLabel( new HTML(messages.id_true() ) , messages.id_general_router()) );
		} else {
			vLeft.add(new WizardFieldLabel( new HTML(messages.id_false() ) , messages.id_general_router()) );
		}
		vLeft.add(new WizardFieldLabel( new HTML(generalData.getDefaultGateway() ) , messages.id_general_ifToGateway()) );

		HorizontalLayoutContainer hIds = new HorizontalLayoutContainer();


		FieldLabel numIps = new FieldLabel();
		FieldLabel numIfs = new FieldLabel();
		FieldLabel numArps = new FieldLabel();
		FieldLabel numMacs = new FieldLabel();

		numIps.setContent(messages.id_general_numIps());
		numIfs.setContent(messages.id_general_numIfs());
		numArps.setContent(messages.id_general_numArps());
		numMacs.setContent(messages.id_general_numMacs());


		hIds.add(numIps);
		HTML ips = new HTML(generalData.getNumIPs()+"");
		hIds.add(ips);

		hIds.add(numIfs);
		HTML ifs = new HTML(generalData.getNumIfs()+"");
		hIds.add(ifs);

		hIds.add(numArps);
		HTML arps = new HTML(generalData.getNumArps()+"");
		hIds.add(arps);

		hIds.add(numMacs);
		HTML mac = new HTML(generalData.getNumPortForMac()+"");
		hIds.add(mac);

		ips.getElement().getStyle().setPaddingTop(5, Style.Unit.PX);
		ifs.getElement().getStyle().setPaddingTop(5, Style.Unit.PX);
		arps.getElement().getStyle().setPaddingTop(5, Style.Unit.PX);
		mac.getElement().getStyle().setPaddingTop(5, Style.Unit.PX);

		ips.getElement().getStyle().setPaddingRight(140, Style.Unit.PX);
		ifs.getElement().getStyle().setPaddingRight(140, Style.Unit.PX);
		arps.getElement().getStyle().setPaddingRight(140, Style.Unit.PX);
		mac.getElement().getStyle().setPaddingRight(140, Style.Unit.PX);

		vLeft.add(hIds);
		add(vLeft);

	}

	private void getErrorAndWarnings( ArrayList<String> errorMessages,ArrayList<String> warningMessages, VerticalLayoutContainer vLeft )
	{

		String disclosurePanelText = null;
		disclosurePanelText = getDisclosurePanelHeaderText(errorMessages, warningMessages, disclosurePanelText);
		VerticalPanel vpnlErrorsWarnings = new VerticalPanel();
		DisclosurePanel showErrorsWarnings = new DisclosurePanel(disclosurePanelText);
		showErrorsWarnings.setAnimationEnabled(true);
		if(disclosurePanelText == null){
			showErrorsWarnings.setVisible(false);
		}
		vLeft.add(showErrorsWarnings);
		showErrorsWarnings.add(vpnlErrorsWarnings);

		if (errorMessages.isEmpty())
		{
			vLeft.add( new WizardFieldLabel( new HTML( "" ), "No Error Messages" ) );

		}
		else
		{
			for ( String error : errorMessages )
			{
					HTML lblErrors = new HTML();
					lblErrors.setText(error);
					vpnlErrorsWarnings.add(lblErrors);
					lblErrors.addStyleName("errorMessage");
				}

			}

		if (warningMessages.isEmpty())
		{
			vLeft.add( new WizardFieldLabel( new HTML( "" ), "No Warning Messages" ) );

		}
		else
		{
		for ( String warning : warningMessages )
			{
				HTML lblWarnings = new HTML();
				lblWarnings.setText(warning);
				lblWarnings.addStyleName("warningMessage");
				vpnlErrorsWarnings.add(lblWarnings);

			}
		}
	}

	private String getDisclosurePanelHeaderText(ArrayList<String> errorMessages, ArrayList<String> warningMessages, String disclosurePanelText) {
		if(!errorMessages.isEmpty() && !warningMessages.isEmpty()){
			disclosurePanelText = "Show Errors And Warnings";
		}
		else if(!errorMessages.isEmpty() && warningMessages.isEmpty()){
			disclosurePanelText = "Show Errors";
		}
		else if(errorMessages.isEmpty() && !warningMessages.isEmpty()){
			disclosurePanelText = "Show Warnings";
		}
		return disclosurePanelText;
	}


}
