package org.kuali.rice.krms.service.impl;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.util.BeanPropertyComparator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeEntryDefinitionContract;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeRuleEntry;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.term.TermResolverDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
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
import org.kuali.rice.krms.util.NaturalLanguageHelper;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.springframework.beans.BeanUtils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Helpers Service for the Rule Pages.
 *
 * @author Kuali Student Team
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
        if (components.size() == 0) {
            container.setRender(false);
        } else {
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
        if (prop.getType() == null) {
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
            if (prop.getTerm().getSpecification() == null) {
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

            if (proposition.getTerm() == null) {
                proposition.setTerm(new TermEditor());
            }

            String termSpecName = this.getTemplateRegistry().getTermSpecNameForType(proposition.getType());
            proposition.getTerm().setSpecification(getTermRepositoryService().getTermSpecificationByNameAndNamespace(termSpecName, KsKrmsConstants.NAMESPACE_CODE));

        }
    }

    @Override
    public void refreshInitTrees(RuleEditor rule) {

        if (rule == null) {
            return;
        }

        // Refresh the natural language if required.
        if (rule.getProposition() != null) {
            PropositionEditor root = rule.getPropositionEditor();
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
            rule.setLogicArea(PropositionTreeUtil.configureLogicExpression(rule.getPropositionEditor()));
        } else {
            rule.setLogicArea(StringUtils.EMPTY);
        }

    }

    /**
     * Rebuild the tree used for the view only trees.
     *
     * @param rule
     */
    @Override
    public void refreshViewTree(RuleEditor rule) {

        if (rule == null) {
            return;
        }

        //Rebuild the trees
        rule.setViewTree(this.getViewTreeBuilder().buildTree(rule));

    }

    @Override
    public Tree<CompareTreeNode, String> buildCompareTree(RuleEditor original, String refObjectId) throws Exception {

        //Get the CLU Tree.
        RuleEditor compare = original.getParent();
        if (compare == null) {
            compare = this.getCompareRule(refObjectId, original.getTypeId());
            original.setParent(compare);
        }
        this.getNaturalLanguageHelper().setNaturalLanguageTreeForUsage(compare.getPropositionEditor(), this.getPreviewTreeBuilder().getNaturalLanguageUsageKey());

        //Build the Tree
        return this.getCompareTreeBuilder().buildTree(original, compare);

    }

    /**
     * Compare all the propositions in a rule tree with a parent rule tree. Returns false if any proposition's type
     * or term parameters are not the same.
     *
     * Apart from the type and termparameters, all other detail is derived from the typeid and therefore not included in
     * the comparison.     *
     *
     * @param original
     * @param refObjectId - the current editing ref object id.
     * @return
     * @throws Exception
     */
    @Override
    public Boolean compareRules(RuleEditor original, String refObjectId) throws Exception {

        //Get the rule to compare with.
        RuleEditor compareEditor = original.getParent();
        if (compareEditor == null) {
            compareEditor = this.getCompareRule(refObjectId, original.getTypeId());
            original.setParent(compareEditor);
        }

        //Compare Root Proposition Type and if the same test recursively
        if (original.getProposition().getTypeId().equals(compareEditor.getProposition().getTypeId())) {
            return compareProposition(original.getPropositionEditor(), compareEditor.getPropositionEditor());
        } else {
            return false;
        }
    }

    /**
     * Method to handle the proposition comparison recursively.
     *
     * @param original
     * @param compare
     * @return true if proposition are the same.
     */
    @Override
    public Boolean compareProposition(PropositionEditor original, PropositionEditor compare) {
        //Compare the proposition
        BeanPropertyComparator propositionComparator = new BeanPropertyComparator(Arrays.asList("typeId"));
        if (propositionComparator.compare(original, compare) != 0) {
            return false;
        }

        //Compare the term values
        if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(original.getPropositionTypeCode())) {
            TermEditor term = new TermEditor(PropositionTreeUtil.getTermParameter(compare.getParameters()).getTermValue());
            if (!compareTerm(original.getTerm().getEditorParameters(), term.getEditorParameters())) {
                return false;
            }
        }

        //Compare the compound propositions.
        return compareCompoundProposition(original.getCompoundEditors(), compare.getCompoundEditors());
    }

    /**
     * Compare all the keys and values of the term parameter. Returns false if any of the keys (names) or
     * values of the term paramters is not the same.
     *
     * @param original list of term parameters for current term
     * @param compare list of term paramters to compare with.
     * @return true if all names and values are the same.
     */
    @Override
    public Boolean compareTerm(List<TermParameterEditor> original, List<TermParameterEditor> compare) {

        //If the sizes doesn't match, they are not same.
        int originalSize = original == null ? 0 : original.size();
        if (originalSize != (compare == null ? 0 : compare.size())) {
            return false;
        } else if (originalSize > 0) {

            //Compare the compound propositions.
            BeanPropertyComparator termComparator = new BeanPropertyComparator(Arrays.asList("name", "value"));
            for (int index = 0; index < originalSize; index++) {
                if (termComparator.compare(original.get(index), compare.get(index)) != 0) {
                    return false;
                }
            }
        }

        return true;

    }

    /**
     * Recursively compare child propositions.
     *
     * @param original
     * @param compare
     * @return
     */
    @Override
    public Boolean compareCompoundProposition(List<PropositionEditor> original, List<PropositionEditor> compare) {

        //If the sizes doesn't match, they are not same.
        int originalSize = original == null ? 0 : original.size();
        if (originalSize != (compare == null ? 0 : compare.size())) {
            return false;
        } else if (originalSize > 0) {

            //Compare the compound propositions.
            for (int index = 0; index < originalSize; index++) {
                if (!compareProposition(original.get(index), compare.get(index))) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Make a new copy of the current proposition including the compounds.
     *
     * The deepcopy is done to make sure that create a full copy and does not only copy the references.
     *
     * @param oldProposition
     * @return
     */
    @Override
    public PropositionEditor copyProposition(PropositionEditor oldProposition) {
        try {
            PropositionEditor newProposition = this.copyPropositionEditor(oldProposition);

            //Use a deepcopy to create new references to inner objects such as string.
            return (PropositionEditor) ObjectUtils.deepCopy(newProposition);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Used when the user clicked the copy button. It creates a new copy of the proposition with all the related
     * compound propositions.
     *
     * The compound propositions is handled recursively.
     *
     * @param oldProposition
     * @return
     */
    protected PropositionEditor copyPropositionEditor(PropositionEditor oldProposition) {
        PropositionEditor newProposition;
        try {
            newProposition = this.getPropositionEditorClass().newInstance();
        } catch (Exception e) {
            newProposition = new PropositionEditor();
        }
        BeanUtils.copyProperties(oldProposition, newProposition, new String[]{"key", "id", "term", "parameters"});

        if (!oldProposition.getPropositionTypeCode().equals("C")) {
            List<PropositionParameterEditor> propositionParameterEditors = new ArrayList<PropositionParameterEditor>();
            for (PropositionParameterEditor parm : oldProposition.getParameters()) {
                PropositionParameterEditor newParm = new PropositionParameterEditor();
                BeanUtils.copyProperties(parm, newParm, new String[]{"termValue", "id", "versionNumber"});
                propositionParameterEditors.add(newParm);
            }

            newProposition.setParameters(propositionParameterEditors);

            TermEditor termEditor = new TermEditor();
            BeanUtils.copyProperties(oldProposition.getTerm(), termEditor, new String[]{"id", "versionNumber", "parameters"});
            List<TermParameterEditor> termParameterEditors = new ArrayList<TermParameterEditor>();
            for (TermParameterEditor termParm : oldProposition.getTerm().getEditorParameters()) {
                TermParameterEditor newTermParm = new TermParameterEditor();
                BeanUtils.copyProperties(termParm, newTermParm, new String[]{"id", "versionNumber"});
                termParameterEditors.add(newTermParm);
            }
            termEditor.setParameters(termParameterEditors);

            newProposition.setTerm(termEditor);
            this.resetDescription(newProposition);
        }

        if (newProposition.getCompoundEditors() != null) {
            List<PropositionEditor> props = new ArrayList<PropositionEditor>();
            for (PropositionEditor prop : newProposition.getCompoundEditors()) {
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

    /**
     * Creates a new instance of a simple proposition.
     *
     * @param sibling
     * @return
     */
    @Override
    public PropositionEditor createSimplePropositionBoStub(PropositionEditor sibling) {
        try {
            return PropositionTreeUtil.createSimplePropositionBoStub(sibling, this.getPropositionEditorClass());
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Override this method to return the reference object id of the parent object.
     *
     * @param refObjectId
     * @return
     */
    @Override
    public String getParentRefOjbectId(String refObjectId) throws Exception  {
        return null;
    }

    /**
     * Retrieves the rule of the same type from the agenda linked to the given reference object id.
     *
     * @param refObjectId
     * @param typeId
     * @return
     */
    public RuleEditor getCompareRule(String refObjectId, String typeId) throws Exception {

        String parentRefObjectId = this.getParentRefOjbectId(refObjectId);
        List<ReferenceObjectBinding> referenceObjects = this.getRuleManagementService().findReferenceObjectBindingsByReferenceObject("kuali.lu.type.CreditCourse", parentRefObjectId);

        RuleEditor compareRule = null;
        for (ReferenceObjectBinding referenceObject : referenceObjects) {
            AgendaTreeDefinition agendaTree = this.getRuleManagementService().getAgendaTree(referenceObject.getKrmsObjectId());
            compareRule = this.getRuleFromTree(agendaTree.getEntries(), typeId);

            if (compareRule != null) {
                return compareRule;
            }
        }

        return new RuleEditor();
    }

    /**
     * Retrieves the rule of the same type from the rule linked to the given reference object id.
     *
     * @param agendaTreeEntries
     * @param typeId
     * @return
     */
    private RuleEditor getRuleFromTree(List<AgendaTreeEntryDefinitionContract> agendaTreeEntries, String typeId) {

        for (AgendaTreeEntryDefinitionContract treeEntry : agendaTreeEntries) {
            if (treeEntry instanceof AgendaTreeRuleEntry) {
                AgendaTreeRuleEntry treeRuleEntry = (AgendaTreeRuleEntry) treeEntry;
                AgendaItemDefinition agendaItem = this.getRuleManagementService().getAgendaItem(treeEntry.getAgendaItemId());
                if (agendaItem.getRule().getTypeId().equals(typeId)) {
                    RuleEditor rule = new RuleEditor(agendaItem.getRule());
                    return rule;
                }

                if (treeRuleEntry.getIfTrue() != null) {
                    RuleEditor childRule = getRuleFromTree(treeRuleEntry.getIfTrue().getEntries(), typeId);
                    if (childRule != null) {
                        return childRule;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Override this method to return a different class type if you need to use a different propositoin editor class.
     *
     * @return
     */
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
