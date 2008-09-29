package org.kuali.student.rules.devgui.server.convert;

import java.io.PrintWriter;
import java.io.StringWriter;

import com.google.gwt.user.client.rpc.SerializableException;

public class GwtException extends SerializableException {

    public static SerializableException toGWT(Exception ex) {
        return new SerializableException(GwtException.stringException(ex));
    }

    private static String stringException(Exception e) {
        e.printStackTrace();
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        String str = sw.toString();
        return str;
    }
}
