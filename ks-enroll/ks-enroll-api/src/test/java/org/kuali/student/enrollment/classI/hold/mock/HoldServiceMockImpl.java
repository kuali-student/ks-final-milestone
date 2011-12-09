package org.kuali.student.enrollment.classI.hold.mock;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.rice.core.api.criteria.QueryByCriteria;

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
import org.kuali.student.r2.core.hold.dto.HoldInfo;
import org.kuali.student.r2.core.hold.dto.IssueInfo;
import org.kuali.student.r2.core.hold.dto.RestrictionInfo;
import org.kuali.student.r2.core.hold.service.HoldService;

public class HoldServiceMockImpl implements HoldService {

	private static Map<String, List<String>> issueRestrictionsMap = new HashMap<String, List<String>>();
	private static Map<String, HoldInfo> holdCache = new HashMap<String, HoldInfo>();
	private static Map<String, IssueInfo> issuesCache = new HashMap<String, IssueInfo>();
	private static Map<String, RestrictionInfo> restrictionsCache = new HashMap<String, RestrictionInfo>();

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context)
			throws OperationFailedException, MissingParameterException,
			PermissionDeniedException {
	    return new ArrayList<String>();
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey,
			ContextInfo context) throws OperationFailedException,
			MissingParameterException, PermissionDeniedException,
			DoesNotExistException {
	    return null;
	}

	@Override
	public StateProcessInfo getProcessByKey(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    return null;
	}
	
	@Override
	public List<String> getProcessByObjectType(String objectTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
	    return new ArrayList<String>();
	}
	
	@Override
	public StateInfo getState(String processKey, String stateKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    return null;
	}

	@Override
	public List<StateInfo> getStatesByProcess(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    return new ArrayList<StateInfo>();
	}

	@Override
	public List<StateInfo> getInitialValidStates(String processKey,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    return new ArrayList<StateInfo>();
	}

	@Override
	public StateInfo getNextHappyState(String processKey,
			String currentStateKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    return null;
	}

	@Override
	public TypeInfo getType(String typeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    return null;
	}

	@Override
	public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI,
			ContextInfo context) throws DoesNotExistException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException {
	    return new ArrayList<TypeInfo>();
	}

	@Override
	public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey,
			String relatedRefObjectURI, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    return new ArrayList<TypeInfo>();
	}

	@Override
	public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(
			String ownerTypeKey, String relationTypeKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    return new ArrayList<TypeTypeRelationInfo>();
	}

	

	

	@Override
	public HoldInfo getHold(String holdId, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {

		return holdCache.get(holdId);
	}

	

	@Override
	public List<HoldInfo> getHoldsByIssue(String issueKey, ContextInfo context)
			throws InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {

		List<HoldInfo> allHold = new ArrayList<HoldInfo>();
		List<HoldInfo> holdsToReturn = new ArrayList<HoldInfo>();
		for (HoldInfo hold : allHold) {
			if (hold.getIssueKey().equals(issueKey)) {
				holdsToReturn.add(hold);
			}
		}

		return holdsToReturn;
	}

	

    @Override
    public List<String> searchForHoldIds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return new ArrayList<String>();
    }	

    @Override
    public List<HoldInfo> searchForHolds(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return new ArrayList<HoldInfo>();
    }	

	@Override
	public List<ValidationResultInfo> validateHold(String validationTypeKey,
			HoldInfo holdInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    return new ArrayList<ValidationResultInfo>();
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
	                throws DoesNotExistException, InvalidParameterException, MissingParameterException,
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
	public IssueInfo getIssue(String issueKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		return issuesCache.get(issueKey);
	}



	@Override
	public List<String> getIssueKeysByType(String issueTypeKey,
			ContextInfo context) throws InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		List<String> issueList = new ArrayList<String>();

		for (IssueInfo issue : issuesCache.values()) {
			if (issue.getTypeKey().equals(issueTypeKey)) {
				issueList.add(issue.getKey());
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
    public List<String> searchForIssueKeys(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return new ArrayList<String>();
    }	

    @Override
    public List<IssueInfo> searchForIssues(QueryByCriteria criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
	return new ArrayList<IssueInfo>();
    }	

	@Override
	public List<ValidationResultInfo> validateIssue(String validationTypeKey,
			IssueInfo issueInfo, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException {
	    return new ArrayList<ValidationResultInfo>();
	}

	@Override
	public IssueInfo createIssue(IssueInfo issueInfo, ContextInfo context)
			throws AlreadyExistsException, DataValidationErrorException,
			InvalidParameterException, MissingParameterException,
			OperationFailedException, PermissionDeniedException {
		issuesCache.put(issueInfo.getKey(), issueInfo);
		return issueInfo;
	}

	@Override
	public IssueInfo updateIssue(String issueKey, IssueInfo issueInfo,
			ContextInfo context) throws DataValidationErrorException,
			DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		issuesCache.put(issueKey, issueInfo);
		return issueInfo;
	}

	@Override
	public StatusInfo deleteIssue(String issueKey, ContextInfo context)
			throws DoesNotExistException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {
		issuesCache.remove(issueKey);
		return new StatusInfo();
	}

    @Override
    public List<HoldInfo> getHoldsByIds(List<String> holdIds, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getHoldIdsByType(String holdTypeKey, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getActiveHoldsByPerson(String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getHoldsByIssueAndPerson(String issueKey, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<HoldInfo> getActiveHoldsByIssueAndPerson(String issueKey, String personId, ContextInfo contextInfo) throws InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<IssueInfo> getIssuesByIds(List<String> issueKeys, ContextInfo contextInfo) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException,
            PermissionDeniedException {
        // TODO sambit - THIS METHOD NEEDS JAVADOCS
        return null;
    }


 

}
