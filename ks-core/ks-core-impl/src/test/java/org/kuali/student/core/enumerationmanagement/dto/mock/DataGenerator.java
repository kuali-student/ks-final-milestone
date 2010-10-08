/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
		MockEnumeratedValueDTOs dtos = new MockEnumeratedValueDTOs("Semester","SemesterEnum" );
		List<EnumeratedValueInfo> values = dtos.getEnumeratedValues();
		for(EnumeratedValueInfo value: values){
			enumService.addEnumeratedValue("SemesterEnum", value);
		}
		
		dtos = new MockEnumeratedValueDTOs("City", "CityEnum");
		values = dtos.getEnumeratedValues();
		for(EnumeratedValueInfo value: values){
			enumService.addEnumeratedValue("CityEnum", value);
		}
	}
}
