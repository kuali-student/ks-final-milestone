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

public class ModelDTOConstraintDataProvider implements ConstraintDataProvider {
    ModelDTO modelDTO = null;

    @Override
    public String getObjectId() {

        if (modelDTO == null) {
            return null;
        }
        ModelDTOValue modelDTOValue = modelDTO.get("id");
        if (modelDTOValue == null) {
            return null;
        }
        return ((StringType) modelDTOValue).get();
    }
    public String getPath(){
        if (modelDTO == null) {
            return "";
        }
        if(modelDTO.getKey() != null){
            return modelDTO.getKey();
        }
        return "";
    }

    @Override
    public Object getValue(String fieldKey) {

        if (modelDTO == null) {
            return null;
        }

        ModelDTOValue modelDTOValue = modelDTO.get(fieldKey);
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
        return null;
    }

    @Override
    public Boolean hasField(String fieldKey) {
        if (modelDTO == null) {
            return false;
        }
        return modelDTO.get(fieldKey) != null;
    }

    @Override
    public void initialize(Object o) {
        if(o == null){
            modelDTO = null;
        }else if (o instanceof ModelDTO) {
            modelDTO = (ModelDTO) o;
        } else {
            throw new IllegalArgumentException("The object must be an instance of ModelDTO.");
        }
    }
}
