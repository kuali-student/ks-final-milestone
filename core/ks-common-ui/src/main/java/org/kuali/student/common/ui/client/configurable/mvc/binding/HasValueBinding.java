/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.common.ui.client.configurable.mvc.binding;

import java.util.Date;

import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.ui.client.mvc.DataModel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasValue;

public class HasValueBinding implements ModelWidgetBinding<HasValue>{
    
    public static HasValueBinding INSTANCE = new HasValueBinding();
      
    private HasValueBinding(){}
    
    @Override
    public void setModelValue(HasValue object, DataModel model, String path) {        
        QueryPath qPath = QueryPath.parse(path);
        Object value = object.getValue();
        
        //TODO: Type checking to ensure that the value type of widget matches model defintion
        if (value instanceof String){
            model.set(qPath, (String)value);
        } else if (value instanceof Character){
            model.set(qPath, (Character)value);
        } else if (value instanceof Integer){
            model.set(qPath, (Integer)value);
        } else if (value instanceof Long){
            model.set(qPath, (Long)value);
        } else if (value instanceof Float){
            model.set(qPath, (Float)value);
        } else if (value instanceof Double){
            model.set(qPath, (Double)value);
        } else if (value instanceof Byte){
            model.set(qPath, (Byte)value);
        } else if (value instanceof Boolean){
            model.set(qPath, (Boolean)value);
        } else if (value instanceof Date){
            model.set(qPath, (Date)value);
        }
        
    }

    @Override
    public void setWidgetValue(HasValue object, DataModel model, String path) {
        QueryPath qPath = QueryPath.parse(path);
        Object value = model.get(qPath);
        
        if (value != null && object != null) {            
            object.setValue(value);
        } else if (object != null){
            try {
                object.setValue("");
            } catch (RuntimeException e) {
                GWT.log("Warning: Ignoring error attempting to setValue for " + object.getClass().getName(), e);
            }
        }
    }

}
