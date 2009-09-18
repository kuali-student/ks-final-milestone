package org.kuali.student.core.enumerationmanagement.dto.mock;

import java.util.List;

import org.kuali.student.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.core.enumerationmanagement.service.impl.EnumerationManagementServiceImpl;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;

public class DataGenerator{
	
	public static void generate(EnumerationManagementServiceImpl enumService) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException{
		MockEnumeratedValueDTOs dtos = new MockEnumeratedValueDTOs("Semester");
		List<EnumeratedValueInfo> values = dtos.getEnumeratedValues();
		for(EnumeratedValueInfo value: values){
			enumService.addEnumeratedValue("SemesterEnum", value);
		}
		
		dtos = new MockEnumeratedValueDTOs("City");
		values = dtos.getEnumeratedValues();
		for(EnumeratedValueInfo value: values){
			enumService.addEnumeratedValue("CityEnum", value);
		}
	}
}