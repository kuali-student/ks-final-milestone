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

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.DefaultArtifactRepository;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.codehaus.plexus.PlexusTestCase;


/**
 * Tests the TorqueOMMojo
 *
 * @author Rapha�l Pi�roni (rafale_at_codehaus.org)
 */
public class TorqueSQLMojoTest
        extends PlexusTestCase
{
    /**
     * Test of execute method of class org.apache.torque.mojo.TorqueOMMojo;.
     *
     * @throws Exception if the test fails.
     */
    public void testExecute()
            throws Exception
    {
        System.out.println ("execute");

        MavenProjectBuilder builder
                = (MavenProjectBuilder) lookup(MavenProjectBuilder.ROLE);

        ArtifactRepositoryLayout localRepositoryLayout
                = (ArtifactRepositoryLayout) lookup(
                        ArtifactRepositoryLayout.ROLE,
                        "default");

        ArtifactRepository localRepository
                = new DefaultArtifactRepository(
                        "local",
                        "file://" + getBasedir() + File.separator
                            + "target/test-classes/repository",
                        localRepositoryLayout);

        MavenProject mavenProject = builder.buildWithDependencies(
                new File(
                    getBasedir(),
                    "target/test-classes/projects/TorqueOMMojoTest/pom.xml"),
                localRepository,
                null);

        SqlMojo instance = new SqlMojo();

        instance.setProject(mavenProject);

        instance.setOutputDir(
                mavenProject.getBuild().getDirectory()
                    + "/generated-sql/torque");

        instance.setContextPropertiesPath(
                mavenProject.getBuild().getDirectory()
                    + "/torque/context.sql.properties");

        instance.setSchemaDir(
                mavenProject.getBasedir() + "/src/main/torque/schema");

        instance.setUseClasspath(true);

        instance.setTargetDatabase("postgresql");

        instance.setSqlDbMap(mavenProject.getBuild().getDirectory()
                +"/torque/sqldbmap.properties");

        instance.setSchemaIncludes("*schema.xml");

        instance.setReportFile("../../torque/torque."
                + mavenProject.getArtifact().getArtifactId()
                + "."
                + "sql.generation");

        instance.execute();

        String generatedSqlDir
                = "generated-sql/torque/";

        assertTrue(
                "test-TorqueOMMojoTest-schema.sql must exist",
                new File(
                        mavenProject.getBuild().getDirectory(),
                        generatedSqlDir + "test-TorqueOMMojoTest-schema.sql")
                    .exists());

        assertTrue(
                "id-table-schema.sql must exist",
                new File(
                        mavenProject.getBuild().getDirectory(),
                        generatedSqlDir + "id-table-schema.sql")
                    .exists());

        assertTrue(
                "sqldbmap.properties must exist",
                new File(
                        mavenProject.getBuild().getDirectory(),
                        "torque/sqldbmap.properties")
                    .exists());
    }
}
