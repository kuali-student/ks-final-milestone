package org.apache.torque.mojo;

import java.io.File;

import org.apache.maven.artifact.repository.ArtifactRepository;
import org.apache.maven.artifact.repository.DefaultArtifactRepository;
import org.apache.maven.artifact.repository.layout.ArtifactRepositoryLayout;
import org.apache.maven.project.MavenProject;
import org.apache.maven.project.MavenProjectBuilder;
import org.codehaus.plexus.PlexusTestCase;

/**
 * Tests SQLMojo
 */
public class ImpexSQLMojoTest extends PlexusTestCase {
	/**
	 * Test of execute method of class org.apache.torque.mojo.TorqueOMMojo;.
	 * 
	 * @throws Exception
	 *             if the test fails.
	 */
	public void testExecute() throws Exception {
		System.out.println("execute");
		MavenProjectBuilder builder = (MavenProjectBuilder) lookup(MavenProjectBuilder.ROLE);
		ArtifactRepositoryLayout localRepositoryLayout = (ArtifactRepositoryLayout) lookup(ArtifactRepositoryLayout.ROLE, "default");
		ArtifactRepository localRepository = new DefaultArtifactRepository("local", "file://" + getBasedir() + File.separator + "target/test-classes/repository", localRepositoryLayout);
		MavenProject mavenProject = builder.buildWithDependencies(new File(getBasedir(), "target/test-classes/projects/TorqueOMMojoTest/pom.xml"), localRepository, null);

		SchemaSqlMojo instance = new SchemaSqlMojo();
		instance.setProject(mavenProject);
		instance.setOutputDir(mavenProject.getBuild().getDirectory() + "/generated-sql/impex");
		instance.setContextPropertiesPath(mavenProject.getBuild().getDirectory() + "/impex/context.sql.properties");
		instance.setSchemaDir(mavenProject.getBasedir() + "/src/main/impex/schema");
		instance.setUseClasspath(true);
		instance.setTargetDatabase("mysql");
		instance.setSqlDbMap(mavenProject.getBuild().getDirectory() + "/impex/sqldbmap.properties");
		instance.setSchemaIncludes("*schema.xml");
		instance.setReportFile("../../impex/torque." + mavenProject.getArtifact().getArtifactId() + ".sql.generation");
		instance.execute();

		String generatedSqlDir = "generated-sql/impex/";

		assertTrue("test-TorqueOMMojoTest-schema.sql must exist", new File(mavenProject.getBuild().getDirectory(), generatedSqlDir + "test-TorqueOMMojoTest-schema.sql").exists());
		assertTrue("test-TorqueOMMojoTest-schema.sql must exist", new File(mavenProject.getBuild().getDirectory(), generatedSqlDir + "test-TorqueOMMojoTest-schema-constraints.sql").exists());

		assertTrue("id-table-schema.sql must exist", new File(mavenProject.getBuild().getDirectory(), generatedSqlDir + "id-table-schema.sql").exists());
		assertTrue("id-table-schema.sql must exist", new File(mavenProject.getBuild().getDirectory(), generatedSqlDir + "id-table-schema-constraints.sql").exists());

		assertTrue("sqldbmap.properties must exist", new File(mavenProject.getBuild().getDirectory(), "impex/sqldbmap.properties").exists());
	}
}
