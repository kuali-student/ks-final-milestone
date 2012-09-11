package org.kuali.student.common.ui.client.configurable.mvc.multiplicity;

import org.kuali.student.common.ui.client.configurable.mvc.binding.ModelWidgetBinding;

import com.google.gwt.user.client.ui.Widget;

public interface MultiplicityFieldWidgetInitializer {
    public Widget getNewWidget();
    public ModelWidgetBinding getModelWidgetBindingInstance();
}

