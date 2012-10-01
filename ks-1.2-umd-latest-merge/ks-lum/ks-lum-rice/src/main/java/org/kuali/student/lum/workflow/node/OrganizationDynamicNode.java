/**
 * 
 */
package org.kuali.student.lum.workflow.node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.exception.RiceRuntimeException;
import org.kuali.rice.core.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kew.doctype.bo.DocumentType;
import org.kuali.rice.kew.engine.RouteContext;
import org.kuali.rice.kew.engine.RouteHelper;
import org.kuali.rice.kew.engine.node.Branch;
import org.kuali.rice.kew.engine.node.DynamicNode;
import org.kuali.rice.kew.engine.node.DynamicResult;
import org.kuali.rice.kew.engine.node.NodeState;
import org.kuali.rice.kew.engine.node.Process;
import org.kuali.rice.kew.engine.node.RoleNode;
import org.kuali.rice.kew.engine.node.RouteNode;
import org.kuali.rice.kew.engine.node.RouteNodeInstance;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.role.RoleRouteModule;
import org.kuali.rice.kew.rule.xmlrouting.XPathHelper;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.kew.util.XmlHelper;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.dto.OrgOrgRelationInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.lum.workflow.qualifierresolver.AbstractOrganizationServiceQualifierResolver;
import org.kuali.student.lum.workflow.qualifierresolver.OrganizationCurriculumCommitteeQualifierResolver;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * A Dynamic Node implementation that will use the KS Organization Hierarchy to
 * dynamically generate route paths based on the organizations sent to Workflow
 * for each document
 * 
 */
public class OrganizationDynamicNode implements DynamicNode {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(OrganizationDynamicNode.class);

    // name of the prototype node used by the dynamically created node instances
    protected static final String ORG_HIERARCHY_NODE = "Org Hierarchy Review";
    // node state key that will be used to store the organization ids in each node instance
    public static final String NODE_STATE_ORG_ID_KEY = "org.id.key";

    /**
     * The following 4 properties should match the RoleRouteModule constants
     * which are currently set to 'protected'
     * 
     * Created https://jira.kuali.org/browse/KULRICE-4448 to track change to
     * Rice
     */
    public static final String QUALIFIER_RESOLVER_ELEMENT = "qualifierResolver";
    public static final String QUALIFIER_RESOLVER_CLASS_ELEMENT = "qualifierResolverClass";
    public static final String RESPONSIBILITY_TEMPLATE_NAME_ELEMENT = "responsibilityTemplateName";
    public static final String NAMESPACE_ELEMENT = "namespace";

    private OrganizationService organizationService;

