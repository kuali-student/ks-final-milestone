package org.apache.torque.mojo;

import java.io.IOException;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.db.DatabaseCommand;
import org.kuali.db.SQLGenerator;
import org.kuali.db.Transaction;

/**
 * Drops a database
 * 
 * @goal drop
 */
public class DropMojo extends AbstractSQLExecutorMojo {

	/**
	 * If true, use the artifactId as the schema to drop. See also the trimArtifactId setting
	 * 
	 * @parameter expression="${useArtifactIdForSchema}" default-value="true"
	 * @required
	 */
	boolean useArtifactIdForSchema;

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

	protected void updateConfiguration() {
		super.updateConfiguration();
		if (schema != null) {
			return;
		}
		if (!useArtifactIdForCredentials) {
			return;
		}
		if (trimArtifactId) {
			schema = trimArtifactId(project.getArtifactId());
		} else {
			schema = project.getArtifactId();
		}
	}

	protected void validateConfiguration() throws MojoExecutionException {
		super.validateConfiguration();
		Validate.isTrue(!StringUtils.isEmpty(schema));
	}

	@Override
	protected void configureTransactions() throws MojoExecutionException {
		Properties properties = getContextProperties();
		properties.put("schema", getSchema());
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

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public DatabaseCommand getCommand() {
		return command;
	}

	public void setCommand(DatabaseCommand command) {
		this.command = command;
	}

}
