package org.kuali.student.enrollment.lpr.conversion;

import java.util.List;

/**
 * @author Igor
 */
public interface Converter<Entity, Dto> extends SimpleConverter<Entity, Dto>{

    List<Entity> fromDtos(List<Dto> dto);

    List<Dto> fromEntities(List<Entity> dto);

    void entitiesToDtos(List<Entity> entities, List<Dto> dtos);

    void dtosToEntities(List<Dto> dtos, List<Entity> entities);
}
