package com.nuance.imaging.ops.opsclient.util;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

public class TicksSinceFormat extends DateFormat {

	private static final long serialVersionUID = 1L;

	@Override
    public StringBuffer format(Date date, StringBuffer buffer, FieldPosition field) {
        long millis = date.getTime();

        return new StringBuffer("/Date(" + millis + ")/");
    }

    @Override
    public Date parse(String string, ParsePosition position) {
        int start = string.indexOf("(") + 1;
        int end = string.indexOf(")");

        String ms = string.substring(start, end);
        Date date = new Date(Long.parseLong(ms));

        position.setIndex(string.length() - 1); // MUST SET THIS
        return date;
    }

    @Override
    public Object clone() {
        return new TicksSinceFormat(); // MUST SET THIS
    }
}