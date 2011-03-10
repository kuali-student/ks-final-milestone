package org.kuali.student.enrollment.lpr.conversion;

import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationTypeInfo;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelationType;

/**
 * @author Igor
 */
public class PersonRelationTypeConverter implements SimpleConverter<LuiPersonRelationType, LuiPersonRelationTypeInfo>{

    @Override
    public LuiPersonRelationType fromDto(LuiPersonRelationTypeInfo luiPersonRelationTypeInfo) {
        return null;
    }

    @Override
    public LuiPersonRelationTypeInfo fromEntity(LuiPersonRelationType dto) {
        return null;
    }

    @Override
    public void entityToDto(LuiPersonRelationType luiPersonRelationType, LuiPersonRelationTypeInfo luiPersonRelationTypeInfo) {

    }

    @Override
    public void dtoToEntity(LuiPersonRelationTypeInfo luiPersonRelationTypeInfo, LuiPersonRelationType luiPersonRelationType) {

    }
}
