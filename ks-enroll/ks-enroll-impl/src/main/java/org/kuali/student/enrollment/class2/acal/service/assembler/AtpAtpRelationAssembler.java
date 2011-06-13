package org.kuali.student.enrollment.class2.acal.service.assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.common.exceptions.AlreadyExistsException;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.exceptions.VersionMismatchException;
import org.kuali.student.r2.common.infc.AtpAssembler;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

public class AtpAtpRelationAssembler implements AtpAssembler<Object, Object> {
    private AtpService atpService;
    
    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }
    
	public List<String> assemble(String atpKey, String relatedAtpType, ContextInfo context){
        List<String> ccKeys = new ArrayList<String>();
        List<AtpAtpRelationInfo> atpRels;
        try {
            atpRels = atpService.getAtpAtpRelationsByAtp(atpKey, context);
            
            if(atpRels != null && !atpRels.isEmpty()){                  
                for(AtpAtpRelationInfo atpRelInfo : atpRels){
                	if(atpRelInfo.getAtpKey().equals(atpKey)){
	                    if(atpRelInfo.getTypeKey().equals(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY)){
	                    	AtpInfo thisAtp = atpService.getAtp(atpRelInfo.getRelatedAtpKey(), context);
	                        if(thisAtp != null && thisAtp.getTypeKey().equals(relatedAtpType))
	                        ccKeys.add(atpRelInfo.getRelatedAtpKey());
	                    }
                	}
                }
            }
        } catch (Exception e) {
            return null;
        }
        
        return ccKeys;
    }
	
    public StatusInfo disassemble(String atpKey, List<String> relatedAtpKeys, String relatedAtpType, ContextInfo context){
    	StatusInfo status = new StatusInfo();
        status.setSuccess(Boolean.TRUE);        
        
        try {
            disassembleAtpAtpRelations(atpKey, relatedAtpKeys, relatedAtpType, context);
        } catch (AlreadyExistsException e) {
            status.setSuccess(Boolean.FALSE);        
        } catch (DataValidationErrorException e) {
            status.setSuccess(Boolean.FALSE);        
        } catch (InvalidParameterException e) {
            status.setSuccess(Boolean.FALSE);        
        } catch (MissingParameterException e) {
            status.setSuccess(Boolean.FALSE);        
        } catch (OperationFailedException e) {
            status.setSuccess(Boolean.FALSE);        
        } catch (PermissionDeniedException e) {
            status.setSuccess(Boolean.FALSE);        
        } catch (VersionMismatchException e) {
            status.setSuccess(Boolean.FALSE);        
        }
        
        return status;
    }
    
    private void disassembleAtpAtpRelations(String atpKey, List<String> relatedAtpKeys, String relatedAtpType, ContextInfo context) throws AlreadyExistsException,
    DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException, VersionMismatchException {
        
        try {
            List<AtpAtpRelationInfo > atpRels = atpService.getAtpAtpRelationsByAtp(atpKey, context);
            Map<String, String> currentRelIds = new HashMap<String, String>();
            
            if(atpRels != null && !atpRels.isEmpty()){
                for(AtpAtpRelationInfo atpRelInfo : atpRels){
                	if(atpRelInfo.getAtpKey().equals(atpKey)){
	                    if(AtpServiceConstants.ATP_ATP_RELATION_ASSOCIATED_TYPE_KEY.equals(atpRelInfo.getTypeKey())){
	                        AtpInfo thisAtp = atpService.getAtp(atpRelInfo.getRelatedAtpKey(), context);
	                        if(thisAtp != null && thisAtp.getTypeKey().equals(relatedAtpType))
	                            currentRelIds.put(atpRelInfo.getRelatedAtpKey(), atpRelInfo.getId());
	
	                	}
                    }
                }
            }
            
            for (String relatedKey : relatedAtpKeys){
                if (!currentRelIds.containsKey(relatedKey))
                    createAtpAtpRelations(atpKey, relatedKey, context);
                else
                    updateAtpAtpRelations(currentRelIds.get(relatedKey), context);
            }

        } catch (DoesNotExistException e) {
            //if not exist, create relations
            for (String relatedKey : relatedAtpKeys){
                createAtpAtpRelations(atpKey, relatedKey, context);
            }
        }
    }
    
    public void createAtpAtpRelations(String atpKey, String relatedAtpKey, ContextInfo context) throws AlreadyExistsException,
    DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
    	AtpAtpRelationInfo atpRel = new AtpAtpRelationInfo();
        atpRel.setId(UUIDHelper.genStringUUID());
        atpRel.setAtpKey(atpKey);
        atpRel.setRelatedAtpKey(relatedAtpKey);
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY);
        atpRel.setStateKey(AtpServiceConstants.ATP_ATP_RELATION_ACTIVE_STATE_KEY);
        atpService.createAtpAtpRelation(atpRel, context);        
    }
    
    public void updateAtpAtpRelations(String atpAtpRelationId, ContextInfo context) throws AlreadyExistsException,
    DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException, DoesNotExistException, VersionMismatchException{
        AtpAtpRelationInfo atpRel;
        try {
        	atpRel = new AtpAtpRelationInfo(atpService.getAtpAtpRelation(atpAtpRelationId, context));
            //TODO:what to update? should state same as atpRel.atp?
           // atpRel.setStateKey(state);
            try {
                atpService.updateAtpAtpRelation(atpAtpRelationId, atpRel, context);
            } catch (DoesNotExistException e) {
            	throw new DoesNotExistException(atpAtpRelationId);
            } catch (VersionMismatchException e) {
            	throw new VersionMismatchException(atpAtpRelationId);
            }   
        } catch (DoesNotExistException e1) {
        	throw new DoesNotExistException(atpAtpRelationId);
        }
    }

	@Override
	public Object assemble(Object baseDTO, ContextInfo context) {
		return null;
	}

	@Override
	public Object disassemble(Object businessDTO, ContextInfo context) {
		return null;
	}

}
