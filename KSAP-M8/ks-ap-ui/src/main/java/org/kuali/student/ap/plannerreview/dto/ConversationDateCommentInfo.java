package org.kuali.student.ap.plannerreview.dto;

import org.kuali.student.ap.plannerreview.infc.ConversationComment;

import java.util.ArrayList;
import java.util.List;

public class ConversationDateCommentInfo implements Comparable<ConversationDateCommentInfo> {

	private Integer dayOfYear;
	private List<ConversationComment> comments;
	private String dateDisplay;
	
	public List<ConversationComment> getComments() {
		return comments;
	}
	public void setComments(List<ConversationComment> comments) {
		this.comments = comments;
	}
	public Integer getDayOfYear() {
		return dayOfYear;
	}
	public void setDayOfYear(Integer dayOfYear) {
		this.dayOfYear = dayOfYear;
	}
	
	public String getDateDisplay() {
		return dateDisplay;
	}
	public void setDateDisplay(String dateDisplay) {
		this.dateDisplay = dateDisplay;
	}
	/**
	 * Convenience method to add a comment to the list
	 * @param comment
	 */
	public void addComment(ConversationComment comment, boolean beginning) {
		if (comments == null) {
			comments = new ArrayList<ConversationComment>();
		}
		if (beginning)
			comments.add(0, comment);
		else
			comments.add(comment);
	}
	
	@Override
	public int compareTo(ConversationDateCommentInfo o) {
		return dayOfYear.compareTo(o.getDayOfYear());
	}
	
}
