package org.kuali.student.ap.plannerreview.dto;

import org.kuali.student.ap.plannerreview.infc.Conversation;
import org.kuali.student.ap.plannerreview.infc.ConversationAdvisor;
import org.kuali.student.ap.plannerreview.infc.ConversationComment;
import org.w3c.dom.Element;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConversationInfo", propOrder = { "learningPlanId", "advisor", "closed", "comments", "topic", "_futureElements" })
public class ConversationInfo implements Conversation, Serializable {
	

	private static final long serialVersionUID = -6571614169803413175L;

	@XmlElement
	private String learningPlanId;
	
	@XmlElement
	private ConversationAdvisor advisor;
	
	@XmlElement
	private boolean closed;
	
	@XmlElement
	private List<ConversationComment> comments;
	
	@XmlElement
	private String topic;
	
	@SuppressWarnings("unused")
	@XmlAnyElement
	private List<Element> _futureElements;

	@Override
	public String getLearningPlanId() {
		return learningPlanId;
	}
	
	public void setLearningPlanId(String learningPlanId) {
		this.learningPlanId = learningPlanId;
	}

	public ConversationAdvisor getAdvisor() {
		return advisor;
	}

	public void setAdvisor(ConversationAdvisor advisor) {
		this.advisor = advisor;
	}

	@Override
	public boolean isClosed() {
		return closed;
	}
	
	public void setClosed(boolean closed) {
		this.closed = closed;
	}

	@Override
	public List<ConversationComment> getComments() {
		return comments;
	}
	
	public void setComments(List<ConversationComment> comments) {
		this.comments = comments;
	}
	
	@Override
	public String getTopic() {
		return topic;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}

	public ConversationComment getLatestComment() {
		if (comments != null && comments.size() > 0) {
			return comments.get(comments.size()-1);
		}
		return null;
	}
	
	public boolean hasUnreadComments() {
		if (comments != null) {
			for (ConversationComment comment : comments) {
				if (!comment.isReadOnce())
					return true;
			}
		}
		return false;
	}
}
