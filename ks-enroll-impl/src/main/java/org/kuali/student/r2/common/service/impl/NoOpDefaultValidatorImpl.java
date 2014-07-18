package org.kuali.student.r2.common.service.impl;

import org.kuali.student.r1.common.dictionary.dto.FieldDefinition;
import org.kuali.student.r1.common.dictionary.dto.ObjectStructureDefinition;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.validator.DefaultValidatorImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class NoOpDefaultValidatorImpl extends DefaultValidatorImpl {
	 @Override
	 public List<ValidationResultInfo> validateObject (Object o, ObjectStructureDefinition objStructure, ContextInfo contextInfo) {
	    return new ArrayList<ValidationResultInfo>();
	 }

	 @Override
	 public List<ValidationResultInfo> validateObject (FieldDefinition field, Object o,
	                                                   ObjectStructureDefinition objStructure,
	                                                   Stack<String> elementStack,
                                                       ContextInfo contextInfo) {
	    return new ArrayList<ValidationResultInfo>();
	 }
}
