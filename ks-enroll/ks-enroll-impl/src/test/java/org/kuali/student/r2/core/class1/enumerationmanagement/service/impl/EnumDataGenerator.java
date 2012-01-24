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

package org.kuali.student.r2.core.class1.enumerationmanagement.service.impl;

import java.util.List;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.core.enumerationmanagement.dto.EnumeratedValueInfo;
import org.kuali.student.r2.core.enumerationmanagement.service.EnumerationManagementService;

public class EnumDataGenerator{
	
	public static void generate(EnumerationManagementService enumService, ContextInfo callContext) throws AlreadyExistsException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DataValidationErrorException, DoesNotExistException, ReadOnlyException{
		MockEnumeratedValueDTOs dtos = new MockEnumeratedValueDTOs("Semester","SemesterEnum" );
		List<EnumeratedValueInfo> values = dtos.getEnumeratedValues();
		for(EnumeratedValueInfo value: values){
			enumService.addEnumeratedValue("SemesterEnum", value.getCode(), value, callContext);
		}
		
		dtos = new MockEnumeratedValueDTOs("City", "CityEnum");
		values = dtos.getEnumeratedValues();
		for(EnumeratedValueInfo value: values){
			enumService.addEnumeratedValue("CityEnum", value.getCode(), value, callContext);
		}
	}
}
