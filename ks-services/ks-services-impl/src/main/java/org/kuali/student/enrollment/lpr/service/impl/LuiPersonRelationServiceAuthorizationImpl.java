package org.kuali.student.enrollment.lpr.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.common.dto.ContextInfo;
import org.kuali.student.common.dto.CriteriaInfo;
import org.kuali.student.common.dto.StateInfo;
import org.kuali.student.common.dto.StatusInfo;
import org.kuali.student.common.dto.TypeInfo;
import org.kuali.student.common.dto.TypeTypeRelationInfo;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DisabledIdentifierException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.datadictionary.dto.DictionaryEntryInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;



public class LuiPersonRelationServiceAuthorizationImpl implements LuiPersonRelationService {

	@Override
	public List<String> createBulkRelationshipsForPerson(String personId,
			List<String> luiIdList, String relationState,
			String luiPersonRelationType,
			LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context)
			throws AlreadyExistsException, DoesNotExistException,
			DisabledIdentifierException, InvalidParameterException,
			MissingParameterException, OperationFailedException,
			PermissionDeniedException {


		// TODO Auto-generated method stub
		List<String> bulkRelationshipValues = new ArrayList<String>();
		System.out.println("Inside authorization impl for createBulkRelationshipsForPerson" );
		//Simulating unknown exception behavior
		if(personId!=null){
			bulkRelationshipValues.add(personId);
		}else {
			throw new NullPointerException("person id is null");
		}
		return bulkRelationshipValues;
	}
	
	@Override
	public LuiPersonRelationInfo fetchLuiPersonRelation(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelationsByIdList(List<String> luiPersonRelationIdList, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> findLuiIdsRelatedToPerson(String personId, String luiPersonRelationType, String relationState, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> findPersonIdsRelatedToLui(String luiId, String luiPersonRelationType, String relationState, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelations(String personId, String luiId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIds(String personId, String luiId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelationsForPerson(String personId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIdsForPerson(String personId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> findLuiPersonRelationIdsForLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<ValidationResultInfo> validateLuiPersonRelation(String validationType,
	  LuiPersonRelationInfo luiPersonRelationInfo,
	  ContextInfo context)
	  throws DoesNotExistException,
	  InvalidParameterException,
	  MissingParameterException,
	  OperationFailedException,
	  PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> findAllValidLuisForPerson(String personId, String luiPersonRelationType, String relationState, String atpId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> searchForLuiPersonRelationIds(CriteriaInfo criteria, ContextInfo context) throws InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public String createLuiPersonRelation(String personId, String luiId, String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> createBulkRelationshipsForLui(String luiId, List<String> personIdList, String relationState, String luiPersonRelationType, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public LuiPersonRelationInfo updateLuiPersonRelation(String luiPersonRelationId, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, ReadOnlyException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public StatusInfo deleteLuiPersonRelation(String luiPersonRelationId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public DictionaryEntryInfo getDataDictionaryEntry(String entryKey, ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException, DoesNotExistException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

	@Override
	public List<String> getDataDictionaryEntryKeys(ContextInfo context) throws OperationFailedException, MissingParameterException, PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
	}

    @Override
    public TypeInfo getType(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getTypesByRefObjectURI(String refObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeInfo> getAllowedTypesForType(String ownerTypeKey, String relatedRefObjectURI, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<TypeTypeRelationInfo> getTypeRelationsByOwnerType(String ownerTypeKey, String relationTypeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<String> getProcessKeys(String typeKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getState(String processKey, String stateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getStatesByProcess(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public List<StateInfo> getInitialValidStates(String processKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    @Override
    public StateInfo getNextHappyState(String processKey, String currentStateKey, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException {
        // TODO Kamal - THIS METHOD NEEDS JAVADOCS
        return null;
    }
}
