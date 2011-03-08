package org.kuali.student.enrollment.lpr.conversion;

import java.util.List;

/**
 * @author Igor
 */
public interface Converter<Entity, Dto> {
    Entity fromDto(Dto dto);

    List<Entity> fromDtos(List<Dto> dto);

    Dto fromEntity(Entity dto);

    List<Dto> fromEntities(List<Entity> dto);

    void entityToDto(Entity entity, Dto dto);

    void dtoToEntity(Dto dto, Entity entity);
}
