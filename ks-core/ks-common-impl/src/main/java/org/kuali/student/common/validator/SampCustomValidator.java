package org.kuali.student.common.validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.kuali.student.common.util.MessageUtils;
import org.kuali.student.core.dictionary.dto.Constraint;
import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public class SampCustomValidator implements Validator {

	private ObjectStructureDefinition objStructure;

	@Override
	public List<ValidationResultInfo> validateObject(Object o,
			ObjectStructureDefinition objStructure) {
		return null;
	}

	public ObjectStructureDefinition getObjStructure() {
		return objStructure;
	}

	public void setObjStructure(ObjectStructureDefinition objStructure) {
		this.objStructure = objStructure;
	}

	@Override
	public List<ValidationResultInfo> validateObject(FieldDefinition field,
			Object o, ObjectStructureDefinition objStructure,Stack<String> elementStack) {
		 List<ValidationResultInfo> results = new ArrayList<ValidationResultInfo>();
		ConstraintDataProvider dataProvider = new BeanConstraintDataProvider();
		dataProvider.initialize(o);
		String xPath= getElementXpath(elementStack) + field.getName() ;
		Object value = dataProvider.getValue(field.getName());
		if(value==null){

            ValidationResultInfo valRes = new ValidationResultInfo(xPath);
            valRes.setError(MessageUtils.interpolate("Validation Error Sample", toMap(field)));
            results.add(valRes);
		}
		
		
		return results;
	}
	
    private String getElementXpath(Stack<String> elementStack) {
        StringBuilder xPath = new StringBuilder();
        xPath.append("/");
        Iterator<String> itr = elementStack.iterator();
        while (itr.hasNext()) {
            xPath.append(itr.next() + "/");
        }

        return xPath.toString();
    }

    private Map<String, Object> toMap(Constraint c) {
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("minOccurs", c.getMinOccurs());
        result.put("maxOccurs", c.getMaxOccurs());
        result.put("minLength", c.getMinLength());
        result.put("maxLength", c.getMaxLength());
        result.put("minValue", c.getExclusiveMin());
        result.put("maxValue", c.getInclusiveMax());
        // result.put("dataType", c.getDataType());

        return result;
    }
}
