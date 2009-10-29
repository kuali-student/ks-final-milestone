package org.kuali.student.lum.lu.assembly;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.AssemblyException;
import org.kuali.student.common.assembly.Data;
import org.kuali.student.common.assembly.Metadata;
import org.kuali.student.common.assembly.Data.Property;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.AlreadyExistsException;
import org.kuali.student.core.exceptions.DataValidationErrorException;
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
import org.kuali.student.lum.lu.assembly.data.client.atp.TimeAmountInfoData;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourse;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseActivity;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseFormat;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseProposal;
import org.kuali.student.lum.lu.assembly.data.client.proposal.ProposalInfoData;
import org.kuali.student.lum.lu.dto.AdminOrgInfo;
import org.kuali.student.lum.lu.dto.CluInfo;
import org.kuali.student.lum.lu.service.LuService;

public class CreditCourseProposalAssembler implements Assembler {
	// TODO verify that the right relation types have been used
	// public static final String FORMAT_RELATION_TYPE =
	// LUConstants.LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT;
	// public static final String ACTIVITY_RELATION_TYPE =
	// LUConstants.LU_LU_RELATION_TYPE_CONTAINS;
	public static final String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
	public static final String FORMAT_LU_TYPE = "kuali.lu.type.CreditCourseFormatShell";
	public static final String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";
	public static final String PROPOSAL_REFERENCE_TYPE = "kuali.referenceType.CLU";
	private LuService luService;
	private AtpService atpService;
	private ProposalService proposalService;

	@Override
	public void chain(Assembler assembler) throws AssemblyException {
		// TODO Auto-generated method stub

	}

	@Override
	public Data createNew(String type, String state) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data get(String id) throws AssemblyException {
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
		CluInfo course = luService.getClu(cluId);

		if (course == null) {
			throw new AssemblyException("Unable to retrieve course for id: "
					+ cluId);
		}
		result.setId(cluId);
		result.setCourseNumberSuffix(course.getOfficialIdentifier()
				.getSuffixCode());
		result.setCourseTitle(course.getOfficialIdentifier().getLongName());

		AdminOrgInfo admin = course.getPrimaryAdminOrg();
		if (admin != null) {
			result.setDepartment(admin.getOrgId());
		}

		result.setDescription(getRichTextInfo(course.getDesc()));
		result.setDuration(getTimeAmountInfo(course.getIntensity()));
		result.setState(course.getState());
		result.setSubjectArea(course.getOfficialIdentifier().getDivision());
		result
				.setTranscriptTitle(course.getOfficialIdentifier()
						.getShortName());
		result.setType(course.getType());

		addFormats(result);
		return result;
	}

	private void addFormats(CreditCourse course) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, AssemblyException {
		List<CluInfo> formats = luService.getRelatedClusByCluId(course.getId(),
				FORMAT_RELATION_TYPE);
		for (CluInfo clu : formats) {
			CreditCourseFormat format = new CreditCourseFormat();

			format.setId(clu.getId());
			format.setState(clu.getState());
			addActivities(format);

			course.addFormat(format);
		}
	}

