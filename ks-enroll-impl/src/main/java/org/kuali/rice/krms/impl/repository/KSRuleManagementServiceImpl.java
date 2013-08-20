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
import org.kuali.rice.krms.api.repository.agenda.AgendaDefinition;
import org.kuali.rice.krms.api.repository.agenda.AgendaItemDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionDefinition;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameter;
import org.kuali.rice.krms.api.repository.proposition.PropositionParameterType;
import org.kuali.rice.krms.api.repository.proposition.PropositionType;
import org.kuali.rice.krms.api.repository.rule.RuleDefinition;
import org.kuali.rice.krms.api.repository.term.TermDefinition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Overridden class to handle missing service methods in rice release.
 *
 * @author Kuali Student Team
 */
public class KSRuleManagementServiceImpl extends RuleManagementServiceImpl {

    @Override
    public AgendaItemDefinition createAgendaItem(AgendaItemDefinition agendaItemDefinition) throws RiceIllegalArgumentException {
        agendaItemDefinition = createUpdateAgendaItemIfNeeded(agendaItemDefinition);
        return getAgendaBoService().createAgendaItem(agendaItemDefinition);
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
        return createSubAgendaIfNeeded(agendaItemDefinition);
    }

    private void crossCheckRuleId(AgendaItemDefinition agendatemDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (agendatemDefinition.getRuleId() != null && agendatemDefinition.getRule() != null) {
            if (!agendatemDefinition.getRuleId().equals(agendatemDefinition.getRule().getId())) {
                throw new RiceIllegalArgumentException("ruleId does not rule.getId" + agendatemDefinition.getRuleId() + " " + agendatemDefinition.getRule().getId());
            }
        }
    }

    private void crossCheckWhenTrueId(AgendaItemDefinition agendatemDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (agendatemDefinition.getWhenTrueId() != null && agendatemDefinition.getWhenTrue() != null) {
            if (!agendatemDefinition.getWhenTrueId().equals(agendatemDefinition.getWhenTrue().getId())) {
                throw new RiceIllegalArgumentException("when true id does not match " + agendatemDefinition.getWhenTrueId() + " " + agendatemDefinition.getWhenTrue().getId());
            }
        }
    }

    private void crossCheckWhenFalseId(AgendaItemDefinition agendatemDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (agendatemDefinition.getWhenFalseId() != null && agendatemDefinition.getWhenFalse() != null) {
            if (!agendatemDefinition.getWhenFalseId().equals(agendatemDefinition.getWhenFalse().getId())) {
                throw new RiceIllegalArgumentException("when false id does not match " + agendatemDefinition.getWhenFalseId() + " " + agendatemDefinition.getWhenFalse().getId());
            }
        }
    }

    private void crossCheckAlwaysId(AgendaItemDefinition agendatemDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (agendatemDefinition.getAlwaysId() != null && agendatemDefinition.getAlways() != null) {
            if (!agendatemDefinition.getAlwaysId().equals(agendatemDefinition.getAlways().getId())) {
                throw new RiceIllegalArgumentException("Always id does not match " + agendatemDefinition.getAlwaysId() + " " + agendatemDefinition.getAlways().getId());
            }
        }
    }

    private void crossCheckSubAgendaId(AgendaItemDefinition agendatemDefinition)
            throws RiceIllegalArgumentException {
        // if both are set they better match
        if (agendatemDefinition.getSubAgendaId() != null && agendatemDefinition.getSubAgenda() != null) {
            if (!agendatemDefinition.getSubAgendaId().equals(agendatemDefinition.getSubAgenda().getId())) {
                throw new RiceIllegalArgumentException("SubAgenda id does not match " + agendatemDefinition.getSubAgendaId() + " " + agendatemDefinition.getSubAgenda().getId());
            }
        }
    }

