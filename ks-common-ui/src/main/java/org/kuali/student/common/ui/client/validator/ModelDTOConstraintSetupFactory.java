package org.kuali.student.common.ui.client.validator;

import org.kuali.student.common.ui.client.application.Application;
import org.kuali.student.common.validator.ConstraintDataProvider;
import org.kuali.student.common.validator.ConstraintSetupFactory;
import org.kuali.student.core.dictionary.dto.ObjectStructure;

public class ModelDTOConstraintSetupFactory implements ConstraintSetupFactory {
  
	@Override
	public ConstraintDataProvider getDataProvider(Object obj) {

		ModelDTOConstraintDataProvider mdp = new ModelDTOConstraintDataProvider();
		mdp.initialize(obj);

		return mdp;
	}

	@Override
	public ObjectStructure getObjectStructure(String key) {
		return Application.getApplicationContext().getDictionaryData(key);
	}

}
