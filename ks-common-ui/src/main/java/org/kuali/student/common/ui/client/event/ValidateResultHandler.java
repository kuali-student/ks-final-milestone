package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEventHandler;

public interface ValidateResultHandler extends UncheckedApplicationEventHandler {
    public void onValidateResult(ValidateResultEvent event);
}