/**
 * 
 */
package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.RouteNodeUtils;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.util.XmlHelper;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.search.dto.SearchParam;
import org.kuali.student.core.search.dto.SearchRequest;
import org.kuali.student.core.search.dto.SearchResult;
import org.kuali.student.core.search.dto.SearchResultCell;
import org.kuali.student.core.search.dto.SearchResultRow;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

//TODO: The following annotation needs to be udpated on role definition.
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
 * <role name="Department Review">
 *   <activationType>P</activationType>
 *   <qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeQualifierResolver</qualifierResolverClass>
 *   <useNonDerivedRoles>true</useNonDerivedRoles>
 *   <organizationTypeCode>orgId</organizationTypeCode>
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
 */
public class CocOrgTypeQualifierResolver extends AbstractOrganizationServiceQualifierResolver {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(CocOrgTypeQualifierResolver.class);

    protected static final String DOCUMENT_CONTENT_XML_DEFAULT_ORG_ID_KEY = "orgId";
    protected static final String DOCUMENT_CONTENT_XML_ORG_ID_KEY = "organizationIdDocumentContentKey";

    protected static final String ROUTE_NODE_DOCUMENT_CONTENT_XML_ORG_TYPE_CODE = "organizationTypeCode";
    protected static final String ROUTE_NODE_XML_ORG_ID_QUALIFIER_KEY = "organizationIdQualifierKey";
    protected static final String ROUTE_NODE_XML_USE_NON_DERIVED_ROLES = "useNonDerivedRoles";

    public static final String KUALI_ORG_TYPE_CURRICULUM_PARENT = "kuali.org.CurriculumParent";
    public static final String KUALI_ORG_HIERARCHY_CURRICULUM = "kuali.org.hierarchy.Curriculum";
    public static final String KUALI_ORG_COC = "kuali.org.COC";

    // below string MUST match
    // org.kuali.student.core.assembly.transform.WorkflowFilter.DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME constant
    public static final String DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME = "info";

    /**
     * @see org.kuali.rice.kew.role.QualifierResolver#resolve(org.kuali.rice.kew.engine.RouteContext)
     */
    @Override
    public List<AttributeSet> resolve(RouteContext context) {
        List<AttributeSet> attributeSets = new ArrayList<AttributeSet>();
        for (String orgId : getOrganizationIdsFromDocumentContent(context)) {
              attributeSets.addAll(cocAttributeSetsFromAncestors(orgId, getOrganizationTypeCode(context), getNodeSpecificOrganizationIdAttributeSetKey(context)));
        }
        return attributeSets;
    }

    public String getOrganizationIdDocumentContentFieldKey(RouteContext context) {
        String organizationIdFieldKey = RouteNodeUtils.getValueOfCustomProperty(context.getNodeInstance().getRouteNode(), DOCUMENT_CONTENT_XML_ORG_ID_KEY);
        if (StringUtils.isBlank(organizationIdFieldKey)) {
            LOG.info("Cannot find element '" + DOCUMENT_CONTENT_XML_ORG_ID_KEY + "' on Route Node XML configuration. Will use default value of '" + DOCUMENT_CONTENT_XML_DEFAULT_ORG_ID_KEY + "'.");
            organizationIdFieldKey = DOCUMENT_CONTENT_XML_DEFAULT_ORG_ID_KEY;
        }
        return organizationIdFieldKey;
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

    /**
     * Fetches the organization type code from the Route Node XML configuration
     */
    public String getOrganizationTypeCode(RouteContext context) {
        String organizationTypeCode = RouteNodeUtils.getValueOfCustomProperty(context.getNodeInstance().getRouteNode(), ROUTE_NODE_DOCUMENT_CONTENT_XML_ORG_TYPE_CODE);
        if (StringUtils.isBlank(organizationTypeCode)) {
            throw new RuntimeException("Cannot find required XML element '" + ROUTE_NODE_DOCUMENT_CONTENT_XML_ORG_TYPE_CODE + "' on the Route Node XML configuration.");
        }
        return organizationTypeCode;
    }

    /**
     * Method to fetch the organization ids from the KEW document content XML
     * 
     * @param context
     *            - RouteContext class that holds data about the current document's routing and data
     * @return A list of organization ids that are listed in the XML (may have duplicates if duplicates are allowed by
     *         KS code)
     */
    protected Set<String> getOrganizationIdsFromDocumentContent(RouteContext context) {
        String baseXpathExpression = "/" + KEWConstants.DOCUMENT_CONTENT_ELEMENT + "/" + KEWConstants.APPLICATION_CONTENT_ELEMENT + "/" + DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME;
        String orgXpathExpression = "./" + getOrganizationIdDocumentContentFieldKey(context);
        Document xmlContent = context.getDocumentContent().getDocument();
        XPath xPath = XPathHelper.newXPath();
        try {
            NodeList baseElements = (NodeList) xPath.evaluate(baseXpathExpression, xmlContent, XPathConstants.NODESET);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Found " + baseElements.getLength() + " baseElements to parse for AttributeSets using document XML:");
                XmlHelper.printDocumentStructure(xmlContent);
            }
            Set<String> distinctiveOrganizationIds = new HashSet<String>();
            for (int i = 0; i < baseElements.getLength(); i++) {
                Node baseNode = baseElements.item(i);
                NodeList attributes = (NodeList) xPath.evaluate(orgXpathExpression, baseNode, XPathConstants.NODESET);
                for (int j = 0; j < attributes.getLength(); j++) {
                    Element attributeElement = (Element) attributes.item(j);
                    distinctiveOrganizationIds.add(attributeElement.getTextContent());
                }
            }
            return distinctiveOrganizationIds;
        } catch (XPathExpressionException e) {
            throw new RuntimeException("Encountered an issue executing XPath.", e);
        }
    }

    protected List<AttributeSet> cocAttributeSetsFromAncestors(String orgId, String orgType, String orgIdKey) {
        
        List<AttributeSet> returnAttributeSets = new ArrayList<AttributeSet>();
        List<OrgInfo> orgsForRouting = null;
        if (orgId != null) {
            try {
                List<String> orgIds = new ArrayList<String>();
                // add the existing org in to the list to check for the given type
                orgIds.add(orgId);
                orgIds.addAll(getOrganizationService().getAllAncestors(orgId, getOrganizationHierarchyTypeCode()));
                orgsForRouting = getOrganizationService().getOrganizationsByIdList(orgIds);
            } catch (Exception e) {
                LOG.error("Error calling org service");
                throw new RuntimeException(e);
            }
            if (orgsForRouting != null) {
                for (OrgInfo orgForRouting : orgsForRouting) {
                    if (orgType != null && orgType.equals(orgForRouting.getType())) {
                        List<SearchResultRow> results = relatedOrgsFromOrgId(orgForRouting.getId(), getOrganizationRelationTypeCode(), getRelatedOrganizationTypeCode());
                        returnAttributeSets.addAll(attributeSetFromSearchResult(results, orgIdKey));
                    }
                }
            }
        }
        return returnAttributeSets;
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
