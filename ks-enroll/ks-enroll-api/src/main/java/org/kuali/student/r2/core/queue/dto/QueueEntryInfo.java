package org.kuali.student.r2.core.queue.dto;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.queue.infc.QueueEntry;


/**
 * 
* This is a description of what this class does - sambitpatnaik don't forget to fill this in. 
* 
* @author Kuali Student Team (sambitpatnaik)
*
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "QueueEntryInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "entryRefObjectId", "entryRefObjectTypeKey", "checkInDate", "position", "meta", "attributes", "_futureElements"})

public class QueueEntryInfo extends IdEntityInfo implements
		QueueEntry, Serializable {
	

	private static final long serialVersionUID = 1L;

    private String entryRefObjectId;
	
	private String entryRefObjectTypeKey;

	private Date checkInDate;
	
	private Integer position;

	public void setEntryRefObjectTypeKey(String entryRefObjectTypeKey) {
		this.entryRefObjectTypeKey = entryRefObjectTypeKey;
	}

	public void setEntryRefObjectId(String entryRefObjectId) {
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

	@Override
	public String getEntryRefObjectTypeKey() {
		return entryRefObjectTypeKey;
	}

}
