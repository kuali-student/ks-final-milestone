package org.kuali.student.enrollment.class2.registration.admin.service.impl;

import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.kim.api.identity.affiliation.EntityAffiliation;
import org.kuali.rice.kim.api.identity.entity.Entity;
import org.kuali.rice.kim.api.identity.name.EntityName;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.util.KRADConstants;
import org.kuali.student.common.uif.service.impl.KSViewHelperServiceImpl;
import org.kuali.student.enrollment.class2.courseoffering.util.CourseOfferingConstants;
import org.kuali.student.enrollment.class2.courseoffering.util.ManageSocConstants;
import org.kuali.student.enrollment.class2.registration.admin.form.AdminRegistrationForm;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationActivity;
import org.kuali.student.enrollment.class2.registration.admin.form.RegistrationCourse;
import org.kuali.student.enrollment.class2.registration.admin.service.CourseRegAdminViewHelperService;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegConstants;
import org.kuali.student.enrollment.class2.registration.admin.util.AdminRegistrationUtil;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseregistration.dto.ActivityRegistrationInfo;
import org.kuali.student.enrollment.courseregistration.dto.CourseRegistrationInfo;
import org.kuali.student.r2.core.acal.dto.TermInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SW Genis on 2014/07/04.
 */
public class AdminRegistrationViewHelperServiceImpl extends KSViewHelperServiceImpl implements CourseRegAdminViewHelperService {

    @Override
    public void getRegistrationStatus() {

    }

    @Override
    public void submitRegistrationRequest() {

        // get the regGroup
        //RegGroupSearchResult rg = CourseRegistrationAndScheduleOfClassesUtil.getRegGroup(null, termCode, courseCode, regGroupCode, regGroupId, contextInfo);

        // get the registration group, returns default (from Course Offering) credits (as creditId) and grading options (as a string of options)
        //CourseOfferingInfo courseOfferingInfo = CourseRegistrationAndScheduleOfClassesUtil.getCourseOfferingIdCreditGrading(rg.getCourseOfferingId(), courseCode, rg.getTermId(), termCode);

        // verify passed credits (must be non-empty unless fixed) and grading option (can be null)
        //credits = verifyRegistrationRequestCreditsGradingOption(courseOfferingInfo, credits, gradingOptionId, contextInfo);

        //Create the request object
        //RegistrationRequestInfo regReqInfo = createRegistrationRequest(contextInfo.getPrincipalId(), rg.getTermId(), rg.getRegGroupId(), null, credits, gradingOptionId, LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY, LprServiceConstants.LPRTRANS_NEW_STATE_KEY, LprServiceConstants.REQ_ITEM_ADD_TYPE_KEY, LprServiceConstants.LPRTRANS_ITEM_NEW_STATE_KEY, okToWaitlist);

        // persist the request object in the service
        //RegistrationRequestInfo newRegReq = CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().createRegistrationRequest(LprServiceConstants.LPRTRANS_REGISTER_TYPE_KEY, regReqInfo, contextInfo);

        // submit the request to the registration engine.
        //return CourseRegistrationAndScheduleOfClassesUtil.getCourseRegistrationService().submitRegistrationRequest(newRegReq.getId(), contextInfo);
    }

    @Override
    public TermInfo getTermByCode(String termCode) {

        try{
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();

            qbcBuilder.setPredicates(PredicateFactory.equal(CourseOfferingConstants.ATP_CODE, termCode));

            QueryByCriteria criteria = qbcBuilder.build();

            List<TermInfo> terms = AdminRegistrationUtil.getAcademicCalendarService().searchForTerms(criteria, createContextInfo());
            int firstTerm = 0;
            if (terms.size() > 1) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_MULTIPLE_TERMS);
                return null;
            }
            if (terms.isEmpty()) {
                GlobalVariables.getMessageMap().putError(KRADConstants.GLOBAL_ERRORS, ManageSocConstants.MessageKeys.ERROR_INVALID_TERM);
                return null;
            }
            return terms.get(firstTerm);
        }catch (Exception e){
//            LOG.debug("Error getting term for the code - {}", termCode);
            throw convertServiceExceptionsToUI(e);
        }
    }

    @Override
    public List<RegistrationCourse> getCourseRegStudentAndTerm(String studentId, String termCode) {

        List<RegistrationCourse> registeredCourses = new ArrayList<RegistrationCourse>();

        try {

            List<CourseRegistrationInfo> courseRegistrationInfos = AdminRegistrationUtil.getCourseRegistrationService().getCourseRegistrationsByStudentAndTerm(studentId, termCode, createContextInfo());

            for (CourseRegistrationInfo courseRegInfo : courseRegistrationInfos) {

                RegistrationCourse registeredCourse = new RegistrationCourse();
                CourseOfferingInfo coInfo = AdminRegistrationUtil.getCourseOfferingService().getCourseOffering(courseRegInfo.getCourseOfferingId(), createContextInfo());
                registeredCourse.setCode(coInfo.getCourseOfferingCode());
                registeredCourse.setCourseName(coInfo.getCourseOfferingTitle());
                registeredCourse.setCredits(Integer.parseInt(coInfo.getCreditCnt()));
                registeredCourse.setRegDate(courseRegInfo.getEffectiveDate());

                List<ActivityRegistrationInfo> activityOfferings = AdminRegistrationUtil.getCourseRegistrationService().getActivityRegistrationsForCourseRegistration(courseRegInfo.getId(), createContextInfo());

                for (ActivityRegistrationInfo activityRegInfos : activityOfferings) {
                    //Use activityRegInfos - to retrieve the hardcoded values
                    RegistrationActivity regActivity = new RegistrationActivity("Lec", "MWF 04:00pm - 05:30pm", "Steve Capriani", "PTX 2391");
                    registeredCourse.getActivities().add(regActivity);

                }
                registeredCourses.add(registeredCourse);
            }


        } catch (Exception e) {
            throw convertServiceExceptionsToUI(e);
        }
        return registeredCourses;
    }


    @Override
    public void populateStudentInfo(AdminRegistrationForm form) throws Exception {

        Entity entityInfo =  AdminRegistrationUtil.getIdentityService().getEntity(form.getStudentId());
        if ((entityInfo != null)) {
          
            //KSENROLL-13558 :work around for incorrect Data
            form.getPrincipalIDs().addAll(entityInfo.getPrincipals());

            Boolean validStudent = false;
            for (EntityAffiliation entityAffiliationInfo : entityInfo.getAffiliations()) {
                if (entityAffiliationInfo.getAffiliationType().getCode().equals(AdminRegConstants.STUDENT_AFFILIATION_TYPE_CODE)) {
                    validStudent = true;
                }
            }

            if (!validStudent) {
//                GlobalVariables.getMessageMap().putErrorForSectionId(AdminRegConstants.STUDENT_INFO_SECTION, AdminRegConstants.ADMIN_REG_MSG_ERROR_STUDENT_ROLE_NOT_FOUND, form.getStudentId());
//                return;
            }

            for (EntityName entityNameInfo : entityInfo.getNames()) {
                if (entityNameInfo.isDefaultValue()) {
                    form.setStudentName(entityNameInfo.getFirstName() + " " + entityNameInfo.getLastName());
                    break;
                }
            }

        } else {
            GlobalVariables.getMessageMap().putErrorForSectionId(AdminRegConstants.STUDENT_INFO_SECTION, AdminRegConstants.ADMIN_REG_MSG_ERROR_INVALID_STUDENT, form.getStudentId());
        }
    }

}
