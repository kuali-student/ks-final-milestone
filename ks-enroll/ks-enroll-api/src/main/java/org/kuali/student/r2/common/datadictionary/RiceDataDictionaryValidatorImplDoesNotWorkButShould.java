/*
 * Copyright 2011 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.common.datadictionary;

import java.util.List;
import org.kuali.rice.kns.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.kns.service.DictionaryValidationService;
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator.ValidationType;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;


/**
 * This is an adapter that calls rice to do the valication
 *
 * TODO: figure why this does not validate anything
 *
 * @author nwright
 */
public class RiceDataDictionaryValidatorImplDoesNotWorkButShould implements DataDictionaryValidator {

    private DictionaryValidationService riceService;

    public RiceDataDictionaryValidatorImplDoesNotWorkButShould() {
    }


    public RiceDataDictionaryValidatorImplDoesNotWorkButShould(DictionaryValidationService riceService) {
        this.riceService = riceService;
    }

    public DictionaryValidationService getRiceService() {
        return riceService;
    }

    public void setRiceService(DictionaryValidationService riceService) {
        this.riceService = riceService;
    }

    @Override
    public List<ValidationResultInfo> validate(ValidationType validationType, Object info, ContextInfo context)
            throws OperationFailedException, MissingParameterException, InvalidParameterException, PermissionDeniedException {
       boolean doOptionalProcessing = true;
//       @param doOptionalProcessing true if the validation should do optional validation
//       (e.g. to check if empty values are required or not), false otherwise
       if (validationType.equals(DataDictionaryValidator.ValidationType.SKIP_REQUREDNESS_VALIDATIONS)) {
           doOptionalProcessing = false;
       }
        DictionaryValidationResult dvr = riceService.validate(info, doOptionalProcessing);
        Rice2ValidationResultConverter converter = new Rice2ValidationResultConverter ();
        List<ValidationResultInfo> vrs = converter.convert(dvr);
        return vrs;
    }
}
