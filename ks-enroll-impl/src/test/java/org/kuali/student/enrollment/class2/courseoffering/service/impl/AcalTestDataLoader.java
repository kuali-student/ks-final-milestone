/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kuali.student.enrollment.class2.courseoffering.service.impl;

import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.RichTextHelper;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.core.constants.AtpServiceConstants;

import java.util.Date;

/**
 * @author nwright
 */
public class AcalTestDataLoader {

    private AcademicCalendarService acalService;
    private static String principalId = AcalTestDataLoader.class.getSimpleName();

    public AcademicCalendarService getAcalService() {
        return acalService;
    }

    public void setAcalService(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public AcalTestDataLoader() {
    }

    public AcalTestDataLoader(AcademicCalendarService acalService) {
        this.acalService = acalService;
    }

    public void loadData() {
        loadTerm("testAtpId1", "test1", "description 1", AtpServiceConstants.ATP_FALL_TYPE_KEY, "2000-01-01 00:00:00.0", "2100-12-31 00:00:00.0");
    }

    public TermInfo loadTerm(String id,
                             String name,
                             String description,
                             String type,
                             String startDate,
                             String endDate) {

        TermInfo info = new TermInfo();
        info.setId(id);
        info.setCode(id);// use id for code
        info.setName(name);
        info.setDescr(new RichTextHelper().fromPlain(description));
        info.setTypeKey(type);
        info.setStateKey(AtpServiceConstants.ATP_DRAFT_STATE_KEY);
        info.setStartDate(str2Date(startDate, id));
        info.setEndDate(str2Date(endDate, id));


        ContextInfo context = new ContextInfo();
        context.setPrincipalId(principalId);
        context.setCurrentDate(new Date());
        try {
            TermInfo newInfo = this.acalService.createTerm(type, info, context);
            return newInfo;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    private Date str2Date(String str, String context) {
        if (str == null) {
            return null;
        }
        try {
            Date date = DateFormatters.DEFAULT_YEAR_MONTH_24HOUR_MILLISECONDS_FORMATTER.parse(str);
            return date;
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Bad date " + str + " in " + context);
        }
    }
}