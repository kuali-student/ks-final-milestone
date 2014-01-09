package org.kuali.student.ap.plannerreview.controller;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.resourceloader.GlobalResourceLoader;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.coursesearch.service.impl.CourseDetailsInquiryHelperImpl;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.TermHelper;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.ap.plannerreview.form.ConversationCreateForm;
import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;
import org.kuali.student.ap.plannerreview.util.ConversationConstants;
import org.kuali.student.ap.plannerreview.infc.LearningPlanReviewTerm;
import org.kuali.student.ap.plannerreview.dto.AcademicYearInfo;
import org.kuali.student.ap.plannerreview.dto.ConversationAdvisorInfo;
import org.kuali.student.ap.plannerreview.dto.CourseInfo;
import org.kuali.student.ap.plannerreview.dto.CourseTypeInfo;
import org.kuali.student.ap.plannerreview.dto.LearningPlanReviewRequestInfo;
import org.kuali.student.ap.plannerreview.dto.LearningPlanReviewTermInfo;
import org.kuali.student.ap.plannerreview.dto.PlanTermInfo;
import org.kuali.student.common.util.KSCollectionUtils;
import org.kuali.student.r2.core.acal.dto.AcademicCalendarInfo;
import org.kuali.student.r2.core.acal.service.AcademicCalendarService;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.myplan.plan.dataobject.PlanItemDataObject;
import org.kuali.student.myplan.plan.dataobject.PlannedCourseDataObject;
import org.kuali.student.r2.common.dto.RichTextInfo;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.RichText;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

@Controller
@RequestMapping(value = "/reviewCreate")
public class ConversationCreateController extends ConversationControllerBase {

	private static final Logger LOG = Logger
			.getLogger(ConversationCreateController.class);

	public static final String CREATE_FORM = "Conversation-create-FormView";

