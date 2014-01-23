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
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.rice.krms.impl.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krms.api.repository.LogicalOperator;
import org.kuali.rice.krms.api.repository.NaturalLanguageTree;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.TranslateBusinessMethods;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplaterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermParameterDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;

/**
 * @author nwright
 */
public class KSTranslationUtility implements TranslateBusinessMethods {

    private RuleManagementService ruleManagementService;
    private TermRepositoryService termRepositoryService;
    private NaturalLanguageTemplaterContract templater;

    public KSTranslationUtility(RuleManagementService ruleManagementService, TermRepositoryService termRepositoryService,
                              NaturalLanguageTemplaterContract templater) {
        this.ruleManagementService = ruleManagementService;
        this.termRepositoryService = termRepositoryService;
        this.templater = templater;
    }

    public RuleManagementService getRuleManagementService() {
        return ruleManagementService;
    }

    public void setRuleManagementService(RuleManagementService ruleManagementService) {
        this.ruleManagementService = ruleManagementService;
    }

    public NaturalLanguageTemplaterContract getTemplater() {
        return templater;
    }

    public void setTemplater(NaturalLanguageTemplaterContract templater) {
        this.templater = templater;
    }

    @Override
    public String translateNaturalLanguageForObject(String naturalLanguageUsageId, String typeId, String krmsObjectId, String languageCode)
            throws RiceIllegalArgumentException {

        Map<String, NaturalLanguageTemplate> templateMap = getNaturalLanguageTemplateMap(naturalLanguageUsageId, languageCode);

        if (typeId.equals("agenda")) {
            AgendaDefinition agenda = this.ruleManagementService.getAgenda(krmsObjectId);
            if (agenda == null) {
                throw new RiceIllegalArgumentException(krmsObjectId + " is not an Id for an agenda");
            }
            return this.translateNaturalLanguageForAgenda(agenda, templateMap);
        } else if (typeId.equals("rule")) {
            RuleDefinition rule = this.ruleManagementService.getRule(krmsObjectId);
            if (rule == null) {
                throw new RiceIllegalArgumentException(krmsObjectId + " is not an Id for a rule");
            }
            return this.translateNaturalLanguageForRule(rule, templateMap);
        } else if (typeId.equals("proposition")) {
            PropositionDefinition proposition = this.ruleManagementService.getProposition(krmsObjectId);
            if (proposition == null) {
                throw new RiceIllegalArgumentException(krmsObjectId + " is not an Id for a proposition");
            }
            return this.translateNaturalLanguageForProposition(naturalLanguageUsageId, proposition, languageCode);
        }

        return StringUtils.EMPTY;
    }

    protected String translateNaturalLanguageForAgenda(AgendaDefinition agenda, Map<String, NaturalLanguageTemplate> templateMap) throws RiceIllegalArgumentException {
        if (agenda.getFirstItemId() == null) {
            throw new RiceIllegalArgumentException("Agenda has no first item");
        }

        AgendaItemDefinition item = this.ruleManagementService.getAgendaItem(agenda.getFirstItemId());
        return translateNaturalLanguageForAgendaItem(item, templateMap);
    }

    protected String translateNaturalLanguageForAgendaItem(AgendaItemDefinition item, Map<String, NaturalLanguageTemplate> templateMap) {
        if(item==null){
            return StringUtils.EMPTY;
        }

        String naturalLanguage = StringUtils.EMPTY;
        if (item.getRuleId() != null) {
            RuleDefinition rule = this.ruleManagementService.getRule(item.getRuleId());
            naturalLanguage += this.translateNaturalLanguageForRule(rule, templateMap);
        }
        naturalLanguage += translateNaturalLanguageForAgendaItem(item.getWhenTrue(), templateMap);
        naturalLanguage += translateNaturalLanguageForAgendaItem(item.getWhenFalse(), templateMap);
        naturalLanguage += translateNaturalLanguageForAgendaItem(item.getAlways(), templateMap);
        return naturalLanguage;
    }

