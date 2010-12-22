package org.apache.torque.mojo;

/**
 * Authenticates to a database server using credentials with DBA authority and drops a database along with the default
 * user that was created for accessing that database. See also <code>impex:create</code>
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
	String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
