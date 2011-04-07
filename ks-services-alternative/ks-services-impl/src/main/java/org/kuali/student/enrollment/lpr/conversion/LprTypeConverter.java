package org.kuali.student.enrollment.lpr.conversion;

import org.kuali.student.enrollment.lpr.dto.DynamicAttributeInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.model.DynamicAttribute;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelationType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
public class LprTypeConverter implements Converter<LuiPersonRelationType, LuiPersonRelationTypeInfo> {

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

    @Override
    public List<LuiPersonRelationType> fromDtos(List<LuiPersonRelationTypeInfo> dtos) {
        List<LuiPersonRelationType> entities = new ArrayList<LuiPersonRelationType>();
        for (LuiPersonRelationTypeInfo dto : dtos) {
            entities.add(fromDto(dto));
        }
        return entities;
    }

    @Override
    public List<LuiPersonRelationTypeInfo> fromEntities(List<LuiPersonRelationType> entities) {
        List<LuiPersonRelationTypeInfo> dtos = new ArrayList<LuiPersonRelationTypeInfo>();
        for (LuiPersonRelationType entity : entities) {
            dtos.add(fromEntity(entity));
        }
        return dtos;
    }
}
