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
import org.apache.torque.task.TorqueSQLTask;

/**
 * Generates SQL for creating the database from the schema.xml files.
 *
 * @author Raphael Pieroni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 * @author <a href="kannegiesser@synyx.de">Marc Kannegiesser</a>
 *
 * @goal sql
 * @phase generate-sources
 */
public class SqlMojo extends SqlMojoBase
{
    // The following three dummies trick the Mojo Description Extractor
    // into setting the correct default values for
    // outputDir, reportFile, contextPropertiesPath, schemaExcludes, suffix.
    /**
     * The directory in which the SQL will be generated.
     *
     * @parameter property="outputDir"
     *            expression="${project.build.directory}/generated-sql/torque"
     */
    private String dummy;

    /**
     * The location where the report file will be generated.
     *
     * @parameter property="reportFile"
     *            expression="../../torque/report.${project.artifact.artifactId}.sql.generation"
     */
    private String dummy2;

    /**
     * The location where the context property file for velocity will be
     * generated.
     *
     * @parameter property="contextPropertiesPath"
     *            expression="${project.build.directory}/torque/context.sql.properties"
     */
    private String dummy3;

    /**
     * The schema files which should be excluded in generation
     * (in ant-style notation).
     *
     * @parameter property="schemaExcludes" expression=""
     */
    private String dummy4;

    /**
     * The suffix of the generated sql files.
     *
     * @parameter property="suffix"
     *            expression=""
     */
    private String dummy5;
}
