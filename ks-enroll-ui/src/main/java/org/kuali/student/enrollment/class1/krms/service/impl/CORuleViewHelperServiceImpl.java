package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.util.BeanPropertyComparator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinitionContract;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.dto.EnrolRuleEditor;
import org.kuali.student.enrollment.class1.krms.tree.CORuleCompareTreeBuilder;
import org.kuali.student.enrollment.class1.krms.tree.EnrolRulePreviewTreeBuilder;
import org.kuali.student.enrollment.class1.krms.tree.EnrolRuleViewTreeBuilder;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.util.ContextUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: SW
 * Date: 2012/12/04
 * Time: 11:52 AM
 * To change this template use File | Settings | File Templates.
 */
public class CORuleViewHelperServiceImpl extends EnrolRuleViewHelperServiceImpl {

    private RulePreviewTreeBuilder previewTreeBuilder;
    private RuleViewTreeBuilder viewTreeBuilder;

    @Override
    public Class<? extends PropositionEditor> getPropositionEditorClass() {
        return EnrolPropositionEditor.class;
    }

    @Override
    public Tree<CompareTreeNode, String> buildCompareTree(RuleDefinitionContract original, String compareToRefObjectId) throws Exception {

        //Set the original nl if not already exists.
        if (original.getProposition()!=null){
            PropositionEditor originalRoot = (PropositionEditor) original.getProposition();
            if (!originalRoot.getNaturalLanguage().containsKey(this.getEditTreeBuilder().getNaturalLanguageUsageKey())) {
                this.getNaturalLanguageHelper().setNaturalLanguageTreeForUsage(originalRoot, this.getEditTreeBuilder().getNaturalLanguageUsageKey());
            }
        }

        //Get the CLU Tree.
        CourseOfferingInfo courseOffering = this.getCourseOfferingService().getCourseOffering(compareToRefObjectId, ContextUtils.createDefaultContextInfo());
        CORuleCompareTreeBuilder treeBuilder = new CORuleCompareTreeBuilder();
        treeBuilder.setRuleManagementService(this.getRuleManagementService());
        RuleDefinitionContract compare = treeBuilder.getCompareRule(courseOffering.getCourseId(), original.getTypeId());

        //Build the Tree
        RuleEditor compareEditor;
        if(compare==null){
            compareEditor = new EnrolRuleEditor();
        } else {
            compareEditor = new EnrolRuleEditor(compare);
            this.initPropositionEditor((PropositionEditor) compareEditor.getProposition());
        }
        if(compareEditor.getProposition()!=null){
            PropositionEditor root = (PropositionEditor) compareEditor.getProposition();
            this.getNaturalLanguageHelper().setNaturalLanguageTreeForUsage(root, this.getEditTreeBuilder().getNaturalLanguageUsageKey());
        }
        Tree<CompareTreeNode, String> compareTree = treeBuilder.buildTree(original, compareEditor);

        return compareTree;
    }

    @Override
    public Boolean compareRules(RuleEditor original, String compareToRefObjectId) throws Exception {
        //Get the CLU Tree.
        CourseOfferingInfo courseOffering = this.getCourseOfferingService().getCourseOffering(compareToRefObjectId, ContextUtils.createDefaultContextInfo());
        CORuleCompareTreeBuilder treeBuilder = new CORuleCompareTreeBuilder();
        treeBuilder.setRuleManagementService(this.getRuleManagementService());
        RuleDefinitionContract compare = treeBuilder.getCompareRule(courseOffering.getCourseId(), original.getTypeId());

        //If no CLU Rule exists, then return true
        RuleEditor compareEditor;
        if(compare==null){
            return true;
        } else {
            compareEditor = new EnrolRuleEditor(compare);
            this.initPropositionEditor((PropositionEditor) compareEditor.getProposition());
        }

        //Compare Root Proposition Type and if the same test recursively
        if(original.getProposition().getTypeId().equals(compareEditor.getProposition().getTypeId())) {
            Boolean result = comparePropositions((EnrolPropositionEditor) original.getProposition(), (EnrolPropositionEditor) compareEditor.getProposition());
            return result;
        } else {
            return false;
        }
    }

    private Boolean comparePropositions(EnrolPropositionEditor original, EnrolPropositionEditor compare) {
        BeanPropertyComparator propertyComparator = null;
        if(compare.getPropositionTypeCode().equals("C")) {
            propertyComparator = new BeanPropertyComparator(Arrays.asList("compoundOpCode","propositionTypeCode"));
            if(propertyComparator.compare(original, compare) != 0) {
                return false;
            }
        } else {
            propertyComparator = new BeanPropertyComparator(Arrays.asList("compoundSequenceNumber","typeId","propositionTypeCode"));
            if(propertyComparator.compare(original, compare) != 0) {
                return false;
            }
            if(original.getCourseInfo() != null && compare.getCourseInfo() != null) {
                if(!original.getCourseInfo().getCode().equals(compare.getCourseInfo().getCode())) {
                    return false;
                }
            }
            if(original.getCluSet() != null && compare.getCluSet() != null) {
                if(original.getCluSet().getClus() != null && compare.getCluSet().getClus() != null) {
                    for(int index = 0; index < original.getCluSet().getClus().size(); index++) {
                        if(original.getCluSet().getClus().get(index).getCode().equals(compare.getCluSet().getClus().get(index).getCode())) {
                            return false;
                        }
                    }
                }
            }
        }

        if(original.getCompoundComponents() != null && compare.getCompoundComponents() != null) {
            if(!original.getCompoundComponents().isEmpty() && !compare.getCompoundComponents().isEmpty()) {
                if(original.getCompoundComponents().size() == compare.getCompoundComponents().size()) {
                    for(int index = 0; index < original.getCompoundComponents().size(); index++) {
                        comparePropositions((EnrolPropositionEditor) original.getCompoundComponents().get(index), (EnrolPropositionEditor) compare.getCompoundComponents().get(index));
                    }
                } else {
                    return false;
                }
            }
        }

        return true;
    }

    protected void initPropositionEditor(PropositionEditor propositionEditor) {
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

    protected Map<String, String> getTermParameters(PropositionEditor proposition) {

        Map<String, String> termParameters = new HashMap<String, String>();
        if (proposition.getTerm() == null) {
            if (proposition.getParameters().get(0) != null) {

                //TODO: this should already be on the proposition.
                String termId = proposition.getParameters().get(0).getValue();
                TermDefinition termDefinition = this.getTermRepositoryService().getTerm(termId);
                proposition.setTerm(new TermEditor(termDefinition));
            } else {
                return termParameters;
            }
        }

        for (TermParameterEditor parameter : proposition.getTerm().getEditorParameters()) {
            termParameters.put(parameter.getName(), parameter.getValue());
        }

        return termParameters;
    }

    @Override
    public RulePreviewTreeBuilder getPreviewTreeBuilder() {
        if (previewTreeBuilder == null) {
            previewTreeBuilder = new EnrolRulePreviewTreeBuilder();
            previewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return previewTreeBuilder;
    }

    protected RuleViewTreeBuilder getViewTreeBuilder() {
        if (viewTreeBuilder == null) {
            viewTreeBuilder = new EnrolRuleViewTreeBuilder();
            viewTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return viewTreeBuilder;
    }

}
