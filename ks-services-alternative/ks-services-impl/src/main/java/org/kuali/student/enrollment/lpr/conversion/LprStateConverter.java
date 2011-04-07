package org.kuali.student.enrollment.lpr.conversion;

import org.kuali.student.enrollment.lpr.dto.DynamicAttributeInfo;
import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.model.DynamicAttribute;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelationState;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
public class LprStateConverter implements Converter<LuiPersonRelationState, LuiPersonRelationStateInfo> {

    private Converter<DynamicAttribute, DynamicAttributeInfo> dynamicAttributeConverter;

    @Override
    public LuiPersonRelationState fromDto(LuiPersonRelationStateInfo dto) {
        LuiPersonRelationState entity = new LuiPersonRelationState();
        dtoToEntity(dto, entity);
        return entity;
    }

    @Override
    public LuiPersonRelationStateInfo fromEntity(LuiPersonRelationState entity) {
        LuiPersonRelationStateInfo dto = new LuiPersonRelationStateInfo();
        entityToDto(entity, dto);
        return dto;
    }

    @Override
    public void entityToDto(LuiPersonRelationState entity, LuiPersonRelationStateInfo dto) {
        dto.setId(entity.getId() + "");
        dto.setName(entity.getName());
        dto.setDescr(entity.getDescription());
        dto.setEffectiveDate(entity.getEffectiveDate());
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setDynamicAttributes(dynamicAttributeConverter.fromEntities(entity.getDynamicAttributes()));
    }

    @Override
    public void dtoToEntity(LuiPersonRelationStateInfo dto, LuiPersonRelationState entity) {
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
    public List<LuiPersonRelationState> fromDtos(List<LuiPersonRelationStateInfo> dtos) {
        List<LuiPersonRelationState> entities = new ArrayList<LuiPersonRelationState>();
        for (LuiPersonRelationStateInfo dto : dtos) {
            entities.add(fromDto(dto));
        }
        return entities;
    }

    @Override
    public List<LuiPersonRelationStateInfo> fromEntities(List<LuiPersonRelationState> entities) {
        List<LuiPersonRelationStateInfo> dtos = new ArrayList<LuiPersonRelationStateInfo>();
        for (LuiPersonRelationState entity : entities) {
            dtos.add(fromEntity(entity));
        }
        return dtos;
    }
}
