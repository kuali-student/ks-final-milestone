package org.kuali.student.ap.academicplan.infc;

import org.kuali.student.r2.common.infc.HasId;

/**
 * Student's Learning Plan that contains a list of plan items
 *
 * @Author mguilla
 */
public interface PlaceholderInstance extends HasId, TypedObjectReference {  
	
	/**
	 * the placeholder id
	 * @name the placeholder id
	 */
	String getPlaceholderId();

	/**
	 * the advisor id
	 * @name the advisor id
	 */
	String getAdvisorId();

	/**
	 * has the advisor approved/liked the placeholder instance
	 * @name has the advisor approved/liked the placeholder instance
	 */
	boolean isAdvisorOK();

	/**
	 * has the student approved/liked the placeholder instance
	 * @name has the student approved/liked the placeholder instance
	 */
	boolean isStudentOK();
}
