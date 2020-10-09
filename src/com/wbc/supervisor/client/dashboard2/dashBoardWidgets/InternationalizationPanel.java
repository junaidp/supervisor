package com.wbc.supervisor.client.dashboard2.dashBoardWidgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.logical.shared.CloseEvent;
import com.google.gwt.event.logical.shared.CloseHandler;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.http.client.UrlBuilder;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;

/**
 * Created by Junaid on 11/26/2014.
 */
public class InternationalizationPanel extends HorizontalPanel{

    private Label lblSelectedLang = new Label("English");

    public InternationalizationPanel(String headerStyle){

        UrlBuilder x = Window.Location.createUrlBuilder();
        Anchor french;
        Anchor spanish;
        Anchor german;
        Anchor english;
        lblSelectedLang.setStyleName("blueText");
        String fulladd= x.buildString();
        if(fulladd.contains("3Dfr")){
            lblSelectedLang.setText("French");
        }
        else if(fulladd.contains("3Dde")){
            lblSelectedLang.setText("German");
        }
        else if(fulladd.contains("3Des")){
            lblSelectedLang.setText("Spanish");
        }else{
            lblSelectedLang.setText("English");
        }

        if(GWT.isProdMode())
        {
            french=new Anchor("French",GWT.getHostPageBaseURL()+"?locale=fr");
            german=new Anchor("German",GWT.getHostPageBaseURL()+"?locale=de");
            spanish=new Anchor("Spanish",GWT.getHostPageBaseURL()+"?locale=es");
            english=new Anchor("English",GWT.getHostPageBaseURL());
        }
        else
        {

            int end =x.buildString().indexOf("%");
            String currentUrl = x.buildString().substring(0, end);
            String urlFench = currentUrl.concat(":9997/?locale=fr#");
            String urlSp = currentUrl.concat(":9997/?locale=es#");
            String urlGm = currentUrl.concat(":9997/?locale=de#");
            String urlEng = currentUrl.concat(":9997/");
            french=new Anchor("French",urlFench);
            spanish=new Anchor("Spanish",urlSp);
            german=new Anchor("German",urlGm);
            english=new Anchor("English",urlEng);
          }

        HorizontalPanel hpnlLang = new HorizontalPanel();
        hpnlLang.setSpacing(1);
        final DisclosurePanel discLanguages = new DisclosurePanel("Languages");
        VerticalPanel vpnlLang = new VerticalPanel();
        hpnlLang.add(lblSelectedLang);
        hpnlLang.add(discLanguages);
        vpnlLang.add(english);
        vpnlLang.add(french);
        vpnlLang.add(german);
        vpnlLang.add(spanish);
        final PopupPanel pop = new PopupPanel();
        pop.setWidget(vpnlLang);
//        discLanguages.add(pop);
//        discLanguages.setWidth("200px");
        add(hpnlLang);

        setStyleName(headerStyle);
//        setStyleName("header");
        vpnlLang.setWidth("100px");
        hpnlLang.setWidth("50px");

        discLanguages.addOpenHandler(new OpenHandler<DisclosurePanel>() {
            @Override
            public void onOpen(OpenEvent<DisclosurePanel> event) {
                pop.showRelativeTo(discLanguages);
            }
        });

        discLanguages.addCloseHandler(new CloseHandler<DisclosurePanel>() {
            @Override
            public void onClose(CloseEvent<DisclosurePanel> event) {
                pop.hide();
            }
        });


    }
}
