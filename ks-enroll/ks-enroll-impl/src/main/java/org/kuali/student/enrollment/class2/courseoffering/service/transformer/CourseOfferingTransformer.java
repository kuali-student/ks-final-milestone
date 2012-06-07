package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.apache.commons.lang.ArrayUtils;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.courseoffering.service.R1ToR2CopyHelper;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.*;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;

import java.util.ArrayList;
import java.util.List;

public class CourseOfferingTransformer {

    public void lui2CourseOffering(LuiInfo lui, CourseOfferingInfo co, ContextInfo context) {
        co.setId(lui.getId());
        co.setTypeKey(lui.getTypeKey());
        co.setStateKey(lui.getStateKey());
        co.setDescr(lui.getDescr());
        co.setMeta(lui.getMeta());
        co.setCourseOfferingURL(lui.getReferenceURL());

        //Dynamic attributes
        List<AttributeInfo> attributes = co.getAttributes();
        for (Attribute attr : lui.getAttributes()) {
            if (CourseOfferingServiceConstants.WAIT_LIST_LEVEL_TYPE_KEY_ATTR.equals(attr.getKey())){
                co.setWaitlistLevelTypeKey(attr.getValue());
            } else if  (CourseOfferingServiceConstants.WAIT_LIST_TYPE_KEY_ATTR.equals((attr.getKey()))){
                co.setWaitlistTypeKey(attr.getValue());
            } else if (CourseOfferingServiceConstants.WAIT_LIST_INDICATOR_ATTR.equals((attr.getKey()))){
                co.setHasWaitlist(Boolean.valueOf(attr.getValue()));
            } else if (CourseOfferingServiceConstants.FINAL_EXAM_INDICATOR_ATTR.equals(attr.getKey())){
                co.setFinalExamType(attr.getValue());
            } else if(CourseOfferingServiceConstants.COURSE_EVALUATION_INDICATOR_ATTR.equals(attr.getKey())){
                co.setEvaluated(Boolean.valueOf(attr.getValue()));
            } else if (CourseOfferingServiceConstants.WHERE_FEES_ATTACHED_FLAG_ATTR.equals(attr.getKey())){
                co.setFeeAtActivityOffering(Boolean.valueOf(attr.getValue()));
            } else if (CourseOfferingServiceConstants.FUNDING_SOURCE_ATTR.equals(attr.getKey())){
                co.setFundingSource(attr.getValue());
            } else {
                attributes.add(new AttributeInfo(attr));
            }
        }
        co.setAttributes(attributes);

        // specific fields
        co.setMaximumEnrollment(lui.getMaximumEnrollment());
        co.setMinimumEnrollment(lui.getMinimumEnrollment());

        co.setCourseId(lui.getCluId());
        co.setTermId(lui.getAtpId());
        co.setUnitsDeployment(lui.getUnitsDeployment());
        co.setUnitsContentOwner(lui.getUnitsContentOwner());

        //Split up the result keys for student registration options into a separate field.
        for(String resultValueGroupKey : lui.getResultValuesGroupKeys()){
            co.getStudentRegistrationOptionIds().clear();
            co.getGradingOptionIds().clear();
            if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS, resultValueGroupKey)){
                co.getStudentRegistrationOptionIds().add(resultValueGroupKey);
            }else{
                co.getGradingOptionIds().add(resultValueGroupKey);
            }
        }


        LuiIdentifierInfo identifier = lui.getOfficialIdentifier();
        if (identifier == null) {
            co.setCourseOfferingCode(null);
            co.setCourseNumberSuffix(null);
            co.setCourseOfferingTitle(null);
            co.setSubjectArea(null);
        } else {
            co.setCourseOfferingCode(identifier.getCode());
            co.setCourseNumberSuffix(identifier.getSuffixCode());
            co.setCourseOfferingTitle(identifier.getLongName());
            co.setSubjectArea(identifier.getDivision());
        }

        // store honors in lu code
        LuCodeInfo luCode = this.findLuCode(lui, LuiServiceConstants.HONORS_LU_CODE);
        if (luCode == null) {
            co.setIsHonorsOffering(false);
        } else {
            co.setIsHonorsOffering(string2Boolean(luCode.getValue()));
        }


        //below undecided
        //co.setHasWaitlist(lui.getHasWaitlist());
        //co.setWaitlistTypeKey(lui.getWaitlistTypeKey());
        //co.setWaitlistMaximum(lui.getWaitlistMaximum());
        //co.setIsWaitlistCheckinRequired(lui.getIsWaitlistCheckinRequired());
        //co.setWaitlistCheckinFrequency(lui.getWaitlistCheckinFrequency());

        //lui.getAlternateIdentifiers() -- where to map?
        //lui.getName() -- where to map?
        //lui.getReferenceURL() -- where to map?
        //LuiLuiRelation (to set jointOfferingIds, hasFinalExam)
