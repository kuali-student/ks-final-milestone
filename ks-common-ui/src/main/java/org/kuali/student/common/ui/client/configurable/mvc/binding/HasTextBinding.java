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

import org.kuali.student.common.assembly.client.Model;
import org.kuali.student.common.assembly.client.QueryPath;
import org.kuali.student.common.assembly.client.Data.DataType;
import org.kuali.student.common.ui.client.validator.ClientDateParser;

import com.google.gwt.user.client.ui.HasText;

public class HasTextBinding implements ModelWidgetBinding<HasText>{
    public static HasTextBinding INSTANCE = new HasTextBinding();    
    
    private ClientDateParser dateParser = new ClientDateParser();
    
    private HasTextBinding(){}
    
    @Override
    public void setModelValue(HasText object, Model model, String path) {
        QueryPath qPath = QueryPath.parse(path);        
        DataType type = model.getType(qPath);
        String value = object.getText().trim();

        switch (type) {
            case STRING:
                model.set(qPath, value);
                break;
            case CHARACTER:
                if(value.length() == 1){
                    Character character = new Character(value.charAt(0));
                    model.set(qPath, character);
                }
                else{
                    throw new UnsupportedOperationException("Characters can only be set with Strings containing 1 character");
                }
                break;
            case INTEGER:
                if (value != null && value.length() > 0){
                    model.set(qPath, Integer.parseInt(value));
                }
                break;
            case LONG:
                model.set(qPath, Long.parseLong(value));
                break;
            case FLOAT:
                model.set(qPath, Float.parseFloat(value));
                break;
            case DOUBLE:
                model.set(qPath, Double.parseDouble(value));
                break;
            case BYTE:
                model.set(qPath, Byte.parseByte(value));
                break;
            case BOOLEAN:
                if(value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false")){
                    model.set(qPath, Boolean.parseBoolean(value));
                }
                else{
                    throw new UnsupportedOperationException("BooleanTypes can only be set with true or false");
                }
                break;
            case DATE:
                model.set(qPath, dateParser.parseDate(value));
                break;
        }
    }
    
    

    @Override
    public void setWidgetValue(HasText object, Model model, String path ) {
        QueryPath qPath = QueryPath.parse(path);
        String value = model.get(qPath);
        
        if (value != null && object != null) {
            object.setText(value);
        }
        else if(value == null && object != null){
            object.setText("");
        }
    }

}
