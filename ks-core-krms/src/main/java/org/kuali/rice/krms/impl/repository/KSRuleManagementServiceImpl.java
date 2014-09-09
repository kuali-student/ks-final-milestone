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
package org.kuali.rice.krms.impl.repository;

import org.kuali.rice.core.api.exception.RiceIllegalArgumentException;
import org.kuali.rice.krms.api.repository.RuleManagementService;
import org.kuali.rice.krms.api.repository.NaturalLanguage;
import org.kuali.rice.krms.api.repository.TranslateBusinessMethods;
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.language.NaturalLanguageTemplaterContract;
import org.kuali.rice.krms.api.repository.reference.ReferenceObjectBinding;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.impl.repository.language.SimpleNaturalLanguageTemplater;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.kuali.rice.core.api.criteria.PredicateFactory.in;

/**
 * The implementation of {@link org.kuali.rice.krms.api.repository.RuleManagementService} operations facilitate management of rules and
 * associated information.
 *
 * @author Kuali Rice Team (rice.collab@kuali.org)
 */
public class KSRuleManagementServiceImpl extends RuleManagementServiceImpl implements RuleManagementService {

    private TranslateBusinessMethods translationBusinessMethods;
    private NaturalLanguageTemplaterContract templater = new SimpleNaturalLanguageTemplater();
    private AgendaBoService agendaBoService = new AgendaBoServiceImpl();

    @Override
    public List<ReferenceObjectBinding> findReferenceObjectBindingsByReferenceObjectIds(String referenceObjectReferenceDiscriminatorType,
                                                                                        List<String> referenceObjectIds)
            throws RiceIllegalArgumentException {
        if (referenceObjectReferenceDiscriminatorType == null) {
            throw new RiceIllegalArgumentException("reference binding object discriminator type must not be null");
        }

        if (referenceObjectIds == null) {
            throw new RiceIllegalArgumentException("reference object ids must not be null");
        }

        List<ReferenceObjectBinding> list = new ArrayList<ReferenceObjectBinding>();
        for(String referenceObjectId : referenceObjectIds){
            for (ReferenceObjectBinding binding : this.getReferenceObjectBindingBoService().findReferenceObjectBindingsByReferenceObject(referenceObjectId)) {
                if (binding.getReferenceDiscriminatorType().equals(referenceObjectReferenceDiscriminatorType)) {
                    list.add(binding);
                }
            }
        }

        return list;
    }

    @Override
    public List<NaturalLanguage> translateNaturalLanguageForObjects(String naturalLanguageUsageId, String typeId, List<String> krmsObjectIds,
                                                                    String languageCode)
            throws RiceIllegalArgumentException {

        List<NaturalLanguage> nlList = new ArrayList<NaturalLanguage>();
        for(String krmsObjectId : krmsObjectIds){
            String nl = this.getTranslateBusinessMethods().translateNaturalLanguageForObject(naturalLanguageUsageId, typeId, krmsObjectId, languageCode);

            NaturalLanguage.Builder nlBuilder = NaturalLanguage.Builder.create();
            nlBuilder.setKrmsObjectId(krmsObjectId);
            nlBuilder.setNaturalLanguage(nl);
            nlList.add(nlBuilder.build());
        }

        return nlList;
    }

    ////
    //// agenda item methods
    ////
    @Override
    public AgendaItemDefinition createAgendaItem(AgendaItemDefinition agendaItemDefinition) throws RiceIllegalArgumentException {
        return createUpdateAgendaItemIfNeeded(agendaItemDefinition);
    }

    @Override
    public void updateAgendaItem(AgendaItemDefinition agendaItemDefinition) throws RiceIllegalArgumentException {
        createUpdateAgendaItemIfNeeded(agendaItemDefinition);
    }

