package org.kuali.student.enrollment.lpr.conversion;

import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationInfo;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelation;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Igor
 */
public class PersonRelationConverter implements Converter<LuiPersonRelation, LuiPersonRelationInfo> {

    @Override
    public LuiPersonRelation fromDto(LuiPersonRelationInfo luiPersonRelationInfo) {
        return null;
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
    }

    @Override
    public void dtoToEntity(LuiPersonRelationInfo luiPersonRelationInfo, LuiPersonRelation luiPersonRelation) {

    }
}
