package org.apache.torque.mojo;

import org.kuali.db.DatabaseCommand;

/**
 * Drops a database
 * 
 * @goal drop
 */
public class DropMojo extends SingleDBACommandMojo {

	/**
	 * The database command to execute
	 * 
	 * @parameter expression="${command}" default-value="DROP"
	 * @required
	 */
	DatabaseCommand command;

	public DatabaseCommand getCommand() {
		return command;
	}

	public void setCommand(DatabaseCommand command) {
		this.command = command;
	}

}