    protected String translateNaturalLanguageForRule(RuleDefinition rule, Map<String, NaturalLanguageTemplate> templateMap) throws RiceIllegalArgumentException {
        if(rule==null){
            return StringUtils.EMPTY;
        }

        NaturalLanguageTemplate nlTemplate = templateMap.get(rule.getTypeId());
        String naturalLanguage = nlTemplate.getTemplate() + " ";

        if(rule.getProposition()!=null){
            naturalLanguage += this.translateNaturalLanguageForProposition(rule.getProposition(), templateMap, true) + ". ";
        }

        return naturalLanguage;
    }

    @Override
    public String translateNaturalLanguageForProposition(String naturalLanguageUsageId,
                                                         PropositionDefinition proposition, String languageCode)
            throws RiceIllegalArgumentException {

        Map<String, NaturalLanguageTemplate> templateMap = getNaturalLanguageTemplateMap(naturalLanguageUsageId, languageCode);
        return translateNaturalLanguageForProposition(proposition, templateMap, true) + ". ";
    }

    private Map<String, NaturalLanguageTemplate> getNaturalLanguageTemplateMap(String naturalLanguageUsageId, String languageCode) {
        Map<String, NaturalLanguageTemplate> templateMap = new HashMap<String, NaturalLanguageTemplate>();
        List<NaturalLanguageTemplate> templates = this.ruleManagementService.findNaturalLanguageTemplatesByNaturalLanguageUsage(naturalLanguageUsageId);
        for(NaturalLanguageTemplate nlTemplate : templates){
            if(languageCode.equals(nlTemplate.getLanguageCode())){
                templateMap.put(nlTemplate.getTypeId(), nlTemplate);
            }
        }
        return templateMap;
    }

    /**
     * This method is added because from a functional point of view the root proposition is ignored when it is a group
     * and therefore handled differently.
     *
     * @param proposition
     * @param templateMap
     * @param isRoot
     * @return
     */
    private String translateNaturalLanguageForProposition(PropositionDefinition proposition, Map<String, NaturalLanguageTemplate> templateMap, boolean isRoot) {
        NaturalLanguageTemplate naturalLanguageTemplate = templateMap.get(proposition.getTypeId());

        StringBuilder naturalLanguage = new StringBuilder();
        if (proposition.getPropositionTypeCode().equals(PropositionType.SIMPLE.getCode())) {
            if(naturalLanguageTemplate!=null){
                Map<String, Object> contextMap = this.buildSimplePropositionContextMap(proposition);
                naturalLanguage.append(templater.translate(naturalLanguageTemplate, contextMap));
            }

        } else if (proposition.getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
            if(naturalLanguageTemplate!=null){
                Map<String, Object> contextMap = this.buildCompoundPropositionContextMap(proposition, templateMap);
                naturalLanguage.append(templater.translate(naturalLanguageTemplate, contextMap));
            }

            //Null check because newly created compound propositions should also be translateable.
            if(proposition.getCompoundComponents()!=null){
                String operator = getCompoundSeperator(proposition, isRoot);
                for (PropositionDefinition child : proposition.getCompoundComponents()) {
                    if(proposition.getCompoundComponents().indexOf(child)!=0){
                        naturalLanguage.append(operator);
                    }
                    naturalLanguage.append(this.translateNaturalLanguageForProposition(child, templateMap, false));
                }
            }

        } else {
            throw new RiceIllegalArgumentException("Unknown proposition type: " + proposition.getPropositionTypeCode());
        }

        return naturalLanguage.toString();
    }

    private String getCompoundSeperator(PropositionDefinition proposition, boolean isRoot) {
        String operator = getCompoundOperator(proposition);
        if (isRoot){
            return ". " + StringUtils.capitalize(operator) + " ";
        }
        return "; " + operator + " ";
    }

