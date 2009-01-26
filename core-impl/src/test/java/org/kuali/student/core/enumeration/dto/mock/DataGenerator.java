package org.kuali.student.core.enumeration.dto.mock;

import java.util.List;


import org.kuali.student.core.enumeration.dto.EnumeratedValue;
import org.kuali.student.core.enumeration.service.impl.EnumerationServiceImpl;

public class DataGenerator{
	
	public static void generate(EnumerationServiceImpl enumService){
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