package org.kuali.student.ap.plannerreview.dto;

import org.kuali.student.ap.plannerreview.infc.ConversationComment;
import org.kuali.student.r2.common.infc.Meta;
import org.kuali.student.r2.common.infc.RichText;
import org.kuali.student.r2.common.util.date.DateFormatters;
import org.kuali.student.r2.common.util.date.KSDateTimeFormatter;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConversationCommentInfo", propOrder = { "id", "meta", "byAdvisor",
		"readOnce", "commentText", "_futureElements" })
public class ConversationCommentInfo implements ConversationComment, Serializable, Comparable<ConversationCommentInfo> {

	private static final long serialVersionUID = -6518426637453193416L;

	@XmlElement
	private String id;
	
	@XmlElement
	private Meta meta;
	
	@XmlElement
	private boolean byAdvisor;
	
	@XmlElement
	private boolean readOnce;
	
	@XmlElement
	private RichText commentText;
	
	@SuppressWarnings("unused")
	@XmlAnyElement
	private List<Element> _futureElements;
	
	@Override
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public Meta getMeta() {
		return meta;
	}
	
	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	@Override
	public boolean isByAdvisor() {
		return byAdvisor;
	}
	
	public void setByAdvisor(boolean byAdvisor) {
		this.byAdvisor = byAdvisor;
	}

	@Override
	public boolean isReadOnce() {
		return readOnce;
	}
	
	public void setReadOnce(boolean readOnce) {
		this.readOnce = readOnce;
	}

	@Override
	public RichText getCommentText() {
		return commentText;
	}
	
	public void setCommentText(RichText commentText) {
		this.commentText = commentText;
	}
	
	@Override
	public int compareTo(ConversationCommentInfo o) {
		// TODO Better to use create or update time?
		return meta.getCreateTime().compareTo(o.getMeta().getCreateTime());
	}
	
	/* Convenience methods are below */
	
	/**
	 * Turn the comment's update time into something more suitable for human viewing
	 * See {@link #makeDateHumanReadable(java.util.Date)}
	 * @return A String representation of the update time
	 */
	public String getHumanReadableDate() {
		return makeDateHumanReadable(meta.getUpdateTime());
	}

	/**
	 * Get the time that the comment was created
	 * See {@link #getTimeFromDate(java.util.Date)}
	 * @return
	 */
	public String getCommentCreateTime() {
		return getTimeFromDate(meta.getCreateTime());
	}
	
	/**
	 * Get the day of the year.  Will be used to determine if the date "header" 
	 * should be displayed that summarizes all comments for a specific day
	 * @return The integer day of the year, so February 20 would return 51
	 */
	public int getCommentCreationDay() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(meta.getCreateTime());
		int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		return dayOfYear;
	}

	/**
	 * Get some date display text for when the comment was created.
	 * It will have the format of "EEEE, MMM d" (Friday, April 2).
	 * If the date in question is of a different year than the current year, also add the year to the returned string.
	 * @return
	 */
	public String getCommentCreationDayDisplay() {
		Date now = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		int nowYear = cal.get(Calendar.YEAR);
		Date commentDate = meta.getCreateTime();
		cal.setTime(commentDate);
		int commentYear = cal.get(Calendar.YEAR);
		boolean isSameYear = nowYear == commentYear;

        KSDateTimeFormatter df = isSameYear ? DateFormatters.DAY_MONTH_DATE_FORMATTER : DateFormatters.DAY_MONTH_DATE_YEAR_FORMATTER;
		String humanDate = df.format(commentDate);
		return humanDate;
	}
	
	/**
	 * Method that will turn a java.util.Date into something more readable by a human.
	 * Default format will be MMM d (Jul 5).  If the date is the same as the current date, result will be "Today".
	 * If the date is one day less than the current date, the result will be "Yesterday".
	 * TODO Move the strings into a property file so they can be internationalized?
	 * @param date Date object to convert to a human readable form
	 * @return
	 */
	private String makeDateHumanReadable(Date date) {
        KSDateTimeFormatter df = DateFormatters.SHORTMONTH_DAY_FORMATTER;
		String humanDate = df.format(date);
		Date now = new Date();
		Calendar todayCal = Calendar.getInstance();
		todayCal.setTime(now);
		int todayDayOfYear = todayCal.get(Calendar.DAY_OF_YEAR);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
		
		int dateDiff = todayDayOfYear - dayOfYear;
		switch(dateDiff) {
			case 0:
				humanDate = "Today";
				break;
			case 1:
				humanDate = "Yesterday";
		}
		
		return humanDate;
	}
	
	/**
	 * Get the time portion from a date.  The format will be "h:m a" (3:12 pm)
	 * @param date Date object to extract the time from
	 * @return The time portion of a date
	 */
	private String getTimeFromDate(Date date) {
		KSDateTimeFormatter df = DateFormatters.HOUR_NOZERO_MINUTE_AM_PM_TIME_FORMATTER;
		String humanDate = df.format(date);
		return humanDate;
	}
}
