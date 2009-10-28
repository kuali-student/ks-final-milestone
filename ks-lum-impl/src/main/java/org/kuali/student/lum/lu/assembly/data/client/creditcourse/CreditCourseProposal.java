package org.kuali.student.lum.lu.assembly.data.client.creditcourse;

import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.proposal.ProposalInfoData;

public class CreditCourseProposal extends ModifiableData {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		COURSE("course"), PROPOSAL("proposal");

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
}
