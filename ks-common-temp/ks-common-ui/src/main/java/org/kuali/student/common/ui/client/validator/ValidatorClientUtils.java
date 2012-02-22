package org.kuali.student.common.ui.client.validator;

import java.util.List;

import org.kuali.student.common.validation.dto.ValidationResultInfo;
import org.kuali.student.common.validation.dto.ValidationResultInfo.ErrorLevel;

public class ValidatorClientUtils {

	/**
	 * Inspect the validation result to determine if there are any errors.
 
	 * 
	 * @param validationResults
	 * @return true if at least one validation result is an error. 
	 */
	public static boolean hasErrors(List<ValidationResultInfo> validationResults){
		if (validationResults !=null){
			for (ValidationResultInfo vr:validationResults){
				if (vr.getErrorLevel() == ErrorLevel.ERROR){
					return true;
				}
			}
		}
		
		return false;
	}

	/**
	 * Inspect the validation result to determine if there are any errors.
	 * 
	 * @param validationResults
	 * @return true if at least one validation result is an error. 
	 */
	public static boolean hasWarnings(List<ValidationResultInfo> validationResults){
		if (validationResults !=null){
			for (ValidationResultInfo vr:validationResults){
				if (vr.getErrorLevel() == ErrorLevel.WARN){
					return true;
				}
			}
		}
		
		return false;
	}

}
