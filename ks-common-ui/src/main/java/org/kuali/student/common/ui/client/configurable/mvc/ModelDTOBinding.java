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

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;

public class ModelDTOBinding implements PropertyBinding<ModelDTO>{
    
    private String fieldKey;
    
    public ModelDTOBinding(String fieldKey){
        this.fieldKey = fieldKey;
    }
    
    @Override
    public ModelDTOValue getValue(ModelDTO object) {
        //Object returnValue = ;
        return object.get(fieldKey);
       /* switch (fieldType) {
            case STRING:
                returnValue = (((StringType)object.get(fieldKey)).get());
                break;

            case CHARACTER:
                returnValue = (((CharacterType)object.get(fieldKey)).get());
                break;

            case INTEGER:
                returnValue = (((IntegerType)object.get(fieldKey)).get());
                break;

            case LONG:
                returnValue = (((LongType)object.get(fieldKey)).get());
                break;

            case FLOAT:
                returnValue = (((FloatType)object.get(fieldKey)).get());
                break;

            case DOUBLE:
                returnValue = (((DoubleType)object.get(fieldKey)).get());
                break;

            case BYTE:
                returnValue = (((ByteType)object.get(fieldKey)).get());
                break;

            case BOOLEAN:
                returnValue = (((BooleanType)object.get(fieldKey)).get());
                break;
                
            case DATE:
                returnValue = (((DateType)object.get(fieldKey)).get());
                break;
        }*/
        
    }

    @Override
    public void setValue(ModelDTO object, ModelDTOValue value) {
    	object.put(fieldKey, value);
    	
    	//ModelDTOValue m = value;
        
       /* if (fieldType != null) {
            switch (fieldType) {
                case STRING:
                    m = new ModelDTOValue.StringType();
                    ((StringType) m).set((String) value);
                    break;
                case CHARACTER:
                    m = new ModelDTOValue.LongType();
                    if(value instanceof String){
                        ((CharacterType) m).setWithString((String) value);
                    }
                    else{
                        ((CharacterType) m).set((Character) value);
                    }
                    break;
                case INTEGER:
                    m = new ModelDTOValue.IntegerType();
                    if(value instanceof String){
                        ((IntegerType) m).setWithString((String) value);
                    }
                    else{
                        ((IntegerType) m).set((Integer) value);
                    }
                    break;
                case LONG:
                    m = new ModelDTOValue.LongType();
                    if(value instanceof String){
                        ((LongType) m).setWithString((String) value);
                    }
                    else{
                        ((LongType) m).set((Long) value);
                    }
                    break;
                case FLOAT:
                    m = new ModelDTOValue.FloatType();
                    if(value instanceof String){
                        ((FloatType) m).setWithString((String) value);
                    }
                    else{
                        ((FloatType) m).set((Float) value);
                    }
                    break;
                case DOUBLE:
                    m = new ModelDTOValue.DoubleType();
                    if(value instanceof String){
                        ((DoubleType) m).setWithString((String) value);
                    }
                    else{
                        ((DoubleType) m).set((Double) value);
                    }
                    break;
                case BYTE:
                    m = new ModelDTOValue.ByteType();
                    if(value instanceof String){
                        ((ByteType) m).setWithString((String) value);
                    }
                    else{
                        ((ByteType) m).set((Byte) value);
                    }
                    break;
                case BOOLEAN:
                    m = new ModelDTOValue.BooleanType();
                    if(value instanceof String){
                        ((BooleanType) m).setWithString((String) value);
                    }
                    else{
                        ((BooleanType) m).set((Boolean) value);
                    }
                    break;
                case DATE:
                    m = new ModelDTOValue.DateType();
                    if(value instanceof String){
                        ((DateType) m).setWithString((String) value);
                    }
                    else{
                        ((DateType) m).set((Date) value);
                    }
                    break;
            }
        }*/
        
        
    }
}
