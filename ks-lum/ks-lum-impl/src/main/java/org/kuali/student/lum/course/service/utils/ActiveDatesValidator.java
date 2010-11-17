package org.kuali.student.lum.course.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.kuali.student.common.validator.DefaultValidatorImpl;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class ActiveDatesValidator extends DefaultValidatorImpl {
	private AtpService atpService;
    @Override
    public List<ValidationResultInfo> validateObject(Object data, ObjectStructureDefinition objStructure) {
        // Custom validators are required to only override the other validateObject method
        return null;
    }

	@Override
	public List<ValidationResultInfo> validateObject(FieldDefinition field,
			Object o, ObjectStructureDefinition objStructure,
			Stack<String> elementStack) {
		
//		try {
//			
//			//AtpInfo endAtp = atpService.getAtp((String)o);
//		} catch (DoesNotExistException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (InvalidParameterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (MissingParameterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (OperationFailedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return new ArrayList<ValidationResultInfo>();
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}
    
}
