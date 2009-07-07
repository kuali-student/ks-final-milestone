package org.kuali.student.common.validator;

import org.kuali.student.core.dictionary.newmodel.dto.ObjectStructure;

public class ModelDTOConstraintSetupFactory implements ConstraintSetupFactory {

	@Override
	public ConstraintDataProvider getDataProvider(Object obj) {
		ModelDTOConstraintDataProvider mdp = new ModelDTOConstraintDataProvider();
		mdp.initialize(obj);
		return mdp;
	}

	@Override
	public ObjectStructure getObjectStructure(String key) {
		// TODO Auto-generated method stub
		return null;
	}

}
