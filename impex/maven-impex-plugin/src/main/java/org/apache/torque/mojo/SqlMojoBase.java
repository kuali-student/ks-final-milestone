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
 * Generates SQL from the schema.xml files.
 *
 * @author Raphael Pieroni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 * @author <a href="kannegiesser@synyx.de">Marc Kannegiesser</a>
 *
 */
public abstract class SqlMojoBase extends DataModelTaskMojo
{
    /**
     * The suffix of the generated sql files.
     */
    private String suffix ="";

    /**
     * Creates a new SQLMojo object.
     */
    public SqlMojoBase()
    {
        super(new TorqueSQLTask());
    }

    /**
     * Sets the suffix of the generated sql files.
     *
     * @param suffix the suffix of the generated sql files.
     */
    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }

    /**
     * Returns the suffix of the generated sql files.
     *
     * @return the suffix of the generated sql files.
     */
    public String getSuffix()
    {
        return suffix;
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
                TARGET_DATABASE_CONTEXT_PROPERTY,
                super.getTargetDatabase());
        return configuration;
    }

    /**
     * Returns the path to the control template.
     *
     * @return "sql/Control.vm"
     */
    protected String getControlTemplate()
    {
        return "sql/base/Control.vm";
    }

    /**
      * Configures the Texen task which is wrapped by this mojo.
      * In this implementation, the context properties, useClasspath,
      * the output directory, the control template, the schema Fileset,
      * the target package, the target database  and the suffix are set.
      *
      * @throws MojoExecutionException if an error occurs when setting the Tasks
      *         properties.
      *
      * @see TexenTaskMojo#configureTask()
      */
     protected void configureTask() throws MojoExecutionException
     {  
         super.configureTask();
         
         TorqueSQLTask task
                 = (TorqueSQLTask) super.getGeneratorTask();
       
         if (suffix != null)
         {  
             getLog().debug("Adding suffix: " + suffix );
             task.setSuffix(suffix);
         }
     }
}
