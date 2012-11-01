/**
 * 
 */
package org.kuali.student.lum.workflow.qualifierresolver;

import java.util.*;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.xml.XmlJotter;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.RouteNodeUtils;
import org.kuali.rice.kew.role.QualifierResolver;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An abstract base class that consolidates convenience methods for using the {@link OrganizationService} class.
 * 
 */
public abstract class AbstractOrganizationServiceQualifierResolver implements QualifierResolver {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AbstractOrganizationServiceQualifierResolver.class);
    
    protected static final String DOCUMENT_CONTENT_XML_DEFAULT_ORG_ID_KEY = "orgId";
    protected static final String DOCUMENT_CONTENT_XML_ORG_ID_KEY = "organizationIdDocumentContentKey";

    // below string MUST match
    // org.kuali.student.common.assembly.transform.ProposalWorkflowFilter.DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME constant
    public static final String DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME = "info";

    public static final String KUALI_ORG_TYPE_CURRICULUM_PARENT = "kuali.org.CurriculumParent";
    public static final String KUALI_ORG_HIERARCHY_CURRICULUM  = "kuali.org.hierarchy.Curriculum";
    public static final String KUALI_ORG_DEPARTMENT               = "kuali.org.Department";
    public static final String KUALI_ORG_COLLEGE                  = "kuali.org.College";
    public static final String KUALI_ORG_COC                      = "kuali.org.COC";
    public static final String KUALI_ORG_DIVISION                 = "kuali.org.Division";
    public static final String KUALI_ORG_PROGRAM                  = "kuali.org.Program";

    private OrganizationService organizationService;

    protected OrganizationService getOrganizationService() {
        if (null == organizationService) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization", "OrganizationService"));
        }
        return organizationService;
    }

    protected void setOrganizationService(OrganizationService orgSvc) {
        organizationService = orgSvc;
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
        String baseXpathExpression = "/" + KewApiConstants.DOCUMENT_CONTENT_ELEMENT + "/" + KewApiConstants.APPLICATION_CONTENT_ELEMENT + "/" + DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME;
        String orgXpathExpression = "./" + getOrganizationIdDocumentContentFieldKey(context);
        Document xmlContent = context.getDocumentContent().getDocument();
        XPath xPath = XPathHelper.newXPath();
        try {
            NodeList baseElements = (NodeList) xPath.evaluate(baseXpathExpression, xmlContent, XPathConstants.NODESET);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Found " + baseElements.getLength() + " baseElements to parse for AttributeSets using document XML:" + XmlJotter.jotDocument(xmlContent));
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

    protected String getOrganizationIdDocumentContentFieldKey(RouteContext context) {
        String organizationIdFieldKey = RouteNodeUtils.getValueOfCustomProperty(context.getNodeInstance().getRouteNode(), DOCUMENT_CONTENT_XML_ORG_ID_KEY);
        if (StringUtils.isBlank(organizationIdFieldKey)) {
            LOG.info("Cannot find element '" + DOCUMENT_CONTENT_XML_ORG_ID_KEY + "' on Route Node XML configuration. Will use default value of '" + DOCUMENT_CONTENT_XML_DEFAULT_ORG_ID_KEY + "'.");
            organizationIdFieldKey = DOCUMENT_CONTENT_XML_DEFAULT_ORG_ID_KEY;
        }
        return organizationIdFieldKey;
    }

    protected List<SearchResultRowInfo> relatedOrgsFromOrgId(String orgId, String relationType, String relatedOrgType) {
        List<SearchResultRowInfo> results = null;
        if (null != orgId) {
            List<SearchParamInfo> queryParamValues = new ArrayList<SearchParamInfo>(3);
            SearchParamInfo qpRelType = new SearchParamInfo();
            qpRelType.setKey("org.queryParam.relationType");
            qpRelType.getValues().add(relationType);
            queryParamValues.add(qpRelType);

            SearchParamInfo qpOrgId = new SearchParamInfo();
            qpOrgId.setKey("org.queryParam.orgId");
            qpOrgId.getValues().add(orgId);
            queryParamValues.add(qpOrgId);

            SearchParamInfo qpRelOrgType = new SearchParamInfo();
            qpRelOrgType.setKey("org.queryParam.relatedOrgType");
            qpRelOrgType.getValues().add(relatedOrgType);
            queryParamValues.add(qpRelOrgType);

            SearchRequestInfo searchRequest = new SearchRequestInfo();
            searchRequest.setSearchKey("org.search.orgQuickViewByRelationTypeRelatedOrgTypeOrgId");
            searchRequest.setParams(queryParamValues);
            try {
                SearchResultInfo result = null;
                // TODO: Fix the ContextInfo.
                result = getOrganizationService().search(searchRequest, new ContextInfo());
                results = result.getRows();
            } catch (Exception e) {
                LOG.error("Error calling org service");
                throw new RuntimeException(e);
            }
        }
        return results;
    }
    
    /*
     *  Add attributes for derived role and adhoc routing participants to the results
     */
    protected List<Map<String,String>> attributeSetFromSearchResult(List<SearchResultRowInfo> results, String orgIdKey) {
        List<Map<String,String>> returnAttrSetList = new ArrayList<Map<String,String>>();
        if (results != null) {
            for (SearchResultRowInfo result : results) {
                Map<String,String> attributeSet = new LinkedHashMap<String,String>();
                String resolvedOrgId = "";
                String resolvedOrgShortName = "";
                for (SearchResultCellInfo resultCell : result.getCells()) {
                    if ("org.resultColumn.orgId".equals(resultCell.getKey())) {
                        resolvedOrgId = resultCell.getValue();
                    } else if ("org.resultColumn.orgShortName".equals(resultCell.getKey())) {
                        resolvedOrgShortName = resultCell.getValue();
                    }
                }
                if (orgIdKey != null) {
                    attributeSet.put(orgIdKey, resolvedOrgId);
                }
                attributeSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, resolvedOrgId);
                returnAttrSetList.add(attributeSet);
            }
        }
        return returnAttrSetList;
    }

}
