package org.kuali.student.brms.repository;

import java.util.Calendar;

public interface Item 
{
	public String getName();

	public String getDescription();

	public void setCheckinComment(String checkinComment);

	public String getCheckinComment();

	public String getUUID();

	public long getVersionNumber();

	public String getStatus();

	public Calendar getCreatedDate();

	public Calendar getLastModifiedDate();
	
	public boolean isArchived();

	public void setArchived(boolean archived);

	public boolean isHistorical();
	
	/**
	 * Returns the UUID of the snapshot version.
	 * This will return null if this is the current version.
	 * Only historical version will return a UUID.
	 * 
	 * @return UUID of the historical version
	 */
	public String getVersionSnapshotUUID();
}
