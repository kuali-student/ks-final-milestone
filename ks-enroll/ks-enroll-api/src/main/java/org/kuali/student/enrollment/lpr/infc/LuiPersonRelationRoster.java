package org.kuali.student.enrollment.lpr.infc;

import java.util.List;

import org.kuali.student.r2.common.infc.IdEntity;


public interface LuiPersonRelationRoster extends IdEntity {

    public List<String> getAssociatedLuiIds();

    /**
     * This method ...
     * 
     * @return
     */
    public List<String> getLprIds();
}
