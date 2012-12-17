/*
 * Copyright 2008 The Kuali Foundation
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.kuali.student.r2.lum.course.service.assembler;



import org.apache.log4j.Logger;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r1.common.assembly.BOAssembler;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode;
import org.kuali.student.r1.common.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.DtoConstants;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;
import org.kuali.student.r2.lum.clu.dto.AdminOrgInfo;
import org.kuali.student.r2.lum.clu.dto.CluAccountingInfo;
import org.kuali.student.r2.lum.clu.dto.CluCluRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeInfo;
import org.kuali.student.r2.lum.clu.dto.CluFeeRecordInfo;
import org.kuali.student.r2.lum.clu.dto.CluIdentifierInfo;
import org.kuali.student.r2.lum.clu.dto.CluInfo;
import org.kuali.student.r2.lum.clu.dto.CluLoRelationInfo;
import org.kuali.student.r2.lum.clu.dto.CluResultInfo;
import org.kuali.student.r2.lum.clu.dto.LuCodeInfo;
import org.kuali.student.r2.lum.clu.dto.ResultOptionInfo;
import org.kuali.student.r2.lum.clu.service.CluService;
import org.kuali.student.r2.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.r2.lum.course.dto.CourseExpenditureInfo;
import org.kuali.student.r2.lum.course.dto.CourseFeeInfo;
import org.kuali.student.r2.lum.course.dto.CourseInfo;
import org.kuali.student.r2.lum.course.dto.CourseJointInfo;
import org.kuali.student.r2.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.r2.lum.course.dto.CourseVariationInfo;
import org.kuali.student.r2.lum.course.dto.FormatInfo;
import org.kuali.student.r2.lum.course.dto.LoDisplayInfo;
import org.kuali.student.r2.lum.lo.dto.LoInfo;
import org.kuali.student.r2.lum.lo.service.LearningObjectiveService;
import org.kuali.student.r2.lum.lrc.dto.ResultValueInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValueRangeInfo;
import org.kuali.student.r2.lum.lrc.dto.ResultValuesGroupInfo;
import org.kuali.student.r2.lum.lrc.service.LRCService;
import org.kuali.student.r2.lum.service.assembler.CluAssemblerUtils;
import org.kuali.student.r2.lum.util.constants.LrcServiceConstants;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Assembler for CourseInfo. Provides assemble and disassemble operation on
 * CourseInfo from/to CluInfo and other base DTOs
 *
 * @author Kuali Student Team
 *
 */

public class CourseAssembler implements BOAssembler<CourseInfo, CluInfo> {

    final static Logger LOG = Logger.getLogger(CourseAssembler.class);
    private CluService cluService;
    private FormatAssembler formatAssembler;
    private CourseJointAssembler courseJointAssembler;
    private LoAssembler loAssembler;
    private LearningObjectiveService loService;
    private CluAssemblerUtils cluAssemblerUtils;
    private LRCService lrcService;
    private AtpService atpService;
    private float defaultCreditIncrement = 1.0f;
    //
    @Override
    public CourseInfo assemble(CluInfo clu, CourseInfo courseInfo,
                               boolean shallowBuild,ContextInfo contextInfo) throws AssemblyException {

        CourseInfo course = (null != courseInfo) ? courseInfo
                : new CourseInfo();

        // Copy all the data from the clu to the course

        course.setAttributes(clu.getAttributes());
        course.setCampusLocations(clu.getCampusLocations());
        course.setCode(clu.getOfficialIdentifier().getCode());
        course.setCourseNumberSuffix(clu.getOfficialIdentifier()
                .getSuffixCode());
        course.setLevel(clu.getOfficialIdentifier().getLevel());
        course.setOutOfClassHours(clu.getIntensity());
        course.setInstructors(clu.getInstructors());
        course.setStartTerm(clu.getExpectedFirstAtp());
        course.setEndTerm(clu.getLastAtp());
        course.setCourseTitle(clu.getOfficialIdentifier().getLongName());

        // CrossListings
        List<CourseCrossListingInfo> crossListings = assembleCrossListings(clu.getAlternateIdentifiers());
        course.setCrossListings(crossListings);

        //Variation
        List<CourseVariationInfo> variations = assembleVariations(clu.getAlternateIdentifiers());
        course.setVariations(variations);

//        course.setDepartment(clu.getPrimaryAdminOrg().getOrgId());
        if (course.getUnitsDeployment() == null) {
            course.setUnitsDeployment(new ArrayList<String>());
        }
        if (course.getUnitsContentOwner() == null) {
            course.setUnitsContentOwner(new ArrayList<String>());
        }
        List<String> courseAdminOrgs = new ArrayList<String>();
        List<String> courseSubjectOrgs = new ArrayList<String>();
        for(AdminOrgInfo adminOrg: clu.getAdminOrgs()){
            if (adminOrg.getTypeKey().equals(CourseAssemblerConstants.ADMIN_ORG)) {
                courseAdminOrgs.add(adminOrg.getOrgId());
            }
            if (adminOrg.getTypeKey().equals(CourseAssemblerConstants.SUBJECT_ORG)) {
                courseSubjectOrgs.add(adminOrg.getOrgId());
            }
        }
        course.setUnitsDeployment(courseAdminOrgs);
        course.setUnitsContentOwner(courseSubjectOrgs);
        course.setDescr(clu.getDescr());
        course.setDuration(clu.getStdDuration());
        course.setEffectiveDate(clu.getEffectiveDate());
        course.setExpirationDate(clu.getExpirationDate());

        //Fees
        //Fee justification
        List<CourseFeeInfo> fees = new ArrayList<CourseFeeInfo>();
        List<CourseRevenueInfo> revenues = new ArrayList<CourseRevenueInfo>();
        if(clu.getFeeInfo() != null){
            course.setFeeJustification(clu.getFeeInfo().getDescr());

            //Fees and revenues come from the same place but revenues have a special feeType
            for(CluFeeRecordInfo cluFeeRecord: clu.getFeeInfo().getCluFeeRecords()){
                String feeType = cluFeeRecord.getFeeType();
                if(CourseAssemblerConstants.COURSE_FINANCIALS_REVENUE_TYPE.equals(feeType)){
                    CourseRevenueInfo courseRevenue = new CourseRevenueInfo();
                    courseRevenue.setFeeType(feeType);
                    courseRevenue.setAffiliatedOrgs(cluFeeRecord.getAffiliatedOrgs());
                    courseRevenue.setAttributes(cluFeeRecord.getAttributes());
                    courseRevenue.setId(cluFeeRecord.getId());
                    courseRevenue.setMeta(cluFeeRecord.getMeta());
                    revenues.add(courseRevenue);
                }else{
                    CourseFeeInfo courseFee = new CourseFeeInfo();
                    courseFee.setFeeType(feeType);
                    courseFee.setRateType(cluFeeRecord.getRateType());
                    courseFee.setDescr(cluFeeRecord.getDescr());
                    courseFee.setMeta(cluFeeRecord.getMeta());
                    courseFee.setId(cluFeeRecord.getId());
                    courseFee.setFeeAmounts(cluFeeRecord.getFeeAmounts());
                    courseFee.setAttributes(cluFeeRecord.getAttributes());
                    fees.add(courseFee);
                }
            }
        }
        course.setFees(fees);
        course.setRevenues(revenues);
        //Expenditures are mapped from accounting info
        if(course.getExpenditure() == null || clu.getAccountingInfo() == null){
            course.setExpenditure(new CourseExpenditureInfo());
        }
        if(clu.getAccountingInfo() != null){
            course.getExpenditure().setAffiliatedOrgs(clu.getAccountingInfo().getAffiliatedOrgs());
        }

        course.setId(clu.getId());
        course.setTypeKey(clu.getTypeKey());
        course.setTermsOffered(clu.getOfferedAtpTypes());
        course.setPrimaryInstructor(clu.getPrimaryInstructor());
        course.setInstructors(clu.getInstructors());
        course.setStateKey(clu.getStateKey());
        course.setSubjectArea(clu.getOfficialIdentifier().getDivision());
        course.setTranscriptTitle(clu.getOfficialIdentifier().getShortName());
        course.setMeta(clu.getMeta());
        course.setVersion(clu.getVersion());


        //Special topics code
        course.setSpecialTopicsCourse(false);
        for(LuCodeInfo luCode : clu.getLuCodes()){
            if(CourseAssemblerConstants.COURSE_CODE_SPECIAL_TOPICS.equals(luCode.getType())){
                course.setSpecialTopicsCourse(Boolean.parseBoolean(luCode.getValue()));
                break;
            }
        }
        //Pilot Course code
        course.setPilotCourse(false);
        for(LuCodeInfo luCode : clu.getLuCodes()){
            if(CourseAssemblerConstants.COURSE_CODE_PILOT_COURSE.equals(luCode.getType())){
                course.setPilotCourse(Boolean.parseBoolean(luCode.getValue()));
                break;
            }
        }

        // Don't make any changes to nested datastructures if this is
        if (!shallowBuild) {
            try {
                // Use the cluService to find Joints, then convert and add to the
                // course
                List<CluCluRelationInfo> cluClus = cluService.getCluCluRelationsByClu(clu.getId(),contextInfo);

                for (CluCluRelationInfo cluRel : cluClus) {
                    if (cluRel.getTypeKey().equals(CourseAssemblerConstants.JOINT_RELATION_TYPE)) {
                        CourseJointInfo jointInfo = null;
                        if(cluRel.getCluId().equals(clu.getId())){
                            jointInfo = courseJointAssembler.assemble(cluRel, cluRel.getRelatedCluId(), null, false, contextInfo);
                        }else{
                            jointInfo = courseJointAssembler.assemble(cluRel, cluRel.getCluId(), null, false, contextInfo);
                        }
                        if (jointInfo != null){
                            course.getJoints().add(jointInfo);
                        }
                    }
                }
            } catch (DoesNotExistException e) {
            } catch (Exception e) {
                throw new AssemblyException("Error getting course joints", e);
            }

            try {
                // Use the cluService to find formats, then convert and add to
                // the course
                List<CluInfo> formats = cluService.getRelatedClusByCluAndRelationType(course
                        .getId(),
                        CourseAssemblerConstants.COURSE_FORMAT_RELATION_TYPE , new ContextInfo());

                for (CluInfo format : formats) {
                    FormatInfo formatInfo = formatAssembler.assemble(format,
                            null, false,contextInfo);
                    course.getFormats().add(formatInfo);
                }

            } catch (DoesNotExistException e) {
            } catch (Exception e) {
                throw new AssemblyException("Error getting related formats", e);
            }

            try{
                //Set Credit and Grading options
                List<CluResultInfo> cluResults = cluService.getCluResultByClu(course.getId(),contextInfo);

                List<ResultValuesGroupInfo> creditOptions = assembleCreditOptions(cluResults, contextInfo);

                course.setCreditOptions(creditOptions);

                List<String> gradingOptions = assembleGradingOptions(cluResults);

                course.setGradingOptions(gradingOptions);
            } catch (DoesNotExistException e){
            } catch (Exception e) {
                throw new AssemblyException("Error getting course results", e);
            }

            //Learning Objectives
            //course.getCourseSpecificLOs().addAll(cluAssemblerUtils.assembleLos(course.getId(), shallowBuild, contextInfo));

        }

        //Remove special cases for grading options
        boolean isAudit = course.getGradingOptions().remove(CourseAssemblerConstants.COURSE_RESULT_COMP_GRADE_AUDIT);
        //course.setAttributeValue(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT, Boolean.toString(isAudit));
        return course;
    }

