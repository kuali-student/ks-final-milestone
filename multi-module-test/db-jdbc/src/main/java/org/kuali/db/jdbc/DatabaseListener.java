package org.kuali.db.jdbc;

/**
 * Listens for Database events
 */
public interface DatabaseListener {
	public void messageLogged(DatabaseEvent event);

	public void beginTransaction(DatabaseEvent event);

	public void finishTransaction(DatabaseEvent event);

	public void beforeExecuteSQL(DatabaseEvent event);

	public void afterExecuteSQL(DatabaseEvent event);

	public void afterProcessingSQLResults(DatabaseEvent event);
}
