package org.kuali.student.ap.schedulebuilder.controller;

import org.apache.commons.lang.StringEscapeUtils;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.kuali.rice.krad.UserSession;
import org.kuali.rice.krad.util.GlobalVariables;
import org.kuali.rice.krad.web.controller.UifControllerBase;
import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.common.infc.HasUniqueId;
import org.kuali.student.ap.framework.config.KsapFrameworkServiceLocator;
import org.kuali.student.ap.schedulebuilder.ScheduleBuildForm;
import org.kuali.student.ap.schedulebuilder.ShoppingCartForm;
import org.kuali.student.ap.schedulebuilder.infc.ActivityOption;
import org.kuali.student.ap.schedulebuilder.infc.ClassMeetingTime;
import org.kuali.student.ap.schedulebuilder.infc.PossibleScheduleOption;
import org.kuali.student.ap.schedulebuilder.infc.ReservedTime;
import org.kuali.student.ap.schedulebuilder.infc.ScheduleBuildEvent;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.kuali.student.r2.core.acal.infc.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.json.JsonWriter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

@Controller
@RequestMapping(value = "/sb")
public class ScheduleBuildController extends UifControllerBase {

	private static final Logger LOG = LoggerFactory.getLogger(ScheduleBuildController.class);

	private static String EVENT_CSS_PREFIX = "ks-schedule-Color";

	public static final String SB_FORM = "ScheduleBuild-FormView";

	@Override
	protected UifFormBase createInitialForm(HttpServletRequest request) {
		return (UifFormBase) KsapFrameworkServiceLocator
				.getScheduleBuildStrategy().getInitialForm();
	}

	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView get(@ModelAttribute("KualiForm") UifFormBase form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		super.start((UifFormBase) form, result, request, response);

		ScheduleBuildForm sbform = (ScheduleBuildForm) form;
		try {
			sbform.reset();
			// Generate test case for ScheduleBuildMavenTest - not for
			// production.
			//			File out = new File("/tmp/CourseOptions.ser");
			//			FileOutputStream fout = new FileOutputStream(out);
			//			ObjectOutputStream oos = new ObjectOutputStream(fout);
			//			oos.writeObject(((ScheduleBuildForm) form).getCourseOptions());
			//			oos.flush();
			//			oos.close();
			//			fout.flush();
			//			fout.close();
			//			LOG.debug("Written to " + out);
		} catch (IllegalArgumentException e) {
			LOG.error("Failed to initialize schedule build form", e);
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Failed to initialize schedule build form");
			return null;
		}

		form.setViewId(SB_FORM);
		form.setView(super.getViewService().getViewById(SB_FORM));
		return getUIFModelAndView(form);
	}

	private static class EventAggregateData {
		private final Calendar cal = Calendar.getInstance();
		private final Date minDate;
		private final Date maxDate;
		private final Set<Date> breakDates = new TreeSet<Date>();

		private boolean weekends;
		private int minTime = 8;
		private int maxTime = 17;
		private Date lastUntilDate;

		private EventAggregateData(Date minDate, Date maxDate) {
			this.minDate = minDate;
			this.maxDate = maxDate;
		}

		private Date getDatePortion(Date date) {
			if (date == null)
				return null;

			cal.setTime(date);
			if (cal.get(Calendar.YEAR) == 1970
					&& cal.get(Calendar.MONTH) == Calendar.JANUARY
					&& cal.get(Calendar.DATE) == 1)
				return null;

			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();
		}

		private Date getTimePortion(Date date) {
			if (date == null) {
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
			} else
				cal.setTime(date);

			cal.set(Calendar.YEAR, 1970);
			cal.set(Calendar.MONTH, Calendar.JANUARY);
			cal.set(Calendar.DATE, 1);
			return cal.getTime();
		}

		private void addBreakDate(Date date) {
			getDatePortion(date); // adjusts cal
			cal.add(Calendar.DATE,
					-(cal.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY));
			breakDates.add(minDate.after(cal.getTime()) ? minDate : cal
					.getTime());
		}

		private void updateLastUntilDate(Date untilDate) {
			getDatePortion(untilDate); // adjusts cal
			cal.add(Calendar.DATE,
					Calendar.SUNDAY + 7 - cal.get(Calendar.DAY_OF_WEEK));
			if (maxDate.after(cal.getTime()))
				breakDates.add(cal.getTime());
			if (!maxDate.before(untilDate)
					&& (lastUntilDate == null || (untilDate != null && lastUntilDate
							.before(untilDate))))
				lastUntilDate = untilDate;
		}

