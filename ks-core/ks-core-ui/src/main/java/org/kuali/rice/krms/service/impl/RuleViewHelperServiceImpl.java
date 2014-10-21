/**
 * Copyright 2005-2013 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.rice.krms.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.uif.UifConstants;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.lifecycle.ViewLifecycle;
import org.kuali.rice.krad.uif.util.ComponentFactory;
import org.kuali.rice.krad.uif.util.ComponentUtils;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krad.uif.view.ViewModel;
import org.kuali.rice.krad.util.BeanPropertyComparator;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krad.web.form.MaintenanceDocumentForm;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleManagementWrapper;
import org.kuali.rice.krms.dto.RuleManager;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RuleEditTreeBuilder;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.rice.krms.util.KRMSConstants;
import org.kuali.rice.krms.util.NaturalLanguageHelper;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.service.RuleViewHelperService;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.r2.core.constants.KSKRMSServiceConstants;
import org.springframework.beans.BeanUtils;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
            } else if (dataObject instanceof RuleManager) {
                RuleManager wrapper = (RuleManager) dataObject;
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
    public void addCustomContainerComponents(ViewModel model, Container container) {
        if (KRMSConstants.KRMS_PROPOSITION_DETAILSECTION_ID.equals(container.getId())) {
            customizePropositionEditSection(model, container);
        }
    }

    private void customizePropositionEditSection(Object model, Container container) {
        //Retrieve the current editing proposition if exists.
        MaintenanceDocumentForm maintenanceDocumentForm = (MaintenanceDocumentForm) model;
        Object dataObject = maintenanceDocumentForm.getDocument().getNewMaintainableObject().getDataObject();

        RuleEditor ruleEditor = ((RuleManager) dataObject).getRuleEditor();
        PropositionEditor propEditor = PropositionTreeUtil.getProposition(ruleEditor);

        List<Component> components = new ArrayList<Component>();
        if (propEditor != null) {
            //Retrieve the name of the xml component to display for the proposition type.
            TemplateInfo template = this.getTemplateForType(propEditor.getType());
            if (template != null) {

                //Only set the objectpath on ajax component refresh.
                String objectPath = null;
                if ((maintenanceDocumentForm.getUpdateComponentId() != null) &&
                        maintenanceDocumentForm.getUpdateComponentId().startsWith(container.getId())) {
                    objectPath = ViewLifecycle.getView().getDefaultBindingObjectPath();
                }

                //Add custom component
                if (template.getComponentId() != null) {
                    components.add(createComponent(objectPath, propEditor.getBindingPath(), template.getComponentId()));
                }

                //Add constant component.
                if (template.getConstantComponentId() != null) {
                    components.add(createComponent(objectPath, propEditor.getBindingPath(), template.getConstantComponentId()));
                }
            }
        }

        //Do not display if there are no components.
        if (components.size() == 0) {
            container.getHeader().setRender(false);
        }

        container.setItems(components);
    }

    private Component createComponent(String objectPath, String bindingPath, String componentId) {
        Component component = ComponentFactory.getNewComponentInstance(componentId);
        if (objectPath != null) {
            String nodePath = objectPath + "." + bindingPath;
            ComponentUtils.pushObjectToContext(component, UifConstants.ContextVariableNames.NODE_PATH, nodePath);
        }
        return component;
    }

    /**
     * Validate the rule.
     *
     * @param rule
     * @return True if valid, false if not.
     */
    @Override
    public Boolean validateRule(RuleEditor rule) {

        boolean hasError = false;

        //Return with error message if user is currently editing a proposition.
        PropositionEditor proposition = PropositionTreeUtil.getProposition(rule);
        if ((proposition != null) && (proposition.isEditMode())) {
            GlobalVariables.getMessageMap().putErrorForSectionId(KRMSConstants.KRMS_PROPOSITION_DETAILSECTION_ID, KRMSConstants.KRMS_MSG_ERROR_RULE_PREVIEW);
            hasError = true;
        }

        return hasError;
    }

    /**
     * Validate the proposition.
     *
     * @param proposition
     * @return True if valid, false if not.
     */
    @Override
    public Boolean validateProposition(PropositionEditor proposition) {

        // Retrieve the builder for the current proposition type.
        ComponentBuilder builder = this.getTemplateRegistry().getComponentBuilderForType(proposition.getType());
        if (builder != null) {
            // Execute validation
            builder.validate(proposition);
        }

        if (GlobalVariables.getMessageMap().getErrorMessages().isEmpty()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    /**
     * Initializes the proposition, populating the type and terms.
     *
     * @param propositionEditor
     */
    @Override
    public void initPropositionEditor(PropositionEditor propositionEditor) {
        if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(propositionEditor.getPropositionTypeCode())) {

            if (propositionEditor.getType() == null) {
                KrmsTypeDefinition type = this.getKrmsTypeRepositoryService().getTypeById(propositionEditor.getTypeId());
                propositionEditor.setType(type.getName());
            }

            ComponentBuilder builder = this.getTemplateRegistry().getComponentBuilderForType(propositionEditor.getType());
            if (builder != null) {
                Map<String, String> termParameters = this.getTermParameters(propositionEditor);
                builder.resolveTermParameters(propositionEditor, termParameters);
            }
        } else {
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                initPropositionEditor(child);
            }

        }
    }

    /**
     * Finalizes the proposition, setting the type and terms.
     *
     * @param propositionEditor
     */
    @Override
    public void finPropositionEditor(PropositionEditor propositionEditor) {
        if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(propositionEditor.getPropositionTypeCode())) {

            //Set the default operation and value
            TemplateInfo template = this.getTemplateRegistry().getTemplateForType(propositionEditor.getType());
            PropositionTreeUtil.getOperatorParameter(propositionEditor.getParameters()).setValue(template.getOperator());

            if (!"n".equals(template.getValue())) {
                PropositionTreeUtil.getConstantParameter(propositionEditor.getParameters()).setValue(template.getValue());
            }

            if (propositionEditor.getTerm() != null) {
                TermDefinition.Builder termBuilder = TermDefinition.Builder.create(propositionEditor.getTerm());
                PropositionTreeUtil.getTermParameter(propositionEditor.getParameters()).setTermValue(termBuilder.build());
            }

        } else {

            //If not a simple node, recursively finalize the child proposition editors.
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                finPropositionEditor(child);
            }

        }
    }

    /**
     * Create TermEditor from the TermDefinition objects to be used in the ui and return a map of
     * the key and values of the term parameters.
     *
     * @param proposition
     * @return
     */
    @Override
    public Map<String, String> getTermParameters(PropositionEditor proposition) {

        Map<String, String> termParameters = new HashMap<String, String>();
        if (proposition.getTerm() == null) {
            PropositionParameterEditor termParameter = PropositionTreeUtil.getTermParameter(proposition.getParameters());
            if (termParameter != null) {
                proposition.setTerm(new TermEditor(termParameter.getTermValue()));
            } else {
                return termParameters;
            }
        }

        for (TermParameterEditor parameter : proposition.getTerm().getEditorParameters()) {
            termParameters.put(parameter.getName(), parameter.getValue());
        }

        return termParameters;
    }

    /**
     * Clear the description and natural language on proposition editors.
     *
     * @param prop
     * @return
     */
    @Override
    public void resetDescription(PropositionEditor prop) {

        //If proposition type is null, set description and term null
        if (prop.getType() == null) {
            prop.setDescription(StringUtils.EMPTY);
            prop.setTerm(null);
            prop.getNaturalLanguage().clear();
            return;
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
                prop.getTerm().setSpecification(getTermRepositoryService().getTermSpecificationByNameAndNamespace(termSpecName, KSKRMSServiceConstants.NAMESPACE_CODE));
            }

        } else {
            prop.setTerm(null);
        }

        //Refresh the natural language.
        prop.getNaturalLanguage().clear();
    }

    @Override
    public void buildActions(RuleEditor ruleEditor) {
    }

    public void configurePropositionForType(PropositionEditor proposition) {

        if (proposition != null) {

            if (PropositionType.COMPOUND.getCode().equalsIgnoreCase(proposition.getPropositionTypeCode())) {
                return;
            }

            String propositionTypeId = proposition.getTypeId();
            if ((propositionTypeId == null) || (propositionTypeId.isEmpty())) {
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
            proposition.getTerm().setSpecification(getTermRepositoryService().getTermSpecificationByNameAndNamespace(termSpecName, KSKRMSServiceConstants.NAMESPACE_CODE));

            ComponentBuilder builder = this.getTemplateRegistry().getComponentBuilderForType(proposition.getType());
            if (builder != null) {
                builder.initialize(proposition);
            }

        }
    }

    @Override
    public void refreshInitTrees(RuleEditor rule) {

        if (rule == null) {
            return;
        }

        //Rebuild the trees
        rule.setEditTree(this.getEditTreeBuilder().buildTree(rule));
        rule.setPreviewTree(this.getPreviewTreeBuilder().buildTree(rule));
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
    public Tree<CompareTreeNode, String> buildCompareTree(RuleEditor original, RuleEditor compare) {

        //Build the Tree
        return this.getCompareTreeBuilder().buildTree(original, compare);

    }

    @Override
    public Tree<CompareTreeNode, String> buildMultiViewTree(RuleEditor original, RuleEditor compare) {

        //Build the Tree
        return this.getCompareTreeBuilder().buildTree(original, compare);

    }

    /**
     * Compare all the propositions in a rule tree with a parent rule tree. Returns false if any proposition's type
     * or term parameters are not the same.
     * <p/>
     * Apart from the type and termparameters, all other detail is derived from the typeid and therefore not included in
     * the comparison.     *
     *
     * @param original
     * @return boolean
     * @throws Exception
     */
    @Override
    public Boolean compareRules(RuleEditor original) {

        //Do null check on propositions.
        RuleEditor compareEditor = original.getParent();
        if ((compareEditor == null) || (compareEditor.getProposition() == null)) {
            if (original.getProposition() != null) {
                return false; //if compare is null and original is not, they differ.
            } else {
                return true; //both of them are null.
            }
        } else if (original.getProposition() == null) {
            return false;
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
     * @param compare  list of term paramters to compare with.
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
     * <p/>
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
     * <p/>
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
        BeanUtils.copyProperties(oldProposition, newProposition, new String[]{"key", "id", "term", "parameters", "versionNumber"});

        if (!oldProposition.getPropositionTypeCode().equals("C")) {
            List<PropositionParameterEditor> propositionParameterEditors = new ArrayList<PropositionParameterEditor>();
            for (PropositionParameterEditor parm : oldProposition.getParameters()) {
                PropositionParameterEditor newParm = new PropositionParameterEditor();
                BeanUtils.copyProperties(parm, newParm, new String[]{"termValue", "id", "versionNumber"});
                propositionParameterEditors.add(newParm);
            }

            newProposition.setParameters(propositionParameterEditors);

            TermEditor termEditor = new TermEditor();
            List<TermParameterEditor> termParameterEditors = new ArrayList<TermParameterEditor>();
            if (oldProposition.getTerm() != null) {
                BeanUtils.copyProperties(oldProposition.getTerm(), termEditor, new String[]{"id", "versionNumber", "parameters"});
                for (TermParameterEditor termParm : oldProposition.getTerm().getEditorParameters()) {
                    TermParameterEditor newTermParm = new TermParameterEditor();
                    BeanUtils.copyProperties(termParm, newTermParm, new String[]{"id", "versionNumber"});
                    termParameterEditors.add(newTermParm);
                }
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
            this.setTypeForCompoundOpCode(compound, LogicalOperator.AND.getCode());
            return compound;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public void setTypeForCompoundOpCode(PropositionEditor proposition, String compoundOpCode) {
        //Return as quickly as possible for performance.
        if (compoundOpCode.equals(proposition.getCompoundOpCode())) {
            return;
        }

        //Clear the natural language so the the tree builder can rebuild it.
        proposition.getNaturalLanguage().clear();
        proposition.setCompoundOpCode(compoundOpCode);
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
        }
        return compareTreeBuilder;
    }

    protected RuleEditTreeBuilder getEditTreeBuilder() {
        if (editTreeBuilder == null) {
            editTreeBuilder = new RuleEditTreeBuilder();
        }
        return editTreeBuilder;
    }

    protected RulePreviewTreeBuilder getPreviewTreeBuilder() {
        if (previewTreeBuilder == null) {
            previewTreeBuilder = new RulePreviewTreeBuilder();
        }
        return previewTreeBuilder;
    }

    protected RuleViewTreeBuilder getViewTreeBuilder() {
        if (viewTreeBuilder == null) {
            viewTreeBuilder = new RuleViewTreeBuilder();
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
            templateRegistry = (TemplateRegistry) GlobalResourceLoader.getService(new QName("http://student.kuali.org/wsdl/templateResolverService", "templateResolverService"));
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
