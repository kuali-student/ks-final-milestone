package org.kuali.student.enrollment.lpr.conversion;

import org.kuali.student.enrollment.lpr.dto.DynamicAttributeInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.model.DynamicAttribute;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelation;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelationState;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelationType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
public class PersonRelationConverter implements Converter<LuiPersonRelation, LuiPersonRelationInfo> {

    private Converter<DynamicAttribute, DynamicAttributeInfo> dynamicAttributeConverter;

    private SimpleConverter<LuiPersonRelationState, LuiPersonRelationStateInfo> personRelationStateConverter;

    private SimpleConverter<LuiPersonRelationType, LuiPersonRelationTypeInfo> personRelationTypeConverter;

    @Override
    public LuiPersonRelation fromDto(LuiPersonRelationInfo dto) {
        LuiPersonRelation entity = new LuiPersonRelation();
        dtoToEntity(dto, entity);
        return entity;
    }

    @Override
    public List<LuiPersonRelation> fromDtos(List<LuiPersonRelationInfo> dtos) {
        List<LuiPersonRelation> entities = new ArrayList<LuiPersonRelation>();
        for (LuiPersonRelationInfo dto : dtos) {
            entities.add(fromDto(dto));
        }
        return entities;
    }

    @Override
    public LuiPersonRelationInfo fromEntity(LuiPersonRelation entity) {
        LuiPersonRelationInfo personRelationInfo = new LuiPersonRelationInfo();
        entityToDto(entity, personRelationInfo);
        return personRelationInfo;
    }

    @Override
    public List<LuiPersonRelationInfo> fromEntities(List<LuiPersonRelation> entities) {
        List<LuiPersonRelationInfo> dtos = new ArrayList<LuiPersonRelationInfo>();
        for (LuiPersonRelation personRelation : entities) {
            dtos.add(fromEntity(personRelation));
        }
        return dtos;
    }

    @Override
    public void entityToDto(LuiPersonRelation entity, LuiPersonRelationInfo dto) {
        dto.setId(entity.getId() + "");
        dto.setLuiId(entity.getLuiId());
        dto.setPersonId(entity.getPersonId());
        dto.setEffectiveDate(entity.getEffectiveDate());
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setDynamicAttributes(dynamicAttributeConverter.fromEntities(entity.getDynamicAttributes()));
        dto.setState(personRelationStateConverter.fromEntity(entity.getPersonRelationState()));
        dto.setType(personRelationTypeConverter.fromEntity(entity.getPersonRelationType()));
    }

    @Override
    public void dtoToEntity(LuiPersonRelationInfo dto, LuiPersonRelation entity) {
        entity.setId(Long.valueOf(dto.getId()));
        entity.setLuiId(dto.getLuiId());
        entity.setPersonId(dto.getPersonId());
        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setDynamicAttributes(dynamicAttributeConverter.fromDtos(dto.getDynamicAttributes()));
        entity.setPersonRelationState(personRelationStateConverter.fromDto(dto.getState()));
        entity.setPersonRelationType(personRelationTypeConverter.fromDto(dto.getType()));
    }

    public void setDynamicAttributeConverter(Converter<DynamicAttribute, DynamicAttributeInfo> dynamicAttributeConverter) {
        this.dynamicAttributeConverter = dynamicAttributeConverter;
    }

    public void setPersonRelationStateConverter(SimpleConverter<LuiPersonRelationState, LuiPersonRelationStateInfo> personRelationStateConverter) {
        this.personRelationStateConverter = personRelationStateConverter;
    }

    public void setPersonRelationTypeConverter(SimpleConverter<LuiPersonRelationType, LuiPersonRelationTypeInfo> personRelationTypeConverter) {
        this.personRelationTypeConverter = personRelationTypeConverter;
    }
}
