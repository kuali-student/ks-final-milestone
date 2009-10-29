package org.kuali.student.lum.lu.assembly;

import java.util.List;
import java.util.Map;

import org.kuali.student.common.assembly.Assembler;
import org.kuali.student.common.assembly.AssemblyException;
import org.kuali.student.common.assembly.Data;
import org.kuali.student.common.assembly.Metadata;
import org.kuali.student.core.atp.service.AtpService;
import org.kuali.student.core.dto.RichTextInfo;
import org.kuali.student.core.dto.TimeAmountInfo;
import org.kuali.student.core.exceptions.DoesNotExistException;
import org.kuali.student.core.exceptions.InvalidParameterException;
import org.kuali.student.core.exceptions.MissingParameterException;
import org.kuali.student.core.exceptions.OperationFailedException;
import org.kuali.student.core.proposal.dto.ProposalInfo;
import org.kuali.student.core.proposal.service.ProposalService;
import org.kuali.student.core.validation.dto.ValidationResultInfo;
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
//	public static final String FORMAT_RELATION_TYPE = LUConstants.LU_LU_RELATION_TYPE_HAS_COURSE_FORMAT;
//    public static final String ACTIVITY_RELATION_TYPE = LUConstants.LU_LU_RELATION_TYPE_CONTAINS;
	public final static String FORMAT_RELATION_TYPE = "luLuRelationType.hasCourseFormat";
    public final static String ACTIVITY_RELATION_TYPE = "luLuRelationType.contains";
    
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
			throw new AssemblyException("Could not assemble credit course proposal", e);
		}
		
		return result;
	}
	private CreditCourse getCourse(ProposalInfoData proposal) throws AssemblyException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		CreditCourse result = new CreditCourse();
		
		Data references = proposal.getReferences();
		if (references.size() != 1) {
			throw new AssemblyException("Invalid number of referenced courses for proposal: " + references.size());
		}
		String cluId = references.get(0);
		CluInfo course = luService.getClu(cluId);
		
		if (course == null) {
			throw new AssemblyException("Unable to retrieve course for id: " + cluId);
		}
		result.setId(cluId);
		result.setCourseNumberSuffix(course.getOfficialIdentifier().getSuffixCode());
		result.setCourseTitle(course.getOfficialIdentifier().getLongName());

		AdminOrgInfo admin = course.getPrimaryAdminOrg();
		if (admin != null) {
			result.setDepartment(admin.getOrgId());
		}
		
		result.setDescription(getRichTextInfo(course.getDesc()));
		result.setDuration(getTimeAmountInfo(course.getIntensity()));
		result.setState(course.getState());
		result.setSubjectArea(course.getOfficialIdentifier().getDivision());
		result.setTranscriptTitle(course.getOfficialIdentifier().getShortName());
		result.setType(course.getType());
		
		addFormats(result);
		return result;
	}
	
	private void addFormats(CreditCourse course) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, AssemblyException {
		List<CluInfo> formats = luService.getRelatedClusByCluId(course.getId(), FORMAT_RELATION_TYPE);
		for (CluInfo clu : formats) {
			CreditCourseFormat format = new CreditCourseFormat();
			
			format.setId(clu.getId());
			format.setState(clu.getState());
			addActivities(format);

			course.addFormat(format);
		}
	}


	private void addActivities(CreditCourseFormat format) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
		List<CluInfo> activities = luService.getRelatedClusByCluId(format.getId(), ACTIVITY_RELATION_TYPE);
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

	private ProposalInfoData getProposal(String id) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
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
		result.getProperties().put(CreditCourseProposal.Properties.TYPE.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourseProposal.Properties.STATE.getKey(), getMockMetadata(String.class));
		
		result.getProperties().put(CreditCourseProposal.Properties.PROPOSAL.getKey(), getProposalMetadata());
		result.getProperties().put(CreditCourseProposal.Properties.COURSE.getKey(), getCreditCourseMetadata());
		
		return result;
	}
	
	private Metadata getCreditCourseMetadata() {
		Metadata result = new Metadata();
		result.setDataType(CreditCourse.class.getName());
		
		// TODO read metadata from dictionary
		result.getProperties().put(CreditCourse.Properties.ID.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.TYPE.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.STATE.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.COURSE_NUMBER_SUFFIX.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.COURSE_TITLE.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.DEPARTMENT.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.DESCRIPTION.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourse.Properties.DURATION.getKey(), getMockMetadata(TimeAmountInfoData.class));
		result.getProperties().put(CreditCourse.Properties.SUBJECT_AREA.getKey(), getMockMetadata(String.class));
		// TODO verify if this is a useful "dataType" for Data objects acting as a list 
		result.getProperties().put(CreditCourse.Properties.TERMS_OFFERED.getKey(), getMockMetadata(Data.class));
		result.getProperties().put(CreditCourse.Properties.TRANSCRIPT_TITLE.getKey(), getMockMetadata(String.class));
		
		result.getProperties().put(CreditCourse.Properties.FORMATS.getKey(), getCreditCourseFormatMetadata());

		return result;
	}

	private Metadata getCreditCourseFormatMetadata() {
		Metadata result = new Metadata();
		result.setDataType(CreditCourseFormat.class.getName());
		
		// TODO read metadata from dictionary
		result.getProperties().put(CreditCourseFormat.Properties.ID.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourseFormat.Properties.STATE.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourseFormat.Properties.ACTIVITIES.getKey(),getCreditCourseActivityMetadata());

		return result;
	}

	private Metadata getCreditCourseActivityMetadata() {
		Metadata result = new Metadata();
		result.setDataType(CreditCourseActivity.class.getName());
		
		// TODO read metadata from dictionary
		result.getProperties().put(CreditCourseActivity.Properties.ID.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourseActivity.Properties.STATE.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourseActivity.Properties.ACTIVITY_TYPE.getKey(), getMockMetadata(String.class));
		result.getProperties().put(CreditCourseActivity.Properties.INTENSITY.getKey(), getMockMetadata(TimeAmountInfoData.class));
		
		return result;
	}

	private Metadata getProposalMetadata() {
		Metadata result = new Metadata();
		result.setDataType(ProposalInfoData.class.getName());
		
		// TODO read metadata from dictionary
		result.getProperties().put(ProposalInfoData.Properties.ID.getKey(), getMockMetadata(String.class));
		result.getProperties().put(ProposalInfoData.Properties.TYPE.getKey(), getMockMetadata(String.class));
		result.getProperties().put(ProposalInfoData.Properties.STATE.getKey(), getMockMetadata(String.class));
		result.getProperties().put(ProposalInfoData.Properties.RATIONALE.getKey(), getMockMetadata(String.class));
		result.getProperties().put(ProposalInfoData.Properties.REFERENCE_TYPE.getKey(), getMockMetadata(String.class));
		// TODO verify if this is a useful "dataType" for Data objects acting as a list 
		result.getProperties().put(ProposalInfoData.Properties.REFERENCES.getKey(), getMockMetadata(Data.class));
		result.getProperties().put(ProposalInfoData.Properties.TITLE.getKey(), getMockMetadata(String.class));
		
		return result;
	}

	private Metadata getMockMetadata(Class<?> dataType) {
		Metadata result = new Metadata();
		result.setDataType(dataType.getName());
		// TODO mock more metadata, or go ahead and implement dictionary lookup
		return result;
	}

	@Override
	public Metadata getMetadata(String type, String state) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Data, List<ValidationResultInfo>> save(Data data) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Data transform(Data data) throws AssemblyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ValidationResultInfo> validate(Data data) throws AssemblyException {
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
