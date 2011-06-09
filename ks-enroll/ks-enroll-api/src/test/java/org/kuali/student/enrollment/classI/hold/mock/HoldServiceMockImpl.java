package org.kuali.student.enrollment.classI.hold.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.enrollment.hold.dto.HoldInfo;
import org.kuali.student.enrollment.hold.dto.IssueInfo;
import org.kuali.student.enrollment.hold.dto.RestrictionInfo;
import org.kuali.student.enrollment.hold.service.HoldService;
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
import org.kuali.student.r2.common.util.constants.HoldServiceConstants;

public class HoldServiceMockImpl implements HoldService {

	private static Map<String, List<String>> issueRestrictionsMap = new HashMap<String, List<String>>();
	private static Map<String, HoldInfo> holdCache = new HashMap<String, HoldInfo>();
	private static Map<String, IssueInfo> issuesCache = new HashMap<String, IssueInfo>();
	private static Map<String, RestrictionInfo> restrictionsCache = new HashMap<String, RestrictionInfo>();

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    // TODO Kamal - THIS METHOD NEEDS JAVADOCS
	    return null;
	}
	
	@Override
	public List<String> getProcessByObjectType(String objectTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    // TODO Kamal - THIS METHOD NEEDS JAVADOCS
	    return null;
	}
	
	@Override
	public StateInfo getState(String processKey, String stateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateInfo> getStatesByProcess(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StateInfo> getInitialValidStates(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public StateInfo getNextHappyState(String processKey,
			String currentStateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TypeInfo getType(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean isPersonRestricted(String restrictionKey, String personId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		boolean personRestricted = false;
		List<HoldInfo> holds = getHoldsForPerson(personId, context);

		if (holds != null && holds.size() > 0) {
			List<String> allIssuesPerPerson = new ArrayList<String>();

			for (HoldInfo hold : holds) {
				allIssuesPerPerson.add(hold.getIssueId());
			}
			List<RestrictionInfo> allRestrictionsPerPerson = new ArrayList<RestrictionInfo>();

			for (String issueId : allIssuesPerPerson) {
				allRestrictionsPerPerson.addAll(getRestrictionsByIssue(issueId,
						context));
			}
			for (RestrictionInfo resInfo : allRestrictionsPerPerson) {
				if (resInfo.getKey().equals(restrictionKey)) {
					personRestricted = true;
				}
			}
		}
		return personRestricted;

	}

	@Override
	public List<String> getRestrictedPersons(String restrictionKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<IssueInfo> issues = getIssuesByRestriction(restrictionKey, context);
		List<String> peopleRestricted = new ArrayList<String>();
		for (IssueInfo issue : issues) {
			List<HoldInfo> holds = getHoldsByIssue(issue.getId(), context);
			for (HoldInfo hold : holds) {
				peopleRestricted.add(hold.getPersonId());
			}
		}

		return peopleRestricted;
	}

	@Override
	public List<HoldInfo> getHoldsByRestrictionForPerson(String restrictionKey,
			String personId, ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<HoldInfo> holdsForRestriction = new ArrayList<HoldInfo>();
		List<IssueInfo> issues = getIssuesByRestriction(restrictionKey, context);

		for (IssueInfo issue : issues) {
			holdsForRestriction.addAll(getHoldsByIssue(issue.getId(), context));
		}
		List<HoldInfo> holdsForPerson = getHoldsForPerson(personId, context);
		holdsForRestriction.retainAll(holdsForPerson);
		return holdsForRestriction;

	}

	@Override
	public List<HoldInfo> getActiveHoldsByRestrictionForPerson(
			String restrictionKey, String personId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<HoldInfo> holdsAll = getHoldsByRestrictionForPerson(
				restrictionKey, personId, context);
		List<HoldInfo> holdsActive = getActiveHoldsForPerson(personId, context);
		holdsAll.retainAll(holdsActive);
		return holdsAll;

	}

	@Override
	public HoldInfo getHold(String holdId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		return holdCache.get(holdId);
	}

	@Override
	public List<HoldInfo> getHoldsByIdList(List<String> holdIdList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<HoldInfo> holdsToReturn = new ArrayList<HoldInfo>();
		for (String holdId : holdIdList) {
			holdsToReturn.add(getHold(holdId, context));
		}
		return holdsToReturn;
	}

	@Override
	public List<HoldInfo> getHoldsByIssue(String issueId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<HoldInfo> allHold = new ArrayList<HoldInfo>();
		List<HoldInfo> holdsToReturn = new ArrayList<HoldInfo>();
		for (HoldInfo hold : allHold) {
			if (hold.getIssueId().equals(issueId)) {
				holdsToReturn.add(hold);
			}
		}

		return holdsToReturn;
	}

	@Override
	public List<HoldInfo> getHoldsForPerson(String personId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<HoldInfo> allHold = new ArrayList<HoldInfo>();
		List<HoldInfo> holdsToReturn = new ArrayList<HoldInfo>();
		for (HoldInfo hold : allHold) {
			if (hold.getPersonId().equals(personId)) {
				holdsToReturn.add(hold);
			}
		}

		return holdsToReturn;
	}

	@Override
	public List<HoldInfo> getActiveHoldsForPerson(String personId,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<HoldInfo> holdsForPerson = getHoldsForPerson(personId, context);
		List<HoldInfo> holdsActive = new ArrayList<HoldInfo>();
		for (HoldInfo hold : holdsForPerson) {
			if (hold.getStateKey().equals(
					HoldServiceConstants.HOLD_ACIVE_STATE_KEY)) {
				holdsActive.add(hold);
			}
		}

		return holdsActive;
	}

	@Override
	public List<HoldInfo> getHoldsByIssueForPerson(String issueId,
			String personId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<HoldInfo> holdForIssue = getHoldsByIssue(issueId, context);
		List<HoldInfo> holdForPerson = getHoldsForPerson(personId, context);
		holdForIssue.retainAll(holdForPerson);
		return holdForIssue;
	}

	@Override
	public List<HoldInfo> getActiveHoldsByIssueForPerson(String issueId,
			String personId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<HoldInfo> holdsForPersonByIssues = getHoldsByIssueForPerson(
				issueId, personId, context);
		List<HoldInfo> holdsActive = new ArrayList<HoldInfo>();
		for (HoldInfo hold : holdsForPersonByIssues) {
			if (hold.getStateKey().equals(
					HoldServiceConstants.HOLD_ACIVE_STATE_KEY)) {
				holdsActive.add(hold);
			}
		}
		return holdsActive;
	}

	@Override
	public List<ValidationResultInfo> validateHold(String validationTypeKey,
			HoldInfo holdInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HoldInfo createHold(HoldInfo holdInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		holdCache.put(holdInfo.getId(), holdInfo);
		return holdInfo;
	}

	@Override
	public HoldInfo updateHold(String holdId, HoldInfo holdInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException, VersionMismatchException {
		holdCache.put(holdId, holdInfo);
		return holdInfo;
	}

	@Override
	public HoldInfo releaseHold(String holdId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		HoldInfo hold = holdCache.get(holdId);
		hold.setStateKey(HoldServiceConstants.HOLD_RELEASED_STATE_KEY);
		hold.setReleasedDate(new Date());
		return hold;

	}

	@Override
	public StatusInfo deleteHold(String holdId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		holdCache.remove(holdId);
		return new StatusInfo();
	}

	@Override
	public IssueInfo getIssue(String issueId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return issuesCache.get(issueId);
	}

	@Override
	public List<IssueInfo> getIssuesByIdList(List<String> issueIdList,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<IssueInfo> issueList = new ArrayList<IssueInfo>();
		for (String issueId : issueIdList) {
			issueList.add(getIssue(issueId, context));
		}
		return issueList;
	}

	@Override
	public List<String> getIssueIdsByType(String issueTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> issueList = new ArrayList<String>();

		for (IssueInfo issue : issuesCache.values()) {
			if (issue.getTypeKey().equals(issueTypeKey)) {
				issueList.add(issue.getId());
			}
		}
		return issueList;
	}

	@Override
	public List<IssueInfo> getIssuesByOrg(String organizationId,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<IssueInfo> issueList = new ArrayList<IssueInfo>();

		for (IssueInfo issue : issuesCache.values()) {
			if (issue.getOrganizationId().equals(organizationId)) {
				issueList.add(issue);
			}
		}
		return issueList;
	}

	@Override
	public List<IssueInfo> getIssuesByRestriction(String restrictionKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<IssueInfo> issueList = new ArrayList<IssueInfo>();

		for (IssueInfo issue : issuesCache.values()) {
			if (issueRestrictionsMap.get(issue.getId())
					.contains(restrictionKey)) {
				issueList.add(issue);
			}
		}
		return issueList;

	}

	@Override
	public StatusInfo addIssueToRestriction(String restrictionKey,
			String issueId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		if (!issueRestrictionsMap.get(issueId).contains(restrictionKey)) {
			issueRestrictionsMap.get(issueId).add(restrictionKey);
		}
		return new StatusInfo();
	}

	@Override
	public StatusInfo removeIssueFromRestriction(String restrictionKey,
			String issueId, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		if (issueRestrictionsMap.get(issueId).contains(restrictionKey)) {
			issueRestrictionsMap.get(issueId).remove(restrictionKey);
		}
		return new StatusInfo();
	}

	@Override
	public List<ValidationResultInfo> validateIssue(String validationTypeKey,
			IssueInfo issueInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IssueInfo createIssue(IssueInfo issueInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		issuesCache.put(issueInfo.getId(), issueInfo);
		return issueInfo;
	}

	@Override
	public IssueInfo updateIssue(String issueId, IssueInfo issueInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		issuesCache.put(issueId, issueInfo);
		return issueInfo;
	}

	@Override
	public StatusInfo deleteIssue(String issueId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		issuesCache.remove(issueId);
		return new StatusInfo();
	}

	@Override
	public RestrictionInfo getRestriction(String restrictionKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		return restrictionsCache.get(restrictionKey);
	}

	@Override
	public List<RestrictionInfo> getRestrictionsByKeyList(
			List<String> restrictionKeyList, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		List<RestrictionInfo> restrictInfoList = new ArrayList<RestrictionInfo>();
		for (String restrictionKey : restrictionKeyList) {
			restrictInfoList.add(getRestriction(restrictionKey, context));
		}
		return restrictInfoList;
	}

	@Override
	public List<RestrictionInfo> getRestrictionsByIssue(String issueId,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		List<String> restrictionKeys = issueRestrictionsMap.get(issueId);
		List<RestrictionInfo> restrictionInfos = new ArrayList<RestrictionInfo>();
		for (String restInfo : restrictionKeys) {
			restrictionInfos.add(getRestriction(restInfo, context));
		}
		return restrictionInfos;
	}

	@Override
	public List<String> getRestrictionKeysByType(String restrictionTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> restrictionKeys = new ArrayList<String>();
		for (RestrictionInfo restInfo : restrictionsCache.values()) {
			if (restInfo.getTypeKey().equals(restrictionTypeKey)) {

				restrictionKeys.add(restInfo.getKey());
			}
		}
		return restrictionKeys;

	}

	@Override
	public List<ValidationResultInfo> validateRestriction(
			String validationTypeKey, RestrictionInfo restrictionInfo,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RestrictionInfo createRestriction(String restrictionKey,
			RestrictionInfo restrictionInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		restrictionsCache.put(restrictionKey, restrictionInfo);

		return restrictionInfo;
	}

	@Override
	public RestrictionInfo updateRestriction(String restrictionKey,
			RestrictionInfo restrictionInfo, ContextInfo context)
			throws DataValidationErrorException, DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException,
			VersionMismatchException {
		restrictionsCache.put(restrictionKey, restrictionInfo);

		return restrictionInfo;
	}

	@Override
	public StatusInfo deleteRestriction(String restrictionKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		restrictionsCache.remove(restrictionKey);
		return new StatusInfo();
	}

}
