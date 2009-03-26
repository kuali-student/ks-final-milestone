package org.kuali.student.common.ui.client.widgets;

import org.kuali.student.common.ui.client.dto.HelpInfo;

public abstract class KSHelpDialogAbstract extends KSInfoDialogPanel {

 /**
  * @deprecated
  */   
    
    public KSHelpDialogAbstract() {
        super();
    }

    protected abstract void init(HelpInfo helpInfo);

}