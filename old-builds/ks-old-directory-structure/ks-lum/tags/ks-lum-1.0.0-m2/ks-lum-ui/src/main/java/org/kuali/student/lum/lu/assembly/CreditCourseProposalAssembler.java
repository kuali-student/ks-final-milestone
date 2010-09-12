package org.kuali.student.lum.lu.assembly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.client.AssemblyException;
import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.common.assembly.client.Metadata;
import org.kuali.student.common.assembly.client.SaveResult;
import org.kuali.student.common.assembly.client.Data.Property;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
import org.kuali.student.core.exceptions.DependentObjectsExistException;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.exceptions.PermissionDeniedException;
import org.kuali.student.core.exceptions.VersionMismatchException;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
import org.kuali.student.core.validation.dto.ValidationResultInfo.ErrorLevel;
import org.kuali.student.lum.lu.assembly.CluInfoHierarchyAssembler.RelationshipHierarchy;
import org.kuali.student.lum.lu.assembly.data.client.CluIdentifierInfoData;
import org.kuali.student.lum.lu.assembly.data.client.VersionData;
import org.kuali.student.lum.lu.assembly.data.client.atp.TimeAmountInfoData;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourse;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseActivity;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseFormat;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseProposal;
import org.kuali.student.lum.lu.assembly.data.client.proposal.ProposalInfoData;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy;
import org.kuali.student.lum.lu.assembly.data.server.CluInfoHierarchy.ModificationState;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluIdentifierInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

public class CreditCourseProposalAssembler implements Assembler<CreditCourseProposal, Void> {
	// TODO make sure that cluclurelation version indicators are carried over on retrieval
	// TODO verify that the right relation types have been used
	// public static final String FORMAT_RELATION_TYPE =
	// LUConstants.LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT;
	// public static final String ACTIVITY_RELATION_TYPE =
	// LUConstants.LU_LU_RELATION_TYPE_CONTAINS;
	public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
	public static final String FORMAT_LU_TYPE = "kuali.lu.type.CreditCourseFormatShell";
	public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";
	public static final String PROPOSAL_REFERENCE_TYPE = "kuali.proposal.referenceType.clu"; // <- what the service says, but the dictionary says: "kuali.referenceType.CLU";
	private final String proposalState;
	private CluInfoHierarchyAssembler cluHierarchyAssembler;
	private final RichTextInfoAssembler richtextAssembler = new RichTextInfoAssembler();
	private final TimeAmountInfoAssembler timeamountAssembler = new TimeAmountInfoAssembler();
	private final CluIdentifierInfoAssembler cluIdentifierAssembler = new CluIdentifierInfoAssembler();
	private final CluInstructorInfoDataAssembler instructorAssembler = new CluInstructorInfoDataAssembler();
	private ProposalService proposalService;
	private LuService luService;
	
	public CreditCourseProposalAssembler(String proposalState) {
		this.proposalState = proposalState;
	}
	
	@Override
	public CreditCourseProposal get(String id) throws AssemblyException {
		CreditCourseProposal result = null;
		// TODO figure out why the format isn't found
		try {
			ProposalInfoData proposal = getProposal(id);
			if (proposal != null) {
				result = new CreditCourseProposal();
				result.setProposal(proposal);
				result.setCourse(getCourse(proposal));
			}
		} catch (Exception e) {
			throw new AssemblyException(
					"Could not assemble credit course proposal", e);
		}

		return result;
	}

	private CluInfoHierarchyAssembler getCluHierarchyAssembler() {
		if (cluHierarchyAssembler == null) {
			RelationshipHierarchy course = new RelationshipHierarchy();
			RelationshipHierarchy formats = new RelationshipHierarchy(FORMAT_RELATION_TYPE, "Active");
			RelationshipHierarchy activities = new RelationshipHierarchy(ACTIVITY_RELATION_TYPE, "Active");
			
			course.addChild(formats);
			formats.addChild(activities);
			
			cluHierarchyAssembler = new CluInfoHierarchyAssembler();
			cluHierarchyAssembler.setHierarchy(course);
			cluHierarchyAssembler.setLuService(luService);
		}
		return cluHierarchyAssembler;
	}

	private CreditCourse getCourse(ProposalInfoData proposal)
			throws AssemblyException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		CreditCourse result = new CreditCourse();

