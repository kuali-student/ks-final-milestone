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
import org.kuali.rice.krad.util.ObjectUtils;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
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
import org.kuali.rice.krms.tree.node.CompareTreeNode;
import org.kuali.rice.krms.util.PropositionTreeUtil;
import org.kuali.student.core.krms.tree.KSRuleCompareTreeBuilder;
import org.kuali.student.lum.lu.ui.krms.dto.LUPropositionEditor;
import org.kuali.student.enrollment.class1.krms.tree.AORuleCompareTreeBuilder;
import org.kuali.student.enrollment.class1.krms.tree.AORuleViewCoCluTreeBuilder;
import org.kuali.student.lum.lu.ui.krms.service.impl.LURuleViewHelperServiceImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kuali Student Team
 */
public class AORuleViewHelperServiceImpl extends LURuleViewHelperServiceImpl {

    private AORuleCompareTreeBuilder compareTreeBuilder;
    private KSRuleCompareTreeBuilder viewCoCluTreeBuilder;

    /**
     * @return
     */
    @Override
    public Class<? extends PropositionEditor> getPropositionEditorClass() {
        return LUPropositionEditor.class;
    }

    /**
     * Builds compare tree for the compare AO and CLU lightbox links.
     *
     * @param aoRuleEditor
     * @param cluRuleEditor
     * @return
     * @throws Exception
     */
    @Override
    public Tree<CompareTreeNode, String> buildCompareTree(RuleEditor aoRuleEditor, RuleEditor cluRuleEditor) {

        //Set the original nl if not already exists.
        checkNaturalLanguageForTree(aoRuleEditor);
        checkNaturalLanguageForTree(aoRuleEditor.getParent());
        checkNaturalLanguageForTree(cluRuleEditor);

        Tree<CompareTreeNode, String> compareTree = this.getCompareTreeBuilder().buildTree(aoRuleEditor.getParent(), cluRuleEditor, aoRuleEditor);

        return compareTree;
    }

    /**
     * Builds view tree for  view CO and CLU lightbox links.
     *
     * @param coRuleEditor
     * @param cluRuleEditor
     * @return
     * @throws Exception
     */
    @Override
    public Tree<CompareTreeNode, String> buildMultiViewTree(RuleEditor coRuleEditor, RuleEditor cluRuleEditor) {

        //Set the original nl if not already exists.
        checkNaturalLanguageForTree(coRuleEditor);
        checkNaturalLanguageForTree(cluRuleEditor);

        Tree<CompareTreeNode, String> compareTree = this.getViewCoCluTreeBuilder().buildTree(coRuleEditor, cluRuleEditor);

        return compareTree;
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
     * Copy proposition from parent CO and nullify proposition parameters.
     *
     * @param coProposition
     * @return
     */
    public PropositionEditor copyCOProposition(PropositionEditor coProposition) {
        LUPropositionEditor propositionEditor = (LUPropositionEditor) super.copyProposition(coProposition);

        nullifyPropositionParameters(propositionEditor);

        return propositionEditor;
    }

    /**
     * Method to recursively set proposition's parameters propId to null to force rebuild.
     * If the proposition parameter is of type Term, null the term id out as well(value field)
     *
     * @param propositionEditor
     */
    protected void nullifyPropositionParameters(LUPropositionEditor propositionEditor) {
        //If the proposition has parameters its a simple proposition
        if(propositionEditor.getParameters()!=null){
            for(PropositionParameterEditor param : propositionEditor.getParameters()) {
                param.setPropId(null);
                if(param.getParameterType().equals(PropositionParameterType.TERM.getCode())){
                    param.setTermValue(null);
                    param.setValue(null);
                }
            }
        } else if(propositionEditor.getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
            //The proposition is a compound so do a recursive call on each of its child propositions
            for(int i = 0; i < propositionEditor.getCompoundEditors().size(); i++) {
                nullifyPropositionParameters((LUPropositionEditor) propositionEditor.getCompoundEditors().get(i));
            }
        }
    }

    @Override
    protected AORuleCompareTreeBuilder getCompareTreeBuilder() {
        if (compareTreeBuilder == null) {
            compareTreeBuilder = new AORuleCompareTreeBuilder();
            compareTreeBuilder.setNlHelper(this.getNaturalLanguageHelper());
        }
        return compareTreeBuilder;
    }

    protected RuleCompareTreeBuilder getViewCoCluTreeBuilder() {
        if (viewCoCluTreeBuilder == null) {
            viewCoCluTreeBuilder = new AORuleViewCoCluTreeBuilder();
            viewCoCluTreeBuilder.setNlHelper(this.getNaturalLanguageHelper());
        }
        return viewCoCluTreeBuilder;
    }


}
