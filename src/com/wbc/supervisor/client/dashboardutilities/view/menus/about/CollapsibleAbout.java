package com.wbc.supervisor.client.dashboardutilities.view.menus.about;

import com.wbc.supervisor.client.dashboardutilities.view.widgets.DialogDashboard;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Image;
import com.sencha.gxt.core.client.dom.ScrollSupport;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.ContentPanel;
import com.sencha.gxt.widget.core.client.Dialog;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;


public class CollapsibleAbout extends VerticalLayoutContainer {
    private ContentPanel cp;
    public CollapsibleAbout(Dialog dialogDashboard){

        HTML heading =  new HTML("WBC Industrial Network Services would like to acknowledge, attribute, and" +
                " thank the authors of the following software used in developing WBC-Utilities.");

        add(heading);
        heading.getElement().getStyle().setPaddingLeft(10, Style.Unit.PX);
        heading.getElement().getStyle().setVerticalAlign(Style.VerticalAlign.TOP);
        VerticalLayoutContainer v = new VerticalLayoutContainer();
        for(AboutCollapsibleEnum aboutCollapsibleEnum: AboutCollapsibleEnum.values()){

            cp = new ContentPanel();
            cp.setHeading(aboutCollapsibleEnum.heading);
            cp.setPosition(10, 10);
            cp.setCollapsible(true);
            cp.setExpanded(true);
            cp.getElement().getStyle().setPaddingTop(10, Style.Unit.PX);
            cp.getElement().getStyle().setPaddingBottom(10, Style.Unit.PX);
            HTML description = new HTML(aboutCollapsibleEnum.description);
            description.setAutoHorizontalAlignment(HasHorizontalAlignment.ALIGN_JUSTIFY);
            cp.setWidget(description);
            v.add(cp);
        }
        add(v);
        v.setScrollMode(ScrollSupport.ScrollMode.AUTOY);
        v.setHeight(300);

        dialogDashboard.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                GWT.log(dialogDashboard.getOffsetHeight()+"," + dialogDashboard.getOffsetWidth());
                v.setHeight(dialogDashboard.getOffsetHeight()-200);

            }
        });


    }
}
