package org.kuali.student.enrollment.class2.acal.service.assembler;


import org.kuali.student.enrollment.acal.dto.CampusCalendarInfo;
import org.kuali.student.r2.common.assembler.DTOAssembler;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.util.constants.AtpServiceConstants;
import org.kuali.student.r2.core.atp.dto.AtpInfo;

import java.util.ArrayList;
import java.util.List;

public class CampusCalendarAssembler implements DTOAssembler<CampusCalendarInfo, AtpInfo> {

    @Override
    public CampusCalendarInfo assemble(AtpInfo atp, ContextInfo context) {
        if(atp != null){
            CampusCalendarInfo campusCalendarInfo = new CampusCalendarInfo();
            campusCalendarInfo.setKey(atp.getKey());
            campusCalendarInfo.setName(atp.getName());
            campusCalendarInfo.setDescr(atp.getDescr());
            campusCalendarInfo.setStartDate(atp.getStartDate());
            campusCalendarInfo.setEndDate(atp.getEndDate());
            campusCalendarInfo.setTypeKey(atp.getTypeKey());
            campusCalendarInfo.setStateKey(atp.getStateKey());
            campusCalendarInfo.setMeta(atp.getMeta());
            campusCalendarInfo.setAttributes(atp.getAttributes());

            List<AttributeInfo> attributes = atp.getAttributes();
            if(attributes != null && !attributes.isEmpty()){
                for (AttributeInfo attribute : attributes){
                    if(attribute.getKey().equals(AtpServiceConstants.CAMPUS_LOCATION)){
                        campusCalendarInfo.setLocation(attribute.getValue());
                        break;
                    }
                }
            }

            return campusCalendarInfo;
        }
        else
            return null;
    }

    @Override
    public AtpInfo disassemble(CampusCalendarInfo campusCalendarInfo, ContextInfo context) {
        if (campusCalendarInfo != null){
            AtpInfo atp = new AtpInfo();
            atp.setKey(campusCalendarInfo.getKey());
            atp.setName(campusCalendarInfo.getName());
            atp.setDescr(campusCalendarInfo.getDescr());
            atp.setStartDate(campusCalendarInfo.getStartDate());
            atp.setEndDate(campusCalendarInfo.getEndDate());
            atp.setTypeKey(AtpServiceConstants.ATP_CAMPUS_CALENDAR_TYPE_KEY);
            atp.setStateKey(campusCalendarInfo.getStateKey());
            atp.setMeta(campusCalendarInfo.getMeta());

            List<AttributeInfo> attributes = (null != campusCalendarInfo.getAttributes() ? campusCalendarInfo.getAttributes() : new ArrayList<AttributeInfo>());

            if (noAttributeEntryWithKey(attributes, AtpServiceConstants.CAMPUS_LOCATION) && campusCalendarInfo.getLocation() != null) {
                AttributeInfo cpt = new AttributeInfo();
                cpt.setKey(AtpServiceConstants.CAMPUS_LOCATION);
                cpt.setValue(campusCalendarInfo.getLocation());
                attributes.add(cpt);
            }

            atp.setAttributes(attributes);

            return atp;
        }

        return null;
    }

    private boolean noAttributeEntryWithKey(List<AttributeInfo> attributes, String key) {
        for (AttributeInfo attInfo : attributes) {
            if (attInfo.getKey().equals(key)) {
                return false;
            }
        }
        return true;
    }
}
