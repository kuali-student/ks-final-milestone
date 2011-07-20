package org.kuali.student.enrollment.class1.lpr.service.decorators;

import java.util.ArrayList;
import java.util.List;


import org.kuali.rice.core.util.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;

import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DisabledIdentifierException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.ReadOnlyException;
import org.kuali.student.r2.common.infc.HoldsPermissionService;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionInfo;
import org.kuali.student.enrollment.lpr.dto.LPRTransactionItemInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceDecorator;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.dto.ValidationResultInfo;

/**
 * An example authorization decorator for the {@link LuiPersonRelationService}.
 * We would like to decorate the createBulkRelationshipsForPerson method here with authorization checks and custom logic
 * 
 * @author sambit
 *
 */


public class LuiPersonRelationServiceAuthorizationDecorator extends LuiPersonRelationServiceDecorator  implements HoldsPermissionService {

	private PermissionService permissionService;
	 
	public static final String ENRLLMENT_NAMESPACE = "KS-Enrollment";
		
	@Override
	public PermissionService getPermissionService() {
		return permissionService;
	}

	@Override
	public void setPermissionService(PermissionService permissionService) {
		this.permissionService = permissionService;
	}

	
	@Override
	public List<String> createBulkRelationshipsForPerson(String personId, List<String> luiIdList, String relationState, String luiPersonRelationTypeKey, LuiPersonRelationInfo luiPersonRelationInfo, ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
		// TODO Auto-generated method stub
		List<String> bulkRelationshipValues = new ArrayList<String>();
		System.out.println("Inside authorization impl for createBulkRelationshipsForPerson" );
		//Simulating unknown exception behavior
		if(personId != null){
			bulkRelationshipValues.addAll(nextDecorator.createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo, context));
		}else {
			throw new NullPointerException("person id is null");
		}
		return super.createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo, context);		
	}

    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForLui(String luiId, ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
    	return nextDecorator.findLuiPersonRelationsForLui(luiId, context);
    }

	@Override
	public List<ValidationResultInfo> validateLuiPersonRelation(String validationType,
			LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context)
			throws DoesNotExistException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException, 
			InvalidParameterException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return nextDecorator.validateLuiPersonRelation(validationType, luiPersonRelationInfo, context);
	}

	  /**
     * Fake authorization method.
     *
     * @param principal
     * @param permissionName  the authorization permission
     * @param qualifier an authorization qualifier
     * @return true if authorization successful
     */

	protected boolean isAuthorized(String principal, String permissionName, String qualifier) {
		AttributeSet permissionDetails = null;
		AttributeSet qualifierDetails = new AttributeSet();
		qualifierDetails.put("qualifierKey", qualifier);
		return this.permissionService.isAuthorized(principal,
				ENRLLMENT_NAMESPACE,
				permissionName,
				permissionDetails,
				qualifierDetails);
	}

      /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#createLuiPersonRelationTransaction(org.kuali.student.enrollment.lpr.dto.LprTransactionInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LPRTransactionInfo createLuiPersonRelationTransaction(
            LPRTransactionInfo lPRTransactionInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#createLuiPersonRelationTransactionFromExisting(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LPRTransactionInfo createLuiPersonRelationTransactionFromExisting(
            String luiPersonRelationTransactionId, ContextInfo context) throws DataValidationErrorException,
            AlreadyExistsException, DoesNotExistException, DisabledIdentifierException, ReadOnlyException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#updateLuiPersonRelationTransaction(java.lang.String, org.kuali.student.enrollment.lpr.dto.LPRTransactionItemInfo, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LPRTransactionInfo updateLuiPersonRelationTransaction(String lprTransactionId,
            LPRTransactionItemInfo luiPersonRelationRequestInfo, ContextInfo context)
            throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLuiPersonRelationTransaction(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LPRTransactionInfo getLuiPersonRelationTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

   

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#deleteLuiPersonRelationTransaction(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public StatusInfo deleteLuiPersonRelationTransaction(String lprTransactionId, ContextInfo context)
            throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

  
    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLuiPersonRelationTransactionsByPerson(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByPerson(String personId,
            ContextInfo context) throws DoesNotExistException, DisabledIdentifierException, InvalidParameterException,
            MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#findLuiPersonRelationsForPersonAndAtp(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LuiPersonRelationInfo> findLuiPersonRelationsForPersonAndAtp(String personId, String atpId,
            ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLuiPersonRelationTransactionsByPersonAndLui(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByPersonAndLui(String personId,
            String luiId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLuiPersonRelationTransactionsByAtpAndPerson(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByAtpAndPerson(String atpId,
            String personId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#getLuiPersonRelationTransactionsByAtpAndLui(java.lang.String, java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public List<LPRTransactionInfo> getLuiPersonRelationTransactionsByAtpAndLui(String luiId,
            String atpId, ContextInfo context) throws DoesNotExistException, DisabledIdentifierException,
            InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }

    /**
     * This overridden method ...
     * 
     * @see org.kuali.student.enrollment.lpr.service.LuiPersonRelationService#submitLuiPersonRelationTransaction(java.lang.String, org.kuali.student.r2.common.dto.ContextInfo)
     */
    @Override
    public LPRTransactionInfo submitLuiPersonRelationTransaction(String lprTransactionId,
            ContextInfo context) throws DataValidationErrorException, AlreadyExistsException, DoesNotExistException,
            DisabledIdentifierException, ReadOnlyException, InvalidParameterException, MissingParameterException,
            OperationFailedException, PermissionDeniedException {
        // TODO sambitpatnaik - THIS METHOD NEEDS JAVADOCS
        return null;
    }
	
	

}
