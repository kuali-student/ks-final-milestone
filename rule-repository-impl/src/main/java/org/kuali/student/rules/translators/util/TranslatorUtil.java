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
		List<FactStructureDTO> list = yieldValueFunction.getFactStructureList();
		for(FactStructureDTO factStructure : list) {
			if ( factStructure.getFactStructureId().equals(factStructureId)) {
				return factStructure.getDefinitionVariableList().get(variableKey);
			}
		}
		return null;
	}

}
