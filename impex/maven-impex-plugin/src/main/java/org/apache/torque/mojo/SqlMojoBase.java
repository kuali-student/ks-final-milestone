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
import org.apache.tools.ant.types.FileSet;
import org.kuali.core.db.torque.KualiTorqueSQLTask;

/**
 * Generates SQL from schema.xml files
 */
public abstract class SqlMojoBase extends DataModelTaskMojo {
	/**
	 * Creates a new SQLMojo object.
	 */
	public SqlMojoBase() {
		setAntTask(new KualiTorqueSQLTask());
	}

	/**
	 * The suffix of the generated sql files.
	 */
	private String suffix = "";

	/**
	 * Returns the context properties for the Texen task.
	 * 
	 * @return The PropertiesConfiguration containing all context properties, not null.
	 */
	protected PropertiesConfiguration getMojoContextProperties() {
		PropertiesConfiguration configuration = new PropertiesConfiguration();
		configuration.addProperty(TARGET_DATABASE_CONTEXT_PROPERTY, super.getTargetDatabase());
		return configuration;
	}

	/**
	 * Returns the path to the control template.
	 * 
	 * @return "sql/base/Control.vm"
	 */
	protected String getControlTemplate() {
		return "sql/base/Control.vm";
	}

	/**
	 * Configures the Texen task wrapped by this mojo
	 */
	protected void configureTask() throws MojoExecutionException {
		super.configureTask();

		KualiTorqueSQLTask task = (KualiTorqueSQLTask) super.getGeneratorTask();

		if (getSuffix() != null) {
			getLog().debug("Adding suffix: " + getSuffix());
			task.setSuffix(getSuffix());
		}
		FileSet fileSet = getSchemaXMLFileSet();
		task.addFileset(fileSet);
	}

	/**
	 * Sets the suffix of the generated sql files.
	 * 
	 * @param suffix
	 *            the suffix of the generated sql files.
	 */
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	/**
	 * Returns the suffix of the generated sql files.
	 * 
	 * @return the suffix of the generated sql files.
	 */
	public String getSuffix() {
		return suffix;
	}

}
