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
package org.kuali.student.lum.course.service.assembler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.log4j.Logger;
import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.core.assembly.BOAssembler;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode;
import org.kuali.student.core.assembly.BaseDTOAssemblyNode.NodeOperation;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.lum.course.dto.CourseCrossListingInfo;
import org.kuali.student.lum.course.dto.CourseExpenditureInfo;
import org.kuali.student.lum.course.dto.CourseFeeInfo;
import org.kuali.student.lum.course.dto.CourseInfo;
import org.kuali.student.lum.course.dto.CourseJointInfo;
import org.kuali.student.lum.course.dto.CourseRevenueInfo;
import org.kuali.student.lum.course.dto.CourseVariationInfo;
import org.kuali.student.lum.course.dto.FormatInfo;
import org.kuali.student.lum.course.dto.LoDisplayInfo;
import org.kuali.student.lum.lo.dto.LoInfo;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lrc.dto.ResultComponentInfo;
import org.kuali.student.lum.lrc.service.LrcService;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluAccountingInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluFeeRecordInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluLoRelationInfo;
import org.kuali.student.lum.lu.dto.CluResultInfo;
import org.kuali.student.lum.lu.dto.LuCodeInfo;
import org.kuali.student.lum.lu.dto.ResultOptionInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.service.assembler.CluAssemblerUtils;
import org.springframework.util.StringUtils;
/**
 * Assembler for CourseInfo. Provides assemble and disassemble operation on
 * CourseInfo from/to CluInfo and other base DTOs
 * 
 * @author Kuali Student Team
 * 
 */
public class CourseAssembler implements BOAssembler<CourseInfo, CluInfo> {

    final static Logger LOG = Logger.getLogger(CourseAssembler.class);
	private LuService luService;
	private FormatAssembler formatAssembler;
	private CourseJointAssembler courseJointAssembler;
	private LoAssembler loAssembler;
	private LearningObjectiveService loService;
    private CluAssemblerUtils cluAssemblerUtils;
    private LrcService lrcService;
	
