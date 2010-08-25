package org.apache.torque.mojo;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.shared.filtering.MavenFileFilterRequest;
import org.apache.maven.shared.filtering.MavenFilteringException;
import org.apache.torque.engine.database.model.Database;
import org.apache.torque.engine.database.model.Table;
import org.codehaus.plexus.util.FileUtils;
import org.kuali.core.db.torque.Utils;
import org.kuali.db.Transaction;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import static org.apache.commons.lang.StringUtils.*;

/**
 * Executes SQL for creating a database and populating it with a dataset
 * 
 * @goal import
 */
public class ImportMojo extends AbstractSQLExecutorMojo {
	public enum Order {
		ASCENDING, DESCENDING;
	}

	/**
	 * The name of the schema to import
	 * 
	 * @parameter expression="${schema}" default-value="${project.artifactId}"
	 */
	private String schema;

	/**
	 * Spring style resource entries that point to one or more schema XML files
	 * 
	 * @parameter expression="${schemas}"
	 */
	private List<String> schemas;

	/**
	 * File(s) containing SQL statements to load.
	 * 
	 * @since 1.0
	 * @parameter
	 */
	private Fileset fileset;

	/**
	 * Set to false to skip importing the schema
	 * 
	 * @since 1.0
	 * @parameter expression="${importSchema}" default-value="true"
	 */
	private boolean importSchema;

	/**
	 * Set to false to skip importing data
	 * 
	 * @since 1.0
	 * @parameter expression="${importData}" default-value="true"
	 */
	private boolean importData;

	/**
	 * Set to false to skip importing the schema constraints
	 * 
	 * @since 1.0
	 * @parameter expression="${importSchemaConstraints}" default-value="true"
	 */
	private boolean importSchemaConstraints;

	/**
	 * Set the order in which the SQL files will be executed. Possible values are <code>ascending</code> and
	 * <code>descending</code>. Any other value means that no sorting will be performed.
	 * 
	 * @since 1.1
	 * @parameter expression="${orderFile}" default-value="ASCENDING"
	 */
	private Order order;

	// /////////////////////////////////////////////////////////////////////////////////////
	/**
	 * Print SQL results.
	 * 
	 * @parameter expression="${printResultSet}" default-value="false"
	 * @since 1.3
	 */
	private boolean printResultSet;

	/**
	 * Dump the SQL exection's output to a file. Default is stdout.
	 * 
	 * @parameter expression="${outputFile}"
	 * @since 1.3
	 */
	private File outputFile;

	/**
	 * @parameter expression="${outputDelimiter}" default-value=","
	 * @since 1.4
	 */
	private String outputDelimiter;

	/**
	 * Set to true if you want to filter the srcFiles using system-, user- and project properties
	 * 
	 * @parameter expression="${enableFiltering}" default-value="false"
	 * @since 1.4
	 */
	private boolean enableFiltering;

	/**
	 * Create a new Transaction object and add it to the list of transactions that will be executed
	 * 
	 * @return a new Transaction
	 */
	public Transaction createTransaction() {
		Transaction t = new Transaction();
		transactions.addElement(t);
		return t;
	}

	/**
	 * Print result sets from the statements; optional, default false
	 * 
	 * @param print
	 *            <code>true</code> to print the resultset, otherwise <code>false</code>
	 */
	public void setPrintResultSet(boolean printResultSet) {
		this.printResultSet = printResultSet;
	}

	/**
	 * Set the output file;
	 * 
	 * @param output
	 *            the output file
	 */
	public void setOutputFile(File output) {
		this.outputFile = output;
	}

	protected void configureTransactions() throws MojoExecutionException {
		getLog().debug("schemas=" + schemas);
		if (schemas != null) {
			addSchemaXMLResourcesToTransactions();
		} else if (fileset == null) {
			fileset = new Fileset();
			fileset.setBasedir(project.getBuild().getDirectory() + "/generated-sql/sql/" + getTargetDatabase());
			fileset.setIncludes(new String[] { "*.sql" });
		}

		addCommandToTransactions();
		addFilesToTransactions();
		addFileSetToTransactions();
		sortTransactions();
	}

	/**
	 * Add sql command to transactions list.
	 */
	protected void addCommandToTransactions() {
		if (!isEmpty(sqlCommand)) {
			createTransaction().addText(sqlCommand.trim());
		}
	}

	/**
	 * Add user sql fileset to transation list
	 */
	protected void addFileSetToTransactions() {
		String[] includedFiles = new String[0];
		if (fileset != null) {
			fileset.scan();
			includedFiles = fileset.getIncludedFiles();
		}

		for (int j = 0; j < includedFiles.length; j++) {
			createTransaction().setResourceLocation(new File(fileset.getBasedir(), includedFiles[j]).getAbsolutePath());
		}
	}

	protected String getDefaultSchemaLocation() {
		String schema = project.getArtifactId();
		return "classpath:" + schema + "-schema.xml";
	}

	protected boolean defaultSchemaExists() {
		DefaultResourceLoader loader = new DefaultResourceLoader();
		String s = getDefaultSchemaLocation();
		Resource resource = loader.getResource(s);
		boolean exists = resource.exists();
		if (!exists) {
			String filename = project.getBuild().getDirectory() + "/generated-impex/" + project.getArtifactId() + "-schema.xml";
			exists = new File(filename).exists();
			getLog().debug(exists + " " + filename);
		}
		getLog().debug("exists=" + exists + " " + s);
		return exists;
	}

