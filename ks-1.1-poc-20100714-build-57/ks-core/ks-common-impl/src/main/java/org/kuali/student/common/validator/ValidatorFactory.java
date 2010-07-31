package org.kuali.student.common.validator;

import java.util.HashMap;
import java.util.Map;

import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;



public class ValidatorFactory {

	private Map<String,Validator> customValidators;
	private DefaultValidatorImpl defaultValidator;
	private ObjectStructureDefinition objStructure;
	public ValidatorFactory(){
		
	}
	
	public ValidatorFactory(Validator...validators ){
		this.customValidators = new HashMap<String, Validator>();
		for(Validator validator:validators){
			String validatorName = validator.getClass().getSimpleName();
			customValidators.put(validatorName, validator);
		}
	}
	
	
	public Validator getValidator(String customValidator) {
//		if(defaultValidator==null){
//			defaultValidator = new DefaultValidatorImpl();
//			defaultValidator.setValidatorFactory(this);
//			defaultValidator.setObjStructure(objStructure);
//			return defaultValidator;
//		}
		return customValidators.get(customValidator);
	}
	
	public Validator getValidator(){
		if(defaultValidator==null){
		defaultValidator = new DefaultValidatorImpl();
		}
		defaultValidator.setValidatorFactory(this);
		defaultValidator.setObjStructure(objStructure);
		return defaultValidator;
	}
	public void setObjectStructureDefinition(ObjectStructureDefinition objStructure){
		this.objStructure=objStructure;
	}
	
	public ObjectStructureDefinition getObjectStructureDefinition(){
		return this.objStructure;
	}

	public DefaultValidatorImpl getDefaultValidator() {
		return defaultValidator;
	}

	public void setDefaultValidator(DefaultValidatorImpl defaultValidator) {
		this.defaultValidator = defaultValidator;
	}
}
