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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.kuali.rice.kns.datadictionary.validation.result.ConstraintValidationResult;
import org.kuali.rice.kns.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.kns.service.DictionaryValidationService;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.infc.ValidationResultInfc;


/**
 *
 * @author nwright
 */
public class RiceValidatorImpl implements ValidatorInfc {

    private DictionaryValidationService riceService;

    public RiceValidatorImpl() {
    }


    public RiceValidatorImpl(DictionaryValidationService riceService) {
        this.riceService = riceService;
    }

    public DictionaryValidationService getRiceService() {
        return riceService;
    }

    public void setRiceService(DictionaryValidationService riceService) {
        this.riceService = riceService;
    }

    @Override
    public List<ValidationResultInfc> validate(String validationType, Object info) {
        // TODO: figure out what to do with validationType
        DictionaryValidationResult dvr = riceService.validate(info);
        List<ValidationResultInfc> vrs = new ArrayList<ValidationResultInfc>();
        Iterator<ConstraintValidationResult> it = dvr.iterator();
        while (it.hasNext()) {
            ValidationResultInfo vr = null;
            ConstraintValidationResult cvr = it.next();
            switch (cvr.getStatus()) {
                case OK:
                    continue;
                case NOCONSTRAINT:
                    continue;
                case INAPPLICABLE:
                    continue;
                case ERROR:
                    vr = new ValidationResultInfo ();
                    vr.setElement(cvr.getAttributeName());
                    vr.setError(cvr.getErrorKey());
                    vrs.add(vr);
                case WARN:
                    vr = new ValidationResultInfo();
                    vr.setElement(cvr.getAttributeName());
                    vr.setWarning(cvr.getErrorKey());
                    vrs.add(vr);
            }
        }

        return vrs;
    }
}
