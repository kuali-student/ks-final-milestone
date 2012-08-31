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
import org.kuali.student.r2.common.datadictionary.DataDictionaryValidator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.LocaleInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.course.dto.CourseInfo;

import java.util.ArrayList;
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


            CourseOfferingInfo courseOffering = new CourseOfferingInfo();
            List<String> optionKeys = new ArrayList<String>();

            CourseInfo courseInfo = coWrapper.getCourse();
            courseOffering.setTermId(coWrapper.getTerm().getId());
            courseOffering.setCourseOfferingTitle(courseInfo.getCourseTitle());
            courseOffering.setCourseId(courseInfo.getId());
            courseOffering.setCourseCode(courseInfo.getCode());
            courseOffering.setTypeKey(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY);
            courseOffering.setStateKey(LuiServiceConstants.LUI_CO_STATE_DRAFT_KEY);

            // Catalog course code is case INSENSITIVE, but the suffix is case SENSITIVE
            String newCoCode = (coWrapper.getCatalogCourseCode().toUpperCase()) + coWrapper.getCourseOfferingSuffix();

            courseOffering.setCourseOfferingCode(newCoCode);

            List<ValidationResultInfo> validationResults;
            try {
                validationResults = getCourseOfferingService().validateCourseOffering(DataDictionaryValidator.ValidationType.FULL_VALIDATION.toString(), courseOffering, getContextInfo());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            for(ValidationResultInfo vr : validationResults) {
                if (vr.isError()) {
                    if (vr.getMessage().equals(CourseOfferingServiceConstants.COURSE_OFFERING_CODE_UNIQUENESS_VALIDATION_MESSAGE)) {
                        StringBuilder sb = new StringBuilder(EXISTING_CO_CODE_FOUND_ERROR);
                        sb.append(coWrapper.getCatalogCourseCode());
                        GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, sb.toString());
                        coWrapper.setEnableCreateButton(true);
                        return false;
                    }
                    else {
                        throw new RuntimeException("Unhandled validation result when attempting to save a Course Offering: " + newCoCode + ", validation message is: " + vr.getMessage());
                    }
                }
            }

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