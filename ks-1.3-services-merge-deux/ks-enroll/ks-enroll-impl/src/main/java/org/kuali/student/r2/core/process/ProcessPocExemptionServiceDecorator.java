/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.r2.core.process;

import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.common.util.constants.ExemptionServiceConstants;
import org.kuali.student.r2.common.util.constants.ProcessServiceConstants;
import org.kuali.student.r2.core.exemption.dto.DateOverrideInfo;
import org.kuali.student.r2.core.exemption.dto.ExemptionInfo;
import org.kuali.student.r2.core.exemption.dto.ExemptionRequestInfo;
import org.kuali.student.r2.core.exemption.service.ExemptionService;
import org.kuali.student.r2.core.exemption.service.ExemptionServiceDecorator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author nwright
 */
public class ProcessPocExemptionServiceDecorator extends ExemptionServiceDecorator {

    public ProcessPocExemptionServiceDecorator(ExemptionService nextDecorator) {
        super();
        setNextDecorator(nextDecorator);
        initializeData();
    }

    private void initializeData() {
        ContextInfo context = new ContextInfo();
        context.setPrincipalId("POC-Initializer");

        ExemptionRequestInfo request1 = _createRequest(ProcessPocConstants.PERSON_ID_JOHNNY_MANNING_2374, context);
        ExemptionInfo exemption1 = this._createCheckExemption(request1, context);

        ExemptionRequestInfo request2 = _createRequest(ProcessPocConstants.PERSON_ID_EDDIE_PITTMAN_2406, context);
        Date endDate2;
        try {
            endDate2 = new SimpleDateFormat("yyyy-MM-dd").parse("2011-12-31");
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        ExemptionInfo exemption2 = this._createMilestoneDateExemption(request2, endDate2, context);

        ExemptionRequestInfo request3 = _createRequest(ProcessPocConstants.PERSON_ID_TRACY_BURTON_2132, context);
        Date endDate3;
        try {
            endDate3 = new SimpleDateFormat("yyyy-MM-dd").parse("2011-11-30");
        } catch (ParseException ex) {
            throw new RuntimeException(ex);
        }
        ExemptionInfo exemption3 = this._createMilestoneDateExemption(request3, endDate3, context);
    }

    private ExemptionRequestInfo _createRequest(String personId, ContextInfo context) {
        ExemptionRequestInfo request = new ExemptionRequestInfo();
        request.setTypeKey(ExemptionServiceConstants.GENERIC_EXEMPTION_REQUEST_TYPE_KEY);
        request.setStateKey(ExemptionServiceConstants.EXEMPTION_REQUEST_APPROVED_STATE_KEY);
        request.setRequesterId(personId);
        request.setPersonId(personId);
        request.setRequestDate(new Date());
        request.setName("test exemption for " + personId);
        try {
            request = this.createExemptionRequest(request.getPersonId(), request.getTypeKey(), request, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating exemption request", ex);
        }
        return request;
    }

    private ExemptionInfo _createCheckExemption(ExemptionRequestInfo request, ContextInfo context) {
        ExemptionInfo info = new ExemptionInfo();
        info.setPersonId(request.getPersonId());
        info.setExemptionRequestId(request.getId());
        info.setTypeKey(ExemptionServiceConstants.CHECK_EXEMPTION_TYPE_KEY);
        info.setStateKey(ExemptionServiceConstants.EXEMPTION_ACTIVE_STATE_KEY);
        info.setName(request.getName());
        info.setProcessKey(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM);
        info.setCheckId(ProcessPocConstants.CHECK_ID_REGISTRATION_PERIOD_IS_OPEN);
        try {
            info = this.createExemption(info.getExemptionRequestId(), info.getTypeKey(), info, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating exemption request", ex);
        }
        return info;
    }

    private ExemptionInfo _createMilestoneDateExemption(ExemptionRequestInfo request, Date endDate, ContextInfo context) {
        ExemptionInfo info = new ExemptionInfo();
        info.setPersonId(request.getPersonId());
        info.setExemptionRequestId(request.getId());
        info.setTypeKey(ExemptionServiceConstants.MILESTONE_DATE_EXEMPTION_TYPE_KEY);
        info.setStateKey(ExemptionServiceConstants.EXEMPTION_ACTIVE_STATE_KEY);
        info.setName(request.getName());
        info.setProcessKey(ProcessServiceConstants.PROCESS_KEY_ELIGIBILITY_FOR_TERM);
        info.setCheckId(ProcessPocConstants.CHECK_ID_REGISTRATION_PERIOD_IS_NOT_CLOSED);
        DateOverrideInfo dateOverride = new DateOverrideInfo();
        dateOverride.setMilestoneId(AtpServiceConstants.MILESTONE_REGISTRATION_PERIOD_TYPE_KEY);
        dateOverride.setEffectiveEndDate(endDate);
        info.setDateOverride(dateOverride);
        try {
            info = this.createExemption(info.getExemptionRequestId(), info.getType(), info, context);
        } catch (Exception ex) {
            throw new RuntimeException("error creating exemption request", ex);
        }
        return info;
    }
}
