package org.kuali.student.lum.lu.assembly;

import java.util.List;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.AssemblyException;
import org.kuali.student.common.assembly.Data;
import org.kuali.student.common.assembly.Metadata;
import org.kuali.student.common.assembly.SaveResult;
import org.kuali.student.common.assembly.Data.Property;
import org.kuali.student.core.atp.service.AtpService;
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

public class CreditCourseProposalAssembler implements Assembler<CreditCourseProposal, Void> {
	// TODO verify that the right relation types have been used
	// public static final String FORMAT_RELATION_TYPE =
	// LUConstants.LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT;
	// public static final String ACTIVITY_RELATION_TYPE =
	// LUConstants.LU_LU_RELATION_TYPE_CONTAINS;
	public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
	public static final String FORMAT_LU_TYPE = "kuali.lu.type.CreditCourseFormatShell";
	public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";
	public static final String PROPOSAL_REFERENCE_TYPE = "kuali.referenceType.CLU";
	private final String proposalState;
	private CluInfoHierarchyAssembler cluHierarchyAssembler;
	private final RichTextInfoAssembler richtextAssembler = new RichTextInfoAssembler();
	private final TimeAmountInfoAssembler timeamountAssembler = new TimeAmountInfoAssembler();
	private AtpService atpService;
	private ProposalService proposalService;
	
	public CreditCourseProposalAssembler(String proposalState) {
		this.proposalState = proposalState;
	}
	
