package org.apache.torque.mojo;

import java.io.IOException;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.db.jdbc.DatabaseCommand;
import org.kuali.db.jdbc.SQLGenerator;
import org.kuali.db.jdbc.Transaction;

/**
 * Runs a command that performs a single operation on a database (create,drop etc)
 */
public abstract class SingleDBACommandMojo extends AbstractDBACommandMojo {

	public abstract String getCommand();

	@Override
	protected void configureTransactions() throws MojoExecutionException {
		Properties properties = getContextProperties();
		SQLGenerator generator = new SQLGenerator(properties, url, DatabaseCommand.valueOf(getCommand().toUpperCase()));
		try {
			generator.setEncoding(getEncoding());
			String sql = generator.getSQL();
			Transaction t = new Transaction();
			t.addText(sql);
			t.setDescription(getTransactionDescription(DatabaseCommand.valueOf(getCommand().toUpperCase())));
			transactions.add(t);
		} catch (IOException e) {
			throw new MojoExecutionException("Error configuring transactions", e);
		}
	}
}