    private AgendaItemDefinition createUpdateRuleIfNeeded(AgendaItemDefinition agendaItemDefinition)
            throws RiceIllegalArgumentException {
        // no rule to create
        if (agendaItemDefinition.getRule() == null) {
            return agendaItemDefinition;
        }
        // update
        if (agendaItemDefinition.getRule().getId() != null) {
            this.updateRule(agendaItemDefinition.getRule());
            RuleDefinition rule = this.getRule(agendaItemDefinition.getRule().getId());
            AgendaItemDefinition.Builder agendaItemBuilder = AgendaItemDefinition.Builder.create(agendaItemDefinition);
            agendaItemBuilder.setRule(RuleDefinition.Builder.create(rule));
            agendaItemBuilder.setRuleId(rule.getId());
            return agendaItemBuilder.build();
        }
        AgendaItemDefinition.Builder agendaItemBuilder = AgendaItemDefinition.Builder.create(agendaItemDefinition);
        RuleDefinition ruleDefinition = this.createRule(agendaItemDefinition.getRule());
        RuleDefinition.Builder ruleBuilder = RuleDefinition.Builder.create(ruleDefinition);
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
        AgendaItemDefinition subAgendaITem = this.createUpdateAgendaItemIfNeeded(agendaItemDefinition.getWhenTrue());
        agendaItemBuilder.setWhenTrue(AgendaItemDefinition.Builder.create(subAgendaITem));
        agendaItemBuilder.setWhenTrueId(subAgendaITem.getId());
        return agendaItemBuilder.build();
    }


    private AgendaItemDefinition createWhenFalseAgendaItemIfNeeded(AgendaItemDefinition agendaItemDefinition) {
        // nothing to create
        if (agendaItemDefinition.getWhenFalse() == null) {
            return agendaItemDefinition;
        }
        // ojb does not take care of terms and termparameters, recursively loop thru agendaitems to update terms
        AgendaItemDefinition.Builder agendaItemBuilder = AgendaItemDefinition.Builder.create(agendaItemDefinition);
        AgendaItemDefinition subAgendaITem = this.createUpdateAgendaItemIfNeeded(agendaItemDefinition.getWhenFalse());
        agendaItemBuilder.setWhenFalse(AgendaItemDefinition.Builder.create(subAgendaITem));
        agendaItemBuilder.setWhenFalseId(subAgendaITem.getId());
        return agendaItemBuilder.build();
    }


    private AgendaItemDefinition createAlwaysAgendaItemIfNeeded(AgendaItemDefinition agendaItemDefinition) {
        // nothing to create
        if (agendaItemDefinition.getAlways() == null) {
            return agendaItemDefinition;
        }
        // ojb does not take care of terms and termparameters, recursively loop thru agendaitems to update terms
        AgendaItemDefinition.Builder agendaItemBuilder = AgendaItemDefinition.Builder.create(agendaItemDefinition);
        AgendaItemDefinition subAgendaITem = this.createUpdateAgendaItemIfNeeded(agendaItemDefinition.getAlways());
        agendaItemBuilder.setAlways(AgendaItemDefinition.Builder.create(subAgendaITem));
        agendaItemBuilder.setAlwaysId(subAgendaITem.getId());
        return agendaItemBuilder.build();
    }


    private AgendaItemDefinition createSubAgendaIfNeeded(AgendaItemDefinition agendaItemDefinition) {
        // nothing to create
        if (agendaItemDefinition.getSubAgenda() == null) {
            return agendaItemDefinition;
        }
        // ojb will take care of it if it has already been created
        if (agendaItemDefinition.getSubAgenda().getId() != null) {
            return agendaItemDefinition;
        }
        AgendaItemDefinition.Builder agendaItemBuilder = AgendaItemDefinition.Builder.create(agendaItemDefinition);
        AgendaDefinition subAgenda = this.createAgenda(agendaItemDefinition.getSubAgenda());
        agendaItemBuilder.setSubAgenda(AgendaDefinition.Builder.create(subAgenda));
        agendaItemBuilder.setSubAgendaId(subAgenda.getId());
        return agendaItemBuilder.build();
    }

    @Override
    public AgendaItemDefinition getAgendaItem(String id) throws RiceIllegalArgumentException {
        AgendaItemDefinition agendaItem = getAgendaBoService().getAgendaItemById(id);
        return setTermValuesForAgendaItem(agendaItem).build();
    }

