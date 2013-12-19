/**
 * Copyright 2013 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * Created by mahtabme on 12/17/13
 */
package org.kuali.student.enrollment.class2.population;

import org.kuali.student.common.test.AbstractMockServicesAwareDataLoader;
import org.kuali.student.poc.rules.population.ExplicitlyListedPersonsPopulationRuleInfo;
import org.kuali.student.poc.rules.population.PopulationPocPersonEnum;
import org.kuali.student.poc.rules.population.PopulationPocStudentEnum;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * This class loads data into a Population Service
 *
 * @author Mezba Mahtab
 */
public class PopulationServiceDataLoader extends AbstractMockServicesAwareDataLoader {

    /////////////////////
    // Data Variables
    /////////////////////

    @Resource
    private PopulationService populationService;

    private String firstYearStudentPopulationId;
    private String freshmenStudentPopulationId;
    private String instructorPopulationId;
    private String studentPopulationId;
    private String undergraduteStudentPopulationId;

    /////////////////////////
    // Getters and Setters
    /////////////////////////

    public PopulationService getPopulationService() {
        return populationService;
    }

    public void setPopulationService(PopulationService populationService) {
        this.populationService = populationService;
    }

    public String getFirstYearStudentPopulationId() {
        return firstYearStudentPopulationId;
    }

    public String getFreshmenStudentPopulationId() {
        return freshmenStudentPopulationId;
    }

    public String getInstructorPopulationId() {
        return instructorPopulationId;
    }

    public String getStudentPopulationId() {
        return studentPopulationId;
    }

    public String getUndergraduteStudentPopulationId() {
        return undergraduteStudentPopulationId;
    }

    ////////////////////////////
    // Functionals
    ////////////////////////////

