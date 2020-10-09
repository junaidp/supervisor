package com.wbc.supervisor.client.dashboardutilities;

import com.sencha.gxt.widget.core.client.Dialog;

public class DialogBase extends Dialog {

    public final int buttonHeight = 30;
    public final int buttonWidth = 100;

    public int getHeight()
    {
        return 400;
    }

    public int getWidth()
    {
        return 700;
    }

    public void setDefault()
    {
        setClosable(true);
        setSize(getWidth()+"px", getHeight()+"px");
        addStyleName("centered");

    }
}
