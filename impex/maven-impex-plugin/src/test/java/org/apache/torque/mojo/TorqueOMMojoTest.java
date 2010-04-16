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
import org.apache.torque.mojo.OMMojo;
import org.codehaus.plexus.PlexusTestCase;


/**
 * Tests the TorqueOMMojo
 *
 * @author Rapha�l Pi�roni (rafale_at_codehaus.org)
 */
public class TorqueOMMojoTest
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

        OMMojo instance = new OMMojo();

        instance.setProject(mavenProject);

        instance.setOutputDir(
                mavenProject.getBuild().getSourceDirectory());

        instance.setContextPropertiesPath(
                mavenProject.getBuild().getDirectory()
                    + "/torque/context.om.properties");

        instance.setBaseOutputDir(
                mavenProject.getBuild().getDirectory()
                    + "/generated-sources/torque");


        instance.setSchemaDir(
                mavenProject.getBasedir() + "/src/main/torque/schema");

        instance.setUseClasspath(true);

        instance.setBasePrefix("Base");

        instance.setSubpackageMap("map");

        instance.setTargetPackage("org.apache.torque.mojo.test");

        instance.setSchemaIncludes("*schema.xml");

        instance.setSchemaExcludes("id-table-schema.xml");

        instance.setReportFile("../../../target/torque/torque."
                + mavenProject.getArtifact().getArtifactId()
                + "."
                + "om.generation");

        instance.setBaseReportFile("../../torque/torque."
                + mavenProject.getArtifact().getArtifactId()
                + "."
                + "om.base.generation");

        instance.execute();

        String generatedSourcesBasePackageDir
                = "generated-sources/torque/org/apache/torque/mojo/test/";

        String generatedSourcesPackageDir
                = "src/main/java/org/apache/torque/mojo/test/";

        assertTrue(
                "BaseTestTable1.java must exist",
                new File(
                        mavenProject.getBuild().getDirectory(),
                        generatedSourcesBasePackageDir + "BaseTestTable1.java")
                    .exists());

        assertTrue(
                "BaseTestTable2.java must exist",
                new File(
                        mavenProject.getBuild().getDirectory(),
                        generatedSourcesBasePackageDir + "BaseTestTable2.java")
                    .exists());

        assertTrue(
                "BaseTestTable1Peer.java must exist",
                new File(
                        mavenProject.getBuild().getDirectory(),
                        generatedSourcesBasePackageDir + "BaseTestTable1Peer.java")
                    .exists());

        assertTrue(
                "BaseTestTable2Peer.java must exist",
                new File(
                        mavenProject.getBuild().getDirectory(),
                        generatedSourcesBasePackageDir + "BaseTestTable2Peer.java")
                    .exists());

        assertTrue(
                "TestTable1.java must exist",
                new File(
                        mavenProject.getBasedir(),
                        generatedSourcesPackageDir + "TestTable1.java")
                    .exists());

        assertTrue(
                "TestTable2.java must exist",
                new File(
                        mavenProject.getBasedir(),
                        generatedSourcesPackageDir + "TestTable2.java")
                    .exists());

        assertTrue(
                "TestTable1Peer.java must exist",
                new File(
                        mavenProject.getBasedir(),
                        generatedSourcesPackageDir + "TestTable1Peer.java")
                    .exists());

        assertTrue(
                "TestTable2Peer.java must exist",
                new File(
                        mavenProject.getBasedir(),
                        generatedSourcesPackageDir + "TestTable2Peer.java")
                    .exists());

        assertTrue(
                "TestTable1MapBuilder.java must exist",
                new File(
                        mavenProject.getBuild().getDirectory(),
                        generatedSourcesBasePackageDir
                            + "map/TestTable1MapBuilder.java")
                    .exists());

        assertTrue(
                "TestTable2MapBuilder.java must exist",
                new File(
                        mavenProject.getBuild ().getDirectory(),
                        generatedSourcesBasePackageDir
                            + "map/TestTable2MapBuilder.java")
                    .exists());

        assertTrue(
                "Project does not contain "
                    + mavenProject.getBuild().getDirectory()
                    + "/generated-sources/torque"
                    + " as compile source",
                mavenProject.getCompileSourceRoots().contains(
                        mavenProject.getBuild ().getDirectory()
                            + "/generated-sources/torque"));
    }
}
