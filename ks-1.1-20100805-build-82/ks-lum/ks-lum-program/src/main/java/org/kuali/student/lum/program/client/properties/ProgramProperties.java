package org.kuali.student.lum.program.client.properties;

import com.google.gwt.core.client.GWT;

/**
 * @author Igor
 */
public class ProgramProperties {
    private static final Program properties = GWT.create(Program.class);

    public static Program get() {
        return properties;
    }
}
