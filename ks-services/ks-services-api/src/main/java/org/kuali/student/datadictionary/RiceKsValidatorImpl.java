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
package org.kuali.student.datadictionary;

import java.util.List;
import org.kuali.rice.kns.datadictionary.DataDictionaryEntry;
import org.kuali.rice.kns.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.kns.service.DictionaryValidationService;
import org.kuali.student.common.infc.ValidationResultInfc;


/**
 * This is an implementation that gets the dictionary directly using KS methods but then calls
 * the rice validation method that takes in the dictionary entry to be used.
 *
 * *** THIS IS THE ONLY WAY I COULD GET THE INTEGRATION TO WORK ***
 * ==> I could not figure out why the RiceValidatorImpl doesn't validate anything
 *
 * @author nwright
 */
public class RiceKsValidatorImpl implements ValidatorInfc {
    private static org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(RiceKsValidatorImpl.class);
    
    private KsDictionaryServiceInfc ksDictionaryService;

    private DictionaryValidationService riceService;

    public RiceKsValidatorImpl() {
    }

    public KsDictionaryServiceInfc getKsDictionaryService() {
        return ksDictionaryService;
    }

    public void setKsDictionaryService(KsDictionaryServiceInfc ksDictionaryService) {
        this.ksDictionaryService = ksDictionaryService;
    }

    public DictionaryValidationService getRiceService() {
        return riceService;
    }

    public void setRiceService(DictionaryValidationService riceService) {
        this.riceService = riceService;
    }

    @Override
    public List<ValidationResultInfc> validate(ValidationType validationType, Object info) {
       boolean doOptionalProcessing = true;
//       @param doOptionalProcessing true if the validation should do optional validation
//       (e.g. to check if empty values are required or not), false otherwise
       if (validationType.equals(ValidatorInfc.ValidationType.SKIP_REQUREDNESS_VALIDATIONS)) {
           doOptionalProcessing = false;
       }
       String entryName = info.getClass().getName();
        DataDictionaryEntry dictEntry = this.ksDictionaryService.getDataDictionaryEntry(entryName);
        DictionaryValidationResult dvr = riceService.validate(info, entryName, dictEntry, doOptionalProcessing);
        Rice2KsValidationResultConverter converter = new Rice2KsValidationResultConverter ();
        List<ValidationResultInfc> vrs = converter.convert(dvr);
        return vrs;
    }
}
