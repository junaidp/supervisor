package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.google.gwt.core.client.GWT;

public enum SwitchProbeEnum
{

	GENERAL_INFORMATION("General Information", "1", new GeneralInformationPanel()),
	INTERFACE_INFORMATION("Interface Information", "2", new InterfaceInformationPanel() ),
	APR_INFORMATION("ARP information", "3", new ARPInformationPanel() ),
	VLAN_AND_PORT_INFORMATION("Cisco VLAN Information", "4", new VlanAndPortInformationPanel() ),
	MAC_INFORMATION("MAC Information", "5", new MacInformationPanel() ),
	IP_INFORMATION("IP Information", "6", new IpInformationPanel() ),
	PORT_INFORMATION("Port Information", "7", new PortInformationPanel() ),
	MAC_INFORMATION_DUP("Macs Found on more than 1 port", "8", new MacInformationPanelDup());
	String heading;
	String id;
	SwitchProbePanel panel;

	SwitchProbeEnum( String heading, String id, SwitchProbePanel panel ){
		GWT.log(("Creating SwitchProbeEnum"));
			this.heading = heading;
			this.id = id;
			this.panel = panel;
	}
}
