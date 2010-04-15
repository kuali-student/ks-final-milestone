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
import org.apache.torque.task.TorqueSQLTask;

/**
 * Creates html or anakia documentation for the data model from the
 * schema.xml files.
 *
 * @author Raphael Pieroni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 *
 * @goal documentation
 * @phase generate-sources
 */
public class DocumentationMojo extends DataModelTaskMojo
{
    /** The context property for the output format. */
    public static final String OUTPUT_FORMAT_CONTEXT_PROPERTY
        = "outputFormat";

    // The following dummies trick the Mojo Description Extractor
    // into setting the correct default values for
    // outputDir, reportFile, contextPropertiesPath, schemaExcludes
    /**
     * The directory in which the documentation will be generated
     *
     * @parameter property="outputDir"
     *            expression="${project.build.directory}/generated-docs/torque"
     */
    private String dummy;

    /**
     * The location where the report file will be generated.
     *
     * @parameter property="reportFile"
     *            expression="../../torque/report.${project.artifact.artifactId}.doc.generation"
     */
    private String dummy2;

    /**
     * The location where the context property file for velocity will be
     * generated.
     *
     * @parameter property="contextPropertiesPath"
     *            expression="${project.build.directory}/torque/context.doc.properties"
     */
    private String dummy3;

    /**
     * The schema files which should be excluded in generation
     * (in ant-style notation).
     *
     * @parameter property="schemaExcludes" expression="id-table-schema.xml"
     */
    private String dummy4;

    /**
     * The format of the generated documentation. Can be either html or anakia.
     *
     * @parameter expression="html"
     */
    private String outputFormat;

    /**
     * Creates a new SQLMojo object.
     */
    public DocumentationMojo()
    {
        super(new TorqueSQLTask());
    }

    /**
     * Sets the output format of the documentation (html or anakia)
     *
     * @param outputFormat the output format of the documentation.
     */
    public void setOutputFormat(String outputFormat)
    {
        this.outputFormat = outputFormat;
    }

    /**
     * Returns the output format of the documentation (html or anakia)
     *
     * @return the output format of the documentation.
     */
    public String getOutputFormat()
    {
        return outputFormat;
    }

    /**
     * Returns the context properties for the Texen task.
     *
     * @return The PropertiesConfiguration containing all context properties,
     *         not null.
     */
    protected PropertiesConfiguration getMojoContextProperties()
    {
        PropertiesConfiguration configuration = new PropertiesConfiguration();
        configuration.addProperty(
                OUTPUT_FORMAT_CONTEXT_PROPERTY,
                getOutputFormat());
        return configuration;
    }

    /**
     * Returns the path to the control template.
     *
     * @return "doc/Control.vm"
     */
    protected String getControlTemplate()
    {
        return "doc/Control.vm";
    }
}
