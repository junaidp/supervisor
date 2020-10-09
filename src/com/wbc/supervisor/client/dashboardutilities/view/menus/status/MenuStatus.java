package com.wbc.supervisor.client.dashboardutilities.view.menus.status;
import com.wbc.supervisor.client.dashboardutilities.DashboardUtilMessages;
import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.view.menus.MenuBase;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WizardFieldLabel;
import com.wbc.supervisor.shared.dashboardutilities.Globals;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostStatus;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

import java.util.ArrayList;

public class MenuStatus extends MenuBase implements IsWidget
{

    private AsyncCallback<String> asyncCallback;
    private DashboardUtilMessages messages =
            GWT.create(DashboardUtilMessages.class);
    private TextButton refresh = new TextButton("Refresh");

    @Override
    public Widget asWidget() {
        container.clear();
        return super.asWidget();
    }

    public MenuStatus(AsyncCallback<String> asyncCallback)
    {
        this.asyncCallback = asyncCallback;
        getApplicationStatus();
    }

    private void getApplicationStatus()
    {
        AutoProgressMessageBox messageBoxProgress = DashboardUtils.getProgressMessageBox( "Intravue Status","Getting Intravue Status" );
        messageBoxProgress.show();
        supervisorServiceAsync rpcService = GWT.create( supervisorService.class );
        IntravueHostStatus ivHostStatus = new IntravueHostStatus();
        String ipToTest = Globals.HOST_IP_ADDRESS;
        rpcService.getIntravueStatusInfo( ipToTest, ivHostStatus, new AsyncCallback<IntravueHostStatus>()
        {
            @Override
            public void onFailure( Throwable caught )
            {
                messageBoxProgress.hide();
                logError("Error in converting json for Error Txt "+ caught.getLocalizedMessage() );
                asyncCallback.onFailure( caught);
            }

            @Override
            public void onSuccess( IntravueHostStatus result )
            {
                messageBoxProgress.hide();
                if(result.getHostDescription().contains("Error"))
                {
                    logError("Error in getApplicationStatus: "+ result.getHostDescription());
                }
                else {
                    // logInfo("Success in getApplicationStatus " + result);
                    layout(result);
                }

            }
        } );
    }

  private void layout(IntravueHostStatus intravueHostStatus){
        container.clear();
        VerticalLayoutContainer v = new VerticalLayoutContainer();
         HTML htmlVersion = new HTML( intravueHostStatus.getIvVersion() );//version + extra

        HorizontalPanel h = new HorizontalPanel();
        h.setWidth("100%");
        h.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
        h.add(refresh);
        refresh.addSelectHandler(Event -> refresh());
        refresh.setWidth(100);
        v.add(h);
        HTML status =  new HTML(intravueHostStatus.getIvHostStatus());
        HTML stoppedMessage = new HTML(intravueHostStatus.getStoppedMessage());
        status.getElement().getStyle().setColor(intravueHostStatus.getIvHostStatusColor());
        stoppedMessage.getElement().getStyle().setColor(intravueHostStatus.getStoppedMessageColor());

        v.add(new WizardFieldLabel( htmlVersion , "Intravue Version", 150) );
        v.add(new WizardFieldLabel( new HTML(intravueHostStatus.getHostDescription()) , messages.id_intravueHostOS(), 150 ) );
        v.add(new WizardFieldLabel( status , messages.id_intravueStatus(), 150 ) );
        v.add(new WizardFieldLabel(stoppedMessage  , messages.id_statusMessage(), 150 ) );
        v.add(new WizardFieldLabel( new HTML(intravueHostStatus.getScannerSpeed()) , messages.id_scannerSpeed(), 150 ) );
        v.add(new WizardFieldLabel( new HTML(intravueHostStatus.getFoundIps()+"") , messages.id_foundDevices(), 150 ) );

      int paddingLeft = 50;
      HorizontalPanel h1 = new HorizontalPanel();
      HorizontalPanel h2 = new HorizontalPanel();
      HTML nic1Value = new HTML(intravueHostStatus.getIpNic1());
      HTML nic2Value = new HTML(intravueHostStatus.getIpNic2());
      HTML mac1Value = new HTML(intravueHostStatus.getMacNic1());
      HTML mac2Value = new HTML(intravueHostStatus.getMacNic2());
      nic1Value.getElement().getStyle().setPaddingLeft(paddingLeft, Style.Unit.PX);
      nic1Value.getElement().getStyle().setPaddingTop(5, Style.Unit.PX);
      nic2Value.getElement().getStyle().setPaddingLeft(paddingLeft, Style.Unit.PX);
      nic2Value.getElement().getStyle().setPaddingTop(5, Style.Unit.PX);
      mac1Value.getElement().getStyle().setPaddingLeft(paddingLeft, Style.Unit.PX);
      mac1Value.getElement().getStyle().setPaddingTop(5, Style.Unit.PX);
      mac2Value.getElement().getStyle().setPaddingLeft(paddingLeft, Style.Unit.PX);
      mac2Value.getElement().getStyle().setPaddingTop(5, Style.Unit.PX);
      htmlVersion.getElement().getStyle().setPaddingTop(5, Style.Unit.PX);
      nic1Value.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);
      nic2Value.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);
      mac1Value.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);
      mac2Value.getElement().getStyle().setFontWeight(Style.FontWeight.BOLD);

      nic1Value.setWidth("150px");
      nic2Value.setWidth("150px");

      FieldLabel nic1 = new FieldLabel();
      nic1.setContent(messages.id_hostNIC1Ip());

      FieldLabel mac1 = new FieldLabel();
      mac1.setContent(messages.id_hostNIC1Mac());

      FieldLabel nic2 = new FieldLabel();
      nic2.setContent(messages.id_hostNIC2Ip());

      FieldLabel mac2 = new FieldLabel();
      mac2.setContent(messages.id_hostNIC2Mac());

      h1.add(nic1);
      h1.add(nic1Value);

      h1.add(mac1);
      h1.add(mac1Value);

      h2.add(nic2);
      h2.add(nic2Value);

      h2.add(mac2);
      h2.add(mac2Value);

      v.add(h1);
      v.add(h2);

      v.getElement().getStyle().setPaddingBottom(20, Style.Unit.PX);

      container.add(v);

        for(IntravueHostStatus.IvNetworkInfo ivNetworkInfo : intravueHostStatus.getNetworkInfo()){
            IvNetworkInfoPanel ivNetworkInfoPanel = new IvNetworkInfoPanel(ivNetworkInfo);
            container.add(ivNetworkInfoPanel);
        }

        asyncCallback.onSuccess("Refreshed");

    }

    private void refresh() {
        getApplicationStatus();
    }

}

