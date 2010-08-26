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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.comparators.ReverseComparator;
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
		ASCENDING, DESCENDING, NONE;
	}

	/**
	 * @parameter expression="${schema}" default-value="${project.artifactId}"
	 */
	private String schema;

	/**
	 * @parameter expression="${importDir}" default-value="${project.build.directory}/generated-sql/sql"
	 * @required
	 */
	private File importDir;

	/**
	 * @parameter expression="${importDirIncludes}" default-value="*.sql"
	 */
	private String importDirIncludes = "*.sql";

	/**
	 * @parameter expression="${importDirExcludes}" default-value=""
	 */
	private String importDirExcludes = "";

	/**
	 * Set the order in which the SQL files will be executed. Possible values are <code>ASCENDING</code> and
	 * <code>DESCENDING</code> and <code>NONE</code>
	 * 
	 * @since 1.1
	 * @parameter expression="${order}" default-value="ASCENDING"
	 */
	private Order order = Order.ASCENDING;

	protected void updateImportDir() {
		String path = importDir.getAbsolutePath();
		if (!path.endsWith(FS)) {
			path += FS;
		}
		path += getTargetDatabase();
		importDir = new File(path);
	}

	protected List<File> getFiles(Fileset fileset) {
		fileset.scan();
		String[] includedFiles = fileset.getIncludedFiles();
		List<File> files = new ArrayList<File>();
		for (String includedFile : includedFiles) {
			String filename = importDir.getAbsolutePath() + FS + includedFile;
			files.add(new File(filename));
		}
		return files;
	}

	protected Vector<Transaction> getTransactions(List<File> files) {
		Vector<Transaction> transactions = new Vector<Transaction>();
		for (File file : files) {
			Transaction t = new Transaction();
			t.setResourceLocation(file.getAbsolutePath());
			transactions.add(t);
		}
		return transactions;
	}

	protected void configureTransactions() throws MojoExecutionException {
		updateImportDir();
		Fileset fileset = getFileset();
		List<File> files = getFiles(fileset);
		transactions = getTransactions(files);
		getLog().info("order=" + order);
		getLog().info("transactions.size()=" + transactions.size());
		for (Transaction t : transactions) {
			getLog().debug(t.getResourceLocation());
		}
		getLog().info("----------------------");
		sortTransactions(transactions);
		for (Transaction t : transactions) {
			getLog().debug(t.getResourceLocation());
		}
	}

	protected Fileset getFileset() {
		Fileset fileset = new Fileset();
		fileset.setBasedir(importDir);
		fileset.setExcludes(new String[] { importDirExcludes });
		fileset.setIncludes(new String[] { importDirIncludes });
		return fileset;
	}

	/**
	 * Sort the transaction list.
	 */
	@SuppressWarnings("unchecked")
	protected void sortTransactions(Vector<Transaction> transactions) {
		Comparator<Transaction> comparator = new TransactionComparator<Transaction>(schema);
		if (Order.ASCENDING.equals(order)) {
			getLog().info("sorting ascending");
			Collections.sort(transactions, comparator);
		} else if (Order.DESCENDING.equals(order)) {
			getLog().info("sorting descending");
			Collections.sort(transactions, new ReverseComparator(comparator));
		} else {
			getLog().info("no sort");
		}
	}

	public Order getOrder() {
		return this.order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public File getImportDir() {
		return importDir;
	}

	public void setImportDir(File importDirectory) {
		this.importDir = importDirectory;
	}

	public String getImportDirIncludes() {
		return importDirIncludes;
	}

	public void setImportDirIncludes(String importDirectoryIncludes) {
		this.importDirIncludes = importDirectoryIncludes;
	}

	public String getImportDirExcludes() {
		return importDirExcludes;
	}

	public void setImportDirExcludes(String importDirectoryExcludes) {
		this.importDirExcludes = importDirectoryExcludes;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}
}
