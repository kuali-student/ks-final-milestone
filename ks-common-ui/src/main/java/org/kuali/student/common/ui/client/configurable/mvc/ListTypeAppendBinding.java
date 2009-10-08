package org.kuali.student.common.ui.client.configurable.mvc;

import java.util.List;

import org.kuali.student.common.ui.client.mvc.dto.ModelDTO;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue;
import org.kuali.student.common.ui.client.mvc.dto.ModelDTOValue.ListType;

public class ListTypeAppendBinding implements PropertyBinding<ModelDTO>{
    private String fieldKey;
    private int totalListsToCombine = 0;
    private int appendCount = 0;
    
    public ListTypeAppendBinding(String fieldKey, int totalListsToCombine){
        this.fieldKey = fieldKey;
        this.totalListsToCombine = totalListsToCombine;
    }

	@Override
	public ModelDTOValue getValue(ModelDTO object) {
		return object.get(fieldKey);
	}
	
	@Override
	public void setValue(ModelDTO object, ModelDTOValue value) {
		if(value instanceof ListType){
			ListType original = (ListType) object.get(fieldKey);
			if(original != null && value != null){
				
				if(appendCount == 0){
					original = (ListType) value;
				}
				else if (appendCount < totalListsToCombine){
					List<ModelDTOValue> oList = original.get();
					oList.addAll(((ListType) value).get());
					original.set(oList);
				}
			}
			else if(original == null){
				if(appendCount == 0){
					original = (ListType) value;
				}
			}
			appendCount++;
			if(appendCount == totalListsToCombine){
				appendCount = 0;
			}
			
			object.put(fieldKey, original);
			
		}
		
	}
}