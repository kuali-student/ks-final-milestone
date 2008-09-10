/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.repository.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.rules.internal.common.entity.BusinessRule;
import org.kuali.student.rules.internal.common.entity.BusinessRuleContainer;
import org.kuali.student.rules.internal.common.entity.BusinessRuleInfo;
import org.kuali.student.rules.internal.common.entity.RuleElement;
import org.kuali.student.rules.internal.common.entity.RuleProposition;
import org.kuali.student.rules.repository.drools.rule.CategoryFactory;
import org.kuali.student.rules.repository.drools.rule.RuleFactory;
import org.kuali.student.rules.repository.drools.rule.RuleSetFactory;
import org.kuali.student.rules.repository.dto.CategoryDTO;
import org.kuali.student.rules.repository.dto.RuleDTO;
import org.kuali.student.rules.repository.dto.RuleSetDTO;
import org.kuali.student.rules.repository.rule.Category;
import org.kuali.student.rules.repository.rule.Rule;
import org.kuali.student.rules.repository.rule.RuleSet;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleContainerDTO;
import org.kuali.student.rules.rulemanagement.dto.BusinessRuleInfoDTO;
import org.kuali.student.rules.rulemanagement.dto.RuleElementDTO;
import org.kuali.student.rules.rulemanagement.dto.RulePropositionDTO;

public class RuleAdapter {

	private final static CategoryFactory categoryFactory = CategoryFactory.getInstance();
	private final static RuleFactory ruleFactory = RuleFactory.getInstance();
	private final static RuleSetFactory ruleSetFactory = RuleSetFactory.getInstance();
	
	private RuleAdapter() {}
	
	public static RuleAdapter getInstance() {
		return new RuleAdapter();
	}

	public Category getCategory(CategoryDTO categoryDTO) {
		return categoryFactory.createDroolsCategory(categoryDTO.getName(), categoryDTO.getPath());
	}
	
	public CategoryDTO getCategoryDTO(Category category) {
		return new CategoryDTO(category.getName(),category.getPath());
	}
	
	public Rule getRule(RuleDTO ruleDTO) {
		Rule rule = ruleFactory.createDroolsRule(
				ruleDTO.getName(), 
				ruleDTO.getDescription(), 
				ruleDTO.getContent()); 
		rule.setCategoryNames(ruleDTO.getCategoryNames());
		rule.setCheckinComment(ruleDTO.getCheckinComment());
		rule.setEffectiveDate(ruleDTO.getEffectiveDate());
		rule.setExpiryDate(ruleDTO.getExpiryDate());
		rule.setStatus(ruleDTO.getStatus());
		return rule;
	}

	public RuleDTO getRuleDTO(Rule rule) {
		RuleDTO dto = new RuleDTO(
				rule.getUUID(),
				rule.getName(),
				rule.getVersionNumber(),
				rule.getRuleSetUUID(),
				rule.getRuleSetName());
		dto.setArchived(rule.isArchived());
		dto.setBinaryContent(rule.getBinaryContent());
		dto.setCategories(getCategories(rule.getCategories()));
		dto.setCategoryNames(rule.getCategoryNames());
		dto.setCheckinComment(rule.getCheckinComment());
		dto.setContent(rule.getContent());
		dto.setCreatedDate(rule.getCreatedDate());
		dto.setDescription(rule.getDescription());
		dto.setEffectiveDate(rule.getEffectiveDate());
		dto.setExpiryDate(rule.getExpiryDate());
		dto.setFormat(rule.getFormat());
		dto.setHistorical(rule.isHistorical());
		dto.setLastModifiedDate(rule.getLastModifiedDate());
		dto.setStatus(rule.getStatus());
		dto.setVersionSnapshotUUID(rule.getVersionSnapshotUUID());
		return dto;
	}
	
	private List<CategoryDTO> getCategories(List<Category> categories) {
		List<CategoryDTO> dtoList = new ArrayList<CategoryDTO>(categories.size());
		for( Category category : categories) {
			dtoList.add(getCategoryDTO(category));
		}
		return dtoList;
	}
	
	public RuleSet getRuleSet(RuleSetDTO ruleSetDTO) {
		RuleSet ruleSet = ruleSetFactory.createRuleSet(
				ruleSetDTO.getName(), 
				ruleSetDTO.getDescription(), 
				ruleSetDTO.getFormat());
		ruleSet.setCheckinComment(ruleSetDTO.getCheckinComment());
		ruleSet.setSnapshotName(ruleSetDTO.getSnapshotName());
		ruleSet.setStatus(ruleSetDTO.getStatus());
		ruleSet.getHeaderList().addAll(ruleSetDTO.getHeader());
		ruleSet.getRules().addAll(getRules(ruleSetDTO.getRules()));
		return ruleSet;
	}

