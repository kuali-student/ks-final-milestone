package org.kuali.student.r2.common.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * ValidatorFactory provides a mechanism to 
 *  
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */

// This class is a special case, this class/equivelent doesn't exist in R2 
//packages and is a common and has methods used in both R1 and R2 packages, 
//this class was duplicated to R2 and modified to work with R2 services
//BaseAbstractValidator, BaseAbstractValidator, Validator, ValidatorFactory

public class ValidatorFactory {
	private static final Logger LOG = Logger.getLogger(ValidatorFactory.class);
	private volatile Map<String,Validator> customValidators = null; 
	private DefaultValidatorImpl defaultValidator = new DefaultValidatorImpl();
	
	private List<Validator> validatorList = new ArrayList<Validator>();
	
	public ValidatorFactory(){
		defaultValidator.setValidatorFactory(this);
	}
	
	/**
	 * Updated to fix double check lock not working
	 * @return
	 */
	public Map<String,Validator> getCustomValidators(){
		Map<String,Validator> result = customValidators;
	    if(result == null) {
	    	synchronized (this) {
	    		result = customValidators;
	    		if(result == null){
	    	        result = new HashMap<String, Validator>();
	    	        for(Validator validator: validatorList){
	    	            String validatorName = validator.getClass().getName();
	    	            result.put(validatorName, validator);
	    	        }
	    			customValidators = result;
	    		}
			}
	    }
	    return result;
	}
	
	
	public Validator getValidator(String customValidator) {
	
		LOG.info("Retrieving validatior:" + customValidator);
	    
	    Validator v = getCustomValidators().get(customValidator); 
	    
	    if(v != null && v instanceof BaseAbstractValidator) {
	        BaseAbstractValidator bv = (BaseAbstractValidator)v;
	        bv.setValidatorFactory(this);
	        return bv;
	    } else {
	       return v;
	    }
	}
	
	public Validator getValidator(){
		return defaultValidator;
	}
	
	public DefaultValidatorImpl getDefaultValidator() {
		return defaultValidator;
	}

	public void setDefaultValidator(DefaultValidatorImpl defaultValidator) {
		this.defaultValidator = defaultValidator;
		this.defaultValidator.setValidatorFactory(this);
	}

    public void setValidatorList(List<Validator> validatorList) {
        this.validatorList = validatorList;
    }
}
