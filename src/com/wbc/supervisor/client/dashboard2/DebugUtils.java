package com.wbc.supervisor.client.dashboard2;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.UmbrellaException;
import com.google.gwt.user.client.Window;

/**
 * Created by JIM on 6/7/2015.
 */
public class DebugUtils {


    public static void initDebugAndErrorHandling() {
        GWT.setUncaughtExceptionHandler(new GWT.UncaughtExceptionHandler() {
            public void onUncaughtException(final Throwable e) {
                Throwable unwrapped = unwrap(e);
                performDefaultErrorHandling(unwrapped);
            }
        });
    }

    public static Throwable unwrap(Throwable e) {
        if(e instanceof UmbrellaException) {
            UmbrellaException ue = (UmbrellaException) e;
            if(ue.getCauses().size() == 1) {
                return unwrap(ue.getCauses().iterator().next());
            }
        }
        return e;
    }

    public static void performDefaultErrorHandling(final Throwable caught) {
        if (caught != null) {
            final String stacktrace = com.wbc.supervisor.client.dashboard2.DebugUtils.getStacktraceAsString(caught);
            Window.alert(stacktrace);
        } else {
            final String message = "Error ocuured, but we have no further information" ;
            Window.alert(message);
        }
    }

    public static String getStacktraceAsString(final Throwable tracepoint) {
        final StackTraceElement[] trace = tracepoint.getStackTrace();
        final StringBuffer sbuf = new StringBuffer(2048);
        sbuf.append(tracepoint.toString());
        sbuf.append(": at\n");
        // I cut the stacktrace at depth 7
        final int length = Math.min(7, trace.length);
        for (int i = 0; i <= length; i++) {
            sbuf.append(trace[i].toString());
            sbuf.append("\n");
        }
        if (trace.length > 7) {
            sbuf.append("...");
        }
        final String stacktrace = sbuf.toString();
        return stacktrace;
    }




}
