package org.apache.torque.mojo;

import java.io.IOException;
import java.util.Properties;

import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.db.DatabaseCommand;
import org.kuali.db.SQLGenerator;
import org.kuali.db.Transaction;

/**
 * Does a DROP->CREATE of the database
 * 
 * @goal reset
 */
public class ResetMojo extends AbstractDBACommandMojo {

	protected Transaction getTransaction(Properties properties, DatabaseCommand command) throws IOException {
		SQLGenerator generator = new SQLGenerator(properties, url, command);
		generator.setEncoding(getEncoding());
		String sql = generator.getSQL();
		Transaction t = new Transaction();
		t.setDescription(getTransactionDescription(command));
		t.addText(sql);
		return t;
	}

	@Override
	protected void configureTransactions() throws MojoExecutionException {
		Properties properties = getContextProperties();
		try {
			transactions.add(getTransaction(properties, DatabaseCommand.DROP));
			transactions.add(getTransaction(properties, DatabaseCommand.CREATE));
		} catch (IOException e) {
			throw new MojoExecutionException("Error generating SQL", e);
		}
	}
}
