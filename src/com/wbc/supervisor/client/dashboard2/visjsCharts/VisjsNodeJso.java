package com.wbc.supervisor.client.dashboard2.visjsCharts;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by JIM on 12/26/2014.
 */
public class VisjsNodeJso extends JavaScriptObject implements VisjsNodeInterface {

    protected VisjsNodeJso() {}


    @Override
    public final native int getId()  /*-{
        return this.id;
    }-*/;

    @Override
    public final native void setId(int id) /*-{
        this.id = id;
    }-*/;

    @Override
    public final native String getLabel()  /*-{
        return this.label;
    }-*/;

    @Override
    public final native void setLabel(String label) /*-{
        this.label = label;
    }-*/;

}
