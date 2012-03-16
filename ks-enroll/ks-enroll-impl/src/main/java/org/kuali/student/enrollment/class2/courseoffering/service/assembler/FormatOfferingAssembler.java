package org.kuali.student.enrollment.class2.courseoffering.service.assembler;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;





public class FormatOfferingAssembler{

    public static FormatOfferingInfo assemble(LuiInfo lui)
        {
            FormatOfferingInfo  formatOfferingInfo = new FormatOfferingInfo();
            formatOfferingInfo.setActivityOfferingTypeKeys(lui.getRelatedLuiTypes());
            formatOfferingInfo.setId(lui.getId());
            formatOfferingInfo.setName(lui.getName());
            formatOfferingInfo.setFormatId(lui.getCluId());
            formatOfferingInfo.setTypeKey(lui.getTypeKey());
            formatOfferingInfo.setStateKey(lui.getStateKey());
            formatOfferingInfo.setMeta(lui.getMeta());
            formatOfferingInfo.setAttributes(lui.getAttributes());
            formatOfferingInfo.setDescr(lui.getDescr());
            
            return formatOfferingInfo;
        }


    public static LuiInfo disassemble(FormatOfferingInfo businessDTO)
        {
            LuiInfo lui =  new LuiInfo(); 
            lui.setId(businessDTO.getId());
            lui.setAttributes(businessDTO.getAttributes());
            lui.setCluId(businessDTO.getFormatId());
            lui.setDescr(businessDTO.getDescr());
            lui.setMeta(businessDTO.getMeta());
            lui.setName(businessDTO.getName());
            lui.setRelatedLuiTypes(businessDTO.getActivityOfferingTypeKeys());
            return lui;
        }
}