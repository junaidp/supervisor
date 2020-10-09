package com.wbc.supervisor.client.dashboard2.dashBoardWidgets;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

/**
 * Created by Junaid on 10/24/2014.
 */
public class WbcLabelWidget extends Widget{

    private int labelid;
    private String name ;
    private int type;
    private String shortname ;
    private String owners;

    public WbcLabelWidget(){
        name ="test name";
    }

    public Button getButton(){

    Button button = (Button)this.getParent();
    button.setText(name);
    return button;
 }

    public Label getLabel(){
        Label lbl = (Label)this.getParent();
        lbl.setText(name);
        return lbl;
    }


    public int getLabelid() {
        return labelid;
    }

    public void setLabelid(int labelid) {
        this.labelid = labelid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    public String getOwners() {
        return owners;
    }

    public void setOwners(String owners) {
        this.owners = owners;
    }


}