    private String getCompoundOperator(PropositionDefinition proposition) {
        String operator = null;
        if (LogicalOperator.AND.getCode().equalsIgnoreCase(proposition.getCompoundOpCode())) {
            operator = "and";
        } else if (LogicalOperator.OR.getCode().equalsIgnoreCase(proposition.getCompoundOpCode())) {
            operator = "or";
        }
        return operator;
    }

    @Override
    public NaturalLanguageTree translateNaturalLanguageTreeForProposition(String naturalLanguageUsageId,
                                                                          PropositionDefinition proposition,
                                                                          String languageCode) throws RiceIllegalArgumentException {

        Map<String, NaturalLanguageTemplate> templateMap = getNaturalLanguageTemplateMap(naturalLanguageUsageId, languageCode);
        return translateNaturalLanguageTreeForProposition(proposition, templateMap);
    }

    public NaturalLanguageTree translateNaturalLanguageTreeForProposition(PropositionDefinition proposition,
                                                                          Map<String, NaturalLanguageTemplate> templateMap) throws RiceIllegalArgumentException {

        NaturalLanguageTemplate naturalLanguageTemplate = templateMap.get(proposition.getTypeId());

        NaturalLanguageTree.Builder tree = NaturalLanguageTree.Builder.create();
        if (proposition.getPropositionTypeCode().equals(PropositionType.SIMPLE.getCode())) {
            Map<String, Object> contextMap = this.buildSimplePropositionContextMap(proposition);
            String naturalLanguage = templater.translate(naturalLanguageTemplate, contextMap);
            tree.setNaturalLanguage(naturalLanguage);

        } else if (proposition.getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
            Map<String, Object> contextMap = this.buildCompoundPropositionContextMap(proposition, templateMap);
            String naturalLanguage = templater.translate(naturalLanguageTemplate, contextMap);
            tree.setNaturalLanguage(naturalLanguage);

            //Null check because newly created compound propositions should also be translateable.
            if(proposition.getCompoundComponents()!=null){
                List<NaturalLanguageTree> children = new ArrayList<NaturalLanguageTree>();
                for (PropositionDefinition child : proposition.getCompoundComponents()) {
                    children.add(this.translateNaturalLanguageTreeForProposition(child, templateMap));
                }
                tree.setChildren(children);
            }

        } else {
            throw new RiceIllegalArgumentException("Unknown proposition type: " + proposition.getPropositionTypeCode());
        }

        return tree.build();
    }

    protected Map<String, Object> buildSimplePropositionContextMap(PropositionDefinition proposition) {
        if (!proposition.getPropositionTypeCode().equals(PropositionType.SIMPLE.getCode())) {
            throw new RiceIllegalArgumentException("proposition is not simple " + proposition.getPropositionTypeCode() + " " + proposition.getId() + proposition.getDescription());
        }
        Map<String, Object> contextMap = new LinkedHashMap<String, Object>();
        for (PropositionParameter param : proposition.getParameters()) {
            if (param.getParameterType().equals(PropositionParameterType.TERM.getCode())) {
                TermDefinition term = param.getTermValue();
                if ((term == null) && (StringUtils.isNotBlank(param.getValue()))) {
                    term = this.termRepositoryService.getTerm(param.getValue());
                }
                if (term != null) {
                    for (TermParameterDefinition termParam : term.getParameters()) {
                        contextMap.put(termParam.getName(), termParam.getValue());
                    }
                } else {
                    contextMap.put(param.getParameterType(), param.getValue());
                }
            } else {
                contextMap.put(param.getParameterType(), param.getValue());
            }
        }
        return contextMap;
    }

    protected Map<String, Object> buildCompoundPropositionContextMap(PropositionDefinition proposition, Map<String, NaturalLanguageTemplate> templateMap) {
        if (!proposition.getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
            throw new RiceIllegalArgumentException("proposition us not compound " + proposition.getPropositionTypeCode() + " " + proposition.getId() + proposition.getDescription());
        }
        return new LinkedHashMap<String, Object>();
    }

}