    @Override
    public BaseDTOAssemblyNode<CourseInfo, CluInfo> disassemble(
            CourseInfo course, NodeOperation operation,ContextInfo contextInfo)
            throws AssemblyException, PermissionDeniedException {

        if (course == null) {
            // FIXME Unsure now if this is an exception or just return null or
            // empty assemblyNode
            LOG.error("Course to disassemble is null!");
            throw new AssemblyException("Course can not be null");
        }

        BaseDTOAssemblyNode<CourseInfo, CluInfo> result = new BaseDTOAssemblyNode<CourseInfo, CluInfo>(
                this);

        CluInfo clu;
        try {
            clu = (NodeOperation.UPDATE == operation) ? cluService.getClu(course.getId(),contextInfo) : new CluInfo();
        } catch (Exception e) {
            throw new AssemblyException("Error getting existing learning unit during course update", e);
        }

        // Create the id if it's not there already(important for creating
        // relations)
        clu.setId(UUIDHelper.genStringUUID(course.getId()));
        if (null == course.getId()) {
            course.setId(clu.getId());
        }
        clu.setTypeKey(CourseAssemblerConstants.COURSE_TYPE);
        clu.setStateKey(course.getStateKey());
        clu.setIsEnrollable(false);

        CluIdentifierInfo identifier = new CluIdentifierInfo();
        identifier.setTypeKey(CourseAssemblerConstants.COURSE_OFFICIAL_IDENT_TYPE);
        identifier.setStateKey(course.getStateKey());
        identifier.setLongName(course.getCourseTitle());
        identifier.setShortName(course.getTranscriptTitle());
        identifier.setSuffixCode(course.getCourseNumberSuffix());
        identifier.setDivision(course.getSubjectArea());
        identifier.setCode(course.getCode());

        //Custom logic to set the level, if level not provided
        if(StringUtils.hasText(course.getLevel())) {
            identifier.setLevel(course.getLevel());
        } else if(course.getCourseNumberSuffix()!=null&&course.getCourseNumberSuffix().length()>=3){
            identifier.setLevel(course.getCourseNumberSuffix().substring(0, 1)+"00");
        }

        clu.setOfficialIdentifier(identifier);

        clu.setAdminOrgs(new ArrayList<AdminOrgInfo>());

        // Use the Course Variation assembler to disassemble the variations

        // copy all fields
        //Remove any existing variations or crosslistings
        for(Iterator<CluIdentifierInfo> iter = clu.getAlternateIdentifiers().iterator();iter.hasNext();){
            CluIdentifierInfo cluIdentifier = iter.next();
            if(CourseAssemblerConstants.COURSE_VARIATION_IDENT_TYPE.equals(cluIdentifier.getTypeKey()) ||
                    CourseAssemblerConstants.COURSE_CROSSLISTING_IDENT_TYPE.equals(cluIdentifier.getTypeKey()) ){
                iter.remove();
            }
        }
        //Add in variations
        for(CourseVariationInfo variation:course.getVariations()){
            CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
            cluIdentifier.setId(variation.getId());
            cluIdentifier.setTypeKey(CourseAssemblerConstants.COURSE_VARIATION_IDENT_TYPE);
            cluIdentifier.setCode(identifier.getCode());
            cluIdentifier.setSuffixCode(course.getCourseNumberSuffix());
            cluIdentifier.setDivision(course.getSubjectArea());
            cluIdentifier.setVariation(variation.getVariationCode());
            cluIdentifier.setLongName(variation.getVariationTitle());
            cluIdentifier.setStateKey(course.getStateKey());
            clu.getAlternateIdentifiers().add(cluIdentifier);
        }
        //Add in crosslistings
        for(CourseCrossListingInfo crossListing:course.getCrossListings()){
            CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
            cluIdentifier.setId(crossListing.getId());
            cluIdentifier.setTypeKey(CourseAssemblerConstants.COURSE_CROSSLISTING_IDENT_TYPE);
            cluIdentifier.setSuffixCode(crossListing.getCourseNumberSuffix());
            cluIdentifier.setDivision(crossListing.getSubjectArea());
            cluIdentifier.setStateKey(course.getStateKey());
            cluIdentifier.setOrgId(crossListing.getDepartment());
            cluIdentifier.setAttributes(crossListing.getAttributes());
            cluIdentifier.setCode(crossListing.getCode());
            clu.getAlternateIdentifiers().add(cluIdentifier);
        }

        List<AdminOrgInfo> adminOrgInfos = new ArrayList<AdminOrgInfo>();
        for(String org:course.getUnitsDeployment()){
            AdminOrgInfo adminOrg = new AdminOrgInfo();
            adminOrg.setTypeKey(CourseAssemblerConstants.ADMIN_ORG);
            adminOrg.setOrgId(org);
            adminOrgInfos.add(adminOrg);
        }
        clu.getAdminOrgs().addAll(adminOrgInfos);

        List<AdminOrgInfo> subjectOrgs = new ArrayList<AdminOrgInfo>();
        for (String subOrg : course.getUnitsContentOwner()) {
            AdminOrgInfo subjectOrg = new AdminOrgInfo();
            subjectOrg.setTypeKey(CourseAssemblerConstants.SUBJECT_ORG);
            subjectOrg.setOrgId(subOrg);
            subjectOrgs.add(subjectOrg);
        }
        clu.getAdminOrgs().addAll(subjectOrgs);


        clu.setAttributes(course.getAttributes());
        clu.setCampusLocations(course.getCampusLocations());
        clu.setDescr(course.getDescr());
        clu.setStdDuration(course.getDuration());

        //Default course effective dates to the atps if entered
        if(course.getStartTerm() != null){
            try {
                AtpInfo startAtp = atpService.getAtp(course.getStartTerm(), contextInfo);
                course.setEffectiveDate(startAtp.getStartDate());
            } catch (Exception e) {
                throw new AssemblyException("Error getting start term Atp.",e);
            }
        }
        if(course.getEndTerm() != null){
            try {
                AtpInfo endAtp = atpService.getAtp(course.getEndTerm(), contextInfo);
                course.setExpirationDate(endAtp.getEndDate());
            } catch (Exception e) {
                throw new AssemblyException("Error getting end term Atp.",e);
            }
        }

        clu.setEffectiveDate(course.getEffectiveDate());
        clu.setExpirationDate(course.getExpirationDate());

        clu.setOfferedAtpTypes(course.getTermsOffered());
        clu.setPrimaryInstructor(course.getPrimaryInstructor());

        clu.setIntensity(course.getOutOfClassHours());
        clu.setInstructors(course.getInstructors());

        clu.setExpectedFirstAtp(course.getStartTerm());
        clu.setLastAtp(course.getEndTerm());

        clu.setMeta(course.getMeta());
        clu.setVersion(course.getVersion());

        // Add the Clu to the result
        result.setNodeData(clu);
        result.setOperation(operation);
        result.setBusinessDTORef(course);

        // Use the Format assembler to disassemble the formats and relations
        List<BaseDTOAssemblyNode<?, ?>> formatResults;
        try {
            formatResults = disassembleFormats(clu.getId(), course, operation,contextInfo);
            result.getChildNodes().addAll(formatResults);

        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling format", e);
        }

        // Use the CourseJoint assembler to disassemble the CourseJoints and
        // relations
        List<BaseDTOAssemblyNode<?, ?>> courseJointResults = disassembleJoints(clu.getId(), course, operation,contextInfo);
        result.getChildNodes().addAll(courseJointResults);

        //Disassemble the CluResults (grading and credit options)
        //Special code to take audit from attributes and put into options
        for (AttributeInfo attr : course.getAttributes()) {
            if (attr.getKey() != null){
                if (attr.getKey().equals(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT)
                        && "true".equals(attr.getValue()) ){
                    if(!course.getGradingOptions().contains(CourseAssemblerConstants.COURSE_RESULT_COMP_GRADE_AUDIT)){
                        course.getGradingOptions().add(CourseAssemblerConstants.COURSE_RESULT_COMP_GRADE_AUDIT);
                    }
                    break;
                }
            }
        }

        List<CluResultInfo> cluResultList;
        try {
            cluResultList = cluService.getCluResultByClu(clu.getId(),contextInfo);
        } catch (DoesNotExistException e) {
            cluResultList = Collections.emptyList();
        } catch (Exception e) {
            throw new AssemblyException("Error getting cluResults", e);
        }

        List<BaseDTOAssemblyNode<?, ?>> creditOutcomes = disassembleCreditOutcomes(course, clu, cluResultList, operation, contextInfo);
        result.getChildNodes().addAll(creditOutcomes);
        BaseDTOAssemblyNode<?, ?> gradingOptions = disassembleGradingOptions(clu.getId(), course.getStateKey(), course.getGradingOptions(), cluResultList, operation);
        result.getChildNodes().add(gradingOptions);

        //Use the LoAssembler to disassemble Los
        try {
            List<BaseDTOAssemblyNode<?, ?>> loResults;
            loResults = disassembleLos(clu.getId(), course, operation,contextInfo);
            result.getChildNodes().addAll(loResults);
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling los", e);
        }

        //add the special topics code if it did not exist, or remove if it was not wanted
        boolean alreadyHadSpecialTopicsCode = false;
        for(Iterator<LuCodeInfo> luCodeIterator = clu.getLuCodes().iterator();luCodeIterator.hasNext();){
            LuCodeInfo luCode = luCodeIterator.next();
            if(CourseAssemblerConstants.COURSE_CODE_SPECIAL_TOPICS.equals(luCode.getType())){
                alreadyHadSpecialTopicsCode = true;
                if(!course.isSpecialTopicsCourse()){
                    luCodeIterator.remove();
                }
                break;
            }
        }
        if(!alreadyHadSpecialTopicsCode && course.isSpecialTopicsCourse()){
            LuCodeInfo luCode = new LuCodeInfo();
            luCode.setType(CourseAssemblerConstants.COURSE_CODE_SPECIAL_TOPICS);
            luCode.setValue("true");
            clu.getLuCodes().add(luCode);
        }

        //add the special topics code if it did not exist, or remove if it was not wanted
        boolean alreadyHadPilotCourseCode = false;
        for(Iterator<LuCodeInfo> luCodeIterator = clu.getLuCodes().iterator();luCodeIterator.hasNext();){
            LuCodeInfo luCode = luCodeIterator.next();
            if(CourseAssemblerConstants.COURSE_CODE_PILOT_COURSE.equals(luCode.getType())){
                alreadyHadPilotCourseCode = true;
                if(!course.isPilotCourse()){
                    luCodeIterator.remove();
                }
                break;
            }
        }
        if(!alreadyHadPilotCourseCode && course.isPilotCourse()){
            LuCodeInfo luCode = new LuCodeInfo();
            luCode.setType(CourseAssemblerConstants.COURSE_CODE_PILOT_COURSE);
            luCode.setValue("true");
            clu.getLuCodes().add(luCode);
        }

        //FEES
        if(clu.getFeeInfo() == null){
            clu.setFeeInfo(new CluFeeInfo());
        }
        clu.getFeeInfo().setDescr(course.getFeeJustification());
        clu.getFeeInfo().getCluFeeRecords().clear();
        for(CourseRevenueInfo courseRevenue:course.getRevenues()){
            CluFeeRecordInfo cluFeeRecord  = new CluFeeRecordInfo();
            cluFeeRecord.setFeeType(CourseAssemblerConstants.COURSE_FINANCIALS_REVENUE_TYPE);
            cluFeeRecord.setRateType(CourseAssemblerConstants.COURSE_FINANCIALS_REVENUE_TYPE);

            cluFeeRecord.setAttributes(courseRevenue.getAttributes());
            cluFeeRecord.setAffiliatedOrgs(courseRevenue.getAffiliatedOrgs());
            cluFeeRecord.setId(courseRevenue.getId());
            cluFeeRecord.setMeta(courseRevenue.getMeta());
            clu.getFeeInfo().getCluFeeRecords().add(cluFeeRecord);
        }
        for(CourseFeeInfo courseFee : course.getFees()){
            CluFeeRecordInfo cluFeeRecord  = new CluFeeRecordInfo();
            cluFeeRecord.setFeeType(courseFee.getFeeType());
            cluFeeRecord.setRateType(courseFee.getRateType());
            cluFeeRecord.setDescr(courseFee.getDescr());
            cluFeeRecord.setMeta(courseFee.getMeta());
            cluFeeRecord.setId(courseFee.getId());
            cluFeeRecord.setFeeAmounts(courseFee.getFeeAmounts());

            cluFeeRecord.setAttributes(courseFee.getAttributes());
            clu.getFeeInfo().getCluFeeRecords().add(cluFeeRecord);
        }
        if(clu.getAccountingInfo() == null || course.getExpenditure()== null){
            clu.setAccountingInfo( new CluAccountingInfo());
        }
        if(course.getExpenditure() != null){
            clu.getAccountingInfo().setAffiliatedOrgs(course.getExpenditure().getAffiliatedOrgs());

            clu.getAccountingInfo().setAttributes(course.getExpenditure().getAttributes());
        }

        return result;
    }

