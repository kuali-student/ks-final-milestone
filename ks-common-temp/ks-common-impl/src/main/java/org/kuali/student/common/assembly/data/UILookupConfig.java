package org.kuali.student.common.assembly.data;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class UILookupConfig{

	private static final long serialVersionUID = 1L;

	protected UILookupData initialLookup;
	protected List<UILookupData> additionalLookups;
	private String name;
	private String path;
	private String dataType;
 private String type;

	public UILookupData getInitialLookup() {
		return initialLookup;
	}

	public void setInitialLookup(UILookupData initialLookup) {
		this.initialLookup = initialLookup;
	}

	public List<UILookupData> getAdditionalLookups() {
		return additionalLookups;
	}

	public void setAdditionalLookups(List<UILookupData> additionalLookups) {
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

 public String getType () {
  return type;
 }

 public void setType (String type) {
  this.type = type;
 }
}
