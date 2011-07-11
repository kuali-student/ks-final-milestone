package org.kuali.student.r2.core.collection.infc;

import java.util.List;


public interface EntitiesCollection {

	public List<CollectionEntry> getCollectionEntries();
		
	public String getRefObjectId();
	
	public String getRefObjectTypeKey();

		
}
