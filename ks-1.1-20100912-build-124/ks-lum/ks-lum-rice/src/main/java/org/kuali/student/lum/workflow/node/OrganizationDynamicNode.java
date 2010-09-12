/**
 * 
 */
package org.kuali.student.lum.workflow.node;

import org.apache.commons.lang.StringUtils;
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
import org.kuali.rice.kew.engine.node.RouteNodeConfigParam;
import org.kuali.rice.kew.engine.node.RouteNodeInstance;
import org.kuali.rice.kew.exception.WorkflowException;
import org.kuali.rice.kew.role.RoleRouteModule;
import org.kuali.rice.kew.service.KEWServiceLocator;
import org.kuali.rice.kew.util.KEWConstants;
import org.kuali.rice.student.bo.KualiStudentKimAttributes;
import org.kuali.student.lum.workflow.qualifierresolver.AbstractCocOrgQualifierResolver;
import org.kuali.student.lum.workflow.qualifierresolver.OrganizationCommitteeQualifierResolver;

/**
 * A Dynamic Node implementation that will use the KS Organization Hierarchy to
 * dynamically generate route paths based on the organizations sent to Workflow
 * for each document
 * 
 */
public class OrganizationDynamicNode implements DynamicNode {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(OrganizationDynamicNode.class);

	/**
	 * The following 4 properties should match the RoleRouteModule constants
	 * which are currently set to 'protected'
	 * 
	 * Created https://jira.kuali.org/browse/KULRICE-4448 to track change to Rice
	 */
	public static final String QUALIFIER_RESOLVER_ELEMENT = "qualifierResolver";
	public static final String QUALIFIER_RESOLVER_CLASS_ELEMENT = "qualifierResolverClass";
	public static final String RESPONSIBILITY_TEMPLATE_NAME_ELEMENT = "responsibilityTemplateName";
	public static final String NAMESPACE_ELEMENT = "namespace";

    /* (non-Javadoc)
	 * @see org.kuali.rice.kew.engine.node.DynamicNode#transitioningInto(org.kuali.rice.kew.engine.RouteContext, org.kuali.rice.kew.engine.node.RouteNodeInstance, org.kuali.rice.kew.engine.RouteHelper)
	 */
	@Override
	public DynamicResult transitioningInto(RouteContext context, RouteNodeInstance dynamicNodeInstance, RouteHelper helper) throws Exception {
        LOG.info("Entering transitioningInto");
        DocumentType docType = setUpDocumentType(context.getDocument().getDocumentType(), dynamicNodeInstance);
        RouteNode roleNodePrototype = docType.getNamedProcess(ROLE_NODE_PROTOTYPE).getInitialRouteNode();
        if (roleNodePrototype == null) {
            throw new WorkflowException("Couldn't locate node for name: " + ROLE_NODE_PROTOTYPE);
        }

//        //set up initial node instance
//        RouteNode initialRouteNode = docType.getNamedProcess(ROLE_NODE_PROTOTYPE).getInitialRouteNode();
//        RouteNodeInstance initialNodeInstance = helper.getNodeFactory().createRouteNodeInstance(context.getDocument().getRouteHeaderId(), initialRouteNode);
//        initialNodeInstance.setBranch(dynamicNodeInstance.getBranch());

        RouteNodeInstance nodeInstance = generateNextNodeInstance(RouteNodes.values()[0], roleNodePrototype, context, dynamicNodeInstance.getBranch(), helper);
        LOG.info("Exiting transitioningInto with " + ((nodeInstance == null) ? "no" : "a") + " valid next node instance");
        return new DynamicResult(false, nodeInstance);
	}

