package org.kuali.student.ap.plannerreview.form;

import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;
import org.kuali.student.ap.plannerreview.util.ConversationConstants;
import org.kuali.student.ap.plannerreview.dto.AcademicYearInfo;
import org.kuali.student.ap.plannerreview.dto.CourseInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

public class ConversationCreateForm extends ConversationFormBase {

	private static final long serialVersionUID = -6603731205873195959L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ConversationCreateForm.class);

	private String learningPlanId;
	private String advisorId;
	private int wizardStep = ConversationConstants.CREATE_CONV_WIZARD_STEP1;
	private String topicType;
	private boolean includeNotes;
	private boolean includeBackups;
	private String selectedPlan;
	private String fullPlanDisplay;
	private Map<String, CourseInfo> bookmarkedCourses;
	private List<String> selectedBookmarks;
	private String messageTitle;
	private String messageTitleShort;
	private String messageText;
	private List<AcademicYearInfo> availableAcademicYears;
	private List<String> messageTitleItems;
	
	/**
	 * Complete flag used to determine if the create workflow is finished and the lightbox can be closed
	 */
	private boolean complete;
	
	/**
	 * Flag to indicate of any terms have been selected.  This will be used for form validation
	 */
	private boolean selectedTerms = false;
	
	/**
	 * Flag to indicate of any courses have been selected.  This will be used for form validation
	 */
	private boolean selectedCourses = false;
	
	
	public String getLearningPlanId() {
		return learningPlanId;
	}

	public void setLearningPlanId(String learningPlanId) {
		this.learningPlanId = learningPlanId;
	}
	
	public String getAdvisorId() {
		return advisorId;
	}

	public void setAdvisorId(String advisorId) {
		this.advisorId = advisorId;
	}

	public int getWizardStep() {
		return wizardStep;
	}

	public void setWizardStep(int wizardStep) {
		this.wizardStep = wizardStep;
	}

	public String getTopicType() {
		return topicType;
	}

	public void setTopicType(String topicType) {
		this.topicType = topicType;
	}

	public boolean isIncludeNotes() {
		return includeNotes;
	}

	public void setIncludeNotes(boolean includeNotes) {
		this.includeNotes = includeNotes;
	}

	public boolean isIncludeBackups() {
		return includeBackups;
	}

	public void setIncludeBackups(boolean includeBackups) {
		this.includeBackups = includeBackups;
	}

	public String getSelectedPlan() {
		return selectedPlan;
	}

	public void setSelectedPlan(String selectedPlan) {
		this.selectedPlan = selectedPlan;
	}

	public Map<String, CourseInfo> getBookmarkedCourses() {
		return bookmarkedCourses;
	}

	public void setBookmarkedCourses(Map<String, CourseInfo> bookmarkedCourses) {
		this.bookmarkedCourses = bookmarkedCourses;
	}

	public List<String> getSelectedBookmarks() {
		return selectedBookmarks;
	}

	public void setSelectedBookmarks(List<String> selectedBookmarks) {
		this.selectedBookmarks = selectedBookmarks;
	}

	public String getMessageTitle() {
		return messageTitle;
	}

	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}

	public String getMessageTitleShort() {
		return messageTitleShort;
	}

	public void setMessageTitleShort(String messageTitleShort) {
		this.messageTitleShort = messageTitleShort;
	}

	public String getMessageText() {
		return messageText;
	}

	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	public ConversationCreateForm() {
		LOG.debug("DefaultLearningPlanReviewForm()");
	}
	
	public String getSelectedAdvisorName() {
		String name = "";
		if (getAdvisorMap() != null) {
			ConversationAdvisor advisor = getAdvisorMap().get(advisorId);
			if (advisor != null)
				name = advisor.getName();
		}
		return name;
	}
	
	public boolean isStep1() {
		return ConversationConstants.CREATE_CONV_WIZARD_STEP1 == wizardStep;
	}
	
	public boolean isStep2() {
		return ConversationConstants.CREATE_CONV_WIZARD_STEP2 == wizardStep;
	}
	
	public boolean isStep3() {
		return ConversationConstants.CREATE_CONV_WIZARD_STEP3 == wizardStep;
	}
	
	/**
	 * Convenience method to determine if the advisorId field has been set yet
	 * @return True if advisorId is not null or an empty string, false otherwise
	 */
	public boolean isAdvisorIdEmpty() {
		return (advisorId == null || "".equals(advisorId));
	}
	
	/**
	 * Convenience method to determine if the topicType field has been set yet
	 * @return True if topicType is not null or an empty string, false otherwise
	 */
	public boolean isTopicTypeEmpty() {
		return (topicType == null || "".equals(topicType));
	}
	
	/**
	 * Convenience method to determine if the messageText field has been set yet
	 * @return True if messageText is not null or an empty string, false otherwise
	 */
	public boolean isMessageTextEmpty() {
		return (messageText == null || "".equals(messageText));
	}
	
	public String getFullPlanDisplay() {
		return fullPlanDisplay;
	}
	
	public void setFullPlanDisplay(String fullPlanDisplay) {
		this.fullPlanDisplay = fullPlanDisplay;
	}

	public List<AcademicYearInfo> getAvailableAcademicYears() {
		return availableAcademicYears;
	}

	public void setAvailableAcademicYears(List<AcademicYearInfo> availableAcademicYears) {
		this.availableAcademicYears = availableAcademicYears;
	}

	public List<String> getMessageTitleItems() {
		return messageTitleItems;
	}

	public void setMessageTitleItems(List<String> messageTitleItems) {
		this.messageTitleItems = messageTitleItems;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public boolean isSelectedTerms() {
		return selectedTerms;
	}

	public void setSelectedTerms(boolean selectedTerms) {
		this.selectedTerms = selectedTerms;
	}

	public boolean isSelectedCourses() {
		return selectedCourses;
	}

	public void setSelectedCourses(boolean selectedCourses) {
		this.selectedCourses = selectedCourses;
	}

	@Override
	public String toString() {
		return "DefaultLearningPlanReviewForm [learningPlanId="
				+ learningPlanId + ", advisorId=" + advisorId + ", wizardStep="
				+ wizardStep + "]";
	}

}
