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
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.task.TorqueDataModelTask;

/**
 * Generates a data dtd from schema files.
 * 
 * @author Raphael Pieroni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 * 
 * @goal datadtd
 * @phase generate-sources
 */
public class DataDtdMojo extends DataModelTaskMojo {
	/** The context property for the name of the project. */
	public static final String PROJECT_CONTEXT_PROPERTY = "project";

	// The following dummies trick the Mojo Description Extractor
	// into setting the correct default values for
	// outputDir, reportFile, contextPropertiesPath, schemaExcludes
	/**
	 * The directory in which the SQL will be generated.
	 * 
	 * @parameter property="outputDir" expression="${project.build.directory}/data/torque"
	 */
	private String dummy;

	/**
	 * The location where the report file will be generated, relative to outputDir.
	 * 
	 * @parameter property="reportFile"
	 *            expression="../../torque/report.${project.artifact.artifactId}.datadtd.generation"
	 */
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath"
	 *            expression="${project.build.directory}/torque/context.datadtd.properties"
	 */
	private String dummy3;

	/**
	 * The schema files which should be excluded in generation (in ant-style notation).
	 * 
	 * @parameter property="schemaExcludes" expression="id-table-schema.xml"
	 */
	private String dummy4;

	/**
	 * The name of the project, used as a prefix of the name of the datadtd.
	 * 
	 * @parameter expression="torque"
	 */
	private String projectName = null;

	/**
	 * The name of the xml file to process. Only one xml file can be processed at a time. Overrides the settings
	 * schemaIncludes and schemaExcludes
	 * 
	 * @parameter
	 * @required
	 */
	private String xmlFile = null;

	/**
	 * Creates a new SQLMojo object.
	 */
	public DataDtdMojo() {
		super(new TorqueDataModelTask());
	}

	/**
	 * Returns the context properties for the Texen task.
	 * 
	 * @return The PropertiesConfiguration containing all context properties, not null.
	 */
	protected PropertiesConfiguration getMojoContextProperties() {
		PropertiesConfiguration configuration = new PropertiesConfiguration();
		configuration.addProperty(PROJECT_CONTEXT_PROPERTY, projectName);
		return configuration;
	}

	/**
	 * Configures the Texen task which is wrapped by this mojo. In this implementation, the xml file is set in addition
	 * to the properties set by DataModelTaskMojo#configureTask().
	 * 
	 * @throws MojoExecutionException
	 *             if an error occurs when setting the Tasks properties.
	 * 
	 * @see DataModelTaskMojo#configureTask()
	 */
	protected void configureTask() throws MojoExecutionException {
		super.configureTask();

		TorqueDataModelTask task = (TorqueDataModelTask) super.getGeneratorTask();

		task.setXmlFile(xmlFile);
	}

	/**
	 * Returns the path to the control template.
	 * 
	 * @return "sql/Control.vm"
	 */
	protected String getControlTemplate() {
		return "data/Control.vm";
	}

	/**
	 * Returns the name of the project, which is used as prefix for the name of the datadtd.
	 * 
	 * @return the name of the project.
	 */
	public String getProjectName() {
		return projectName;
	}

	/**
	 * Sets the name of the project, which is used as prefix for the name of the datadtd.
	 * 
	 * @param project
	 *            the name of the project.
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	/**
	 * Returns the name of the xml file to process.
	 * 
	 * @return the name of the xml file to process.
	 */
	public String getXmlFile() {
		return xmlFile;
	}

	/**
	 * Sets the name of the xml file to process.
	 * 
	 * @param project
	 *            the name of the xml file to process.
	 */
	public void setXmlFile(String xmlFile) {
		this.xmlFile = xmlFile;
	}
}
