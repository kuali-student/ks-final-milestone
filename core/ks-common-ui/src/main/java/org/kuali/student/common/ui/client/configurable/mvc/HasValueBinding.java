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
package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.Date;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.BooleanType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ByteType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.CharacterType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.DateType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.DoubleType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.FloatType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.IntegerType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.LongType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasValue;

public class HasValueBinding implements PropertyBinding<HasValue>{
    
    public static HasValueBinding INSTANCE = new HasValueBinding();
      
    private HasValueBinding(){}
    
    @Override
    public ModelDTOValue getValue(HasValue object) {
        Object value = object.getValue();
        ModelDTOValue modelValue = null;
        if (value != null) {
            if(value instanceof String){
                modelValue = new StringType();
                ((StringType) modelValue).set((String) value);
            }
            else if(value instanceof Character){
                modelValue = new CharacterType();
                ((CharacterType) modelValue).set((Character) value);
            }
            else if(value instanceof Integer){
                modelValue = new IntegerType();
                ((IntegerType) modelValue).set((Integer) value);
            }
            else if(value instanceof Long){
                modelValue = new LongType();
                ((LongType) modelValue).set((Long) value);
            }
            else if(value instanceof Float){
                modelValue = new FloatType();
                ((FloatType) modelValue).set((Float) value);
            }
            else if(value instanceof Double){
                modelValue = new DoubleType();
                ((DoubleType) modelValue).set((Double) value);
            }
            else if(value instanceof Byte){
                modelValue = new ByteType();
                ((ByteType) modelValue).set((Byte) value);
            }
            else if(value instanceof Boolean){
                modelValue = new BooleanType();
                ((BooleanType) modelValue).set((Boolean) value);
            }
            else if(value instanceof Date){
                modelValue = new DateType();
                ((DateType) modelValue).set((Date) value);
            } else if (value instanceof ModelDTOValue){
                modelValue = (ModelDTOValue)value;
            }
        }
        
        return modelValue;
    }

    @Override
    public void setValue(HasValue object, ModelDTOValue value) {
        if (value != null && object != null) {
            if(value instanceof StringType){
                object.setValue(((StringType)value).get());
            }
            else if(value instanceof CharacterType){
                object.setValue(((CharacterType)value).get());
            }
            else if(value instanceof IntegerType){
                object.setValue(((IntegerType)value).get());
            }
            else if(value instanceof LongType){
                object.setValue(((LongType)value).get());
            }
            else if(value instanceof FloatType){
                object.setValue(((FloatType)value).get());
            }
            else if(value instanceof DoubleType){
                object.setValue(((DoubleType)value).get());
            }
            else if(value instanceof ByteType){
                object.setValue(((ByteType)value).get());
            }
            else if(value instanceof BooleanType){
                object.setValue(((BooleanType)value).get());
            }
            else if(value instanceof DateType){
                object.setValue(((DateType)value).get());
            }
        } else if (object != null){
               try {
                object.setValue("");
            } catch (RuntimeException e) {
                GWT.log("Warning: Ignoring error attempting to setValue for " + object.getClass().getName(), e);
            }
        }
    }

}
