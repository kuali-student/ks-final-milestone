package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
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
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.CourseOfferingSetServiceConstants;
import org.kuali.student.r2.common.util.constants.LprServiceConstants;
import org.kuali.student.r2.common.util.constants.LrcServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseOfferingTransformer {
    private LprService lprService;
    private PersonService personService;
    private LRCService lrcService;

    final Logger LOG = Logger.getLogger(CourseOfferingTransformer.class);

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
                co.setIsEvaluated(Boolean.valueOf(attr.getValue()));
            } else if (CourseOfferingServiceConstants.WHERE_FEES_ATTACHED_FLAG_ATTR.equals(attr.getKey())){
                co.setIsFeeAtActivityOffering(Boolean.valueOf(attr.getValue()));
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
        co.getStudentRegistrationGradingOptions().clear();
        co.setGradingOptionId(null);
        for(String resultValueGroupKey : lui.getResultValuesGroupKeys()){
            if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS, resultValueGroupKey)){
                co.getStudentRegistrationGradingOptions().add(resultValueGroupKey);
            }else if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_GRADING_OPTION_TYPE_KEYS, resultValueGroupKey)){
                if(co.getGradingOptionId()!=null){
                    throw new RuntimeException("This course offering has multiple grading options in the data. It should only have at most one.");
                }
                co.setGradingOptionId(resultValueGroupKey);
            }else if(resultValueGroupKey!=null && resultValueGroupKey.startsWith("kuali.creditType.credit")){//There should be a better way of distinguishing credits from other results
                co.setCreditOptionId(resultValueGroupKey);
            }
        }

        if ( co.getGradingOptionId() != null ) {//TODO why are we doing substrings of keys?
            co.setGradingOption(co.getGradingOptionId().substring(co.getGradingOptionId().lastIndexOf('.') + 1));
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
        //lui.getAlternateIdentifiers() -- where to map?
        //lui.getName() -- where to map?
        //lui.getReferenceURL() -- where to map?
        //LuiLuiRelation (to set jointOfferingIds, hasFinalExam)
//        assembleLuiLuiRelations(co, lui.getId(), context);
        return;
    }


    public LprService getLprService() {
        return lprService;
    }

    public void setLprService(LprService lprService) {
        this.lprService = lprService;
    }

    public void setPersonService(PersonService personService){
        this.personService = personService;
    }

    public PersonService getPersonService() {
        if(personService == null){
            personService = KimApiServiceLocator.getPersonService();
        }
        return personService;
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

        // Just to make it easier to track in DB
        String coCode = co.getCourseOfferingCode();
        if (coCode == null) {
            coCode = "NOCODE";
        }
        lui.setName(coCode + " CO");

        //Dynamic Attributes
        HashMap<String, AttributeInfo> attributesMap = new HashMap<String, AttributeInfo>();
        List<AttributeInfo> attributes = new ArrayList<AttributeInfo>();
        for (AttributeInfo attr : lui.getAttributes()) {
            attributesMap.put(attr.getKey(), attr) ;
        }
        for (AttributeInfo attr : co.getAttributes()) {
            attributesMap.put(attr.getKey(), attr) ;
        }

        AttributeInfo waitlistLevelTypeKey = new AttributeInfo();
        waitlistLevelTypeKey.setKey(CourseOfferingServiceConstants.WAIT_LIST_LEVEL_TYPE_KEY_ATTR);
        waitlistLevelTypeKey.setValue(String.valueOf(co.getWaitlistLevelTypeKey()));
        attributesMap.put(CourseOfferingServiceConstants.WAIT_LIST_LEVEL_TYPE_KEY_ATTR, waitlistLevelTypeKey);

        AttributeInfo waitlistTypeKey = new AttributeInfo();
        waitlistTypeKey.setKey(CourseOfferingServiceConstants.WAIT_LIST_TYPE_KEY_ATTR);
        waitlistTypeKey.setValue(String.valueOf(co.getWaitlistTypeKey()));
        attributesMap.put(CourseOfferingServiceConstants.WAIT_LIST_TYPE_KEY_ATTR, waitlistTypeKey);

        AttributeInfo waitlistIndicator = new AttributeInfo();
        waitlistIndicator.setKey(CourseOfferingServiceConstants.WAIT_LIST_INDICATOR_ATTR);
        waitlistIndicator.setValue(String.valueOf(co.getHasWaitlist()));
        attributesMap.put(CourseOfferingServiceConstants.WAIT_LIST_INDICATOR_ATTR, waitlistIndicator);

        AttributeInfo finalExamIndicator = new AttributeInfo();
        finalExamIndicator.setKey(CourseOfferingServiceConstants.FINAL_EXAM_INDICATOR_ATTR);
        finalExamIndicator.setValue(co.getFinalExamType());
        attributesMap.put(CourseOfferingServiceConstants.FINAL_EXAM_INDICATOR_ATTR, finalExamIndicator);

        AttributeInfo courseEvaluationIndicator = new AttributeInfo();
        courseEvaluationIndicator.setKey(CourseOfferingServiceConstants.COURSE_EVALUATION_INDICATOR_ATTR);
        courseEvaluationIndicator.setValue(String.valueOf(co.getIsEvaluated()));
        attributesMap.put(CourseOfferingServiceConstants.COURSE_EVALUATION_INDICATOR_ATTR, courseEvaluationIndicator);

        AttributeInfo whereFeesAttachedFlag = new AttributeInfo();
        whereFeesAttachedFlag.setKey(CourseOfferingServiceConstants.WHERE_FEES_ATTACHED_FLAG_ATTR);
        whereFeesAttachedFlag.setValue(String.valueOf(co.getIsFeeAtActivityOffering()));
        attributesMap.put(CourseOfferingServiceConstants.WHERE_FEES_ATTACHED_FLAG_ATTR, whereFeesAttachedFlag);

        AttributeInfo fundingSource = new AttributeInfo();
        fundingSource.setKey(CourseOfferingServiceConstants.FUNDING_SOURCE_ATTR);
        fundingSource.setValue(co.getFundingSource());
        attributesMap.put(CourseOfferingServiceConstants.FUNDING_SOURCE_ATTR, fundingSource);

        for (Map.Entry<String, AttributeInfo> entry : attributesMap.entrySet()) {
            attributes.add(entry.getValue());
        }

        lui.setAttributes(attributes);


        lui.setCluId(co.getCourseId());
        lui.setAtpId(co.getTermId());
        lui.setUnitsContentOwner(co.getUnitsContentOwnerOrgIds());
        lui.setUnitsDeployment(co.getUnitsDeploymentOrgIds());
        lui.setMaximumEnrollment(co.getMaximumEnrollment());
        lui.setMinimumEnrollment(co.getMinimumEnrollment());

        // there are primary key constraints on the resultValuesGroupKeys.
        // So we need to blow out old list, and replace with new
        // TODO: Shouldn't this be handled at the JPA level with some sort of merge?
        List<String> newOptions = new ArrayList<String>();
        newOptions.add(co.getGradingOptionId());
        newOptions.addAll(co.getStudentRegistrationGradingOptions());
        lui.setResultValuesGroupKeys(newOptions);
        lui.getResultValuesGroupKeys().add(co.getCreditOptionId());

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

        //TODO: the following mapping undecided on wiki
        //gradeRosterLevelTypeKey
        //fundingSource
        //isFinancialAidEligible
        //registrationOrderTypeKey

    }

    public void copyFromCanonical(CourseInfo courseInfo, CourseOfferingInfo courseOfferingInfo, List<String> optionKeys, ContextInfo context) throws InvalidParameterException, MissingParameterException, PermissionDeniedException, OperationFailedException {
        courseOfferingInfo.setCourseId(courseInfo.getId());
        if (!optionKeys.contains(CourseOfferingSetServiceConstants.NOT_COURSE_TITLE_OPTION_KEY)) {
         courseOfferingInfo.setCourseOfferingTitle(courseInfo.getCourseTitle());
        }
        courseOfferingInfo.setSubjectArea(courseInfo.getSubjectArea());

        if(optionKeys.contains(CourseOfferingServiceConstants.APPEND_COURSE_OFFERING_CODE_SUFFIX_OPTION_KEY)) {
            String codeSuffix = courseOfferingInfo.getCourseOfferingCode();
            courseOfferingInfo.setCourseOfferingCode(courseInfo.getCode() + codeSuffix);
        } else {
            courseOfferingInfo.setCourseOfferingCode(courseInfo.getCode());
        }

        courseOfferingInfo.setUnitsContentOwner(courseInfo.getUnitsContentOwner());
        courseOfferingInfo.setUnitsDeployment(courseInfo.getUnitsDeployment());

        //Split up the result keys for student registration options into a separate field.
        courseOfferingInfo.getStudentRegistrationGradingOptions().clear();
        courseOfferingInfo.setGradingOptionId(null);
        for(String resultValueGroupKey : courseInfo.getGradingOptions()){
            if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS, resultValueGroupKey)){
                courseOfferingInfo.getStudentRegistrationGradingOptions().add(resultValueGroupKey);
            }else if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_GRADING_OPTION_TYPE_KEYS, resultValueGroupKey)){
                if(courseOfferingInfo.getGradingOptionId()!=null){
                    //Log warning
                    LOG.warn("When Copying from Course CLU, multiple grading options were found");
                }
                courseOfferingInfo.setGradingOptionId(resultValueGroupKey);
            }
        }

        //Set the credit options as the first option from the clu
        if (courseInfo.getCreditOptions() != null && !courseInfo.getCreditOptions().isEmpty()) {
            //Convert R1 to R2 LRC data
            ResultComponentInfo resultComponent = courseInfo.getCreditOptions().get(0);

            // Credit Options (also creates extra-line)
            if (LrcServiceConstants.R1_RESULT_COMPONENT_TYPE_KEY_FIXED.equals(resultComponent.getType())) {
                ResultValuesGroupInfo rvgInfo = getLrcService().getCreateFixedCreditResultValuesGroup(resultComponent.getAttributes().get(LrcServiceConstants.R1_DYN_ATTR_CREDIT_OPTION_FIXED_CREDITS),
                        LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, context);
                courseOfferingInfo.setCreditOptionId(rvgInfo.getKey());
            } else if (LrcServiceConstants.R1_RESULT_COMPONENT_TYPE_KEY_RANGE.equals(resultComponent.getType())) {
                ResultValuesGroupInfo rvgInfo = getLrcService().getCreateRangeCreditResultValuesGroup(resultComponent.getAttributes().get(LrcServiceConstants.R1_DYN_ATTR_CREDIT_OPTION_MIN_CREDITS),
                        resultComponent.getAttributes().get(LrcServiceConstants.R1_DYN_ATTR_CREDIT_OPTION_MAX_CREDITS), "1", LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, context);
                courseOfferingInfo.setCreditOptionId(rvgInfo.getKey());
            } else if (LrcServiceConstants.R1_RESULT_COMPONENT_TYPE_KEY_MULTIPLE.equals(resultComponent.getType())) {
                ResultValuesGroupInfo rvgInfo = getLrcService().getCreateMultipleCreditResultValuesGroup(resultComponent.getResultValues(),
                        LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE, context);
                courseOfferingInfo.setCreditOptionId(rvgInfo.getKey());
            }
        }else{
            courseOfferingInfo.setCreditOptionId(null);
        }

        //Log warning if the Clu has multiple credit options
        if(courseInfo.getCreditOptions().size() > 1){
            LOG.warn("When Copying from Course CLU, multiple credit options were found");
        }

        courseOfferingInfo.setDescr(new R1ToR2CopyHelper().copyRichText(courseInfo.getDescr()));
        courseOfferingInfo.setInstructors(new R1ToR2CopyHelper().copyInstructors(courseInfo.getInstructors()));
    }

    public void assembleInstructors(CourseOfferingInfo co, String luiId, ContextInfo context, LprService lprService) {
        List<LprInfo> lprs = null;
        try {
            lprs = lprService.getLprsByLui(luiId, context);
        } catch (DoesNotExistException e) {
            LOG.warn("Instructors do not exist for LuiId: " + luiId, e);
        } catch (InvalidParameterException e) {
            LOG.error("Error getting instructors for LuiId: " + luiId + " Invalid Parameter ", e);
            throw new RuntimeException("Error getting instructors for LuiId: " + luiId + " Invalid Parameter ", e);
        } catch (MissingParameterException e) {
            LOG.error("Error getting instructors for LuiId: " + luiId + " Missing Parameter ", e);
            throw new RuntimeException("Error getting instructors for LuiId: " + luiId + " Missing Parameter ", e);
        } catch (OperationFailedException e) {
            LOG.error("Error getting instructors for LuiId: " + luiId + " Operation Failed ", e);
            throw new RuntimeException("Error getting instructors for LuiId: " + luiId + " Operation Failed ", e);
        } catch (PermissionDeniedException e) {
            LOG.error("Error getting instructors for LuiId: " + luiId + " Permission Denied ", e);
            throw new RuntimeException("Error getting instructors for LuiId: " + luiId + " Permission Denied ", e);
        }

        for (LprInfo lpr : lprs) {
            if (lpr.getStateKey()==null || !lpr.getStateKey().equals(LprServiceConstants.DROPPED_STATE_KEY)) {
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

                 // Should be only one person found by person id
                List<Person> personList = OfferingInstructorTransformer.getInstructorByPersonId(instructor.getPersonId());
                if(personList != null && !personList.isEmpty()){
                    instructor.setPersonName(personList.get(0).getName());
                }
                co.getInstructors().add(instructor);
            }

        }
    }


    public LRCService getLrcService() {
        if(lrcService == null){
            lrcService = GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE,LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }
}