		private void updateMinMaxTime(Date eventStart, Date eventEnd) {
			cal.setTime(eventStart);
			minTime = Math.min(minTime, cal.get(Calendar.HOUR_OF_DAY));
			cal.setTime(eventEnd);
			maxTime = Math.max(maxTime,
					cal.get(Calendar.HOUR_OF_DAY)
							+ (cal.get(Calendar.MINUTE) > 0 ? 1 : 0));
		}

		public void updateWeekends(boolean weekends) {
			weekends &= weekends;
		}

		private Date addOneWeek(Date eventStart) {
			cal.setTime(eventStart);
			cal.add(Calendar.DATE, 7);
			return cal.getTime();
		}

		private void addWeekBreaks(JsonArrayBuilder jweeks, Term term) {
			int weekNumber = 1;
			Iterator<Date> weekBreaks = breakDates.iterator();
			Date start = weekBreaks.hasNext() ? weekBreaks.next() : term
					.getStartDate();
			if (lastUntilDate == null)
				lastUntilDate = term.getEndDate();
            KSDateTimeFormatter df = DateFormatters.SHORTMONTH_DAY_FORMATTER;
			boolean done;
			do {
				Date end = (done = !weekBreaks.hasNext()) ? lastUntilDate
						: weekBreaks.next();
				long sd = start.getTime();
				long ed = end.getTime();
				int weeks = (int) ((ed - sd) / 604800000);
				JsonObjectBuilder jweek = Json.createObjectBuilder();
				jweek.add("title", "Week" + (weeks > 1 ? "s " : " ")
						+ weekNumber
						+ (weeks > 1 ? "-" + (weekNumber + weeks - 1) : ""));
				jweek.add("subtitle", df.format(start) + " - " + df.format(end));
				cal.setTime(start);
				jweek.add("gotoYear", cal.get(Calendar.YEAR));
				jweek.add("gotoMonth", cal.get(Calendar.MONTH) + 1);
				jweek.add("gotoDate", cal.get(Calendar.DATE));
				jweeks.add(jweek);
				weekNumber += weeks;
				start = end;
			} while (!done);
		}
	}

	private static void addEvents(Term term, ScheduleBuildEvent meeting,
			String description, ActivityOption ao, String cssClass,
			JsonArrayBuilder jevents, EventAggregateData aggregate) {
		Date startDate = aggregate.getDatePortion(meeting.getStartDate());
		if (startDate == null || startDate.before(term.getStartDate()))
			startDate = aggregate.getDatePortion(term.getStartDate());
		aggregate.addBreakDate(startDate);

		Date untilDate = aggregate.getDatePortion(meeting.getUntilDate());
		if (untilDate == null || untilDate.after(term.getEndDate()))
			untilDate = aggregate.getDatePortion(term.getEndDate());
		aggregate.updateLastUntilDate(untilDate);

		aggregate.updateWeekends(meeting.isSunday() || meeting.isSaturday());

		Date eventStart = aggregate.getTimePortion(meeting.getStartDate());
		long durationSeconds;
		if (meeting.isAllDay())
			durationSeconds = 0L;
		else {
			Date eventEnd = aggregate.getTimePortion(meeting.getUntilDate());
			durationSeconds = (eventEnd.getTime() - eventStart.getTime()) / 1000;
			aggregate.updateMinMaxTime(eventStart, eventEnd);
		}

		while (!startDate.after(untilDate)) {
			if (meeting.isSunday())
				jevents.add(createEvent(startDate, eventStart, aggregate.cal,
						Calendar.SUNDAY, durationSeconds, cssClass, ao,
						description, meeting));
			if (meeting.isMonday())
				jevents.add(createEvent(startDate, eventStart, aggregate.cal,
						Calendar.MONDAY, durationSeconds, cssClass, ao,
						description, meeting));
			if (meeting.isTuesday())
				jevents.add(createEvent(startDate, eventStart, aggregate.cal,
						Calendar.TUESDAY, durationSeconds, cssClass, ao,
						description, meeting));
			if (meeting.isWednesday())
				jevents.add(createEvent(startDate, eventStart, aggregate.cal,
						Calendar.WEDNESDAY, durationSeconds, cssClass, ao,
						description, meeting));
			if (meeting.isThursday())
				jevents.add(createEvent(startDate, eventStart, aggregate.cal,
						Calendar.THURSDAY, durationSeconds, cssClass, ao,
						description, meeting));
			if (meeting.isFriday())
				jevents.add(createEvent(startDate, eventStart, aggregate.cal,
						Calendar.FRIDAY, durationSeconds, cssClass, ao,
						description, meeting));
			if (meeting.isSaturday())
				jevents.add(createEvent(startDate, eventStart, aggregate.cal,
						Calendar.SATURDAY, durationSeconds, cssClass, ao,
						description, meeting));
			startDate = aggregate.addOneWeek(startDate);
		}
	}

