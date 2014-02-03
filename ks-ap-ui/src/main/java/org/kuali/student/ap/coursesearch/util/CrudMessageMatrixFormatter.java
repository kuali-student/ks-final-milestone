package org.kuali.student.ap.coursesearch.util;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.kuali.rice.core.api.config.property.ConfigContext;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.framework.context.YearTerm;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingInfo;
import org.kuali.student.enrollment.courseoffering.service.CourseOfferingService;
import org.kuali.student.ap.coursesearch.dataobject.CourseDetails;
import org.kuali.student.myplan.plan.dataobject.AcademicRecordDataObject;
import org.kuali.student.myplan.plan.dataobject.PlanItemDataObject;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.util.date.DateFormatters;

/**
 * Created by IntelliJ IDEA. User: hemanthg Date: 5/3/12 Time: 10:46 AM To
 * change this template use File | Settings | File Templates.
 */
public class CrudMessageMatrixFormatter extends PropertyEditorSupport {
	private final static Logger logger = Logger
			.getLogger(CrudMessageMatrixFormatter.class);

	private transient CourseOfferingService courseOfferingService;

    //We may be able to split this out so we have the URL separate from the markup of the anchor tag
    private final String plannerScreenUrl = "<a href=\"planner?methodToCall=start&viewId=Planner-FormView&focusAtpId=";

	protected CourseOfferingService getCourseOfferingService() {
		if (this.courseOfferingService == null) {
			// TODO: Use constants for namespace.
			this.courseOfferingService = KsapFrameworkServiceLocator
					.getCourseOfferingService();
		}
		return this.courseOfferingService;
	}

	public void setCourseOfferingService(
			CourseOfferingService courseOfferingService) {
		this.courseOfferingService = courseOfferingService;
	}

	@Override
	public void setValue(Object value) {
		super.setValue(value);
	}

