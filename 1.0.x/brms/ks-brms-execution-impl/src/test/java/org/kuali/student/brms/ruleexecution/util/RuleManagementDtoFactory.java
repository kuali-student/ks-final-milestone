/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.brms.ruleexecution.util;

import java.util.List;

import org.kuali.student.brms.factfinder.dto.FactStructureInfo;
import org.kuali.student.brms.rulemanagement.dto.BusinessRuleInfo;
import org.kuali.student.brms.rulemanagement.dto.LeftHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RightHandSideInfo;
import org.kuali.student.brms.rulemanagement.dto.RuleElementInfo;
import org.kuali.student.brms.rulemanagement.dto.RulePropositionInfo;
import org.kuali.student.brms.rulemanagement.dto.YieldValueFunctionInfo;

public class RuleManagementDtoFactory {

	public RuleManagementDtoFactory() {}
	
	public static RuleManagementDtoFactory getInstance() {
		return new RuleManagementDtoFactory();
	}
	
	public YieldValueFunctionInfo createYieldValueFunctionDTO(List<FactStructureInfo> factStructureList,
															 String yieldValueFunctionType) {
		YieldValueFunctionInfo dto = new YieldValueFunctionInfo();
		dto.setFactStructureList(factStructureList);
		dto.setYieldValueFunctionType(yieldValueFunctionType);
		return dto;
	}

	public LeftHandSideInfo createLeftHandSideDTO(YieldValueFunctionInfo yieldValueFunction) {
		LeftHandSideInfo dto = new LeftHandSideInfo();
		dto.setYieldValueFunction(yieldValueFunction);
		return dto;
	}

	public RightHandSideInfo createRightHandSideDTO(String expectedValue) {
		RightHandSideInfo dto = new RightHandSideInfo();
		dto.setExpectedValue(expectedValue);
		return dto;
	}

	public RulePropositionInfo createRulePropositionDTO(String name,
													   String comparisonDataType,
													   String comparisonOperatorType,
													   LeftHandSideInfo leftHandSide,
													   RightHandSideInfo rightHandSide) {
		RulePropositionInfo dto = new RulePropositionInfo();
		dto.setComparisonDataTypeKey(comparisonDataType);
		dto.setComparisonOperatorTypeKey(comparisonOperatorType);
		dto.setDesc("Rule proposition DTO");
		dto.setFailureMessage("Rule proposition failure");
		dto.setLeftHandSide(leftHandSide);
		dto.setName(name);
		dto.setRightHandSide(rightHandSide);
		return dto;
	}

	public RuleElementInfo createRuleElementDTO(String name, 
											   String operation, 
											   Integer ordinalPosition,
											   RulePropositionInfo ruleProposition) {
		RuleElementInfo dto = new RuleElementInfo();
		dto.setDesc("Rule element DTO");
		dto.setName(name);
		dto.setBusinessRuleElemnetTypeKey(operation);
		dto.setOrdinalPosition(ordinalPosition);
		dto.setBusinessRuleProposition(ruleProposition);
		return dto;
	}

	public BusinessRuleInfo createBusinessRuleInfoDTO(String name, 
														 String businessRuleId,
														 String successMessage,
														 String failureMessage,
														 String businessRuleTypeKey,
														 List<RuleElementInfo> ruleElementList,
														 String anchorTypeKey,
														 String anchorValue) {
		BusinessRuleInfo dto = new BusinessRuleInfo();
		dto.setName(name);
		dto.setDesc("Business rule info DTO");
		dto.setSuccessMessage(successMessage);
		dto.setFailureMessage(failureMessage);
		dto.setId(businessRuleId);
		//dto.setbusinessRuleInfo = businessRuleInfo;
		dto.setType(businessRuleTypeKey);
		dto.setAnchorTypeKey(anchorTypeKey);
		dto.setAnchor(anchorValue);
		dto.setBusinessRuleElementList(ruleElementList);
		return dto;
	}
}
