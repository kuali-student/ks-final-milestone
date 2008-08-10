/*
 * Copyright 2008 The Kuali Foundation
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
package org.kuali.student.ui.personidentity.client.model.validators;

import java.util.Map;

import org.kuali.student.commons.ui.messages.client.Messages;
import org.kuali.student.commons.ui.validators.client.ValidationResult;
import org.kuali.student.commons.ui.validators.client.Validator;
import org.kuali.student.ui.personidentity.client.model.GwtPersonCreateInfo;

/**
 * This is a description of what this class does - Garey don't forget to fill this in. 
 * 
 * @author Kuali Student Team
 *
 */
public class PersonCreateValidator implements Validator {


    public void setMessages(Messages messages) {
    // TODO Garey - THIS METHOD NEEDS JAVADOCS

    }

    
    public ValidationResult validate(Object value) {
        
        ValidationResult res = new ValidationResult();
        
        if(value instanceof GwtPersonCreateInfo){
            GwtPersonCreateInfo pInfo = (GwtPersonCreateInfo)value;
            
            if(pInfo.getPreferredName().getGivenName() == null ||
                    "".equals(pInfo.getPreferredName().getGivenName())   ){
                res.addError("Given Name must not be null");
            }
            
            if(pInfo.getPreferredName().getSurname() == null ||
                    "".equals(pInfo.getPreferredName().getSurname())   ){
                res.addError("Sur Name must not be null");
            }                      
        }
        
        return res;
    }

    
    public ValidationResult validate(Object value, String valueName) {
        // TODO Garey - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    
    public ValidationResult validate(Object value, Map<String, String> attributes) {
        // TODO Garey - THIS METHOD NEEDS JAVADOCS
        return null;
    }
    
    public ValidationResult validate(Object value, String valueName, Map<String, String> attributes) {
        // TODO Garey - THIS METHOD NEEDS JAVADOCS
        return null;
    }

}
