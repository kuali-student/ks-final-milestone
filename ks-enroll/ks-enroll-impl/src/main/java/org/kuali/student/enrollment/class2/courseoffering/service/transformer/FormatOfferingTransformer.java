package org.kuali.student.enrollment.class2.courseoffering.service.transformer;

import org.kuali.student.enrollment.courseoffering.dto.FormatOfferingInfo;
import org.kuali.student.enrollment.lui.dto.LuiIdentifierInfo;
import org.kuali.student.enrollment.lui.dto.LuiInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.CourseOfferingServiceConstants;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;

import java.util.List;

public class FormatOfferingTransformer {

    public void lui2Format(LuiInfo lui, FormatOfferingInfo format) {
        format.setId(lui.getId());
        format.setTypeKey(lui.getTypeKey());
        format.setStateKey(lui.getStateKey());
        format.setName(lui.getName());
        //Pull the short and long name from the official identifier
        if(lui.getOfficialIdentifier() != null){
            format.setShortName(lui.getOfficialIdentifier().getShortName());
            format.setName(lui.getOfficialIdentifier().getLongName());
        }
        format.setFormatId(lui.getCluId());
        format.setTermId(lui.getAtpId());
        format.setDescr(lui.getDescr());
        format.setActivityOfferingTypeKeys(lui.getRelatedLuiTypes());
        format.setMeta(lui.getMeta());

        // Dynamic attributes - Some fields in Format Offering need to be mapped to LUI dynamic attributes
        List<AttributeInfo> attributes = format.getAttributes();
        for (Attribute attr : lui.getAttributes()) {
            if (CourseOfferingServiceConstants.GRADE_ROSTER_LEVEL_TYPE_KEY_ATTR.equals(attr.getKey())){
                format.setGradeRosterLevelTypeKey(attr.getValue());
            } else if (CourseOfferingServiceConstants.FINAL_EXAM_LEVEL_TYPE_KEY_ATTR.equals(attr.getKey())){
                format.setFinalExamLevelTypeKey(attr.getValue());
            } else {
                // Format Offering dynamic attribute that was stored in LUI dyn attribute
                attributes.add(new AttributeInfo(attr));
            }
        }
        format.setAttributes(attributes);
    }

    public void format2Lui(FormatOfferingInfo format, LuiInfo lui) {
        lui.setId(format.getId());
        lui.setTypeKey(format.getTypeKey());
        lui.setStateKey(format.getStateKey());
        if (format.getName() != null){
            //Set the format's name into the format offering lui official identifier
            LuiIdentifierInfo luiIdent = lui.getOfficialIdentifier();
            if(luiIdent == null){
                luiIdent = new LuiIdentifierInfo();
                luiIdent.setTypeKey(LuiServiceConstants.LUI_IDENTIFIER_OFFICIAL_TYPE_KEY);
                luiIdent.setStateKey(LuiServiceConstants.LUI_IDENTIFIER_ACTIVE_STATE_KEY);
                lui.setOfficialIdentifier(luiIdent);
            }
            lui.setName(format.getName());
            lui.getOfficialIdentifier().setLongName(format.getName());
            lui.getOfficialIdentifier().setShortName(format.getShortName());
        }
        lui.setCluId(format.getFormatId());
        lui.setAtpId(format.getTermId());
        lui.setDescr(format.getDescr());
        lui.setRelatedLuiTypes(format.getActivityOfferingTypeKeys());
        lui.setMeta(format.getMeta());
        lui.getAttributes().clear();

        //Dynamic Attributes in Format Offering
        List<AttributeInfo> attributes = lui.getAttributes();
        for (Attribute attr : format.getAttributes()) {
            attributes.add(new AttributeInfo(attr));
        }

        //Dynamic attributes - Some fields in Format Offering need to be mapped to LUI dynamic attributes
        AttributeInfo gradeRosterLevelTypeKey = new AttributeInfo();
        gradeRosterLevelTypeKey.setKey(CourseOfferingServiceConstants.GRADE_ROSTER_LEVEL_TYPE_KEY_ATTR);
        gradeRosterLevelTypeKey.setValue(format.getGradeRosterLevelTypeKey());
        attributes.add(gradeRosterLevelTypeKey);

        AttributeInfo finalExamLevelTypeKey = new AttributeInfo();
        finalExamLevelTypeKey.setKey(CourseOfferingServiceConstants.FINAL_EXAM_LEVEL_TYPE_KEY_ATTR);
        finalExamLevelTypeKey.setValue(format.getFinalExamLevelTypeKey());
        attributes.add(finalExamLevelTypeKey);
        
        lui.setAttributes(attributes);
    }
}