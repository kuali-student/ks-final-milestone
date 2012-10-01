package org.kuali.student.lum.program.client.permissions;

import org.kuali.student.common.ui.client.mvc.DataModel;
import org.kuali.student.lum.program.client.ProgramConstants;
import org.kuali.student.lum.program.client.ProgramStatus;

/**
 * @author Igor
 */
public enum ModelPermissionType implements IModelPermission {
    DRAFT_STATUS {
        @Override
        public boolean check(DataModel dataModel) {
            ProgramStatus programStatus = ProgramStatus.of((String) dataModel.get(ProgramConstants.STATE));
            return programStatus != ProgramStatus.DRAFT;
        }
    };

    public abstract boolean check(DataModel dataModel);
}
