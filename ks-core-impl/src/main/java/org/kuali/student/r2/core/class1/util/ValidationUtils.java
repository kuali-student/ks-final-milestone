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


import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.*;
import org.kuali.student.r2.common.infc.HasType;
import org.kuali.student.r2.core.class1.type.dto.TypeInfo;
import org.kuali.student.r2.core.class1.type.service.TypeService;
import org.springframework.util.Log4jConfigurer;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides static utility methods for services.
 *
 * @author Kuali Student Team
 */
public class ValidationUtils {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(ValidationUtils.class);

    public static String TYPE_VALIDATION_TYPE_KEY = "typeValidation";

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

    /**
     * Valdiate that the type key is in the db. Should be used on all create/update statements that have types.
     * @param typeKeys
     * @param typeService
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public static  List<ValidationResultInfo> validateTypeKeys(List<String> typeKeys, TypeService typeService, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors = new ArrayList<ValidationResultInfo>();

        for(String typeKey : typeKeys){
            try{
                typeService.getType(typeKey, contextInfo);
            }catch (DoesNotExistException ex){
                ValidationResultInfo validationResult = new ValidationResultInfo();
                validationResult.setError("Error trying to use Type that is not configured. Make sure the type exists in the database. [" +typeKey+ "] must be configured.");
                validationResult.setElement(typeKey);
                errors.add(validationResult);
            }
        }

        return errors;
    }

    /**
     * Validate that the type keys are in the database. Should be used on all create/update statements that have types.
     * @param typeKey
     * @param typeService
     * @param contextInfo
     * @return
     * @throws DoesNotExistException
     * @throws InvalidParameterException
     * @throws MissingParameterException
     * @throws OperationFailedException
     * @throws PermissionDeniedException
     */
    public static List<ValidationResultInfo> validateTypeKey(String typeKey, String refObjectUri, TypeService typeService, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        List<ValidationResultInfo> errors = new ArrayList<ValidationResultInfo>();

        try {
//            typeService.getType(typeKey, contextInfo);
            List<TypeInfo> types = typeService.getTypesByRefObjectUri(refObjectUri, contextInfo);
            if (types == null) {
                errors.add(new ValidationResultInfo(String.format("No TypeInfo found for the Ref Object Uri %s", refObjectUri)));
                log.debug(String.format("No TypeInfo found for the Ref Object Uri %s", refObjectUri));
            } else {
                boolean isKeyFound = false;
                for (TypeInfo info : types) {
                    if (typeKey.equals(info.getKey())) {
                        isKeyFound = true;
                    }
                }
                if (!isKeyFound) {
                    errors.add(new ValidationResultInfo(String.format("No TypeInfo found for the Type Key %s with Ref Object Uri %s", typeKey, refObjectUri)));
                    log.debug(String.format("No TypeInfo found for the Type Key %s with Ref Object Uri %s", typeKey, refObjectUri));
                }
            }
        } catch (DoesNotExistException ex) {
            ValidationResultInfo validationResult = new ValidationResultInfo();
            validationResult.setError("Error trying to use Type that is not configured. Make sure the type exists in the database. [" + typeKey + "] must be configured.");
            validationResult.setElement(typeKey);
            errors.add(validationResult);
        }

        return errors;
    }

    /**
     * For all update calls we need to ensure that the types are equal. if not, add an error.
     * @param typeInfo1
     * @param typeInfo2
     * @return
     */
    public static List<ValidationResultInfo> validateTypesAreEqual(HasType typeInfo1, HasType typeInfo2){
        List<ValidationResultInfo> errors = new ArrayList<ValidationResultInfo>();

        if(!typeInfo1.getTypeKey().equals(typeInfo2.getTypeKey()))  {
            ValidationResultInfo validationResult = new ValidationResultInfo();
            validationResult.setError("Error type keys cannot be altered during an update. [" + typeInfo1.getTypeKey() + "] != [" + typeInfo2.getTypeKey() +"]" );
            validationResult.setElement(typeInfo1.getTypeKey());
            errors.add(validationResult);
        }



        return errors;
    }

    public static boolean checkForErrors(List<ValidationResultInfo> errors) {

        if (errors != null && !errors.isEmpty()) {
            for (ValidationResultInfo error : errors) {
                if (error.isError()) {
                    return true;
                }
            }
        }

        return false;
    }


}
