package org.kuali.student.enrollment.lpr.conversion;

import org.kuali.student.enrollment.lpr.dto.LuiPersonRelationStateInfo;
import org.kuali.student.enrollment.lpr.model.LuiPersonRelationState;

/**
 * @author Igor
 */
public class PersonRelationStateConverter implements SimpleConverter<LuiPersonRelationState, LuiPersonRelationStateInfo> {

    @Override
    public LuiPersonRelationState fromDto(LuiPersonRelationStateInfo personRelationStateInfo) {
        return null;
    }

    @Override
    public LuiPersonRelationStateInfo fromEntity(LuiPersonRelationState personRelationState) {
        return null;
    }

    @Override
    public void entityToDto(LuiPersonRelationState personRelationState, LuiPersonRelationStateInfo personRelationStateInfo) {

    }

    @Override
    public void dtoToEntity(LuiPersonRelationStateInfo personRelationStateInfo, LuiPersonRelationState personRelationState) {

    }
}
