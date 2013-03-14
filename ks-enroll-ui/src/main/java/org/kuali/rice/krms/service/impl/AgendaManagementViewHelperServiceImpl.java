package org.kuali.rice.krms.service.impl;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.core.api.util.tree.Node;
import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.uif.component.Component;
import org.kuali.rice.krad.uif.container.Container;
import org.kuali.rice.krad.uif.view.View;
import org.kuali.rice.krms.api.KrmsConstants;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeEntryDefinitionContract;
import org.kuali.rice.krms.api.repository.agenda.AgendaTreeRuleEntry;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.api.repository.term.TermSpecificationDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeRepositoryService;
import org.kuali.rice.krms.api.repository.typerelation.TypeTypeRelation;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.AgendaEditor;
import org.kuali.rice.krms.dto.AgendaTypeInfo;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.RuleTypeInfo;
import org.kuali.rice.krms.dto.TemplateInfo;
import org.kuali.rice.krms.impl.repository.KrmsRepositoryServiceLocator;
import org.kuali.rice.krms.service.AgendaManagementViewHelperService;
import org.kuali.rice.krms.service.TemplateRegistry;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.AgendaBuilder;
import org.kuali.student.enrollment.class1.krms.form.AgendaManagementForm;
import org.kuali.student.enrollment.class2.courseoffering.service.decorators.PermissionServiceConstants;
import org.kuali.student.enrollment.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.krms.naturallanguage.util.KsKrmsConstants;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AgendaManagementViewHelperServiceImpl extends KSViewHelperServiceImpl implements AgendaManagementViewHelperService {
    private static final org.apache.log4j.Logger LOG = org.apache.log4j.Logger.getLogger(AgendaManagementViewHelperServiceImpl.class);

    private transient RuleManagementService ruleManagementService;
    private transient KrmsTypeRepositoryService krmsTypeRepositoryService;

    private RuleViewTreeBuilder viewTreeBuilder;
    private Map<String, AgendaTypeInfo> typeRelationsMap;
    private transient TemplateRegistry templateRegistry;
    private RuleCompareTreeBuilder compareTreeBuilder;

    @Override
    protected void addCustomContainerComponents(View view, Object model, Container container) {
        if ("KRMS-AgendaManageRequisites-Page".equals(container.getId())) {

            AgendaBuilder builder = new AgendaBuilder(view);
            builder.setTypeRelationsMap(this.getTypeRelationsMap());
            List<Component> components = new ArrayList<Component>();

            List<AgendaTypeInfo> agendaTypeInfos = new ArrayList<AgendaTypeInfo>(typeRelationsMap.values());

            //Retrieve the current editing proposition if exists.
            List<AgendaEditor> agendas = ((AgendaManagementForm) model).getAgendas();
            for (AgendaTypeInfo agendaTypeInfo : agendaTypeInfos) {
                boolean exist = false;
                for(AgendaEditor agenda : agendas) {
                    if(agenda.getTypeId().equals(agendaTypeInfo.getId())) {
                        components.add(builder.buildAgenda(agenda));
                        exist = true;
                    }
                }
                if(!exist) {
                    AgendaEditor emptyAgenda = new AgendaEditor();
                    emptyAgenda.setTypeId(agendaTypeInfo.getId());
                    components.add(builder.buildAgenda(emptyAgenda));
                }
            }
            container.setItems(components);
            //Initialize the compare tree
            ((AgendaManagementForm) model).setCompareTree(this.buildCompareTree(null));
        }
    }

    public List<AgendaEditor> getAgendaEditors() {
        List<AgendaEditor> agendas = new ArrayList<AgendaEditor>();

        //TODO: get all agendas linked to a course offering
        agendas.add(this.getAgendaEditor("10064"));
        agendas.add(this.getAgendaEditor("10002"));

        return agendas;
    }

    private AgendaEditor getAgendaEditor(String agendaId) {
        AgendaDefinition agenda = this.getRuleManagementService().getAgenda(agendaId);
        AgendaEditor agendaEditor = new AgendaEditor(agenda);

        AgendaTreeDefinition agendaTree = this.getRuleManagementService().getAgendaTree(agendaId);
        agendaEditor.setRuleEditors(getRuleEditorsFromTree(agendaTree.getEntries()));

        return agendaEditor;
    }

    private List<RuleEditor> getRuleEditorsFromTree(List<AgendaTreeEntryDefinitionContract> agendaTreeEntries) {

        List<RuleEditor> rules = new ArrayList<RuleEditor>();
        for (AgendaTreeEntryDefinitionContract treeEntry : agendaTreeEntries) {
            if (treeEntry instanceof AgendaTreeRuleEntry) {
                AgendaTreeRuleEntry treeRuleEntry = (AgendaTreeRuleEntry) treeEntry;
                AgendaItemDefinition agendaItem = this.getRuleManagementService().getAgendaItem(treeEntry.getAgendaItemId());

                if (agendaItem.getRuleId() != null) {
                    RuleDefinition rule = this.getRuleManagementService().getRule(treeRuleEntry.getRuleId());
                    RuleEditor ruleEditor = new RuleEditor(rule);
                    ruleEditor.setPreviewTree(this.getViewTreeBuilder().buildTree(ruleEditor, true));
                    rules.add(ruleEditor);
                }

                if (treeRuleEntry.getIfTrue() != null) {
                    rules.addAll(getRuleEditorsFromTree(treeRuleEntry.getIfTrue().getEntries()));
                }
            }

            // TODO: Check for sub agendas, not required for course offering.
        }

        return rules;
    }

    protected void initPropositionEditor(PropositionEditor propositionEditor) {
        if (PropositionType.SIMPLE.getCode().equalsIgnoreCase(propositionEditor.getPropositionTypeCode())) {

           ComponentBuilder builder = this.getTemplateRegistry().getComponentBuilderForType(propositionEditor.getType());
            if (builder != null) {
                this.resolveTermParameters(propositionEditor, builder);
            }
        } else {
            for (PropositionEditor child : propositionEditor.getCompoundEditors()) {
                initPropositionEditor(child);
            }

        }
    }

    protected void resolveTermParameters(PropositionEditor proposition, ComponentBuilder builder) {

        if (proposition.getTerm() == null) {
            if (proposition.getParameters().get(0) != null) {
                String termId = proposition.getParameters().get(0).getValue();
                proposition.setTerm(KrmsRepositoryServiceLocator.getTermBoService().getTerm(termId));
            } else {
                return;
            }
        }

        Map<String, String> termParameters = new HashMap<String, String>();
        for (TermParameterDefinition parameter : proposition.getTerm().getParameters()) {
            termParameters.put(parameter.getName(), parameter.getValue());
        }

        builder.resolveTermParameters(proposition, termParameters);
    }

    /**
     * Setup a map with all the type information required to build an agenda management page.
     * @return
     */
    private Map<String, AgendaTypeInfo> getTypeRelationsMap(){
        if (typeRelationsMap == null){
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
            for (TypeTypeRelation agendaRelationship : agendaRelationships){
                AgendaTypeInfo agendaTypeInfo = new AgendaTypeInfo();
                agendaTypeInfo.setId(agendaRelationship.getToTypeId());
                agendaTypeInfo.setDescription(this.getDescriptionForTypeAndUsage(agendaRelationship.getToTypeId(), descriptionUsageId));

                // Get all rule types for each agenda type
                List<TypeTypeRelation> ruleRelationships = this.getKrmsTypeRepositoryService().findTypeTypeRelationsByFromType(agendaRelationship.getToTypeId());
                List<RuleTypeInfo> ruleTypes = new ArrayList<RuleTypeInfo>();
                for (TypeTypeRelation ruleRelationship : ruleRelationships){
                    RuleTypeInfo ruleTypeInfo = new RuleTypeInfo();
                    ruleTypeInfo.setId(ruleRelationship.getToTypeId());
                    ruleTypeInfo.setDescription(this.getDescriptionForTypeAndUsage(ruleRelationship.getToTypeId(), descriptionUsageId));
                    if(ruleTypeInfo.getDescription().isEmpty()) {
                        ruleTypeInfo.setDescription("Description is unset rule type");
                    }
                    ruleTypeInfo.setInstruction(this.getDescriptionForTypeAndUsage(ruleRelationship.getToTypeId(), instructionUsageId));
                    if(ruleTypeInfo.getInstruction().isEmpty()) {
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

    private String getDescriptionForTypeAndUsage(String typeId, String usageId){
        NaturalLanguageTemplate template = null;
        try{
            template = getRuleManagementService().findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId("en", typeId, usageId);
            return template.getTemplate();
        }catch (Exception e){
            return StringUtils.EMPTY;
        }
    }

    private RuleViewTreeBuilder getViewTreeBuilder() {
        if (viewTreeBuilder == null) {
            viewTreeBuilder = new RuleViewTreeBuilder();
            viewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return viewTreeBuilder;
    }

    public RuleManagementService getRuleManagementService() {
        if (ruleManagementService == null) {
            ruleManagementService = (RuleManagementService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "ruleManagementService"));
        }
        return ruleManagementService;
    }

    public KrmsTypeRepositoryService getKrmsTypeRepositoryService() {
        if (krmsTypeRepositoryService == null) {
            krmsTypeRepositoryService = (KrmsTypeRepositoryService) GlobalResourceLoader.getService(new QName(KrmsConstants.Namespaces.KRMS_NAMESPACE_2_0, "krmsTypeRepositoryService"));
        }
        return krmsTypeRepositoryService;
    }

    private TemplateRegistry getTemplateRegistry() {
        if (templateRegistry == null) {
            templateRegistry = (TemplateRegistry) GlobalResourceLoader.getService(QName.valueOf("templateResolverMockService"));
        }
        return templateRegistry;
    }

    public RuleCompareTreeBuilder getCompareTreeBuilder() {
        if (compareTreeBuilder == null) {
            compareTreeBuilder = new RuleCompareTreeBuilder();
            compareTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return compareTreeBuilder;
    }
}
