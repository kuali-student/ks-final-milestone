package org.apache.torque.mojo;


/**
 * Creates a database
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
