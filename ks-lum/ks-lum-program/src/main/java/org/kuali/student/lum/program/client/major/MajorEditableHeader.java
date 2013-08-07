package org.kuali.student.lum.program.client.major;

import org.kuali.student.lum.program.client.widgets.EditableHeader;

/**
 * @author Igor
 */
public class MajorEditableHeader extends EditableHeader{

    public MajorEditableHeader(String title, Enum<?> viewToken) {
        super(title, viewToken, MajorManager.getEventBus());
    }
}