//        assembleLuiLuiRelations(co, lui.getId(), context);
        return;
    }

    private String boolean2String(Boolean bval) {
        if (bval == null) {
            return null;
        }
        return bval.toString();
    }

    private Boolean string2Boolean(String sval) {
        if (sval == null) {
            return null;
        }
        return Boolean.parseBoolean(sval.toString());
    }

    private LuCodeInfo findLuCode(LuiInfo lui, String typeKey) {
        for (LuCodeInfo info : lui.getLuiCodes()) {
            if (info.getTypeKey().equals(typeKey)) {
                return info;
            }
        }
        return null;
    }

    private LuCodeInfo findAddLuCode(LuiInfo lui, String typeKey) {
        LuCodeInfo info = this.findLuCode(lui, typeKey);
        if (info != null) {
            return info;
        }
        info = new LuCodeInfo();
        info.setTypeKey(typeKey);
        lui.getLuiCodes().add(info);
        return info;
    }

    public void courseOffering2Lui(CourseOfferingInfo co, LuiInfo lui, ContextInfo context) {
        lui.setId(co.getId());
        lui.setTypeKey(co.getTypeKey());
        lui.setStateKey(co.getStateKey());
        lui.setDescr(co.getDescr());
        lui.setMeta(co.getMeta());
        lui.setReferenceURL(co.getCourseOfferingURL());

        //Dynamic Attributes
        List<AttributeInfo> attributes = lui.getAttributes();
        for (Attribute attr : co.getAttributes()) {
            attributes.add(new AttributeInfo(attr));
        }

        AttributeInfo waitlistLevelTypeKey = new AttributeInfo();
        waitlistLevelTypeKey.setKey(CourseOfferingServiceConstants.WAIT_LIST_LEVEL_TYPE_KEY_ATTR);
        waitlistLevelTypeKey.setValue(String.valueOf(co.getWaitlistLevelTypeKey()));
        attributes.add(waitlistLevelTypeKey);

        AttributeInfo waitlistTypeKey = new AttributeInfo();
        waitlistTypeKey.setKey(CourseOfferingServiceConstants.WAIT_LIST_TYPE_KEY_ATTR);
        waitlistTypeKey.setValue(String.valueOf(co.getWaitlistTypeKey()));
        attributes.add(waitlistTypeKey);

        AttributeInfo waitlistIndicator = new AttributeInfo();
        waitlistIndicator.setKey(CourseOfferingServiceConstants.WAIT_LIST_INDICATOR_ATTR);
        waitlistIndicator.setValue(String.valueOf(co.getHasWaitlist()));
        attributes.add(waitlistIndicator);

        AttributeInfo finalExamIndicator = new AttributeInfo();
        finalExamIndicator.setKey(CourseOfferingServiceConstants.FINAL_EXAM_INDICATOR_ATTR);
        finalExamIndicator.setValue(co.getFinalExamType());
        attributes.add(finalExamIndicator);

        AttributeInfo courseEvaluationIndicator = new AttributeInfo();
        courseEvaluationIndicator.setKey(CourseOfferingServiceConstants.COURSE_EVALUATION_INDICATOR_ATTR);
        courseEvaluationIndicator.setValue(String.valueOf(co.getIsEvaluated()));
        attributes.add(courseEvaluationIndicator);

        AttributeInfo whereFeesAttachedFlag = new AttributeInfo();
        whereFeesAttachedFlag.setKey(CourseOfferingServiceConstants.WHERE_FEES_ATTACHED_FLAG_ATTR);
        whereFeesAttachedFlag.setValue(String.valueOf(co.getIsFeeAtActivityOffering()));
        attributes.add(whereFeesAttachedFlag);

        AttributeInfo fundingSource = new AttributeInfo();
        fundingSource.setKey(CourseOfferingServiceConstants.FUNDING_SOURCE_ATTR);
        fundingSource.setValue(co.getFundingSource());
        attributes.add(fundingSource);

        lui.setAttributes(attributes);


        lui.setCluId(co.getCourseId());
        lui.setAtpId(co.getTermId());
        lui.setUnitsContentOwner(co.getUnitsContentOwnerOrgIds());
        lui.setUnitsDeployment(co.getUnitsDeploymentOrgIds());
        lui.setMaximumEnrollment(co.getMaximumEnrollment());
        lui.setMinimumEnrollment(co.getMinimumEnrollment());
        lui.getResultValuesGroupKeys().addAll(co.getGradingOptionIds());
        lui.getResultValuesGroupKeys().addAll(co.getStudentRegistrationOptionIds());
        options.add(co.getCreditOptionId());
        lui.setResultValuesGroupKeys(options);

        LuiIdentifierInfo oi = lui.getOfficialIdentifier();
        if (oi == null) {
            oi = new LuiIdentifierInfo();
            lui.setOfficialIdentifier(oi);
            oi.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
            oi.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
        }
        oi.setCode(co.getCourseOfferingCode());
        oi.setSuffixCode(co.getCourseNumberSuffix());
        oi.setLongName(co.getCourseOfferingTitle());
        oi.setDivision(co.getSubjectArea());

        LuCodeInfo luCode = this.findAddLuCode(lui, LuiServiceConstants.HONORS_LU_CODE);
        luCode.setValue(boolean2String(co.getIsHonorsOffering()));

        //below undecided
        //lui.setHasWaitlist(co.getHasWaitlist());
        //lui.setIsWaitlistCheckinRequired(co.getIsWaitlistCheckinRequired());
        //lui.setWaitlistCheckinFrequency(co.getWaitlistCheckinFrequency());
        //lui.setWaitlistMaximum(co.getWaitlistMaximum());
        //lui.setWaitlistTypeKey(co.getWaitlistTypeKey());

        //TODO: the following mapping undecided on wiki
        //gradeRosterLevelTypeKey
        //fundingSource
        //isFinancialAidEligible
        //registrationOrderTypeKey

    }

    public void copyFromCanonical(CourseInfo courseInfo, CourseOfferingInfo courseOfferingInfo, List<String> optionKeys) {
        courseOfferingInfo.setCourseId(courseInfo.getId());
        courseOfferingInfo.setCourseNumberSuffix(courseInfo.getCourseNumberSuffix());
        if (!optionKeys.contains(CourseOfferingSetServiceConstants.NOT_COURSE_TITLE_OPTION_KEY)) {
         courseOfferingInfo.setCourseOfferingTitle(courseInfo.getCourseTitle());
        }
        courseOfferingInfo.setSubjectArea(courseInfo.getSubjectArea());
        courseOfferingInfo.setCourseOfferingCode(courseInfo.getCode());
        courseOfferingInfo.setUnitsContentOwner(courseInfo.getUnitsContentOwner());
        courseOfferingInfo.setUnitsDeployment(courseInfo.getUnitsDeployment());

        //Split up the result keys for student registration options into a separate field.
        for(String resultValueGroupKey : courseInfo.getGradingOptions()){
            courseOfferingInfo.getStudentRegistrationOptionIds().clear();
            courseOfferingInfo.getGradingOptionIds().clear();
            if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS, resultValueGroupKey)){
                courseOfferingInfo.getStudentRegistrationOptionIds().add(resultValueGroupKey);
            }else{
                courseOfferingInfo.getGradingOptionIds().add(resultValueGroupKey);
            }
        }


        // TODO
        // courseOfferingInfo.setGradingOptionIds(courseInfo.getGradingOptions());
        if ((courseInfo.getCreditOptions() != null) && !courseInfo.getCreditOptions().isEmpty()) {
            /* TODO: which one shouldbe copied ? */
            courseOfferingInfo.setCreditOptionId(courseInfo.getCreditOptions().get(0).getId());
        }

        courseOfferingInfo.setDescr(new R1ToR2CopyHelper().copyRichText(courseInfo.getDescr()));
        courseOfferingInfo.setInstructors(new R1ToR2CopyHelper().copyInstructors(courseInfo.getInstructors()));
    }

    // this is not currently in use and needs to be revisited and plugged into the impl
    public void assembleInstructors(CourseOfferingInfo co, String luiId, ContextInfo context, LprService lprService)
            throws OperationFailedException {
        List<LprInfo> lprs = null;;
        try {
            lprs = lprService.getLprsByLui(luiId, context);
        } catch (Exception e) {
            throw new OperationFailedException("DoesNotExistException: " + e.getMessage());
        }

        for (LprInfo lpr : lprs) {
            if (lpr.getTypeKey().equals(LprServiceConstants.INSTRUCTOR_MAIN_TYPE_KEY)) {
                OfferingInstructorInfo instructor = new OfferingInstructorInfo();
                instructor.setPersonId(lpr.getPersonId());
                if (lpr.getCommitmentPercent() != null) {
                    instructor.setPercentageEffort(Float.parseFloat(lpr.getCommitmentPercent()));
                } else {
                    instructor.setPercentageEffort(null);
                }
                instructor.setId(lpr.getId());
                instructor.setTypeKey(lpr.getTypeKey());
                instructor.setStateKey(lpr.getStateKey());
                co.getInstructors().add(instructor);
            }
        }
    }
}
