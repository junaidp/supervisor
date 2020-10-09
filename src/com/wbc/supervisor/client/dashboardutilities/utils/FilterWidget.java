package com.wbc.supervisor.client.dashboardutilities.utils;

import com.google.gwt.dom.client.Style;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.wbc.supervisor.client.dashboardutilities.images.Images;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;

public class FilterWidget extends HorizontalPanel {

    public ExportWidget exportWidget = new ExportWidget();
  //  public HorizontalPanel searchAndExport = new HorizontalPanel();
    public TextButton buttonSearch = new TextButton("Search");
    public StringComboBox columnToFilter = new StringComboBox();
    public HTML colSearch = new HTML("Column to Search:");
    public HTML labelSearch = new HTML("Search Text:");
    public HTML filteredGridHeading = new HTML("Filtered Grid");
    public TextField searchField = new TextField();
    public TextButton clearField = new TextButton("Clear Search");


    public FilterWidget() {
        clearField.setIcon(Images.INSTANCE.cross());
        FlexTable flex = new FlexTable();
        flex.setWidget(0,0, exportWidget);
        flex.setWidget(0,2, colSearch);
        flex.setWidget(0,3, columnToFilter);
        flex.setWidget(0,4, labelSearch);
        flex.setWidget(0,5, searchField);
        flex.setWidget(0,6, clearField);
        flex.setWidget(0,7, buttonSearch);
        flex.setWidth("100%");

        add(flex);
        add(filteredGridHeading);

        searchField.setHeight(35);
        columnToFilter.add("All");
        columnToFilter.setTriggerAction( ComboBoxCell.TriggerAction.ALL );
        columnToFilter.setEmptyText("All");
        filteredGridHeading.addStyleName("filteredHeading");
        colSearch.setVisible(false);
        columnToFilter.setVisible(false);
        filteredGridHeading.setVisible(false);
        labelSearch.getElement().getStyle().setPaddingLeft(200, Style.Unit.PX);
        colSearch.getElement().getStyle().setPaddingLeft(100, Style.Unit.PX);
        clearField.getElement().getStyle().setPaddingTop(3, Style.Unit.PX);
        filteredGridHeading.getElement().getStyle().setPaddingTop(15, Style.Unit.PX);
        filteredGridHeading.getElement().getStyle().setPaddingLeft(20, Style.Unit.PX);
        buttonSearch.setWidth(100);
        filteredGridHeading.setWidth("100px");
        buttonSearch.setIcon(Images.INSTANCE.search());
        colSearch.setWordWrap(false);
        labelSearch.setWordWrap(false);

    }
}
