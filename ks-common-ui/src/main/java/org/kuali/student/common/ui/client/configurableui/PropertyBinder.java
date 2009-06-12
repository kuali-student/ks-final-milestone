package org.kuali.student.common.ui.client.configurableui;

import org.kuali.student.core.dto.Idable;

public interface PropertyBinder <T extends Idable, R extends Object> {
    public Object getValue(T object);
    public void setValue(T object, R value);

}
