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
package org.kuali.student.enrollment.class1.krms.service.impl;

import org.kuali.rice.core.api.util.tree.Tree;
import org.kuali.rice.krad.util.BeanPropertyComparator;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinitionContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.type.KrmsTypeDefinition;
import org.kuali.rice.krms.builder.ComponentBuilder;
import org.kuali.rice.krms.dto.PropositionEditor;
import org.kuali.rice.krms.dto.PropositionParameterEditor;
import org.kuali.rice.krms.dto.RuleEditor;
import org.kuali.rice.krms.dto.TermEditor;
import org.kuali.rice.krms.dto.TermParameterEditor;
import org.kuali.rice.krms.tree.RuleCompareTreeBuilder;
import org.kuali.rice.krms.tree.RulePreviewTreeBuilder;
import org.kuali.rice.krms.tree.RuleViewTreeBuilder;
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.enrollment.class1.krms.builder.MultiCourseComponentBuilder;
import org.kuali.student.enrollment.class1.krms.dto.CluInformation;
import org.kuali.student.enrollment.class1.krms.dto.EnrolPropositionEditor;
import org.kuali.student.enrollment.class1.krms.tree.CORuleCompareTreeBuilder;
import org.kuali.student.enrollment.class1.krms.tree.EnrolRulePreviewTreeBuilder;
import org.kuali.student.enrollment.class1.krms.tree.EnrolRuleViewTreeBuilder;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.r2.common.util.ContextUtils;
import org.kuali.student.r2.lum.clu.dto.CluSetInfo;
import org.kuali.student.r2.lum.clu.dto.MembershipQueryInfo;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Kuali Student Team
 */
public class CORuleViewHelperServiceImpl extends EnrolRuleViewHelperServiceImpl {

    private RulePreviewTreeBuilder previewTreeBuilder;
    private RuleViewTreeBuilder viewTreeBuilder;
    private RuleCompareTreeBuilder compareTreeBuilder;

    /**
     *
     * @return
     */
    @Override
    public Class<? extends PropositionEditor> getPropositionEditorClass() {
        return EnrolPropositionEditor.class;
    }

    /**
     * Builds compare tree for the compare CO and CLU lightbox links.
     *
     * @param original
     * @param refObjectId
     * @return
     * @throws Exception
     */
    @Override
    public Tree<CompareTreeNode, String> buildCompareTree(RuleEditor original, String refObjectId) throws Exception {

        //Set the original nl if not already exists.
        if (original.getProposition()!=null){
            PropositionEditor originalRoot = original.getPropositionEditor();
            if (!originalRoot.getNaturalLanguage().containsKey(this.getEditTreeBuilder().getNaturalLanguageUsageKey())) {
                this.getNaturalLanguageHelper().setNaturalLanguageTreeForUsage(originalRoot, this.getEditTreeBuilder().getNaturalLanguageUsageKey());
            }
        }

        //Build the Tree
        RuleEditor compareEditor = original.getParent();
        if(compareEditor == null){
            compareEditor = this.getCompareRule(refObjectId, original.getTypeId());
            original.setParent(compareEditor);
        }
        if(compareEditor.getProposition()!=null){
            this.getNaturalLanguageHelper().setNaturalLanguageTreeForUsage(compareEditor.getPropositionEditor(), this.getEditTreeBuilder().getNaturalLanguageUsageKey());
        }
        Tree<CompareTreeNode, String> compareTree = this.getCompareTreeBuilder().buildTree(original, compareEditor);

        return compareTree;
    }

