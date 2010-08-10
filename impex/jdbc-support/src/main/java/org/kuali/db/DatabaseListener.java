package org.kuali.db;

/**
 * Listens for Database events
 */
public interface DatabaseListener {
	public void messageLogged(DatabaseEvent event);

	public void beginTransaction(DatabaseEvent event);

	public void finishTransaction(DatabaseEvent event);
}
