package org.kuali.student.ap.schedulebuilder.support;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.log4j.Logger;
import org.kuali.student.ap.academicplan.dto.LearningPlanInfo;
import org.kuali.student.ap.academicplan.dto.PlanItemInfo;
import org.kuali.student.ap.academicplan.infc.LearningPlan;
import org.kuali.student.ap.academicplan.service.AcademicPlanService;
import org.kuali.student.ap.academicplan.service.AcademicPlanServiceConstants;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.framework.context.CourseHelper;
import org.kuali.student.ap.framework.context.PlanConstants;
import org.kuali.student.ap.schedulebuilder.ScheduleBuildForm;
import org.kuali.student.ap.schedulebuilder.ScheduleBuildStrategy;
import org.kuali.student.ap.schedulebuilder.ShoppingCartForm;
import org.kuali.student.ap.schedulebuilder.ShoppingCartStrategy;
import org.kuali.student.ap.schedulebuilder.dto.ActivityOptionInfo;
import org.kuali.student.ap.schedulebuilder.dto.ClassMeetingTimeInfo;
import org.kuali.student.ap.schedulebuilder.dto.CourseOptionInfo;
import org.kuali.student.ap.schedulebuilder.dto.PossibleScheduleOptionInfo;
import org.kuali.student.ap.schedulebuilder.dto.ReservedTimeInfo;
import org.kuali.student.ap.schedulebuilder.dto.SecondaryActivityOptionsInfo;
import org.kuali.student.ap.schedulebuilder.infc.ActivityOption;
import org.kuali.student.ap.schedulebuilder.infc.ClassMeetingTime;
import org.kuali.student.ap.schedulebuilder.infc.CourseOption;
import org.kuali.student.ap.schedulebuilder.infc.PossibleScheduleOption;
import org.kuali.student.ap.schedulebuilder.infc.ReservedTime;
import org.kuali.student.ap.schedulebuilder.infc.SecondaryActivityOptions;
import org.kuali.student.common.collection.KSCollectionUtils;
import org.kuali.student.enrollment.academicrecord.dto.StudentCourseRecordInfo;
import org.kuali.student.enrollment.courseoffering.dto.ActivityOfferingDisplayInfo;
import org.kuali.student.r2.common.dto.AttributeInfo;
import org.kuali.student.r2.common.dto.ContextInfo;
import org.kuali.student.r2.common.dto.TimeOfDayInfo;
import org.kuali.student.r2.common.exceptions.DataValidationErrorException;
import org.kuali.student.r2.common.exceptions.DoesNotExistException;
import org.kuali.student.r2.common.exceptions.InvalidParameterException;
import org.kuali.student.r2.common.exceptions.MissingParameterException;
import org.kuali.student.r2.common.exceptions.OperationFailedException;
import org.kuali.student.r2.common.exceptions.PermissionDeniedException;
import org.kuali.student.r2.common.infc.Attribute;
import org.kuali.student.r2.common.util.constants.LuiServiceConstants;
import org.kuali.student.r2.core.acal.infc.Term;
import org.kuali.student.r2.core.room.infc.Room;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;
import org.kuali.student.r2.core.scheduling.dto.ScheduleDisplayInfo;
import org.kuali.student.r2.core.scheduling.infc.ScheduleComponentDisplay;
import org.kuali.student.r2.core.scheduling.infc.TimeSlot;
import org.kuali.student.r2.lum.course.infc.Course;

