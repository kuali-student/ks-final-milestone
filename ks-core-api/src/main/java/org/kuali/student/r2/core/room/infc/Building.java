package org.kuali.student.r2.core.room.infc;

import org.kuali.student.r2.common.infc.IdEntity;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Gordon
 * Date: 11/5/12
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Building extends IdEntity {

    /**
     * Returns the code assigned to the building (ex "ARM")
     *
     * @return
     */
    public String getBuildingCode();

    /**
     * Sets the code assigned to the building (ex "ARM)
     *
     * @param buildingCode
     * @return
     */
    public void setBuildingCode(String buildingCode);

    /**
     * Returns the id of the campus record associated with the building
     *
     * @return
     */
    public String getCampusId();

    /**
     * Sets the id of the campus record associated with the building
     *
     * @param campusId
     * @return
     */
    public void setCampusId(String campusId);

}