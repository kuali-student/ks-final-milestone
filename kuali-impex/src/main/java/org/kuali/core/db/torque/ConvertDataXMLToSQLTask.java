package org.kuali.core.db.torque;

import java.util.List;

import org.apache.texen.ant.TexenTask;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.torque.engine.database.model.Database;
import org.apache.torque.engine.database.transform.XmlToData;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.springframework.core.io.Resource;

/**
 * Converts XML data files into SQL targeted for a database - eg Oracle, MySQL
 */
public class ConvertDataXMLToSQLTask extends TexenTask {
	private static final int MVN_INFO = Integer.MAX_VALUE;

	/**
	 * The data.dtd file
	 */
	private String dataDTDResource;

	/**
	 * The list of data XML resources
	 */
	private List<Resource> dataXMLResources;

	/**
	 * The target database we are generating SQL for
	 */
	private String targetDatabase;

	/**
	 * The resource pointing to schema.xml
	 */
	private String schemaXMLResource;

	/**
	 * The database
	 */
	private Database db;

	protected int getTableCount() {
		if (db.getTables() == null) {
			return 0;
		} else {
			return db.getTables().size();
		}
	}

	protected int getDataXMLCount() {
		if (dataXMLResources == null) {
			return 0;
		} else {
			return dataXMLResources.size();
		}
	}

	/**
	 * Set up the initial context for generating data SQL from data XML
	 */
	public Context initControlContext() throws Exception {
		super.initControlContext();

		log("Target Database: " + getTargetDatabase(), MVN_INFO);

		// Transform the XML database schema into data model object.
		GenerateDatabaseHandler xmlParser = new GenerateDatabaseHandler(getTargetDatabase(), "");
		log("Parsing Schema: " + getSchemaXMLResource(), MVN_INFO);
		db = xmlParser.parseFile(getSchemaXMLResource());

		VelocityContext context = new VelocityContext();

		int dataXMLCount = getDataXMLCount();
		int tableCount = getTableCount();
		if (dataXMLCount != tableCount) {
			log("Table count=" + tableCount + " XML data count=" + dataXMLCount, Project.MSG_WARN);
		}

		log("Converting data for " + dataXMLCount + " tables", MVN_INFO);

		// Place our data xml resources into the context
		context.put("xmlfiles", getDataXMLResources());

		// Put our task into the context
		context.put("task", this);

		// Place the target database in the context.
		context.put("targetDatabase", targetDatabase);

		return context;
	}

	public List<?> getData(Resource resource) {
		try {
			XmlToData dataXmlParser = new XmlToData(db, dataDTDResource);
			List<?> newData = dataXmlParser.parseFile(resource);
			return newData;
		} catch (Exception se) {
			se.printStackTrace();
			throw new BuildException(se);
		}
	}

	public String getTargetDatabase() {
		return targetDatabase;
	}

	public void setTargetDatabase(String v) {
		targetDatabase = v;
	}

	public String getDataDTDResource() {
		return dataDTDResource;
	}

	public void setDataDTDResource(String dataDTD) {
		this.dataDTDResource = dataDTD;
	}

	public String getSchemaXMLResource() {
		return schemaXMLResource;
	}

	public void setSchemaXMLResource(String schemaXMLResource) {
		this.schemaXMLResource = schemaXMLResource;
	}

	public List<Resource> getDataXMLResources() {
		return dataXMLResources;
	}

	public void setDataXMLResources(List<Resource> dataXMLResources) {
		this.dataXMLResources = dataXMLResources;
	}

}
