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
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;

/**
 * @author Kuali Student Team
 *
 */
public abstract class AbstractCocOrgQualifierResolver extends XPathQualifierResolver {

	protected static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger
	.getLogger(AbstractCocOrgQualifierResolver.class);
	
	public static final String KUALI_ORG_TYPE_CURRICULUM_PARENT = "kuali.org.CurriculumParent";
	public static final String KUALI_ORG_HIERARCHY_CURRICULUM  = "kuali.org.hierarchy.Curriculum";
	public static final String KUALI_ORG_DEPARTMENT 			  = "kuali.org.Department";
	public static final String KUALI_ORG_COLLEGE    			  = "kuali.org.College";
	public static final String KUALI_ORG_COC        			  = "kuali.org.COC";
	public static final String KUALI_ORG_DIVISION   			  = "kuali.org.Division";
	public static final String KUALI_ORG_PROGRAM    			  = "kuali.org.Program";

	// below string MUST match org.kuali.student.core.assembly.transform.WorkflowFilter.DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME constant
    public static final String DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME	= "info";

	protected OrganizationService orgService;

	private static final String ORG_RESOLVER_CONFIG =
									"<resolverConfig>" +
										"<baseXPathExpression>/documentContent/applicationContent/" + DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME + "</baseXPathExpression>" +
										"<qualifier name=\"" + KualiStudentKimAttributes.QUALIFICATION_ORG_ID +  "\">" +
											"<xPathExpression>./orgId</xPathExpression>" + 
										"</qualifier>" +
									"</resolverConfig>";

	private final static RuleAttribute ruleAttribute = new RuleAttribute();

	static {
		ruleAttribute.setXmlConfigData(ORG_RESOLVER_CONFIG);
	}

	/**
	 * 
	 */
	public AbstractCocOrgQualifierResolver() {
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
	
	protected List<SearchResultRow> relatedOrgsFromOrgId(String orgId, String relationType, String relatedOrgType) {
		List<SearchResultRow> results = null;
		if (null != orgId) {
			List<SearchParam> queryParamValues = new ArrayList<SearchParam>(2);
			SearchParam qpRelType = new SearchParam();
			qpRelType.setKey("org.queryParam.relationType");
			qpRelType.setValue(relationType);
			queryParamValues.add(qpRelType);
			
			SearchParam qpOrgId = new SearchParam();
			qpOrgId.setKey("org.queryParam.orgId");
			qpOrgId.setValue(orgId);
			queryParamValues.add(qpOrgId);
			
			SearchParam qpRelOrgType = new SearchParam();
			qpRelOrgType.setKey("org.queryParam.relatedOrgType");
			qpRelOrgType.setValue(relatedOrgType);
			queryParamValues.add(qpRelOrgType);
			
	        SearchRequest searchRequest = new SearchRequest();
	        searchRequest.setSearchKey("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId");
	        searchRequest.setParams(queryParamValues);
			try {
				SearchResult result = getOrganizationService().search(searchRequest);
				results = result.getRows();
			} catch (Exception e) {
				LOG.error("Error calling org service");
				throw new RuntimeException(e);
			}
		}
		return results;
	}

	protected List<AttributeSet> attributeSetFromSearchResult(List<SearchResultRow> results,
			String orgShortNameKey, String orgIdKey) {
		List<AttributeSet> returnAttrSetList = new ArrayList<AttributeSet>();
		if(results!=null){
			for(SearchResultRow result:results){
				AttributeSet attributeSet = new AttributeSet();
				String resolvedOrgId = "";
				String resolvedOrgShortName = "";
				for (SearchResultCell resultCell : result.getCells()) {
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
		List<OrgInfo> orgsForRouting = null;
		
		if(orgId!=null){
			try {
				List<String> orgIds = new ArrayList<String>(); 
				// add the existing org in to the list to check for the given type
				orgIds.add(orgId);
				orgIds.addAll(getOrganizationService().getAllAncestors(orgId, KUALI_ORG_HIERARCHY_CURRICULUM));
				orgsForRouting = getOrganizationService().getOrganizationsByIdList(orgIds);
			} catch (Exception e) {
				LOG.error("Error calling org service");
				throw new RuntimeException(e);
			}
			if(orgsForRouting!=null){
				for(OrgInfo orgForRouting:orgsForRouting){
					if(orgType!=null && orgType.equals(orgForRouting.getType())){
						List<SearchResultRow> results = relatedOrgsFromOrgId(orgForRouting.getId(),KUALI_ORG_TYPE_CURRICULUM_PARENT,KUALI_ORG_COC);
						returnAttributeSets.addAll(attributeSetFromSearchResult(results,orgShortNameKey,orgIdKey));
					}
				}
			}
		}
		return returnAttributeSets;
	}
}
