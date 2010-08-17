package org.apache.torque.mojo;

import java.io.IOException;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.db.DatabaseCommand;
import org.kuali.db.DefaultSQLGenerator;
import org.kuali.db.Transaction;

/**
 * Drops a database
 * 
 * @goal drop
 */
public class DropMojo extends AbstractSQLExecutorMojo {

	/**
	 * The database command to execute
	 * 
	 * @parameter expression="${command}" default-value="DROP"
	 * @required
	 */
	DatabaseCommand command;

	/**
	 * The schema to drop
	 * 
	 * @parameter expression="${schema}"
	 * @required
	 */
	String schema;

	@Override
	public void execute() throws MojoExecutionException {
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	@Override
	protected void configureTransactions() throws MojoExecutionException {
		Properties properties = getProperties();
		properties.put("schema", getSchema());
		DefaultSQLGenerator generator = new DefaultSQLGenerator(properties, url, command);
		try {
			String sql = generator.getSQL();
			Transaction t = new Transaction();
			t.addText(sql);
			transactions.add(t);
		} catch (IOException e) {
			throw new MojoExecutionException("Error generating SQL", e);
		}
	}

	public DatabaseCommand getCommand() {
		return command;
	}

	public void setCommand(DatabaseCommand command) {
		this.command = command;
	}

}
