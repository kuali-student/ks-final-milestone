package org.kuali.student.core.messages.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MessageGroupKeyList")
public class MessageGroupKeyList implements java.io.Serializable {

	private static final long serialVersionUID = -840422925676029014L;

	@XmlElement(name = "messageGroup", required = true)
	protected List<String> messageGroupKeys;

	public List<String> getMessageGroupKeys() {
		if (messageGroupKeys == null) {
			messageGroupKeys = new ArrayList<String>();
		}
		return this.messageGroupKeys;
	}

	public void setMessageGroupKeys(List<String> l) {
		messageGroupKeys = l;

	}
}