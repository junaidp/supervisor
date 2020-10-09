package com.wbc.supervisor.client.dashboard2.visjsCharts;

import com.google.gwt.core.client.JavaScriptObject;

/**
 * Created by JIM on 12/27/2014.
 */
public class VisjsConnectionJso extends JavaScriptObject implements VisjsConnectionInterface {

    protected VisjsConnectionJso() {}

    @Override
    public final native int getTo()  /*-{
        return this.to;
    }-*/;

    @Override
    public final native void setTo(int to) /*-{
        this.to = to;
    }-*/;

    @Override
    public final native int getFrom()  /*-{
        return this.from;
    }-*/;

    @Override
    public final native void setFrom(int from) /*-{
        this.from = from;
    }-*/;

}
