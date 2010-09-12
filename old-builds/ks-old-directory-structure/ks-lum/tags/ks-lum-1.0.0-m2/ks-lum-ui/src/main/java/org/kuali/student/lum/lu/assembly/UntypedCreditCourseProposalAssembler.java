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
import org.kuali.student.lum.lu.assembly.data.client.CluInstructorInfoData;
import org.kuali.student.lum.lu.assembly.data.client.RichTextInfoData;
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

/**
 * This assembler exists because it was easier than converting the existing assembler to work against the abstract form rather than the specific interfaces (CreditCourseProposal, etc)
 * 
 * @author wilj
 *
 */
public class UntypedCreditCourseProposalAssembler implements Assembler<Data, CreditCourseProposal> {
	private final CreditCourseProposalAssembler assembler;
	
	public UntypedCreditCourseProposalAssembler(String proposalState) {
		this.assembler = new CreditCourseProposalAssembler(proposalState);
	}
	
	@Override
	public Data get(String id) throws AssemblyException {
		return assemble(assembler.get(id));
	}

	@Override
	public Metadata getMetadata() throws AssemblyException {
		return assembler.getMetadata();
	}

	@Override
	public SaveResult<Data> save(Data input) throws AssemblyException {
		SaveResult<CreditCourseProposal> s = assembler.save(disassemble(input));
		SaveResult<Data> result = new SaveResult<Data>();
		result.setValidationResults(s.getValidationResults());
		result.setValue(assemble(s.getValue()));
		return result;
	}

	@Override
	public List<ValidationResultInfo> validate(Data input)
			throws AssemblyException {
		return assembler.validate(disassemble(input));
	}
	
	@Override
	public Data assemble(CreditCourseProposal input) throws AssemblyException {
		// no conversion to do, it's already a subclass of Data
		return input;
	}

	@Override
	public CreditCourseProposal disassemble(Data input)
			throws AssemblyException {
		if (input == null) {
			return null;
		}
		CreditCourseProposal result = new CreditCourseProposal();
		input.copy(result, false);
		
		ProposalInfoData prop = copyIfExists(result, new ProposalInfoData(), CreditCourseProposal.Properties.PROPOSAL.getKey());
		
		CreditCourse course = copyIfExists(result, new CreditCourse(), CreditCourseProposal.Properties.COURSE.getKey());
		if (course != null) {
			copyIfExists(course, new TimeAmountInfoData(), CreditCourse.Properties.DURATION.getKey());
			copyIfExists(course, new RichTextInfoData(), CreditCourse.Properties.DESCRIPTION.getKey());
			copyIfExists(course, new RichTextInfoData(), CreditCourse.Properties.RATIONALE.getKey());
			copyIfExists(course, new CluInstructorInfoData(), CreditCourse.Properties.PRIMARY_INSTRUCTOR.getKey());
			
			Data identifiers = course.getAlternateIdentifiers();
			if (identifiers != null) {
				Data convertedIdentifiers = new Data();
				for (Property identProp : identifiers) {
					Data identData = identProp.getValue();
					CluIdentifierInfoData ident = (CluIdentifierInfoData) identData.copy(new CluIdentifierInfoData(), false);
					convertedIdentifiers.add(ident);
				}
				course.set(CreditCourse.Properties.ALTERNATE_IDENTIFIERS.getKey(), convertedIdentifiers);
			}
			
			Data formats = course.get(CreditCourse.Properties.FORMATS.getKey());
			if (formats != null) {
				Data convertedFormats = new Data();
				course.set(CreditCourse.Properties.FORMATS.getKey(), (Data) null);
				for (Property formatProp : formats) {
					Data formatData = formatProp.getValue();
					CreditCourseFormat format = (CreditCourseFormat) formatData.copy(new CreditCourseFormat(), false);
					convertedFormats.add(format);
					
					Data activities = format.get(CreditCourseFormat.Properties.ACTIVITIES.getKey());
					if (activities != null) {
						Data convertedActivities = new Data();
						for (Property activityProp : activities) {
							Data activityData = activityProp.getValue();
							CreditCourseActivity activity = (CreditCourseActivity) activityData.copy(new CreditCourseActivity(), false);
							copyIfExists(activity, new TimeAmountInfoData(), CreditCourseActivity.Properties.INTENSITY.getKey());
							convertedActivities.add(activity);
						}
						format.set(CreditCourseFormat.Properties.ACTIVITIES.getKey(), convertedActivities);
					}
				}
				course.set(CreditCourse.Properties.FORMATS.getKey(), convertedFormats);
			}
		}
		return result;
	}

	private <T extends Data> T copyIfExists(Data source, T target, String key) {
		Data d = source.get(key);
		if (d == null) {
			return null;
		} else {
			d.copy(target, false);
			source.set(key, target);
			return target;
		}
	}

	public void setProposalService(ProposalService proposalService) {
		assembler.setProposalService(proposalService);
	}

	public void setLuService(LuService luService) {
		assembler.setLuService(luService);
	}





}
