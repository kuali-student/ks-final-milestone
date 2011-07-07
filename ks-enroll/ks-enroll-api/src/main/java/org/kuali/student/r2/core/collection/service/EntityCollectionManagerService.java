package org.kuali.student.r2.core.collection.service;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

import org.kuali.student.r2.common.dto.StatusInfo;
import org.kuali.student.r2.core.collection.dto.AbstractEntitiesCollectionInfo;
import org.kuali.student.r2.core.collection.dto.CollectionEntryInfo;

/**
 * 
 * This is a description of what this class does - sambitpatnaik don't forget to fill this in. 
 * 
 * @author Kuali Student Team (Sambit)
 *
 */
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)


public interface EntityCollectionManagerService {

	public StatusInfo addEntryToCollection(String collectionEntryId, String collectionId);
	public StatusInfo removeFromCollection(String collectionEntryId);
	public  CollectionEntryInfo getHighestRankedEntry();
	public CollectionEntryInfo createEntry();
	public StatusInfo deleteEntry(String collectionEntryId);
	public AbstractEntitiesCollectionInfo createCollection(AbstractEntitiesCollectionInfo abstractEntitiesCollection);
	public StatusInfo deleteCollection(String collectionId);
	
	
}
