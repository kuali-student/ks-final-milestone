package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.RouteNodeUtils;
import org.kuali.student.r1.common.search.dto.SearchResultRow;

public class ParentOrganizationQualifierResolver extends
		AbstractOrganizationServiceQualifierResolver {
    protected static final String ROUTE_NODE_XML_ORG_ID_QUALIFIER_KEY = "organizationIdQualifierKey";
    protected static final String ROUTE_NODE_XML_USE_NON_DERIVED_ROLES = "useNonDerivedRoles";
    
    protected static final String ROUTE_NODE_XML_ORG_RELATION_TYPE = "organizationRelationType";
    protected static final String ROUTE_NODE_XML_RELATED_ORG_TYPE = "relatedOrganizationType";
    
	@Override
	public List<Map<String,String>> resolve(RouteContext context) {
        List<Map<String,String>> attributeSets = new ArrayList<Map<String,String>>();
        
        String orgIdKey = getNodeSpecificOrganizationIdAttributeSetKey(context);
        String orgRelationType = getOrganizationRelationTypeCode(context);
        String relatedOrgType = getRelatedOrganizationTypeCode(context);
        
        for (String orgId : getOrganizationIdsFromDocumentContent(context)) {
            List<SearchResultRow> results = relatedOrgsFromOrgId(orgId, orgRelationType, relatedOrgType);
            attributeSets.addAll(attributeSetFromSearchResult(results, orgIdKey));
        }
        return attributeSets;
	}
	
    protected String getRelatedOrganizationTypeCode(RouteContext context) {
    	 String relatedOrganizationType = RouteNodeUtils.getValueOfCustomProperty(context.getNodeInstance().getRouteNode(), ROUTE_NODE_XML_RELATED_ORG_TYPE);
    	 if(StringUtils.isBlank(relatedOrganizationType)){
    		 throw new RuntimeException("Cannot find required XML element '" + ROUTE_NODE_XML_RELATED_ORG_TYPE + "' on the Route Node XML configuration.");
    	 }
    	 return relatedOrganizationType;
	}

	protected String getOrganizationRelationTypeCode(RouteContext context) {
   	 String orgRelationType = RouteNodeUtils.getValueOfCustomProperty(context.getNodeInstance().getRouteNode(), ROUTE_NODE_XML_ORG_RELATION_TYPE);
   	 if(StringUtils.isBlank(orgRelationType)){
   		 throw new RuntimeException("Cannot find required XML element '" + ROUTE_NODE_XML_ORG_RELATION_TYPE + "' on the Route Node XML configuration.");
   	 }
   	 return orgRelationType;
	}

	public String getNodeSpecificOrganizationIdAttributeSetKey(RouteContext context) {
        String organizationIdFieldKey = RouteNodeUtils.getValueOfCustomProperty(context.getNodeInstance().getRouteNode(), ROUTE_NODE_XML_ORG_ID_QUALIFIER_KEY);
        if (StringUtils.isBlank(organizationIdFieldKey)) {
            if (usesNonDerivedOrganizationRoles(context)) {
                throw new RuntimeException("Cannot find required XML element '" + ROUTE_NODE_XML_ORG_ID_QUALIFIER_KEY + "' on the Route Node XML configuration.");
            }
        }
        return organizationIdFieldKey;
    }
    
    public Boolean usesNonDerivedOrganizationRoles(RouteContext context) {
        String useNonDerivedOrganizationRoles = RouteNodeUtils.getValueOfCustomProperty(context.getNodeInstance().getRouteNode(), ROUTE_NODE_XML_USE_NON_DERIVED_ROLES);
        if (StringUtils.isNotBlank(useNonDerivedOrganizationRoles)) {
            return Boolean.valueOf(useNonDerivedOrganizationRoles);
        }
        return true;
    }
}
