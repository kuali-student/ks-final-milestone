package org.kuali.student.enrollment.lpr.conversion;

import org.kuali.student.enrollment.lpr.dto.DynamicAttributeInfo;
import org.kuali.student.enrollment.lpr.model.DynamicAttribute;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
public class DynamicAttributeConverter implements Converter<DynamicAttribute, DynamicAttributeInfo> {

    @Override
    public List<DynamicAttribute> fromDtos(List<DynamicAttributeInfo> dtos) {
        List<DynamicAttribute> entities = new ArrayList<DynamicAttribute>();
        for (DynamicAttributeInfo dto : dtos) {
            entities.add(fromDto(dto));
        }
        return entities;
    }

    @Override
    public List<DynamicAttributeInfo> fromEntities(List<DynamicAttribute> entities) {
        List<DynamicAttributeInfo> dtos = new ArrayList<DynamicAttributeInfo>();
        for (DynamicAttribute entity : entities) {
            dtos.add(fromEntity(entity));
        }
        return dtos;
    }

    @Override
    public DynamicAttribute fromDto(DynamicAttributeInfo dto) {
        DynamicAttribute entity = new DynamicAttribute();
        dtoToEntity(dto, entity);
        return entity;
    }

    @Override
    public DynamicAttributeInfo fromEntity(DynamicAttribute entity) {
        DynamicAttributeInfo dto = new DynamicAttributeInfo();
        entityToDto(entity, dto);
        return dto;
    }

    @Override
    public void entityToDto(DynamicAttribute entity, DynamicAttributeInfo dto) {
        dto.setId(entity.getId());
        dto.setKey(entity.getKey());
        dto.setValue(entity.getValue());
    }

    @Override
    public void dtoToEntity(DynamicAttributeInfo dto, DynamicAttribute entity) {
        entity.setId(dto.getId());
        entity.setKey(dto.getKey());
        entity.setValue(dto.getValue());
    }
}
