package org.kuali.student.enrollment.class2.courseoffering.rule;

import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.util.RiceKeyConstants;
import org.kuali.rice.krad.maintenance.MaintenanceDocument;
import org.kuali.rice.krad.rules.MaintenanceDocumentRuleBase;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.enrollment.class2.courseoffering.dto.CourseOfferingCreateWrapper;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingResourceLoader;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.r2.common.util.ContextUtils;

import java.util.List;

public class CourseOfferingCreateRule extends MaintenanceDocumentRuleBase {

    private CourseOfferingService courseOfferingService;

    private final static String EXISTING_CO_CODE_FOUND_ERROR = "That Course Offering Code is already in use.  Please enter a different, unique Course Offering Code for ";

    @Override
    protected boolean processGlobalSaveDocumentBusinessRules(MaintenanceDocument document) {
        boolean valid = true;

        if (document.getNewMaintainableObject().getDataObject() instanceof CourseOfferingCreateWrapper){
            CourseOfferingCreateWrapper coWrapper = (CourseOfferingCreateWrapper)document.getNewMaintainableObject().getDataObject();

            valid = validateRequiredFields(coWrapper);

            if (valid){
                valid = validateDuplicateSuffix(coWrapper);
            }
        }

        return valid;
    }

    protected boolean validateRequiredFields(CourseOfferingCreateWrapper coWrapper){
        if (coWrapper.getFormatOfferingWrappers().isEmpty()){
            GlobalVariables.getMessageMap().putError(CourseOfferingConstants.DELIVERY_FORMAT_SECTION_ID,CourseOfferingConstants.DELIVERY_FORMAT_REQUIRED_ERROR);
            return false;
        }
        return true;
    }

    protected boolean validateDuplicateSuffix(CourseOfferingCreateWrapper coWrapper){
        // Catalog course code is case INSENSITIVE, but the suffix is case SENSITIVE
        String newCoCode = (coWrapper.getCatalogCourseCode().toUpperCase()) + coWrapper.getCourseOfferingSuffix();
        try {
            List<CourseOfferingInfo> wrapperList = getCourseOfferingService().getCourseOfferingsByCourseAndTerm(coWrapper.getCourse().getId(), coWrapper.getTerm().getId(), ContextUtils.createDefaultContextInfo());
            for (CourseOfferingInfo courseOfferingInfo : wrapperList) {

                if (StringUtils.equals(newCoCode, courseOfferingInfo.getCourseOfferingCode())) {
                    StringBuilder sb = new StringBuilder(EXISTING_CO_CODE_FOUND_ERROR);
                    sb.append(coWrapper.getCatalogCourseCode());
                    GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, RiceKeyConstants.ERROR_CUSTOM, sb.toString());
                    coWrapper.setEnableCreateButton(true);
                    return false;
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    protected CourseOfferingService getCourseOfferingService() {
        if (courseOfferingService == null) {
            courseOfferingService = CourseOfferingResourceLoader.loadCourseOfferingService();
        }
        return courseOfferingService;
    }
}