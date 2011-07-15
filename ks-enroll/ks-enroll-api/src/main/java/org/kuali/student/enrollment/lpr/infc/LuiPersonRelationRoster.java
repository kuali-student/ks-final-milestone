
package org.kuali.student.enrollment.lpr.infc;

import java.util.List;
import org.kuali.student.r2.common.infc.IdEntity;

/**
 * LuiPersonRelationRoster is a collection or group of LPRs which are used to
 * model a grade roster at the class I level. The LPRs in the roster will always
 * belong to of a particular course or section within the course. The associated
 * LUIs are a way to show multiple levels of learning units that a roster can be
 * made of. A LuiPersonRelationRoster can also be used to model any other
 * student group concept such as a study group.
 * 
 * @author Kuali Student Team (sambit)
 */
public interface LuiPersonRelationRoster extends IdEntity {

    /**
     * This method gets the IDs of the associated LUIs in the
     * {@link LuiPersonRelationRoster}
     * 
     * @return
     */
    public List<String> getAssociatedLuiIds();

    /**
     * This method gets the LPRs that the roster is composed of 
     * 
     * @return
     */
    public List<String> getLprIds();
}