    /**
     * Only checks for floats with format dddd.dddd where d's are optional (digits).
     * The decimal point is also optional.
     * Negative floats not checked.  Also, floats with other formats not checked.
     * @param str
     * @return true if it's a simple float (could be an integer)
     */
    private boolean _checkIfSimpleFloat(String str) {
        String DIGITS = "0123456789";
        String DIGITS_AND_DOT = DIGITS + ".";
        boolean foundDot = false;
        int numDigits = 0;
        for (int i = 0; i < str.length(); i++) {
            String ch = str.substring(i, i + 1);
            if (DIGITS_AND_DOT.indexOf(ch) < 0) {
                return false; //
            }
            if (".".equals(ch)) {
                if (!foundDot) {
                    foundDot = true;
                } else {
                    // Found a second dot--not valid
                    return false;
                }
            }
            if (DIGITS.indexOf(ch) >= 0) {
                numDigits++;
            }
        }
        // Just checks if digits are correct.
        return numDigits > 0;
    }

    /**
     * If resultValueKeys contains IDs, then look up its values and create a list of values
     * @param resultValueKeys Potential list of IDs
     * @return List of values
     */
    private List<String> _computeResultValues(List<String> resultValueKeys, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, DoesNotExistException, PermissionDeniedException, OperationFailedException {
        List<String> values = new ArrayList<String>();
        if(resultValueKeys!=null && !resultValueKeys.isEmpty()){
            String firstKey = resultValueKeys.get(0);


            if (_checkIfSimpleFloat(firstKey)) {
                // No modification needed
                return resultValueKeys;
            }
            // Assume that resultValueKeys contains IDs
            for (String key: resultValueKeys) {
                ResultValueInfo rv = lrcService.getResultValue(key, contextInfo);
                String value = rv.getValue();
                values.add(value);
            }
        }
        return values;
    }

    private List<BaseDTOAssemblyNode<?, ?>> disassembleCreditOutcomes(CourseInfo course, CluInfo clu, List<CluResultInfo> currentCluResults, NodeOperation operation, ContextInfo contextInfo) throws AssemblyException, NumberFormatException {

        List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

        String courseResultType = CourseAssemblerConstants.COURSE_RESULT_TYPE_CREDITS;

        //See if we need to create any new lrcs
        if(NodeOperation.DELETE!=operation){
            //Find all the existing LRCs for the following three types
            Set<String> resultValueGroupIds = new HashSet<String>();

            try{
                try {
                    resultValueGroupIds.addAll(lrcService.getResultValuesGroupKeysByType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED, contextInfo));
                } catch (DoesNotExistException e) {}
                try {
                    resultValueGroupIds.addAll(lrcService.getResultValuesGroupKeysByType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE, contextInfo));
                } catch (DoesNotExistException e) {}
                try {
                    resultValueGroupIds.addAll(lrcService.getResultValuesGroupKeysByType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE, contextInfo));
                } catch (DoesNotExistException e) {}

                //Create any LRCs that do not yet exist
                for(ResultValuesGroupInfo creditOption:course.getCreditOptions()){

                    String id = null;
                    String type = null;
                    List<String> resultValues = null;
                    ResultValueRangeInfo resultValueRange = null;
                    //Depending on the type, set the id, type and result values differently
                    if(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED.equals(creditOption.getTypeKey())){
                        float fixedCreditValue = Float.parseFloat(creditOption.getResultValueRange().getMinValue());
                        id = CourseAssemblerConstants.COURSE_RESULT_COMP_CREDIT_PREFIX + fixedCreditValue;
                        type = CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED;
                        resultValues = new ArrayList<String>();
                        resultValues.add(String.valueOf(fixedCreditValue));
                        resultValueRange = new ResultValueRangeInfo();
                        resultValueRange.setMinValue(String.valueOf(fixedCreditValue));
                        resultValueRange.setMaxValue(String.valueOf(fixedCreditValue));
                    } else if (CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE.equals(creditOption.getTypeKey())){
                        List<String> resultVals = _computeResultValues(creditOption.getResultValueKeys(), contextInfo);
                        Collections.sort(resultVals, new Comparator<String>() {
                            public int compare(String o1, String o2) {
                            return Float.compare(Float.parseFloat(o1),Float.parseFloat(o2));
                            }
                        });

                        StringBuilder sb = new StringBuilder(CourseAssemblerConstants.COURSE_RESULT_COMP_CREDIT_PREFIX);
                        for (Iterator<String> iter = resultVals.iterator();
                             iter.hasNext();){
                            String str = iter.next();
                            sb.append(str);
                            if(iter.hasNext()){
                                sb.append(",");
                            }
                        }
                        id = sb.toString();
                        type = CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE;
                        resultValues = new ArrayList<String>();
                        resultValues.addAll(resultVals);
                    }else if(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE.equals(creditOption.getTypeKey())){
                        /*
                               * For variable credits create a Result values that goes from min to max with the specified increment.
                               * If no increment is specified, use 1.0 as the increment. The increment can be specified as a float.
                               */

                        float minCredits = Float.parseFloat(creditOption.getResultValueRange().getMinValue());
                        float maxCredits = Float.parseFloat(creditOption.getResultValueRange().getMaxValue());
                        String creditValueIncr = creditOption.getResultValueRange().getIncrement();
                        float increment = (null != creditValueIncr && creditValueIncr.length() > 0 ) ? Float.parseFloat(creditValueIncr) : defaultCreditIncrement ;

                        id = CourseAssemblerConstants.COURSE_RESULT_COMP_CREDIT_PREFIX + String.valueOf(minCredits) + "-" + String.valueOf(maxCredits);
                        //Add in the increment to the key (logic is duplicated in LRC service)
                        if (creditValueIncr!=null && !"1".equals(creditValueIncr) && !"1.0".equals(creditValueIncr)) {
                            id += (" by " + creditValueIncr);
                        }

                        type = CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE;
                        resultValues = new ArrayList<String>();
                        for(float i = minCredits; i <= maxCredits; i+=increment){
                            resultValues.add(String.valueOf(i));
                        }
                        resultValueRange = new ResultValueRangeInfo();
                        resultValueRange.setMinValue(String.valueOf(minCredits));
                        resultValueRange.setMaxValue(String.valueOf(maxCredits));
                        resultValueRange.setIncrement(String.valueOf(increment));
                    }else{
                        resultValues = Collections.emptyList();
                    }

                    //Set the id
                    creditOption.setKey(id);

                    //Ensure the resultValueKey has the proper prefix
                    // TODO: Comment: Shouldn't muck around with altering IDs
                    // Assumption is result values contains IDs, not string values like 2.0

//                    String resultValueKeyPrefix = "kuali.result.value.credit.degree.";
//                    for(int i = 0; i < resultValues.size(); i++){
//                        if (!resultValues.get(i).contains("kuali.result.value")){ //only add the prefix if this is not a proper key
//                            String ithResultVal = resultValues.get(i);
//                            resultValues.set(i,resultValueKeyPrefix+Float.parseFloat(ithResultVal));
//                        }
//                    }

                    //Create a new result component
                    if(id != null && !resultValueGroupIds.contains(id)){

                        //Build the new ResultValuesGroup
                        ResultValuesGroupInfo resultValueGroup = new ResultValuesGroupInfo();
                        resultValueGroup.setKey(id);
                        resultValueGroup.setTypeKey(type);
                        if (DtoConstants.STATE_DRAFT.equals(course.getStateKey())){
                            resultValueGroup.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_DRAFT);
                        } else if (DtoConstants.STATE_APPROVED.equals(course.getStateKey())) {
                            resultValueGroup.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_APPROVED);
                        } else {
                            resultValueGroup.setStateKey(LrcServiceConstants.RESULT_GROUPS_STATE_RETIRED);
                        }
                        resultValueGroup.setResultScaleKey(LrcServiceConstants.RESULT_SCALE_KEY_CREDIT_DEGREE);
                        resultValueGroup.setResultValueKeys(resultValues);
                        resultValueGroup.setResultValueRange(resultValueRange);
                        BaseDTOAssemblyNode<ResultValuesGroupInfo, ResultValuesGroupInfo> node = new BaseDTOAssemblyNode<ResultValuesGroupInfo, ResultValuesGroupInfo>(null);
                        node.setOperation(NodeOperation.CREATE);
                        node.setNodeData(resultValueGroup);
                        node.setBusinessDTORef(creditOption);
                        results.add(node);

                        resultValueGroupIds.add(id);
                    }
                }
            }catch (NumberFormatException e){
                throw new AssemblyException("Invalid Arguments for credit outcome values",e);
            }catch (Exception e){
                throw new AssemblyException("Error Assembling", e);
            }
        }

        //Now do dissassembly for the actual clu-lrc relations and result options

        // Get the current options and put them in a map of option type id/cluResult
        Map<String, List<CluResultInfo>> currentResults = new HashMap<String, List<CluResultInfo>>();

        //If this is not a create, lookup the results for this clu
        if (!NodeOperation.CREATE.equals(operation)) {
            for (CluResultInfo currentResult : currentCluResults) {
                if (courseResultType.equals(currentResult.getTypeKey())) {
                    //There should only be one grading option per CluResult for credit outcomes
                    if(currentResult.getResultOptions().size()==1){
                        //Create a mapping to a list of cluresults with the same result componentId
                        String resultComponentId = currentResult.getResultOptions().get(0).getResultComponentId();
                        if(!currentResults.containsKey(resultComponentId)){
                            currentResults.put(resultComponentId, new ArrayList<CluResultInfo>());
                        }
                        currentResults.get(resultComponentId).add(currentResult);
                    }else{
                        LOG.warn("Credit Results should have exactly one result option each");
                    }
                }
            }
        }

        //Loop through options on the course, if they are new, create a new cluResult
        for(ResultValuesGroupInfo creditOption : course.getCreditOptions()){
            if (NodeOperation.CREATE == operation
                    || (NodeOperation.UPDATE == operation && !currentResults.containsKey(creditOption.getKey()) )) {

                ResultOptionInfo resultOption = new ResultOptionInfo();
                resultOption.setStateKey(course.getStateKey());
                resultOption.setResultComponentId(creditOption.getKey());

                CluResultInfo cluResult = new CluResultInfo();
                cluResult.setCluId(clu.getId());
                cluResult.setStateKey(course.getStateKey());
                cluResult.setTypeKey(courseResultType);

                cluResult.getResultOptions().add(resultOption);

                BaseDTOAssemblyNode<ResultValuesGroupInfo, CluResultInfo> cluResultNode = new BaseDTOAssemblyNode<ResultValuesGroupInfo, CluResultInfo>(null);
                cluResultNode.setNodeData(cluResult);
                cluResultNode.setOperation(NodeOperation.CREATE);

                results.add(cluResultNode);
            } else if (NodeOperation.UPDATE == operation
                    && currentResults.containsKey(creditOption.getKey())) {
                //Get the list from the map and remove an entry, if the list is empty then remove it from the map
                List<CluResultInfo> cluResults = currentResults.get(creditOption.getKey());
                cluResults.remove(cluResults.size()-1);
                if(cluResults.isEmpty()){
                    currentResults.remove(creditOption.getKey());
                }
            }
        }

        //Delete the leftovers
        for(Entry<String,List<CluResultInfo>> entry:currentResults.entrySet()){
            for(CluResultInfo cluResult:entry.getValue()){
                BaseDTOAssemblyNode<ResultValuesGroupInfo, CluResultInfo> cluResultNode = new BaseDTOAssemblyNode<ResultValuesGroupInfo, CluResultInfo>(null);
                cluResultNode.setNodeData(cluResult);
                cluResultNode.setOperation(NodeOperation.DELETE);
                results.add(cluResultNode);
            }
        }

        return results;
    }

    private List<String> assembleGradingOptions(List<CluResultInfo> cluResults){

        String courseResultType = CourseAssemblerConstants.COURSE_RESULT_TYPE_GRADE;

        List<String> results = new ArrayList<String>();
        //Loop through all the CluResults to find the one with the matching type
        for(CluResultInfo cluResult:cluResults){
            if(courseResultType.equals(cluResult.getTypeKey())){
                //Loop through all options and add to the list of Strings
                for(ResultOptionInfo resultOption: cluResult.getResultOptions()){
                    results.add(resultOption.getResultComponentId());
                }
                break;
            }
        }
        return results;
    }
    //
    private List<ResultValuesGroupInfo> assembleCreditOptions(
            List<CluResultInfo> cluResults, ContextInfo contextInfo) throws AssemblyException {
        String courseResultType = CourseAssemblerConstants.COURSE_RESULT_TYPE_CREDITS;
        List<ResultValuesGroupInfo> results = new ArrayList<ResultValuesGroupInfo>();
        //Loop through all the CluResults to find the one with the matching type
        for(CluResultInfo cluResult:cluResults){
            if(courseResultType.equals(cluResult.getTypeKey())){
                //Loop through all options and add to the list of Strings
                for(ResultOptionInfo resultOption: cluResult.getResultOptions()){
                    try {
                        if(resultOption.getResultComponentId()!=null){
                            ResultValuesGroupInfo resultValuesGroup = lrcService.getResultValuesGroup(resultOption.getResultComponentId(), contextInfo);
                            results.add(resultValuesGroup);
                        }
                    } catch (DoesNotExistException e) {
                        LOG.warn("Course Credit option:"+resultOption.getId()+" refers to non-existant ResultValuesGroupInfo "+ resultOption.getResultComponentId());
                    } catch (Exception e) {
                        throw new AssemblyException("Error getting ResultValuesGroupInfo",e);
                    }
                }
            }
        }
        return results;
    }

    // TODO Use CluAssemblerUtils
    private List<BaseDTOAssemblyNode<?, ?>> disassembleLos(String cluId,  CourseInfo course, NodeOperation operation,ContextInfo contextInfo) throws AssemblyException {
        // TODO Auto-generated method stub
        List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

        // Get the current formats and put them in a map of format id/relation
        // id
        Map<String, CluLoRelationInfo> currentCluLoRelations = new HashMap<String, CluLoRelationInfo>();
        try {
            List<CluLoRelationInfo> cluLoRelations = cluService.getCluLoRelationsByClu(cluId,contextInfo);
            for(CluLoRelationInfo cluLoRelation:cluLoRelations){
                if(CourseAssemblerConstants.COURSE_LO_COURSE_SPECIFIC_RELATION.equals(cluLoRelation.getTypeKey())){
                    currentCluLoRelations.put(cluLoRelation.getLoId(), cluLoRelation);
                }
            }
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error finding related Los");
        }

        // Loop through all the los in this clu
        for(LoDisplayInfo loDisplay : course.getCourseSpecificLOs()){

            // If this is a clu create/new lo update then all los will be created
            if (NodeOperation.CREATE == operation
                    || (NodeOperation.UPDATE == operation &&  !currentCluLoRelations.containsKey(loDisplay.getLoInfo().getId()))) {

                // the lo does not exist, so create
                // Assemble and add the lo
                loDisplay.getLoInfo().setId(null);
                loDisplay.getLoInfo().setStateKey(course.getStateKey());
                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
                        .disassemble(loDisplay, NodeOperation.CREATE,contextInfo);
                results.add(loNode);

                // Create the relationship and add it as well
                CluLoRelationInfo relation = new CluLoRelationInfo();
                relation.setCluId(cluId);
                relation.setLoId(loNode.getNodeData().getId());
                relation
                        .setTypeKey(CourseAssemblerConstants.COURSE_LO_COURSE_SPECIFIC_RELATION);
                relation.setStateKey(course.getStateKey());

                BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo> relationNode = new BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo>(
                        null);
                relationNode.setNodeData(relation);
                relationNode.setOperation(NodeOperation.CREATE);

                results.add(relationNode);
            } else if (NodeOperation.UPDATE == operation
                    && currentCluLoRelations.containsKey(loDisplay.getLoInfo().getId())) {
                loDisplay.getLoInfo().setStateKey(course.getStateKey());
                // If the clu already has this lo, then just update the lo
                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
                        .disassemble(loDisplay, NodeOperation.UPDATE,contextInfo);
                results.add(loNode);

                // remove this entry from the map so we can tell what needs to
                // be deleted at the end
                currentCluLoRelations.remove(loDisplay.getLoInfo().getId());
            } else if (NodeOperation.DELETE == operation
                    && currentCluLoRelations.containsKey(loDisplay.getLoInfo().getId())) {

                // Delete the Format and its relation
                CluLoRelationInfo relationToDelete = currentCluLoRelations.get(loDisplay.getLoInfo().getId());
                BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo>(
                        null);
                relationToDeleteNode.setNodeData(relationToDelete);
                relationToDeleteNode.setOperation(NodeOperation.DELETE);
                results.add(relationToDeleteNode);

                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
                        .disassemble(loDisplay, NodeOperation.DELETE,contextInfo);
                results.add(loNode);

                // remove this entry from the map so we can tell what needs to
                // be deleted at the end
                currentCluLoRelations.remove(loDisplay.getLoInfo().getId());
            }
        }

        // Now any leftover lo ids are no longer needed, so delete
        // los and relations
        for (Entry<String, CluLoRelationInfo> entry : currentCluLoRelations.entrySet()) {
            // Create a new relation with the id of the relation we want to
            // delete
            CluLoRelationInfo relationToDelete = entry.getValue();
            BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo>(
                    null);
            relationToDeleteNode.setNodeData(relationToDelete);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);

            try{
                LoInfo loToDelete = loService.getLo(entry.getKey(),contextInfo);

                LoDisplayInfo loDisplayToDelete = loAssembler.assemble(loToDelete, null, false,contextInfo);
                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
                        .disassemble(loDisplayToDelete, NodeOperation.DELETE,contextInfo);
                results.add(loNode);
            } catch (DoesNotExistException e){
                LOG.warn("Trying to delete non exsistant LO:"+entry.getKey());
            } catch (Exception e) {
                throw new AssemblyException("Error disassembling LOs",e);
            }
        }

        return results;
    }

    private BaseDTOAssemblyNode<?, ?> disassembleGradingOptions(String cluId,
                                                                String courseState, List<String> options, List<CluResultInfo> currentCluResults, NodeOperation operation) throws AssemblyException {
        BaseDTOAssemblyNode<List<String>, CluResultInfo> cluResultNode = new BaseDTOAssemblyNode<List<String>, CluResultInfo>(null);

        String courseResultType=CourseAssemblerConstants.COURSE_RESULT_TYPE_GRADE;
        String resultsDescription="Grading options";
        String resultDescription="Grading option";

        // Get the current options and put them in a map of option type id/cluResult
        Map<String, ResultOptionInfo> currentResults = new HashMap<String, ResultOptionInfo>();

        CluResultInfo cluResult = null;

        //If this is not a create, lookup the results for this clu
        if (!NodeOperation.CREATE.equals(operation)) {
            for (CluResultInfo currentResult : currentCluResults) {
                if (courseResultType.equals(currentResult.getTypeKey())) {
                    cluResult = currentResult;
                    if(NodeOperation.DELETE.equals(operation)){
                        //if this is a delete, then we only need the CluResultInfo
                        cluResultNode.setOperation(NodeOperation.DELETE);
                    }else{
                        //Find all the Result options and store in a map for easy access later
                        cluResultNode.setOperation(NodeOperation.UPDATE);
                        for(ResultOptionInfo resultOption:currentResult.getResultOptions()){
                            currentResults.put(resultOption.getResultComponentId(), resultOption);
                        }
                    }
                    break;
                }
            }
        }

        //If this is a delete we don't need the result options, just the CluResultInfo
        if(!NodeOperation.DELETE.equals(operation)){
            if(cluResult == null){
                //Create a new resultInfo of the given type if one does not exist and set operation to Create
                cluResult = new CluResultInfo();
                cluResult.setCluId(cluId);
                cluResult.setStateKey(courseState);
                cluResult.setTypeKey(courseResultType);
                RichTextInfo desc = new RichTextInfo();
                desc.setPlain(resultsDescription);
                cluResult.setDescr(desc);
                cluResult.setEffectiveDate(new Date());
                cluResultNode.setOperation(NodeOperation.CREATE);
            }

            cluResult.setResultOptions(new ArrayList<ResultOptionInfo>());

            // Loop through all the credit options in this course
            for (String optionType : options) {
                if(currentResults.containsKey(optionType)){
                    //If the option exists already copy it to the new list of result options
                    ResultOptionInfo resultOptionInfo = currentResults.get(optionType);
                    cluResult.getResultOptions().add(resultOptionInfo);
                }else{
                    //Otherwise create a new result option
                    ResultOptionInfo resultOptionInfo = new ResultOptionInfo();
                    RichTextInfo desc = new RichTextInfo();
                    desc.setPlain(resultDescription);
                    resultOptionInfo.setDescr(desc);
                    resultOptionInfo.setResultComponentId(optionType);
                    resultOptionInfo.setStateKey(courseState);

                    cluResult.getResultOptions().add(resultOptionInfo);
                }
            }
        }

        cluResultNode.setNodeData(cluResult);
        return cluResultNode;
    }

    // TODO This is pretty much a copy of the FormatAssembler's
    // disassembleActivities code... maybe can be made generic
    private List<BaseDTOAssemblyNode<?, ?>> disassembleFormats(String nodeId,
                                                               CourseInfo course, NodeOperation operation,ContextInfo contextInfo)
            throws AssemblyException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {

        List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

        // Get the current formats and put them in a map of format id/relation
        // id
        Map<String, String> currentformatIds = new HashMap<String, String>();

        if (!NodeOperation.CREATE.equals(operation)) {
            try {
                List<CluCluRelationInfo> formatRelationships = cluService
                        .getCluCluRelationsByClu(course.getId(),contextInfo);

                formatRelationships = (null == formatRelationships) ? new ArrayList<CluCluRelationInfo>() : formatRelationships;

                for (CluCluRelationInfo formatRelation : formatRelationships) {
                    if (CourseAssemblerConstants.COURSE_FORMAT_RELATION_TYPE
                            .equals(formatRelation.getTypeKey())) {
                        currentformatIds.put(formatRelation.getRelatedCluId(),
                                formatRelation.getId());
                    }
                }
            } catch (DoesNotExistException e) {
            } catch (InvalidParameterException e) {
                throw new AssemblyException("Error getting related formats", e);
            } catch (MissingParameterException e) {
                throw new AssemblyException("Error getting related formats", e);
            } catch (OperationFailedException e) {
                throw new AssemblyException("Error getting related formats", e);
            }
        }

        // Loop through all the formats in this course
        for (FormatInfo format : course.getFormats()) {

            //  If this is a course create/new format update then all formats will be created
            if (NodeOperation.CREATE == operation
                    || (NodeOperation.UPDATE == operation && !currentformatIds.containsKey(format.getId()) )) {
                // the format does not exist, so create
                // Assemble and add the format
                format.setStateKey(course.getStateKey());
                BaseDTOAssemblyNode<FormatInfo, CluInfo> formatNode = formatAssembler
                        .disassemble(format, NodeOperation.CREATE,contextInfo);
                results.add(formatNode);


                // Create the relationship and add it as well
                CluCluRelationInfo relation = new CluCluRelationInfo();
                relation.setCluId(nodeId);
                relation.setRelatedCluId(formatNode.getNodeData().getId());// this
                // should
                // already
                // be set even if
                // it's a create
                relation
                        .setTypeKey(CourseAssemblerConstants.COURSE_FORMAT_RELATION_TYPE);
                relation.setStateKey(course.getStateKey());

                BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo>(
                        null);
                relationNode.setNodeData(relation);
                relationNode.setOperation(NodeOperation.CREATE);

                results.add(relationNode);
            } else if (NodeOperation.UPDATE == operation
                    && currentformatIds.containsKey(format.getId())) {
                // If the course already has this format, then just update the
                // format
                format.setStateKey(course.getStateKey());
                BaseDTOAssemblyNode<FormatInfo, CluInfo> formatNode = formatAssembler
                        .disassemble(format, NodeOperation.UPDATE,contextInfo);
                results.add(formatNode);

                // remove this entry from the map so we can tell what needs to
                // be deleted at the end
                currentformatIds.remove(format.getId());
            } else if (NodeOperation.DELETE == operation
                    && currentformatIds.containsKey(format.getId()))  {
                // Delete the Format and its relation
                CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
                relationToDelete.setId( currentformatIds.get(format.getId()) );
                BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo>(
                        null);
                relationToDeleteNode.setNodeData(relationToDelete);
                relationToDeleteNode.setOperation(NodeOperation.DELETE);
                results.add(relationToDeleteNode);

                BaseDTOAssemblyNode<FormatInfo, CluInfo> formatNode = formatAssembler
                        .disassemble(format, NodeOperation.DELETE,contextInfo);
                results.add(formatNode);

                // remove this entry from the map so we can tell what needs to
                // be deleted at the end
                currentformatIds.remove(format.getId());
            }
        }

        // Now any leftover format ids are no longer needed, so delete
        // formats and relations. These formats have to be assembled first before they can be marked for deletion
        for (Entry<String, String> entry : currentformatIds.entrySet()) {
            // Create a new relation with the id of the relation we want to
            // delete
            CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
            relationToDelete.setId( entry.getValue() );
            BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo>(
                    null);
            relationToDeleteNode.setNodeData(relationToDelete);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);

            CluInfo formatCluToDelete = cluService.getClu(entry.getKey() , new ContextInfo());
            FormatInfo formatToDelete = formatAssembler.assemble(formatCluToDelete, null, false,contextInfo);
            BaseDTOAssemblyNode<FormatInfo, CluInfo> formatNode = formatAssembler
                    .disassemble(formatToDelete, NodeOperation.DELETE,contextInfo);
            results.add(formatNode);
        }

        return results;
    }

    private List<CourseVariationInfo> assembleVariations(List<CluIdentifierInfo> cluIdents) {
        List<CourseVariationInfo> variations = new ArrayList<CourseVariationInfo>();
        if (cluIdents != null) {
            for (CluIdentifierInfo cluIdent : cluIdents) {
                if (cluIdent.getTypeKey() != null &&
                        cluIdent.getTypeKey().equals(CourseAssemblerConstants.COURSE_VARIATION_IDENT_TYPE)) {
                    CourseVariationInfo variation = new CourseVariationInfo();
                    variation.setId(cluIdent.getId());
                    variation.setTypeKey(cluIdent.getTypeKey());
                    variation.setCourseNumberSuffix(cluIdent.getSuffixCode());
                    variation.setSubjectArea(cluIdent.getDivision());
                    variation.setVariationCode(cluIdent.getVariation());
                    variation.setVariationTitle(cluIdent.getLongName());
                    variations.add(variation);
                }
            }
        }
        return variations;
    }

    private List<CourseCrossListingInfo> assembleCrossListings(List<CluIdentifierInfo> cluIdents)
            throws AssemblyException {
        List<CourseCrossListingInfo> crossListings = new ArrayList<CourseCrossListingInfo>();
        if (cluIdents != null) {
            for (CluIdentifierInfo cluIdent : cluIdents) {
                if (cluIdent.getTypeKey() != null &&
                        cluIdent.getTypeKey().equals(CourseAssemblerConstants.COURSE_CROSSLISTING_IDENT_TYPE)) {
                    CourseCrossListingInfo crosslisting = new CourseCrossListingInfo();

                    boolean found = false;
                    for (AttributeInfo attr : cluIdent.getAttributes()){
                        if (attr.getKey().equals("courseId")){
                            found = true;
                            try {
                                CluInfo cluInfo = cluService.getClu(attr.getValue(), new ContextInfo());
                                crosslisting.setId(cluIdent.getId());
                                crosslisting.setCode(cluInfo.getOfficialIdentifier().getCode());
                                crosslisting.setAttributes(cluIdent.getAttributes());
                                crosslisting.setTypeKey(cluInfo.getTypeKey());
                                crosslisting.setCourseNumberSuffix(cluInfo.getOfficialIdentifier().getSuffixCode());
                                crosslisting.setSubjectArea(cluInfo.getOfficialIdentifier().getDivision());
                                crosslisting.setDepartment(cluIdent.getOrgId());
                            } catch (Exception e) {
                                throw new AssemblyException("Error getting related clus", e);
                            }
                        }
                    }
                    if (!found) {
                        crosslisting.setId(cluIdent.getId());
                        crosslisting.setCode(cluIdent.getCode());
                        crosslisting.setAttributes(cluIdent.getAttributes());
                        crosslisting.setTypeKey(cluIdent.getTypeKey());
                        crosslisting.setCourseNumberSuffix(cluIdent.getSuffixCode());
                        crosslisting.setSubjectArea(cluIdent.getDivision());
                        crosslisting.setDepartment(cluIdent.getOrgId());
                    }

                    crossListings.add(crosslisting);
                }
            }
        }
        return crossListings;
    }

    // TODO This is pretty much a copy of the disassembleJoints
    // code... maybe can be made generic
    private List<BaseDTOAssemblyNode<?, ?>> disassembleJoints(String nodeId,
                                                              CourseInfo course, NodeOperation operation,ContextInfo contextInfo)
            throws AssemblyException, PermissionDeniedException {

        List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

        // Get the current joints and put them in a map of joint id/relation
        // id
        Map<String, CluCluRelationInfo> currentJointIds = new HashMap<String, CluCluRelationInfo>();

        if (!NodeOperation.CREATE.equals(operation)) {
            try {
                List<CluCluRelationInfo> jointRelationships = cluService.getCluCluRelationsByClu(course.getId(),contextInfo);
                for (CluCluRelationInfo jointRelation : jointRelationships) {
                    if (CourseAssemblerConstants.JOINT_RELATION_TYPE.equals(jointRelation.getTypeKey())) {
                        if(jointRelation.getCluId().equals(course.getId())) {
                            cluService.getClu(jointRelation.getRelatedCluId(), contextInfo);
                            currentJointIds.put(jointRelation.getId(),jointRelation);
                        } else {
                            cluService.getClu(jointRelation.getCluId(), contextInfo);
                            currentJointIds.put(jointRelation.getId(),jointRelation);
                        }
                    }
                }
            } catch (DoesNotExistException e) {
            } catch (InvalidParameterException e) {
                throw new AssemblyException("Error getting related formats", e);
            } catch (MissingParameterException e) {
                throw new AssemblyException("Error getting related formats", e);
            } catch (OperationFailedException e) {
                throw new AssemblyException("Error getting related formats", e);
            }
        }

        // Loop through all the joints in this course
        for (CourseJointInfo joint : course.getJoints()) {
            // If this is a course create then all joints will be created
            if (NodeOperation.UPDATE.equals(operation) && joint.getRelationId() != null
                    && currentJointIds.containsKey(joint.getRelationId())) {
                // remove this entry from the map so we can tell what needs to
                // be deleted at the end
                CluCluRelationInfo relation = currentJointIds.remove(joint.getRelationId());
                relation.setRelatedCluId(joint.getCourseId());
                relation.setStateKey(course.getStateKey());
                BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo> jointNode = new BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo>(courseJointAssembler);
                jointNode.setBusinessDTORef(joint);
                jointNode.setNodeData(relation);
                jointNode.setOperation(NodeOperation.UPDATE);
                results.add(jointNode);
            } else if (!NodeOperation.DELETE.equals(operation)) {
                // the joint does not exist, so create cluclurelation
                BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo> jointNode = courseJointAssembler
                        .disassemble(joint, NodeOperation.CREATE,contextInfo);
                jointNode.getNodeData().setCluId(nodeId);
                jointNode.getNodeData().setStateKey(course.getStateKey());
                results.add(jointNode);
            }
        }

        // Now any leftover joint ids are no longer needed, so delete
        // joint relations
        for (String id : currentJointIds.keySet()) {
            // Create a new relation with the id of the relation we want to
            // delete
            CluCluRelationInfo relationToDelete = new CluCluRelationInfo();
            relationToDelete.setId(id);
            BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo> relationToDeleteNode = new BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo>(
                    courseJointAssembler);
            relationToDeleteNode.setNodeData(relationToDelete);
            relationToDeleteNode.setOperation(NodeOperation.DELETE);
            results.add(relationToDeleteNode);
        }

        return results;
    }

    public void setCluService(CluService cluService) {
        this.cluService = cluService;
    }

    public void setFormatAssembler(FormatAssembler formatAssembler) {
        this.formatAssembler = formatAssembler;
    }

    public void setCourseJointAssembler(
            CourseJointAssembler courseJointAssembler) {
        this.courseJointAssembler = courseJointAssembler;
    }

    public void setLoAssembler(LoAssembler loAssembler) {
        this.loAssembler = loAssembler;
    }

    public void setLoService(LearningObjectiveService loService) {
        this.loService = loService;
    }

    public void setCluAssemblerUtils(CluAssemblerUtils cluAssemblerUtils) {
        this.cluAssemblerUtils = cluAssemblerUtils;
    }

    public void setLrcService(LRCService lrcService) {
        this.lrcService = lrcService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }

    public void setDefaultCreditIncrement(float defaultCreditIncrement) {
        this.defaultCreditIncrement = defaultCreditIncrement;
    }
}
