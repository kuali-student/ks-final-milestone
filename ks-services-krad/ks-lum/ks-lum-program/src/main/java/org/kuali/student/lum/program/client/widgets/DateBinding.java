package org.kuali.student.lum.program.client.widgets;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;
import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.common.ui.client.widgets.KSDatePicker;
import org.kuali.student.core.assembly.data.QueryPath;
import org.kuali.student.lum.program.client.ProgramUtils;

import java.util.Date;

/**
 * @author Igor
 */
class DateBinding implements ModelWidgetBinding<KSDatePicker> {

    @Override
    public void setModelValue(KSDatePicker widget, DataModel model, String path) {
        QueryPath qPath = QueryPath.parse(path);
        Date dateValue = widget.getValue();
        if (dateValue != null) {
            model.set(qPath, ProgramUtils.df.format(dateValue));
        }
    }

    @Override
    public void setWidgetValue(KSDatePicker widget, DataModel model, String path) {
        String value = model.get(path);
        if (value != null) {
            widget.setValue(ProgramUtils.df.parse(value));
        }
    }
}
