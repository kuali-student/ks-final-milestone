package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.kim.impl.KIMPropertyConstants;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActivityOfferingTransformer {

    public static void lui2Activity(ActivityOfferingInfo ao, LuiInfo lui, LprService lprService, ContextInfo context) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        ao.setId(lui.getId());
        ao.setMeta(lui.getMeta());
        ao.setStateKey(lui.getStateKey());
        ao.setTypeKey(lui.getTypeKey());
        ao.setDescr(lui.getDescr());
        ao.setName(lui.getName());
        ao.setActivityId(lui.getCluId());
        ao.setTermId(lui.getAtpId());
        ao.setMinimumEnrollment(lui.getMinimumEnrollment());
        ao.setMaximumEnrollment(lui.getMaximumEnrollment());
        ao.setScheduleId(lui.getScheduleId());
        ao.setActivityOfferingURL(lui.getReferenceURL());

        if (lui.getOfficialIdentifier() != null){
            ao.setActivityCode(lui.getOfficialIdentifier().getCode());
        }

        //Dynamic attributes - Some lui dynamic attributes are defined fields on Activity Offering
        List<AttributeInfo> attributes = ao.getAttributes();
        for (Attribute attr : lui.getAttributes()) {
            if (CourseOfferingServiceConstants.COURSE_EVALUATION_INDICATOR_ATTR.equals(attr.getKey())){
                ao.setIsEvaluated(Boolean.valueOf(attr.getValue()));
            } else if (CourseOfferingServiceConstants.MAX_ENROLLMENT_IS_ESTIMATED_ATTR.equals(attr.getKey())){
                ao.setIsMaxEnrollmentEstimate(Boolean.valueOf(attr.getValue()));
            } else {
                attributes.add(new AttributeInfo(attr));
            }
        }
        ao.setAttributes(attributes);

        // store honors in lu code
        LuCodeInfo luCode = findLuCode(lui, LuiServiceConstants.HONORS_LU_CODE);
        if (luCode == null) {
            ao.setIsHonorsOffering(false);
        } else {
            ao.setIsHonorsOffering(Boolean.valueOf(luCode.getValue()));
        }

        // build list of OfferingInstructors
        List<LprInfo> lprs = lprService.getLprsByLui(ao.getId(), context);

        ao.setInstructors(OfferingInstructorTransformer.lprs2Instructors(lprs));

    }

    public static void activity2Lui (ActivityOfferingInfo ao, LuiInfo lui) {
        lui.setId(ao.getId());
        lui.setTypeKey(ao.getTypeKey());
        lui.setStateKey(ao.getStateKey());
        if (ao.getName() == null) {
            String coCode = ao.getCourseOfferingCode();
            if (coCode == null) {
                coCode = "NOCODE";
            }
            lui.setName(coCode + " AO"); // Makes it easier to track in DB
        } else {
            lui.setName(ao.getName());
        }
        lui.setDescr(ao.getDescr());
        lui.setMeta(ao.getMeta());
        lui.setCluId(ao.getActivityId());
        lui.setAtpId(ao.getTermId());
        lui.setMinimumEnrollment(ao.getMinimumEnrollment());
        lui.setMaximumEnrollment(ao.getMaximumEnrollment());
        lui.setScheduleId(ao.getScheduleId());
        lui.setReferenceURL(ao.getActivityOfferingURL());

        //Lui Official Identifier
        LuiIdentifierInfo officialIdentifier = new LuiIdentifierInfo();
        officialIdentifier.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        officialIdentifier.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
        officialIdentifier.setCode(ao.getActivityCode());
        lui.setOfficialIdentifier(officialIdentifier);

        //Dynamic attributes - Some lui dynamic attributes are defined fields on Activity Offering
        List<AttributeInfo> attributes = lui.getAttributes();
        for (Attribute attr : ao.getAttributes()) {
            attributes.add(new AttributeInfo(attr));
        }

        AttributeInfo isEvaluated = new AttributeInfo();
        isEvaluated.setKey(CourseOfferingServiceConstants.COURSE_EVALUATION_INDICATOR_ATTR);
        isEvaluated.setValue(String.valueOf(ao.getIsEvaluated()));
        attributes.add(isEvaluated);

        AttributeInfo isMaxEnrollmentEstimate = new AttributeInfo();
        isMaxEnrollmentEstimate.setKey(CourseOfferingServiceConstants.MAX_ENROLLMENT_IS_ESTIMATED_ATTR);
        isMaxEnrollmentEstimate.setValue(String.valueOf(ao.getIsMaxEnrollmentEstimate()));
        attributes.add(isMaxEnrollmentEstimate);

        lui.setAttributes(attributes);

        //Honors code
        LuCodeInfo luCode = findAddLuCode(lui, LuiServiceConstants.HONORS_LU_CODE);
        luCode.setValue(String.valueOf(ao.getIsHonorsOffering()));
    }


    public static List<Person> getInstructorByPersonId(String personId){
        Map<String, String> searchCriteria = new HashMap<String, String>();
        searchCriteria.put(KIMPropertyConstants.Person.ENTITY_ID, personId);
        return getPersonService().findPeople(searchCriteria);
    }

    public static PersonService getPersonService() {
        return KimApiServiceLocator.getPersonService();
    }

    public static LuCodeInfo findLuCode(LuiInfo lui, String typeKey) {
        for (LuCodeInfo info : lui.getLuiCodes()) {
            if (info.getTypeKey().equals(typeKey)) {
                return info;
            }
        }
        return null;
    }

    public static LuCodeInfo findAddLuCode(LuiInfo lui, String typeKey) {
        LuCodeInfo info = findLuCode(lui, typeKey);
        if (info != null) {
            return info;
        }
        info = new LuCodeInfo();
        info.setTypeKey(typeKey);
        lui.getLuiCodes().add(info);
        return info;
    }

}
