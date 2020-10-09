package com.wbc.supervisor.client.dashboardutilities.view.widgets;

import com.wbc.supervisor.shared.dashboardutilities.WbcFileInfo;
import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.XTemplates;

public class ComboCell extends AbstractCell<WbcFileInfo> {
    public interface SampleXTemplates extends XTemplates {
        @XTemplate(source = "combocell.html")
        SafeHtml renderCell(WbcFileInfo combo);
    }

    private SampleXTemplates tpl = GWT.create(SampleXTemplates.class);

    @Override
    public void render(Context context, WbcFileInfo combo, SafeHtmlBuilder sb) {
        sb.append(tpl.renderCell(combo));
    }
}