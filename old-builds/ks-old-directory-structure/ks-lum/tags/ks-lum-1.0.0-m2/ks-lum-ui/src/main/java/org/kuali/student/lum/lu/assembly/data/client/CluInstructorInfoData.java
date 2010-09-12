package org.kuali.student.lum.lu.assembly.data.client;

import org.kuali.student.lum.lu.assembly.data.client.atp.TimeAmountInfoData;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourse.Properties;

public class CluInstructorInfoData extends ModifiableData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		PERSON_ID("personId"),
		ORG_ID("orgId");

		private final String key;

		private Properties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
	}

	public CluInstructorInfoData() {
		super(CluInstructorInfoData.class.getName());
	}
	public String getPersonId() {
		return super.get(Properties.PERSON_ID.getKey());
	}
	public String getOrgId() {
		return super.get(Properties.ORG_ID.getKey());
	}

	public void setPersonId(String personId) {
		super.set(Properties.PERSON_ID.getKey(), personId);
	}
	public void setOrgId(String orgId) {
		super.set(Properties.ORG_ID.getKey(), orgId);
	}
}
