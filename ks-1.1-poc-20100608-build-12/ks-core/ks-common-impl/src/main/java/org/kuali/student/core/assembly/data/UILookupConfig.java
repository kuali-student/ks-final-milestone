package org.kuali.student.core.assembly.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class UILookupConfig{

	private static final long serialVersionUID = 1L;

	protected LookupMetadata initialLookup;
	protected List<LookupMetadata> additionalLookups;
	private String name;
	private String path;
	private String dataType;

	public LookupMetadata getInitialLookup() {
		return initialLookup;
	}

	public void setInitialLookup(LookupMetadata initialLookup) {
		this.initialLookup = initialLookup;
	}

	public List<LookupMetadata> getAdditionalLookups() {
		return additionalLookups;
	}

	public void setAdditionalLookups(List<LookupMetadata> additionalLookups) {
		this.additionalLookups = additionalLookups;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

}
