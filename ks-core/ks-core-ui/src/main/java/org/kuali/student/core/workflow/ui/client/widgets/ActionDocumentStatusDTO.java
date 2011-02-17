package org.kuali.student.core.workflow.ui.client.widgets;

import java.io.Serializable;

/**
 * @author Igor
 */
public class ActionDocumentStatusDTO implements Serializable{
    private static final long serialVersionUID = 4757436315689091871L;

    private String actions;

    private String documentStatus;

    public String getActions() {
        return actions;
    }

    public void setActions(String actions) {
        this.actions = actions;
    }

    public String getDocumentStatus() {
        return documentStatus;
    }

    public void setDocumentStatus(String documentStatus) {
        this.documentStatus = documentStatus;
    }
}
