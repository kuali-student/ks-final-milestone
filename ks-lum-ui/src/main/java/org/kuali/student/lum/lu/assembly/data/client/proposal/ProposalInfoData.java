package org.kuali.student.lum.lu.assembly.data.client.proposal;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourse;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourseProposal.Properties;

public class ProposalInfoData extends ModifiableData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		ID("id"), TITLE("title"), RATIONALE("rationale"), STATE("state"), TYPE("type"), REFERENCE_TYPE("referenceType"), REFERENCES("references");

		private final String key;

		private Properties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
	}

	public String getId() {
		return super.get(Properties.ID.getKey());
	}

	public void setId(String id) {
		super.set(Properties.ID.getKey(), id);
	}

	public ProposalInfoData() {
		super(ProposalInfoData.class.getName());
	}

	public String getTitle() {
		return super.get(Properties.TITLE.getKey());
	}

	public void setTitle(String title) {
		super.set(Properties.TITLE.getKey(), title);
	}

	public String getRationale() {
		return super.get(Properties.RATIONALE.getKey());
	}

	public void setRationale(String rationale) {
		super.set(Properties.RATIONALE.getKey(), rationale);
	}
	
	public void setState(String state) {
		super.set(Properties.STATE.getKey(), state);
	}

	public String getState() {
		return super.get(Properties.STATE.getKey());
	}
	
	public void setType(String type) {
		super.set(Properties.TYPE.getKey(), type);
	}
	
	public String getType() {
		return super.get(Properties.TYPE.getKey());
	}
	
	public void setReferenceType(String referenceType) {
		super.set(Properties.REFERENCE_TYPE.getKey(), referenceType);
	}
	
	public String getReferenceType() {
		return super.get(Properties.REFERENCE_TYPE.getKey());
	}
	
	public void addReference(String reference) {
		getReferences().add(reference);
	}
	
	public Data getReferences() {
		Data result = super.get(Properties.REFERENCES.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.REFERENCES.getKey(), result);
		}
		return result;
	}
	
}
