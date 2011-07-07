package org.kuali.student.r2.core.collection.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.collection.infc.AbstractEntitiesCollection;
import org.kuali.student.r2.core.collection.infc.CollectionEntry;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AbstractEntitiesCollectionInfo", propOrder = {"id", "typeKey", "stateKey", "effectiveDate", "expirationDate",
        "collectionEntries",  "refObjectId", "meta", "attributes", "_futureElements"})

public class AbstractEntitiesCollectionInfo extends IdEntityInfo implements
		AbstractEntitiesCollection, Serializable {

	private static final long serialVersionUID = 1L;

	private List<CollectionEntry> collectionEntries; 
	
	private   String refObjectId;
	
	@Override
	public List<CollectionEntry> getCollectionEntries() {
		return collectionEntries;
	}

	@Override
	public String getRefObjectId() {
		return refObjectId;
	}

	public void setCollectionEntries(List<CollectionEntry> collectionEntries) {
		this.collectionEntries = collectionEntries;
	}

	public void setRefObjectId(String refObjectId) {
		this.refObjectId = refObjectId;
	}

}
