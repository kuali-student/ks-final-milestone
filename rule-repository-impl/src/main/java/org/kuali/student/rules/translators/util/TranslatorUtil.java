package org.kuali.student.rules.translators.util;

import java.util.List;

import org.kuali.student.rules.rulemanagement.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class TranslatorUtil {

	private TranslatorUtil() {}

	public static TranslatorUtil getInstance() {
		return new TranslatorUtil();
	}

	public String getDefinitionVariableValue(YieldValueFunctionDTO yieldValueFunction, String factStructureId, String variableKey) {
		if (yieldValueFunction == null) return null;
		List<FactStructureDTO> list = yieldValueFunction.getFactStructureList();
		for(FactStructureDTO factStructure : list) {
			if (factStructure != null && factStructure.getFactStructureId().equals(factStructureId)) {
				return (factStructure.getDefinitionVariableList() == null ? null : factStructure.getDefinitionVariableList().get(variableKey));
			}
		}
		return null;
	}

	public String getExecutionVariableValue(YieldValueFunctionDTO yieldValueFunction, String factStructureId, String variableKey) {
		if (yieldValueFunction == null) return null;
		List<FactStructureDTO> list = yieldValueFunction.getFactStructureList();
		for(FactStructureDTO factStructure : list) {
			if (factStructure != null && factStructure.getFactStructureId().equals(factStructureId)) {
				return (factStructure.getExecutionVariableList() == null ? null : factStructure.getExecutionVariableList().get(variableKey));
			}
		}
		return null;
	}

}
