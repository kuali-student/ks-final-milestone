package org.kuali.student.lum.lu.assembly.data.client.creditcourse;

import org.kuali.student.common.assembly.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.atp.TimeAmountInfoData;

public class CreditCourseActivity extends ModifiableData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		ACTIVITY_TYPE("activityType"), INTENSITY("intensity"), STATE("state");

		private final String key;

		private Properties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
	}

	public CreditCourseActivity() {
		super(CreditCourseActivity.class.getName());
	}
	public String getActivityType() {
		return super.get(Properties.ACTIVITY_TYPE.getKey());
	}

	public void setActivityType(String activityType) {
		super.set(Properties.ACTIVITY_TYPE.getKey(), activityType);
	}

	public TimeAmountInfoData getIntensity() {
		return super.get(Properties.INTENSITY.getKey());
	}

	public void setIntensity(TimeAmountInfoData intensity) {
		super.set(Properties.INTENSITY.getKey(), intensity);
	}

	public void setState(String state) {
		super.set(Properties.STATE.getKey(), state);
	}
	public String getState() {
		return super.get(Properties.STATE.getKey());
	}
}
