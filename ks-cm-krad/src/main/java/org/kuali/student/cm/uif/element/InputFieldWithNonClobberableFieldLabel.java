package org.kuali.student.cm.uif.element;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krad.uif.element.Label;
import org.kuali.rice.krad.uif.field.InputField;
import org.kuali.rice.krad.uif.field.InputFieldBase;

/**
 * KRAD's ViewCleaner cleans up InputFields during completion. We don't want that to happen with Review Proposal
 * because the validation messages have null where the label should be. This class prevents the fieldLabel and
 * labelText from being overwritten.
 */
public class InputFieldWithNonClobberableFieldLabel extends InputFieldBase {
    @Override
    public void setFieldLabel(Label fieldLabel) {
        if (fieldLabel != null) {
            super.setFieldLabel(fieldLabel);
        }
    }

    @Override
    public void setLabel(String text) {
        if (StringUtils.isNotBlank(text)) {
            super.setLabel(text);
        }
    }
}
