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
import org.kuali.rice.kew.api.exception.WorkflowException;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.RouteNodeUtils;
import org.kuali.rice.kew.role.QualifierResolver;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.kew.api.KewApiConstants;
import org.kuali.rice.kim.api.KimConstants;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.datadictionary.DocumentEntry;
import org.kuali.rice.krad.datadictionary.RoutingTypeDefinition;
import org.kuali.rice.krad.datadictionary.WorkflowAttributes;
import org.kuali.rice.krad.document.Document;
import org.kuali.rice.krad.service.DocumentService;
import org.kuali.rice.krad.service.KRADServiceLocatorWeb;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.common.util.ContextBuilder;
import org.kuali.student.r2.core.search.dto.*;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * An abstract base class that consolidates convenience methods for using the {@link OrganizationService} class.
 * 
 */
public abstract class AbstractOrganizationServiceQualifierResolver implements QualifierResolver {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractOrganizationServiceQualifierResolver.class);
    
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
    private DocumentService documentService;

    protected OrganizationService getOrganizationService() {
        if (null == organizationService) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization", "OrganizationService"));
        }
        return organizationService;
    }

    protected void setOrganizationService(OrganizationService orgSvc) {
        organizationService = orgSvc;
    }

    protected DocumentService getDocumentService() {
        if ( documentService == null ) {
            documentService = KRADServiceLocatorWeb.getDocumentService();
        }
        return documentService;
    }

    protected void setDocumentService(DocumentService docService) {
        documentService = docService;
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
        org.w3c.dom.Document xmlContent = context.getDocumentContent().getDocument();
        XPath xPath = XPathHelper.newXPath();
        try {
            NodeList baseElements = (NodeList) xPath.evaluate(baseXpathExpression, xmlContent, XPathConstants.NODESET);
            if (LOG.isDebugEnabled()) {
                LOG.debug("Found {} baseElements to parse for AttributeSets using document XML: {}", baseElements.getLength(), XmlJotter.jotDocument(xmlContent));
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
            LOG.info("Cannot find element '{}' on Route Node XML configuration. Will use default value of '{}'.", DOCUMENT_CONTENT_XML_ORG_ID_KEY, DOCUMENT_CONTENT_XML_DEFAULT_ORG_ID_KEY);
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
                result = getOrganizationService().search(searchRequest, ContextBuilder.loadContextInfo());
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

    /**
     * Retrieves the document that the current route context is operating on
     * @param context the current route context
     * @return the document
     */
    protected Document getDocument(RouteContext context) {
        String documentID = getDocumentId(context);

        if (documentID != null) {
            try {
                // below setup of user session is so no NPE is thrown by Exception Routing
                if (GlobalVariables.getUserSession() == null) {
                    GlobalVariables.setUserSession(new UserSession(KRADConstants.SYSTEM_USER));
                }

                return getDocumentService().getByDocumentHeaderIdSessionless(documentID);
            }
            catch (WorkflowException e) {
                LOG.error("Unable to retrieve document.", e);
                return null;
            }
        }
        return null;
    }

    /**
     * Retrieves the id of the current document from the RouteContext
     * @param context the current route context
     * @return the id of the document
     */
    protected String getDocumentId(RouteContext context) {
        final String documentID = context.getNodeInstance().getDocumentId();
        return documentID != null ? documentID.toString() : null;
    }


    /**
     * Retrieves the data dictionary entry for the document being operated on by the given route context
     * @param context the current route context
     * @return the data dictionary document entry
     */
    protected DocumentEntry getDocumentEntry(RouteContext context) {
        return KRADServiceLocatorWeb.getDataDictionaryService().getDataDictionary().getDocumentEntry(context.getDocument().getDocumentType().getName());
    }

    /**
     * Retrieves the proper List of WorkflowAttributes for the given route level from the data dictionary
     * document entry
     * @param documentEntry the data dictionary document entry for the currently routed document
     * @param routeLevelName the name of the route level
     * @return a WorkflowAttributeDefinition if one could be found for the route level; otherwise, nothing
     */
    protected RoutingTypeDefinition getWorkflowAttributeDefintion(DocumentEntry documentEntry, String routeLevelName) {
        final WorkflowAttributes workflowAttributes = documentEntry.getWorkflowAttributes();
        if ( workflowAttributes == null ) {
            return null;
        }
        final Map<String, RoutingTypeDefinition> routingTypeMap = workflowAttributes.getRoutingTypeDefinitions();
        if (routingTypeMap.containsKey(routeLevelName)) return routingTypeMap.get(routeLevelName);
        return null;
    }

    /**
     * Add common qualifiers to every Map<String, String> in the given List of Map<String, String>
     * @param qualifiers a List of Map<String, String>s to add common qualifiers to
     * @param document the document currently being routed
     * @param documentEntry the data dictionary entry of the type of document currently being routed
     * @param routeLevel the document's current route level
     */
    protected void decorateWithCommonQualifiers(List<Map<String, String>> qualifiers, org.kuali.rice.krad.document.Document document, DocumentEntry documentEntry, String routeLevel) {
        for (Map<String, String> qualifier : qualifiers) {
            addCommonQualifiersToMap(qualifier, document, documentEntry, routeLevel);
        }
    }

    /**
     * Adds common qualifiers to a given Map<String, String>
     * @param qualifier an Map<String, String> to add common qualifiers to
     * @param document the document currently being routed
     * @param documentEntry the data dictionary entry of the type of document currently being routed
     * @param routeLevel the document's current route level
     */
    protected void addCommonQualifiersToMap(Map<String, String> qualifier, org.kuali.rice.krad.document.Document document, DocumentEntry documentEntry, String routeLevel) {
        if ( document != null ) {
            qualifier.put(KimConstants.AttributeConstants.DOCUMENT_NUMBER, document.getDocumentNumber());
        }
        if ( documentEntry != null ) {
            qualifier.put(KimConstants.AttributeConstants.DOCUMENT_TYPE_NAME, documentEntry.getDocumentTypeName());
        }
        qualifier.put(KimConstants.AttributeConstants.ROUTE_NODE_NAME, routeLevel);
    }

}
