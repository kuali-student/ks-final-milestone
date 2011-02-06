package org.kuali.db.jdbc;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;

import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import static org.apache.commons.lang.StringUtils.*;

/**
 * Contains the definition of a new transaction element. Transactions allow several files or blocks of statements to be
 * executed using the same JDBC connection and commit operation in between.
 */
public class Transaction {
	String resourceLocation;
	String sqlCommand;
	String encoding;
	String description;

	/**
     *
     */
	public void addText(String sql) {
		if (sqlCommand == null) {
			sqlCommand = sql;
		} else {
			sqlCommand += sql;
		}
	}

	public Reader getReader() throws IOException {
		// The SQL for this transaction is contained in a String
		if (!isEmpty(sqlCommand)) {
			return new StringReader(sqlCommand);
		}

		// If both sqlCommand and resourceLocation are blank, we can't continue
		if (isEmpty(resourceLocation)) {
			throw new IOException("Unable to locate the SQL for this transaction");
		}

		// First check the file system
		File file = new File(resourceLocation);
		if (file.exists()) {
			return getReader(new FileInputStream(file));
		}

		// Next check Resource locations
		ResourceLoader loader = new DefaultResourceLoader();
		Resource resource = loader.getResource(resourceLocation);
		if (!resource.exists()) {
			throw new IOException("Unable to locate the SQL for this transaction");
		}
		return getReader(resource.getInputStream());
	}

	protected Reader getReader(InputStream in) throws IOException {
		if (isEmpty(getEncoding())) {
			return new InputStreamReader(in);
		} else {
			return new InputStreamReader(in, getEncoding());
		}
	}

	public String getResourceLocation() {
		return resourceLocation;
	}

	public void setResourceLocation(String resourceLocation) {
		this.resourceLocation = resourceLocation;
	}

	public String getSqlCommand() {
		return sqlCommand;
	}

	public void setSqlCommand(String sqlCommand) {
		this.sqlCommand = sqlCommand;
	}

	public String getEncoding() {
		return encoding;
	}

	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
