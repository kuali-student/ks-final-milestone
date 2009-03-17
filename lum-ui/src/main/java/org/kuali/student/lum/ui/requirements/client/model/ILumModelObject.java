package org.kuali.student.lum.ui.requirements.client.model;

import org.kuali.student.commons.ui.mvc.client.model.ModelObject;


public interface ILumModelObject extends ModelObject {

    Object getValue(String fieldName);
    
}
