/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.process.poc;

import java.util.Date;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.ExemptionServiceConstants;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.exemption.service.ExemptionServiceDecorator;

/**
 *
 * @author nwright
 */
public class ProcessPocExemptionServiceDecorator extends ExemptionServiceDecorator {

    public ProcessPocExemptionServiceDecorator(ExemptionService nextDecorator) {
        super();
        this.setNextDecorator(nextDecorator);
        initializeData();
    }

    private void initializeData() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-Initializer");

        ExemptionInfo exemption = new ExemptionInfo();
//        exemption.setTypeKey(ExemptionServiceConstants.);
        exemption.setStateKey(ExemptionServiceConstants.EXEMPTION_ACTIVE_STATE_KEY);
        exemption.setName("test exemption for ");
        exemption.setEffectiveDate(new Date());
        exemption.setMilestoneOverride(null);
//        exemption.setPersonId(ProcessPocConstants.PERSON_ID_CLIFFORD_RIDDLE_2397);
//        try {
//            this.createExemption(exemption, context);
//        } catch (Exception ex) {
//            throw new RuntimeException("error creating hold", ex);
//        }
//        exemption.setPersonId(ProcessPocConstants.PERSON_ID_JOHNNY_MANNING_2374);
//        try {
//            this.createExemption(exemption, context);
//        } catch (Exception ex) {
//            throw new RuntimeException("error creating hold", ex);
//        }
//
//
//        exemption.setTypeKey(ExemptionServiceConstants.STUDENT_HOLD_TYPE_KEY);
//        exemption.setStateKey(ExemptionServiceConstants.HOLD_ACTIVE_STATE_KEY);
//        exemption.setName(overdueBookIssue.getName());
//        exemption.setIsOverridable(true);
//        exemption.setIsWarning(false);
//        exemption.setEffectiveDate(new Date());
//        exemption.setIssueId(overdueBookIssue.getId());
//        exemption.setPersonId(ProcessPocConstants.PERSON_ID_BETTY_MARTIN_2005);
//        try {
//            this.createExemption(exemption, context);
//        } catch (Exception ex) {
//            throw new RuntimeException("error creating hold", ex);
//        }
//        exemption.setPersonId(ProcessPocConstants.PERSON_ID_NINA_WELCH_2166);
//        try {
//            this.createExemption(exemption, context);
//        } catch (Exception ex) {
//            throw new RuntimeException("error creating hold", ex);
//        }

    }
}
