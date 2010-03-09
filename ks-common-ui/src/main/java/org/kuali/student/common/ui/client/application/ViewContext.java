package org.kuali.student.common.ui.client.application;

/**
 * ViewContext can be used to pass along context information when switching or initializing a view.
 * 
 * For example a display view requires the id of the object to display, the view context can be used
 * to pass along that information from a different controller or view.
 *
 */
public class ViewContext {
	public enum IdType {
		PROPOSAL_ID("proposalId"), DOCUMENT_ID("documentNumber"), OBJECT_ID("objectId");
        
		final String stringValue;

		private IdType(String value) {
            this.stringValue = value;
        }

        public String toString() {
            return stringValue;
        }
	}
	
	private String id = "";
	private IdType idType = IdType.OBJECT_ID;	
	private String state = "draft";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public IdType getIdType() {
		return idType;
	}

	public void setIdType(IdType idType) {
		this.idType = idType;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
