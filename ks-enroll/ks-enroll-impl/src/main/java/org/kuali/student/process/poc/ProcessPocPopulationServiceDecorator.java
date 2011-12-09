/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.process.poc;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.PopulationServiceConstants;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.population.dto.PopulationInfo;
import org.kuali.student.r2.core.population.service.PopulationService;
import org.kuali.student.r2.core.population.service.PopulationServiceDecorator;

/**
 *
 * @author nwright
 */
public class ProcessPocPopulationServiceDecorator extends PopulationServiceDecorator {

    public ProcessPocPopulationServiceDecorator(PopulationService nextDecorator) {
        super();
        this.setNextDecorator(nextDecorator);
        initializeData();
    }

    private void initializeData() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-Initializer");

        PopulationInfo summerOnly = new PopulationInfo();
        summerOnly.setName("summer only students");
        summerOnly.setTypeKey(PopulationServiceConstants.SUMMER_ONLY_STUDENTS_POPULATION_KEY);
//        summerOnly.setStateKey(PopulationServiceConstants.);
        try {
            summerOnly = this.createPopulation(summerOnly, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating hold", ex);
        }

        IssueInfo overdueBookIssue = new IssueInfo();
        overdueBookIssue.setName("Overdue Library Issue");
//        overdueBookIssue.setTypeKey(PopulationServiceConstants.OVERDUE_LIBRARY_MATERIALS_ISSUE_TYPE_KEY);
//        overdueBookIssue.setStateKey(PopulationServiceConstants.ISSUE_ACTIVE_STATE_KEY);
//        try {
//            overdueBookIssue = this.createIssue(overdueBookIssue, context);
//        } catch (Exception ex) {
//            throw new RuntimeException("error creating hold", ex);
//        }


//        PopulationInfo hold = new PopulationInfo();
//        hold.setTypeKey(PopulationServiceConstants.STUDENT_HOLD_TYPE_KEY);
//        hold.setStateKey(PopulationServiceConstants.HOLD_ACTIVE_STATE_KEY);
//        hold.setName(summerOnly.getName());
//        hold.setIsOverridable(true);
//        hold.setIsWarning(false);
//        hold.setEffectiveDate(new Date());
//        hold.setIssueId(summerOnly.getId());
//        hold.setPersonId(ProcessPocConstants.PERSON_ID_CLIFFORD_RIDDLE_2397);
//        try {
//            this.createPopulation(hold, context);
//        } catch (Exception ex) {
//            throw new RuntimeException("error creating hold", ex);
//        }
//        hold.setPersonId(ProcessPocConstants.PERSON_ID_NINA_WELCH_2166);
//        try {
//            this.createPopulation(hold, context);
//        } catch (Exception ex) {
//            throw new RuntimeException("error creating hold", ex);
//        }
//
//
//        hold.setTypeKey(PopulationServiceConstants.STUDENT_HOLD_TYPE_KEY);
//        hold.setStateKey(PopulationServiceConstants.HOLD_ACTIVE_STATE_KEY);
//        hold.setName(overdueBookIssue.getName());
//        hold.setIsOverridable(true);
//        hold.setIsWarning(false);
//        hold.setEffectiveDate(new Date());
//        hold.setIssueId(overdueBookIssue.getId());
//        hold.setPersonId(ProcessPocConstants.PERSON_ID_BETTY_MARTIN_2005);
//        try {
//            this.createPopulation(hold, context);
//        } catch (Exception ex) {
//            throw new RuntimeException("error creating hold", ex);
//        }
//        hold.setPersonId(ProcessPocConstants.PERSON_ID_NINA_WELCH_2166);
//        try {
//            this.createPopulation(hold, context);
//        } catch (Exception ex) {
//            throw new RuntimeException("error creating hold", ex);
//        }

    }
}
