package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.mvc.ApplicationEventHandler;

public interface ModifyHandler extends ApplicationEventHandler{
    public void onModify(ModifyEvent modifyEvent);
}
