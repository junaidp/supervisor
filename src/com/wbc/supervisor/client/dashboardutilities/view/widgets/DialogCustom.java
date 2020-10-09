package com.wbc.supervisor.client.dashboardutilities.view.widgets;

import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.Dialog;

public class DialogCustom {
    private Dialog dialog;
    private ScrollPanel scrollPanelMain;

    public DialogCustom(Widget widget, String heading) {
        layout(widget, heading);
        scrollPanelMain.setSize("80%", "150px");
    }

    public DialogCustom(Widget widget, String heading, String width, String height) {
        layout(widget, heading);
        scrollPanelMain.setSize(width, height);
    }

    private void layout(Widget widget, String heading) {
        dialog = new Dialog();
        dialog.setHeading(heading);
        scrollPanelMain = new ScrollPanel();
        scrollPanelMain.add(widget);
        dialog.setWidget(scrollPanelMain);
        dialog.show();
        dialog.setSize("80%", "300px");
        dialog.center();
        clickHandler();
    }

    private void clickHandler() {
        dialog.getButton(Dialog.PredefinedButton.OK).addSelectHandler(event ->closeDialog() );
    }

    private void closeDialog() {
        dialog.removeFromParent();
        scrollPanelMain.removeFromParent();
    }
}
