package org.kuali.student.rules.internal.common.utils;

import java.util.List;
import java.util.Map;

import org.kuali.student.rules.factfinder.dto.FactParamDTO;
import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.factfinder.dto.FactParamDTO.FactParamDefTime;
import org.kuali.student.rules.internal.common.entity.YieldValueFunctionType;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FactUtil {
    /** SLF4J logging framework */
    final static Logger logger = LoggerFactory.getLogger(FactUtil.class);

	public final static String getFactParamDefinitionKey(YieldValueFunctionDTO yieldValueFunction) {
		if (yieldValueFunction == null) return null;
		List<FactStructureDTO> factStructureList = yieldValueFunction.getFactStructureList();
		FactStructureDTO factStructure = factStructureList.get(0);
		YieldValueFunctionType type = YieldValueFunctionType.valueOf(yieldValueFunction.getYieldValueFunctionType());

		switch(type) {
			case SUBSET:
			case INTERSECTION: {
				// Must only have one fact
				Map<String, FactParamDTO> factParamMap = factStructure.getCriteriaTypeInfo().getFactParamMap();
				logger.debug("DEFINITION: factParamMap="+factParamMap);
				// Assume only one definition key
				for(FactParamDTO factParam : factParamMap.values()) {
					if ( factParam.getDefTime() == FactParamDefTime.DEFINITION) {
						return factParam.getKey();
					}
				}
				break;
			}
			case AVERAGE:
			case SUM:
			case MIN:
			case MAX: {
				// Assume no definition
				return null;
			}
		}
		throw new RuntimeException("Definition Key: Yield value function type not found: Type="+type);
	}

	public final static String getFactParamExecutionKey(YieldValueFunctionDTO yieldValueFunction) {
		if (yieldValueFunction == null) return null;
		List<FactStructureDTO> factStructureList = yieldValueFunction.getFactStructureList();
		FactStructureDTO factStructure = factStructureList.get(0);
		YieldValueFunctionType type = YieldValueFunctionType.valueOf(yieldValueFunction.getYieldValueFunctionType());

		switch(type) {
			case SUBSET:
			case INTERSECTION: {
				// Must only have one fact
				Map<String, FactParamDTO> factParamMap = factStructure.getCriteriaTypeInfo().getFactParamMap();
				logger.debug("EXECUTION: factParamMap="+factParamMap);
				// Assume only one execution key
				for(FactParamDTO factParam : factParamMap.values()) {
					if ( factParam.getDefTime() == FactParamDefTime.EXECUTION) {
						return factParam.getKey();
					}
				}
			}
			case AVERAGE:
			case SUM:
			case MIN:
			case MAX: {
				// Must only have one fact
				Map<String, FactParamDTO> factParamMap = factStructure.getCriteriaTypeInfo().getFactParamMap();
				logger.debug("EXECUTION: factParamMap="+factParamMap);
				// Assume only one execution key
				for(FactParamDTO factParam : factParamMap.values()) {
					if ( factParam.getDefTime() == FactParamDefTime.EXECUTION) {
						return factParam.getKey();
					}
				}
				break;
			}
		}
		throw new RuntimeException("Execution Key: Yield value function type not found: Type="+type);
	}
	
	/**
	 * Calculate the fact id key.
	 * 
	 * @param propositionName Rule proposition name
	 * @param id Fact structure id
	 * @param index Fact structure list index
	 * @return
	 */
	/*public final static String getFactKey(String propositionName, String id, int index) {
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
	/*public final static List<String> getFactKey(YieldValueFunctionDTO yieldValueFunction, String propositionName) {
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
	}*/

}
