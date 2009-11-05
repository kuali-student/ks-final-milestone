package org.kuali.student.common.assembly.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Metadata {
	// TODO this class, and referenced classes, need to be moved into a GWT module
	private List<Constraint> constraints;
	private String dataType;
	private EnumerationMetadata enumerationMetadata;
	private Map<String, Metadata> properties;
	private boolean masked;
	private boolean readOnly;
	
	public List<Constraint> getConstraints() {
		if (constraints == null) {
			constraints = new ArrayList<Constraint>();
		}
		return constraints;
	}
	public void setConstraints(List<Constraint> constraints) {
		this.constraints = constraints;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public EnumerationMetadata getEnumerationMetadata() {
		return enumerationMetadata;
	}
	public void setEnumerationMetadata(EnumerationMetadata enumerationMetadata) {
		this.enumerationMetadata = enumerationMetadata;
	}
	public Map<String, Metadata> getProperties() {
		if (properties == null) {
			properties = new HashMap<String, Metadata>();
		}
		return properties;
	}
	public void setProperties(Map<String, Metadata> properties) {
		this.properties = properties;
	}
	public boolean isMasked() {
		return masked;
	}
	public void setMasked(boolean masked) {
		this.masked = masked;
	}
	public boolean isReadOnly() {
		return readOnly;
	}
	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}
	
	
}