	/* (non-Javadoc)
	 * @see org.kuali.rice.kew.engine.node.DynamicNode#transitioningOutOf(org.kuali.rice.kew.engine.RouteContext, org.kuali.rice.kew.engine.RouteHelper)
	 */
	@Override
	public DynamicResult transitioningOutOf(RouteContext context, RouteHelper helper) throws Exception {
        LOG.info("Variables for transitioningOutOf");
        RouteNodeInstance processInstance = context.getNodeInstance().getProcess();
        RouteNodeInstance currentNode = context.getNodeInstance();

        String currentProcessNodeName = processInstance.getName();
        LOG.info("currentProcessNodeName = '" + currentProcessNodeName + "'");
        NodeState currentProcessOrgTypeCode = processInstance.getNodeState(NODE_STATE_ORG_TYPE_CODE);
        String currentProcessOrgTypeCodeName = (currentProcessOrgTypeCode != null) ? currentProcessOrgTypeCode.getValue() : null;
        LOG.info("currentProcessOrgTypeCodeName = '" + currentProcessOrgTypeCodeName + "'");
        String currentNodeName = currentNode.getName();
        LOG.info("currentNodeName = '" + currentNodeName + "'");
        NodeState currentOrgTypeCode = currentNode.getNodeState(NODE_STATE_ORG_TYPE_CODE);
        LOG.info("currentOrgTypeCode is " + ((currentOrgTypeCode != null) ? "not " : "") + "null");
        String currentOrgTypeCodeName = (currentOrgTypeCode != null) ? currentOrgTypeCode.getValue() : null;
        LOG.info("currentOrgTypeCodeName = '" + currentOrgTypeCodeName + "'");

        RouteNodes nextRouteNode = RouteNodes.getNextRouteNode(currentOrgTypeCodeName);

        DynamicResult result = null;
        if (nextRouteNode == null) {
            LOG.info("nextRouteNode is null");
        	result = new DynamicResult(true, null);
        }
        else {
            LOG.info("nextRouteNode is not null");
            LOG.info("nextRouteNode name is '" + nextRouteNode.getNodeName() + "'");
        	processInstance.removeNodeState(NODE_STATE_ORG_TYPE_CODE);
        	processInstance.addNodeState(new NodeState(NODE_STATE_ORG_TYPE_CODE,nextRouteNode.getOrganizationTypeCode()));
        	RouteNodeInstance nodeInstance = generateNextNodeInstance(nextRouteNode,context,processInstance.getBranch(),helper);
        	nodeInstance.addNodeState(new NodeState(NODE_STATE_ORG_TYPE_CODE, nextRouteNode.getOrganizationTypeCode()));
        	result = new DynamicResult(false, nodeInstance);
        }
        return result;

//        Map<Long, RouteNodeInstance> requestNodeMap = new HashMap<Long, RouteNodeInstance>();
//        List<RouteNodeInstance> nodeInstances = KEWServiceLocator.getRouteNodeService().getFlattenedNodeInstances(context.getDocument(), true);
//        for (RouteNodeInstance nodeInstance: nodeInstances) {
//            requestNodeMap.put(nodeInstance.getRouteNodeInstanceId(), nodeInstance);
//        }
//        findStopRequestNodes(provider, context, stopRequestNodeMap);//SpringServiceLocator.getRouteNodeService().findProcessNodeInstances(processInstance);
//        
//        Stop stop = provider.getStop(currentNode);
//
//        if (provider.isRoot(stop)) {
//            return new DynamicResult(true, null);
//        }        
//        
//        //create a join node for the next node and attach any sibling orgs to the join
//        //if no join node is necessary i.e. no siblings create a requests node
//        InnerTransitionResult transition = canTransitionFrom(provider, stop, stopRequestNodeMap.values(), helper);
//        DynamicResult result = null;
//        if (transition.isCanTransition()) {
//            DocumentType documentType = context.getDocument().getDocumentType();
//            // make a simple requests node
//            RouteNodeInstance requestNode = createNextStopRequestNodeInstance(provider, context, stop, processInstance, helper);
//
//            if (transition.getSiblings().isEmpty()) {
//                result = new DynamicResult(false, requestNode);
//            } else {
//                /* join stuff not working
//
//                //create a join to transition us to the next org
//                RouteNode joinPrototype = documentType.getNamedProcess(JOIN_PROCESS_NAME).getInitialRouteNode();
//                RouteNodeInstance joinNode = helper.getNodeFactory().createRouteNodeInstance(context.getDocument().getRouteHeaderId(), joinPrototype);
//                LOG.debug("Created join node: " + joinNode);
//                String branchName = "Branch for join " + provider.getStopIdentifier(stop);
//                Branch joinBranch = helper.getNodeFactory().createBranch(branchName, null, joinNode);
//                LOG.debug("Created branch for join node: " + joinBranch);
//                joinNode.setBranch(joinBranch);
//
//                for (RouteNodeInstance sibling: transition.getSiblings()) {
//                    LOG.debug("Adding expected joiner: " + sibling.getRouteNodeInstanceId() + " " + provider.getStop(sibling));
//                    helper.getJoinEngine().addExpectedJoiner(joinNode, sibling.getBranch());
//                }
//
//                ///XXX: can't get stop from node that hasn't been saved yet maybe...need to follow up on this...comes back as 'root'
//                LOG.debug("Adding as stop after join: " + requestNode.getRouteNodeInstanceId() + " " + provider.getStop(requestNode));
//                //set the next org after the join
//                joinNode.addNextNodeInstance(requestNode);
//
//                result = new DynamicResult(false, joinNode);
//                
//                */
//            }
//
//        } else {
//            result = new DynamicResult(false, null);
//        }
//        result.getNextNodeInstances().addAll(getNewlyAddedOrgRouteInstances(provider, context, helper));
//        return result;
	}

/*
 *   MAY NOT BE NEEDED 
 */

