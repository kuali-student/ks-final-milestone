/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import org.kuali.student.r2.core.population.service.decorators.PopulationServiceDecorator;

public class ProcessPocPopulationServiceDecorator extends PopulationServiceDecorator implements PopulationService {

    public ProcessPocPopulationServiceDecorator() {
    }

    public ProcessPocPopulationServiceDecorator(PopulationService nextDecorator) {
        this.setNextDecorator(nextDecorator);
        init();
    }

    private void init() {
        ContextInfo contextInfo = new ContextInfo();
        contextInfo.setPrincipalId("POC-Initializer");
        try {
 
            final String ALL_STUDENTS = PopulationServiceConstants.EVERYONE_POPULATION_KEY;
            final String SUMMER_ONLY_STUDENTS = PopulationServiceConstants.SUMMER_ONLY_STUDENTS_POPULATION_KEY;
            final String SENIOR_ONLY_STUDENTS = "kuali.population.senior.only.student";
            final String ATHLETES_ONLY_STUDENTS = "kuali.population.athletes.only.student";

            PopulationInfo everyonePop = new PopulationInfo();
            everyonePop.setId(ALL_STUDENTS);
            everyonePop.setTypeKey(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY);
            everyonePop.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
            everyonePop.setName("Everyone");
            everyonePop.setDescr(new RichTextHelper ().fromPlain("Everyone"));
            everyonePop = this.createPopulation(everyonePop.getTypeKey(), everyonePop, contextInfo);
            
            PopulationRuleInfo everyoneRule = new PopulationRuleInfo ();
            everyoneRule.setId(ALL_STUDENTS);
            everyoneRule.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY);
            everyoneRule.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
            for (ProcessPocPersonEnum personEnum : ProcessPocPersonEnum.values()) {
                everyoneRule.getPersonIds().add("" + personEnum.getPersonId());
            }
            everyoneRule = this.createPopulationRule (everyoneRule.getTypeKey(), everyoneRule, contextInfo);
            this.applyPopulationRuleToPopulation(everyoneRule.getId(), everyonePop.getId(), contextInfo);
            

            PopulationInfo summerPop = new PopulationInfo();
            summerPop.setId(SUMMER_ONLY_STUDENTS);
            everyonePop.setTypeKey(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY);
            everyonePop.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
            summerPop.setName("Summer only students");
            everyonePop.setDescr(new RichTextHelper ().fromPlain("Students who are only allowed to register during the summer"));
            summerPop =this.createPopulation(summerPop.getTypeKey(), summerPop, contextInfo);
            
            PopulationRuleInfo summerRule = new PopulationRuleInfo ();
            summerRule.setId(SUMMER_ONLY_STUDENTS);
            summerRule.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY);
            summerRule.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
            summerRule.getPersonIds().add("" + ProcessPocPersonEnum.STUDENT8.getPersonId());
            summerRule = this.createPopulationRule (summerRule.getTypeKey(), summerRule, contextInfo);
            this.applyPopulationRuleToPopulation(summerRule.getId(), summerPop.getId(), contextInfo);

            PopulationInfo seniorPop = new PopulationInfo();
            seniorPop.setId(SENIOR_ONLY_STUDENTS);
            everyonePop.setTypeKey(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY);
            everyonePop.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
            seniorPop.setName("Senior students");
            everyonePop.setDescr(new RichTextHelper ().fromPlain("Senior citizens who can take classes for free"));
            createPopulation(seniorPop.getTypeKey(), seniorPop, contextInfo);

            PopulationRuleInfo seniorRule = new PopulationRuleInfo ();
            seniorRule.setId(SUMMER_ONLY_STUDENTS);
            seniorRule.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY);
            seniorRule.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
            seniorRule.getPersonIds().add("" + ProcessPocPersonEnum.STUDENT8.getPersonId());
            seniorRule = this.createPopulationRule (seniorRule.getTypeKey(), seniorRule, contextInfo);
            this.applyPopulationRuleToPopulation(seniorRule.getId(), seniorPop.getId(), contextInfo);

            PopulationInfo athletePop = new PopulationInfo();
            athletePop.setId(ATHLETES_ONLY_STUDENTS);
            everyonePop.setTypeKey(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY);
            everyonePop.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
            athletePop.setName("Athletes");
            everyonePop.setDescr(new RichTextHelper ().fromPlain("NCAA athletes"));
            createPopulation(athletePop.getTypeKey(), athletePop, contextInfo);

            PopulationRuleInfo athleteRule = new PopulationRuleInfo ();
            athleteRule.setId(SUMMER_ONLY_STUDENTS);
            athleteRule.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY);
            athleteRule.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
            athleteRule.getPersonIds().add("" + ProcessPocPersonEnum.STUDENT8.getPersonId());
            athleteRule = this.createPopulationRule (athleteRule.getTypeKey(), athleteRule, contextInfo);
            this.applyPopulationRuleToPopulation(athleteRule.getId(), athletePop.getId(), contextInfo);            

        } catch (Exception e) {
            throw new RuntimeException("Error initializing", e);
        }
    }


}
