package org.kuali.student.r2.core.queue.infc;

import java.util.Date;
import java.util.List;

public interface QueueEntry {

	
	public String getEntryRefObjectId(); 

	public String getEntryRefObjectTypeKey();
	
	public Date getCheckInDate(); 

	public Integer getPosition();

}
