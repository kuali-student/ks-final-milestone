package org.apache.torque.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.StringUtils;

/**
 * Converts Ant impex data XML files into maven-impex-plugin data XML filse
 * 
 * @goal morphdata
 * @phase generate-sources
 */
public class MorphDataMojo extends AbstractMojo {
	private static final String FS = System.getProperty("file.separator");

	/**
	 * The Maven project this plugin runs in.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;

	/**
	 * The directory in which the SQL will be generated.
	 * 
	 * @parameter expression="${outputDir}"
	 *            default-value="${project.build.directory}/generated-impex/data"
	 * @required
	 */
	private String outputDir;

	/**
	 * The directory containing data XML files
	 * 
	 * @parameter expression="${dataXMLDir}"
	 *            default-value="${basedir}/src/main/impex/data"
	 * @required
	 */
	private File dataXMLDir;

	/**
	 * The default set of files in that directory to include (ant style
	 * notation)
	 * 
	 * @parameter expression="${dataXMLIncludes}" default-value="*.xml"
	 * @required
	 */
	private String dataXMLIncludes;

	/**
	 * The default set of files in that directory to exclude (ant style
	 * notation)
	 * 
	 * @parameter expression="${dataXMLExcludes}"
	 */
	private String dataXMLExcludes;

	@Override
	public void execute() throws MojoExecutionException {
		getLog().info("------------------------------------------------------------------------");
		getLog().info("Converting data XML files");
		getLog().info("------------------------------------------------------------------------");
		try {
			DirectoryScanner ds = new DirectoryScanner();
			ds.setIncludes(new String[] { getDataXMLIncludes() });
			if (getDataXMLExcludes() != null) {
				ds.setExcludes(new String[] { getDataXMLExcludes() });
			}
			ds.setBasedir(getDataXMLDir());
			ds.scan();
			String[] files = ds.getIncludedFiles();
			getLog().info("Located " + files.length + " XML files to convert");
			String inputPath = getDataXMLDir().getAbsolutePath();
			String outputPath = getProject().getBuild().getDirectory() + FS + "generated-impex" + FS + "data";
			File newDir = new File(outputPath);
			FileUtils.forceMkdir(newDir);
			List<String> input = new ArrayList<String>();
			List<String> output = new ArrayList<String>();
			for (String file : files) {
				input.add(inputPath + FS + file);
				output.add(outputPath + FS + file);
			}
			for (int i = 0; i < input.size(); i++) {
				String filename = input.get(i);
				String contents = getContents(filename);
				contents = getMorphedContents(contents);
				writeContents(output.get(i), contents);
			}
		} catch (Exception e) {
			throw new MojoExecutionException("Unexpected error", e);
		}
	}

	protected void writeContents(String filename, String contents) throws IOException {
		OutputStream out = null;
		try {
			out = new FileOutputStream(filename);
			IOUtils.write(contents, out, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	protected String getMorphedContents(String contents) {
		return StringUtils.replace(contents, "\"data.dtd\"", '"' + getProject().getArtifactId() + "-schema.dtd\"");
	}

	protected String getContents(String filename) throws IOException {
		InputStream in = null;
		try {
			in = new FileInputStream(filename);
			return IOUtils.toString(in, "UTF-8");
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(in);
		}
	}

	/**
	 * Sets the maven project.
	 * 
	 * @param project
	 *            the maven project where this plugin runs in.
	 */
	public void setProject(MavenProject project) {
		this.project = project;
	}

	/**
	 * Returns the maven project.
	 * 
	 * @return The maven project where this plugin runs in.
	 */
	public MavenProject getProject() {
		return project;
	}

	public String getDataXMLIncludes() {
		return dataXMLIncludes;
	}

	public void setDataXMLIncludes(String dataXMLIncludes) {
		this.dataXMLIncludes = dataXMLIncludes;
	}

	public String getDataXMLExcludes() {
		return dataXMLExcludes;
	}

	public void setDataXMLExcludes(String dataXMLExcludes) {
		this.dataXMLExcludes = dataXMLExcludes;
	}

	public File getDataXMLDir() {
		return dataXMLDir;
	}

	public void setDataXMLDir(File dataXMLDir) {
		this.dataXMLDir = dataXMLDir;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
}
