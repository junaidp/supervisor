package com.wbc.supervisor.shared;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.logging.impl.FormatterImpl;

import java.util.Date;
import java.util.logging.LogRecord;

/**
 * Created by Junaid on 3/12/2015.
 */
public class DashboardLogger extends FormatterImpl {
    private boolean showStackTraces;
    private final DateTimeFormat dtFormat = DateTimeFormat.getFormat("dd HH:mm:ss");


    public DashboardLogger(boolean showStackTraces) {
        this.showStackTraces = showStackTraces;
    }

    @Override
    public String format(LogRecord event) {
        StringBuilder message = new StringBuilder();
        message.append(getRecordInfo(event, " "));
        message.append(event.getMessage());
        if (showStackTraces) {
            message.append(getStackTraceAsString(event.getThrown(), "\n", "\t"));
        }
        return message.toString();
    }

    @Override
    protected String getRecordInfo(LogRecord event, String newline) {
        Date date = new Date(event.getMillis());
        StringBuilder s = new StringBuilder();
        s.append(dtFormat.format(date));
//        s.append(" "); // comment this line to get rid of classpath name
//        s.append(event.getLoggerName()); // comment this line to get rid of classpath name
        s.append(newline);
//        s.append(event.getLevel().getName());
//        s.append(": ");
        s.append("->");
        return s.toString();
    }
}