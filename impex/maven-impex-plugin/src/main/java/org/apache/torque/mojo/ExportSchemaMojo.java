package org.apache.torque.mojo;

import org.kuali.core.db.torque.KualiTorqueSchemaDumpTask;

/**
 * Export a description of a database to XML. This generates DDL information. The tables, primary keys, foreign keys,
 * indexes, sequences, and views. It does not export data.
 * 
 * @goal exportschema
 * @phase generate-sources
 */
public class ExportSchemaMojo extends AntTaskMojo {

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
	 * The XML file contains a name attribute for the schema being exported. This value is what will end up there
	 * 
	 * @parameter expression="${schemaXMLName}" default-value="${project.artifactId}"
	 * @required
	 */
	private String schemaXMLName;

	/**
	 * Creates a new SQLMojo object.
	 */
	public ExportSchemaMojo() {
		super(new KualiTorqueSchemaDumpTask());
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

	public String getSchemaXMLName() {
		return schemaXMLName;
	}

	public void setSchemaXMLName(String schemaXMLName) {
		this.schemaXMLName = schemaXMLName;
	}

}
