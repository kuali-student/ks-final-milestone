package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;

public class FormatOfferingTransformer {

    public void lui2Format(LuiInfo lui, FormatOfferingInfo format) {
        format.setId(lui.getId());
        format.setTypeKey(lui.getTypeKey());
        format.setStateKey(lui.getStateKey());
        format.setName(lui.getName());
        format.setFormatId(lui.getCluId());
        format.setTermId(lui.getAtpId());
        format.setDescr(lui.getDescr());
        format.setActivityOfferingTypeKeys(lui.getRelatedLuiTypes());
        format.setMeta(lui.getMeta());
        format.setAttributes(lui.getAttributes());
    }

    public void format2Lui(FormatOfferingInfo format, LuiInfo lui) {
        lui.setId(format.getId());
        lui.setTypeKey(format.getTypeKey());
        lui.setStateKey(format.getStateKey());
        lui.setName(format.getName());
        lui.setCluId(format.getFormatId());
        lui.setAtpId(format.getTermId());
        lui.setDescr(format.getDescr());
        lui.setRelatedLuiTypes(format.getActivityOfferingTypeKeys());
        lui.setMeta(format.getMeta());
        lui.setAttributes(format.getAttributes());
    }
}