	@Override
	public String getAsText() {

		CourseDetails courseDetails = (CourseDetails) super.getValue();
		StringBuilder sb = new StringBuilder();
		boolean currentTermRegistered = false;

		/*
		 * When academic terms are not null then populating message
		 * "You took this course on Winter 2012" or
		 * "This course was withdrawn on week 6 in Spring 2012" or
		 * "You're enrolled in this course for Autumn 2012"
		 */
		if (courseDetails.getPlannedCourseSummary().getAcademicTerms().size() > 0) {
			List<String> withDrawnCourseTerms = new ArrayList<String>();
			List<String> nonWithDrawnCourseTerms = new ArrayList<String>();

			for (String term : courseDetails.getPlannedCourseSummary()
					.getAcademicTerms()) {
				for (AcademicRecordDataObject academicRecordDataObject : courseDetails
						.getPlannedCourseSummary().getAcadRecList()) {
					if (term.equals(academicRecordDataObject.getAtpId())
							&& academicRecordDataObject.getGrade().contains(
									PlanConstants.WITHDRAWN_GRADE)) {
						if (!withDrawnCourseTerms.contains(term)) {
							withDrawnCourseTerms.add(term);
						}
					}
					if (term.equals(academicRecordDataObject.getAtpId())
							&& !academicRecordDataObject.getGrade().contains(
									PlanConstants.WITHDRAWN_GRADE)) {
						if (!nonWithDrawnCourseTerms.contains(term)) {
							nonWithDrawnCourseTerms.add(term);
						}
					}
				}
			}
			int counter = 0;
			for (String withdrawnTerm : withDrawnCourseTerms) {
				Term term = KsapFrameworkServiceLocator.getTermHelper()
						.getTerm(withdrawnTerm);
				if (counter == 0) {
					if (KsapFrameworkServiceLocator.getUserSessionHelper()
							.isAdviser()) {
						String user = KsapFrameworkServiceLocator
								.getUserSessionHelper().getStudentName();
                        /*
                         * @TODO: Convert this tag manipulation handling
                         * This is simply creating an orphan DD element, with no containing DL
                         * Moreover the ends up getting rendered inside of a SPAN element;
                         * It is bad form, and will not validate, when you put block-level elements, such as
                         * A UL/OL/DL, inside of an inline element like a span.
                         *
                         * Additional recommendations:
                         *
                         * If we are to use a list, would be to use a UL since we are only dealing with a list of items.
                         * DL is for a list of key/value pairs, which is not what we have in this case.
                         *
                         * Instead of the string appending, a formatted string would be much easier to manage.
                         * For an example, look at ScheduledTermsPropertyEditor.java
                         *  */
						sb = sb.append("<dd>")
								.append(user + " withdrew from this course in ")
								.append(plannerScreenUrl)
								.append(withdrawnTerm).append(">")
								.append(term.getName()).append("</a>");
					} else {
						sb = sb.append("<dd>")
								.append("You withdrew from this course in ")
								.append(plannerScreenUrl)
								.append(withdrawnTerm).append("\">")
								.append(term.getName()).append("</a>");
					}
				}
				if (counter > 0) {
					sb = sb.append(",")
							.append(plannerScreenUrl)
							.append(withdrawnTerm).append("\">")
							.append(term.getName()).append("</a>");
				}
				counter++;
			}

			int counter2 = 0;
			int counter3 = 0;
            /*
            * @TODO: look at the efficiency of how we are looping here
             *
             * As it stands, we potentially have an O(n^2) going on here
            * */
			for (String nonWithdrawnTerm : nonWithDrawnCourseTerms) {
				Term term = KsapFrameworkServiceLocator.getTermHelper()
						.getTerm(nonWithdrawnTerm);
				List<String> sections = getSections(courseDetails,
						nonWithdrawnTerm);
				if (!KsapFrameworkServiceLocator.getTermHelper().isCompleted(term.getId())) {
					if (counter2 == 0) {
						String message = "You are enrolled in ";
						if (KsapFrameworkServiceLocator.getUserSessionHelper()
								.isAdviser()) {
							String user = KsapFrameworkServiceLocator
									.getUserSessionHelper().getStudentName();
							message = user
									+ ". currently enrolled in this course for ";
						}
						StringBuilder sec = new StringBuilder();
						int count = 0;
						for (String section : sections) {
							if (count == 0) {
								sec = sec.append(section);
								count++;
							} else {
								sec = sec.append(" and ").append(section);
								count++;
							}
						}
						sb = sb.append("<dd>")
								.append(message)
								.append(sec)
								.append(" for ")
								.append(plannerScreenUrl)
								.append(nonWithdrawnTerm).append("\">")
								.append(term.getName()).append("</a>");
						currentTermRegistered = true;
					}
					if (counter2 > 0) {
						sb = sb.append(",")
								.append(plannerScreenUrl)
								.append(nonWithdrawnTerm).append("\">")
								.append(term.getName()).append("</a>");
						currentTermRegistered = true;
					}
					counter2++;
				} else {
					if (counter3 == 0) {
						String message = "You took this course in ";
						if (KsapFrameworkServiceLocator.getUserSessionHelper()
								.isAdviser()) {
							String user = KsapFrameworkServiceLocator
									.getUserSessionHelper().getStudentName();
							message = user + " took this course in ";
						}
						sb = sb.append("<dd>")
								.append(message)
								.append(plannerScreenUrl)
								.append(nonWithdrawnTerm).append("\">")
								.append(term.getName()).append("</a>");

					}
					if (counter3 > 0) {
						sb = sb.append(", ")
								.append(plannerScreenUrl)
								.append(nonWithdrawnTerm).append("\">")
								.append(term.getName()).append("</a>");
					}
					counter3++;
				}

			}

		}

		/*
		 * When plannedList or backupList are not null then populating message
		 * "Added to Spring 2013 Plan, Spring 2014 Plan on 01/18/2012" or
		 * "Added to Spring 2013 Plan on 01/18/2012 and Spring 2014 Plan on 09/18/2012"
		 */
		if ((courseDetails.getPlannedCourseSummary().getPlannedList() != null && courseDetails
				.getPlannedCourseSummary().getPlannedList().size() > 0)
				|| (courseDetails.getPlannedCourseSummary().getBackupList() != null && courseDetails
						.getPlannedCourseSummary().getBackupList().size() > 0)) {
			List<PlanItemDataObject> planItemDataObjects = new ArrayList<PlanItemDataObject>();
			if (courseDetails.getPlannedCourseSummary().getPlannedList() != null) {
				for (PlanItemDataObject pl : courseDetails
						.getPlannedCourseSummary().getPlannedList()) {
					planItemDataObjects.add(pl);
				}
			}
			if (courseDetails.getPlannedCourseSummary().getBackupList() != null) {
				for (PlanItemDataObject bl : courseDetails
						.getPlannedCourseSummary().getBackupList()) {
					planItemDataObjects.add(bl);
				}
			}
			/* Dividing the plan items on same date and different date */
			Map<String, String> planItemsMap = new HashMap<String, String>();
			if (planItemDataObjects.size() > 0) {

				for (PlanItemDataObject pl : planItemDataObjects) {
					Term term = KsapFrameworkServiceLocator.getTermHelper()
							.getTerm(pl.getAtp());
					YearTerm yearTerm = KsapFrameworkServiceLocator
							.getTermHelper().getYearTerm(term);
					String date = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER
							.format(DateFormatters.DEFAULT_DATE_FORMATTER
									.parse(pl.getDateAdded().toString()
											.substring(0, 10)));
					if (planItemsMap.containsKey(date)) {
						StringBuilder sbuf = new StringBuilder();
						sbuf = sbuf.append(planItemsMap.get(date)).append(",")
								.append(KsapFrameworkServiceLocator.getTermHelper().getTerm(yearTerm).getId());
						planItemsMap.put(date, sbuf.toString());
					} else {
						planItemsMap.put(date, KsapFrameworkServiceLocator.getTermHelper().getTerm(yearTerm).getId());
					}
				}

			}
			int count = 0;
			StringBuilder startsSub = new StringBuilder();
			if (sb.toString().length() > 0) {
				startsSub = startsSub.append(sb);
			}
			if (!currentTermRegistered) {
				startsSub = startsSub.append("<dd>").append("Added to ");
			} else {
				startsSub = startsSub.append("<dd>").append(
						"This course was also added to ");
			}

			for (Map.Entry<String, String> entry : planItemsMap.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
				if (count == 0) {
					if (value.contains(",")) {
						String[] terms = value.split(",");
						for (String t : terms) {
							Term term = KsapFrameworkServiceLocator
									.getTermHelper().getTerm(t);
							sb = startsSub
									.append(plannerScreenUrl)
									.append(t).append("\">")
									.append(term.getName()).append(" plan")
									.append("</a>").append(", ");
						}
						String formattedString = sb.substring(0,
								sb.lastIndexOf(","));
						StringBuilder formattedSubBuf = new StringBuilder();
						formattedSubBuf = formattedSubBuf
								.append(formattedString);
						sb = formattedSubBuf.append(" on ").append(key);
					} else {
						Term term = KsapFrameworkServiceLocator.getTermHelper()
								.getTerm(value);
						if (!currentTermRegistered) {
							sb = sb.append("<dd>")
									.append("Added to ")
									.append(plannerScreenUrl)
									.append(term.getId()).append("\">")
									.append(term.getName()).append(" plan")
									.append("</a> ").append(" on ").append(key)
									.append(" ");
						} else {
							sb = sb.append("<dd>")
									.append("This course was also added to ")
									.append(plannerScreenUrl)
									.append(term.getId()).append("\">")
									.append(term.getName()).append(" plan")
									.append("</a> ").append(" on ").append(key)
									.append(" ");
						}
					}

				}
				if (count > 0) {
					if (value.contains(",")) {
						String[] terms = value.split(",");
						for (String t : terms) {
							Term term = KsapFrameworkServiceLocator
									.getTermHelper().getTerm(t);
							sb = sb.append(plannerScreenUrl)
									.append(t).append("\">")
									.append(term.getName()).append(" plan")
									.append("</a> ").append(",");
						}
						String formattedString = sb.substring(0,
								sb.lastIndexOf(",") - 1);
						StringBuilder formattedSubBuf = new StringBuilder();
						formattedSubBuf = formattedSubBuf
								.append(formattedString);
						sb = formattedSubBuf.append(" on ").append(key);
					} else {
						Term term = KsapFrameworkServiceLocator.getTermHelper()
								.getTerm(value);
						sb = sb.append(" and ")
								.append(plannerScreenUrl)
								.append(term.getId()).append("\">")
								.append(term.getName()).append(" plan")
								.append("</a> ").append(" on ").append(key);
					}

				}
				count++;

			}
		}
		/*
		 * When savedItemId and savedItemDateCreated are not null then
		 * populating message"Bookmarked on 8/15/2012"
		 */
		if (courseDetails.getPlannedCourseSummary().getSavedItemId() != null
				&& courseDetails.getPlannedCourseSummary()
						.getSavedItemDateCreated() != null) {
			sb = sb.append("<dd>")
					.append("<a href=lookup?methodToCall=search&viewId=SavedCoursesDetail-LookupView>")
					.append("Bookmarked")
					.append("</a>")
					.append(" on ")
					.append(courseDetails.getPlannedCourseSummary()
							.getSavedItemDateCreated());

		}

		return sb.toString();
	}

