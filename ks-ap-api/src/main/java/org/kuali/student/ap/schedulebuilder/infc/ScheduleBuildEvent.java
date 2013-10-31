package org.kuali.student.ap.schedulebuilder.infc;

import java.io.Serializable;
import java.util.Date;

public interface ScheduleBuildEvent extends Serializable {

	/**
	 * Get the description of the reserved time.
	 * 
	 * @return The description of the reserved time.
	 */
	String getDescription();

	/**
	 * Get the days and times in a condensed form, for easy reading.
	 * 
	 * @return The days and times, for easy reading.
	 */
	String getDaysAndTimes();

	/**
	 * Determine if the reserved time is all day.
	 * 
	 * @return True if the reserved time is all day.
	 */
	boolean isAllDay();

	/**
	 * Get the start date.
	 * 
	 * <p>
	 * The date portion refers to the first date, after which the selected days
	 * of the week will be included. When the event matches the entire term, and
	 * {@link #isAllDay()} is true, then the start date can be null. Otherwise,
	 * the date portion should be set to 1/1/1970.
	 * </p>
	 * 
	 * <p>
	 * The time portion refers to the start time of the event on each of the
	 * selected days of the week. When {@isAllDay()} is true, the time portion
	 * will be ignored, but should be set to 12:00AM for consistency.
	 * </p>
	 * 
	 * @return The start date and event start time.
	 */
	Date getStartDate();

	/**
	 * Get the until date.
	 * 
	 * <p>
	 * The date portion refers to the date until which the selected days
	 * of the week will be included. When the event matches the entire term, and
	 * {@link #isAllDay()} is true, then the until date can be null. Otherwise,
	 * the date portion should be set to 1/1/1970.
	 * </p>
	 * 
	 * <p>
	 * The time portion refers to the end time of the event on each of the
	 * selected days of the week. When {@isAllDay()} is true, the time portion
	 * will be ignored, but should be set to 12:00AM for consistency.
	 * </p>
	 * 
	 * @return The until date and event end time.
	 */
	Date getUntilDate();

	/**
	 * Determine if the reserved time includes Sunday.
	 * 
	 * @return True if the reserved time includes Sunday.
	 */
	boolean isSunday();

	/**
	 * Determine if the reserved time includes Monday.
	 * 
	 * @return True if the reserved time includes Monday.
	 */
	boolean isMonday();

	/**
	 * Determine if the reserved time includes Tuesday.
	 * 
	 * @return True if the reserved time includes Tuesday.
	 */
	boolean isTuesday();

	/**
	 * Determine if the reserved time includes Wednesday.
	 * 
	 * @return True if the reserved time includes Wednesday.
	 */
	boolean isWednesday();

	/**
	 * Determine if the reserved time includes Thursday.
	 * 
	 * @return True if the reserved time includes Thursday.
	 */
	boolean isThursday();

	/**
	 * Determine if the reserved time includes Friday.
	 * 
	 * @return True if the reserved time includes Friday.
	 */
	boolean isFriday();

	/**
	 * Determine if the reserved time includes Saturday.
	 * 
	 * @return True if the reserved time includes Saturday.
	 */
	boolean isSaturday();

}
