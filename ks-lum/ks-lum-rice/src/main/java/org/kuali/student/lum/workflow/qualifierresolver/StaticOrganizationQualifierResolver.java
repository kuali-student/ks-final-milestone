/**
 * 
 */
package org.kuali.student.lum.workflow.qualifierresolver;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.core.util.AttributeSet;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

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
 *   <qualifierResolverClass>org.kuali.student.lum.workflow.qualifierresolver.StaticOrganizationQualifierResolver</qualifierResolverClass>
 *   <organizationId>141</organizationId>
 * </role>
 * }
 * </pre>
 * 
 */
public class StaticOrganizationQualifierResolver extends AbstractOrganizationServiceQualifierResolver {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(StaticOrganizationQualifierResolver.class);

    protected static final String ROUTE_NODE_ORGANIZATION_ID_XML_TAG_NAME = "organizationId";

    /**
     * @see org.kuali.rice.kew.role.QualifierResolver#resolve(org.kuali.rice.kew.engine.RouteContext)
     */
    @Override
    public List<AttributeSet> resolve(RouteContext context) {
        List<AttributeSet> attributeSets = new ArrayList<AttributeSet>();
        XPath xPath = XPathHelper.newXPath();
        NodeList organizationElements;
        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = db.parse(new InputSource(new StringReader(context.getNodeInstance().getRouteNode().getContentFragment())));
            organizationElements = (NodeList) xPath.evaluate("//" + getOrganizationIdXmlTagName(), document, XPathConstants.NODESET);
        } catch (Exception e) {
            LOG.error(e);
            throw new RuntimeException("Encountered an issue fetching organization ids using xml tag name '" + getOrganizationIdXmlTagName() + "'.", e);
        }
        if (organizationElements.getLength() == 0) {
            LOG.error("No organizations found in Route Node xml configuration using xml tag name '" + getOrganizationIdXmlTagName() + "'");
            throw new RuntimeException("No organizations found in Route Node xml configuration using xml tag name '" + getOrganizationIdXmlTagName() + "'");
        }
        String orgId = "";
        try {
            for (int i = 0; i < organizationElements.getLength(); i++) {
                Node organizationElement = organizationElements.item(i);
                orgId = "";
                orgId = organizationElement.getTextContent();
                OrgInfo orgInfo = getOrganizationService().getOrganization(orgId);
                AttributeSet attrSet = new AttributeSet();
                attrSet.put(KualiStudentKimAttributes.QUALIFICATION_ORG_ID, orgInfo.getId());
                attributeSets.add(attrSet);
            }
        } catch (DOMException e) {
            LOG.error(e);
            throw new RuntimeException("Error getting organization from XML node", e);
        } catch (Exception e) {
            LOG.error(e);
            throw new RuntimeException("Error getting organization with id '" + orgId + "' from OrganizationService", e);
        }
        return attributeSets;
    }

    protected String getOrganizationIdXmlTagName() {
        return ROUTE_NODE_ORGANIZATION_ID_XML_TAG_NAME;
    }
}