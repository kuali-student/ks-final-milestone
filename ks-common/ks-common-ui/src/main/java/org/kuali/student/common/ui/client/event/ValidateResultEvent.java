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

package org.kuali.student.common.ui.client.event;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.ui.client.mvc.UncheckedApplicationEvent;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

/**
 * Event for returning the results of a validation or validation request.
 * 
 * @author Kuali Student Team
 *
 */
public class ValidateResultEvent extends UncheckedApplicationEvent<ValidateResultHandler> {
    public static final Type<ValidateResultHandler> TYPE = new Type<ValidateResultHandler>();
    private List<ValidationResultInfo> validationResultList = new ArrayList<ValidationResultInfo>();
    @Override
    protected void dispatch(ValidateResultHandler handler) {
        handler.onValidateResult(this);
    }

    @Override
    public Type<ValidateResultHandler> getAssociatedType() {
        return TYPE;
    }

    public void setValidationResult(List<ValidationResultInfo> l){
        validationResultList = l;
    }

    public void addValidationResult(List<ValidationResultInfo> l){
        validationResultList.addAll(l);
    }
    public List<ValidationResultInfo> getValidationResult(){
        return validationResultList;
    }
}
