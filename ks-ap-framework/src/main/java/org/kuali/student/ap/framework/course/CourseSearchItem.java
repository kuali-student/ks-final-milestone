package org.kuali.student.ap.framework.course;

import java.util.List;
import java.util.Set;

public interface CourseSearchItem {
	
    static final String EMPTY_RESULT_VALUE_KEY = "&mdash;";

	enum CreditType {
		fixed, range, multiple, unknown
	}

	enum PlanState {
		UNPLANNED(""), SAVED("Bookmarked"), IN_PLAN("Planned");

		private final String label;

		PlanState(String label) {
			this.label = label;
		}

		public String getLabel() {
			return this.label;
		}
	}

    String getCourseId();

    void setCourseId(String courseId);

    String getNumber();

    void setNumber(String number);

    String getSubject();

    void setSubject(String subject);

    String getCode();

    void setCode(String code);

    String getLevel();

    void setLevel(String level);

    String getCourseName();

    void setCourseName(String courseName);

	String getScheduledAndOfferedTerms();

    String getCredit();

    void setCredit(String credit);

    float getCreditMin();

    void setCreditMin(float creditMin);

    float getCreditMax();

    void setCreditMax(float creditMax);

    CreditType getCreditType();

    void setCreditType(CreditType creditType);

	PlanState getStatus();

	void setStatus(PlanState status);

	boolean isStatusSaved();

	boolean isStatusInPlan();

	boolean isStatusUnplanned();

	Set<String> getCurriculumFacetKeys();

	Set<String> getCourseLevelFacetKeys();

	Set<String> getGenEduReqFacetKeys();

	Set<String> getTermsFacetKeys();

	Set<String> getScheduledFacetKeys();

	Set<String> getCreditsFacetKeys();

	Set<String> getQuartersFacetKeys();

	String getCourseLevelFacetKey();

	String getCurriculumFacetKey();

	String getGenEduReqFacetKey();

	String getTermsFacetKey();

	String getScheduledFacetKey();

	String getCreditsFacetKey();

	String getQuartersFacetKey();

    String getGenEduReq();

    String[] getSearchColumns();

    void setGenEduReq(String genEduReq);

	void setCurriculumFacetKeys(Set<String> curriculumFacetKeys);

	void setCourseLevelFacetKeys(Set<String> courseLevelFacetKeys);

	void setGenEduReqFacetKeys(Set<String> genEduReqFacetKeys);

	void setTermsFacetKeys(Set<String> termsFacetKeys);

	void setScheduledFacetKeys(Set<String> scheduledFacetKeys);

	void setCreditsFacetKeys(Set<String> creditsFacetKeys);

	List<String> getTermInfoList();

	void setTermInfoList(List<String> termInfoList);

	List<String> getScheduledTermsList();

	void setScheduledTerms(List<String> scheduledTermsList);

	void addScheduledTerm(String term);

}
