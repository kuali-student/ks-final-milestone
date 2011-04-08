package org.kuali.student.enrollment.lpr.service.decorators;

import java.util.ArrayList;
import java.util.List;


import org.kuali.rice.kim.bo.types.dto.AttributeSet;
import org.kuali.rice.kim.service.PermissionService;
import org.kuali.student.common.dto.ContextInfo;

import org.kuali.student.common.exceptions.AlreadyExistsException;
import org.kuali.student.common.exceptions.DataValidationErrorException;
import org.kuali.student.common.exceptions.DisabledIdentifierException;
import org.kuali.student.common.exceptions.DoesNotExistException;
import org.kuali.student.common.exceptions.InvalidParameterException;
import org.kuali.student.common.exceptions.MissingParameterException;
import org.kuali.student.common.exceptions.OperationFailedException;
import org.kuali.student.common.exceptions.PermissionDeniedException;
import org.kuali.student.common.exceptions.ReadOnlyException;
import org.kuali.student.common.infc.HoldsPermissionService;
import org.kuali.student.common.dto.ValidationResultInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.service.LuiPersonRelationService;




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
		if(personId!=null){
			bulkRelationshipValues.add(personId);
		}else {
			throw new NullPointerException("person id is null");
		}
		return super.createBulkRelationshipsForPerson(personId, luiIdList, relationState, luiPersonRelationTypeKey, luiPersonRelationInfo, context);		
	}



	@Override
	public List<ValidationResultInfo> validateLuiPersonRelation(String validationType,
			LuiPersonRelationInfo luiPersonRelationInfo,
			ContextInfo context)
			throws DoesNotExistException,
			MissingParameterException,
			OperationFailedException,
			PermissionDeniedException {
		// TODO Kamal - THIS METHOD NEEDS JAVADOCS
		return null;
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
