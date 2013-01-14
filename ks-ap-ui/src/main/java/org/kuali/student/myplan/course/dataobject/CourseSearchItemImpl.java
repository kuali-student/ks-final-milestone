package org.kuali.student.myplan.course.dataobject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kuali.student.ap.framework.course.CourseSearchItem;
import org.kuali.student.myplan.course.util.CollectionListPropertyEditorHtmlListType;
import org.kuali.student.myplan.course.util.FacetKeyFormatter;
import org.springframework.util.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * User: kmuthu
 * Date: 11/3/11
 * Time: 11:08 AM
 * <p/>
 * Wrapper for CourseInfo data.
 */
public class CourseSearchItemImpl implements CourseSearchItem {

    private String courseId;
    private String code;

    private String number;
    private String subject;
    private String level;
    private String courseName;

    private String credit;
    private float creditMin;
    private float creditMax;
    private CreditType creditType;

    private String genEduReq = EMPTY_RESULT_VALUE_KEY;

    private PlanState status = PlanState.UNPLANNED;

    /* Facet keys used for filtering in the view. */
    private Set<String> curriculumFacetKeys = new HashSet<String>();
    private Set<String> courseLevelFacetKeys = new HashSet<String>();
    private Set<String> genEduReqFacetKeys = new HashSet<String>();
    private Set<String> termsFacetKeys = new HashSet<String>();
    private Set<String> scheduledFacetKeys = new HashSet<String>();
    private Set<String> creditsFacetKeys = new HashSet<String>();

    private List<String> termInfoList;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    // aka KSLU_CLU_IDENT.DIVISION
    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }

    public float getCreditMin() {
        return creditMin;
    }

    public void setCreditMin(float creditMin) {
        this.creditMin = creditMin;
    }

    public float getCreditMax() {
        return creditMax;
    }

    public void setCreditMax(float creditMax) {
        this.creditMax = creditMax;
    }

    public CreditType getCreditType() {
        return creditType;
    }

    public void setCreditType(CreditType creditType) {
        this.creditType = creditType;
    }

    /**
     * TODO: This should be handled with a Formatter.
     *
     * @return
     */
    public String getScheduledAndOfferedTerms() {

        CollectionListPropertyEditorHtmlListType listType = CollectionListPropertyEditorHtmlListType.DL;

        Element termsList = DocumentHelper.createElement(listType.getListElementName()); // dl

        if (scheduledTermsList != null && scheduledTermsList.size() > 0) {
            Element termsListItem = termsList.addElement(listType.getListItemElementName()); // dd
            termsListItem.addAttribute("class", "scheduled");
            Element scheduledListElement = termsListItem.addElement(listType.getListElementName()); //  dl
            for (String scheduledTerm : scheduledTermsList) {
                Element scheduledListItem = scheduledListElement.addElement(listType.getListItemElementName()); //  dd
                String termAbbreviation = scheduledTerm.substring(0, 2).toUpperCase();
                scheduledListItem.addAttribute("class", termAbbreviation);
                String year = scheduledTerm.substring(scheduledTerm.length() - 2);
                scheduledListItem.setText(String.format("%s %s", termAbbreviation, year));
            }
        }

        if (termInfoList != null && termInfoList.size() > 0) {
            Element termsListItem = termsList.addElement(listType.getListItemElementName()); // dd
            termsListItem.addAttribute("class", "projected");
            Element termListElement = termsListItem.addElement(listType.getListElementName()); // dl
            for (String term : termInfoList) {
                Element scheduledListItem = termListElement.addElement(listType.getListItemElementName()); //dd
                scheduledListItem.setText(term.substring(0, 2).toUpperCase());
            }
        }
        return termsList.asXML();
    }

    public String getGenEduReq() {
        return genEduReq;
    }

    public void setGenEduReq(String genEduReq) {
        if (StringUtils.hasText(genEduReq)) {
            this.genEduReq = genEduReq;
        }
    }

    public PlanState getStatus() {
        return this.status;
    }

    public void setStatus(PlanState status) {
        this.status = status;
    }

    public boolean isStatusSaved() {
        return status == PlanState.SAVED;
    }

    public boolean isStatusInPlan() {
        return status == PlanState.IN_PLAN;
    }

    public boolean isStatusUnplanned() {
        return status == PlanState.UNPLANNED;
    }

    public Set<String> getCurriculumFacetKeys() {
        return curriculumFacetKeys;
    }

    public Set<String> getCourseLevelFacetKeys() {
        return courseLevelFacetKeys;
    }

    public Set<String> getGenEduReqFacetKeys() {
        return genEduReqFacetKeys;
    }

    public Set<String> getTermsFacetKeys() {
        return termsFacetKeys;
    }

    public Set<String> getScheduledFacetKeys() {
        return scheduledFacetKeys;
    }

    public Set<String> getCreditsFacetKeys() {
        return creditsFacetKeys;
    }

    /**
     * Get a combined set of terms and scheduled.
     *
     * @return
     */
    public Set<String> getQuartersFacetKeys() {
        Set<String> termsAndQuarters = new HashSet<String>();
        termsAndQuarters.addAll(scheduledFacetKeys);
        termsAndQuarters.addAll(termsFacetKeys);
        return termsAndQuarters;
    }

    public String getCourseLevelFacetKey() {
        return FacetKeyFormatter.format(courseLevelFacetKeys);
    }

    public String getCurriculumFacetKey() {
        return FacetKeyFormatter.format(curriculumFacetKeys);
    }

    public String getGenEduReqFacetKey() {
        return FacetKeyFormatter.format(genEduReqFacetKeys);
    }

    public String getTermsFacetKey() {
        return FacetKeyFormatter.format(termsFacetKeys);
    }

    public String getScheduledFacetKey() {
        return FacetKeyFormatter.format(scheduledFacetKeys);
    }

    public String getCreditsFacetKey() {
        return FacetKeyFormatter.format(creditsFacetKeys);
    }

    public String getQuartersFacetKey() {
        return FacetKeyFormatter.format(getQuartersFacetKeys());
    }

    public void setCurriculumFacetKeys(Set<String> curriculumFacetKeys) {
        this.curriculumFacetKeys = curriculumFacetKeys;
    }

    public void setCourseLevelFacetKeys(Set<String> courseLevelFacetKeys) {
        this.courseLevelFacetKeys = courseLevelFacetKeys;
    }

    public void setGenEduReqFacetKeys(Set<String> genEduReqFacetKeys) {
        this.genEduReqFacetKeys = genEduReqFacetKeys;
    }

    public void setTermsFacetKeys(Set<String> termsFacetKeys) {
        this.termsFacetKeys = termsFacetKeys;
    }

    public void setScheduledFacetKeys(Set<String> scheduledFacetKeys) {
        this.scheduledFacetKeys = scheduledFacetKeys;
    }

    public void setCreditsFacetKeys(Set<String> creditsFacetKeys) {
        this.creditsFacetKeys = creditsFacetKeys;
    }

    public List<String> getTermInfoList() {
        return termInfoList;
    }

    public void setTermInfoList(List<String> termInfoList) {
        this.termInfoList = termInfoList;
    }

    private List<String> scheduledTermsList = new ArrayList<String>();

    public List<String> getScheduledTermsList() {
        return this.scheduledTermsList;
    }

    public void setScheduledTerms(List<String> scheduledTermsList) {
        this.scheduledTermsList = scheduledTermsList;
    }

    public void addScheduledTerm(String term) {
        scheduledTermsList.add(term);
    }

    @Override
    public String toString() {
        return String.format("%s: %s", getCode(), getCourseId());
    }
}