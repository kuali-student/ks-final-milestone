package org.kuali.student.common.ui.client.event;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEvent;
import org.kuali.student.core.validation.dto.ValidationResultContainer;

import com.google.gwt.event.shared.GwtEvent.Type;

public class ValidateResultEvent extends UncheckedApplicationEvent<ValidateResultHandler> {
    public static final Type<ValidateResultHandler> TYPE = new Type<ValidateResultHandler>();
    private List<ValidationResultContainer> validationResultList;
    @Override
    protected void dispatch(ValidateResultHandler handler) {
        handler.onValidateResult(this);
    }

    @Override
    public Type<ValidateResultHandler> getAssociatedType() {
        return TYPE;
    }

    public void setValidationResult(List<ValidationResultContainer> l){
        validationResultList = l;
    }
    public List<ValidationResultContainer> getValidationResult(){
        return validationResultList;
    }
}