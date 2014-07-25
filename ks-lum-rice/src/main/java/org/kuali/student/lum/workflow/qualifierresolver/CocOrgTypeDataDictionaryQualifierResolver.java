/**
 * Copyright 2014 The Kuali Foundation Licensed under the
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
 *
 * Created by delyea on 7/18/14
 */
package org.kuali.student.lum.workflow.qualifierresolver;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.node.RouteNodeUtils;
import org.kuali.rice.kns.service.KNSServiceLocator;
import org.kuali.rice.krad.datadictionary.DocumentEntry;
import org.kuali.rice.krad.datadictionary.RoutingTypeDefinition;
import org.kuali.rice.krad.document.Document;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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
 *   <qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.CocOrgTypeDataDictionaryQualifierResolver</qualifierResolverClass>
 *   <useNonDerivedRoles>true</useNonDerivedRoles>
 *   <organizationTypeCode>kuali.org.Department</organizationTypeCode>
 *   <organizationIdQualifierKey>orgId</organizationIdQualifierKey>
 *   <organizationIdDocumentContentKey>orgId</organizationIdDocumentContentKey>
 * </role>
 * }
 * </pre>
 *
 * <p>
 * The org id value in KRAD converted CM is pulled directly from the document data rather than the document content xml.
 * <p>
 *
 * @author Kuali Student Team
 */
public class CocOrgTypeDataDictionaryQualifierResolver extends AbstractOrganizationServiceQualifierResolver {
    private static final Logger LOG = LoggerFactory.getLogger(CocOrgTypeDataDictionaryQualifierResolver.class);

    protected static final String ROUTE_NODE_DOCUMENT_TYPE_XML_ORG_TYPE_CODE = "organizationTypeCode";
    protected static final String ROUTE_NODE_XML_ORG_ID_QUALIFIER_KEY = "organizationIdQualifierKey";
    protected static final String ROUTE_NODE_XML_USE_NON_DERIVED_ROLES = "useNonDerivedRoles";

    /**
     * @see org.kuali.rice.kew.role.QualifierResolver#resolve(org.kuali.rice.kew.engine.RouteContext)
     */
    @Override
    public List<Map<String,String>> resolve(RouteContext context) {
        final String routeLevel = context.getNodeInstance().getName();
        final DocumentEntry documentEntry = getDocumentEntry(context);
        final RoutingTypeDefinition routingTypeDefinition = getWorkflowAttributeDefintion(documentEntry, routeLevel);
        final Document document = getDocument(context);
        List<Map<String, String>> qualifiers = new ArrayList<>();

        List<Map<String, String>> orgIdsFromDocument;
        if (document != null && routingTypeDefinition != null) {
            // get the org ids from the document instance
            orgIdsFromDocument = KNSServiceLocator.getWorkflowAttributePropertyResolutionService().resolveRoutingTypeQualifiers(document, routingTypeDefinition);
            for (Map<String, String> orgIdFromDocument : orgIdsFromDocument) {
                for(Map.Entry<String,String> entry : orgIdFromDocument.entrySet()) {
                    // for each org id from the document, fetch the ancestors
                    qualifiers.addAll(cocAttributeSetsFromAncestors(entry.getValue(), getOrganizationTypeCode(context), getNodeSpecificOrganizationIdAttributeSetKey(context)));
                }
            }
        } else {
            LOG.warn("Could not find valid organization id values from document with KEW document id '{}' for route level '{}'", getDocumentId(context), routeLevel);
            if (routingTypeDefinition == null) {
                LOG.warn("RoutingTypeDefinition is null for route level '{}'", routeLevel);
            }
            qualifiers = new ArrayList<>();
            Map<String, String> basicQualifier = new HashMap<String, String>();
            qualifiers.add(basicQualifier);
        }
        decorateWithCommonQualifiers(qualifiers, document, documentEntry, context.getDocument().getDocumentType().getName());
        return qualifiers;
    }

    /**
     * Fetches the organization type code from the Route Node XML configuration
     */
    public String getOrganizationTypeCode(RouteContext context) {
        String organizationTypeCode = RouteNodeUtils.getValueOfCustomProperty(context.getNodeInstance().getRouteNode(), ROUTE_NODE_DOCUMENT_TYPE_XML_ORG_TYPE_CODE);
        if (StringUtils.isBlank(organizationTypeCode)) {
            throw new RuntimeException("Cannot find required XML element '" + ROUTE_NODE_DOCUMENT_TYPE_XML_ORG_TYPE_CODE + "' on the Route Node XML configuration.");
        }
        return organizationTypeCode;
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

    protected List<Map<String,String>> cocAttributeSetsFromAncestors(String orgId, String orgType, String orgIdKey) {

        List<Map<String,String>> returnAttributeSets = new ArrayList<Map<String,String>>();
        List<OrgInfo> orgsForRouting = null;
        if (orgId != null) {
            try {
                List<String> orgIds = new ArrayList<String>();
                // add the existing org in to the list to check for the given type
                orgIds.add(orgId);
                orgIds.addAll(getOrganizationService().getAllAncestors(orgId, getOrganizationHierarchyTypeCode(), ContextUtils.getContextInfo()));
                orgsForRouting = null;
                orgsForRouting = getOrganizationService().getOrgsByIds(orgIds, null);
            } catch (Exception e) {
                LOG.error("Error calling org service");
                throw new RuntimeException(e);
            }
            if (orgsForRouting != null) {
                for (OrgInfo orgForRouting : orgsForRouting) {
                    if (orgType != null && orgType.equals(orgForRouting.getTypeKey())) {
                        List<SearchResultRowInfo> results = relatedOrgsFromOrgId(orgForRouting.getId(), getOrganizationRelationTypeCode(), getRelatedOrganizationTypeCode());
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
