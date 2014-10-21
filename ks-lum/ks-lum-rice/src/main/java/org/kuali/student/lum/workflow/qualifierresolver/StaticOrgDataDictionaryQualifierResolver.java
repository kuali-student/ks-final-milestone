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

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.common.util.security.ContextUtils;
import org.kuali.student.r2.core.organization.dto.OrgInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A QualifierResolver class that takes one or more organization ids from the Route Node configuration XML on the
 * document type and uses those organizations as the qualifiers.
 *
 * <p>
 * A sample of the Route Node configuration:
 * <p>
 *
 * <pre>
 * {@code
 * <role name="Senate Review">
 *   <activationType>P</activationType>
 *   <qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrgDataDictionaryQualifierResolver</qualifierResolverClass>
 *   <organizationId>ORGID-141</organizationId>
 * </role>
 * }
 * </pre>
 */
public class StaticOrgDataDictionaryQualifierResolver extends AbstractOrganizationServiceQualifierResolver {
    private static final Logger LOG = LoggerFactory.getLogger(StaticOrgDataDictionaryQualifierResolver.class);

    protected static final String ROUTE_NODE_ORGANIZATION_ID_XML_TAG_NAME = "organizationId";

    /**
     * @see org.kuali.rice.kew.role.QualifierResolver#resolve(org.kuali.rice.kew.engine.RouteContext)
     */
    @Override
    public List<Map<String, String>> resolve(RouteContext context) {
        List<Map<String,String>> attributeSets = new ArrayList<Map<String,String>>();
        XPath xPath = XPathHelper.newXPath();
        NodeList organizationElements;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = db.parse(new InputSource(new StringReader(context.getNodeInstance().getRouteNode().getContentFragment())));
            organizationElements = (NodeList) xPath.evaluate("//" + getOrganizationIdXmlTagName(), document, XPathConstants.NODESET);
        } catch (Exception e) {
            LOG.error("Encountered an issue fetching organization ids", e);
            throw new RuntimeException("Encountered an issue fetching organization ids using xml tag name '" + getOrganizationIdXmlTagName() + "'.", e);
        }
        if (organizationElements.getLength() == 0) {
            String message = String.format("No organizations found in Route Node xml configuration using xml tag name '%s'", getOrganizationIdXmlTagName());
            LOG.error(message);
            throw new RuntimeException(message);
        }
        String orgId = "";
        try {
            for (int i = 0; i < organizationElements.getLength(); i++) {
                Node organizationElement = organizationElements.item(i);
                orgId = "";
                orgId = organizationElement.getTextContent();
                OrgInfo orgInfo = getOrganizationService().getOrg(orgId, ContextUtils.createDefaultContextInfo());
                Map<String,String> attrSet = new LinkedHashMap<String,String>();
                attrSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, orgInfo.getId());
                attributeSets.add(attrSet);
            }
        } catch (DOMException e) {
            LOG.error("Error getting organization from XML node", e);
            throw new RuntimeException("Error getting organization from XML node", e);
        } catch (Exception e) {
            LOG.error("Error getting organization from OrganizationService", e);
            throw new RuntimeException("Error getting organization with id '" + orgId + "' from OrganizationService", e);
        }
        return attributeSets;
    }

    protected String getOrganizationIdXmlTagName() {
        return ROUTE_NODE_ORGANIZATION_ID_XML_TAG_NAME;
    }

}
