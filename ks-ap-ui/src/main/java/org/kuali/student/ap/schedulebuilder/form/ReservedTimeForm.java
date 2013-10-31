package org.kuali.student.ap.schedulebuilder.form;

import org.kuali.rice.krad.web.form.UifFormBase;
import org.kuali.student.ap.schedulebuilder.infc.ReservedTime;
import org.kuali.student.r2.core.scheduling.constants.SchedulingServiceConstants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class ReservedTimeForm extends UifFormBase implements ReservedTime {

	private static final long serialVersionUID = 2036743000466751688L;

	private static final String[] DATE_FORMATS = new String[] { "MM/dd/yyyy", };
	private static final String[] TIME_FORMATS = new String[] { "hh:mm a",
			"hh:mma", "hh:mm" };

	// TODO: convert these to property editors, add to standard registry
	private static final Date toTime(String str, Date date) {
		if (str == null)
			return date;

		SimpleDateFormat df = new SimpleDateFormat();
		Calendar dc = Calendar.getInstance();
		if (date == null) {
			dc.set(Calendar.YEAR, 1970);
			dc.set(Calendar.MONTH, Calendar.JANUARY);
			dc.set(Calendar.DATE, 1);
		} else {
			dc.setTime(date);
		}
		dc.set(Calendar.HOUR_OF_DAY, 0);
		dc.set(Calendar.MINUTE, 0);
		dc.set(Calendar.SECOND, 0);
		dc.set(Calendar.MILLISECOND, 0);

		str = str.toUpperCase().trim();
		if (str.endsWith("A") || str.endsWith("P"))
			str += "M";

		boolean success = false;
		for (int i = 0; i < TIME_FORMATS.length; i++) {
			try {
				Calendar c = Calendar.getInstance();
				df.applyPattern(TIME_FORMATS[i]);
				c.setTime(df.parse(str));
				if (i == TIME_FORMATS.length - 1)
					if (c.get(Calendar.HOUR_OF_DAY) < 8)
						c.set(Calendar.AM_PM, Calendar.PM);
				dc.set(Calendar.HOUR_OF_DAY, c.get(Calendar.HOUR_OF_DAY));
				dc.set(Calendar.MINUTE, c.get(Calendar.MINUTE));
				success = true;
				break;
			} catch (ParseException e) {
			}
		}

		return success ? dc.getTime() : date;
	}

	private static final String fromTime(Date time) {
		return time == null ? null : new SimpleDateFormat(TIME_FORMATS[0])
				.format(time);
	}

	private static final Date toDate(String str, Date time) {
		if (str == null)
			return time;

		SimpleDateFormat df = new SimpleDateFormat();
		Calendar tc = Calendar.getInstance();
		if (time == null) {
			tc.set(Calendar.HOUR_OF_DAY, 0);
			tc.set(Calendar.MINUTE, 0);
			tc.set(Calendar.SECOND, 0);
			tc.set(Calendar.MILLISECOND, 0);
		} else {
			tc.setTime(time);
		}

		str = str.toUpperCase().trim();

		boolean success = false;
		for (int i = 0; i < DATE_FORMATS.length; i++) {
			try {
				Calendar c = Calendar.getInstance();
				df.applyPattern(DATE_FORMATS[i]);
				c.setTime(df.parse(str));
				tc.set(Calendar.YEAR, c.get(Calendar.YEAR));
				tc.set(Calendar.MONTH, c.get(Calendar.MONTH));
				tc.set(Calendar.DATE, c.get(Calendar.DATE));
				success = true;
				break;
			} catch (ParseException e) {
			}
		}

		return success ? tc.getTime() : time;
	}

	private static final String fromDate(Date date) {
		return date == null ? null : new SimpleDateFormat(DATE_FORMATS[0])
				.format(date);
	}

	private final String uniqueId = UUID.randomUUID().toString();
	private String requestedLearningPlanId;
	private String id;
	private String description;
	private boolean allDay;
	private boolean sunday;
	private boolean monday;
	private boolean tuesday;
	private boolean wednesday;
	private boolean thursday;
	private boolean friday;
	private boolean saturday;
	private Date startDate;
	private Date untilDate;

	/**
	 * Set to true after the requested reserved time create, update, or delete
	 * has been committed.
	 */
	private boolean complete;

	public String getRequestedLearningPlanId() {
		return requestedLearningPlanId;
	}

	public void setRequestedLearningPlanId(String requestedLearningPlanId) {
		this.requestedLearningPlanId = requestedLearningPlanId;
	}

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean isAllDay() {
		return allDay;
	}

	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}

	@Override
	public Date getStartDate() {
		return startDate;
	}

	public String getStartDateStr() {
		return fromDate(startDate);
	}

	public void setStartDateStr(String startDateStr) {
		this.startDate = toDate(startDateStr, this.startDate);
	}

	public String getStartTimeStr() {
		return fromTime(startDate);
	}

	public void setStartTimeStr(String startTimeStr) {
		this.startDate = toTime(startTimeStr, this.startDate);
	}

	public String getEndTimeStr() {
		return fromTime(untilDate);
	}

	public void setEndTimeStr(String endTimeStr) {
		this.untilDate = toTime(endTimeStr, this.untilDate);
	}

	@Override
	public Date getUntilDate() {
		return untilDate;
	}

	public String getUntilDateStr() {
		return fromDate(untilDate);
	}

	public void setUntilDateStr(String untilDateStr) {
		this.untilDate = toDate(untilDateStr, this.untilDate);
	}

	@Override
	public boolean isSunday() {
		return sunday;
	}

	public void setSunday(boolean sunday) {
		this.sunday = sunday;
	}

	@Override
	public boolean isMonday() {
		return monday;
	}

	public void setMonday(boolean monday) {
		this.monday = monday;
	}

	@Override
	public boolean isTuesday() {
		return tuesday;
	}

	public void setTuesday(boolean tuesday) {
		this.tuesday = tuesday;
	}

	@Override
	public boolean isWednesday() {
		return wednesday;
	}

	public void setWednesday(boolean wednesday) {
		this.wednesday = wednesday;
	}

	@Override
	public boolean isThursday() {
		return thursday;
	}

	public void setThursday(boolean thursday) {
		this.thursday = thursday;
	}

	@Override
	public boolean isFriday() {
		return friday;
	}

	public void setFriday(boolean friday) {
		this.friday = friday;
	}

	@Override
	public boolean isSaturday() {
		return saturday;
	}

	public void setSaturday(boolean saturday) {
		this.saturday = saturday;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@Override
	public String getDaysAndTimes() {
		StringBuilder daysAndTimes = new StringBuilder();

		if (monday)
			daysAndTimes
					.append(SchedulingServiceConstants.MONDAY_TIMESLOT_DISPLAY_DAY_CODE);
		if (tuesday)
			daysAndTimes
					.append(SchedulingServiceConstants.TUESDAY_TIMESLOT_DISPLAY_DAY_CODE);
		if (wednesday)
			daysAndTimes
					.append(SchedulingServiceConstants.WEDNESDAY_TIMESLOT_DISPLAY_DAY_CODE);
		if (thursday)
			daysAndTimes
					.append(SchedulingServiceConstants.THURSDAY_TIMESLOT_DISPLAY_DAY_CODE);
		if (friday)
			daysAndTimes
					.append(SchedulingServiceConstants.FRIDAY_TIMESLOT_DISPLAY_DAY_CODE);
		if (saturday)
			daysAndTimes
					.append(SchedulingServiceConstants.SATURDAY_TIMESLOT_DISPLAY_DAY_CODE);
		if (sunday)
			daysAndTimes
					.append(SchedulingServiceConstants.SUNDAY_TIMESLOT_DISPLAY_DAY_CODE);

		SimpleDateFormat df = new SimpleDateFormat("h:mm a");
		if (daysAndTimes.length() > 0)
			daysAndTimes.append(" ");
		daysAndTimes.append(df.format(startDate));
		daysAndTimes.append(" to ");
		daysAndTimes.append(df.format(untilDate));

		return daysAndTimes.toString();
	}

	@Override
	public String getUniqueId() {
		return uniqueId;
	}

	@Override
	public boolean isSelected() {
		return true;
	}

}
