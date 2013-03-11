package org.kuali.rice.krms.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.container.TabGroup;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.util.KRMSPropertyConstants;
import org.kuali.rice.krms.impl.util.KrmsImplConstants;
import org.kuali.student.enrollment.class1.krms.dto.KrmsSuggestDisplay;
import org.kuali.student.enrollment.class1.krms.dto.PropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.RuleEditor;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RuleEditTreeBuilder;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.common.util.constants.OrganizationServiceConstants;
import org.kuali.student.r2.core.organization.service.OrganizationService;
import org.kuali.student.r2.core.search.dto.SearchParamInfo;
import org.kuali.student.r2.core.search.dto.SearchRequestInfo;
import org.kuali.student.r2.core.search.dto.SearchResultCellInfo;
import org.kuali.student.r2.core.search.dto.SearchResultInfo;
import org.kuali.student.r2.core.search.dto.SearchResultRowInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/04
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class RuleViewHelperServiceImpl extends KSViewHelperServiceImpl implements RuleViewHelperService {

    private CluService cluService;
    private ContextInfo contextInfo;
    private OrganizationService organizationService;
    private RuleManagementService ruleManagementService;

    private RuleCompareTreeBuilder compareTreeBuilder;
    private RuleEditTreeBuilder editTreeBuilder;
    private RulePreviewTreeBuilder previewTreeBuilder;

    private static TemplateRegistry templateRegistry;

    @Override
    public void performInitialization(View view, Object model) {

        if (model instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
            RuleEditor ruleEditor = (RuleEditor) maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();

            //Set the editTree and preview tree on the ruleeditor wrapper
            this.refreshInitTrees(ruleEditor);
            this.setLogicSection(ruleEditor);

            //Initialize the compare tree
            ruleEditor.setCompareTree(this.buildCompareTree(null));
        }
        super.performInitialization(view, model);

    }

    @Override
    public TemplateInfo getTemplateForType(String type) {
        return this.getTemplateRegistry().getTemplateForType(type);
    }

    @Override
    protected void addCustomContainerComponents(View view, Object model, Container container) {
        if ("KS-PropositionEdit-DetailSection".equals(container.getId())) {

            //Retrieve the current editing proposition if exists.
            MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
            RuleEditor ruleEditor = (RuleEditor) maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();
            PropositionEditor propEditor = PropositionTreeUtil.getProposition(ruleEditor);

            List<Component> components = new ArrayList<Component>();
            if (propEditor != null) {
                //Retrieve the name of the xml component to display for the proposition type.
                TemplateInfo template = this.getTemplateForType(propEditor.getType());

                if (template != null && template.getComponentId() != null) {
                    Component component = ComponentFactory.getNewComponentInstance(template.getComponentId());
                    view.assignComponentIds(component);

                    //Add Proposition Type FieldGroup to Tree Node
                    components.add(component);
                }

                if (template != null && template.getConstantComponentId() != null) {
                    Component component = ComponentFactory.getNewComponentInstance(template.getConstantComponentId());
                    view.assignComponentIds(component);

                    //Add Proposition Type FieldGroup to Tree Node
                    components.add(component);
                }
            }

            container.setItems(components);
        } else if ("KS-RuleEdit-TabSection".equals(container.getId())) {
            if (container instanceof TabGroup) {
                MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
                RuleEditor ruleEditor = (RuleEditor) maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();
                TabGroup tabGroup = (TabGroup) container;
                Map<String, String> options = tabGroup.getTabsWidget().getTemplateOptions();
                if (ruleEditor.getSelectedTab() == null) {
                    ruleEditor.setSelectedTab("0");
                }
                options.put("selected", ruleEditor.getSelectedTab());
                ruleEditor.setSelectedTab("0");
            }
        }
    }

    public List<KrmsSuggestDisplay> getCourseNamesForSuggest(String moduleName) {

        List<KrmsSuggestDisplay> displays = new ArrayList<KrmsSuggestDisplay>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo stateKeyParam = new SearchParamInfo();
        stateKeyParam.setKey("lu.queryParam.luOptionalState");

        List<String> stateValues = new ArrayList<String>();
        stateValues.add("Active");
        stateValues.add("Approved");
        stateKeyParam.setValues(stateValues);
        queryParamValueList.add(stateKeyParam);
        SearchParamInfo cluCodeParam = new SearchParamInfo();
        cluCodeParam.setKey("lu.queryParam.luOptionalCode");
        cluCodeParam.getValues().add(moduleName);
        queryParamValueList.add(cluCodeParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("lu.search.current.quick");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = null;

        try {
            clus = getCluService().search(searchRequest, getContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                KrmsSuggestDisplay display = new KrmsSuggestDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("lu.resultColumn.cluId".equals(cell.getKey())) {
                        display.setId(cell.getValue());
                    } else if ("lu.resultColumn.luOptionalCode".equals(cell.getKey())) {
                        display.setDisplayName(cell.getValue());
                    }
                }
                displays.add(display);
            }
        } catch (Exception e) {
            //do nothing
        }

        return displays;
    }

    public List<KrmsSuggestDisplay> getOrgDepartmentForSuggest(String orgName) {

        List<KrmsSuggestDisplay> displays = new ArrayList<KrmsSuggestDisplay>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo orgNameParam = new SearchParamInfo();
        orgNameParam.setKey("org.queryParam.orgOptionalLongName");
        orgNameParam.getValues().add(orgName);
        queryParamValueList.add(orgNameParam);
        SearchParamInfo orgTypeParam = new SearchParamInfo();
        orgTypeParam.setKey("org.queryParam.orgOptionalType");
        List<String> orgTypeValues = new ArrayList<String>();
        orgTypeValues.add("kuali.org.Department");
        orgTypeParam.setValues(orgTypeValues);
        queryParamValueList.add(orgTypeParam);
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("org.search.generic");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo orgs = null;

        try {
            orgs = getOrganizationService().search(searchRequest, getContextInfo());
            for (SearchResultRowInfo result : orgs.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                KrmsSuggestDisplay display = new KrmsSuggestDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("org.resultColumn.orgId".equals(cell.getKey())) {
                        display.setId(cell.getValue());
                    } else if ("org.resultColumn.orgOptionalLongName".equals(cell.getKey())) {
                        display.setDisplayName(cell.getValue());
                    }
                }
                displays.add(display);
            }
        } catch (Exception e) {
            //do nothing
        }

        return displays;
    }

    public List<KrmsSuggestDisplay> getTestNamesForSuggest(String testName) {

        List<KrmsSuggestDisplay> displays = new ArrayList<KrmsSuggestDisplay>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo testNameParam = new SearchParamInfo();
        testNameParam.setKey("cluset.queryParam.optionalName");
        testNameParam.getValues().add(testName);
        queryParamValueList.add(testNameParam);
        SearchParamInfo reusableParam = new SearchParamInfo();
        reusableParam.setKey("cluset.queryParam.optionalReusable");
        reusableParam.getValues().add(Boolean.TRUE.toString());
        queryParamValueList.add(reusableParam);
        SearchParamInfo cluSetTypeParam = new SearchParamInfo();
        cluSetTypeParam.setKey("cluset.queryParam.optionalType");
        cluSetTypeParam.getValues().add("kuali.cluSet.type.Test");
        queryParamValueList.add(cluSetTypeParam);

        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("cluset.search.generic");
        searchRequest.setParams(queryParamValueList);
        SearchResultInfo clus = null;

        try {
            clus = getCluService().search(searchRequest, getContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                KrmsSuggestDisplay display = new KrmsSuggestDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("cluset.resultColumn.cluSetId".equals(cell.getKey())) {
                        display.setId(cell.getValue());
                    } else if ("cluset.resultColumn.name".equals(cell.getKey())) {
                        display.setDisplayName(cell.getValue());
                    }
                }
                displays.add(display);
            }
        } catch (Exception e) {
            //do nothing
        }
        return displays;
    }

    public List<KrmsSuggestDisplay> getCourseSetForSuggest(String cluSetName) {
        List<KrmsSuggestDisplay> displays = new ArrayList<KrmsSuggestDisplay>();
        List<SearchParamInfo> queryParamValueList = new ArrayList<SearchParamInfo>();
        SearchParamInfo cluSetParam = new SearchParamInfo();
        cluSetParam.setKey("cluset.queryParam.optionalName");
        cluSetParam.getValues().add(cluSetName);
        queryParamValueList.add(cluSetParam);
        SearchParamInfo reusableCluSet = new SearchParamInfo();
        reusableCluSet.setKey("cluset.queryParam.optionalReusable");
        reusableCluSet.getValues().add(Boolean.TRUE.toString());
        queryParamValueList.add(reusableCluSet);
        SearchRequestInfo searchRequest = new SearchRequestInfo();
        searchRequest.setSearchKey("cluset.search.generic");
        searchRequest.setParams(queryParamValueList);
        SearchParamInfo cluSetTypeParam = new SearchParamInfo();
        cluSetTypeParam.setKey("cluset.queryParam.optionalType");
        cluSetTypeParam.getValues().add("kuali.cluSet.type.CreditCourse");
        queryParamValueList.add(cluSetTypeParam);
        SearchResultInfo clus = null;
        try {
            clus = getCluService().search(searchRequest, getContextInfo());
            for (SearchResultRowInfo result : clus.getRows()) {
                List<SearchResultCellInfo> cells = result.getCells();
                KrmsSuggestDisplay display = new KrmsSuggestDisplay();
                for (SearchResultCellInfo cell : cells) {
                    if ("cluset.resultColumn.cluSetId".equals(cell.getKey())) {
                        display.setId(cell.getValue());
                    } else if ("cluset.resultColumn.name".equals(cell.getKey())) {
                        display.setDisplayName(cell.getValue());
                    }
                }
                displays.add(display);
            }
        } catch (Exception e) {
            //do nothing
        }
        return displays;
    }

    /**
     * finds the term resolver with the fewest parameters that resolves the given term specification
     *
     * @param termSpecId the id of the term specification
     * @param namespace  the  namespace of the term specification
     * @return the simples {@link org.kuali.rice.krms.api.repository.term.TermResolverDefinition} found, or null if none was found
     */
    // public access so that AgendaEditorController can use it too
    public static TermResolverDefinition getSimplestTermResolver(String termSpecId,
                                                                 String namespace) {// Get the term resolver for the term spec

        List<TermResolverDefinition> resolvers =
                KrmsRepositoryServiceLocator.getTermBoService().findTermResolversByOutputId(termSpecId, namespace);

        TermResolverDefinition simplestResolver = null;

        for (TermResolverDefinition resolver : resolvers) {
            if (simplestResolver == null ||
                    simplestResolver.getParameterNames().size() < resolver.getParameterNames().size()) {
                simplestResolver = resolver;
            }
        }

        return simplestResolver;
    }

    public String getDescriptionForTypeId(String typeId) {

        NaturalLanguageUsage usage = this.getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KsKrmsConstants.KRMS_NL_TYPE_DESCRIPTION, PermissionServiceConstants.KS_SYS_NAMESPACE);

        NaturalLanguageTemplate template = null;
        try {
            template = this.getRuleManagementService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en", typeId, usage.getId());
        } catch (IndexOutOfBoundsException e) {
            //Ignore, rice error in NaturalLanguageTemplateBoServiceImpl line l
        }

        //TODO: Do translation.

        return template.getTemplate();
    }

    /**
     * Validate the given proposition and its children.  Note that this method is side-effecting,
     * when errors are detected with the proposition, errors are added to the error map.
     *
     * @param proposition the proposition to validate
     * @param namespace   the namespace of the parent rule
     * @return true if the proposition and its children (if any) are considered valid
     */
    // TODO also wire up to proposition for faster feedback to the user
    public boolean validateProposition(PropositionEditor proposition, String namespace) {
        boolean result = true;

        if (proposition != null) { // Null props are allowed.

            if (StringUtils.isBlank(proposition.getCompoundOpCode())) {
                // then this is a simple proposition, validate accordingly

                result &= validateSimpleProposition(proposition, namespace);

            } else {
                // this is a compound proposition (or it should be)
                List<PropositionEditor> compoundComponents = proposition.getCompoundEditors();

                if (!CollectionUtils.isEmpty(proposition.getParameters())) {
                    GlobalVariables.getMessageMap().putError(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                            "error.rule.proposition.compound.invalidParameter", proposition.getDescription());
                    result &= false;
                }

                // recurse
                if (!CollectionUtils.isEmpty(compoundComponents))
                    for (PropositionEditor childProp : compoundComponents) {
                        result &= validateProposition(childProp, namespace);
                    }
            }
        }

        return result;
    }

    /**
     * Validate the given simple proposition.  Note that this method is side-effecting,
     * when errors are detected with the proposition, errors are added to the error map.
     *
     * @param proposition the proposition to validate
     * @param namespace   the namespace of the parent rule
     * @return true if the proposition is considered valid
     */
    private boolean validateSimpleProposition(PropositionEditor proposition, String namespace) {
        boolean result = true;

        String propConstant = null;
        if (proposition.getParameters().get(1) != null) {
            propConstant = proposition.getParameters().get(1).getValue();
        }
        String operator = null;
        if (proposition.getParameters().get(2) != null) {
            operator = proposition.getParameters().get(2).getValue();
        }

        String termId = null;
        if (proposition.getParameters().get(0) != null) {
            termId = proposition.getParameters().get(0).getValue();
        }
        // Simple proposition requires all of propConstant, termId and operator to be specified
        if (StringUtils.isBlank(termId)) {
            GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                    "error.rule.proposition.simple.blankField", proposition.getDescription(), "Term");
            result &= false;
        } else {
            result = validateTerm(proposition, namespace);
        }
        if (StringUtils.isBlank(operator)) {
            GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                    "error.rule.proposition.simple.blankField", proposition.getDescription(), "Operator");
            result &= false;
        }
        if (StringUtils.isBlank(propConstant) && !operator.endsWith("null")) { // ==null and !=null operators have blank values.
            GlobalVariables.getMessageMap().putErrorForSectionId(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                    "error.rule.proposition.simple.blankField", proposition.getDescription(), "Value");
            result &= false;
        } else if (operator.endsWith("null")) { // ==null and !=null operators have blank values.
            if (propConstant != null) {
                proposition.getParameters().get(1).setValue(null);
            }
        }

        if (!CollectionUtils.isEmpty(proposition.getCompoundComponents())) {
            GlobalVariables.getMessageMap().putError(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                    "error.rule.proposition.simple.hasChildren", proposition.getDescription());
            result &= false; // simple prop should not have compound components
        }
        return result;
    }

    /**
     * Validate the term in the given simple proposition.  Note that this method is side-effecting,
     * when errors are detected with the proposition, errors are added to the error map.
     *
     * @param proposition the proposition with the term to validate
     * @param namespace   the namespace of the parent rule
     * @return true if the proposition's term is considered valid
     */
    private boolean validateTerm(PropositionEditor proposition, String namespace) {
        boolean result = true;

        String termId = proposition.getParameters().get(0).getValue();
        if (termId.startsWith(KrmsImplConstants.PARAMETERIZED_TERM_PREFIX)) {
            // validate parameterized term

            // is the term name non-blank
            if (StringUtils.isBlank(proposition.getNewTermDescription())) {
                GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                        "error.rule.proposition.simple.emptyTermName", proposition.getDescription());
                result &= false;
            }

            String termSpecificationId = termId.substring(KrmsImplConstants.PARAMETERIZED_TERM_PREFIX.length());

            TermResolverDefinition termResolverDefinition =  null;
                    //RuleViewHelperServiceImpl.getSimplestTermResolver(termSpecificationId, namespace);

            if (termResolverDefinition == null) {
                GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                        "error.rule.proposition.simple.invalidTerm", proposition.getDescription());
                result &= false;
            } else {
                List<String> parameterNames = new ArrayList<String>(termResolverDefinition.getParameterNames());
                Collections.sort(parameterNames);
                //for (String parameterName : parameterNames) {
                //if (!proposition.getTermParameters().containsKey(parameterName) ||
                //        StringUtils.isBlank(proposition.getTermParameters().get(parameterName))) {
                //    GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                //            "error.rule.proposition.simple.missingTermParameter", proposition.getDescription());
                //    result &= false;
                //    break;
                //}
                //}
            }

        } else {
            //validate normal term
            TermDefinition termDefinition = KrmsRepositoryServiceLocator.getTermBoService().getTerm(termId);
            if (termDefinition == null) {
                GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                        "error.rule.proposition.simple.invalidTerm", proposition.getDescription());
            } else if (!namespace.equals(termDefinition.getSpecification().getNamespace())) {
                GlobalVariables.getMessageMap().putErrorWithoutFullErrorPath(KRMSPropertyConstants.Rule.PROPOSITION_TREE_GROUP_ID,
                        "error.rule.proposition.simple.invalidTerm", proposition.getDescription());
            }
        }
        return result;
    }

    @Override
    public void refreshInitTrees(RuleEditor rule) {
        // Refresh the editing tree
        rule.setEditTree(this.getEditTreeBuilder().buildTree(rule));

        // Refresh the preview tree
        rule.setPreviewTree(this.getPreviewTreeBuilder().buildTree(rule));
    }

    @Override
    public Tree<CompareTreeNode, String> buildCompareTree(RuleDefinitionContract original) {

        //Get the CLU Tree.
        RuleDefinitionContract compare = this.getRuleManagementService().getRule("10063");

        //Build the Tree
        Tree<CompareTreeNode, String> compareTree = this.getCompareTreeBuilder().buildTree(original, compare);

        //Set data headers on root node.
        Node<CompareTreeNode, String> node = compareTree.getRootElement();
        if ((node.getChildren() != null) && (node.getChildren().size() > 0)) {
            Node<CompareTreeNode, String> childNode = node.getChildren().get(0);

            // Set the headers on the first root child
            if (childNode.getData() != null) {
                CompareTreeNode compareTreeNode = childNode.getData();
                compareTreeNode.setOriginal("CO Rules");
                compareTreeNode.setCompared("CLU Rules");
            }

        }

        return compareTree;
    }

    @Override
    public void setLogicSection(RuleEditor ruleEditor) {
        if (ruleEditor.getProposition() != null) {
            ruleEditor.setLogicArea(PropositionTreeUtil.configureLogicExpression((PropositionEditor) ruleEditor.getProposition()));
        }
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(QName.valueOf("ruleManagementService"));
        }
        return ruleManagementService;
    }

    public RuleCompareTreeBuilder getCompareTreeBuilder() {
        if (compareTreeBuilder == null) {
            compareTreeBuilder = new RuleCompareTreeBuilder();
            compareTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return compareTreeBuilder;
    }

    public RuleEditTreeBuilder getEditTreeBuilder() {
        if (editTreeBuilder == null) {
            editTreeBuilder = new RuleEditTreeBuilder();
            editTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return editTreeBuilder;
    }

    public RulePreviewTreeBuilder getPreviewTreeBuilder() {
        if (previewTreeBuilder == null) {
            previewTreeBuilder = new RulePreviewTreeBuilder();
            previewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return previewTreeBuilder;
    }

    private CluService getCluService() {
        if (cluService == null) {
            cluService = (CluService) GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    private ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = ContextUtils.createDefaultContextInfo();
        }
        return contextInfo;
    }

    protected OrganizationService getOrganizationService() {
        if (organizationService == null) {
            organizationService = (OrganizationService) GlobalResourceLoader.getService(new QName(OrganizationServiceConstants.NAMESPACE, OrganizationServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return organizationService;
    }

    private TemplateRegistry getTemplateRegistry() {
        if (templateRegistry == null) {
            templateRegistry = (TemplateRegistry) GlobalResourceLoader.getService(QName.valueOf("templateResolverMockService"));
        }
        return templateRegistry;
    }

}
