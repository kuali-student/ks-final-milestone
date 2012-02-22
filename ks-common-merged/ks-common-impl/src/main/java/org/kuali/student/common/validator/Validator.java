package org.kuali.student.common.validator;

import java.util.List;
import java.util.Stack;

import org.kuali.student.common.olddictionary.dto.FieldDefinition;
import org.kuali.student.common.olddictionary.dto.ObjectStructureDefinition;
import org.kuali.student.common.oldvalidation.dto.ValidationResultInfo;
import org.kuali.student.common.dto.ContextInfo;

public interface Validator {

	public List<ValidationResultInfo> validateObject(Object o, ObjectStructureDefinition objStructure, ContextInfo context);
	public List<ValidationResultInfo> validateObject(FieldDefinition field,Object o, ObjectStructureDefinition objStructure,Stack<String> elementStack, ContextInfo context);
}
