package org.kuali.core.db.torque;

import java.io.File;
import java.util.List;

import org.apache.texen.ant.TexenTask;
import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Project;
import org.apache.torque.engine.database.model.Database;
import org.apache.torque.engine.database.transform.XmlToData;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.context.Context;
import org.springframework.core.io.Resource;

public class KualiTorqueDataSQLTask extends TexenTask {

	/**
	 * The data.dtd file
	 */
	private String dataDTD;

	/**
	 * The list of data XML resources
	 */
	private List<Resource> dataXMLResources;

	/**
	 * The target database(s) we are generating SQL for
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

	/**
	 * Set up the initial context for generating data SQL from data XML
	 */
	public Context initControlContext() throws Exception {
		super.initControlContext();

		// Transform the XML database schema into data model object.
		KualiXmlToAppData xmlParser = new KualiXmlToAppData(getTargetDatabase(), "");
		db = xmlParser.parseFile(getSchemaXMLResource());

		VelocityContext context = new VelocityContext();

		log("foo", Project.MSG_WARN);

		// Place our data xml resources into the context
		context.put("xmlfiles", getDataXMLResources());

		// Put our task into the context
		context.put("task", this);

		// Place the target database in the context.
		context.put("targetDatabase", targetDatabase);

		return context;
	}

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

	public String getTargetDatabase() {
		return targetDatabase;
	}

	public void setTargetDatabase(String v) {
		targetDatabase = v;
	}

	public String getDataDTD() {
		return dataDTD;
	}

	public void setDataDTD(String dataDTD) {
		this.dataDTD = dataDTD;
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