    private AgendaItemDefinition createUpdateAgendaItemIfNeeded(AgendaItemDefinition agendaItemDefinition) {
        this.crossCheckRuleId(agendaItemDefinition);
        this.crossCheckWhenTrueId(agendaItemDefinition);
        this.crossCheckWhenFalseId(agendaItemDefinition);
        this.crossCheckAlwaysId(agendaItemDefinition);
        this.crossCheckSubAgendaId(agendaItemDefinition);
        agendaItemDefinition = createUpdateRuleIfNeeded(agendaItemDefinition);
        agendaItemDefinition = createWhenTrueAgendaItemIfNeeded(agendaItemDefinition);
        agendaItemDefinition = createWhenFalseAgendaItemIfNeeded(agendaItemDefinition);
        agendaItemDefinition = createAlwaysAgendaItemIfNeeded(agendaItemDefinition);
        agendaItemDefinition = createSubAgendaIfNeeded(agendaItemDefinition);

        // Create or update
        if (agendaItemDefinition.getId() != null) {
            agendaBoService.updateAgendaItem(agendaItemDefinition);
        } else {
            agendaItemDefinition = agendaBoService.createAgendaItem(agendaItemDefinition);
        }

        return agendaItemDefinition;
    }

    private void crossCheckRuleId(AgendaItemDefinition agendItemDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (agendItemDefinition.getRuleId() != null && agendItemDefinition.getRule() != null) {
            if (!agendItemDefinition.getRuleId().equals(agendItemDefinition.getRule().getId())) {
                throw new RiceIllegalArgumentException("ruleId does not rule.getId" + agendItemDefinition.getRuleId() + " " + agendItemDefinition.getRule().getId());
            }
        }
    }

    private void crossCheckWhenTrueId(AgendaItemDefinition agendaItemDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (agendaItemDefinition.getWhenTrueId()!= null && agendaItemDefinition.getWhenTrue() != null) {
            if (!agendaItemDefinition.getWhenTrueId().equals(agendaItemDefinition.getWhenTrue().getId())) {
                throw new RiceIllegalArgumentException("when true id does not match " + agendaItemDefinition.getWhenTrueId() + " " + agendaItemDefinition.getWhenTrue().getId());
            }
        }
    }

    private void crossCheckWhenFalseId(AgendaItemDefinition agendItemDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (agendItemDefinition.getWhenFalseId()!= null && agendItemDefinition.getWhenFalse() != null) {
            if (!agendItemDefinition.getWhenFalseId().equals(agendItemDefinition.getWhenFalse().getId())) {
                throw new RiceIllegalArgumentException("when false id does not match " + agendItemDefinition.getWhenFalseId() + " " + agendItemDefinition.getWhenFalse().getId());
            }
        }
    }

    private void crossCheckAlwaysId(AgendaItemDefinition agendItemDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (agendItemDefinition.getAlwaysId()!= null && agendItemDefinition.getAlways() != null) {
            if (!agendItemDefinition.getAlwaysId().equals(agendItemDefinition.getAlways().getId())) {
                throw new RiceIllegalArgumentException("Always id does not match " + agendItemDefinition.getAlwaysId() + " " + agendItemDefinition.getAlways().getId());
            }
        }
    }

    private void crossCheckSubAgendaId(AgendaItemDefinition agendItemDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (agendItemDefinition.getSubAgendaId()!= null && agendItemDefinition.getSubAgenda() != null) {
            if (!agendItemDefinition.getSubAgendaId().equals(agendItemDefinition.getSubAgenda().getId())) {
                throw new RiceIllegalArgumentException("SubAgenda id does not match " + agendItemDefinition.getSubAgendaId() + " " + agendItemDefinition.getSubAgenda().getId());
            }
        }
    }

    private AgendaItemDefinition createUpdateRuleIfNeeded(AgendaItemDefinition agendaItemDefinition)
            throws RiceIllegalArgumentException {
        // no rule to create
        if (agendaItemDefinition.getRule() == null) {
            return agendaItemDefinition;
        }

        // create or update
        RuleDefinition rule = null;
        if (agendaItemDefinition.getRule().getId() != null) {
            this.updateRule(agendaItemDefinition.getRule());
            rule = this.getRule(agendaItemDefinition.getRule().getId());
        } else {
            rule = this.createRule(agendaItemDefinition.getRule());
        }

        AgendaItemDefinition.Builder agendaItemBuilder = AgendaItemDefinition.Builder.create(agendaItemDefinition);
        RuleDefinition.Builder ruleBuilder = RuleDefinition.Builder.create(rule);
        agendaItemBuilder.setRule(ruleBuilder);
        agendaItemBuilder.setRuleId(ruleBuilder.getId());
        return agendaItemBuilder.build();
    }

