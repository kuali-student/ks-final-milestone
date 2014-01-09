/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.kuali.student.enrollment.class2.population.service.decorators.PopulationServiceDecorator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.core.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.dto.PopulationRuleInfo;
import org.kuali.student.r2.core.population.service.PopulationService;

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

        _createPop(PopulationServiceConstants.EVERYONE_POPULATION_KEY, "Everyone", "Everyone is included in this population",
                PopulationServiceConstants.POPULATION_RULE_TYPE_EVERYONE_KEY, "", "", false, false, contextInfo);
        _createPop(PopulationServiceConstants.ATHLETES_POPULATION_KEY, "Athletes", "Active NCAA qualified athletes",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.DISABLED_POPULATION_KEY, "Disabled", "Students with a disability",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);

        _createPop(PopulationServiceConstants.CURRENT_STUDENTS_POPULATION_KEY, "Current Students", "Currently Enrolled Students ",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.CONTINUING_STUDENTS_POPULATION_KEY, "Continuing Students",
                "Currently Enrolled Students who are expected to continue at the school",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.NEW_STUDENTS_POPULATION_KEY, "New Students", "Newly Admitted Students",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.RETURNING_STUDENTS_POPULATION_KEY, "Returning Students",
                "Previously Admitted Students who left and are now returning",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);

        _createPop(PopulationServiceConstants.FRESHMAN_POPULATION_KEY, "Freshman", "Class level or year of study first year",
                PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, "", "", true, false, contextInfo);
        _createPop(PopulationServiceConstants.SOPHOMORE_POPULATION_KEY, "Sophomore", "Class level or year of study second year",
                PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, "", "", true, false, contextInfo);
        _createPop(PopulationServiceConstants.JUNIOR_POPULATION_KEY, "Junior", "Class level or year of study thrird year",
                PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, "", "", true, false, contextInfo);
        _createPop(PopulationServiceConstants.SENIOR_POPULATION_KEY, "Senior",
                "Class level or year of study fourth and typically final year",
                PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, "", "", true, false, contextInfo);
        _createPop(PopulationServiceConstants.UPPERCLASS_POPULATION_KEY, "Upperclass", "Undergraduate but not freshmen",
                PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, "", "", true, false, contextInfo);
        _createPop(PopulationServiceConstants.FINAL_TERM_SENIORS_POPULATION_KEY, "Final Term Seniors",
                "Students who have enough credits to graduate this semester",
                PopulationServiceConstants.POPULATION_RULE_TYPE_RULE_KEY, "", "", true, false, contextInfo);

        _createPop(PopulationServiceConstants.GRADUATE_POPULATION_KEY, "Graduate",
                "Graduate level student, working on masters or higher", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY,
                "", "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.PROFESSIONAL_POPULATION_KEY, "Professional",
                "Students in one of the professional schools", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "",
                false, true, contextInfo);
        _createPop(PopulationServiceConstants.LAW_SCHOOL_STUDENTS_POPULATION_KEY, "Law School Students",
                "Students in the law school", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true,
                contextInfo);
        _createPop(PopulationServiceConstants.UNDERGRADUATE_POPULATION_KEY, "Undergraduate",
                "Undergraduates working on a bachelors degree", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "",
                false, true, contextInfo);


        _createPop(PopulationServiceConstants.FIN_AID_RECIPIENTS_POPULATION_KEY, "Fin Aid Recipients",
                "Everyone who has applied for and receives some form of financial aid",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.IN_A_DEGREE_GRANTING_PROGRAM_POPULATION_KEY, "In a degree granting program",
                "Everyone who is in a degree granting program", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "",
                false, true, contextInfo);
        _createPop(PopulationServiceConstants.NOT_IN_A_DEGREE_GRANTING_PROGRAM_POPULATION_KEY, "Not in a degree granting program",
                "Everyone who is NOT in a degree granting program", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "",
                "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.INTERNATIONAL_STUDENT_POPULATION_KEY, "International Student",
                "International Students", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true,
                contextInfo);
        _createPop(PopulationServiceConstants.SUMMER_ONLY_STUDENT_POPULATION_KEY, "Summer Only Student",
                "Student only admitted for the summer session", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "2155",
                "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.IN_A_PART_TIME_PROGRAM_POPULATION_KEY, "In a part-time program",
                "Everyone who is in a degree granting program", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "",
                false, true, contextInfo);

        _createPop(PopulationServiceConstants.NORTH_CAMPUS_STUDENTS_POPULATION_KEY, "North Campus Students",
                "Students admitted to the North campus ", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "",
                false, true, contextInfo);
        _createPop(PopulationServiceConstants.SOUTH_CAMPUS_STUDENTS_POPULATION_KEY, "South Campus Students",
                "Students admitted to the South campus", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false,
                true, contextInfo);

        _createPop(PopulationServiceConstants.SENIOR_CITIZENS_POPULATION_KEY, "Senior Citizens",
                "Senior Citizens as part of the Access Program", PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "",
                "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.TUITION_EXEMPT_EMPLOYEES_POPULATION_KEY, "Tuition Exempt Employees",
                "Faculty and staff of who are taking courses under the tuition exemption program",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.TUITION_EXEMPT_OTHERS_POPULATION_KEY, "Tuition Exempt Others",
                "Other students tuition exempt students such as National Guard and other state employees",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);

        _createPop(PopulationServiceConstants.ODD_NUMBERED_POPULATION_KEY, "Odd Numbered",
                "Students whose last digit of their id is odd, 1,3,5,7,9",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);
        _createPop(PopulationServiceConstants.EVEN_NUMBERED_POPULATION_KEY, "Even Numbered",
                "Students whose last digit of their id is even 0,2,4,6,8",
                PopulationServiceConstants.POPULATION_RULE_TYPE_PERSON_KEY, "", "", false, true, contextInfo);



    }

    private PopulationInfo _createPop(String id, String name, String descr, String ruleTypeKey, String personIds, String ruleId,
            boolean variesByTime, boolean supportsGetMembers, ContextInfo contextInfo) {
        PopulationInfo pop = new PopulationInfo();
        pop.setId(id);
        pop.setTypeKey(PopulationServiceConstants.POPULATION_STUDENT_TYPE_KEY);
        pop.setStateKey(PopulationServiceConstants.POPULATION_ACTIVE_STATE_KEY);
        pop.setName(name);
        pop.setDescr(new RichTextHelper().fromPlain(descr));
        try {
            pop = createPopulation(pop.getTypeKey(), pop, contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected during initilaization", ex);
        }
        pop.setSupportsGetMembers(supportsGetMembers);
        pop.setVariesByTime(variesByTime);

        PopulationRuleInfo rule = new PopulationRuleInfo();
        rule.setId(id);
        rule.setTypeKey(ruleTypeKey);
        rule.setStateKey(PopulationServiceConstants.POPULATION_RULE_ACTIVE_STATE_KEY);
        rule.getPersonIds().addAll(this._splitIt(personIds));
        rule.getRuleIds().addAll(this._splitIt(ruleId));  // fix this once the rule becomes singular
        try {
            rule = this.createPopulationRule(rule.getTypeKey(), rule, contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected during initilaization", ex);
        }
        try {
            this.applyPopulationRuleToPopulation(rule.getId(), pop.getId(), contextInfo);
        } catch (Exception ex) {
            throw new RuntimeException("Unexpected during initilaization", ex);
        }

        return pop;
    }

    private List<String> _splitIt(String str) {
        if (str.trim().isEmpty()) {
            return Collections.EMPTY_LIST;
        }
        String[] strs = str.split(",");
        for (int i = 0; i < strs.length; i++) {
            strs[i] = strs[i].trim();
        }
        List<String> list = Arrays.asList(strs);
        return list;
    }
}
