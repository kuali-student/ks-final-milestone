package org.kuali.student.lum.lu.bo;

import javax.persistence.Column;

import org.kuali.student.core.bo.KsBusinessObjectBase;

public class CluCampusLocation extends KsBusinessObjectBase {

	private static final long serialVersionUID = -3035956537786940766L;
	
	@Column(name = "CAMP_LOC")
    private String campusLocation; 

    private String cluId;

    
	public String getCampusLocation() {
		return campusLocation;
	}

	public void setCampusLocation(String campusLocation) {
		this.campusLocation = campusLocation;
	}

	public String getCluId() {
		return cluId;
	}

	public void setCluId(String cluId) {
		this.cluId = cluId;
	}

}
