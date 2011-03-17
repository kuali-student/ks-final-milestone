package org.kuali.student.enrollment.lpr.conversion;

/**
 * @author Igor
 */
public interface SimpleConverter<Entity, Dto> {

    Entity fromDto(Dto dto);

    Dto fromEntity(Entity dto);

    void entityToDto(Entity entity, Dto dto);

    void dtoToEntity(Dto dto, Entity entity);
}