package org.kuali.student.common.ui.client.event;

import org.kuali.student.common.ui.client.mvc.ApplicationEventHandler;

public interface ModifyActionHandler extends ApplicationEventHandler{
    public void onModify(ModifyActionEvent modifyEvent);
}


