/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.datadictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.kuali.rice.kns.datadictionary.validation.result.ConstraintValidationResult;
import org.kuali.rice.kns.datadictionary.validation.result.DictionaryValidationResult;
import org.kuali.rice.kns.service.DictionaryValidationService;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.infc.ContextInfc;
import org.kuali.student.common.infc.ValidationResultInfc;

/**
 *
 * @author nwright
 */
public class RiceValidatorImpl implements ValidatorInfc {

    private DictionaryValidationService riceDictionaryValidationService;

    public DictionaryValidationService getRiceDictionaryValidationService() {
        return riceDictionaryValidationService;
    }

    public void setRiceDictionaryValidationService(DictionaryValidationService riceDictionaryValidationService) {
        this.riceDictionaryValidationService = riceDictionaryValidationService;
    }

    @Override
    public List<ValidationResultInfc> validate(String validationType, Object info, ContextInfc context) {
        // TODO: figure out what to do with validationType
        DictionaryValidationResult dvr = riceDictionaryValidationService.validate(info);
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