    public OrganizationService getOrganizationService() {
        if (null == organizationService) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/organization", "OrganizationService"));
        }
        return organizationService;
    }

    public void setOrganizationService(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Override
    public DynamicResult transitioningInto(RouteContext context, RouteNodeInstance dynamicNodeInstance, RouteHelper helper) throws Exception {
        LOG.debug("Entering transitioningInto");
        DocumentType docType = setUpDocumentType(context.getDocument().getDocumentType(), dynamicNodeInstance);
        // String prototypeNodeName = RouteNodes.DEPT.getNodeName();
        String prototypeNodeName = ORG_HIERARCHY_NODE;
        RouteNode roleNodePrototype = docType.getNamedProcess(prototypeNodeName).getInitialRouteNode();
        if (roleNodePrototype == null) {
            throw new WorkflowException("Couldn't locate node for name: " + prototypeNodeName);
        }

        List<String> orgIds = getInitialOrganizationIdsForRouting(context, dynamicNodeInstance, helper);
        if ((orgIds != null) && (orgIds.size() > 1)) {
            throw new RuntimeException("Found a total of " + orgIds.size() + " organizations for routing on document when only one is allowed.");
        }
        DynamicResult result = new DynamicResult(orgIds == null || orgIds.isEmpty(), null);
        for (String orgId : orgIds) {
            RouteNodeInstance nodeInstance = generateNextNodeInstance(orgId, roleNodePrototype, context, dynamicNodeInstance.getBranch(), helper);
            LOG.debug("Exiting transitioningInto with " + ((nodeInstance == null) ? "no" : "a") + " valid next node instance");
            if (nodeInstance != null) {
                result.setNextNodeInstance(nodeInstance);
            }
        }
        return result;
    }

    /**
     * This method is used by the {@link #transitioningInto(RouteContext, RouteNodeInstance, RouteHelper)} method and
     * the organization id values returned will be used to generate node instances that will begin the dynamic
     * organization routing.
     * 
     * @param context
     *            - RouteContext class that holds data about the current document's routing and data
     * @param dynamicNodeInstance
     *            - The initial instance of the dynamic node as determined by the route node configuration
     * @param helper
     *            - RouteHelper convenience class used to make some routing operations a bit easier
     * @return A list of organization ids that will be used to create next node instances in the routing of the
     *         document. By default these are the organizations set in the the current document's Document Content xml
     *         by Kuali Student at the point of Save and/or Submit
     */
    protected List<String> getInitialOrganizationIdsForRouting(RouteContext context, RouteNodeInstance dynamicNodeInstance, RouteHelper helper) {
        List<String> orgIds = getOrganizationIdsFromDocumentContent(context);
        if ((orgIds != null) && (orgIds.size() > 1)) {
            throw new RuntimeException("Found a total of " + orgIds.size() + " organizations for routing on document when only one is allowed.");
        }

        try {
            for (String orgId : orgIds) {
                OrgInfo orgInfo = getOrganizationService().getOrganization(orgId);
                LOG.debug("Org on Document: " + getOrgInfoForPrint(orgInfo));
                List<OrgOrgRelationInfo> orgRelationInfos = getOrganizationService().getOrgOrgRelationsByOrg(orgId);
                for (OrgOrgRelationInfo orgOrgRelationInfo : orgRelationInfos) {
                    LOG.debug("---- Org Relation:");
                    LOG.debug("------------ Org ID: " + orgOrgRelationInfo.getOrgId());
                    orgInfo = getOrganizationService().getOrganization(orgOrgRelationInfo.getRelatedOrgId());
                    LOG.debug("------------ Related Org on Document: " + getOrgInfoForPrint(orgInfo));
                    LOG.debug("------------ Relation State: " + orgOrgRelationInfo.getState());
                    LOG.debug("------------ Relation Type: " + orgOrgRelationInfo.getType());
                }
                List<OrgOrgRelationInfo> relatedOrgRelationInfos = getOrganizationService().getOrgOrgRelationsByRelatedOrg(orgId);
                for (OrgOrgRelationInfo orgOrgRelationInfo : relatedOrgRelationInfos) {
                    LOG.debug("---- Related Org Relation:");
                    LOG.debug("------------ Related Org ID: " + orgOrgRelationInfo.getRelatedOrgId());
                    orgInfo = getOrganizationService().getOrganization(orgOrgRelationInfo.getOrgId());
                    LOG.debug("------------ Org of Relation: " + getOrgInfoForPrint(orgInfo));
                    LOG.debug("------------ Relation State: " + orgOrgRelationInfo.getState());
                    LOG.debug("------------ Relation Type: " + orgOrgRelationInfo.getType());
                }
            }
        } catch (Exception e) {
            LOG.error(e);
            throw new RuntimeException("Caught Exception using Organization Service", e);
        }
        return orgIds;
    }

    /**
     * Method to fetch the organization ids from the KEW document content xml
     * 
     * @param context
     *            - RouteContext class that holds data about the current document's routing and data
     * @return A list of organization ids that are listed in the xml (may have duplicates if duplicates are allowed by
     *         KS code)
     */
    protected List<String> getOrganizationIdsFromDocumentContent(RouteContext context) {
        Document xmlContent = context.getDocumentContent().getDocument();
        XPath xPath = XPathHelper.newXPath();
        try {
            List<String> orgIds = new ArrayList<String>();
            NodeList orgElements = (NodeList) xPath.evaluate("/documentContent/applicationContent/" + AbstractOrganizationServiceQualifierResolver.DOCUMENT_CONTENT_XML_ROOT_ELEMENT_NAME + "/orgId", xmlContent,
                    XPathConstants.NODESET);
            for (int index = 0; index < orgElements.getLength(); index++) {
                Element attributeElement = (Element) orgElements.item(index);
                String attributeValue = attributeElement.getTextContent();
                orgIds.add(attributeValue);
            }
            if (LOG.isDebugEnabled()) {
                LOG.debug("Found " + orgElements.getLength() + " organization ids to parse for routing:");
                XmlHelper.printDocumentStructure(xmlContent);
            }
            return orgIds;
        } catch (XPathExpressionException e) {
            throw new RiceRuntimeException("Encountered an issue executing XPath.", e);
        }
    }

    @Override
    public DynamicResult transitioningOutOf(RouteContext context, RouteHelper helper) throws Exception {
        LOG.debug("Variables for transitioningOutOf");
        RouteNodeInstance processInstance = context.getNodeInstance().getProcess();

        List<String> relatedOrgIds = getNextOrganizationIdsForRouting(context, helper);
        // dynamic routing is complete if there are no more related org ids
        DynamicResult result = new DynamicResult(relatedOrgIds.isEmpty(), null);
        for (String relatedOrgId : relatedOrgIds) {
            RouteNodeInstance nodeInstance = generateNextNodeInstance(relatedOrgId, context, processInstance.getBranch(), helper);
            result.getNextNodeInstances().add(nodeInstance);
        }
        return result;
    }

    /**
     * Convenience method to get a consistent organization data in order to print to the log
     */
    protected String getOrgInfoForPrint(OrgInfo orgInfo) {
        return orgInfo.getId() + " - " + orgInfo.getShortName() + " (" + orgInfo.getLongName() + ")";
    }

    /**
     * This method is used by the {@link #transitioningOutOf(RouteContext, RouteHelper)} method and the organization id
     * values returned will be used to generate node instances that will continue the dynamic organization routing.
     * 
     * The default implementation retrieves the organization from the previous route node and uses the
     * {@link OrganizationService#getOrgOrgRelationsByRelatedOrg(String)} method to find all organization relations for
     * it. That list is then parsed to find all organization relations that are both active and of the relation type
     * that matches {@link AbstractOrganizationServiceQualifierResolver#KUALI_ORG_TYPE_CURRICULUM_PARENT}. A unique list of those
     * organization ids is returned.
     * 
     * @param context
     *            - RouteContext class that holds data about the current document's routing and data
     * @param helper
     *            - RouteHelper convenience class used to make some routing operations a bit easier
     * @return A list of organization ids that will be used to create next node instances in the routing of the
     *         document.
     */
    protected List<String> getNextOrganizationIdsForRouting(RouteContext context, RouteHelper helper) {
        RouteNodeInstance currentNode = context.getNodeInstance();
        String currentNodeName = currentNode.getName();
        LOG.debug("currentNodeName = '" + currentNodeName + "'");
        NodeState currentNodeOrgIdState = currentNode.getNodeState(NODE_STATE_ORG_ID_KEY);
        LOG.debug("currentNodeOrgIdState is " + ((currentNodeOrgIdState != null) ? "not " : "") + "null");
        String currentNodeOrgId = (currentNodeOrgIdState != null) ? currentNodeOrgIdState.getValue() : null;
        LOG.debug("currentNodeOrgId = '" + currentNodeOrgId + "'");
        Set<String> relatedOrgIds = new HashSet<String>();
        try {
            List<OrgOrgRelationInfo> relatedOrgRelationInfos = getOrganizationService().getOrgOrgRelationsByRelatedOrg(currentNodeOrgId);
            for (OrgOrgRelationInfo orgOrgRelationInfo : relatedOrgRelationInfos) {
                if (StringUtils.equals("Active", orgOrgRelationInfo.getState())) {
                    if (StringUtils.equals(AbstractOrganizationServiceQualifierResolver.KUALI_ORG_TYPE_CURRICULUM_PARENT, orgOrgRelationInfo.getType())) {
                        LOG.debug("---- Related Org Relation:");
                        OrgInfo referenceOrgInfo = getOrganizationService().getOrganization(orgOrgRelationInfo.getRelatedOrgId());
                        OrgInfo nextNodeOrgInfo = getOrganizationService().getOrganization(orgOrgRelationInfo.getOrgId());
                        LOG.debug("------------ Reference Org: " + getOrgInfoForPrint(referenceOrgInfo));
                        LOG.debug("------------ Org for Next Node: " + getOrgInfoForPrint(nextNodeOrgInfo));
                        relatedOrgIds.add(nextNodeOrgInfo.getId());
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("Exception caught attempting to use org hierarchy routing", e);
            throw new RuntimeException("Exception caught attempting to use org hierarchy routing", e);
        }
        return new ArrayList<String>(relatedOrgIds);
    }

    /**
     * Generates a new node instance for the given organization id using the default prototype 'role' route node
     * definition created by the {@link #setUpDocumentType(DocumentType, RouteNodeInstance)} method.
     * 
     */
    protected RouteNodeInstance generateNextNodeInstance(String orgId, RouteContext context, Branch branch, RouteHelper helper) {
        return generateNextNodeInstance(orgId, helper.getNodeFactory().getRouteNode(context, ORG_HIERARCHY_NODE), context, branch, helper);
    }

    /**
     * Generates a new node instance for the given organization id using the given route node definition.
     * 
     */
    protected RouteNodeInstance generateNextNodeInstance(String orgId, RouteNode routeNodeDefinition, RouteContext context, Branch branch, RouteHelper helper) {
        LOG.debug("Adding new node with name '" + routeNodeDefinition.getRouteNodeName() + "'");
        RouteNodeInstance actualRouteNodeInstance = helper.getNodeFactory().createRouteNodeInstance(context.getDocument().getRouteHeaderId(), routeNodeDefinition);
        actualRouteNodeInstance.setBranch(branch);
        actualRouteNodeInstance.addNodeState(new NodeState(NODE_STATE_ORG_ID_KEY, orgId));
        return actualRouteNodeInstance;
    }

    /**
     * Method verifies that the Organization Hierarchy Review node exists on the document type. If it does not exist it
     * will add it and save the document type. This node is required because it will be used as a prototype for any
     * generated 'role' nodes (also known as KIM Responsibility Review Nodes).
     * 
     * @param documentType
     *            - DocumentType object that needs nodes defined but may not have them defined
     * @param dynamicNodeInstance
     *            - The node instance that represents the dynamic node as defined in the document type configuration
     *            (the node that tells KEW to look at this class for the node processing)
     */
    protected DocumentType setUpDocumentType(DocumentType documentType, RouteNodeInstance dynamicNodeInstance) {
        boolean altered = false;
        // add the org hierarchy review node
        if (documentType.getNamedProcess(ORG_HIERARCHY_NODE) == null) {
            RouteNode hierarchyNode = getKimRoleNode(ORG_HIERARCHY_NODE, dynamicNodeInstance);
            documentType.addProcess(getPrototypeProcess(hierarchyNode, documentType));
            altered = true;
        }
        if (altered) {
            // side step normal version etc. because it can cause exceptions
            KEWServiceLocator.getDocumentTypeService().save(documentType);
        }
        return KEWServiceLocator.getDocumentTypeService().findByName(documentType.getName());
    }

    /**
     * Method generates the {@link RouteNode} definition that will be used as a prototype for any dynamically created route node instances for this dynamic node class.
     * 
     * @param routeNodeName - The name to be used for the new route node definition
     * @param dynamicNodeInstance - used to set up the {@link DocumentType} on the generated route node definition 
     */
    protected RouteNode getKimRoleNode(String routeNodeName, RouteNodeInstance dynamicNodeInstance) {
        RouteNode roleNode = new RouteNode();
        roleNode.setFinalApprovalInd(Boolean.FALSE);
        roleNode.setMandatoryRouteInd(Boolean.FALSE);
        roleNode.setActivationType(KEWConstants.ROUTE_LEVEL_PARALLEL);
        roleNode.setDocumentType(dynamicNodeInstance.getRouteNode().getDocumentType());
        roleNode.setNodeType(RoleNode.class.getName());
        roleNode.setRouteMethodName(RoleRouteModule.class.getName());
        roleNode.setRouteMethodCode(KEWConstants.ROUTE_LEVEL_ROUTE_MODULE);
        roleNode.setRouteNodeName(routeNodeName);
        roleNode.setContentFragment("<" + QUALIFIER_RESOLVER_CLASS_ELEMENT + ">" + OrganizationCurriculumCommitteeQualifierResolver.class.getName() + "</" + QUALIFIER_RESOLVER_CLASS_ELEMENT + ">");
        return roleNode;
    }

    protected Process getPrototypeProcess(RouteNode node, DocumentType documentType) {
        Process process = new Process();
        process.setDocumentType(documentType);
        process.setInitial(false);
        process.setInitialRouteNode(node);
        process.setName(node.getRouteNodeName());
        return process;
    }

}