	private void addActivities(CreditCourseFormat format)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		List<CluInfo> activities = luService.getRelatedClusByCluId(format
				.getId(), ACTIVITY_RELATION_TYPE);
		for (CluInfo clu : activities) {
			CreditCourseActivity activity = new CreditCourseActivity();

			activity.setId(clu.getId());
			activity.setActivityType(clu.getType());
			activity.setIntensity(getTimeAmountInfo(clu.getIntensity()));
			activity.setState(clu.getState());

			format.addActivity(activity);
		}
	}

	private TimeAmountInfoData getTimeAmountInfo(TimeAmountInfo intensity) {
		TimeAmountInfoData result = null;
		if (intensity != null) {
			result = new TimeAmountInfoData();
			result.setAtpDurationTypeKey(intensity.getAtpDurationTypeKey());
			result.setTimeQuantity(intensity.getTimeQuantity());
		}
		return result;
	}

	private String getRichTextInfo(RichTextInfo rti) {
		if (rti == null) {
			return null;
		} else {
			return rti.getFormatted();
		}
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
	public Metadata getMetadata(String type, String state)
			throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Data, List<ValidationResultInfo>> save(Data data)
			throws AssemblyException {
		// TODO change this method sig to something other than Map? there won't
		// be multiples, just a single pair.
		try {
			Map<Data, List<ValidationResultInfo>> result = new HashMap<Data, List<ValidationResultInfo>>();
			List<ValidationResultInfo> validationResults = validate(data);
			if (validationFailed(validationResults)) {
				result.put(data, validationResults);
				return result;
			}

			CreditCourseProposal resultData = new CreditCourseProposal();

			CreditCourseProposal root = (CreditCourseProposal) data;
			// first save all of the clus and relations
			CreditCourse inputCourse = root.getCourse();
			CreditCourse resultCourse = saveCourse(inputCourse);

			// make sure that the proposal's reference info is properly set
			ProposalInfoData inputProposal = root.getProposal();
			inputProposal.setReferenceType(PROPOSAL_REFERENCE_TYPE);
			Data references = inputProposal.getReferences();
			if (!references.containsValue(new Data.StringValue(resultCourse
					.getId()))) {
				references.add(resultCourse.getId());
			}

			ProposalInfoData resultProposal = saveProposal(inputProposal);

			resultData.setCourse(resultCourse);
			resultData.setProposal(resultProposal);

			result.put(resultData, validationResults);
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

	private ProposalInfoData saveProposal(ProposalInfoData inputProposal) {
		// TODO Auto-generated method stub
		return null;
	}

	private CreditCourse saveCourse(CreditCourse inputCourse)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			DataValidationErrorException, PermissionDeniedException,
			VersionMismatchException, AlreadyExistsException {
		CreditCourse result = null;
		CluInfo courseClu = null;
		if (inputCourse.isCreated() || inputCourse.isUpdated()) {
			// convert the input CreditCourse to a CluInfo, apply defaults
			courseClu = convertCourse(inputCourse);
			if (inputCourse.isCreated()) {
				courseClu = luService.createClu(inputCourse.getType(),
						courseClu);
			} else {
				courseClu = luService.updateClu(courseClu.getId(), courseClu);
			}
		} else {
			// note, not checking isDeleted, since the course itself can't be
			// deleted, right?
			// get the latest from the service
			courseClu = luService.getClu(inputCourse.getId());
		}

		result = convertCluToCreditCourse(courseClu);

		for (Property p : inputCourse.getFormats()) {
			CreditCourseFormat inputFormat = p.getValue();
			CreditCourseFormat resultFormat = saveFormat(result, inputFormat);
			result.addFormat(resultFormat);
		}
		return result;
	}

	private CreditCourseFormat saveFormat(CreditCourse result,
			CreditCourseFormat inputFormat) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, DataValidationErrorException,
			PermissionDeniedException, VersionMismatchException,
			AlreadyExistsException {

		CreditCourseFormat resultFormat = null;
		CluInfo formatClu = null;

		if (inputFormat.isCreated() || inputFormat.isUpdated()) {
			// convert the input CreditCourseFormat to a CluInfo, apply defaults
			formatClu = convertCourseFormat(inputFormat);
			if (inputFormat.isCreated()) {
				formatClu = luService.createClu(FORMAT_LU_TYPE, formatClu);
				// TODO 2nd pass at saving the relation, just stubbing out for
				// now
				// luService.createCluCluRelation(result.getId(),
				// formatClu.getId(), FORMAT_RELATION_TYPE, cluCluRelationInfo)
				saveCluCluRelation(result.getId(), formatClu,
						FORMAT_RELATION_TYPE);
			} else {
				formatClu = luService.updateClu(formatClu.getId(), formatClu);
			}
		} else if (inputFormat.isDeleted()) {
			deleteFormat(inputFormat.getId());
			return null;
		} else {
			// get the latest from the service
			formatClu = luService.getClu(inputFormat.getId());
		}

		resultFormat = convertCluToCreditCourseFormat(formatClu);

		for (Property p : inputFormat.getActivities()) {
			CreditCourseActivity inputActivity = p.getValue();
			CreditCourseActivity resultActivity = saveActivity(resultFormat,
					inputActivity);
			if (resultActivity != null) {
				resultFormat.addActivity(resultActivity);
			}
		}

		return resultFormat;
	}

	private CreditCourseActivity saveActivity(CreditCourseFormat resultFormat,
			CreditCourseActivity inputActivity) throws AlreadyExistsException,
			DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		CreditCourseActivity resultActivity = null;
		CluInfo activityClu = null;

		if (inputActivity.isCreated() || inputActivity.isUpdated()) {
			// convert the input CreditCourseFormat to a CluInfo, apply defaults
			activityClu = convertCourseActivity(inputActivity);
			if (inputActivity.isCreated()) {
				activityClu = luService.createClu(inputActivity
						.getActivityType(), activityClu);
				saveCluCluRelation(resultFormat.getId(), activityClu,
						ACTIVITY_RELATION_TYPE);
			} else {
				activityClu = luService.updateClu(activityClu.getId(),
						activityClu);
			}
		} else if (inputActivity.isDeleted()) {
			deleteActivity(inputActivity.getId());
			return null;
		} else {
			// get the latest from the service
			activityClu = luService.getClu(inputActivity.getId());
		}

		resultActivity = convertCluToCreditCourseActivity(activityClu);

		return resultActivity;
	}

	private CluInfo convertCourse(CreditCourse inputCourse) {
		// TODO convert the input CreditCourse to a CluInfo, apply defaults
		return null;
	}

	private CreditCourseActivity convertCluToCreditCourseActivity(
			CluInfo activityClu) {
		// TODO refactor assembly code to reuse same conversion code that is
		// used for retrieval
		return null;
	}

	private void saveCluCluRelation(String parentId, CluInfo childClu,
			String relationType) {
		// TODO save cluclurelation

	}

	private CluInfo convertCourseActivity(CreditCourseActivity inputActivity) {
		// TODO convert the input CreditCourseFormat to a CluInfo, apply
		// defaults
		return null;
	}

	private CreditCourseFormat convertCluToCreditCourseFormat(CluInfo formatClu) {
		// TODO refactor assembly code to reuse same conversion code that is
		// used for retrieval
		return null;
	}

	private void deleteFormat(String formatId) {
		// TODO recursively delete activities, the format, and all clu-clu
		// relations
	}

	private void deleteActivity(String activityId) {
		// TODO recursively delete the activity and all clu-clu relations
	}

	private CluInfo convertCourseFormat(CreditCourseFormat inputFormat) {
		// TODO convert the input CreditCourseFormat to a CluInfo, apply
		// defaults
		return null;
	}

	private CreditCourse convertCluToCreditCourse(CluInfo courseClu) {
		// TODO refactor the assembly code above that populates the Data object,
		// so that it can be reused here
		return null;
	}

	@Override
	public Data transform(Data data) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validate(Data data)
			throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setLuService(LuService luService) {
		this.luService = luService;
	}

	public void setAtpService(AtpService atpService) {
		this.atpService = atpService;
	}

	public void setProposalService(ProposalService proposalService) {
		this.proposalService = proposalService;
	}

}