    @Override
    public void initializeData() throws Exception {

        // First year students
        PopulationInfo pop1 = createPopulationInfo
                ("First year students",
                        "A list of students in their first year of study in their program",
                        PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY,
                        PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY,
                        new ArrayList<String>(),
                        Boolean.FALSE,
                        Boolean.TRUE);
        pop1 = populationService.createPopulation(pop1, context);
        ExplicitlyListedPersonsPopulationRuleInfo rule1e =
                createExplicitlyListedPersonsPopulationRuleInfo
                        ("First year students",
                                "A list of students in their first year of study in their program",
                                PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        rule1e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT1);
        rule1e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT4);
        rule1e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT5);
        rule1e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT7);
        rule1e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT10);
        PopulationRuleInfo rule1 = populationService.createPopulationRule(rule1e, context);
        populationService.applyPopulationRuleToPopulation(rule1.getId(), pop1.getId(), context);
        firstYearStudentPopulationId = pop1.getId();

        // Students
        PopulationInfo pop2 = createPopulationInfo
                ("Students",
                        "A list of students",
                        PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY,
                        PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY,
                        new ArrayList<String>(),
                        Boolean.FALSE,
                        Boolean.TRUE);
        pop2 = populationService.createPopulation(pop2, context);
        ExplicitlyListedPersonsPopulationRuleInfo rule2e =
                createExplicitlyListedPersonsPopulationRuleInfo
                        ("Students",
                                "A list of students",
                                PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT1);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT2);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT3);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT4);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT5);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT6);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT7);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT8);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT9);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT10);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT11);
        rule2e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT12);
        PopulationRuleInfo rule2 = populationService.createPopulationRule(rule2e, context);
        populationService.applyPopulationRuleToPopulation(rule2.getId(), pop2.getId(), context);
        studentPopulationId = pop2.getId();

        // Instructors
        PopulationInfo pop3 = createPopulationInfo
                ("Instructors",
                        "A list of instructors",
                        PopulationServiceConstants.POPULATION_TYPE_KEY,
                        PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY,
                        new ArrayList<String>(),
                        Boolean.FALSE,
                        Boolean.TRUE);
        pop3 = populationService.createPopulation(pop3, context);
        ExplicitlyListedPersonsPopulationRuleInfo rule3e =
                createExplicitlyListedPersonsPopulationRuleInfo
                        ("Instructors",
                                "A list of instructors",
                                PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        rule3e.addPersonIdOfPersonToList(PopulationPocPersonEnum.INSTRUCTOR1);
        rule3e.addPersonIdOfPersonToList(PopulationPocPersonEnum.INSTRUCTOR2);
        PopulationRuleInfo rule3 = populationService.createPopulationRule(rule3e, context);
        populationService.applyPopulationRuleToPopulation(rule3.getId(), pop3.getId(), context);
        instructorPopulationId = pop3.getId();

        // Undergraduate Students
        PopulationInfo pop4 = createPopulationInfo
                ("Undergraduate Students",
                        "A list of undergraduate students",
                        PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY,
                        PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY,
                        new ArrayList<String>(),
                        Boolean.FALSE,
                        Boolean.TRUE);
        pop4 = populationService.createPopulation(pop4, context);
        ExplicitlyListedPersonsPopulationRuleInfo rule4e =
                createExplicitlyListedPersonsPopulationRuleInfo
                        ("Undergraduate Students",
                                "A list of undergraduate students",
                                PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        rule4e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT1);
        rule4e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT8);
        rule4e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT10);
        rule4e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT11);
        rule4e.addPersonIdOfPersonToList(PopulationPocStudentEnum.STUDENT12);
        PopulationRuleInfo rule4 = populationService.createPopulationRule(rule4e, context);
        populationService.applyPopulationRuleToPopulation(rule4.getId(), pop4.getId(), context);
        undergraduteStudentPopulationId = pop4.getId();

        // Undergraduate Freshmen Students
        PopulationInfo pop5 = createPopulationInfo
                ("Undergraduate Freshmen Students",
                        "A list of undergraduate freshmen students",
                        PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY,
                        PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY,
                        new ArrayList<String>(),
                        Boolean.FALSE,
                        Boolean.FALSE);
        pop5 = populationService.createPopulation(pop5, context);

        List<String> childPopulationRuleIds = new ArrayList<String> ();
        childPopulationRuleIds.add(firstYearStudentPopulationId);
        childPopulationRuleIds.add(undergraduteStudentPopulationId);
        String refPopulationId = null;

        PopulationRuleInfo rule5 = createPopulationRuleInfo
                ("Undergraduate Freshmen Students",
                        "A list of undergraduate freshmen students",
                        PopulationServiceConstants.POPULATION_RULE_TYPE_INTERSECTION_KEY,
                        PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY,
                        new ArrayList<String>(),
                        new ArrayList<String>(),
                        new ArrayList<String>(),
                        childPopulationRuleIds,
                        refPopulationId,
                        new ArrayList<String>(),
                        Boolean.FALSE,
                        Boolean.FALSE);
        rule5 = populationService.createPopulationRule(rule5, context);
        populationService.applyPopulationRuleToPopulation(rule5.getId(), pop5.getId(), context);
        freshmenStudentPopulationId = pop5.getId();

    }

    /////////////////////////
    // Helper Methods
    /////////////////////////

    protected PopulationInfo createPopulationInfo
            (String name,
             String descriptionPlain,
             String typeKey,
             String stateKey,
             List<String> sortOrderTypeKeys,
             Boolean variesByTime,
             Boolean supportsGetMembers) {
        PopulationInfo populationInfo = new PopulationInfo();
        populationInfo.setName(name);
        populationInfo.setDescr(new RichTextHelper().fromPlain(descriptionPlain));
        populationInfo.setTypeKey(typeKey);
        populationInfo.setStateKey(stateKey);
        populationInfo.setSortOrderTypeKeys(sortOrderTypeKeys);
        populationInfo.setVariesByTime(variesByTime);
        populationInfo.setSupportsGetMembers(supportsGetMembers);
        return populationInfo;
    }

    protected ExplicitlyListedPersonsPopulationRuleInfo createExplicitlyListedPersonsPopulationRuleInfo
            (String name,
             String descriptionPlain,
             String stateKey) {
        ExplicitlyListedPersonsPopulationRuleInfo ruleInfo =
                new ExplicitlyListedPersonsPopulationRuleInfo();
        ruleInfo.setName(name);
        ruleInfo.setDescr(new RichTextHelper().fromPlain(descriptionPlain));
        ruleInfo.setTypeKey(PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY);
        ruleInfo.setStateKey(stateKey);
        ruleInfo.setVariesByTime(Boolean.FALSE);
        ruleInfo.setSupportsGetMembers(Boolean.TRUE);
        return ruleInfo;
    }

    protected PopulationRuleInfo createPopulationRuleInfo
            (String name,
             String descriptionPlain,
             String typeKey,
             String stateKey,
             List<String> agendaIds,
             List<String> groupIds,
             List<String> personIds,
             List<String> childPopulationIds,
             String referencePopulationId,
             List<String> sortOrderTypeKeys,
             Boolean variesByTime,
             Boolean supportsGetMembers) {
        PopulationRuleInfo ruleInfo = new PopulationRuleInfo();
        ruleInfo.setName(name);
        ruleInfo.setDescr(new RichTextHelper().fromPlain(descriptionPlain));
        ruleInfo.setTypeKey(typeKey);
        ruleInfo.setStateKey(stateKey);
        ruleInfo.setAgendaIds(agendaIds);
        ruleInfo.setGroupIds(groupIds);
        ruleInfo.setPersonIds(personIds);
        ruleInfo.setChildPopulationIds(childPopulationIds);
        ruleInfo.setReferencePopulationId(referencePopulationId);
        ruleInfo.setSortOrderTypeKeys(sortOrderTypeKeys);
        ruleInfo.setVariesByTime(variesByTime);
        ruleInfo.setSupportsGetMembers(supportsGetMembers);
        return ruleInfo;
    }
}