	private AcademicPlanService academicPlanService;
	private AcademicCalendarService academicCalendarService;
	private CourseDetailsInquiryHelperImpl courseDetailsInquiryService;
	private TermHelper termHelper;

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		LOG.debug("IN createInitialForm()");
		return new ConversationCreateForm();
	}

	@RequestMapping(params = "methodToCall=create", method = RequestMethod.GET)
	public ModelAndView getCreate(
			@ModelAttribute("KualiForm") ConversationCreateForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		init(form);
		return changeStep(ConversationConstants.CREATE_CONV_WIZARD_STEP1, form,
				result, request, response);
	}

	@RequestMapping(params = "methodToCall=nextStep")
	public ModelAndView getNextStep(
			@ModelAttribute("KualiForm") ConversationCreateForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		return changeStep(form.getWizardStep() + 1, form, result, request,
				response);
	}

	@RequestMapping(params = "methodToCall=previousStep")
	public ModelAndView getPreviousStep(
			@ModelAttribute("KualiForm") ConversationCreateForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return changeStep(form.getWizardStep() - 1, form, result, request,
				response);
	}

	@RequestMapping(params = "methodToCall=step1")
	public ModelAndView gotoStep1(
			@ModelAttribute("KualiForm") ConversationCreateForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return changeStep(ConversationConstants.CREATE_CONV_WIZARD_STEP1, form,
				result, request, response);
	}

	@RequestMapping(params = "methodToCall=step2")
	public ModelAndView gotoStep2(
			@ModelAttribute("KualiForm") ConversationCreateForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return changeStep(ConversationConstants.CREATE_CONV_WIZARD_STEP2, form,
				result, request, response);
	}

	@RequestMapping(params = "methodToCall=step3")
	public ModelAndView gotoStep3(
			@ModelAttribute("KualiForm") ConversationCreateForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return changeStep(ConversationConstants.CREATE_CONV_WIZARD_STEP3, form,
				result, request, response);
	}

	@RequestMapping(params = "methodToCall=send")
	public ModelAndView submit(
			@ModelAttribute("KualiForm") ConversationCreateForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		if (!result.hasErrors()) {
			LearningPlanReviewRequestInfo lprr = new LearningPlanReviewRequestInfo();
			ConversationAdvisor advisorInfo = form.getAdvisorMap().get(
					form.getAdvisorId());
			lprr.setAdvisor(advisorInfo);

			RichText richMessage = new RichTextInfo(form.getMessageText(),
					form.getMessageText());
			lprr.setFirstCommentToAdvisor(richMessage);

			lprr.setOriginalLearningPlanId(form.getLearningPlanId());
			lprr.setReviewTerms(getSelectedCoursesForRequest(
					form.getAvailableAcademicYears(), form.getTopicType(),
					form.isIncludeBackups()));
			lprr.setTopic(form.getMessageTitle());

			lprr.setUnassignedCourseIds(form.getSelectedBookmarks());

			try {
				String reviewId = getLearningPlanReviewStrategy()
						.createLearningPlanReview(lprr);
				form.setComplete(true);
				LOG.debug("Review created: " + reviewId);
			} catch (PermissionDeniedException e) {
				throw new ServletException("Unexpected authorization failure",
						e);
			}

		}
		return getUIFModelAndView(form);
	}

	/**
	 * Build up the list of courses that will be used to create the snapshot
	 * 
	 * @param academicYears
	 * @param selectionType
	 *            Flag indicating if the user selected all/terms/courses
	 * @param includeBackup
	 *            Flag indicating that backup courses should be included
	 * @return
	 */
	private List<LearningPlanReviewTerm> getSelectedCoursesForRequest(
			List<AcademicYearInfo> academicYears, String selectionType,
			boolean includeBackup) {
		List<LearningPlanReviewTerm> terms = new ArrayList<LearningPlanReviewTerm>();
		for (AcademicYearInfo year : academicYears) {
			for (PlanTermInfo term : year.getTerms()) {
				LearningPlanReviewTermInfo lprTerm = new LearningPlanReviewTermInfo();
				lprTerm.setTermId(term.getTermId());
				List<CourseInfo> courses = new ArrayList<CourseInfo>();
				if (includeBackup
						|| ConversationConstants.CONV_TOPIC_TYPE_COURSES
								.equals(selectionType)) {
					courses = term.getAllCourses();
				} else {
					courses = term.getPlannedCourses();
				}
				List<String> courseIds = new ArrayList<String>();
				for (CourseInfo course : courses) {
					boolean add = false;
					if (ConversationConstants.CONV_TOPIC_TYPE_ALL
							.equals(selectionType)) {
						add = true;
					} else if (ConversationConstants.CONV_TOPIC_TYPE_TERMS
							.equals(selectionType)) {
						add = term.isSelected();
					} else if (ConversationConstants.CONV_TOPIC_TYPE_COURSES
							.equals(selectionType)) {
						add = course.getIsChecked();
					}
					if (add) {
						courseIds.add(course.getCourseId());
					}
				}
				lprTerm.setCourseIds(courseIds);
				terms.add(lprTerm);
			}
		}

		return terms;
	}

	/**
	 * Move from step to step in the wizard style screens
	 * 
	 * @param newStep
	 * @param form
	 * @param result
	 * @param request
	 * @param response
	 * @return
	 * @throws java.io.IOException
	 */
	private ModelAndView changeStep(int newStep, ConversationCreateForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		LOG.debug("IN changeStep(" + newStep + ")");
		super.start(form, result, request, response);
		LOG.debug("CREATE_FORM: " + form);

		if (ConversationConstants.CREATE_CONV_WIZARD_STEP3 == newStep) {
			determineSelectedCourses(form);
		}
		int currentStep = form.getWizardStep();

		// No need to validate (or change the step on the form) if the values
		// match
		if (currentStep > newStep
				|| (currentStep != newStep && validateFormPerStep(form))) {
			// set the next step value
			form.setWizardStep(newStep);
		}

		String viewId = CREATE_FORM;

		form.setViewId(viewId);
		form.setView(super.getViewService().getViewById(viewId));
		return getUIFModelAndView(form);
	}

	/**
	 * Do some form validation before progressing on to the next step in the
	 * wizard flow
	 * 
	 * @param form
	 *            Form to validate
	 * @return Boolean indicating if validation passed or not
	 */
	private boolean validateFormPerStep(ConversationCreateForm form) {
		boolean validates = true;
		String errorKey = "foo";

		int currentStep = form.getWizardStep();
		switch (currentStep) {
		case ConversationConstants.CREATE_CONV_WIZARD_STEP1:
			if (form.getAdvisorId() == null)
				validates = false;
			errorKey = ConversationConstants.CONV_MISSING_ADVISOR_KEY;
			break;
		case ConversationConstants.CREATE_CONV_WIZARD_STEP2:
			if (form.getTopicType() == null) {
				validates = false;
				errorKey = ConversationConstants.CONV_MISSING_TOPICTYPE_KEY;
			} else {
				String type = form.getTopicType();
				if (ConversationConstants.CONV_TOPIC_TYPE_TERMS.equals(type)
						&& !form.isSelectedTerms()) {
					validates = false;
					errorKey = ConversationConstants.CONV_MISSING_TERMS_KEY;
				} else if (ConversationConstants.CONV_TOPIC_TYPE_COURSES
						.equals(type) && !form.isSelectedCourses()) {
					validates = false;
					errorKey = ConversationConstants.CONV_MISSING_COURSES_KEY;
				}
			}
			break;
		case ConversationConstants.CREATE_CONV_WIZARD_STEP3:
			if (form.getMessageText() == null) {
				validates = false;
				errorKey = ConversationConstants.CONV_MISSING_MESSAGETEXT_KEY;
			}
			break;
		}

		if (!validates) {
			GlobalVariables.getMessageMap().clearErrorMessages();
			GlobalVariables.getMessageMap().putErrorForSectionId(
					"create_conversation_page", errorKey);
			GlobalVariables.getMessageMap().addGrowlMessage("", errorKey);
		}

		return validates;
	}

	/**
	 * Go through the form's academicYear property looking for courses/terms
	 * that have been selected
	 * 
	 * @param form
	 * @see LearningPlanReviewForm#getAcademicYears()
	 * @see LearningPlanReviewForm#setMessageTitle(String)
	 */
	private void determineSelectedCourses(ConversationCreateForm form) {
		SortedSet<PlanTermInfo> termSet = new TreeSet<PlanTermInfo>();
		SortedSet<CourseInfo> courseSet = new TreeSet<CourseInfo>();
		List<String> messageTitleItems = new ArrayList<String>();

		// Reset the flags for determining if courses or terms have been
		// selected
		form.setSelectedTerms(false);
		form.setSelectedCourses(false);

		// Get the text for the full plan that was selected
		if (ConversationConstants.CONV_TOPIC_TYPE_ALL.equals(form
				.getTopicType())) {
			messageTitleItems.add(form.getFullPlanDisplay());
		}

		List<AcademicYearInfo> acadYears = form.getAvailableAcademicYears();
		if (acadYears != null) {
			for (AcademicYearInfo aYear : acadYears) {
				for (PlanTermInfo term : aYear.getTerms()) {
					if (term.isSelected()) {
						termSet.add(term);
						form.setSelectedTerms(true);
					}
					for (CourseTypeInfo cti : term.getCourseTypes()) {
						for (CourseInfo course : cti.getCourses()) {
							if (course.getIsChecked()) {
								courseSet.add(course);
								termSet.add(term);
								form.setSelectedCourses(true);
							}
						}
					}
				}
			}
		}

		// Get the text for the terms selected
		for (PlanTermInfo term : termSet) {
			messageTitleItems.add(term.getFullName());
		}

		// Get the text for the courses selected
		for (CourseInfo course : courseSet) {
			messageTitleItems.add(course.getCourseCode());
		}

		// Get the text for the bookmarked courses selected
		Map<String, CourseInfo> bookmarkedCourses = form.getBookmarkedCourses();
		List<String> selectedBookmarks = form.getSelectedBookmarks();
		if (selectedBookmarks != null) {
			for (String courseId : selectedBookmarks) {
				CourseInfo course = bookmarkedCourses.get(courseId);
				messageTitleItems.add(course.getCourseCode());
			}
		}

		StringBuilder fullText = new StringBuilder();
		StringBuilder condensedText = new StringBuilder();

		int count = 0;
		int itemsToDisplay = ConversationConstants.CONV_MESSAGE_TITLE_ITEM_LENGTH;
		for (String item : messageTitleItems) {
			fullText.append(richSpanWrap(item) + ", ");

			if (count < itemsToDisplay)
				condensedText.append(richSpanWrap(item) + ", ");
			count++;
		}

		int howManyMore = messageTitleItems.size() - itemsToDisplay;

		if (howManyMore > 0) {
			String moreLink = "and [id='show_more_link' actionLabel='&nbsp;"
					+ howManyMore + " more']";
			condensedText.append(moreLink);
		} else {
			// remove the last ', ' from the string
			int length = condensedText.length();
			if (length > 2)
				condensedText.delete(length - 2, length);
		}

		// remove the last ', ' from the string
		int length = fullText.length();
		if (length > 2)
			fullText.delete(length - 2, length);

		form.setMessageTitle(fullText.toString());
		form.setMessageTitleShort(condensedText.toString());
	}

	/**
	 * Wrap a string with a rich span tag so that css formatting can be done on
	 * it on the front end
	 * 
	 * @param content
	 *            String content to be wrapped
	 * @return Original string with a span tag wrapped around it
	 */
	private String richSpanWrap(String content) {
		String output = "";
		if (content != null && !"".equals(content)) {
			output = "[span]" + content + "[/span]";
		}
		return output;
	}

	/**
	 * Initialize all the advisor, term, course data
	 * 
	 * @param form
	 */
	private void init(ConversationCreateForm form) {
		Map<String, CourseInfo> bookmarks = new HashMap<String, CourseInfo>();
		SortedSet<String> atps = new TreeSet<String>();
		Map<String, PlanTermInfo> terms = new HashMap<String, PlanTermInfo>();
		List<AcademicYearInfo> availableAcademicYears = new ArrayList<AcademicYearInfo>();

		List<LearningPlanInfo> plans;
        String planId;
		try {
			plans = getAcademicPlanService().getLearningPlansForStudentByType(
					getUserId(), PlanConstants.LEARNING_PLAN_TYPE_PLAN,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
            planId = KSCollectionUtils.getRequiredZeroElement(plans).getId();
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP lookup failure", e);
		}


		form.setLearningPlanId(planId);
		List<PlanItemInfo> planItems;
		try {
			planItems = getAcademicPlanService().getPlanItemsInPlan(planId,
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("LP lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("LP lookup failure", e);
		}

		for (PlanItemInfo planItem : planItems) {
			PlannedCourseDataObject plannedCourseDO = new PlannedCourseDataObject();
			String courseID = planItem.getRefObjectId();
			// Only create a data object for the specified type.

			plannedCourseDO.setPlanItemDataObject(PlanItemDataObject
					.build(planItem));

			// If the course info lookup fails just log the error and
			// omit the item.
			try {
				if (getCourseDetailsInquiryService().isCourseIdValid(courseID)) {
					plannedCourseDO
							.setCourseDetails(getCourseDetailsInquiryService()
									.retrieveCourseSummaryById(courseID));
				}
			} catch (Exception e) {
				LOG.error(String.format(
						"Unable to retrieve course info for plan item [%s].",
						planItem.getId()), e);
				continue;
			}
			String atp = plannedCourseDO.getPlanItemDataObject().getAtp();

			CourseInfo course = new CourseInfo();
			course.setCourseId(plannedCourseDO.getCourseDetails().getCourseId());
			course.setCourseCode(plannedCourseDO.getCourseDetails().getCode());
			course.setCourseName(plannedCourseDO.getCourseDetails()
					.getCourseTitle());
			course.setCourseDescription(plannedCourseDO.getCourseDetails()
					.getCourseDescription());
			boolean backup = false;

			if (AcademicPlanServiceConstants.ItemCategory.WISHLIST.equals(planItem
					.getCategory())) {
				// Bookmarks don't have a term
				bookmarks.put(course.getCourseId(), course);
			} else {
				PlanTermInfo pTerm = terms.get(atp);
				if (pTerm == null) {
					YearTerm term = getTermHelper().getYearTerm(atp);

					pTerm = new PlanTermInfo(term.getTermId(),
							term.getTermName(), term.getYear());
					// term.
					terms.put(atp, pTerm);
					addTermToAcademicCalendar(pTerm, availableAcademicYears);
				}
				if (atp != null) {
					atps.add(atp);
				}
				CourseInfo courseInfo = new CourseInfo(course.getCourseId(),
						course.getCourseCode(), course.getCourseName(),
						course.getCourseDescription(), backup);
				if (AcademicPlanServiceConstants.ItemCategory.PLANNED
						.equals(planItem.getCategory())) {
					// addCourseToMap(plannedCourses, course, atp);
					pTerm.addPlannedCourse(courseInfo);
				} else if (AcademicPlanServiceConstants.ItemCategory.BACKUP
						.equals(planItem.getCategory())) {
					// addCourseToMap(backupCourses, course, atp);
					backup = true;
					courseInfo.setBackup(backup);
					pTerm.addBackupCourse(courseInfo);
				}
			}
		}

		form.setBookmarkedCourses(bookmarks);
		Map<String, ConversationAdvisorInfo> advisorMap = initAdvisorData();
		form.setAdvisorMap(advisorMap);

		// If there's only one available advisor, go ahead and select it on the
		// form
		if (advisorMap.size() == 1) {
			String advisorId = advisorMap.keySet().iterator().next();
			form.setAdvisorId(advisorId);
		}

		// Add some more mock data if it was asked for by the form
		if (form.isMockData()) {
			availableAcademicYears.addAll(initMockData());
			// LOG.debug("ALL ACADEMIC YEAR DATA: " + availableAcademicYears);
		}

		form.setAvailableAcademicYears(availableAcademicYears);

		String fullPlanDisplay = "No plan terms available";
		if (atps.size() > 1) {
			fullPlanDisplay = terms.get(atps.first()).getFullName() + " - "
					+ terms.get(atps.last()).getFullName();
		} else if (atps.size() == 1) {
			fullPlanDisplay = terms.get(atps.first()).getFullName();
		}
		form.setFullPlanDisplay(fullPlanDisplay);
	}

	/**
	 * Create mock data. This is really only used for development purposes.
	 * 
	 * @param atp
	 */
	private List<AcademicYearInfo> initMockData() {
		List<AcademicYearInfo> acadYears = new ArrayList<AcademicYearInfo>();
		AcademicYearInfo ayi = new AcademicYearInfo("year1", "2010-2011");
		List<PlanTermInfo> pTerms = new ArrayList<PlanTermInfo>();
		PlanTermInfo pTerm = new PlanTermInfo("term1", "Fall", 2010);
		initMockCourseData(pTerm);
		pTerms.add(pTerm);
		pTerm = new PlanTermInfo("term2", "Spring", 2011);
		initMockCourseData(pTerm);
		pTerms.add(pTerm);
		pTerm = new PlanTermInfo("term3", "Summer", 2011);
		initMockCourseData(pTerm);
		pTerms.add(pTerm);

		ayi.setTerms(pTerms);
		acadYears.add(ayi);
		LOG.debug(ayi.toString());

		ayi = new AcademicYearInfo("year2", "2011-2012");
		pTerms = new ArrayList<PlanTermInfo>();
		pTerm = new PlanTermInfo("term4", "Fall", 2011);
		initMockCourseData(pTerm);
		pTerms.add(pTerm);
		pTerm = new PlanTermInfo("term5", "Spring", 2012);
		initMockCourseData(pTerm);
		pTerms.add(pTerm);
		pTerm = new PlanTermInfo("term6", "Summer", 2012);
		initMockCourseData(pTerm);
		pTerms.add(pTerm);

		ayi.setTerms(pTerms);
		acadYears.add(ayi);
		LOG.debug(ayi.toString());

		return acadYears;
	}

	/**
	 * Create some mock course data. This is really only used for development
	 * purposes
	 * 
	 * @param pTerm
	 */
	private void initMockCourseData(PlanTermInfo pTerm) {
		CourseInfo courseInfo = new CourseInfo("courseId1", "COURSE-A 100",
				"Test course 1", "Description for course 1", false);
		pTerm.addPlannedCourse(courseInfo);
		courseInfo = new CourseInfo("courseId2", "COURSE-B 200",
				"Test course 2", "Description for course 2", false);
		pTerm.addPlannedCourse(courseInfo);
		courseInfo = new CourseInfo("courseId3", "COURSE-C 300",
				"Test course 3", "Description for course 3", false);
		pTerm.addPlannedCourse(courseInfo);
		courseInfo = new CourseInfo("courseId4", "COURSE-D 400",
				"Test course 4", "Description for course 4", false);
		pTerm.addPlannedCourse(courseInfo);

		courseInfo = new CourseInfo("courseId5", "COURSE-E 500",
				"Test course 5", "Description for course 5", true);
		pTerm.addBackupCourse(courseInfo);
		courseInfo = new CourseInfo("courseId6", "COURSE-F 600",
				"Test course 6", "Description for course 6", true);
		pTerm.addBackupCourse(courseInfo);
		courseInfo = new CourseInfo("courseId7", "COURSE-G 700",
				"Test course 7", "Description for course 7", true);
		pTerm.addBackupCourse(courseInfo);
		courseInfo = new CourseInfo("courseId8", "COURSE-H 800",
				"Test course 8", "Description for course 8", true);
		pTerm.addBackupCourse(courseInfo);
	}

	/**
	 * Add a term to an academic calendar
	 * 
	 * @param pTerm
	 *            term to add
	 * @param aYears
	 *            List of academic calendars.
	 * @return
	 */
	private boolean addTermToAcademicCalendar(PlanTermInfo pTerm,
			List<AcademicYearInfo> aYears) {
		List<AcademicCalendarInfo> acals;
		try {
			acals = getAcademicCalendarService().getAcademicCalendarsForTerm(
					pTerm.getTermId(),
					KsapFrameworkServiceLocator.getContext().getContextInfo());
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("ACAL lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("ACAL lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("ACAL lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("ACAL lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("ACAL lookup failure", e);
		}
		for (AcademicCalendarInfo acal : acals) {
			String yearId = acal.getId();
			// String yearName = acal.getName();
			AcademicYearInfo aYear = null;
			Calendar yearStart = Calendar.getInstance();
			yearStart.setTime(acal.getStartDate());
			int startYear = yearStart.get(Calendar.YEAR);
			Calendar yearEnd = Calendar.getInstance();
			yearEnd.setTime(acal.getEndDate());
			int endYear = yearEnd.get(Calendar.YEAR);
			String displayYear = startYear == endYear ? String
					.valueOf(startYear) : startYear + "-" + endYear;

			if (aYears == null) {
				aYears = new ArrayList<AcademicYearInfo>();
				aYear = new AcademicYearInfo(yearId, displayYear);
				aYear.addOrUpdateTerm(pTerm);
				aYears.add(aYear);
			} else {
				aYear = findAcademicYear(yearId, aYears);
				if (aYear == null) {
					aYear = new AcademicYearInfo(yearId, displayYear);
					aYear.addOrUpdateTerm(pTerm);
					aYears.add(aYear);
				} else {
					aYear.addOrUpdateTerm(pTerm);
				}
			}
		}
		return true;
	}

	/**
	 * Look through the list of AcademicYearInfo objects and return the one with
	 * the matching id.
	 * 
	 * @param aYearId
	 * @param aYears
	 * @return
	 */
	private AcademicYearInfo findAcademicYear(String aYearId,
			List<AcademicYearInfo> aYears) {
		for (AcademicYearInfo aYear : aYears) {
			if (aYearId.equals(aYear.getId()))
				return aYear;
		}
		return null;
	}

	/**
	 * Initialize the advisorMap with the user's current advisors
	 * 
	 * @return
	 */
	private Map<String, ConversationAdvisorInfo> initAdvisorData() {
		Map<String, ConversationAdvisorInfo> advisorMap = new HashMap<String, ConversationAdvisorInfo>();

		for (ConversationAdvisor advisor : getLearningPlanReviewStrategy()
				.getAdvisors()) {
			advisorMap.put(advisor.getUniqueId(),
					(ConversationAdvisorInfo) advisor);
		}

		return advisorMap;
	}

	private AcademicPlanService getAcademicPlanService() {
		if (academicPlanService == null) {
			academicPlanService = (AcademicPlanService) GlobalResourceLoader
					.getService(new QName(PlanConstants.NAMESPACE,
							PlanConstants.SERVICE_NAME));
		}
		return academicPlanService;
	}

	private synchronized CourseDetailsInquiryHelperImpl getCourseDetailsInquiryService() {
		// TODO does this need to be synchronized? It was where I copied it from
		// but not sure if it should be here or not.
		if (this.courseDetailsInquiryService == null) {
			this.courseDetailsInquiryService = new CourseDetailsInquiryHelperImpl();
		}
		return courseDetailsInquiryService;
	}

	private TermHelper getTermHelper() {
		if (termHelper == null) {
			termHelper = KsapFrameworkServiceLocator.getTermHelper();
		}
		return termHelper;
	}

	private AcademicCalendarService getAcademicCalendarService() {
		if (academicCalendarService == null) {
			academicCalendarService = KsapFrameworkServiceLocator
					.getAcademicCalendarService();
		}
		return academicCalendarService;
	}
}
