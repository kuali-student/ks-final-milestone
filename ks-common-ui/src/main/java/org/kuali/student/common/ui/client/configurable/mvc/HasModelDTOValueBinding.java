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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOAdapter;
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


@Deprecated
public class HasModelDTOValueBinding implements PropertyBinding<HasModelDTOValue>{

	public static HasModelDTOValueBinding INSTANCE = new HasModelDTOValueBinding();
	
	private HasModelDTOValueBinding(){}
	
	@Override
	public ModelDTOValue getValue(HasModelDTOValue object) {
		return object.getValue();
	}

	@Override
	public void setValue(HasModelDTOValue object, ModelDTOValue value) {
		object.setValue(deepCopy(value));
	}
	
	public static ModelDTOValue deepCopy(ModelDTOValue value){
		ModelDTOValue copy = null;
		if(value instanceof ListType){
			List<ModelDTOValue> list = ((ListType) value).get();
			List<ModelDTOValue> newList = new ArrayList<ModelDTOValue>();
			if (null != list) {
				for(ModelDTOValue v : list){
					newList.add(deepCopy(v));
				}
			}
			copy = new ListType();
			((ListType) copy).set(newList);
		}
		else if(value instanceof MapType){
			Map<String, ModelDTOValue> map = ((MapType)value).get();
			Map<String, ModelDTOValue> newMap = new HashMap<String, ModelDTOValue>();
			if (null != map) {
				for(String key: map.keySet()){
					newMap.put(key, deepCopy(map.get(key)));
				}
			}
			copy = new MapType();
			((MapType) copy).set(newMap);
		}
		else if(value instanceof ModelDTOType){
			ModelDTO model = ((ModelDTOType) value).get();
			assert null != model;
			ModelDTO newModel = new ModelDTO(model.getClassName());
			if(model.getAdapter() != null){
				newModel.setAdapter(new ModelDTOAdapter(newModel, model.getAdapter().getObjectKeyClassNameMap(), model.getAdapter().getObjectKey()));
			}
			newModel.setKey(model.getKey());
			for(String key: model.keySet()){
				newModel.put(key, deepCopy(model.get(key)),true);
			}
			copy = new ModelDTOType();
			((ModelDTOType) copy).set(newModel);
		}
		else if(value instanceof StringType){
            copy = new StringType();
            ((StringType) copy).set(((StringType) value).get());
        }
        else if(value instanceof CharacterType){
            copy = new CharacterType();
            ((CharacterType) copy).set(((CharacterType) value).get());
        }
        else if(value instanceof IntegerType){
            copy = new IntegerType();
            ((IntegerType) copy).set(((IntegerType) value).get());
        }
        else if(value instanceof LongType){
            copy = new LongType();
            ((LongType) copy).set(((LongType) value).get());
        }
        else if(value instanceof FloatType){
            copy = new FloatType();
            ((FloatType) copy).set(((FloatType) value).get());
        }
        else if(value instanceof DoubleType){
            copy = new DoubleType();
            ((DoubleType) copy).set(((DoubleType) value).get());
        }
        else if(value instanceof ByteType){
            copy = new ByteType();
            ((ByteType) copy).set(((ByteType) value).get());
        }
        else if(value instanceof BooleanType){
            copy = new BooleanType();
            ((BooleanType) copy).set(((BooleanType) value).get());
        }
        else if(value instanceof DateType){
            copy = new DateType();
            Date dateVal = (Date) ((DateType) value).get();
            if (null != dateVal) {
	            ((DateType) copy).set((Date) dateVal.clone());
            }
        }
		
		return copy;
	}

}