	private static JsonObjectBuilder createEvent(Date startDate,
			Date eventStart, Calendar cal, int dow, long durationSeconds,
			String cssClass, ActivityOption ao, String description,
			ScheduleBuildEvent sbEvent) {

		// Calculate the date for the event in seconds since the epoch
		cal.setTime(startDate);
		cal.add(Calendar.DATE, dow - cal.get(Calendar.DAY_OF_WEEK));
		long eventStartSeconds = cal.getTime().getTime() / 1000L;

		// Add the time for the event in seconds since midnight GMT
		cal.setTime(eventStart);
		eventStartSeconds += cal.getTime().getTime() / 1000L;

		// Adjust for time zone by subtracting midnight prior to the event.
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		eventStartSeconds -= cal.getTime().getTime() / 1000L;

		JsonObjectBuilder event = Json.createObjectBuilder();
		String uid = ao == null ? ((HasUniqueId) sbEvent).getUniqueId() : ao
				.getUniqueId();
		event.add("id", uid + "_" + eventStartSeconds);
		event.add("title", description);
		event.add("start", eventStartSeconds);
		JsonArrayBuilder acss = Json.createArrayBuilder();
		acss.add(EVENT_CSS_PREFIX);
		acss.add(cssClass);
		event.add("className", acss);
		if (durationSeconds == 0) {
			event.add("allDay", true);
		} else {
			event.add("allDay", false);
			event.add("end", eventStartSeconds + durationSeconds);
		}
		event.add("editable", false);

		if (ao != null) {
			StringBuilder hover = new StringBuilder();
			hover.append(ao.getActivityName());
			hover.append(" (");
			hover.append(ao.getActivityTypeDescription());
			hover.append(") ");
			hover.append(description);
			hover.append(' ');
			hover.append(ao.getAcademicSessionDescr());
			for (ClassMeetingTime meetingTime : ao.getClassMeetingTimes()) {
				hover.append(' ');
				hover.append(meetingTime.getDaysAndTimes());
			}
			event.add("hoverText", hover.toString());
		} else {
			event.add("hoverText", description);
		}

        KSDateTimeFormatter edf = DateFormatters.DAY_SHORTMONTH_DATE_FORMATTER;
		Element modal = DocumentHelper.createElement("div");
		modal.addAttribute("class", "ksap-sb-event-dialog");
		Element titleDiv = DocumentHelper.createElement("div");
		titleDiv.addAttribute("class", "ksap-sb-event-dialog-title");
		Element titleSpan = titleDiv.addElement("span");
		if (ao != null) {
			titleSpan.setText(ao.getCourseOfferingCode() + " "
					+ ao.getActivityName());
		} else {
			titleSpan.setText(description);
		}
		Element dl = modal.addElement("dl"), dt, dd;
		if (ao != null) {
			dt = dl.addElement("dt");
			dt.setText("Class Number");
			dd = dl.addElement("dd");
			dd.setText(ao.getRegistrationCode());
			dt = dl.addElement("dt");
			dt.setText("Available Seats");
			dd = dl.addElement("dd");
			dd.setText(ao.getOpenSeats() + " / " + ao.getTotalSeats());
			dt = dl.addElement("dt");
			dt.setText("Permission Required");
			dd = dl.addElement("dd");
			dd.setText(ao.isRequiresPermission() ? "Yes" : "No");
		}
		dt = dl.addElement("dt");
		dt.setText("Time");
		dd = dl.addElement("dd");
		dd.setText(sbEvent.getDaysAndTimes() + " from "
				+ edf.format(sbEvent.getStartDate()) + " to "
				+ edf.format(sbEvent.getUntilDate()));

		event.add("dialogHtml", modal.asXML());
		return event;
	}

