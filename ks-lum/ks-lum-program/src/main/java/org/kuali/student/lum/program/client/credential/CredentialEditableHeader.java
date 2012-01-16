package org.kuali.student.lum.program.client.credential;

import org.kuali.student.lum.program.client.widgets.EditableHeader;

/**
 * @author Igor
 */
public class CredentialEditableHeader extends EditableHeader {

    public CredentialEditableHeader(String title, Enum<?> viewToken) {
        super(title, viewToken, CredentialManager.getEventBus());
    }
}
