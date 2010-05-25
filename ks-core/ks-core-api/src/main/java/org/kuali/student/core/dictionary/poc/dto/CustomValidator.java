package org.kuali.student.core.dictionary.poc.dto;

import java.util.List;
import java.util.Stack;

import org.kuali.student.common.validator.poc.ConstraintDataProvider;
import org.kuali.student.core.validation.dto.ValidationResultInfo;

public interface CustomValidator {
	public List<ValidationResultInfo> validateField(FieldDefinition field,
			ObjectStructureDefinition objStruct,
			ConstraintDataProvider dataProvider, Stack<String> elementStack);
}
