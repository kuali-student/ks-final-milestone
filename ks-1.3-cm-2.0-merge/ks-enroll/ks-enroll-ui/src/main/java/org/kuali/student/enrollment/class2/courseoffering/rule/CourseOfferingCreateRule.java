package org.kuali.student.enrollment.class2.courseoffering.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;

import java.util.List;
import java.util.Locale;

public class CourseOfferingCreateRule extends MaintenanceDocumentRuleBase {

    private CourseOfferingService courseOfferingService;
    private ContextInfo contextInfo;

    private final static String EXISTING_CO_CODE_FOUND_ERROR = "That Course Offering Code is already in use.  Please enter a different, unique Course Offering Code for ";

    @Override
    protected boolean processGlobalSaveDocumentBusinessRules(MaintenanceDocument document) {
        if (document.getNewMaintainableObject().getDataObject() instanceof CourseOfferingCreateWrapper){
            CourseOfferingCreateWrapper coWrapper = (CourseOfferingCreateWrapper)document.getNewMaintainableObject().getDataObject();

            String newCoCode = coWrapper.getCatalogCourseCode() + coWrapper.getCourseOfferingSuffix();
            try {
                List<CourseOfferingInfo> wrapperList = getCourseOfferingService().getCourseOfferingsByCourseAndTerm(coWrapper.getCourse().getId(), coWrapper.getTerm().getId(), getContextInfo());
                for (CourseOfferingInfo courseOfferingInfo : wrapperList) {

                    if (StringUtils.equals(newCoCode, courseOfferingInfo.getCourseOfferingCode())) {
                        StringBuilder sb = new StringBuilder(EXISTING_CO_CODE_FOUND_ERROR);
                        sb.append(coWrapper.getCatalogCourseCode());
                        GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, sb.toString());
                        coWrapper.setCreateErrorMessage(sb.toString());
                        coWrapper.setEnableCreateButton(true);
                        return false;
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            coWrapper.setCreateErrorMessage(null);
        }

        return true;
    }

    public ContextInfo getContextInfo() {
        if (null == contextInfo) {
            contextInfo = new ContextInfo();
            contextInfo.setAuthenticatedPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            contextInfo.setPrincipalId(GlobalVariables.getUserSession().getPrincipalId());
            LocaleInfo localeInfo = new LocaleInfo();
            localeInfo.setLocaleLanguage(Locale.getDefault().getLanguage());
            localeInfo.setLocaleRegion(Locale.getDefault().getCountry());
            contextInfo.setLocale(localeInfo);
        }
        return contextInfo;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }
}