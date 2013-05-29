package org.kuali.rice.krms.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.AgendaTypeInfo;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.impl.util.KRMSPropertyConstants;
import org.kuali.rice.krms.impl.util.KrmsImplConstants;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RuleEditTreeBuilder;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.rice.krms.util.AgendaBuilder;
import org.kuali.rice.krms.util.NaturalLanguageHelper;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
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
    private RuleViewTreeBuilder viewTreeBuilder;
    private NaturalLanguageHelper naturalLanguageHelper;

    private static TemplateRegistry templateRegistry;

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
    public TemplateInfo getTemplateForType(String type) {
        return this.getTemplateRegistry().getTemplateForType(type);
    }

    @Override
    protected void addCustomContainerComponents(View view, Object model, Container container) {
        if ("KRMS-PropositionEdit-DetailSection".equals(container.getId())) {
            customizePropositionEditSection(view, model, container);
        }
    }

    private void customizePropositionEditSection(View view, Object model, Container container) {
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

        //Do not display if there are no components.
        if(components.size()==0){
            container.setRender(false);
        }
        else {
            container.setRender(true);
        }

        container.setItems(components);
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

        //If proposition type is null, set description and term null
        if(prop.getType() == null) {
            prop.setDescription(StringUtils.EMPTY);
            prop.setTerm(null);
            prop.getNaturalLanguage().clear();
            return prop.getDescription();
        }

        //Build the new termParamters with the matching component builder.
        if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(prop.getPropositionTypeCode())) {
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

            //Set the term specification if it doesn't exist.
            if(prop.getTerm().getSpecification()==null){
                String termSpecName = this.getTemplateRegistry().getTermSpecNameForType(prop.getType());
                prop.getTerm().setSpecification(getTermRepositoryService().getTermSpecificationByNameAndNamespace(termSpecName, KsKrmsConstants.NAMESPACE_CODE));
            }

        } else {
            prop.setTerm(null);
        }

        //Refresh the natural language.
        this.getNaturalLanguageHelper().setNaturalLanguageForUsage(prop, KsKrmsConstants.KRMS_NL_RULE_EDIT);
        this.getNaturalLanguageHelper().setNaturalLanguageForUsage(prop, KsKrmsConstants.KRMS_NL_PREVIEW);
        String description = prop.getNaturalLanguageForUsage(KsKrmsConstants.KRMS_NL_RULE_EDIT);
        prop.setDescription(StringUtils.abbreviate(description, 99));
        return prop.getDescription();
    }

    public void configurePropositionForType(PropositionEditor proposition) {

        if (proposition != null) {

            if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(proposition.getPropositionTypeCode())) {
                return;
            }

            String propositionTypeId = proposition.getTypeId();
            if (propositionTypeId == null) {
                proposition.setType(null);
                return;
            }

            KrmsTypeDefinition type = KrmsRepositoryServiceLocator.getKrmsTypeRepositoryService().getTypeById(propositionTypeId);
            if (type != null) {
                proposition.setType(type.getName());
            }

            if (proposition.getTerm() == null){
                proposition.setTerm(new TermEditor());
            }

            String termSpecName = this.getTemplateRegistry().getTermSpecNameForType(proposition.getType());
            proposition.getTerm().setSpecification(getTermRepositoryService().getTermSpecificationByNameAndNamespace(termSpecName, KsKrmsConstants.NAMESPACE_CODE));

        }
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
        }

        //Rebuild the trees
        rule.setEditTree(this.getEditTreeBuilder().buildTree(rule));
        rule.setPreviewTree(this.getPreviewTreeBuilder().buildTree(rule));

        //Also reset the logic expression. Should only be done after editTree is already built.
        if (rule.getProposition() != null) {
            rule.setLogicArea(PropositionTreeUtil.configureLogicExpression((PropositionEditor) rule.getProposition()));
        } else {
            rule.setLogicArea(StringUtils.EMPTY);
        }

    }

    @Override
    public void refreshViewTree(RuleEditor rule) {

        if (rule == null) {
            return;
        }

        //Rebuild the trees
        rule.setViewTree(this.getViewTreeBuilder().buildTree(rule));

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
    public PropositionEditor copyProposition(PropositionEditor oldProposition) {
        try {
            PropositionEditor newProposition = this.copyPropositionEditor(oldProposition);
            return (PropositionEditor) ObjectUtils.deepCopy(newProposition);
        } catch (Exception e) {
            return null;
        }
    }

    private PropositionEditor copyPropositionEditor(PropositionEditor oldProposition) {
        PropositionEditor newProposition;
        try {
            newProposition = this.getPropositionEditorClass().newInstance();
        } catch (Exception e) {
            newProposition = new PropositionEditor();
        }
        BeanUtils.copyProperties(oldProposition, newProposition, new String[]{"key","id","term","parameters"});

        if(!oldProposition.getPropositionTypeCode().equals("C")) {
            List<PropositionParameterEditor> propositionParameterEditors = new ArrayList<PropositionParameterEditor>();
            for(PropositionParameterEditor parm : oldProposition.getParameters()) {
                PropositionParameterEditor newParm = new PropositionParameterEditor();
                BeanUtils.copyProperties(parm, newParm, new String[]{"termValue","id","versionNumber"});
                propositionParameterEditors.add(newParm);
            }

            newProposition.setParameters(propositionParameterEditors);

            TermEditor termEditor = new TermEditor();
            BeanUtils.copyProperties(oldProposition.getTerm(), termEditor, new String[]{"id","versionNumber","parameters"});
            List<TermParameterEditor> termParameterEditors = new ArrayList<TermParameterEditor>();
            for(TermParameterEditor termParm : oldProposition.getTerm().getEditorParameters()) {
                TermParameterEditor newTermParm = new TermParameterEditor();
                BeanUtils.copyProperties(termParm, newTermParm, new String[]{"id","versionNumber"});
                termParameterEditors.add(newTermParm);
            }
            termEditor.setParameters(termParameterEditors);

            newProposition.setTerm(termEditor);
            this.resetDescription(newProposition);
        }

        if(newProposition.getCompoundEditors() != null) {
            List<PropositionEditor> props = new ArrayList<PropositionEditor>();
            for(PropositionEditor prop : newProposition.getCompoundEditors()) {
                props.add(this.copyPropositionEditor(prop));
            }
            newProposition.setCompoundEditors(props);
        }


        return newProposition;
    }

    @Override
    public PropositionEditor createCompoundPropositionBoStub(PropositionEditor existing, boolean addNewChild) {
        try {
            PropositionEditor compound = PropositionTreeUtil.createCompoundPropositionBoStub(existing, addNewChild, this.getPropositionEditorClass());
            PropositionTreeUtil.setTypeForCompoundOpCode(compound, LogicalOperator.AND.getCode());
            this.resetDescription(compound);
            return compound;
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

    protected RuleViewTreeBuilder getViewTreeBuilder() {
        if (viewTreeBuilder == null) {
            viewTreeBuilder = new RuleViewTreeBuilder();
            viewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return viewTreeBuilder;
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
