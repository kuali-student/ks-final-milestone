package org.kuali.student.enrollment.class2.acal.service.assembler;

import java.util.ArrayList;
import java.util.List;

import org.kuali.student.enrollment.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.common.assembler.AssemblyException;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpAtpRelationInfo;
import org.kuali.student.r2.core.atp.dto.AtpInfo;
import org.kuali.student.r2.core.atp.service.AtpService;

public class AcademicCalendarAssembler implements DTOAssembler<AcademicCalendarInfo, AtpInfo> {
  private AtpService atpService;
    
    public AtpService getAtpService() {
        return atpService;
    }

    public void setAtpService(AtpService atpService) {
        this.atpService = atpService;
    }


    @Override
    public AcademicCalendarInfo assemble(AtpInfo atp, ContextInfo context) throws AssemblyException {
        
        AcademicCalendarInfo acal = new AcademicCalendarInfo();
        acal.setId(atp.getKey());
        acal.setName(atp.getName());
        acal.setDescr(atp.getDescr());
        acal.setStartDate(atp.getStartDate());
        acal.setEndDate(atp.getEndDate());
        acal.setTypeKey(atp.getTypeKey());
        acal.setStateKey(atp.getStateKey());
        acal.setMeta(atp.getMeta());
        acal.setAttributes(atp.getAttributes());


        acal.setCampusCalendarIds(assembleRelations(atp.getKey(), AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY, context));
        return acal;
    }

    
    public List<String> assembleRelations(String atpKey, String relatedAtpType, ContextInfo context) throws AssemblyException {
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
            throw new AssemblyException(e.getMessage());
        }
        
        return ccKeys;
    }
    @Override
    public AtpInfo disassemble(AcademicCalendarInfo acal, ContextInfo context) throws AssemblyException {
        AtpInfo atp = new AtpInfo();
        atp.setKey(acal.getId());
        atp.setName(acal.getName());
        atp.setDescr(acal.getDescr());
        atp.setStartDate(acal.getStartDate());
        atp.setEndDate(acal.getEndDate());
        atp.setTypeKey(AtpServiceConstants.ATP_ACADEMIC_CALENDAR_TYPE_KEY);
        atp.setStateKey(acal.getStateKey());
        atp.setMeta(acal.getMeta());

        List<AttributeInfo> attributes = (null != acal.getAttributes() ? acal.getAttributes() : new ArrayList<AttributeInfo>());

       
        atp.setAttributes(attributes);

        return atp;
    }

   

}
