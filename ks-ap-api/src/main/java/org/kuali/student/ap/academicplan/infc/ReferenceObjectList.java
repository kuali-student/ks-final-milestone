package org.kuali.student.ap.academicplan.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.HasId;

/**
 *  a list of reference items
 *
 * @Author mguilla
 */
public interface ReferenceObjectList extends HasId {  
    
	
	public List<TypedObjectReference> getReferences();
	  

}
