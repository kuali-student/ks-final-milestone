package org.kuali.student.ap.academicplan.service;

import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.rice.core.api.criteria.QueryByCriteria;
import org.kuali.student.ap.academicplan.dto.DegreeMapInfo;
import org.kuali.student.ap.academicplan.dto.DegreeMapRequirementInfo;
import org.kuali.student.ap.academicplan.dto.PlaceholderInfo;
import org.kuali.student.ap.academicplan.dto.PlaceholderInstanceInfo;
import org.kuali.student.ap.academicplan.dto.ReferenceObjectListInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;

/**
 *
 * @version 1.0 (Dev)
 * @Author mguilla
 * Date: 1/27/14
 */
@WebService(name = DegreeMapServiceConstants.SERVICE_NAME, serviceName = DegreeMapServiceConstants.SERVICE_NAME, portName = DegreeMapServiceConstants.SERVICE_NAME, targetNamespace = DegreeMapServiceConstants.NAMESPACE)
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
public interface DegreeMapService {

    public DegreeMapInfo getDegreeMap(@WebParam(name = "degreeMapId") String degreeMapId, @WebParam(name = "effdt") Date effdt, @WebParam(name = "context") ContextInfo context) 
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    public List<DegreeMapRequirementInfo> getRequirements(@WebParam(name = "degreeMapId") String degreeMapId, @WebParam(name = "effdt") Date effdt,  @WebParam(name = "context") ContextInfo context) 
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

    public DegreeMapRequirementInfo getRequirement(@WebParam(name = "requirementId") String requirementId,  @WebParam(name = "context") ContextInfo context) 
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    public DegreeMapRequirementInfo createRequirement(@WebParam(name = "requirement") DegreeMapRequirementInfo requirement,
    		@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    public DegreeMapRequirementInfo updateRequirement(@WebParam(name = "requirementId") String requirementId, @WebParam(name = "requirement") DegreeMapRequirementInfo Requirement, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;


    public StatusInfo deleteRequirement(@WebParam(name = "requirementId") String requirementId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    public List<DegreeMapInfo> search(@WebParam(name = "criteria") QueryByCriteria criteria, @WebParam(name = "contextInfo") ContextInfo contextInfo) throws InvalidParameterException,
    	MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    // THESE BELONG IN ACADEMIC PLAN SERVICE BUT PUTTING THEM HERE FOR NOW
    
// PLACEHOLDER
    
    public PlaceholderInfo getPlaceholder(@WebParam(name = "placeholderId") String placeholderId,  @WebParam(name = "context") ContextInfo context) 
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    public PlaceholderInfo createPlaceholder(@WebParam(name = "placeholderInfo") PlaceholderInfo placeholderId,
    		@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    public PlaceholderInfo updatePlaceholder(@WebParam(name = "placeholderId") String placeholderId, @WebParam(name = "placeholderId") PlaceholderInfo Placeholder, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;


    public StatusInfo deletePlaceholder(@WebParam(name = "placeholderId") String placeholderId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
// PLACEHOLDER INSTANCE
    
    public PlaceholderInstanceInfo getPlaceholderInstance(@WebParam(name = "placeholderInstanceId") String placeholderInstanceId,  @WebParam(name = "context") ContextInfo context) 
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    public PlaceholderInstanceInfo createPlaceholderInstance(@WebParam(name = "placeholderInstanceInfo") PlaceholderInstanceInfo placeholderInstanceId,
    		@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    public PlaceholderInstanceInfo updatePlaceholderInstance(@WebParam(name = "placeholderInstanceId") String placeholderInstanceId, @WebParam(name = "placeholderInstanceId") PlaceholderInstanceInfo PlaceholderInstance, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;


    public StatusInfo deletePlaceholderInstance(@WebParam(name = "placeholderInstanceId") String placeholderInstanceId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
 // REFERENCE OBJECT LIST 
 
    public ReferenceObjectListInfo getReferenceObjectList(@WebParam(name = "id") String id,  @WebParam(name = "context") ContextInfo context) 
    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;
    
    public ReferenceObjectListInfo createReferenceObjectList(@WebParam(name = "referenceObjectList") ReferenceObjectListInfo referenceObjectList,
    		@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    public ReferenceObjectListInfo updateReferenceObjectList(@WebParam(name = "id") String id, @WebParam(name = "referenceObjectList") ReferenceObjectListInfo referenceObjectList, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;

    public StatusInfo deleteReferenceObjectList(@WebParam(name = "id") String id, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;

    
    
//    public DegreeMapInfo createDegreeMap(@WebParam(name = "degreeMap") DegreeMapInfo degreeMap,
//    		@WebParam(name = "context") ContextInfo context) throws AlreadyExistsException, DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
//    
//    public DegreeMapInfo updateDegreeMap(@WebParam(name = "degreeMapId") String degreeMapId, @WebParam(name = "DegreeMap") DegreeMapInfo DegreeMap, @WebParam(name = "context") ContextInfo context) throws DataValidationErrorException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException, DoesNotExistException;
//
//
//    public StatusInfo deleteDegreeMap(@WebParam(name = "degreeMapId") String degreeMapId, @WebParam(name = "context") ContextInfo context) throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException, PermissionDeniedException;
//
//
//    public List<DegreeMapInfo> getDegreeMapsForProgram(@WebParam(name = "credentialProgramId") String credentialProgramId, @WebParam(name = "context") ContextInfo context) 
//    		throws DoesNotExistException, InvalidParameterException, MissingParameterException, OperationFailedException;

}