		Data references = proposal.getReferences();
		if (references.size() != 1) {
			throw new AssemblyException(
					"Invalid number of referenced courses for proposal: "
							+ references.size());
		}
		String cluId = references.get(0);
		CluInfoHierarchy cluHierarchy = getCluHierarchyAssembler().get(cluId);

		if (cluHierarchy == null || cluHierarchy.getCluInfo() == null) {
			throw new AssemblyException("Unable to retrieve course for id: "
					+ cluId);
		}
		CluInfo course = cluHierarchy.getCluInfo();
		result.setId(cluId);
		result.setCourseNumberSuffix(course.getOfficialIdentifier()
				.getSuffixCode());
		result.setCourseTitle(course.getOfficialIdentifier().getLongName());
		result.setEffectiveDate(course.getEffectiveDate());
		result.setExpirationDate(course.getExpirationDate());
		
		AdminOrgInfo admin = course.getPrimaryAdminOrg();
		if (admin != null) {
			result.setDepartment(admin.getOrgId());
		}
		
		for (CluIdentifierInfo alt : course.getAlternateIdentifiers()) {
			result.getAlternateIdentifiers().add(cluIdentifierAssembler.assemble(alt));
		}
		// FIXME wilj: figure out how to retrieve the version codes and cross listings 

		result.setDescription(richtextAssembler.assemble(course.getDesc()));
		result.setRationale(richtextAssembler.assemble(course.getMarketingDesc()));
		result.setDuration(timeamountAssembler.assemble(course.getIntensity()));
		result.setPrimaryInstructor(instructorAssembler.assemble(course.getPrimaryInstructor()));
		result.setState(course.getState());
		result.setSubjectArea(course.getOfficialIdentifier().getDivision());
		result.setTranscriptTitle(course.getOfficialIdentifier().getShortName());
		result.setType(course.getType());
		

		for (String org : course.getAcademicSubjectOrgs()) {
			result.getAcademicSubjectOrgs().add(org);
		}
		
		for (String campus : course.getCampusLocationList()) {
			result.getCampusLocations().add(campus);
		}
		result.getModifications().getVersions().add(new VersionData(CluInfo.class.getName(), course.getId(), course.getMetaInfo().getVersionInd()));

		for (CluInfoHierarchy format : cluHierarchy.getChildren()) {
			addFormats(result, format);	
		}
		
