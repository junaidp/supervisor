package com.wbc.supervisor.client.dashboard2.wbcwidgets;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * Created by Junaid on 7/25/14.
 */
public class WbcNameWidget extends Composite implements Comparable<WbcNameWidget> {

    private String name1;
    private String name2;
    private int id;
    private int rank;
    private TextBox tb1;
    private TextBox tb2;
    private VerticalPanel nameWidgetContainer;

    public VerticalPanel getNameWidgetContainer() {
        return nameWidgetContainer;
    }

    public void setNameWidgetContainer(VerticalPanel nameWidgetContainer) {
        this.nameWidgetContainer = nameWidgetContainer;
    }

    private int selectedIndex;

    public WbcNameWidget(){
        DecoratorPanel container = new DecoratorPanel();
        nameWidgetContainer = new VerticalPanel();
        container.setWidth("100%");
        initWidget(container);
        name1 = "undefined";
        name2 = "undefined";
        id = 0;
        rank = 0;
        tb1 = new TextBox();

        tb2 = new TextBox();
        tb1.setWidth("100%");
        tb2.setWidth("100%");
        tb1.setText(name1);
        tb2.setText(name2);
        nameWidgetContainer.add(tb1);
        nameWidgetContainer.add(tb2);
        container.setWidget(nameWidgetContainer);
        nameWidgetContainer.setWidth("100%");
    }

    public WbcNameWidget( String name1, String name2, int id, int rank){

        DecoratorPanel container = new DecoratorPanel();
        nameWidgetContainer = new VerticalPanel();
        this.name1 = name1;
        this.name2 = name2;
        this.id = id;
        this.rank = rank;
        tb1 = new TextBox();
        tb2 = new TextBox();
        tb1.setText(name1);
        tb2.setText(name2);
        initWidget(container);
        nameWidgetContainer.add(tb1);
        nameWidgetContainer.add(tb2);
        container.setWidget(nameWidgetContainer);
        tb1.setWidth("100%");
        tb2.setWidth("100%");
        nameWidgetContainer.setWidth("100%");
    }

    public String getName1() {
        return name1;
    }
    public void setName1(String name ) {
        name1 = name;
    }
    public String getName2() {
        return name2;
    }
    public void setName2(String name) {
        name2 = name;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getRank() {
        return rank;
    }
    public void setRank(int rank) {
        this.rank = rank;
    }

    public TextBox getTb1() {
        return tb1;
    }

    public void setTb1(TextBox tb1) {
        this.tb1 = tb1;
    }

    public TextBox getTb2() {
        return tb2;
    }

    public void setTb2(TextBox tb2) {
        this.tb2 = tb2;
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    public int compareTo(WbcNameWidget widget) {
        if ( this.rank > widget.rank )
        {
            return 1;
        } else if ( this.rank < widget.rank )
        {
            return -1;
        }
        return 0;
    }


}