    private AgendaItemDefinition createWhenTrueAgendaItemIfNeeded(AgendaItemDefinition agendaItemDefinition) {
        // nothing to create
        if (agendaItemDefinition.getWhenTrue() == null) {
            return agendaItemDefinition;
        }

        // ojb does not take care of terms and termparameters, recursively loop thru agendaitems to update terms
        AgendaItemDefinition.Builder agendaItemBuilder = AgendaItemDefinition.Builder.create(agendaItemDefinition);
        AgendaItemDefinition subAgendaItem = this.createUpdateAgendaItemIfNeeded(agendaItemDefinition.getWhenTrue());
        agendaItemBuilder.setWhenTrue(AgendaItemDefinition.Builder.create(subAgendaItem));

        return agendaItemBuilder.build();
    }

    private AgendaItemDefinition createWhenFalseAgendaItemIfNeeded(AgendaItemDefinition agendaItemDefinition) {
        // nothing to create
        if (agendaItemDefinition.getWhenFalse() == null) {
            return agendaItemDefinition;
        }

        // ojb does not take care of terms and termparameters, recursively loop thru agendaitems to update terms
        AgendaItemDefinition.Builder agendaItemBuilder = AgendaItemDefinition.Builder.create(agendaItemDefinition);
        AgendaItemDefinition subAgendaItem = this.createUpdateAgendaItemIfNeeded(agendaItemDefinition.getWhenFalse());
        agendaItemBuilder.setWhenFalse(AgendaItemDefinition.Builder.create(subAgendaItem));
        return agendaItemBuilder.build();
    }


    private AgendaItemDefinition createAlwaysAgendaItemIfNeeded(AgendaItemDefinition agendaItemDefinition) {
        // nothing to create
        if (agendaItemDefinition.getAlways()== null) {
            return agendaItemDefinition;
        }

        // ojb does not take care of terms and termparameters, recursively loop thru agendaitems to update terms
        AgendaItemDefinition.Builder agendaItemBuilder = AgendaItemDefinition.Builder.create(agendaItemDefinition);
        AgendaItemDefinition subAgendaItem = this.createUpdateAgendaItemIfNeeded(agendaItemDefinition.getAlways());
        agendaItemBuilder.setAlways(AgendaItemDefinition.Builder.create(subAgendaItem));

        return agendaItemBuilder.build();
    }

    private AgendaItemDefinition createSubAgendaIfNeeded(AgendaItemDefinition agendaItemDefinition) {
        // nothing to create
        if (agendaItemDefinition.getSubAgenda() == null) {
            return agendaItemDefinition;
        }

        // ojb does not take care of terms and termparameters, recursively loop thru agendaitems to update terms
        AgendaItemDefinition.Builder agendaItemBuilder = AgendaItemDefinition.Builder.create(agendaItemDefinition);
        AgendaDefinition subAgenda = this.createAgenda(agendaItemDefinition.getSubAgenda());
        agendaItemBuilder.setSubAgenda(AgendaDefinition.Builder.create(subAgenda));
        agendaItemBuilder.setSubAgendaId(subAgenda.getId());

        return agendaItemBuilder.build();
    }

    /**
     * get the {@link NaturalLanguageTemplaterContract}
     * @return the {@link NaturalLanguageTemplaterContract}
     */
    public NaturalLanguageTemplaterContract getTemplater() {
        return templater;
    }

    /**
     * set the {@link NaturalLanguageTemplaterContract}
     * @param templater the {@link NaturalLanguageTemplaterContract} to set
     */
    public void setTemplater(NaturalLanguageTemplaterContract templater) {
        this.templater = templater;
    }

    /**
     * get the {@link TranslateBusinessMethods}
     * @return the current {@link TranslateBusinessMethods}
     */
    public TranslateBusinessMethods getTranslateBusinessMethods() {
        if(translationBusinessMethods  == null) {
            this.translationBusinessMethods  = new KSTranslationUtility(this, getTermRepositoryService(), this.templater);
        }
        return this.translationBusinessMethods ;
    }

    /**
     * get the {@link AgendaBoService}
     * @return the {@link AgendaBoService}
     */
    public AgendaBoService getAgendaBoService() {
        return agendaBoService;
    }

    /**
     * set the {@link AgendaBoService}
     * @param agendaBoService the {@link AgendaBoService} to set
     */
    public void setAgendaBoService(AgendaBoService agendaBoService) {
        this.agendaBoService = agendaBoService;
        super.setAgendaBoService(agendaBoService);
    }

}