	/**
	 * returns the section links string for given courseId and term in the form
	 * ("<a href="http://localhost:8080/kr-krad/inquiry?methodToCall=
	 * start&viewId=CourseDetails-InquiryView&courseId=60325fa8-7307-454a-be73-3
	 * cc1c642122d#kuali-uw-atp-2013-1-19889"> Section (SLN) </a>")
	 * 
	 * @param courseDetails
	 * @param term
	 * @return
	 */
	private List<String> getSections(CourseDetails courseDetails, String term) {
		String[] yearAndTerm = term.split(" ");

		List<String> sections = new ArrayList<String>();
		List<String> sectionAndSln = new ArrayList<String>();
		for (AcademicRecordDataObject acr : courseDetails
				.getPlannedCourseSummary().getAcadRecList()) {
			if (acr.getAtpId().equals(term)) {
				sections.add(acr.getActivityCode());
			}
		}
		for (String section : sections) {
			String sln = getSLN(yearAndTerm[1].trim(), yearAndTerm[0].trim(),
					courseDetails.getCourseSummaryDetails().getSubjectArea(),
					courseDetails.getCourseSummaryDetails().getCourseNumber(),
					section);
			String sectionSln = String.format("Section %s (%s)", section, sln);
			String sec = String
					.format("<a href=\"%s\">%s</a>",
							ConfigContext.getCurrentContextConfig()
									.getProperty("appserver.url")
									+ "/kr-krad/inquiry?methodToCall=start&viewId=CourseDetails-InquiryView&courseId="
									+ courseDetails.getCourseSummaryDetails()
											.getCourseId()
									+ "#"
									+ term.replace(".", "-") + "-" + sln,
							sectionSln);
			sectionAndSln.add(sec);
		}
		return sectionAndSln;
	}

	/**
	 * returns the SLN for the given params
	 * 
	 * @param year
	 * @param term
	 * @param curriculum
	 * @param number
	 * @param section
	 * @return
	 */
	private String getSLN(String year, String term, String curriculum,
			String number, String section) {
		String sln = null;
		ActivityOfferingInfo activityOfferingInfo = new ActivityOfferingInfo();
		try {
			activityOfferingInfo = getCourseOfferingService()
					.getActivityOffering(
							year + "," + term + "," + curriculum + "," + number
									+ "," + section,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
		} catch (Exception e) {
			logger.error("could not load the ActivityOfferinInfo from SWS", e);
		}
		for (AttributeInfo attributeInfo : activityOfferingInfo.getAttributes()) {
			if (attributeInfo.getKey().equalsIgnoreCase("SLN")) {
				sln = attributeInfo.getValue();
				break;
			}
		}
		return sln;
	}
}
