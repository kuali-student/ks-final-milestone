package org.kuali.student.enrollment.class2.acal.service.impl;

import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.uif.service.impl.ViewHelperServiceImpl;
import org.kuali.student.enrollment.acal.dto.TermInfo;
import org.kuali.student.enrollment.acal.service.AcademicCalendarService;
import org.kuali.student.enrollment.class2.acal.form.AcademicTermForm;
import org.kuali.student.enrollment.class2.acal.service.AcademicTermEditViewHelperService;
import org.kuali.student.r2.common.constants.CommonServiceConstants;
import org.kuali.student.r2.common.dto.ContextInfo;

import javax.xml.namespace.QName;

public class AcademicTermEditViewHelperServiceImpl extends ViewHelperServiceImpl implements AcademicTermEditViewHelperService {

    private AcademicCalendarService acalService;

    public void saveTerm(AcademicTermForm academicTermForm,ContextInfo context) throws Exception {
        //Create Term
        TermInfo termInfo = academicTermForm.getTermInfo();
        termInfo.setStartDate(academicTermForm.getStartDate());
        termInfo.setEndDate(academicTermForm.getEndDate());
        termInfo.setName(academicTermForm.getName());
        termInfo.setTypeKey(academicTermForm.getTermType());

        TermInfo term = getAcalService().updateTerm(termInfo.getTypeKey(), termInfo, context);

//        int instructionalDays = getAcalService().getInstructionalDaysForTerm(term.getId(),context);
//        academicTermForm.setInstructionalDays(instructionalDays);
    }

    public void buildTerm(String termId,AcademicTermForm academicTermForm,ContextInfo context) throws Exception {

         TermInfo term = getAcalService().getTerm(termId, context);

         academicTermForm.setTermInfo(term);
         academicTermForm.setStartDate(term.getStartDate());
         academicTermForm.setEndDate(term.getEndDate());
         academicTermForm.setTermType(term.getTypeKey());
         academicTermForm.setName(term.getName());

        //Commented out for now as there are no keydates in ref data.
//         int instructionalDays = getAcalService().getInstructionalDaysForTerm(termId,context);
//         academicTermForm.setInstructionalDays(instructionalDays);
    }

    public AcademicCalendarService getAcalService() {
        if(acalService == null) {
            acalService = (AcademicCalendarService) GlobalResourceLoader.getService(new QName(CommonServiceConstants.REF_OBJECT_URI_GLOBAL_PREFIX + "acal", "AcademicCalendarService"));
        }
        return this.acalService;
    }

}
