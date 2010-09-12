package org.kuali.student.rules.ruleexecution.util;

import java.util.List;

import org.kuali.student.rules.factfinder.dto.FactStructureDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.LeftHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RightHandSideDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;
import org.kuali.student.rules.rulemanagement.dto.YieldValueFunctionDTO;

public class RuleManagementDtoFactory {

	public RuleManagementDtoFactory() {}
	
	public static RuleManagementDtoFactory getInstance() {
		return new RuleManagementDtoFactory();
	}
	
	public YieldValueFunctionDTO createYieldValueFunctionDTO(List<FactStructureDTO> factStructureList,
															 String yieldValueFunctionType) {
		YieldValueFunctionDTO dto = new YieldValueFunctionDTO();
		dto.setFactStructureList(factStructureList);
		dto.setYieldValueFunctionType(yieldValueFunctionType);
		return dto;
	}

	public LeftHandSideDTO createLeftHandSideDTO(YieldValueFunctionDTO yieldValueFunction) {
		LeftHandSideDTO dto = new LeftHandSideDTO();
		dto.setYieldValueFunction(yieldValueFunction);
		return dto;
	}

	public RightHandSideDTO createRightHandSideDTO(String expectedValue) {
		RightHandSideDTO dto = new RightHandSideDTO();
		dto.setExpectedValue(expectedValue);
		return dto;
	}

	public RulePropositionDTO createRulePropositionDTO(String name,
													   String comparisonDataType,
													   String comparisonOperatorType,
													   LeftHandSideDTO leftHandSide,
													   RightHandSideDTO rightHandSide) {
		RulePropositionDTO dto = new RulePropositionDTO();
		dto.setComparisonDataType(comparisonDataType);
		dto.setComparisonOperatorType(comparisonOperatorType);
		dto.setDescription("Rule proposition DTO");
		dto.setFailureMessage("Rule proposition failure");
		dto.setLeftHandSide(leftHandSide);
		dto.setName(name);
		dto.setRightHandSide(rightHandSide);
		return dto;
	}

	public RuleElementDTO createRuleElementDTO(String name, 
											   String operation, 
											   Integer ordinalPosition,
											   RulePropositionDTO ruleProposition) {
		RuleElementDTO dto = new RuleElementDTO();
		dto.setDescription("Rule element DTO");
		dto.setName(name);
		dto.setOperation(operation);
		dto.setOrdinalPosition(ordinalPosition);
		dto.setRuleProposition(ruleProposition);
		return dto;
	}

	public BusinessRuleInfoDTO createBusinessRuleInfoDTO(String name, 
														 String businessRuleId,
														 String successMessage,
														 String failureMessage,
														 String businessRuleTypeKey,
														 List<RuleElementDTO> ruleElementList,
														 String anchorTypeKey,
														 String anchorValue) {
		BusinessRuleInfoDTO dto = new BusinessRuleInfoDTO();
		dto.setName(name);
		dto.setDescription("Business rule info DTO");
		dto.setSuccessMessage(successMessage);
		dto.setFailureMessage(failureMessage);
		dto.setBusinessRuleId(businessRuleId);
		//dto.setbusinessRuleInfo = businessRuleInfo;
		dto.setBusinessRuleTypeKey(businessRuleTypeKey);
		dto.setAnchorTypeKey(anchorTypeKey);
		dto.setAnchorValue(anchorValue);
		dto.setRuleElementList(ruleElementList);
		return dto;
	}
}
