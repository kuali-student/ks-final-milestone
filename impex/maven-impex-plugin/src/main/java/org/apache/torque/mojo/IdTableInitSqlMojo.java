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

import org.apache.commons.configuration.PropertiesConfiguration;

/**
 * Generates SQL for populating the id table in the database.
 * 
 * @author Raphael Pieroni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 * @author <a href="kannegiesser@synyx.de">Marc Kannegiesser</a>
 * 
 * @goal id-table-init-sql
 * @phase generate-sources
 */
public class IdTableInitSqlMojo extends SqlMojoBase {
	public static final String INITIALID_CONTEXT_PROPERTY = "initialID";
	public static final String INITIALIDVALUE_CONTEXT_PROPERTY = "initialIDValue";
	public static final String INITIALIDSTEP_CONTEXT_PROPERTY = "initialIDStep";

	// The following dummies trick the Mojo Description Extractor
	// into setting the correct default values for
	// outputDir, reportFile, contextPropertiesPath, schemaExcludes, suffix
	/**
	 * The directory in which the SQL will be generated.
	 * 
	 * @parameter property="outputDir" expression="${project.build.directory}/generated-sql/torque"
	 */
	@SuppressWarnings("unused")
	private String dummy;

	/**
	 * The location where the report file will be generated.
	 * 
	 * @parameter property="reportFile"
	 *            expression="../../torque/report.${project.artifact.artifactId}.idtable-init-sql.generation"
	 */
	@SuppressWarnings("unused")
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath"
	 *            expression="${project.build.directory}/torque/context.idtable-init-sql.properties"
	 */
	@SuppressWarnings("unused")
	private String dummy3;

	/**
	 * The schema files which should be excluded in generation (in ant-style notation).
	 * 
	 * @parameter property="schemaExcludes" expression="id-table-schema.xml"
	 */
	@SuppressWarnings("unused")
	private String dummy4;

	/**
	 * The suffix of the idTable-sql files.
	 * 
	 * @parameter property="suffix" expression="-idtable-init"
	 */
	@SuppressWarnings("unused")
	private String dummy5;

	/**
	 * The initial ID of the idtables
	 * 
	 * @parameter expression="101"
	 */
	private String initialID;

	/**
	 * The initial IDValue of the idtable
	 * 
	 * @parameter expression="1000"
	 */
	private String initialIDValue;

	/**
	 * The initial ID Step of the idtable
	 * 
	 * @parameter expression="10"
	 */
	private String initialIDStep;

	/**
	 * Creates a new IdTableInitSqlMojo object.
	 */
	public IdTableInitSqlMojo() {
	}

	/**
	 * Sets the initial id of the idtables
	 * 
	 * @param initialID
	 *            The initial of the idtables
	 */
	public void setInitialID(String initialID) {
		this.initialID = initialID;
	}

	/**
	 * Returns the initial id of the idtable
	 * 
	 * @return initialID The initial ID of the idtables
	 */
	public String getInitialID() {
		return this.initialID;
	}

	/**
	 * Sets the initial id value of the idtables
	 * 
	 * @param initialIDValue
	 *            The initial id value of the tables
	 */
	public void setInitialIDValue(String initialIDValue) {
		this.initialIDValue = initialIDValue;
	}

	/**
	 * Returns the initial id value of the idtables
	 * 
	 * @return initialIDValue the initial id value of the idtables
	 */
	public String getInitialIDValue() {
		return this.initialIDValue;
	}

	/**
	 * Sets the initial id step of the idtables
	 * 
	 * @param initialIDStep
	 *            The initial id step of the tables
	 */
	public void setInitialIDStep(String initialIDStep) {
		this.initialIDStep = initialIDStep;
	}

	/**
	 * Returns the initial id step of the idtables
	 * 
	 * @return initialIDStep the initial id step of the idtables
	 */
	public String getInitialIDStep() {
		return this.initialIDStep;
	}

	/**
	 * Returns the context properties for the Texen task.
	 * 
	 * @return The PropertiesConfiguration containing all context properties, not null.
	 */
	protected PropertiesConfiguration getMojoContextProperties() {
		PropertiesConfiguration configuration = super.getMojoContextProperties();
		configuration.addProperty(INITIALID_CONTEXT_PROPERTY, initialID);

		configuration.addProperty(INITIALIDVALUE_CONTEXT_PROPERTY, initialIDValue);

		configuration.addProperty(INITIALIDSTEP_CONTEXT_PROPERTY, initialIDStep);
		return configuration;
	}

	/**
	 * Returns the path to the control template.
	 * 
	 * @return "sql/id-table/Control.vm"
	 */
	protected String getControlTemplate() {
		return "sql/id-table/Control.vm";
	}
}
