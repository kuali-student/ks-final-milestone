package org.apache.torque.mojo;

import java.io.IOException;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.db.DatabaseCommand;
import org.kuali.db.SQLGenerator;
import org.kuali.db.Transaction;

/**
 * Runs a command that performs a single operation on a database (create,drop etc)
 * 
 * @goal reset
 */
public class ResetMojo extends AbstractDatabaseCommandMojo {

	protected Transaction getTransaction(Properties properties, DatabaseCommand command) throws IOException {
		SQLGenerator generator = new SQLGenerator(properties, url, command);
		generator.setEncoding(getEncoding());
		String sql = generator.getSQL();
		Transaction t = new Transaction();
		t.addText(sql);
		return t;
	}

	@Override
	protected void configureTransactions() throws MojoExecutionException {
		Properties properties = getContextProperties();
		properties.put("database", getDatabase());
		properties.put("databasePassword", getDatabasePassword());
		try {
			transactions.add(getTransaction(properties, DatabaseCommand.DROP));
			transactions.add(getTransaction(properties, DatabaseCommand.CREATE));
		} catch (IOException e) {
			throw new MojoExecutionException("Error generating SQL", e);
		}
	}
}
