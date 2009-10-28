package org.kuali.student.lum.lu.assembly.data.client.proposal;

import org.kuali.student.common.assembly.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;

public class ProposalInfoData extends ModifiableData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		TITLE("title"), RATIONALE("rationale");

		private final String key;

		private Properties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
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

}
