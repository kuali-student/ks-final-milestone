package org.apache.torque.mojo;

import org.kuali.db.DatabaseCommand;

/**
 * Creates a database
 * 
 * @goal create
 */
public class CreateMojo extends SingleDatabaseAdministratorCommandMojo {

	/**
	 * The database command to execute
	 * 
	 * @parameter expression="${command}" default-value="CREATE"
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