    /**
     * Creates a Org Request RouteNodeInstance that is a child of the passed in split.  This is used to create the initial 
     * request RouteNodeInstances off the begining split.
     * @param org
     * @param splitNodeInstance
     * @param processInstance
     * @param requestsNode
     * @return Request RouteNodeInstance bound to the passed in split as a 'nextNodeInstance'
     */
//    private RouteNodeInstance createInitialRequestNodeInstance(RouteNodeInstance splitNodeInstance, RouteNodeInstance processInstance, RouteNode requestsNode) {
//        String branchName = "Branch temp";
//        RouteNodeInstance orgRequestInstance = SplitTransitionEngine.createSplitChild(branchName, requestsNode, splitNodeInstance);
//        splitNodeInstance.addNextNodeInstance(orgRequestInstance);
//        orgRequestInstance.addNodeState(new NodeState(STOP_ID, provider.getStopIdentifier(stop)));
//        provider.setStop(orgRequestInstance, stop);
//        addStopToProcessState(provider, processInstance, stop);
//        return orgRequestInstance;
//    }

    private RouteNodeInstance generateNextNodeInstance(RouteNodes nextRouteNode, RouteContext context, Branch branch, RouteHelper helper) {
    	return generateNextNodeInstance(nextRouteNode, helper.getNodeFactory().getRouteNode(context, ROLE_NODE_PROTOTYPE), context, branch, helper);
    }

    private RouteNodeInstance generateNextNodeInstance(RouteNodes nextRouteNode, RouteNode routeNodeDefinition, RouteContext context, Branch branch, RouteHelper helper) {
        LOG.info("Adding new node with name '" + nextRouteNode.getNodeName() + "'");
        RouteNodeInstance actualRouteNodeInstance = helper.getNodeFactory().createRouteNodeInstance(context.getDocument().getRouteHeaderId(), routeNodeDefinition);
        actualRouteNodeInstance.setBranch(branch);
		actualRouteNodeInstance.addNodeState(new NodeState(NODE_STATE_ORG_TYPE_CODE, nextRouteNode.getOrganizationTypeCode()));
		actualRouteNodeInstance.addNodeState(new NodeState(NODE_STATE_ORG_QUAL_SHORT_NAME_KEY, nextRouteNode.getOrgShortNameKey()));
		actualRouteNodeInstance.addNodeState(new NodeState(NODE_STATE_ORG_QUAL_ID_KEY, nextRouteNode.getOrgIdKey()));
//        LOG.debug("Stop set on request node: " + provider.getStop(requestNode));
        return actualRouteNodeInstance;
    }

//    protected static final String SPLIT_PROCESS_NAME = "Hierarchy Split";
//    protected static final String JOIN_PROCESS_NAME = "Hierarchy Join";
    protected static final String ORG_HIERARCHY_NODE = "Org Hierarchy Node";
    protected static final String ROLE_NODE_PROTOTYPE = "Department Review";
//    protected static final String NO_STOP_NAME = "No stop";

    /**
     * Make the 'floating' split, join and request RouteNodes that will be independent processes. These are the prototypes from which our RouteNodeInstance will belong
     * 
     * @param documentType
     * @param dynamicNodeInstance
     */
    private DocumentType setUpDocumentType(DocumentType documentType, RouteNodeInstance dynamicNodeInstance) {
        boolean altered = false;
        if (documentType.getNamedProcess(ROLE_NODE_PROTOTYPE) == null) {
            RouteNode requestsNode = getRoleNode(dynamicNodeInstance);
            documentType.addProcess(getPrototypeProcess(requestsNode, documentType));
            altered = true;
        }
        if (altered) {
                //side step normal version etc. because it's a pain.
            KEWServiceLocator.getDocumentTypeService().save(documentType);
        }
        return KEWServiceLocator.getDocumentTypeService().findByName(documentType.getName());
    }

    private RouteNode getRoleNode(RouteNodeInstance dynamicNodeInstance) {
        RouteNode roleNode = new RouteNode();
//        RouteNode dynamicNode = dynamicNodeInstance.getRouteNode();
//        roleNode.setActivationType(dynamicNode.getActivationType());
//        roleNode.setDocumentType(dynamicNode.getDocumentType());
//        roleNode.setFinalApprovalInd(dynamicNode.getFinalApprovalInd());
//        roleNode.setExceptionWorkgroupId(dynamicNode.getExceptionWorkgroupId());
//        roleNode.setMandatoryRouteInd(dynamicNode.getMandatoryRouteInd());
        roleNode.setFinalApprovalInd(Boolean.FALSE);
        roleNode.setMandatoryRouteInd(Boolean.FALSE);
        roleNode.setActivationType(KEWConstants.ROUTE_LEVEL_PARALLEL);
        roleNode.setDocumentType(dynamicNodeInstance.getRouteNode().getDocumentType());
        roleNode.setNodeType(RoleNode.class.getName());
        roleNode.setRouteMethodName(RoleRouteModule.class.getName());
        roleNode.setRouteMethodCode(KEWConstants.ROUTE_LEVEL_ROUTE_MODULE);
        roleNode.setRouteNodeName(ROLE_NODE_PROTOTYPE);
//        roleNode.getConfigParams().add(new RouteNodeConfigParam(roleNode, QUALIFIER_RESOLVER_CLASS_ELEMENT, OrganizationCommitteeQualifierResolver.class.getName()));
        roleNode.setContentFragment("<" + QUALIFIER_RESOLVER_CLASS_ELEMENT + ">" + OrganizationCommitteeQualifierResolver.class.getName() + "</" + QUALIFIER_RESOLVER_CLASS_ELEMENT + ">");
        return roleNode;
    }

