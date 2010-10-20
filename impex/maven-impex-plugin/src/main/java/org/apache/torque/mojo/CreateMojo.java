package org.apache.torque.mojo;

/**
 * Authenticates to a database server using credentials with DBA authority and creates an empty database along with a
 * default user for accessing the newly created database. See also <code>impex:drop</code>
 * 
 * @goal create
 */
public class CreateMojo extends SingleDBACommandMojo {

	/**
	 * The database command to execute
	 * 
	 * @parameter expression="${command}" default-value="CREATE"
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
