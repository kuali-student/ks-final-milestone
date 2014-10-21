/**
 * 
 */
package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.RouteNodeUtils;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;

/**
 * A QualifierResolver class that will use configuration elements from the Route Node xml configuration to get a list of
 * organizations related to the organization(s) that are set in the document content xml for a particular document
 * instance.
 * 
 * <p>
 * A sample of the Route Node configuration:
 * <p>
 * 
 * <pre>
 * {@code
 * <role name="Document Organization Review">
 *   <activationType>P</activationType>
 *   <qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrganizationQualifierResolver</qualifierResolverClass>
 *   <useNonDerivedRoles>true</useNonDerivedRoles>
 *   <organizationIdQualifierKey>orgId</organizationIdQualifierKey>
 *   <organizationIdDocumentContentKey>orgId</organizationIdDocumentContentKey>
 * </role>
 * }
 * </pre>
 * 
 * <p>
 * A sample of the Document Content xml expected by default:
 * <p>
 * 
 * <pre>
 * {@code
 * <info>
 *   <orgId>1234</orgId>
 *   <orgId>5678</orgId>
 * </info>
 * }
 * </pre>
 *
 * @deprecated - This QualifierResolver class is not being used by the KRAD version of CM (deprecated since CM 3.0)
 */
@Deprecated
public class CocOrganizationQualifierResolver extends AbstractOrganizationServiceQualifierResolver {

    protected static final String ROUTE_NODE_XML_ORG_ID_QUALIFIER_KEY = "organizationIdQualifierKey";
    protected static final String ROUTE_NODE_XML_USE_NON_DERIVED_ROLES = "useNonDerivedRoles";

    public static final String KUALI_ORG_TYPE_CURRICULUM_PARENT = "kuali.org.CurriculumParent";
    public static final String KUALI_ORG_HIERARCHY_CURRICULUM = "kuali.org.hierarchy.Curriculum";
    public static final String KUALI_ORG_COC = "kuali.org.COC";

    /**
     * @see org.kuali.rice.kew.role.QualifierResolver#resolve(org.kuali.rice.kew.engine.RouteContext)
     */
    @Override
    public List<Map<String,String>> resolve(RouteContext context) {
        List<Map<String,String>> attributeSets = new ArrayList<Map<String,String>>();
        String orgIdKey = getNodeSpecificOrganizationIdAttributeSetKey(context);
        for (String orgId : getOrganizationIdsFromDocumentContent(context)) {
            List<SearchResultRowInfo> results = relatedOrgsFromOrgId(orgId, getOrganizationRelationTypeCode(), getRelatedOrganizationTypeCode());
            attributeSets.addAll(attributeSetFromSearchResult(results, orgIdKey));
        }
        return attributeSets;
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

    protected String getOrganizationHierarchyTypeCode() {
        return KUALI_ORG_HIERARCHY_CURRICULUM;
    }

    protected String getOrganizationRelationTypeCode() {
        return KUALI_ORG_TYPE_CURRICULUM_PARENT;
    }

    protected String getRelatedOrganizationTypeCode() {
        return KUALI_ORG_COC;
    }

}
