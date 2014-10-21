package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.kuali.rice.core.api.criteria.PredicateFactory;
import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.kim.api.identity.Person;
import org.kuali.rice.kim.api.identity.PersonService;
import org.kuali.rice.kim.api.services.KimApiServiceLocator;
import org.kuali.rice.krms.impl.util.KrmsRuleManagementCopyMethods;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingCrossListingInfo;
import org.kuali.student.enrollment.courseoffering.dto.CourseOfferingInfo;
import org.kuali.student.enrollment.courseoffering.dto.OfferingInstructorInfo;
import org.kuali.student.enrollment.lpr.dto.LprInfo;
import org.kuali.student.enrollment.lpr.service.LprService;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.enrollment.lui.service.LuiService;
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
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.lum.clu.dto.CluInstructorInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.service.assembler.CourseAssemblerConstants;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.util.constants.CluServiceConstants;
import org.kuali.student.r2.lum.util.constants.CourseServiceConstants;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.namespace.QName;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CourseOfferingTransformer {
    private LprService lprService;
    private PersonService personService;
    private LRCService lrcService;
    private CluService cluService;
    private LuiService luiService;
    private KrmsRuleManagementCopyMethods krmsRuleManagementCopyMethods;

    private static final Logger LOG = LoggerFactory.getLogger(CourseOfferingTransformer.class);

    protected static Logger getLog() {
        return LOG;
    }

    /**
     * Transform a list of LuiInfos into CourseOfferingInfos. It is the bulk version of lui2CourseOffering transformer
     *
     * @param courseOfferingIds     the list of courseOfferingIds which is used to retrieve the list of LuiInfos
     * @param cos                   the reference of CourseOfferingInfo list whith points to the transformed CourseOfferingInfo list
     * @param context               information containing the principalId and locale
     *                              information about the caller of service operation
     * @throws DoesNotExistException     ActivityOfferingDisplayInfo is not found
     * @throws InvalidParameterException contextInfo is not valid
     * @throws MissingParameterException courseOfferingIds, cos, stateService, or context is missing or null
     * @throws OperationFailedException  unable to complete request
     * @throws PermissionDeniedException an authorization failure occurred
     */
    public void luis2CourseOfferings(List<String> courseOfferingIds, List<CourseOfferingInfo>cos, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        if(courseOfferingIds == null || courseOfferingIds.isEmpty()){
            getLog().warn("invalid courseOfferingIds");
            return;
        }

        List<String> cluIds = new ArrayList<String>();
        List<String> luiIds = new ArrayList<String>();
        List<LuiInfo> luiInfos = getLuiService().getLuisByIds(courseOfferingIds, context);

        // lui id to list of result value group keys
        Map<String, List<String>>luiToResultValueGroupKeysMap = new HashMap<String, List<String>>();
        // rvg key to rvg info
        Map<String, ResultValuesGroupInfo>rvgKeyToResultValueGroupMap = new HashMap<String, ResultValuesGroupInfo>();
        // result value key to result value
        Map<String, ResultValueInfo>resultValueKeyToResultValueMap = new HashMap<String, ResultValueInfo>();
        // cluid to list of results
        Map<String, List<CluResultInfo>> cluResultListMap = new HashMap<String, List<CluResultInfo>>();

        Set<String> totalResultValueGroupKeySet = new HashSet<String>();
        Set<String> totalResultValueKeySet = new HashSet<String>();

        for (LuiInfo lui : luiInfos) {
            List<String> rvgKeys = lui.getResultValuesGroupKeys();
            luiToResultValueGroupKeysMap.put(lui.getId(), rvgKeys);

            // build the union of all the result value group keys
            totalResultValueGroupKeySet.addAll(rvgKeys);
            cluIds.add(lui.getCluId());
            luiIds.add(lui.getId());
        }

        //retrieve a list of CluResultInfo by a list of cluId and generate the map of cluId to CluResultInfo list
		if (getCluService() != null) {
			List<CluResultInfo> cluResults = getCluService().getCluResultsByClus(
                    cluIds, context);
			for (CluResultInfo cluResultInfo : cluResults) {
				List<CluResultInfo> resultsList = cluResultListMap
						.get(cluResultInfo.getCluId());
				if (resultsList == null) {
					resultsList = new ArrayList<CluResultInfo>();
					cluResultListMap.put(cluResultInfo.getCluId(), resultsList);
				}

				resultsList.add(cluResultInfo);
				totalResultValueGroupKeySet.add(cluResultInfo
						.getResultOptions().get(0).getResultComponentId());
			}
		} else {
			getLog().warn("CourseOfferingTransformer: 'cluService' dependency is not configured.");
		}

        //retrieve a list of ResultValuesGroupInfo by a list of result value group key and generate the map of rvg key to ResultValuesGroupInfo
        List<String>totalResultValueGroupKeyList = new ArrayList<String>(totalResultValueGroupKeySet);
        List<ResultValuesGroupInfo> rvgList = getLrcService().getResultValuesGroupsByKeys(totalResultValueGroupKeyList, context);
        for (ResultValuesGroupInfo rvg : rvgList) {
            // store all of the result value groups
            rvgKeyToResultValueGroupMap.put(rvg.getKey(), rvg);

            totalResultValueKeySet.addAll(rvg.getResultValueKeys());
        }

        //retrieve a list of ResultValueInfo by a list of result value key and generate the map of result value key to ResultValueInfo
        List<String> totalResultValueKeyList = new ArrayList<String>(totalResultValueKeySet);
        List<ResultValueInfo> resultValues = getLrcService().getResultValuesByKeys(totalResultValueKeyList, context);
        if (resultValues != null && resultValues.size() > 0) {
            for (ResultValueInfo resultValueInfo : resultValues) {
                resultValueKeyToResultValueMap.put(resultValueInfo.getKey(), resultValueInfo);
            }
        }

        //retrieve a list of LprInfo by a list of luiIds and generate the map of luiId to LprInfo
        List<LprInfo> lprs = new ArrayList<LprInfo>();
        for(String ao: luiIds){
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.in("luiId", ao),
                    PredicateFactory.in("personRelationTypeId", LprServiceConstants.COURSE_INSTRUCTOR_TYPE_KEYS));
            QueryByCriteria criteria = qbcBuilder.build();
            lprs.addAll(getLprService().searchForLprs(criteria, context));
        }
        Map<String, List<LprInfo>>luiToLprListMap = new HashMap<String, List<LprInfo>>();
        for (LprInfo lprInfo : lprs) {
            List<LprInfo> lprList = luiToLprListMap.get(lprInfo.getLuiId());
            if (lprList == null) {
                lprList = new ArrayList<LprInfo>();
                luiToLprListMap.put(lprInfo.getLuiId(), lprList);
            }
            lprList.add(lprInfo);
        }

        for (LuiInfo luiInfo : luiInfos) {
            CourseOfferingInfo co = new CourseOfferingInfo();
            lui2CourseOffering(luiInfo, co, cluResultListMap, rvgKeyToResultValueGroupMap, resultValueKeyToResultValueMap);
            List<LprInfo>courseInstructors = luiToLprListMap.get(luiInfo.getId());
            assembleInstructorsByLprs(co, courseInstructors);

            cos.add(co);

        }
    }

    /**
     * Transform a LuiInfo into an CourseOfferingInfo. It takes cached maps of cluResultListMap,
     * rvgMap and resultValueMap as the params instead of doing
     * service calls inside to retrieve CluResultInfo, ResultValuesGroupInfo and ResultValueInfo
     *
     * @param lui                   the LuiInfo that is transformed into CourseOfferingInfo
     * @param co                    the reference of CourseOfferingInfo that is transformed from LuiInfo
     * @param cluResultListMap      the cached map of cluID to CluResultInfo list
     * @param rvgMap                the cached map of rvg key to ResultValuesGroupInfo
     * @param resultValueMap        the cached map of result value key to ResultValueInfo
     *
     */
    protected void lui2CourseOffering(LuiInfo lui, CourseOfferingInfo co, Map<String, List<CluResultInfo>> cluResultListMap, Map<String, ResultValuesGroupInfo> rvgMap, Map<String, ResultValueInfo> resultValueMap) {
        co.setId(lui.getId());
        co.setTypeKey(lui.getTypeKey());
        co.setStateKey(lui.getStateKey());
        co.setDescr(lui.getDescr());
        co.setMeta(lui.getMeta());
        co.setCourseOfferingURL(lui.getReferenceURL());

        co.setCrossListings(buildCrosslistingsFromLui(lui));

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
            } else if (CourseOfferingServiceConstants.COURSE_NUMBER_IN_SUFX_ATTR.equals(attr.getKey())){
                co.setCourseNumberInternalSuffix(attr.getValue());
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
        co.setUnitsDeploymentOrgIds(lui.getUnitsDeployment());
        co.setUnitsContentOwnerOrgIds(lui.getUnitsContentOwner());

        //Split up the result keys for student registration options into a separate field.
        co.getStudentRegistrationGradingOptions().clear();
        co.setGradingOptionId(null);

        for(String resultValueGroupKeyRef : lui.getResultValuesGroupKeys()){
            String resultValueGroupKey = new String(resultValueGroupKeyRef);   // values from the map are pass by ref so we need to create a new instance.
            if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS, resultValueGroupKey)){
                co.getStudentRegistrationGradingOptions().add(resultValueGroupKey);
            } else if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_GRADING_OPTION_TYPE_KEYS, resultValueGroupKey)){
                if(co.getGradingOptionId()!=null){
                    throw new RuntimeException("This course[lui.id="+ co.getId() +", rvg key="+resultValueGroupKey+"] offering has multiple grading options in the data. It should only have at most one.\n" + rvgMap.toString() );
                }
                co.setGradingOptionId(resultValueGroupKey);
            } else if(resultValueGroupKey!=null && resultValueGroupKey.startsWith("kuali.creditType.credit")){//There should be a better way of distinguishing credits from other results
                co.setCreditOptionId(resultValueGroupKey);
            }
        }

        // we need to set the creditOptionId on the CO If it doesn't exist.
        if(co.getCreditOptionId() == null || co.getCreditOptionId().equals("")){
            //co.setCreditOptionId(this.getCreditOptionId(co.getCourseId(), cluResultListMap));
            getLog().error("This course offering (" + co.getCourseOfferingCode() + ") is invalid. Credit option must have a value. ");
            throw new NullPointerException("This course offering ("+co.getCourseOfferingCode()+") is invalid. Credit option must have a value. ");
        }

        co.setCreditCnt(getCreditCount(co.getCreditOptionId(), co.getCourseId(), rvgMap, resultValueMap, null));

        if ( co.getGradingOptionId() != null ) {//TODO why are we doing substrings of keys?
            co.setGradingOption(co.getGradingOptionId().substring(co.getGradingOptionId().lastIndexOf('.') + 1));
        } else {
            getLog().error("This course offering (" + co.getCourseOfferingCode() + ") is invalid. Grading option must have a value. ");
            throw new NullPointerException("This course offering ("+co.getCourseOfferingCode()+") is invalid. Grading option must have a value. ");
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
        //assembleLuiLuiRelations(co, lui.getId(), context);
        return;
    }

    public void lui2CourseOffering(LuiInfo lui, CourseOfferingInfo co, ContextInfo context) {

        co.setId(lui.getId());
        co.setTypeKey(lui.getTypeKey());
        co.setStateKey(lui.getStateKey());
        co.setDescr(lui.getDescr());
        co.setMeta(lui.getMeta());
        co.setCourseOfferingURL(lui.getReferenceURL());

        co.setCrossListings( buildCrosslistingsFromLui(lui) );

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
            } else if (CourseOfferingServiceConstants.COURSE_NUMBER_IN_SUFX_ATTR.equals(attr.getKey())){
                co.setCourseNumberInternalSuffix(attr.getValue());
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
        co.setUnitsDeploymentOrgIds(lui.getUnitsDeployment());
        co.setUnitsContentOwnerOrgIds(lui.getUnitsContentOwner());

        //Split up the result keys for student registration options into a separate field.
        co.getStudentRegistrationGradingOptions().clear();
        co.setGradingOptionId(null);

        List<String> resultValuesGroupKeys = lui.getResultValuesGroupKeys();
        for(String resultValueGroupKey : resultValuesGroupKeys){
            if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS, resultValueGroupKey)){
                co.getStudentRegistrationGradingOptions().add(resultValueGroupKey);
            }else if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_GRADING_OPTION_TYPE_KEYS, resultValueGroupKey)){
                 co.setGradingOptionId(resultValueGroupKey);
            }else if(resultValueGroupKey!=null && resultValueGroupKey.startsWith("kuali.creditType.credit")){//There should be a better way of distinguishing credits from other results
                co.setCreditOptionId(resultValueGroupKey);
            }
        }
        co.setCreditCnt(getCreditCount(co.getCreditOptionId(), "", null, null, context));

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
        //lui.getName() -- where to map?
        //lui.getReferenceURL() -- where to map?
        //LuiLuiRelation (to set jointOfferingIds, hasFinalExam)
        //assembleLuiLuiRelations(co, lui.getId(), context);

        return;
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

        // cross-listing work
        assignCourseOfferingInfoToCrossListings( co );
        lui.setAlternateIdentifiers( buildAlternateIdentifiersFromCo(co) );

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

        AttributeInfo courseNumberInternalSuffix = new AttributeInfo();
        courseNumberInternalSuffix.setKey(CourseOfferingServiceConstants.COURSE_NUMBER_IN_SUFX_ATTR);
        courseNumberInternalSuffix.setValue(co.getCourseNumberInternalSuffix());
        attributesMap.put(CourseOfferingServiceConstants.COURSE_NUMBER_IN_SUFX_ATTR, courseNumberInternalSuffix);

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
        if(co.getCreditOptionId() != null){
            lui.getResultValuesGroupKeys().add(co.getCreditOptionId());
        }
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

    protected void assignCourseOfferingInfoToCrossListings(CourseOfferingInfo co) {

        if( co == null || co.getCrossListings() == null ) return;

        for( CourseOfferingCrossListingInfo cross : co.getCrossListings() ) {
            cross.setLongName(co.getCourseOfferingTitle());
            cross.setCourseNumberSuffix( co.getCourseNumberSuffix() );
            if(!StringUtils.isEmpty(cross.getCourseNumberSuffix()))
                cross.setCode( cross.getCode() + cross.getCourseNumberSuffix() );
        }
    }

    protected List<CourseOfferingCrossListingInfo> buildCrosslistingsFromLui( LuiInfo lui ) {

        List<CourseOfferingCrossListingInfo> result = new ArrayList<CourseOfferingCrossListingInfo>();
        for(LuiIdentifierInfo luiIdentifierInfo : lui.getAlternateIdentifiers()) {

            CourseOfferingCrossListingInfo info = new CourseOfferingCrossListingInfo();

            info.setId(luiIdentifierInfo.getId());
            info.setTypeKey(luiIdentifierInfo.getTypeKey());
            info.setStateKey(luiIdentifierInfo.getStateKey());
            info.setCode(luiIdentifierInfo.getCode());
            info.setLongName(luiIdentifierInfo.getLongName());
            info.setSubjectArea(luiIdentifierInfo.getDivision());
            info.setSubjectOrgId(luiIdentifierInfo.getOrgId());
            info.setCourseNumberSuffix(luiIdentifierInfo.getSuffixCode());
            info.setMeta(luiIdentifierInfo.getMeta());
            info.setAttributes(luiIdentifierInfo.getAttributes());


            result.add(info);
        }

        return result;
    }

    protected List<LuiIdentifierInfo> buildAlternateIdentifiersFromCo( CourseOfferingInfo co ) {

        List<LuiIdentifierInfo> result = new ArrayList<LuiIdentifierInfo>();
        for(CourseOfferingCrossListingInfo crossListingInfo : co.getCrossListings()) {

            LuiIdentifierInfo info = new LuiIdentifierInfo();

            info.setId(crossListingInfo.getId());
            info.setTypeKey(crossListingInfo.getTypeKey());
            info.setStateKey(crossListingInfo.getStateKey());
            info.setCode(crossListingInfo.getCode());
            info.setLongName(crossListingInfo.getLongName());
            info.setDivision(crossListingInfo.getSubjectArea());
            info.setSuffixCode(crossListingInfo.getCourseNumberSuffix());
            info.setOrgId(crossListingInfo.getSubjectOrgId());
            info.setMeta(crossListingInfo.getMeta());
            info.setAttributes(crossListingInfo.getAttributes());

            result.add(info);
        }

        return result;
    }

    public static String trimTrailing0(String creditValue){
        if (creditValue.indexOf(".0") > 0) {
            return creditValue.substring(0, creditValue.length( )- 2);
        } else {
            return creditValue;
        }
    }

    protected String getCreditOptionId(String courseId, Map<String, List<CluResultInfo>> cluResultListMap){

        String creditOptionId = "";
        List<CluResultInfo> cluResults = cluResultListMap.get(courseId);
        if(cluResults != null){
            for(CluResultInfo cluResultInfo : cluResults){
                if(CourseAssemblerConstants.COURSE_RESULT_TYPE_CREDITS.equals(cluResultInfo.getTypeKey())){
                    creditOptionId = cluResultInfo.getResultOptions().get(0).getResultComponentId();
                    break;
                }
            }
        }
        return creditOptionId;
    }

    //get credit count from persisted COInfo
    public String getCreditCount(String creditOptionId,
                                  String courseId,
                                  Map<String, ResultValuesGroupInfo> rvgMap,
                                  Map<String, ResultValueInfo>resultValueMap,
                                  ContextInfo contextInfo) {
        String creditCount="";
        ResultValuesGroupInfo resultValuesGroupInfo;
        List<ResultValueInfo> resultValueInfos;
        try{
            //Lookup persisted values (if the CO has a Credit set use that, otherwise the CO is invalid)
            if(creditOptionId == null || creditOptionId.equals("")){
                getLog().info("Credit is missing for this course offering");
                return creditCount = "N/A";
            } else {
                if (rvgMap!= null) {  //where the function is called from
                    resultValuesGroupInfo = rvgMap.get(creditOptionId);
                } else {
                    resultValuesGroupInfo = getLrcService().getResultValuesGroup(creditOptionId, contextInfo);
                }
                String typeKey = resultValuesGroupInfo.getTypeKey();
                if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_FIXED)) {
                    if (resultValueMap != null) {  //where the function is called from
                        resultValueInfos = getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), resultValueMap);
                    } else {
                        resultValueInfos = getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), contextInfo);
                    }

                    //Code Modified for JIRA 8727 fixing SONAR issue
                    int firstResultValueInfo = 0;
                    if(!resultValueInfos.isEmpty()){
                        creditCount = trimTrailing0(resultValueInfos.get(firstResultValueInfo).getValue());
                    } else{
                        getLog().info("Credit is missing for this course offering");
                        return creditCount = "N/A";
                    }

                } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_RANGE)) {                          //range
                    //Use the min/max values from the RVG
                    creditCount = trimTrailing0(resultValuesGroupInfo.getResultValueRange().getMinValue()) + " - " +
                            trimTrailing0(resultValuesGroupInfo.getResultValueRange().getMaxValue());
                } else if (typeKey.equals(LrcServiceConstants.RESULT_VALUES_GROUP_TYPE_KEY_MULTIPLE)) {
                    //Get the actual values with a service call
                    if (resultValueMap != null) {  //where the function is called from
                        resultValueInfos = getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), resultValueMap);
                    } else {
                        resultValueInfos = getLrcService().getResultValuesByKeys(resultValuesGroupInfo.getResultValueKeys(), contextInfo);
                    }
                    if (!resultValueInfos.isEmpty()) {
                        //Convert to floats and sort
                        List<Float> creditValuesF = new ArrayList<Float>();
                        for (ResultValueInfo resultValueInfo : resultValueInfos ) {  //convert String to Float for sorting
                            creditValuesF.add(Float.valueOf(resultValueInfo.getValue()));
                        }
                        Collections.sort(creditValuesF); //Do the sort

                        StringBuilder creditCounts = new StringBuilder();
                        //Convert back to strings and concatenate to one field
                        for (Float creditF : creditValuesF ){
                            creditCounts.append(", ").append(trimTrailing0(String.valueOf(creditF)));
                        }
                        if(creditCounts.length() >=  2)  {
                            creditCount =  creditCounts.substring(2);  //trim leading ", "
                        }
                    }
                } else {
                    //no credit option
                    getLog().info("Credit is missing for course id {}", courseId);
                    creditCount = "N/A";
                }
            }
            return creditCount;
        }catch (Exception e){
            throw new RuntimeException("Error getting credit count for course offering", e);
        }
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

    protected String boolean2String(Boolean bval) {
        if (bval == null) {
            return null;
        }
        return bval.toString();
    }

    protected Boolean string2Boolean(String sval) {
        if (sval == null) {
            return null;
        }
        return Boolean.parseBoolean(sval.toString());
    }

    protected LuCodeInfo findLuCode(LuiInfo lui, String typeKey) {
        for (LuCodeInfo info : lui.getLuiCodes()) {
            if (info.getTypeKey().equals(typeKey)) {
                return info;
            }
        }
        return null;
    }

    protected LuCodeInfo findAddLuCode(LuiInfo lui, String typeKey) {
        LuCodeInfo info = this.findLuCode(lui, typeKey);
        if (info != null) {
            return info;
        }
        info = new LuCodeInfo();
        info.setTypeKey(typeKey);
        lui.getLuiCodes().add(info);
        return info;
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

        //Set the units deployment orgs to the unit content owner of the course
        courseOfferingInfo.setUnitsContentOwnerOrgIds(courseInfo.getUnitsContentOwner());
        if (courseOfferingInfo.getUnitsDeploymentOrgIds().size() < 1) {
            courseOfferingInfo.setUnitsDeploymentOrgIds(courseInfo.getUnitsContentOwner());
        }

        //Split up the result keys for student registration options into a separate field.
        for(String resultValueGroupKey : courseInfo.getGradingOptions()){
            if(ArrayUtils.contains(CourseOfferingServiceConstants.ALL_STUDENT_REGISTRATION_OPTION_TYPE_KEYS, resultValueGroupKey)
                    && !courseOfferingInfo.getStudentRegistrationGradingOptions().contains(resultValueGroupKey)){
                courseOfferingInfo.getStudentRegistrationGradingOptions().add(resultValueGroupKey);
            }else if(courseOfferingInfo.getGradingOptionId() == null &&
                    ArrayUtils.contains(CourseOfferingServiceConstants.ALL_GRADING_OPTION_TYPE_KEYS, resultValueGroupKey)){
                courseOfferingInfo.setGradingOptionId(resultValueGroupKey);
            }
        }

        //Set the credit options as the first option from the clu
        if (courseInfo.getCreditOptions() != null && !courseInfo.getCreditOptions().isEmpty()) {
            if(!courseInfo.getCreditOptions().contains(courseOfferingInfo.getCreditOptionId())){
                //In this case, the supplied credit option does not exist in the Clu, so default to the first
                courseOfferingInfo.setCreditOptionId(courseInfo.getCreditOptions().get(0).getKey());
            }
            //Otherwise use what is supplied.
        }else{
            //In this case the Clu has no credit options, but the lui does (should not happen)
            throw new OperationFailedException("Target Course has no credit options");
            //courseOfferingInfo.setCreditOptionId(null);
        }

        //Log warning if the Clu has multiple credit options
        if(courseInfo.getCreditOptions().size() > 1){
            getLog().warn("When Copying from Course CLU, multiple credit options were found");
        }

        courseOfferingInfo.setDescr(courseInfo.getDescr());
    }

    public void copyRulesFromCanonical(CourseInfo courseInfo,
            CourseOfferingInfo courseOfferingInfo,
            List<String> optionKeys, ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            PermissionDeniedException,
            OperationFailedException {
        if (courseOfferingInfo.getId() == null) {
            throw new InvalidParameterException("Target CourseOffering should already have it's id assigned");
        }
        getKrmsRuleManagementCopyMethods().deepCopyReferenceObjectBindingsFromTo(
                CourseServiceConstants.REF_OBJECT_URI_COURSE,
                courseInfo.getId(),
                CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING,
                courseOfferingInfo.getId(),
                optionKeys);
    }

    
    public void copyRulesFromExistingCourseOffering(CourseOfferingInfo sourceCo,
            CourseOfferingInfo targetCo,
            List<String> optionKeys, ContextInfo context)
            throws InvalidParameterException,
            MissingParameterException,
            PermissionDeniedException,
            OperationFailedException {
        if (targetCo.getId() == null) {
            throw new InvalidParameterException("Target CourseOffering should already have it's id assigned");
        }
        getKrmsRuleManagementCopyMethods().deepCopyReferenceObjectBindingsFromTo(
                CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING,
                sourceCo.getId(),
                CourseOfferingServiceConstants.REF_OBJECT_URI_COURSE_OFFERING,
                targetCo.getId(),
                optionKeys);
    }

    public void deleteExistingRulesOnCourseOffering(CourseOfferingInfo courseOffering) throws InvalidParameterException {
        if (courseOffering.getId() == null) {
            throw new InvalidParameterException("Target CourseOffering should already have it's id assigned");
        }
        getKrmsRuleManagementCopyMethods().deleteReferenceObjectBindingsCascade(LuiServiceConstants.COURSE_OFFERING_TYPE_KEY, courseOffering.getId());
    }
    
    public List<OfferingInstructorInfo> copyInstructors(List<CluInstructorInfo> cluInstructors) {
        if (cluInstructors == null) {
            return null;
        }
        List<OfferingInstructorInfo> coInstructors = new ArrayList<OfferingInstructorInfo>(cluInstructors.size());
        for (CluInstructorInfo cluInstructor : cluInstructors) {
            coInstructors.add(copyInstructor(cluInstructor));
        }
        return coInstructors;
    }

    public OfferingInstructorInfo copyInstructor(CluInstructorInfo cluInstructor) {
        if (cluInstructor == null) {
            return null;
        }
        OfferingInstructorInfo coInstructor = new OfferingInstructorInfo();
        List<AttributeInfo> attrs = new ArrayList<AttributeInfo>();
        for (AttributeInfo attr : cluInstructor.getAttributes()) {
            attrs.add(new AttributeInfo(attr));
        }
        coInstructor.setAttributes(attrs);
        coInstructor.setPersonId(cluInstructor.getPersonId());

        return coInstructor;
    }


    public void assembleInstructors(CourseOfferingInfo co, String luiId, ContextInfo context, LprService lprService) {
        List<LprInfo> lprs = null;
        try {
            QueryByCriteria.Builder qbcBuilder = QueryByCriteria.Builder.create();
            qbcBuilder.setPredicates(PredicateFactory.in("luiId", co.getId()),
                    PredicateFactory.in("personRelationTypeId", LprServiceConstants.COURSE_INSTRUCTOR_TYPE_KEYS));
            QueryByCriteria coCriteria = qbcBuilder.build();
            QueryByCriteria criteria = qbcBuilder.build();

            lprs = lprService.searchForLprs(criteria, context);
        } catch (InvalidParameterException e) {
            String errorMessage = String.format("Error getting instructors for LuiId: %s Invalid Parameter ", luiId);
            getLog().error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        } catch (MissingParameterException e) {
            String errorMessage = String.format("Error getting instructors for LuiId: %s Missing Parameter ", luiId);
            getLog().error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        } catch (OperationFailedException e) {
            String errorMessage = String.format("Error getting instructors for LuiId: %s Operation Failed ", luiId);
            getLog().error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        } catch (PermissionDeniedException e) {
            String errorMessage = String.format("Error getting instructors for LuiId: %s Permission Denied ", luiId);
            getLog().error(errorMessage, e);
            throw new RuntimeException(errorMessage, e);
        }

        assembleInstructorsByLprs(co, lprs);
    }

    protected void assembleInstructorsByLprs(CourseOfferingInfo co, List<LprInfo> lprs) {
        if (lprs != null && lprs.size() > 0) {
            for (LprInfo lpr : lprs) {
                if (lpr.getStateKey() == null || !lpr.getStateKey().equals(LprServiceConstants.DROPPED_STATE_KEY)) {
                    OfferingInstructorInfo instructor = new OfferingInstructorInfo();
                    instructor.setPersonId(lpr.getPersonId());
                    if (lpr.getCommitmentPercent() != null) {
                        instructor.setPercentageEffort(lpr.getCommitmentPercent().floatValue());
                    } else {
                        instructor.setPercentageEffort(null);
                    }
                    instructor.setId(lpr.getId());
                    instructor.setTypeKey(lpr.getTypeKey());
                    instructor.setStateKey(lpr.getStateKey());

                    // Should be only one person found by person id
                    List<Person> personList = OfferingInstructorTransformer.getInstructorByPersonId(instructor.getPersonId());
                    if (personList != null && !personList.isEmpty()) {
                        int firstPerson = 0;
                        instructor.setPersonName(personList.get(firstPerson).getName());
                    }
                    co.getInstructors().add(instructor);
                }
            }
        }
    }

    /**
     * Get the list of values from the cached map of results.
     *
     * @param resultValueKeys       the list of result value keys
     * @param resultValueMap        the cached map of result value key to ResultValueInfo
     * @return                      ResultValueInfo list
     *
     */
    protected List<ResultValueInfo> getResultValuesByKeys(List<String> resultValueKeys,
                                                        Map<String, ResultValueInfo>resultValueMap) {

        List<ResultValueInfo> values = new ArrayList<ResultValueInfo>();

        for (String key : resultValueKeys) {

            ResultValueInfo value = resultValueMap.get(key);

            values.add(value);
        }

        return values;

    }

    public CluService getCluService() {
        if(cluService == null){
            cluService = GlobalResourceLoader.getService(new QName(CluServiceConstants.CLU_NAMESPACE, CluServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return cluService;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public LRCService getLrcService() {
        if(lrcService == null){
            lrcService = GlobalResourceLoader.getService(new QName(LrcServiceConstants.NAMESPACE, LrcServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return lrcService;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public LuiService getLuiService() {
        if(luiService == null){
            luiService = GlobalResourceLoader.getService(new QName(LuiServiceConstants.NAMESPACE, LuiServiceConstants.SERVICE_NAME_LOCAL_PART));
        }
        return luiService;
    }

    public void setLuiService(LuiService luiService) {
        this.luiService = luiService;
    }

    public KrmsRuleManagementCopyMethods getKrmsRuleManagementCopyMethods() {
        return krmsRuleManagementCopyMethods;
    }

    public void setKrmsRuleManagementCopyMethods(KrmsRuleManagementCopyMethods krmsRuleManagementCopyMethods) {
        this.krmsRuleManagementCopyMethods = krmsRuleManagementCopyMethods;
    }

}
