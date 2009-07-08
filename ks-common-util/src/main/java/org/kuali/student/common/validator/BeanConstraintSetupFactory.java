package org.kuali.student.common.validator;

import org.kuali.student.core.dictionary.dto.ObjectStructure;

public class BeanConstraintSetupFactory implements ConstraintSetupFactory {

	@Override
	public ConstraintDataProvider getDataProvider(Object obj) {
		BeanConstraintDataProvider result = new BeanConstraintDataProvider();		
		result.initialize(obj);		
		return result;
	}

	@Override
	public ObjectStructure getObjectStructure(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
