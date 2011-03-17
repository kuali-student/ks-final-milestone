package org.kuali.student.enrollment.lpr.conversion;

import org.kuali.student.enrollment.lpr.dto.DynamicAttributeInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.model.DynamicAttribute;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelationType;

/**
 * @author Igor
 */
public class PersonRelationTypeConverter implements SimpleConverter<LuiPersonRelationType, LuiPersonRelationTypeInfo> {

    private Converter<DynamicAttribute, DynamicAttributeInfo> dynamicAttributeConverter;

    @Override
    public LuiPersonRelationType fromDto(LuiPersonRelationTypeInfo dto) {
        LuiPersonRelationType entity = new LuiPersonRelationType();
        dtoToEntity(dto, entity);
        return entity;
    }

    @Override
    public LuiPersonRelationTypeInfo fromEntity(LuiPersonRelationType entity) {
        LuiPersonRelationTypeInfo dto = new LuiPersonRelationTypeInfo();
        entityToDto(entity, dto);
        return dto;
    }

    @Override
    public void entityToDto(LuiPersonRelationType entity, LuiPersonRelationTypeInfo dto) {
        dto.setId(entity.getId() + "");
        dto.setName(entity.getName());
        dto.setDescr(entity.getDescription());
        dto.setEffectiveDate(entity.getEffectiveDate());
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setDynamicAttributes(dynamicAttributeConverter.fromEntities(entity.getDynamicAttributes()));
    }

    @Override
    public void dtoToEntity(LuiPersonRelationTypeInfo dto, LuiPersonRelationType entity) {
        entity.setId(Long.valueOf(dto.getId()));
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescr());
        entity.setEffectiveDate(dto.getEffectiveDate());
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setDynamicAttributes(dynamicAttributeConverter.fromDtos(dto.getDynamicAttributes()));
    }

    public void setDynamicAttributeConverter(Converter<DynamicAttribute, DynamicAttributeInfo> dynamicAttributeConverter) {
        this.dynamicAttributeConverter = dynamicAttributeConverter;
    }
}
