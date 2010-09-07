package org.apache.torque.mojo;

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
	String command;

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

}
