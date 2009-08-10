package org.kuali.student.common.validator;

import org.kuali.student.core.dictionary.dto.ObjectStructure;

public interface ConstraintSetupFactory {

	/**
	 * @return the dataProvider
	 */
	public ConstraintDataProvider getDataProvider(Object obj);

	/**
	 * 
	 * @return the object structure
	 */
	public ObjectStructure getObjectStructure(String key);
}
