package org.kuali.student.core.dictionary.poc.dto;

import java.util.List;

import org.kuali.student.core.validation.dto.ValidationResultInfo;

public interface CustomValidator {
	public List<ValidationResultInfo> validateField(FieldDefinition field,
			ObjectStructureDefinition objStruct, Object value);
}
