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
import org.kuali.rice.krad.datadictionary.DataObjectEntry;
import org.kuali.rice.krad.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.krad.service.DictionaryValidationService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

/**
 * This is an implementation that gets the dictionary directly using KS methods but then calls
 * the rice validation method that takes in the dictionary entry to be used.
 *
 * *** THIS IS THE ONLY WAY I COULD GET THE INTEGRATION TO WORK ***
 *
 * I could not figure out how to get the validation to work by simply calling
 * the validate(info, doOptionalProcessing) method so instead I had to read the dictionary
 * myself and pass the dictionary in on the call using the
 * validate(info,entryName, dictEntry, doOptionalProcessing) method instead
 *
 *
 * @author nwright
 */
public class RiceDataDictionaryValidatorImpl implements DataDictionaryValidator {

    private static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(RiceDataDictionaryValidatorImpl.class);
    private RiceDataDictionaryServiceInfc riceDataDictionaryService;
    private DictionaryValidationService riceDictionaryValidationService;

    public RiceDataDictionaryValidatorImpl() {
    }

    public RiceDataDictionaryServiceInfc getRiceDataDictionaryService() {
        return riceDataDictionaryService;
    }

    public void setRiceDataDictionaryService(RiceDataDictionaryServiceInfc riceDataDictionaryService) {
        this.riceDataDictionaryService = riceDataDictionaryService;
    }

    public DictionaryValidationService getRiceDictionaryValidationService() {
        return riceDictionaryValidationService;
    }

    public void setRiceDictionaryValidationService(DictionaryValidationService riceDictionaryValidationService) {
        this.riceDictionaryValidationService = riceDictionaryValidationService;
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
        String entryName = info.getClass().getName();
        DataObjectEntry dictEntry;
        dictEntry = this.riceDataDictionaryService.getDataObjectEntry(entryName);
        if (dictEntry == null) {
            throw new OperationFailedException("Dictionary entry for " + entryName + " does not exist");
        }
        DictionaryValidationResult dvr = this.riceDictionaryValidationService.validate(info,
          entryName,
          dictEntry,
          doOptionalProcessing);
        Rice2ValidationResultConverter converter = new Rice2ValidationResultConverter();
        List<ValidationResultInfo> vrs = converter.convert(dvr);
        return vrs;
    }
}