	@RequestMapping(params = "methodToCall=build")
	public ModelAndView build(
			@ModelAttribute("KualiForm") ScheduleBuildForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		form.buildSchedules();

		EventAggregateData aggregate = new EventAggregateData(form.getTerm()
				.getStartDate(), form.getTerm().getEndDate());
		JsonObjectBuilder json = Json.createObjectBuilder();
		JsonArrayBuilder jpossible = Json.createArrayBuilder();
		List<PossibleScheduleOption> psos = form.getPossibleScheduleOptions();
		List<PossibleScheduleOption> sss = form.getSavedSchedules();
		Map<String, PossibleScheduleOption> cartOptions =
				new HashMap<String, PossibleScheduleOption>(psos.size() + sss.size());

		int discardCount = 0;
		for (int i = 0; i < psos.size(); i++) {
			PossibleScheduleOption pso = psos.get(i);
			if (pso.isDiscarded()) {
				discardCount++;
				continue;
			}

			String cssClass = EVENT_CSS_PREFIX + "--"
					+ ((i - discardCount) % 26);
			JsonObjectBuilder jpso = Json.createObjectBuilder();
			JsonArrayBuilder jevents = Json.createArrayBuilder();
			jpso.add("path", "possibleScheduleOptions[" + i + "]");
			jpso.add("uniqueId", pso.getUniqueId());
			jpso.add("selected", pso.isSelected());
			int saveIndex = sss.indexOf(pso);
			jpso.add("saved", saveIndex != -1);
			if (saveIndex != -1) {
				jpso.add("id", sss.get(saveIndex).getId());
			}
			jpso.add("htmlDescription", pso.getDescription().getFormatted());
			JsonArrayBuilder acss = Json.createArrayBuilder();
			acss.add(EVENT_CSS_PREFIX);
			acss.add(cssClass);
			jpso.add("eventClass", acss);
			for (ActivityOption ao : pso.getActivityOptions())
				if (!ao.isPrimary() || !ao.isEnrollmentGroup())
					for (ClassMeetingTime meeting : ao.getClassMeetingTimes())
						addEvents(
								form.getTerm(),
								meeting,
								ao.getCourseOfferingCode()
										+ (meeting.getLocation() == null ? ""
												: " (" + meeting.getLocation()
														+ ") - " + pso.getDescription().getPlain()
										), ao, cssClass,
								jevents, aggregate);

			jpso.add("weekends", aggregate.weekends);
			jpso.add("minTime", aggregate.minTime);
			jpso.add("maxTime", aggregate.maxTime);
			jpso.add("events", jevents);
			jpossible.add(jpso);

			cartOptions.put(pso.getUniqueId(), pso);
		}
		json.add("possible", jpossible);

		discardCount = 0;
		JsonArrayBuilder jsaved = Json.createArrayBuilder();
		for (int i = 0; i < sss.size(); i++) {
			PossibleScheduleOption sso = sss.get(i);
			if (sso.isDiscarded()) {
				discardCount++;
				continue;
			}

			String cssClass = EVENT_CSS_PREFIX + "--"
					+ ((i + psos.size() - discardCount) % 26);
			JsonObjectBuilder jsso = Json.createObjectBuilder();
			JsonArrayBuilder jevents = Json.createArrayBuilder();
			jsso.add("path", "savedSchedules[" + i + "]");
			jsso.add("id", sso.getId());
			jsso.add("uniqueId", sso.getUniqueId());
			jsso.add("selected", sso.isSelected());
			jsso.add("saved", true);
			jsso.add("htmlDescription", sso.getDescription().getFormatted());
			JsonArrayBuilder acss = Json.createArrayBuilder();
			acss.add(EVENT_CSS_PREFIX);
			acss.add(cssClass);
			jsso.add("eventClass", acss);
			for (ActivityOption ao : sso.getActivityOptions())
				if (!ao.isPrimary() || !ao.isEnrollmentGroup())
					for (ClassMeetingTime meeting : ao.getClassMeetingTimes())
						addEvents(
								form.getTerm(),
								meeting,
								ao.getCourseOfferingCode()
										+ (meeting.getLocation() == null ? ""
												: " (" + meeting.getLocation()
														+ ") - " + sso.getDescription().getPlain()),
								ao, cssClass,
								jevents, aggregate);

			jsso.add("weekends", aggregate.weekends);
			jsso.add("minTime", aggregate.minTime);
			jsso.add("maxTime", aggregate.maxTime);
			jsso.add("events", jevents);
			jsaved.add(jsso);

			cartOptions.put(sso.getUniqueId(), sso);
		}
		json.add("saved", jsaved);

		UserSession sess = GlobalVariables.getUserSession();
		sess.addObject(ShoppingCartForm.POSSIBLE_OPTIONS_KEY, cartOptions);

        KSDateTimeFormatter ddf = DateFormatters.MONTH_DAY_YEAR_DATE_FORMATTER;
		JsonArrayBuilder jreserved = Json.createArrayBuilder();
		List<ReservedTime> rts = form.getReservedTimes();
		for (int i = 0; i < rts.size(); i++) {
			ReservedTime rt = rts.get(i);

			String cssClass = EVENT_CSS_PREFIX + "--"
					+ ((i - discardCount + 13) % 26);
			JsonObjectBuilder rto = Json.createObjectBuilder();
			JsonArrayBuilder jevents = Json.createArrayBuilder();
			rto.add("uniqueId", rt.getUniqueId());
			rto.add("selected", rt.isSelected());
			rto.add("descr", StringEscapeUtils.escapeHtml(rt.getDescription()));
			rto.add("daysTimes", rt.getDaysAndTimes());
			rto.add("startDate", ddf.format(rt.getStartDate()));
			rto.add("untilDate", ddf.format(rt.getUntilDate()));
			JsonArrayBuilder acss = Json.createArrayBuilder();
			acss.add(EVENT_CSS_PREFIX);
			acss.add(cssClass);
			rto.add("eventClass", acss);

			addEvents(form.getTerm(), rt, rt.getDescription(), null, cssClass,
					jevents, aggregate);

			rto.add("weekends", aggregate.weekends);
			rto.add("minTime", aggregate.minTime);
			rto.add("maxTime", aggregate.maxTime);
			rto.add("events", jevents);
			jreserved.add(rto);
		}
		json.add("reserved", jreserved);

		JsonArrayBuilder jweeks = Json.createArrayBuilder();
		aggregate.addWeekBreaks(jweeks, form.getTerm());
		json.add("weeks", jweeks);

		json.add("more", form.hasMore());

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "No-cache");
		response.setHeader("Cache-Control", "No-store");
		response.setHeader("Cache-Control", "max-age=0");
		JsonWriter jwriter = Json.createWriter(response.getWriter());
		jwriter.writeObject(json.build());
		jwriter.close();

