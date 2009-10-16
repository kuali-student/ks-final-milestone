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
package org.kuali.student.common.ui.client.mvc.dto;

import java.util.Date;

import org.kuali.student.common.ui.client.configurable.mvc.HasModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.BooleanType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ByteType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.CharacterType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.DateType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.DoubleType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.FloatType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.IntegerType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.LongType;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.StringType;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

/**
 * This can be used to copy values between the ModelDTOValue widget and screen UI
 * 
 * @author Kuali Student Team
 */
public class ModelDTOValueBinder {
    public static void copyValueToModelDTO(Object v, ModelDTOValue m) {
        if (v != null) {
            switch (m.getType()) {
                case STRING:
                    ((StringType) m).set((String) v);
                    break;

                case CHARACTER:
                    ((CharacterType) m).set((Character) v);
                    break;

                case INTEGER:
                    ((IntegerType) m).set((Integer) v);
                    break;

                case LONG:
                    ((LongType) m).set((Long) v);
                    break;

                case FLOAT:
                    ((FloatType) m).set((Float) v);
                    break;

                case DOUBLE:
                    ((DoubleType) m).set((Double) v);
                    break;

                case BYTE:
                    ((ByteType) m).set((Byte) v);
                    break;

                case BOOLEAN:
                    ((BooleanType) m).set((Boolean) v);
                    break;

                case DATE:
                    ((DateType) m).set((Date) v);
                    break;
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static void copyValueFromModelDTO(ModelDTOValue m, Widget w) {
        if (m != null) {
            switch (m.getType()) {
                case STRING:
                    ((HasValue)w).setValue(((StringType) m).get());
                    break;

                case CHARACTER:
                    ((HasValue)w).setValue(((CharacterType) m).get());
                    break;

                case INTEGER:
                    ((HasValue)w).setValue(((IntegerType) m).get());
                    break;

                case LONG:
                    ((HasValue)w).setValue(((LongType) m).get());
                    break;

                case FLOAT:
                    ((HasValue)w).setValue(((FloatType) m).get());
                    break;

                case DOUBLE:
                    ((HasValue)w).setValue(((DoubleType) m).get());
                    break;

                case BYTE:
                    ((HasValue)w).setValue(((ByteType) m).get());
                    break;

                case BOOLEAN:
                    ((HasValue)w).setValue(((BooleanType) m).get());
                    break;
                    
                case DATE:
                    ((HasValue)w).setValue(((DateType) m).get());
                    break;
                    
                default:
                    ((HasModelDTOValue)w).setValue(m);
            }
        }
    }
    
    @SuppressWarnings("unchecked")
    public static ModelDTOValue createModelDTOInstance(Object value, ModelDTOValue.Type modelDTOType){
        ModelDTOValue m = null;
        if (modelDTOType != null) {
            switch (modelDTOType) {
                case STRING:
                    m = new ModelDTOValue.StringType();
                    break;

                case CHARACTER:
                    m = new ModelDTOValue.CharacterType();
                    break;

                case INTEGER:
                    m = new ModelDTOValue.IntegerType();
                    break;

                case LONG:
                    m = new ModelDTOValue.LongType();
                    break;

                case FLOAT:
                    m = new ModelDTOValue.FloatType();
                    break;

                case DOUBLE:
                    m = new ModelDTOValue.DoubleType();
                    break;

                case BYTE:
                    m = new ModelDTOValue.ByteType();
                    break;

                case BOOLEAN:
                    m = new ModelDTOValue.BooleanType();
                    break;
            }
        }
        
        copyValueToModelDTO(value, m);
        
        return m;
        
    }

}
