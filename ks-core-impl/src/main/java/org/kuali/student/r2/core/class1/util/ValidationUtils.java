/**
 * Copyright 2011 The Kuali Foundation Licensed under the
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
 *
 * Created by bobhurt on 9/9/11
 */
package org.kuali.student.r2.core.class1.util;



import java.util.List;

import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

/**
 * This class provides static utility methods for services.
 *
 * @author Kuali Student Team
 */
public class ValidationUtils {

    public static List<ValidationResultInfo> validateInfo(  DataDictionaryValidator validator,
                                                            String validationType,
                                                            Object info,
                                                            ContextInfo context)
    		throws OperationFailedException, MissingParameterException, InvalidParameterException {
        if (null == validator) {
            throw new InvalidParameterException("DataDictionaryValidator parameter cannot be null");
        }

        List<ValidationResultInfo> errors;
        try {
            errors = validator.validate(DataDictionaryValidator.ValidationType.fromString(validationType), info, context);
        } catch (PermissionDeniedException ex) {
            throw new OperationFailedException("Validation failed due to permission exception", ex);
        }
        return errors;
    }

    /**
     * Convert a list of ValidationResultInfo's into a string.
     *
     * @param vris The list of validation results.
     * @return the semi colon then newline delimited list of results.
     */
    public static String asString(List<ValidationResultInfo> vris) {
        StringBuilder sb = new StringBuilder();
        String newLine = ";";
        for (ValidationResultInfo vri : vris) {
            sb.append(newLine);
            newLine = "/n";
            sb.append(vri.getMessage());
        }
        return sb.toString();
    }

}
