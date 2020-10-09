package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

//import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.view.menus.MenuBase;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.SwitchProbeReportInfoImpl;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.container.AccordionLayoutContainer;

public class MenuSwitchProbe extends MenuBase implements IsWidget
{

	private AccordionLayoutContainer.AccordionLayoutAppearance appearance = GWT.create( AccordionLayoutContainer.AccordionLayoutAppearance.class);

	public MenuSwitchProbe( ){

	}

	@Override
	public Widget asWidget() {

		container.clear();
		container.add( new SwitchProbeInitialPanel(new AsyncCallback<SwitchProbeReportInfoImpl>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				WarningMessageBox messageBox = new WarningMessageBox( "Error", "SwitchProbe Report Error"+ caught );
				messageBox.show();
			}

			@Override
			public void onSuccess( SwitchProbeReportInfoImpl swtichprobeReportInfo )
			{
				GWT.log( "BACK FROM SERVER" );
				container.clear();
				container.add( layout(swtichprobeReportInfo));

			}
		} ) );
		return super.asWidget();

	}

	private Widget layout( SwitchProbeReportInfoImpl swtichprobeReportInfo )
	{
		GWT.log("Creating Switch Probe Container");
		AccordionLayoutContainer accordion = new AccordionLayoutContainer();
		accordion.setExpandMode( AccordionLayoutContainer.ExpandMode.SINGLE_FILL);
		DashboardUtils.logInfo("Numberof port for mac: "+swtichprobeReportInfo.getGeneralData().getNumDuplicateMacs());
		for(SwitchProbeEnum switchProbeEnum: SwitchProbeEnum.values())
		{
			ContentPanel panel = getPanel(switchProbeEnum.id, switchProbeEnum.heading, switchProbeEnum.panel, swtichprobeReportInfo.getGeneralData().getNumDuplicateMacs());
			accordion.add(panel);
			if(switchProbeEnum.id.equals( "1" ))
			{

			}
			  GWT.log( "GOING TO POPULATE" );
			  populate(switchProbeEnum, swtichprobeReportInfo);
		}
		// GWT.log( "BACK FROM POPULATING ALL PANELS" );

		ContentPanel panel = new ContentPanel(  );
		panel.setHeading( SafeHtmlUtils.fromString(com.wbc.supervisor.client.dashboardutilities.Constants.SWITCH_PROBE_HEADING +":  "+swtichprobeReportInfo.getUploadedFile()+" "  ) );
		panel.add( accordion );
		return panel;
	}

	private void populate( SwitchProbeEnum switchProbeEnum, SwitchProbeReportInfoImpl swtichprobeReportInfo)
	{

		switch(switchProbeEnum)
		{
		case GENERAL_INFORMATION:
			GeneralInformationPanel generalInformationPanel = (GeneralInformationPanel) switchProbeEnum.panel;
			generalInformationPanel.populate( swtichprobeReportInfo.getGeneralData(), swtichprobeReportInfo.getErrorMessages(), swtichprobeReportInfo.getWarningMessages() );
			break;
		case MAC_INFORMATION:
			MacInformationPanel macInformationPanel = (MacInformationPanel) switchProbeEnum.panel;
			macInformationPanel.populate( swtichprobeReportInfo.getMacList() );
			break;
		case APR_INFORMATION:
			ARPInformationPanel arpInformationPanel = (ARPInformationPanel)switchProbeEnum.panel;
			arpInformationPanel.populate( swtichprobeReportInfo.getArpList() );
			break;
		case VLAN_AND_PORT_INFORMATION:
			VlanAndPortInformationPanel vlanPanel = (VlanAndPortInformationPanel)switchProbeEnum.panel;
			vlanPanel.populate( swtichprobeReportInfo.getVlanList() );
			break;
		case IP_INFORMATION:
			IpInformationPanel informationPanel = ( IpInformationPanel)switchProbeEnum.panel;
			informationPanel.populate( swtichprobeReportInfo.getIpList() );
			break;
		case INTERFACE_INFORMATION:
			InterfaceInformationPanel ifInformationPanel = (InterfaceInformationPanel)switchProbeEnum.panel;
			ifInformationPanel.populate( swtichprobeReportInfo.getIfList() );
			break;
		case PORT_INFORMATION:
			PortInformationPanel portInformationPanel = (PortInformationPanel)switchProbeEnum.panel;
			portInformationPanel.populate( swtichprobeReportInfo.getPortList() );
			break;
			case MAC_INFORMATION_DUP:
				MacInformationPanelDup macInformationPanelDup = (MacInformationPanelDup)switchProbeEnum.panel;
				macInformationPanelDup.populate( swtichprobeReportInfo.getDuplicateMacList() );
				break;
		default:
			break;
		}
	}

	private ContentPanel getPanel(String id, String heading, SwitchProbePanel panel, int numPortForMac)
	{
		ContentPanel cp = new ContentPanel(appearance);
		cp.setAnimCollapse(true);
		cp.setBodyStyleName("pad-text");
		cp.setHeading(heading);
		cp.setCollapsible(true);
		if (id.equals("1")) {  //  General
			panel.setHeight(360);
			DelayedTask d = new DelayedTask() {
				@Override
				public void onExecute() {
					cp.setExpanded(true);
				}
			};
			d.delay(300);
		}
		else if (id.equals("3")) {  // Arp
			panel.setHeight(600);
		} else if (id.equals("5")) {  // Mac
			panel.setHeight(600);
		} else {
			panel.setHeight(360);
		}
		panel.setScrollMode( ScrollSupport.ScrollMode.AUTOY );

		if(numPortForMac == 0 && id.equals("8"))
			cp.setVisible(false);

		cp.setWidget( panel );
		return  cp;
	}
}
