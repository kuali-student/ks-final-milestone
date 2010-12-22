package org.apache.torque.mojo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.io.FileUtils.*;

import org.apache.maven.plugin.MojoExecutionException;
import org.apache.torque.mojo.morph.DataMorpher;
import org.apache.torque.mojo.morph.MorphRequest;
import org.apache.torque.mojo.morph.Morpher;
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
	 * @parameter expression="${oldDataXMLExcludes}" default-value="schema.xml"
	 */
	private String oldDataXMLExcludes;

	/**
	 * Use our configuration to determine the list of files we need to convert
	 */
	protected String[] getOldFiles() throws IOException {
		DirectoryScanner ds = new DirectoryScanner();
		ds.setIncludes(new String[] { getOldDataXMLIncludes() });
		if (getOldDataXMLExcludes() != null) {
			ds.setExcludes(new String[] { getOldDataXMLExcludes() });
		}
		ds.setBasedir(getOldDataXMLDir());
		ds.scan();
		return ds.getIncludedFiles();
	}

	protected boolean isMorphNeeded(File oldFile, File newFile) {
		if (!newFile.exists()) {
			return true;
		}

		long lastModifiedOld = oldFile.lastModified();
		long lastModifiedNew = newFile.lastModified();
		return lastModifiedOld > lastModifiedNew;
	}

	protected List<MorphRequest> getMorphRequests(String[] oldFiles) throws IOException {
		String inputPath = getOldDataXMLDir().getAbsolutePath();
		String outputPath = getNewDataOutputDir().getAbsolutePath();
		forceMkdir(getNewDataOutputDir());
		List<MorphRequest> requests = new ArrayList<MorphRequest>();
		for (String oldFilename : oldFiles) {
			String oldFilePath = inputPath + FS + oldFilename;
			String newFilePath = outputPath + FS + oldFilename;
			File oldFile = new File(oldFilePath);
			File newFile = new File(newFilePath);
			if (!isMorphNeeded(oldFile, newFile)) {
				getLog().debug("Skipping morph on " + oldFilename);
				continue;
			}
			MorphRequest request = new MorphRequest();
			request.setEncoding(getEncoding());
			request.setName(oldFile.getName());
			request.setInputStream(new FileInputStream(oldFile));
			request.setOutputStream(new FileOutputStream(newFile));
			requests.add(request);
		}
		return requests;
	}

	protected void executeMojo() throws MojoExecutionException {
		Utils utils = new Utils();
		try {
			String[] oldFiles = getOldFiles();
			getLog().info(StringUtils.repeat("-", utils.getDefaultPrintableConsoleWidth() - 7));
			PrettyPrint pp = new PrettyPrint("[INFO] Converting " + oldFiles.length + " data XML files");
			utils.left(pp);
			List<MorphRequest> requests = getMorphRequests(oldFiles);
			for (MorphRequest request : requests) {
				Morpher morpher = new DataMorpher(request, getProject().getArtifactId());
				morpher.executeMorph();
			}
			utils.right(pp);
			int skipCount = oldFiles.length - requests.size();
			if (skipCount > 0) {
				getLog().info("Skipped " + skipCount + " files that were unchanged.");
			}
		} catch (IOException e) {
			throw new MojoExecutionException("Unexpected error", e);
		}
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
