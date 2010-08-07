package org.kuali.db;

/**
 * 
 * 
 */
public interface DatabaseListener {
	public void messageLogged(DatabaseEvent event);

	public void beginTransaction(DatabaseEvent event);

	public void finishTransaction(DatabaseEvent event);
}
