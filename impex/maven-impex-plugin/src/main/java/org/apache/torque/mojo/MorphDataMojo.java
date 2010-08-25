package org.apache.torque.mojo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FileUtils.*;
import org.apache.maven.plugin.MojoExecutionException;
import org.codehaus.plexus.util.DirectoryScanner;
import org.codehaus.plexus.util.StringUtils;
import org.kuali.core.db.torque.PrettyPrint;
import org.kuali.core.db.torque.Utils;

/**
 * Converts Ant impex data XML files into maven-impex-plugin data XML files
 * 
 * @goal morphdata
 * @phase generate-sources
 */
public class MorphDataMojo extends BaseMojo {

	/**
	 * The directory in which the morphed XML will be generated.
	 * 
	 * @parameter expression="${newDataOutputDir}" default-value="${project.build.directory}/generated-impex/xml"
	 * @required
	 */
	private File newDataOutputDir;

	/**
	 * The directory containing the source (non-morphed) data XML files
	 * 
	 * @parameter expression="${oldDataXMLDir}" default-value="${basedir}/src/main/impex"
	 * @required
	 */
	private File oldDataXMLDir;

	/**
	 * The default set of files in that directory to include (ant style notation)
	 * 
	 * @parameter expression="${oldDataXMLIncludes}" default-value="*.xml"
	 * @required
	 */
	private String oldDataXMLIncludes;

	/**
	 * The default set of files in that directory to exclude (ant style notation)
	 * 
	 * @parameter expression="${oldDataXMLExcludes}" default-value="*schema.xml"
	 */
	private String oldDataXMLExcludes;

	protected String[] getFiles() throws IOException {
		DirectoryScanner ds = new DirectoryScanner();
		ds.setIncludes(new String[] { getOldDataXMLIncludes() });
		if (getOldDataXMLExcludes() != null) {
			ds.setExcludes(new String[] { getOldDataXMLExcludes() });
		}
		ds.setBasedir(getOldDataXMLDir());
		ds.scan();
		return ds.getIncludedFiles();
	}

	protected void executeMojo() throws MojoExecutionException {
		Utils utils = new Utils();
		try {
			String[] files = getFiles();
			PrettyPrint pp = new PrettyPrint("[INFO] Converting " + files.length + " data XML files");
			utils.left(pp);
			String inputPath = getOldDataXMLDir().getAbsolutePath();
			forceMkdir(getNewDataOutputDir());
			List<String> input = new ArrayList<String>();
			List<String> output = new ArrayList<String>();
			for (String file : files) {
				input.add(inputPath + FS + file);
				output.add(getNewDataOutputDir() + FS + file);
			}
			for (int i = 0; i < input.size(); i++) {
				String filename = input.get(i);
				String contents = readFileToString(new File(filename), getEncoding());
				contents = getMorphedContents(contents);
				writeStringToFile(new File(output.get(i)), contents, getEncoding());
			}
			utils.right(pp);
		} catch (IOException e) {
			throw new MojoExecutionException("Unexpected error", e);
		}
	}

	protected String getMorphedContents(String contents) {
		return StringUtils.replace(contents, "\"data.dtd\"", '"' + getProject().getArtifactId() + "-data.dtd\"");
	}

	public File getNewDataOutputDir() {
		return newDataOutputDir;
	}

	public void setNewDataOutputDir(File newDataOutputDir) {
		this.newDataOutputDir = newDataOutputDir;
	}

	public File getOldDataXMLDir() {
		return oldDataXMLDir;
	}

	public void setOldDataXMLDir(File oldDataXMLDir) {
		this.oldDataXMLDir = oldDataXMLDir;
	}

	public String getOldDataXMLIncludes() {
		return oldDataXMLIncludes;
	}

	public void setOldDataXMLIncludes(String oldDataXMLIncludes) {
		this.oldDataXMLIncludes = oldDataXMLIncludes;
	}

	public String getOldDataXMLExcludes() {
		return oldDataXMLExcludes;
	}

	public void setOldDataXMLExcludes(String oldDataXMLExcludes) {
		this.oldDataXMLExcludes = oldDataXMLExcludes;
	}
}
