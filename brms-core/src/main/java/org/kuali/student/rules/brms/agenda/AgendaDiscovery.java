/*
 * Copyright 2007 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.rules.brms.agenda;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.kuali.student.rules.brms.core.service.FunctionalBusinessRuleManagementService;
import org.kuali.student.rules.internal.common.agenda.AgendaRequest;
import org.kuali.student.rules.internal.common.agenda.entity.Agenda;
import org.kuali.student.rules.internal.common.agenda.entity.AgendaType;
import org.kuali.student.rules.internal.common.agenda.entity.Anchor;
import org.kuali.student.rules.internal.common.agenda.entity.BusinessRuleSet;
import org.kuali.student.rules.internal.common.agenda.entity.BusinessRuleSetType;
import org.kuali.student.rules.internal.common.entity.BusinessRule;
import org.kuali.student.rules.rulesetexecution.RuleSetExecutorInternal;

public class AgendaDiscovery {

    private final static String AGENDA_RULE_ID = "AgendaRuleSetId";
    private final static String AGENDA_FILE = "/agenda.drl";

    private FunctionalBusinessRuleManagementService ruleMgmtService;
    private RuleSetExecutorInternal ruleSetExecutor;

    /**
     * Gets an agenda.
     * 
     * @param request An agenda request
     * @param anchor An anchor
     * @return An agenda
     */
    public Agenda getAgenda(AgendaRequest request, Anchor anchor) {
        Iterator it = executeRule(request);
        // Retrieve agenda type from rule set based on AgendaRequest
        // Iterate through returned rule engine objects
        // This should not be done in production
        List<BusinessRuleSetType> ruleTypes = new ArrayList<BusinessRuleSetType>();
        AgendaType agendaType = null;
        while (it != null && it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof AgendaType) {
                agendaType = (AgendaType) obj;
                break;
            }
        }
        // Create actual agenda with actual rules
        Agenda agenda = new Agenda(agendaType.getName(), agendaType);
        ruleTypes = agendaType.getBusinessRuleTypes();
        // retrieveRulesFromDatabase( agenda, anchor );
        List<BusinessRule> businessRules = ruleMgmtService.retrieveBusinessRules(ruleTypes, anchor.getName());

        for (Iterator<BusinessRule> iter = businessRules.iterator(); iter.hasNext();) {
            BusinessRule businessRule = iter.next();
            // for now, we have BusinessRuleSetType in Agenda object in case we need BusinessRuleSetType to determine
            // rules execution sequence when executing rules
            agenda.addBusinessRule(new BusinessRuleSet(businessRule.getCompiledID(), businessRule.getName(),
                    new BusinessRuleSetType(businessRule.getBusinessRuleType(), businessRule.getBusinessRuleType()),
                    businessRule.createAdjustedRuleFunctionString()));
        }

        return agenda;
    }

    /**
     * Executes the agenda rules with <code>fact</code>.
     * 
     * @param fact A fact to execute agenda rules with
     * @return An iterator of evaluated facts
     */
    private Iterator executeRule(Object fact) { 
        this.ruleSetExecutor.addRuleSet(AGENDA_RULE_ID, loadAgenda());
        return (Iterator) this.ruleSetExecutor.execute(AGENDA_RULE_ID, Arrays.asList(fact));
    }
    
    /**
     * Loads the agenda rule set source file.
     * 
     * @return An agenda source
     */
    private Reader loadAgenda() {
        InputStream is = AgendaDiscovery.class.getResourceAsStream(AGENDA_FILE);
        return new InputStreamReader(is);
    }
    
    /**
     * @return the ruleMgmtService
     */
    public FunctionalBusinessRuleManagementService getRuleMgmtService() {
        return ruleMgmtService;
    }

    /**
     * @param ruleMgmtService
     *            the ruleMgmtService to set
     */
    public void setRuleMgmtService(FunctionalBusinessRuleManagementService ruleMgmtService) {
        this.ruleMgmtService = ruleMgmtService;
    }

    /**
     * Gets the rule execution engine.
     * 
     * @return A rule set executor
     */
    public RuleSetExecutorInternal getRuleSetExecutor() {
        return this.ruleSetExecutor;
    }

    /**
     * Sets the rule execution engine.
     * 
     * @param ruleSetExecutor The rule execution engine
     */
    public void setRuleSetExecutor(RuleSetExecutorInternal ruleSetExecutor) {
        this.ruleSetExecutor = ruleSetExecutor;
    }

}
