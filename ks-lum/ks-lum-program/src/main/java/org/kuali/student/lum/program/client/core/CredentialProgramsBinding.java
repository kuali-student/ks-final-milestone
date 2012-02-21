package org.kuali.student.lum.program.client.core;

import com.google.gwt.user.client.ui.VerticalPanel;

import org.kuali.student.common.assembly.data.Data;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.KSLabel;

/**
 * @author Igor
 */
public class CredentialProgramsBinding implements ModelWidgetBinding<VerticalPanel> {

    @Override
    public void setModelValue(VerticalPanel widget, DataModel model, String path) {
        throw new UnsupportedOperationException("Operation is not supported");
    }

    @Override
    public void setWidgetValue(VerticalPanel verticalPanel, DataModel model, String path) {
        verticalPanel.clear();
        final Data credentialPrograms = model.get(path);
        for (final Data.Property property : credentialPrograms) {
            verticalPanel.add(new KSLabel(property.<String>getValue()));
        }
    }
}
