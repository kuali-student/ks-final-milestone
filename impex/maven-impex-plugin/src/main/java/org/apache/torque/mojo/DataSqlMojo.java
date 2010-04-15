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
import org.apache.torque.task.TorqueDataSQLTask;

/**
 * Generates SQL for populating the database from data.xml files.
 * 
 * @author Raphael Pieroni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 * 
 * @goal datasql
 * @phase generate-sources
 */
public class DataSqlMojo extends DataModelTaskMojo {
	// The following three dummies trick the Mojo Description Extractor
	// into setting the correct default values for
	// outputDir, reportFile and contextPropertiesPath
	/**
	 * The directory in which the sql will be generated.
	 * 
	 * @parameter property="outputDir" expression="${project.build.directory}/generated-sql/torque"
	 */
	private String dummy;

	/**
	 * The location where the sql file will be generated.
	 * 
	 * @parameter property="reportFile" expression="${project.artifact.artifactId}-data.sql"
	 */
	private String dummy2;

	/**
	 * The location where the context property file for velocity will be generated.
	 * 
	 * @parameter property="contextPropertiesPath"
	 *            expression="${project.build.directory}/torque/context.datasql.properties"
	 */
	private String dummy3;

	/**
	 * The schema files which should be excluded in generation (in ant-style notation).
	 * 
	 * @parameter property="schemaExcludes" expression="id-table-schema.xml"
	 */
	private String dummy4;

	/**
	 * The data Xml file to process.
	 * 
	 * @parameter
	 * @required
	 */
	private String dataXmlFile;

	/**
	 * The data dtd file for the data xml file to process.
	 * 
	 * @parameter
	 * @required
	 */
	private String dataDtd;

	/**
	 * Creates a new SQLMojo object.
	 */
	public DataSqlMojo() {
		super(new TorqueDataSQLTask());
	}

	/**
	 * Returns the context properties for the Texen task.
	 * 
	 * @return The PropertiesConfiguration containing all context properties, not null.
	 */
	protected PropertiesConfiguration getMojoContextProperties() {
		PropertiesConfiguration configuration = new PropertiesConfiguration();
		return configuration;
	}

	/**
	 * Configures the Texen task which is wrapped by this mojo. In addition to the prioerties set by
	 * DataModelTaskMojo#configureTask(), the properties dataXmlFile and dataDTD are set.
	 * 
	 * @throws MojoExecutionException
	 *             if an error occurs when setting the Tasks properties.
	 * 
	 * @see DataModelTaskMojo#configureTask()
	 */
	protected void configureTask() throws MojoExecutionException {
		super.configureTask();

		TorqueDataSQLTask task = (TorqueDataSQLTask) super.getGeneratorTask();

		task.setDataXmlFile(dataXmlFile);
		task.setDataDTD(dataDtd);
	}

	/**
	 * Returns the path to the control template.
	 * 
	 * @return "sql/load/Control.vm"
	 */
	protected String getControlTemplate() {
		return "sql/load/Control.vm";
	}

	/**
	 * Returns the data dtd file for the data xml file.
	 * 
	 * @return the data dtd file for the data xml file.
	 */
	public String getDataDtd() {
		return dataDtd;
	}

	/**
	 * Sets the data dtd file for the data xml file.
	 * 
	 * @param dataDtd
	 *            the data dtd file for the data xml file.
	 */
	public void setDataDtd(String dataDtd) {
		this.dataDtd = dataDtd;
	}

	/**
	 * Returns the data xml file which should be processed.
	 * 
	 * @return the data xml file which should be processed.
	 */
	public String getDataXmlFile() {
		return dataXmlFile;
	}

	/**
	 * Sets the data xml file which should be processed.
	 * 
	 * @param dataXmlFile
	 *            the data xml file which should be processed.
	 */
	public void setDataXmlFile(String dataXmlFile) {
		this.dataXmlFile = dataXmlFile;
	}
}
