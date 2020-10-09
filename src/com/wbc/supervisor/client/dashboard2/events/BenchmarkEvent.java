package com.wbc.supervisor.client.dashboard2.events;

import com.google.gwt.event.shared.GwtEvent;

/**
 * Created by JIM on 10/2/2014.
 */
public class BenchmarkEvent extends GwtEvent<BenchmarkEventHandler> {

    private long startTime;
    private String name = "undefined";
    private String type = "undefined";

    public BenchmarkEvent(long startTime, String name, String type) {
        this.startTime = startTime;
        this.name = name;
        this.type = type;
    }

    public BenchmarkEvent(long startTime) {
        this.startTime = startTime;
    }

    @Override
    public Type<BenchmarkEventHandler> getAssociatedType() {
        return null;
    }

    @Override
    protected void dispatch(BenchmarkEventHandler handler) {
        handler.onBenchmark(this);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    public long getElapsed() {
        return System.currentTimeMillis() - startTime;
    }

    /*
    public void logElapsed ( Logger logger, String additionalText ) {
        if (additionalText != null && additionalText.length() > 0 ) {
            logger.info("Benchmark " + name + ", " + type + " elapsed " + (System.currentTimeMillis() - startTime) + " > " + additionalText );
        } else {
            logger.info("Benchmark " + name + ", " + type + " elapsed " + (System.currentTimeMillis() - startTime) );
        }
    }
    */


    public String toStringElapsed ( String additionalText ) {
        if (additionalText != null && additionalText.length() > 0 ) {
            return ("Benchmark " + name + ", " + type + " elapsed " + (System.currentTimeMillis() - startTime) + " > " + additionalText );
        } else {
            return ("Benchmark " + name + ", " + type + " elapsed " + (System.currentTimeMillis() - startTime) );
        }
    }

}
