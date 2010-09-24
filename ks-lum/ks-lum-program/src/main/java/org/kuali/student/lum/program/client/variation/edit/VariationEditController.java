package org.kuali.student.lum.program.client.variation.edit;

import com.google.gwt.core.client.GWT;
import org.kuali.student.common.ui.client.application.ViewContext;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.variation.VariationController;

/**
 * @author Igor
 */
public class VariationEditController extends VariationController {

    public VariationEditController(String name, DataModel programModel, ViewContext viewContext) {
        super(name, programModel, viewContext);
        configurer = GWT.create(VariationEditConfigurer.class);
    }
}
