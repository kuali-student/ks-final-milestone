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

package org.kuali.student.common.ui.client.validator;

import java.util.List;

import org.kuali.student.common.ui.client.configurable.mvc.FieldDescriptor;
import org.kuali.student.common.ui.client.configurable.mvc.Section;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.BooleanType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ByteType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.CharacterType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.DateType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.DoubleType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.FloatType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.IntegerType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ListType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.LongType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.MapType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ModelDTOType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;
import org.kuali.student.common.validator.ConstraintDataProvider;

public class SectionConstraintDataProvider implements ConstraintDataProvider  {
    private ConstraintDataProvider model = null;
    private Section section = null;
    private String className;
    // TODO add constructors/setters for passing these in
    public SectionConstraintDataProvider(Section s,ConstraintDataProvider model){
        this.model = model;
        this.section = s;
    }
    public String getPath(){
        return "";
    }
 // question
    public String getObjectId() {
        return model.getObjectId();
    }

    public Object getValue(String fieldKey) {
        System.out.println("\n\n\n\nFieldKey:"+fieldKey);
        Object result = getValueFromSectionIfExists(fieldKey);
        System.out.println("Result:"+result);
        if (result == null) {
            result = model.getValue(fieldKey);
        }
        System.out.println("Result from dto:"+result);
        return result;
    }

    public Object getValueFromSectionIfExists(String fieldKey) {
        List<FieldDescriptor> fdList = section.getFields();
        for(FieldDescriptor fd: fdList){
            System.out.println("fd.getFieldKey():"+fd.getFieldKey());
            System.out.println("className:"+className);
            String fullPath = fd.getFieldKey().toLowerCase();
            //if(fd.getFieldKey() != null && fd.getFieldKey().equals(fieldKey)){
            if(fd.getFieldKey() != null && fd.getFieldKey().indexOf(fieldKey) != -1
            &&  fullPath.indexOf(className.toLowerCase())!= -1      
            ){
                System.out.println("getting value for:"+fieldKey);
                ModelDTOValue modelDTOValue =  fd.getWidgetBinding().getValue(fd.getFieldWidget());
                System.out.println("modelDTOValue:"+modelDTOValue.getType());
                if (modelDTOValue == null) {
                    return null;
                }
                switch (modelDTOValue.getType()) {
                    case STRING:
                        return ((StringType) modelDTOValue).get();
                    case CHARACTER:
                        return ((CharacterType) modelDTOValue).get();
                    case INTEGER:
                        return ((IntegerType) modelDTOValue).get();
                    case LONG:
                        return ((LongType) modelDTOValue).get();
                    case FLOAT:
                        return ((FloatType) modelDTOValue).get();
                    case DOUBLE:
                        return ((DoubleType) modelDTOValue).get();
                    case BYTE:
                        return ((ByteType) modelDTOValue).get();
                    case BOOLEAN:
                        return ((BooleanType) modelDTOValue).get();
                    case DATE:
                        return ((DateType) modelDTOValue).get();
                    case LIST:
                        return ((ListType) modelDTOValue). getBaseTypeList();
                    case MAP:
                        return ((MapType) modelDTOValue).get();
                    case MODELDTO:
                       
                        return ((ModelDTOType) modelDTOValue).get();
                }
              
            }
        }
        return null;
    }

    public Boolean hasField(String fieldKey) {
        if(model.hasField(fieldKey)){
            return true;
        }
        List<FieldDescriptor> fdList = section.getFields();
        for(FieldDescriptor fd: fdList){
            if(fd.getFieldKey() != null && fd.getFieldKey().equals(fieldKey)){
               return true;
            }
        }
        
        return false;
    }
    public void initialize(Object o) {
        if (o instanceof ModelDTO) {
           // System.out.println("init:"+((ModelDTO) o).getClassName());
            className = ((ModelDTO) o).getClassName();
            int index = className.lastIndexOf(".");
            if(index == -1 ){
                index = 0;
            }
            className = className.substring(index+1, className.length());
        }
        model.initialize(o);
    }
}
