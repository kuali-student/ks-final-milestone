package org.kuali.student.r2.lum.course.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

public class ActiveDatesValidator extends DefaultValidatorImpl {
	private AtpService atpService;

	@Override
	public List<ValidationResultInfo> validateObject(Object data,
			ObjectStructureDefinition objStructure, ContextInfo contextInfo) {
		// Custom validators are required to only override the other
		// validateObject method
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateObject(FieldDefinition field,
			Object o, ObjectStructureDefinition objStructure,
			Stack<String> elementStack, ContextInfo contextInfo) {
		//Get ATPs and compare dates.  If the end is <= the start, throw a validation error.
		List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
		if (o instanceof CourseInfo) {
			CourseInfo course = (CourseInfo) o;
			if (course.getEndTerm() != null && course.getStartTerm() != null) {
				try {
					AtpInfo startAtp = atpService.getAtp(course.getStartTerm(), contextInfo);
					AtpInfo endAtp = atpService.getAtp(course.getEndTerm(), contextInfo);
					if (startAtp.getStartDate()
							.compareTo(endAtp.getStartDate()) > 0) {
						ValidationResultInfo vr = new ValidationResultInfo();
						vr.setElement(field.getName());
						vr.setError("validation.endDateAfterStartDate");
						results.add(vr);
					}
				} catch (Exception e) {
					throw new RuntimeException("Error validating.", e);
				}

			}
		}
		return results;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

}
