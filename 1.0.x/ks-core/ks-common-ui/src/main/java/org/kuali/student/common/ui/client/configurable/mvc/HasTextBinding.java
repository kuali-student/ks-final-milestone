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

package org.kuali.student.common.ui.client.configurable.mvc;

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
import org.kuali.student.common.ui.client.validator.ClientDateParser;

import com.google.gwt.user.client.ui.HasText;

@Deprecated
public class HasTextBinding implements PropertyBinding<HasText>{
    
    private ClientDateParser dateParser = new ClientDateParser();
    private ModelDTOValue.Type fieldType;
    
    public HasTextBinding(ModelDTOValue.Type fieldType){
        this.fieldType = fieldType;
    }
    
    @Override
    public ModelDTOValue getValue(HasText object) {
        String value = object.getText().trim();
        ModelDTOValue modelValue = null;
        switch (fieldType) {
            case STRING:
                modelValue = new StringType();
                ((StringType) modelValue).set(value);
                break;
            case CHARACTER:
                modelValue = new CharacterType();
                if(value.length() == 1){
                    Character character = new Character(value.charAt(0));
                    ((CharacterType) modelValue).set(character);
                }
                else{
                    throw new UnsupportedOperationException("Characters can only be set with Strings containing 1 character");
                }
                break;
            case INTEGER:
                modelValue = new IntegerType();
                if (value != null && value.length() > 0){
                    ((IntegerType) modelValue).set(Integer.parseInt(value));
                }
                break;
            case LONG:
                modelValue = new LongType();
                ((LongType) modelValue).set(Long.parseLong(value));
                break;
            case FLOAT:
                modelValue = new FloatType();
                ((FloatType) modelValue).set(Float.parseFloat(value));
                break;
            case DOUBLE:
                modelValue = new DoubleType();
                ((DoubleType) modelValue).set(Double.parseDouble(value));
                break;
            case BYTE:
                modelValue = new ByteType();
                ((ByteType) modelValue).set(Byte.parseByte(value));
                break;
            case BOOLEAN:
                modelValue = new BooleanType();
                if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")){
                    ((BooleanType) modelValue).set(Boolean.parseBoolean(value));
                }
                else{
                    throw new UnsupportedOperationException("BooleanTypes can only be set with true or false");
                }
                break;
            case DATE:
                modelValue = new DateType();
                ((DateType) modelValue).set(dateParser.parseDate(value));
                break;
        }
        return modelValue;
    }
    
    

    @Override
    public void setValue(HasText object, ModelDTOValue value) {
        if (value != null && object != null) {
            if(value instanceof StringType){
                object.setText(((StringType)value).get());
            }
            else if(value instanceof CharacterType){
                object.setText(((CharacterType)value).get().toString());
            }
            else if(value instanceof IntegerType){
                Integer intValue = ((IntegerType)value).get();
                if (intValue != null){
                    object.setText(intValue.toString());
                }
            }
            else if(value instanceof LongType){
                object.setText(((LongType)value).get().toString());
            }
            else if(value instanceof FloatType){
                object.setText(((FloatType)value).get().toString());
            }
            else if(value instanceof DoubleType){
                object.setText(((DoubleType)value).get().toString());
            }
            else if(value instanceof ByteType){
                object.setText(((ByteType)value).get().toString());
            }
            else if(value instanceof BooleanType){
                object.setText(((BooleanType)value).get().toString());
            }
            else if(value instanceof DateType){
                object.setText(dateParser.toString(((DateType)value).get()));
            }
        }
        else if(value == null && object != null){
            object.setText("");
        }
    }

}