	protected void addDefaultSchema() {
		// They supplied a list of schemas. Only update schemas in their list
		if (!isNullOrEmpty(getSchemas())) {
			return;
		}
		// The default schema does not exist
		if (!defaultSchemaExists()) {
			return;
		}
		// We are not importing a schema
		if (!isImportSchema()) {
			return;
		}

		// Add the default schema to the list
		List<String> schemas = getSchemas();
		if (schemas == null) {
			schemas = new ArrayList<String>();
		}
		schemas.add(getDefaultSchemaLocation());
		setSchemas(schemas);
	}

	protected void addSchemaXMLResourcesToTransactions() throws MojoExecutionException {
		addDefaultSchema();
		if (isNullOrEmpty(getSchemas())) {
			return;
		}

		try {
			List<Database> databases = new Utils().getDatabases(getSchemas(), getTargetDatabase());
			for (String schemaXMLResource : getSchemas()) {
				getLog().info("Adding " + schemaXMLResource);
			}
			for (Database database : databases) {
				handleDatabase(database);
			}
		} catch (IOException e) {
			throw new MojoExecutionException("Error obtaining databases: " + e);
		}
	}

	protected void handleDatabase(Database database) {
		DefaultResourceLoader loader = new DefaultResourceLoader();

		if (isImportSchema()) {
			String schemaSQL = "classpath:sql/" + getTargetDatabase() + "/" + database.getName() + "-schema.sql";
			createTransaction().setResourceLocation(schemaSQL);
		}
		if (isImportData()) {
			List<?> tables = database.getTables();
			for (Object object : tables) {
				Table table = (Table) object;
				String location = "classpath:sql/" + getTargetDatabase() + "/" + table.getName() + ".sql";
				Resource resource = loader.getResource(location);
				if (!resource.exists()) {
					getLog().debug("Skipping " + location + " because it does not exist");
					continue;
				} else {
					createTransaction().setResourceLocation(location);
					getLog().debug("Adding " + location);
				}
			}
		}
		if (isImportSchemaConstraints()) {
			String schemaConstraintsSQL = "classpath:sql/" + getTargetDatabase() + "/" + database.getName() + "-schema-constraints.sql";
			createTransaction().setResourceLocation(schemaConstraintsSQL);
		}
	}

	/**
	 * Add user input of srcFiles to transaction list.
	 * 
	 * @throws MojoExecutionException
	 */
	protected void addFilesToTransactions() throws MojoExecutionException {
		File[] files = getSrcFiles();

		MavenFileFilterRequest request = new MavenFileFilterRequest();
		request.setEncoding(encoding);
		request.setMavenSession(mavenSession);
		request.setMavenProject(project);
		request.setFiltering(enableFiltering);
		for (int i = 0; files != null && i < files.length; ++i) {
			if (files[i] != null && !files[i].exists()) {
				throw new MojoExecutionException(files[i].getPath() + " not found.");
			}

			File sourceFile = files[i];
			String basename = FileUtils.basename(sourceFile.getName());
			String extension = FileUtils.extension(sourceFile.getName());
			File targetFile = FileUtils.createTempFile(basename, extension, null);
			if (!getLog().isDebugEnabled()) {
				targetFile.deleteOnExit();
			}

			request.setFrom(sourceFile);
			request.setTo(targetFile);

			try {
				fileFilter.copyFile(request);
			} catch (MavenFilteringException e) {
				throw new MojoExecutionException(e.getMessage());
			}

			createTransaction().setResourceLocation(targetFile.getAbsolutePath());
		}
	}

	/**
	 * Sort the transaction list.
	 */
	protected void sortTransactions() {
		if (Order.ASCENDING.equals(this.order)) {
			Collections.sort(transactions);
		} else if (Order.DESCENDING.equals(this.order)) {
			Collections.sort(transactions, Collections.reverseOrder());
		}
	}

	public void setFileset(Fileset fileset) {
		this.fileset = fileset;
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public boolean isImportData() {
		return importData;
	}

	public void setImportData(boolean importData) {
		this.importData = importData;
	}

	public boolean isImportSchema() {
		return importSchema;
	}

	public void setImportSchema(boolean importSchema) {
		this.importSchema = importSchema;
	}

	public boolean isImportSchemaConstraints() {
		return importSchemaConstraints;
	}

	public void setImportSchemaConstraints(boolean importSchemaConstraints) {
		this.importSchemaConstraints = importSchemaConstraints;
	}

	public String getOutputDelimiter() {
		return outputDelimiter;
	}

	public void setOutputDelimiter(String outputDelimiter) {
		this.outputDelimiter = outputDelimiter;
	}

	public File getOutputFile() {
		return outputFile;
	}

	public boolean isPrintResultSet() {
		return printResultSet;
	}

	public List<String> getSchemas() {
		return schemas;
	}

	public void setSchemas(List<String> schemas) {
		this.schemas = schemas;
	}

	public boolean isEnableFiltering() {
		return enableFiltering;
	}

	public void setEnableFiltering(boolean enableFiltering) {
		this.enableFiltering = enableFiltering;
	}

	public Fileset getFileset() {
		return fileset;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
}
