package org.kuali.student.lum.lu.assembly.data.client.creditcourse;

import org.kuali.student.common.assembly.Data;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.atp.TimeAmountInfoData;

public class CreditCourse extends ModifiableData {
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		FORMATS("formats"), STATE("state"), TERMS_OFFERED("termsOffered"), DURATION(
				"duration"), TRANSCRIPT_TITLE("transcriptTitle"), COURSE_TITLE(
				"courseTitle"), DESCRIPTION("description"), DEPARTMENT(
				"department"), SUBJECT_AREA("subjectArea"), COURSE_NUMBER_SUFFIX(
				"courseNumberSuffix");

		private final String key;

		private Properties(final String key) {
			this.key = key;
		}

		@Override
		public String getKey() {
			return this.key;
		}
	}

	public CreditCourse() {
		super(CreditCourse.class.getName());
	}

	public Data getFormats() {
		Data result = super.get(Properties.FORMATS.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.FORMATS.getKey(), result);
		}
		return result;
	}

	public void addFormat(CreditCourseFormat format) {
		getFormats().add(format);
	}

	public void setState(String state) {
		super.set(Properties.STATE.getKey(), state);
		for (Property p : getFormats()) {
			CreditCourseFormat format = p.getValue();
			if (format != null) {
				format.setState(state);
			}
		}
	}

	public Data getTermsOffered() {
		Data result = super.get(Properties.TERMS_OFFERED.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.TERMS_OFFERED.getKey(), result);
		}
		return result;
	}

	public void addTermOffered(String term) {
		getTermsOffered().add(term);
	}

	public TimeAmountInfoData getDuration() {
		return super.get(Properties.DURATION.getKey());
	}

	public void setDuration(TimeAmountInfoData duration) {
		super.set(Properties.DURATION.getKey(), duration);
	}

	public void setTranscriptTitle(String title) {
		super.set(Properties.TRANSCRIPT_TITLE.getKey(), title);
	}

	public String getTranscriptTitle() {
		return super.get(Properties.TRANSCRIPT_TITLE.getKey());
	}

	public void setCourseTitle(String title) {
		super.set(Properties.COURSE_TITLE.getKey(), title);
	}

	public String getCourseTitle() {
		return super.get(Properties.COURSE_TITLE.getKey());
	}

	/**
	 * Returns the formatted (rich text/html) content of the description.
	 * Conversion to RichTextInfo should be handled at assembler.
	 * 
	 * @return
	 */
	public String getDescription() {
		return super.get(Properties.DESCRIPTION.getKey());
	}

	public void setDescription(String description) {
		super.set(Properties.DESCRIPTION.getKey(), description);
	}

	public void setDepartment(String orgId) {
		super.set(Properties.DEPARTMENT.getKey(), orgId);
	}

	public String getDepartment() {
		return super.get(Properties.DEPARTMENT.getKey());
	}

	public String getSubjectArea() {
		return super.get(Properties.SUBJECT_AREA.getKey());
	}

	public void setSubjectArea(String subjectArea) {
		super.set(Properties.SUBJECT_AREA.getKey(), subjectArea);
	}

	public String getCourseNumberSuffix() {
		return super.get(Properties.COURSE_NUMBER_SUFFIX.getKey());

	}

	public void setCourseNumberSuffix(String suffix) {
		super.set(Properties.COURSE_NUMBER_SUFFIX.getKey(), suffix);
	}
}