	public RuleSetDTO getRuleSetDTO(RuleSet ruleSet) {
		RuleSetDTO dto = new RuleSetDTO(
				ruleSet.getUUID(),
				ruleSet.getName(),
				ruleSet.getVersionNumber());
		dto.setArchived(ruleSet.isArchived());
		dto.setCheckinComment(ruleSet.getCheckinComment());
		dto.setCompiledRuleSet(ruleSet.getCompiledRuleSet());
		dto.setContent(ruleSet.getContent());
		dto.setCreatedDate(ruleSet.getCreatedDate());
		dto.setDescription(ruleSet.getDescription());
		dto.setFormat(ruleSet.getFormat());
		dto.setHeader(ruleSet.getHeaderList());
		dto.setHistorical(ruleSet.isHistorical());
		dto.setLastModifiedDate(ruleSet.getLastModifiedDate());
		dto.setRules(getRules(ruleSet.getRules()));
		dto.setSnapshot(ruleSet.isSnapshot());
		dto.setSnapshotName(ruleSet.getSnapshotName());
		dto.setStatus(ruleSet.getStatus());
		dto.setVersionSnapshotUUID(ruleSet.getVersionSnapshotUUID());
		return dto;
	}

	private List<Rule> getRules(Map<String,RuleDTO> dtoMap) {
		List<Rule> rules = new ArrayList<Rule>(dtoMap.size());
		for(RuleDTO dto : dtoMap.values()){
			rules.add(getRule(dto));
		}
		return rules;
	}

	private Map<String,RuleDTO> getRules(List<Rule> rules) {
		Map<String,RuleDTO> dtoMap = new LinkedHashMap<String,RuleDTO>(rules.size());
		for(Rule rule : rules){
			dtoMap.put(rule.getName(), getRuleDTO(rule));
		}
		return dtoMap;
	}
	
	/*private BusinessRuleInfo getBusinessRuleInfo(BusinessRuleInfoDTO bri) {
		return null;
	}

	/**
	 * TODO this needs fixing - DTO and rule-common/rule-management entity needs to be merged
	 * 
	 * @param ruleProposition
	 * @return
	 */
	/*private RulePropositionDTO getRulePropositionDTO(RuleProposition ruleProposition) {
		RulePropositionDTO dto = new RulePropositionDTO();
		//dto.setComparisonDataType(comparisonDataType)
		return null;
	}

	/**
	 * TODO this needs fixing - DTO and rule-common/rule-management entity needs to be merged
	 * 
	 * @param collection
	 * @return
	 */
	/*private Collection<RuleElement> getRuleElementCollection(Collection<RuleElementDTO> collection) {
		List<RuleElement> list = new ArrayList<RuleElement>(collection.size());
		for(RuleElementDTO dto : collection) {
			RuleElement re = new RuleElement();
//			re.setBusinessRule(dto.getbusinessRule)
		}
		//return list;
		return null;
	}

	/**
	 * TODO this needs fixing - DTO and rule-common/rule-management entity needs to be merged
	 * 
	 * @param collection
	 * @return
	 */
	/*private Collection<BusinessRule> getBusinessRuleCollection(Collection<BusinessRuleDTO> collection) {
		List<BusinessRule> list = new ArrayList<BusinessRule>(collection.size());
		for(BusinessRuleDTO dto : collection) {
			BusinessRule br = new BusinessRule();
			br.setAnchor(dto.getAnchor());
			BusinessRuleInfo bri = getBusinessRuleInfo(dto.getBusinessRuleInfo());
			br.setBusinessRuleInfo(bri);
			br.setBusinessRuleType(dto.getBusinessRuleType());
			br.setCompiledID(dto.getCompiledID());
			br.setDescription(dto.getDescription());
//			br.setElements(dto.getElements());
			br.setFailureMessage(dto.getFailureMessage());
			br.setId(dto.getId());
			br.setIdentifier(dto.getIdentifier());
			br.setName(dto.getName());
			br.setSuccessMessage(dto.getSuccessMessage());

			list.add(br);
		}		
		//return list;
		return null;
	}

	/**
	 * TODO this needs fixing - DTO and rule-common/rule-management entity needs to be merged
	 * 
	 * @param dto
	 * @return
	 */
	/*public BusinessRuleContainer getBusinessRuleContainer(BusinessRuleContainerDTO dto) {
		BusinessRuleContainer brc = new BusinessRuleContainer(dto.getNamespace(), dto.getDescription());
		brc.getBusinessRules().addAll(getBusinessRuleCollection(dto.getBusinessRuleList()));
		
		return brc;
	}*/
}