		return null;
	}

	@RequestMapping(params = "methodToCall=save")
	public ModelAndView saveSchedule(
			@ModelAttribute("KualiForm") ScheduleBuildForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		JsonObjectBuilder json = Json.createObjectBuilder();

		PossibleScheduleOption sso = form.saveSchedule();
		if (sso == null) {
			response.sendError(HttpServletResponse.SC_BAD_REQUEST,
					"Failed to save schedule, check log for error");
			return null;
		}

		List<PossibleScheduleOption> ssos = form.getSavedSchedules();
		int i = ssos.indexOf(sso);
		JsonObjectBuilder jsso = Json.createObjectBuilder();
		JsonArrayBuilder jevents = Json.createArrayBuilder();
		jsso.add("id", sso.getId());
		jsso.add("path", "savedSchedules[" + i + "]");
		jsso.add("uniqueId", sso.getUniqueId());
		jsso.add("selected", false);
		jsso.add("saved", true);
		jsso.add("htmlDescription", sso.getDescription().getFormatted());

		String cssClass = EVENT_CSS_PREFIX + "--"
				+ ((i + 13 + ssos.size()) % 26);
		JsonArrayBuilder acss = Json.createArrayBuilder();
		acss.add(EVENT_CSS_PREFIX);
		acss.add(cssClass);
		jsso.add("eventClass", acss);

		EventAggregateData aggregate = new EventAggregateData(form.getTerm()
				.getStartDate(), form.getTerm().getEndDate());
		for (ActivityOption ao : sso.getActivityOptions())
			if (!ao.isPrimary() || !ao.isEnrollmentGroup())
				for (ClassMeetingTime meeting : ao.getClassMeetingTimes())
					addEvents(form.getTerm(), meeting, sso.getDescription()
							.getPlain()
							+ " - "
							+ ao.getCourseOfferingCode()
							+ (meeting.getLocation() == null ? "" : " ("
									+ meeting.getLocation() + ")"), ao,
							cssClass, jevents, aggregate);

		jsso.add("weekends", aggregate.weekends);
		jsso.add("minTime", aggregate.minTime);
		jsso.add("maxTime", aggregate.maxTime);
		jsso.add("events", jevents);
		json.add("saved", jsso);

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "No-cache");
		response.setHeader("Cache-Control", "No-store");
		response.setHeader("Cache-Control", "max-age=0");
		JsonWriter jwriter = Json.createWriter(response.getWriter());
		jwriter.writeObject(json.build());
		jwriter.close();

		return null;
	}

	@RequestMapping(params = "methodToCall=remove")
	public ModelAndView removeSchedule(
			@ModelAttribute("KualiForm") ScheduleBuildForm form,
			BindingResult result, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		form.removeSchedule();

		JsonObjectBuilder json = Json.createObjectBuilder();
		json.add("success", true);

		response.setContentType("application/json");
		response.setHeader("Cache-Control", "No-cache");
		response.setHeader("Cache-Control", "No-store");
		response.setHeader("Cache-Control", "max-age=0");
		JsonWriter jwriter = Json.createWriter(response.getWriter());
		jwriter.writeObject(json.build());
		jwriter.close();

		return null;
	}

}