	@Override
	public CourseInfo assemble(CluInfo clu, CourseInfo courseInfo,
			boolean shallowBuild) throws AssemblyException {

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
		
//		course.setDepartment(clu.getPrimaryAdminOrg().getOrgId());
		if(course.getUnitsDeployment()==null){
			course.setUnitsDeployment(new ArrayList<String>());
		}
		if(course.getUnitsContentOwner()==null){
			course.setUnitsContentOwner(new ArrayList<String>());
		}
		List<String> courseAdminOrgs = new ArrayList<String>();
		List<String> courseSubjectOrgs = new ArrayList<String>();
		for(AdminOrgInfo adminOrg: clu.getAdminOrgs()){
			if(adminOrg.getType().equals(CourseAssemblerConstants.ADMIN_ORG)){
				courseAdminOrgs.add(adminOrg.getOrgId());
			}
			if(adminOrg.getType().equals(CourseAssemblerConstants.SUBJECT_ORG)){
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
					courseRevenue.setMetaInfo(cluFeeRecord.getMetaInfo());
					revenues.add(courseRevenue);
				}else{
					CourseFeeInfo courseFee = new CourseFeeInfo();
					courseFee.setFeeType(feeType);
					courseFee.setRateType(cluFeeRecord.getRateType());
					courseFee.setDescr(cluFeeRecord.getDescr());
					courseFee.setMetaInfo(cluFeeRecord.getMetaInfo());
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
		course.setType(clu.getType());
		course.setTermsOffered(clu.getOfferedAtpTypes());
		course.setPrimaryInstructor(clu.getPrimaryInstructor());
		course.setInstructors(clu.getInstructors());
		course.setState(clu.getState());
		course.setSubjectArea(clu.getOfficialIdentifier().getDivision());
		course.setTranscriptTitle(clu.getOfficialIdentifier().getShortName());
		course.setMetaInfo(clu.getMetaInfo());
		course.setVersionInfo(clu.getVersionInfo());

		
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
				// Use the luService to find Joints, then convert and add to the
				// course
				List<CluCluRelationInfo> cluClus = luService
						.getCluCluRelationsByClu(clu.getId());
				
				for (CluCluRelationInfo cluRel : cluClus) {
					if (cluRel.getType().equals(
							CourseAssemblerConstants.JOINT_RELATION_TYPE)) {
						CourseJointInfo jointInfo = courseJointAssembler
								.assemble(cluRel, null, false);
						course.getJoints().add(jointInfo);
					}
				}
			} catch (DoesNotExistException e) {
			} catch (Exception e) {
				throw new AssemblyException("Error getting course joints", e);
			}

			try {
				// Use the luService to find formats, then convert and add to
				// the course
				List<CluInfo> formats = luService.getRelatedClusByCluId(course
						.getId(),
						CourseAssemblerConstants.COURSE_FORMAT_RELATION_TYPE);
				
				for (CluInfo format : formats) {
					FormatInfo formatInfo = formatAssembler.assemble(format,
							null, false);
					course.getFormats().add(formatInfo);
				}

			} catch (DoesNotExistException e) {
			} catch (Exception e) {
				throw new AssemblyException("Error getting related formats", e);
			}

			try{
				//Set Credit and Grading options
				List<CluResultInfo> cluResults = luService.getCluResultByClu(course.getId());

				List<ResultComponentInfo> creditOptions = assembleCreditOptions(cluResults);
				course.setCreditOptions(creditOptions);
				
				List<String> gradingOptions = assembleGradingOptions(cluResults);
				
				course.setGradingOptions(gradingOptions);
			} catch (DoesNotExistException e){
			} catch (Exception e) {
				throw new AssemblyException("Error getting course results", e);
			}
			
			//Learning Objectives
            course.getCourseSpecificLOs().addAll(cluAssemblerUtils.assembleLos(course.getId(), shallowBuild));
			
		}

		//Remove special cases for grading options
		course.getGradingOptions().remove(CourseAssemblerConstants.COURSE_RESULT_COMP_GRADE_AUDIT);
		
		return course;
	}

	@Override
	public BaseDTOAssemblyNode<CourseInfo, CluInfo> disassemble(
			CourseInfo course, NodeOperation operation)
			throws AssemblyException {

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
			clu = (NodeOperation.UPDATE == operation) ? luService.getClu(course.getId()) : new CluInfo();
        } catch (Exception e) {
			throw new AssemblyException("Error getting existing learning unit during course update", e);
        } 

		// Create the id if it's not there already(important for creating
		// relations)
		clu.setId(UUIDHelper.genStringUUID(course.getId()));
		if (null == course.getId()) {
			course.setId(clu.getId());
		}
		clu.setType(CourseAssemblerConstants.COURSE_TYPE);
		clu.setState(course.getState());

		CluIdentifierInfo identifier = new CluIdentifierInfo();
		identifier.setType(CourseAssemblerConstants.COURSE_OFFICIAL_IDENT_TYPE);
		identifier.setState(course.getState());
		identifier.setLongName(course.getCourseTitle());
		identifier.setShortName(course.getTranscriptTitle());
		identifier.setSuffixCode(course.getCourseNumberSuffix());
		identifier.setDivision(course.getSubjectArea());

