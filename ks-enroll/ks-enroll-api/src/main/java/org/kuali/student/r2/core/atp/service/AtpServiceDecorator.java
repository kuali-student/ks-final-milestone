package org.kuali.student.r2.core.atp.service;

import java.util.Date;
import java.util.List;

import org.kuali.student.r2.common.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StateInfo;
import org.kuali.student.r2.common.dto.StateProcessInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.TypeInfo;
import org.kuali.student.r2.common.dto.TypeTypeRelationInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.dto.AtpMilestoneRelationInfo;
import org.kuali.student.r2.core.atp.dto.MilestoneInfo;


public class AtpServiceDecorator implements AtpService {
	protected AtpService nextDecorator;

	public AtpService getNextDecorator() {
		return nextDecorator;
	}

	public void setNextDecorator(AtpService nextDecorator) {
		this.nextDecorator = nextDecorator;
	}
	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
	throws OperationFailedException,
	MissingParameterException,
	PermissionDeniedException {

		return this.nextDecorator.getDataDictionaryEntryKeys(context);
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context)
	throws OperationFailedException,
	MissingParameterException,
	PermissionDeniedException,
	DoesNotExistException {

		return this.nextDecorator.getDataDictionaryEntry(entryKey,context);
	}

	@Override
	public TypeInfo getType(String typeKey, ContextInfo context)
	throws DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException {

		return this.nextDecorator.getType(typeKey,context);
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException {
		return this.nextDecorator.getTypesByRefObjectURI(refObjectURI,context);
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException {
		return this.nextDecorator.getAllowedTypesForType(ownerTypeKey,relatedRefObjectURI, context);
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException {
		return this.nextDecorator.getTypeRelationsByOwnerType(ownerTypeKey,relationTypeKey, context);
	}

	@Override
	public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    return nextDecorator.getProcessByKey(processKey, context);
	}
	
	@Override
	public List<String> getProcessByObjectType(String refObjectUri, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    return nextDecorator.getProcessByObjectType(refObjectUri, context);
	}

	@Override
	public StateInfo getState(String processKey, String stateKey,
			ContextInfo context)
	throws DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException {
		return this.nextDecorator.getState(processKey,stateKey,context);
	}

	@Override
	public List<StateInfo> getStatesByProcess(String processKey,
			ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException {
		return this.nextDecorator.getStatesByProcess(processKey,context);
	}

	@Override
	public List<StateInfo> getInitialValidStates(String processKey,
			ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException {
		return this.nextDecorator.getInitialValidStates(processKey,context);
	}

	@Override
	public StateInfo getNextHappyState(String processKey,
			String currentStateKey, ContextInfo context)
	throws DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException {
		return this.nextDecorator.getNextHappyState(processKey,currentStateKey, context);
	}

	@Override
	public AtpInfo getAtp(
			String atpKey, ContextInfo context)
	throws DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException {
		return this.nextDecorator.getAtp(atpKey, context);
	}

	@Override
	public List<AtpInfo> getAtpsByDate(
			Date searchDate, ContextInfo context)
			throws InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getAtpsByDate(searchDate, context);
	}

	@Override
	public List<AtpInfo> getAtpsByDates(
			Date startDate, Date endDate, ContextInfo context)
			throws InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getAtpsByDates(startDate,endDate, context);
	}

	@Override
	public List<AtpInfo> getAtpsByKeyList(
			List<String> atpKeyList, ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getAtpsByKeyList(atpKeyList, context);
	}

	@Override
	public List<String> getAtpKeysByType(String atpTypeKey, ContextInfo context)
	throws InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException {
		return this.nextDecorator.getAtpKeysByType(atpTypeKey, context);
	}

	@Override
	public MilestoneInfo getMilestone(
			String milestoneKey, ContextInfo context)
	throws DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException {
		return this.nextDecorator.getMilestone(milestoneKey, context);
	}

	@Override
	public List<MilestoneInfo> getMilestonesByKeyList(
			List<String> milestoneKeyList, ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getMilestonesByKeyList(milestoneKeyList, context);
	}

	@Override
	public List<String> getMilestoneKeysByType(String milestoneTypeKey,
			ContextInfo context)
			throws InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getMilestoneKeysByType(milestoneTypeKey, context);
	}

	@Override
	public List<MilestoneInfo> getMilestonesByAtp(
			String atpKey, ContextInfo context)
			throws InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getMilestonesByAtp(atpKey, context);
	}

