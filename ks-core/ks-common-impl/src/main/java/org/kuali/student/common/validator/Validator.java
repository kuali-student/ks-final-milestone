package org.kuali.student.common.validator;

import java.util.List;
import java.util.Stack;

import org.kuali.student.core.dictionary.dto.FieldDefinition;
import org.kuali.student.core.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public interface Validator {

	public List<ValidationResultInfo> validateObject(Object o, ObjectStructureDefinition objStructure);
	public List<ValidationResultInfo> validateObject(FieldDefinition field,Object o, ObjectStructureDefinition objStructure,Stack<String> elementStack);
}
