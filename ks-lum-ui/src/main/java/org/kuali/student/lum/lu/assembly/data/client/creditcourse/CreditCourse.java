package org.kuali.student.lum.lu.assembly.data.client.creditcourse;

import java.util.Date;

import org.kuali.student.common.assembly.client.Data;
import org.kuali.student.lum.lu.assembly.data.client.CluInstructorInfoData;
import org.kuali.student.lum.lu.assembly.data.client.ModifiableData;
import org.kuali.student.lum.lu.assembly.data.client.PropertyEnum;
import org.kuali.student.lum.lu.assembly.data.client.RichTextInfoData;
import org.kuali.student.lum.lu.assembly.data.client.atp.TimeAmountInfoData;

public class CreditCourse extends ModifiableData {
	private static final long serialVersionUID = 1L;

	public enum Properties implements PropertyEnum {
		ID("id"), FORMATS("formats"), TERMS_OFFERED("termsOffered"), DURATION(
				"duration"), TRANSCRIPT_TITLE("transcriptTitle"), COURSE_TITLE(
				"courseTitle"), DESCRIPTION("description"), DEPARTMENT(
				"department"), SUBJECT_AREA("subjectArea"), COURSE_NUMBER_SUFFIX(
				"courseNumberSuffix"), STATE("state"), TYPE("type"), 
				EFFECTIVE_DATE("effectiveDate"), EXPIRATION_DATE("expirationDate"),
				ACADEMIC_SUBJECT_ORGS("academicSubjectOrgs"), 
				CAMPUS_LOCATIONS("campusLocations"),
				ALTERNATE_IDENTIFIERS("alternateIdentifiers"),
				VERSION_CODES("versionCodes"),
				RATIONALE("rationale"),
				PRIMARY_INSTRUCTOR("primaryInstructor");

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


	public Data getTermsOffered() {
		Data result = super.get(Properties.TERMS_OFFERED.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.TERMS_OFFERED.getKey(), result);
		}
		return result;
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
	public RichTextInfoData getDescription() {
		return super.get(Properties.DESCRIPTION.getKey());
	}

	public void setDescription(RichTextInfoData description) {
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
	
	public void setState(String state) {
		super.set(Properties.STATE.getKey(), state);
		for (Property p : getFormats()) {
			CreditCourseFormat format = p.getValue();
			if (format != null) {
				format.setState(state);
			}
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
	
	public void setId(String id) {
		super.set(Properties.ID.getKey(), id);
	}
	
	public String getId() {
		return super.get(Properties.ID.getKey());
	}
	
	public Date getEffectiveDate() {
		return super.get(Properties.EFFECTIVE_DATE.getKey());
	}
	
	public void setEffectiveDate(Date value)  {
		super.set(Properties.EFFECTIVE_DATE.getKey(), value);
	}
	
	public Date getExpirationDate() {
		return super.get(Properties.EXPIRATION_DATE.getKey());
	}
	
	public void setExpirationDate(Date value)  {
		super.set(Properties.EXPIRATION_DATE.getKey(), value);
	}
	
	public Data getAcademicSubjectOrgs() {
		Data result = super.get(Properties.ACADEMIC_SUBJECT_ORGS.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.ACADEMIC_SUBJECT_ORGS.getKey(), result);
		}
		return result;
	}
	public void setAcademicSubjectOrgs(Data value) {
		super.set(Properties.ACADEMIC_SUBJECT_ORGS.getKey(), value);
	}
	
	public Data getCampusLocations() {
		Data result = super.get(Properties.CAMPUS_LOCATIONS.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.CAMPUS_LOCATIONS.getKey(), result);
		}
		return result;
	}
	
	public void setCampusLocations(Data value) {
		super.set(Properties.CAMPUS_LOCATIONS.getKey(), value);
	}
	
	public Data getAlternateIdentifiers() {
		Data result = super.get(Properties.ALTERNATE_IDENTIFIERS.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.ALTERNATE_IDENTIFIERS.getKey(), result);
		}
		return result;
	}
	
	public void setAlternateIdentifiers(Data alternateIdentifiers) {
		super.set(Properties.ALTERNATE_IDENTIFIERS.getKey(), alternateIdentifiers);
	}
	
	public Data getVersionCodes() {
		Data result = super.get(Properties.VERSION_CODES.getKey());
		if (result == null) {
			result = new Data();
			super.set(Properties.VERSION_CODES.getKey(), result);
		}
		return result;
	}
	
	public void setVersionCodes(Data versionCodes) {
		super.set(Properties.VERSION_CODES.getKey(), versionCodes);
	}
	
	public void setRationale(RichTextInfoData rationale) {
		super.set(Properties.RATIONALE.getKey(), rationale);
	}
	
	public RichTextInfoData getRationale() {
		return super.get(Properties.RATIONALE.getKey());
	}

	public void setPrimaryInstructor(CluInstructorInfoData instructor) {
		super.set(Properties.PRIMARY_INSTRUCTOR.getKey(), instructor);
	}
	
	public CluInstructorInfoData getPrimaryInstructor() {
		return super.get(Properties.PRIMARY_INSTRUCTOR.getKey());
	}

	
}
