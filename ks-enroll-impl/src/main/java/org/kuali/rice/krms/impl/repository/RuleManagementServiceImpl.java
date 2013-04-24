/**
 * Copyright 2005-2012 The Kuali Foundation
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
package org.kuali.rice.krms.impl.repository;

import org.kuali.rice.core.api.criteria.GenericQueryResults;
import org.kuali.rice.core.api.criteria.Predicate;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplate;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageUsage;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static org.kuali.rice.core.api.criteria.PredicateFactory.in;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krms.api.repository.NaturalLanguageTree;
import org.kuali.rice.krms.api.repository.action.ActionDefinition;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplaterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.term.TermDefinition;
import org.kuali.rice.krms.api.repository.term.TermRepositoryService;
import org.kuali.rice.krms.impl.repository.language.SimpleNaturalLanguageTemplater;

/**
 * The implementation of {@link RuleManagementService} operations facilitate management of rules and
 * associated information.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class RuleManagementServiceImpl extends RuleRepositoryServiceImpl implements RuleManagementService {

    private ReferenceObjectBindingBoService referenceObjectBindingBoService = new ReferenceObjectBindingBoServiceImpl();
    private AgendaBoService agendaBoService = new AgendaBoServiceImpl();
    private RuleBoService ruleBoService = new RuleBoServiceImpl();
    private ActionBoService actionBoService = new ActionBoServiceImpl();
    private PropositionBoService propositionBoService = new PropositionBoServiceImpl();
    private NaturalLanguageUsageBoService naturalLanguageUsageBoService = new NaturalLanguageUsageBoServiceImpl();
    private NaturalLanguageTemplateBoService naturalLanguageTemplateBoService = new NaturalLanguageTemplateBoServiceImpl();
    private ContextBoService contextBoService = new ContextBoServiceImpl();
    private NaturalLanguageTemplaterContract templater = new SimpleNaturalLanguageTemplater ();
    private TermRepositoryService termRepositoryService = new TermBoServiceImpl ();

    
    public ReferenceObjectBindingBoService getReferenceObjectBindingBoService() {
        return referenceObjectBindingBoService;
    }

    public void setReferenceObjectBindingBoService(ReferenceObjectBindingBoService referenceObjectBindingBoService) {
        this.referenceObjectBindingBoService = referenceObjectBindingBoService;
    }

    public AgendaBoService getAgendaBoService() {
        return agendaBoService;
    }

    public void setAgendaBoService(AgendaBoService agendaBoService) {
        this.agendaBoService = agendaBoService;
    }

    public RuleBoService getRuleBoService() {
        return ruleBoService;
    }

    public void setRuleBoService(RuleBoService ruleBoService) {
        this.ruleBoService = ruleBoService;
    }

    public PropositionBoService getPropositionBoService() {
        return propositionBoService;
    }

    public void setPropositionBoService(PropositionBoService propositionBoService) {
        this.propositionBoService = propositionBoService;
    }

    public NaturalLanguageUsageBoService getNaturalLanguageUsageBoService() {
        return naturalLanguageUsageBoService;
    }

    public void setNaturalLanguageUsageBoService(NaturalLanguageUsageBoService naturalLanguageUsageBoService) {
        this.naturalLanguageUsageBoService = naturalLanguageUsageBoService;
    }

    public NaturalLanguageTemplateBoService getNaturalLanguageTemplateBoService() {
        return naturalLanguageTemplateBoService;
    }

    public void setNaturalLanguageTemplateBoService(NaturalLanguageTemplateBoService naturalLanguageTemplateBoService) {
        this.naturalLanguageTemplateBoService = naturalLanguageTemplateBoService;
    }

    public ContextBoService getContextBoService() {
        return contextBoService;
    }

    public void setContextBoService(ContextBoService contextBoService) {
        this.contextBoService = contextBoService;
    }

    public ActionBoService getActionBoService() {
        return actionBoService;
    }

    public void setActionBoService(ActionBoService actionBoService) {
        this.actionBoService = actionBoService;
    }
      
    public NaturalLanguageTemplaterContract getTemplater() {
        return templater;
    }

    public void setTemplater(NaturalLanguageTemplaterContract templater) {
        this.templater = templater;
    }

    public TermRepositoryService getTermRepositoryService() {
        return termRepositoryService;
    }

    public void setTermRepositoryService(TermRepositoryService termRepositoryService) {
        this.termRepositoryService = termRepositoryService;
    }
    
    ////
    //// reference object binding methods
    ////
    @Override
    public ReferenceObjectBinding createReferenceObjectBinding(ReferenceObjectBinding referenceObjectDefinition)
            throws RiceIllegalArgumentException {
        return referenceObjectBindingBoService.createReferenceObjectBinding(referenceObjectDefinition);
    }

    @Override
    public ReferenceObjectBinding getReferenceObjectBinding(String id) throws RiceIllegalArgumentException {
        return referenceObjectBindingBoService.getReferenceObjectBinding(id);
    }

    @Override
    public List<ReferenceObjectBinding> getReferenceObjectBindings(List<String> ids) throws RiceIllegalArgumentException {
        if (ids == null) {
            throw new IllegalArgumentException("reference binding object ids must not be null");
        }

        // Fetch BOs
        List<ReferenceObjectBindingBo> bos = null;
        if (ids.size() == 0) {
            bos = Collections.emptyList();
        } else {
            QueryByCriteria.Builder qBuilder = QueryByCriteria.Builder.create();
            List<Predicate> pList = new ArrayList<Predicate>();
            qBuilder.setPredicates(in("id", ids.toArray()));
            GenericQueryResults<ReferenceObjectBindingBo> results = getCriteriaLookupService().lookup(ReferenceObjectBindingBo.class, qBuilder.build());

            bos = results.getResults();
        }

        // Translate BOs
        List<ReferenceObjectBinding> bindings = new LinkedList<ReferenceObjectBinding>();
        for (ReferenceObjectBindingBo bo : bos) {
            ReferenceObjectBinding binding = ReferenceObjectBindingBo.to(bo);
            bindings.add(binding);
        }
        return Collections.unmodifiableList(bindings);
    }

    @Override
    public List<ReferenceObjectBinding> findReferenceObjectBindingsByReferenceObject(String referenceObjectReferenceDiscriminatorType,
            String referenceObjectId)
            throws RiceIllegalArgumentException {
        if (referenceObjectReferenceDiscriminatorType == null) {
            throw new RiceIllegalArgumentException("reference binding object discriminator type must not be null");
        }
        if (referenceObjectId == null) {
            throw new RiceIllegalArgumentException("reference object id must not be null");
        }
        List<ReferenceObjectBinding> list = new ArrayList<ReferenceObjectBinding>();
        for (ReferenceObjectBinding binding : this.referenceObjectBindingBoService.findReferenceObjectBindingsByReferenceObject(referenceObjectId)) {
            if (binding.getReferenceDiscriminatorType().equals(referenceObjectReferenceDiscriminatorType)) {
                list.add(binding);
            }
        }
        return list;
    }
    
    @Override
    public List<ReferenceObjectBinding> findReferenceObjectBindingsByReferenceDiscriminatorType(String referenceObjectReferenceDiscriminatorType) throws RiceIllegalArgumentException {
        return referenceObjectBindingBoService.findReferenceObjectBindingsByReferenceDiscriminatorType(referenceObjectReferenceDiscriminatorType);
    }

    @Override
    public List<ReferenceObjectBinding> findReferenceObjectBindingsByKrmsDiscriminatorType(String referenceObjectKrmsDiscriminatorType) throws RiceIllegalArgumentException {
        return referenceObjectBindingBoService.findReferenceObjectBindingsByKrmsDiscriminatorType(referenceObjectKrmsDiscriminatorType);
    }

    @Override
    public List<ReferenceObjectBinding> findReferenceObjectBindingsByKrmsObject(String krmsObjectId) throws RiceIllegalArgumentException {
        return referenceObjectBindingBoService.findReferenceObjectBindingsByKrmsObject(krmsObjectId);
    }

    @Override
    public void updateReferenceObjectBinding(ReferenceObjectBinding referenceObjectBindingDefinition)
            throws RiceIllegalArgumentException {
        referenceObjectBindingBoService.updateReferenceObjectBinding(referenceObjectBindingDefinition);
    }

    @Override
    public void deleteReferenceObjectBinding(String id) throws RiceIllegalArgumentException {
        referenceObjectBindingBoService.deleteReferenceObjectBinding(id);
    }

    @Override
    public List<String> findReferenceObjectBindingIds(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        return referenceObjectBindingBoService.findReferenceObjectBindingIds(queryByCriteria);
    }

    ////
    //// agenda
    //// 
    @Override
    public AgendaDefinition createAgenda(AgendaDefinition agendaDefinition) throws RiceIllegalArgumentException {
        return agendaBoService.createAgenda(agendaDefinition);
    }

    @Override
    public AgendaDefinition getAgenda(String id) throws RiceIllegalArgumentException {
        return agendaBoService.getAgendaByAgendaId(id);
    }

    @Override
    public List<AgendaDefinition> getAgendasByContext(String contextId) throws RiceIllegalArgumentException {
        return agendaBoService.getAgendasByContextId(contextId);
    }

    @Override
    public void updateAgenda(AgendaDefinition agendaDefinition) throws RiceIllegalArgumentException {
        agendaBoService.updateAgenda(agendaDefinition);
    }

    @Override
    public void deleteAgenda(String id) throws RiceIllegalArgumentException {
        agendaBoService.deleteAgenda(id);
    }

    @Override
    public List<AgendaDefinition> getAgendasByType(String typeId) throws RiceIllegalArgumentException {
        return agendaBoService.getAgendasByType(typeId);
    }

    @Override
    public List<AgendaDefinition> getAgendasByTypeAndContext(String typeId, String contextId)
            throws RiceIllegalArgumentException {
        return agendaBoService.getAgendasByTypeAndContext(typeId, contextId);
    }

    ////
    //// agenda item methods
    ////
    @Override
    public AgendaItemDefinition createAgendaItem(AgendaItemDefinition agendaItemDefinition) throws RiceIllegalArgumentException {
        return agendaBoService.createAgendaItem(agendaItemDefinition);
    }

    @Override
    public AgendaItemDefinition getAgendaItem(String id) throws RiceIllegalArgumentException {
        return agendaBoService.getAgendaItemById(id);
    }

    @Override
    public List<AgendaItemDefinition> getAgendaItemsByType(String typeId)
            throws RiceIllegalArgumentException {
        return agendaBoService.getAgendaItemsByType(typeId);
    }

    @Override
    public List<AgendaItemDefinition> getAgendaItemsByContext(String contextId) throws RiceIllegalArgumentException {
        return agendaBoService.getAgendaItemsByContext(contextId);
    }

    @Override
    public List<AgendaItemDefinition> getAgendaItemsByTypeAndContext(String typeId, String contextId)
            throws RiceIllegalArgumentException {
        return agendaBoService.getAgendaItemsByTypeAndContext(typeId, contextId);
    }

    @Override
    public void deleteAgendaItem(String id) throws RiceIllegalArgumentException {
        agendaBoService.deleteAgendaItem(id);
    }

    @Override
    public void updateAgendaItem(AgendaItemDefinition agendaItemDefinition) throws RiceIllegalArgumentException {
        agendaBoService.updateAgendaItem(agendaItemDefinition);
    }

    private void crossCheckPropId(RuleDefinition ruleDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (ruleDefinition.getPropId() != null && ruleDefinition.getProposition() != null) {
            if (!ruleDefinition.getPropId().equals(ruleDefinition.getProposition().getId())) {
                throw new RiceIllegalArgumentException("propId does not proposition.getId" + ruleDefinition.getPropId() + " " + ruleDefinition.getProposition().getId());
            }
        }
    }
    
    
    ////
    //// rule methods
    ////
    @Override
    public RuleDefinition createRule(RuleDefinition ruleDefinition) throws RiceIllegalArgumentException {
        // CREATE
        if (ruleDefinition.getId() != null) {
            RuleDefinition orig = this.getRule(ruleDefinition.getId());
            if (orig != null) {
                throw new RiceIllegalArgumentException(ruleDefinition.getId());
            }
        }
        // if both are set they better match
        crossCheckPropId (ruleDefinition);
        RuleDefinition rule = ruleBoService.createRule(ruleDefinition);
        return this.createPropositionIfNeeded(rule);
    }

    private RuleDefinition createPropositionIfNeeded(RuleDefinition rule) {
        // no prop to create
        if (rule.getProposition() == null) {
            return rule;
        }
        // ojb will take care of props that have already been created
        if (rule.getProposition().getId() != null) {
            return rule;
        }
        // create the proposition
        RuleDefinition.Builder ruleBldr = RuleDefinition.Builder.create(rule);
        PropositionDefinition.Builder propBldr = ruleBldr.getProposition();
        propBldr.setRule(ruleBldr);
        PropositionDefinition prop = this.createProposition(propBldr.build());
        // now update the rule so it holds the proposition id
        propBldr = PropositionDefinition.Builder.create(prop);
        ruleBldr.setProposition(propBldr);
        this.ruleBoService.updateRule(ruleBldr.build());
        return this.getRule(ruleBldr.getId());
    }
    
    
    
    @Override
    public void updateRule(RuleDefinition ruleDefinition) throws RiceIllegalArgumentException {
        crossCheckPropId (ruleDefinition);
        ruleBoService.updateRule(ruleDefinition);
        this.createPropositionIfNeeded(ruleDefinition);
    }

    @Override
    public void deleteRule(String id) throws RiceIllegalArgumentException {
        ruleBoService.deleteRule(id);
    }
    
    
    ////
    //// action methods
    ////
    @Override
    public ActionDefinition createAction(ActionDefinition actionDefinition) throws RiceIllegalArgumentException {
        return actionBoService.createAction(actionDefinition);
    }

    @Override
    public void updateAction(ActionDefinition actionDefinition) throws RiceIllegalArgumentException {
        actionBoService.updateAction(actionDefinition);
    }

    @Override
    public void deleteAction(String id) throws RiceIllegalArgumentException {
        throw new RiceIllegalArgumentException ("not implemented yet because not supported by the bo service");
//        actionBoService.deleteAction(id);
    }

    @Override
    public ActionDefinition getAction(String actionId) {
        return actionBoService.getActionByActionId(actionId);
    }

    @Override
    public List<ActionDefinition> getActions(List<String> actionIds) {
        // TODO: implement this more efficiently by adding the builk op to the bo service and calling it
        List<ActionDefinition> list = new ArrayList<ActionDefinition>();
        for (String id : actionIds) {
            list.add(this.getAction(id));
        }
        return list;
    }
    
    private void crossCheckPropositionParameters (PropositionDefinition propositionDefinition) {        
        // check that if the value and termValue are both supplied that they match
        for (PropositionParameter param : propositionDefinition.getParameters()) {
            if (param.getValue() != null && param.getTermValue() != null) {
                if (!param.getValue().equals(param.getTermValue().getId())) {
                    throw new RiceIllegalArgumentException("value does not match termValue.id on param "
                            + param.getSequenceNumber() + " " + param.getValue() + " " + param.getTermValue().getId());
                }
            }
        }
    }


    ////
    //// proposition methods
    ////
    @Override
    public PropositionDefinition createProposition(PropositionDefinition propositionDefinition) throws RiceIllegalArgumentException {
        // CREATE
        if (propositionDefinition.getId() != null) {
            PropositionDefinition orig = this.getProposition(propositionDefinition.getId());
            if (orig != null) {
                throw new RiceIllegalArgumentException(propositionDefinition.getId());
            }
        }
        crossCheckPropositionParameters(propositionDefinition);
        PropositionDefinition prop = propositionBoService.createProposition(propositionDefinition);
        prop = createTermValuesIfNeeded(prop);
        prop = createCompoundPropsIfNeeded (prop);
        return prop;
    }
    
    private PropositionDefinition createTermValuesIfNeeded(PropositionDefinition prop) {
        if (prop.getParameters() == null) {
            return prop;
        }
        if (prop.getParameters().isEmpty ()) {
            return prop;
        }
        boolean updated = false;
        PropositionDefinition.Builder propBldr = PropositionDefinition.Builder.create(prop);
        List<PropositionParameter.Builder> paramBldrs = new ArrayList<PropositionParameter.Builder> ();
        for (PropositionParameter.Builder paramBldr : propBldr.getParameters()) {
            paramBldrs.add(paramBldr);
            // link the param the proposition's id
            // not sure we need to do this but...
            if (paramBldr.getPropId() == null) {
                paramBldr.setPropId(propBldr.getId());
                updated = true;
            }
            // create the termvalue if it was specified
            if (paramBldr.getTermValue() != null) {
                TermDefinition termValue = paramBldr.getTermValue();
                // no id means it does not exist yet
                if (termValue.getId() == null) {
                    termValue = this.termRepositoryService.createTerm(termValue);
                    paramBldr.setTermValue(termValue);
                    updated = true;
                } 
                if (paramBldr.getValue() == null) {
                    paramBldr.setValue(termValue.getId());
                    updated = true;
                }
                // TODO: do we have to worry about updates to Terms?
            }
        }
        if (!updated) {
            return prop;
        }
        propBldr.setParameters(paramBldrs);
        this.propositionBoService.updateProposition(propBldr.build());         
        prop = this.getProposition(propBldr.getId());
        return prop;
    }
        
    private PropositionDefinition createCompoundPropsIfNeeded(PropositionDefinition prop) {
        if (prop.getCompoundComponents() == null) {
            return prop;
        }
        if (prop.getCompoundComponents().isEmpty ()) {
            return prop;
        }
        boolean updated = false;
        PropositionDefinition.Builder propBldr = PropositionDefinition.Builder.create(prop);
        List<PropositionDefinition.Builder> compPropBldrs = new ArrayList<PropositionDefinition.Builder>();
        for (PropositionDefinition.Builder compPropBldr : propBldr.getCompoundComponents()) {
            if (compPropBldr.getId() == null) {
                PropositionDefinition compProp = this.createProposition(compPropBldr.build());
                compPropBldrs.add(PropositionDefinition.Builder.create(compProp));
                updated = true;
            } else {
                compPropBldrs.add (compPropBldr);
            }
            if (compPropBldr.getRuleId() == null) {
                compPropBldr.setRuleId(prop.getRuleId());
                updated = true;
            }            
        }
        if (!updated) {
            return prop;
        }
        propBldr.setCompoundComponents(compPropBldrs);
        this.propositionBoService.updateProposition(propBldr.build());
        prop = this.getProposition(propBldr.getId());
        return prop;
    }

    @Override
    public PropositionDefinition getProposition(String id) throws RiceIllegalArgumentException {
        PropositionDefinition proposition = propositionBoService.getPropositionById(id);
        if (proposition == null) {
            throw new RiceIllegalArgumentException (id);
        }
        proposition = this.replaceTermValues (proposition);
        return proposition;
    }
    
    private PropositionDefinition replaceTermValues(PropositionDefinition proposition) {
        // only do this for simple props
        if (!PropositionType.SIMPLE.getCode ().equalsIgnoreCase (proposition.getPropositionTypeCode())) {
            return proposition;
        }
        // that have parameters
        if (proposition.getParameters() == null) {
            return proposition;
        }
        if (proposition.getParameters().isEmpty()) {
            return proposition;
        }
        boolean found = false;
        List<PropositionParameter.Builder> params = new ArrayList<PropositionParameter.Builder> (proposition.getParameters().size());
        for (PropositionParameter param : proposition.getParameters()) {
            if (!PropositionParameterType.TERM.getCode().equalsIgnoreCase (param.getParameterType())) {
                params.add(PropositionParameter.Builder.create(param));
                continue;
            }
            // inflate the termValue
            found = true;
            TermDefinition termValue = this.termRepositoryService.getTerm(param.getValue());
            PropositionParameter.Builder bldr = PropositionParameter.Builder.create(param);
            bldr.setTermValue(termValue);
            params.add(bldr);
        }
        if (!found) {
            return proposition;
        }
        PropositionDefinition.Builder bldr = PropositionDefinition.Builder.create(proposition);
        bldr.setParameters(params);
        return bldr.build();
    }

    
    private Set<PropositionDefinition> replaceTermValuesInSet(Set<PropositionDefinition> propositions) {
        if (propositions == null) {
            return null;
        }
        if (propositions.isEmpty()) {
            return propositions;
        }
        Set<PropositionDefinition> set = new LinkedHashSet<PropositionDefinition>(propositions.size());
        for (PropositionDefinition proposition : propositions) {
            proposition = this.replaceTermValues(proposition);
            set.add(proposition);
        }
        return set;
    }
    
    @Override
    public Set<PropositionDefinition> getPropositionsByType(String typeId) throws RiceIllegalArgumentException {
        return replaceTermValuesInSet (propositionBoService.getPropositionsByType(typeId));
    }

    @Override
    public Set<PropositionDefinition> getPropositionsByRule(String ruleId) throws RiceIllegalArgumentException {
        return replaceTermValuesInSet (propositionBoService.getPropositionsByRule(ruleId));
    }

    @Override
    public void updateProposition(PropositionDefinition propositionDefinition) throws RiceIllegalArgumentException {
        this.crossCheckPropositionParameters(propositionDefinition);
        propositionBoService.updateProposition(propositionDefinition);
        PropositionDefinition prop = this.getProposition(propositionDefinition.getId());
        prop = createTermValuesIfNeeded(prop);
        createCompoundPropsIfNeeded (prop);
    }

    @Override
    public void deleteProposition(String id) throws RiceIllegalArgumentException {
        propositionBoService.deleteProposition(id);
    }

    ////
    //// natural language usage methods
    ////
    @Override
    public NaturalLanguageUsage createNaturalLanguageUsage(NaturalLanguageUsage naturalLanguageUsage) throws RiceIllegalArgumentException {
        return naturalLanguageUsageBoService.createNaturalLanguageUsage(naturalLanguageUsage);
    }

    @Override
    public NaturalLanguageUsage getNaturalLanguageUsage(String id) throws RiceIllegalArgumentException {
        return naturalLanguageUsageBoService.getNaturalLanguageUsage(id);
    }

    @Override
    public void updateNaturalLanguageUsage(NaturalLanguageUsage naturalLanguageUsage) throws RiceIllegalArgumentException {
        naturalLanguageUsageBoService.updateNaturalLanguageUsage(naturalLanguageUsage);
    }

    @Override
    public void deleteNaturalLanguageUsage(String naturalLanguageUsageId) throws RiceIllegalArgumentException {
        naturalLanguageUsageBoService.deleteNaturalLanguageUsage(naturalLanguageUsageId);
    }

    @Override
    public List<NaturalLanguageUsage> getNaturalLanguageUsagesByNamespace(String namespace)
            throws RiceIllegalArgumentException {
        return this.naturalLanguageUsageBoService.findNaturalLanguageUsagesByNamespace(namespace);
    }

    @Override
    public NaturalLanguageUsage getNaturalLanguageUsageByNameAndNamespace(String name, String namespace) 
            throws RiceIllegalArgumentException {
        return this.naturalLanguageUsageBoService.getNaturalLanguageUsageByName(namespace, name);
    }  
    
    
    ////
    //// natural language translations
    ////
    @Override
    public String translateNaturalLanguageForObject(String naturalLanguageUsageId,
            String typeId,
            String krmsObjectId,
            String languageCode)
            throws RiceIllegalArgumentException {
        TranslationUtility util = new TranslationUtility (this, this.templater);
        return util.translateNaturalLanguageForObject(naturalLanguageUsageId, typeId, krmsObjectId, languageCode);
    }

    @Override
    public String translateNaturalLanguageForProposition(String naturalLanguageUsageId,
            PropositionDefinition proposition, String languageCode)
            throws RiceIllegalArgumentException {
        TranslationUtility util = new TranslationUtility (this, this.templater);
        return util.translateNaturalLanguageForProposition(naturalLanguageUsageId, proposition, languageCode);
    }

    @Override
    public NaturalLanguageTree translateNaturalLanguageTreeForProposition(String naturalLanguageUsageId, 
    PropositionDefinition propositionDefinintion, 
    String languageCode) throws RiceIllegalArgumentException {
        TranslationUtility util = new TranslationUtility (this, this.templater);
        return util.translateNaturalLanguageTreeForProposition(naturalLanguageUsageId, propositionDefinintion, languageCode);
    }
    
    

    ////
    //// context methods
    ////
    @Override
    public ContextDefinition createContext(ContextDefinition contextDefinition) throws RiceIllegalArgumentException {
        return this.contextBoService.createContext(contextDefinition);
    }
    
    
    @Override
    public ContextDefinition findCreateContext(ContextDefinition contextDefinition) throws RiceIllegalArgumentException {         
        ContextDefinition orig = this.contextBoService.getContextByNameAndNamespace(contextDefinition.getName(), contextDefinition.getNamespace());
        if (orig != null) {
            return orig;
        }
        return this.contextBoService.createContext(contextDefinition);
    }

    @Override
    public void updateContext(ContextDefinition contextDefinition) throws RiceIllegalArgumentException {
        this.contextBoService.updateContext(contextDefinition);
    }

    @Override
    public void deleteContext(String id) throws RiceIllegalArgumentException {
        throw new RiceIllegalArgumentException("not implemented yet");
    }

    @Override
    public ContextDefinition getContext(String id) throws RiceIllegalArgumentException {
        return this.contextBoService.getContextByContextId(id);
    }

    @Override
    public ContextDefinition getContextByNameAndNamespace(String name, String namespace) throws RiceIllegalArgumentException {
        return this.contextBoService.getContextByNameAndNamespace(name, namespace);
    }

    ////
    //// natural language templates
    ////
    @Override
    public NaturalLanguageTemplate createNaturalLanguageTemplate(NaturalLanguageTemplate naturalLanguageTemplate) throws RiceIllegalArgumentException {
        return this.naturalLanguageTemplateBoService.createNaturalLanguageTemplate(naturalLanguageTemplate);
    }

    @Override
    public NaturalLanguageTemplate getNaturalLanguageTemplate(String naturalLanguageTemplateId) throws RiceIllegalArgumentException {
        return this.naturalLanguageTemplateBoService.getNaturalLanguageTemplate(naturalLanguageTemplateId);
    }

    @Override
    public void updateNaturalLanguageTemplate(NaturalLanguageTemplate naturalLanguageTemplate) throws RiceIllegalArgumentException {
        this.naturalLanguageTemplateBoService.updateNaturalLanguageTemplate(naturalLanguageTemplate);
    }

    @Override
    public void deleteNaturalLanguageTemplate(String naturalLanguageTemplateId) throws RiceIllegalArgumentException {
        this.naturalLanguageTemplateBoService.deleteNaturalLanguageTemplate(naturalLanguageTemplateId);
    }

    @Override
    public List<NaturalLanguageTemplate> findNaturalLanguageTemplatesByLanguageCode(String languageCode) throws RiceIllegalArgumentException {
        return this.naturalLanguageTemplateBoService.findNaturalLanguageTemplatesByLanguageCode(languageCode);
    }

    @Override
    public NaturalLanguageTemplate findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId(String languageCode, String typeId, String naturalLanguageUsageId) throws RiceIllegalArgumentException {
        return this.naturalLanguageTemplateBoService.findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId(languageCode, typeId, naturalLanguageUsageId);
    }

    @Override
    public List<NaturalLanguageTemplate> findNaturalLanguageTemplatesByNaturalLanguageUsage(String naturalLanguageUsageId) throws RiceIllegalArgumentException {
        return this.naturalLanguageTemplateBoService.findNaturalLanguageTemplatesByNaturalLanguageUsage(naturalLanguageUsageId);
    }

    @Override
    public List<NaturalLanguageTemplate> findNaturalLanguageTemplatesByType(String typeId) throws RiceIllegalArgumentException {
        return this.naturalLanguageTemplateBoService.findNaturalLanguageTemplatesByType(typeId);
    }

    @Override
    public List<NaturalLanguageTemplate> findNaturalLanguageTemplatesByTemplate(String template) throws RiceIllegalArgumentException {
        return this.naturalLanguageTemplateBoService.findNaturalLanguageTemplatesByTemplate(template);
    }

    @Override
    public List<String> findContextIds(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        GenericQueryResults<ContextBo> results = getCriteriaLookupService().lookup(ContextBo.class, queryByCriteria);
        List<String> list = new ArrayList<String> ();
        for (ContextBo bo : results.getResults()) {
            list.add (bo.getId());
        }
        return list;
    }

    @Override
    public List<String> findAgendaIds(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        GenericQueryResults<AgendaBo> results = getCriteriaLookupService().lookup(AgendaBo.class, queryByCriteria);
        List<String> list = new ArrayList<String> ();
        for (AgendaBo bo : results.getResults()) {
            list.add (bo.getId());
        }
        return list;
    }

    @Override
    public List<String> findRuleIds(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        GenericQueryResults<RuleBo> results = getCriteriaLookupService().lookup(RuleBo.class, queryByCriteria);
        List<String> list = new ArrayList<String> ();
        for (RuleBo bo : results.getResults()) {
            list.add (bo.getId());
        }
        return list;
    }

    @Override
    public List<String> findPropositionIds(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        GenericQueryResults<PropositionBo> results = getCriteriaLookupService().lookup(PropositionBo.class, queryByCriteria);
        List<String> list = new ArrayList<String> ();
        for (PropositionBo bo : results.getResults()) {
            list.add (bo.getId());
        }
        return list;
    }

    @Override
    public List<String> findActionIds(QueryByCriteria queryByCriteria) throws RiceIllegalArgumentException {
        GenericQueryResults<ActionBo> results = getCriteriaLookupService().lookup(ActionBo.class, queryByCriteria);
        List<String> list = new ArrayList<String> ();
        for (ActionBo bo : results.getResults()) {
            list.add (bo.getId());
        }
        return list;
    }
    
    
    /**
     * Sets the businessObjectService property.
     *
     * @param businessObjectService The businessObjectService to set.
     */
    @Override
    public void setBusinessObjectService(final BusinessObjectService businessObjectService) {
        super.setBusinessObjectService(businessObjectService);
        if (referenceObjectBindingBoService instanceof ReferenceObjectBindingBoServiceImpl) {
            ((ReferenceObjectBindingBoServiceImpl) referenceObjectBindingBoService).setBusinessObjectService(businessObjectService);
        }
        if (agendaBoService instanceof AgendaBoServiceImpl) {
            ((AgendaBoServiceImpl) agendaBoService).setBusinessObjectService(businessObjectService);
        }
        if (ruleBoService instanceof RuleBoServiceImpl) {
            ((RuleBoServiceImpl) ruleBoService).setBusinessObjectService(businessObjectService);
        }
        if (actionBoService instanceof ActionBoServiceImpl) {
            ((ActionBoServiceImpl) actionBoService).setBusinessObjectService(businessObjectService);
        }
        if (propositionBoService instanceof PropositionBoServiceImpl) {
            ((PropositionBoServiceImpl) propositionBoService).setBusinessObjectService(businessObjectService);
        }
        if (naturalLanguageUsageBoService instanceof NaturalLanguageUsageBoServiceImpl) {
            ((NaturalLanguageUsageBoServiceImpl) naturalLanguageUsageBoService).setBusinessObjectService(businessObjectService);
        }
        if (naturalLanguageTemplateBoService instanceof NaturalLanguageTemplateBoServiceImpl) {
            ((NaturalLanguageTemplateBoServiceImpl) naturalLanguageTemplateBoService).setBusinessObjectService(businessObjectService);
        }
        if (contextBoService instanceof ContextBoServiceImpl) {
            ((ContextBoServiceImpl) contextBoService).setBusinessObjectService(businessObjectService);
        }
        if (termRepositoryService instanceof TermBoServiceImpl) {
            ((TermBoServiceImpl) termRepositoryService).setBusinessObjectService(businessObjectService);
        }
    }    
}
