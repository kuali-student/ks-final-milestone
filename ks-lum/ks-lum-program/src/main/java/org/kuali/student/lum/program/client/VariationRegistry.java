package org.kuali.student.lum.program.client;

import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.core.assembly.data.Data;

/**
 * @author Igor
 */
public class VariationRegistry {

    private static Data data;

    private static ViewContext viewContext;

    public static Data getData() {
        return data;
    }

    public static void setData(Data data) {
        VariationRegistry.data = data;
    }

    public static ViewContext getViewContext() {
        return viewContext;
    }

    public static void setViewContext(ViewContext viewContext) {
        VariationRegistry.viewContext = viewContext;
    }
}
