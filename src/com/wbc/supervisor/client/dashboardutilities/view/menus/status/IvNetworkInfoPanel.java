package com.wbc.supervisor.client.dashboardutilities.view.menus.status;

import com.wbc.supervisor.client.dashboardutilities.view.widgets.WizardFieldLabel;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostStatus;
import com.google.gwt.dom.client.Style;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.widget.core.client.Portlet;
import com.sencha.gxt.widget.core.client.button.ToolButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

public class IvNetworkInfoPanel extends  Portlet {

    public IvNetworkInfoPanel(IntravueHostStatus.IvNetworkInfo ivNetworkInfo)
    {
        HTML heading = getHeading(ivNetworkInfo);
        SafeHtml t = SafeHtmlUtils.fromTrustedString(heading.getHTML());
        setHeading(t);
        add(getData(ivNetworkInfo));
        setHeight(200);
        configPanel(this);
    }

    private IsWidget getData(IntravueHostStatus.IvNetworkInfo ivNetworkInfo) {

        VerticalLayoutContainer v  = new VerticalLayoutContainer();
        FlexTable flex = new FlexTable();
        FieldLabel from = new FieldLabel();
        from.setContent("From");
        FieldLabel to = new FieldLabel();
        to.setContent("To");

        flex.setWidget(0,0, from);
        flex.setWidget(0,1,to);

        from.getElement().getStyle().setPaddingRight(30, Style.Unit.PX);
        to.getElement().getStyle().setPaddingRight(30, Style.Unit.PX);

        for(int i =0 ; i<ivNetworkInfo.getScanRanges().size(); i++){// IntravueHostStatus.IvScanRange ivScanRange : ivNetworkInfo.getScanRanges()){
            flex.setWidget(i+1, 0 , new HTML(ivNetworkInfo.getScanRanges().get(i).getFromIP()));
            flex.setWidget(i+1, 1 , new HTML(ivNetworkInfo.getScanRanges().get(i).getToIP() ));

        }

        v.getElement().getStyle().setPaddingLeft(10, Style.Unit.PX);
        v.getElement().getStyle().setPaddingTop(10, Style.Unit.PX);
        v.add(flex);
        v.setHeight(200);
        v.setScrollMode(ScrollSupport.ScrollMode.AUTOY);
        return v;

    }

    private HTML getHeading(IntravueHostStatus.IvNetworkInfo ivNetworkInfo) {
        /*
        String agentIp = "Agent Ip:";
        String agentIpValue = ivNetworkInfo.getAgentIp();
        if(agentIpValue == null || agentIpValue.isEmpty() || agentIpValue.equalsIgnoreCase("undefined")){
            agentIp = "";
            agentIpValue = "";
        }
        HTML heading = new HTML("Network Name: &nbsp " + ivNetworkInfo.getNetworkName()+"&nbsp &nbsp &nbsp &nbsp "+" Top Parent: &nbsp" + ivNetworkInfo.getTopIP()+"  "+"&nbsp &nbsp &nbsp &nbsp "+agentIp+" &nbsp"
                + agentIpValue+" "+ "&nbsp &nbsp &nbsp &nbsp Agent Group Number: &nbsp" +  ivNetworkInfo.getAgentNetgroup() );

         */
        StringBuilder sb = new StringBuilder();
        int maxNameLen = 30;
        int maxIpLen = 25;
        sb.append("Network Name: &nbsp" );
        int len = ivNetworkInfo.getNetworkName().length();
        sb.append(ivNetworkInfo.getNetworkName());
        if ( len < maxNameLen) {
            for (int i = len; i<maxNameLen; i++) {
                sb.append("&nbsp");
            }
        }
        sb.append("Top Parent: &nbsp");
        sb.append(ivNetworkInfo.getTopIP());
        // This is all the text if there is no agent
        String agentIpValue = ivNetworkInfo.getAgentIp();
        if(agentIpValue != null && !agentIpValue.isEmpty() && !agentIpValue.equalsIgnoreCase("undefined")){
            len = ivNetworkInfo.getTopIP().length();
            if ( len < maxIpLen) {
                for (int i = len; i<maxIpLen; i++) {
                    sb.append("&nbsp");
                }
            }
            sb.append("Agent IP: &nbsp");
            sb.append(agentIpValue);
            len = agentIpValue.length();
            if ( len < maxIpLen) {
                for (int i = len; i<maxIpLen; i++) {
                    sb.append("&nbsp");
                }
            }
            sb.append("Agent Group Number: &nbsp");
            sb.append( ivNetworkInfo.getAgentNetgroup());
        }
        HTML heading = new HTML( sb.toString());
        return  heading;

    }

    private void configPanel(final Portlet portlet) {
        portlet.setCollapsible(true);
        portlet.setAnimCollapse(false);

    }
}
