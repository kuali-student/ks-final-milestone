package org.kuali.student.r2.core.collection.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.common.infc.Entity;
import org.kuali.student.r2.core.collection.infc.CollectionEntry;

/**
 * 
 * This is a description of what this class does - sambitpatnaik don't forget to fill this in. 
 * 
 * @author Kuali Rice Team (kuali-rice@googlegroups.com)
 *
 */


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CollectionEntryInfo", propOrder = {"id", "typeKey", "stateKey", "effectiveDate", "expirationDate",
        "entryRefObjectId",  "checkInDate", "position", "meta", "attributes", "_futureElements"})

public class CollectionEntryInfo extends IdEntityInfo implements
		CollectionEntry, Serializable {
	
	private static final long serialVersionUID = 6513602532091764366L;

	private String entryRefObjectId;
	
	private Date checkInDate;
	
	private Integer position;

	public void setEntryRefObjectId(String entryRefObject) {
		this.entryRefObjectId = entryRefObjectId;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public void setPosition(Integer position) {
		this.position = position;
	}
	
	@Override
	public String getEntryRefObjectId() {
		return entryRefObjectId;
	}

	@Override
	public Date getCheckInDate() {
		return checkInDate;
	}

	@Override
	public Integer getPosition() {
		return position;
	}

}
