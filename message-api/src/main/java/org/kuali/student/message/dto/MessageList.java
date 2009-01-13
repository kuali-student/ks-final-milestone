package org.kuali.student.message.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "MessageList")
public class MessageList implements java.io.Serializable{

  @XmlElement(name = "message", required = true)
  protected List<Message> messages;


  public List<Message> getMessages() {
      if (messages == null) {
          messages = new ArrayList<Message>();
      }
      return this.messages;
  }

  public void setMessages(List<Message> l) {
      messages = l;

  } 
}