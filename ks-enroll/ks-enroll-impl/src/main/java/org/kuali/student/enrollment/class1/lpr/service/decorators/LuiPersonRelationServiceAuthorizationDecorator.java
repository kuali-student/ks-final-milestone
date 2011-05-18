package org.kuali.student.enrollment.class1.lpr.service.decorators;

import java.util.ArrayList;
import java.util.List;


import org.kuali.rice.core.xml.dto.AttributeSet;
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
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationServiceDecorator;
import org.kuali.student.r2.common.dto.ContextInfo;
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
		return super.validateLuiPersonRelation(validationType, luiPersonRelationInfo, context);
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
	
	

}
