package org.kuali.student.r2.core.queue.infc;

import java.util.List;


public interface Queue {

	public List<QueueEntry> getQueueEntries();
		
	public String getRefObjectId();
	
	public String getProcessingAlgorithm();

}
