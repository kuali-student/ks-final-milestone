package org.kuali.student.enrollment.classII.acal.service.assembler;

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
import org.kuali.student.r2.common.infc.AtpAssembler;
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
                    if(atpRel.getTypeKey().equals("kuali.atp.atp.relation.includes")){
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
    public AtpInfo disassemble(AcademicCalendarInfo academicCalendarInfo, ContextInfo context) {
        AtpInfo atp = AtpInfo.newInstance();
        atp.setKey(academicCalendarInfo.getKey());
        atp.setName(academicCalendarInfo.getName());
        atp.setDescr(academicCalendarInfo.getDescr());
        atp.setStartDate(academicCalendarInfo.getStartDate());
        atp.setEndDate(academicCalendarInfo.getEndDate());
        //TODO:pick hard coded from a constant file
        atp.setTypeKey("kuali.atp.type.AcademicCalendar");
        atp.setStateKey(academicCalendarInfo.getStateKey());
        atp.setMetaInfo(academicCalendarInfo.getMetaInfo());

        List<AttributeInfo> attributes = (null != academicCalendarInfo.getAttributes()? academicCalendarInfo.getAttributes(): new ArrayList<AttributeInfo>());
        AttributeInfo cpt = AttributeInfo.newInstance();
        cpt.setKey("CredentialProgramType");
        cpt.setValue(academicCalendarInfo.getCredentialProgramTypeKey());
        attributes.add(cpt);
        
        if(academicCalendarInfo.getCampusCalendarKeys() != null && !academicCalendarInfo.getCampusCalendarKeys().isEmpty()){
            try{
                disassembleAtpAtpRelations(academicCalendarInfo.getKey(), academicCalendarInfo.getCampusCalendarKeys(), context);
            }catch (Exception e){
                return null;
            }
        }        
        
        return atp;   
    }

    private void disassembleAtpAtpRelations(String atpKey, List<String> relatedAtpKeys, ContextInfo context) throws AlreadyExistsException,
    DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        
        try {
            List<AtpAtpRelationInfo > atpRels = atpService.getAtpAtpRelationsByAtp(atpKey, context);
            
            for (String relatedKey : relatedAtpKeys){
                for(AtpAtpRelationInfo atpRelInfo : atpRels){
                    if(!relatedKey.equals(atpRelInfo.getRelatedAtpKey())){
                      //if not exist, create relations
                        createAtpAtpRelations(atpKey, relatedKey, context);
                    }
                }   
            }

        } catch (DoesNotExistException e) {
            //if not exist, create relations
            for (String relatedKey : relatedAtpKeys){
                createAtpAtpRelations(atpKey, relatedKey, context);
            }
    }
    }
 
    private void createAtpAtpRelations(String atpKey, String relatedAtpKey, ContextInfo context) throws AlreadyExistsException,
    DataValidationErrorException, InvalidParameterException, MissingParameterException,
    OperationFailedException, PermissionDeniedException {
        AtpAtpRelationInfo atpRel = AtpAtpRelationInfo.newInstance();
        atpRel.setId(UUIDHelper.genStringUUID());
        atpRel.setAtpKey(atpKey);
        atpRel.setRelatedAtpKey(relatedAtpKey);
        atpRel.setTypeKey("kuali.atp.atp.relation.includes");
        atpService.createAtpAtpRelation(atpRel, context);        
    }
}
