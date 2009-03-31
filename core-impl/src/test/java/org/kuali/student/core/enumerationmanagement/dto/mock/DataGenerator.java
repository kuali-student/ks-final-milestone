package org.kuali.student.core.enumerationmanagement.dto.mock;

import java.util.List;

import org.kuali.student.core.enumerable.dto.EnumeratedValue;
import org.kuali.student.core.enumerationmanagement.service.impl.EnumerationManagementServiceImpl;

public class DataGenerator{
	
	public static void generate(EnumerationManagementServiceImpl enumService){
		MockEnumeratedValueDTOs dtos = new MockEnumeratedValueDTOs("Semester");
		List<EnumeratedValue> values = dtos.getEnumeratedValues();
		for(EnumeratedValue value: values){
			enumService.addEnumeratedValue("SemesterEnum", value);
		}
		
		dtos = new MockEnumeratedValueDTOs("City");
		values = dtos.getEnumeratedValues();
		for(EnumeratedValue value: values){
			enumService.addEnumeratedValue("CityEnum", value);
		}
	}
}