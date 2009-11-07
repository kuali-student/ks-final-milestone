package org.kuali.student.lum.lu.assembly.data.client.creditcourse;

import org.kuali.student.common.assembly.client.Data.Property;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.creditcourse.CreditCourse.Properties;
import org.kuali.student.lum.lu.assembly.data.client.proposal.ProposalInfoData;

public class CreditCourseProposal extends ModifiableData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		COURSE("course"), PROPOSAL("proposal"), STATE("state"), TYPE("type");

		private final String key;

		private Properties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
	}

	public CreditCourseProposal() {
		super(CreditCourseProposal.class.getName());
	}

	public CreditCourse getCourse() {
		return super.get(Properties.COURSE.getKey());
	}
	public void setCourse(CreditCourse course) {
		super.set(Properties.COURSE.getKey(), course);
	}
	public ProposalInfoData getProposal() {
		return super.get(Properties.PROPOSAL.getKey());
	}
	public void setProposal(ProposalInfoData proposal) {
		super.set(Properties.PROPOSAL.getKey(), proposal);
	}
	
	public void setState(String state) {
		super.set(Properties.STATE.getKey(), state);
		CreditCourse course = getCourse();
		if (course != null) {
			course.setState(state);
		}
		ProposalInfoData proposal = getProposal();
		if (proposal != null) {
			proposal.setState(state);
		}
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
	
}
