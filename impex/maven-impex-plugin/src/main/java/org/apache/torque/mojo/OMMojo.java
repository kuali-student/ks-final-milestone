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

import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.tools.ant.BuildException;
import org.apache.torque.task.TorqueDataModelTask;

/**
 * Generates the sources for the persistence classes from the schema.xml files.
 *
 * @author Raphael Pieroni (rafale_at_codehaus.org)
 * @author <a href="fischer@seitenbau.de">Thomas Fischer</a>
 *
 * @goal om
 * @phase generate-sources
 */
public class OMMojo
        extends DataModelTaskMojo
{
    // The following dummies trick the Mojo Description Extractor
    // into setting the correct default values for
    // outputDir, reportFile, contextPropertiesPath, schemaExcludes
    /**
     * The directory in which the sources for the non-base persistence classes
     * will be generated.
     *
     * @parameter property="outputDir"
     *            expression="${project.build.sourceDirectory}/../generated-java"
     */
    private String dummy;

    /**
     * The location where the report file for the non-base persistence classes 
     * will be generated.
     *
     * @parameter property="reportFile"
     *            expression="../../../target/torque/report.${project.artifact.artifactId}.om.generation"
     */
    private String dummy2;

    /**
     * The location where the context property file for velocity will be
     * generated.
     *
     * @parameter property="contextPropertiesPath"
     *            expression="${project.build.directory}/torque/context.om.properties"
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
     * The directory where the sources for the base persistence classes
     * will be generated. 
     *
     * @parameter expression="${project.build.directory}/generated-sources/torque"
     */
    private String baseOutputDir;

    /**
     * The location where the report file for the base persistence classes
     * will be generated.
     *
     * @parameter expression="../../torque/report.${project.artifact.artifactId}.om.base.generation"
     */
    private String baseReportFile;

    /**
     * The context property for the target package.
     */
    public static final String TARGET_PACKAGE_CONTEXT_PROPERTY
            = "targetPackage";

    /**
     * The context property for the prefix of the class name for
     * the generated base classes,
     */
    public static final String BASE_PREFIX_CONTEXT_PROPERTY
            = "basePrefix";

    /**
     * The context property which determines the name suffix of
     * generated bean classes.
     */
    public static final String BEAN_SUFFIX_CONTEXT_PROPERTY
            = "beanSuffix";

    /**
     * The context property which determines the fully qualifed
     * class name that the base beans will extend.
     */
    public static final String BEAN_EXTENDS_CLASS_PROPERTY
            = "beanExtendsClass";

    /**
     * The context property for the subpackage of the generated
     * database Map classes.
     */
    public static final String SUBPACKAGE_MAP_CONTEXT_PROPERTY
            = "subpackageMap";

    /**
     * The context property for the subpackage of the generated
     * base Object, Peer, and Manager classes.
     */
    public static final String SUBPACKAGE_BASE_CONTEXT_PROPERTY
            = "subpackageBase";

    /**
     * The context property for the subpackage of the generated
     * base bean classes.
     */
    public static final String SUBPACKAGE_BASE_BEAN_CONTEXT_PROPERTY
            = "subpackageBaseBean";

    /**
     * The context property for the subpackage of the generated
     * bean classes.
     */
    public static final String SUBPACKAGE_BEAN_CONTEXT_PROPERTY
            = "subpackageBean";

    /**
     * The context property for the subpackage of the generated
     * manager classes.
     */
    public static final String SUBPACKAGE_MANAGER_CONTEXT_PROPERTY
            = "subpackageManager";

    /**
     * The context property for the subpackage of the generated
     * data object classes.
     */
    public static final String SUBPACKAGE_OBJECT_CONTEXT_PROPERTY
            = "subpackageObject";

    /**
     * The context property for the subpackage of the generated
     * peer classes.
     */
    public static final String SUBPACKAGE_PEER_CONTEXT_PROPERTY
            = "subpackagePeer";

    /**
     * The context property where the generation of the getByName-
     * Methods can be turned on and off.
     */
    public static final String ADD_GET_BY_NAME_METHOD_CONTEXT_PROPERTY
            = "addGetByNameMethod";

    /**
     * The context property where it is configured whether the
     * data objects implement a Retrievable interface.
     */
    public static final String ADD_INTAKE_RETRIEVABLE_CONTEXT_PROPERTY
            = "addIntakeRetrievable";

    /**
     * The context property where it is configured whether the
     * data objects implement a Retrievable interface.
     */
    public static final String RETRIEVABLE_INTERFACE_CONTEXT_PROPERTY
            = "retrievableInterface";

    /**
     * The context property where the generation of the save
     * methods can be turned on and off.
     */
    public static final String ADD_SAVE_METHOD_CONTEXT_PROPERTY
            = "addSaveMethod";

    /**
     * The context property where the exception thown by save methods
     * can be configured.
     */
    public static final String SAVE_EXCEPTION_CONTEXT_PROPERTY
            = "saveException";

    /**
     * The context property where it is configured whether methods
     * are generated to easily retrieve foreign key relationships.
     */
    public static final String COMPLEX_OBJECT_MODEL_CONTEXT_PROPERTY
            = "complexObjectModel";

    /**
     * The context property which determines whether empty cached
     * foreign key relations should be filled automatically.
     */
    public static final String SILENT_DB_FETCH_CONTEXT_PROPERTY
            = "silentDbFetch";

    /**
     * The context property which determines whether manager classes
     * are generated.
     */
    public static final String USE_MANAGERS_CONTEXT_PROPERTY
            = "useManagers";

    /**
     * The context property which determines whether foreign key relationships
     * are cached.
     */
    public static final String OBJECT_IS_CACHING_CONTEXT_PROPERTY
            = "objectIsCaching";

    /**
     * The context property which determines whether isXXX methods are
     * generated for boolean properties instead of getXXX methods.
     */
    public static final String CORRECT_GETTERS_CONTEXT_PROPERTY
            = "correctGetters";

    /**
     * The context property which determines whether java5 generics
     * and easy iteration are used in generated code.
     */
    public static final String ENABLE_JAVA_5_FEATURES_CONTEXT_PROPERTY
            = "enableJava5Features";

    /**
     * The context property which determines whether time stamps are put
     * into generated files.
     */
    public static final String ADD_TIME_STAMP_CONTEXT_PROPERTY
            = "addTimeStamp";

    /**
     * The context property which determines whether bean classes
     * should be generated.
     */
    public static final String GENERATE_BEANS_CONTEXT_PROPERTY
            = "generateBeans";

    /**
     * The prefix for the base classes.
     *
     * @parameter expression="Base"
     */
    private String basePrefix;

    /**
     * The suffix for the class name of the bean classes.
     *
     * @parameter expression="Bean"
     */
    private String beanSuffix;

    /**
     * A fully qualified class name that the generated base bean class will
     * extend.
     *
     * @parameter expression=""
     */
    private String beanExtendsClass;

    /**
     * The subpackage (relative to <code>targetPackage</code>
     * where Torque will put the generated Java classes for the database map.
     *
     * @parameter expression="map"
     */

    private String subpackageMap;

    /**
     * The subpackage (relative to <code>targetPackage</code>)
     * where Torque will put the generated Peer Java classes.
     * If not set, the Peer classes will be generated in
     * <code>targetPackage</code>.
     *
     * @parameter
     */
    private String subpackagePeer;

    /**
     * The subpackage (relative to <code>targetPackage</code>)
     * where Torque will put the generated data object Java classes.
     * If not set, the object classes will be generated in
     * <code>targetPackage</code>.
     *
     * @parameter
     */
    private String subpackageObject;

    /**
     * The subpackage (relative to <code>targetPackage</code>)
     * where Torque will put the generated Java Manager classes,
     * if they are generated at all.
     * If not set, the Manager classes will be generated in
     * <code>targetPackage</code>.
     *
     * @parameter
     */
    private String subpackageManager;

    /**
     * The subpackage (relative to <code>targetPackage</code>)
     * where Torque will put the generated JavaBean classes,
     * if they are generated at all.
     *
     * @parameter expression="bean"
     */
    private String subpackageBean;

    /**
     * The subpackage (relative to <code>targetPackage</code>)
     * where Torque will put the generated BaseObject, BasePeer,
     * (and BaseManager, if they are generated at all) Java classes.
     * If not set, the Base classes will be generated in
     * <code>targetPackage</code>.
     *
     * @parameter
     */
    private String subpackageBase;

    /**
     * The subpackage (relative to <code>targetPackage</code>)
     * where Torque will put the generated BaseBean Java classes,
     * if they are generated at all.
     *
     * @parameter expression="bean"
     */
    private String subpackageBaseBean;

    /**
     * If true, Torque adds methods to get database fields by name/position.
     *
     * @parameter expression="true"
     */
    private boolean addGetByNameMethod;

    /**
     * If true, the data objects will implement a Retrievable interface.
     *
     * @parameter expression="false"
     */
    private boolean addRetrievableInterface;

    /**
     * The fully qualified class name of the retrievable interface
     * to be implemented by the data objects.
     *
     * @parameter expression="org.apache.turbine.om.Retrievable"
     */
    private String retrievableInterface;

    /**
     * Determines whether save()-Methods should be added to the data objects.
     *
     * @parameter expression="true"
     */
    private boolean addSaveMethod;

    /**
     * Defines which Exception should be thrown by the Object.save() method.
     *
     * @parameter expression="Exception"
     */
    private String saveException;

    /**
     * Determines whether a time stamp and a serialVersionUID will be
     * added to generated objects.
     *
     * @parameter expression="true"
     */
    private boolean addTimeStamp;

    /**
     * If true, Torque generates data objects with collection support
     * and methods to easily retrieve foreign key relationships.
     *
     * @parameter expression="true"
     */
    private boolean complexObjectModel;

    /**
     * If true, Torque will generate Manager classes that use JCS for caching.
     *
     * @parameter expression="false"
     */
    private boolean useManagers;

    /**
     * If true, Torque generates data objects that cache their foreign key
     * relationships.
     *
     * @parameter expression="true"
     */
    private boolean objectIsCaching;

    /**
     * If true and objectIsCaching is true, Torque silently fetches
     * foreign key relation collections if the collection is not yet
     * initialized.
     *
     * @parameter expression="true"
     */
    private boolean silentDbFetch;

    /**
     * If true, Torque generates isXXX getter methods for boolean columns.
     *
     * @parameter expression="false"
     */
    private boolean correctGetters;

    /**
     * If true, Torque generates a bean object for each data object,
     * plus methods to convert data objects to beans and vice versa.
     *
     * @parameter expression="false"
     */
    private boolean generateBeans;

    /**
     * Whether Java5 generics and iteration shorthand should be used
     * in generated code.
     *
     * @parameter expression="false"
     */
    private boolean enableJava5Features;

    /**
     * The control template which should be used by the Texen task. 
     */
    private String controlTemplate;

    /**
     * Creates a new TorqueOMMojo object.
     */
    public OMMojo()
    {
    }

    /**
     * Generates the OM classes for a Torque project and adds them to
     * the compile sources of the project.
     *
     * @throws MojoExecutionException If an error occurs during generation.
     *
     * @see TexenTaskMojo#execute()
     * @see org.apache.maven.plugin.Mojo#execute()
     */
    public void execute() throws MojoExecutionException
    {
        // generate non-base classes
        controlTemplate = "om/Control.vm";
        super.execute();
        String outputDir = super.getOutputDir();
        String reportFile = super.getReportFile();
        try
        {
            File outputDirectory = new File(outputDir);
            getLog().info ("torque non-base java sources generated into: "
                    + outputDirectory.getAbsolutePath());

            if (getProject() != null)
            {
                getProject().addCompileSourceRoot(
                        outputDirectory.getPath());
            }
        }
        catch (BuildException e)
        {
            getLog().error(e.getMessage());
            throw new MojoExecutionException(e.getMessage(), e);
        }

        // generate base classes
        // reset texen task (for an unknown reason, the old texen task will
        // append the already generated output to the already generated files
        super.setGeneratorTask(new TorqueDataModelTask());
        controlTemplate = "om/ControlBase.vm";
        super.setOutputDir(baseOutputDir);
        super.setReportFile(baseReportFile);
        super.execute();
        super.setOutputDir(outputDir);
        super.setReportFile(reportFile);
        
        try
        {
            File baseOutputDirectory = new File(baseOutputDir);
            getLog().info ("torque base java sources generated into: "
                    + baseOutputDirectory.getAbsolutePath());

            if (getProject() != null)
            {
                getProject().addCompileSourceRoot(
                        baseOutputDirectory.getPath());
            }
        }
        catch (BuildException e)
        {
            getLog().error(e.getMessage());
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }

    /**
     * Returns the path to the control template.
     *
     * @return the path to the current control template.
     */
    protected String getControlTemplate()
    {
        return controlTemplate;
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

        configuration.addProperty(
                TARGET_PACKAGE_CONTEXT_PROPERTY,
                getTargetPackage());

        configuration.addProperty(
                BASE_PREFIX_CONTEXT_PROPERTY,
                basePrefix);

        configuration.addProperty(
                SUBPACKAGE_MAP_CONTEXT_PROPERTY,
                subpackageMap);

        configuration.addProperty(
                SUBPACKAGE_BASE_CONTEXT_PROPERTY,
                subpackageBase);

        configuration.addProperty(
                SUBPACKAGE_BASE_BEAN_CONTEXT_PROPERTY,
                subpackageBaseBean);

        configuration.addProperty(
                SUBPACKAGE_BEAN_CONTEXT_PROPERTY,
                subpackageBean);

        configuration.addProperty(
                SUBPACKAGE_MANAGER_CONTEXT_PROPERTY,
                subpackageManager);

        configuration.addProperty(
                SUBPACKAGE_OBJECT_CONTEXT_PROPERTY,
                subpackageObject);

        configuration.addProperty(
                SUBPACKAGE_PEER_CONTEXT_PROPERTY,
                subpackagePeer);

        configuration.addProperty(
                ADD_GET_BY_NAME_METHOD_CONTEXT_PROPERTY,
                Boolean.toString(addGetByNameMethod));

        configuration.addProperty(
                ADD_INTAKE_RETRIEVABLE_CONTEXT_PROPERTY,
                Boolean.toString(addRetrievableInterface));

        configuration.addProperty(
                RETRIEVABLE_INTERFACE_CONTEXT_PROPERTY,
                retrievableInterface);

        configuration.addProperty(
                ADD_SAVE_METHOD_CONTEXT_PROPERTY,
                Boolean.toString(addSaveMethod));

        configuration.addProperty(
                ADD_TIME_STAMP_CONTEXT_PROPERTY,
                Boolean.toString(addTimeStamp));

        configuration.addProperty(
                BEAN_SUFFIX_CONTEXT_PROPERTY,
                beanSuffix);

        configuration.addProperty(
                BEAN_EXTENDS_CLASS_PROPERTY,
                beanExtendsClass);

        configuration.addProperty(
                GENERATE_BEANS_CONTEXT_PROPERTY,
                Boolean.toString(generateBeans));

        configuration.addProperty(
                COMPLEX_OBJECT_MODEL_CONTEXT_PROPERTY,
                Boolean.toString(complexObjectModel));

        configuration.addProperty(
                CORRECT_GETTERS_CONTEXT_PROPERTY,
                Boolean.toString(correctGetters));

        configuration.addProperty(
                ENABLE_JAVA_5_FEATURES_CONTEXT_PROPERTY,
                Boolean.toString(enableJava5Features));

        configuration.addProperty(
                OBJECT_IS_CACHING_CONTEXT_PROPERTY,
                Boolean.toString(objectIsCaching));

        configuration.addProperty(
                SAVE_EXCEPTION_CONTEXT_PROPERTY,
                saveException);

        configuration.addProperty(
                SILENT_DB_FETCH_CONTEXT_PROPERTY,
                Boolean.toString(silentDbFetch));

        configuration.addProperty(
                USE_MANAGERS_CONTEXT_PROPERTY,
                Boolean.toString(useManagers));

        return configuration;
    }

    /**
     * Returns the prefix for the base classes.
     *
     * @return the prefix for the base classes.
     */
    public String getBasePrefix()
    {
        return basePrefix;
    }

    /**
     * Sets the prefix for the names of the base classes.
     *
     * @param basePrefix the prefix for the base classes.
     */
    public void setBasePrefix(String basePrefix)
    {
        this.basePrefix = basePrefix;
    }

    /**
     * Returns the suffix for the names of the bean classes.
     *
     * @return the suffix for the bean classes.
     */
    public String getBeanSuffix()
    {
        return beanSuffix;
    }

    /**
     * Sets the optional fully qualified class name that the
     * generated base bean classes will extend.
     *
     * @param beanExtendsClass the fully qualified class or 
     *          null/"" if none is used.
     */
    public void setBeanExtendsClass(String beanExtendsClass)
    {
        this.beanExtendsClass = beanExtendsClass;
    }

    /**
     * Returns the fully qualified class that the generated base bean
     * classes will extend.
     *
     * @return the fully qualified class or null/"" if none is used.
     */
    public String getBeanExtendsClass()
    {
        return beanExtendsClass;
    }

    /**
     * Sets the suffix for the names of the bean classes.
     *
     * @param beanSuffix the suffix for the bean classes.
     */
    public void setBeanSuffix(String beanSuffix)
    {
        this.beanSuffix = beanSuffix;
    }

    /**
     * Gets the subpackage for the database map classes.
     *
     * @return the subpackage for the database map classes.
     */
    public String getSubpackageMap()
    {
        return subpackageMap;
    }

    /**
     * Sets the subpackage for the database map classes.
     *
     * @param subpackageMap the subpackage for the database map classes.
     */
    public void setSubpackageMap(String subpackageMap)
    {
        this.subpackageMap = subpackageMap;
    }

    /**
     * Gets the subpackage for the base Object, Peer, and Manager classes.
     *
     * @return the subpackage for the base Object, Peer, and Manager classes,
     *         or null if not set.
     */
    public String getSubpackageBase()
    {
        return subpackageBase;
    }

    /**
     * Sets the subpackage for the base Object, Peer, and Manager classes.
     *
     * @param subpackageBase the subpackage for the base Object, Peer,
     *        and Manager classes.
     */
    public void setSubpackageBase(String subpackageBase)
    {
        this.subpackageBase = subpackageBase;
    }

    /**
     * Gets the subpackage for the base peer, object, and manager classes.
     *
     * @return the subpackage for the base peer, object, and manager classes,
     *         or null if not set.
     */
    public String getSubpackageBaseBean()
    {
        return subpackageBaseBean;
    }

    /**
     * Sets the subpackage for the base bean classes.
     *
     * @param subpackageBaseBean the subpackage for the base bean classes.
     */
    public void setSubpackageBaseBean(String subpackageBaseBean)
    {
        this.subpackageBaseBean = subpackageBaseBean;
    }

    /**
     * Gets the subpackage for the bean classes.
     *
     * @return the subpackage for the bean classes.
     */
    public String getSubpackageBean()
    {
        return subpackageBean;
    }

    /**
     * Sets the subpackage for the bean classes.
     *
     * @param subpackageBean the subpackage for the bean classes.
     */
    public void setSubpackageBean(String subpackageBean)
    {
        this.subpackageBean = subpackageBean;
    }

    /**
     * Gets the subpackage for the manager classes.
     *
     * @return the subpackage for the manager classes, or null if not set.
     */
    public String getSubpackageManager()
    {
        return subpackageManager;
    }

    /**
     * Sets the subpackage for the manager classes.
     *
     * @param subpackageManager the subpackage for the manager classes.
     */
    public void setSubpackageManager(String subpackageManager)
    {
        this.subpackageManager = subpackageManager;
    }

    /**
     * Gets the subpackage for the data object classes.
     *
     * @return the subpackage for the data object classes.
     */
    public String getSubpackageObject()
    {
        return subpackageObject;
    }

    /**
     * Sets the subpackage for the data object classes.
     *
     * @param subpackageObject the subpackage for the data object classes.
     */
    public void setSubpackageObject(String subpackageObject)
    {
        this.subpackageObject = subpackageObject;
    }

    /**
     * Gets the subpackage for the peer classes.
     *
     * @return the subpackage for the peer classes, or null if not set.
     */
    public String getSubpackagePeer()
    {
        return subpackagePeer;
    }

    /**
     * Sets the subpackage for the peer classes.
     *
     * @param subpackagePeer the subpackage for the peer classes.
     */
    public void setSubpackagePeer(String subpackagePeer)
    {
        this.subpackagePeer = subpackagePeer;
    }

    /**
     * Returns whether Torque adds methods to get database fields
     * by name/position.
     *
     * @return true if Torque adds methods to get database fields
     *         by name/position, false otherwise.
     */
    public boolean isAddGetByNameMethod()
    {
        return addGetByNameMethod;
    }

    /**
     * Sets whether Torque should add methods to get database fields
     * by name/position.
     *
     * @param addGetByNameMethod whether Torque should add methods to get
     *        database fields by name/position.
     */
    public void setAddGetByNameMethod(boolean addGetByNameMethod)
    {
        this.addGetByNameMethod = addGetByNameMethod;
    }

    /**
     * Returns whether the data objects will implement an Retrievable interface.
     *
     * @return true if the data objects will implement an Retrievable interface,
     *         false otherwise.
     */
    public boolean isAddRetrievableInterface()
    {
        return addRetrievableInterface;
    }

    /**
     * Sets whether the data objects should implement an Retrievable interface.
     *
     * @param addRetrievableInterface whether the data objects should implement
     *        a Retrievable interface.
     */
    public void setAddRetrievableInterface(boolean addRetrievableInterface)
    {
        this.addRetrievableInterface = addRetrievableInterface;
    }

    /**
     * Returns whether Torque will add save methods to the data objects.
     *
     * @return true if Torque will add save methods to the data objects,
     *         false otherwise.
     */
    public boolean isAddSaveMethod()
    {
        return addSaveMethod;
    }

    /**
     * Sets whether Torque should add save methods to the data objects.
     *
     * @param addSaveMethod whether Torque should add save methods to the
     *        data objects.
     */
    public void setAddSaveMethod(boolean addSaveMethod)
    {
        this.addSaveMethod = addSaveMethod;
    }

    /**
     * Returns whether Torque puts time stamps in generated om files.
     *
     * @return true if Torque puts time stamps in generated om files,
     *         false otherwise.
     */
    public boolean isAddTimeStamp()
    {
        return addTimeStamp;
    }

    /**
     * Sets whether Torque puts time stamps in generated om files.
     *
     * @param addTimeStamp whether Torque puts time stamps in generated
     *        om files.
     */
    public void setAddTimeStamp(boolean addTimeStamp)
    {
        this.addTimeStamp = addTimeStamp;
    }

    /**
     * Returns whether Torque generates data objects with collection support
     * and methods to easily retrieve foreign key relationships.
     *
     * @return true if Torque generates data objects with collection support
     *         and methods to easily retrieve foreign key relationships,
     *         false otherwise.
     */
    public boolean isComplexObjectModel()
    {
        return complexObjectModel;
    }

    /**
     * Sets whether Torque generates data objects with collection support
     * and methods to easily retrieve foreign key relationships.
     *
     * @param complexObjectModel whether Torque generates data objects
     *        with collection support and methods to easily retrieve
     *        foreign key relationships, false otherwise.
     */
    public void setComplexObjectModel(boolean complexObjectModel)
    {
        this.complexObjectModel = complexObjectModel;
    }

    /**
     * Returns whether Torque generates isXXX getter methods for boolean
     * columns.
     *
     * @return true if Torque generates isXXX getter methods for boolean
     *         columns, false if Torque generates getXXX getter methods
     *         for boolean columns.
     */
    public boolean isCorrectGetters()
    {
        return correctGetters;
    }

    /**
     * Sets whether Torque generates isXXX getter methods for boolean
     * columns.
     *
     * @param correctGetters true if Torque generates isXXX getter methods
     *        for boolean columns, false if Torque generates getXXX getter
     *        methods for boolean columns.
     */
    public void setCorrectGetters(boolean correctGetters)
    {
        this.correctGetters = correctGetters;
    }

    /**
     * Returns whether Java5 generics and iteration shorthand will be used
     * in generated code.
     *
     * @return true if Java5 generics and iteration shorthand will be used
     *         in generated code, false to generate 1.4-compliant code.
     */
    public boolean isEnableJava5Features()
    {
        return enableJava5Features;
    }

    /**
     * Sets whether Java5 generics and iteration shorthand should be used
     * in generated code.
     *
     * @param enableJava5Features true if Java5 generics and iteration
     *        shorthand will be used in generated code, false to generate
     *        1.4-compliant code.
     */
    public void setEnableJava5Features(boolean enableJava5Features)
    {
        this.enableJava5Features = enableJava5Features;
    }

    /**
     * Returns  whether bean classes will be generated.
     *
     * @return true if bean classes will be generated, false otherwise.
     */
    public boolean isGenerateBeans()
    {
        return generateBeans;
    }

    /**
     * Sets whether bean classes should be generated.
     *
     * @param generateBeans whether bean classes should be generated.
     */
    public void setGenerateBeans(boolean generateBeans)
    {
        this.generateBeans = generateBeans;
    }

    /**
     * Returns whether data objects cache their foreign key relationships.
     *
     * @return true if data objects cache their foreign key relationships,
     *         false otherwise.
     */
    public boolean isObjectIsCaching()
    {
        return objectIsCaching;
    }

    /**
     * Sets whether data objects cache their foreign key relationships.
     *
     * @param objectIsCaching true if data objects cache their
     *        foreign key relationships, false otherwise.
     */
    public void setObjectIsCaching(boolean objectIsCaching)
    {
        this.objectIsCaching = objectIsCaching;
    }

    /**
     * Returns the fully qualified class name of the Retrievable interface
     * which should be implemented by the generated data objects.
     *
     * @return the fully qualified class name of the Retrievable interface.
     */
    public String getRetrievableInterface()
    {
        return retrievableInterface;
    }

    /**
     * Sets the fully qualified class name of the Retrievable interface
     * which should be implemented by the generated data objects.
     *
     * @param retrievableInterface the fully qualified class name of the
     *        Retrievable interface.
     */
    public void setRetrievableInterface(String retrievableInterface)
    {
        this.retrievableInterface = retrievableInterface;
    }

    /**
     * Returns the class name of the Exception which should be thrown
     * by the save methods of the generated data objects.
     *
     * @return the class name of the Exception which should be thrown
     *         by the generated save methods.
     */
    public String getSaveException()
    {
        return saveException;
    }

    /**
     * Sets the class name of the Exception which should be thrown
     * by the save methods of the generated data objects.
     *
     * @param saveException the class name of the Exception
     *        which should be thrown by the generated save methods.
     */
    public void setSaveException(String saveException)
    {
        this.saveException = saveException;
    }

    /**
     * Returns whether Torque silently fetches foreign key relation collections
     * if the collection is not yet initialized.
     *
     * @return true if Torque silently fetches foreign key relation collections
     *         if the collection is not yet initialized, false otherwise.
     */
    public boolean isSilentDbFetch()
    {
        return silentDbFetch;
    }

    /**
     * Sets whether Torque should silently fetches foreign key relation
     * collections if the collection is not yet initialized.
     *
     * @param silentDbFetch whether Torque should silently fetch
     *        foreign key relation collections if the collection
     *        is not yet initialized.
     */
    public void setSilentDbFetch(boolean silentDbFetch)
    {
        this.silentDbFetch = silentDbFetch;
    }

    /**
     * Returns whether Torque will generate Manager classes that use JCS
     * for caching.
     *
     * @return true if Torque will generate Manager classes that use JCS
     *         for caching, false otherwise.
     */
    public boolean isUseManagers()
    {
        return useManagers;
    }

    /**
     * Sets whether Torque should generate Manager classes that use JCS
     * for caching.
     *
     * @param useManagers whether Torque should generate Manager classes
     *        that use JCS for caching.
     */
    public void setUseManagers(boolean useManagers)
    {
        this.useManagers = useManagers;
    }

    /**
     * Returns where the sources for the persistence base classes
     * will be generated.
     *
     * @return where the sources for the persistence base classes
     *          will be generated.
     */
    public String getBaseOutputDir()
    {
        return baseOutputDir;
    }

    /**
     * Sets where the sources for the persistence base classes
     * will be generated.
     *
     * @param baseOutputDir where the sources for the persistence base classes
     *         will be generated.
     */
    public void setBaseOutputDir(String baseOutputDir)
    {
        this.baseOutputDir = baseOutputDir;
    }

    /**
     * Returns the location where the report file for the base persistence
     * classes will be generated.
     *
     * @return the location where the report file for the base persistence
     *          classes will be generated.
     */
    public String getBaseReportFile()
    {
        return baseReportFile;
    }

    /**
     * Sets the location where the report file for the base persistence
     * classes will be generated.
     *
     * @param baseReportFile the location where the report file for the
     *         base persistence classes will be generated.
     */
    public void setBaseReportFile(String baseReportFile)
    {
        this.baseReportFile = baseReportFile;
    }
}
