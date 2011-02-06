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
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

import org.apache.commons.collections.comparators.ReverseComparator;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.util.SimpleScanner;
import org.apache.torque.util.TransactionComparator;
import org.kuali.db.jdbc.Transaction;

/**
 * Executes SQL for creating tables, primary keys, constraints, indexes, relationships, and views inside a database.
 * Also populates the tables with a default set of data.
 * 
 * @goal import
 */
public class ImportMojo extends AbstractSQLExecutorMojo {
	private static final String FS = System.getProperty("file.separator");

	public enum Order {
		ASCENDING, DESCENDING, NONE;
	}

	/**
	 * This is the directory to scan for SQL files to import. ${targetDatabase} gets appended to this value
	 * 
	 * @parameter expression="${importDir}" default-value="${project.build.directory}/classes/sql"
	 * @required
	 */
	private File importDir;

	/**
	 * @parameter expression="${importDirIncludes}" default-value="*.sql"
	 */
	private String importDirIncludes;

	/**
	 * @parameter expression="${importDirExcludes}" default-value=""
	 */
	private String importDirExcludes = "";

	/**
	 * Set the order in which the SQL files will be executed. Possible values are <code>ASCENDING</code> and
	 * <code>DESCENDING</code> and <code>NONE</code>. Default value is <code>ASCENDING</code>
	 * 
	 * @parameter expression="${order}"
	 */
	private String order = Order.ASCENDING.toString();

	protected void updateImportDir() {
		String path = importDir.getAbsolutePath();
		if (!path.endsWith(FS)) {
			path += FS;
		}
		path += getTargetDatabase();
		importDir = new File(path);
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

	@Override
	protected void configureTransactions() throws MojoExecutionException {
		updateImportDir();
		List<File> files = new SimpleScanner(importDir, importDirIncludes, importDirExcludes).getFiles();
		transactions = getTransactions(files);
		sortTransactions(transactions);
	}

	/**
	 * Sort the transaction list.
	 */
	@SuppressWarnings("unchecked")
	protected void sortTransactions(Vector<Transaction> transactions) {
		Comparator<Transaction> comparator = new TransactionComparator<Transaction>(getProject().getArtifactId());
		if (Order.ASCENDING.toString().equals(order)) {
			Collections.sort(transactions, comparator);
		} else if (Order.DESCENDING.toString().equals(order)) {
			Collections.sort(transactions, new ReverseComparator(comparator));
		}
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

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
}