	@Override
	public CreditCourseProposal get(String id) throws AssemblyException {
		CreditCourseProposal result = null;

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
			RelationshipHierarchy formats = new RelationshipHierarchy(FORMAT_RELATION_TYPE, proposalState);
			RelationshipHierarchy activities = new RelationshipHierarchy(ACTIVITY_RELATION_TYPE, proposalState);
			
			course.addChild(formats);
			formats.addChild(activities);
			
			cluHierarchyAssembler = new CluInfoHierarchyAssembler();
			cluHierarchyAssembler.setHierarchy(course);
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

		AdminOrgInfo admin = course.getPrimaryAdminOrg();
		if (admin != null) {
			result.setDepartment(admin.getOrgId());
		}

		result.setDescription(richtextAssembler.assemble(course.getDesc()));
		result.setDuration(timeamountAssembler.assemble(course.getIntensity()));
		result.setState(course.getState());
		result.setSubjectArea(course.getOfficialIdentifier().getDivision());
		result.setTranscriptTitle(course.getOfficialIdentifier().getShortName());
		result.setType(course.getType());
		result.setVersionIndicator(course.getMetaInfo().getVersionInd());

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
		format.setVersionIndicator(clu.getMetaInfo().getVersionInd());
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
		activity.setVersionIndicator(clu.getMetaInfo().getVersionInd());

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
		result.setVersionIndicator(prop.getMetaInfo().getVersionInd());

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
			if (validationFailed(validationResults)) {
				result.setValidationResults(validationResults);
				result.setValue(data);
				return result;
			}


			CreditCourseProposal root = (CreditCourseProposal) data;
			// first save all of the clus and relations
			saveCourse(root.getCourse());

			// make sure that the proposal's reference info is properly set
			ProposalInfoData inputProposal = root.getProposal();
			inputProposal.setReferenceType(PROPOSAL_REFERENCE_TYPE);
			Data references = inputProposal.getReferences();
			if (!references.containsValue(new Data.StringValue(data.getCourse().getId()))) {
				references.add(data.getCourse().getId());
			}

			saveProposal(inputProposal);


			result.setValidationResults(validationResults);
			result.setValue(get(data.getProposal().getId()));
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

	private void saveProposal(ProposalInfoData inputProposal) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, DependentObjectsExistException, PermissionDeniedException, AlreadyExistsException, DataValidationErrorException, VersionMismatchException {
		if (inputProposal.isDeleted()) {
			proposalService.deleteProposal(inputProposal.getId());
		} else if (inputProposal.isModified()) {
			ProposalInfo prop = null;
			if (inputProposal.isCreated()) {
				prop = new ProposalInfo();
			} else {
				prop = proposalService.getProposal(inputProposal.getId());
			}

			prop.setRationale(inputProposal.getRationale());
			prop.setState(inputProposal.getState());
			prop.setName(inputProposal.getTitle());
			prop.setType(inputProposal.getType());
			prop.setProposalReferenceType(inputProposal.getReferenceType());
			for (Property p : inputProposal.getReferences()) {
				String ref = p.getValue();
				if (!prop.getProposalReference().contains(ref)) {
					prop.getProposalReference().add(ref);
				}
			}
			if (prop.getMetaInfo() != null) {
				prop.getMetaInfo().setVersionInd(inputProposal.getVersionIndicator());
			}
			
			if (inputProposal.isCreated()) {
				proposalService.createProposal(prop.getType(), prop);
			} else {
				proposalService.updateProposal(prop.getId(), prop);
			}
		}
	}

	private void saveCourse(CreditCourse course) throws AssemblyException {
		if (course == null) {
			throw new AssemblyException("Cannot save proposal without course");
		}
		CluInfoHierarchy clus = buildCluInfoHiearchy(course);
		cluHierarchyAssembler.save(clus);
	}


	
	private CluInfoHierarchy buildCluInfoHiearchy(CreditCourse course) throws AssemblyException {
		CluInfoHierarchy result = null;
		CluInfo courseClu = null;
		if (course.isCreated()) {
			result = new CluInfoHierarchy();
			courseClu = new CluInfo();
			result.setCluInfo(courseClu);
		} else {
			result = cluHierarchyAssembler.get(course.getId());
			courseClu = result.getCluInfo();
		}
		
		if (course.isModified()) {
			if (course.isCreated()) {
				result.setModificationState(ModificationState.CREATED);
			} else if (course.isUpdated()) {
				result.setModificationState(ModificationState.UPDATED);
			} else if (course.isDeleted()) {
				result.setModificationState(ModificationState.DELETED);
			} 
			
			CluIdentifierInfo cluId = new CluIdentifierInfo();
			cluId.setSuffixCode(course.getCourseNumberSuffix());
			cluId.setLongName(course.getCourseTitle());
			cluId.setDivision(course.getSubjectArea());
			cluId.setShortName(course.getTranscriptTitle());
			courseClu.setOfficialIdentifier(cluId);
			
			AdminOrgInfo admin = new AdminOrgInfo();
			admin.setOrgId(course.getDepartment());
			courseClu.setPrimaryAdminOrg(admin);
			
			courseClu.setDesc(richtextAssembler.disassemble(course.getDescription()));
			courseClu.setIntensity(timeamountAssembler.disassemble(course.getDuration()));
	
			// TODO should probably set these via defaults rather than requiring the client to provide them
			courseClu.setState(course.getState());
			courseClu.setType(course.getType());
			
			if (courseClu.getMetaInfo() != null) {
				courseClu.getMetaInfo().setVersionInd(course.getVersionIndicator());
			}
		}
		
		buildFormatUpdates(result, course);
		
		return result;
	}

	private void buildFormatUpdates(CluInfoHierarchy courseHierarchy, CreditCourse course) throws AssemblyException {
		for (Property p : course.getFormats()) {
			CreditCourseFormat format = p.getValue();
			CluInfoHierarchy formatHierarchy = null;
			CluInfo formatClu = null;
			
			if (format.isCreated()) {
				formatHierarchy = new CluInfoHierarchy();
				formatClu = new CluInfo();
				courseHierarchy.getChildren().add(formatHierarchy);
			} else {
				formatHierarchy = findChildByCluId(courseHierarchy, format.getId());
				formatClu = formatHierarchy.getCluInfo();
			}
			
			if (format.isModified()) {
				if (format.isCreated()) {
					formatHierarchy.setModificationState(ModificationState.CREATED);
				} else if (format.isUpdated()) {
					formatHierarchy.setModificationState(ModificationState.UPDATED);
				} else if (format.isDeleted()) {
					formatHierarchy.setModificationState(ModificationState.DELETED);
				} 
				
				formatClu.setState(format.getState());
				if (formatClu.getMetaInfo() != null) {
					formatClu.getMetaInfo().setVersionInd(format.getVersionIndicator());
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
			
			if (activity.isCreated()) {
				activityHierarchy = new CluInfoHierarchy();
				activityClu = new CluInfo();
				formatHierarchy.getChildren().add(formatHierarchy);
			} else {
				activityHierarchy = findChildByCluId(formatHierarchy, activity.getId());
				activityClu = formatHierarchy.getCluInfo();
			}
			
			if (activity.isModified()) {
				if (activity.isCreated()) {
					activityHierarchy.setModificationState(ModificationState.CREATED);
				} else if (activity.isUpdated()) {
					activityHierarchy.setModificationState(ModificationState.UPDATED);
				} else if (activity.isDeleted()) {
					activityHierarchy.setModificationState(ModificationState.DELETED);
				} 

				activityClu.setType(activity.getActivityType());
				activityClu.setIntensity(timeamountAssembler.disassemble(activity.getIntensity()));
				activityClu.setState(activity.getState());
				
				if (activityClu.getMetaInfo() != null) {
					activityClu.getMetaInfo().setVersionInd(activity.getVersionIndicator());
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

	

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
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

}
