package org.kuali.student.lum.lu.assembly.data.client.creditcourse;

import org.kuali.student.common.assembly.Data;
import org.kuali.student.common.assembly.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;

public class CreditCourseFormat extends ModifiableData {
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		ACTIVITIES("activities"), STATE("state");

		private final String key;

		private Properties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
	}

	public CreditCourseFormat() {
		super(CreditCourseFormat.class.getName());
	}

	public Data getActivities() {
		Data result = super.get(Properties.ACTIVITIES.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.ACTIVITIES.getKey(), result);
		}
		return result;
	}

	public void addActivity(CreditCourseActivity activity) {
		getActivities().add(activity);
	}

	public void setState(String state) {
		super.set(Properties.STATE.getKey(), state);
		for (Property p : getActivities()) {
			CreditCourseActivity activity = p.getValue();
			if (activity != null) {
				activity.setState(state);
			}
		}
	}

	public String getState() {
		return super.get(Properties.STATE.getKey());
	}
}
