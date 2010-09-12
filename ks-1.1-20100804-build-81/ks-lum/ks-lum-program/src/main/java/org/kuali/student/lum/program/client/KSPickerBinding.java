package org.kuali.student.lum.program.client;

import com.google.gwt.user.client.ui.Label;
import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.core.assembly.data.Data;

/**
 * @author Igor
 */
public class KSPickerBinding implements ModelWidgetBinding<Label> {

    @Override
    public void setModelValue(Label label, DataModel model, String path) {

    }

    @Override
    public void setWidgetValue(Label label, DataModel model, String path) {
        Data data = (Data) model.get(path);
    }
}
