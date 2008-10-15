package org.kuali.student.rules.internal.common.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class FactUtil {

	/**
	 * Gets the definition variable value by <code>variableKey</code> from the
	 * <code>yieldValueFunction</code>.
	 * 
	 * @param yieldValueFunction Yield value function to get the list of fact structures from
	 * @param variableKey key to lookup in the definition variable map
	 * @return Definition variable value
	 */
	/*public final static String getDefinitionVariableValue(YieldValueFunctionDTO yieldValueFunction, String variableKey) {
		if (yieldValueFunction == null) return null;
		List<FactStructureDTO> list = yieldValueFunction.getFactStructureList();
		for(int i=0; i<list.size(); i++) {
			FactStructureDTO factStructure = list.get(i);
			String factStructureId = factStructure.getFactStructureId();
			if (factStructure != null && factStructure.getFactStructureId().equals(factStructureId)) {
				return (factStructure.getDefinitionVariableList() == null ? null : factStructure.getDefinitionVariableList().get(variableKey));
			}
		}
		return null;
	}*/

	public final static String getDefinitionVariableValue(YieldValueFunctionDTO yieldValueFunction) {
		if (yieldValueFunction == null) return null;
		List<FactStructureDTO> list = yieldValueFunction.getFactStructureList();
		YieldValueFunctionType type = YieldValueFunctionType.valueOf(yieldValueFunction.getYieldValueFunctionType());

		switch(type) {
			case SUBSET:
			case INTERSECTION: {
				// Assume only one fact
				FactStructureDTO factStructure = list.get(0);
				Map<String,String> map = factStructure.getDefinitionVariableList();
				// Assume only one definition
				return map.values().iterator().next();
			}
			case AVERAGE:
			case SUM:
			case MIN:
			case MAX: {
				// Assume no definition
				return null;
			}
		}
		throw new RuntimeException("Definition Variable: Yield value function type not found: Type="+type);
	}
	
	/**
	 * Calculate the fact id key.
	 * 
	 * @param propositionName Rule proposition name
	 * @param id Fact structure id
	 * @param index Fact structure list index
	 * @return
	 */
	public final static String getFactKey(String propositionName, String id, int index) {
		StringBuilder sb = new StringBuilder(5);
		sb.append(propositionName);
		sb.append(".");
		sb.append(id);
		sb.append(".");
		sb.append(index);
		return sb.toString();
	}

	/**
	 * Gets the calculated fact id depending on the yield value function.
	 * Fact id is of the form: PropositionName.FactStructureId.index
	 * 
	 * @param yieldValueFunction Yield value function to get the list of fact structures from
	 * @param propositionName Rule element proposition name
	 * @return 
	 */
	public final static List<String> getFactKey(YieldValueFunctionDTO yieldValueFunction, String propositionName) {
		if (yieldValueFunction == null) return null;
		List<FactStructureDTO> list = yieldValueFunction.getFactStructureList();
		YieldValueFunctionType type = YieldValueFunctionType.valueOf(yieldValueFunction.getYieldValueFunctionType());
		List<String> keyList = new ArrayList<String>();
		
		switch(type) {
			case AVERAGE: {
				// Assume only one fact
				FactStructureDTO factStructure = list.get(0);
				keyList.add(getFactKey(propositionName, factStructure.getFactStructureId(), 0));
				break;
			}
			case SUBSET:
			case INTERSECTION: {
				// Assume only one fact
				FactStructureDTO factStructure = list.get(0);
				keyList.add(getFactKey(propositionName, factStructure.getFactStructureId(), 0));
				break;
			}
			case SUM: {
				// Assume only one fact
				FactStructureDTO factStructure = list.get(0);
				keyList.add(getFactKey(propositionName, factStructure.getFactStructureId(), 0));
				break;
			}
			case MIN:
			case MAX: {
				// Assume only one fact
				FactStructureDTO factStructure = list.get(0);
				keyList.add(getFactKey(propositionName, factStructure.getFactStructureId(), 0));
				break;
			}
			default: {
				throw new RuntimeException("Fact Id: Yield value function type not found: Type="+type);
			}
		}
		return keyList;
	}

}
