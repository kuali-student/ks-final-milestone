package org.kuali.student.ap.coursesearch.dataobject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

import org.apache.commons.lang.StringUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseSearchConstants;

/**
 * Course details that comes from course catalog (KS CM)
 */
public class CourseSummaryDetails implements Serializable {

	private static final long serialVersionUID = 6869257353692530138L;

	private String courseId;
	private String versionIndependentId;
	private String code;

	private String subjectArea;
	private String courseNumber;

	private String courseTitle;
	private String credit;
	private String courseDescription;
	private List<String> termsOffered;

	private String curriculumTitle;

	private List<String> campusLocations;
	private List<String> requisites;
	private List<String> genEdRequirements;
	private List<String> abbrGenEdRequirements;

	private String lastOffered;
	private List<String> scheduledTerms;

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getCourseIdXmlSafe() {
        if(getCourseId()==null)return "";
		return getCourseId().replace('.', '_');
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCourseTitle() {
		// Double quotes are very problematic in the serialization to JSON so
		// change to single quotes.;
		if (StringUtils.isEmpty(courseTitle)) {
			return courseTitle;
		}
		return courseTitle.replaceAll("\"", "'");
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public List<String> getTermsOffered() {
		if (termsOffered == null) {
			termsOffered = new ArrayList<String>();
		}
		return termsOffered;
	}

	public void setTermsOffered(List<String> termsOffered) {
		this.termsOffered = termsOffered;
	}

	public String getVersionIndependentId() {
		return versionIndependentId;
	}

	public void setVersionIndependentId(String versionIndependentId) {
		this.versionIndependentId = versionIndependentId;
	}

	public String getCurriculumTitle() {
		return curriculumTitle;
	}

	public void setCurriculumTitle(String curriculumTitle) {
		this.curriculumTitle = curriculumTitle;
	}

	public List<String> getGenEdRequirements() {
		if (genEdRequirements == null) {
			genEdRequirements = new ArrayList<String>();
		}
		return genEdRequirements;
	}

	public void setGenEdRequirements(List<String> genEdRequirements) {
		this.genEdRequirements = genEdRequirements;
	}

	public List<String> getAbbrGenEdRequirements() {
		if (abbrGenEdRequirements == null) {
			abbrGenEdRequirements = new ArrayList<String>();
		}
		return abbrGenEdRequirements;
	}

	public void setAbbrGenEdRequirements(List<String> abbrGenEdRequirements) {
		this.abbrGenEdRequirements = abbrGenEdRequirements;
	}

	public List<String> getRequisites() {
		if (requisites == null) {
			requisites = new ArrayList<String>();
		}
		return requisites;
	}

	public void setRequisites(List<String> requisites) {
		this.requisites = requisites;
	}

	public List<String> getCampusLocations() {
		if (campusLocations == null) {
			campusLocations = new ArrayList<String>();
		}
		return campusLocations;
	}

	public void setCampusLocations(List<String> campusLocations) {
		this.campusLocations = campusLocations;
	}

	public String getSubjectArea() {
		return subjectArea;
	}

	public void setSubjectArea(String subjectArea) {
		this.subjectArea = subjectArea;
	}

	public String getCourseNumber() {
		return courseNumber;
	}

	public void setCourseNumber(String courseNumber) {
		this.courseNumber = courseNumber;
	}

	public String getLastOffered() {
		return lastOffered;
	}

	public void setLastOffered(String lastOffered) {
		this.lastOffered = lastOffered;
	}

	public List<String> getScheduledTerms() {
		if (scheduledTerms == null) {
			scheduledTerms = new java.util.ArrayList<String>();
		}
		return scheduledTerms;
	}

	public void setScheduledTerms(List<String> scheduledTerms) {
		this.scheduledTerms = scheduledTerms;
	}

	public String getScheduledTermsNames() {
		StringBuilder list = new StringBuilder();
		if (scheduledTerms != null) {
			for (int i = 0; i < scheduledTerms.size(); i++) {
				String term = scheduledTerms.get(i);
				String elemTxt = KsapFrameworkServiceLocator.getTermHelper().getTerm(term).getName();

				// Convert Winter 2012 to WI 12

				Matcher m = CourseSearchConstants.TERM_PATTERN.matcher(KsapFrameworkServiceLocator.getTermHelper()
						.getTerm(term).getName());
				if (m.matches()) {
					elemTxt = m.group(1).substring(0, 2).toUpperCase() + " " + m.group(2);
				}
				list.append(elemTxt + " ");
			}
		}
		return list.toString();
	}

	public String getPlanningTerms() {
		StringBuilder schTermsb = new StringBuilder();
		for (String term : scheduledTerms) {
			String termDescription = KsapFrameworkServiceLocator.getTermHelper().getTerm(term).getDescr().getPlain();
			Element schTermSpan = DocumentHelper.createElement("span");
			schTermSpan.setText(termDescription);
			Matcher m = CourseSearchConstants.TERM_PATTERN.matcher(termDescription);
			String termAbbreviation;
			if (m.matches()) {
				termAbbreviation = m.group(1).substring(0, 2).toUpperCase();
			} else
				termAbbreviation = null;
			if (termAbbreviation != null)
				schTermSpan.addAttribute("class", termAbbreviation);
			if (schTermsb.length() == 0)
				schTermsb.append("Scheduled for ");
			else
				schTermsb.append(" ");
			schTermsb.append(schTermSpan.asXML());
		}
		if (schTermsb.length() == 0) {
			if (termsOffered != null && !termsOffered.isEmpty()) {
				schTermsb.append("Typically offered");
				for (String to : termsOffered)
					schTermsb.append(" ").append(to);
			} else
				schTermsb.append("No currently offered");
		}
		return schTermsb.toString();
	}

	@Override
	public String toString() {
		return "CourseSummaryDetails [courseId=" + courseId + ", code=" + code + ", subjectArea=" + subjectArea
				+ ", courseNumber=" + courseNumber + ", courseTitle=" + courseTitle + "]";
	}

}
