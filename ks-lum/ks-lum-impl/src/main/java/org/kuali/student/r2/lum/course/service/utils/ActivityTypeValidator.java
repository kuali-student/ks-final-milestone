package org.kuali.student.r2.lum.course.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.AmountInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeAmountInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.kuali.student.r2.lum.course.dto.ActivityInfo;

/**
 * Validates Subject COde usage
 * If the Course has a subject code with usage of all, the 
 *
 */
public class ActivityTypeValidator extends DefaultValidatorImpl {
	
	@Override
	public List<ValidationResultInfo> validateObject(FieldDefinition field,	Object o, ObjectStructureDefinition objStructure,
			Stack<String> elementStack, ContextInfo contextInfo) {
		
		List<ValidationResultInfo> validationResults = new ArrayList<ValidationResultInfo>();

		if (o instanceof ActivityInfo && o != null) {
			ActivityInfo activity = (ActivityInfo)o;
 
			if (hasActivityData(activity)) { // && !hasText(activity.getActivityType())){
				ValidationResultInfo vr = new ValidationResultInfo();
				String elementPath = getElementXpath(elementStack) + "/activityType";
				vr.setElement(elementPath);
				vr.setError("validation.required");
				validationResults.add(vr);
			}			
		}
		
		return validationResults;
	}
	
	protected boolean hasActivityData(ActivityInfo activity){
		boolean hasData = false;
		
		AmountInfo contactHours = activity.getContactHours();
		if (contactHours != null){
			hasData = hasData || hasText(contactHours.getUnitQuantity()) || hasText(contactHours.getUnitTypeKey());
		}
		
		int enrollmentEstimate = activity.getDefaultEnrollmentEstimate();
		hasData = hasData || enrollmentEstimate >= 0;
		
		TimeAmountInfo duration = activity.getDuration();
		if (duration != null){
			hasData = hasData || hasText(duration.getAtpDurationTypeKey());
			hasData = hasData || (duration.getTimeQuantity() != null && duration.getTimeQuantity().intValue() >= 0);
		}
						
		return hasData;
	}

}
