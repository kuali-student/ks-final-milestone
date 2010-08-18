package org.apache.torque.mojo;

import java.io.IOException;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.db.DatabaseCommand;
import org.kuali.db.SQLGenerator;
import org.kuali.db.Transaction;

import static org.apache.commons.lang.StringUtils.*;

/**
 * Drops a database
 * 
 * @goal drop
 */
public class DropMojo extends AbstractSQLExecutorMojo {

	/**
	 * If true, use the artifactId as the schema to drop. See also the trimArtifactId setting
	 * 
	 * @parameter expression="${useArtifactIdForDatabaseName}" default-value="true"
	 * @required
	 */
	boolean useArtifactIdForDatabaseName;

	/**
	 * The database command to execute
	 * 
	 * @parameter expression="${command}" default-value="DROP"
	 * @required
	 */
	DatabaseCommand command;

	/**
	 * The database to drop
	 * 
	 * @parameter expression="${database}"
	 * @required
	 */
	String database;

	protected void updateConfiguration() {
		super.updateConfiguration();
		if (!isEmpty(database)) {
			return;
		}
		if (!useArtifactIdForDatabaseName) {
			return;
		}
		if (trimArtifactId) {
			database = trimArtifactId(project.getArtifactId());
		} else {
			database = project.getArtifactId();
		}
	}

	protected void validateConfiguration() throws MojoExecutionException {
		super.validateConfiguration();
		if (isEmpty(database)) {
			throw new MojoExecutionException("Must specify a database to drop!");
		}

		if (isEmpty(username) || isEmpty(password)) {
			throw new MojoExecutionException("username and password must be specified to drop a database");
		}
	}

	@Override
	protected void configureTransactions() throws MojoExecutionException {
		Properties properties = getContextProperties();
		properties.put("database", getDatabase());
		SQLGenerator generator = new SQLGenerator(properties, url, command);
		try {
			generator.setEncoding(getEncoding());
			String sql = generator.getSQL();
			Transaction t = new Transaction();
			t.addText(sql);
			transactions.add(t);
		} catch (IOException e) {
			throw new MojoExecutionException("Error generating SQL", e);
		}
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String schema) {
		this.database = schema;
	}

	public DatabaseCommand getCommand() {
		return command;
	}

	public void setCommand(DatabaseCommand command) {
		this.command = command;
	}

}
