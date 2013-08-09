package org.kuali.student.lum.program.client.core;

import org.kuali.student.lum.program.client.widgets.EditableHeader;

/**
 * @author Igor
 */
public class CoreEditableHeader extends EditableHeader {

    public CoreEditableHeader(String title, Enum<?> viewToken) {
        super(title, viewToken, CoreManager.getEventBus());
    }
}
