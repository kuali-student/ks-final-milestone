package org.kuali.student.core.bo;


public abstract class KsTypeStateBusinessObjectBase extends KsMetaBusinessObjectBase implements KsTypeStateBusinessObject {

    private static final long serialVersionUID = -8215829676177229024L;


	private String name;

	private String state;

	private String typeId;


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

}
