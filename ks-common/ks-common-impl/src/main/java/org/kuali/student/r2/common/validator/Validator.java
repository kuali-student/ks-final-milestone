package org.kuali.student.r2.common.validator;

import java.util.List;
import java.util.Stack;

import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

//This interface is a special case, this class/equivelent doesn't exist in R2 
//packages and is a common and has methods used in both R1 and R2 packages, 
//this class was duplicated to R2 and modified to work with R2 services
//BaseAbstractValidator, BaseAbstractValidator, Validator, ValidatorFactory

public interface Validator {

	public List<ValidationResultInfo> validateObject(Object o, ObjectStructureDefinition objStructure, ContextInfo contextInfo);
	public List<ValidationResultInfo> validateObject(FieldDefinition field,Object o, ObjectStructureDefinition objStructure,Stack<String> elementStack, ContextInfo contextInfo);
}
