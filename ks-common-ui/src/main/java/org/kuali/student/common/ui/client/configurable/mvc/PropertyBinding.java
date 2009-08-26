package org.kuali.student.common.ui.client.configurable.mvc;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;

public interface PropertyBinding<ObjectType> {
    public ModelDTOValue getValue(ObjectType object);
    public void setValue(ObjectType object, ModelDTOValue value);
}
