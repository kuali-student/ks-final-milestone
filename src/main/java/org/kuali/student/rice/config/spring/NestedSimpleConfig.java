package org.kuali.student.rice.config.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class NestedSimpleConfig extends NestedBaseConfig {


	private Properties baseProperties;
	private Map<String, Object> baseObjects;

	public NestedSimpleConfig() {
		super(new ArrayList<String>());
	}

	public NestedSimpleConfig(Properties properties) {
		super(new ArrayList<String>());
		this.baseProperties = properties;
	}

	public NestedSimpleConfig(List<String> fileLocs, Properties baseProperties) {
		super(fileLocs);
		this.baseProperties = baseProperties;
	}

	public NestedSimpleConfig(List<String> fileLocs) {
		super(fileLocs);
	}

	public NestedSimpleConfig(String fileLoc) {
		this(fileLoc, null);
	}

	public NestedSimpleConfig(String fileLoc, Properties baseProperties) {
		super(fileLoc);
		this.baseProperties = baseProperties;
	}

	@Override
	public Map<String, Object> getBaseObjects() {
		if (this.baseObjects == null) {
		    this.baseObjects = new HashMap<String, Object>();
		}
		return this.baseObjects;
	}

	@Override
	public Properties getBaseProperties() {
		if (this.baseProperties == null) {
			return new Properties();
		}
		return this.baseProperties;
	}


}
