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
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.FileConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.apache.tools.ant.Project;
import org.apache.texen.ant.TexenTask;


/**
 * The base Class for all Mojos wrapping Texen Tasks.
 *
 * @author Rapha�l Pi�roni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 */
public abstract class TexenTaskMojo
    extends AbstractMojo
{
    /**
     * The directory where the generator output is written to.
     *
     * @required
     */
    private String outputDir;

    /**
     * The base path where the templates are read from, if they are not read
     * from the classpath.
     *
     * @parameter expression="${basedir}/src/main/torque/templates"
     */
    private String templatePath;

    /**
     * Whether the templates should be loaded from the classpath.
     *
     * @parameter expression="true"
     */
    private boolean useClasspath;

    /**
     * A map where all user-defined context properties can be set.
     * Overrides all other mojo configuration settings which are mapped
     * to context properties.
     *
     * @parameter
     */
    private Map userContextProperties;

    /**
     * The path to the generated context property file.
     *
     * @required
     */
    private String contextPropertiesPath;

    /**
     * The Maven project this plugin runs in.
     *
     * @parameter expression="${project}"
     * @required
     * @readonly
     */
    private MavenProject project;

    /**
     * The generator task to be executed by this mojo.
     */
    private TexenTask generatorTask;

    /**
     * The ant project for the generator task.
     */
    private Project antProject;

    /**
     * Creates a new instance of AbstractTorqueMojo.
     *
     * @throws IllegalArgumentException if generatorTask is null.
     */
    public TexenTaskMojo(TexenTask generatorTask)
    {
        setGeneratorTask(generatorTask);
    }
    
    /**
     * Sets the Texen task.
     * @param generatorTask the Texen task to be used in this Mojo.
     */
    protected void setGeneratorTask(TexenTask generatorTask)
    {
        if (generatorTask == null)
        {
            throw new IllegalArgumentException("generatorTask is null");
        }

        // because the Texen Task needs the ant project to load its
        // files, we need to attach one to the Texen task
        antProject = new Project();
        antProject.init();
        generatorTask.setProject(antProject);

        this.generatorTask = generatorTask;
    }

    /**
     * Sets the maven project.
     *
     * @param project the maven project where this plugin runs in.
     */
    public void setProject(MavenProject project)
    {
        this.project = project;
    }

    /**
     * Returns the maven project.
     *
     * @return The maven project where this plugin runs in.
     */
    public MavenProject getProject()
    {
        return project;
    }

    /**
     * Returns the ant project.
     *
     * @return The ant project assigned to the generator task.
     */
    protected Project getAntProject()
    {
        return antProject;
    }

    /**
     * Sets the path to Torque's output directory.
     *
     * @param outputDir the path to Torque's output directory.
     */
    public void setOutputDir(String outputDir)
    {
        this.outputDir = outputDir;
    }

    /**
     * Returns the path to Torque's output directory.
     *
     * @return the path to Torque's output directory. Not null if
     *         initialized correctly.
     */
    public String getOutputDir()
    {
        return this.outputDir;
    }

    /**
     * Sets the path to Torque's templates, if the classpath is not used
     * to load the templates.
     *
     * @param templatePath the path to Torque's templates.
     */
    public void setTemplatePath(String templatePath)
    {
        this.templatePath = templatePath;
    }

    /**
     * Returns the path to Torque's templates, if the classpath is not used
     * to load the templates.
     *
     * @return the path to Torque's templates.
     */
    public String getTemplatePath()
    {
        return this.templatePath;
    }

    /**
     * Sets whether the classpath should be used to locate the templates.
     *
     * @param templatePath the path to Torque's templates.
     */
    public void setUseClasspath(boolean useClasspath)
    {
        this.useClasspath = useClasspath;
    }

    /**
     * Returns whether the classpath is used to locate the templates.
     *
     * @return true if the classpath is used to locate the templates,
     *         false otherwise
     */
    public boolean getUseClasspath()
    {
        return this.useClasspath;
    }

    /**
     * Sets the path to the generated property file used as Texen's context
     * properties.
     *
     * @param generatedPropertyFilePath the path to the generated context
     *        properties file.
     */
    public void setContextPropertiesPath(String contextPropertiesPath)
    {
        this.contextPropertiesPath = contextPropertiesPath;
    }

    /**
     * Returns the path to the generated property file used as Texen's context
     * properties.
     *
     * @return the path to the generated context properties file.
     */
    public String getContextPropertiesPath()
    {
        return this.contextPropertiesPath;
    }

    /**
     * Sets the map which defines user-defined context properties.
     * The settings override all other mojo configuration settings
     * which are mapped to context properties.
     *
     * @param contextProperties the user-defined context properties.
     */
    public void setUserContextProperties(Map userContextProperties)
    {
        this.userContextProperties = userContextProperties;
    }

    /**
     * Returns the map which defines user-defined context properties.
     *
     * @return the map containing user-defined context properties,
     *         or null if not set.
     */
    public Map getUserContextProperties()
    {
        return userContextProperties;
    }

    /**
     * returns the generator Task for this mojo.
     *
     * @return the generator Task, not null.
     */
    protected TexenTask getGeneratorTask()
    {
        return generatorTask;
    }

    /**
     * Generates the context properties file for Texen.
     * The file is written to the path contextPropertiesPath.
     *
     * @throws MojoExecutionException if an error occurs.
     */
    protected void generateContextProperties()
            throws MojoExecutionException
    {
        try
        {
            FileConfiguration configuration = getMojoContextProperties();
            if (userContextProperties != null)
            {
                for (Iterator contextPropertyIt
                        = userContextProperties.entrySet().iterator();
                        contextPropertyIt.hasNext();)
                {
                    Map.Entry entry = (Map.Entry) contextPropertyIt.next();
                    configuration.addProperty(
                            entry.getKey().toString(),
                            entry.getValue().toString());
                }
            }
            configuration.save(contextPropertiesPath);
        }
        catch (ConfigurationException e)
        {
            getLog().error("Error writing temporary context properties: "
                    + e.getMessage());
            throw new MojoExecutionException(e.getMessage());
        }
    }

    /**
     * Configures the Texen task which is wrapped by this mojo.
     * In this implementation, the context properties, useClasspath
     * and the output directory are set. If more parameters need to be set,
     * this method shouldb be overwritten in subclasses.
     *
     * @throws MojoExecutionException if an error occurs when setting the Tasks
     *         properties.
     */
    protected void configureTask()
            throws MojoExecutionException
    {
        generatorTask.setContextProperties(contextPropertiesPath);

        generatorTask.setUseClasspath(useClasspath);

        if (templatePath != null)
        {
            try
            {
                generatorTask.setTemplatePath(templatePath);
            }
            catch (Exception e)
            {
                getLog().error("Error setting template path: "
                        + e.getMessage());
                throw new MojoExecutionException(e.getMessage());
            }
        }

        {
            File outputDirectory = new File(outputDir);
            getLog().debug("generating torque java sources into: "
                + outputDirectory.getAbsolutePath());
            outputDirectory.mkdirs();
            generatorTask.setOutputDirectory(outputDirectory);
        }
    }

    /**
     * Executes the wrapped Texen task. Before this is done, the context
     * properties file is generated and the Texen task is configured.
     *
     * @throws MojoExecutionException if an error occurs during execution.
     *
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    public void execute() throws MojoExecutionException
    {
        generateContextProperties();
        configureTask();
        generatorTask.execute();
    }

    /**
     * Returns the context properties for the Texen task which are defined
     * in the configuration of the child mojo.
     * This method needs to be overwritten in subclasses.
     *
     * @return The PropertiesConfiguration containing all context properties,
     *         not null.
     */
    protected abstract PropertiesConfiguration getMojoContextProperties();
}
