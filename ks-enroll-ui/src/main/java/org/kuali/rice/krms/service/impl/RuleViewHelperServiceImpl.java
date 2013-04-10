package org.kuali.rice.krms.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.container.TabGroup;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.AgendaTypeInfo;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.util.KRMSPropertyConstants;
import org.kuali.rice.krms.impl.util.KrmsImplConstants;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RuleEditTreeBuilder;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.rice.krms.util.AgendaBuilder;
import org.kuali.rice.krms.util.NaturalLanguageHelper;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.uif.service.impl.KSViewHelperServiceImpl;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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

    private transient RuleManagementService ruleManagementService;
    private transient KrmsTypeRepositoryService krmsTypeRepositoryService;
    private transient TermRepositoryService termRepositoryService;

    private RuleCompareTreeBuilder compareTreeBuilder;
    private RuleEditTreeBuilder editTreeBuilder;
    private RulePreviewTreeBuilder previewTreeBuilder;
    private NaturalLanguageHelper naturalLanguageHelper;

    private static TemplateRegistry templateRegistry;
    private Map<String, AgendaTypeInfo> typeRelationsMap;

    protected RuleEditor getRuleEditor(Object model) {
        if (model instanceof MaintenanceDocumentForm) {
            MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
            Object dataObject = maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();

            if (dataObject instanceof RuleEditor) {
                return (RuleEditor) dataObject;
            } else if (dataObject instanceof RuleManagementWrapper) {
                RuleManagementWrapper wrapper = (RuleManagementWrapper) dataObject;
                return wrapper.getRuleEditor();
            }
        }
        return null;
    }

    @Override
    public String getViewTypeName() {
        return "kuali.krms.agenda.type.course";
    }

    @Override
    public TemplateInfo getTemplateForType(String type) {
        return this.getTemplateRegistry().getTemplateForType(type);
    }

    @Override
    protected void addCustomContainerComponents(View view, Object model, Container container) {
        if ("KS-PropositionEdit-DetailSection".equals(container.getId())) {

            //Retrieve the current editing proposition if exists.
            RuleEditor ruleEditor = this.getRuleEditor(model);
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
                RuleEditor ruleEditor = this.getRuleEditor(model);
                TabGroup tabGroup = (TabGroup) container;
                Map<String, String> options = tabGroup.getTabsWidget().getTemplateOptions();
                if (ruleEditor.getSelectedTab() == null) {
                    ruleEditor.setSelectedTab("0");
                }
                options.put("selected", ruleEditor.getSelectedTab());
                ruleEditor.setSelectedTab("0");
            }
        } else if ("KRMS-AgendaMaintenance-Page".equals(container.getId())) {

            AgendaBuilder builder = new AgendaBuilder(view);
            builder.setTypeRelationsMap(this.getTypeRelationsMap());
            List<Component> components = new ArrayList<Component>();

            List<AgendaTypeInfo> agendaTypeInfos = new ArrayList<AgendaTypeInfo>(typeRelationsMap.values());

            //Retrieve the current editing proposition if exists.
            MaintenanceDocumentForm document = (MaintenanceDocumentForm) model;
            RuleManagementWrapper form = (RuleManagementWrapper) document.getDocument().getNewMaintainableObject().getDataObject();

            List<AgendaEditor> agendas = form.getAgendas();
            for (AgendaTypeInfo agendaTypeInfo : agendaTypeInfos) {
                boolean exist = false;
                for (AgendaEditor agenda : agendas) {
                    if (agenda.getTypeId().equals(agendaTypeInfo.getId())) {
                        components.add(builder.buildAgenda(agenda));
                        exist = true;
                    }
                }
                if (!exist) {
                    AgendaEditor emptyAgenda = new AgendaEditor();
                    emptyAgenda.setTypeId(agendaTypeInfo.getId());
                    components.add(builder.buildAgenda(emptyAgenda));
                }
            }
            container.setItems(components);

        }
    }

    /**
     * Setup a map with all the type information required to build an agenda management page.
     *
     * @return
     */
    private Map<String, AgendaTypeInfo> getTypeRelationsMap() {
        if (typeRelationsMap == null) {
            typeRelationsMap = new HashMap<String, AgendaTypeInfo>();

            // Get Instruction Usage Id
            String instructionUsageId = getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KsKrmsConstants.KRMS_NL_TYPE_INSTRUCTION,
                    PermissionServiceConstants.KS_SYS_NAMESPACE).getId();

            // Get Description Usage Id
            String descriptionUsageId = getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(KsKrmsConstants.KRMS_NL_TYPE_DESCRIPTION,
                    PermissionServiceConstants.KS_SYS_NAMESPACE).getId();

            // Get the super type.
            KrmsTypeDefinition requisitesType = this.getKrmsTypeRepositoryService().getTypeByName(PermissionServiceConstants.KS_SYS_NAMESPACE, "kuali.krms.agenda.type.course");

            // Get all agenda types linked to super type.
            List<TypeTypeRelation> agendaRelationships = this.getKrmsTypeRepositoryService().findTypeTypeRelationsByFromType(requisitesType.getId());
            for (TypeTypeRelation agendaRelationship : agendaRelationships) {
                AgendaTypeInfo agendaTypeInfo = new AgendaTypeInfo();
                agendaTypeInfo.setId(agendaRelationship.getToTypeId());
                agendaTypeInfo.setDescription(this.getDescriptionForTypeAndUsage(agendaRelationship.getToTypeId(), descriptionUsageId));

                // Get all rule types for each agenda type
                List<TypeTypeRelation> ruleRelationships = this.getKrmsTypeRepositoryService().findTypeTypeRelationsByFromType(agendaRelationship.getToTypeId());
                List<RuleTypeInfo> ruleTypes = new ArrayList<RuleTypeInfo>();
                for (TypeTypeRelation ruleRelationship : ruleRelationships) {
                    RuleTypeInfo ruleTypeInfo = new RuleTypeInfo();
                    ruleTypeInfo.setId(ruleRelationship.getToTypeId());
                    ruleTypeInfo.setDescription(this.getDescriptionForTypeAndUsage(ruleRelationship.getToTypeId(), descriptionUsageId));
                    if (ruleTypeInfo.getDescription().isEmpty()) {
                        ruleTypeInfo.setDescription("Description is unset rule type");
                    }
                    ruleTypeInfo.setInstruction(this.getDescriptionForTypeAndUsage(ruleRelationship.getToTypeId(), instructionUsageId));
                    if (ruleTypeInfo.getInstruction().isEmpty()) {
                        ruleTypeInfo.setInstruction("Instruction is unset for rule type");
                    }
                    // Add rule types to list.
                    ruleTypes.add(ruleTypeInfo);
                }
                agendaTypeInfo.setRuleTypes(ruleTypes);
                typeRelationsMap.put(agendaRelationship.getToTypeId(), agendaTypeInfo);
            }
        }
        return typeRelationsMap;
    }

    private String getDescriptionForTypeAndUsage(String typeId, String usageId) {
        NaturalLanguageTemplate template = null;
        try {
            template = getRuleManagementService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en", typeId, usageId);
            return template.getTemplate();
        } catch (Exception e) {
            return StringUtils.EMPTY;
        }
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

    public String resetDescription(PropositionEditor prop) {

        //Build the new termParamters with the matching component builder.
        Map<String, String> termParameters = null;
        ComponentBuilder builder = this.getTemplateRegistry().getComponentBuilderForType(prop.getType());
        if (builder != null) {
            termParameters = builder.buildTermParameters(prop);
        }

        List<TermParameterEditor> parameters = new ArrayList<TermParameterEditor>();
        if (termParameters != null) {
            for (Map.Entry<String, String> entry : termParameters.entrySet()) {

                TermParameterEditor parameterEditor = null;
                if (prop.getTerm().getParameters() != null) {
                    for (TermParameterEditor parameter : prop.getTerm().getEditorParameters()) {

                        if (entry.getKey().equals(parameter.getName())) {
                            parameterEditor = parameter;
                            parameterEditor.setValue(entry.getValue());
                            break;
                        }
                    }
                }

                //Create a new parameter if not exist.
                if (parameterEditor == null) {
                    parameterEditor = new TermParameterEditor();
                    parameterEditor.setName(entry.getKey());
                    parameterEditor.setValue(entry.getValue());
                }
                parameters.add(parameterEditor);
            }
        }
        prop.getTerm().setParameters(parameters);

        this.getNaturalLanguageHelper().setNaturalLanguageForUsage(prop, KsKrmsConstants.KRMS_NL_RULE_EDIT);
        this.getNaturalLanguageHelper().setNaturalLanguageForUsage(prop, KsKrmsConstants.KRMS_NL_PREVIEW);
        prop.setDescription(prop.getNaturalLanguageForUsage(KsKrmsConstants.KRMS_NL_RULE_EDIT));
        return prop.getDescription();
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

            TermResolverDefinition termResolverDefinition = null;
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

        if (rule == null) {
            return;
        }

        // Refresh the natural language if required.
        if (rule.getProposition() != null) {
            PropositionEditor root = (PropositionEditor) rule.getProposition();
            if (!root.getNaturalLanguage().containsKey(this.getEditTreeBuilder().getNaturalLanguageUsageKey())) {
                this.getNaturalLanguageHelper().setNaturalLanguageTreeForUsage(root, this.getEditTreeBuilder().getNaturalLanguageUsageKey());
            }
            if (!root.getNaturalLanguage().containsKey(this.getPreviewTreeBuilder().getNaturalLanguageUsageKey())) {
                this.getNaturalLanguageHelper().setNaturalLanguageTreeForUsage(root, this.getPreviewTreeBuilder().getNaturalLanguageUsageKey());
            }

            //Also reset the logic expression
            rule.setLogicArea(PropositionTreeUtil.configureLogicExpression(root));
        }

        //Rebuild the trees
        rule.setEditTree(this.getEditTreeBuilder().buildTree(rule));
        rule.setPreviewTree(this.getPreviewTreeBuilder().buildTree(rule));

    }

    @Override
    public Tree<CompareTreeNode, String> buildCompareTree(RuleDefinitionContract original, String compareToRefObjectId) throws Exception {

        //Get the CLU Tree.
        RuleDefinitionContract compare = this.getRuleManagementService().getRule("10063");
        this.getNaturalLanguageHelper().setNaturalLanguageTreeForUsage((PropositionEditor) compare, this.getPreviewTreeBuilder().getNaturalLanguageUsageKey());

        //Build the Tree
        return this.getCompareTreeBuilder().buildTree(original, compare);

    }

    @Override
    public PropositionEditor copyProposition(PropositionEditor proposition) {
        try {
            return PropositionTreeUtil.copyProposition(proposition, this.getPropositionEditorClass());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PropositionEditor createCompoundPropositionBoStub(PropositionEditor existing, boolean addNewChild) {
        try {
            return PropositionTreeUtil.createCompoundPropositionBoStub(existing, addNewChild, this.getPropositionEditorClass());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public PropositionEditor createSimplePropositionBoStub(PropositionEditor sibling) {
        try {
            return PropositionTreeUtil.createSimplePropositionBoStub(sibling, this.getPropositionEditorClass());
        } catch (Exception e) {
            return null;
        }
    }

    public Class<? extends PropositionEditor> getPropositionEditorClass() {
        return PropositionEditor.class;
    }

    protected RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(QName.valueOf("ruleManagementService"));
        }
        return ruleManagementService;
    }

    protected RuleCompareTreeBuilder getCompareTreeBuilder() {
        if (compareTreeBuilder == null) {
            compareTreeBuilder = new RuleCompareTreeBuilder();
            compareTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return compareTreeBuilder;
    }

    protected RuleEditTreeBuilder getEditTreeBuilder() {
        if (editTreeBuilder == null) {
            editTreeBuilder = new RuleEditTreeBuilder();
            editTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return editTreeBuilder;
    }

    protected RulePreviewTreeBuilder getPreviewTreeBuilder() {
        if (previewTreeBuilder == null) {
            previewTreeBuilder = new RulePreviewTreeBuilder();
            previewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return previewTreeBuilder;
    }

    protected NaturalLanguageHelper getNaturalLanguageHelper() {
        if (naturalLanguageHelper == null) {
            naturalLanguageHelper = new NaturalLanguageHelper();
            naturalLanguageHelper.setRuleManagementService(this.getRuleManagementService());
        }
        return naturalLanguageHelper;
    }

    protected TemplateRegistry getTemplateRegistry() {
        if (templateRegistry == null) {
            templateRegistry = (TemplateRegistry) GlobalResourceLoader.getService(QName.valueOf("templateResolverMockService"));
        }
        return templateRegistry;
    }

    protected KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        if (krmsTypeRepositoryService == null) {
            krmsTypeRepositoryService = (KrmsTypeRepositoryService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "krmsTypeRepositoryService"));
        }
        return krmsTypeRepositoryService;
    }

    public TermRepositoryService getTermRepositoryService() {
        if (termRepositoryService == null) {
            termRepositoryService = (TermRepositoryService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "termRepositoryService"));
        }
        return termRepositoryService;
    }

}