public class DefaultScheduleBuildStrategy implements ScheduleBuildStrategy,
		Serializable {

	private static final long serialVersionUID = -3524818039744728212L;

	private static final String SCHEDULE_BUILD_ATTR = ScheduleBuildStrategy.class
			.getName() + ".scheduleBuild";

	private static final Logger LOG = Logger
			.getLogger(DefaultScheduleBuildStrategy.class);

	/**
	 * Simple XML wrapper for storing a list of reserved times as a dynamic
	 * attribute on a learning plan.
	 */
	@XmlRootElement(name = "schedule-build", namespace = "http://sb.ap.student.kuali.org/")
	@XmlAccessorType(XmlAccessType.FIELD)
	public static class ScheduleBuildAttribute {
		private int savedScheduleSequence;
		private List<ReservedTimeInfo> reservedTime;
		private List<PossibleScheduleOptionInfo> schedule;
	}

	/**
	 * Retrieve schedule build saved information as a dynamic attribute on the
	 * requested learning plan.
	 * 
	 * @param requestedLearningPlanId
	 *            The requested learning plan ID.
	 * @return The schedule build saved info, stored as a dynamic attribute on
	 *         the requested learning plan.
	 * @throws PermissionDeniedException
	 *             If the current user does not have access to the requested
	 *             learning plan.
	 * @see #getLearningPlan(String)
	 */
	private ScheduleBuildAttribute getScheduleBuildAttribute(
			String requestedLearningPlanId) throws PermissionDeniedException {
		LearningPlanInfo learningPlanInfo = (LearningPlanInfo) getLearningPlan(requestedLearningPlanId);
		List<AttributeInfo> attributes = learningPlanInfo.getAttributes();
		Iterator<AttributeInfo> attributeIterator = attributes.iterator();
		AttributeInfo scheduleBuildAttribute = null;
		while (attributeIterator.hasNext() && scheduleBuildAttribute == null) {
			AttributeInfo attribute = attributeIterator.next();
			if (attribute.getKey().equals(SCHEDULE_BUILD_ATTR)) {
				scheduleBuildAttribute = attribute;
			}
		}
		ScheduleBuildAttribute rv;
		if (scheduleBuildAttribute != null) {
			@SuppressWarnings("deprecation")
			ScheduleBuildAttribute schduleBuildInfo = org.kuali.student.ap.framework.util.XmlMarshalUtil
					.unmarshal(scheduleBuildAttribute.getValue(),
							ScheduleBuildAttribute.class,
							ReservedTimeInfo.class,
							PossibleScheduleOptionInfo.class);
			rv = schduleBuildInfo;
		} else {
			rv = new ScheduleBuildAttribute();
		}

		if (rv.reservedTime == null) {
			rv.reservedTime = new ArrayList<ReservedTimeInfo>(0);
		}

		if (rv.schedule == null) {
			rv.schedule = new ArrayList<PossibleScheduleOptionInfo>(0);
		}
		return rv;
	}

	/**
	 * Update or create a dynamic attribute on the requested learning plan to
	 * represent stored information related to schedule build.
	 *
	 * @param requestedLearningPlanId
	 *            The requested learning plan ID.
	 * @return The saved data, to be stored as a dynamic attribute on the
	 *         requested learning plan.
	 * @throws PermissionDeniedException
	 *             If the current user does not have access to the requested
	 *             learning plan.
	 * @see #getLearningPlan(String)
	 */
	private void updateScheduleBuildAttribute(String requestedLearningPlanId,
			ScheduleBuildAttribute scheduleBuildInfo)
			throws PermissionDeniedException {
		LearningPlanInfo learningPlanInfo = (LearningPlanInfo) getLearningPlan(requestedLearningPlanId);

		@SuppressWarnings("deprecation")
		AttributeInfo newScheduleBuildAttribute = new AttributeInfo(
				SCHEDULE_BUILD_ATTR,
				org.kuali.student.ap.framework.util.XmlMarshalUtil.marshal(
						scheduleBuildInfo, ReservedTimeInfo.class,
						PossibleScheduleOptionInfo.class));

		List<AttributeInfo> attributes = new ArrayList<AttributeInfo>(
				learningPlanInfo.getAttributes());
		ListIterator<AttributeInfo> attributeListIterator = attributes
				.listIterator();

		boolean found = false;
		while (!found && attributeListIterator.hasNext()) {
			if (attributeListIterator.next().getKey()
					.equals(SCHEDULE_BUILD_ATTR)) {
				attributeListIterator.set(newScheduleBuildAttribute);
				found = true;
			}
		}

		if (!found) {
			attributes.add(newScheduleBuildAttribute);
		}

		learningPlanInfo.setAttributes(attributes);
		try {
			KsapFrameworkServiceLocator.getAcademicPlanService()
					.updateLearningPlan(
							learningPlanInfo.getId(),
							learningPlanInfo,
							KsapFrameworkServiceLocator.getContext()
									.getContextInfo());
		} catch (DataValidationErrorException e) {
			throw new IllegalArgumentException(
					"Error saving reserved time attributes in learning plan", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException(
					"Error saving reserved time attributes in learning plan", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException(
					"Error saving reserved time attributes in learning plan", e);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException(
					"Error saving reserved time attributes in learning plan", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException(
					"Error saving reserved time attributes in learning plan", e);
		}
	}

	@Override
	public ScheduleBuildForm getInitialForm() {
		return new DefaultScheduleBuildForm();
	}

	private ClassMeetingTime adaptClassMeeting(TimeSlot timeSlot,
			ScheduleComponentDisplay scdi, Calendar tcal, Calendar sdcal,
			Calendar edcal, DateFormat tdf, DateFormat ddf, String instructor,
			Date sessionStartDate, Date sessionEndDate) {
		ClassMeetingTimeInfo meeting = new ClassMeetingTimeInfo();
		sdcal.setTime(sessionStartDate);
		edcal.setTime(sessionEndDate);
		meeting.setAllDay(true);

		String location = null;
		Room roomInfo = scdi.getRoom();
		if (roomInfo != null) {
			location = roomInfo.getDescr().getPlain();
		}

		StringBuilder daysAndTimes = new StringBuilder();
		Set<Integer> weekdays = new java.util.TreeSet<Integer>();
		if (timeSlot.getWeekdays() != null)
			weekdays.addAll(timeSlot.getWeekdays());

		for (int weekday : weekdays)
			switch (weekday) {
			case Calendar.MONDAY:
				daysAndTimes
						.append(SchedulingServiceConstants.MONDAY_TIMESLOT_DISPLAY_DAY_CODE);
				meeting.setMonday(true);
				break;
			case Calendar.TUESDAY:
				daysAndTimes
						.append(SchedulingServiceConstants.TUESDAY_TIMESLOT_DISPLAY_DAY_CODE);
				meeting.setTuesday(true);
				break;
			case Calendar.WEDNESDAY:
				daysAndTimes
						.append(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DISPLAY_DAY_CODE);
				meeting.setWednesday(true);
				break;
			case Calendar.THURSDAY:
				daysAndTimes
						.append(SchedulingServiceConstants.THURSDAY_TIMESLOT_DISPLAY_DAY_CODE);
				meeting.setThursday(true);
				break;
			case Calendar.FRIDAY:
				daysAndTimes
						.append(SchedulingServiceConstants.FRIDAY_TIMESLOT_DISPLAY_DAY_CODE);
				meeting.setFriday(true);
				break;
			case Calendar.SATURDAY:
				daysAndTimes
						.append(SchedulingServiceConstants.SATURDAY_TIMESLOT_DISPLAY_DAY_CODE);
				meeting.setSaturday(true);
				break;
			case Calendar.SUNDAY:
				daysAndTimes
						.append(SchedulingServiceConstants.SUNDAY_TIMESLOT_DISPLAY_DAY_CODE);
				meeting.setSunday(true);
				break;
			default:
				throw new IllegalArgumentException("Unexpected day code "
						+ weekday);
			}

		TimeOfDayInfo startInfo = timeSlot.getStartTime();
		TimeOfDayInfo endInfo = timeSlot.getEndTime();

		if (startInfo != null && endInfo != null) {
			meeting.setAllDay(false);
			if (daysAndTimes.length() > 0)
				daysAndTimes.append(" ");
			Date startTime = new Date(startInfo.getMilliSeconds());
			Date endTime = new Date(endInfo.getMilliSeconds());
			daysAndTimes.append(tdf.format(startTime));
			daysAndTimes.append(" - ");
			daysAndTimes.append(tdf.format(endTime));
			tcal.setTime(startTime);
			sdcal.set(Calendar.HOUR_OF_DAY, tcal.get(Calendar.HOUR_OF_DAY));
			sdcal.set(Calendar.MINUTE, tcal.get(Calendar.MINUTE));
			sdcal.set(Calendar.SECOND, tcal.get(Calendar.SECOND));
			sdcal.set(Calendar.MILLISECOND, tcal.get(Calendar.MILLISECOND));
			meeting.setStartDate(sdcal.getTime());
			tcal.setTime(endTime);
			edcal.set(Calendar.HOUR_OF_DAY, tcal.get(Calendar.HOUR_OF_DAY));
			edcal.set(Calendar.MINUTE, tcal.get(Calendar.MINUTE));
			edcal.set(Calendar.SECOND, tcal.get(Calendar.SECOND));
			edcal.set(Calendar.MILLISECOND, tcal.get(Calendar.MILLISECOND));
			meeting.setUntilDate(edcal.getTime());
		} else {
			edcal.set(Calendar.HOUR_OF_DAY, 0);
			edcal.set(Calendar.MINUTE, 0);
			edcal.set(Calendar.SECOND, 0);
			edcal.set(Calendar.MILLISECOND, 0);
		}
		meeting.setLocation(location);
		meeting.setArranged(location != null);
		meeting.setDaysAndTimes(daysAndTimes.toString());
		meeting.setInstructorName(instructor);
		meeting.setStartDate(sdcal.getTime());
		meeting.setUntilDate(edcal.getTime());
		meeting.setDescription(ddf.format(meeting.getStartDate()) + " - "
				+ ddf.format(meeting.getUntilDate()));
		return meeting;
	}

	private ActivityOptionInfo getActivityOption(Term term, ActivityOfferingDisplayInfo aodi,
			int courseIndex, String courseId, String campusCode, StringBuilder msg,
			DateFormat tdf, DateFormat udf, DateFormat ddf, Calendar sdcal, Calendar edcal,
			Calendar tcal) {

		ActivityOptionInfo activityOption = new ActivityOptionInfo();
		activityOption.setCourseIndex(courseIndex);
		activityOption.setUniqueId(UUID.randomUUID().toString());
		activityOption.setCourseId(courseId);
		activityOption.setActivityOfferingId(aodi.getId());
		activityOption.setActivityTypeDescription(aodi.getTypeName());
		activityOption.setCourseOfferingCode((campusCode == null ? ""
				: campusCode + " ") + aodi.getCourseOfferingCode());
		activityOption.setActivityName(aodi.getName());
		activityOption.setRegistrationCode(aodi
				.getActivityOfferingCode());
		activityOption
				.setClosed(!LuiServiceConstants.LUI_AO_STATE_OFFERED_KEY
						.equals(aodi.getStateKey()));
		activityOption.setTotalSeats(aodi.getMaximumEnrollment());
		if (msg != null)
			msg.append("\nActivity ")
					.append(activityOption.getUniqueId()).append(": ")
					.append(activityOption.getCourseOfferingCode())
					.append(" ")
					.append(activityOption.getRegistrationCode());

		boolean enrollmentGroup = false;
		String instructor = aodi.getInstructorName();
		String primaryOfferingId = null;

		String sessionDescr = term.getName();
		Date sessionStartDate = term.getStartDate();
		Date sessionEndDate = term.getEndDate();
		BigDecimal minCredits = BigDecimal.ZERO;
		BigDecimal maxCredits = BigDecimal.ZERO;
		for (AttributeInfo attrib : aodi.getAttributes()) {
			String key = attrib.getKey();
			String value = attrib.getValue();
			if ("PrimaryActivityOfferingId".equalsIgnoreCase(key)) {
				primaryOfferingId = value;
				activityOption.setPrimary(aodi.getId().equals(
						primaryOfferingId));
			}
			if ("PermissionRequired".equalsIgnoreCase(key)) {
				activityOption.setRequiresPermission("true"
						.equals(value));
			}
			if ("BlockEnrollment".equalsIgnoreCase(key)) {
				enrollmentGroup = "true".equals(value);
			}
			if ("Closed".equalsIgnoreCase(key)) {
				activityOption.setClosed("true".equals(value));
			}
			if ("enrollOpen".equalsIgnoreCase(key)) {
				activityOption.setOpenSeats(Integer.parseInt(value));
			}
			if ("SessionDescr".equalsIgnoreCase(key)) {
				sessionDescr = value;
			}
			if ("SessionStartDate".equalsIgnoreCase(key)) {
				try {
					sessionStartDate = udf.parse(value);
				} catch (ParseException e) {
					throw new IllegalArgumentException(
							"Invalid session start date "
									+ sessionStartDate);
				}
			}
			if ("SessionEndDate".equalsIgnoreCase(key)) {
				try {
					sessionEndDate = udf.parse(value);
				} catch (ParseException e) {
					throw new IllegalArgumentException(
							"Invalid session start date "
									+ sessionEndDate);
				}
			}

			if ("CourseCode".equalsIgnoreCase(key)) {
				activityOption.setCourseOfferingCode((campusCode == null ? ""
						: campusCode + " ") + value);
			}

			// TODO: Add getResultValuesGroup() to
			// ActivityOfferingDisplayInfo and use it instead.
			if ("minUnits".equalsIgnoreCase(key)) {
				minCredits = new BigDecimal(value);
			}
			if ("maxUnits".equalsIgnoreCase(key)) {
				maxCredits = new BigDecimal(value);
			}

		}
		sessionDescr += " " + ddf.format(sessionStartDate) + " - "
				+ ddf.format(sessionEndDate);
		activityOption.setAcademicSessionDescr(sessionDescr);
		activityOption.setMinCredits(minCredits);
		activityOption.setMaxCredits(maxCredits);

		List<ClassMeetingTime> meetingTimes = new LinkedList<ClassMeetingTime>();
		ScheduleDisplayInfo sdi = aodi.getScheduleDisplay();
		for (ScheduleComponentDisplay scdi : sdi
				.getScheduleComponentDisplays())
			for (TimeSlot timeSlot : scdi.getTimeSlots())
				meetingTimes.add(adaptClassMeeting(timeSlot, scdi,
						tcal, sdcal, edcal, tdf, ddf, instructor,
						sessionStartDate, sessionEndDate));
		activityOption.setClassMeetingTimes(meetingTimes);
		activityOption.setEnrollmentGroup(enrollmentGroup);

		return activityOption;
	}

	@Override
	public ActivityOption getActivityOption(String termId, String courseId, String regCode) {
		if (regCode == null)
			return null;

		CourseHelper courseHelper = KsapFrameworkServiceLocator.getCourseHelper();
		Course course = courseHelper.getCourseInfo(courseId);
		if (course == null)
			return null;

		String campusCode = null;
		for (Attribute ca : course.getAttributes())
			if ("campusCode".equals(ca.getKey()))
				campusCode = ca.getValue();

		Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);
		if (term == null)
			return null;

		for (ActivityOfferingDisplayInfo aodi : courseHelper
				.getActivityOfferingDisplaysByCourseAndTerm(courseId,
						termId))
			if (regCode.equals(aodi.getActivityOfferingCode())) {
				DateFormat tdf = new SimpleDateFormat("h:mm a");
				DateFormat udf = new SimpleDateFormat("MM/dd/yyyy");
				DateFormat ddf = new SimpleDateFormat("M/d");
				Calendar sdcal = Calendar.getInstance();
				Calendar edcal = Calendar.getInstance();
				Calendar tcal = Calendar.getInstance();
				return getActivityOption(term, aodi, 0, courseId, campusCode, null, tdf, udf, ddf,
						sdcal, edcal, tcal);
			}

		return null;
	}

	@Override
	public List<CourseOption> getCourseOptions(List<String> courseIds,
			String termId) {
		Term term = KsapFrameworkServiceLocator.getTermHelper().getTerm(termId);
		CourseHelper courseHelper = KsapFrameworkServiceLocator
				.getCourseHelper();
		ShoppingCartStrategy cartStrategy = KsapFrameworkServiceLocator.getShoppingCartStrategy();
		DateFormat tdf = new SimpleDateFormat("h:mm a");
		DateFormat udf = new SimpleDateFormat("MM/dd/yyyy");
		DateFormat ddf = new SimpleDateFormat("M/d");
		Calendar sdcal = Calendar.getInstance();
		Calendar edcal = Calendar.getInstance();
		Calendar tcal = Calendar.getInstance();
		List<CourseOption> rv = new LinkedList<CourseOption>();
		courseHelper.frontLoad(courseIds, termId);
		int courseIndex = -1;
		for (String courseId : courseIds) {
			courseIndex++;
			Course c = courseHelper.getCourseInfo(courseId);
			if (c == null)
				continue;
			CourseOptionInfo courseOption = new CourseOptionInfo();
			courseOption.setUniqueId(UUID.randomUUID().toString());
			courseOption.setCourseId(c.getId());

			StringBuilder code = new StringBuilder();
			String campusCode = null;
			for (Attribute ca : c.getAttributes())
				if ("campusCode".equals(ca.getKey()))
					code.append(campusCode = ca.getValue()).append(" ");
			if (!cartStrategy.isCartAvailable(termId, campusCode))
				continue;

			code.append(c.getCode());
			courseOption.setCourseCode(code.toString());

			courseOption.setCourseTitle(c.getCourseTitle());
			List<ActivityOption> primaryActivities = new LinkedList<ActivityOption>();
			Map<String, Map<String, List<ActivityOptionInfo>>> secondaryActivities = new LinkedHashMap<String, Map<String, List<ActivityOptionInfo>>>();

			StringBuilder msg = null;
			if (LOG.isDebugEnabled())
				msg = new StringBuilder();
			for (ActivityOfferingDisplayInfo aodi : courseHelper
					.getActivityOfferingDisplaysByCourseAndTerm(courseId,
							termId)) {
				ActivityOptionInfo activityOption = getActivityOption(term, aodi, courseIndex,
						courseId, campusCode, msg, tdf, udf, ddf, sdcal, edcal, tcal);

				boolean enrollmentGroup = false;
				String primaryOfferingId = null;
				for (AttributeInfo attrib : aodi.getAttributes()) {
					String key = attrib.getKey();
					String value = attrib.getValue();

					if ("PrimaryActivityOfferingId".equalsIgnoreCase(key))
						primaryOfferingId = value;

					if ("BlockEnrollment".equalsIgnoreCase(key))
						enrollmentGroup = "true".equals(value);
				}

				if (activityOption.isPrimary()) {
					activityOption
							.setParentUniqueId(courseOption.getUniqueId());
					if (msg != null)
						msg.append("\nPrimary ")
								.append(activityOption.getUniqueId())
								.append(": ")
								.append(activityOption.getCourseOfferingCode())
								.append(" ")
								.append(activityOption.getRegistrationCode());
					primaryActivities.add(activityOption);
				} else {
					Map<String, List<ActivityOptionInfo>> secondaryGroup = secondaryActivities
							.get(primaryOfferingId);
					if (msg != null)
						msg.append("\nSecondary ")
								.append(activityOption.getUniqueId())
								.append(": ")
								.append(activityOption.getCourseOfferingCode())
								.append(" ")
								.append(activityOption.getRegistrationCode())
								.append(" -> ").append(primaryOfferingId);
					if (secondaryGroup == null)
						secondaryActivities
								.put(primaryOfferingId,
										secondaryGroup = new LinkedHashMap<String, List<ActivityOptionInfo>>());
					String groupKey = enrollmentGroup ? "kuali.ap.enrollmentGroup"
							: activityOption.getActivityTypeDescription();
					List<ActivityOptionInfo> aol = secondaryGroup.get(groupKey);
					if (aol == null)
						secondaryGroup
								.put(groupKey,
										aol = new LinkedList<ActivityOptionInfo>());
					aol.add(activityOption);
				}
			}
			if (msg != null)
				LOG.debug(msg.toString());

			for (ActivityOption primary : primaryActivities) {
				String parentUniqueId = primary.getUniqueId();
				Map<String, List<ActivityOptionInfo>> secondaryGroup = secondaryActivities
						.get(primary.getActivityOfferingId());
				if (secondaryGroup != null) {
					List<SecondaryActivityOptions> sao = new ArrayList<SecondaryActivityOptions>();
					int i = 0;
					for (Entry<String, List<ActivityOptionInfo>> e : secondaryGroup
							.entrySet()) {
						SecondaryActivityOptionsInfo secondaryOptions = new SecondaryActivityOptionsInfo();
						secondaryOptions.setUniqueId(parentUniqueId);
						secondaryOptions.setIndex(i);
						secondaryOptions.setActivityTypeDescription(e.getKey());
						secondaryOptions
								.setEnrollmentGroup("kuali.ap.enrollmentGroup"
										.equals(e.getKey()));
						List<ActivityOptionInfo> secondaryOptionList = e
								.getValue();
						for (ActivityOptionInfo secondaryOption : secondaryOptionList) {
							secondaryOption.setParentUniqueId(parentUniqueId);
							secondaryOption.setParentIndex(i);
						}
						Collections.sort(secondaryOptionList);
						secondaryOptions
								.setActivityOptions(secondaryOptionList);
						sao.add(secondaryOptions);
						i++;
					}
					((ActivityOptionInfo) primary).setSecondaryOptions(sao);
				}
			}
			Collections.sort(primaryActivities);
			courseOption.setActivityOptions(primaryActivities);

			if (courseOption.getActivityCount(false) == 0) {
				courseOption.setSelected(false);
			}

			rv.add(courseOption);
		}
		Collections.sort(rv);
		return rv;
	}

	private void buildCourseOptions(String termId, boolean courseLockIn, boolean lockIn,
			Map<String, List<String>> courseIdsActivityCodes, List<CourseOption> rv) {
		if (!courseIdsActivityCodes.isEmpty()) {
			StringBuilder msg = null;
			if (LOG.isDebugEnabled()) {
				msg = new StringBuilder("Course options for ");
				msg.append(termId);
				msg.append("\nCourse lock-in ? ");
				msg.append(courseLockIn);
				msg.append("\nActivity lock-in ? ");
				msg.append(lockIn);
				msg.append("\nSelections :");
				msg.append(courseIdsActivityCodes);
			}
			Queue<ActivityOptionInfo> toCourseLockIn = courseLockIn
					? new LinkedList<ActivityOptionInfo>() : null;
			for (CourseOption co : getCourseOptions(new ArrayList<String>(
					courseIdsActivityCodes.keySet()), termId)) {
				List<String> acodes = courseIdsActivityCodes.get(co
						.getCourseId());
				if (msg != null) {
					msg.append("\n  Course ").append(co.getCourseCode());
					msg.append(" ").append(co.getCourseId());
					msg.append(" ").append(acodes);
				}
				boolean found = false;
				for (ActivityOption ao : co.getActivityOptions()) {
					ActivityOptionInfo aoi = (ActivityOptionInfo) ao;

					if (courseLockIn)
						if (found)
							aoi.setCourseLockedIn(true);
						else
							toCourseLockIn.add(aoi);

					boolean foundHere = false;
					if (acodes.isEmpty()
							|| acodes.contains(ao.getRegistrationCode())) {
						aoi.setSelected(true);
						if (lockIn)
							aoi.setLockedIn(true);
						foundHere = found = true;
					}
					if (msg != null) {
						msg.append("\n    Activity ")
								.append(ao.getRegistrationCode()).append(" ")
								.append(ao.getActivityOfferingId());
						if (found)
							msg.append(" found");
						if (foundHere)
							msg.append(" here");
					}

					for (SecondaryActivityOptions so : ao.getSecondaryOptions())
						if (!so.isEnrollmentGroup())
							for (ActivityOption sao : so.getActivityOptions()) {
								ActivityOptionInfo saoi = (ActivityOptionInfo) sao;
								if (courseLockIn)
									if (found)
										saoi.setCourseLockedIn(true);
									else
										toCourseLockIn.add(saoi);

								boolean select = foundHere
										&& (acodes.isEmpty() || acodes
												.contains(sao
														.getRegistrationCode()));
								if (select) {
									saoi.setSelected(true);
									if (lockIn)
										saoi.setLockedIn(true);
								}
								if (msg != null) {
									msg.append("\n      ")
											.append(so
													.getActivityTypeDescription())
											.append(" ")
											.append(sao.getRegistrationCode())
											.append(" ")
											.append(sao.getActivityOfferingId());
									if (select)
										msg.append(" selected");
								}

							}
				}

				CourseOptionInfo coi = (CourseOptionInfo) co;
				coi.setSelected(found);
				if (found && courseLockIn) {
					coi.setLockedIn(found);
					while (!toCourseLockIn.isEmpty())
						toCourseLockIn.poll().setCourseLockedIn(true);
				}

				rv.add(co);
			}

			if (msg != null) {
				LOG.debug(msg);
			}
		}

	}

	@Override
	public List<CourseOption> getCourseOptions(String learningPlanId,
			String termId) {
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper()
				.getStudentId();

		AcademicPlanService academicPlanService = KsapFrameworkServiceLocator
				.getAcademicPlanService();
		ContextInfo context = KsapFrameworkServiceLocator.getContext()
				.getContextInfo();
		List<PlanItemInfo> planItems;
		try {
			planItems = academicPlanService.getPlanItemsInPlan(learningPlanId,
					context);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("CO lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("CO lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("CO lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalArgumentException("CO lookup failure", e);
		}

		List<StudentCourseRecordInfo> completedRecords;
		try {
			completedRecords = KsapFrameworkServiceLocator
					.getAcademicRecordService().getCompletedCourseRecords(
							studentId, context);
		} catch (DoesNotExistException e) {
			throw new IllegalArgumentException("AR lookup failure", e);
		} catch (InvalidParameterException e) {
			throw new IllegalArgumentException("AR lookup failure", e);
		} catch (MissingParameterException e) {
			throw new IllegalArgumentException("AR lookup failure", e);
		} catch (OperationFailedException e) {
			throw new IllegalStateException("AR lookup failure", e);
		} catch (PermissionDeniedException e) {
			throw new IllegalStateException("AR lookup failure", e);
		}

		Map<String, List<String>> registeredCourseIdsAndActivityCodes = new LinkedHashMap<String, List<String>>();
		for (StudentCourseRecordInfo completedRecord : completedRecords) {
			if (!termId.equals(completedRecord.getTermName()))
				continue;

			String acodeattr = completedRecord.getActivityCode();
			List<String> acodes;
			if (acodeattr == null) {
				acodes = Collections.emptyList();
			} else {
				acodes = Arrays.asList(acodeattr.split(","));
			}

			// TODO: switch to attribute, ID is incorrect here
			// courseId = completedRecord.getAttributeValue("courseId");
			registeredCourseIdsAndActivityCodes.put(completedRecord.getId(),
					acodes);
		}

		Map<String, List<String>> cartCourseIdsAndActivityCodes = new LinkedHashMap<String, List<String>>();
		Map<String, List<String>> plannedCourseIdsAndActivityCodes = new LinkedHashMap<String, List<String>>();

		List<String> backupCourseIds = new LinkedList<String>();
		for (PlanItemInfo planItem : planItems) {
			if (!PlanConstants.COURSE_TYPE.equals(planItem.getRefObjectType()))
				continue;

			List<String> periods = planItem.getPlanPeriods();
			if (periods == null || !periods.contains(termId))
				continue;

			String acodeattr = planItem.getAttributeValue("activityCode");
			List<String> acodes;
			if (acodeattr == null) {
				acodes = Collections.emptyList();
			} else {
				acodes = Arrays.asList(acodeattr.split(","));
			}

			AcademicPlanServiceConstants.ItemCategory category = planItem.getCategory();
			if (AcademicPlanServiceConstants.ItemCategory.CART
					.equals(category))
				cartCourseIdsAndActivityCodes.put(planItem.getRefObjectId(),
						acodes);
			else if (AcademicPlanServiceConstants.ItemCategory.PLANNED
					.equals(category))
				plannedCourseIdsAndActivityCodes.put(planItem.getRefObjectId(),
						acodes);
			else if (AcademicPlanServiceConstants.ItemCategory.BACKUP
					.equals(category))
				backupCourseIds.add(planItem.getRefObjectId());
		}

		List<CourseOption> rv = new ArrayList<CourseOption>(
				registeredCourseIdsAndActivityCodes.size()
						+ cartCourseIdsAndActivityCodes.size()
						+ plannedCourseIdsAndActivityCodes.size()
						+ backupCourseIds.size());
		buildCourseOptions(termId, true, true, registeredCourseIdsAndActivityCodes,
				rv);
		buildCourseOptions(termId, false, true, cartCourseIdsAndActivityCodes, rv);
		buildCourseOptions(termId, false, false, plannedCourseIdsAndActivityCodes, rv);
		if (!backupCourseIds.isEmpty())
			for (CourseOption co : getCourseOptions(backupCourseIds, termId))
				rv.add(co);

		return rv;
	}

	@Override
	public LearningPlan getLearningPlan(String requestedLearningPlanId)
			throws PermissionDeniedException {
		if (!KsapFrameworkServiceLocator.getUserSessionHelper().isStudent()) {
			throw new PermissionDeniedException(
					"Must be a student to build a schedule");
		}
		String studentId = KsapFrameworkServiceLocator.getUserSessionHelper()
				.getStudentId();
		ContextInfo ctx = KsapFrameworkServiceLocator.getContext()
				.getContextInfo();
		if (requestedLearningPlanId != null) {
			try {
				LearningPlanInfo rv = KsapFrameworkServiceLocator
						.getAcademicPlanService().getLearningPlan(
								requestedLearningPlanId, ctx);
				if (!studentId.equals(rv.getStudentId()))
					throw new PermissionDeniedException("Learning plan "
							+ requestedLearningPlanId
							+ " belongs to another student");
				if (!PlanConstants.LEARNING_PLAN_TYPE_PLAN.equals(rv
						.getTypeKey()))
					throw new PermissionDeniedException(
							"Not a viable learning plan for building a schedule "
									+ requestedLearningPlanId);
				return rv;
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException("Learning plan "
						+ requestedLearningPlanId + " does not exist", e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException("Invalid learning plan ID "
						+ requestedLearningPlanId, e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException("Invalid learning plan ID "
						+ requestedLearningPlanId, e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("LP lookup error "
						+ requestedLearningPlanId, e);
			}
		} else {
			try {
				List<LearningPlanInfo> lps = KsapFrameworkServiceLocator
						.getAcademicPlanService()
						.getLearningPlansForStudentByType(studentId,
								PlanConstants.LEARNING_PLAN_TYPE_PLAN, ctx);
				if (lps.isEmpty()) {
					throw new PermissionDeniedException(
							"No learning plans found for student " + studentId);
				}
				return KSCollectionUtils.getRequiredZeroElement(lps);
			} catch (DoesNotExistException e) {
				throw new IllegalArgumentException(
						"No learning plan exists for student " + studentId, e);
			} catch (InvalidParameterException e) {
				throw new IllegalArgumentException(
						"Invalid student ID or learning plan type " + studentId
								+ " " + PlanConstants.LEARNING_PLAN_TYPE_PLAN,
						e);
			} catch (MissingParameterException e) {
				throw new IllegalArgumentException(
						"Invalid student ID or learning plan type " + studentId
								+ " " + PlanConstants.LEARNING_PLAN_TYPE_PLAN,
						e);
			} catch (OperationFailedException e) {
				throw new IllegalStateException("LP lookup error", e);
			}
		}
	}

	@Override
	// TODO: Convert from dynamic attributes to DAO service
	public List<ReservedTime> getReservedTimes(String requestedLearningPlanId)
			throws PermissionDeniedException {
		return new ArrayList<ReservedTime>(
				getScheduleBuildAttribute(requestedLearningPlanId).reservedTime);
	}

	@Override
	// TODO: Convert from dynamic attributes to DAO service
	public void createReservedTime(String requestedLearningPlanId,
			ReservedTime reservedTime) throws PermissionDeniedException {
		ReservedTimeInfo createReservedTime = new ReservedTimeInfo(reservedTime);
		assert createReservedTime.getId() == null : "Already has an Id "
				+ createReservedTime.getId();
		createReservedTime.setId(UUID.randomUUID().toString());
		ScheduleBuildAttribute reservedTimes = getScheduleBuildAttribute(requestedLearningPlanId);
		reservedTimes.reservedTime.add(createReservedTime);
		updateScheduleBuildAttribute(requestedLearningPlanId, reservedTimes);
	}

	@Override
	// TODO: Convert from dynamic attributes to DAO service
	public void updateReservedTime(String requestedLearningPlanId,
			ReservedTime reservedTime) throws PermissionDeniedException {
		ScheduleBuildAttribute reservedTimes = getScheduleBuildAttribute(requestedLearningPlanId);
		ReservedTimeInfo updateReservedTime = null;
		ListIterator<ReservedTimeInfo> reservedTimeListIterator = reservedTimes.reservedTime
				.listIterator();
		while (updateReservedTime == null && reservedTimeListIterator.hasNext()) {
			ReservedTimeInfo reservedTimeInfo = reservedTimeListIterator.next();
			if (reservedTimeInfo.getId().equals(reservedTime.getId())) {
				updateReservedTime = reservedTimeInfo;
			}
		}
		if (updateReservedTime == null) {
			throw new IllegalArgumentException("Reserved time "
					+ reservedTime.getId()
					+ " does not exist on learning plan "
					+ requestedLearningPlanId == null ? "for student "
					+ KsapFrameworkServiceLocator.getUserSessionHelper()
							.getStudentId() : requestedLearningPlanId);
		}
		reservedTimeListIterator.set(new ReservedTimeInfo(reservedTime));
		updateScheduleBuildAttribute(requestedLearningPlanId, reservedTimes);
	}

	@Override
	// TODO: Convert from dynamic attributes to DAO service
	public void deleteReservedTime(String requestedLearningPlanId,
			String reservedTimeId) throws PermissionDeniedException {
		ScheduleBuildAttribute reservedTimes = getScheduleBuildAttribute(requestedLearningPlanId);
		boolean found = false;
		Iterator<ReservedTimeInfo> reservedTimeIterator = reservedTimes.reservedTime
				.iterator();
		while (!found && reservedTimeIterator.hasNext()) {
			ReservedTimeInfo reservedTimeInfo = reservedTimeIterator.next();
			if (reservedTimeInfo.getId().equals(reservedTimeId)) {
				reservedTimeIterator.remove();
				found = true;
			}
		}
		if (!found) {
			throw new IllegalArgumentException("Reserved time "
					+ reservedTimeId + " does not exist on learning plan "
					+ requestedLearningPlanId == null ? "for student "
					+ KsapFrameworkServiceLocator.getUserSessionHelper()
							.getStudentId() : requestedLearningPlanId);
		}
		updateScheduleBuildAttribute(requestedLearningPlanId, reservedTimes);
	}

	@Override
	// TODO: Convert from dynamic attributes to DAO service
	public List<PossibleScheduleOption> getSchedules(
			String requestedLearningPlanId) throws PermissionDeniedException {
		return new ArrayList<PossibleScheduleOption>(
				getScheduleBuildAttribute(requestedLearningPlanId).schedule);
	}

	@Override
	// TODO: Convert from dynamic attributes to DAO service
	public PossibleScheduleOption createSchedule(
			String requestedLearningPlanId, PossibleScheduleOption schedule)
			throws PermissionDeniedException {
		PossibleScheduleOptionInfo createSchedule = new PossibleScheduleOptionInfo(
				schedule);
		assert createSchedule.getId() == null : "Already has an Id "
				+ createSchedule.getId();
		createSchedule.setId(UUID.randomUUID().toString());
		createSchedule.setUniqueId(UUID.randomUUID().toString());
		createSchedule.setSelected(true);

		ScheduleBuildAttribute scheduleBuildInfo = getScheduleBuildAttribute(requestedLearningPlanId);
		createSchedule.setDescription("Saved "
				+ (++scheduleBuildInfo.savedScheduleSequence));
		scheduleBuildInfo.schedule.add(createSchedule);
		updateScheduleBuildAttribute(requestedLearningPlanId, scheduleBuildInfo);
		return createSchedule;
	}

	@Override
	// TODO: Convert from dynamic attributes to DAO service
	public void updateSchedule(String requestedLearningPlanId,
			PossibleScheduleOption schedule) throws PermissionDeniedException {
		ScheduleBuildAttribute scheduleBuildInfo = getScheduleBuildAttribute(requestedLearningPlanId);
		PossibleScheduleOptionInfo updateschedule = null;
		ListIterator<PossibleScheduleOptionInfo> scheduleListIterator = scheduleBuildInfo.schedule
				.listIterator();
		while (updateschedule == null && scheduleListIterator.hasNext()) {
			PossibleScheduleOptionInfo scheduleInfo = scheduleListIterator
					.next();
			if (scheduleInfo.getId().equals(schedule.getId())) {
				updateschedule = scheduleInfo;
			}
		}
		if (updateschedule == null) {
			throw new IllegalArgumentException("Reserved time "
					+ schedule.getId() + " does not exist on learning plan "
					+ requestedLearningPlanId == null ? "for student "
					+ KsapFrameworkServiceLocator.getUserSessionHelper()
							.getStudentId() : requestedLearningPlanId);
		}
		scheduleListIterator.set(new PossibleScheduleOptionInfo(schedule));
		updateScheduleBuildAttribute(requestedLearningPlanId, scheduleBuildInfo);
	}

	@Override
	// TODO: Convert from dynamic attributes to DAO service
	public void deleteSchedule(String requestedLearningPlanId, String scheduleId)
			throws PermissionDeniedException {
		ScheduleBuildAttribute scheduleBuildInfo = getScheduleBuildAttribute(requestedLearningPlanId);
		boolean found = false;
		Iterator<PossibleScheduleOptionInfo> scheduleIterator = scheduleBuildInfo.schedule
				.iterator();
		while (!found && scheduleIterator.hasNext()) {
			PossibleScheduleOptionInfo reservedTimeInfo = scheduleIterator
					.next();
			if (reservedTimeInfo.getId().equals(scheduleId)) {
				scheduleIterator.remove();
				found = true;
			}
		}
		if (!found) {
			throw new IllegalArgumentException("Schedule " + scheduleId
					+ " does not exist on learning plan "
					+ requestedLearningPlanId == null ? "for student "
					+ KsapFrameworkServiceLocator.getUserSessionHelper()
							.getStudentId() : requestedLearningPlanId);
		}
		updateScheduleBuildAttribute(requestedLearningPlanId, scheduleBuildInfo);
	}

	@Override
	public ShoppingCartForm getInitialCartForm() {
		return new DefaultShoppingCartForm();
	}

}
