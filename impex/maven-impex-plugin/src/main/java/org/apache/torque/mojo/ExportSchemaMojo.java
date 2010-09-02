package org.apache.torque.mojo;

import org.apache.maven.plugin.MojoExecutionException;
import org.kuali.core.db.torque.KualiTorqueSchemaDumpTask;

/**
 * Export a description of a database to XML. This generates DDL information. Tables, primary keys, foreign keys,
 * indexes, sequences, and views. It does not export data.
 * 
 * @goal exportschema
 * @phase generate-sources
 */
public class ExportSchemaMojo extends ExportMojo {

	/**
	 * Flag indicating whether or not tables will be processed. Default is true
	 * 
	 * @parameter expression="${processTables}" default-value="true"
	 */
	private boolean processTables;

	/**
	 * Flag indicating whether or not views will be processed. Default is true
	 * 
	 * @parameter expression="${processViews}" default-value="true"
	 */
	private boolean processViews;

	/**
	 * Flag indicating whether or not sequences will be processed. Default is true
	 * 
	 * @parameter expression="${processSequences}" default-value="true"
	 */
	private boolean processSequences;

	/**
	 * The file that the schema XML will get written to
	 * 
	 * @parameter expression="${schemaXMLFile}" default-value="${basedir}/src/main/impex/${project.artifactId}.xml"
	 * @required
	 */
	private String schemaXMLFile;

	/**
	 * Configure the Ant task
	 */
	protected void configureTask() throws MojoExecutionException {
		KualiTorqueSchemaDumpTask task = new KualiTorqueSchemaDumpTask();
		setAntTask(task);
		super.configureTask();
	}

	public boolean isProcessTables() {
		return processTables;
	}

	public void setProcessTables(boolean processTables) {
		this.processTables = processTables;
	}

	public boolean isProcessViews() {
		return processViews;
	}

	public void setProcessViews(boolean processViews) {
		this.processViews = processViews;
	}

	public boolean isProcessSequences() {
		return processSequences;
	}

	public void setProcessSequences(boolean processSequences) {
		this.processSequences = processSequences;
	}

	public String getSchemaXMLFile() {
		return schemaXMLFile;
	}

	public void setSchemaXMLFile(String schemaXMLFile) {
		this.schemaXMLFile = schemaXMLFile;
	}

}
