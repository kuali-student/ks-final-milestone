package org.kuali.student.lum.program.client.view;

import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBindingSupport;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.lum.program.client.ProgramConstants;

/**
 * @author Igor
 */
public class VariationsBinding extends ModelWidgetBindingSupport<VerticalPanel> {

    @Override
    public void setModelValue(VerticalPanel verticalPanel, DataModel model, String path) {
        throw new UnsupportedOperationException("Method is not implemented");
    }

    @Override
    public void setWidgetValue(VerticalPanel verticalPanel, DataModel model, String path) {
        verticalPanel.clear();
        Data variationMap = model.get(path);
        for (Data.Property property : variationMap) {
            Data variationData = property.getValue();
            verticalPanel.add(new Anchor(variationData.<String>get(ProgramConstants.LONG_TITLE)));
        }
    }
}
