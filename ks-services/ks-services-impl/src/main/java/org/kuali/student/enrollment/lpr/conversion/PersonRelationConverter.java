package org.kuali.student.enrollment.lpr.conversion;

import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.model.DynamicAttribute;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelation;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelationState;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelationType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Igor
 */
public class PersonRelationConverter implements Converter<LuiPersonRelation, LuiPersonRelationInfo> {

    private Converter<List<DynamicAttribute>, Map<String, String>> dynamicAttributeConverter;

    private Converter<LuiPersonRelationState, LuiPersonRelationStateInfo> personRelationStateConverter;

    private Converter<LuiPersonRelationType, LuiPersonRelationTypeInfo> personRelationTypeConverter;

    @Override
    public LuiPersonRelation fromDto(LuiPersonRelationInfo luiPersonRelationInfo) {
        LuiPersonRelation personRelation = new LuiPersonRelation();
        dtoToEntity(luiPersonRelationInfo, personRelation);
        return personRelation;
    }

    @Override
    public List<LuiPersonRelation> fromDtos(List<LuiPersonRelationInfo> dto) {
        return null;
    }

    @Override
    public LuiPersonRelationInfo fromEntity(LuiPersonRelation personRelation) {
        LuiPersonRelationInfo personRelationInfo = new LuiPersonRelationInfo();
        entityToDto(personRelation, personRelationInfo);
        return personRelationInfo;
    }

    @Override
    public List<LuiPersonRelationInfo> fromEntities(List<LuiPersonRelation> personRelations) {
        List<LuiPersonRelationInfo> dtos = new ArrayList<LuiPersonRelationInfo>();
        for (LuiPersonRelation personRelation : personRelations) {
            dtos.add(fromEntity(personRelation));
        }
        return dtos;
    }

    @Override
    public void entityToDto(LuiPersonRelation personRelation, LuiPersonRelationInfo personRelationInfo) {
        personRelationInfo.setId(personRelation.getId() + "");
        personRelationInfo.setLuiId(personRelation.getLuiId());
        personRelationInfo.setPersonId(personRelation.getPersonId());
        personRelationInfo.setEffectiveDate(personRelation.getEffectiveDate());
        personRelationInfo.setExpirationDate(personRelation.getExpirationDate());
        personRelationInfo.setAttributes(dynamicAttributeConverter.fromEntity(personRelation.getDynamicAttributes()));
        personRelationInfo.setState(personRelation.getPersonRelationState().getId() + "");
        personRelationInfo.setType(personRelation.getPersonRelationType().getId() + "");
    }

    @Override
    public void dtoToEntity(LuiPersonRelationInfo luiPersonRelationInfo, LuiPersonRelation personRelation) {

    }

    @Override
    public void entitiesToDtos(List<LuiPersonRelation> personRelations, List<LuiPersonRelationInfo> luiPersonRelationInfos) {

    }

    @Override
    public void dtosToEntities(List<LuiPersonRelationInfo> luiPersonRelationInfos, List<LuiPersonRelation> personRelations) {

    }

    public void setDynamicAttributeConverter(Converter<List<DynamicAttribute>, Map<String, String>> dynamicAttributeConverter) {
        this.dynamicAttributeConverter = dynamicAttributeConverter;
    }

    public void setPersonRelationStateConverter(Converter<LuiPersonRelationState, LuiPersonRelationStateInfo> personRelationStateConverter) {
        this.personRelationStateConverter = personRelationStateConverter;
    }

    public void setPersonRelationTypeConverter(Converter<LuiPersonRelationType, LuiPersonRelationTypeInfo> personRelationTypeConverter) {
        this.personRelationTypeConverter = personRelationTypeConverter;
    }
}
