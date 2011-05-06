package org.kuali.student.r2.core.classI.atp.service.decorators;

import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.infc.HoldsValidator;
import org.kuali.student.r2.core.classI.atp.service.AtpServiceDecorator;

public class AtpServiceValidationDecorator extends AtpServiceDecorator  implements HoldsValidator{
    private DataDictionaryValidator validator;

    @Override
    public DataDictionaryValidator getValidator() {
        return validator;
    }

    @Override
    public void setValidator(DataDictionaryValidator validator) {
        this.validator = validator;
    }

}
