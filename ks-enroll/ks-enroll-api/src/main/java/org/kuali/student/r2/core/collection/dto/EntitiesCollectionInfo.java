package org.kuali.student.r2.core.collection.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

import org.kuali.student.r2.common.dto.IdEntityInfo;
import org.kuali.student.r2.core.collection.infc.EntitiesCollection;
import org.kuali.student.r2.core.collection.infc.CollectionEntry;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntitiesCollectionInfo", propOrder = {"id", "typeKey", "stateKey", "name", "descr",
        "collectionEntries",  "refObjectId", "refObjectTypeKey", "meta", "attributes", "_futureElements"})

public class EntitiesCollectionInfo extends IdEntityInfo implements
		EntitiesCollection, Serializable {

	private static final long serialVersionUID = 1L;

	private List<CollectionEntry> collectionEntries; 
	
	private   String refObjectId;
	
	private String refObjectTypeKey;
	
	public void setRefObjectTypeKey(String refObjectTypeKey) {
		this.refObjectTypeKey = refObjectTypeKey;
	}

	@Override
	public String getRefObjectTypeKey() {
		return refObjectTypeKey;
	}

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
