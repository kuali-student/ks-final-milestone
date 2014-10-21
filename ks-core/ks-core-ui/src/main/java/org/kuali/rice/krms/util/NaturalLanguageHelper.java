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
package org.kuali.rice.krms.util;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.krms.api.repository.NaturalLanguageTree;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.dto.PropositionEditor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * @author Kuali Student Team
 */
public class NaturalLanguageHelper {

    private RuleManagementService ruleManagementService;

    private Map<String, String> usageMap = new HashMap<String, String>();

    /**
     * This method should set the natural language only for the given proposition. It does not
     * do a recursive call through the tree structure.
     *
     * @param proposition
     * @param usageName
     */
    public void setNaturalLanguageForUsage(PropositionEditor proposition, String usageName, String namespace){
        //Setup the Proposition
        List<PropositionParameter.Builder> parameters = new ArrayList<PropositionParameter.Builder>();
        if(proposition.getParameters()!=null){  //Parameters on Compound Propositions could be null.
            for(PropositionParameterContract parameter : proposition.getParameters()){
                PropositionParameter.Builder parmBuilder = PropositionParameter.Builder.create(parameter);

                //Add the edited term.
                if(parameter.getParameterType().equals(PropositionParameterType.TERM.getCode())){
                    if(proposition.getTerm()!=null){
                        TermDefinition.Builder termBuilder = TermDefinition.Builder.create(proposition.getTerm());
                        for (Map.Entry<String, String> entry : proposition.getNlParameters().entrySet()) {
                            termBuilder.getParameters().add(TermParameterDefinition.Builder.create(null, null, entry.getKey(), entry.getValue()));
                        }
                        parmBuilder.setTermValue(termBuilder.build());
                    }
                }
                parameters.add(parmBuilder);
            }
        }

        //Create the propbuilder but don set compound editors here, we only want to get the nl for current proposition
        PropositionDefinition.Builder propBuilder = PropositionDefinition.Builder.create(proposition.getId(),
                proposition.getPropositionTypeCode(), proposition.getRuleId(), proposition.getTypeId(), parameters);

        //Build the tree.
        TreeIterator nlTree = this.buildNaturalLanguageTree(propBuilder, this.getNaturalLanguageUsageId(usageName, namespace));
        this.setTranslatedNaturalLanguage(proposition, usageName, nlTree);
    }

    /**
     * This method sets the natural language descriptions for the given proposition and all its child propositions as
     * well.
     *
     * @param proposition
     * @param usageName
     */
    public void setNaturalLanguageTreeForUsage(PropositionEditor proposition, String usageName, String namespace){
        if(proposition==null){
            return;
        }

        PropositionDefinition.Builder propBuilder = PropositionDefinition.Builder.create(proposition);
        TreeIterator nlTree = this.buildNaturalLanguageTree(propBuilder, this.getNaturalLanguageUsageId(usageName, namespace));
        this.setTranslatedNaturalLanguage(proposition, usageName, nlTree);
    }

    private TreeIterator buildNaturalLanguageTree(PropositionDefinition.Builder propBuilder, String usageId){
        NaturalLanguageTree nlTree = this.getRuleManagementService().translateNaturalLanguageTreeForProposition(usageId, propBuilder.build(), "en");
        return new TreeIterator(nlTree);
    }

    private void setTranslatedNaturalLanguage(PropositionEditor proposition, String usageName, TreeIterator nlDescriptions){
        if(!nlDescriptions.hasNext()){
            return;
        }

        proposition.setNaturalLanguageForUsage(usageName, nlDescriptions.next());

        if (proposition.getCompoundEditors() != null){
            for (PropositionEditor child : proposition.getCompoundEditors()){
                setTranslatedNaturalLanguage(child, usageName, nlDescriptions);
            }
        }
    }

    private String getNaturalLanguageUsageId(String usageName, String namespace){
        //usageName cannot be null or blank
        if ((usageName == null) || (StringUtils.isBlank(usageName))){
            return null;
        }

        //Retrieve usageId form map.
        String usageId = usageMap.get(usageName);
        if (usageId == null){

            //Retrieve usage from service if usageId not on map.
            NaturalLanguageUsage usage = this.getRuleManagementService().getNaturalLanguageUsageByNameAndNamespace(usageName,
                    namespace);

            //Set the usage id to the map.
            if (usage != null){
                usageId = usage.getId();
                usageMap.put(usageName, usageId);
            }
        }
        return usageId;
    }

    public RuleManagementService getRuleManagementService() {
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }

    /**
     * Put the natural language descriptions in a queue and retrieve them in
     * the same order as the propostion tree.
     */
    protected class TreeIterator implements Serializable, Iterator<String> {

        private Queue<String> nl;

        public TreeIterator(NaturalLanguageTree tree){
            nl = new LinkedList<String>();
            this.addToStack(tree);
        }

        private void addToStack(NaturalLanguageTree tree){
            if (tree == null){
                return;
            }

            nl.offer(tree.getNaturalLanguage());
            if (tree.getChildren() != null){
                for (NaturalLanguageTree child : tree.getChildren()){
                    addToStack(child);
                }
            }
        }

        @Override
        public boolean hasNext() {
            return !nl.isEmpty();
        }

        @Override
        public String next() {
            return nl.poll();
        }

        @Override
        public void remove() {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }
}
