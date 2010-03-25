package org.kuali.core.db.torque;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.texen.ant.TexenTask;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.DirectoryScanner;
import org.apache.tools.ant.types.FileSet;
import org.apache.torque.engine.database.model.Database;
import org.apache.torque.engine.database.transform.XmlToData;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;

public class KualiTorqueDataSQLTask extends TexenTask {

	/** the data dtd file */
	private String dataDTD;

	/** Fileset of XML schemas which represent our data models. */
	protected List<FileSet> filesets = new ArrayList<FileSet>();
	/**
	 * The target database(s) we are generating SQL for. Right now we can only
	 * deal with a single target, but we will support multiple targets soon.
	 */
	private String targetDatabase;

	private File xmlFile;

	/**
	 * Get the current target database.
	 * 
	 * @return String target database(s)
	 */
	public String getTargetDatabase() {
		return targetDatabase;
	}

	/**
	 * Set the current target database. This is where generated java classes
	 * will live.
	 * 
	 * @param v
	 *            The new TargetDatabase value
	 */
	public void setTargetDatabase(String v) {
		targetDatabase = v;
	}

	/**
	 * Gets the DataDTD attribute of the TorqueDataSQLTask object
	 * 
	 * @return The DataDTD value
	 */
	public String getDataDTD() {
		return dataDTD;
	}

	/**
	 * Sets the DataDTD attribute of the TorqueDataSQLTask object
	 * 
	 * @param dataDTD
	 *            The new DataDTD value
	 */
	public void setDataDTD(String dataDTD) {
		this.dataDTD = getProject().resolveFile(dataDTD).toString();
	}

	/**
	 * Adds a set of xml schema files (nested fileset attribute).
	 * 
	 * @param set
	 *            a Set of xml schema files
	 */
	public void addFileset(FileSet set) {
		filesets.add(set);
	}

	/**
	 * Set up the initial context for generating the SQL from the XML schema.
	 * 
	 * @return the context
	 * @throws Exception
	 *             If there is an error parsing the data xml.
	 */
	public Context initControlContext() throws Exception {
		super.initControlContext();

		if (filesets.isEmpty()) {
			throw new BuildException("You must specify a fileset of XML data files!");
		}

		// Transform the XML database schema into
		// data model object.
		KualiXmlToAppData xmlParser = new KualiXmlToAppData(getTargetDatabase(), "");
		db = xmlParser.parseFile(xmlFile.getAbsolutePath());
		// ad.setFileName(grokName(xmlFile.getPath()));

		ArrayList<File> files = new ArrayList<File>(300);

		// Deal with the filesets.
		for (int i = 0; i < filesets.size(); i++) {
			FileSet fs = (FileSet) filesets.get(i);
			DirectoryScanner ds = fs.getDirectoryScanner(getProject());
			File srcDir = fs.getDir(getProject());

			String[] dataModelFiles = ds.getIncludedFiles();

			// Make a transaction for each file
			for (int j = 0; j < dataModelFiles.length; j++) {
				File f = new File(srcDir, dataModelFiles[j]);
				files.add(f);
			}
		}

		VelocityContext context = new VelocityContext();

		context.put("xmlfiles", files);
		context.put("task", this);

		// Place the target database in the context.
		context.put("targetDatabase", targetDatabase);

		return context;
	}

	Database db;

	public List<?> getData(File f) {
		try {
			XmlToData dataXmlParser = new XmlToData(db, dataDTD);
			List<?> newData = dataXmlParser.parseFile(f.toString());
			return newData;
		} catch (Exception se) {
			se.printStackTrace();
			throw new BuildException(se);
		}
	}

	public File getXmlFile() {
		return xmlFile;
	}

	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}

}
