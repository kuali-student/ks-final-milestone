package org.kuali.student.lum.program.client.bacc;

import org.kuali.student.lum.program.client.widgets.EditableHeader;

/**
 * @author Igor
 */
public class BaccEditableHeader extends EditableHeader {

    public BaccEditableHeader(String title, Enum<?> viewToken) {
        super(title, viewToken, CredentialManager.getEventBus());
    }
}
