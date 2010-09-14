package org.kuali.student.lum.program.client;

import org.kuali.student.core.assembly.data.Data;

/**
 * @author Igor
 */
public class VariationRegistry {

    private static Data data;

    public static Data getData() {
        return data;
    }

    public static void setData(Data data) {
        VariationRegistry.data = data;
    }
}
