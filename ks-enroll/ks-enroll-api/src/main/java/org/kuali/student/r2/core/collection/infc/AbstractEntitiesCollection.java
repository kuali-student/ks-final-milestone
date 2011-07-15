package org.kuali.student.r2.core.collection.infc;

import java.util.List;


public interface AbstractEntitiesCollection {

	public List<CollectionEntry> getCollectionEntries();
		
	public String getRefObjectId();
	
	public String getRefObjectTypeKey();

		
}
