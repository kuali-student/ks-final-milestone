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
import java.util.Collections;

import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.db.Transaction;

/**
 * Executes SQL for creating a database and populating it with a dataset
 * 
 * @goal import
 */
public class ImportMojo extends AbstractSQLExecutorMojo {
	private static final String FS = System.getProperty("file.separator");

	public enum Order {
		ASCENDING, DESCENDING;
	}

	/**
	 * @parameter expression="${schema}" default-value="${project.artifactId"
	 */
	private String schema;

	/**
	 * @parameter expression="${importDirectory}" default-value="${project.build.directory}/generated-sql/sql"
	 * @required
	 */
	private File importDirectory;

	/**
	 * @parameter expression="${importDirectoryIncludes}" default-value="*.sql"
	 */
	private String importDirectoryIncludes = "*.sql";

	/**
	 * @parameter expression="${importDirectoryExcludes}" default-value=""
	 */
	private String importDirectoryExcludes = "";

	/**
	 * Set the order in which the SQL files will be executed. Possible values are <code>ascending</code> and
	 * <code>descending</code>. Any other value means that no sorting will be performed.
	 * 
	 * @since 1.1
	 * @parameter expression="${order}" default-value="ASCENDING"
	 */
	private Order order;

	protected void configureTransactions() throws MojoExecutionException {
		String path = importDirectory.getAbsolutePath();
		if (!path.endsWith(FS)) {
			path += FS;
		}
		path += getTargetDatabase();
		importDirectory = new File(path);
		getLog().info("importDirectory=" + importDirectory.getAbsolutePath());
		Fileset fileset = getFileset();
		fileset.scan();
		String[] includedFiles = fileset.getIncludedFiles();
		for (String includedFile : includedFiles) {
			String filename = importDirectory.getAbsolutePath() + FS + includedFile;
			Transaction t = new Transaction();
			t.setResourceLocation(filename);
			transactions.add(t);
		}
		sortTransactions();
	}

	protected Fileset getFileset() {
		Fileset fileset = new Fileset();
		fileset.setBasedir(importDirectory);
		fileset.setExcludes(new String[] { importDirectoryExcludes });
		fileset.setIncludes(new String[] { importDirectoryIncludes });
		return fileset;
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

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public File getImportDirectory() {
		return importDirectory;
	}

	public void setImportDirectory(File importDirectory) {
		this.importDirectory = importDirectory;
	}

	public String getImportDirectoryIncludes() {
		return importDirectoryIncludes;
	}

	public void setImportDirectoryIncludes(String importDirectoryIncludes) {
		this.importDirectoryIncludes = importDirectoryIncludes;
	}

	public String getImportDirectoryExcludes() {
		return importDirectoryExcludes;
	}

	public void setImportDirectoryExcludes(String importDirectoryExcludes) {
		this.importDirectoryExcludes = importDirectoryExcludes;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
}