	@Override
	public List<MilestoneInfo> getMilestonesByDates(
			Date startDate, Date endDate, ContextInfo context)
			throws InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getMilestonesByDates(startDate, endDate, context);
	}

	@Override
	public List<MilestoneInfo> getMilestonesByDatesAndType(
			String milestoneTypeKey, Date startDate, Date endDate,
			ContextInfo context)
			throws InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getMilestonesByDatesAndType(milestoneTypeKey, startDate, endDate, context);
	}

	@Override
	public List<ValidationResultInfo> validateAtp(
			String validationType,
			AtpInfo atpInfo,
			ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException {
		return this.nextDecorator.validateAtp(validationType, atpInfo, context);
	}

	@Override
	public AtpInfo createAtp(
			String atpKey,
			AtpInfo atpInfo,
			ContextInfo context)
	throws AlreadyExistsException,
	DataValidationErrorException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException {
		return this.nextDecorator.createAtp(atpKey ,atpInfo,  context);
	}

	@Override
	public AtpInfo updateAtp(
			String atpKey,
			AtpInfo atpInfo,
			ContextInfo context)
	throws DataValidationErrorException,
	DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException,
	VersionMismatchException {
		return this.nextDecorator.updateAtp(atpKey ,atpInfo,  context);
	}

	@Override
	public  StatusInfo deleteAtp(String atpKey,ContextInfo context) throws DoesNotExistException,InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		return this.nextDecorator.deleteAtp(atpKey,context);
	}

	@Override
	public List<ValidationResultInfo> validateMilestone(
			String validationType,
			MilestoneInfo milestoneInfo,
			ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException {
		return this.nextDecorator.validateMilestone(validationType,milestoneInfo,context);
	}

	@Override
	public MilestoneInfo createMilestone(
			String milestoneKey,
			MilestoneInfo milestoneInfo,
			ContextInfo context)
	throws AlreadyExistsException,
	DataValidationErrorException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException {
		return this.nextDecorator.createMilestone(milestoneKey,milestoneInfo,context);
	}

	@Override
	public 	MilestoneInfo updateMilestone(
			String milestoneKey,
			MilestoneInfo milestoneInfo,
			ContextInfo context)
	throws DataValidationErrorException,
	DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException,
	VersionMismatchException {
		return this.nextDecorator.updateMilestone(milestoneKey,milestoneInfo,context);
	}

	@Override
	public StatusInfo deleteMilestone(
			String milestoneKey, ContextInfo context)
	throws DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException {
		return this.nextDecorator.deleteMilestone(milestoneKey, context);
	}

    @Override
    public AtpAtpRelationInfo getAtpAtpRelation(String atpAtpRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return this.nextDecorator.getAtpAtpRelation(atpAtpRelationId, context);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByIdList(List<String> atpAtpRelationIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return this.nextDecorator.getAtpAtpRelationsByIdList(atpAtpRelationIdList, context);
    }

    @Override
    public List<String> getAtpAtpRelationIdsByType(String atpAtpRelationTypeKey, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return this.nextDecorator.getAtpAtpRelationIdsByType(atpAtpRelationTypeKey, context);
    }

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtp(String atpKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return this.nextDecorator.getAtpAtpRelationsByAtp(atpKey, context);
    }

    @Override
    public List<ValidationResultInfo> validateAtpAtpRelation(String validationType, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	return this.nextDecorator.validateAtpAtpRelation(validationType, atpAtpRelationInfo, context);
    }

    @Override
    public AtpAtpRelationInfo createAtpAtpRelation(AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context) throws AlreadyExistsException,DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return this.nextDecorator.createAtpAtpRelation(atpAtpRelationInfo, context);
    }

    @Override
    public AtpAtpRelationInfo updateAtpAtpRelation(String atpAtpRelationId, AtpAtpRelationInfo atpAtpRelationInfo, ContextInfo context) throws DataValidationErrorException, DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, VersionMismatchException {
	return this.nextDecorator.updateAtpAtpRelation(atpAtpRelationId, atpAtpRelationInfo, context);
    }

    @Override
    public StatusInfo deleteAtpAtpRelation(String atpAtpRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return this.nextDecorator.deleteAtpAtpRelation(atpAtpRelationId, context);
    }

	@Override
	public AtpMilestoneRelationInfo getAtpMilestoneRelation(
			String atpMilestoneRelationId, ContextInfo context)
	throws DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException {
		return this.nextDecorator.getAtpMilestoneRelation(atpMilestoneRelationId, context);
	}

	@Override
	public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByIdList(
			List<String> atpMilestoneRelationIdList, ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getAtpMilestoneRelationsByIdList(atpMilestoneRelationIdList, context);
	}

	@Override
	public List<String> getAtpMilestoneRelationIdsByType(
			String atpMilestoneRelationTypeKey, ContextInfo context)
			throws InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getAtpMilestoneRelationIdsByType(atpMilestoneRelationTypeKey, context);
	}

	@Override
	public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByAtp(
			String atpKey, ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getAtpMilestoneRelationsByAtp(atpKey, context);
	}

	@Override
	public List<AtpMilestoneRelationInfo> getAtpMilestoneRelationsByMilestone(
			String milestoneKey, ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		return this.nextDecorator.getAtpMilestoneRelationsByAtp(milestoneKey, context);
	}

	@Override
	public List<ValidationResultInfo> validateAtpMilestoneRelation(
			String validationType,
			AtpMilestoneRelationInfo atpMilestoneRelationInfo,
			ContextInfo context)
			throws DoesNotExistException,
			InvalidParameterException,
			MissingParameterException,
			OperationFailedException {
		return this.nextDecorator.validateAtpMilestoneRelation(validationType, atpMilestoneRelationInfo, context);
	}

	@Override
	public AtpMilestoneRelationInfo createAtpMilestoneRelation(
			AtpMilestoneRelationInfo atpMilestoneRelationInfo,
			ContextInfo context)
	throws AlreadyExistsException,
	DataValidationErrorException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException {
		return this.nextDecorator.createAtpMilestoneRelation(atpMilestoneRelationInfo, context);
	}

	@Override
	public AtpMilestoneRelationInfo updateAtpMilestoneRelation(
			String atpMilestoneRelationId,
			AtpMilestoneRelationInfo atpMilestoneRelationInfo,
			ContextInfo context)
	throws DataValidationErrorException,
	DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException,
	VersionMismatchException {
		return this.nextDecorator.updateAtpMilestoneRelation(atpMilestoneRelationId, atpMilestoneRelationInfo, context);
	}

	@Override
	public StatusInfo deleteAtpMilestoneRelation(
			String atpMilestoneRelationId, ContextInfo context)
	throws DoesNotExistException,
	InvalidParameterException,
	MissingParameterException,
	OperationFailedException,
	PermissionDeniedException {
		return this.nextDecorator.deleteAtpMilestoneRelation(atpMilestoneRelationId, context);
	}

    @Override
    public List<AtpAtpRelationInfo> getAtpAtpRelationsByAtpAndRelationType(String atpKey, String relationType,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
	return this.nextDecorator.getAtpAtpRelationsByAtpAndRelationType(atpKey, relationType, context);
    }
}