		//Custom logic to set the code as the concatenation of division and course number suffix if code not provided
		if (StringUtils.hasText(course.getCode())){
			identifier.setCode(course.getCode());
		} else if(StringUtils.hasText(course.getCourseNumberSuffix()) && StringUtils.hasText(course.getSubjectArea())){
			identifier.setCode(calculateCourseCode(course.getSubjectArea(),course.getCourseNumberSuffix()));			
		}else{
			identifier.setCode(null);
		}
		
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
			if(CourseAssemblerConstants.COURSE_VARIATION_IDENT_TYPE.equals(cluIdentifier.getType()) ||
				CourseAssemblerConstants.COURSE_CROSSLISTING_IDENT_TYPE.equals(cluIdentifier.getType()) ){
				iter.remove();
			}
		}
		//Add in variations
		for(CourseVariationInfo variation:course.getVariations()){
			CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
			cluIdentifier.setId(variation.getId());
			cluIdentifier.setType(CourseAssemblerConstants.COURSE_VARIATION_IDENT_TYPE);
			cluIdentifier.setCode(identifier.getCode());
			cluIdentifier.setSuffixCode(course.getCourseNumberSuffix());
			cluIdentifier.setDivision(course.getSubjectArea());
			cluIdentifier.setVariation(variation.getVariationCode());
			cluIdentifier.setLongName(variation.getVariationTitle());
			cluIdentifier.setState(course.getState());
			clu.getAlternateIdentifiers().add(cluIdentifier);
		}
		//Add in ings
		for(CourseCrossListingInfo crossListing:course.getCrossListings()){
			CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
			cluIdentifier.setId(crossListing.getId());
			cluIdentifier.setType(CourseAssemblerConstants.COURSE_CROSSLISTING_IDENT_TYPE);
			cluIdentifier.setSuffixCode(crossListing.getCourseNumberSuffix());
			cluIdentifier.setDivision(crossListing.getSubjectArea());
			cluIdentifier.setState(course.getState());
			cluIdentifier.setOrgId(crossListing.getDepartment());
			cluIdentifier.setAttributes(crossListing.getAttributes());

	        //Custom logic to set the code as the concatenation of division and course number suffix if code not provided
	        if (StringUtils.hasText(crossListing.getCode())){
	            cluIdentifier.setCode(crossListing.getCode());
	        } else if(StringUtils.hasText(crossListing.getCourseNumberSuffix()) && StringUtils.hasText(crossListing.getSubjectArea())){
	            cluIdentifier.setCode(calculateCourseCode(crossListing.getSubjectArea(), crossListing.getCourseNumberSuffix()));         
	        }else{
	            cluIdentifier.setCode(null);
	        }
	        			
			clu.getAlternateIdentifiers().add(cluIdentifier);
		}

		List<AdminOrgInfo> adminOrgInfos = new ArrayList<AdminOrgInfo>();
		for(String org:course.getUnitsDeployment()){
			AdminOrgInfo adminOrg = new AdminOrgInfo();
			adminOrg.setType(CourseAssemblerConstants.ADMIN_ORG);
			adminOrg.setOrgId(org);
			adminOrgInfos.add(adminOrg);
		}
		clu.getAdminOrgs().addAll(adminOrgInfos);
		
		List<AdminOrgInfo> subjectOrgs = new ArrayList<AdminOrgInfo>();
		for (String subOrg : course.getUnitsContentOwner()) {
			AdminOrgInfo subjectOrg = new AdminOrgInfo();
			subjectOrg.setType(CourseAssemblerConstants.SUBJECT_ORG);
			subjectOrg.setOrgId(subOrg);
			subjectOrgs.add(subjectOrg);
		}
		clu.getAdminOrgs().addAll(subjectOrgs);

		
		clu.setAttributes(course.getAttributes());
		clu.setCampusLocations(course.getCampusLocations());
		clu.setDescr(course.getDescr());
		clu.setStdDuration(course.getDuration());
		clu.setEffectiveDate(course.getEffectiveDate());
		clu.setExpirationDate(course.getExpirationDate());

		clu.setOfferedAtpTypes(course.getTermsOffered());
		clu.setPrimaryInstructor(course.getPrimaryInstructor());
		
		clu.setIntensity(course.getOutOfClassHours());
		clu.setInstructors(course.getInstructors());
		
		clu.setExpectedFirstAtp(course.getStartTerm());
		clu.setLastAtp(course.getEndTerm());
		
		clu.setMetaInfo(course.getMetaInfo());
		clu.setVersionInfo(course.getVersionInfo());

		// Add the Clu to the result
		result.setNodeData(clu);
		result.setOperation(operation);
		result.setBusinessDTORef(course);

		// Use the Format assembler to disassemble the formats and relations
		List<BaseDTOAssemblyNode<?, ?>> formatResults;
        try {
            formatResults = disassembleFormats(clu
            		.getId(), course, operation);
            result.getChildNodes().addAll(formatResults);
            
        } catch (DoesNotExistException e) {
        } catch (Exception e) {
            throw new AssemblyException("Error while disassembling format", e);
        }

		// Use the CourseJoint assembler to disassemble the CourseJoints and
		// relations
		List<BaseDTOAssemblyNode<?, ?>> courseJointResults = disassembleJoints(
				clu.getId(), course, operation);
		result.getChildNodes().addAll(courseJointResults);

		//Disassemble the CluResults (grading and credit options)
		//Special code to take audit from attributes and put into options
		if(course.getAttributes().containsKey(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT)&&"true".equals(course.getAttributes().get(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_AUDIT))){
			if(!course.getGradingOptions().contains(CourseAssemblerConstants.COURSE_RESULT_COMP_GRADE_AUDIT)){
				course.getGradingOptions().add(CourseAssemblerConstants.COURSE_RESULT_COMP_GRADE_AUDIT);
			}
		}
		
		List<CluResultInfo> cluResultList;
		try {
			cluResultList = luService.getCluResultByClu(clu.getId());
		} catch (DoesNotExistException e) {
			cluResultList = Collections.emptyList();
		} catch (Exception e) {
			throw new AssemblyException("Error getting cluResults", e);
		}
		
		List<BaseDTOAssemblyNode<?, ?>> creditOutcomes = disassembleCreditOutcomes(course, clu, cluResultList, operation);
		result.getChildNodes().addAll(creditOutcomes);
		
		BaseDTOAssemblyNode<?, ?> gradingOptions = disassembleGradingOptions(
				clu.getId(), course.getState(), course.getGradingOptions(), cluResultList, operation);
		result.getChildNodes().add(gradingOptions);
		
		//Use the LoAssembler to disassemble Los
        try {
    		List<BaseDTOAssemblyNode<?, ?>> loResults;
    		loResults = disassembleLos(clu.getId(), course, operation);
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
			cluFeeRecord.setMetaInfo(courseRevenue.getMetaInfo());
			clu.getFeeInfo().getCluFeeRecords().add(cluFeeRecord);
		}
		for(CourseFeeInfo courseFee : course.getFees()){
			CluFeeRecordInfo cluFeeRecord  = new CluFeeRecordInfo();
			cluFeeRecord.setFeeType(courseFee.getFeeType());
			cluFeeRecord.setRateType(courseFee.getRateType());
			cluFeeRecord.setDescr(courseFee.getDescr());
			cluFeeRecord.setMetaInfo(courseFee.getMetaInfo());
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

	private List<BaseDTOAssemblyNode<?, ?>> disassembleCreditOutcomes(CourseInfo course, CluInfo clu, List<CluResultInfo> currentCluResults, NodeOperation operation) throws AssemblyException, NumberFormatException {
		
		List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();
		
		String courseResultType = CourseAssemblerConstants.COURSE_RESULT_TYPE_CREDITS;
		
		//See if we need to create any new lrcs
		if(NodeOperation.DELETE!=operation){
			//Find all the existing LRCs for the following three types
			Set<String> rsltComps = new HashSet<String>();
			
			try{
				try {
					rsltComps.addAll(lrcService.getResultComponentIdsByResultComponentType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED));
				} catch (DoesNotExistException e) {}
				try {
					rsltComps.addAll(lrcService.getResultComponentIdsByResultComponentType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE));
				} catch (DoesNotExistException e) {}
				try {
					rsltComps.addAll(lrcService.getResultComponentIdsByResultComponentType(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE));
				} catch (DoesNotExistException e) {}

				//Create any LRCs that do not yet exist
				for(ResultComponentInfo creditOption:course.getCreditOptions()){
					String id = null;
					String type = null;
					List<String> resultValues = null;
					Map<String,String> attributes = null;
					//Depending on the type, set the id, type and result values differently
					if(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED.equals(creditOption.getType())){
						float fixedCreditValue = Float.parseFloat(creditOption.getAttributes().get(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_FIXED_CREDIT_VALUE));
						id = CourseAssemblerConstants.COURSE_RESULT_COMP_CREDIT_PREFIX + fixedCreditValue;
						type = CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_FIXED;
						resultValues = new ArrayList<String>();
						resultValues.add(String.valueOf(fixedCreditValue));
						attributes = new HashMap<String,String>();
						attributes.put(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_FIXED_CREDIT_VALUE, String.valueOf(fixedCreditValue));
					}else if(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE.equals(creditOption.getType())){
						Collections.sort(creditOption.getResultValues());
						StringBuilder sb = new StringBuilder(CourseAssemblerConstants.COURSE_RESULT_COMP_CREDIT_PREFIX);
						for(Iterator<String> iter = creditOption.getResultValues().iterator();iter.hasNext();){
							sb.append(iter.next());
							if(iter.hasNext()){
								sb.append(",");
							}
						}
						id = sb.toString();
						type = CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_MULTIPLE;
						resultValues = creditOption.getResultValues();
					}else if(CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE.equals(creditOption.getType())){
					    /*
					     * For variable credits create a Result values that goes from min to max with the specified increment. 
					     * If no increment is specified, use 1.0 as the increment. The increment can be specified as a float.
					     */
					  					   
					    String minCreditValue = creditOption.getAttributes().get(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MIN_CREDIT_VALUE);
						String maxCreditValue = creditOption.getAttributes().get(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MAX_CREDIT_VALUE);
						String creditValueIncr = creditOption.getAttributes().get(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_CREDIT_VALUE_INCR);
						float minCredits = Float.parseFloat(minCreditValue);
						float maxCredits = Float.parseFloat(maxCreditValue);
												
						float increment = (null != creditValueIncr && creditValueIncr.length() > 0 ) ? Float.parseFloat(creditValueIncr) : 1.0f ;
												
						id = CourseAssemblerConstants.COURSE_RESULT_COMP_CREDIT_PREFIX + minCreditValue + "-" + maxCreditValue;
						type = CourseAssemblerConstants.COURSE_RESULT_COMP_TYPE_CREDIT_VARIABLE;
						resultValues = new ArrayList<String>();
						for(float i = minCredits; i <= maxCredits; i+=increment){
							resultValues.add(String.valueOf(i));
						}
						attributes = new HashMap<String,String>();
						attributes.put(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MIN_CREDIT_VALUE, minCreditValue);
						attributes.put(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_MAX_CREDIT_VALUE, maxCreditValue);
                        attributes.put(CourseAssemblerConstants.COURSE_RESULT_COMP_ATTR_CREDIT_VALUE_INCR, creditValueIncr);
					}
	
					//Set the id
					creditOption.setId(id);
					
					//Create a new result component
					if(id != null && !rsltComps.contains(id)){
												
						//need to make a fixed degree result type component
						ResultComponentInfo resultComponent = new ResultComponentInfo();
						resultComponent.setId(id);
						resultComponent.setType(type);
						resultComponent.setState ("Active");
						resultComponent.setResultValues(resultValues);
						resultComponent.setAttributes(attributes);
						BaseDTOAssemblyNode<ResultComponentInfo, ResultComponentInfo> node = new BaseDTOAssemblyNode<ResultComponentInfo, ResultComponentInfo>(null);
						node.setOperation(NodeOperation.CREATE);
						node.setNodeData(resultComponent);
						node.setBusinessDTORef(creditOption);
						results.add(node);
						
						rsltComps.add(id);
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
				if (courseResultType.equals(currentResult.getType())) {
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
		for(ResultComponentInfo creditOption : course.getCreditOptions()){
		    if (NodeOperation.CREATE == operation
		            || (NodeOperation.UPDATE == operation && !currentResults.containsKey(creditOption.getId()) )) {
		    	
		    	ResultOptionInfo resultOption = new ResultOptionInfo();
		    	resultOption.setState(course.getState());
		    	resultOption.setResultComponentId(creditOption.getId());
		    	
		    	CluResultInfo cluResult = new CluResultInfo();
				cluResult.setCluId(clu.getId());
				cluResult.setState(course.getState());
				cluResult.setType(courseResultType);
				
				cluResult.getResultOptions().add(resultOption);
				
				BaseDTOAssemblyNode<ResultComponentInfo, CluResultInfo> cluResultNode = new BaseDTOAssemblyNode<ResultComponentInfo, CluResultInfo>(null);
				cluResultNode.setNodeData(cluResult);
				cluResultNode.setOperation(NodeOperation.CREATE);
				
                results.add(cluResultNode);
            } else if (NodeOperation.UPDATE == operation
					&& currentResults.containsKey(creditOption.getId())) {
            	//Get the list from the map and remove an entry, if the list is empty then remove it from the map
            	List<CluResultInfo> cluResults = currentResults.get(creditOption.getId());
            	cluResults.remove(cluResults.size()-1);
            	if(cluResults.isEmpty()){
            		currentResults.remove(creditOption.getId());
            	}
			}
		}
		
		//Delete the leftovers
		for(Entry<String,List<CluResultInfo>> entry:currentResults.entrySet()){
			for(CluResultInfo cluResult:entry.getValue()){
				BaseDTOAssemblyNode<ResultComponentInfo, CluResultInfo> cluResultNode = new BaseDTOAssemblyNode<ResultComponentInfo, CluResultInfo>(null);
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
			if(courseResultType.equals(cluResult.getType())){
				//Loop through all options and add to the list of Strings
				for(ResultOptionInfo resultOption: cluResult.getResultOptions()){
					results.add(resultOption.getResultComponentId());
				}
				break;
			}
		}
		return results;
	}
	
	private List<ResultComponentInfo> assembleCreditOptions(
			List<CluResultInfo> cluResults) throws AssemblyException {
		String courseResultType = CourseAssemblerConstants.COURSE_RESULT_TYPE_CREDITS;
		List<ResultComponentInfo> results = new ArrayList<ResultComponentInfo>();
		//Loop through all the CluResults to find the one with the matching type
		for(CluResultInfo cluResult:cluResults){
			if(courseResultType.equals(cluResult.getType())){
				//Loop through all options and add to the list of Strings
				for(ResultOptionInfo resultOption: cluResult.getResultOptions()){
					try {
						ResultComponentInfo resultComponent = lrcService.getResultComponent(resultOption.getResultComponentId());
						results.add(resultComponent);
					} catch (DoesNotExistException e) {
						LOG.warn("Course Credit option:"+resultOption.getId()+" refers to non-existant ResultComponentInfo "+resultOption.getResultComponentId());
					} catch (Exception e) {
						throw new AssemblyException("Error getting result components",e);
					}
				}
			}
		}
		return results;
	}
	
	// TODO Use CluAssemblerUtils
	private List<BaseDTOAssemblyNode<?, ?>> disassembleLos(String cluId,
			CourseInfo course, NodeOperation operation) throws AssemblyException {
		// TODO Auto-generated method stub
		List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

		// Get the current formats and put them in a map of format id/relation
		// id
		Map<String, CluLoRelationInfo> currentCluLoRelations = new HashMap<String, CluLoRelationInfo>();
		try {
			List<CluLoRelationInfo> cluLoRelations = luService.getCluLoRelationsByClu(cluId);
			for(CluLoRelationInfo cluLoRelation:cluLoRelations){
				if(CourseAssemblerConstants.COURSE_LO_COURSE_SPECIFIC_RELATION.equals(cluLoRelation.getType())){
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
                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
                        .disassemble(loDisplay, NodeOperation.CREATE);
                results.add(loNode);

                // Create the relationship and add it as well
                CluLoRelationInfo relation = new CluLoRelationInfo();
                relation.setCluId(cluId);
                relation.setLoId(loNode.getNodeData().getId());
                relation
                        .setType(CourseAssemblerConstants.COURSE_LO_COURSE_SPECIFIC_RELATION);
                relation.setState(course.getState());

                BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo> relationNode = new BaseDTOAssemblyNode<LoDisplayInfo, CluLoRelationInfo>(
                        null);
                relationNode.setNodeData(relation);
                relationNode.setOperation(NodeOperation.CREATE);

                results.add(relationNode);
            } else if (NodeOperation.UPDATE == operation
					&& currentCluLoRelations.containsKey(loDisplay.getLoInfo().getId())) {
				// If the clu already has this lo, then just update the lo
                BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
                		.disassemble(loDisplay, NodeOperation.UPDATE);
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
        				.disassemble(loDisplay, NodeOperation.DELETE);
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
            	LoInfo loToDelete = loService.getLo(entry.getKey());
            
	            LoDisplayInfo loDisplayToDelete = loAssembler.assemble(loToDelete, null, false);
	            BaseDTOAssemblyNode<LoDisplayInfo, LoInfo> loNode = loAssembler
	            		.disassemble(loDisplayToDelete, NodeOperation.DELETE);
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
				if (courseResultType.equals(currentResult.getType())) {
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
				cluResult.setState(courseState);
				cluResult.setType(courseResultType);
				RichTextInfo desc = new RichTextInfo();
				desc.setPlain(resultsDescription);
				cluResult.setDesc(desc);
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
					resultOptionInfo.setDesc(desc);
					resultOptionInfo.setResultComponentId(optionType);
					resultOptionInfo.setState(courseState);
					
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
			CourseInfo course, NodeOperation operation)
			throws AssemblyException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {

		List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

		// Get the current formats and put them in a map of format id/relation
		// id
		Map<String, String> currentformatIds = new HashMap<String, String>();

		if (!NodeOperation.CREATE.equals(operation)) {
			try {
				List<CluCluRelationInfo> formatRelationships = luService
						.getCluCluRelationsByClu(course.getId());
				
				//formatRelationships = (null == formatRelationships) ? new ArrayList<CluCluRelationInfo>() : formatRelationships;
				
				for (CluCluRelationInfo formatRelation : formatRelationships) {
					if (CourseAssemblerConstants.COURSE_FORMAT_RELATION_TYPE
							.equals(formatRelation.getType())) {
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
		    	format.setState(course.getState());
                BaseDTOAssemblyNode<FormatInfo, CluInfo> formatNode = formatAssembler
                        .disassemble(format, NodeOperation.CREATE);
                formatNode.getNodeData().setState(course.getState());
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
                        .setType(CourseAssemblerConstants.COURSE_FORMAT_RELATION_TYPE);
                relation.setState(course.getState());

                BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo> relationNode = new BaseDTOAssemblyNode<CourseInfo, CluCluRelationInfo>(
                        null);
                relationNode.setNodeData(relation);
                relationNode.setOperation(NodeOperation.CREATE);

                results.add(relationNode);
            } else if (NodeOperation.UPDATE == operation
					&& currentformatIds.containsKey(format.getId())) {
				// If the course already has this format, then just update the
				// format
            	format.setState(course.getState());
				BaseDTOAssemblyNode<FormatInfo, CluInfo> formatNode = formatAssembler
						.disassemble(format, NodeOperation.UPDATE);
				formatNode.getNodeData().setState(course.getState());
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
                .disassemble(format, NodeOperation.DELETE);
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
            
            CluInfo formatCluToDelete = luService.getClu(entry.getKey());
            FormatInfo formatToDelete = formatAssembler.assemble(formatCluToDelete, null, false);
            BaseDTOAssemblyNode<FormatInfo, CluInfo> formatNode = formatAssembler
            .disassemble(formatToDelete, NodeOperation.DELETE);
            results.add(formatNode);                                            
        }

		return results;
	}
	
	private List<CourseVariationInfo> assembleVariations(List<CluIdentifierInfo> cluIdents) {
		List<CourseVariationInfo> variations = new ArrayList<CourseVariationInfo>();
		if (cluIdents != null) {
			for (CluIdentifierInfo cluIdent : cluIdents) {
				if (cluIdent.getType() != null && 
						cluIdent.getType().equals(CourseAssemblerConstants.COURSE_VARIATION_IDENT_TYPE)) {
					CourseVariationInfo variation = new CourseVariationInfo();
					variation.setId(cluIdent.getId());
					variation.setType(cluIdent.getType());
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
	
	private List<CourseCrossListingInfo> assembleCrossListings(List<CluIdentifierInfo> cluIdents) {
		List<CourseCrossListingInfo> crossListings = new ArrayList<CourseCrossListingInfo>();
		if (cluIdents != null) {
			for (CluIdentifierInfo cluIdent : cluIdents) {
				if (cluIdent.getType() != null && 
						cluIdent.getType().equals(CourseAssemblerConstants.COURSE_CROSSLISTING_IDENT_TYPE)) {
					CourseCrossListingInfo crosslisting = new CourseCrossListingInfo();
					crosslisting.setId(cluIdent.getId());
					crosslisting.setCode(cluIdent.getCode());
					crosslisting.setAttributes(cluIdent.getAttributes());
					crosslisting.setType(cluIdent.getType());
					crosslisting.setCourseNumberSuffix(cluIdent.getSuffixCode());
					crosslisting.setSubjectArea(cluIdent.getDivision());
					crosslisting.setDepartment(cluIdent.getOrgId());
					crossListings.add(crosslisting);
				}
			}
		}
		return crossListings;
	}
	
	// TODO This is pretty much a copy of the disassembleJoints
	// code... maybe can be made generic
	private List<BaseDTOAssemblyNode<?, ?>> disassembleJoints(String nodeId,
			CourseInfo course, NodeOperation operation)
			throws AssemblyException {

		List<BaseDTOAssemblyNode<?, ?>> results = new ArrayList<BaseDTOAssemblyNode<?, ?>>();

		// Get the current joints and put them in a map of joint id/relation
		// id
		Map<String, CluCluRelationInfo> currentJointIds = new HashMap<String, CluCluRelationInfo>();

		if (!NodeOperation.CREATE.equals(operation)) {
			try {
				List<CluCluRelationInfo> jointRelationships = luService
						.getCluCluRelationsByClu(course.getId());
				for (CluCluRelationInfo jointRelation : jointRelationships) {
					if (CourseAssemblerConstants.JOINT_RELATION_TYPE
							.equals(jointRelation.getType())) {
						currentJointIds.put(jointRelation.getId(),jointRelation);
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
				BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo> jointNode = new BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo>(courseJointAssembler);
				jointNode.setBusinessDTORef(joint);
				jointNode.setNodeData(relation);
				jointNode.setOperation(NodeOperation.UPDATE);
				results.add(jointNode);
			} else if (!NodeOperation.DELETE.equals(operation)) {
				// the joint does not exist, so create cluclurelation
				BaseDTOAssemblyNode<CourseJointInfo, CluCluRelationInfo> jointNode = courseJointAssembler
						.disassemble(joint, NodeOperation.CREATE);
				jointNode.getNodeData().setCluId(nodeId);
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

	/**
	 * 
	 * This method calculates code for course and cross listed course.
	 * 
	 * @param subjectArea
	 * @param suffixNumber
	 * @return
	 */
	private String calculateCourseCode(String subjectArea, String suffixNumber) {
	    return subjectArea + suffixNumber;
	}
	
	public void setLuService(LuService luService) {
		this.luService = luService;
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

	public void setLrcService(LrcService lrcService) {
		this.lrcService = lrcService;
	}
}