    private AgendaItemDefinition.Builder setTermValuesForAgendaItem(AgendaItemDefinition agendaItem) {
        if (agendaItem == null){
            return null;
        }

        AgendaItemDefinition.Builder itemBuiler = AgendaItemDefinition.Builder.create(agendaItem);
        if (itemBuiler.getRule() != null) {
            // Set the termValues on the agenda item.
            PropositionDefinition proposition = agendaItem.getRule().getProposition();
            if (proposition != null) {
                proposition = this.orderCompoundPropositionsIfNeeded(proposition);
                itemBuiler.getRule().setProposition(this.replaceTermValues(proposition));
            }
        }

        // Recursively set the term values on child agenda items.
        itemBuiler.setWhenTrue(setTermValuesForAgendaItem(agendaItem.getWhenTrue()));
        itemBuiler.setWhenFalse(setTermValuesForAgendaItem(agendaItem.getWhenFalse()));
        itemBuiler.setAlways(setTermValuesForAgendaItem(agendaItem.getAlways()));

        return itemBuiler;
    }

    private PropositionDefinition orderCompoundPropositionsIfNeeded(PropositionDefinition prop) {
        if (!prop.getPropositionTypeCode().equals(PropositionType.COMPOUND.getCode())) {
            return prop;
        }
        if (prop.getCompoundComponents() == null) {
            return prop;
        }
        if (prop.getCompoundComponents().size() <= 1) {
            return prop;
        }
        PropositionDefinition.Builder propBldr = PropositionDefinition.Builder.create(prop);
        List<PropositionDefinition> childProps = new ArrayList<PropositionDefinition>(prop.getCompoundComponents());
        Collections.sort(childProps, new CompoundPropositionComparator());
        List<PropositionDefinition.Builder> childPropBldrs = new ArrayList<PropositionDefinition.Builder>(childProps.size());
        for (PropositionDefinition chidProp : childProps) {
            PropositionDefinition.Builder childPropBlder = PropositionDefinition.Builder.create(chidProp);
            childPropBldrs.add(childPropBlder);
        }
        propBldr.setCompoundComponents(childPropBldrs);
        return propBldr.build();
    }

    private PropositionDefinition.Builder replaceTermValues(PropositionDefinition proposition) {

        PropositionDefinition.Builder bldr = PropositionDefinition.Builder.create(proposition);

        // recursively add termValues to child propositions.
        if (!PropositionType.SIMPLE.getCode().equalsIgnoreCase(proposition.getPropositionTypeCode())) {
            List<PropositionDefinition.Builder> cmpdProps = new ArrayList<PropositionDefinition.Builder>();
            for (PropositionDefinition cmpdProp : proposition.getCompoundComponents()) {
                cmpdProps.add(replaceTermValues(cmpdProp));
            }
            bldr.setCompoundComponents(cmpdProps);
            return bldr;
        }
        // that have parameters
        if (proposition.getParameters() == null) {
            return bldr;
        }
        if (proposition.getParameters().isEmpty()) {
            return bldr;
        }
        boolean found = false;
        List<PropositionParameter.Builder> params = new ArrayList<PropositionParameter.Builder>(proposition.getParameters().size());
        for (PropositionParameter param : proposition.getParameters()) {
            if (!PropositionParameterType.TERM.getCode().equalsIgnoreCase(param.getParameterType())) {
                params.add(PropositionParameter.Builder.create(param));
                continue;
            }
            // inflate the termValue
            found = true;
            TermDefinition termValue = getTermRepositoryService().getTerm(param.getValue());
            PropositionParameter.Builder parmbldr = PropositionParameter.Builder.create(param);
            parmbldr.setTermValue(termValue);
            params.add(parmbldr);
        }
        if (!found) {
            return bldr;
        }

        bldr.setParameters(params);
        return bldr;
    }

    @Override
    public void updateAgendaItem(AgendaItemDefinition agendaItemDefinition) throws RiceIllegalArgumentException {
        agendaItemDefinition = createUpdateAgendaItemIfNeeded(agendaItemDefinition);
        getAgendaBoService().updateAgendaItem(agendaItemDefinition);
    }
}
