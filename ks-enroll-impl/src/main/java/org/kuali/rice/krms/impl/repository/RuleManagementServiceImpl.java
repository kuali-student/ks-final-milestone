/**
 * Copyright 2005-2012 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl2.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.kuali.rice.core.api.criteria.PredicateFactory.in;
import org.kuali.rice.krad.service.BusinessObjectService;
import org.kuali.rice.krms.api.repository.NaturalLanguageTree;
import org.kuali.rice.krms.api.repository.context.ContextDefinition;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplaterContract;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.impl.repository.language.SimpleNaturalLanguageTemplater;

/**
 * The implementation of {@link RuleManagementService} operations facilitate
 * management of rules and associated information.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class RuleManagementServiceImpl extends RuleRepositoryServiceImpl implements RuleManagementService {

    private ReferenceObjectBindingBoService referenceObjectBindingBoService = new ReferenceObjectBindingBoServiceImpl();
    private AgendaBoService agendaBoService = new AgendaBoServiceImpl();
    private RuleBoService ruleBoService = new RuleBoServiceImpl();
    private PropositionBoService propositionBoService = new PropositionBoServiceImpl();
    private NaturalLanguageUsageBoService naturalLanguageUsageBoService = new NaturalLanguageUsageBoServiceImpl();
    private NaturalLanguageTemplateBoService naturalLanguageTemplateBoService = new NaturalLanguageTemplateBoServiceImpl();
    private ContextBoService contextBoService = new ContextBoServiceImpl();
    private NaturalLanguageTemplaterContract templater = new SimpleNaturalLanguageTemplater ();

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

    public NaturalLanguageTemplaterContract getTemplater() {
        return templater;
    }

    public void setTemplater(NaturalLanguageTemplaterContract templater) {
        this.templater = templater;
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

    ////
    //// rule methods
    ////
    @Override
    public RuleDefinition createRule(RuleDefinition ruleDefinition) throws RiceIllegalArgumentException {
        return ruleBoService.createRule(ruleDefinition);
    }

    @Override
    public void updateRule(RuleDefinition ruleDefinition) throws RiceIllegalArgumentException {
        ruleBoService.updateRule(ruleDefinition);
    }

    @Override
    public void deleteRule(String id) throws RiceIllegalArgumentException {
        ruleBoService.deleteRule(id);
    }

    ////
    //// proposition methods
    ////
    @Override
    public PropositionDefinition createProposition(PropositionDefinition propositionDefinition) throws RiceIllegalArgumentException {
        return propositionBoService.createProposition(propositionDefinition);
    }

    @Override
    public PropositionDefinition getProposition(String id) throws RiceIllegalArgumentException {
        return propositionBoService.getPropositionById(id);
    }

    @Override
    public Set<PropositionDefinition> getPropositionsByType(String typeId) throws RiceIllegalArgumentException {
        return propositionBoService.getPropositionsByType(typeId);
    }

    @Override
    public Set<PropositionDefinition> getPropositionsByRule(String ruleId) throws RiceIllegalArgumentException {
        return propositionBoService.getPropositionsByRule(ruleId);
    }

    @Override
    public void updateProposition(PropositionDefinition propositionDefinition) throws RiceIllegalArgumentException {
        propositionBoService.updateProposition(propositionDefinition);
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

        PropositionDefinition proposition = null;
        // TODO: find out what RICE intended for this typeId? Was it supposed to be the Simple Class name?
        if (typeId.equals("proposition")) {
            proposition = this.getProposition(krmsObjectId);
            if (proposition == null) {
                throw new RiceIllegalArgumentException(krmsObjectId + " is not an Id for a proposition");
            }
        } else if (typeId.equals("agenda")) {
            AgendaDefinition agenda = this.getAgenda(krmsObjectId);
            if (agenda == null) {
                throw new RiceIllegalArgumentException(krmsObjectId + " is not an Id for an agenda");
            }
            if (agenda.getFirstItemId() == null) {
                throw new RiceIllegalArgumentException("Agenda has no first item");
            }
            AgendaItemDefinition item = this.getAgendaItem(agenda.getFirstItemId());
            if (item.getRuleId() == null) {
                throw new RiceIllegalArgumentException("Only simple agenda's composed of one item that holds a rule is supported at this time");
            }
            RuleDefinition rule = this.getRule(item.getRuleId());
            proposition = rule.getProposition();
            if (proposition == null) {
                throw new RiceIllegalArgumentException("The agenda's rule has a proposition that is null");
            }
        }
        String propositionTypeId = proposition.getTypeId();
        NaturalLanguageTemplate naturalLanguageTemplate =
                naturalLanguageTemplateBoService.findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId(languageCode,
                propositionTypeId,
                naturalLanguageUsageId);
        if (naturalLanguageTemplate == null) {
            throw new RiceIllegalArgumentException("no template found for " + languageCode
                    + " " + typeId
                    + " " + naturalLanguageUsageId);
        }
        return this.translateNaturalLanguageForProposition(naturalLanguageUsageId, proposition, languageCode);
    }

    @Override
    public String translateNaturalLanguageForProposition(String naturalLanguageUsageId,
            PropositionDefinition proposition, String languageCode)
            throws RiceIllegalArgumentException {

        NaturalLanguageTemplate naturalLanguageTemplate =
                this.naturalLanguageTemplateBoService.findNaturalLanguageTemplateByLanguageCodeTypeIdAndNluId(languageCode,
                proposition.getTypeId(), naturalLanguageUsageId);
        Map<String, Object> contextMap = new LinkedHashMap<String, Object>();
        for (PropositionParameter param : proposition.getParameters()) {
            String paramType = param.getParameterType();
            contextMap.put(paramType, param.getValue());
        }
        return templater.translate(naturalLanguageTemplate, contextMap);
    }

    @Override
    public NaturalLanguageTree translateNaturalLanguageTreeForProposition(String naturalLanguageUsageId, PropositionDefinition propositionDefinintion, String languageCode) throws RiceIllegalArgumentException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    

    ////
    //// context methods
    ////
    @Override
    public ContextDefinition createContext(ContextDefinition contextDefinition) throws RiceIllegalArgumentException {
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
    }
}
