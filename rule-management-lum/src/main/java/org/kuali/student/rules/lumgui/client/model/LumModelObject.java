package org.kuali.student.rules.lumgui.client.model;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;


public interface LumModelObject extends ModelObject {

    Object getValue(String fieldName);
    
}