    /**
     * Places a Process on the documentType wrapping the node and setting the node as the process's initalRouteNode
     * 
     * @param node
     * @param documentType
     * @return Process wrapping the node passed in
     */
    protected Process getPrototypeProcess(RouteNode node, DocumentType documentType) {
        Process process = new Process();
        process.setDocumentType(documentType);
        process.setInitial(false);
        process.setInitialRouteNode(node);
        process.setName(node.getRouteNodeName());
        return process;
    }

    protected RouteNode getRoleNodePrototype(DocumentType documentType) {
        return documentType.getNamedProcess(ROLE_NODE_PROTOTYPE).getInitialRouteNode();
    }

/*
 *   WILL NOT BE NEEDED 
 */

	public static final String NODE_STATE_ORG_TYPE_CODE = "org.type.code";
	public static final String NODE_STATE_ORG_QUAL_SHORT_NAME_KEY = "org.qual.short.name.key";
	public static final String NODE_STATE_ORG_QUAL_ID_KEY = "org.qual.id.key";

	public enum RouteNodes {
		DEPT("Department Review", "org.kuali.student.lum.workflow.qualifierresolver.DepartmentCommitteeQualifierResolver", AbstractCocOrgQualifierResolver.KUALI_ORG_DEPARTMENT, KualiStudentKimAttributes.QUALIFICATION_DEPARTMENT, KualiStudentKimAttributes.QUALIFICATION_DEPARTMENT_ID),
		DIV("Division Review", "org.kuali.student.lum.workflow.qualifierresolver.DivisionCommitteeQualifierResolver", AbstractCocOrgQualifierResolver.KUALI_ORG_DIVISION, KualiStudentKimAttributes.QUALIFICATION_DIVISION, KualiStudentKimAttributes.QUALIFICATION_DIVISION_ID),
		COLLEGE("College Review", "org.kuali.student.lum.workflow.qualifierresolver.CollegeCommitteeQualifierResolver", AbstractCocOrgQualifierResolver.KUALI_ORG_COLLEGE, KualiStudentKimAttributes.QUALIFICATION_COLLEGE, KualiStudentKimAttributes.QUALIFICATION_COLLEGE_ID);

		private String nodeName;
		private String qualifierResolverClass;
		private String organizationTypeCode;
		private String orgShortNameKey;
		private String orgIdKey;
		
		RouteNodes(String nodeName, String qualifierResolverClass,
				String organizationTypeCode, String orgShortNameKey,
				String orgIdKey) {
			this.nodeName = nodeName;
			this.qualifierResolverClass = qualifierResolverClass;
			this.organizationTypeCode = organizationTypeCode;
			this.orgShortNameKey = orgShortNameKey;
			this.orgIdKey = orgIdKey;
		}

		public String getNodeName() {
			return nodeName;
		}

		public String getQualifierResolverClass() {
			return qualifierResolverClass;
		}

		public String getOrganizationTypeCode() {
			return organizationTypeCode;
		}

		public String getOrgShortNameKey() {
			return orgShortNameKey;
		}

		public String getOrgIdKey() {
			return orgIdKey;
		}

		public static RouteNodes getByOrganizationTypeCode(String organizationTypeCode) {
            for (RouteNodes routeNode : RouteNodes.values()) {
                if (StringUtils.equals(routeNode.getOrganizationTypeCode(), organizationTypeCode)) {
                    return routeNode;
                }
            }
            return null;
        }

        public static RouteNodes getNextRouteNode(String organizationTypeCode) {
        	RouteNodes currentRouteNode = RouteNodes.getByOrganizationTypeCode(organizationTypeCode);
            if ( (currentRouteNode != null) && ((currentRouteNode.ordinal() + 1) < RouteNodes.values().length) ) {
                return RouteNodes.values()[currentRouteNode.ordinal() + 1];
            }
            return null;
        }

	}

}
