package org.kuali.student.common.ui.client.widgets.search;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;

/**
 * @author Igor
 */
public class KSPickerView extends Composite {

    private Label label = new Label("Hello");

    public KSPickerView() {
        initWidget(label);
    }
}
