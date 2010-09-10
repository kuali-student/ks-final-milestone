/**
 * Copyright 2010 The Kuali Foundation Licensed under the
 * Educational Community License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may
 * obtain a copy of the License at
 *
 * http://www.osedu.org/licenses/ECL-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS"
 * BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.kuali.student.lum.lu.assembly;

import static org.kuali.student.core.assembly.util.AssemblerUtils.addVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.getVersionIndicator;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isCreated;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isDeleted;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isModified;
import static org.kuali.student.core.assembly.util.AssemblerUtils.isUpdated;
import static org.kuali.student.core.assembly.util.AssemblerUtils.setCreated;
import static org.kuali.student.core.assembly.util.AssemblerUtils.setUpdated;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.core.statement.service.StatementService;
import org.kuali.student.core.assembly.BaseAssembler;
import org.kuali.student.core.assembly.data.AssemblyException;
import org.kuali.student.core.assembly.data.Data;
import org.kuali.student.core.assembly.data.Metadata;
import org.kuali.student.core.assembly.data.SaveResult;
import org.kuali.student.core.assembly.data.Metadata.WriteAccess;
import org.kuali.student.core.atp.dto.AtpTypeInfo;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.dto.AmountInfo;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.CircularReferenceException;
import org.kuali.student.core.exceptions.CircularRelationshipException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.organization.dto.OrgInfo;
import org.kuali.student.core.organization.service.OrganizationService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.lum.common.client.lo.RichTextInfoHelper;
import org.kuali.student.lum.lo.service.LearningObjectiveService;
import org.kuali.student.lum.lu.assembly.data.client.LuData;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.CluInstructorInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.base.TimeAmountInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityContactHoursHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityDurationHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseActivityHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseCrossListingsHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseDurationHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseExpenditureInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseFormatHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseJointsHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseProposalHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseRevenueInfoHelper;
import org.kuali.student.lum.lu.assembly.data.client.refactorme.orch.CreditCourseVersionsHelper;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy.ModificationState;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluCluRelationInfo;
import org.kuali.student.lum.lu.dto.CluFeeInfo;
import org.kuali.student.lum.lu.dto.CluFeeRecordInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.dto.CluInstructorInfo;
import org.kuali.student.lum.lu.dto.LuTypeInfo;
import org.kuali.student.lum.lu.service.LuService;
import org.kuali.student.lum.lu.ui.course.server.gwt.LuRuleInfoPersistanceBean;
import org.kuali.student.lum.ui.requirements.client.model.RuleInfo;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor={Throwable.class})
public class CourseAssembler extends BaseAssembler<Data, CluInfoHierarchy> {
	//  TODO Split out CluInfo assembly to its own class

	final Logger LOG = Logger.getLogger(CourseAssembler.class);

	public static final String JOINT_RELATION_TYPE = "kuali.lu.relation.type.co-located";
	public static final String PROPOSAL_TYPE_CREATE_COURSE = "kuali.proposal.type.course.create";
	public static final String FORMAT_LU_TYPE = "kuali.lu.type.CreditCourseFormatShell";
	public static final String COPY_OF_CLU_RELATION_TYPE = "kuali.lu.relation.type.copyOfClu";

	public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
	public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";

	public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu"; // <- what the service says, but the dictionary says: "kuali.referenceType.CLU";
	public static final String COURSE_DATA_TYPE = "course";

	private SingleUseLoInfoAssembler loAssembler;
	private final RichTextInfoAssembler richtextAssembler = new RichTextInfoAssembler();
	private final TimeAmountInfoAssembler timeamountAssembler = new TimeAmountInfoAssembler();
	private final CluInstructorInfoDataAssembler instructorAssembler = new CluInstructorInfoDataAssembler();
	private final CluFeeInfoDataAssembler feeAssembler = new CluFeeInfoDataAssembler();
	private final RevenueDataAssembler revenueAssembler = new RevenueDataAssembler();
	private final ExpenditureDataAssembler expenditureAssembler = new ExpenditureDataAssembler();
	private LearningResultAssembler lrAssembler;

	private LuService luService;
	private StatementService statementService;
	private LearningObjectiveService loService;
	private OrganizationService orgService;
	private AtpService atpService;

	@Override
	public Data get(String id) throws AssemblyException {

		LuData luData = new LuData();
		CreditCourseHelper result = CreditCourseHelper.wrap(luData);

		try {

			CluInfoHierarchy hierarchy = getCluInfoHierarchyFromId(id);

			CreditCourseHelper course = buildCourseFromCluInfo(hierarchy);

			luData.setData(course.getData());

			result.setGradingOptions(getLearningResultAssembler().getGradingOptions(id));
			result.setOutcomeOptions(getLearningResultAssembler().getOutcomeOptions(id));

			luData.setRuleInfos(getRules(id));
			
			//FIXME:  This is a hack to extract the rule info statements from LuData and put them
			//  into the cluModel for use by View Course. Once Rule Info switches to using the DOL this method 
			//  call can be removed 
			extractStatements(luData);

			// TODO - need a SingleUseLoListAssembler that calls SingleUseLoAssembler once for each LO
			// associated with the course
			result.setCourseSpecificLOs(getSingleUseLoAssembler().get(course.getId()));
		} catch (Exception e) {
			throw new AssemblyException("Could not assemble course", e);
		}

		return result.getData();
	}

	@Override
	public SaveResult<Data> save(Data input) throws AssemblyException {

		try {
			SaveResult<Data> result = new SaveResult<Data>();

			//Run orchestration layer validation, if errors just return and don't bother trying to persist 
			List<ValidationResultInfo> validationResults = validate(input);
			if (hasValidationErrors(validationResults)) {
				result.setValidationResults(validationResults);
				result.setValue(input);
				return result;
			}

			//FIXME: This is a bit clunky. Think of a better way to do this!  
			// CreditCourseProposalAssembler passes in LuData which contains proposal, LO and rules.
			// This class needs LO and rules but not proposal.
			CreditCourseHelper course = null;
			if (input instanceof LuData) {
				LuData luData = (LuData)input;
				CreditCourseProposalHelper helper = CreditCourseProposalHelper.wrap(luData.getData());
				course = CreditCourseHelper.wrap(helper.getCourse().getData());               
			} else {
				course = CreditCourseHelper.wrap(input);
			}
			
            //See if this is a create and this is a copy
            String copyOfCourseId = null;
            if(course.getId()==null&&course.getCopyOfCourseId()!=null){
            	copyOfCourseId=course.getCopyOfCourseId();
            }
            
			// first save all of the clus and relations
			SaveResult<CluInfoHierarchy> courseResult = saveHierarchy(course);
			result.addValidationResults(courseResult.getValidationResults());

			//If there were no validation errors, we should get back a course id
			String courseId = null;
			if (!hasValidationErrors(result.getValidationResults())){
				if (courseResult.getValue() != null && courseResult.getValue().getCluInfo() != null) {
					courseId = courseResult.getValue().getCluInfo().getId();
				}
				if (courseId == null) {
					throw new AssemblyException("Course ID was null after save");
				}
			}

            //if this is a copy, save a new copy relation
            if(copyOfCourseId!=null){
            	CluCluRelationInfo cluCluRelationInfo = new CluCluRelationInfo();
            	luService.createCluCluRelation(courseId, copyOfCourseId, COPY_OF_CLU_RELATION_TYPE, cluCluRelationInfo);
            }
            
            getLearningResultAssembler().saveGradingOptions(course.getData(), courseId);
            getLearningResultAssembler().saveOutcomeOptions(course.getData(), courseId);
            
			saveRules(courseId, (LuData)input);

			// save the Learning Objective(s)
			// TODO - Need a SingleUseLoListAssembler w/ standard save() method that in turn calls
			// a new single-LO save() method in SingleUseLoAssembler
			SaveResult<Data> loResult = getSingleUseLoAssembler().save(courseId, course.getCourseSpecificLOs());
			result.addValidationResults(loResult.getValidationResults());

			if (courseId != null) {
				Data d = get(courseId);     
				result.setValue(d);
			} else {
				result.setValue(null);
			}

			return result;
		} catch (Exception e) {
			throw new AssemblyException("Unable to save course", e);
		}
	}

	@Override
	public Data assemble(CluInfoHierarchy input) throws AssemblyException {
		throw new UnsupportedOperationException("Data assembly not supported");
	}

	@Override
	public CluInfoHierarchy disassemble(Data input) throws AssemblyException {
		throw new UnsupportedOperationException("Data disassembly not supported");
	}

	private CluInfoHierarchy getCluInfoHierarchyFromId(String id) throws AssemblyException {

		RelationshipHierarchy course = new RelationshipHierarchy();
		RelationshipHierarchy formats = new RelationshipHierarchy(FORMAT_RELATION_TYPE, "Active");
		RelationshipHierarchy activities = new RelationshipHierarchy(ACTIVITY_RELATION_TYPE, "Active");

		course.addChild(formats);
		formats.addChild(activities);

		CluInfoHierarchy result = null;
		try {
			CluInfo clu = luService.getClu(id);
			if (clu != null) {
				result = new CluInfoHierarchy();
				result.setCluInfo(clu);
				for (RelationshipHierarchy h : course.getChildren()) {
					buildRelatedCourse(result, h);
				}
			}
		} catch (Exception e) {
			throw new AssemblyException(e);
		}
		return result;


	}

	private CreditCourseHelper buildCourseFromCluInfo(CluInfoHierarchy hierarchy) throws AssemblyException {

		CreditCourseHelper result = CreditCourseHelper.wrap(new Data());

		CluInfo course = hierarchy.getCluInfo();

		try {

			//TODO move bulk of this logic to new CluInfoAssembler
			result.setId(course.getId());
			result.setCourseNumberSuffix(course.getOfficialIdentifier()
					.getSuffixCode());
			result.setCourseTitle(course.getOfficialIdentifier().getLongName());

			result.setEffectiveDate(course.getEffectiveDate());
			result.setExpirationDate(course.getExpirationDate());

//			AdminOrgInfo admin = course.getPrimaryAdminOrg();
//			if (admin != null) {
//				result.setDepartment(admin.getOrgId());
//			}

			if (course.getDescr() != null){
				result.setDescription(RichTextInfoHelper.wrap(richtextAssembler.assemble(course.getDescr())));
			}

			TimeAmountInfoHelper time = TimeAmountInfoHelper.wrap(timeamountAssembler.assemble(course.getStdDuration()));
			if (time != null) {
				CreditCourseDurationHelper duration = CreditCourseDurationHelper.wrap(new Data());
				if(time.getTimeQuantity() != null) {
					duration.setQuantity(Integer.valueOf(time.getTimeQuantity()));
				}
				duration.setTermType(time.getAtpDurationTypeKey());
				result.setDuration(duration);
			}

			result.setTermsOffered(new Data());
			for (String atpType : course.getOfferedAtpTypes()) {
				result.getTermsOffered().add(atpType);
			}

			result.setFirstExpectedOffering("");
			String atp = course.getExpectedFirstAtp();
			if (atp != null && !atp.isEmpty()) {
				result.setFirstExpectedOffering(atp);
			}

			CluInstructorInfoHelper instr = CluInstructorInfoHelper.wrap(instructorAssembler.assemble(course.getPrimaryInstructor()));
			if (instr != null) {
				result.setPrimaryInstructor(instr.getPersonId());
			}
			
			for (CluInstructorInfo instructor : course.getInstructors()) {
				result.getInstructors().add(instructor.getPersonId());
			}
			result.setState(course.getState());
			result.setSubjectArea(course.getOfficialIdentifier().getDivision());
			result.setTranscriptTitle(course.getOfficialIdentifier().getShortName());
			result.setType(course.getType());

//			result.setAcademicSubjectOrgs(new Data());

//			for (AcademicSubjectOrgInfo org : course.getAcademicSubjectOrgs()) {
//				result.getAcademicSubjectOrgs().add(org.getOrgId());
//			}

			result.setCampusLocations(new Data());
			for (String campus : course.getCampusLocations()) {
				result.getCampusLocations().add(campus);
			}
			
			result.setFees(feeAssembler.assemble(course.getFeeInfo()));
			
			setRevenueData(result, course);
			
			setExpenditureData(result, course);
			
			addVersionIndicator(result.getData(), CluInfo.class.getName(), course.getId(), course.getMetaInfo().getVersionInd());

			for (CluInfoHierarchy format : hierarchy.getChildren()) {
				buildFormatsFromCluInfo(result, format);

			}
			addCrossListings(result, hierarchy);
			addVersions(result, hierarchy);

			//Retrieve related clus of type kuali.lu.relation.type.co-located and add the list to the map.
			List<CluCluRelationInfo> cluClus = luService.getCluCluRelationsByClu(course.getId());
			if (cluClus != null){
				for(CluCluRelationInfo cluRel:cluClus){
					if(cluRel.getType().equals(JOINT_RELATION_TYPE)){
						CluInfo cluInfo = luService.getClu(cluRel.getRelatedCluId());
						CreditCourseJointsHelper joint = CreditCourseJointsHelper
						.wrap(new Data());
						buildJointsFromCluInfo(cluInfo,joint);
						joint.setRelationId(cluRel.getId());
						if(result.getJoints()==null){
							result.setJoints(new Data());
						}
						result.getJoints().add(joint.getData());

					}
				}
			}
			result.setId(course.getId());
			
			//FIXME: Temp hack to send course code back to View Course UI. Once fields added to DOL, fix!!
			result.getData().set ("courseCode", course.getOfficialIdentifier().getCode());
      		
			//See if this is a copy of a clu and set that;    
			List<String> copiedFromIds = luService.getRelatedCluIdsByCluId(result.getId(), COPY_OF_CLU_RELATION_TYPE);
			if(copiedFromIds!=null&&copiedFromIds.size()>0){
				result.setCopyOfCourseId(copiedFromIds.get(0));
			}
		}
		catch (Exception e) {
			throw new AssemblyException(
					"Could not assemble course ", e);

		}
		return result;

	}
	
	// don't set them if we can't find them
	private void setRevenueData(CreditCourseHelper ccHelper, CluInfo course) throws AssemblyException {
		if (null != course.getFeeInfo()) {
			CreditCourseRevenueInfoHelper ccrInfoHelper = CreditCourseRevenueInfoHelper.wrap(revenueAssembler.assemble(course.getFeeInfo().getCluFeeRecords()));
			if (null != ccrInfoHelper) {				
				ccHelper.setRevenueInfo(ccrInfoHelper);
			}
		} 
	}
	private void setExpenditureData(CreditCourseHelper ccHelper, CluInfo course) throws AssemblyException {
		if (null != course.getAccountingInfo()) {
			CreditCourseExpenditureInfoHelper cceInfoHelper = CreditCourseExpenditureInfoHelper.wrap(expenditureAssembler.assemble(course.getAccountingInfo()));
			if (null != cceInfoHelper) {				
				ccHelper.setExpenditureInfo(cceInfoHelper);
			}
		} 
	}


	private void buildRelatedCourse(CluInfoHierarchy currentClu, RelationshipHierarchy currentRel) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		LOG.info("Retrieving relation: " + currentClu.getCluInfo().getId() + "\t" + currentRel.getRelationshipType());
		List<CluCluRelationInfo> children = luService.getCluCluRelationsByClu(currentClu.getCluInfo().getId());
		if (children != null) {
			for (CluCluRelationInfo rel : children) {
			// if the cluclu realtion is of type jointCourses dont add as a child to the CluInfoHierarchy object
				if(rel.getType().equals(currentRel.getRelationshipType())){
					CluInfo clu = luService.getClu(rel.getRelatedCluId());
					CluInfoHierarchy c = new CluInfoHierarchy();
					c.setParentRelationType(currentRel.getRelationshipType());
					c.setParentRelationState(currentRel.getRelationshipState());
					c.setCluInfo(clu);
					currentClu.getChildren().add(c);
					for (RelationshipHierarchy h : currentRel.getChildren()) {
						buildRelatedCourse(c, h);
					}
				}
			}
		}
	}


	private SaveResult<CluInfoHierarchy> saveHierarchy(CreditCourseHelper course) throws AssemblyException {

		CluInfoHierarchy clus = buildCluInfoHierarchyFromData(course);

		CourseAssembler.RelationshipHierarchy hierarchy = new CourseAssembler.RelationshipHierarchy();
		CourseAssembler.RelationshipHierarchy formats = new CourseAssembler.RelationshipHierarchy(FORMAT_RELATION_TYPE, "Active");
		CourseAssembler.RelationshipHierarchy activities = new CourseAssembler.RelationshipHierarchy(ACTIVITY_RELATION_TYPE, "Active");

		hierarchy.addChild(formats);
		formats.addChild(activities);
		SaveResult<CluInfoHierarchy> result = new SaveResult<CluInfoHierarchy>();

		try {
			List<ValidationResultInfo> val = validate(null);
			result.setValidationResults(val);

			if (!hasValidationErrors(val)) {
				saveClus(clus, null);
				saveRelations(null, clus);
				result.setValue(removeOrphans(clus));
			} else {
				result.setValue(clus);
			}

		} catch (Exception e) {
			throw new AssemblyException("Unable to save CluInfoHierarchy", e);
		}

		//      CluInfoHierarchyAssembler cluHierarchyAssembler = new CluInfoHierarchyAssembler();
		//      cluHierarchyAssembler.setHierarchy(hierarchy);

		buildJointsFromData(result.getValue().getCluInfo().getId(),course);

		return result;
	}

	private CluInfoHierarchy removeOrphans(CluInfoHierarchy input) {
		if (input.getModificationState() == ModificationState.DELETED) {
			return null;
		}
		List<CluInfoHierarchy> children = new ArrayList<CluInfoHierarchy>();
		for (CluInfoHierarchy c : input.getChildren()) {
			c = removeOrphans(c);
			if (c != null) {
				children.add(c);
			}
		}
		input.setChildren(children);
		return input;
	}

	private void saveRules(String courseId, LuData luData) throws Exception{
		LuRuleInfoPersistanceBean ruleInfoBean = new LuRuleInfoPersistanceBean();
		ruleInfoBean.setLuService(luService);
		ruleInfoBean.setStatementService(statementService);
		ruleInfoBean.updateRules(courseId, luData);
	}

	private CluInfoHierarchy buildCluInfoHierarchyFromData(CreditCourseHelper course) throws AssemblyException {
		//metadata for authz
		//FIXME: change idType below to be non-hardcoded
		Metadata courseMetadata = getMetadata("courseId", course.getId(), course.getType(), course.getState());//TODO cache the metadata

		CluInfoHierarchy result = null;
		CluInfo courseClu = null;
		String state = "draft";
		CluIdentifierInfo cluId = null;
		AdminOrgInfo adminOrg = null;
		
		// FIXME wilj: temp check for id, client needs to enforce modification flags once we get the basics working
		if (course.getId() == null) {
			setCreated(course.getData(), true);
			result = new CluInfoHierarchy();
			courseClu = new CluInfo();
			result.setCluInfo(courseClu);
			
			// TODO unhardcode this stuff
            courseClu.setState(state);
            courseClu.setType("kuali.lu.type.CreditCourse");
		} else {
			// FIXME wilj: forcing update for now, until client enforces modification flags
			setUpdated(course.getData(), true);
			result = getCluInfoHierarchyFromId(course.getId());
			courseClu = result.getCluInfo();
			state = course.getState();
			courseClu.setState(state);
		}

		if (isModified(course.getData())) {
			if (isCreated(course.getData())) {
				result.setModificationState(ModificationState.CREATED);
			} else if (isUpdated(course.getData())) {
				result.setModificationState(ModificationState.UPDATED);
			} else if (isDeleted(course.getData())) {
				result.setModificationState(ModificationState.DELETED);
			}

			cluId = courseClu.getOfficialIdentifier();
			if (cluId == null) {
				cluId = new CluIdentifierInfo();
				courseClu.setOfficialIdentifier(cluId);
			}
			cluId.setSuffixCode(course.getCourseNumberSuffix());
			//AuthzCheck
			if(courseMetadata.getProperties().get("courseTitle").getWriteAccess()!=WriteAccess.NEVER){
				cluId.setLongName(course.getCourseTitle());
			}
			cluId.setDivision(course.getSubjectArea());
			cluId.setShortName(course.getTranscriptTitle());
			//Get the level as the first character in the number pluss "00"
			if(course.getCourseNumberSuffix()!=null&&!course.getCourseNumberSuffix().isEmpty()){
				cluId.setLevel(course.getCourseNumberSuffix().substring(0,1)+"00");
			}
			courseClu.setExpectedFirstAtp(course.getFirstExpectedOffering());

			String instrId = course.getPrimaryInstructor();
			CluInstructorInfo instr = courseClu.getPrimaryInstructor();
			if (instr == null) {
				instr = new CluInstructorInfo();
				courseClu.setPrimaryInstructor(instr);
			}
			instr.setPersonId(instrId);
			
			if (course.getInstructors() != null) {
				for (Data.Property p : course.getInstructors()) {
					if(!"_runtimeData".equals(p.getKey())){
						String instructorId = p.getValue();
						CluInstructorInfo instructor = new CluInstructorInfo();
						instructor.setPersonId(instructorId);
						if (courseClu.getInstructors() == null) {
							courseClu.setInstructors(new ArrayList<CluInstructorInfo>());
						}
						courseClu.getInstructors().add(instructor);
					}
				}
			}

//			//AuthzCheck
//			if(courseMetadata.getProperties().get("department").getWriteAccess()!=WriteAccess.NEVER){
//				adminOrg = courseClu.getPrimaryAdminOrg();
//				if (adminOrg == null) {
//				    adminOrg = new AdminOrgInfo();
//					courseClu.setPrimaryAdminOrg(adminOrg);
//				}
//				adminOrg.setOrgId(course.getDepartment());
//			}
//
//			List<AcademicSubjectOrgInfo> subjectOrgs = new ArrayList<AcademicSubjectOrgInfo>();
//			if (course.getAcademicSubjectOrgs() != null) {
//				for (Data.Property p : course.getAcademicSubjectOrgs()) {
//					if(!"_runtimeData".equals(p.getKey())){
//						String org = p.getValue();
//						AcademicSubjectOrgInfo info = new AcademicSubjectOrgInfo();
//						info.setOrgId(org);
//						subjectOrgs.add(info);
//					}
//				}
//			}
//			courseClu.setAcademicSubjectOrgs(subjectOrgs);

			List<String> campuses = new ArrayList<String>();
			if (course.getCampusLocations() != null) {
				for (Data.Property p : course.getCampusLocations()) {
					if(!"_runtimeData".equals(p.getKey())){
						String campus = p.getValue();
						campuses.add(campus);
					}
				}
			}
			courseClu.setCampusLocations(campuses);

			courseClu.setFeeInfo(feeAssembler.disassemble(course.getFees()));
			addRevenueFee(courseClu, course);
			
			if (null != course.getExpenditureInfo()) {
				courseClu.setAccountingInfo(expenditureAssembler.disassemble(course.getExpenditureInfo().getData()));
			}
			
			//          mergeAlternateIdentifiers(course, courseClu);
			//FIXME this is a fix for rich text data which does not work yet, in place so the ui workaround can work
			if(course.getDescription() == null){
				course.setDescription(RichTextInfoHelper.wrap(new Data()));
			}
			//AuthzCheck
			if(courseMetadata.getProperties().get("description").getWriteAccess()!=WriteAccess.NEVER){
				courseClu.setDescr(getRichText(course.getDescription()));
			}


			//AuthzCheck
			if(courseMetadata.getProperties().get("duration").getWriteAccess()!=WriteAccess.NEVER){
				TimeAmountInfo time = courseClu.getStdDuration();
				if (time == null) {
					time = new TimeAmountInfo();
					courseClu.setStdDuration(time);
				}
				if (course.getDuration() != null) {
					time.setAtpDurationTypeKey(course.getDuration().getTermType());
					if(course.getDuration().getQuantity() != null) {
						time.setTimeQuantity(course.getDuration().getQuantity());
					}
				}
			}

			courseClu.setEffectiveDate(course.getEffectiveDate());
			courseClu.setExpirationDate(course.getExpirationDate());

			if (courseClu.getMetaInfo() != null) {
				courseClu.getMetaInfo().setVersionInd(getVersionIndicator(course.getData()));
			}
		}
		
		buildFormatUpdates(result, course, state);
		buildCrossListingsUpdates(result, course, state);
		buildVersionsUpdates(result, course, state);

		return result;
	}

	// Revenue (also a Fee in R1.0) is hung off course in DOL ("course/revenue"),
	// rather than in the data that course.getFees() returns
	private void addRevenueFee(CluInfo courseClu, CreditCourseHelper course) throws AssemblyException {
		
		// no revenue info, no CreditCourseRevenueInfoHelper, no Revenue Fee
		CreditCourseRevenueInfoHelper revenueHelper = course.getRevenueInfo();
		if (null != revenueHelper) {
			// see RevenueDataAssembler for rationale re: disassemble() returning a List
			List<CluFeeRecordInfo> revenueFeeList = revenueAssembler.disassemble(revenueHelper.getData());
			if (null != revenueFeeList) {
				if (null == courseClu.getFeeInfo()) {
					courseClu.setFeeInfo(new CluFeeInfo());
				}
				if (null == courseClu.getFeeInfo().getCluFeeRecords()) {
					courseClu.getFeeInfo().setCluFeeRecords(new ArrayList<CluFeeRecordInfo>());
				}
				courseClu.getFeeInfo().getCluFeeRecords().add(revenueFeeList.get(0));
			}
		}
		
	}

	private void buildJointsFromData(String parentCourseId,CreditCourseHelper course ) throws AssemblyException{
		try {
			if (course.getJoints() == null) {
				return;
			}
			// CreditCourseJointsHelper joints =
			// CreditCourseJointsHelper.wrap(course.getJoints());
			for (Data.Property p : course.getJoints()) {
				if(!"_runtimeData".equals(p.getKey())){
					CreditCourseJointsHelper joint = CreditCourseJointsHelper
					.wrap((Data) p.getValue());
					//If user has not entered a joint course on the screen then return back
					if(joint.getCourseId()==null){
						return;
					}
					if (isCreated(joint.getData())) {
						CluCluRelationInfo rel = new CluCluRelationInfo();
						rel.setCluId(parentCourseId);
						rel.setRelatedCluId(joint.getCourseId());
						//                  rel.setType(joint.getType());
						//                  Remove hardcoded Type
						rel.setType(JOINT_RELATION_TYPE);
	
						CluCluRelationInfo result= luService.createCluCluRelation(parentCourseId, joint
								.getCourseId(), JOINT_RELATION_TYPE, rel);
						joint.setRelationId(result.getId());
	
					} else if (isDeleted(joint.getData())){
						String relationId = joint.getRelationId();
						if (relationId!= null){
							luService.deleteCluCluRelation(relationId);
						}
					} else if(isUpdated(joint.getData())){
						String relationId = joint.getRelationId();
						CluInfo clu = luService.getClu(joint.getCourseId());
						if(!(clu.getOfficialIdentifier().getLongName().equals(joint.getCourseTitle()))){
							CluCluRelationInfo cluCluRelation = new CluCluRelationInfo();
							cluCluRelation.setId(relationId);
							cluCluRelation.setCluId(parentCourseId);
							cluCluRelation.setRelatedCluId(joint.getCourseId());
							cluCluRelation.setType(JOINT_RELATION_TYPE);
	
							luService.updateCluCluRelation(relationId, cluCluRelation);
						}
					}
				}
			}
		} catch (Exception e) {
			throw new AssemblyException("Unable to save cluClu Joint Relationship", e);
		}

	}

	private void saveClus(CluInfoHierarchy input, String parentId) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException {

		CluInfo result = null;
		CluInfo clu = input.getCluInfo();
		if (input.getModificationState() != null) {
			switch (input.getModificationState()) {
			case CREATED:
				result = luService.createClu(clu.getType(), clu);
				break;
			case UPDATED:
				result = luService.updateClu(clu.getId(), clu);
				break;
			case DELETED:
				//old comment: back out any relationships in case of RI
				deleteCluHierarchy(input, parentId);

			break;
				default:
					// do nothing
			}
		}
		if (result != null) {
			input.setCluInfo(result);
		}
		for (CluInfoHierarchy child : input.getChildren()) {
			saveClus(child, input.getCluInfo().getId());
		}
	}

	private void deleteCluHierarchy(CluInfoHierarchy input, String parentId) throws AlreadyExistsException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException, DependentObjectsExistException {
		CluInfo clu = input.getCluInfo();
		if (parentId != null){
			//getCluCluRelationsByClu doesnt get clus by related clu type?
			List<CluCluRelationInfo> relations = luService.getCluCluRelationsByClu(parentId);
			if(relations != null){
				for (CluCluRelationInfo rel : relations) {
					if(rel.getRelatedCluId().equals(clu.getId())){
						luService.deleteCluCluRelation(rel.getId());
					}
				}
			}
		}
		for (CluInfoHierarchy child : input.getChildren()) {
			deleteCluHierarchy(child, clu.getId());
		}
		input.setChildren(new ArrayList<CluInfoHierarchy>());
		luService.deleteClu(clu.getId());
	}

	private void saveRelations(String parentId, CluInfoHierarchy input) throws AlreadyExistsException, CircularReferenceException, DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		if (parentId != null && input.getModificationState() == ModificationState.CREATED) {
			CluCluRelationInfo rel = new CluCluRelationInfo();
			rel.setCluId(parentId);
			rel.setRelatedCluId(input.getCluInfo().getId());
			rel.setType(input.getParentRelationType());
			rel.setState(input.getParentRelationState());
			LOG.info("Creating relation: " + rel.getCluId() + "\t" + rel.getType() + "\t" + rel.getRelatedCluId());
			try {
				luService.createCluCluRelation(rel.getCluId(), rel.getRelatedCluId(), rel.getType(), rel);
			} catch (CircularRelationshipException e) {
				throw new CircularReferenceException(e);
			}
		}
		for (CluInfoHierarchy h : input.getChildren()) {
			saveRelations(input.getCluInfo().getId(), h);
		}
	}

	private void buildJointsFromCluInfo(CluInfo clu,CreditCourseJointsHelper joint) throws AssemblyException{
		try{
			List<CluInfo> clus = luService.getClusByRelation(clu.getId(), JOINT_RELATION_TYPE);
			joint.setCourseId(clu.getId());
			joint.setType(JOINT_RELATION_TYPE);
			joint.setSubjectArea(clu.getOfficialIdentifier().getDivision());
			joint.setCourseTitle(clu.getOfficialIdentifier().getLongName());
			joint.setCourseNumberSuffix(clu.getOfficialIdentifier().getSuffixCode());

		}
		catch(Exception e){
			throw new AssemblyException("Unable to get cluClu Joint Relationship");
		}

	}

	private void buildFormatsFromCluInfo(CreditCourseHelper course, CluInfoHierarchy cluHierarchy) throws DoesNotExistException,
	InvalidParameterException, MissingParameterException,
	OperationFailedException, AssemblyException {

		CreditCourseFormatHelper format = CreditCourseFormatHelper.wrap(new Data());
		CluInfo clu = cluHierarchy.getCluInfo();
		format.setId(clu.getId());
		format.setState(clu.getState());
		addVersionIndicator(format.getData(), CluInfo.class.getName(), clu.getId(), clu.getMetaInfo().getVersionInd());
		for (CluInfoHierarchy activity : cluHierarchy.getChildren()) {
			addActivities(format, activity);
		}
		if (course.getFormats() == null) {
			course.setFormats(new Data());
		}
		course.getFormats().add(format.getData());
	}

	private void addActivities(CreditCourseFormatHelper format, CluInfoHierarchy cluHierarchy)
	throws DoesNotExistException, InvalidParameterException,
	MissingParameterException, OperationFailedException, AssemblyException {

		CreditCourseActivityHelper activity = CreditCourseActivityHelper.wrap(new Data());
		CluInfo clu = cluHierarchy.getCluInfo();

		activity.setId(clu.getId());
		activity.setActivityType(clu.getType());

		//TimeAmountInfoHelper time = TimeAmountInfoHelper.wrap(timeamountAssembler.assemble(clu.getIntensity()));
		AmountInfo time = clu.getIntensity();
		if (time != null) {
			CreditCourseActivityContactHoursHelper hours = CreditCourseActivityContactHoursHelper.wrap(new Data());
			if(time.getUnitQuantity() != null) {
				hours.setHrs(Integer.valueOf(time.getUnitQuantity()));
			}
			hours.setPer(time.getUnitType());
			activity.setContactHours(hours);
		}

		TimeAmountInfoHelper stdDuration = TimeAmountInfoHelper.wrap(timeamountAssembler.assemble(clu.getStdDuration()));
		if (stdDuration != null) {
			CreditCourseActivityDurationHelper duration = CreditCourseActivityDurationHelper.wrap(new Data());
			duration.setQuantity(stdDuration.getTimeQuantity());
			duration.setTimeUnit(stdDuration.getAtpDurationTypeKey());
			activity.setDuration(duration);
		}

		activity.setDefaultEnrollmentEstimate(clu.getDefaultEnrollmentEstimate());
		activity.setState(clu.getState());
		addVersionIndicator(activity.getData(), CluInfo.class.getName(), clu.getId(), clu.getMetaInfo().getVersionInd());

		if (format.getActivities() == null) {
			format.setActivities(new Data());
		}
		format.getActivities().add(activity.getData());
	}

	private void addCrossListings(CreditCourseHelper course, CluInfoHierarchy cluHierarchy)
	throws DoesNotExistException, InvalidParameterException, MissingParameterException,
	OperationFailedException, AssemblyException {

		CreditCourseCrossListingsHelper xListings = null;
		CluInfo clu = cluHierarchy.getCluInfo();
		List<CluIdentifierInfo> cluIdentifiers = clu.getAlternateIdentifiers();

		for(CluIdentifierInfo cluIdentifier : cluIdentifiers){
			String identifierType = cluIdentifier.getType();
			if(identifierType != null && identifierType.equals("kuali.lu.type.CreditCourse.identifier.cross-listed")){
				xListings = CreditCourseCrossListingsHelper.wrap(new Data());
				xListings.setId(cluIdentifier.getId());
				xListings.setType(cluIdentifier.getType());
				xListings.setDepartment(cluIdentifier.getOrgId());
				xListings.setSubjectArea(cluIdentifier.getDivision());
				xListings.setCourseNumberSuffix(cluIdentifier.getSuffixCode());

				Data data = course.getCrossListings();
				if(data == null){
					data = new Data();
					course.setCrossListings(data);
				}
				data.add(xListings.getData());
			}
		}
	}

	private void addVersions(CreditCourseHelper course, CluInfoHierarchy cluHierarchy)
	throws DoesNotExistException, InvalidParameterException, MissingParameterException,
	OperationFailedException, AssemblyException {

		CreditCourseVersionsHelper versions = null;
		CluInfo clu = cluHierarchy.getCluInfo();
		List<CluIdentifierInfo> cluIdentifiers = clu.getAlternateIdentifiers();

		for(CluIdentifierInfo cluIdentifier : cluIdentifiers){
			String identifierType = cluIdentifier.getType();
			if(identifierType != null && identifierType.equals("kuali.lu.type.CreditCourse.identifier.version")){
				versions = CreditCourseVersionsHelper.wrap(new Data());
				versions.setId(cluIdentifier.getId());
				versions.setType(cluIdentifier.getType());
				versions.setVersionTitle(cluIdentifier.getLongName());
				versions.setSubjectArea(cluIdentifier.getDivision());
				versions.setCourseNumberSuffix(cluIdentifier.getSuffixCode());
				versions.setVersionCode(cluIdentifier.getVariation());

				Data data = course.getVersions();
				if(data == null){
					data = new Data();
					course.setVersions(data);
				}
				data.add(versions.getData());
			}
		}
	}

	private void buildFormatUpdates(CluInfoHierarchy courseHierarchy, CreditCourseHelper course, String state) throws AssemblyException {
		if (course.getFormats() == null) {
			return;
		}
		for (Data.Property p : course.getFormats()) {
        	if(!"_runtimeData".equals(p.getKey())){
				CreditCourseFormatHelper format = CreditCourseFormatHelper.wrap((Data)p.getValue());
				CluInfoHierarchy formatHierarchy = null;
				CluInfo formatClu = null;
	
	
				if (isModified(format.getData())) {
					if (isCreated(format.getData())) {
						formatHierarchy = new CluInfoHierarchy();
						formatClu = new CluInfo();
						formatHierarchy.setCluInfo(formatClu);
						courseHierarchy.getChildren().add(formatHierarchy);
						formatHierarchy.setModificationState(ModificationState.CREATED);
					} else if (isDeleted(format.getData())) {
						formatHierarchy = findChildByCluId(courseHierarchy, format.getId());
						if(formatHierarchy != null){
							formatClu = formatHierarchy.getCluInfo();
							formatHierarchy.setModificationState(ModificationState.DELETED);
						}
					} else if (isUpdated(format.getData())) {
						formatHierarchy = findChildByCluId(courseHierarchy, format.getId());
						if(formatHierarchy != null){
							formatClu = formatHierarchy.getCluInfo();
							formatHierarchy.setModificationState(ModificationState.UPDATED);
						}
					} 
	
					if(formatClu != null){
						// TODO un-hardcode
						formatClu.setType(FORMAT_LU_TYPE);
						formatClu.setState(state);
						formatHierarchy.setParentRelationType(FORMAT_RELATION_TYPE);
						formatHierarchy.setParentRelationState("Active");
						
						// set with effDate, adminOrg and officialIdent needed for validation
						formatClu.setEffectiveDate(course.getEffectiveDate());
	
//						AdminOrgInfo adminOrg = formatClu.getPrimaryAdminOrg();
//		                if (adminOrg == null) {
//		                    adminOrg = new AdminOrgInfo();
//		                    formatClu.setPrimaryAdminOrg(adminOrg);
//		                }
//		                adminOrg.setOrgId(course.getDepartment());
										
						CluIdentifierInfo cluId = formatClu.getOfficialIdentifier();
			            if (cluId == null) {
			                cluId = new CluIdentifierInfo();
			                formatClu.setOfficialIdentifier(cluId);		                
			            }
			            cluId.setSuffixCode(course.getCourseNumberSuffix());
	                    cluId.setLongName(course.getCourseTitle());
	                    cluId.setDivision(course.getSubjectArea());
	                    cluId.setShortName(course.getTranscriptTitle());
	                    cluId.setType(FORMAT_LU_TYPE);
			            
		                
						if (formatClu.getMetaInfo() != null) {
							formatClu.getMetaInfo().setVersionInd(getVersionIndicator(format.getData()));
						}
	
					}
	
					if(formatHierarchy != null){
						buildActivityUpdates(formatHierarchy, format, state);
					}
				}
        	}
		}
	}



	private void buildActivityUpdates(CluInfoHierarchy formatHierarchy,
			CreditCourseFormatHelper format, String state) throws AssemblyException {
		if (format.getActivities() == null) {
			return;
		}
		for (Data.Property p : format.getActivities()) {
        	if(!"_runtimeData".equals(p.getKey())){
				CreditCourseActivityHelper activity = CreditCourseActivityHelper.wrap((Data)p.getValue());
				CluInfoHierarchy activityHierarchy = null;
				CluInfo activityClu = null;
	
	
				if (isModified(activity.getData())) {
					if (isCreated(activity.getData())) {
						activityHierarchy = new CluInfoHierarchy();
						activityClu = new CluInfo();
						activityHierarchy.setCluInfo(activityClu);
						formatHierarchy.getChildren().add(activityHierarchy);
	
						activityHierarchy.setModificationState(ModificationState.CREATED);
					} else if (isDeleted(activity.getData())) {
						activityHierarchy = findChildByCluId(formatHierarchy, activity.getId());
						if(activityHierarchy != null){
							activityClu = activityHierarchy.getCluInfo();
							activityHierarchy.setModificationState(ModificationState.DELETED);
						}     	
	
					} else if (isUpdated(activity.getData())) {
						activityHierarchy = findChildByCluId(formatHierarchy, activity.getId());
						if(activityHierarchy != null){
							activityClu = activityHierarchy.getCluInfo();
							activityHierarchy.setModificationState(ModificationState.UPDATED);
						}
					} 
	
					if(activityClu != null){
						activityClu.setType(activity.getActivityType());
						activityClu.setState(state);
						
						// the formatClu was set with effDate, adminOrg and officialIdent above
						// get it here and set the same for activity
						CluInfo formatClu = formatHierarchy.getCluInfo();
						activityClu.setEffectiveDate(formatClu.getEffectiveDate());
						
//						AdminOrgInfo adminOrg = activityClu.getPrimaryAdminOrg();
//	                    if (adminOrg == null) {
//	                        adminOrg = new AdminOrgInfo();
//	                        activityClu.setPrimaryAdminOrg(adminOrg);
//	                    }
//	                    adminOrg.setOrgId(formatClu.getPrimaryAdminOrg().getOrgId());
	                    
	                    CluIdentifierInfo cluId = activityClu.getOfficialIdentifier();
	                    if (cluId == null) {
	                        cluId = new CluIdentifierInfo();
	                        activityClu.setOfficialIdentifier(cluId);
	                    }
	                    CluIdentifierInfo formatCluId = formatClu.getOfficialIdentifier();
	                    cluId.setSuffixCode(formatCluId.getSuffixCode());
	                    cluId.setLongName(formatCluId.getLongName());
	                    cluId.setDivision(formatCluId.getDivision());
	                    cluId.setShortName(formatCluId.getShortName());
	                    cluId.setType(activity.getActivityType());
						
	
						AmountInfo time = activityClu.getIntensity();
						if (time == null) {
							time = new AmountInfo();
							activityClu.setIntensity(time);
						}
						if (activity.getContactHours() != null) {
							time.setUnitType(activity.getContactHours().getPer());
							time.setUnitQuantity(String.valueOf(activity.getContactHours().getHrs()));
						}
	
						TimeAmountInfo stdDuration = activityClu.getStdDuration();
						if (stdDuration == null){
							stdDuration = new TimeAmountInfo();
							activityClu.setStdDuration(stdDuration);
						}
						if (activity.getDuration() != null){
							stdDuration.setAtpDurationTypeKey(activity.getDuration().getTimeUnit());
							stdDuration.setTimeQuantity(activity.getDuration().getQuantity());
						}
	
						Integer enrEst = activity.getDefaultEnrollmentEstimate();
						activityClu.setDefaultEnrollmentEstimate(enrEst == null ? 0 : enrEst);
	
						// TODO un-hardcode
						
						activityHierarchy.setParentRelationType(ACTIVITY_RELATION_TYPE);
						activityHierarchy.setParentRelationState("Active");
	
	
						if (activityClu.getMetaInfo() != null) {
							activityClu.getMetaInfo().setVersionInd(getVersionIndicator(activity.getData()));
						}
					}
				}
        	}
		}
	}

	private void buildCrossListingsUpdates(CluInfoHierarchy courseHierarchy, CreditCourseHelper course, String state) throws AssemblyException {
		if (course.getCrossListings() == null) {
			return;
		}

		CluInfo cluInfoToStore = courseHierarchy.getCluInfo();
		List<CluIdentifierInfo> alternateIdentifiers = cluInfoToStore.getAlternateIdentifiers();

		List<CluIdentifierInfo> clearedIdInfos = new ArrayList<CluIdentifierInfo>();
		if (isModified(course.getData())) {
			// clear the list because the screen should have all loaded all these AltIdentifiers and
			// they may have been modified by the user and will be populated in for() below.
			Iterator<CluIdentifierInfo> iterator = alternateIdentifiers.iterator();
			while(iterator.hasNext()){
				CluIdentifierInfo cluIdentifierInfo = iterator.next();
				String identifierType = cluIdentifierInfo.getType();
				if(identifierType.equals("kuali.lu.type.CreditCourse.identifier.cross-listed")){
					clearedIdInfos.add(cluIdentifierInfo);
				}
			}
		}
		alternateIdentifiers.removeAll(clearedIdInfos);

		for (Data.Property p : course.getCrossListings()) {
        	if(!"_runtimeData".equals(p.getKey())){
				CreditCourseCrossListingsHelper xListings = CreditCourseCrossListingsHelper.wrap((Data)p.getValue());
				if(!isDeleted(xListings.getData())){ 
					CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
					cluIdentifier.setType("kuali.lu.type.CreditCourse.identifier.cross-listed");
					cluIdentifier.setState(state);
					cluIdentifier.setOrgId(xListings.getDepartment());
					cluIdentifier.setDivision(xListings.getSubjectArea());
					cluIdentifier.setSuffixCode(xListings.getCourseNumberSuffix());
					alternateIdentifiers.add(cluIdentifier);
				}
        	}
		}
		cluInfoToStore.setAlternateIdentifiers(alternateIdentifiers);
	}

	private void buildVersionsUpdates(CluInfoHierarchy courseHierarchy, CreditCourseHelper course, String state) throws AssemblyException {
		if (course.getVersions() == null) {
			return;
		}

		CluInfo cluInfoToStore = courseHierarchy.getCluInfo();
		List<CluIdentifierInfo> alternateIdentifiers = cluInfoToStore.getAlternateIdentifiers();

		List<CluIdentifierInfo> clearedIdInfos = new ArrayList<CluIdentifierInfo>();
		if (isModified(course.getData())) {
			// clear the list because the screen should have all loaded all these AltIdentifiers and
			// they may have been modified by the user and will be populated in for() below.
			Iterator<CluIdentifierInfo> iterator = alternateIdentifiers.iterator();
			while(iterator.hasNext()){
				CluIdentifierInfo cluIdentifierInfo = iterator.next();
				String identifierType = cluIdentifierInfo.getType();
				if(identifierType.equals("kuali.lu.type.CreditCourse.identifier.version")){
					clearedIdInfos.add(cluIdentifierInfo);
				}
			}
		}
		alternateIdentifiers.removeAll(clearedIdInfos);

		for (Data.Property p : course.getVersions()) {
        	if(!"_runtimeData".equals(p.getKey())){
				CreditCourseVersionsHelper versions = CreditCourseVersionsHelper.wrap((Data)p.getValue());
				//Don't add to AlternateIdentifiers if it is deleted
				if(!isDeleted(versions.getData())){ 
					CluIdentifierInfo cluIdentifier = new CluIdentifierInfo();
					cluIdentifier.setId(versions.getId());
					cluIdentifier.setDivision(versions.getSubjectArea());
					cluIdentifier.setSuffixCode(versions.getCourseNumberSuffix());
					cluIdentifier.setType("kuali.lu.type.CreditCourse.identifier.version");
					cluIdentifier.setState(state);
					cluIdentifier.setLongName(versions.getVersionTitle());
					cluIdentifier.setVariation(versions.getVersionCode());
					alternateIdentifiers.add(cluIdentifier);
				}
        	}
		}
		cluInfoToStore.setAlternateIdentifiers(alternateIdentifiers);
	}

	// TODO rewrite alternate identifier code to create separate properties per alternate identifier type
	//  private void mergeAlternateIdentifiers(CreditCourse course,
	//  CluInfo courseClu) throws AssemblyException {
	//  // create map of existing identifiers
	//  Map<String, CluIdentifierInfo> ids = new HashMap<String, CluIdentifierInfo>();
	//  for (CluIdentifierInfo id : courseClu.getAlternateIdentifiers()) {
	//  if (id.getId() != null) {
	//  ids.put(id.getId(), id);
	//  }
	//  }
	//  for (Property p : course.getAlternateIdentifiers()) {
	//  CluIdentifierInfoData data = p.getValue();
	//  CluIdentifierInfo idSource = cluIdentifierAssembler.disassemble(data);
	//  CluIdentifierInfo idTarget = ids.get(idSource.getId());
	//  if (idTarget == null) {
	//  // newly created
	//  courseClu.getAlternateIdentifiers().add(idSource);
	//  } else {
	//  // modified
	//  // skipping some fields that shouldn't be reassigned like id, cluId, etc
	//  idTarget.setCode(idSource.getCode());
	//  idTarget.setDivision(idSource.getDivision());
	//  idTarget.setLevel(idSource.getLevel());
	//  idTarget.setLongName(idSource.getLongName());
	//  idTarget.setOrgId(idSource.getOrgId());
	//  idTarget.setShortName(idSource.getShortName());
	//  idTarget.setSuffixCode(idSource.getSuffixCode());
	//  idTarget.setVariation(idSource.getVariation());

	//  ids.remove(idTarget.getId());
	//  }
	//  }

	//  // anything left in the ids map is something that was deleted by the client
	//  for (CluIdentifierInfo c : ids.values()) {
	//  courseClu.getAlternateIdentifiers().remove(c);
	//  }
	//  }
	private CluInfoHierarchy findChildByCluId(CluInfoHierarchy parent, String cluId) {
		for (CluInfoHierarchy c : parent.getChildren()) {
			if (c.getCluInfo().getId() != null && c.getCluInfo().getId().equals(cluId)) {
				return c;
			}
		}
		return null;
	}

	private String getCluTypeName(String cluTypeKey) throws AssemblyException {
		if (cluTypeKey != null) {
			LuTypeInfo cluType;
			try {
				cluType = getLuService().getLuType(cluTypeKey);
				return cluType.getName();        
			} catch (Exception e) {
				LOG.warn("Unable to get clu type for key " + cluTypeKey, e);
			}
		}
		return cluTypeKey;
	}

	private String getAtpTypeName(String atpTypeKey) throws AssemblyException {
		if (atpTypeKey != null) {
			AtpTypeInfo atp;
			try {
				atp = getAtpService().getAtpType(atpTypeKey);
				return atp.getName();        
			} catch (Exception e) {
				LOG.warn("Unable to get atp type for key " + atpTypeKey, e);
			}
		}
		return atpTypeKey;
	}

	private String getOrgName(String orgId) throws AssemblyException {
		if (orgId != null) {
			OrgInfo org;
			try {
				org = getOrgService().getOrganization(orgId);
				return org.getLongName();        
			} catch (Exception e) {
				LOG.warn("Unable to get org name for id " + orgId, e);
			}
		}
		return orgId;
	}   

	private List<RuleInfo> getRules(String courseId) throws Exception{
		LuRuleInfoPersistanceBean ruleInfoBean = new LuRuleInfoPersistanceBean();
		ruleInfoBean.setLuService(luService);
		ruleInfoBean.setStatementService(statementService);
		return ruleInfoBean.fetchRules(courseId);
	}

	//FIXME:  This is a hack to extract the rule info statements from LuData and put them
	//  into the cluModel. Once Rule Info is using the correct DOL paths this method can be removed 
	private void extractStatements(Data data) {
		LuData luData = (LuData)data;
		if (luData.getRuleInfos() != null && !luData.getRuleInfos().isEmpty()) {
			Data statements = new Data();
			for (RuleInfo r : luData.getRuleInfos()) {
			   statements.add(r.getNaturalLanguageForRuleEdit());	
			}
			data.set("statements",statements);
		}
	}

	private RichTextInfo getRichText(RichTextInfoHelper hlp) throws AssemblyException {
		if (hlp == null || hlp.getData() == null) {
			return null;
		}
		return richtextAssembler.disassemble(hlp.getData());
	}

	private SingleUseLoInfoAssembler getSingleUseLoAssembler() {
		if (loAssembler == null) {
			loAssembler = new SingleUseLoInfoAssembler();
			loAssembler.setLoService(loService);
			loAssembler.setLuService(luService);
		}
		return loAssembler;
	}
	
	private LearningResultAssembler getLearningResultAssembler() {
		if (lrAssembler == null) {
			lrAssembler = new LearningResultAssembler();
			lrAssembler.setLuService(luService);
		}
		return lrAssembler;
	}

	public LuService getLuService() {
		return luService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	public StatementService getStatementService() {
		return statementService;
	}

	public void setStatementService(StatementService statementService) {
		this.statementService = statementService;
	}

	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	public void setLoService(LearningObjectiveService loService) {
		this.loService = loService;
	}

	public OrganizationService getOrgService() {
		return orgService;
	}

	public void setOrgService(OrganizationService orgService) {
		this.orgService = orgService;
	}

	public AtpService getAtpService() {
		return atpService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	public static class RelationshipHierarchy {
		private final String relationshipType;
		private final String relationshipState;
		private final List<RelationshipHierarchy> children = new ArrayList<RelationshipHierarchy>();
		public RelationshipHierarchy() {
			this.relationshipType = null;
			this.relationshipState = null;
		}
		public RelationshipHierarchy(final String relationshipType, final String relationshipState) {
			this.relationshipType = relationshipType;
			this.relationshipState = relationshipState;
		}
		public RelationshipHierarchy addChild(RelationshipHierarchy child) {
			children.add(child);
			return this;
		}
		public String getRelationshipType() {
			return relationshipType;
		}
		public List<RelationshipHierarchy> getChildren() {
			return children;
		}
		public String getRelationshipState() {
			return relationshipState;
		}

	}

	@Override
	protected String getDataType() {
		return COURSE_DATA_TYPE;
	}

	@Override
	protected String getDocumentPropertyName() {
		return "course";
	}

	@Override
	protected String getDtoName() {
		return "kuali.lu.type.CreditCourse";
	}

	@Override
	protected AttributeSet getQualification(String idType, String id) {
		String DOCUMENT_TYPE_NAME = "documentTypeName";
		AttributeSet qualification = new AttributeSet();
		qualification.put(DOCUMENT_TYPE_NAME, "CluCreditCourse");
		/*
		 *	This commented out for permission changes
		 * 
		 *         String QUALIFICATION_PROPOSAL_ID = "courseId";
		 *         qualification.put(QUALIFICATION_PROPOSAL_ID, id);
		 */        
		qualification.put(idType, id);
		return qualification;
	}
}
