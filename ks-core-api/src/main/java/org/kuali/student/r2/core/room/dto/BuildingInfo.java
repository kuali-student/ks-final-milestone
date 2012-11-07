package org.kuali.student.r2.core.room.dto;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.room.infc.Building;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//import org.w3c.dom.Element;

/**
 * Created with IntelliJ IDEA.
 * User: Gordon
 * Date: 11/5/12
 * Time: 11:05 AM
 *
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BuildingInfo", propOrder = {"id", "typeKey", "stateKey",
        "name", "descr", "buildingCode", "campusId", "meta", "attributes"})//, "_futureElements" }) TODO KSCM-372: Non-GWT translatable code})

public class BuildingInfo extends IdEntityInfo implements Building, Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String buildingCode;

    @XmlElement
    private String campusId;

//    TODO KSCM-372: Non-GWT translatable code
//    @XmlAnyElement
//    private List<Element> _futureElements;

    public BuildingInfo() {
        super();
    }

    public BuildingInfo(Building building) {
        super(building);
        if (null != building) {
            this.setBuildingCode( building.getBuildingCode() );
            this.setCampusId( building.getCampusId() );
        }
    }

    /**
     * Returns the code assigned to the building (ex "ARM")
     *
     * @return
     */
    @Override
    public String getBuildingCode() {
        return this.buildingCode;  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * Sets the code assigned to the building (ex "ARM)
     *
     * @param buildingCode
     * @return
     */
    @Override
    public void setBuildingCode(String buildingCode) {
        this.buildingCode = buildingCode;
    }

    /**
     * Returns the id of the campus record associated with the building
     *
     * @return
     */
    @Override
    public String getCampusId() {
        return this.campusId;
    }

    /**
     * Sets the id of the campus record associated with the building
     *
     * @param campusId
     * @return
     */
    @Override
    public void setCampusId(String campusId) {
        this.campusId = campusId;
    }
}

