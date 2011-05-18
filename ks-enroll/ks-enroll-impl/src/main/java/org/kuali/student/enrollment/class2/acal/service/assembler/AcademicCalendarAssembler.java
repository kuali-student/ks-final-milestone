package org.kuali.student.enrollment.class2.acal.service.assembler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.kuali.student.common.util.UUIDHelper;
import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
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

public class AcademicCalendarAssembler implements AtpAssembler<AcademicCalendarInfo, AtpInfo> {
    private AtpService atpService;
 
    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }
    
    @Override
    public AcademicCalendarInfo assemble(AtpInfo atp, ContextInfo context) {
        if(atp != null){
            AcademicCalendarInfo acal = AcademicCalendarInfo.newInstance();
            acal.setKey(atp.getKey());
            acal.setName(atp.getName());
            acal.setDescr(atp.getDescr());
            acal.setStartDate(atp.getStartDate());
            acal.setEndDate(atp.getEndDate());
            acal.setTypeKey(atp.getTypeKey());
            acal.setStateKey(atp.getStateKey());
            acal.setMetaInfo(atp.getMetaInfo());
            acal.setAttributes(atp.getAttributes());
            
            List<AttributeInfo> attributes = atp.getAttributes();
            if(attributes != null && !attributes.isEmpty()){
                for (AttributeInfo attribute : attributes){
                    if(attribute.getKey().equals("CredentialProgramType")){
                        acal.setCredentialProgramTypeKey(attribute.getValue());
                        break;
                    }
                }
            }

            //process atpatprelation
            assembleAtpAtpRelations(atp.getKey(), acal, context);
            return acal;
        }
        else
            return null;
    }

    private List<String> assembleAtpAtpRelations(String atpKey, AcademicCalendarInfo acal, ContextInfo context){
        List<String> ccKeys = new ArrayList<String>();
        List<AtpAtpRelationInfo> atpRels;
        try {
            atpRels = atpService.getAtpAtpRelationsByAtp(atpKey, context);
            
            if(atpRels != null && !atpRels.isEmpty()){                  
                for(AtpAtpRelationInfo atpRel : atpRels){
                    if(atpRel.getTypeKey().equals(AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY)){
                        ccKeys.add(atpRel.getRelatedAtpKey());
                    }
                }
            }
        } catch (Exception e) {
            return null;
        }
        
        return ccKeys;
    }
    
    @Override
    public AtpInfo disassemble(AcademicCalendarInfo acal, ContextInfo context) {
        AtpInfo atp = AtpInfo.newInstance();
        atp.setKey(acal.getKey());
        atp.setName(acal.getName());
        atp.setDescr(acal.getDescr());
        atp.setStartDate(acal.getStartDate());
        atp.setEndDate(acal.getEndDate());
        atp.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        atp.setStateKey(acal.getStateKey());
        atp.setMetaInfo(acal.getMetaInfo());

        List<AttributeInfo> attributes = (null != acal.getAttributes()? acal.getAttributes(): new ArrayList<AttributeInfo>());
        
        if(acal.getCredentialProgramTypeKey() != null){
            AttributeInfo cpt = AttributeInfo.newInstance();
            cpt.setKey("CredentialProgramType");
            cpt.setValue(acal.getCredentialProgramTypeKey());
            attributes.add(cpt);
        }
        atp.setAttributes(attributes);
        
        if(acal.getCampusCalendarKeys() != null && !acal.getCampusCalendarKeys().isEmpty()){
            try{
                //TODO: should state pass along to relations?
                disassembleAtpAtpRelations(acal.getKey(), acal.getCampusCalendarKeys(), acal.getStateKey(), context);
            }catch (Exception e){
                return null;
            }
        }        
        
        return atp;   
    }

    private void disassembleAtpAtpRelations(String atpKey, List<String> relatedAtpKeys, String state, ContextInfo context) throws AlreadyExistsException,
    DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        
        try {
            List<AtpAtpRelationInfo > atpRels = atpService.getAtpAtpRelationsByAtp(atpKey, context);
            Map<String, String> currentRelIds = new HashMap<String, String>();
            
            if(atpRels != null && !atpRels.isEmpty()){
                for(AtpAtpRelationInfo atpRelInfo : atpRels){
                    if(AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY.equals(atpRelInfo.getTypeKey())){
                        currentRelIds.put(atpRelInfo.getRelatedAtpKey(), atpRelInfo.getId());
                    }
                }
            }
            
            for (String relatedKey : relatedAtpKeys){
                if (!currentRelIds.containsKey(relatedKey))
                    createAtpAtpRelations(atpKey, relatedKey, state, context);
                else
                    updateAtpAtpRelations(currentRelIds.get(relatedKey), state, context);
            }

        } catch (DoesNotExistException e) {
            //if not exist, create relations
            for (String relatedKey : relatedAtpKeys){
                createAtpAtpRelations(atpKey, relatedKey, state, context);
            }
    }
    }
 
    private void createAtpAtpRelations(String atpKey, String relatedAtpKey, String state, ContextInfo context) throws AlreadyExistsException,
    DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        AtpAtpRelationInfo atpRel = AtpAtpRelationInfo.newInstance();
        atpRel.setId(UUIDHelper.genStringUUID());
        atpRel.setAtpKey(atpKey);
        atpRel.setRelatedAtpKey(relatedAtpKey);
        atpRel.setTypeKey(AtpServiceConstants.ATP_ATP_RELATION_INCLUDES_TYPE_KEY);
        atpRel.setStateKey(state);
        atpService.createAtpAtpRelation(atpRel, context);        
    }
    
    private void updateAtpAtpRelations(String atpAtpRelationId, String state, ContextInfo context) throws AlreadyExistsException,
    DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        AtpAtpRelationInfo atpRel;
        try {
            atpRel = AtpAtpRelationInfo.getInstance(atpService.getAtpAtpRelation(atpAtpRelationId, context));
            atpRel.setStateKey(state);
            try {
                atpService.updateAtpAtpRelation(atpAtpRelationId, atpRel, context);
            } catch (DoesNotExistException e) {
                e.printStackTrace();
            } catch (VersionMismatchException e) {
                e.printStackTrace();
            }   
        } catch (DoesNotExistException e1) {
            e1.printStackTrace();
        }
    }
}