    /**
     * Compares CO and CLU with each other for the display of a info message.
     *
     * @param original
     * @param compare
     * @return
     */
    @Override
    public Boolean compareProposition(PropositionEditor original, PropositionEditor compare) {

        if(!super.compareProposition(original, compare)) {
            return false;
        } else if(!original.getPropositionTypeCode().equals("C")) {
            EnrolPropositionEditor enrolOriginal = (EnrolPropositionEditor) original;
            EnrolPropositionEditor enrolCompare = new EnrolPropositionEditor((PropositionDefinitionContract) compare);

            //Populate compare proposition cluSetInformation for comparison
            if(enrolCompare.getCluSet() == null) {
                MultiCourseComponentBuilder builder = new MultiCourseComponentBuilder();
                TermEditor term = new TermEditor(PropositionTreeUtil.getTermParameter(compare.getParameters()).getTermValue());
                for(TermParameterEditor termParameterEditor : term.getEditorParameters()) {
                    if(termParameterEditor.getName().equals("kuali.term.parameter.type.course.cluSet.id")) {
                        enrolCompare.setCluSet(builder.getCluSetInformation(termParameterEditor.getValue()));
                        break;
                    }
                }
            }

            if(enrolOriginal.getCluSet() != null && enrolCompare.getCluSet() != null) {
                //Compare clu's
                if(enrolOriginal.getCluSet().getClus() != null && enrolCompare.getCluSet().getClus() != null) {
                    if(!compareClus(enrolOriginal.getCluSet().getClus(), enrolCompare.getCluSet().getClus())) {
                        return false;
                    }
                } //Compare cluSets
                else if(enrolOriginal.getCluSet().getCluSets() != null && enrolCompare.getCluSet().getCluSets() != null) {
                    if(!compareCluSets(enrolOriginal.getCluSet().getCluSets(), enrolCompare.getCluSet().getCluSets())) {
                        return false;
                    }
                } //Compare membershipQueryInfo
                else if(enrolOriginal.getCluSet().getMembershipQueryInfo() != null && enrolCompare.getCluSet().getMembershipQueryInfo() != null) {
                    if(!compareMembershipQueryInfo(enrolOriginal.getCluSet().getMembershipQueryInfo(), enrolCompare.getCluSet().getMembershipQueryInfo())) {
                        return false;
                    }
                }
            } //If propositions cluSets differ, return false
            else if((enrolOriginal.getCluSet() == null ? false : true) != (enrolCompare.getCluSet() == null ? false : true)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Compare the propositions CluSetInformation's Clu's
     *
     * @param original
     * @param compare
     * @return
     */
    protected Boolean compareClus(List<CluInformation> original, List<CluInformation> compare) {
        //If the sizes doesn't match, they are not same.
        int originalSize = original == null ? 0 : original.size();
        if (originalSize != (compare == null ? 0 : compare.size())) {
            return false;
        } else if (originalSize > 0) {
            //Compare the cluSet clu's.
            BeanPropertyComparator cluComparator = new BeanPropertyComparator(Arrays.asList("code", "type"));
            for (int index = 0; index < originalSize; index++) {
                if (cluComparator.compare(original.get(index), compare.get(index)) != 0) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Compare the propositions CluSetInformation's CluSets
     *
     * @param original
     * @param compare
     * @return
     */
    protected Boolean compareCluSets(List<CluSetInfo> original, List<CluSetInfo> compare) {
        //If the sizes doesn't match, they are not same.
        int originalSize = original == null ? 0 : original.size();
        if (originalSize != (compare == null ? 0 : compare.size())) {
            return false;
        } else if (originalSize > 0) {
            //Compare the cluSet cluSetInfo's.
            BeanPropertyComparator cluSetComparator = new BeanPropertyComparator(Arrays.asList("membershipQuery", "adminOrg","effectiveDate", "expirationDate"));
            for (int index = 0; index < originalSize; index++) {
                if (cluSetComparator.compare(original.get(index), compare.get(index)) != 0) {
                    return false;
                }
                //Compare cluSetIds
                int originalCluSetIdSize = original.get(index).getCluSetIds() == null ? 0 : original.get(index).getCluSetIds().size();
                if (originalCluSetIdSize != (compare.get(index).getCluSetIds() == null ? 0 : compare.get(index).getCluSetIds().size())) {
                    return false;
                } else if (originalCluSetIdSize > 0) {
                    for(int i = 0; i < originalCluSetIdSize; i++) {
                        if(!original.get(index).getCluSetIds().get(i).equals(compare.get(index).getCluSetIds().get(i))) {
                            return false;
                        }
                    }

                }
                //Compare cluIds
                int originalCluIdSize = original.get(index).getCluIds() == null ? 0 : original.get(index).getCluIds().size();
                if (originalCluIdSize != (compare.get(index).getCluIds() == null ? 0 : compare.get(index).getCluIds().size())) {
                    return false;
                } else if (originalCluIdSize > 0) {
                    for(int k = 0; k < originalCluIdSize; k++) {
                        if(!original.get(index).getCluIds().get(k).equals(compare.get(index).getCluIds().get(k))) {
                            return false;
                        }
                    }

                }
            }
        }

        return true;
    }

    /**
     * Compare the propositions CluSetInformation's MembershipQueryInfo
     *
     * @param original
     * @param compare
     * @return
     */
    protected Boolean compareMembershipQueryInfo(MembershipQueryInfo original, MembershipQueryInfo compare) {
        //Compare the cluSet membershipQueryInfo
        BeanPropertyComparator mqiComparator = new BeanPropertyComparator(Arrays.asList("searchTypeKey"));
        if (mqiComparator.compare(original, compare) != 0) {
            return false;
        }
        return true;
    }

    /**
     * Initializes the proposition, populating the type and terms.
     *
     * @param propositionEditor
     */
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

    /**
     * Create TermEditor from the TermDefinition objects to be used in the ui and return a map of
     * the key and values of the term parameters.
     *
     * @param proposition
     * @return
     */
    protected Map<String, String> getTermParameters(PropositionEditor proposition) {

        Map<String, String> termParameters = new HashMap<String, String>();
        if (proposition.getTerm() == null) {
            PropositionParameterEditor termParameter = PropositionTreeUtil.getTermParameter(proposition.getParameters());
            if (termParameter != null) {
                String termId = termParameter.getValue();
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

    /**
     * Return the clu id from the canonical course that is linked to the given course offering id.
     *
     * @param refObjectId - the course offering id.
     * @return
     * @throws Exception
     */
    @Override
    public String getParentRefOjbectId(String refObjectId) throws Exception {
        CourseOfferingInfo courseOffering = this.getCourseOfferingService().getCourseOffering(refObjectId, ContextUtils.createDefaultContextInfo());
        return courseOffering.getCourseId();
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

    protected RuleCompareTreeBuilder getCompareTreeBuilder() {
        if (compareTreeBuilder == null) {
            compareTreeBuilder = new CORuleCompareTreeBuilder();
            compareTreeBuilder.setRuleManagementService(this.getRuleManagementService());
        }
        return compareTreeBuilder;
    }

}