		return result;
	}

	private void addFormats(CreditCourse course, CluInfoHierarchy cluHierarchy) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, AssemblyException {

		CreditCourseFormat format = new CreditCourseFormat();
		CluInfo clu = cluHierarchy.getCluInfo();
		format.setId(clu.getId());
		format.setState(clu.getState());
		format.getModifications().getVersions().add(new VersionData(CluInfo.class.getName(), clu.getId(), clu.getMetaInfo().getVersionInd()));
		for (CluInfoHierarchy activity : cluHierarchy.getChildren()) {
			addActivities(format, activity);
		}


		course.addFormat(format);
	}

	private void addActivities(CreditCourseFormat format, CluInfoHierarchy cluHierarchy)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException, AssemblyException {

		CreditCourseActivity activity = new CreditCourseActivity();
		CluInfo clu = cluHierarchy.getCluInfo();
		
		activity.setId(clu.getId());
		activity.setActivityType(clu.getType());
		activity.setIntensity(timeamountAssembler.assemble(clu.getIntensity()));
		activity.setState(clu.getState());
		activity.getModifications().getVersions().add(new VersionData(CluInfo.class.getName(), clu.getId(), clu.getMetaInfo().getVersionInd()));

		format.addActivity(activity);
	}

	
	private ProposalInfoData getProposal(String id)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		ProposalInfoData result = null;

		ProposalInfo prop = proposalService.getProposal(id);
		if (prop != null) {
			result = copyProposalDTO(prop);
		}

		return result;
	}

	private ProposalInfoData copyProposalDTO(ProposalInfo prop) {
		ProposalInfoData result = new ProposalInfoData();

		result.setId(prop.getId());
		result.setRationale(prop.getRationale());
		result.setState(prop.getState());
		result.setTitle(prop.getName());
		result.setType(prop.getType());
		result.setReferenceType(prop.getProposalReferenceType());
		for (String s : prop.getProposalReference()) {
			result.getReferences().add(s);
		}
		result.getModifications().getVersions().add(new VersionData(ProposalInfo.class.getName(), prop.getId(), prop.getMetaInfo().getVersionInd()));

		return result;
	}

	@Override
	public Metadata getMetadata() throws AssemblyException {
		Metadata result = new Metadata();
		result.setDataType(CreditCourseProposal.class.getName());

		// TODO read metadata from dictionary
		result.getProperties().put(
				CreditCourseProposal.Properties.TYPE.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				CreditCourseProposal.Properties.STATE.getKey(),
				getMockMetadata(String.class));

		result.getProperties().put(
				CreditCourseProposal.Properties.PROPOSAL.getKey(),
				getProposalMetadata());
		result.getProperties().put(
				CreditCourseProposal.Properties.COURSE.getKey(),
				getCreditCourseMetadata());

		return result;
	}

	private Metadata getCreditCourseMetadata() {
		Metadata result = new Metadata();
		result.setDataType(CreditCourse.class.getName());

		// TODO read metadata from dictionary
		result.getProperties().put(CreditCourse.Properties.ID.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.TYPE.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.STATE.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				CreditCourse.Properties.COURSE_NUMBER_SUFFIX.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				CreditCourse.Properties.COURSE_TITLE.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.DEPARTMENT.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				CreditCourse.Properties.DESCRIPTION.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.DURATION.getKey(),
				getMockMetadata(TimeAmountInfoData.class));
		result.getProperties().put(
				CreditCourse.Properties.SUBJECT_AREA.getKey(),
				getMockMetadata(String.class));
		// TODO verify if this is a useful "dataType" for Data objects acting as
		// a list
		result.getProperties().put(
				CreditCourse.Properties.TERMS_OFFERED.getKey(),
				getMockMetadata(Data.class));
		result.getProperties().put(
				CreditCourse.Properties.TRANSCRIPT_TITLE.getKey(),
				getMockMetadata(String.class));

		result.getProperties().put(CreditCourse.Properties.FORMATS.getKey(),
				getCreditCourseFormatMetadata());

		return result;
	}

	private Metadata getCreditCourseFormatMetadata() {
		Metadata result = new Metadata();
		result.setDataType(CreditCourseFormat.class.getName());

		// TODO read metadata from dictionary
		result.getProperties().put(CreditCourseFormat.Properties.ID.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				CreditCourseFormat.Properties.STATE.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				CreditCourseFormat.Properties.ACTIVITIES.getKey(),
				getCreditCourseActivityMetadata());

		return result;
	}

	private Metadata getCreditCourseActivityMetadata() {
		Metadata result = new Metadata();
		result.setDataType(CreditCourseActivity.class.getName());

		// TODO read metadata from dictionary
		result.getProperties().put(CreditCourseActivity.Properties.ID.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				CreditCourseActivity.Properties.STATE.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				CreditCourseActivity.Properties.ACTIVITY_TYPE.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				CreditCourseActivity.Properties.INTENSITY.getKey(),
				getMockMetadata(TimeAmountInfoData.class));

		return result;
	}

	private Metadata getProposalMetadata() {
		Metadata result = new Metadata();
		result.setDataType(ProposalInfoData.class.getName());

		// TODO read metadata from dictionary
		result.getProperties().put(ProposalInfoData.Properties.ID.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(ProposalInfoData.Properties.TYPE.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(ProposalInfoData.Properties.STATE.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				ProposalInfoData.Properties.RATIONALE.getKey(),
				getMockMetadata(String.class));
		result.getProperties().put(
				ProposalInfoData.Properties.REFERENCE_TYPE.getKey(),
				getMockMetadata(String.class));
		// TODO verify if this is a useful "dataType" for Data objects acting as
		// a list
		result.getProperties().put(
				ProposalInfoData.Properties.REFERENCES.getKey(),
				getMockMetadata(Data.class));
		result.getProperties().put(ProposalInfoData.Properties.TITLE.getKey(),
				getMockMetadata(String.class));

		return result;
	}

	private Metadata getMockMetadata(Class<?> dataType) {
		Metadata result = new Metadata();
		result.setDataType(dataType.getName());
		// TODO mock more metadata, or go ahead and implement dictionary lookup
		return result;
	}


	@Override
	public SaveResult<CreditCourseProposal> save(CreditCourseProposal data)
			throws AssemblyException {
		try {
			SaveResult<CreditCourseProposal> result = new SaveResult<CreditCourseProposal>();
			List<ValidationResultInfo> validationResults = validate(data);
			result.setValidationResults(validationResults);
			if (validationFailed(validationResults)) {
				result.setValue(data);
				return result;
			}


			CreditCourseProposal root = (CreditCourseProposal) data;
			// first save all of the clus and relations
			SaveResult<CluInfoHierarchy> courseResult = saveCourse(root.getCourse());
			if (result.getValidationResults() == null) {
				result.setValidationResults(courseResult.getValidationResults());
			} else if (courseResult.getValidationResults() != null){
				result.getValidationResults().addAll(courseResult.getValidationResults());
			}
			String courseId = null;
			if (courseResult.getValue() != null && courseResult.getValue().getCluInfo() != null) {
				courseId = courseResult.getValue().getCluInfo().getId();
			}
			if (courseId == null) {
				throw new AssemblyException("Course ID was null after save");
			}
			
			// make sure that the proposal's reference info is properly set
			ProposalInfoData inputProposal = root.getProposal();
			inputProposal.setReferenceType(PROPOSAL_REFERENCE_TYPE);
			Data references = inputProposal.getReferences();
			if (!references.containsValue(new Data.StringValue(courseId))) {
				references.add(courseId);
			}

			String proposalId = saveProposal(inputProposal);


			result.setValidationResults(validationResults);
			result.setValue((proposalId == null) ? null : get(proposalId));
			return result;
		} catch (Exception e) {
			throw new AssemblyException("Unable to save proposal", e);
		}
	}

	
	private boolean validationFailed(
			List<ValidationResultInfo> validationResults) {
		boolean result = false;
		if (validationResults != null) {
			for (ValidationResultInfo info : validationResults) {
				if (info.getLevel() == ErrorLevel.ERROR) {
					result = true;
					break;
				}
			}
		}
		return result;
	}

	private String saveProposal(ProposalInfoData inputProposal) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException {
		String result = null;
		if (inputProposal.getModifications().isDeleted()) {
			proposalService.deleteProposal(inputProposal.getId());
		} else if (inputProposal.getModifications().isModified() || inputProposal.getId() == null) { // FIXME wilj: use modification flags once the client enforces them
			ProposalInfo prop = null;
			// FIXME wilj: use modification flags once the client enforces them
			if (inputProposal.getId() == null) {
//			if (inputProposal.getModifications().isCreated()) {
				prop = new ProposalInfo();
				prop.setType("kuali.proposal.type.course.create");
				prop.setProposalReferenceType("kuali.proposal.referenceType.clu");
			} else {
				prop = proposalService.getProposal(inputProposal.getId());
			}

			prop.setRationale(inputProposal.getRationale());
			prop.setState(inputProposal.getState());
			prop.setName(inputProposal.getTitle());
			for (Property p : inputProposal.getReferences()) {
				String ref = p.getValue();
				if (!prop.getProposalReference().contains(ref)) {
					prop.getProposalReference().add(ref);
				}
			}
			if (prop.getMetaInfo() != null) {
				prop.getMetaInfo().setVersionInd(inputProposal.getModifications().getVersionIndicator());
			}
			
			ProposalInfo saved = null;
			// FIXME wilj: use modification flags once the client enforces them
			if (inputProposal.getId() == null) {
//			if (inputProposal.getModifications().isCreated()) {
				saved = proposalService.createProposal(prop.getType(), prop);
			} else {
				saved = proposalService.updateProposal(prop.getId(), prop);
			}
			if (saved != null) {
				result = saved.getId();
			}
		} else {
			result = inputProposal.getId();
		}
		return result;
	}

	private SaveResult<CluInfoHierarchy> saveCourse(CreditCourse course) throws AssemblyException {
		if (course == null) {
			throw new AssemblyException("Cannot save proposal without course");
		}
		CluInfoHierarchy clus = buildCluInfoHiearchy(course);
		return getCluHierarchyAssembler().save(clus);
	}


	
	private CluInfoHierarchy buildCluInfoHiearchy(CreditCourse course) throws AssemblyException {
		CluInfoHierarchy result = null;
		CluInfo courseClu = null;
		// FIXME wilj: temp check for id, client needs to enforce modification flags once we get the basics working
		if (course.getId() == null) {
			course.getModifications().setCreated(true);
			result = new CluInfoHierarchy();
			courseClu = new CluInfo();
			result.setCluInfo(courseClu);
		} else {
			// FIXME wilj: forcing update for now, until client enforces modification flags
			course.getModifications().setUpdated(true);
			result = getCluHierarchyAssembler().get(course.getId());
			courseClu = result.getCluInfo();
		}
		
		if (course.getModifications().isModified()) {
			if (course.getModifications().isCreated()) {
				result.setModificationState(ModificationState.CREATED);
			} else if (course.getModifications().isUpdated()) {
				result.setModificationState(ModificationState.UPDATED);
			} else if (course.getModifications().isDeleted()) {
				result.setModificationState(ModificationState.DELETED);
			} 
			
			CluIdentifierInfo cluId = courseClu.getOfficialIdentifier();
			if (cluId == null) {
				cluId = new CluIdentifierInfo();
				courseClu.setOfficialIdentifier(cluId);
			}
			cluId.setSuffixCode(course.getCourseNumberSuffix());
			cluId.setLongName(course.getCourseTitle());
			cluId.setDivision(course.getSubjectArea());
			cluId.setShortName(course.getTranscriptTitle());
			
			courseClu.setPrimaryInstructor(instructorAssembler.disassemble(course.getPrimaryInstructor()));
			AdminOrgInfo admin = courseClu.getPrimaryAdminOrg();
			if (admin == null) {
				admin = new AdminOrgInfo();
				courseClu.setPrimaryAdminOrg(admin);
			}
			admin.setOrgId(course.getDepartment());
			
			List<String> subjectOrgs = new ArrayList<String>();
			for (Property p : course.getAcademicSubjectOrgs()) {
				String org = p.getValue();
				subjectOrgs.add(org);
			}
			courseClu.setAcademicSubjectOrgs(subjectOrgs);
			
			List<String> campuses = new ArrayList<String>();
			for (Property p : course.getCampusLocations()) {
				String campus = p.getValue();
				campuses.add(campus);
			}
			courseClu.setCampusLocationList(campuses);
			
	
			mergeAlternateIdentifiers(course, courseClu);
			
			
			courseClu.setDesc(richtextAssembler.disassemble(course.getDescription()));
			courseClu.setMarketingDesc(richtextAssembler.disassemble(course.getRationale()));
			courseClu.setIntensity(timeamountAssembler.disassemble(course.getDuration()));
	
			courseClu.setEffectiveDate(course.getEffectiveDate());
			courseClu.setExpirationDate(course.getExpirationDate());
			
			// TODO unhardcode this stuff
			courseClu.setState("draft");
			courseClu.setType("kuali.lu.type.CreditCourse");
			
			if (courseClu.getMetaInfo() != null) {
				courseClu.getMetaInfo().setVersionInd(course.getModifications().getVersionIndicator());
			}
		}
		
		buildFormatUpdates(result, course);
		
		return result;
	}

	private void mergeAlternateIdentifiers(CreditCourse course,
			CluInfo courseClu) throws AssemblyException {
		// create map of existing identifiers
		Map<String, CluIdentifierInfo> ids = new HashMap<String, CluIdentifierInfo>();
		for (CluIdentifierInfo id : courseClu.getAlternateIdentifiers()) {
			if (id.getId() != null) {
				ids.put(id.getId(), id);
			}
		}
		for (Property p : course.getAlternateIdentifiers()) {
			CluIdentifierInfoData data = p.getValue();
			CluIdentifierInfo idSource = cluIdentifierAssembler.disassemble(data);
			CluIdentifierInfo idTarget = ids.get(idSource.getId());
			if (idTarget == null) {
				// newly created
				courseClu.getAlternateIdentifiers().add(idSource);
			} else {
				// modified
				// skipping some fields that shouldn't be reassigned like id, cluId, etc
				idTarget.setCode(idSource.getCode());
				idTarget.setDivision(idSource.getDivision());
				idTarget.setLevel(idSource.getLevel());
				idTarget.setLongName(idSource.getLongName());
				idTarget.setOrgId(idSource.getOrgId());
				idTarget.setShortName(idSource.getShortName());
				idTarget.setSuffixCode(idSource.getSuffixCode());
				idTarget.setVariation(idSource.getVariation());
				
				ids.remove(idTarget.getId());
			}
		}
		
		// anything left in the ids map is something that was deleted by the client
		for (CluIdentifierInfo c : ids.values()) {
			courseClu.getAlternateIdentifiers().remove(c);
		}
	}

	private void buildFormatUpdates(CluInfoHierarchy courseHierarchy, CreditCourse course) throws AssemblyException {
		for (Property p : course.getFormats()) {
			CreditCourseFormat format = p.getValue();
			CluInfoHierarchy formatHierarchy = null;
			CluInfo formatClu = null;
			
			if (format.getModifications().isCreated()) {
				formatHierarchy = new CluInfoHierarchy();
				formatClu = new CluInfo();
				formatHierarchy.setCluInfo(formatClu);
				courseHierarchy.getChildren().add(formatHierarchy);
			} else {
				formatHierarchy = findChildByCluId(courseHierarchy, format.getId());
				formatClu = formatHierarchy.getCluInfo();
			}
			
			if (format.getModifications().isModified()) {
				if (format.getModifications().isCreated()) {
					formatHierarchy.setModificationState(ModificationState.CREATED);
				} else if (format.getModifications().isUpdated()) {
					formatHierarchy.setModificationState(ModificationState.UPDATED);
				} else if (format.getModifications().isDeleted()) {
					formatHierarchy.setModificationState(ModificationState.DELETED);
				} 
				
				// TODO un-hardcode
				formatClu.setState("draft");
				formatClu.setType(FORMAT_LU_TYPE);
				formatHierarchy.setParentRelationType(FORMAT_RELATION_TYPE);
				formatHierarchy.setParentRelationState("Active");
				if (formatClu.getMetaInfo() != null) {
					formatClu.getMetaInfo().setVersionInd(format.getModifications().getVersionIndicator());
				}
				
			}
			buildActivityUpdates(formatHierarchy, format);
		}
	}
	
	
	
	private void buildActivityUpdates(CluInfoHierarchy formatHierarchy,
			CreditCourseFormat format) throws AssemblyException {
		for (Property p : format.getActivities()) {
			CreditCourseActivity activity = p.getValue();
			CluInfoHierarchy activityHierarchy = null;
			CluInfo activityClu = null;
			
			if (activity.getModifications().isCreated()) {
				activityHierarchy = new CluInfoHierarchy();
				activityClu = new CluInfo();
				activityHierarchy.setCluInfo(activityClu);
				formatHierarchy.getChildren().add(activityHierarchy);
			} else {
				activityHierarchy = findChildByCluId(formatHierarchy, activity.getId());
				activityClu = formatHierarchy.getCluInfo();
			}
			
			if (activity.getModifications().isModified()) {
				if (activity.getModifications().isCreated()) {
					activityHierarchy.setModificationState(ModificationState.CREATED);
				} else if (activity.getModifications().isUpdated()) {
					activityHierarchy.setModificationState(ModificationState.UPDATED);
				} else if (activity.getModifications().isDeleted()) {
					activityHierarchy.setModificationState(ModificationState.DELETED);
				} 

				activityClu.setType(activity.getActivityType());
				activityClu.setIntensity(timeamountAssembler.disassemble(activity.getIntensity()));
				// TODO un-hardcode
				activityClu.setState("draft");
				activityHierarchy.setParentRelationType(ACTIVITY_RELATION_TYPE);
				activityHierarchy.setParentRelationState("Active");

				
				if (activityClu.getMetaInfo() != null) {
					activityClu.getMetaInfo().setVersionInd(activity.getModifications().getVersionIndicator());
				}
			}
		}
	}

	private CluInfoHierarchy findChildByCluId(CluInfoHierarchy parent, String cluId) {
		for (CluInfoHierarchy c : parent.getChildren()) {
			if (c.getCluInfo().getId() != null && c.getCluInfo().getId().equals(cluId)) {
				return c;
			}
		}
		return null;
	}

	@Override
	public List<ValidationResultInfo> validate(CreditCourseProposal data)
			throws AssemblyException {
		// TODO validate CreditCourseProposal
		return null;
	}

	


	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

	@Override
	public CreditCourseProposal assemble(Void input) throws AssemblyException {
		throw new UnsupportedOperationException("CreditCourseProposalAssember does not support assembly from source type");
	}

	@Override
	public Void disassemble(CreditCourseProposal input)
			throws AssemblyException {
		throw new UnsupportedOperationException("CreditCourseProposalAssember does not support disassembly to source type");
	}

	public LuService getLuService() {
		return luService;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}


}
