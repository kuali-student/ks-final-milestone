/*
 * Copyright 2009 The Kuali Foundation Licensed under the
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
package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.List;

import javax.xml.namespace.QName;

import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.role.XPathQualifierResolver;
import org.kuali.rice.kew.rule.bo.RuleAttribute;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.search.dto.QueryParamValue;
import org.kuali.student.core.search.dto.Result;
import org.kuali.student.core.search.dto.ResultCell;

/**
 * @author Kuali Student Team
 *
 */
public abstract class AbstractOrgQualifierResolver extends XPathQualifierResolver {

	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(AbstractOrgQualifierResolver.class);
	
	protected static final String KUALI_ORG_TYPE_CURRICULUM_CHILD = "kuali.org.CurriculumChild";
	protected static final String KUALI_ORG_HIERARCHY_CURRICULUM  = "kuali.org.hierarchy.Curriculum";
	protected static final String KUALI_ORG_DEPARTMENT 			  = "kuali.org.Department";
	protected static final String KUALI_ORG_COLLEGE    			  = "kuali.org.College";
	protected static final String KUALI_ORG_COC        			  = "kuali.org.COC";
	protected static final String KUALI_ORG_DIVISION   			  = "kuali.org.Division";
	protected static final String KUALI_ORG_PROGRAM    			  = "kuali.org.Program";

	protected OrganizationService orgService;

	protected static final String ORG_RESOLVER_CONFIG =
									"<resolverConfig>" +
										"<baseXPathExpression>/documentContent/applicationContent/cluProposalDocInfo</baseXPathExpression>" +
										"<qualifier name=\"" + KualiStudentKimAttributes.QUALIFICATION_ORG_ID +  "\">" +
											"<xPathExpression>./orgId</xPathExpression>" + 
										"</qualifier>" +
									"</resolverConfig>";

	protected static RuleAttribute ruleAttribute = new RuleAttribute();

	static {
		ruleAttribute.setXmlConfigData(ORG_RESOLVER_CONFIG);
	}

	/**
	 * 
	 */
	public AbstractOrgQualifierResolver() {
		setRuleAttribute(ruleAttribute);
	}

	protected OrganizationService getOrganizationService() {
		if (null == orgService) {
		   	orgService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization","OrganizationService"));
		}
		return orgService;
	}

	protected void setOrganizationService(OrganizationService orgSvc) {
		orgService = orgSvc;
	}

	protected boolean isOrgType(OrgInfo orgInfo, String orgType) {
		String actualType;
		if ((null != orgInfo) && (null != (actualType = orgInfo.getType()))) {
			return actualType.equals(orgType);
		}
		return false;
	}
	
	/**
	 * @param attributeSets 
	 * @param string
	 * @return
	 */
	protected String getAttribute(List<AttributeSet> attributeSets, String searchStr) {
		String attrStr;
		
		for (AttributeSet set : attributeSets) {
			attrStr = set.get(searchStr);
			if (null != attrStr) {
				return attrStr;
			}
		}
		return null;
	}
	
	protected List<Result> relatedOrgsFromOrgId(String orgId, String relationType, String relatedOrgType) {
		List<Result> results = null;
		if (null != orgId) {
			List<QueryParamValue> queryParamValues = new ArrayList<QueryParamValue>(2);
			QueryParamValue qpRelType = new QueryParamValue();
			qpRelType.setKey("org.queryParam.relationType");
			qpRelType.setValue(relationType);
			queryParamValues.add(qpRelType);
			
			QueryParamValue qpOrgId = new QueryParamValue();
			qpOrgId.setKey("org.queryParam.orgId");
			qpOrgId.setValue(orgId);
			queryParamValues.add(qpOrgId);
			
			QueryParamValue qpRelOrgType = new QueryParamValue();
			qpRelOrgType.setKey("org.queryParam.relatedOrgType");
			qpRelOrgType.setValue(relatedOrgType);
			queryParamValues.add(qpRelOrgType);
			try {
				results = getOrganizationService().searchForResults("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId",
						queryParamValues);
			} catch (Exception e) {
				LOG.error("Error calling org service");
				throw new RuntimeException(e);
			}
		}
		return results;
	}

	protected List<AttributeSet> attributeSetFromSearchResult(List<Result> results,
			String orgShortNameKey, String orgIdKey) {
		List<AttributeSet> returnAttrSetList = new ArrayList<AttributeSet>();
		if(results!=null){
			for(Result result:results){
				AttributeSet attributeSet = new AttributeSet();
				String resolvedOrgId = "";
				String resolvedOrgShortName = "";
				for (ResultCell resultCell : result.getResultCells()) {
					if ("org.resultColumn.orgId".equals(resultCell
							.getKey())) {
						resolvedOrgId = resultCell.getValue();
					} else if ("org.resultColumn.orgShortName"
							.equals(resultCell.getKey())) {
						resolvedOrgShortName = resultCell.getValue();
					}
				}
				if(orgShortNameKey!=null){
					attributeSet.put(orgShortNameKey, resolvedOrgShortName);
				}
				if(orgIdKey!=null){
					attributeSet.put(orgIdKey, resolvedOrgId);
				}
				attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG, resolvedOrgShortName);
				attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, resolvedOrgId);
				returnAttrSetList.add(attributeSet);
			}
		}
		return returnAttrSetList;
	}
	
	protected List<AttributeSet> cocAttributeSetsFromAncestors(String orgId, String orgType, String orgShortNameKey,String orgIdKey){
		List<AttributeSet> returnAttributeSets = new ArrayList<AttributeSet>();
		List<OrgInfo> ancestorOrgs = null;
		
		if(orgId!=null){
			try {
				List<String> ancestorIds = getOrganizationService().getAllAncestors(orgId, KUALI_ORG_HIERARCHY_CURRICULUM);
				if(ancestorIds != null && ancestorIds.size() > 0) {
					ancestorOrgs = getOrganizationService().getOrganizationsByIdList(ancestorIds);
				}
			} catch (Exception e) {
				LOG.error("Error calling org service");
				throw new RuntimeException(e);
			}
			if(ancestorOrgs!=null){
				for(OrgInfo ancestorOrg:ancestorOrgs){
					if(orgType!=null && orgType.equals(ancestorOrg.getType())){
						List<Result> results = relatedOrgsFromOrgId(ancestorOrg.getId(),KUALI_ORG_TYPE_CURRICULUM_CHILD,KUALI_ORG_COC);
						returnAttributeSets.addAll(attributeSetFromSearchResult(results,orgShortNameKey,orgIdKey));
					}
				}
			}
		}
		return returnAttributeSets;
	}
}
