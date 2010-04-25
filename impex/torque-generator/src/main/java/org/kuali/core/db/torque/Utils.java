package org.kuali.core.db.torque;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.torque.engine.database.model.Database;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

public class Utils {
	public void verifyExists(String[] locations) throws FileNotFoundException {
		for (int i = 0; i < locations.length; i++) {
			locations[i] = locations[i].trim();
		}
		DefaultResourceLoader loader = new DefaultResourceLoader();
		for (String location : locations) {
			Resource resource = loader.getResource(location);
			if (!resource.exists()) {
				throw new FileNotFoundException("Unable to locate " + location);
			}
		}
	}
	public List<Database> getDatabases(String schemaXMLResources, String targetDatabase) throws IOException {
		List<Database> databases = new ArrayList<Database>();
		if (schemaXMLResources == null) {
			return databases;
		}
		String[] locations = StringUtils.split(schemaXMLResources, ",");

		verifyExists(locations);

		for (String location : locations) {
			// Get an xml parser for schema.xml
			KualiXmlToAppData xmlParser = new KualiXmlToAppData(targetDatabase, "");

			// Parse schema.xml into a database object
			try {
				Database database = xmlParser.parseFile(location);
				databases.add(database);
			} catch (Exception e) {
				throw new IOException("Error parsing: " + location, e);
			}
		}
		return databases;
	}

}
